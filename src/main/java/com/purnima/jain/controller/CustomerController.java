package com.purnima.jain.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.github.javafaker.Faker;
import com.purnima.jain.dto.request.CreateCustomerRequestDto;
import com.purnima.jain.dto.response.CustomerResponseDto;
import com.purnima.jain.dto.response.ErrorResponseDto;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/api/v1/customers")
@Slf4j
public class CustomerController {

	Faker faker = new Faker();

	private Map<String, CustomerResponseDto> customerMap = new HashMap<>();

	@GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<CustomerResponseDto>> getAllCustomers() {
		log.info("Inside getAllCustomers()......");

		List<CustomerResponseDto> customerResponseDtoList = new ArrayList<>();
		CustomerResponseDto customerResponseDto1 = new CustomerResponseDto();
		customerResponseDto1.setCustomerId(Long.toString(faker.number().randomNumber(5, false)));
		customerResponseDto1.setFirstName(faker.name().firstName());
		customerResponseDto1.setLastName(faker.name().lastName());
		customerResponseDtoList.add(customerResponseDto1);

		CustomerResponseDto customerResponseDto2 = new CustomerResponseDto();
		customerResponseDto2.setCustomerId(Long.toString(faker.number().randomNumber(5, false)));
		customerResponseDto2.setFirstName(faker.name().firstName());
		customerResponseDto2.setLastName(faker.name().lastName());
		customerResponseDtoList.add(customerResponseDto2);

		return new ResponseEntity<>(customerResponseDtoList, HttpStatus.OK);
	}

	@GetMapping(value = "/listByCount", produces = MediaType.APPLICATION_JSON_VALUE)
	// Query Parameter Example, where count is a query parameter
	public ResponseEntity<List<CustomerResponseDto>> getNCustomers(@RequestParam(name = "limit") Integer limit, @RequestParam(name = "offset") Integer offset) {
		log.info("Inside getNCustomers()......");
		log.info("Input limit:: {}", limit);
		log.info("Input offset:: {}", offset);

		List<CustomerResponseDto> customerResponseDtoList = new ArrayList<>();

		for (int i = 0; i < limit; i++) {
			CustomerResponseDto customerResponseDto1 = new CustomerResponseDto();
			customerResponseDto1.setCustomerId(Long.toString(faker.number().randomNumber(5, false)));
			customerResponseDto1.setFirstName(faker.name().firstName());
			customerResponseDto1.setLastName(faker.name().lastName());
			customerResponseDtoList.add(customerResponseDto1);
		}
		log.info("customerResponseDtoList :: {}", customerResponseDtoList);

		return new ResponseEntity<>(customerResponseDtoList, HttpStatus.OK);
	}

	@PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CustomerResponseDto> createCustomer(@RequestBody CreateCustomerRequestDto createCustomerRequestDto) {
		log.info("Inside createCustomer()......");
		log.info("Input createCustomerRequestDto:: {}", createCustomerRequestDto);

		HttpServletRequest request = getCurrentHttpRequest();
		String authorizationHeader = request.getHeader("Authorization");
		log.info("authorizationHeader :: {}", authorizationHeader);

		CustomerResponseDto customerResponseDto = new CustomerResponseDto();
		customerResponseDto.setCustomerId(Long.toString(faker.number().randomNumber(5, false)));
		customerResponseDto.setFirstName(createCustomerRequestDto.getFirstName());
		customerResponseDto.setLastName(createCustomerRequestDto.getLastName());

		customerMap.put(customerResponseDto.getCustomerId(), customerResponseDto);

		log.info("customerResponseDto :: {}", customerResponseDto);

		return new ResponseEntity<>(customerResponseDto, HttpStatus.CREATED);
	}

	public static HttpServletRequest getCurrentHttpRequest() {
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = null;
		if (requestAttributes instanceof ServletRequestAttributes) {
			request = ((ServletRequestAttributes) requestAttributes).getRequest();
		}

		return request;
	}

	@GetMapping(value = "/{customerId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CustomerResponseDto> getCustomerById(@PathVariable("customerId") String customerId) {
		log.info("Inside getCustomerById()......");
		log.info("Input customerId:: {}", customerId);

		CustomerResponseDto customerResponseDto = null;
		if (customerMap.containsKey(customerId)) {
			customerResponseDto = customerMap.get(customerId);
			log.info("Input customerId:: {} maps to customerResponseDto:: {}", customerId, customerResponseDto);
		} else {
			log.info("No Match found for customerId:: {}", customerId);
			return ResponseEntity.notFound().build();
		}

		return new ResponseEntity<>(customerResponseDto, HttpStatus.OK);
	}

	@DeleteMapping(value = "/{customerId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> deleteCustomer(@PathVariable("customerId") String customerId) {
		log.info("Inside deleteCustomer()......");
		log.info("Input customerId:: {}", customerId);

		HttpServletRequest request = getCurrentHttpRequest();
		String authorizationHeader = request.getHeader("Authorization");
		log.info("authorizationHeader :: {}", authorizationHeader);

		if (customerMap.containsKey(customerId)) {
			customerMap.remove(customerId);
		}

		return ResponseEntity.ok().build();
	}
	
	@PostMapping(value = "/validate", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ErrorResponseDto> validateCustomer(@RequestBody CreateCustomerRequestDto createCustomerRequestDto) {
		log.info("Inside validateCustomer()......");
		log.info("Input createCustomerRequestDto:: {}", createCustomerRequestDto);

		ErrorResponseDto errorResponseDto = new ErrorResponseDto();
		if(createCustomerRequestDto.getFirstName().isEmpty()) {
			errorResponseDto.getErrors().add("First Name cannot be empty");
		} else if(createCustomerRequestDto.getFirstName().length() < 4) {
			errorResponseDto.getErrors().add("First Name should be at least 4 characters");
		}
		if(createCustomerRequestDto.getLastName().length() < 4) {
			errorResponseDto.getErrors().add("Last Name should be at least 4 characters");
		}

		log.info("errorResponseDto :: {}", errorResponseDto);
		
		if(errorResponseDto.getErrors().isEmpty()) {
			return new ResponseEntity<>(errorResponseDto, HttpStatus.OK);
		}

		return new ResponseEntity<>(errorResponseDto, HttpStatus.UNPROCESSABLE_ENTITY);
	}

}
