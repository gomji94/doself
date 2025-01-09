package doself.admin.challenge.domain;

import doself.admin.member.domain.Member;
import lombok.Data;

@Data
public class Reward {
	
	private String rphNum;
	private String cgNum;
	private String mbrId;
	private String rhNum;
	private String rphRewardSelectedDate;
	private String rphAdmin;
	private String rphRewardPaymentDate;
	
	private Challenge challengeInfo;
	private Member memberInfo;
	private RewardHistory rewardHistoryInfo;
}
