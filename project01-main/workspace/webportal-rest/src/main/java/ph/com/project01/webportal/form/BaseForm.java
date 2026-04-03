/*
 * Common
 *
 * v 00.001 - 10/21/2024
 *
 * PIC: emonteverde
 */

package ph.com.project01.webportal.form;
import java.io.Serializable;
import org.springframework.stereotype.Component;

@Component
public class BaseForm implements Serializable {

    private static final long serialVersionUID = 1L;

    // User ID
    private String sParam01;

    // Role ID
    private String sParam02;

    // ----------------------------------------
    // Getters and Setters
    // ----------------------------------------

    public String getsParam01() {
        return sParam01;
    }

    public void setsParam01(String sParam01) {
        this.sParam01 = sParam01;
    }

    public String getsParam02() {
        return sParam02;
    }

    public void setsParam02(String sParam02) {
        this.sParam02 = sParam02;
    }
}
