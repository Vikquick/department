package ru.gorshkov.department.Service.Mappers;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import ru.gorshkov.department.Models.Impl.EmployerImpl;

@Mapper
public interface EmployerMapper {

    @Select("select * FROM employer WHERE id = #{id}")
    EmployerImpl getEmployerById(Integer id) throws NullPointerException;

    @Select("select * FROM employer WHERE departmentid = #{id} and AND head=TRUE")
    EmployerImpl getHead(Integer id) throws NullPointerException;

    @Select("select count(id) FROM employer WHERE departmentid = #{id}")
    Integer getEmployersCount(Integer id) throws NullPointerException;
}
