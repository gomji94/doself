package doself.user.challenge.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@RequestMapping("/feed")
public class ChallengeFeedController {
	@GetMapping("/challengeview")
	public String getChallengeView() {
		return "user/challenge/challenge-view";
	}
	
	@GetMapping("/memberlist")
	public String getMemberList() {
		return "user/challenge/member-list";
	}
	
	@GetMapping("/warning")
	public String getMemberWarnig() {
		return "user/challenge/challenge-member-warning";
	}
	
	@GetMapping("/feedcomment")
	public String getFeedComment() {
		return "user/challenge/feed-comment";
	}
	
	@GetMapping("/createchallenge")
	public String getCreateChallenge() {
		return "user/challenge/create-challenge";
	}
	
	@GetMapping("/modifychallengefeed")
	public String getModifyChallengeFeed() {
		return "user/challenge/modify-challenge-feed";
	}
}
