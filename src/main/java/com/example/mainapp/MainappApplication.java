package com.example.mainapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

//определяет автоматическое сканирование пакета, где находится класс Application
@SpringBootApplication
public class MainappApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {

		SpringApplication.run(MainappApplication.class, args);
	}

}
