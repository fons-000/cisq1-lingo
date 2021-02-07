Feature: Lingo Trainer

  As a player, administrator or head administrator
  I want to guess 5, 6 and 7 letter words
  In order to prepare for Lingo

  Scenario: Start new game
    When I start a new game
    Then the word to guess has "5" letters
    And I should see the first letter
    And My score is "0"

  Scenario: Requesting the game state
    Given I am playing a game
    And I press pause
    When I press resume
    Then the game continues at it's current state

  Scenario Outline: Guessing a word
    Given I am playing a game
    And I'm in a round
    When I type a word to <guess> and hit enter
    Then The <word>, <hint>, <guess> and <feedback> will be shown in the round overview

    Examples:

      | word | hint | guess | feedback
      | BAARD | ‘B’, ‘.’, ‘.’, ‘.’, ‘.’ | BERGEN | INVALID, INVALID, INVALID, INVALID, INVALID, INVALID |
      | BAARD | ‘B’, ‘.’, ‘.’, ‘.’, ‘.’ | BONJE | CORRECT, ABSENT, ABSENT, ABSENT, ABSENT |
      | BAARD | ‘B’, ‘.’, ‘.’, ‘.’, ‘.’ | BARST | CORRECT, CORRECT, PRESENT, ABSENT, ABSENT |
      | BAARD | ‘B’, ‘A’, ‘.’, ‘.’, ‘.’ | DRAAD | ABSENT, PRESENT, CORRECT, PRESENT, CORRECT |
      | BAARD | ‘B’, ‘A’, ‘A’, ‘.’, ‘D’ | BAARD | CORRECT, CORRECT, CORRECT, CORRECT, CORRECT |

    #Sucess path
    Given The word has been guessed in five turns
    Then My score will get higher for the game, regarding on how well I did in the round

    # Failure path
    Given I am playing a game
    And the round was lost
    Then I cannot take another guess nor can I start a new round

  Scenario: Game round overview
    Given I am playing a game
    And I made a turn
    Then I want to view all the curent turns of the round in an information tabel

  Scenario Outline: Start a new round
    Given I am playing a game
    And the round was won
    And the last word had "<previous length>" letters
    When I start a new round
    Then the word to guess has "<next length>" letters and the word hasn't been guessed in the game yet

    Examples:
      | previous length | next length |
      | 5               | 6           |
      | 6               | 7           |
      | 7               | 5           |

    # Failure path
    Given I am playing a game
    And the round was lost
    Then I cannot start a new round

  Scenario: Look at your played games
    Given I am on the games page
    And I want to look at a game I played
    When I press a game I played
    Then I get an overview of all the rounds I've played and I can see the total game score
    When I click on a round, I can see the game round overview

  Scenario: Total game score
    Given I just lost a round in a game
    Then My total score will be shown on the screen
    And My total score will be stored in the game, and can be looked at on the games page => specific game

Feature: Lingo Trainer Security

  As an head administrator and administrator

  I want to be able to search and remove users with less authority

  In order to keep the application secure

  Scenario: Search an user
    Given I'm on the users page of the application
    And I want to look an user up
    When I go to the search bar, type in the name of the user and press enter
    Then I should get an entry with the users name

  Scenario: Remove a player
    Given I'm on the users page of the application
    And I want to remove a player for misconduct
    Then I search the player I want to remove
    When I found an entry of the player I want to remove
    Then I press the remove button besides the player and click on enter

  Scenario: Remove an administrator
    Given I'm the head administrator and I'm on the users page of the application
    And I want to remove an administrator
    Then I search for the administrator I want to remove
    When I found an entry of the administrator I want to remove
    Then I press the remove button besides the administrator and click on enter

  Scenario: Add an administrator
    Given I'm the head administrator and I'm on the users page of the application
    And I want to add an administrator
    Then I search for the player I want to make administrator
    When I found an entry of the player I want to make administrator
    Then I press the add administrator button besides the player and click on enter