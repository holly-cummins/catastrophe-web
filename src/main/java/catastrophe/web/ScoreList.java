package catastrophe.web;

import java.util.List;

public class ScoreList {

	@SuppressWarnings("rawtypes")
	private List scores;
	private String catastropheUsers;

	@SuppressWarnings("rawtypes")
	public List getScores() {
		return scores;
	}

	@SuppressWarnings("rawtypes")
	public void setScores(List scores) {
		this.scores = scores;
	}

	public String getCatastropheUsers() {
		return catastropheUsers;
	}

	public void setCatastropheUsers(String host) {
		catastropheUsers = host;
	}
}
