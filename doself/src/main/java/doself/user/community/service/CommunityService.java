package doself.user.community.service;

import java.util.List;
import java.util.Map;

import doself.user.community.domain.Article;
import doself.user.community.domain.SearchArticle;
import doself.util.PageInfo;
import doself.util.Pageable;

public interface CommunityService {
	
	// 게시글 목록 조회
	PageInfo<Article> getArticleList(Pageable pageable);
	
	// 게시글 카테고리 목록 조회
	List<Map<String, String>> getArticleCategoryList();
	
	// 카테고리별 게시글 조회
	PageInfo<Article> getArticleListByCategory(Pageable pageable, Integer categoryCode);
	
	// 게시글 조회
	Article getArticleDetail(String articleKeyNum);
	
	// 게시글 검색
	PageInfo<Article> getArticleListBySearch(Pageable pageable, SearchArticle searchArticle);

}
