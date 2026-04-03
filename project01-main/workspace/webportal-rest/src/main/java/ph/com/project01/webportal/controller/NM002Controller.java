/*
 * NM002
 *
 * v 00.001 - 10/25/2024
 *
 * PIC: emonteverde
 * 
 * Description: Used to handle requests related to adding news.
 * 
 */
package ph.com.project01.webportal.controller;

import ph.com.project01.webportal.common.PortalConstants;
import ph.com.project01.webportal.model.AddNews;
import ph.com.project01.webportal.service.NM002Service;

import jakarta.annotation.PostConstruct;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/news")
public class NM002Controller {

    @Autowired
    private NM002Service newsService;

    // Inject the property value for the upload directory
    @Value("${news.file.upload-dir}")
    private String uploadDir;

    @Value("${news.start-end-date-format}")
    private String newsStartEndDateFormat;

    @Value("${news.main.default.image-folder}")
    private String newsMainDefaultImageFolder;

    @Value("${news.valid.image-extensions}")
    private String newsValidImageExtensions;

    private Path staticDir;

    @PostConstruct
    public void init() {
        // Initialize the staticDir with the absolute path
        this.staticDir = Paths.get(System.getProperty("user.dir"), uploadDir);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addNews(
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam(value = "image", required = false) MultipartFile image, // Optional image
            @RequestParam(value = "imagePath", required = false) String imagePath, // Existing image path
            @RequestParam("startDate") String startDateString,
            @RequestParam("endDate") String endDateString,
            @RequestParam(value = "createId") String createId) {
        try {
            // Define the expected date format (MM-DD-YYYY)
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(newsStartEndDateFormat);

            LocalDate startDate = LocalDate.parse(startDateString, formatter);
            LocalDate endDate = LocalDate.parse(endDateString, formatter);

            // Create AddNews object
            AddNews addNews = new AddNews();
            addNews.setTitle(title);
            addNews.setContent(content);
            addNews.setStart_date(startDate);
            addNews.setEnd_date(endDate);
            addNews.setCreate_id(createId);
            addNews.setDel_flag(0);

            // Save the news using the appropriate method
            if (image != null && !image.isEmpty()) {
                newsService.saveNews(addNews, image); // Save with image
            } else if (imagePath != null && !imagePath.isEmpty()) {
                newsService.saveNewsWithPath(addNews, imagePath); // Save with existing image path
            } else {
                return ResponseEntity.badRequest().body(PortalConstants.ERR_IMAGE_REQUIRED);
            }
            return ResponseEntity.ok(PortalConstants.INFO_NEWS_ADDED);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/default-news-images")
    public ResponseEntity<List<String>> getDefaultImages() {
        // Construct the path to the default images directory
        File folder = staticDir.resolve("default-news-images").toFile();
        List<String> imagePaths = new ArrayList<>();

        String[] extensions = newsValidImageExtensions.split(",");
        if (folder.exists() && folder.isDirectory()) {
            for (File file : folder.listFiles()) {
                if (file.isFile()) {
                    for (String ext : extensions) {
                        if (file.getName().endsWith(ext)) {
                            imagePaths.add(newsMainDefaultImageFolder + file.getName());
                            break;
                        }
                    }
                }
            }
        }
        else {
            return ResponseEntity.badRequest()
                    .body(List.of(PortalConstants.ERR_FOLDER_NOT_EXIST));
        }

        return ResponseEntity.ok(imagePaths);
    }

}
