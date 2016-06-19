package com.reyme.league.team;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by reyme on 6/18/16.
 */
@RestController
@RequestMapping("/teams")
public class TeamRestController {

    private final TeamRepository teamRepository;

    @Autowired
    TeamRestController(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }


}
