package doself.user.community.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class Comment {
	private String commentKeyNum;
	private String commentAuthorId;
	@NotBlank(message = "댓글 내용을 입력해주세요")
	private String commentContent;
	private String commentDate;
	private int articleNum;
	private String articleKeyValue;
}
