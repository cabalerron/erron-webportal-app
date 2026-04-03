/*
 * NM001
 *
 * v 00.001 - 10/21/2024
 *
 * PIC: emonteverde
 */

package ph.com.project01.webportal.form;

import org.springframework.data.domain.Page ;
import org.springframework.stereotype.Component;

import ph.com.project01.webportal.model.News ;


@Component
public class NM001Form extends BaseForm {

    private static final long serialVersionUID = 1L;

    private Page<News> pageNM001 ;

    private String errMessage;

    private String screenTitle;

    // ----------------------------------------
    // Getters and Setters
    // ----------------------------------------

    public String getScreenTitle() {
        return this.screenTitle;
      }
    
    public void setScreenTitle(String screenTitle) {
    this.screenTitle = screenTitle;
    }

    public String getErrMessage() {
        return errMessage;
    }

    public void setErrMessage(String errMessage) {
        this.errMessage = errMessage;
    }

    public Page<News> getNM001List(){
        return pageNM001;
    }

    public void setNM001List(Page<News> pageNM001){
        this.pageNM001 = pageNM001;
    }
}
