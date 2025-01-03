package doself.user.challenge.feed.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import doself.user.challenge.feed.domain.ChallengeFeed;

@Mapper
public interface ChallengeFeedMapper {
	// 챌린지 피드 리스트 조회
	List<ChallengeFeed> getChallengeFeedList(@Param("offset") int offset, @Param("pageSize") int pageSize);
}
