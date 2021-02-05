Feature: Guessing a 5-, 6- or 7 letter word

  As a User,

  I want to guess a 5-, 6- or 7 letter word.

  In order to guess a 5-, 6- or 7 letter word in a chain cycle.

  Scenario: A perfectly matching entry is found
    Given I am on the English Wikipedia main page
    When I search for "Socrates"
    Then I should see the entry with the title "Socrates"

Feature: Creating a game

  As a User,

  I want to create a game.

  In order to start guessing a 5-, 6- or 7 letter word.

Feature: Requesting the game state (for the optional other features pauze and continue)

  As a User,

  I want to request the current state of a game.

  In order to pause and continue the game in the future .

Feature: Viewing a round of a game

  As a User,

  I want to view the current round of the game.

  In order to keep progress of the word to guess and if letters are correct or not.

Feature: Guessing a word in a turn of a round

  As a User,

  I want to guess a word in a turn.

  In order to guess a word and play the game properly.

Feature: Remove an User

  As a system administrator or administrator,

  I want to remove a User from the application.

  In order to remove unwanted users.

Feature: Remove an Administrator

  As a system administrator or administrator,

  I want to remove an Administrator.

  In order to protect the system from abusive administrators.