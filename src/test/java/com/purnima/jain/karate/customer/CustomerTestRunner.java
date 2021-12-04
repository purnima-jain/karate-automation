package com.purnima.jain.karate.customer;

import com.intuit.karate.Results;
import com.intuit.karate.Runner;
import com.intuit.karate.junit5.Karate;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class CustomerTestRunner {
	
	@Test
	void testAllInParallel() {
		Results results = Runner.path("classpath:com/purnima/jain/karate/customer").parallel(5);
		assertEquals(0, results.getFailCount(), results.getErrorMessages());
	}
	
	@Karate.Test
	Karate testAllSequentially() {
		return Karate.run("classpath:com/purnima/jain/karate/customer");
	}
	
}
