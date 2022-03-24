package com.prs.services.student.cucumberConfig;

import io.cucumber.java.After;
import io.cucumber.java.Before;

public class Hooks {

	@Before
	public void BeforeSteps() {
		System.out.println("Initialized.");
	}

	@After
	public void AfterSteps() {
		System.out.println("End.");
	}
}
