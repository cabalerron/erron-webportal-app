/*
 * NM002
 *
 * v 00.001 - 10/23/2024
 *
 * PIC: emonteverde
 *  
 * Description: Used to define the method that will add the news
 *
 */
package ph.com.project01.webportal.service.impl;

import ph.com.project01.webportal.model.AddNews;
import ph.com.project01.webportal.repository.NM002Repository;
import ph.com.project01.webportal.service.NM002Service;
import ph.com.project01.webportal.service.NewsFileService;

import java.io.IOException;
import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;

@Service
public class NM002ServiceImpl implements NM002Service {

    @Autowired
    private NM002Repository newsRepository; // Repository for saving news

    @Autowired
    private NewsFileService newsfileService; // File service for handling files

    @Override
    @Transactional
    public void saveNews(AddNews addNews, MultipartFile image) throws IOException {
        // Use FileService to handle file saving
        String imagePathInDb = newsfileService.saveFile(image);
        addNews.setImage_path(imagePathInDb); // Set the relative path in the news object

        // Set the current timestamp for createDate and updateDate
        Timestamp currentTimestamp = Timestamp.from(Instant.now());
        addNews.setCreate_date(currentTimestamp);
        addNews.setUpdate_date(currentTimestamp);

        // Save the News object to the database
        newsRepository.addNews(addNews);
    }

    @Override
    @Transactional
    public void saveNewsWithPath(AddNews addNews, String imagePath) {
        // Set the image path directly
        addNews.setImage_path(imagePath); // Set the provided image path in the news object

        // Set the current timestamp for createDate and updateDate
        Timestamp currentTimestamp = Timestamp.from(Instant.now());
        addNews.setCreate_date(currentTimestamp);
        addNews.setUpdate_date(currentTimestamp);

        // Save the News object to the database
        newsRepository.addNews(addNews);
    }
}
