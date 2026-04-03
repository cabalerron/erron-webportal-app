/*
 * NM002
 *
 * v 00.001 - 10/25/2024
 *
 * PIC: emonteverde
 * 
 * Description: Used to provide methods for creating directories and generating unique filenames
 * 
 */
package ph.com.project01.webportal.util;

import java.io.File;
import java.io.IOException;

import ph.com.project01.webportal.common.PortalConstants;

public class NewsFileUtil {

    // Method to create a directory if it doesn't exist
    public static void createDirectory(String directoryPath) throws IOException {
        File directory = new File(directoryPath);

        if (!directory.exists()) {
            if (directory.mkdirs()) {
            } else {
                throw new IOException(PortalConstants.ERR_CREATE_DIRECTORY + directoryPath);
            }
        } else {
        }
    }

    // Method to generate a unique filename based on the original filename and timestamp
    public static String generateUniqueFileName(String originalFilename) {
        String fileExtension = originalFilename != null ? originalFilename.substring(originalFilename.lastIndexOf('.')) : ".png";
        String fileName = originalFilename != null ? originalFilename.replace(fileExtension, "") : "image";
        String timestamp = String.valueOf(System.currentTimeMillis());
        String uniqueFileName = fileName + "_" + timestamp + fileExtension;

        return uniqueFileName;
    }
}
