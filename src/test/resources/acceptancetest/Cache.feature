Feature: Cache

  Scenario: Super Admin users have access to caches list page
    Given a logged in super admin user
    When attempting to access the caches list page
    Then the caches list page is displayed

  Scenario: Admin users do not have access to caches list page
    Given a logged in admin user
    When attempting to access the caches list page
    Then the user is forbidden from the caches list page