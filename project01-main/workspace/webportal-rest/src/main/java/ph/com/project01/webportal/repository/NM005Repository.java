/*
 * NM005
 *
 * v 00.001 - 10/23/2024
 *
 * PIC: emonteverde
 * 
 * Description: Used to define a method for retrieving a list of news from the database.
 * 
 */
package ph.com.project01.webportal.repository;

import ph.com.project01.webportal.model.NewsBulletin;
import java.util.List;

public interface NM005Repository{
    List<NewsBulletin> getNewsBulletinList();
}
