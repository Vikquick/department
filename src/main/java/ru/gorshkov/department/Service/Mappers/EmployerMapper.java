package ru.gorshkov.department.Service.Mappers;

import org.apache.ibatis.annotations.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;
import ru.gorshkov.department.Models.Impl.EmployerImpl;

import java.util.Date;
import java.util.List;

@Mapper
@Transactional
public interface EmployerMapper {

    @Select("select * FROM employer WHERE id = #{id}")
    EmployerImpl getEmployerById(@Param("id") Integer id) throws NullPointerException;

    @Select("select * FROM employer WHERE departmentid = #{id} AND head=TRUE")
    EmployerImpl getHeadByDepartment(Integer id) throws NullPointerException;

    @Select("select count(id) FROM employer WHERE departmentid = #{id}")
    Integer getEmployersCount(Integer id) throws NullPointerException;

    @Select("select * from employer where departmentid = #{departmentid}")
    List<EmployerImpl> getListOfEmployers(@Param("departmentid") Integer departmentid) throws NullPointerException;

    @Insert("insert into employer (firstname, name, lastname, gender, dateofbirth, phone, email, dateofemployment, dateofunemployment, post, salary, head, departmentid)" +
            " VALUES (#{firstname}, #{name}, #{lastname}, #{gender}, #{dateofbirth}, #{phone}, #{email}, #{dateofemployment}, #{dateofunemployment}, #{post}, #{salary}, #{head}, #{departmentid})")
    void addEmployer(EmployerImpl employer) throws DataIntegrityViolationException;

    @Update("UPDATE employer SET (firstname, name, lastname, gender, dateofbirth, phone, email, dateofemployment, dateofunemployment, post, salary, head, departmentid)" +
            " = (#{firstname}, #{name}, #{lastname}, #{gender}, #{dateofbirth}, #{phone}, #{email}, #{dateofemployment}, #{dateofunemployment}, #{post}, #{salary}, #{head}, #{departmentid}) WHERE id = #{id}")
    void updateEmployer(EmployerImpl employer) throws DataIntegrityViolationException;


    @Update("UPDATE employer SET departmentid = #{departmentid} WHERE id = #{id}")
    void transportToAnotherDepartment(@Param("id") Integer id, @Param("departmentid") Integer departmentid);

    @Update("UPDATE employer SET departmentid = #{departmentid} WHERE departmentid = #{olddepartmentid}")
    void transportAllToAnotherDepartment(@Param("departmentid") Integer departmentid, @Param("olddepartmentid") Integer olddepartmentid);

    @Update("UPDATE employer SET head = false WHERE id = #{id}")
    void throwHead(@Param("id") Integer id);

    @Update("UPDATE employer SET head = false WHERE departmentid = #{departmentid}")
    void throwHeads(@Param("departmentid") Integer departmentid);

    @Select("SELECT * FROM employer WHERE departmentid = (SELECT departmentid FROM employer WHERE id = #{id})  AND head = TRUE;")
    EmployerImpl getHeadByEmployer(@Param("id") Integer id);

    @Update("update employer set dateofunemployment = #{dateofunemployment}, fired=true where id = #{id}")
    void setFired(@Param("id") Integer id, @Param("dateofunemployment") Date dateofunemployment);

    @Select("SELECT * FROM employer WHERE (firstname, name, lastname) = (#{firstname}, #{name}, #{lastname})")
    EmployerImpl getEmployerByFio(@Param("firstname") String firstname, @Param("name") String name, @Param("lastname") String lastname);

    @Select("SELECT * FROM employer WHERE (firstname, name) = (#{firstname}, #{name})")
    EmployerImpl getEmployerByFi(@Param("firstname") String firstname, @Param("name") String name);
}
