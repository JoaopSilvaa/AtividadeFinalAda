Feature: Register new tasks

  Scenario: The task was not registered
    Given that I don't have a task registered
      | title           | description        | closedAt |
      | Aula toda terca | Terca fire!!!!     | 2023-04-26 |
    When I register a task
    Then found the task in database
    And The response should have status equals 201
    And apply contract validation

  Scenario: Try register task with status equals CLOSE
    Given that I don't have a task registered
      | title           | description        | status | closedAt |
      | Aula toda terca | Terca fire!!!!     | CLOSE  | 2023-04-26 |
    When I register a task
    Then the task was not found in database
    And The response should have status equals 400