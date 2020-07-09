@normal
Feature: Show image of result in result table

  Background:
    Given the user is on the "main page"
    And the result table has the following results
      | place | fire department| time| mistake points | image            |
      | 1     | Feuerwehr 1    | 50  | 10             | test_image_2.jpg |

  Scenario: After the application has launched the show image button is disabled
    Then the "show image button" is disabled

  Scenario: If a result table row is selected the show image button is enabled
    Given the 1. row of the result table is selected
    When click on "show image button"
    Then the "show image dialog" is visible

  Scenario: If the image dialog is visible the image of the corresponding result is shown
    Given the show image dialog is opened
    Then the show image dialog shows "test_image_2.jpg"

  Scenario: If the close button is pressed the image dialog is invisible
    Given the show image dialog is opened
    When click on "close button"
    Then the "show image dialog" is invisible

  Scenario: Error dialog is visible if image does not exists
    Given "Feuerwehr 1" has no image
    And the 1. row of the result table is selected
    When click on "show image button"
    Then the "show image error dialog" is visible
    And the dialog text is "Das Bild für die Ortsfeuerwehr Feuerwehr 1 konnte leider nicht geladen werden!"