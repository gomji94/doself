package doself.user.member.dto;

import java.sql.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class Member {
	
	private String memberId;
	private String memberPw;
	private String memberName;
	private String memberGrdCode;
	private String memberEmail;
	private String memberBrith;
	private String memberPhoneNum;
	private String memberGender;
	private String memberAgeRange;
	private Integer openTicketCnt;
	private Integer partTicketCnt;
	private Integer memberPoint;
	private String memberImage;
	
}
