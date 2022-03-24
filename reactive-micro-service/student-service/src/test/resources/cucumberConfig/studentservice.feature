Feature: Student Controller functionalities
	Description: The purpose of this feature is to do unit test.

Scenario: Add student into database
		Given User is on Home Page
	  When I add a student to server
	  Then the server should contain that student