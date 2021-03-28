package nl.hu.cisq1.lingo.trainer.presentation.dto;

import nl.hu.cisq1.lingo.trainer.domain.*;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

public class PersonDTO {
    private int id;
    private String name;
    private Account account;
    private Role role;

    public PersonDTO(int id, String name, Account account, Role role) {
        this.id = id;
        this.name = name;
        this.account = account;
        this.role = role;
    }

    public static PersonDTO createPersonDTO(Person person) {
        PersonDTO personDTO = new PersonDTO(person.getId(), person.getName(), person.getAccount(), person.getRole());
        return personDTO;
    }

    public String getName() {
        return name;
    }

    public Account getAccount() {
        return account;
    }

    public int getId() {
        return id;
    }

    public Role getRole() {
        return role;
    }
}