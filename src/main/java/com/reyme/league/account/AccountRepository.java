package com.reyme.league.account;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created by reyme on 6/18/16.
 */
public interface AccountRepository extends JpaRepository<Account, Long> {
    /**
     * Find {@link Account} by {@link Account#username}
     * @param username
     * @return
     */
    Optional<Account> findByUsername(String username);
}
