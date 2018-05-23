@user
Feature: Budget

  Scenario: Insert One Budget
    When input month "2018-05" and amount "500"
    Then show month and amount
      | month   | amount |
      | 2018-08 | 500    |