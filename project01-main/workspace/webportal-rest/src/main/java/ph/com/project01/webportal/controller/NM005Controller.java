/*
 * NM005
 *
 * v 00.001 - 10/23/2024
 *
 * PIC: emonteverde
 * 
 * Description: Used to handle requests related to fetchinga filtered list of news.
 * 
 */
package ph.com.project01.webportal.controller;

import ph.com.project01.webportal.model.NewsBulletin;
import ph.com.project01.webportal.service.NM005Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/news")
public class NM005Controller {

    @Autowired
    private NM005Service newsService;

    // Fetch filtered news
    @GetMapping("/bulletin")
    public ResponseEntity<?> getFilteredNews() {
        List<NewsBulletin> filteredNews = newsService.getNewsList();
        return new ResponseEntity<>(filteredNews, HttpStatus.OK);
    }

}
