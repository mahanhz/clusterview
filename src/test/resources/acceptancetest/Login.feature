Feature: Login

  Scenario: Login page is displayed
    Given valid url
    When accessing the url
    Then login is displayed

  Scenario: Login is successful
    Given valid username "guest@example.com" and password "guest123"
    When login is attempted
    Then login is successful

  Scenario: Login is invalid
    Given invalid username "hacker@example.com" and password "hackit"
    When attempt to login
    Then login is invalid