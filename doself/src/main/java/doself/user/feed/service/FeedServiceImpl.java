package doself.user.feed.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import doself.user.feed.domain.Feed;
import doself.user.feed.mapper.FeedMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
//jdbc가 추가된 상태에서만 트랜잭셔널 어노테이션 실행됨
//전체 적용하려면 여기에 작성 || 특정 메소드에만 설정하고 싶으면 메소드 상단에 배치
@Transactional
@RequiredArgsConstructor
//log.이 가능한 이유는 아래의 어노테이션 때문

@Slf4j
public class FeedServiceImpl implements FeedService {

	private final FeedMapper feedMapper;
	
	@Override
	public List<Feed> getFeedList() {
		List<Feed> feedList = feedMapper.getFeedList();
		if (feedList == null || feedList.isEmpty()) {
	        log.warn("피드 정보가 없습니다.");
	    }
		return feedList;
	}
	
	@Override
	public List<Feed> searchList(String searchCate, String searchValue, int listSize) {
		switch (searchCate) {
			case "id" 		-> searchCate = "f.mbr_id";
			case "content" 	-> searchCate = "f.feed_content";	
		}
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("searchCate", searchCate);
		searchMap.put("searchValue", searchValue);
		searchMap.put("listSize", listSize);
		
		List<Feed> feedList = feedMapper.getSearchList(searchMap);
		
		return feedList;
	}
}
	
