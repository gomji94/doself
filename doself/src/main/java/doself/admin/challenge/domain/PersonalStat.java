package doself.admin.challenge.domain;

import doself.admin.member.domain.Member;
import lombok.Data;

@Data
public class PersonalStat {
	
	private String cmssNum;
	private String cgNum;
	private String mbrId;
	private String cmssDate;
	private int cmssUploadFeedCount;
	private double cmssTodayParticipationRate;
	private Integer cmssParticipationCheck;
	private double cmssTodayAchievementRate;
	
	private Challenge challengeInfo;
	private Member memberInfo;
}
