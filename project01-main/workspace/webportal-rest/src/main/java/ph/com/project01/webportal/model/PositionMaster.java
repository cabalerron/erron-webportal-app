package ph.com.project01.webportal.model;

import java.sql.Timestamp;

public class PositionMaster {
  
  private int positionId;

  private String positionName;

  private String positionShName;

  private int delFlag;

  private String createId;

  private Timestamp createDate;

  private String updateId;

  private Timestamp updateDate;

  public int getPositionId() {
    return positionId;
  }

  public void setPositionId(int positionId) {
    this.positionId = positionId;
  }

  public String getPositionName() {
    return positionName;
  }

  public void setPositionName(String positionName) {
    this.positionName = positionName;
  }

  public String getPositionShName() {
    return positionShName;
  }

  public void setPositionShName(String positionShName) {
    this.positionShName = positionShName;
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
