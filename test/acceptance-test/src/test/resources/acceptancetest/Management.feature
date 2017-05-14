Feature: Management

  Scenario: Any logged in user has access to health page
    Given a logged in user
    When attempting to access the health page
    Then the health page is displayed

  Scenario: Super Admin users have access to management page
    Given a logged in super admin user
    When attempting to access the management page
    Then the management page is displayed

  Scenario: Admin users do not have access to management page
    Given a logged in admin user
    When attempting to access the management page
    Then the user is forbidden from the management page

  Scenario: Admin users do not have access to env page
    Given a logged in admin user
    When attempting to access the env page
    Then the user is forbidden from the env page