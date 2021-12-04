package com.purnima.jain.karate;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.intuit.karate.Results;
import com.intuit.karate.Runner;
import com.intuit.karate.junit5.Karate;

public class UberTestRunner {
	
	@Test
	void testAllInParallel() {
		Results results = Runner.path("classpath:com/purnima/jain/karate").parallel(5);
		assertEquals(0, results.getFailCount(), results.getErrorMessages());
	}
	
	@Karate.Test
	Karate testAllSequentially() {
		return Karate.run("classpath:com/purnima/jain/karate");
	}
	
	@Test
	void testParallel() {
		Results results = Runner.parallel(getClass(), 5);
		assertEquals(0, results.getFailCount(), results.getErrorMessages());
	}

}
