package nl.hu.cisq1.lingo.trainer.domain;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AccountTest {
    //AccountTest
    //
    //1. Gaat een account aanmaken goed, zitten de juiste waardes erin?
    //2. Zitten de juiste waardes er in na een set?
    //3. Werkt de equals functie?
    //	3.1 Hetzelfde object => goed
    //	3.2 Is null => fout
    //	3.3 andere klasse => fout
    //	3.4 Goede klasse, met goede waardes => goed
    //	3.5 Goede klasse, met foute waardes => fout
    Account account = new Account("Boom", "5678");

    @AfterEach
     public void after() {
        account.setUsername("Boom");
        account.setPassword("5678");
    }

    @Test
    @DisplayName("createEmptyAccountInstance")
    void createEmptyRoundInstance() {
        Account account = new Account();
        assertSame(Account.class, account.getClass());
    }

    @Test
    @DisplayName("Creating account has the right attributes")
    void creatingAccount() {
        Account account = new Account("FS Fons", "1234");
        String username = account.getName();
        String password = account.getPassword();
        assertSame("FS Fons", username);
        assertSame("1234", password);
    }

    @Test
    @DisplayName("Setting attributes works")
    void setAttributes() {
        account.setUsername("Bang");
        account.setPassword("0123");
        assertSame("Bang", account.getName());
        assertSame("0123", account.getPassword());
    }

    @Test
    @DisplayName("Equal function test #1: Equal object")
    void equalObject() {
        assertEquals(account, account);
    }

    @Test
    @DisplayName("Equal function test #2: Null")
    void nullObject() {
        assertNotEquals(account, null);
    }

    @Test
    @DisplayName("Equal function test #3: Object with different class")
    void differentClass() {
        Person person = new Person("yolo01", "1234", "Fons", Role.PLAYER);
        assertNotEquals(account, person);
    }

    @Test
    @DisplayName("Equal function test #4: Object with right class & attribitues")
    void sameAttributesObject() {
        Account account2 = new Account("Boom", "5678");
        assertEquals(account, account2);
    }

    @Test
    @DisplayName("Equal function test #5: Object with right class & wrong attribitues")
    void wrongAttributesObject() {
        Account account2 = new Account("Banzai", "83012");
        assertNotEquals(account, account2);
    }

    @Test
    @DisplayName("Equal function test #6: Object with right class & username but wrong password.")
    void wrongPassword() {
        Account account2 = new Account("Boom", "92405");
        assertNotEquals(account, account2);
    }

    @Test
    @DisplayName("Equal function test #7: Object with right class & password but wrong username.")
    void wrongUsername() {
        Account account2 = new Account("Peter", "5678");
        assertNotEquals(account, account2);
    }
}