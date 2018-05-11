package ru.gorshkov.department.Service.Mappers;

import org.apache.ibatis.annotations.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;
import ru.gorshkov.department.Models.Impl.DepartmentImpl;

import java.time.LocalDate;
import java.util.List;

@Mapper
@Transactional
public interface DepartmentMapper {

    @Select("select * FROM department WHERE id = #{id}")
    DepartmentImpl getDepartmentById(Integer id) throws NullPointerException;

    @Select("select * FROM department WHERE name = #{name}")
    DepartmentImpl getDepartmentByName(String name) throws NullPointerException;

    @Update("UPDATE department SET name = #{name} WHERE id = #{id}")
    DepartmentImpl updateDepartment(@Param("name") String name, @Param("id") Integer id);

    @Insert("INSERT INTO department (name, creationdate) VALUES (#{name}, #{creationdate})")
    void createMainDepartment(@Param("name") String name, @Param("creationdate") LocalDate creationdate);

    @Insert("INSERT INTO department (name, creationdate, departmentid) VALUES (#{name}, #{creationdate}, #{departmentid})")
    void createDepartment(@Param("name") String name, @Param("creationdate") LocalDate creationdate, @Param("departmentid") Integer departmentid);

    @Delete("DELETE FROM department WHERE id =#{id}")
    void remove(Integer id) throws DataIntegrityViolationException, NullPointerException;

    @Select("select * FROM department WHERE departmentid =#{id}")
    List<DepartmentImpl> getDaughterDepartments(Integer id);

    @Select("SELECT * FROM department where id = (SELECT departmentid FROM department WHERE id =#{id})")
    DepartmentImpl getParentDepartment(Integer id);

    @Update("UPDATE department SET departmentid = #{departmentid} WHERE id = #{id}")
    void transportDepartment(@Param("id") Integer id, @Param("departmentid") Integer departmentid) throws NullPointerException;
}
