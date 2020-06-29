Feature: Delete result from result table

  Background:
    Given the user is on the "main page"
    And the result table has the following results
      | place | fire department| time| mistake points |
      | 1     | Feuerwehr 1    | 50  | 10             |
      | 2     | Feuerwehr 2    | 60  | 20             |

  Scenario: If no result is selected the delete button is disabled
    Then the "delete button" is disabled

  Scenario: If one result is selected and the delete button is clicked the delete dialog is visible
    Given the 1. row of the result table is selected
    When click on "delete button"
    Then the "delete dialog" is visible

  Scenario: The delete dialog should display the correct text
    Given try to delete the 1. row
    Then the dialog text is "Soll das Ergebnis der Feuerwehr Feuerwehr 1 wirklich gelöscht werden?"

  Scenario: If the delete dialog is confirmed the result should be deleted and the dialog is invisible
    When delete the 1. result
    Then the result table should look like
      | place | fire department| time| mistake points |
      | 1     | Feuerwehr 2    | 60  | 20             |
    And the "delete dialog" is invisible

  Scenario: If the delete dialog is canceled the result table shouldn't changed and the dialog is invisible
    When try to delete the 1. row
    And click on "no button"
    Then the result table should look like
      | place | fire department| time| mistake points |
      | 1     | Feuerwehr 1    | 50  | 10             |
      | 2     | Feuerwehr 2    | 60  | 20             |
    And the "delete dialog" is invisible

  Scenario: Delete two results one after another
    When delete the 1. result
    And delete the 1. result
    Then the result table should look like
      | place | fire department| time| mistake points |