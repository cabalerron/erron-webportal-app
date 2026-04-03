/*
 * RM001
 *
 * v 00.001 - 10/28/2024
 *
 * PIC: emonteverde
 * 
 */
package ph.com.project01.webportal.form;

import org.springframework.data.domain.Page ;
import org.springframework.stereotype.Component;

import ph.com.project01.webportal.model.RoleMaster ;

@Component
public class RM001Form extends BaseForm {

    private static final long serialVersionUID = 1L;

    private Page<RoleMaster> pageRM001 ;

    private String errMessage;

    // ----------------------------------------
    // Getters and Setters
    // ----------------------------------------

    public String getErrMessage() {
        return errMessage;
    }

    public void setErrMessage(String errMessage) {
        this.errMessage = errMessage;
    }

    public Page<RoleMaster> getRM001List(){
        return pageRM001;
    }

    public void setRM001List(Page<RoleMaster> pageRM001){
        this.pageRM001 = pageRM001;
    }
}
