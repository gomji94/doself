package doself.user.challenge.list.domain;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

//@Data : @Getter + @Setter + @ToString
@Data
public class ChallengeList {
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private String  challengeCode;
	private String	challengeTopicLevelCode;
	private String  challengeLeaderName;
	private String  memberId;
	private String	challengeName;
	private String  challengePicture;
	private String  challengeContent;
	private Date	challengeCreationDate;
	private Date	challengeStartDate;
	private Date 	challengeEndDate;
	// Integer : 참조형 null과 비교 가능하고 boolean가능
	// 타입 변경 여부 확인
	private int		challengeGroupLike;
	private int 	challengeCurrentMember;
	private int 	challengeMaxMember;
	private String  challengeTopicLevel;
	private String  challengeTopicCode;
	private String  challengeTopicName;
	private String  challengeStatusCode;
	private String  challengeStatus;
	private String  challengeRewardCheck;
	
	private ChallengeDetailView challengeDetailView;
}