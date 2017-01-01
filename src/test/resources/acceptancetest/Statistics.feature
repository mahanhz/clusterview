Feature: Statistics

  Scenario: Admin users can save history
    Given a logged in admin user
    When attempting to save history
    Then the history is saved

  Scenario: Admin users have save history button
    Given a logged in admin user
    When in the stats page
    Then save history button is available

  Scenario: Cluster users do not have save history button
    Given a logged in stockholm cluster user
    When in the stats page
    Then save history button is not available

  Scenario: Users do not have save history button
    Given a logged in user
    When in the stats page
    Then save history button is not available