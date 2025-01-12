package doself.user.challenge.feed.service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import doself.user.challenge.feed.domain.ChallengeFeed;
import doself.user.challenge.feed.domain.ChallengeMemberList;
import doself.user.challenge.feed.domain.ChallengeProgress;
import doself.user.challenge.feed.mapper.ChallengeFeedMapper;
import doself.util.FeedPageInfo;
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
	// 요청 데이터 한번만 가져오도록 캐싱 적용
	private final ConcurrentMap<String, List<ChallengeProgress>> challengeProgressCache = new ConcurrentHashMap<>();
	
	// 로그인된 챌린지 멤버 아이디
	@Override
	public String getChallengeCodeByMemberId(String challengeMemberId) {
	    return challengeFeedMapper.getChallengeCodeByMemberId(challengeMemberId);
	}
	
	// 챌린지 피드
	@Override
	public FeedPageInfo<ChallengeFeed> getChallengeFeed(Map<String, Object> params) {
		// 데이터 가져오기
		List<ChallengeFeed> challengeFeedList = challengeFeedMapper.getChallengeFeed(params);
		int totalCount = challengeFeedMapper.getChallengeFeedCount((String) params.get("challengeCode"));

        // 페이징 정보 생성
		int currentPage = (int) params.getOrDefault("currentPage", 1);
		int pageSize = (int) params.getOrDefault("pageSize", 10);
	    
	    if (currentPage < 0) {
	        params.put("offset", 0); // offset이 음수일 경우 0으로 설정
	    }

	    if (pageSize <= 0) {
	        params.put("pageSize", 10); // pageSize가 유효하지 않을 경우 기본값 설정
	    }
	    
	    return new FeedPageInfo<>(challengeFeedList, totalCount, currentPage, pageSize);
	}

	// 챌린지 진행 상태 조회
	
	
	// 총 업로드 데이터 합계
	@Override
	public int calculateTotalProgress(String challengeCode) {
		return challengeFeedMapper.getTodayProgressSum(challengeCode) / 14;
	}

	// 참여 멤버 상위 3명 표시
	@Override
    public List<ChallengeMemberList> getTopParticipants(String challengeCode) {
        return challengeFeedMapper.getTopParticipants(challengeCode);
    }

//	// D+ 계산
//	@Override
//	public String calculateDPlus(String challengeCode) {
//		return challengeFeedMapper.findDPlus(challengeCode);
//    }
//
//	// D- 계산
//	@Override
//	public String calculateDMinus(String challengeCode) {
//		return challengeFeedMapper.findDMinus(challengeCode);
//    }
	
	// 챌린지 멤버 조회(참여자 번호, 챌린지 번호, 챌린지 참여 및 퇴장 일시 기록 → 추후, 유효성 검사 로직 추가)
	@Override
	public List<ChallengeMemberList> getMemberList(String challengeCode) {
		List<ChallengeMemberList> memberList = challengeFeedMapper.getMemberList(challengeCode);
	    log.info("Fetched memberList from Mapper: {}", memberList); // Mapper에서 가져온 데이터 확인
	    return memberList;
	}

	@Override
	public List<ChallengeProgress> getProcessChallengeStatus(String challengeCode, String challengeStatus) {
		// 캐시 내 데이터 여부 확인
		if (challengeProgressCache.containsKey(challengeCode)) {
	        return challengeProgressCache.get(challengeCode); // 캐시된 데이터 반환
	    }
		
        // 데이터 가져오기
		List<ChallengeProgress> challengeProgress = challengeFeedMapper.getChallengeProgress(challengeCode);

	    // 중복 제거 후 캐시에 저장
		List<ChallengeProgress> distinctProgress = challengeProgress.stream()
		        .distinct() // 중복 제거
		        .collect(Collectors.toList());
//	    List<ChallengeProgress> distinctProgress = challengeProgress.stream()
//	        .distinct()
//	        .collect(Collectors.toList());
//	    challengeProgressCache.put(challengeCode, distinctProgress);

	    return distinctProgress;
	}
	
	// 불필요한 캐시 초기화
	public void clearChallengeProgressCache(String challengeCode) {
	    challengeProgressCache.remove(challengeCode);
	}
}
