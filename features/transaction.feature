# N26 test
# Author: Carolyn Hung
# created at : April 15, 2018


Feature: testing api calls

  Scenario: create a transaction
    When I am a non-authenticated user
    And I send PUT request to endpoint to create a new transaction with transaction id 3, amount 10.00, type cars, parent_id 1
    Then I should get OK response
    And I send PUT request to endpoint to create a new transaction with transaction id 4, amount 20.00 and type cleaning
    Then I should get OK response

  Scenario Outline: create more transactions
    When I am a non-authenticated user
    And I send PUT request to endpoint to create a new transaction with transaction id <transaction_id>, amount <amount>, type <type>, parent_id <parent_id>
    Then I should get OK response
    Examples:
      | transaction_id | amount | type | parent_id |
      | 5              | 30.00   | cars |  1         |
      | 6              | 40.50  | groceries | 2    |


  Scenario: get a transaction by transaction id
    When I am a non-authenticated user
    And I send GET request to endpoint to get a transaction by id 5
    And I should get a transaction with id 5

  Scenario Outline: get transactions ids by type
    When I am a non-authenticated user
    And I send GET request to endpoint to get transaction by type <type>
    And I should get transaction id with type <type>
    Examples:
      | type |
      | cars |
      | groceries |

  Scenario: get sum by transaction id
    When I am a non-authenticated user
    And I send GET request to endpoint to get a transaction by id 1
    Then I should get OK response
    And I send GET request to endpoint to get sum by id 1
    Then I should get OK response
    And I should get sum with id 1
    And sum with id 1 should be 90.0
