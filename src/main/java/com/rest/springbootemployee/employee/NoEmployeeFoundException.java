package com.rest.springbootemployee.employee;

public class NoEmployeeFoundException extends RuntimeException {
    public NoEmployeeFoundException () {
        super("No employee record found");
    }
}
