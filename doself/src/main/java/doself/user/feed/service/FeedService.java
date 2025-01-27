package doself.user.feed.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import doself.user.feed.domain.Feed;

public interface FeedService {
	// 피드 리스트 조회
	List<Feed> getFeedList();
	
	// 특정 피드 상세 조회
	Feed getFeedDetail(String feedCode);
	
	// 피드 추가
	void addFeed(Feed feed,  MultipartFile feedPicture);

	// 피드 좋아요 증가
	void incrementLike(String feedNum);
	
	// 피드 좋아요 감소
	void decrementLike(String feedNum);
	
	// 피드 수정
	void modifyFeed(Feed feed, MultipartFile feedPicture);
	
	// 피드 코드 조회
	Feed getFeedByCode(String feedCode);
	
	// 피드 삭제
	void deleteFeed(String feedCode);
	
	// 피드 댓글 추가
	void addComment(String feedCode, String memberId, String commentContent);
	
	// 피드 댓글 조회
	List<Feed> getCommentsByFeedCode(String feedCode);
}
