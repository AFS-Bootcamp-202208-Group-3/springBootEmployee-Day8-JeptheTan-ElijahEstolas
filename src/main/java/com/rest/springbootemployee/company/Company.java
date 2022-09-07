package com.rest.springbootemployee.company;

import com.rest.springbootemployee.employee.Employee;

import java.util.List;

public class Company {
    private Integer companyID;
    private String companyName;
    private List<Employee> employees;

    public Company (Integer companyID, String companyName, List<Employee> employees) {
        this.companyID = companyID;
        this.companyName = companyName;
        this.employees = employees;
    }

    public Integer getCompanyID () {
        return companyID;
    }

    public void setCompanyID (Integer companyID) {
        this.companyID = companyID;
    }

    public String getCompanyName () {
        return companyName;
    }

    public void setCompanyName (String companyName) {
        this.companyName = companyName;
    }

    public List<Employee> getEmployees () {
        return employees;
    }

    public void setEmployee (List<Employee> employees) {
        this.employees = employees;
    }
}
