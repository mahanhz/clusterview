Feature: Edit pyramid

  Scenario: Admin users have edit pyramid link
    Given a logged in admin user
    When in "any" cluster page
    Then edit pyramid link is available

  Scenario: Users do not have edit pyramid link
    Given a logged in user
    When in "any" cluster page
    Then edit pyramid link is not available

  Scenario: Admin users can edit pyramid
    Given a logged in admin user
    When attempting to save pyramid in "any" cluster
    Then the pyramid is saved in "" cluster

  Scenario: Users cannot edit pyramid
    Given a logged in user
    When attempting to access the edit pyramid page
    Then the user is forbidden from editing the pyramid

  Scenario: Cluster users can edit pyramid within their cluster
    Given a logged in stockholm cluster user
    When attempting to save pyramid in "stockholm" cluster
    Then the pyramid is saved in "stockholm" cluster

  Scenario: Cluster users cannot edit pyramid outside their cluster
    Given a logged in stockholm cluster user
    When attempting to save pyramid in "uppsala" cluster
    Then the cluster user is forbidden from editing the pyramid

  Scenario: Cluster users have edit pyramid link within their cluster
    Given a logged in stockholm cluster user
    When in "stockholm" cluster page
    Then edit pyramid link is available

  Scenario: Cluster users do not have edit pyramid link outside their cluster
    Given a logged in stockholm cluster user
    When in "uppsala" cluster page
    Then edit pyramid link is not available