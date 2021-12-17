package com.purnima.jain.helpers;

public class TokenGenerator {

	public static String generateToken(String username, String password) {
		System.out.println("Inside TokenGenerator.generateToken() :: Username:" + username);
		System.out.println("Inside TokenGenerator.generateToken() :: Password:" + password);

		return "my_newly_generated_library_token";
	}

}
