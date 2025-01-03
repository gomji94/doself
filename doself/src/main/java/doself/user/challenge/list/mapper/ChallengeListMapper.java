package doself.user.challenge.list.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import doself.user.challenge.list.domain.ChallengeList;

@Mapper
public interface ChallengeListMapper {
	// 진행중인 챌린지 리스트 조회
	List<ChallengeList> getChallengeList();
	
	// 특정 챌린지 정보 조회(detail view)
	ChallengeList getChallengeListView(String ChallengeCode);

	int addChallenge(ChallengeList challengeList);
}
