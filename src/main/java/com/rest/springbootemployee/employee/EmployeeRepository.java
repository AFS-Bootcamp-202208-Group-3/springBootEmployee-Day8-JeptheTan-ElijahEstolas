package com.rest.springbootemployee.employee;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
public class EmployeeRepository {
    private final List<Employee> employees;

    public EmployeeRepository () {
        employees = new ArrayList<>();
        employees.add(new Employee(1, "Hugo First", 25, "Male", 45300));
        employees.add(new Employee(2, "Olive Tree", 28, "Female", 35600));
        employees.add(new Employee(3, "Percy Vere", 21, "Male", 25300));
        employees.add(new Employee(4, "Fay Daway", 35, "Female", 35100));
        employees.add(new Employee(5, "Bess Twishes", 43, "Female", 55200));
    }

    public List<Employee> fetchAllRecords () {
        return employees;
    }

    public Employee findByID (Integer employeeID) {
        return employees.stream()
                .filter(employee -> employee.getEmployeeID().equals(employeeID))
                .findFirst()
                .orElseThrow(NoEmployeeFoundException::new);
    }

    public List<Employee> findByGender (String gender) {
        return employees.stream()
                .filter(employee -> employee.getGender().equals(gender))
                .collect(Collectors.toList());
    }

    public Employee createNewRecord (Employee employee) {
        Integer newID = generateNewID();
        employee.setEmployeeID(newID);
        employees.add(employee);
        return employee;
    }

    private Integer generateNewID () {
        int newID = employees.stream()
                .mapToInt(employee -> employee.getEmployeeID())
                .max()
                .orElse(1);
        return ++newID;
    }

    public Employee updateExistingRecord (Integer employeeID, Employee employee) {
        Employee existingEmployee = findByID(employeeID);
        if (Objects.nonNull(employee.getAge())) {
            existingEmployee.setAge(employee.getAge());
        }
        if (Objects.nonNull(employee.getSalary())) {
            existingEmployee.setSalary(employee.getSalary());
        }
        return existingEmployee;
    }

    public void deleteRecord (Integer employeeID) {
        Employee existingEmployee = findByID(employeeID);
        employees.remove(existingEmployee);
    }

    public List<Employee> fetchRecordsByPage (Integer page, Integer pageSize) {
        return employees.stream()
                .skip((page - 1) * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
    }
}
