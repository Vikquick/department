package ru.gorshkov.department.Service.Controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import ru.gorshkov.department.Models.Impl.EmployerImpl;
import ru.gorshkov.department.Models.Intf.Employer;
import ru.gorshkov.department.Service.Mappers.EmployerMapper;
import ru.gorshkov.department.Service.Utilities.Exception.CommonException;
import ru.gorshkov.department.Service.Utilities.Intf.DepartmentUtilities;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/employer")
public class EmployerController {
    private static final Logger LOGGER = LogManager.getLogger(EmployerController.class);

    private final EmployerMapper employerMapper;
    private final DepartmentUtilities departmentUtilities;

    @Autowired
    public EmployerController(EmployerMapper employerMapper, DepartmentUtilities departmentUtilities) {
        this.employerMapper = employerMapper;
        this.departmentUtilities = departmentUtilities;
    }


    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class,
                new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true, 10));
    }

    @GetMapping("/get")
    public ResponseEntity getEmployerById(@RequestParam("id") Integer id) {
        LOGGER.info("Getting info about employer with id = {}", id);
        EmployerImpl employer = employerMapper.getEmployerById(id);
        if (employer != null) {
            LOGGER.info(employer.toString());
            return new ResponseEntity<>(employer, HttpStatus.OK);
        } else {
            LOGGER.info("Employer with id = {} doesn`t exist", id);
            return new ResponseEntity<>("Employer with this id not exists", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getList")
    public ResponseEntity getListOfEmployers(@RequestParam("departmentid") Integer departmentid) {
        LOGGER.info("Getting all employers in department with id = {}", departmentid);
        try {
            return new ResponseEntity<>(employerMapper.getListOfEmployers(departmentid), HttpStatus.OK);
        } catch (NullPointerException exc) {
            LOGGER.info("Department with id = {} doesn`t exist", departmentid);
            return new ResponseEntity<>("Department with this id doesn`t exist", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/add")
    public ResponseEntity addEmployer(@ModelAttribute EmployerImpl employer) {
        LOGGER.info("Adding employer: {} {} {}", employer.getFirstname(), employer.getName(), employer.getLastname());
        try {
            departmentUtilities.employerValidity(employer);

            employerMapper.addEmployer(employer);
            LOGGER.info("Employer added");
            return new ResponseEntity<>(findEmployerByFio(employer.getFirstname(), employer.getName(), employer.getLastname()), HttpStatus.OK);
        } catch (CommonException exc) {
            LOGGER.info("Employer doesn`t added");
            LOGGER.error(exc.getMessage());
            return new ResponseEntity<>(exc.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("/update")
    public ResponseEntity updateEmployer(@ModelAttribute EmployerImpl employer) {

        LOGGER.info("Updating employer with id = {}", employer.getId());
        try {
            departmentUtilities.employerValidity(employer);
            EmployerImpl oldEmployer = employerMapper.getEmployerById(employer.getId());
            if (oldEmployer != null) {
                employerMapper.updateEmployer(employer);
                LOGGER.info(employer.toString());
                return new ResponseEntity<>(employerMapper.getEmployerById(employer.getId()), HttpStatus.OK);
            }
        } catch (CommonException exc) {
            LOGGER.error(exc.getMessage());
            return new ResponseEntity<>(exc.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Employer with this id doesn`t exist", HttpStatus.BAD_REQUEST);
    }

    @PostMapping(path = "/transport")
    public ResponseEntity transportToAnotherDepartment(@RequestParam("id") Integer id, @RequestParam("departmentid") Integer departmentid) {
        LOGGER.info("Transporting employer with id = {} to department with id = {}", id, departmentid);
        try {
            employerMapper.throwHead(id);
            employerMapper.transportToAnotherDepartment(id, departmentid);
            LOGGER.info("Employer was transport to another department: " + employerMapper.getEmployerById(departmentid).getName());
            return new ResponseEntity<>(employerMapper.getEmployerById(id), HttpStatus.OK);
        } catch (NullPointerException exc) {
            LOGGER.error("No departments or employers found with this id");
            return new ResponseEntity<>("No departments or employers found with this id", HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping(path = "/transportAll")
    public ResponseEntity transportAlltoAnotherDepartment(@RequestParam("departmentid") Integer departmentid, @RequestParam("olddepartmentid") Integer olddepartmentid) {
        LOGGER.info("Transporting all employers from department with id = {} to {}", departmentid, olddepartmentid);
        try {
            employerMapper.throwHeads(olddepartmentid);
            employerMapper.transportAllToAnotherDepartment(departmentid, olddepartmentid);
            LOGGER.info("All employers was transport to another department " + employerMapper.getListOfEmployers(departmentid));
            return new ResponseEntity<>(employerMapper.getListOfEmployers(departmentid), HttpStatus.OK);
        } catch (NullPointerException exc) {
            LOGGER.error("No departments found with this id");
            return new ResponseEntity<>("No departments found with this id", HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping(path = "/getEmployersHead")
    public ResponseEntity getHeadByEmployer(@RequestParam("id") Integer id) {
        LOGGER.info("Getting head of employer with id = {}", id);
        EmployerImpl head = employerMapper.getHeadByEmployer(id);
        LOGGER.info(head.toString());
        return new ResponseEntity<>(head, HttpStatus.OK);
    }

    @PostMapping(path = "/unemploy")
    public ResponseEntity setFired(@RequestParam("id") Integer id) {
        LOGGER.info("Firing employer with id = {}", id);
        Employer employer;
        try {
            employer = employerMapper.getEmployerById(id);
            if (employer != null) {
                departmentUtilities.isDateOfUnemploymentValid(employer.getDateofunemployment(), new Date());
                employerMapper.setFired(id, new Date());
                LOGGER.info("Employer fired");
                return new ResponseEntity<>(employerMapper.getEmployerById(id), HttpStatus.OK);
            } else return new ResponseEntity<>("No such employer", HttpStatus.BAD_REQUEST);
        } catch (CommonException exc) {
            return new ResponseEntity<>(exc.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/findByFio")
    public ResponseEntity findEmployerByFio(@RequestParam("firstname") String firstname,
                                            @RequestParam("name") String name,
                                            @RequestParam("lastname") String lastname) {
        LOGGER.info("Looking for employer with fio: {} {} {}", firstname, name, lastname);
        EmployerImpl employer = employerMapper.getEmployerByFio(firstname, name, lastname);
        if (employer != null) {
            LOGGER.info(employer.toString());
            return new ResponseEntity<>(employer, HttpStatus.OK);
        } else {
            LOGGER.error("No such employer");
            return new ResponseEntity<>("No such employer", HttpStatus.BAD_REQUEST);
        }
    }
}
