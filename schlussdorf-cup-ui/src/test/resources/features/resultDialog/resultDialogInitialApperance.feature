@normal
Feature: The initial appearance of the result dialog

  Background:
    Given result dialog is open

  Scenario: The fire department text field is focused
    Then the "fire department text field" is focused

  Scenario Outline: The text fields should be empty and the error texts are invisible
    Then The <text field> is empty
    And the <error text> is invisible
    Examples:
      | text field                   | error text                    |
      | "fire department text field" | "fire department error label" |
      | "time text field"            | "time error label"            |
      | "mistake points text field"  | "mistake points error label"  |

  Scenario: The image view is empty and the image error text is invisible
    Then the image view is empty
    And the "image error label" is invisible