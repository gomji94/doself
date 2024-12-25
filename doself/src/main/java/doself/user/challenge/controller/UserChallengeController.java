package doself.user.challenge.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@RequestMapping("/user/list")
public class UserChallengeController {
	@GetMapping(value={"","/"})
	public String defaultTest() {
		return "user/challenge-feed-index";
	}
	
}
