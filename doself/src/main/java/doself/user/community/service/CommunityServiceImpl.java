package doself.user.community.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import doself.common.mapper.CommonMapper;
import doself.user.community.domain.Article;
import doself.user.community.domain.Comment;
import doself.user.community.domain.Like;
import doself.user.community.domain.Report;
import doself.user.community.domain.SearchArticle;
import doself.user.community.mapper.CommunityMapper;
import doself.util.PageInfo;
import doself.util.Pageable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CommunityServiceImpl implements CommunityService {
	
	private final CommunityMapper communityMapper;
	private final CommonMapper commonMapper;

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
	public Article getArticleDetail(int articleKeyNum) {
		
		String formattedKeyNum = String.format("fb_%03d", articleKeyNum);
		
		Article articleDetail = communityMapper.getArticleDetail(formattedKeyNum);
		articleDetail.setCommentList(communityMapper.getCommentsByArticle(formattedKeyNum));
		
		return articleDetail;
	}
	
	@Override
	public void createArticle(Article article) {
		// TODO Auto-generated method stub
		
		String formattedKeyNum = commonMapper.getPrimaryKey("fb_", "free_board", "fb_num");
		article.setArticleKeyValue(formattedKeyNum);
		
		String formattedArticleCategory = String.format("fbcate_%03d", article.getArticleCategoryKeyNum());
		article.setArticleCategory(formattedArticleCategory);
		
		if (article.getArticleAttachmentFile() == null) {
			article.setArticleAttachmentFile("");
		}
		
		communityMapper.createArticle(article);
		
	}

	@Override
	public void deleteArticle(int articleKeyNum) {
		// TODO Auto-generated method stub
		String formattedKeyNum = String.format("fb_%03d", articleKeyNum);
		communityMapper.deleteArticle(formattedKeyNum);
		
	}
	
	@Override
	public Like isLiked(Like like) {
		// TODO Auto-generated method stub
		Like likeInfo = communityMapper.isLikedByUser(like);
		
		if(likeInfo != null) return likeInfo;
		
		return null;
	}

	@Override
	public boolean createLikeToArticle(Like like) {
		// TODO Auto-generated method stub
		
		boolean result = false;
		
		String formattedKeyNum = commonMapper.getPrimaryKey("lh_", "like_history", "lh_num");
		like.setLikeKeyNum(formattedKeyNum);
		int insertResult = communityMapper.createLikeToArticle(like);
		
		if (insertResult > 0) {
			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("likeOccurArticleNumValue", like.getLikeOccurArticleNumValue());
			Integer calcLikeCnt = like.getCurrentArticleLikeCnt() + 1;
			params.put("calcLikeCnt", calcLikeCnt);
			
			communityMapper.modifyArticleLikeCnt(params);
			
			result = true;
			
			return result;
		}
		
		return result;
	}

	@Override
	public boolean modifyLikeToArticle(Like like) {
		// TODO Auto-generated method stub
		boolean result = false;
		
		communityMapper.modifyisLiked(like);
		boolean isLiked = Boolean.parseBoolean(like.getIsLiked());
		
		if (isLiked) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("likeOccurArticleNumValue", like.getLikeOccurArticleNumValue());
			Integer calcLikeCnt = like.getCurrentArticleLikeCnt() - 1;
			params.put("calcLikeCnt", calcLikeCnt);
			
			communityMapper.modifyArticleLikeCnt(params);
			result =  true;
			
			
		} else {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("likeOccurArticleNumValue", like.getLikeOccurArticleNumValue());
			Integer calcLikeCnt = like.getCurrentArticleLikeCnt() + 1;
			params.put("calcLikeCnt", calcLikeCnt);
			
			communityMapper.modifyArticleLikeCnt(params);
			result =  true;
		}
		
		return result;
	}

	@Override
	public void createComment(Comment comment) {
		// TODO Auto-generated method stub
		
		String commentKeyValue = commonMapper.getPrimaryKey("fbc_", "free_board_comments", "fbc_num");
		comment.setCommentKeyNum(commentKeyValue);
		String formattedArticleKeyValue = String.format("fb_%03d", comment.getArticleNum());
		comment.setArticleKeyValue(formattedArticleKeyValue);
		
		communityMapper.createComment(comment);
		
	}

	@Override
	public int createReport(Report report) {
		// TODO Auto-generated method stub
		
		String reportKeyValue = commonMapper.getPrimaryKey("rr_", "report_request", "rr_num");
		report.setReportKeyValue(reportKeyValue);
		
		String formattedReportedCateValue = String.format("rc_%03d", report.getReportCateNum());
		report.setReportCateValue(formattedReportedCateValue);
		
		switch (report.getOccurType()) {
			case 1 -> {
				report.setOccurLocationCode("olc_001");
				// 게시글 키값으로 변경
				String formattedReportedKeyValue = String.format("fb_%03d", report.getReportedKeyNum());
				report.setReportedKeyValue(formattedReportedKeyValue);
			}
			case 2 -> {
				report.setOccurLocationCode("olc_002");
				// 댓글 키값으로 변경
				String formattedReportedKeyValue = String.format("fbc_%03d", report.getReportedKeyNum());
				report.setReportedKeyValue(formattedReportedKeyValue);
			}
		}
		
		return communityMapper.createReport(report);
	}


}
