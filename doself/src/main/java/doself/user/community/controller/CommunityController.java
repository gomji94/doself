package doself.user.community.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import doself.user.community.domain.Article;
import doself.user.community.domain.Comment;
import doself.user.community.domain.Like;
import doself.user.community.domain.Report;
import doself.user.community.domain.SearchArticle;
import doself.user.community.mapper.CommunityMapper;
import doself.user.community.service.CommunityService;
import doself.util.Pageable;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequestMapping("/community")
@RequiredArgsConstructor
@Slf4j
public class CommunityController {
	
	private final CommunityService communityService;
	private final CommunityMapper communityMapper;
	
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
	public String getArticleDetail(@RequestParam(name = "articleNum") int articleKeyNum, Model model) {
		
		// log.info("================> articleDetail : {}", communityService.getArticleDetail(articleKeyNum));
		
		model.addAttribute("articleDetail", communityService.getArticleDetail(articleKeyNum));
		
		return "user/community/view";
	}
	
	@GetMapping("/create")
	public String createArticle() {
		return "user/community/create";
	}
	
	@PostMapping("/create")
	public String createArticle(Article article, HttpSession session) {
		//TODO: process POST request
		
		article.setArticleAuthorId((String) session.getAttribute("SID"));
		
		communityService.createArticle(article);
		
		return "user/community/list";
	}
	
	
	@PostMapping("/delete")
	public String deleteArticle(@RequestParam(name = "articleNum") int articleKeyNum) {
		//TODO: process POST request
		communityService.deleteArticle(articleKeyNum);
		return "redirect:/community/list";
	}
	
	
	@PostMapping("/like")
	@ResponseBody
	public boolean createLikeToArticle(Like like, HttpSession session) {
		//TODO: process POST request
		
		like.setLikeMemberId((String) session.getAttribute("SID"));
		// 발생위치 게시글 넘버
		String formattedArticleNum = String.format("fb_%03d", like.getLikeOccurArticleNum());
		like.setLikeOccurArticleNumValue(formattedArticleNum);
		
		// 좋아요 여부 객체
		Like isLikedResult = communityService.isLiked(like);
		
		// 좋아요 객체가 null 일시
		if (isLikedResult == null) {
			// 좋아요 데이터 생성
			communityService.createLikeToArticle(like);
		} else {
			// 좋아요 +1 or 좋아요 취소
			like.setIsLiked(isLikedResult.getIsLiked());
			like.setLikeKeyNum(isLikedResult.getLikeKeyNum());
			communityService.modifyLikeToArticle(like);
		}
		
		return true;
	}
	
	@PostMapping("/createcomment")
	public String createComment(Comment comment, @RequestParam(name = "articleNum") String articleNum, HttpSession session, RedirectAttributes reAttr) {
		//TODO: process POST request
		comment.setCommentAuthorId((String) session.getAttribute("SID"));
		
		communityService.createComment(comment);
		
		reAttr.addAttribute("articleNum", articleNum);
		
		return "redirect:/community/view";
	}
	
	@PostMapping("/deletecomment")
	public String deleteComment(@RequestParam(name = "commentKeyNum") String commentKeyNum, @RequestParam(name = "articleNum") String articleNum, RedirectAttributes reAttr) {
		//TODO: process POST request
		
		communityMapper.deleteComment(commentKeyNum);
		
		reAttr.addAttribute("articleNum", articleNum);
		
		return "redirect:/community/view";
	}
	
	@PostMapping("modifycomment")
	@ResponseBody
	public Map<String, Boolean> modifyComment(Comment comment) {
		//TODO: process POST request
		
	    Map<String, Boolean> resultMap = new HashMap<>();
	    resultMap.put("success", communityMapper.modifyComment(comment) > 0);
	    return resultMap;
		
	}
	
	@PostMapping("/createreport")
	@ResponseBody
	public boolean createReport(Report report, HttpSession session) {
		//TODO: process POST request
		
		report.setReporterId((String) session.getAttribute("SID"));
		
		boolean isReg = false;
		int result = communityService.createReport(report);
		if(result > 0) isReg = true; 
		
		return isReg;
	}
	
	
	
}
