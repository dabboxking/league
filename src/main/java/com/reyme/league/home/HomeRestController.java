package com.reyme.league.home;

import com.reyme.league.account.Account;
import com.reyme.league.account.AccountRepository;
import com.reyme.league.team.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by reyme on 6/19/16.
 */
@RestController
public class HomeRestController {

    final AccountRepository accountRepository;
    final TeamRepository teamRepository;

    /**
     * {@link AccountRepository} coustructor
     * @param accountRepository
     * @param teamRepository
     */
    @Autowired
    public HomeRestController(AccountRepository accountRepository, TeamRepository teamRepository) {
        this.accountRepository = accountRepository;
        this.teamRepository = teamRepository;
    }

    @RequestMapping("/user")
    public Principal user(Principal user) {
        return user;
    }

    private void validate(Principal principal) {
        if (principal == null) {
            throw new HomeException();
        }
    }
}
