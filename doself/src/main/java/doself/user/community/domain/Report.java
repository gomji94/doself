package doself.user.community.domain;

import lombok.Data;

@Data
public class Report {
	
	private String reportKeyValue;
	private String reporterId;
	private String occurLocationCode;
	private int reportedKeyNum;
	private String reportedKeyValue;

}
