package com.resmed.challenge.controller;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.resmed.challenge.model.statistics.MatchStatus;
import com.resmed.challenge.model.statistics.Statistics;
import com.resmed.challenge.model.user.User;
import com.resmed.challenge.model.user.UserRepository;
import com.resmed.challenge.model.user.UserStatus;

import java.util.HashMap;

import javax.validation.Valid;

@RestController
@SuppressWarnings("unchecked")
public class UserController {

	@Autowired
	UserRepository userRepository;

	private Hands[][] rules;

	public UserController() {
		// TODO Auto-generated constructor stub
		rules = new Hands[3][3];
		rules[0][0] = Hands.ROCK;
		rules[0][1] = Hands.PAPER;
		rules[0][2] = Hands.ROCK;

		rules[1][0] = Hands.PAPER;
		rules[1][1] = Hands.PAPER;
		rules[1][2] = Hands.SCISSOR;

		rules[2][0] = Hands.ROCK;
		rules[2][1] = Hands.SCISSOR;
		rules[2][2] = Hands.SCISSOR;
	}

	@PostMapping("api/users/register")
	public UserStatus registerUser(@Valid @RequestBody User newUser) {
		if (userRepository.findAll().stream().filter(u -> u.equals(newUser)).findFirst().isPresent()) {
			System.out.println("User Already exists!");
			return UserStatus.USER_ALREADY_EXISTS;
		}
		userRepository.save(newUser);
		return UserStatus.SUCCESS;
	}

	@PostMapping("api/users/login")
	public UserStatus loginUser(@Valid @RequestBody User user) {
		User us = userRepository.findAll().stream().filter(u -> u.equals(user)).findFirst().orElse(null);
		if (us != null) {
			us.setLoggedIn(true);
			userRepository.save(us);
			return UserStatus.SUCCESS;
		}
		return UserStatus.FAILURE;
	}

	@PostMapping("api/users/logout")
	public UserStatus logUserOut(@Valid @RequestBody User user) {
		User us = findUser(user);
		if (us != null) {
			us.setLoggedIn(false);
			userRepository.save(us);
			return UserStatus.SUCCESS;
		}
		return UserStatus.FAILURE;
	}

	@DeleteMapping("api/users/all")
	public UserStatus deleteUsers() {
		userRepository.deleteAll();
		return UserStatus.SUCCESS;
	}

	private User findUser(User user) {
		return userRepository.findAll().stream().filter(u -> u.getUsername().equals(user.getUsername())).findFirst()
				.orElse(null);
	}

	private boolean isLoggedIn(User user) {
		User us = findUser(user);
		if (us != null) {
			return us.getIsLoggedIn();
		}
		return false;
	}

	@PostMapping("api/play")
	public Object play(@Valid @RequestBody User user) {

		JSONObject response = new JSONObject();

		if (user.getUsername() == null || !isLoggedIn(user)) {
			response.put("Status", HttpStatus.UNAUTHORIZED);
			response.put("Description", "User not logged in");
			return response;
		}

		User us = findUser(user);
		Hands compHand = Hands.values()[(int) (Math.random() * 3)];
		Hands userHand = user.getHand();
		Hands won = rules[compHand.getIndex()][userHand.getIndex()];

		String result = null;
		MatchStatus status = null;
		
		if (compHand.equals(userHand)) {
			result = "Draw";
			status = MatchStatus.DRAW;
		} else if (compHand.equals(won)) {
			result = "Computer Wins";
			status = MatchStatus.LOST;
		} else {
			result = "User Wins";
			status = MatchStatus.WON;
		}

		us.addStatistics(new Statistics(us, "Computer", status));
		userRepository.save(us);

		response.put("Status", HttpStatus.OK);
		response.put("Result", result);
		return response;
	}
	
	@PostMapping("api/statistics")
	public Object statistics(@Valid @RequestBody User user) {
		JSONObject response = new JSONObject();

		if (user.getUsername() == null || !isLoggedIn(user)) {
			response.put("Status", HttpStatus.UNAUTHORIZED);
			response.put("Description", "User not logged in");
			return response;
		}

		User us = findUser(user);

		HashMap<MatchStatus, Long> stats = new HashMap<>();
		stats.put(MatchStatus.DRAW, us.getRoundsDraw());
		stats.put(MatchStatus.WON, us.getRoundsWon());
		stats.put(MatchStatus.LOST, us.getRoundsLost());
		
		response.put("Status", HttpStatus.OK);
		response.put("Statistics", stats);
		
		return response;
	}

}
