package doself.user.feed.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	// 피드 조회
	@Override
	public List<Feed> getFeedList() {
		List<Feed> feedList = feedMapper.getFeedList();
		if (feedList == null || feedList.isEmpty()) {
	        log.warn("피드 정보가 없습니다.");
	    }
		return feedList;
	}
	
	// 특정 피드 조회
	@Override
    public Feed getFeedDetail(String feedCode) {
        Feed feed = feedMapper.getFeedDetail(feedCode);
        if (feed == null) {
            log.warn("해당 피드가 존재하지 않습니다. feedCode: {}", feedCode);
            throw new RuntimeException("피드 정보를 찾을 수 없습니다.");
        }
        return feed;
    }
	
	// 피드 추가
	@Override
	public void addFeed(Feed feed) {
	    try {
	        feedMapper.addFeed(feed);
	        log.info("피드가 성공적으로 생성되었습니다. {}", feed);
	    } catch (Exception e) {
	        log.error("피드 생성 중 오류 발생: {}", e.getMessage());
	        throw new RuntimeException("피드 생성 중 오류 발생");
	    }
	}
	
	// 자동완성 검색
	public List<String> getKeywords(String query) {
        return feedMapper.findKeywords(query);
    }
}
	
