package catastrophe.cats;

public class Cat {
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

	public String getAttribution() {
		return attribution;
	}

	public void setAttribution(String attribution) {
		this.attribution = attribution;
	}

}
