package doself.user.challenge.feed.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import doself.user.challenge.feed.domain.ChallengeFeed;
import doself.user.challenge.feed.domain.ChallengeMemberList;
import doself.user.challenge.feed.domain.ChallengeProcess;

@Mapper
public interface ChallengeFeedMapper {
	// 챌린지 피드 리스트 조회
	List<ChallengeFeed> getChallengeFeedList(@Param("offset") int offset, @Param("pageSize") int pageSize);
	
	// 챌린지 진행 상태 조회
	List<ChallengeProcess> getChallengeProgress(@Param("challengeCode") String challengeCode);
	
	// 챌린지 참여 멤버 리스트 조회
	List<ChallengeMemberList> getMemberList(@Param("challengeCode") String challengeCode);
}
