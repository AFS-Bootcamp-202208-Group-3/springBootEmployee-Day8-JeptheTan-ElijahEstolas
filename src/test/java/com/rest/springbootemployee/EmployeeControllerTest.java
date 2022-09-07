package com.rest.springbootemployee;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rest.springbootemployee.employee.Employee;
import com.rest.springbootemployee.employee.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.text.MessageFormat;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class EmployeeControllerTest {


    @Autowired
    MockMvc client;

    @Autowired
    EmployeeRepository employeeRepository;


    @BeforeEach
    void cleanRepository() {
        employeeRepository.clearAll();
    }

    @Test
    void should_get_all_employees_when_perform_get_given_employees() throws Exception {
//        //given
        employeeRepository.createNewRecord(new Employee(1, "Susan", 22, "Female", 10000));
//        //when
        client.perform(MockMvcRequestBuilders.get("/employees/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].employeeID").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].employeeName").value("Susan"));
//        //then
    }

    @Test
    void should_get_1_employee_when_perform_get_given_employee_id() throws Exception {
//        //given
        Employee employee = employeeRepository.createNewRecord(new Employee(null, "Susan", 22, "Female", 10000));
//        //when
        client.perform(MockMvcRequestBuilders.get("/employees/{employeeID}", employee.getEmployeeID()))
                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.employeeID").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.employeeName").value("Susan"))
        ;
//        //then
    }

    @Test
    void should_get_female_employee_when_perform_get_given_employee_female() throws Exception {
//        //given
        employeeRepository.createNewRecord(new Employee(1, "Susan", 22, "Female", 10000));
        employeeRepository.createNewRecord(new Employee(2, "Ruby", 22, "Female", 12000));
        employeeRepository.createNewRecord(new Employee(3, "Ruby2", 22, "Male", 12000));
//        //when
        client.perform(MockMvcRequestBuilders.get("/employees?gender={gender}", "Female"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].employeeName", containsInAnyOrder("Susan", "Ruby")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].gender", containsInAnyOrder("Female", "Female")));
//        //then
    }

    @Test
    void should_get_all_employees_by_page_when_perform_get_given_employee_page_and_page_size() throws Exception {
//        //given
        employeeRepository.createNewRecord(new Employee(1, "Hugo First", 25, "Male", 45300));
        employeeRepository.createNewRecord(new Employee(2, "Olive Tree", 28, "Female", 35600));
        employeeRepository.createNewRecord(new Employee(3, "Percy Vere", 21, "Male", 25300));
        employeeRepository.createNewRecord(new Employee(4, "Fay Daway", 35, "Female", 35100));
        employeeRepository.createNewRecord(new Employee(5, "Bess Twishes", 43, "Female", 55200));
//        //when
        client.perform(MockMvcRequestBuilders.get("/employees?page={page}&pageSize={pageSize}", 1, 2))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].employeeName", containsInAnyOrder("Hugo First", "Olive Tree")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].gender", containsInAnyOrder("Male", "Female")));
//        //then
    }

    @Test
    void should_post_new_employee_when_perform_post_given_employee() throws Exception {
//        //given
        String requestBody = new ObjectMapper().writeValueAsString(
                new Employee(4, "Fay Daway", 35, "Female", 35100));
//        //when
        client.perform(MockMvcRequestBuilders.post("/employees", requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isCreated());
//        //then
        client.perform(MockMvcRequestBuilders.get("/employees/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].employeeID").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].employeeName").value("Fay Daway"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].salary").value(35100));
    }

    @Test
    void should_update_employee_when_update_record_given_new_employee_data() throws Exception {
        //given
        Employee employee = employeeRepository.createNewRecord(new Employee(1, "Hugo First", 25, "Male", 45300));
        Employee existingEmployee = new Employee(1, "Aigo Second", 28, "Male", 76543);
        String updateEmployeeJSON = new ObjectMapper().writeValueAsString(existingEmployee);
        //when
        client.perform(MockMvcRequestBuilders.put("/employees/{id}", employee.getEmployeeID())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateEmployeeJSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employeeName").value("Hugo First"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(28))
                .andExpect(MockMvcResultMatchers.jsonPath("$.gender").value("Male"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.salary").value(76543));
        //then
        List<Employee> employeeList = employeeRepository.fetchAllRecords();
        assertThat(employeeList, hasSize(1));
        Employee updatedEmployee = employeeRepository.fetchAllRecords().get(0);
        assertThat(updatedEmployee.getEmployeeName(), equalTo("Hugo First"));
        assertThat(updatedEmployee.getGender(), equalTo("Male"));
    }


}
