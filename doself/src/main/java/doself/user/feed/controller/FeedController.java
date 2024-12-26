package doself.user.feed.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
@RequestMapping("/user/feed")
public class FeedController {
	
	@GetMapping("/view")
	public String getFeed(Model model) {
		model.addAttribute("title", "피드");
		
		return "user/feed/feed-view";
	}
	
	@GetMapping("/detailsview")
	public String getDetailsFeed(Model model) {
		model.addAttribute("title", "상세 피드");
		
		return "user/feed/detailsfeed_view";
	}
	
	@GetMapping("/createfeed")
	public String getcreatefeed(Model model) {
		model.addAttribute("title", "피드 만들기");
		
		return "user/feed/feed_create";
	}
}
