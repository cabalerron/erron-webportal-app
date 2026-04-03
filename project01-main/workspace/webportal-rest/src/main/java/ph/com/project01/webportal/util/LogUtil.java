/*
 * LogUtil
 *
 * v 00.001 - 10/21/2024
 *
 * PIC: emonteverde
 */

package ph.com.project01.webportal.util;
import org.slf4j.Logger ;
import org.slf4j.LoggerFactory ;


public class LogUtil {

    private Logger log ;

    /**
     * LogUtil
     */
    public LogUtil() {

        StackTraceElement ste = Thread.currentThread().getStackTrace()[3] ;
        String className = ste.getClassName() ;
        log = LoggerFactory.getLogger( className ) ;
    }

    /**
     * LogUtil
     * @param className
     */
    public LogUtil( String className ) {

        log = LoggerFactory.getLogger( className ) ;
    }

    /**
     * info
     * @param message
     * @param exception
     */
    public void info(
        String message,
        Exception exception ) {

        log.info( message, exception ) ;
    }

    /**
     * info
     * @param user
     * @param message
     * @param exception
     */
    public void info(
        String user,
        String message,
        Exception exception ) {

        log.info( message, exception ) ;
    }

    /**
     * Info
     * @param message
     */
    public void info(
        String message ) {

        log.info( message ) ;
    }

    /**
     * Info
     * @param user
     * @param message
     */
    public void info(
        String user,
        String message ) {

        log.info( "{} {} {}", user, log.getName(), message ) ;
    }

    /**
     * Error
     * @param message
     * @param exception
     */
    public void error(
        String message,
        Exception exception ) {

        log.error( message, exception ) ;
    }

    /**
     * Error
     * @param message
     */
    public void error(
        String message ) {

        log.error( message ) ;
    }


    public void error(
        String user,
        String message ) {

        log.error( "{} {} {}", user, log.getName(), message ) ;
    }
    
}
