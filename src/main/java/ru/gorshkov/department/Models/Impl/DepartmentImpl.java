package ru.gorshkov.department.Models.Impl;

import org.springframework.format.annotation.DateTimeFormat;
import ru.gorshkov.department.Models.Intf.Department;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DepartmentImpl implements Department {

    private Integer id;
    private String name;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate creationdate;
    private Integer departmentid;
    List<DepartmentImpl> child = new ArrayList<>();

    public DepartmentImpl(Integer id, String name, LocalDate creationdate, Integer departmentid) {
        this.id = id;
        this.name = name;
        this.creationdate = creationdate;
        this.departmentid = departmentid;
    }

    public DepartmentImpl() {
    }

    public DepartmentImpl(String name, LocalDate creationdate, Integer departmentid) {
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

    public LocalDate getCreationdate() {
        return creationdate;
    }

    public void setCreationdate(LocalDate creationdate) {
        this.creationdate = creationdate;
    }

    public Integer getDepartmentid() {
        return departmentid;
    }

    public void setDepartmentid(Integer departmentid) {
        this.departmentid = departmentid;
    }

    public List<DepartmentImpl> getChild() {
        return child;
    }

    public void addChild(DepartmentImpl child) {
        this.child.add(child);
    }

    public void setChild(List<DepartmentImpl> child) {
        this.child = child;
    }
}
