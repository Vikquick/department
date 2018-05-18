package ru.gorshkov.department.Service.Controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gorshkov.department.Models.Impl.DepartmentImpl;
import ru.gorshkov.department.Models.Impl.DepartmentInfo;
import ru.gorshkov.department.Models.Impl.EmployerImpl;
import ru.gorshkov.department.Models.Intf.Department;
import ru.gorshkov.department.Service.Mappers.DepartmentMapper;
import ru.gorshkov.department.Service.Mappers.EmployerMapper;
import ru.gorshkov.department.Service.Utilities.Exception.CommonException;
import ru.gorshkov.department.Service.Utilities.Intf.DepartmentUtilities;

import java.util.Date;

@RestController
@RequestMapping("/department")
public class DepartmentController {

    private static final Logger LOGGER = LogManager.getLogger(DepartmentController.class);


    private final DepartmentMapper departmentMapper;
    private final EmployerMapper employerMapper;
    private final DepartmentUtilities departmentUtilities;

    @Autowired
    public DepartmentController(DepartmentMapper departmentMapper, EmployerMapper employerMapper, DepartmentUtilities departmentUtilities) {
        this.departmentMapper = departmentMapper;
        this.employerMapper = employerMapper;
        this.departmentUtilities = departmentUtilities;
    }

    @GetMapping("/getById")
    public ResponseEntity getDepartmentById(@RequestParam("id") Integer id) {
        LOGGER.info("Getting department by it`s id...");
        DepartmentImpl department = departmentMapper.getDepartmentById(id);
        if (department != null) {
            LOGGER.info(department.toString());
            return new ResponseEntity<>(department, HttpStatus.OK);
        } else {
            LOGGER.info("Department with this id doesn`t exist");
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
    }


    @GetMapping("/getByName")
    public ResponseEntity getDepartmentByName(@RequestParam("name") String name) {
        LOGGER.info("Getting department by it`s name");
        DepartmentImpl department = departmentMapper.getDepartmentByName(name);
        if (department != null) {
            LOGGER.info(department.toString());
            return new ResponseEntity<>(department, HttpStatus.OK);
        } else {
            LOGGER.info("Department with this name doesn`t exist");
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping(path = "/add")
    public ResponseEntity createDepartment(@ModelAttribute DepartmentImpl department) {

        try {
            departmentUtilities.isNameUnique(department.getName());
            if (department.getDepartmentid() != null) {
                departmentMapper.createDepartment(department);
                writeDown(departmentMapper.getDepartmentByName(department.getName()).getId(), "added");
                LOGGER.info("Department added");
                return new ResponseEntity<>(department, HttpStatus.OK);
            } else {
                LOGGER.info("Executed" + department.getName() + department.getCreationdate());
                departmentMapper.createMainDepartment(department);
                writeDown(departmentMapper.getDepartmentByName(department.getName()).getId(), "added");
                LOGGER.info("Department added");
                return new ResponseEntity<>(department, HttpStatus.OK);
            }
        } catch (CommonException exc) {
            LOGGER.error(exc);
            return new ResponseEntity<>(exc.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping(path = "/update")
    public ResponseEntity updateDepartment(@RequestParam("id") Integer id, @RequestParam("name") String name) {

        try {
            departmentUtilities.isNameUnique(name);
            LOGGER.info("Name - {} is unique", name);
            writeDown(id, "updated from " + departmentMapper.getDepartmentById(id).getName() + " to " + name);
            departmentMapper.updateDepartment(name, id);
            LOGGER.info("Department name is updated to " + name);
            return new ResponseEntity<>(departmentMapper.getDepartmentById(id), HttpStatus.OK);
        } catch (CommonException exc) {
            return new ResponseEntity<>(exc.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(path = "/remove")
    public ResponseEntity remove(@RequestParam("id") Integer id) {
        LOGGER.info("Removing department {}", id);
        try {
            departmentMapper.removeSalary(id);
            departmentMapper.remove(id);
            writeDown(id, "removed");
        } catch (NullPointerException exc) {
            LOGGER.error("No departments found with this id = {}", id);
            return new ResponseEntity<>("No departments found with this id", HttpStatus.NO_CONTENT);
        } catch (DataIntegrityViolationException exc) {
            LOGGER.error("This department has employers within");
            return new ResponseEntity<>("This department has employers within", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Department was sucsessfully removed", HttpStatus.OK);
    }

    @GetMapping(path = "/info")
    public ResponseEntity getInfo(@RequestParam("id") Integer id) {
        LOGGER.info("Getting info about departent with id = {}", id);
        try {
            DepartmentInfo departmentInfo;
            DepartmentImpl department = departmentMapper.getDepartmentById(id);
            EmployerImpl employer = employerMapper.getHeadByDepartment(id);
            if (employer.getLastname() == null) {
                departmentInfo = new DepartmentInfo(department.getName(), department.getCreationdate(),
                        employer.getFirstname() + " " + employer.getName(), employerMapper.getEmployersCount(id));
                LOGGER.info(departmentInfo.toString());
                return new ResponseEntity<>(departmentInfo, HttpStatus.OK);
            } else {
                departmentInfo = new DepartmentInfo(department.getName(), department.getCreationdate(),
                        employer.getFirstname() + " " + employer.getName() + " " + employer.getLastname(), employerMapper.getEmployersCount(id));
                LOGGER.info(departmentInfo.toString());
                return new ResponseEntity<>(departmentInfo, HttpStatus.OK);
            }
        } catch (NullPointerException exc) {
            LOGGER.error("No departments found with id = {}", id);
            return new ResponseEntity<>("No departments found with this id", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/getChildren")
    public ResponseEntity getChildren(@RequestParam("id") Integer id) {
        LOGGER.info("Getting children of department with id = {}...", id);
        try {
            return new ResponseEntity<>(departmentMapper.getDaughterDepartments(id), HttpStatus.OK);
        } catch (NullPointerException exc) {
            LOGGER.error("No departments found with id = {}", id);
            return new ResponseEntity<>("No departments found with this id", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/getAllChildren")
    public ResponseEntity getAllChildren(@RequestParam("id") Integer id) {
        LOGGER.info("Getting all children of department...");
        try {
            DepartmentImpl root = departmentMapper.getDepartmentById(id);
            return new ResponseEntity<>(departmentUtilities.buildChildTree(root).getChild(), HttpStatus.OK);
        } catch (NullPointerException exc) {
            LOGGER.error("No departments found with this id");
            return new ResponseEntity<>("No departments found with this id", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(path = "/transport")
    public ResponseEntity transportDepartment(@RequestParam("id") Integer id, @RequestParam("departmentid") Integer departmentid) {
        LOGGER.info("Transporting department with id {} to root with id = {}", id, departmentid);
        try {
            Integer oldDepartmentid = departmentMapper.getDepartmentById(id).getDepartmentid();
            departmentMapper.transportDepartment(id, departmentid);
            writeDown(id, "transported from " + oldDepartmentid + " to " + departmentid);
            LOGGER.info("Department was transport to another root " + departmentMapper.getDepartmentById(departmentid).getName());
            return new ResponseEntity<>(departmentMapper.getDepartmentById(id), HttpStatus.OK);
        } catch (NullPointerException exc) {
            LOGGER.error("No departments found with this id");
            return new ResponseEntity<>("No departments found with this id", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/getAllParents")
    public ResponseEntity getAllparents(@RequestParam("id") Integer id) {
        LOGGER.info("Getting parents of department with id = {}...", id);
        try {
            Department tree = departmentUtilities.buildParentTree(departmentMapper.getParentDepartment(id));
            LOGGER.info("All parents are found");
            return new ResponseEntity<>(tree, HttpStatus.OK);
        } catch (NullPointerException exc) {
            LOGGER.error("No departments found with id={}", id);
            return new ResponseEntity<>("No departments found with this id", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/countSalary")
    public ResponseEntity countSalary(@RequestParam("id") Integer id) {
        LOGGER.info("Counting salary for department with id {}", id);
        Integer salary = salary = departmentMapper.countSalary(id);
        if (salary != null) {
            LOGGER.info("Salary of department with id = {} is - {}", id, salary);
            return new ResponseEntity<>(salary, HttpStatus.OK);
        } else {
            LOGGER.info("No department exist with id = {}", id);
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    private void writeDown(Integer departmentid, String action) {
        departmentMapper.writeDown(departmentid, action, new Date());
        LOGGER.info("Note about department changes has been written: id = {}, action = {}", departmentid, action);
    }
}
