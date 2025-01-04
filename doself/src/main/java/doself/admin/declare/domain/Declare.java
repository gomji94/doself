package doself.admin.declare.domain;

import doself.admin.challenge.domain.OccuranceLocation;
import doself.admin.member.domain.Member;
import doself.admin.nutrition.domain.StatusCategory;
import lombok.Data;

@Data
public class Declare {
	
	private String rrNum;
	private String mbrId;
	private String olcCode;
	private String rrBcNum;
	private String rcCode;
	private String rrContent;
	private String rrDate;
	private String scCode;
	private String rrAdmin;
	private String rrProcessingPeriod;
	private String rrReasonContent;
	
	private Member memberInfo;
	private OccuranceLocation occuranceLocationInfo;
	private StatusCategory statusCategoryInfo;
	private DeclareCategory declareCategoryInfo;
}
