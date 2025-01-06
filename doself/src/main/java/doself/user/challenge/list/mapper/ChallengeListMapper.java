package doself.user.challenge.list.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import doself.user.challenge.list.domain.ChallengeList;
import doself.util.CardPageable;
import doself.util.PageInfo;
import doself.util.Pageable;

@Mapper
public interface ChallengeListMapper {
	// 진행중인 챌린지 리스트 조회
	List<ChallengeList> getChallengeList();
	
	// 특정 챌린지 정보 조회(detail view)
	ChallengeList getChallengeListView(String ChallengeCode);

	// 챌린지 추가(작업중)
	int addChallenge(ChallengeList challengeList);
	
	// 챌린지 페이지
	List<ChallengeList> getChallengePage(CardPageable cardPageable);
	
	// 챌린지 총 갯수
	int getCntChallengeList();
}
