package com.rest.springbootemployee;

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

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;

@SpringBootTest
@AutoConfigureMockMvc
class EmployeeControllerTest {


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
		client.perform(MockMvcRequestBuilders.get("/employees?page={page}&pageSize={pageSize}", 1,2))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].employeeName", containsInAnyOrder("Hugo First", "Olive Tree")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].gender", containsInAnyOrder("Male", "Female")));
//        //then
	}
	@Test
	void should_post_new_employee_when_perform_post_given_employee() throws Exception {
//        //given
		String employeeName = "Ruby";
		String gender = "Female";
		Integer age = 19;
		Integer salary = 999999;
		String requestBody = MessageFormat.format(

				"\"employeeID\":{4}," +
				"\"employeeName\":\"{0}\"," +
				"\"gender\":\"{1}\"," +
				"\"age\":{2}," +
				"\"salary\":{3}" +
				"" ,
				employeeName,gender,age,String.valueOf(salary),4);
		requestBody = "{ " +requestBody+"}";
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
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].employeeName").value("Ruby"))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].salary").value(999999));
	}

	}
