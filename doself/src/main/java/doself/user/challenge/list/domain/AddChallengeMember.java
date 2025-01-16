package doself.user.challenge.list.domain;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class AddChallengeMember {
	private String challengeMemberCode;			// 챌린지 참여 멤버 코드
	private String challengeCode;				// 챌린지 코드
	private String challengeMemberId;			// 챌린지 참여 멤버 아이디
	private String challengeStatusCode;			// 챌린지 상태 코드
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date challengeParticipationDate;	// 챌린지 참여 일자(퇴장일자 기록 포함)
}
