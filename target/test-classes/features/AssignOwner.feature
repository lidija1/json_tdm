Feature: Assign an Owner to a Claim Coverage
  As a user, I want to assign an owner to a claim coverage.

  Background: Login as Claim Adjuster
    Given the data is loaded "src/test_data/SandboxData.xlsx", "AddClaimantThirdPartySheet", "TC_ID_0001"
    Given the user is logged in as a Claim Adjuster

  @Run
  Scenario: Assign an Owner to a Claim Coverage
    Given the user navigates to the Claims tab
    When the user filters claims by entering the claimant name in the column filter
    And selects a claim by clicking the claim number hyperlink
    And clicks the Assignment button
    And selects an owner from the Claim Owner dropdown
    And clicks Save changes
    And clicks Process
    Then the Assigned To field in the Claim Transaction Summary block should display the selected owner
    And the Claim Transaction status should be Processed
    And the user exits

    Then the claim owner in the Processing Details block on the Claim Summary | Details Page should match the selected owner
    And the Transaction Assignment in the Transactions block should be created and marked as Processed
    And the user exits

    And the user logs out
