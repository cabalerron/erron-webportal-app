/*
 * NM002
 *
 * v 00.001 - 10/25/2024
 *
 * PIC: emonteverde
 * 
 * Description: Used to define a method for adding news.
 * 
 */
package ph.com.project01.webportal.service;

import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;
import ph.com.project01.webportal.model.AddNews;

public interface NM002Service {
    void saveNews(AddNews addNews, MultipartFile image) throws IOException;

    // New method to save news with an existing image path
    void saveNewsWithPath(AddNews addNews, String imagePath);
}
