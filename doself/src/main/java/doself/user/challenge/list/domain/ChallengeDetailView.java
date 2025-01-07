package doself.user.challenge.list.domain;

import java.util.List;

import lombok.Data;

@Data
public class ChallengeDetailView {
	private String challengeCode;		   // 챌린지 코드
//	private String challengeName;
//	private String challengeImage;
	private String challengeLeaderImage;   // 챌린지 리더 프로필 사진
	private String challengeLeaderId;      // 챌린지 리더 아이디
//	private String challengeCategory;
//	private String challengeStartDate;
//	private String challengeEndDate;
	private String challengeTopicContent;  // 챌린지 주제 레벨
	private String challengeContent;	   // 챌린지 내용
	
	private List<ChallengeList> challengeList;
}
