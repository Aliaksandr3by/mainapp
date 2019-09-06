package com.example.mainapp;

import com.example.mainapp.controller.HomeController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = HomeController.class)
public class HomeControllerTest {

	@Autowired
	private HomeController cd;

	@Test
	public void cdShouldNotBeNull() {
		assertNotNull(cd);
	}
}