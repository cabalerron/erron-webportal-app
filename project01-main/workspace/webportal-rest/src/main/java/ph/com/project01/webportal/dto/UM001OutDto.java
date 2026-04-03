package ph.com.project01.webportal.dto;

import java.time.LocalDateTime;

public class UM001OutDto {

    private Long userId;
    private String accountId;
    private String firstName;
    private String lastName;
    // private Integer position_id;
    private String positionShName;
    private String mailaddress;
    // private Integer department_id;
    private String departmentShName;
    // private Integer section_id;
    private String sectionShName;
    private LocalDateTime createDate;

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public UM001OutDto userId(Long userId) {
        setUserId(userId);
        return this;
    }

    public String getAccountId() {
        return this.accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public UM001OutDto accountId(String accountId) {
        setAccountId(accountId);
        return this;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public UM001OutDto firstName(String firstName) {
        setFirstName(firstName);
        return this;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public UM001OutDto lastName(String lastName) {
        setLastName(lastName);
        return this;
    }

    public String getPositionShName() {
        return this.positionShName;
    }

    public void setPositionShName(String positionShName) {
        this.positionShName = positionShName;
    }

    public UM001OutDto positionShName(String positionShName) {
        setPositionShName(positionShName);
        return this;
    }

    public String getMailaddress() {
        return this.mailaddress;
    }

    public void setMailaddress(String mailaddress) {
        this.mailaddress = mailaddress;
    }

    public UM001OutDto mailaddress(String mailaddress) {
        setMailaddress(mailaddress);
        return this;
    }

    public String getDepartmentShName() {
        return this.departmentShName;
    }

    public void setDepartmentShName(String departmentShName) {
        this.departmentShName = departmentShName;
    }

    public UM001OutDto departmentShName(String departmentShName) {
        setDepartmentShName(departmentShName);
        return this;
    }

    public String getSectionShName() {
        return this.sectionShName;
    }

    public void setSectionShName(String sectionShName) {
        this.sectionShName = sectionShName;
    }

    public UM001OutDto sectionShName(String sectionShName) {
        setSectionShName(sectionShName);
        return this;
    }

    public LocalDateTime getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public UM001OutDto createDate(LocalDateTime createDate) {
        setCreateDate(createDate);
        return this;
    }

}
