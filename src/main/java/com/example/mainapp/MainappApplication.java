package com.example.mainapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication //определяет автоматическое сканирование пакета, где находится класс Application
public class MainappApplication {

	public static void main(String[] args) {

		SpringApplication.run(MainappApplication.class, args);
	}

}
