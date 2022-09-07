package com.rest.springbootemployee.company;

import com.rest.springbootemployee.employee.Employee;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
public class CompanyRepository {
    private final List<Company> companies;

    public CompanyRepository () {
        companies = new ArrayList<>();
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1, "Patrick Star", 21, "Male", 17800));
        employees.add(new Employee(2, "Fran Tick", 22, "Male", 25900));
        employees.add(new Employee(3, "Manny Jah", 22, "Male", 28100));
        companies.add(new Company(1, "Oracle", employees));
        companies.add(new Company(2, "OOCL", employees));
        companies.add(new Company(3, "Microsoft", employees));
    }

    public List<Company> fetchAllRecords () {
        return companies;
    }

    public Company findByID (Integer companyID) {
        return companies.stream().filter(company -> company.getCompanyID().equals(companyID)).findFirst().orElseThrow(NoCompanyFoundException::new);
    }

    public List<Employee> findEmployeesByCompanyID (Integer companyID) {
        Company existingCompany = findByID(companyID);
        return existingCompany.getEmployees();
    }

    public List<Company> fetchRecordsByPage (Integer page, Integer pageSize) {
        return companies.stream()
                .skip((long) (page - 1) * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    public Company createNewRecord (Company company) {
        Integer newID = generateNewID();
        company.setCompanyID(newID);
        companies.add(company);
        return company;
    }

    private Integer generateNewID () {
        int newID = companies.stream()
                .mapToInt(company -> company.getCompanyID())
                .max()
                .orElse(1);
        return ++newID;
    }

    public Company updateExistingRecord (Integer companyID, Company company) {
        Company existingCompany = findByID(companyID);
        if (Objects.nonNull(existingCompany.getCompanyName())) {
            existingCompany.setCompanyName(company.getCompanyName());
        }
        return existingCompany;
    }

    public void deleteRecord (Integer companyID) {
        Company existingCompany = findByID(companyID);
        companies.remove(existingCompany);
    }
}
