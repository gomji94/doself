package doself.user.feed.controller;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
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
	
	@org.springframework.beans.factory.annotation.Value("${file.path}")
	private String fileRealPath;
	
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
			/* log.info("FeedCode: {}", feed.getFeedCode()); */
	        if (feed.getFeedDate() != null) {
	            feed.setFormattedDate(feed.getFeedDate().format(formatter));
	        }

	        // 각 피드의 링크 생성
	        feed.setOwner(loggedInMemberId.equals(feed.getMemberId()));
	        feed.setFeedUrl("/feed/view?feedCode=" + feed.getFeedCode()); // 고유 URL 생성
	    });

	    for (Feed feed : feedList) {
	    	System.out.println("FeedCode: " + feed.getFeedCode());
	        feed.setOwner(loggedInMemberId.equals(feed.getMemberId()));
	    }

	    model.addAttribute("FeedList", feedList);
	    model.addAttribute("foodNutritionList", foodNutritionList);

	    Feed feed = new Feed(); // 예: 빈 객체 생성 또는 기본 값 설정
	    model.addAttribute("feed", feed);

	    return "user/feed/feed-list";
	}
	
	// 특정 피드 상세 조회
    @GetMapping("/view")
    public String getFeedDetail(@RequestParam(name="feedCode") String feedCode, Model model) {
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
    @GetMapping("/modifyfeed")
    @ResponseBody
    public Feed getFeedModifyData(@RequestParam(name="feedCode") String feedCode) {
    	return feedService.getFeedDetail(feedCode);
    }
	
    // 피드 수정 폼
    @PostMapping("/modifyfeed")
    public String modifyFeed(
        @RequestPart(name = "files", required = false) MultipartFile files,
        @ModelAttribute Feed feed,
        @RequestParam("feedCode") String feedCode,
        HttpSession session
        
    ) {
    	log.info("fileYN:{}, files: {}", files.isEmpty(), files);
    	log.info("fileYN:{}, files: {}", files.isEmpty(), files);
        String memberId = (String) session.getAttribute("SID");
        feed.setMemberId(memberId);
        feed.setFeedCode(feedCode);
        feedService.modifyFeed(feed, files);

        return "redirect:/feed/list?feedCode=" + feedCode;
    }
	
    // 피드 삭제
    @PostMapping("/deletefeed")
    public String deleteFeed(@RequestParam("feedCode") String feedCode,
				    		 @ModelAttribute Feed feed,
				    		 HttpSession session) {
    	String memberId = (String) session.getAttribute("SID");
    	feedService.deleteFeed(feedCode, memberId);
    	
    	return "redirect:/feed/list?feedCode=" + feedCode;
    }
    
	// 피드 댓글 조회
	@GetMapping("/feedcomment")
	@ResponseBody
    public List<Feed> getFeedComment (
    		@RequestParam(value = "feedCode") String feedCode,
    		HttpSession session) {
    	
    	@SuppressWarnings("unused")
		String loggedInMemberId = (String) session.getAttribute("SID");
    	
    	List<Feed> feedCommentList = feedService.getFeedCommentList(feedCode);
    	log.info("Feed Comment List: {}", feedCommentList);
    	
        return feedCommentList;
    }
	
	// 피드 댓글 등록
	@PostMapping("/createfeedcomment")
	public String addFeedComment(@RequestParam("feedCode") String feedCode,
								 @RequestParam("feedCommentContent") String feedCommentContent,
								 Feed feed, HttpSession session) {
	    
		String memberId = (String) session.getAttribute("SID");
		
		Feed comment = new Feed();
		comment.setFeedCode(feedCode);
		comment.setMemberId(memberId);
		comment.setFeedCommentContent(feedCommentContent);
		
		System.out.println("commentContent in Feed: " + comment.getFeedCommentContent());
		
		feedService.addFeedComment(comment);
		
		return "redirect:/feed/list?feedCode=" + feedCode;
	}
	
	// 피드 댓글 수정
	@PostMapping("/modifyfeedcomment")
	@ResponseBody
	public boolean modifyFeedComent(@RequestParam("feedCommentCode") String feedCommentCode,
								   @RequestParam("feedCommentContent") String feedCommentContent,
								   Feed feed) {
		
		boolean isModify = feedService.mofidyFeedComment(feedCommentCode, feedCommentContent);
		
		return isModify;
	}
	
	// 피드 댓글 삭제
	@PostMapping("/deletefeedcomment")
	@ResponseBody
	public boolean deleteFeedComment(@RequestParam("feedCommentCode") String feedCommentCode, Feed feed) {
		
		boolean isDelete = feedService.deleteFeedComment(feedCommentCode);
		
		return isDelete;
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
	/*
	 * @GetMapping("/nutritioninfo") public String getNutritionInfo(Model model) {
	 * model.addAttribute("title", "영양 정보 조회");
	 * 
	 * return "user/feed/nutritioninfo-view"; }
	 */
}
