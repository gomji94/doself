package doself.user.challenge.list.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import doself.user.challenge.list.controller.ChallengeListController;
import doself.user.challenge.list.domain.ChallengeList;
import doself.user.challenge.list.mapper.ChallengeListMapper;
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
	
	// 챌린지 조회 리스트 반환
	@Override
	public List<ChallengeList> getChallengeList() {
		List<ChallengeList> challengeList = challengeListMapper.getChallengeList();
		if (challengeList == null || challengeList.isEmpty()) {
	        log.warn("챌린지 리스트 정보가 없습니다.");
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
	public ChallengeList getChallengeListView(String ChallengeCode) {
			
		return challengeListMapper.getChallengeListView(ChallengeCode);
	}

	// 챌린지 생성(등록)
	@Override
	public void addChallenge(ChallengeList challengeList) {
		int addChallengeResult = challengeListMapper.addChallenge(challengeList);
		
	}
	
}
