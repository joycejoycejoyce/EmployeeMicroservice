package net.joyce.employeeservice;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class EmployeeServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeeServiceApplication.class, args);
	}

	@Bean
	public ModelMapper createModelMapper() {
		return new ModelMapper();
	}
	/*
	* this WebClient is registered in a Spring IoC Container
	* 现在就可以 inject 这个 rest template 在整个 project 的任何地方
	* */
	@Bean
	public WebClient webClient() {
		return WebClient.builder().build();
	}
}
