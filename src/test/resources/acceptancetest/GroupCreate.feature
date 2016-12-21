Feature: Create group

  Scenario: Admin users can create groups
    Given a logged in admin user
    When attempting to create a group
    Then the group is created