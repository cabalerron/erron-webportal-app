package ph.com.project01.webportal.model;

import java.sql.Timestamp;

public class SectionMaster {
  private int sectionId;

  private String sectionName;

  private String sectionShName;

  private int delFlag;

  private String createId;

  private Timestamp createDate;

  private String updateId;

  private Timestamp updateDate;

  public int getSectionId() {
    return sectionId;
  }

  public void setSectionId(int sectionId) {
    this.sectionId = sectionId;
  }

  public String getSectionName() {
    return sectionName;
  }

  public void setSectionName(String sectionName) {
    this.sectionName = sectionName;
  }

  public String getSectionShName() {
    return sectionShName;
  }

  public void setSectionShName(String sectionShName) {
    this.sectionShName = sectionShName;
  }

  public int getDelFlag() {
    return delFlag;
  }

  public void setDelFlag(int delFlag) {
    this.delFlag = delFlag;
  }

  public String getCreateId() {
    return createId;
  }

  public void setCreateId(String createId) {
    this.createId = createId;
  }

  public Timestamp getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Timestamp createDate) {
    this.createDate = createDate;
  }

  public String getUpdateId() {
    return updateId;
  }

  public void setUpdateId(String updateId) {
    this.updateId = updateId;
  }

  public Timestamp getUpdateDate() {
    return updateDate;
  }

  public void setUpdateDate(Timestamp updateDate) {
    this.updateDate = updateDate;
  }
}
