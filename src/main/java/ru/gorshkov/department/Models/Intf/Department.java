package ru.gorshkov.department.Models.Intf;

import ru.gorshkov.department.Models.Impl.DepartmentImpl;
import ru.gorshkov.department.Models.Intf.Basic.IdAble;
import ru.gorshkov.department.Models.Intf.Basic.Nameable;

import java.util.Date;
import java.util.List;

public interface Department extends Nameable, IdAble {
    Date getCreationdate();

    void setCreationdate(Date creationdate);

    Integer getDepartmentid();

    void setDepartmentid(Integer departmentid);

    List<DepartmentImpl> getChild();

    void addChild(DepartmentImpl child);

    void setChild(List<DepartmentImpl> child);
}
