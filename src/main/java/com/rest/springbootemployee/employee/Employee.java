package com.rest.springbootemployee.employee;

public class Employee {
    private Integer employeeID;
    private String employeeName;
    private Integer age;
    private String gender;
    private Integer salary;

    public Employee (Integer employeeID, String employeeName, Integer age, String gender, Integer salary) {
        this.employeeID = employeeID;
        this.employeeName = employeeName;
        this.age = age;
        this.gender = gender;
        this.salary = salary;
    }

    public Integer getEmployeeID () {
        return employeeID;
    }

    public void setEmployeeID (Integer employeeID) {
        this.employeeID = employeeID;
    }

    public String getEmployeeName () {
        return employeeName;
    }

    public void setEmployeeName (String employeeName) {
        this.employeeName = employeeName;
    }

    public Integer getAge () {
        return age;
    }

    public void setAge (Integer age) {
        this.age = age;
    }

    public String getGender () {
        return gender;
    }

    public void setGender (String gender) {
        this.gender = gender;
    }

    public Integer getSalary () {
        return salary;
    }

    public void setSalary (Integer salary) {
        this.salary = salary;
    }
}
