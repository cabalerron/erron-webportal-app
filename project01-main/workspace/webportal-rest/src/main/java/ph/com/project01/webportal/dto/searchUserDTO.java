package ph.com.project01.webportal.dto;

public class searchUserDTO {

    private String firstName;
    private String lastName;
    private String positionShName;
    private String departmentShName;
    private String sectionShName;

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public searchUserDTO firstName(String firstName) {
        setFirstName(firstName);
        return this;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public searchUserDTO lastName(String lastName) {
        setLastName(lastName);
        return this;
    }

    public String getPositionShName() {
        return this.positionShName;
    }

    public void setPositionShName(String positionShName) {
        this.positionShName = positionShName;
    }

    public searchUserDTO positionShName(String positionShName) {
        setPositionShName(positionShName);
        return this;
    }

    public String getDepartmentShName() {
        return this.departmentShName;
    }

    public void setDepartmentShName(String departmentShName) {
        this.departmentShName = departmentShName;
    }

    public searchUserDTO departmentShName(String departmentShName) {
        setDepartmentShName(departmentShName);
        return this;
    }

    public String getSectionShName() {
        return this.sectionShName;
    }

    public void setSectionShName(String sectionShName) {
        this.sectionShName = sectionShName;
    }

    public searchUserDTO sectionShName(String sectionShName) {
        setSectionShName(sectionShName);
        return this;
    }

}
