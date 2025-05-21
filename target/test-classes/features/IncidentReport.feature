Feature: Incident Report
  As a user, I want to report an incident and create an FNOL.

  Background: Login as Claim Adjuster
    Given the data is loaded "src/test_data/SandboxData.xlsx", "IncidentReportSheet", "TC_ID_0001"
    Given the user is logged in as a Claim Adjuster

#  @Run
  Scenario: Report an Incident
    Given the user initiates a new incident report by clicking the New Incident button
    When the user selects a status from the Notifier Is dropdown
    And selects a country
    And enters a contact number
    And enters a value in the Insured Search field
    And selects an insured from the Insured List dropdown
    And fills out the incident details, including type, cause, and description
    And the user chooses the estimated claim value
    And enters the incident date, time, and reporting date
    And clicks Get Details
    And clicks Create FNOL
    And selects a claimant from the dropdown
    And selects a loss type from the dropdown
    And selects a property from the dropdown
    And sets up an inspection appointment by entering the scheduled date, start time, and end time
    And clicks Save changes
    And verifies LOB and coverages, includes the coverages, and saves them
    And exits the report
    Then the status of the Incident should be Loss Reported
    And the user logs out













