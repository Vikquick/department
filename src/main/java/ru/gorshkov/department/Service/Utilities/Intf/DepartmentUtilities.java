package ru.gorshkov.department.Service.Utilities.Intf;

import ru.gorshkov.department.Models.Impl.DepartmentImpl;
import ru.gorshkov.department.Models.Intf.Employer;
import ru.gorshkov.department.Service.Utilities.Exception.CommonException;

import java.util.Date;

public interface DepartmentUtilities {

    void isNameUnique(String name) throws CommonException;

    boolean haveChild(Integer id);

    DepartmentImpl buildChildTree(DepartmentImpl root);

    DepartmentImpl buildParentTree(DepartmentImpl child);

    void isAvailable(Employer employer) throws CommonException;

    void twoHeadException(Integer departmentid, Boolean head) throws CommonException;

    void isNameValid(String name) throws CommonException;

    void isFioValid(String firstname, String name, String lastname) throws CommonException;

    void isPhoneValid(String phone) throws CommonException;

    void isEmailValid(String email) throws CommonException;

    void isDateOfEmploymentValid(Date dateOfEmployment, Date dateOfBirth) throws CommonException;

    void isDateOfUnemploymentValid(Date dateOfEmployment, Date dateOfUnemployment) throws CommonException;

    void isSalaryValid(Integer salary, Integer departmentid) throws CommonException;

    void employerValidity(Employer employer) throws CommonException;
}
