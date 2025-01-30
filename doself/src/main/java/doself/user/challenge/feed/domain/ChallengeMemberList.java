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
	
	private String memberWarningCode;				// 챌린지 경고 코드
	private String challengeWarningCategoryCode;	// 챌린지 경고 카테고리 코드
	private String challengeWarningCategory;		// 챌린지 경고 카테고리
	private String occuranceLocationCode;			// 발생 위치 코드
	private String contentCode;						// 해당 글/댓글 번호(기본값 0)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date warningDate;						// 경고일
	private String challengeFeedCode;				// 챌린지 피드 코드
	private String challengeFeedContent;			// 챌린지 피드 내용
	private String challengeCommentCode;			// 챌린지 댓글 코드
	private String challengeCommentContent;			// 챌린지 댓글 내용
}