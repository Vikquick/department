package ru.gorshkov.department.Application;

import org.apache.ibatis.type.MappedTypes;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import ru.gorshkov.department.Models.Impl.DepartmentImpl;
import ru.gorshkov.department.Models.Impl.EmployerImpl;


@SpringBootApplication
@ComponentScan(basePackages = "ru.gorshkov.department")
@MapperScan("ru.gorshkov.department.Service.Mappers")
@MappedTypes({DepartmentImpl.class, EmployerImpl.class})
public class DepartmentApplication {

    private static final Logger LOGGER = LogManager.getLogger(DepartmentApplication.class);


    public static void main(String[] args) {
        SpringApplication.run(DepartmentApplication.class, args);
    }
}
