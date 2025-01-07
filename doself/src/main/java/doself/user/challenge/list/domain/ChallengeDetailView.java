package doself.user.challenge.list.domain;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class ChallengeDetailView {
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private String challengeCode;		     // 챌린지 코드
    private String challengeName;		     // 챌린지 이름
    private String memberId;       			 // 챌린지 리더 아이디
    private String challengeCategory;	     // 챌린지 카테고리
    private Date challengeStartDate;	     // 챌린지 시작일
    private Date challengeEndDate;		     // 챌린지 종료일
    private String challengeTopicLevel;		 // 챌린지 난이도
    private int challengeCurrentMember; 	 // 챌린지 현재 멤버수
    private int challengeMaxMember;			 // 챌린지 최대 멤버수(생성 시, 설정한 값)
    private String challengeContent;	     // 챌린지 내용
    private String challengeLeaderName;	     // 챌린지 리더 이름
    private String challengeLeaderImage;	 // 챌린지 리더 프로필
    private String challengeLevelContent;	 // 챌린지 상태 코드
    private String challengeRewardCheck;	 // 챌린지 보상 지급 여부
    
    private String challengeTopicLevelCode;	 // 챌린지 레벨 코드
	private Date challengeCreationDate;	     // 챌린지 시작일
	private String challengeStatusCode;	     // 챌린지 상태 코드
	
	private List<ChallengeList> challengeList;
}
