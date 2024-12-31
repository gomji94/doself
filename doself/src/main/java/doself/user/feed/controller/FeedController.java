package doself.user.feed.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
@RequestMapping("/feed")
public class FeedController {
	
	// 메인 피드 조회
	@GetMapping("/list")
	public String getFeed(Model model) {
		model.addAttribute("title", "피드");
		
		return "user/feed/feed-view";
	}
	
	// 특정 피드 상세 조회
	@GetMapping("/view")
	public String getDetailsFeed(Model model) {
		model.addAttribute("title", "상세 피드");
		
		return "user/feed/detailsfeed-view";
	}
	
	// 피드 만들기
	@GetMapping("/create")
	public String getCreateFeed(Model model) {
		model.addAttribute("title", "피드 만들기");
		
		return "user/feed/feed-create";
	}
	
	// 피드 댓글 조회
	@GetMapping("/comments")
    public String getFeedComments(Model model) {
        model.addAttribute("title", "피드 댓글");
        
        return "user/feed/feed-comment";
    }
}
