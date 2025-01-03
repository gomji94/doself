package doself.user.challenge.feed.domain;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class ChallengeMemberList {
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private String challengeMemberCode;			 // 참여자 번호
	private String challengeCode;				 // 챌린지 번호
	private String memberId;					 // 아이디
	private String memberProfileImage;	   		 // 프로필 사진
	private String challengeStatus;			     // 챌린지 상태 분류 번호
	private Date   challengeMemberInOutDate;     // 챌린지 참여 및 퇴장 일시 기록
}