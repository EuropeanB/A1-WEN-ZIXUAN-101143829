Feature: Library login feature

  Scenario: User logs into the library system successfully
    Given a library system is running
    When the user logs in with username "alice" and password "pass123"
    Then login should be successful