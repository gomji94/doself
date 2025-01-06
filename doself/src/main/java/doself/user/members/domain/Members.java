package doself.user.members.domain;

import lombok.Data;

@Data
public class Members {
	
	private String memberId;
	private String oldMemberPw;
	private String newMemberPw;
	private String confirmMemberPw;
	private String memberEmail;
	private String memberName;
	private String memberBrith;
	private String memberPhoneNum;
	private String memberGrdCode;
	private String memberGender;
	private String memberAgeRange;
	private Integer openTicketCnt; //챌린지개설 티켓 갯수
	private Integer partTicketCnt; //챌린지참여 티켓 갯수
	private Integer memberPoint;
	private String memberImage;
	
}
