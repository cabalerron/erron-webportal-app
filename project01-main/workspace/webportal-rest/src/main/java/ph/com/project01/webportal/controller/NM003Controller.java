/*
 * NM003
 *
 * v 00.001 - 11/06/2024
 *
 * PIC: emonteverde
 * 
 * Description: Used to handle requests related to edit news.
 * 
 */

package ph.com.project01.webportal.controller;

import org.springframework.web.bind.annotation.RestController ;
import org.springframework.web.multipart.MultipartFile ;

import ph.com.project01.webportal.Exception.BusinessOperationException ;
import ph.com.project01.webportal.common.PortalConstants ;
import ph.com.project01.webportal.form.ImageForm ;
import ph.com.project01.webportal.form.NM003Form ;
import ph.com.project01.webportal.model.News ;
import ph.com.project01.webportal.service.NM003Service ;
import ph.com.project01.webportal.util.DateUtil ;
import ph.com.project01.webportal.util.LogUtil ;
import ph.com.project01.webportal.util.ValidationUtils ;

import org.springframework.beans.factory.annotation.Autowired ;
import org.springframework.beans.factory.annotation.Value ;
import org.springframework.core.io.Resource ;
import org.springframework.core.io.support.ResourcePatternResolver ;
import org.springframework.http.HttpStatus ;
import org.springframework.http.ResponseEntity ;
import org.springframework.web.bind.annotation.GetMapping ;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam ;
import java.sql.Timestamp;
import java.util.ArrayList ;
import java.util.List ;
import java.io.IOException ;
import java.sql.Date ;
import ph.com.project01.webportal.service.FileService;


@RestController
public class NM003Controller {

    LogUtil log = new LogUtil( " SCREEN ID: NM003 " ) ;

    private final ResourcePatternResolver resourcePatternResolver;

    @Value("${image.folder.path:/images/News/default-news-images/}")
    private String imageFolderPath;
    
    @Value("${news.date.format}")
    private String dateFormatPattern;

    @Autowired
    NM003Form nm003Form;

    @Autowired
    NM003Service nm003service;

    @Autowired
    FileService fileService;

    /**
     * ONLOAD FUNCTION
     * @param loggedInUsername
     * @param loggedInUserRole
     * @param sNewsId
     * @return
     */
    @PostMapping("/initNM003")
    public ResponseEntity<NM003Form> init(
        @RequestParam("loggedInUsername")
        String loggedInUsername,
        @RequestParam("loggedInUserRole")
        String loggedInUserRole,
        @RequestParam("NewsId")
        String sNewsId
    ){

        nm003Form = new NM003Form();

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

        String screenTitle = nm003service.getScreenTitle();
        News  resultNews = nm003service.getNews(sNewsId);

        nm003Form.setScreenTitle(screenTitle);
        nm003Form.setsParam01(loggedInUsername);
        nm003Form.setsParam02(loggedInUserRole);

        nm003Form.setNewsId(resultNews.getNewsId());
        nm003Form.setTitle(resultNews.getTitle());
        nm003Form.setContent(resultNews.getContent());
        nm003Form.setImgPath(resultNews.getImagePath());
        
        nm003Form.setStartDate(DateUtil.convertToLocalDate(
            resultNews.getStartDate()));
        nm003Form.setEndDate(DateUtil.convertToLocalDate(
            resultNews.getEndDate()));

        nm003Form.setDelFlag(resultNews.getDelFlag());
        nm003Form.setCreateId(resultNews.getCreateId());
        nm003Form.setCreateDate(resultNews.getCreateDate());
        nm003Form.setUpdateId(resultNews.getUpdateId());
        nm003Form.setUpdateDate(resultNews.getUpdateDate());

        log.info( " USERNAME : " + loggedInUsername + " ROLE ID : " 
        + loggedInUserRole + "NEWS ID" 
        + sNewsId, "OPERATION : ONLOAD " ) ;

        return ResponseEntity.ok(nm003Form);
    }


    /**
     * Update Function
     * @param loggedInUsername
     * @param loggedInUserRole
     * @param sNewsId
     * @param sCreateId
     * @param sTitle
     * @param sContent
     * @param sStartDate
     * @param sEndDate
     * @param sImgPath
     * @return
     */
    @PostMapping("/updateNM003")
    public NM003Form updateNM003(
        @RequestParam("loggedInUsername") String loggedInUsername,
        @RequestParam("loggedInUserRole") String loggedInUserRole,
        @RequestParam("NewsId") String sNewsId,
        @RequestParam("CreateBy") String sCreateId,
        @RequestParam("Title") String sTitle,
        @RequestParam("Content") String sContent,
        @RequestParam("StartDate") String sStartDate,
        @RequestParam("EndDate") String sEndDate,
        @RequestParam(value = "ImgFile", required = false) MultipartFile file,
        @RequestParam(value = "ImgPath", required = false) String sImgPath
    ) {
        NM003Form nm003Form = new NM003Form();

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

        //Input check
        if(ValidationUtils.mandatoryCheck( sNewsId )
        || ValidationUtils.mandatoryCheck( sTitle )
        || ValidationUtils.mandatoryCheck( sCreateId )
        || ValidationUtils.mandatoryCheck( sContent )
        || ValidationUtils.mandatoryCheck( sStartDate )
        || ValidationUtils.mandatoryCheck(  sEndDate )){
        
        log.error(" ON CLICK UPDATE " + PortalConstants.ERR_MANDATORY_MSG );
    
        throw new BusinessOperationException( PortalConstants.ERR_MANDATORY_MSG ) ; 

        }

        News newsModel = nm003service.getNews(sNewsId);
        StringBuilder sb = new StringBuilder() ;

        try{

            if (file != null) {
                String imgPath = fileService.saveNewsFile(file);
                System.out.println(imgPath);
                newsModel.setImagePath(imgPath);
                sb.append( "Image " );
            }

            if(file == null){

                if( !sImgPath.equals( newsModel.getImagePath() ) ) {
                    newsModel.setImagePath(sImgPath);
                    sb.append( "image " );
                }
            }

            if( !sTitle.equals( newsModel.getTitle() ) ) {
                newsModel.setTitle(sTitle);
                sb.append( "title " );
            }

            if( !sContent.equals( newsModel.getContent() ) ) {
                newsModel.setContent(sContent);
                sb.append( "content " );
            }

            if( !sStartDate.equals( DateUtil.convertToLocalDate(
                newsModel.getStartDate()).toString() ) ) {
                newsModel.setStartDate(Date.valueOf( sStartDate ) );
                sb.append( "start_date " );
            }

            if( !sEndDate.equals( DateUtil.convertToLocalDate(
                newsModel.getEndDate()).toString()) ) {
                newsModel.setEndDate(Date.valueOf(  sEndDate ));
                sb.append( "end_date " );
            }

            if( sb.isEmpty() ){
                // if the input data no changes
                nm003Form.setIsdetailsUpdated(false);
            }
            else{
                // if the input data has different
                newsModel.setUpdateId(sCreateId);
                newsModel.setUpdateDate(new Timestamp(System.currentTimeMillis()));

                nm003service.updateNewsItem(newsModel);

                nm003Form.setIsdetailsUpdated(true);

            }

            nm003Form.setNewsId(newsModel.getNewsId());
            nm003Form.setTitle(newsModel.getTitle());
            nm003Form.setContent(newsModel.getContent());
            nm003Form.setImgPath(newsModel.getImagePath());
            nm003Form.setStartDate(DateUtil.convertToLocalDate(
                newsModel.getStartDate()));

            nm003Form.setEndDate(DateUtil.convertToLocalDate(
                newsModel.getEndDate()));

            nm003Form.setUpdateId(newsModel.getUpdateId());
            nm003Form.setUpdateDate(newsModel.getUpdateDate());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return nm003Form;
    }

    /**
     * Constructor for resource
     * @param resourcePatternResolver
     */
    public NM003Controller(ResourcePatternResolver resourcePatternResolver) {
        this.resourcePatternResolver = resourcePatternResolver;
    }

    /**
     * Get Default Images
     * @return
     */
    @GetMapping("/NM003/displayImages")
    public ResponseEntity<List<ImageForm>> getImageNames() {
        try {
            List<ImageForm> ImageForms = new ArrayList<>();
            Resource[] resources = resourcePatternResolver.getResources(
                "classpath:static" + imageFolderPath + "*");

            for (Resource resource : resources) {
                String filename = resource.getFilename();
                String path = imageFolderPath + filename; 
                ImageForms.add(new ImageForm(filename, path)); 
            }
            return ResponseEntity.ok(ImageForms);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
}
