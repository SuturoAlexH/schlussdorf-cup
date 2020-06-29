Feature: Edit result in result table

  Background:
    Given the user is on the "main page"
    And the result table has the following results
      | place | fire department | time| mistake points | final score|
      | 1     | Feuerwehr 1     | 50  | 10             | 440        |
      | 2     | Feuerwehr 2     | 60  | 20             | 420        |

  Scenario: Edit the fire department
    When edit the "fire department" of the 1. entry to "neue Feuerwehr 1"
    Then the result table should look like
           | place | fire department  | time| mistake points | final score|
           | 1     | neue Feuerwehr 1 | 50  | 10             | 440        |
           | 2     | Feuerwehr 2      | 60  | 20             | 420        |

  Scenario: Edit the time
    When edit the "time" of the 1. entry to "55"
    Then the result table should look like
         | place | fire department | time| mistake points | final score|
         | 1     | Feuerwehr 1     | 55  | 10             | 435        |
         | 2     | Feuerwehr 2     | 60  | 20             | 420        |

  Scenario: Edit the mistake points
    When edit the "mistake points" of the 1. entry to "15"
    Then the result table should look like
      | place | fire department | time| mistake points | final score|
      | 1     | Feuerwehr 1     | 50  | 15             | 435        |
      | 2     | Feuerwehr 2     | 60  | 20             | 420        |

  Scenario: Edit the image
    When edit the image of the 1. entry to "test_image_1.jpeg"
    Then the result table should look like
      | place | fire department | time| mistake points | final score|
      | 1     | Feuerwehr 1     | 50  | 10             | 440        |
      | 2     | Feuerwehr 2     | 60  | 20             | 420        |

  Scenario: Edit the time and the place changed
    When edit the "time" of the 1. entry to "100"
    Then the result table should look like
      | place | fire department | time| mistake points | final score|
      | 1     | Feuerwehr 2     | 60  | 20             | 420        |
      | 2     | Feuerwehr 1     | 100 | 10             | 390        |