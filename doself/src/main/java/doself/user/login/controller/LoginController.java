package doself.user.login.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import doself.admin.member.domain.Member;
import doself.common.mapper.CommonMapper;
import doself.user.login.mapper.LoginMapper;
import doself.user.login.service.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Controller
@Slf4j
@RequiredArgsConstructor
public class LoginController {

	private final LoginService loginService;
	private final LoginMapper loginMapper;
	private final CommonMapper commonMapper;
	
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		
		// 세션내용 초기화
		session.invalidate();
		
		return "redirect:/login";
	}
	
	@PostMapping("/login/loginPro")
	public String loginProcess(@RequestParam(value="mbrId") String mbrId,
			   @RequestParam(value="mbrPw") String mbrPw,
			   RedirectAttributes reAttr, HttpSession session,HttpServletRequest request) {
		
		String viewName = "redirect:/login";
		
		
		  Map<String, Object> resultMap = loginService.matchedMember(mbrId, mbrPw); 
		  boolean isMatched = (boolean) resultMap.get("isMatched");
		  
		  if(isMatched) { 
			  Member memberInfo = (Member) resultMap.get("memberInfo");
			  String membergrade = memberInfo.getMgCode(); 
			  String memberName = memberInfo.getMbrName(); 
			  String memberImage = memberInfo.getMbrImage();
			  
			  String memberIp = request.getRemoteAddr();
			  // 로그인이력 키값생성
			  String memberIpKey = commonMapper.getPrimaryKey("mll_", "member_login_log", "mll_num");
			  loginMapper.createMemberLoginLog(memberIpKey, mbrId, memberIp);
		  
			  session.setAttribute("SID", mbrId);
			  session.setAttribute("SNAME", memberName);
			  session.setAttribute("SGRD", membergrade);
			  session.setAttribute("IMAGE", memberImage);
			  
			  if(membergrade.equals("mg_001")) {
				  viewName = "redirect:/admin/member/list";
			  }else if(membergrade.equals("mg_002") || membergrade.equals("mg_003")) {
				  viewName = "redirect:/feed/list";				 
			  }
		  }else { 
			  reAttr.addAttribute("msg", "회원의 정보가 일치하지 않거나 탈퇴한 계정입니다."); 
		  }

		return viewName;		
	}
	
	@GetMapping("/login")
	public String loginView(Model model, @RequestParam(value="msg", required= false) String msg) {
		
		model.addAttribute("title", "로그인페이지");
		if(msg != null) model.addAttribute("msg", msg);
		return "loginpage";
	}
	
	@GetMapping("/register")
	public String postMethodName() {		
		
		return "register";
	}
	
	@PostMapping("/register")
	public String postMethodName(Member member) {
		
		loginService.createMember(member);
		
		return "redirect:/login";
	}
	
	
	
}
