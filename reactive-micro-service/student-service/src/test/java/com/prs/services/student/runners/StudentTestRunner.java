package com.prs.services.student.runners;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
		features = "src/test/resources/cucumberConfig", 
		plugin = { "pretty","html:target/cucumber/student.html" }, 
		glue = { "com.prs.services.student.cucumberConfig" },
		stepNotifications = true, 
		monochrome = true)
public class StudentTestRunner {
}