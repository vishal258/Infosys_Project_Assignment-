package com.retailer.app;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RewardsProgramApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void testMainMethod() {
		String[] args = {};
		RewardsApp.main(args);
	}
}