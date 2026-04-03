/*
 * NM005
 *
 * v 00.001 - 10/23/2024
 *
 * PIC: emonteverde
 * 
 * Description: Used to define a method for retrieving a list of filtered news.
 * 
 */
package ph.com.project01.webportal.service;

import ph.com.project01.webportal.model.NewsBulletin;
import java.util.List;

public interface NM005Service {
    List<NewsBulletin> getNewsList();

}
