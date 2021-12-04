package com.purnima.jain.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.purnima.jain.dto.request.UserRequestDto;
import com.purnima.jain.dto.response.UserTokenResponseDto;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/api/v1/token")
@Slf4j
public class TokenController {
	
	@PostMapping(value = "/createTokenViaApi", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserTokenResponseDto> createTokenViaApi(@RequestBody UserRequestDto userRequestDto) {
		log.info("Inside createTokenViaApi()......");
		log.info("Input userRequestDto:: {}", userRequestDto);

		UserTokenResponseDto userTokenResponseDto = new UserTokenResponseDto();
		userTokenResponseDto.setUsername(userRequestDto.getUsername());
		userTokenResponseDto.setToken("JWT:" + Math.random());
		
		log.info("userTokenResponseDto :: {}", userTokenResponseDto);

		return new ResponseEntity<>(userTokenResponseDto, HttpStatus.OK);
	}

}
