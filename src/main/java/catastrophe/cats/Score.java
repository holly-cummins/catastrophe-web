package catastrophe.cats;

public class Score {

	private Integer score;
	private String algorithm;
	public String bestGuess;

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

	public String getBestGuess() {
		return bestGuess;
	}

	public void setBestGuess(String bestGuess) {
		this.bestGuess = bestGuess;
	}
}
