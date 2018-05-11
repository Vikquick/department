package ru.gorshkov.department.Service.Utilities.Intf;

import ru.gorshkov.department.Models.Impl.DepartmentImpl;

import java.util.List;

public interface DepartmentUtilities {

    boolean isNameUnique(String name);

    boolean haveChild(Integer id);

    DepartmentImpl buildChildTree(DepartmentImpl root);

    DepartmentImpl buildParentTree(DepartmentImpl child);
}
