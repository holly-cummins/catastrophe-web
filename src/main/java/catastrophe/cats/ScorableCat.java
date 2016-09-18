package catastrophe.cats;

public class ScorableCat {
	private int score;
	private String realName;
	private String attribution;
	private long id;
	private String image;

	// Constructor for jackson
	public ScorableCat() {
		super();
	}

	public ScorableCat(long id, String realName, String image, String attribution) {
		this.id = id;
		this.image = image;
		this.realName = realName;
		this.attribution = attribution;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

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
