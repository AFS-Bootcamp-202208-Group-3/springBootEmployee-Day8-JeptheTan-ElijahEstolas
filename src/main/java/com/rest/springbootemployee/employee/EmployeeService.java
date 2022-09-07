package com.rest.springbootemployee.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;

    public List<Employee> findAll() {
        return employeeRepository.fetchAllRecords();
    }

    public Employee findByID(Integer employeeID) {
        return employeeRepository.findByID(employeeID);
    }

    public List<Employee> findByGender(String gender) {
        return employeeRepository.findByGender(gender);
    }

    public Employee createNewRecord(Employee employee) {
        return employeeRepository.createNewRecord(employee);
    }

    public void deleteRecord(Integer employeeID) {
        employeeRepository.deleteRecord(employeeID);
    }

    public List<Employee> fetchRecordsByPage(Integer page, Integer pageSize) {
        return employeeRepository.fetchRecordsByPage(page, pageSize);
    }

    public Employee updateById(Integer employeeID, Employee employee) {
        Employee existingEmployee = findByID(employeeID);
        if (Objects.nonNull(employee.getAge())) {
            existingEmployee.setAge(employee.getAge());
        }
        if (Objects.nonNull(employee.getSalary())) {
            existingEmployee.setSalary(employee.getSalary());
        }
        return employeeRepository.updateExistingRecord(existingEmployee);
    }

    private void setNewID(Employee employee) {
        Integer newID = generateNewID();
        employee.setEmployeeID(newID);
    }
    private Integer generateNewID () {
        int newID = findAll().stream()
                .mapToInt(Employee::getEmployeeID)
                .max()
                .orElse(1);
        return ++newID;
    }
}
