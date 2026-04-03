/*
 * Business Operation Exception
 *
 * v 00.001 - 11/06/2024
 *
 * PIC: emonteverde
 */

package ph.com.project01.webportal.Exception;

import org.springframework.http.HttpStatus ;

public class BusinessOperationException extends RuntimeException{
    
    private static final long serialVersionUID = 1L ;

    private HttpStatus httpStatus;

    /**
     * INTERNAL_SERVER_ERROR
     * @param message
     */
    public BusinessOperationException(String message){
        super( message );
        this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    /**
     * INTERNAL_SERVER_ERROR
     * @param message
     * @param cause
     */
    public BusinessOperationException( String message, Throwable cause ) {

        super( message, cause ) ;
        this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    /**
     * 
     * @param message
     * @param httpStatus
     */
    public BusinessOperationException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    /**
     * 
     * @return
     */
    public HttpStatus getHttpStatus() {
        return httpStatus; 
    }
}
