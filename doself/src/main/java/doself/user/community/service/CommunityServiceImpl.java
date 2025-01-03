package doself.user.community.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import doself.user.community.domain.Article;
import doself.user.community.mapper.CommunityMapper;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CommunityServiceImpl implements CommunityService {
	
	private final CommunityMapper communityMapper;

	@Override
	public List<Article> getArticleList() {
		// TODO Auto-generated method stub
		return communityMapper.getArticleList();
	}

	@Override
	public List<Map<String, String>> getArticleCategoryList() {
		// TODO Auto-generated method stub
		return communityMapper.getArticleCategoryList();
	}

	@Override
	public List<Article> getArticleListByCategory(String categoryCode) {
		// TODO Auto-generated method stub
		return communityMapper.getArticleListByCategory(categoryCode);
	}

}
