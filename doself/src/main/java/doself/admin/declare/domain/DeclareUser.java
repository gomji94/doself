package doself.admin.declare.domain;

import doself.admin.member.domain.Member;
import lombok.Data;

@Data
public class DeclareUser {
	
	private String rmmNum;
	private String rrNum;
	private String mbrId;
	private String rcCode;
	private String rmmSanctionStartDate;
	private int rmmSanctionPeriod;
	private String rmmSanctionEndDate;
	private String rmmAdmin;
	
	private Member memberInfo;
	private DeclareCategory declareCategoryInfo;
}
