package com.rest.springbootemployee.company;

public class NoCompanyFoundException extends RuntimeException {
    public NoCompanyFoundException () {
        super("No company record found");
    }
}