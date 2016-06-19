package com.reyme.league.team;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by reyme on 6/18/16.
 */
public interface TeamRepository extends JpaRepository<Team, Long> {

    Team findByName(String name);
}
