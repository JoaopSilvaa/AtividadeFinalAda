Feature: List tasks

  Scenario: I have registered task
    Given I have task registered
      | title             | description       |
      | Aula toda segunda | Segunda fire!!!!  |
    When I search the task by id
    Then I should found "Aula toda segunda" task
    And The response should have status equals 200
    And apply contract validation on list

  Scenario: I don't have task registered
    Given that I don't have a task registered
      | title            | description         |
      | Aula toda quarta | Quarta fire!!!!     |
    When I search the task by id
    Then I shouldn't found a task
    And The response should have status equals 404