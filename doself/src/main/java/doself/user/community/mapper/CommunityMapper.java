package doself.user.community.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import doself.user.community.domain.Article;

@Mapper
public interface CommunityMapper {
	
	// 게시글 목록 조회
	List<Article> getArticleList();
	
	// 게시글 카테고리 목록 조회
	List<Map<String, String>> getArticleCategoryList();
	
	// 카테고리별 게시글 조회
	List<Article> getArticleListByCategory(String categoryCode);
	
	// 게시글 조회
	Article getArticleDetail(String articleKeyNum);

}
