package com.reyme.league.account;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.reyme.league.team.Team;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * Created by reyme on 6/18/16.
 */
@Entity
public class Account {

    @Id
    @GeneratedValue
    public Long id;

    public String username;

    @JsonIgnore
    public String password;

    @ManyToOne
    public Team team;

    Account() {

    }

    public Account(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Long getId() {
        return id;
    }

    public Team getTeam() { return team; }
}
