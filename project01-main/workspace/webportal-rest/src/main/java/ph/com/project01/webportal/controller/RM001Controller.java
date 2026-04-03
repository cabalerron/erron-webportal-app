/*
 * RM001
 *
 * v 00.001 - 10/28/2024
 *
 * PIC: emonteverde
 * 
 */
package ph.com.project01.webportal.controller;
import ph.com.project01.webportal.form.RM001Form;
import ph.com.project01.webportal.model.RoleMaster;
import ph.com.project01.webportal.model.Pagination;
import ph.com.project01.webportal.service.RM001Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/role")
public class RM001Controller {

    @Autowired
    private RM001Service rm001Service;

    @Autowired
    RM001Form rm001Form;

    @PostMapping("/list")
    public ResponseEntity<RM001Form> init(
        @RequestParam("loggedInUsername")
        String loggedInUsername,
        @RequestParam("loggedInUserRole")
        String loggedInUserRole,
        @RequestParam( "pageRequest" )
        String pageRequest
    ){
        rm001Form = new RM001Form();

        rm001Form.setsParam01(loggedInUsername);
        rm001Form.setsParam02(loggedInUserRole);

        Map<String, Object> map = JsonParserFactory.getJsonParser().parseMap( pageRequest ) ;
        Pagination page = new Pagination() ;
        page.setPage( Integer.parseInt( map.get( "page" ).toString() ));

        Page<RoleMaster> result = rm001Service.getRoleList(loggedInUsername, loggedInUserRole, page);

        rm001Form.setRM001List(result);
        rm001Form.setsParam01(loggedInUsername);
        rm001Form.setsParam02(loggedInUserRole);

        return ResponseEntity.ok(rm001Form);
    }

    @PostMapping( "/search" )
    public ResponseEntity<RM001Form> searchDispNM001(
        @RequestParam("roleName")
        String sRoleName,
        @RequestParam( "pageRequest" )
        String pageRequest
    ){

        rm001Form = new RM001Form();

        Map<String, Object> map = JsonParserFactory.getJsonParser().parseMap( pageRequest ) ;
        Pagination page = new Pagination() ;
        page.setPage( Integer.parseInt( map.get( "page" ).toString() ));

        Page<RoleMaster> result = rm001Service.
                        searchRoleName(sRoleName, page);
                        rm001Form.setRM001List(result);

        return ResponseEntity.ok(rm001Form);
    }

    @PostMapping( "/deleteMulti" )
    public ResponseEntity<RM001Form> deleteMulti(
        @RequestParam("SelectedItems") 
        String sItems,
        @RequestParam( "pageRequest" )
        String pageRequest
        ) {

        Map<String, Object> map = JsonParserFactory.getJsonParser().parseMap( pageRequest ) ;
        Pagination page = new Pagination() ;
        page.setPage( Integer.parseInt( map.get( "page" ).toString() ));

        List<String> selectedItemIds = Arrays.asList(sItems.split(","));

        Page<RoleMaster> result = rm001Service.deleteMultipleItems(selectedItemIds, page);

        rm001Form.setRM001List(result);

        return ResponseEntity.ok(rm001Form);
    }
}
