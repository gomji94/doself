package doself.user.challenge.feed.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import doself.user.challenge.feed.domain.ChallengeFeed;
import doself.user.challenge.feed.domain.ChallengeFeedComment;
import doself.user.challenge.feed.domain.ChallengeMemberList;
import doself.user.challenge.feed.domain.ChallengeProgress;
import doself.user.challenge.feed.mapper.ChallengeFeedMapper;
import doself.util.PageInfo;
import doself.util.Pageable;
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
	
	// 피드 페이징
	@Override
	public PageInfo<ChallengeFeed> getChallengeFeedPage(String challengeCode, Pageable pageable) {
		int rowCnt = challengeFeedMapper.getChallengeFeedCount(challengeCode);
		Map<String, Object> params = new HashMap<>();
		params.put("challengeCode", challengeCode);
		params.put("pageable", pageable);
		List<ChallengeFeed> challengeFeedList = challengeFeedMapper.getChallengeFeed(params);
		
		
		return new PageInfo<>(challengeFeedList, pageable, rowCnt);
	}
	
	// 챌린지 피드
	@Override
	public List<ChallengeFeed> getChallengeFeed(Map<String, Object> params) {
        return challengeFeedMapper.getChallengeFeed(params);
    }
	
	// 총 업로드 데이터 합계
	@Override
	public int calculateTotalProgress(String challengeCode) {
		return challengeFeedMapper.getTodayProgressSum(challengeCode);
	}

	// 참여 멤버 상위 3명 표시
	@Override
    public List<ChallengeMemberList> getTopParticipants(String challengeCode) {
        return challengeFeedMapper.getTopParticipants(challengeCode);
    }

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

	    return distinctProgress;
	}
	
	// 불필요한 캐시 초기화
	public void clearChallengeProgressCache(String challengeCode) {
	    challengeProgressCache.remove(challengeCode);
	}

	@Override
	public Map<String, String> calculateDPlusAndDMinus(String challengeCode) {
		ChallengeProgress progress = challengeFeedMapper.getChallengeProgressByCode(challengeCode);
        if (progress == null) {
            return Map.of("dPlus", "N/A", "dMinus", "N/A");
        }

        LocalDate today = LocalDate.now();
        LocalDate startDate = progress.getChallengeStartDate().toInstant()
                                      .atZone(ZoneId.of("Asia/Seoul"))
                                      .toLocalDate();
        LocalDate endDate = progress.getChallengeEndDate().toInstant()
                                    .atZone(ZoneId.of("Asia/Seoul"))
                                    .toLocalDate();

        long dPlus = ChronoUnit.DAYS.between(startDate, today);
        long dMinus = ChronoUnit.DAYS.between(today, endDate);

        log.info("Start Date from progress: {}", progress.getChallengeStartDate());
        log.info("End Date from progress: {}", progress.getChallengeEndDate());
        
        
        return Map.of(
            "dPlus", "D+" + Math.max(0, dPlus),
            "dMinus", "D-" + Math.max(0, dMinus)
        );
	}

	// 챌린지 피드별 댓글 리스트
	@Override
	public List<ChallengeFeedComment> getFeedCommentList(String challengeFeedCode) {
		List<ChallengeFeedComment> feedCommentList = challengeFeedMapper.getFeedCommentList(challengeFeedCode);
		return feedCommentList;
	}

}
