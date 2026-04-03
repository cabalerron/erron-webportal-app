package ph.com.project01.webportal.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ph.com.project01.webportal.dto.UM001OutDto;
import ph.com.project01.webportal.dto.searchUserDTO;
import ph.com.project01.webportal.form.UM001Form;
import ph.com.project01.webportal.model.Pagination;
import ph.com.project01.webportal.service.UM001Service;


@RestController
public class UM001Controller {

    @Autowired
    private UM001Service um001Service;

    @Autowired
    UM001Form um001Form;

    @GetMapping("/userList")
    public List<UM001OutDto> getAllUseString() {
        return um001Service.getUserList();
    }

    @PostMapping("/searchUM001")
    public ResponseEntity<List<UM001OutDto>> findUserByCriteria(@RequestBody searchUserDTO searchCriteria) {
        List<UM001OutDto> users = um001Service.findUser(searchCriteria);

        if (users.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(users);
        }
    }
 
    @GetMapping("/functionName")
    public String getScreenTitle() {
        return um001Service.getScreenTitle();
    }
     
    @PostMapping("/deleteMultiUM001")
    public ResponseEntity<UM001Form> deleteMulti(
        @RequestParam("Param01")
        String sParam01,
        @RequestParam("Param02")
        String sParam02,
        @RequestParam("SelectedItems") 
        String sItems,
        @RequestParam( "pageRequest" )
        String pageRequest
        ) {
        
        um001Form  = new UM001Form();

       
        Map<String, Object> map = JsonParserFactory.getJsonParser().parseMap( pageRequest ) ;
        Pagination page = new Pagination() ;
        page.setPage( Integer.parseInt( map.get( "page" ).toString() ));

        List<String> selectedItemIds = Arrays.asList(sItems.split(","));

        Page<UM001OutDto> result = um001Service.deleteMultipleItems(selectedItemIds, page);

        um001Form.setUM001List(result);

        return ResponseEntity.ok(um001Form);
    }

}
