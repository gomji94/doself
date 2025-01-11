package doself.user.community.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import doself.user.community.domain.Article;
import doself.user.community.domain.Comment;
import doself.user.community.domain.Like;
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
	
	// 특정 글 댓글 리스트 조회
	List<Comment> getCommentsByArticle(String articleKeyNum);
	
	// 좋아요 기록 가져오기
	Like isLikedByUser(Like like);
	
	// 좋아요 기록 insert
	int createLikeToArticle(Like like);
	
	// 게시글 좋아요 update +1
	void modifyArticleLikeCnt(Map<String, Object> params);
	
	// 좋아요 기록 update
	int modifyisLiked(Like like);
	

}
