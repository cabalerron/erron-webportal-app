package ph.com.project01.webportal.form;

import org.springframework.data.domain.Page ;
import org.springframework.stereotype.Component;

import ph.com.project01.webportal.dto.UM001OutDto;

@Component
public class UM001Form extends BaseForm {

    private static final long serialVersionUID = 1L;

    private Page<UM001OutDto> pageUM001 ;

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

    public Page<UM001OutDto> getUM001List(){
        return pageUM001;
    }

    public void setUM001List(Page<UM001OutDto> pageUM001){
        this.pageUM001 = pageUM001;
    }
}
