package catastrophe.cats;

public class Score {

	private Integer score;
	private String algorithm;
	public String realName;

	public Integer getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getScoringAlgorithm() {
		return algorithm;
	}

	public void setScoringAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}
}
