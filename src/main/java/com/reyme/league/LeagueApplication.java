package com.reyme.league;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


/**
 * Main entry point
 */
@SpringBootApplication
@Controller
public class LeagueApplication {

	@RequestMapping(value = "/{[path:[^\\.]*}")
	public String redirect() {
		return "forward:/";
	}

	/**
	 *
	 * @param args
     */
	public static void main(String[] args) {
		SpringApplication.run(LeagueApplication.class, args);
	}
}
