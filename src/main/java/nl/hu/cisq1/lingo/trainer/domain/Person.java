package nl.hu.cisq1.lingo.trainer.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "person")
public class Person {

    @Id
    @GeneratedValue
    @Column(name = "person_id")
    private long id;

    @Column(name = "person_name")
    private String name;

    //Komt account_id hier (in deze column) in of niet? Ja, is primary key van account!
    @Column(name = "account_id")
    @OneToOne
    private Account account;

    @Column(name = "person_role")
    private Role role;

    @OneToMany(mappedBy = "person")
    private Set<Game> games = new HashSet<>();

    public Person() {
    }

    public Person(String username, String password, String name, Role role) {
        this.account = new Account(username, password);
        this.name = name;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Account getAccount() {
        return account;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean addGame(Game game) {
        return this.games.add(game);
    }

    public Set<Game> getGames() {
        return games;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return name.equals(person.name) && account.equals(person.account) && role == person.role;
    }
}