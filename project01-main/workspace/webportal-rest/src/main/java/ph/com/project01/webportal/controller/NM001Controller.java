/*
 * NM001
 *
 * v 00.001 - 10/21/2024
 *
 * PIC: emonteverde
 */

package ph.com.project01.webportal.controller;
import ph.com.project01.webportal.Exception.BusinessOperationException ;
import ph.com.project01.webportal.common.PortalConstants ;
import ph.com.project01.webportal.form.NM001Form ;
import ph.com.project01.webportal.model.News ;
import ph.com.project01.webportal.model.Pagination ;
import ph.com.project01.webportal.service.NM001Service;
import ph.com.project01.webportal.util.LogUtil ;
import ph.com.project01.webportal.util.ValidationUtils ;
import java.util.Arrays ;
import java.util.List ;
import java.util.Map ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParserFactory ;
import org.springframework.data.domain.Page ;
import org.springframework.http.HttpStatus ;
import org.springframework.http.ResponseEntity ;
import org.springframework.web.bind.annotation.PostMapping ;
import org.springframework.web.bind.annotation.RequestParam ;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NM001Controller {

    LogUtil log = new LogUtil( " SCREEN ID: NM001 " ) ;

    @Autowired
    NM001Service NM001service;

    @Autowired
    NM001Form nm001Form;

    /**
     * Init
     * @param loggedInUsername
     * @param loggedInUserRole
     * @param pageRequest
     * @return
     */
    @PostMapping("/initNM001")
    public ResponseEntity<NM001Form> init(
        @RequestParam("loggedInUsername")
        String loggedInUsername,
        @RequestParam("loggedInUserRole")
        String loggedInUserRole,
        @RequestParam( "pageRequest" )
        String pageRequest
    ){
        nm001Form = new NM001Form();

        if( ValidationUtils.mandatoryCheck(loggedInUsername)
            || ValidationUtils.mandatoryCheck(loggedInUserRole)){
            
            log.error( " USERNAME : " + loggedInUsername + " ROLE ID : " 
            + loggedInUserRole, " ONLOAD " 
            + PortalConstants.ERR_MANDATORY_MSG );
            
            throw new BusinessOperationException( 
                PortalConstants.ERR_MANDATORY_MSG ) ;
        }
        if(ValidationUtils.validSizeCheck(loggedInUsername, 10)
            || ValidationUtils.validSizeCheck(loggedInUserRole, 1)){
            
            log.error( " USERNAME : " + loggedInUsername + " ROLE ID : " 
            + loggedInUserRole, " ONLOAD " 
            +  PortalConstants.ERR_MANDATORY_MSG ) ;
            
            throw new BusinessOperationException( 
                PortalConstants.ERR_SIZE_MSG ) ;
        }

        if(ValidationUtils.alphaNumericCheck(loggedInUsername)
            || ValidationUtils.alphaNumericCheck(loggedInUserRole)){
            
            log.error( " USERNAME : " + loggedInUsername + " ROLE ID : " 
            + loggedInUserRole, " ONLOAD " 
            +  PortalConstants.ERR_ALPHANUMERIC_MSG ) ;
            
            throw new BusinessOperationException( 
                PortalConstants.ERR_ALPHANUMERIC_MSG ) ;
        }

        Map<String, Object> map = JsonParserFactory.getJsonParser().parseMap( 
            pageRequest ) ;

        Pagination page = new Pagination() ;
        page.setPage( Integer.parseInt( map.get( "page" ).toString() ));

        Page<News> result = NM001service.getNewsList(
            loggedInUsername, loggedInUserRole, page);

        log.info( " USERNAME : " + loggedInUsername + " ROLE ID : " 
        + loggedInUserRole, "OPERATION : ONLOAD " ) ;

        String screenTitle = NM001service.getScreenTitle();

        nm001Form.setScreenTitle(screenTitle);
        nm001Form.setNM001List(result);
        nm001Form.setsParam01(loggedInUsername);
        nm001Form.setsParam02(loggedInUserRole);

        return ResponseEntity.ok(nm001Form);
    }

    /**
     * Search 
     * @param sNewsId
     * @param sTitle
     * @param sCreateBy
     * @param pageRequest
     * @return
     */
    @PostMapping( "/searchNM001" )
    public ResponseEntity<NM001Form> searchDispNM001(
        @RequestParam("Param01")
        String loggedInUsername,
        @RequestParam("Param02")
        String loggedInUserRole,
        @RequestParam("newsid")
        String sNewsId,
        @RequestParam("title")
        String sTitle,
        @RequestParam("createdby")
        String sCreateBy,
        @RequestParam( "pageRequest" )
        String pageRequest
    ){

        nm001Form = new NM001Form();

        //User Access validation
        if( ValidationUtils.mandatoryCheck(loggedInUsername)
            || ValidationUtils.mandatoryCheck(loggedInUserRole)){
        
            log.error( " USERNAME : " + loggedInUsername + " ROLE ID : " 
            + loggedInUserRole, " ON CLICK SEARCH " 
            + PortalConstants.ERR_MANDATORY_MSG );
        
            throw new BusinessOperationException( 
                PortalConstants.ERR_MANDATORY_MSG ) ;
        }
        if(ValidationUtils.validSizeCheck(loggedInUsername, 10)
            || ValidationUtils.validSizeCheck(loggedInUserRole, 1)){
        
            log.error( " USERNAME : " + loggedInUsername + " ROLE ID : " 
            + loggedInUserRole, " ON CLICK SEARCH " 
            +  PortalConstants.ERR_MANDATORY_MSG ) ;
        
            throw new BusinessOperationException( 
                PortalConstants.ERR_SIZE_MSG ) ;
        }

        if(ValidationUtils.alphaNumericCheck(loggedInUsername)
            || ValidationUtils.alphaNumericCheck(loggedInUserRole)){
        
            log.error( " USERNAME : " + loggedInUsername + " ROLE ID : " 
            + loggedInUserRole, " ON CLICK SEARCH " 
            +  PortalConstants.ERR_ALPHANUMERIC_MSG ) ;
        
            throw new BusinessOperationException( 
                PortalConstants.ERR_ALPHANUMERIC_MSG ) ;
        }

        if(ValidationUtils.adminAccess(loggedInUserRole)){

            log.error( " USERNAME : " + loggedInUsername + " ROLE ID : " 
            + loggedInUserRole, " ON CLICK SEARCH " 
            +  PortalConstants.ERR_ADMIN_ACCESS ) ;

            throw new BusinessOperationException(
                PortalConstants.ERR_ADMIN_ACCESS, HttpStatus.UNAUTHORIZED);
        }

        //Input check
        if(ValidationUtils.mandatoryCheck( sNewsId )
            && ValidationUtils.mandatoryCheck( sTitle )
            && ValidationUtils.mandatoryCheck( sCreateBy )){
            
            log.error(" ON CLICK SEARCH " + PortalConstants.ERR_MANDATORY_MSG );
        
            throw new BusinessOperationException( 
                PortalConstants.ERR_MANDATORY_MSG ) ;  
        }

        Map<String, Object> map = JsonParserFactory.getJsonParser().parseMap( 
            pageRequest ) ;

        Pagination page = new Pagination() ;
        page.setPage( Integer.parseInt( map.get( "page" ).toString() ));

        Page<News> result = NM001service.
                        NM001search(
                            sNewsId, 
                            sTitle,  
                            sCreateBy, page);

        nm001Form.setNM001List(result);

        log.info( "OPERATION : ON CLICK SEARCH " ) ;

        return ResponseEntity.ok(nm001Form);
    }


    /**
     * Delete Multiple
     * @param sItems
     * @param pageRequest
     * @return
     */
    @PostMapping( "/deleteMultiNM001" )
    public ResponseEntity<NM001Form> deleteMulti(
        @RequestParam("Param01")
        String loggedInUsername,
        @RequestParam("Param02")
        String loggedInUserRole,
        @RequestParam("SelectedItems") 
        String sItems,
        @RequestParam( "pageRequest" )
        String pageRequest
        ) {
        
        nm001Form  = new NM001Form();

        //User Access validation
        if( ValidationUtils.mandatoryCheck(loggedInUsername)
            || ValidationUtils.mandatoryCheck(loggedInUserRole)){
        
            log.error( " USERNAME : " + loggedInUsername + " ROLE ID : " 
            + loggedInUserRole, " ON CLICK DELETE MULTIPLE " 
            + PortalConstants.ERR_MANDATORY_MSG );
        
            throw new BusinessOperationException( 
                PortalConstants.ERR_MANDATORY_MSG ) ;
        }
        if(ValidationUtils.validSizeCheck(loggedInUsername, 10)
            || ValidationUtils.validSizeCheck(loggedInUserRole, 1)){
        
            log.error( " USERNAME : " + loggedInUsername + " ROLE ID : " 
            + loggedInUserRole, " ON CLICK DELETE MULTIPLE " 
            +  PortalConstants.ERR_MANDATORY_MSG ) ;
        
            throw new BusinessOperationException( 
                PortalConstants.ERR_SIZE_MSG ) ;
        }

        if(ValidationUtils.alphaNumericCheck(loggedInUsername)
            || ValidationUtils.alphaNumericCheck(loggedInUserRole)){
        
            log.error( " USERNAME : " + loggedInUsername + " ROLE ID : " 
            + loggedInUserRole, " ON CLICK DELETE MULTIPLE " 
            +  PortalConstants.ERR_ALPHANUMERIC_MSG ) ;
        
            throw new BusinessOperationException( 
                PortalConstants.ERR_ALPHANUMERIC_MSG ) ;
        }

        if(ValidationUtils.adminAccess(loggedInUserRole)){

            log.error( " USERNAME : " + loggedInUsername + " ROLE ID : " 
            + loggedInUserRole, " ON CLICK DELETE MULTIPLE " 
            +  PortalConstants.ERR_ADMIN_ACCESS ) ;

            throw new BusinessOperationException(
                PortalConstants.ERR_ADMIN_ACCESS, HttpStatus.UNAUTHORIZED);
        }

        if(ValidationUtils.mandatoryCheck(sItems)){

            log.error(" ON DELETE MULTIPLE " 
            + PortalConstants.ERR_MANDATORY_MSG );
        
            throw new BusinessOperationException( 
                PortalConstants.ERR_MANDATORY_MSG ) ;  
        }

        Map<String, Object> map = JsonParserFactory.getJsonParser().parseMap( 
            pageRequest ) ;

        Pagination page = new Pagination() ;
        page.setPage( Integer.parseInt( map.get( "page" ).toString() ));

        List<String> selectedItemIds = Arrays.asList(sItems.split(","));

        Page<News> result = NM001service.deleteMultipleItems(
            selectedItemIds, page);

        nm001Form.setNM001List(result);
        log.info( "OPERATION : ON CLICK DELETE MULTIPLE " ) ;

        return ResponseEntity.ok(nm001Form);
    }
}
