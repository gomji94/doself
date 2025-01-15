package doself.user.feed.controller;

import java.io.File;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import doself.user.feed.domain.Feed;
import doself.user.feed.service.FeedService;
import jakarta.servlet.http.HttpServletRequest;
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
	public String getFeedList(Model model) {
		
		List<Feed> feedList = feedService.getFeedList();
		System.out.println(feedList);
		log.info("Fetched feed list: {}", feedList); // 로그 추가
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
    public ResponseEntity<?> addFeed(
            @RequestParam("feedPicture") MultipartFile feedPicture,
            @RequestParam("feedContent") String feedContent,
            @RequestParam("searchedFood") String searchedFood,
            @RequestParam("feedFoodIntake") Integer feedFoodIntake,
            @RequestParam("mealCategoryCode") String mealCategoryCode,
            @RequestParam("feedOpenStatus") Integer feedOpenStatus,
            HttpServletRequest request) {

        try {
            // 유효성 검사
            if (feedPicture == null || feedPicture.isEmpty()) {
                return ResponseEntity.badRequest().body("사진을 업로드해주세요.");
            }
            if (feedContent == null || feedContent.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("내용을 작성해주세요.");
            }
            if (searchedFood == null || searchedFood.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("음식 이름을 검색하고 선택해주세요.");
            }
            if (feedFoodIntake == null || feedFoodIntake <= 0) {
                return ResponseEntity.badRequest().body("섭취 인분을 선택해주세요.");
            }
            if (mealCategoryCode == null || mealCategoryCode.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("식사 분류를 선택해주세요.");
            }
            if (feedOpenStatus == null) {
                return ResponseEntity.badRequest().body("공개 여부를 선택해주세요.");
            }

            // 현재 로그인된 사용자 정보 가져오기
            String memberId = (String) request.getSession().getAttribute("memberId");
            String profileImage = (String) request.getSession().getAttribute("profileImage");

            // Feed 객체 생성
            Feed feed = new Feed();
            feed.setMemberId(memberId);
            feed.setFeedPicture(feedPicture.getOriginalFilename());
            feed.setFeedContent(feedContent);
            feed.setFeedFoodIntake(feedFoodIntake);
            feed.setMealCategoryCode(mealCategoryCode);
            feed.setFeedOpenStatus(feedOpenStatus);

            // 음식 정보 처리 (없으면 추가하고 번호 반환)
            String mealNutritionInfoCode = feedService.getOrCreateMealNutritionInfo(searchedFood);
            feed.setMealNutritionInfoCode(mealNutritionInfoCode);

            // 프로필 이미지 설정
            feed.setMemberProfileImage(profileImage);

            // 피드 생성
            feedService.addFeed(feed, feedPicture);

            return ResponseEntity.ok("피드가 성공적으로 생성되었습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("피드 생성 중 오류 발생");
        }
    }
    
	// 피드 수정
	@GetMapping("/modifyfeed")
	public String getModifyFeed(HttpServletRequest request, Model model) {
		model.addAttribute("currentURI", request.getRequestURI());
		model.addAttribute("title", "피드 수정");
		
		return "user/feed/feed-modify";
	}
	
	// 피드 댓글 조회
	@GetMapping("/{feedCode}/comments")
    public String getFeedComments(Model model) {
        model.addAttribute("title", "피드 댓글");
        
        return "user/feed/feed-comment";
    }
	
	// 자동완성 검색
}
