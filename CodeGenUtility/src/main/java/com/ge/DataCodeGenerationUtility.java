package com.ge;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DataCodeGenerationUtility {

	@PostConstruct
	void started() {
		TimeZone.setDefault(TimeZone.getTimeZone("CST"));
	}
	
	public static void main(String[] args) {
		SpringApplication.run(DataCodeGenerationUtility.class, args);
	}
}
