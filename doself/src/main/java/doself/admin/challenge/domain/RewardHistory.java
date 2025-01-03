package doself.admin.challenge.domain;

import lombok.Data;

@Data
public class RewardHistory {
	
	private String rhNum;
	private String rhTarget;
	private String rhRewardType;
	private String rhRank;
	private int rhPoint;
	
	private String rhName;
}
