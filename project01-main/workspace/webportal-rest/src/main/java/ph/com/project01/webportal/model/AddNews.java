/*
 * NM002
 *
 * v 00.001 - 10/25/2024
 *
 * PIC: emonteverde
 * 
 * Description: Used to represent the news table in the database.
 * 
 */
package ph.com.project01.webportal.model;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.time.LocalDate;


@Entity
@Table(name = "news")
public class AddNews {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long news_id;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(nullable = true, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false, length = 500)
    private String image_path;

    @Column(nullable = false)
    private LocalDate start_date;

    @Column(nullable = false)
    private LocalDate end_date;

    @Column(nullable = false)
    private Integer del_flag;

    @Column(nullable = false, length = 50)
    private String create_id;

    @Column(nullable = false)
    private Timestamp create_date;

    @Column(nullable = true, length = 50)
    private String update_id;

    @Column(nullable = true)
    private Timestamp update_date;

    public Long getNews_id() {
        return news_id;
    }

    public void setNews_id(Long news_id) {
        this.news_id = news_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    public LocalDate getStart_date() {
        return start_date;
    }

    public void setStart_date(LocalDate startDate) {
        this.start_date = startDate;
    }

    public LocalDate getEnd_date() {
        return end_date;
    }

    public void setEnd_date(LocalDate endDate) {
        this.end_date = endDate;
    }

    public Integer getDel_flag() {
        return del_flag;
    }

    public void setDel_flag(Integer del_flag) {
        this.del_flag = del_flag;
    }

    public String getCreate_id() {
        return create_id;
    }

    public void setCreate_id(String create_id) {
        this.create_id = create_id;
    }

    public Timestamp getCreate_date() {
        return create_date;
    }

    public void setCreate_date(Timestamp create_date) {
        this.create_date = create_date;
    }

    public String getUpdate_id() {
        return update_id;
    }

    public void setUpdate_id(String update_id) {
        this.update_id = update_id;
    }

    public Timestamp getUpdate_date() {
        return update_date;
    }

    public void setUpdate_date(Timestamp update_date) {
        this.update_date = update_date;
    }

}
