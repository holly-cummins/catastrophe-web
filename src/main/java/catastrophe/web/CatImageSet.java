package catastrophe.web;

import java.util.Set;

public class CatImageSet {

	@SuppressWarnings("rawtypes")
	private Set cats;
	private String catastropheUsers;

	@SuppressWarnings("rawtypes")
	public Set getCats() {
		return cats;
	}

	@SuppressWarnings("rawtypes")
	public void setCats(Set cats) {
		this.cats = cats;
	}

	public String getCatastropheUsers() {
		return catastropheUsers;
	}

	public void setCatastropheUsers(String host) {
		catastropheUsers = host;
	}
}
