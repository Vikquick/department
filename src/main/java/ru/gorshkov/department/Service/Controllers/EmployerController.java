package ru.gorshkov.department.Service.Controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gorshkov.department.Models.Impl.EmployerImpl;
import ru.gorshkov.department.Service.Mappers.EmployerMapper;

@RestController
@RequestMapping("/employer")
public class EmployerController {
    private static final Logger LOGGER = LogManager.getLogger(EmployerController.class);

    EmployerMapper employerMapper;

    public EmployerController(EmployerMapper employerMapper) {
        this.employerMapper = employerMapper;
    }

    @GetMapping("/get/{id}")
    EmployerImpl getEmployerById(@PathVariable("id") Integer id) {

        return employerMapper.getEmployerById(id);
    }


}
