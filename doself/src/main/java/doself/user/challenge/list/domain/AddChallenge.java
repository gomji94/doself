package doself.user.challenge.list.domain;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class AddChallenge {
	private String challengeCode;			// 챌린지 코드(자동생성)
	private String challengeTopicLevelCode; // 챌린지 카테고리별 난이도(string으로 저장)
	private String challengeLeaderName;		// 챌린지 리더 이름
	private String challengeName;			// 챌린지 이름
	private String challengePicture;		// 챌린지 사진
	private String challengeContent;		// 챌린지 내용
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date   challengeCreationDate;	// 챌린지 생성일(현재시간 CURDATE)
	//private int    challengeCurrentMember;	// 챌린지 현재 멤버수(기본값 0)
	private int    challengeMaxMember;		// 챌린지 설정 최대 멤버수(옵션 선택값)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date   challengeStartDate;		// 챌린지 시작일(옵션 선택값)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date   challengeEndDate;		// 챌린지 완료일(시작일 + 14)
	private int    challengeGroupLike;		// 챌린지 좋아요(기본값 0)
	private String challengeStatusCode;		// 챌린지 상태 분류 번호(초기 기본값 cs_004)
	private String memberId;				// 챌린지 리더 아이디(현재 접속중인 아이디 정보)
	private String challengeRewardCheck;	// 보상 지급 여부(빈값)
	private String challengeFileIdx;
	
	private List<ChallengeList> challengeList;
}
