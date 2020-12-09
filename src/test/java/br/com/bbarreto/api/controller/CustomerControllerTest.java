package br.com.bbarreto.api.controller;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import br.com.bbarreto.api.dto.CustomerResponseDTO;
import br.com.bbarreto.api.service.CustomerService;
import br.com.bbarreto.core.service.impl.JwtServiceImpl;
import br.com.bbarreto.core.service.impl.UserDetailsServiceImpl;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private JwtServiceImpl jwtService;

	@MockBean
	private CustomerService customerService;

	@MockBean
	private UserDetailsServiceImpl userDetailsService;

	private List<CustomerResponseDTO> findAllCustomerResposeDTOList;

	@BeforeEach
	public void beforeEach() {
		Mockito.when(this.userDetailsService.loadUserByUsername(ArgumentMatchers.anyString())).thenCallRealMethod();
		Mockito.when(this.jwtService.extractFromAuthorizationHeader(ArgumentMatchers.any(HttpServletRequest.class)))
		.thenCallRealMethod();

		this.findAllCustomerResposeDTOList = Arrays.asList(
				new CustomerResponseDTO(1L, "John 1", "Doe", "email1@example.com", true, OffsetDateTime.now(), OffsetDateTime.now()),
				new CustomerResponseDTO(2L, "John 2", "Doe", "email2@example.com", true, OffsetDateTime.now(), OffsetDateTime.now())
				);
	}

	@Test
	void whenFindAll_thenHttpStatus200() throws Exception {
		Mockito.when(this.customerService.findAll()).thenReturn(this.findAllCustomerResposeDTOList);

		this.mockMvc.perform(MockMvcRequestBuilders.get("/v1/customer").contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$.list").isArray())
		.andExpect(MockMvcResultMatchers.jsonPath("$.list", Matchers.hasSize(2)))
		.andDo(MockMvcResultHandlers.print());
	}

	@Test
	void whenFindAll_thenShouldReturnAllObjectsFromService() throws Exception {
		Mockito.when(this.customerService.findAll()).thenReturn(this.findAllCustomerResposeDTOList);

		this.mockMvc
		.perform(MockMvcRequestBuilders.get("/v1/customer")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$.list").isArray())
		.andExpect(MockMvcResultMatchers.jsonPath("$.list", Matchers.hasSize(2)))
		.andDo(MockMvcResultHandlers.print());
	}

}
