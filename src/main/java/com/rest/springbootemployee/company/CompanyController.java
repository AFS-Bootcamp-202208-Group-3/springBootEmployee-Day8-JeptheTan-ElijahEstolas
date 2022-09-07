package com.rest.springbootemployee.company;

import com.rest.springbootemployee.employee.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    private CompanyRepository companyRepository;
    private int one;

    public CompanyController (CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @GetMapping
    public List<Company> getAllCompanies () {
        return companyRepository.fetchAllRecords();
    }

    @GetMapping("/{companyID}")
    public Company getCompanyByID (@PathVariable Integer companyID) {
        return companyRepository.findByID(companyID);
    }

    @GetMapping("/{companyID}/employees")
    public List<Employee> getAllEmployeesByCompanyID (@PathVariable Integer companyID) {
        return companyRepository.findEmployeesByCompanyID(companyID);
    }

    @GetMapping(params = {"page", "pageSize"})
    public List<Company> getCompaniesByPage (@RequestParam Integer page, Integer pageSize) {
        return companyRepository.fetchRecordsByPage(page, pageSize);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Company addNewCompany (@RequestBody Company company) {
        return companyRepository.createNewRecord(company);
    }

    @PutMapping("/{companyID}")
    public Company updateCompany (@PathVariable Integer companyID, @RequestBody Company company) {
        return companyRepository.updateExistingRecord(companyID, company);
    }

    @DeleteMapping("/{companyID}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompany (@PathVariable Integer companyID) {
        companyRepository.deleteRecord(companyID);
    }
}
