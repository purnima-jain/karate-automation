package com.purnima.jain.karate.book;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.intuit.karate.Results;
import com.intuit.karate.Runner;
import com.intuit.karate.junit5.Karate;

public class BookTestRunner {
	
	@Test
	void testAllInParallel() {
		Results results = Runner.path("classpath:com/purnima/jain/karate/book").parallel(5);
		assertEquals(0, results.getFailCount(), results.getErrorMessages());
	}
	
	@Karate.Test
	Karate testAllSequentially() {
		return Karate.run("classpath:com/purnima/jain/karate/book");
	}
	
	@Karate.Test
	Karate runOnlyAParticularFeatureFile() {
		return Karate.run("classpath:com/purnima/jain/karate/book/tests/GetBooks.feature");
	}
	
	@Karate.Test
	Karate runOnlyTaggedTests() {
		return Karate.run("classpath:com/purnima/jain/karate/book/tests/GetBooks.feature").tags("@myTag");
	}	

}
