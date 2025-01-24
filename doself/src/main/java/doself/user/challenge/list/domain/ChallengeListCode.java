package doself.user.challenge.list.domain;

import lombok.Data;

@Data
public class ChallengeListCode {
	// 챌린지 카테고리별 난이도
	private String challengeTopicLevelCode;
	private int challengeTopicLevelNum;
}
