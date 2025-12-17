Feature: SIM Card Activation Service Test

  As a network administrator
  I want to activate SIM cards through the microservice
  So that I can manage the network efficiently

  @successful
  Scenario: Successful SIM Card Activation
    Given the service is running
    When I submit an activation request for ICCID "1255789453849037777"
    Then the activation request should be successful with HTTP status 200
    And the activation record with ID 1 should have status "Success"

  @failed
  Scenario: Failed SIM Card Activation
    Given the service is running
    When I submit an activation request for ICCID "8944500102198304826"
    Then the activation request should be successful with HTTP status 200
    And the activation record with ID 2 should have status "Failure"