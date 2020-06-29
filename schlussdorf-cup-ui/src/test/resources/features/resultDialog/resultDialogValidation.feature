Feature: Result dialog initial appearance

  Background:
    Given result dialog is open

  Scenario Outline: Validate initial text fields
    Given the <text field> has text <input>
    When click on "apply button"
    Then the <error text> is <is visible>
    Examples:
      | text field                   | input       | error text                    | is visible |
      | "fire department text field" | " "         | "fire department error label" | visible    |
      | "fire department text field" | "Feuerwehr" | "fire department error label" | invisible  |
      | "time text field"            |  " "        | "time error label"            | visible    |
      | "time text field"            |  "1a"       | "time error label"            | visible    |
      | "time text field"            |  "10,4"     | "time error label"            | invisible  |
      | "time text field"            |  "10.4"     | "time error label"            | invisible  |
      | "mistake points text field"  |  " "        | "mistake points error label"  | invisible  |
      | "mistake points text field"  |  "1a"       | "mistake points error label"  | visible    |
      | "mistake points text field"  |  "10,4"     | "mistake points error label"  | visible    |
      | "mistake points text field"  |  "10.4"     | "mistake points error label"  | visible    |
      | "mistake points text field"  |  "10"       | "mistake points error label"  | invisible  |

#  Scenario Outline: Invalid to valid text field input
#    Given the <text field> has text <invalid input>
#    And Write <valid input> to <text field>
#    And click on "apply button"
#    Then the <error text> is invisible
#    Examples:
#      | text field                   | invalid input | valid input | error text                    |
#      | "fire department text field" | " "           | "Feuerwehr" | "fire department error label" |
#      | "time text field"            | "1a"          | "10.4"      | "time error label"            |
#      | "time text field"            | "1a"          | "10,4"      | "time error label"            |
#      | "mistake points text field"  | "1a"          | " "         | "mistake points error label"  |
#      | "mistake points text field"  | "10,4"        | "10"        | "mistake points error label"  |
#
#  Scenario Outline: Valid to invalid text field input
#    Given the <text field> has text <valid input>
#    When Write <invalid input> to <text field>
#    And click on "apply button"
#    Then the <error text> is visible
#    Examples:
#      | text field              | valid input | invalid input | error text              |
#      | "fireDepartmentTextField" | "Feuerwehr" | " "           |"fireDepartmentErrorLabel" |
#      | "timeTextField"           | "10.4"      | "aa"          |"timeErrorLabel"           |
#      | "timeTextField"           | "10,4"      | "aa"          |"timeErrorLabel"           |
#      | "timeTextField"           | "10,4"      | ""            |"timeErrorLabel"           |
#      | "mistakePointsTextField"  | " "         | "aa"          |"mistakePointsErrorLabel"  |
#      | "mistakePointsTextField"  | "10"        | "10,4"        |"mistakePointsErrorLabel"  |

  Scenario: When no image is selected the error text is visible
    When click on "apply button"
    Then the "image error label" is visible

  Scenario: When image is selected the error text is invisible
    Given image "test_image_1.jpeg" is selected
    When click on "apply button"
    Then the "image error label" is invisible
