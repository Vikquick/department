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
import ru.gorshkov.department.Service.Mappers.DepartmentMapper;
import ru.gorshkov.department.Service.Mappers.EmployerMapper;
import ru.gorshkov.department.Service.Utilities.Intf.DepartmentUtilities;

@RestController
@RequestMapping("/department")
public class DepartmentController {

    private static final Logger LOGGER = LogManager.getLogger(DepartmentController.class);

    @Autowired
    DepartmentMapper departmentMapper;

    @Autowired
    EmployerMapper employerMapper;

    @Autowired
    DepartmentUtilities departmentUtilities;

    public DepartmentController(DepartmentMapper departmentMapper) {
        this.departmentMapper = departmentMapper;
    }

    @GetMapping("/get/{id}")
    public DepartmentImpl getDepartmentById(@PathVariable("id") Integer id) {
        return departmentMapper.getDepartmentById(id);
    }

    @PostMapping(path = "/add")
    public ResponseEntity<DepartmentImpl> createDepartment(@ModelAttribute DepartmentImpl department) {
        if (!departmentUtilities.isNameUnique(department.getName())) {
            try {
                if (department.getDepartmentid() != null) {
                    LOGGER.info("Executed" + department.getName() + department.getCreationdate() + department.getDepartmentid());
                    departmentMapper.createDepartment(department.getName(), department.getCreationdate(), department.getDepartmentid());
                    return new ResponseEntity<>(department, HttpStatus.OK);
                } else {
                    LOGGER.info("Executed" + department.getName() + department.getCreationdate());
                    departmentMapper.createMainDepartment(department.getName(), department.getCreationdate());
                    return new ResponseEntity<>(department, HttpStatus.OK);
                }
            } catch (Exception exc) {
                LOGGER.error(exc);
                return new ResponseEntity<>(department, HttpStatus.BAD_REQUEST);
            }
        } else {
            LOGGER.error("Department with name " + department.getName() + " already exists");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(path = "/update/{id}")
    public ResponseEntity<DepartmentImpl> updateDepartment(@PathVariable("id") Integer id, @RequestParam("name") String name) {

        if (!departmentUtilities.isNameUnique(name)) {
            try {
                departmentMapper.updateDepartment(name, id);
                LOGGER.info("Department name is updated to " + name);
                return new ResponseEntity<>(departmentMapper.getDepartmentById(id), HttpStatus.OK);
            } catch (Exception exc) {
                LOGGER.error(exc);
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else {
            LOGGER.error("Department with name " + name + " already exists");
            return new ResponseEntity<>(departmentMapper.getDepartmentById(id), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(path = "/remove")
    public ResponseEntity remove(@RequestParam("id") Integer id) {
        try {
            departmentMapper.remove(id);
        } catch (NullPointerException exc) {
            LOGGER.error("No departments found with this id");
            return new ResponseEntity<>("No departments found with this id", HttpStatus.NO_CONTENT);
        } catch (DataIntegrityViolationException exc) {
            LOGGER.error("This department has employers within");
            return new ResponseEntity<>("This department has employers within", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Department was sucsessfull removed", HttpStatus.OK);
    }

    @GetMapping(path = "/info")
    public ResponseEntity getInfo(@RequestParam("id") Integer id) {
        try {
            DepartmentImpl department = departmentMapper.getDepartmentById(id);
            EmployerImpl employer = employerMapper.getEmployerById(id);
            if (employer.getLasname() == null)
                return new ResponseEntity<>(new DepartmentInfo(department.getName(), department.getCreationdate(),
                        employer.getFirstname() + " " + employer.getName(), employerMapper.getEmployersCount(id)), HttpStatus.OK);
            else return new ResponseEntity<>(new DepartmentInfo(department.getName(), department.getCreationdate(),
                    employer.getFirstname() + " " + employer.getName() + " " + employer.getLasname(), employerMapper.getEmployersCount(id)), HttpStatus.OK);

        } catch (NullPointerException exc) {
            LOGGER.error("No departments found with this id");
            return new ResponseEntity<>("No departments found with this id", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/getChildren")
    public ResponseEntity getChildren(@RequestParam("id") Integer id) {
        try {
            return new ResponseEntity<>(departmentMapper.getDaughterDepartments(id), HttpStatus.OK);
        } catch (NullPointerException exc) {
            LOGGER.error("No departments found with this id");
            return new ResponseEntity<>("No departments found with this id", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/getAllChildren")
    public ResponseEntity getAllChildren(@RequestParam("id") Integer id) {
        try {
            DepartmentImpl root = departmentMapper.getDepartmentById(id);
            return new ResponseEntity<>(departmentUtilities.buildChildTree(root).getChild(), HttpStatus.OK);
        } catch (NullPointerException exc) {
            LOGGER.error("No departments found with this id");
            return new ResponseEntity<>("No departments found with this id", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(path = "/transport/{id}")
    public ResponseEntity transportDepartment(@PathVariable("id") Integer id, @RequestParam("departmentid") Integer departmentid) {
        try {
            departmentMapper.transportDepartment(id, departmentid);
            LOGGER.info("Department was transport to another root " + departmentMapper.getDepartmentById(departmentid).getName());
            return new ResponseEntity<>(departmentMapper.getDepartmentById(id), HttpStatus.OK);
        } catch (NullPointerException exc) {
            LOGGER.error("No departments found with this id");
            return new ResponseEntity<>("No departments found with this id", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/getAllParents")
    public ResponseEntity getAllparents(@RequestParam("id") Integer id) {
        try {
            DepartmentImpl tree = departmentUtilities.buildParentTree(departmentMapper.getParentDepartment(id));
            return new ResponseEntity<>(tree, HttpStatus.OK);
        } catch (NullPointerException exc) {
            LOGGER.error("No departments found with this id");
            return new ResponseEntity<>("No departments found with this id", HttpStatus.BAD_REQUEST);
        }
    }
}
