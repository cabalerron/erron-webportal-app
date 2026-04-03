/*
 * RM001
 *
 * v 00.001 - 10/28/2024
 *
 * PIC: emonteverde
 * 
 */
package ph.com.project01.webportal.repository.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import ph.com.project01.webportal.common.PortalConstants;
import ph.com.project01.webportal.model.RoleMaster;
import ph.com.project01.webportal.model.Pagination;
import ph.com.project01.webportal.repository.RM001Repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@RequiredArgsConstructor
@Slf4j
public class RM001RepositoryImpl implements RM001Repository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    // Get all role data
    public Page<RoleMaster> getRoleList(String loggedInUsername, String loggedInUserRole, Pagination page) {
        try {
            StringBuilder sbSelect = new StringBuilder("SELECT role_id, role_name, role_sh_name, del_flag, create_id, create_date, update_id, update_date FROM project01.role_mst WHERE del_flag = '0'");
            StringBuilder sbCount = new StringBuilder("SELECT COUNT(*) FROM project01.role_mst WHERE del_flag = '0'");
            
            Long count = jdbcTemplate.queryForObject(sbCount.toString(), Long.class);

            Pageable pageable = PageRequest.of(page.getPage(), PortalConstants.PAGE_SIZE);
            sbSelect.append(" LIMIT ? OFFSET ?");
            
            List<Object> params = new ArrayList<>();
            params.add(pageable.getPageSize());
            params.add(pageable.getOffset());

            RowMapper<RoleMaster> rowMapper = new BeanPropertyRowMapper<>(RoleMaster.class);
            List<RoleMaster> roleList = jdbcTemplate.query(sbSelect.toString(), rowMapper, params.toArray());

            return new PageImpl<>(roleList, pageable, count);

        } catch (DataAccessException ex) {
            log.error("Error fetching role list", ex);
            throw new RuntimeException("Failed to fetch role list", ex);
        }
    }

    // Search a role name
    public Page<RoleMaster> searchRoleName(String sRoleName, Pagination page) {
        try {
            StringBuilder sbSelect = new StringBuilder("SELECT role_id, role_name, role_sh_name, del_flag, create_id, create_date, update_id, update_date FROM project01.role_mst AS n");
            StringBuilder sbCount = new StringBuilder("SELECT COUNT(*) FROM project01.role_mst AS n");
            StringBuilder sbWhere = new StringBuilder();
            List<Object> params = new ArrayList<>();

            if (sRoleName != null && !sRoleName.isEmpty()) {
                sbWhere.append(" LOWER(n.role_name) LIKE ? ");
                params.add("%" + sRoleName.toLowerCase() + "%");
            }

            if (sbWhere.length() > 0) sbWhere.append(" AND ");
            sbWhere.append(" n.del_flag = '0' ");

            if (sbWhere.length() > 0) {
                sbSelect.append(" WHERE ").append(sbWhere);
                sbCount.append(" WHERE ").append(sbWhere);
            }

            Long count = jdbcTemplate.queryForObject(sbCount.toString(), Long.class, params.toArray());

            Pageable pageable = PageRequest.of(page.getPage(), PortalConstants.PAGE_SIZE);
            sbSelect.append(" LIMIT ? OFFSET ? ");
            params.add(pageable.getPageSize());
            params.add(pageable.getOffset());

            RowMapper<RoleMaster> rowMapper = new BeanPropertyRowMapper<>(RoleMaster.class);
            List<RoleMaster> list = jdbcTemplate.query(sbSelect.toString(), rowMapper, params.toArray());

            return new PageImpl<>(list, pageable, count);

        } catch (DataAccessException ex) {
            log.error("Error searching role by name", ex);
            throw new RuntimeException("Failed to search roles by name", ex);
        }
    }

    // Delete multiple items
    public Page<RoleMaster> deleteItems(List<String> sItems, Pagination page) {
        try {
            String updateSql = "UPDATE project01.role_mst SET del_flag = 1 WHERE role_id = ?";

            for (String id : sItems) {
                Integer roleId = Integer.parseInt(id);
                jdbcTemplate.update(updateSql, roleId);
            }

            String countSql = "SELECT COUNT(*) FROM project01.role_mst WHERE del_flag = '0'";
            Long count = jdbcTemplate.queryForObject(countSql, Long.class);

            Pageable pageable = PageRequest.of(page.getPage(), PortalConstants.PAGE_SIZE);
            StringBuilder sbSelect = new StringBuilder("SELECT * FROM project01.role_mst WHERE del_flag = '0' ");
            sbSelect.append(" LIMIT ? OFFSET ?");

            List<Object> params = new ArrayList<>();
            params.add(pageable.getPageSize());
            params.add(pageable.getOffset());

            RowMapper<RoleMaster> rowMapper = new BeanPropertyRowMapper<>(RoleMaster.class);
            List<RoleMaster> roleList = jdbcTemplate.query(sbSelect.toString(), rowMapper, params.toArray());

            return new PageImpl<>(roleList, pageable, count);

        } catch (DataAccessException ex) {
            log.error("Error deleting roles", ex);
            throw new RuntimeException("Failed to delete roles", ex);
        }
    }

}
