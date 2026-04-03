/*
 * HM001
 *
 * v 00.001 - 10/21/2024
 *
 * PIC: emonteverde
 */

package ph.com.project01.webportal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import ph.com.project01.webportal.model.News;
import ph.com.project01.webportal.service.HM001Service;

@RestController

public class HM001Controller {

    @Autowired
    private HM001Service hm001Service;

    @GetMapping("/articleTitle")
    public List<News> getArticleTitle() {
        return hm001Service.getArcticleTitle();
    }

  @GetMapping("/hm001FunctionName")
    public String getScreenTitle() {
        return hm001Service.getScreenTitle();
    }
}
