package doself.user.challenge.feed.domain;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

//@Data : @Getter + @Setter + @ToString
@Data
public class ParticipateChallengeList {
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private String  challengeCode;				// 챌린지 코드
	private String	challengeTopicLevelCode;	// 챌린지 카테고리 코드
	private String  challengeLeaderName;		// 챌린지 생성자 이름(리더)
	private String  memberId;					// 멤버 아이디
	private String	challengeName;				// 챌린지 이름
	private String  challengePicture;			// 챌린지 사진
	private String  challengeContent;			// 챌린지 내용
	private Date	challengeCreationDate;		// 챌린지 생성일
	private Date	challengeStartDate;			// 챌린지 시작일
	private Date 	challengeEndDate;			// 챌린지 종료일
	// Integer : 참조형 null과 비교 가능하고 boolean가능
	// 타입 변경 여부 확인
	private int		challengeGroupLike;			// 챌린지 좋아요(기본 0)
	private int 	challengeCurrentMember;		// 챌린지 현재 멤버수(기본 0)
	private int 	challengeMaxMember;			// 챌린지 최대 멤버수(옵션 선택값)
	private String  challengeTopicLevel;		// 챌린지 카테고리 난이도
	private String  challengeTopicCode;			// 챌린지 카테고리 코드
	private String  challengeTopicName;			// 챌린지 카테고리명
	private String  challengeStatusCode;		// 챌린지 상태 코드
	private String  challengeStatus;			// 챌린지 상태
	private String  challengeRewardCheck;		// 보상 지급 여부
}