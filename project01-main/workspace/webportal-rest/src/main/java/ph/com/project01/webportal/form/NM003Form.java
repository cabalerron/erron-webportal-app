/*
 * NM003
 *
 * v 00.001 - 11/06/2024
 *
 * PIC: emonteverde
 */


package ph.com.project01.webportal.form;
import java.sql.Timestamp ;
import org.springframework.stereotype.Component ;
import java.time.LocalDate;

@Component
public class NM003Form extends BaseForm{
    
    private String screenTitle;
    private int newsId;
    private String title;
    private String content;
    private String imgPath;
    private LocalDate startDate;
    private LocalDate endDate;
    private int delFlag;
    private String createId;
    private Timestamp createDate;
    private String updateId;
    private Timestamp updateDate;

    private Boolean isdetailsUpdated ;


    // Getter and Setter

    public String getScreenTitle() {
        return this.screenTitle;
      }
    
    public void setScreenTitle(String screenTitle) {
    this.screenTitle = screenTitle;
    }

    public int getNewsId() {
        return newsId;
    }

    public void setNewsId(int newsId) {
        this.newsId = newsId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public int getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(int delFlag) {
        this.delFlag = delFlag;
    }

    public String getCreateId() {
        return createId;
    }

    public void setCreateId(String createId) {
        this.createId = createId;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public String getUpdateId() {
        return updateId;
    }

    public void setUpdateId(String updateId) {
        this.updateId = updateId;
    }

    public Timestamp getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Timestamp updateDate) {
        this.updateDate = updateDate;
    }

    public Boolean getIsdetailsUpdated() {

        return isdetailsUpdated ;
    }

    public void setIsdetailsUpdated(
        Boolean isdetailsUpdated ) {

        this.isdetailsUpdated = isdetailsUpdated ;
    }
}
