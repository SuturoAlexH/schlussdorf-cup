@launchBefore
  Feature: General application features

    Background:
      Given the user is on the "main page"

    Scenario: The close application dialog is visible if the application shut down
      When try to close the application
      Then the "close application dialog" is visible

    Scenario: The application is terminated if the close application dialog is accepted
      Given the close application dialog is opened
      When click on "yes button"
      Then the application is terminated

    Scenario: The application is still running if the close application dialog is denied
      Given the close application dialog is opened
      When click on "no button"
      Then the application is still running

