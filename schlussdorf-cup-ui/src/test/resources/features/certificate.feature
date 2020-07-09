@launchBefore
Feature: Create certificate documents

  Background:
    Given the user is on the "main page"
    Given the result table has the following results
      | place | fire department | time| mistake points | final score | image           |
      | 1     | Feuerwehr1      | 50  | 10             | 440         |test_image_2.jpg |
      | 2     | Feuerwehr2      | 60  | 10             | 430         |test_image_2.jpg |

  Scenario: After the application has launched the certificate button should be enabled
    Then the "certificate button" is enabled

  Scenario: When the certificate button is pressed the certificate progress dialog should be visible
    When click on "certificate button"
    Then the "certificate progress dialog" is visible

  Scenario: After the certificate progress dialog has finished the dialog should be closed
    Given the certificate progress dialog has passed successfully
    Then the "certificate progress dialog" is invisible

  Scenario: After the certificate progress dialog has finished successfully the certificate success dialog is visible
    Given the certificate progress dialog has passed successfully
    Then the "certificate success dialog" is visible
    And the dialog text starts with "Urkunden wurden erfolgreich erzeugt im Ordner"

  Scenario: After the certificate progress dialog has finished unsuccessfully the certificate fail dialog is visible
    Given "Feuerwehr1" has no image
    And the certificate progress dialog has passed unsuccessfully
    Then the "certificate fail dialog" is visible
    And the dialog text is "Das Erzeugen der Urkunde für Feuerwehr1 ist fehlgeschlagen"

  Scenario: After the certificate progress dialog has finished a certificate folder should exists
    Given the certificate progress dialog has passed successfully
    Then the certificate folder exists

  Scenario: After the certificate progress dialog has finished the certificates should exists
    Given the certificate progress dialog has passed successfully
    Then a certificate for "Feuerwehr1" has bean created
    And a certificate for "Feuerwehr2" has bean created

  Scenario: After the certificate progress dialog has finished the certificates pdf should exists
    Given the certificate progress dialog has passed successfully
    Then the certificates pdf has bean created

  Scenario: After the certificate progress dialog has finished the summary should exists
    Given the certificate progress dialog has passed successfully
    Then the certificate summary has bean created