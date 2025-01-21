package doself.user.feed.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import doself.admin.nutrition.service.NutritionService;
import doself.user.feed.domain.Feed;
import doself.user.feed.service.FeedService;
import doself.util.Pageable;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Controller
@RequestMapping("/feed")
@RequiredArgsConstructor
@Slf4j
public class FeedController {
	
	private final FeedService feedService;
	private final NutritionService nutritionService;
	
	// 메인 피드 조회
	@GetMapping("/list")
	public String getFeedList(HttpSession session, Model model, Pageable pageable) {
	    String loggedInMemberId = (String) session.getAttribute("SID");
	    
		List<Feed> feedList = feedService.getFeedList();
		pageable.setRowPerPage(100);
		var foodNutrition = nutritionService.getFoodNutritionList("mniName", "", pageable);
		var foodNutritionList = foodNutrition.getContents();
		
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		
		    feedList.forEach(feed -> {
		        if (feed.getFeedDate() != null) {
		            feed.setFormattedDate(feed.getFeedDate().format(formatter));
		        }
		    });
	    
	    for (Feed feed : feedList) {
	        feed.setOwner(loggedInMemberId.equals(feed.getMemberId()));
	    }
		model.addAttribute("FeedList", feedList);
		model.addAttribute("foodNutritionList", foodNutritionList);
		
		Feed feed = new Feed(); // 예: 빈 객체 생성 또는 기본 값 설정
	    model.addAttribute("feed", feed);
	    
		return "user/feed/feed-list";
	}
	
	// 특정 피드 상세 조회
    @GetMapping("/{feedCode}")
    public String getFeedDetail(@PathVariable String feedCode, Model model) {
        Feed feed = feedService.getFeedDetail(feedCode);

        model.addAttribute("feed", feed);
        return "user/feed/feed-view";
    }
    
    
    // 피드 추가
    @PostMapping("/createFeed")
    public String createFeed(@ModelAttribute Feed feed,
				             @RequestParam(value="files", required = false) MultipartFile feedPicture, // Optional 처리
				             HttpSession session,
				             Model model) {
    	if (feedPicture == null || feedPicture.isEmpty()) {
            model.addAttribute("errorMessage", "사진을 업로드하세요.");
            return "user/feed/feed-create";
        }
        feed.setMemberId((String) session.getAttribute("SID"));
        feedService.addFeed(feed, feedPicture);
        return "redirect:/feed/list";
    }
    
	// 피드 수정 모달 열기
    @GetMapping("/modifyfeed/{feedCode}")
    public String getModifyFeed(@PathVariable String feedCode, Model model) {
        Feed feed = feedService.getFeedDetail(feedCode);
        if (feed == null) {
            throw new RuntimeException("해당 피드 정보를 찾을 수 없습니다.");
        }
        
        if (feed.getFeedIntakeDate() == null) {
            feed.setFeedIntakeDate(LocalDateTime.now());
        }
        model.addAttribute("feed", feed);
        return "user/feed/feed-modify";
    }
	
	// 피드 수정
	@PostMapping("/modifyfeed")
	public String modifyFeed(@ModelAttribute Feed feed) {
		try {
	        feedService.modifyFeed(feed);
	        return "redirect:/user/feed/" + feed.getFeedCode();
	    } catch (Exception e) {
	        // 에러 처리 로직
	        e.printStackTrace();
	        return "redirect:/error";
	    }
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
	
	// 음식 영양 정보 조회
	@GetMapping("/nutritioninfo")
    public String getNutritionInfo(Model model) {
		model.addAttribute("title", "영양 정보 조회");
		
		return "user/feed/nutritioninfo-view";
	}
	
	// 피드 댓글 추가
}
