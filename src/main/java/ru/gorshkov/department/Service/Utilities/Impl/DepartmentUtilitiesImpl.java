package ru.gorshkov.department.Service.Utilities.Impl;

import org.apache.commons.validator.routines.EmailValidator;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.gorshkov.department.Models.Impl.DepartmentImpl;
import ru.gorshkov.department.Models.Intf.Department;
import ru.gorshkov.department.Models.Intf.Employer;
import ru.gorshkov.department.Service.Mappers.DepartmentMapper;
import ru.gorshkov.department.Service.Mappers.EmployerMapper;
import ru.gorshkov.department.Service.Utilities.Exception.CommonException;
import ru.gorshkov.department.Service.Utilities.Intf.DepartmentUtilities;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service("departmentService")
public class DepartmentUtilitiesImpl implements DepartmentUtilities {

    private static final Logger LOGGER = org.apache.logging.log4j.LogManager.getLogger(DepartmentUtilitiesImpl.class);
    private final DepartmentMapper departmentMapper;
    private final EmployerMapper employerMapper;

    private Pattern p;
    private Matcher m;

    @Autowired
    public DepartmentUtilitiesImpl(DepartmentMapper departmentMapper, EmployerMapper employerMapper) {
        this.departmentMapper = departmentMapper;
        this.employerMapper = employerMapper;
    }

    @Override
    public void isNameUnique(String name) throws CommonException {
        LOGGER.info("Checking is name unique...");
        Department department = departmentMapper.getDepartmentByName(name);
        if (department != null) {
            LOGGER.error("Name is not unique");
            throw new CommonException("Name is not unique");
        } else LOGGER.info("Name is unique");
    }

    @Override
    public boolean haveChild(Integer id) {
        return departmentMapper.getDaughterDepartments(id) != null;
    }

    public DepartmentImpl buildChildTree(DepartmentImpl root) {
        LOGGER.info("Building child tree");
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
        LOGGER.info("Building parent tree...");
        DepartmentImpl parent;
        if (departmentMapper.getDepartmentById(child.getDepartmentid()) != null) {
            parent = departmentMapper.getDepartmentById(child.getDepartmentid());
            parent.addChild(child);
            return buildParentTree(parent);
        } else return child;
    }

    @Override
    public void isAvailable(Employer employer) throws CommonException {
        LOGGER.info("Checking is this employer available...");
        if (employer.getFirstname() == null || employer.getName() == null || employer.getGender() == null
                || employer.getDateofbirth() == null || employer.getPhone() == null || employer.getEmail() == null
                || employer.getDateofemployment() == null || employer.getPost() == null || employer.getSalary() == null
                || employer.getHead() == null)
            throw new CommonException("Some properties need to be added");
    }

    @Override
    public void twoHeadException(Integer departmentid, Boolean head) throws CommonException {
        if (employerMapper.getHeadByDepartment(departmentid) != null && head)
            throw new CommonException("Head already exists");
    }

    @Override
    public void isNameValid(String name) throws CommonException {
        LOGGER.info("Checking name = {} validity...", name);
        p = Pattern.compile("[А-Яа-я-]+");
        m = p.matcher(name);
        if (!m.matches()) throw new CommonException("Parameter " + name + " is invalid");
    }

    @Override
    public void isFioValid(String firstname, String name, String lastname) throws CommonException {
        try {
            isNameValid(firstname);
            isNameValid(name);
            isNameValid(lastname);
        } catch (CommonException exc) {
            throw new CommonException(exc.getMessage());
        }
    }

    @Override
    public void isPhoneValid(String phone) throws CommonException {

        LOGGER.info("Checking phone = {} validity...", phone);
        p = Pattern.compile("[0-9+ ()-]+");
        m = p.matcher(phone);
        if (!m.matches())
            throw new CommonException("Phone is invalid");
    }

    @Override
    public void isEmailValid(String email) throws CommonException {

        LOGGER.info("Checking email: {} validity...", email);
        if (!EmailValidator.getInstance().isValid(email))
            throw new CommonException("Email is invalid");
    }

    @Override
    public void isDateOfEmploymentValid(Date dateOfEmployment, Date dateOfBirth) throws CommonException {
        LOGGER.info("Checking date of employment {} validity...", dateOfEmployment);
        if (dateOfEmployment.before(dateOfBirth))
            throw new CommonException("Date of employment is invalid");
    }

    @Override
    public void isDateOfUnemploymentValid(Date dateOfEmployment, Date dateOfUnemployment) throws CommonException {
        LOGGER.info("Checking date of unemployment {} validity...", dateOfUnemployment);
        if (dateOfUnemployment.before(dateOfEmployment))
            throw new CommonException("Date of unemployment is invalid");
    }

    @Override
    public void isSalaryValid(Integer salary, Integer departmentid) throws CommonException {
        LOGGER.info("Checking salary validity...");
        Employer head;

        head = employerMapper.getHeadByDepartment(departmentid);
        if (head == null) {
            LOGGER.warn("No head found");
        } else if (head.getSalary() < salary)
            throw new CommonException("Salary of employer cannot be more then salary of department`s head");
    }

    @Override
    public void employerValidity(Employer employer) throws CommonException {
        try {
            isAvailable(employer);
            twoHeadException(employer.getDepartmentid(), employer.getHead());
            isFioValid(employer.getFirstname(), employer.getName(), employer.getLastname());
            isPhoneValid(employer.getPhone());
            isEmailValid(employer.getEmail());
            isDateOfEmploymentValid(employer.getDateofemployment(), employer.getDateofbirth());
            isSalaryValid(employer.getSalary(), employer.getDepartmentid());
        } catch (CommonException e) {
            throw new CommonException(e.getMessage());
        }
    }
}

