package com.purnima.jain.dto.response;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class ErrorResponseDto {

	List<String> errors = new ArrayList<>();

}
