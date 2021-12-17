package com.purnima.jain.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.github.javafaker.Faker;
import com.purnima.jain.dto.request.CreateBookRequestDto;
import com.purnima.jain.dto.response.BookResponseDto;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/api/v1/books")
@Slf4j
public class BookController {

	Faker faker = new Faker();

	private Map<String, BookResponseDto> bookMap = new HashMap<>();

	@GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<BookResponseDto>> getAllBooks() {
		log.info("Inside getAllBooks()......");

		List<BookResponseDto> bookResponseDtoList = new ArrayList<>();
		BookResponseDto bookResponseDto1 = new BookResponseDto();
		bookResponseDto1.setBookId(Long.toString(faker.number().randomNumber(5, false)));
		bookResponseDto1.setTitle(faker.book().title());
		bookResponseDto1.setAuthor(faker.book().author());
		bookResponseDtoList.add(bookResponseDto1);

		BookResponseDto bookResponseDto2 = new BookResponseDto();
		bookResponseDto2.setBookId(Long.toString(faker.number().randomNumber(5, false)));
		bookResponseDto2.setTitle(faker.name().firstName());
		bookResponseDto2.setAuthor(faker.name().lastName());
		bookResponseDtoList.add(bookResponseDto2);

		return new ResponseEntity<>(bookResponseDtoList, HttpStatus.OK);
	}

	@PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BookResponseDto> createBook(@RequestBody CreateBookRequestDto createBookRequestDto) {
		log.info("Inside createBook()......");
		log.info("Input createBookRequestDto:: {}", createBookRequestDto);

		HttpServletRequest request = getCurrentHttpRequest();
		String authorizationHeader = request.getHeader("Authorization");
		log.info("authorizationHeader :: {}", authorizationHeader);

		BookResponseDto bookResponseDto = new BookResponseDto();
		bookResponseDto.setBookId(Long.toString(faker.number().randomNumber(5, false)));
		bookResponseDto.setTitle(createBookRequestDto.getTitle());
		bookResponseDto.setAuthor(createBookRequestDto.getAuthor());

		bookMap.put(bookResponseDto.getBookId(), bookResponseDto);

		log.info("bookResponseDto :: {}", bookResponseDto);

		return new ResponseEntity<>(bookResponseDto, HttpStatus.CREATED);
	}

	public static HttpServletRequest getCurrentHttpRequest() {
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = null;
		if (requestAttributes instanceof ServletRequestAttributes) {
			request = ((ServletRequestAttributes) requestAttributes).getRequest();
		}

		return request;
	}

}