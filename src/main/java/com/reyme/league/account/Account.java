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
    private Long id;

    private String username;

    @JsonIgnore
    private String password;

    @JsonIgnore
    @ManyToOne
    private Team team;

    Account() {

    }

    /**
     * Creates an account with username, password and team
     * @param username
     * @param password
     * @param team
     */
    public Account(String username, String password, Team team) {
        this.username = username;
        this.password = password;
        this.team = team;
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

    public void setTeam(Team team) {
        this.team = team;
    }

    public boolean isNew() {
        return getId() == null;
    }
}
