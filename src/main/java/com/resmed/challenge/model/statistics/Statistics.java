package com.resmed.challenge.model.statistics;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.resmed.challenge.model.user.User;

@Entity
@Table(name = "statistics")
public class Statistics {

	@Id
	@GeneratedValue
	private long id;

	@ManyToOne(fetch = FetchType.LAZY)
	private User user;

	@NotBlank
	private String against;
	
	@NotBlank
	private MatchStatus status;

	public Statistics() {
		// TODO Auto-generated constructor stub
	}
	
	public Statistics(@NotBlank User user, @NotBlank String against, @NotBlank MatchStatus status) {
		this.user = user;
		this.against = against;
		this.status = status;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getAgainst() {
		return against;
	}

	public void setAgainst(String against) {
		this.against = against;
	}

	public MatchStatus getStatus() {
		return status;
	}

	public void setStatus(MatchStatus status) {
		this.status = status;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return Objects.hash(id, user, against, status);
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Statistics))
			return false;
		Statistics stats = (Statistics) o;
		return user.equals(stats.user) && Objects.equals(against, stats.getAgainst());
	}


}
