package doself.admin.challenge.domain;

import lombok.Data;

@Data
public class Challenge {
	
	private String cgNum;
	private String ctlNum;
	private String cgLeaderName;
	private String cgName;
	private String cgPicture;
	private String cgContent;
	private String cgCreationDate;
	private int cgCurrentMbrCount;
	private int cgMaxMbrCount;
	private String cgStartDate;
	private String cgEndDate;
	private int cgLike;
	private String csNum;
	private String mbrId;
	private String csRewardCheck;
}
