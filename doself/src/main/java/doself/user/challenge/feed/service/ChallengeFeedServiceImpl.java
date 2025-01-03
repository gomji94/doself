package doself.user.challenge.feed.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import doself.user.challenge.feed.domain.ChallengeFeed;
import doself.user.challenge.feed.mapper.ChallengeFeedMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
//jdbc가 추가된 상태에서만 트랜잭셔널 어노테이션 실행됨
//전체 적용하려면 여기에 작성 || 특정 메소드에만 설정하고 싶으면 메소드 상단에 배치
@Transactional
//log.이 가능한 이유는 아래의 어노테이션 때문
@RequiredArgsConstructor
@Slf4j
public class ChallengeFeedServiceImpl implements ChallengeFeedService {

	private final ChallengeFeedMapper challengeFeedMapper;
	
	@Override
	public List<ChallengeFeed> getChallengeFeedList(int page, int pageSize) {
		List<ChallengeFeed> challengeFeed = challengeFeedMapper.getChallengeFeedList(page, pageSize);
		if (challengeFeed == null || challengeFeed.isEmpty()) {
	        log.warn("챌린지 피드 정보가 없습니다.");
	        return challengeFeed;
	    }
		
		// 챌린지 번호와 챌린지 피드 번호가 일치하는 것만 조회되게 함
		challengeFeed.forEach(challengeFeedInfo -> {
			String feedMatched = challengeFeedInfo.getChallengeCode();
			if(feedMatched == challengeFeedInfo.getChallengeFeedCode());
		});
		
	    int offset = (page - 1) * pageSize;
	    return challengeFeedMapper.getChallengeFeedList(offset, pageSize);
	}

}
