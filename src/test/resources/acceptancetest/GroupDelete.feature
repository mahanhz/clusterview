Feature: Delete group

  Scenario: Admin users can view delete group button
    Given a logged in admin user
    When in the group page
    Then delete button is available

  Scenario: Admin users can delete groups
    Given a logged in admin user
    When attempting to delete a group
    Then the group is deleted

  Scenario: Users cannot view delete group button
    Given a logged in user
    When in the group page
    Then delete button is not available