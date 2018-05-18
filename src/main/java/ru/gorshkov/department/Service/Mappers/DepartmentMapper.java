package ru.gorshkov.department.Service.Mappers;

import org.apache.ibatis.annotations.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;
import ru.gorshkov.department.Models.Impl.DepartmentImpl;

import java.util.Date;
import java.util.List;

@Mapper
@Transactional
public interface DepartmentMapper {

    @Select("select * FROM department WHERE id = #{id}")
    DepartmentImpl getDepartmentById(Integer id) throws NullPointerException;

    @Select("select * FROM department WHERE name = #{name}")
    DepartmentImpl getDepartmentByName(String name) throws NullPointerException;

    @Update("UPDATE department SET name = #{name} WHERE id = #{id}")
    void updateDepartment(@Param("name") String name, @Param("id") Integer id);

    @Insert("INSERT INTO department (name, creationdate) VALUES (#{name}, #{creationdate})")
    void createMainDepartment(DepartmentImpl department);

    @Insert("INSERT INTO department (name, creationdate, departmentid) VALUES (#{name}, #{creationdate}, #{departmentid})")
    void createDepartment(DepartmentImpl department);

    @Delete("DELETE FROM department WHERE id =#{id}")
    void remove(Integer id) throws DataIntegrityViolationException, NullPointerException;

    @Select("select * FROM department WHERE departmentid =#{id}")
    List<DepartmentImpl> getDaughterDepartments(Integer id);

    @Select("SELECT * FROM department where id = (SELECT departmentid FROM department WHERE id =#{id})")
    DepartmentImpl getParentDepartment(Integer id);

    @Update("UPDATE department SET departmentid = #{departmentid} WHERE id = #{id}")
    void transportDepartment(@Param("id") Integer id, @Param("departmentid") Integer departmentid) throws NullPointerException;

    @Select("SELECT sum(salary) FROM employer where departmentid = #{id}")
    Integer countSalary(@Param("id") Integer id) throws NullPointerException;

    @Insert("INSERT into departmenthistory (departmentid, action, date) values (#{departmentid},#{action},#{date})")
    void writeDown(@Param("departmentid") Integer departmentid,
                   @Param("action") String action,
                   @Param("date") Date date);

    @Select("select * from department")
    List<DepartmentImpl> getAllDepartments();

    @Insert("insert into salary (departmentid, salarysum, date) values (#{departmentid}, #{salarysum}, #{date})")
    void countFonds(@Param("departmentid") Integer departmentid, @Param("salarysum") Integer salarysum, @Param("date") Date date);

    @Delete("remove * from salary where departmentid=#{departmentid}")
    void removeSalary(@Param("departmentid") Integer Departmentid);
}
