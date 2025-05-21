Feature: Add Claimant Third Party
  As a user, I want to add a claimant or third party to an existing claim.

  Background: Login as Claim Adjuster
    Given the data is loaded "src/test_data/SandboxData.xlsx", "AddClaimantThirdPartySheet", "TC_ID_0001"
    Given the user is logged in as a Claim Adjuster

#  @Run
  Scenario: Add Claimant Third Party
    Given the user navigates to the Claims tab
    When the user filters claims by entering the claimant name in the column filter
    And selects a claim by clicking the claim number hyperlink
    And navigates to the Parties tab
    And clicks the Add Party button
    And chooses an option from the Type dropdown
    And chooses an option from the Role dropdown
    And enters the first name in the Name field
    And enters the last name in the Last Name field
    And enters the contact number in the Contact Number field under the Additional Details block
    And selects an option from the Is Party Injured? dropdown
    And selects a location from the Location dropdown under the Primary Address Details block
    And clicks Save changes
    And the user exits

    Then the Additional Insured should be created and displayed in the Party List block on the Claim Party | Details Page
    And the user exits

    And the user logs out


