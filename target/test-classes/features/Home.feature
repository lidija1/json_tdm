Feature: Home Policy Creation

  Background: Login as Insurance Agent
    Given the user is logged in with valid credentials

  @Home
  Scenario Outline: Create a new home policy
    Given the data is loaded "<TestData>", "<TC_ID>"
    When I create a new quote
    And I provide new customer details
    And I provide quote registration details
    And I provide policy details
    And I provide location coverage details
    Then I rate the quote

    Examples:
      | TestData                  | TC_ID      |
      | src/test_data/HomeData.json | TC_ID_0001 |
#      | src/test_data/HomeData.json | TC_ID_0002 |
#      | src/test_data/HomeData.json | TC_ID_0003 |
#      | src/test_data/HomeData.json | TC_ID_0004 |
#      | src/test_data/HomeData.json | TC_ID_0005 |
#      | src/test_data/HomeData.json | TC_ID_0006 |
#      | src/test_data/HomeData.json | TC_ID_0007 |
#      | src/test_data/HomeData.json | TC_ID_0008 |
#      | src/test_data/HomeData.json | TC_ID_0009 |
#      | src/test_data/HomeData.json | TC_ID_0010 |
