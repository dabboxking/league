package com.reyme.league.team;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by reyme on 6/20/16.
 */

@ResponseStatus(HttpStatus.NOT_FOUND)
class TeamNotFoundException extends RuntimeException {
    public TeamNotFoundException(Long teamId) {
        super("could not find team '" + teamId + "'.");
    }
}
