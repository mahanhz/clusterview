Feature: Delete group

  Scenario: Admin users can view delete group button
    Given a logged in admin user
    When in the group page for group 901
    Then delete button is available

  Scenario: Admin users can delete groups
    Given a logged in admin user
    When attempting to delete group 901
    Then the group is deleted

  Scenario: Users cannot view delete group button
    Given a logged in user
    When in the group page for group 902
    Then delete button is not available

  Scenario: Users cannot delete groups
    Given a logged in user
    When attempting to manually delete group 902
    Then the user is forbidden from deleting the group