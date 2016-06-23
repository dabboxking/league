package com.reyme.league.home;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by reyme on 6/20/16.
 */
@ResponseStatus(HttpStatus.UNAUTHORIZED)
class HomeException extends RuntimeException {

    public HomeException() {
        super("user is not authorized");
    }
}
