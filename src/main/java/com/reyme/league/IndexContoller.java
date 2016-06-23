package com.reyme.league;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by reyme on 6/22/16.
 */
@Controller
public class IndexContoller {

    private static final String FORWARD = "forward:/";

    /**
     * Handles request with the following path
     * @return
     */
    @RequestMapping(value = "/{[path:[^\\.]*}")
    public String redirect() {
        return FORWARD;
    }
}
