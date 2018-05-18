package ru.gorshkov.department.Service.Utilities.Impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.gorshkov.department.Models.Impl.DepartmentImpl;
import ru.gorshkov.department.Service.Mappers.DepartmentMapper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
public class SheduledCounter {
    private static final Logger LOGGER = LogManager.getLogger(SheduledCounter.class);

    private final DepartmentMapper departmentMapper;
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    @Autowired
    public SheduledCounter(DepartmentMapper departmentMapper) {
        this.departmentMapper = departmentMapper;
    }

    @Scheduled(fixedRate = 300000)
    public void reportCurrentTime() {

        List<DepartmentImpl> departmentList = departmentMapper.getAllDepartments();
        for (DepartmentImpl d : departmentList) {
            departmentMapper.countFonds(d.getId(), departmentMapper.countSalary(d.getId()), new Date());
            LOGGER.info(d.getId() + " " + departmentMapper.countSalary(d.getId()) + " " + dateFormat.format(new Date()));
        }
    }
}
