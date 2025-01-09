package doself.admin.challenge.domain;

import doself.admin.member.domain.Member;
import lombok.Data;

@Data
public class Warning {
	
	private String lcmwlNum;
	private String cgNum;
	private String cgmNum;
	private String cmwcNum;
	private String olcCode;
	private String lcmwlBcNum;
	private String lcmwlDate;
	
	private Challenge challengeInfo;
	private ChallengeMember challengeMemberInfo;
	private Member memberInfo;
	private WarningCategory warningCategoryInfo;
	private OccuranceLocation occuranceLocationInfo;
}
