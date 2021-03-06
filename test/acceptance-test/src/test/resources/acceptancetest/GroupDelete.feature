Feature: Delete group

  Background:
    Given a group

  Scenario: Admin users can view delete group button
    Given a logged in admin user
    When in the group page
    Then delete button is available

  Scenario: Admin users can delete groups
    Given a logged in admin user
    When attempting to delete the group
    Then the group is deleted

  Scenario: Users cannot view delete group button
    Given a logged in user
    When in the group page
    Then delete button is not available

  Scenario: Users cannot delete groups
    Given a logged in user
    When attempting to manually delete the group
    Then the user is forbidden from deleting the group