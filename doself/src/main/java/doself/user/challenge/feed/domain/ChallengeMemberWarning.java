package doself.user.challenge.feed.domain;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class ChallengeMemberWarning {
	private String memberWarningCode;				// 챌린지 경고 코드
	private String challengeCode;					// 챌린지 코드
	private String challengeMemberCode;				// 챌린지 멤버 코드
	private String challengeWarningCategoryCode;	// 챌린지 경고 카테고리 코드
	private String challengeWarningCategory;		// 챌린지 경고 카테고리
	private String occuranceLocationCode;			// 발생 위치 코드
	private String contentCode;						// 해당 글/댓글 번호(기본값 0)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date warningDate;						// 경고일
}
