package doself.user.challenge.feed.domain;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class ChallengeFeedComment {
	private String  challengeFeedCommentCode;		// 챌린지 피드 댓글 코드
	private String  challengeFeedCode;				// 챌린지 피드 코드
	private String  challengeFeedCommentAuthor;		// 챌린지 피드 댓글 작성자
	private String  challengeFeedCommentContent;	// 챌린지 피드 댓글 내용
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date    challengeFeedCommentDate;		// 챌린지 피드 댓글 작성일
	private Integer challengeFeedCommentLike;		// 챌린지 피드 댓글 좋아요
	private String  challengeFeedCommentCaution;	// 챌린지 피드 댓글 경고
	private String  challengeFeedImageIdx;			// 챌린지 피드 이미지 idx
	private String  challengeFeedImage;				// 챌린지 피드 이미지
	private String  challengeCommentAuthorImageIdx; // 챌린지 피드 댓글 작성자 프로필 idx
	private String  challengeCommentAuthorImage;	// 챌린지 피드 댓글 작성자 프로필
	private String  loggedInMemberId;
}
