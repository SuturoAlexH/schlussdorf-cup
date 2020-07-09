@launchAfter
Feature: The start up of the application

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