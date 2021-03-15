package nl.hu.cisq1.lingo.trainer.domain;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Set;

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
    //  3.4 werkt onderstaand tabel ook met equals?
    //Equaltesting attributes
    //
    //1. Name object = name, account object = account en role object = role. = true
    //
    //2. Name object = name, account object = account en role object != role. = false
    //
    //3. Name object = name, account object != account en role object = role.
    //
    //3.1 wrong username = false
    //3.2 wrong password = false
    //3.3 wrong both = false
    //
    //4. Name object = name, account object != account en role object != role.
    //4.1 wrong username = false
    //4.2 wrong password = false
    //4.3 wrong both = false
    //
    //5. Name object != name, account object = account en role object = role. = false
    //6. Name object != name, account object = account en role object != role. = false
    //
    //7. Name object != name, account object != account en role object = role
    //7.1 wrong username = false
    //7.2 wrong password = false
    //7.3 wrong both = false
    //
    //8. Name object != name, account object != account en role object != role.
    //8.1 wrong username = false
    //8.2 wrong password = false
    //8.3 wrong both = false
    //
    //(In total: 16 equal tests)


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
    @DisplayName("createEmptyPersonInstance")
    void createEmptyPersonInstance() {
        Person person = new Person();
        assertSame(Person.class, person.getClass());
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
    @DisplayName("Equal function test #4.1: Object with right class & attribitues")
    void sameAttributesObject() {
        Person person2 = new Person("FS Fons", "8743", "Fons", Role.PLAYER);
        assertEquals(person, person2);
    }

    @Test
    @DisplayName("Equal function test #4.2: Object with right class & wrong attribitues (username)")
    void wrongAttributesObject() {
        Person person2 = new Person("FS FonsAnders", "8743", "Wazza", Role.ADMINISTRATOR);
        assertNotEquals(person, person2);
    }

    @Test
    @DisplayName("Equal function test #4.3: Object with right class & wrong attribitues (password)")
    void wrongAttributes1bject() {
        Person person2 = new Person("FS Fons", "2895hg", "Wazza", Role.ADMINISTRATOR);
        assertNotEquals(person, person2);
    }

    @Test
    @DisplayName("Equal function test #4.4: Object with right class & wrong attribitues")
    void wrongAttributes2bject() {
        Person person2 = new Person("Yoboy001", "2895hg", "Wazza", Role.ADMINISTRATOR);
        assertNotEquals(person, person2);
    }

    @Test
    @DisplayName("Equal function test #4.5: Object with right class, name and account but wrong role")
    void wrongRoleAttribute() {
        Person person2 = new Person("FS Fons", "8743", "Fons", Role.ADMINISTRATOR);
        assertNotEquals(person, person2);
    }

    @Test
    @DisplayName("Equal function test #4.6: Object with right class, name and role but wrong account (username)")
    void wrongAccountUsernameAttribute() {
        Person person2 = new Person("FS FonsYo", "8743", "Fons", Role.PLAYER);
        assertNotEquals(person, person2);
    }

    @Test
    @DisplayName("Equal function test #4.7: Object with right class, name and role but wrong account (password)")
    void wrongAccountPasswordAttribute() {
        Person person2 = new Person("FS Fons", "174394", "Fons", Role.PLAYER);
        assertNotEquals(person, person2);
    }

    @Test
    @DisplayName("Equal function test #4.8: Object with right class, name and role but wrong account attributes")
    void wrongAccountAttributes() {
        Person person2 = new Person("FS FonsOP", "13f950", "Fons", Role.PLAYER);
        assertNotEquals(person, person2);
    }

    @Test
    @DisplayName("Equal function test #4.9: Object with right class and name attribute but wrong role & account attributes (username)")
    void wrongUsername1Attributes() {
        Person person2 = new Person("FS FonsOP", "8743", "Fons", Role.HEAD_ADMINISTRATOR);
        assertNotEquals(person, person2);
    }

    @Test
    @DisplayName("Equal function test #4.10: Object with right class and name attribute but wrong role & account attributes (password)")
    void wrongPassword1Attributes() {
        Person person2 = new Person("FS Fons", "13f950", "Fons", Role.HEAD_ADMINISTRATOR);
        assertNotEquals(person, person2);
    }

    @Test
    @DisplayName("Equal function test #4.11: Object with right class and name attribute but wrong role & account attributes")
    void wrongAccount1Attributes() {
        Person person2 = new Person("FS FonsOP", "13f950", "Fons", Role.HEAD_ADMINISTRATOR);
        assertNotEquals(person, person2);
    }

    @Test
    @DisplayName("Equal function test #4.12: Object with right class, role and account but wrong name")
    void wrongAccount1UsernameAttribute() {
        Person person2 = new Person("FS Fons", "8743", "Jan", Role.PLAYER);
        assertNotEquals(person, person2);
    }

    @Test
    @DisplayName("Equal function test #4.13: Object with right class, and account but wrong role & name")
    void wrongAccount1PasswordAttribute() {
        Person person2 = new Person("FS Fons", "8743", "Pan", Role.ADMINISTRATOR);
        assertNotEquals(person, person2);
    }

    @Test
    @DisplayName("Equal function test #4.14: Object with right class and role attribute but wrong name & account attributes (username)")
    void wrongAccount2Attributes() {
        Person person2 = new Person("FS FonsOP", "8743", "YoBroPublicName", Role.PLAYER);
        assertNotEquals(person, person2);
    }

    @Test
    @DisplayName("Equal function test #4.15: Object with right class and role attribute but wrong name & account attributes (password)")
    void wrongAccount2UsernameAttribute() {
        Person person2 = new Person("FS Fons", "ogk8401", "Yikes", Role.PLAYER);
        assertNotEquals(person, person2);
    }

    @Test
    @DisplayName("Equal function test #4.16: Object with right class and role attribute but wrong name & account attributes")
    void wrongAccount2PasswordAttribute() {
        Person person2 = new Person("FS FonsBaha", "02lig", "Shizzle", Role.PLAYER);
        assertNotEquals(person, person2);
    }

    @Test
    @DisplayName("Addgame function works")
    void addGame() {
        Set<Game> gamesList = person.getGames();
        assertEquals(0, gamesList.size());
        Game game = new Game();
        assertTrue(person.addGame(game));
        assertEquals(1, gamesList.size());
        ArrayList<Game> gamesArrayList = new ArrayList<>(gamesList);
        assertEquals(100, gamesArrayList.get(0).getScore());
    }
}