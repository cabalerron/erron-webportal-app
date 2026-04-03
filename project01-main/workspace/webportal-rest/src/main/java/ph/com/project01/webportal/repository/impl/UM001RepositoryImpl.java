package ph.com.project01.webportal.repository.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import ph.com.project01.webportal.common.PortalConstants;
import ph.com.project01.webportal.dto.UM001OutDto;
import ph.com.project01.webportal.dto.searchUserDTO;
import ph.com.project01.webportal.model.Pagination;
import ph.com.project01.webportal.repository.UM001Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UM001RepositoryImpl implements UM001Repository {

    @Autowired
    JdbcTemplate jdbcTemplate;


    public String getFunctionName(String functionCode) {
        String sql = 
        "SELECT function_name "
        + "FROM project01.function_mst " 
        + "WHERE function_code = ?";

        try {
        String functionName = jdbcTemplate
        .queryForObject(sql, String.class, functionCode);
        return functionName;
        } catch (EmptyResultDataAccessException e) {
        return null;
        }
    }
 
    public List<UM001OutDto> getUserList() {
        String query = "SELECT i.p_id, i.user_id, i.first_name, i.last_name, i.del_flag, i.create_date, "
                + "u.account_id, u.mailaddress, u.position_id, u.department_id, u.section_id, u.status_id, u.role_id, "
                + "p.position_sh_name, d.department_sh_name, s.section_sh_name, r.role_sh_name "
                + "FROM project01.user_info AS i "
                + "INNER JOIN project01.user_mst AS u ON i.user_id = u.user_id "
                + "INNER JOIN project01.position_mst AS p ON u.position_id = p.position_id "
                + "INNER JOIN project01.department_mst AS d ON u.department_id = d.department_id "
                + "INNER JOIN project01.section_mst AS s ON u.section_id = s.section_id "
                + "INNER JOIN project01.role_mst AS r ON u.role_id = r.role_id "
                + "WHERE i.del_flag = 0 "
                + "ORDER BY u.account_id ASC;";

        try {
            RowMapper<UM001OutDto> rowMapper = new BeanPropertyRowMapper<>(UM001OutDto.class);
            List<UM001OutDto> items = jdbcTemplate.query(query, rowMapper);

            return items;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    // <----------------Search -------------------------->
    public List<UM001OutDto> findUserByCriteria(searchUserDTO searchCriteria) {
        // SQL Query Builder with JOIN
        StringBuilder sb = new StringBuilder(
                "SELECT ui.user_id, ui.first_name, ui.last_name, ui.del_flag, ui.create_date, " +
                        "um.account_id, um.mailaddress, um.position_id, um.department_id, um.section_id, " +
                        "p.position_sh_name, d.department_sh_name, s.section_sh_name " +
                        "FROM project01.user_info ui " +
                        "INNER JOIN project01.user_mst AS um ON ui.user_id = um.user_id " +
                        "INNER JOIN project01.position_mst AS p ON um.position_id = p.position_id " +
                        "INNER JOIN project01.department_mst AS d ON um.department_id = d.department_id " +
                        "INNER JOIN project01.section_mst AS s ON um.section_id = s.section_id " +
                        "WHERE um.del_flag = 0");

        List<Object> params = new ArrayList<>();

        // Add conditions dynamically based on searchCriteria
        if (searchCriteria.getFirstName() != null && !searchCriteria.getFirstName().isEmpty()) {
            sb.append(" AND (LOWER(ui.first_name) LIKE LOWER(?) OR LOWER(ui.last_name) LIKE LOWER(?))");
            params.add("%" + searchCriteria.getFirstName() + "%");
            params.add("%" + searchCriteria.getLastName() + "%");
        }
        if (searchCriteria.getPositionShName() != null && !searchCriteria.getPositionShName().isEmpty()) {
            sb.append(" AND LOWER(p.position_sh_name) LIKE LOWER(?)");
            params.add("%" + searchCriteria.getPositionShName() + "%");
        }
        if (searchCriteria.getDepartmentShName() != null && !searchCriteria.getDepartmentShName().isEmpty()) {
            sb.append(" AND LOWER(d.department_sh_name) LIKE LOWER(?)");
            params.add("%" + searchCriteria.getDepartmentShName() + "%");
        }
        if (searchCriteria.getSectionShName() != null && !searchCriteria.getSectionShName().isEmpty()) {
            sb.append(" AND LOWER(s.section_sh_name) LIKE LOWER(?)");
            params.add("%" + searchCriteria.getSectionShName() + "%");
        }

        // RowMapper to map result set to UM001OutDto object
        RowMapper<UM001OutDto> rowMapper = new BeanPropertyRowMapper<>(UM001OutDto.class);

        // Execute the query and return the list of users
        return jdbcTemplate.query(sb.toString(), rowMapper, params.toArray());
    }

    public Page<UM001OutDto> deleteItems(List<String> sItems, Pagination page) {

        // Convert string IDs to integers
        List<Integer> iItems = sItems.stream()
                                .map(Integer::parseInt)
                                .toList();

        // Batch update del_flag to 1 for both user_info and user_mst tables
        String updateSqlUserInfo = "UPDATE project01.user_info SET del_flag = 1 WHERE user_id = ?";
        String updateSqlUserMst = "UPDATE project01.user_mst SET del_flag = 1 WHERE user_id = ?";
        
        try {
            jdbcTemplate.batchUpdate(updateSqlUserInfo, iItems, iItems.size(), 
                (ps, id) -> ps.setInt(1, id));
            jdbcTemplate.batchUpdate(updateSqlUserMst, iItems, iItems.size(),
                (ps, id) -> ps.setInt(1, id));
            
            System.out.println("User archiving process completed for userIds: " + sItems);
            
        } catch (DataAccessException e) {
            System.err.println("Error in deleteItems method: " + e.getMessage());
            return Page.empty();  // Return an empty page in case of an error
        }

        // Count active users
        // String countSql = "SELECT COUNT(*) FROM project01.user_info WHERE del_flag = 0";
        String countSql = "SELECT COUNT(*) FROM project01.user_info ui " +
                    "INNER JOIN project01.user_mst AS um ON ui.user_id = um.user_id " +
                    "WHERE ui.del_flag = 0 AND um.del_flag = 0";


        Long count = jdbcTemplate.queryForObject(countSql, Long.class);

        // Setup pagination
        Pageable pageable = PageRequest.of(page.getPage(), PortalConstants.PAGE_SIZE);

        // Select users for the current page with del_flag = 0
        String selectSql = "SELECT ui.user_id, ui.first_name, ui.last_name, ui.del_flag, ui.create_date, " +
                    "um.account_id, um.mailaddress, um.position_id, um.department_id, um.section_id, " +
                    "p.position_sh_name, d.department_sh_name, s.section_sh_name " +
                    "FROM project01.user_info ui " +
                    "INNER JOIN project01.user_mst AS um ON ui.user_id = um.user_id " +
                    "INNER JOIN project01.position_mst AS p ON um.position_id = p.position_id " +
                    "INNER JOIN project01.department_mst AS d ON um.department_id = d.department_id " +
                    "INNER JOIN project01.section_mst AS s ON um.section_id = s.section_id " +
                    "WHERE ui.del_flag = 0 AND um.del_flag = 0 " +
                    "LIMIT ? OFFSET ?";

        List<Object> params = List.of(pageable.getPageSize(), pageable.getOffset());

        // RowMapper to map the result to the User class
        RowMapper<UM001OutDto> rowMapper = new BeanPropertyRowMapper<>(UM001OutDto.class);

        // Execute the query
        List<UM001OutDto> userList = jdbcTemplate.query(selectSql, rowMapper, params.toArray());

        // Return paginated result
        return new PageImpl<>(userList, pageable, count);
    }

}
