package doself.user.challenge.list.domain;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

//@Data : @Getter + @Setter + @ToString
@Data
public class ChallengeList {
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private String  ChallengeCode;
	private String  memberId;
	private String	ChallengeName;
	private String  ChallengePicture;
	private Date	ChallengeStartDate;
	private Date 	ChallengeEndDate;
	// Integer : 참조형 null과 비교 가능하고 boolean가능
	// 타입 변경 여부 확인
	private int 	ChallengeCurrentMember;
	private int 	ChallengeMaxMember;
	private String  ChallengeTopicLevel;
	private String  ChallengeTopicCode;
	private String  ChallengeTopicName;
	private String  ChallengeStatus;
}