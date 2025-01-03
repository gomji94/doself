package doself.admin.challenge.domain;

import lombok.Data;

@Data
public class Score {
	
	private String mcslNum;
	private String mcslYear;
	private int mcslMonthly;
	private String cgNum;
	private double mcslCumulativePerformanceRate;
	private double mcslCumulativeParticipationRate;
	private int mcslLevelScore;
	private int mcslCumulativePerformanceScore;
	private int mcslCumulativeParticipationScore;
	private int mcslLikeScore;
	private int mcslTotalScore;
	private int mcslRank;
}
