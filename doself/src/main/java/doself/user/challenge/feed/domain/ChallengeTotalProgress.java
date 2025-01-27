package doself.user.challenge.feed.domain;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class ChallengeTotalProgress {
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private String challengeCode;						// 챌린지 코드
	private String challengePersonalScoreCode;			// 개인별 점수 코드
	private String challengeName;						// 챌린지 이름
	private String challengeStatusCode;					// 챌린지 상태 코드
	private String memberId;							// 챌린지 멤버 아이디
	private Double personalCumulativePerformanceRate;  	// 챌린지 멤버 개인 달성률
	private String challengeMemberProfileIdx;			// 챌린지 멤버 프로필 idx
	private String challengeMemberProfilePath;			// 챌린지 멤버 프로필 path
	private String challengeMonthlyChallengeScoreCode;	// 챌린지 월간 점수 코드
	private Double monthlyCumulativePerformanceRate;	// 챌린지 월간 달성률
	private Date challengeStartDate;					// 챌린지 시작일
	private Date challengeEndDate;						// 챌린지 종료일
	private int challengeCurrentMember;					// 챌린지 현재 참여 멤버수
	private int challengeMaxMember;						// 챌린지 시작 멤버수
}
