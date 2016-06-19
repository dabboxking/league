package com.reyme.league.team;

import com.reyme.league.account.Account;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by reyme on 6/18/16.
 */
@Entity
public class Team {

    @Id
    @GeneratedValue
    public Long id;

    public String name;

    @OneToMany
    public Set<Account> accounts = new HashSet<Account>();

    Team() {

    }

    public Team(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }
}
