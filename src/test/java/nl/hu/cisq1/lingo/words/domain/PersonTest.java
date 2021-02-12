package nl.hu.cisq1.lingo.words.domain;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class PersonTest {
    //PersonTest
    //
    //1. Gaat een persoon aanmaken goed, zitten de juiste waardes erin?
    //2. Zitten de juiste waardes er in na een set?
    //3. Werkt de equals functie?
    //	3.1 Hetzelfde object => goed
    //	3.2 Is null => fout
    //	3.3 andere klasse => fout
    //	3.4 Goede klasse, met goede waardes => goed
    //	3.5 Goede klasse, met foute waardes => fout
    //4. Werkt de addGame functie?
    Person person = new Person("FS Fons", "8743", "Fons", Role.PLAYER);

    @AfterEach
    public void after() {
        person.setName("Fons");
        person.setRole(Role.PLAYER);
    }

    @Test
    @DisplayName("Creating Person has the right attributes")
    void creatingPerson() {
        Person person = new Person("ThePunisher", "9473", "Winnie the Pooh", Role.PLAYER);
        String name = person.getName();
        Account account = person.getAccount();
        String password = account.getPassword();
        String username = account.getName();
        Role role = person.getRole();
        assertSame("Winnie the Pooh", name);
        assertSame("9473", password);
        assertSame("ThePunisher", username);
        assertSame(Role.PLAYER, role);
    }

    @Test
    @DisplayName("Setting attributes works")
    void setAttributes() {
        person.setName("Ruben");
        person.setRole(Role.ADMINISTRATOR);
        assertSame("Ruben", person.getName());
        assertSame(Role.ADMINISTRATOR, person.getRole());
        person.setName("Casper");
        person.setRole(Role.HEAD_ADMINISTRATOR);
    }

    @Test
    @DisplayName("Equal function test #1: Equal object")
    void equalObject() {
        assertEquals(person, person);
    }

    @Test
    @DisplayName("Equal function test #2: Null")
    void nullObject() {
        assertNotEquals(person, null);
    }

    @Test
    @DisplayName("Equal function test #3: Object with different class")
    void differentClass() {
        Account account = new Account("yolo01", "1234");
        assertNotEquals(person, account);
    }

    @Test
    @DisplayName("Equal function test #4: Object with right class & attribitues")
    void sameAttributesObject() {
        Person person2 = new Person("FS Fons", "8743", "Fons", Role.PLAYER);
        assertEquals(person, person2);
    }

    @Test
    @DisplayName("Equal function test #5: Object with right class & wrong attribitues")
    void wrongAttributesObject() {
        Person person2 = new Person("FS FonsAnders", "8743", "Fons", Role.PLAYER);
        assertNotEquals(person, person2);
    }

    @Test
    @DisplayName("Addgame function works")
    void addGame() {
        ArrayList<Game> gamesList = person.getGames();
        assertEquals(0, gamesList.size());
        Game game = new Game(200);
        assertTrue(person.addGame(game));
        assertEquals(1, gamesList.size());
        assertEquals(200, gamesList.get(0).getScore());
    }
}