package com.rest.springbootemployee;

import com.rest.springbootemployee.employee.Employee;
import com.rest.springbootemployee.employee.EmployeeRepository;
import com.rest.springbootemployee.employee.EmployeeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class EmployeeServiceTest {
    @Mock
    EmployeeRepository employeeRepository;

    @InjectMocks
    EmployeeService employeeService;

    @Test
    void should_return_all_employees_when_find_all_given_() {
        //given
        Employee employee = new Employee(1, "Hugo First", 25, "Male", 45300);
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(employee);

        given(employeeRepository.fetchAllRecords()).willReturn(employeeList);
        //when
        List<Employee> result = employeeService.findAll();
        //then
        assertThat(result, hasSize(1));
        assertThat(result.get(0), equalTo(employee));

        verify(employeeRepository).fetchAllRecords();
    }

    @Test
    void should_update_employee_when_update_given_new_employee_data() {
        //given
        final Integer employeeId = 1;

        Employee originalEmployee = new Employee(employeeId, "Hugo First", 25, "Male", 45300);
        when(employeeRepository.findByID(employeeId)).thenReturn(originalEmployee);

        Employee employeeToUpdate = new Employee(employeeId, "Aigo Second", 33, "Female", 16000);

        //when
        Employee updatedEmployee = employeeService.updateById(employeeId, employeeToUpdate);
        //then
        verify(employeeRepository).findByID(employeeId);

        assertThat(updatedEmployee.getEmployeeName(), equalTo("Hugo First"));
        assertThat(updatedEmployee.getGender(), equalTo("Male"));

        assertThat(updatedEmployee.getAge(), equalTo(33));
        assertThat(updatedEmployee.getSalary(), equalTo(16000));
    }
    
    @Test
    void should_return_employees_when_find_by_gender_given_employees_and_gender() {
        //given
       
        //when
    
        //then
    }
}
