package doself.admin.challenge.domain;

import lombok.Data;

@Data
public class Challenge {
	
	private String cgNum;
	private String ctlNum;
	private String cgName;
	private String cgFileIdx;
	private String cgContent;
	private String cgCreationDate;
	private int cgMaxMbrCount;
	private String cgStartDate;
	private String cgEndDate;
	private int cgLike;
	private String csNum;
	private String mbrId;
	private String csRewardCheck;
	
	private TopicLevel topicLevelInfo;
	private Status statusInfo;
}
