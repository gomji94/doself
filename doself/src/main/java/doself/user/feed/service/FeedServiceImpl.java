package doself.user.feed.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import doself.admin.declare.domain.Declare;
import doself.admin.declare.mapper.DeclareMapper;
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
	private final DeclareMapper declareMapper;
	
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
			feed.setFeedFileIndex(fileIdx);
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
            String oldFileIdx = feed.getFeedFileIndex();
            if (oldFileIdx != null) {
                filesMapper.deleteFileByIdx(oldFileIdx);
            }

            Files newFile = filesUtils.uploadFile(feedPicture);
            if (newFile != null) {
                String newFileIdx = commonMapper.getPrimaryKey("file_", "files", "file_idx");
                newFile.setFileIdx(newFileIdx);
                filesMapper.addfile(newFile);
                feed.setFeedFileIndex(newFileIdx);
            }
        }

        feedMapper.modifyFeed(feed);
    }
    
    // 피드 코드 조회
    @Override
	public Feed getFeedByCode(String feedCode) {
    	Map<String, Object> params = new HashMap<>();
    	params.put("feedCode", feedCode);
        return feedMapper.getFeedByCode(params);
	}
    
    // 피드 삭제
    @Override
    @Transactional
    public void deleteFeed(String feedCode, String memberId) {
    	feedMapper.deleteFeedComments(feedCode);
    	feedMapper.deleteDailyNutritionalIntakeComparison(feedCode);
    	feedMapper.deletedDilyNutritionalIntakeInfo(feedCode);
    	feedMapper.deleteFeed(feedCode, memberId);
    }
    
    // 피드 댓글 조회
 	@Override
 	public List<Feed> getFeedCommentList(String feedCode) {
 		List<Feed> feedCommentList = feedMapper.getFeedCommentList(feedCode);
 		return feedCommentList;
 	}
    
    // 피드 댓글 추가
    @Override
    public void addFeedComment(Feed feed) {
    	String formattedKeyNum = commonMapper.getPrimaryKey("fc_", "feed_comments", "fc_num");
    	feed.setFeedCommentCode(formattedKeyNum);
    	feed.setCommentDate(LocalDateTime.now());

    	feedMapper.addFeedComment(feed);
    }
    
    // 피드 댓글 수정
    @Override
    public boolean mofidyFeedComment(String feedCommentCode, String feedCommentContent) {
    	int modifyCnt = feedMapper.modifyFeedComment(feedCommentCode, feedCommentContent);
    	return modifyCnt > 0 ? true : false;
    }
    
    // 피드 댓글 삭제
    @Override
    public boolean deleteFeedComment(String feedCommentCode) {
    	int deleteCnt = feedMapper.deleteFeedComment(feedCommentCode);
		return deleteCnt > 0 ? true : false;
    }
    
    // 피드 신고
    @Override
    public void reportFeed(Declare declare) {
    	String formattedKeyNum = commonMapper.getPrimaryKey("rr_", "report_request", "rr_num");
    	declare.setRrNum(formattedKeyNum);
    	
        // 신고 유형 코드 가져오기
        declare.setRrDate(LocalDateTime.now().toString());
        declare.setScCode("sc_001"); // 초기 상태 설정
        
        // 발생 위치 코드 설정 (olc_code)
        declare.setOlcCode("olc_003");

        // 신고 요청 저장
        feedMapper.insertReportRequest(declare);
    }
    
    // 하루 먹은 영양 정보 조회
	/*
	 * @Override public DailyNutritionalIntakeInfo getNutritionalInfoByDate(String
	 * mbrId, String date) { Map<String, Object> params = new HashMap<>();
	 * params.put("mbrId", mbrId); params.put("date", date);
	 * 
	 * return (DailyNutritionalIntakeInfo)
	 * feedMapper.getNutritionalInfoByDate(params); }
	 */
}
	
