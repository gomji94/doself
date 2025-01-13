package doself.user.community.domain;

import lombok.Data;

@Data
public class Like {
	
	private String likeKeyNum;
	private String likeMemberId;
	private int likeOccurArticleNum;
	private String likeOccurArticleNumValue;
	private String isLiked;
	private int currentArticleLikeCnt;
	
}
