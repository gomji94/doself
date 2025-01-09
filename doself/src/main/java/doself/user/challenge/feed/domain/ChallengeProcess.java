package doself.user.challenge.feed.domain;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data	
public class ChallengeProcess {
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private String challengeCode;					// 챌린지 코드
	private String challengeName;					// 챌린지 이름
	private String challengeStatusCode;				// 챌린지 상태 코드
	private String challengeMemberId;				// 챌린지 멤버
	private String challengeMemberProfile;			// 멤버 프로필 이미지
	private Date challengeCreationDate;				// 챌린지 생성일
	private Date challengeStartDate;				// 챌린지 시작일
	private Date challengeEndDate;					// 챌린지 종료일
	private int challengeCurrentMember;				// 챌린지 현재 참여 멤버수
	private int challengeMaxMember;					// 챌린지 시작 멤버수
	private int challengeTodayParticipationRate;	// 멤버 일일 참여율
	private Double challengeTodayAchievementRate;	// 멤버 일일 달성률
}
