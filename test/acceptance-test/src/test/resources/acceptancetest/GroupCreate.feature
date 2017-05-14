Feature: Create group

  Scenario: Admin users have group operations
    Given a logged in admin user
    When in "any" cluster page
    Then group operations are available

  Scenario: Admin users can create groups
    Given a logged in admin user
    When attempting to create a group in "any" cluster
    Then the group is created in "" cluster

  Scenario: Users do not have group operations
    Given a logged in user
    When in "any" cluster page
    Then group operations are not available

  Scenario: Users cannot create groups
    Given a logged in user
    When attempting to access the new group page
    Then the user is forbidden from creating the group

  Scenario: Cluster users can create groups within their cluster
    Given a logged in stockholm cluster user
    When attempting to create a group in "stockholm" cluster
    Then the group is created in "stockholm" cluster

  Scenario: Cluster users cannot create groups outside their cluster
    Given a logged in stockholm cluster user
    When attempting to create a group in "uppsala" cluster
    Then the cluster user is forbidden from creating the group

  Scenario: Cluster users have group operations within their cluster
    Given a logged in stockholm cluster user
    When in "stockholm" cluster page
    Then group operations are available

  Scenario: Cluster users do not have group operations outside their cluster
    Given a logged in stockholm cluster user
    When in "uppsala" cluster page
    Then group operations are not available