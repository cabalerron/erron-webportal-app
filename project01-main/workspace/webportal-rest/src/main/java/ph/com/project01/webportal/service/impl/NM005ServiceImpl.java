/*
 * NM005
 *
 * v 00.001 - 10/23/2024
 *
 * PIC: emonteverde
 *  
 * Description: Used to define the method to list all the news
 *
 */
package ph.com.project01.webportal.service.impl;

import ph.com.project01.webportal.model.NewsBulletin;
import ph.com.project01.webportal.repository.impl.NM005RepositoryImpl;
import ph.com.project01.webportal.service.NM005Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NM005ServiceImpl implements NM005Service {

    @Autowired
    private NM005RepositoryImpl newsBulletinRepositoryImpl;

    @Override
    public List<NewsBulletin> getNewsList(){
        List<NewsBulletin> a = newsBulletinRepositoryImpl.getNewsBulletinList();
        return a;
    }
}
