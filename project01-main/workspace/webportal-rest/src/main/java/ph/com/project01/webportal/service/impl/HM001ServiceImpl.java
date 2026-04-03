/*
 * HM001
 *
 * v 00.001 - 10/21/2024
 *
 * PIC: emonteverde
 */

package ph.com.project01.webportal.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ph.com.project01.webportal.model.News;
import ph.com.project01.webportal.repository.impl.HM001RepositoryImpl;
import ph.com.project01.webportal.service.HM001Service;

@Service
public class HM001ServiceImpl implements HM001Service {

    @Autowired
    private HM001RepositoryImpl hm001RepositoryImpl;


    @Override
    public List<News> getArcticleTitle() {
        List<News> articleTitle = hm001RepositoryImpl.getNewArticle();
        return articleTitle;
    }

     @Override
    public String getScreenTitle() {
        StringBuilder screenTitle = new StringBuilder();
        // String tier1 = hm001RepositoryImpl.getHM001FunctionName("0").split(" ")[0];
        String tier2 = hm001RepositoryImpl.getHM001FunctionName("131");
        screenTitle.append(" ").append(tier2);
        return screenTitle.toString();
    }

}
