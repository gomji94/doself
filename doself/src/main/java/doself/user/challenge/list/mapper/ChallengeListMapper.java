package doself.user.challenge.list.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import doself.user.challenge.list.domain.ChallengeDetailView;
import doself.user.challenge.list.domain.ChallengeList;
import doself.util.CardPageable;

@Mapper
public interface ChallengeListMapper {
	// 진행중인 챌린지 리스트 조회
	List<ChallengeList> getChallengeList();
	
	// 특정 챌린지 정보 조회(detail view)
	List<ChallengeDetailView> getChallengeListView(@Param("challengeCode") String challengeCode);

	// 챌린지 추가(작업중)
	int addChallenge(ChallengeList challengeList);
	
	// 챌린지 페이지
	List<ChallengeList> getChallengeList(CardPageable cardPageable);
	
	// 챌린지 총 갯수
	int getCntChallengeList();
}
