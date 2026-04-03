/*
 * NM002
 *
 * v 00.001 - 10/25/2024
 *
 * PIC: emonteverde
 * 
 * Description: Used to define a method for adding a news in the database.
 * 
 */
package ph.com.project01.webportal.repository;

import ph.com.project01.webportal.model.AddNews;

public interface NM002Repository {
    Long addNews(AddNews addNews);
}
