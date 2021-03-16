package nl.hu.cisq1.lingo.trainer.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.security.Principal;

@Entity
@Table(name = "account")
public class Account implements Serializable, Principal {
    @Id
    @GeneratedValue
    @Column(name = "account_id")
    private int id;

    @OneToOne(mappedBy = "account")
    private Person person;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "account_password")
    private String password;

    public Account() {
    }

    public Account(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return username.equals(account.username) && password.equals(account.password);
    }

    @Override
    public String getName() {
        return username;
    }

    public int getId() {
        return id;
    }
}