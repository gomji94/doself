package doself.user.feed.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import doself.user.feed.domain.Feed;
import doself.user.feed.service.FeedService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Controller
@RequestMapping("/feed")
@RequiredArgsConstructor
@Slf4j
public class FeedController {
	
	private final FeedService feedService;
	
	// 메인 피드 조회
	@GetMapping("/list")
	public String getFeedList(HttpSession session, Model model) {

		// 로그인된 사용자 정보 확인
	    String loggedInMemberId = (String) session.getAttribute("SID");
	    
		List<Feed> feedList = feedService.getFeedList();
		
		// 본인 피드 여부 설정
	    for (Feed feed : feedList) {
	        feed.setOwner(loggedInMemberId.equals(feed.getMemberId()));
	    }
		model.addAttribute("FeedList", feedList);
		return "user/feed/feed-list";
	}
	
	// 특정 피드 상세 조회
    @GetMapping("/{feedCode}")
    public String getFeedDetail(@PathVariable String feedCode, Model model) {
        log.info("Fetching feed detail for feedCode: {}", feedCode);

        Feed feed = feedService.getFeedDetail(feedCode);
        if (feed == null) {
            throw new RuntimeException("피드 정보를 찾을 수 없습니다.");
        }

        model.addAttribute("feed", feed); // 모델에 피드 데이터를 추가
        return "user/feed/feed-view"; // 상세 정보 페이지로 이동
    }
    
    // 음식 이름 검색
    @GetMapping("/search-food")
    @ResponseBody
    public List<String> searchFood(@RequestParam("query") String query) {
        return feedService.findKeywords(query);
    }
    
	// 피드 추가
    @PostMapping("/createFeed")
    public ResponseEntity<String> createFeed(@RequestParam Map<String, String> feedData,
                                             @RequestParam MultipartFile feedPicture) {
        Feed feed = new Feed();
        feed.setMemberId(feedData.get("memberId")); // 로그인 사용자 ID
        feed.setMealNutritionInfoCode(feedData.get("mealNutritionInfoCode"));
        feed.setFeedFoodIntake(Integer.parseInt(feedData.get("feedFoodIntake")));
        feed.setFeedContent(feedData.get("feedContent"));
        feed.setMealCategoryCode(feedData.get("mealCategoryCode"));
        feed.setFeedOpenStatus(feedData.get("feedOpenStatus").equals("public") ? 1 : 0);
        feed.setFeedIntakeDate(LocalDateTime.parse(feedData.get("intakeDateTime")));

        feedService.addFeed(feed, feedPicture);
        return ResponseEntity.ok("피드 생성 성공");
    }
    
	// 피드 수정 모달 열기
	@GetMapping("/modifyfeed")
	public String getModifyFeed(HttpServletRequest request, Model model) {
		model.addAttribute("currentURI", request.getRequestURI());
		model.addAttribute("title", "피드 수정");
		
		return "user/feed/feed-modify";
	}
	
	// 피드 수정
	@PostMapping("/modifyfeed")
	public String modifyFeed(Feed feed) {
		
		return "redirect:/feed/feed-list";
	}
	
	// 피드 댓글 조회
	@GetMapping("/{feedCode}/comments")
    public String getFeedComments(Model model) {
        model.addAttribute("title", "피드 댓글");
        
        return "user/feed/feed-comment";
    }
	
	// 피드 좋아요 증감
	@PostMapping("/like")
	public ResponseEntity<String> toggleLike(@RequestBody Map<String, Object> payload) {
        String feedNum = (String) payload.get("feedNum");
        Boolean liked = (Boolean) payload.get("liked");

        try {
            if (liked) {
                feedService.incrementLike(feedNum);
            } else {
                feedService.decrementLike(feedNum);
            }
            return ResponseEntity.ok("좋아요 상태가 업데이트되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("좋아요 상태 업데이트 중 오류 발생.");
        }
    }
	
	@GetMapping("/nutritioninfo")
    public String getNutritionInfo(Model model) {
		model.addAttribute("title", "영양 정보 조회");
		
		return "user/feed/nutritioninfo-view";
	}
}
