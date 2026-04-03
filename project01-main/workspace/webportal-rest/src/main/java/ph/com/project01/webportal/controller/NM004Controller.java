/*
 * NM004
 *
 * v 00.001 - 11/07/2024
 *
 * PIC: emonteverde
 * 
 */

package ph.com.project01.webportal.controller;
import org.springframework.beans.factory.annotation.Autowired ;
import org.springframework.http.HttpStatus ;
import org.springframework.http.ResponseEntity ;
import org.springframework.web.bind.annotation.PostMapping ;
import org.springframework.web.bind.annotation.RequestParam ;
import org.springframework.web.bind.annotation.RestController ;
import ph.com.project01.webportal.Exception.BusinessOperationException ;
import ph.com.project01.webportal.common.PortalConstants ;
import ph.com.project01.webportal.form.NM004Form ;
import ph.com.project01.webportal.model.News ;
import ph.com.project01.webportal.service.NM004Service ;
import ph.com.project01.webportal.util.DateUtil ;
import ph.com.project01.webportal.util.LogUtil ;
import ph.com.project01.webportal.util.ValidationUtils ;

@RestController
public class NM004Controller {

    LogUtil log = new LogUtil( " SCREEN ID: NM004 " ) ;

    @Autowired
    NM004Form nm004Form;

    @Autowired
    NM004Service nm004service;

    /**
     * ONLOAD FUNCTION
     * @param loggedInUsername
     * @param loggedInUserRole
     * @param sNewsId
     * @return
     */
    @PostMapping("/initNM004")
    public ResponseEntity<NM004Form> init(
        @RequestParam("loggedInUsername")
        String loggedInUsername,
        @RequestParam("loggedInUserRole")
        String loggedInUserRole,
        @RequestParam("NewsId")
        String sNewsId
    ){

        nm004Form = new NM004Form();

        if( ValidationUtils.mandatoryCheck(loggedInUsername)
            || ValidationUtils.mandatoryCheck(loggedInUserRole)
            || ValidationUtils.mandatoryCheck(sNewsId)){
            
            log.error( " USERNAME : " + loggedInUsername + " ROLE ID : " 
            + loggedInUserRole + "NEWS ID" + sNewsId, " ONLOAD " 
            + PortalConstants.ERR_MANDATORY_MSG );
            
            throw new BusinessOperationException( PortalConstants.ERR_MANDATORY_MSG ) ;
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
            || ValidationUtils.alphaNumericCheck(loggedInUserRole)
            || ValidationUtils.mandatoryCheck(sNewsId) ){
        
            log.error( " USERNAME : " + loggedInUsername + " ROLE ID : " 
            + loggedInUserRole + "NEWS ID" + sNewsId, " ONLOAD " 
            +  PortalConstants.ERR_ALPHANUMERIC_MSG ) ;
        
            throw new BusinessOperationException( 
                PortalConstants.ERR_ALPHANUMERIC_MSG ) ;
        }

        String screenTitle = nm004service.getScreenTitle();
        News  resultNews = nm004service.getNews(sNewsId);

        nm004Form.setScreenTitle(screenTitle);
        nm004Form.setsParam01(loggedInUsername);
        nm004Form.setsParam02(loggedInUserRole);

        nm004Form.setNewsId(resultNews.getNewsId());
        nm004Form.setTitle(resultNews.getTitle());
        nm004Form.setContent(resultNews.getContent());
        nm004Form.setImgPath(resultNews.getImagePath());
        
        nm004Form.setStartDate(DateUtil.convertToLocalDate(
            resultNews.getStartDate()));
        nm004Form.setEndDate(DateUtil.convertToLocalDate(
            resultNews.getEndDate()));

        nm004Form.setDelFlag(resultNews.getDelFlag());
        nm004Form.setCreateId(resultNews.getCreateId());
        nm004Form.setCreateDate(resultNews.getCreateDate());
        nm004Form.setUpdateId(resultNews.getUpdateId());
        nm004Form.setUpdateDate(resultNews.getUpdateDate());

        log.info( " USERNAME : " + loggedInUsername + " ROLE ID : " 
        + loggedInUserRole + "NEWS ID" 
        + sNewsId, "OPERATION : ONLOAD " ) ;

        return ResponseEntity.ok(nm004Form);
    }


    /**
     * Delete Process
     * @param loggedInUsername
     * @param loggedInUserRole
     * @param sNewsId
     * @return
     */
    @PostMapping("/deleteNM004")
    public ResponseEntity<NM004Form> deleteNM004(
        @RequestParam("loggedInUsername") String loggedInUsername,
        @RequestParam("loggedInUserRole") String loggedInUserRole,
        @RequestParam("NewsId") String sNewsId
    ){
        nm004Form = new NM004Form();

        //User Access validation
        if( ValidationUtils.mandatoryCheck(loggedInUsername)
            || ValidationUtils.mandatoryCheck(loggedInUserRole)
            || ValidationUtils.mandatoryCheck(sNewsId) ){
        
            log.error( " USERNAME : " + loggedInUsername + " ROLE ID : " 
            + loggedInUserRole + "NEWS ID" + sNewsId, " ON CLICK UPDATE " 
            + PortalConstants.ERR_MANDATORY_MSG );
        
            throw new BusinessOperationException( 
                PortalConstants.ERR_MANDATORY_MSG ) ;
        }
        if(ValidationUtils.validSizeCheck(loggedInUsername, 10)
            || ValidationUtils.validSizeCheck(loggedInUserRole, 1)){
        
            log.error( " USERNAME : " + loggedInUsername + " ROLE ID : " 
            + loggedInUserRole, " ON CLICK UPDATE " 
            +  PortalConstants.ERR_MANDATORY_MSG ) ;
        
            throw new BusinessOperationException( 
                PortalConstants.ERR_SIZE_MSG ) ;
        }

        if(ValidationUtils.alphaNumericCheck(loggedInUsername)
            || ValidationUtils.alphaNumericCheck(loggedInUserRole)
            || ValidationUtils.alphaNumericCheck(sNewsId) ){
        
            log.error( " USERNAME : " + loggedInUsername + " ROLE ID : " 
            + loggedInUserRole + "NEWS ID" + sNewsId, " ON CLICK UPDATE " 
            +  PortalConstants.ERR_ALPHANUMERIC_MSG ) ;
        
            throw new BusinessOperationException( 
                PortalConstants.ERR_ALPHANUMERIC_MSG ) ;
        }

        if(ValidationUtils.adminAccess(loggedInUserRole)){

            log.error( " USERNAME : " + loggedInUsername + " ROLE ID : " 
            + loggedInUserRole, " ON CLICK UPDATE " 
            +  PortalConstants.ERR_ADMIN_ACCESS ) ;

            throw new BusinessOperationException(
                PortalConstants.ERR_ADMIN_ACCESS, HttpStatus.UNAUTHORIZED);
        }

        try{
            nm004service.deleteNews(Integer.parseInt(sNewsId),
            loggedInUsername);
            
        }catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok(nm004Form);
    }
}
