Feature: Login Functionality
  As a user
  I want to be able to log in to the application
  So that I can access the system

  Scenario: Successful login with valid credentials
    Given the user is logged in with valid credentials
    Then I should be able to access the system 