package com.reyme.league.account;

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
@RequestMapping("/accounts")
public class AccountRestController {

    private final AccountRepository accountRepository;

    @Autowired
    AccountRestController(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<?> addAccount(@RequestBody Account input) {
        Account result = accountRepository.save(new Account(input.getUsername(), input.getPassword(), input.getTeam()));
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(result.getId()).toUri());
        return new ResponseEntity<>(null,httpHeaders, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET)
    Collection<Account> readAccounts() {
        return this.accountRepository.findAll();
    }

    @RequestMapping(value = "/{username}", method = RequestMethod.GET)
    Account readAccount(@PathVariable String username) {
        validateAccount(username);
        return this.accountRepository.findByUsername(username).get();
    }

    private void validateAccount(String username) {
        this.accountRepository.findByUsername(username).orElseThrow(
                () -> new UserNotFoundException(username)
        );
    }
}


