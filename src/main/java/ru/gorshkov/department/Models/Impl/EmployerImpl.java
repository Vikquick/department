package ru.gorshkov.department.Models.Impl;

import ru.gorshkov.department.Models.Enum.Gender;
import ru.gorshkov.department.Models.Enum.Post;
import ru.gorshkov.department.Models.Intf.Employer;

import java.util.Date;

public class EmployerImpl implements Employer {

    private Integer id;
    private String name;
    private String firstname;
    private String lasname;
    private Gender gender;
    private Date dateofbirth;
    private String phone;
    private String email;
    private Date dateofemployment;
    private Date dateofunemployment;
    private Post post;
    private Integer salary;
    private Boolean head;
    private Integer departmentid;

    public EmployerImpl() {
    }

    public EmployerImpl(Integer id, String name, String firstname, Object gender, Date dateofbirth, String phone, String email, Date dateofemployment, Object post, Integer salary, Boolean head, Integer departmentid) {
        this.id = id;
        this.name = name;
        this.firstname = firstname;
        this.gender = Gender.valueOf((String) gender);
        this.dateofbirth = dateofbirth;
        this.phone = phone;
        this.email = email;
        this.dateofemployment = dateofemployment;
        this.post = Post.valueOf((String) post);
        this.salary = salary;
        this.head = head;
        this.departmentid = departmentid;
    }

    public EmployerImpl(Integer id, String name, String firstname, String lasname, Object gender, Date dateofbirth, String phone, String email, Date dateofemployment, Object post, Integer salary, Boolean head, Integer departmentid) {
        this.id = id;
        this.name = name;
        this.firstname = firstname;
        this.lasname = lasname;
        this.gender = Gender.valueOf((String) gender);
        this.dateofbirth = dateofbirth;
        this.phone = phone;
        this.email = email;
        this.dateofemployment = dateofemployment;
        this.post = Post.valueOf((String) post);
        this.salary = salary;
        this.head = head;
        this.departmentid = departmentid;
    }

    public EmployerImpl(Integer id, String name, String firstname, String lasname, Object gender, Date dateofbirth, String phone, String email, Date dateofemployment, Date dateofunemployment, Object post, Integer salary, Boolean head, Integer departmentid) {
        this.id = id;
        this.name = name;
        this.firstname = firstname;
        this.lasname = lasname;
        this.gender = Gender.valueOf((String) gender);
        this.dateofbirth = dateofbirth;
        this.phone = phone;
        this.email = email;
        this.dateofemployment = dateofemployment;
        this.dateofunemployment = dateofunemployment;
        this.post = Post.valueOf((String) post);
        this.salary = salary;
        this.head = head;
        this.departmentid = departmentid;
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

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLasname() {
        return lasname;
    }

    public void setLasname(String lasname) {
        this.lasname = lasname;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Date getDateofbirth() {
        return dateofbirth;
    }

    public void setDateofbirth(Date dateofbirth) {
        this.dateofbirth = dateofbirth;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDateofemployment() {
        return dateofemployment;
    }

    public void setDateofemployment(Date dateofemployment) {
        this.dateofemployment = dateofemployment;
    }

    public Date getDateofunemployment() {
        return dateofunemployment;
    }

    public void setDateofunemployment(Date dateofunemployment) {
        this.dateofunemployment = dateofunemployment;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public Boolean getHead() {
        return head;
    }

    public void setHead(Boolean head) {
        this.head = head;
    }

    public Integer getDepartmentid() {
        return departmentid;
    }

    public void setDepartmentid(Integer departmentid) {
        this.departmentid = departmentid;
    }
}
