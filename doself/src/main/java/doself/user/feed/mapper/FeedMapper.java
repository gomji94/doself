package doself.user.feed.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import doself.user.feed.domain.Feed;

@Mapper
public interface FeedMapper {
	// 챌린지 피드 리스트 조회
	List<Feed> getFeedList();
	
	// 피드 검색조회
	List<Feed> getSearchList(Map<String, Object> searchMap);
}
