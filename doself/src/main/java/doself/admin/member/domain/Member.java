package doself.admin.member.domain;

import lombok.Data;

@Data
public class Member {
	private String mbrId;
	private String mbrPw;
	private String mbrName;
	private String mgCode;
	private String mbrEmail;
	private String mbrBirthDate;
	private String mbrPhoneNum;
	private String mbrGender;
	private String acNum;
	private int openingTicketCount;
	private int participationTicketCount;
	private int mbrPoint;
}
