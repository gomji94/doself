package doself.user.challenge.list.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import doself.user.challenge.list.domain.ChallengeList;
import doself.user.challenge.list.mapper.ChallengeListMapper;
import lombok.RequiredArgsConstructor;

@Service
//jdbc가 추가된 상태에서만 트랜잭셔널 어노테이션 실행됨
//전체 적용하려면 여기에 작성 || 특정 메소드에만 설정하고 싶으면 메소드 상단에 배치
@Transactional
//log.이 가능한 이유는 아래의 어노테이션 때문
@RequiredArgsConstructor
public class ChallengeListServiceImpl implements ChallengeListService {

	private final ChallengeListMapper challengeListMapper;
	
	// 챌린지 조회 리스트 반환
	@Override
	public List<ChallengeList> getChallengeList() {
		List<ChallengeList> challengeList = challengeListMapper.getChallengeList();
		// 현재 진행중인 챌린지만 조회되게 함(cs_status → 진행중/진행중(리더 양도))
		challengeList.forEach(challengeInfo -> {
			String challengeStatus = challengeInfo.getChallengeStatus();
			if(challengeStatus == "cs_002" || challengeStatus == "cs_003") {
				
			}
		});
		return challengeList;
	}
	
}
