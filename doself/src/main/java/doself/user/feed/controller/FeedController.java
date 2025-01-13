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
    
	// 피드 추가
    @PostMapping("/createFeed")
    public ResponseEntity<String> addFeed(
            @RequestParam("feedPicture") MultipartFile feedPicture,
            @RequestParam("feedContent") String feedContent,
            @RequestParam("feedFoodIntake") Integer feedFoodIntake,
            @RequestParam("mealCategoryCode") String mealCategoryCode,
            @RequestParam("feedOpenStatus") Integer feedOpenStatus) {
        try {
            // 유효성 검사
            if (feedPicture == null || feedPicture.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("사진을 업로드해주세요.");
            }

            if (feedContent == null || feedContent.trim().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("내용을 작성해주세요.");
            }

            if (feedFoodIntake == null || feedFoodIntake <= 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("섭취 인분을 선택해주세요.");
            }

            if (mealCategoryCode == null || mealCategoryCode.trim().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("식사 분류를 선택해주세요.");
            }

            // 파일 저장 처리 (임시)
            String filePath = "/uploads/" + feedPicture.getOriginalFilename();
            feedPicture.transferTo(new File(filePath));

            // Feed 객체 생성 및 저장
            Feed feed = new Feed();
            feed.setFeedPicture(filePath);
            feed.setFeedContent(feedContent);
            feed.setFeedFoodIntake(feedFoodIntake);
            feed.setMealCategoryCode(mealCategoryCode);
            feed.setFeedOpenStatus(feedOpenStatus);

            feedService.addFeed(feed);
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
	@GetMapping("/keywords")
    public List<String> getKeywords(@RequestParam("query") String query) {
        return feedService.getKeywords(query);
    }
}
