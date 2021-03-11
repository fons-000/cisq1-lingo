package nl.hu.cisq1.lingo.trainer.domain;

import java.io.Serializable;
import java.security.Principal;

public class Account implements Serializable, Principal {
    private String username;
    private String password;

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
}