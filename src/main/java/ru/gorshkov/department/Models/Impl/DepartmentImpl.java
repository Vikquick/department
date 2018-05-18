package ru.gorshkov.department.Models.Impl;

import org.springframework.format.annotation.DateTimeFormat;
import ru.gorshkov.department.Models.Intf.Department;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DepartmentImpl implements Department {

    private Integer id;
    private String name;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date creationdate;
    private Integer departmentid;
    private List<DepartmentImpl> child = new ArrayList<>();

    public DepartmentImpl(Integer id, String name, Date creationdate, Integer departmentid) {
        this.id = id;
        this.name = name;
        this.creationdate = creationdate;
        this.departmentid = departmentid;
    }

    public DepartmentImpl() {
    }

    public DepartmentImpl(String name, Date creationdate, Integer departmentid) {
        this.name = name;
        this.creationdate = creationdate;
        this.departmentid = departmentid;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Date getCreationdate() {
        return creationdate;
    }

    @Override
    public void setCreationdate(Date creationdate) {
        this.creationdate = creationdate;
    }

    @Override
    public Integer getDepartmentid() {
        return departmentid;
    }

    @Override
    public void setDepartmentid(Integer departmentid) {
        this.departmentid = departmentid;
    }

    @Override
    public List<DepartmentImpl> getChild() {
        return child;
    }

    @Override
    public void addChild(DepartmentImpl child) {
        this.child.add(child);
    }

    @Override
    public void setChild(List<DepartmentImpl> child) {
        this.child = child;
    }

    @Override
    public String toString() {
        return "DepartmentImpl{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", creationdate=" + creationdate +
                ", departmentid=" + departmentid +
                ", child=" + child +
                '}';
    }
}
