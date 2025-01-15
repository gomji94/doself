package doself.user.challenge.list.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import doself.common.mapper.CommonMapper;
import doself.user.challenge.list.domain.AddChallenge;
import doself.user.challenge.list.domain.ChallengeDetailView;
import doself.user.challenge.list.domain.ChallengeList;
import doself.user.challenge.list.mapper.ChallengeListMapper;
import doself.util.CardPageInfo;
import doself.util.CardPageable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
//jdbc가 추가된 상태에서만 트랜잭셔널 어노테이션 실행됨
//전체 적용하려면 여기에 작성 || 특정 메소드에만 설정하고 싶으면 메소드 상단에 배치
@Transactional
//log.이 가능한 이유는 아래의 어노테이션 때문
@RequiredArgsConstructor
@Slf4j
public class ChallengeListServiceImpl implements ChallengeListService {

	private final ChallengeListMapper challengeListMapper;
	private final CommonMapper commonMapper;
	
	// 챌린지 조회 리스트 반환
	@Override
	public List<ChallengeList> getChallengeList() {
		List<ChallengeList> challengeList = challengeListMapper.getChallengeList();
		if (challengeList == null || challengeList.isEmpty()) {
	        //log.warn("챌린지 리스트 정보가 없습니다.");
	        return challengeList;
	    }
		
		// 현재 진행중인 챌린지만 조회되게 함(cs_status → 진행중/진행중(리더 양도))
		challengeList.forEach(challengeInfo -> {
	        String challengeStatus = challengeInfo.getChallengeStatus();
	        if("cs_002".equals(challengeStatus) || "cs_003".equals(challengeStatus));
	    });
		return challengeList;
	}

	// 특정 챌린지 카드 조회
	@Override
	public ChallengeDetailView getChallengeListView(String challengeCode) {
		return challengeListMapper.selectChallengeDetail(challengeCode);
	}
//	public List<ChallengeDetailView> getChallengeListView(String challengeCode) {
//		List<ChallengeDetailView> challengeListDetail = challengeListMapper.getChallengeListView(challengeCode);
//	    log.info(">>> location/service >>> challengeListDetail: {}", challengeListDetail);
//	    return challengeListDetail;
//	}

	// 챌린지 생성(등록)
	@Override
	public void addChallenge(AddChallenge addChallenge) {
		List<AddChallenge> addChallengeList = challengeListMapper.addChallengeList();
		// 코드 번호 생성 앞자리, 테이블명, 컬럼명
		String formattedKeyNum = commonMapper.getPrimaryKey("cg_", "challenge_group", "cg_num");
		addChallenge.setChallengeCode(formattedKeyNum);
		
		//String addChallengeResult = String.format("ctl_%03d", challengeList.getChallengeTopicCode());
		//challengeList.setChallengeTopicCode(addChallengeResult);
		
		//challengeList.setChallengeEndDate(challengeList.getChallengeStartDate());
		
		//log.info(">>> location/service >>> challengeList : {}", challengeList);
		
		challengeListMapper.addChallenge(addChallenge);
	}

	// 챌린지 리스트 페이지
	@Override
	public CardPageInfo<ChallengeList> getChallengeList(CardPageable cardPageable) {
	    int rowCnt = challengeListMapper.getCntChallengeList();
	    List<ChallengeList> challengeList = challengeListMapper.getChallengeList(cardPageable);
	    
	    //log.info(">>> location/service >>> challengeList : {}", challengeList);
	    
	    return new CardPageInfo<>(challengeList, cardPageable, rowCnt);
	}

	// 챌린지 생성 시, 이름 중복 체크
	@Override
	public boolean isNameDuplicate(String challengeName) {
		return challengeListMapper.isNameDuplicate(challengeName);
	}

	// 챌린지 주제 리스트
	@Override
	public List<Map<String, String>> getChallengeTopicList() {
		List<Map<String, String>> topicList = challengeListMapper.getChallengeTopicList();
	    log.info(">>> location/service >>> topicList: {}", topicList); // 로그로 확인
	    return topicList;
	}

	// 챌린지 난이도 리스트
	@Override
	public List<Map<String, String>> getChallengeLevelList() {
		List<Map<String, String>> levelList = challengeListMapper.getChallengeLevelList();
		log.info(">>> location/service >>> levelList: {}", levelList); // 로그로 확인
	    return levelList;
	}
}
