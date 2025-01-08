package doself.user.challenge.list.domain;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class ChallengeDetailView {
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private String challengeCode;		     // 챌린지 번호(기본키)
	private String challengeName;		     // 챌린지 이름
	private String challengeImage;		     // 챌린지 대표 이미지
	private String challengeLeaderImage;     // 챌린지 리더 프로필 이미지
	private String challengeLeaderId;        // 챌린지 리더 아이디
	private String challengeCategory;	     // 챌린지 카테고리 이름
	private Date challengeStartDate;	     // 챌린지 시작일
	private Date challengeEndDate;		     // 챌린지 종료일
	private String challengeTopicLevel;	     // 챌린지 난이도
	private String challengeLevelContent;    // 챌린지 난에도에 따른 설명
	private int challengeCurrentMember;      // 챌린지 현재 멤버수
	private int challengeMaxMember;		     // 챌린지 최대 멤버수(생성 시, 설정한 값)
	private String challengeContent;	     // 챌린지 내용
}
