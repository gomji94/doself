package doself.admin.challenge.domain;

import doself.admin.member.domain.Member;
import lombok.Data;

@Data
public class PersonalScore {
	
	private String cpslNum;
	private String cpslYear;
	private int cpslMonthly;
	private String cgNum;
	private String mbrId;
	private double cpslCumulativePerformanceRate;
	private double cpslCumulativeParticipationRate;
	private int cpslCumulativePerformanceScore;
	private int cpslCumulativeParticipationScore;
	private int cpslLikeScore;
	private int cpslCommentsCount;
	private int cpslTotalScore;
	private int cpslRank;
	
	private Challenge challengeInfo;
	private Member memberInfo;
}
