package doself.user.challenge.feed.domain;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class AddChallengeFeed {
	private String challengeFeedCode;
	private String challengeCode;
	private String challengeMemberId;
	private String challengeFeedContent;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date   challengeFeedDate;
	private int    challengeFeedLike;
	private String challengeFeedWarningCheck;
	private String challengeFeedFileIdx;
	private int challengeFeedFoodIntake;
	private String challengeMealCategory;
}
