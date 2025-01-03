package doself.user.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import doself.user.community.service.CommunityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.RequestParam;



@Controller
@RequestMapping("/community")
@RequiredArgsConstructor
@Slf4j
public class CommunityController {
	
	private final CommunityService communityService;
	
	@GetMapping("/list")
	public String getCommunityList(Model model) {
		model.addAttribute("categoryList", communityService.getArticleCategoryList());
		model.addAttribute("articleList", communityService.getArticleList());
		
		return "user/community/list";
	}
	
	@GetMapping("/list/category")
	public String getArticleListByCategory(@RequestParam(name = "cateNum") String categoryCode, Model model) {
		
		model.addAttribute("categoryList", communityService.getArticleCategoryList());
		model.addAttribute("articleList", communityService.getArticleListByCategory(categoryCode));
		
		return "user/community/list";
	}
	
	@GetMapping("/view")
	public String getArticleDetail(@RequestParam(name = "articleNum") String articleKeyNum, Model model) {
		model.addAttribute("articleDetail", communityService.getArticleDetail(articleKeyNum));
		
		return "user/community/view";
	}
	
	
	
}
