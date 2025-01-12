package doself.user.community.domain;

import java.util.List;

import lombok.Data;

@Data
public class Article {
	
	private int articleKeyNum;
	private String articleKeyValue;;
	private String articleAuthorId;
	private int articleCategoryKeyNum;
	private String articleCategory;
	private String articleTitle;
	private String articleContent;
	private String articleCreationDate;
	private String articleAttachmentFile;
	private int articleViewCnt;
	private int articleLike;
	
	List<Comment> commentList;

}
