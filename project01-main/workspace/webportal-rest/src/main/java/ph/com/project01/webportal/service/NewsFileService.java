/*
 * NM002
 *
 * v 00.001 - 10/25/2024
 *
 * PIC: emonteverde
 * 
 * Description: Used to handles file operations, validating file types, and saving uploaded files with unique names.
 * 
 */
package ph.com.project01.webportal.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ph.com.project01.webportal.common.PortalConstants;
import ph.com.project01.webportal.util.NewsFileUtil;

import jakarta.annotation.PostConstruct;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

@Service
public class NewsFileService {

    @Value("${news.allowed.file.types}")
    private String[] allowedFileTypes;

    @Value("${news.file.upload-dir}")
    private String newsUploadDir;

    @Value("${news.default-image}")
    private String newsDefaultImage;

    
    @Value("${news.image-folder}")
    private String newsImageFolder;

    private Path staticDir;

    @PostConstruct
    public void init() {
        try {
            // Dynamically resolve the 'static' directory path
            this.staticDir = Paths.get(System.getProperty("user.dir"), newsUploadDir);
            // Ensure the directory exists
            if (Files.exists(staticDir)) {
            } else {
                Files.createDirectories(staticDir);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public String saveFile(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            return newsDefaultImage;
        }

        String contentType = file.getContentType();

        if (!Arrays.asList(allowedFileTypes).contains(contentType)) {
            throw new IOException(PortalConstants.ERR_IMG_FILE_TYPE);
        }

        String originalFilename = file.getOriginalFilename();
        String uniqueFileName = NewsFileUtil.generateUniqueFileName(originalFilename);
        Path destinationPath = staticDir.resolve(uniqueFileName);
        file.transferTo(destinationPath.toFile());

        return newsImageFolder + uniqueFileName;
    }
}