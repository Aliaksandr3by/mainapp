package com.example.mainapp;

import com.example.mainapp.controller.HomeController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.Assert.assertNotNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = HomeController.class)
public class HomeControllerTest {

	@Autowired
	private HomeController cd;

	@Test
	public void cdShouldNotBeNull() {
		assertNotNull(cd);
	}
}