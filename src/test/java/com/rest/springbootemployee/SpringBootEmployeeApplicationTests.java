package com.rest.springbootemployee;

import com.rest.springbootemployee.employee.Employee;
import com.rest.springbootemployee.employee.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;

@SpringBootTest
@AutoConfigureMockMvc
class SpringBootEmployeeApplicationTests {


	@Autowired
	MockMvc client;

	@Autowired
	EmployeeRepository employeeRepository;



	@BeforeEach
	void cleanRepository(){
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
		client.perform(MockMvcRequestBuilders.get("/employees/{employeeID}", Integer.getInteger(String.valueOf(employee.getEmployeeID()))))
				.andExpect(MockMvcResultMatchers.status().isOk())
//                .andDo(print())
				.andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].employeeID").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].employeeName").value("Susan"));
//        //then
	}

	@Test
	void should_get_female_employee_when_perform_get_given_employee_female() throws Exception {
//        //given
		employeeRepository.createNewRecord(new Employee(1, "Susan", 22, "Female", 10000));
		employeeRepository.createNewRecord(new Employee(2, "Ruby", 22, "Female", 12000));
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
		client.perform(MockMvcRequestBuilders.get("/employees?page={page}&pageSize={pageSize}", 1,2))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].employeeName", containsInAnyOrder("Hugo First", "Olive Tree")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].gender", containsInAnyOrder("Male", "Female")));
//        //then
	}

	}
