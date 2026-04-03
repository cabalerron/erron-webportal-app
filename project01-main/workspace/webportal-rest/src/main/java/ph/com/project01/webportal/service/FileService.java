/*
 * FileService.java
 * File Service For User Image Upload
 * 
 * v 00.001 - 10/24/2024 
 * 
 * PIC: emonteverde
 */
package ph.com.project01.webportal.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;

@Service
public class FileService {

    // Define the relative path to the static folder within the project structure
    private final String baseDirectory = Paths
    .get("src/main/resources/static/images/user").toAbsolutePath().toString();

    // Define the relative path to the static folder within the project structure
    private final String newsDirectory = Paths
    .get("src/main/resources/static/images/News").toAbsolutePath().toString();

    // Read allowed file types
    @Value("${app.allowed.file.types}")
    private String[] allowedFileTypes;

    public String saveFile(MultipartFile file) throws IOException {
        // Create the directory if it does not exist
        File directory = new File(baseDirectory);
        if (!directory.exists() && !directory.mkdirs()) {
            throw new IOException("Failed to create directory.");
        }

        // Check if file is empty
        if (file == null || file.isEmpty()) {
            return "images/user/defaultImage.jpg";
        }

        String contentType = file.getContentType();

        // Check if the content type is in the allowed types
        if (!Arrays.asList(allowedFileTypes).contains(contentType)) {
            throw new IOException(
                "Invalid image file type. Only JPEG and PNG are allowed.");
        }

        // Get the original file extension
        String originalFilename = file.getOriginalFilename();
        String fileExtension = originalFilename != null ? originalFilename.
        substring(originalFilename.lastIndexOf('.')) : ".png"; 

        // New file name by appending the current timestamp on original filename
        String timestamp = String.valueOf(System.currentTimeMillis());
        String fileName = originalFilename != null ? originalFilename.
        replace(fileExtension, "") : "image";
        fileName = fileName + "_" + timestamp + fileExtension;

        File destinationFile = new File(baseDirectory + File.separator 
        + fileName);

        // Save the image file
        file.transferTo(destinationFile);

        // Return the relative path to be stored in the DB
        return "images/user/" + fileName;
    }

    /**
     * save News Image
     * @param file
     * @return
     * @throws IOException
     */
    public String saveNewsFile(MultipartFile file) throws IOException {
        // Create the directory if it does not exist
        File directory = new File(newsDirectory);
        if (!directory.exists() && !directory.mkdirs()) {
            throw new IOException("Failed to create directory.");
        }

        // Check if file is empty
        if (file == null || file.isEmpty()) {
            return "images/user/defaultImage.jpg";
        }

        String contentType = file.getContentType();

        // Check if the content type is in the allowed types
        if (!Arrays.asList(allowedFileTypes).contains(contentType)) {
            throw new IOException(
                "Invalid image file type. Only JPEG and PNG are allowed.");
        }

        // Get the original file extension
        String originalFilename = file.getOriginalFilename();
        String fileExtension = originalFilename != null ? originalFilename.
        substring(originalFilename.lastIndexOf('.')) : ".png"; 

        // New file name by appending the current timestamp on original filename
        String timestamp = String.valueOf(System.currentTimeMillis());
        String fileName = originalFilename != null ? originalFilename.
        replace(fileExtension, "") : "image";
        fileName = fileName + "_" + timestamp + fileExtension;

        File destinationFile = new File(newsDirectory + File.separator 
        + fileName);

        // Save the image file
        file.transferTo(destinationFile);

        // Return the relative path to be stored in the DB
        return "images/News/" + fileName;
    }
}
