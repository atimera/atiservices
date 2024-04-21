package com.amdiatou.atiservices;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
class MainTests {

	@Autowired
	private ApplicationContext applicationContext;

	@Test
	void contextLoads() {
		System.out.println("Nb bean definitions = " + applicationContext.getBeanDefinitionCount());
	}


}
