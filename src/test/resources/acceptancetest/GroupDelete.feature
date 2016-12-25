Feature: Delete group

  Scenario: Admin users can view delete group button
    Given a logged in admin user
    When in the group page
    Then delete button is available