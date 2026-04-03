/*
 * DateUtil
 *
 * v 00.001 - 10/21/2024
 *
 * PIC: emonteverde
 */

package ph.com.project01.webportal.util;

import java.text.SimpleDateFormat ;
import java.time.Instant ;
import java.time.LocalDate ;
import java.time.ZoneId ;
import java.util.Date ;


public class DateUtil {

    /**
     * Converts Date to LocalDate
     * 
     * @param dateToConvert
     * 
     * @return LocalDate
     */
    public static LocalDate convertToLocalDate(
        Date dateToConvert ) {

        return Instant.ofEpochMilli( dateToConvert.getTime() ).atZone( ZoneId.systemDefault() ).toLocalDate() ;
    }

    /**
     * Formats the date in the specified format
     * 
     * @param String
     *        date
     * 
     * @return String
     */
    public static String formatDate(
        String date ) {

        SimpleDateFormat sdf = new SimpleDateFormat( "yyyy/MM/dd" ) ;
        Date date1 = null ;
        try {
            date1 = sdf.parse( date ) ;
        }
        catch ( Exception e ) {
            e.printStackTrace() ;
        }
        SimpleDateFormat sdf2 = new SimpleDateFormat( "yyyy-MM-dd" ) ;
        return sdf2.format( date1 ) ;
    }
}
