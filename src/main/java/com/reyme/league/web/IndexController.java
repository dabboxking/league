package com.reyme.league.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by reyme on 6/18/16.
 */
@Controller
public class IndexController {

    @RequestMapping("/")
    public String index() {
        return "hello";
    }
}
