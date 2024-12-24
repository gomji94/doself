package doself.user.challenge.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@RequestMapping("/user")
public class ChallengeController {
	@GetMapping(value={"","/"})
	public String defaultTest() {
		return "user/index";
	}
	
}
