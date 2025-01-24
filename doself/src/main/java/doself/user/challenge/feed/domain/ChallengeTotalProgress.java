package doself.user.challenge.feed.domain;

import lombok.Data;

@Data
public class ChallengeTotalProgress {
	private String challengePersonalScoreCode;					// 개인별 점수 코드
	private String challengeCode;								// 챌린지 코드
	private String challengeMemberId;							// 챌린지 멤버 아이디
	private Double challengePersonalCumulativePerformanceRate;  // 챌린지 멤버 개인 달성률
	private String challengeMemberProfileIdx;					// 챌린지 멤버 프로필 idx
	private String challengeMemberProfilePath;					// 챌린지 멤버 프로필 path
	private String challengeMonthlyChallengeScoreCode;			// 챌린지 월간 점수 코드
	private Double challengeMonthlyCumulativePerformanceRate;	// 챌린지 월간 달성률
}
