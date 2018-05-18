package ru.gorshkov.department.Models.Impl;


import java.util.Date;

public class DepartmentInfo {

    private String name;
    private Date creationdate;
    private String fiohead;
    private Integer countemployers;


    public DepartmentInfo() {
    }

    public DepartmentInfo(String name, Date creationdate, String fiohead, Integer countemployers) {
        this.name = name;
        this.creationdate = creationdate;
        this.fiohead = fiohead;
        this.countemployers = countemployers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreationdate() {
        return creationdate;
    }

    public void setCreationdate(Date creationdate) {
        this.creationdate = creationdate;
    }

    public String getFiohead() {
        return fiohead;
    }

    public void setFiohead(String fiohead) {
        this.fiohead = fiohead;
    }

    public Integer getCountemployers() {
        return countemployers;
    }

    public void setCountemployers(Integer countemployers) {
        this.countemployers = countemployers;
    }

    @Override
    public String toString() {
        return "DepartmentInfo{" +
                "name='" + name + '\'' +
                ", creationdate=" + creationdate +
                ", fiohead='" + fiohead + '\'' +
                ", countemployers=" + countemployers +
                '}';
    }
}
