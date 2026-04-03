/*
 * Portal Constant 
 *
 * v 00.001 - 11/06/2024
 *
 * PIC: emonteverde
 */

package ph.com.project01.webportal.common;

public class PortalConstants {

    public static final int PAGE_SIZE = 10 ;
    public static final int ROLE_ADMIN = 1;
  	public static final int ROLE_USER = 2;

    //User Value
    public static final String ADMIN_ROLE = "1";

    /** Validation  */
    
    public static final String ERR_MANDATORY_MSG = " Required Entry Check ";

    public static final String ERR_SIZE_MSG = " Value Validity Check ";

    public static final String ERR_ALPHANUMERIC_MSG = " Character Type (Numeric/Alphanumeric) Check ";

    public static final String ERR_NUMERIC_MSG = " Character Type (Number) Check ";

    public static final String ERR_DATE_MSG = " Date Format Check ";

    public static final String ERR_ADMIN_ACCESS = " Not Authorized "; 
	
	public static final String SCHEMA = "project01"; 

  public static final String ERR_IMAGE_REQUIRED = " There is no image provided ";

  public static final String INFO_NEWS_ADDED = " News added successfully ";

  public static final String ERR_FOLDER_NOT_EXIST = " The specified default images path does not exist or is not a directory ";

  public static final String ERR_NEWS_ADD = " Error occurred while adding news to the database. ";

  public static final String ERR_IMG_FILE_TYPE = " Invalid image file type. Only JPEG and PNG are allowed. ";

  public static final String ERR_CREATE_DIRECTORY = " Failed to create directory: ";


}
