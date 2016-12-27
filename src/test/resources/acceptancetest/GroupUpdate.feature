Feature: Update group

  Scenario: Admin users can view edit group button
    Given a logged in admin user
    When in the group page for group 902
    Then edit button is available

  Scenario: Admin users can update groups
    Given a logged in admin user
    When attempting to update group 902
    Then the group is updated

  Scenario: Users cannot view edit group button
    Given a logged in user
    When in the group page for group 902
    Then edit button is not available

  Scenario: Users cannot update groups
    Given a logged in user
    When attempting to access the edit group page for group 902
    Then the user is forbidden from editing the group