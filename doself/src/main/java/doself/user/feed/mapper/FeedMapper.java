package doself.user.feed.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import doself.user.feed.domain.Feed;

@Mapper
public interface FeedMapper {
	// 피드 리스트 조회
	List<Feed> getFeedList();
	
	// 특정 피드 상세 조회
	Feed getFeedDetail(String feedCode);
	
	// 피드 추가
	int addFeed(Feed feed);
	
	// 자동완성 검색
	@Select("SELECT mni_name FROM meal_nutrition_info WHERE mni_name LIKE CONCAT('%', #{query}, '%')")
    List<String> findKeywords(String query);
}
