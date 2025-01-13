package doself.user.challenge.feed.domain;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

//@Data : @Getter + @Setter + @ToString
@Data
public class ChallengeFeed {
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private String  challengeFeedCode;			  // 챌린지 피드 코드
	private String  challengeCode;				  // 챌린지 코드
	private String  challengeMemberId;  		  // 챌린지 피드 작성자
	private String  challengeFeedContent;		  // 챌린지 피드 내용
	private String  challengeFeedPicture;		  // 챌린지 피드 사진
	private Date	challengeFeedDate;			  // 챌린지 피드 일자
	private Integer challengeFeedLike;			  // 챌린지 피드 좋아요(null이 아니면 카운트)
	private String  challengeFeedWarningCheck;	  // 챌린지 피드 경고 체크
	private String  memberProfileImage;			  // 회원 프로필 이미지
	private String  challengeFeedCommentCode;	  // 챌린지 피드 댓글 번호
	private String  challengeFeedCommentContent;  // 챌린지 피드 댓글 내용
}