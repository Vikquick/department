package ru.gorshkov.department.Models.Intf;

import ru.gorshkov.department.Models.Enum.Gender;
import ru.gorshkov.department.Models.Enum.Post;
import ru.gorshkov.department.Models.Intf.Basic.IdAble;
import ru.gorshkov.department.Models.Intf.Basic.Nameable;

import java.util.Date;

public interface Employer extends Nameable, IdAble {

    String getFirstname();

    void setFirstname(String firstname);

    String getLastname();

    void setLastname(String lastname);

    Gender getGender();

    void setGender(Gender gender);

    Date getDateofbirth();

    void setDateofbirth(Date dateofbirth);

    String getPhone();

    void setPhone(String phone);

    String getEmail();

    void setEmail(String email);

    Date getDateofemployment();

    void setDateofemployment(Date dateofemployment);

    Date getDateofunemployment();

    void setDateofunemployment(Date dateofunemployment);

    Post getPost();

    void setPost(Post post);

    Integer getSalary();

    void setSalary(Integer salary);

    Boolean getHead();

    void setHead(Boolean head);

    Integer getDepartmentid();

    void setDepartmentid(Integer departmentid);

    Boolean getFired();

    void setFired(Boolean fired);
}
