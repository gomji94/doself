package doself.user.community.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import doself.user.community.domain.Article;
import doself.user.community.domain.SearchArticle;
import doself.util.PageInfo;
import doself.util.Pageable;

@Mapper
public interface CommunityMapper {
	
	// 게시글 총 갯수 행 조회
	int getCntOfArticle();
	
	// 카테고리 별 게시글 총 갯수 행 조회
	int getCntOfArticleByCategory(Integer categoryCode);
	
	// 검색 게시글 총 갯수 행 조회
	int getCntOfArticleBySearch(Map<String, Object> params);
	
	// 게시글 목록 조회
	List<Article> getArticleList(Pageable pageable);
	
	// 게시글 카테고리 목록 조회
	List<Map<String, String>> getArticleCategoryList();
	
	// 카테고리별 게시글 조회
	List<Article> getArticleListByCategory(Map<String, Object> params);
	
	// 게시글 조회
	Article getArticleDetail(String articleKeyNum);
	
	// 게시글 검색
	List<Article> getArticleListBySearch(Map<String, Object> params);
	
	// 게시글 삭제
	void deleteArticle(String articleKeyNum);

}
