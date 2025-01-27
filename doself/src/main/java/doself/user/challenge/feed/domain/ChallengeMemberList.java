package doself.user.challenge.feed.domain;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class ChallengeMemberList {
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private String challengeMemberCode;			 	// 참여자 번호
	private String challengeCode;				 	// 챌린지 번호
	private String challengeName;				 	// 챌린지 번호
	private String memberId;					 	// 아이디
	private String challengeStatus;			     	// 챌린지 상태 분류 번호
	private Date   challengeMemberInOutDate;   		// 챌린지 참여 및 퇴장 일시 기록
	private String memberProfileIdx;	   		 	// 프로필 사진 idx
	private String memberProfileImage;	   		 	// 프로필 사진
	//private Date todayParticipationRate;			// 일일 참여율
	//private Date todayAchievementRate;			// 일일 달성률
	//private Double challengeTodayParticipationRate;	// 멤버 일일 참여율
	//private Double challengeTodayAchievementRate;	// 멤버 일일 달성률
	//private Double score;							// 참여 멤버 점수 계산
}