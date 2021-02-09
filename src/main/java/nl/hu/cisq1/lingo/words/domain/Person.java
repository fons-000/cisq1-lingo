package nl.hu.cisq1.lingo.words.domain;

import java.util.ArrayList;

public class Person {
    private String name;
    private Account account;
    private Role role;
    private ArrayList<Game> games = new ArrayList<Game>();

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

    public ArrayList<Game> getGames() {
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