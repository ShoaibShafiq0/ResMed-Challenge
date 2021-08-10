package com.resmed.challenge.model.user;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;

import com.resmed.challenge.controller.Hands;
import com.resmed.challenge.model.statistics.MatchStatus;
import com.resmed.challenge.model.statistics.Statistics;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users")
public class User {
	
	@Id 
	@GeneratedValue 
	private long id;
	
	@NotBlank
	private String username;
	
	@NotBlank
	private String password;
	
	@NotBlank
	@Transient
	private Hands hand;
	
	@NotBlank
	private boolean loggedIn;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Statistics> statistics;

	public User() {
		if (statistics == null)
			statistics = new ArrayList<Statistics>();
	}

	public User(@NotBlank String username, @NotBlank String password) {
		this.username = username;
		this.password = password;
		this.loggedIn = false;
		if (statistics == null)
			statistics = new ArrayList<Statistics>();
	}

	public long getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean getIsLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}
	
	public Hands getHand() {
		return hand;
	}

	public void setHand(Hands hand) {
		this.hand = hand;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof User))
			return false;
		User user = (User) o;
		return Objects.equals(username, user.username) && Objects.equals(password, user.password);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, username, password, loggedIn);
	}

	@Override
	public String toString() {
		return "User{" + "id=" + id + ", username='" + username + '\'' + ", password='" + password + '\''
				+ ", loggedIn=" + loggedIn + '}';
	}
	
	public void addStatistics(Statistics statistic) {
		statistics.add(statistic);
	}
	
	public List<Statistics> getStatistics(){
		return statistics;
	}
	
	public int getRoundsPlayed() {
		return statistics.size();
	}
	
	public long getRoundsWon() {
		return statistics.stream().filter(s -> s.getStatus().equals(MatchStatus.WON)).count();
	}
	
	public long getRoundsLost() {
		return statistics.stream().filter(s -> s.getStatus().equals(MatchStatus.LOST)).count();
	}
	
	public long getRoundsDraw() {
		return statistics.stream().filter(s -> s.getStatus().equals(MatchStatus.DRAW)).count();
	}
}
