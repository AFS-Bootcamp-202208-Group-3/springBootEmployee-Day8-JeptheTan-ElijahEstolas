package com.rest.springbootemployee.employee;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    private EmployeeRepository employeeRepository;

    public EmployeeController (EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @GetMapping
    public List<Employee> getAllEmployees () {
        return employeeRepository.fetchAllRecords();
    }

    @GetMapping("/{employeeID}")
    public Employee getEmployeeByID (@PathVariable Integer employeeID) {
        return employeeRepository.findByID(employeeID);
    }

    @GetMapping(params = {"gender"})
    public List<Employee> getEmployeesByGender (@RequestParam String gender) {
        return employeeRepository.findByGender(gender);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee addNewEmployee (@RequestBody Employee employee) {
        return employeeRepository.createNewRecord(employee);
    }

    @PutMapping("/{employeeID}")
    public Employee updateEmployee (@PathVariable Integer employeeID, @RequestBody Employee employee) {
        return employeeRepository.updateExistingRecord(employeeID, employee);
    }

    @DeleteMapping("/{employeeID}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmployee (@PathVariable Integer employeeID) {
        employeeRepository.deleteRecord(employeeID);
    }

    @GetMapping(params = {"page", "pageSize"})
    public List<Employee> getEmployeesByPage (@RequestParam Integer page, Integer pageSize) {
        return employeeRepository.fetchRecordsByPage(page, pageSize);
    }
}
