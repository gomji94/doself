package doself.user.community.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import doself.user.community.domain.Article;
import doself.user.community.domain.SearchArticle;
import doself.user.community.mapper.CommunityMapper;
import doself.util.PageInfo;
import doself.util.Pageable;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CommunityServiceImpl implements CommunityService {
	
	private final CommunityMapper communityMapper;

	@Override
	public PageInfo<Article> getArticleList(Pageable pageable) {
		// TODO Auto-generated method stub
		int rowCnt = communityMapper.getCntOfArticle();
		List<Article> articleList = communityMapper.getArticleList(pageable);
		return new PageInfo<>(articleList, pageable, rowCnt);
	}

	@Override
	public List<Map<String, String>> getArticleCategoryList() {
		// TODO Auto-generated method stub
		return communityMapper.getArticleCategoryList();
	}

	@Override
	public PageInfo<Article> getArticleListByCategory(Pageable pageable, Integer categoryCode) {
		// TODO Auto-generated method stub
		int rowCnt = communityMapper.getCntOfArticleByCategory(categoryCode);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("categoryCode", categoryCode);
		params.put("pageable", pageable);
		List<Article> articleList = communityMapper.getArticleListByCategory(params);
		return new PageInfo<>(articleList, pageable, rowCnt);
	}

	@Override
	public PageInfo<Article> getArticleListBySearch(Pageable pageable, SearchArticle searchArticle) {
		// TODO Auto-generated method stub
		
		switch (searchArticle.getDateFilter()) {
			case "week" :
				searchArticle.setDays(7);
				break;
			case "month" : 
				searchArticle.setDays(30);
				break;
			case "month3" : 
				searchArticle.setDays(90);
				break;
		}
		
		switch (searchArticle.getSearchFilter()) {
			case "title" -> 
				searchArticle.setSearchFilter("fb.fb_title");
			case "content" ->
				searchArticle.setSearchFilter("fb.fb_content");
			case "id" -> {
				searchArticle.setSearchFilter("fb.mbr_id");
			}
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("searchArticle", searchArticle);
		params.put("pageable", pageable);
		int rowCnt = communityMapper.getCntOfArticleBySearch(params);
		List<Article> articleList = communityMapper.getArticleListBySearch(params);
		
		return new PageInfo<>(articleList, pageable, rowCnt);
	}
	
	@Override
	public Article getArticleDetail(String articleKeyNum) {
		
		int castKeyNum = Integer.parseInt(articleKeyNum);
		String formattedKeyNum = String.format("fb_%03d", castKeyNum);
		
		return communityMapper.getArticleDetail(formattedKeyNum);
	}

}
