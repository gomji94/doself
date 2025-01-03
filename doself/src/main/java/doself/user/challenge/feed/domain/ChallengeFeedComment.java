package doself.user.challenge.feed.domain;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class ChallengeFeedComment {
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private String  challengeFeedCommentCode;
	private String  challengeFeedCode;
	private String  challengeFeedCommentAuthor;
	private String  challengeFeedCommentContent;
	private Date    challengeFeedCommentDate;
	private Integer challengeFeedCommentLike;
	private String  challengeFeedCommentCaution;
	private String  challengeFeedImage;
}