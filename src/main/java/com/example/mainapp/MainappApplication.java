package com.example.mainapp;

import com.example.mainapp.service.EmployeeConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class MainappApplication {

	public static void main(String[] args) {

		SpringApplication.run(MainappApplication.class, args);
	}

}
