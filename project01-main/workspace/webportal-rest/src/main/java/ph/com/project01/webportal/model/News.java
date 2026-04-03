/*
 * NM001
 *
 * v 00.001 - 10/21/2024
 *
 * PIC: emonteverde
 */

package ph.com.project01.webportal.model;

import jakarta.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "news")
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int news_id;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(nullable = true, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false, length = 500)
    private String image_path;

    @Column(nullable = false)
    private Date start_date;

    @Column(nullable = false)
    private Date end_date;

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

    //--------------------------
    // Getters and Setters
    //--------------------------

    public int getNewsId() {
        return news_id;
    }

    public void setNewsId(int news_id) {
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

    public String getImagePath() {
        return image_path;
    }

    public void setImagePath(String image_path) {
        this.image_path = image_path;
    }

    public Date getStartDate() {
        return start_date;
    }

    public void setStartDate(Date start_date) {
        this.start_date = start_date;
    }

    public Date getEndDate() {
        return end_date;
    }

    public void setEndDate(Date end_date) {
        this.end_date = end_date;
    }

    public int getDelFlag() {
        return del_flag;
    }

    public void setDelFlag(int del_flag) {
        this.del_flag = del_flag;
    }

    public String getCreateId() {
        return create_id;
    }

    public void setCreateId(String create_id) {
        this.create_id = create_id;
    }

    public Timestamp getCreateDate() {
        return create_date;
    }

    public void setCreateDate(Timestamp create_date) {
        this.create_date = create_date;
    }

    public String getUpdateId() {
        return update_id;
    }

    public void setUpdateId(String update_id) {
        this.update_id = update_id;
    }

    public Timestamp getUpdateDate() {
        return update_date;
    }

    public void setUpdateDate(Timestamp update_date) {
        this.update_date = update_date;
    }
}
