package com.reyme.league.team;

import com.reyme.league.account.Account;
import com.reyme.league.account.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Collection;

/**
 * Created by reyme on 6/18/16.
 */
@RestController
@RequestMapping("/teams")
public class TeamRestController {

    private final AccountRepository accountRepository;

    private final TeamRepository teamRepository;

    @Autowired
    TeamRestController(AccountRepository accountRepository, TeamRepository teamRepository) {
        this.accountRepository = accountRepository;
        this.teamRepository = teamRepository;
    }

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<?> addTeam(@RequestBody Team input) {
        Team result = teamRepository.save(new Team(input.getName()));
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(result.getId()).toUri());
        return new ResponseEntity<>(null, httpHeaders, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET)
    Collection<Team> readTeams() {
        return this.teamRepository.findAll();
    }

    @RequestMapping(value = "/{teamId}", method = RequestMethod.GET)
    Team readTeam(@PathVariable Long teamId) {
        validateTeam(teamId);
        return this.teamRepository.findOne(teamId);
    }

    @RequestMapping(value = "/{teamId}/accounts", method = RequestMethod.GET)
    Collection<Account> readTeamMembers(@PathVariable Long teamId) {
        validateTeam(teamId);
        return this.teamRepository.findOne(teamId).getAccounts();
    }

    @RequestMapping(value = "/{teamId}/accounts/{accountId}", method = RequestMethod.POST)
    void addTeamMember(@PathVariable Long teamId, @PathVariable Long accountId) {
        validateTeam(teamId);
        Team team = this.teamRepository.findOne(teamId);
        Account account = this.accountRepository.findOne(accountId);
        account.setTeam(team);
        this.accountRepository.save(account);
    }

    @RequestMapping(value = "/{teamId}/accounts/{accountId}", method = RequestMethod.DELETE)
    void removeTeamMember(@PathVariable Long teamId, @PathVariable Long accountId) {
        validateTeam(teamId);
        Account account = this.accountRepository.findOne(accountId);
        account.setTeam(null);
        this.accountRepository.save(account);
    }

    private void validateTeam(Long teamId) {
        if(this.teamRepository.findOne(teamId) == null) {
            throw new TeamNotFoundException(teamId);
        }
    }
}

@ResponseStatus(HttpStatus.NOT_FOUND)
class TeamNotFoundException extends RuntimeException {
    public TeamNotFoundException(Long teamId) {
        super("could not find user '" + teamId + "'.");
    }
}
