package doself.admin.member.domain;

import lombok.Data;

@Data
public class MemberLog {
	
	private String mllNum;
	private String mbrId;
	private String mllIp;
	private String mllDate;
	
	private Member memberInfo;
}
