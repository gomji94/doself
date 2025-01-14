package doself.user.feed.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
	
	// 피드 검색
	@PostMapping("/searchList")
	public String searchListView(Model model) {
		model.addAttribute("title", "피드목록");
		
		return "user/feed/feed-list";
	}
	
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
	@GetMapping("/view")
	public String getDetailsFeed(Model model) {
		model.addAttribute("title", "상세 피드");
		
		return "user/feed/feed-view";
	}
	
	// 피드 수정
	@GetMapping("/modifyfeed")
	public String getModifyFeed(HttpServletRequest request, Model model) {
		model.addAttribute("currentURI", request.getRequestURI());
		model.addAttribute("title", "피드 수정");
		
		return "user/feed/feed-modify";
	}
	
	// 피드 댓글 조회
	@GetMapping("/comments")
    public String getFeedComments(Model model) {
        model.addAttribute("title", "피드 댓글");
        
        return "user/feed/feed-comment";
    }
}
