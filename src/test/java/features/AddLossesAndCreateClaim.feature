Feature: Add Losses and Create a Claim
  As a user, I want to add multiple losses to an incident so that I can create a claim.

  Background: Login as Claim Adjuster
    Given the data is loaded "src/test_data/SandboxData.xlsx", "AddLossesAndCreateClaimSheet", "TC_ID_0001"
    Given the user is logged in as a Claim Adjuster

#  @Run
  Scenario: Add Multiple Losses and Create a Claim
    Given the user navigates to the Incidents tab
    When the user filters incidents by entering the insured name in the column filter
    And selects an incident by clicking the incident number hyperlink
    And clicks the Add More Losses button
    And selects a claimant from the dropdown
    And selects a loss type from the dropdown
    And enters an injury description
    And clicks Save changes
    And clicks the Other Information tree node
    And selects No for the fatalities or deaths question
    And verifies the LOB and coverages
    And clicks the Select Coverages and Save button
    And clicks the PD Loss tree node
    And clicks the Homeowner node
    And clicks the Select Coverages and Save button
    And clicks the Create Claim button
    And selects the Single Claim option
    And clicks the Create Claim button again
    Then the claim status should be Open
    And the assignment transaction should be created and processed
    And the claim registration transaction should be created and processed
    And the user logs out




