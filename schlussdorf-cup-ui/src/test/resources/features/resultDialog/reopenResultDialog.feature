@launchBefore
Feature: The result dialog should be clear if its reopened

  Background:
    Given result dialog is open

  Scenario Outline: The text fields and error texts should be cleared
    Given the <text field> has text <input>
    And the "apply button" has bean clicked
    When reopen the result dialog
    Then The <text field> is empty
    And the <error text> is invisible

    Examples:
    | text field                   | input | error text                    |
    | "fire department text field" | " "   | "fire department error label" |
    | "time text field"            | " "   | "time error label"            |
    | "mistake points text field"  | "a"   | "mistake points error label"  |

  Scenario: The image and the error text should be cleared
    Given image "test_image_1.jpeg" is selected
    And the "apply button" has bean clicked
    When reopen the result dialog
    Then the image view is empty
    And the "image error label" is invisible