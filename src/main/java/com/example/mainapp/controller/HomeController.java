package com.example.mainapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping(value = {""})
@CrossOrigin(origins = "*")
public class HomeController {

	public HomeController() {
	}

	@RequestMapping(value = {"/", "/index", "/home", "/default"}, method = RequestMethod.GET)
	public ModelAndView defaultMethod() {
		System.out.println("home");
		ModelAndView mav = new ModelAndView("index.html");
		return mav;
	}

	public static void main(String[] args) {

	}
}