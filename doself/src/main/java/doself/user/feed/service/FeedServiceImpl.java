package doself.user.feed.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import doself.user.feed.domain.Feed;
import doself.user.feed.domain.MealNutritionInfo;
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
            throw new RuntimeException("피드 정보를 찾을 수 없습니다.");
        }
        return feed;
    }
	
	// 음식 이름 검색
	@Override
	public List<String> findKeywords(String query) {
	    return feedMapper.findKeywords(query);
	}
	
	// 피드 추가
	@Override
    public void addFeed(Feed feed, MultipartFile feedPicture) {
        // 이미지 저장
        String uploadPath = "/upload/feed"; // 파일 업로드 경로 설정
        String fileName = System.currentTimeMillis() + "_" + feedPicture.getOriginalFilename();
        File destinationFile = new File(uploadPath, fileName);

        try {
            feedPicture.transferTo(destinationFile);
            feed.setFeedPicture(fileName); // 저장된 파일 이름 설정
        } catch (IOException e) {
            throw new RuntimeException("이미지 업로드 중 오류 발생", e);
        }

        feedMapper.addFeed(feed);
    }

	// 음식이름 조회
    @Override
    public String getOrCreateMealNutritionInfo(String mealName) {
        String mealNutritionInfoCode = feedMapper.findByName(mealName);

        if (mealNutritionInfoCode == null) {
            // 새 음식 추가
            MealNutritionInfo newMeal = new MealNutritionInfo();
            newMeal.setMniName(mealName);
            newMeal.setMniPicture("default.jpg"); // 기본 이미지 설정
            feedMapper.addMeal(newMeal);

            return newMeal.getMniNum(); // 생성된 번호 반환
        }

        return mealNutritionInfoCode;
    }
}
	
