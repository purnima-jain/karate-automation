package com.purnima.jain.helpers;

import com.github.javafaker.Faker;

public class DataGenerator {
	
	private static Faker faker = new Faker();
	
	public static String getRandomFirstName() {
		String firstName = faker.name().firstName();
		return firstName;
	}
	
	public static String getRandomLastName() {
		String lastName = faker.name().lastName();
		return lastName;
	}
	
	public String getRandomFirstNameViaNonStaticMethod() {
		String firstName = faker.name().firstName();
		return firstName;
	}
	
	public String getRandomLastNameViaNonStaticMethod() {
		String lastName = faker.name().lastName();
		return lastName;
	}

}
