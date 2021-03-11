DROP TABLE IF EXISTS account CASCADE;
DROP TABLE IF EXISTS person CASCADE;
DROP TABLE IF EXISTS game CASCADE;
DROP TABLE IF EXISTS round CASCADE;
DROP TABLE IF EXISTS turn CASCADE;

CREATE TABLE account (
    account_id NUMERIC(2, 0) PRIMARY KEY,
    username VARCHAR(20),
    account_password VARCHAR(100)
);

CREATE TABLE person (
    person_name VARCHAR(1000) PRIMARY KEY,
    account_id NUMERIC(2, 0),
    person_role VARCHAR(20),
    CONSTRAINT fk_account_id
        FOREIGN KEY(account_id)
            REFERENCES account(account_id)
);

CREATE TABLE game (
    game_id NUMERIC(2, 0) PRIMARY KEY,
    person_name VARCHAR(1000),
    score NUMERIC(5, 0),
    CONSTRAINT fk_person_name
        FOREIGN KEY(person_name)
            REFERENCES person(person_name)
);

CREATE TABLE round (
    round_game NUMERIC(3, 0),
    game_id NUMERIC(2, 0),
    word VARCHAR(10),
    first_hint VARCHAR(10),
    PRIMARY KEY(round_game, game_id),
    CONSTRAINT fk_word
        FOREIGN KEY(word)
            REFERENCES words(word),
    CONSTRAINT fk_first_hint
        FOREIGN KEY(first_hint)
            REFERENCES words(word)
);

CREATE TABLE turn (
    turn_round NUMERIC(1, 0),
    round_game NUMERIC(3, 0),
    game_id NUMERIC(2, 0),
    turn_guess VARCHAR(10),
    turn_hint VARCHAR(10),
    PRIMARY KEY(turn_round, round_game, game_id),
    CONSTRAINT fk_round_game_game_id
        FOREIGN KEY(round_game, game_id)
            REFERENCES round(round_game, game_id),
    CONSTRAINT fk_turn_guess
        FOREIGN KEY(turn_guess)
            REFERENCES words(word),
    CONSTRAINT fk_turn_hint
        FOREIGN KEY(turn_hint)
            REFERENCES words(word)
);