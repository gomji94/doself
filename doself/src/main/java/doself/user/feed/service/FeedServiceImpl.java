package doself.user.feed.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import doself.common.mapper.CommonMapper;
import doself.file.domain.Files;
import doself.file.mapper.FilesMapper;
import doself.file.util.FilesUtils;
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
	private final FilesUtils filesUtils;
	private final FilesMapper filesMapper;
	private final CommonMapper commonMapper;
	
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
		log.info("Fetching feed detail for feedCode: {}", feedCode);
        Feed feed = feedMapper.getFeedDetail(feedCode);
        if (feed == null) {
        	log.warn("Feed not found for feedCode: {}", feedCode);
            throw new RuntimeException("피드 정보를 찾을 수 없습니다.");
        }
        log.info("Feed detail retrieved: {}", feed);
        return feed;
    }
	
	// 피드 추가
	@Override
	public void addFeed(Feed feed, MultipartFile feedPicture) {
		if (feed.getFeedIntakeDate() == null) {
	        feed.setFeedIntakeDate(LocalDateTime.now()); // 서버에서 현재 시간 기본 설정
	    }
		Files fileInfo = filesUtils.uploadFile(feedPicture);
		if(fileInfo != null) {
			String fileIdx = commonMapper.getPrimaryKey("file_", "files", "file_idx");
			fileInfo.setFileIdx(fileIdx);
			filesMapper.addfile(fileInfo);
			String feedCode = commonMapper.getPrimaryKey("feed_", "feed", "feed_num");
			feed.setFeedCode(feedCode);
			feed.setFeedFileIdx(fileIdx);
			feedMapper.addFeed(feed);
		}

	}
 
    // 좋아요 수 증가
    public void incrementLike(String feedNum) {
        feedMapper.incrementLike(feedNum);
    }

    // 좋아요 수 감소
    public void decrementLike(String feedNum) {
        feedMapper.decrementLike(feedNum);
    }
    
    // 피드 수정
    @Override
    public void modifyFeed(Feed feed, MultipartFile feedPicture) {
    	if (feedPicture != null && !feedPicture.isEmpty()) {
            String oldFileIdx = feed.getFeedFileIdx();
            if (oldFileIdx != null) {
                filesMapper.deleteFileByIdx(oldFileIdx);
            }

            Files newFile = filesUtils.uploadFile(feedPicture);
            if (newFile != null) {
                String newFileIdx = commonMapper.getPrimaryKey("file_", "files", "file_idx");
                newFile.setFileIdx(newFileIdx);
                filesMapper.addfile(newFile);
                feed.setFeedFileIdx(newFileIdx);
            }
        }

        feedMapper.modifyFeed(feed);
    }
    
    // 피드 코드 조회
    @Override
	public Feed getFeedByCode(String feedCode) {
		Map<String, Object> params = new HashMap<>();
	    params.put("FeedCode", feedCode);
	    return feedMapper.getFeedByCode(params);
	}
    
    // 피드 댓글 추가
}
	
