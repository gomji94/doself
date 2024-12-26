package doself.user.challenge.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/challenge")
public class ChallengeListController {
	@GetMapping("/list")
	public String getChallengeList() {
		return "user/challenge/challenge-list";
	}
	
	@GetMapping("/list/view")
	public String getChallengeListView() {
		return "user/challenge/challenge-list-view";
	}
}
