package com.purnima.jain.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.javafaker.Faker;
import com.purnima.jain.dto.response.BookResponseDto;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/api/v1/books")
@Slf4j
public class BookController {

	Faker faker = new Faker();

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

}