package doself.admin.nutrition.domain;

import doself.admin.member.domain.Member;
import lombok.Data;

@Data
public class Nutrition {
	
	private String nirrNum;
	private String mbrId;
	private String nirrContent;
	private String nirrDate;
	private String nirrCategory;
	private String scCode;
	private String prrAdmin;
	private String nirrProcessingDate;
	private String nirrReasonContent;
	
	private Member memberInfo;
	private StatusCategory statusCategoryInfo;
}
