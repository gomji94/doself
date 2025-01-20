package doself.user.challenge.feed.domain;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

//@Data : @Getter + @Setter + @ToString
@Data
public class ChallengeUser {
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private String challengeCode;
	private String challengeName;
	private String challengeMemberId;
	private String challengeTopic;
	private Date   challengeStartDate;
	private Date   challengeEndDate;
	private String challengeTopicLevel;
	private String challengeTopicLevelContent;
	private int    challengeMaxMemberCount;
	private String challengeContent;
	private String challengeImage;
	private Date   challengeFeedUploadDate;
}