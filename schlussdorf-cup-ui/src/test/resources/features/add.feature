@normal
Feature: Add result to result table

  Background:
    Given the result table is empty

  Scenario: Add one result to the result table
      When add the following result to the table
        | fire department | time | mistake points | image           |
        | Feuerwehr 1     | 20   | 10             | test_image_2.jpg |
      Then the result table should look like
        | place | fire department | time | mistake points | final score |
        |  1    | Feuerwehr 1     | 20.0 | 10             | 470.0       |


  Scenario: Add two results to the result table
    When add the following result to the table
      | fire department | time | mistake points | image           |
      | Feuerwehr 1     | 66.5 | 0              | test_image_2.jpg |
      | Feuerwehr 2     | 55.1 | 10             | test_image_2.jpg |
    Then the result table should look like
      | place | fire department | time | mistake points | final score |
      |  1    | Feuerwehr 2     | 55.1 | 10             | 434.9       |
      |  2    | Feuerwehr 1     | 66.5 | 0              | 433.5       |


  Scenario: Add three results to the result table
    When add the following result to the table
      | fire department | time | mistake points | image           |
      | Feuerwehr 1     | 66.5 | 0              | test_image_2.jpg |
      | Feuerwehr 2     | 55.1 | 10             | test_image_2.jpg |
      | Feuerwehr 3     | 80.4 | 20             | test_image_2.jpg |
    Then the result table should look like
      | place | fire department | time | mistake points | final score |
      |  1    | Feuerwehr 2     | 55.1 | 10             | 434.9       |
      |  2    | Feuerwehr 1     | 66.5 | 0              | 433.5       |
      |  3    | Feuerwehr 3     | 80.4 | 20             | 399.6       |