
Feature: The start up of the application
  @launchOnRequest
  Scenario: The save file is loaded correctly
    Given the save csv file has the following results
      | place | fire department| time| mistake points | image            |
      | 1     | Feuerwehr 1    | 50  | 10             | test_image_2.jpg |
    When application has started
    Then the result table should look like
      | place | fire department| time| mistake points |
      | 1     | Feuerwehr 1    | 50  | 10             |

#  Scenario: The save file is invalid and the error dialog is visible
#    Given the save csv file is invalid
#    When application has started
#    Then the "load save file error dialog" is visible
#    And the dialog text is "as"

  @launchBefore
  Scenario: Deselect if the row is selected twice
    Given the result table has the following results
      | place | fire department | time| mistake points | final score | image           |
      | 1     | Feuerwehr1      | 50  | 10             | 440         |test_image_2.jpg |
    When the 1. row of the result table is selected
    And again the 1. row of the result table is selected
    Then the 1. row is not selected