package catastrophe.cats;

public class Cat {
	private String realName;
	private String attribution;
	private long id;
	private String image;

	public long getId() {
		return id;
	}

	public String getImage() {
		return image;

	}

	public void setId(long id) {
		this.id = id;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getRealName() {
		return realName;
	}

	public String getAttribution() {
		return attribution;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public void setAttribution(String attribution) {
		this.attribution = attribution;
	}

}
