package ph.com.project01.webportal.util;

import java.text.SimpleDateFormat ;

import ph.com.project01.webportal.common.PortalConstants ;

public class ValidationUtils {

    /**
     * Returns true if the input string is null or empty
     * @param sParam
     * @return
     */
    public static Boolean mandatoryCheck(
        String sParam ) {

        return sParam == null || sParam.isBlank() ;
    }

    /**
     * Returns true if the input string is greater than the
     * @param sParam
     * @param iLength
     * @return
     */
    public static Boolean validSizeCheck(
        String sParam,
        int iLength ) {

        if ( sParam == null || sParam.isBlank() ) {
            return false ;
        }


        return sParam.length() > iLength ;
    }

    /**
     * Returns true if the input string not valid
     * @param sParam
     * @return
     */
    public static Boolean alphaNumericCheck(
        String sParam ) {

        if ( sParam == null || sParam.isBlank() ) {
            return false ;
        }


        return !sParam.matches( "^[a-zA-Z0-9]*$" ) ;
    }

    /**
     * Returns true if the input string is not valid
     * @param sParam
     * @return
     */
    public static Boolean numericCheck(
        String sParam ) {

        if ( sParam == null || sParam.isBlank() ) {
            return false ;
        }

        return !sParam.matches( "^[0-9]*$" ) ;
    }

    /**
     * Returns true if the input string is not valid date
     * @param sParam
     * @return
     */
    public static Boolean validDateCheck(
        String sParam ) {

        if ( sParam == null || sParam.isBlank() ) {
            return false ;
        }


        SimpleDateFormat sdf = new SimpleDateFormat( "yyyy/MM/dd" ) ;
        sdf.setLenient( false ) ;

        try {
            sdf.parse( sParam ) ;
        }
        catch ( Exception e ) {
            return true ;
        }
        return false ;
    }

    public static Boolean adminAccess(
        String sParam ) {

        return !PortalConstants.ADMIN_ROLE.equals(sParam);
    }
    
}
