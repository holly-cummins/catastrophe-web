package catastrophe.cats;

public class Score {

	private Integer score;
	private String algorithm;
	private String bestGuess;
	private String fact;

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

	public String getFact() {
		return fact;
	}

	public void setFact(String fact) {
		this.fact = fact;

	}
}
