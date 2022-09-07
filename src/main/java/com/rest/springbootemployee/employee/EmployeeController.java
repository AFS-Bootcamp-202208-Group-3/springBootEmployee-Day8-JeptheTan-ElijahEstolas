package com.rest.springbootemployee.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public List<Employee> getAllEmployees () {
        return employeeService.findAll();
    }

    @GetMapping("/{employeeID}")
    public Employee getEmployeeByID (@PathVariable Integer employeeID) {
        return employeeService.findByID(employeeID);
    }

    @GetMapping(params = {"gender"})
    public List<Employee> getEmployeesByGender (@RequestParam String gender) {
        return employeeService.findByGender(gender);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee addNewEmployee (@RequestBody Employee employee) {
        return employeeService.createNewRecord(employee);
    }

    @PutMapping("/{employeeID}")
    public Employee updateEmployee (@PathVariable Integer employeeID, @RequestBody Employee employee) {
        return employeeService.updateById(employeeID, employee);
    }

    @DeleteMapping("/{employeeID}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmployee (@PathVariable Integer employeeID) {
        employeeService.deleteRecord(employeeID);
    }

    @GetMapping(params = {"page", "pageSize"})
    public List<Employee> getEmployeesByPage (@RequestParam Integer page, Integer pageSize) {
        return employeeService.fetchRecordsByPage(page, pageSize);
    }
}
