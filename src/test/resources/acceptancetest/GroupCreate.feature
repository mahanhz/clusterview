Feature: Create group

  Scenario: Admin users have group operations
    Given a logged in admin user
    When in the cluster page
    Then group operations are available

  Scenario: Admin users can create groups
    Given a logged in admin user
    When attempting to create a group
    Then the group is created

  Scenario: Users do not have group operations
    Given a logged in user
    When in the cluster page
    Then group operations are not available

  Scenario: Users cannot create groups
    Given a logged in user
    When attempting to access the new group page
    Then the user is forbidden from creating the group