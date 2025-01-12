package doself.user.community.domain;

import lombok.Data;

@Data
public class Comment {
	private String commentKeyNum;
	private String commentAuthorId;
	private String commentContent;
	private String commentDate;
	private int articleNum;
	private String articleKeyValue;
}
