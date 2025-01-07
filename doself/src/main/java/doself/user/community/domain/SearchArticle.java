package doself.user.community.domain;

import lombok.Data;

@Data
public class SearchArticle {
	
	private String dateFilter;
	private Integer days;
	private String searchFilter;
	private String searchValue;

}
