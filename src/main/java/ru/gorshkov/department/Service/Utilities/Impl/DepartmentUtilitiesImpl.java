package ru.gorshkov.department.Service.Utilities.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.gorshkov.department.Models.Impl.DepartmentImpl;
import ru.gorshkov.department.Service.Mappers.DepartmentMapper;
import ru.gorshkov.department.Service.Utilities.Intf.DepartmentUtilities;

@Service("departmentService")
public class DepartmentUtilitiesImpl implements DepartmentUtilities {

    @Autowired
    DepartmentMapper departmentMapper;

    @Override
    public boolean isNameUnique(String name) {

        DepartmentImpl department = departmentMapper.getDepartmentByName(name);
        return department != null;
    }

    @Override
    public boolean haveChild(Integer id) {
        return departmentMapper.getDaughterDepartments(id) != null;
    }

    public DepartmentImpl buildChildTree(DepartmentImpl root) {

        if (haveChild(root.getId())) {
            for (DepartmentImpl d : departmentMapper.getDaughterDepartments(root.getId())) {
                root.addChild(d);
                buildChildTree(d);
            }
        }
        return root;
    }

    @Override
    public DepartmentImpl buildParentTree(DepartmentImpl child) {
        DepartmentImpl parent = null;
        if (departmentMapper.getDepartmentById(child.getDepartmentid()) != null) {
            parent = departmentMapper.getDepartmentById(child.getDepartmentid());
            parent.addChild(child);
            return buildParentTree(parent);
        } else return child;
    }
}
