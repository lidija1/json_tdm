Feature: Add Loss to Existing Claim
  As a user, I want to add a new loss to an existing claim.

  Background: Login as Claim Adjuster
    Given the data is loaded "src/test_data/SandboxData.xlsx", "AddLossSheet", "TC_ID_0001"
    Given the user is logged in as a Claim Adjuster

#  @Run
  Scenario: Add a New Loss to an Existing Claim
    Given the user navigates to the Claims tab
    When the user filters claims by entering the claimant name in the column filter
    And selects a claim by clicking the claim number hyperlink
    And clicks the Add Claimant Coverage button
    And clicks the Add New Loss button
    And chooses a claimant from the dropdown
    And picks a loss type from the dropdown
    And inputs an injury description
    And clicks Save changes
    And verifies coverages
    And clicks Save changes
    And processes the claim
    Then the claim status in the Claim Transaction Summary block should be Processed
    And the user exits

    Then the Add Claimant Coverage Transaction should be created and marked as Processed
    And the user exits

    And the user logs out


