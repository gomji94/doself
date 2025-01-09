package doself.user.community.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import doself.user.community.domain.Article;
import doself.user.community.domain.SearchArticle;
import doself.user.community.service.CommunityService;
import doself.util.Pageable;
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
	public String getCommunityList(Pageable pageable, Model model) {
		
		var pageInfo = communityService.getArticleList(pageable);
		List<Article> articleList = pageInfo.getContents();
		int currentPage = pageInfo.getCurrentPage();
		int startPageNum = pageInfo.getStartPageNum();
		int endPageNum = pageInfo.getEndPageNum();
		int lastPage = pageInfo.getLastPage();
		
		model.addAttribute("categoryList", communityService.getArticleCategoryList());
		model.addAttribute("articleList", articleList);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("startPageNum", startPageNum);
		model.addAttribute("endPageNum", endPageNum);
		model.addAttribute("lastPage", lastPage);
		
		return "user/community/list";
	}
	
	@GetMapping("/list/category")
	public String getArticleListByCategory(@RequestParam(name = "cateNum") Integer categoryCode, Pageable pageable, Model model) {
		
		var pageInfo = communityService.getArticleListByCategory(pageable, categoryCode);
		List<Article> articleList = pageInfo.getContents();
		int currentPage = pageInfo.getCurrentPage();
		int startPageNum = pageInfo.getStartPageNum();
		int endPageNum = pageInfo.getEndPageNum();
		int lastPage = pageInfo.getLastPage();
		
		model.addAttribute("categoryList", communityService.getArticleCategoryList());
		model.addAttribute("currentCategory", categoryCode);
		model.addAttribute("articleList", articleList);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("startPageNum", startPageNum);
		model.addAttribute("endPageNum", endPageNum);
		model.addAttribute("lastPage", lastPage);
		
		return "user/community/list";
	}
	
	@GetMapping("/list/search")
	public String getArticleListBySearch(SearchArticle searchArticle, Pageable pageable, Model model, 
			@RequestParam(value = "dateFilter" ,defaultValue = "all") String dateFilter, @RequestParam(value = "searchFilter", defaultValue = "title") String searchFilter, 
				@RequestParam(value = "searchValue", required = false) String searchValue) {
		
		String inputSearchFilter = searchArticle.getSearchFilter();
		
		var pageInfo = communityService.getArticleListBySearch(pageable, searchArticle);
		List<Article> articleList = pageInfo.getContents();
		int currentPage = pageInfo.getCurrentPage();
		int startPageNum = pageInfo.getStartPageNum();
		int endPageNum = pageInfo.getEndPageNum();
		int lastPage = pageInfo.getLastPage();
		
		model.addAttribute("categoryList", communityService.getArticleCategoryList());
		model.addAttribute("articleList", articleList);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("startPageNum", startPageNum);
		model.addAttribute("endPageNum", endPageNum);
		model.addAttribute("lastPage", lastPage);
		
		model.addAttribute("dateFilter", searchArticle.getDateFilter());
		model.addAttribute("searchFilter", inputSearchFilter);
		model.addAttribute("searchValue", searchArticle.getSearchValue());
		
		return "user/community/list";
	}
	
	@GetMapping("/view")
	public String getArticleDetail(@RequestParam(name = "articleNum") String articleKeyNum, Model model) {
		
		log.info("articleDetail : {}", communityService.getArticleDetail(articleKeyNum));
		
		model.addAttribute("articleDetail", communityService.getArticleDetail(articleKeyNum));
		
		return "user/community/view";
	}
	
	
	
}
