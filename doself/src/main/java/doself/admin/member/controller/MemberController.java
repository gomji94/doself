package doself.admin.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/member")
public class MemberController {
	
	@GetMapping("/memberList")
	public String memberListView(Model model) {
		
		model.addAttribute("title", "회원목록");
		return "admin/member/memberList";
	}
}
