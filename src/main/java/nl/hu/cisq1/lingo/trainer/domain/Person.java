package nl.hu.cisq1.lingo.trainer.domain;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "person")
public class Person {

    @Id
    @GeneratedValue(generator = "person_person_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(
            name = "person_person_id_seq",
            sequenceName = "person_person_id_seq",
            allocationSize = 50)
    @Column(name = "person_id")
    private int id;

    @Column(name = "person_name")
    private String name;

    @JoinColumn(name = "account_id")
    @OneToOne
    private Account account;

    @Column(name = "person_role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "person")
    private Set<Game> games = new LinkedHashSet<>();

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

    public int getId() {
        return id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setId(int id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", account=" + account +
                ", role=" + role +
                '}';
    }
}