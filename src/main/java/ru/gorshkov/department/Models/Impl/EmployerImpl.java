package ru.gorshkov.department.Models.Impl;

import org.springframework.format.annotation.DateTimeFormat;
import ru.gorshkov.department.Models.Enum.Gender;
import ru.gorshkov.department.Models.Enum.Post;
import ru.gorshkov.department.Models.Intf.Employer;

import java.util.Date;


public class EmployerImpl implements Employer {

    private Integer id;
    private String firstname;
    private String name;
    private String lastname;
    private Gender gender;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateofbirth;
    private String phone;
    private String email;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateofemployment;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateofunemployment;
    private Post post;
    private Integer salary;
    private Boolean head;
    private Integer departmentid;
    private Boolean fired;

    public EmployerImpl() {
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Integer getId() {
        return this.id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String getFirstname() {
        return firstname;
    }

    @Override
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    @Override
    public String getLastname() {
        return lastname;
    }

    @Override
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Override
    public Gender getGender() {
        return gender;
    }

    @Override
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    @Override
    public Date getDateofbirth() {
        return dateofbirth;
    }

    @Override
    public void setDateofbirth(Date dateofbirth) {
        this.dateofbirth = dateofbirth;
    }

    @Override
    public String getPhone() {
        return phone;
    }

    @Override
    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public Date getDateofemployment() {
        return dateofemployment;
    }

    @Override
    public void setDateofemployment(Date dateofemployment) {
        this.dateofemployment = dateofemployment;
    }

    @Override
    public Date getDateofunemployment() {
        return dateofunemployment;
    }

    @Override
    public void setDateofunemployment(Date dateofunemployment) {
        this.dateofunemployment = dateofunemployment;
    }

    @Override
    public Post getPost() {
        return post;
    }

    @Override
    public void setPost(Post post) {
        this.post = post;
    }

    @Override
    public Integer getSalary() {
        return salary;
    }

    @Override
    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    @Override
    public Boolean getHead() {
        return head;
    }

    @Override
    public void setHead(Boolean head) {
        this.head = head;
    }

    @Override
    public Integer getDepartmentid() {
        return departmentid;
    }

    @Override
    public void setDepartmentid(Integer departmentid) {
        this.departmentid = departmentid;
    }

    @Override
    public Boolean getFired() {
        return fired;
    }

    @Override
    public void setFired(Boolean fired) {
        this.fired = fired;
    }

    @Override
    public String toString() {
        return "EmployerImpl{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", name='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                ", gender=" + gender +
                ", dateofbirth=" + dateofbirth +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", dateofemployment=" + dateofemployment +
                ", dateofunemployment=" + dateofunemployment +
                ", post=" + post +
                ", salary=" + salary +
                ", head=" + head +
                ", departmentid=" + departmentid +
                ", fired=" + fired +
                '}';
    }
}
