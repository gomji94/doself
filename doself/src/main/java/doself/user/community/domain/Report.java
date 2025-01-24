package doself.user.community.domain;

import lombok.Data;

@Data
public class Report {
	
	// db Pk value
	private String reportKeyValue;
	private String reporterId;
	// 신고발생위치
	private int occurType;
	private String occurLocationCode;
	
	// 신고 카테고리
	private int reportCateNum;
	private String reportCateValue;
	
	// 신고되어진 글 번호
	private int reportedKeyNum;
	private String reportedKeyValue;
	
	// 신고사유
	private String reportContent;

}
