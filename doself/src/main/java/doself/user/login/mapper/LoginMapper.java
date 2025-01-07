package doself.user.login.mapper;

import org.apache.ibatis.annotations.Mapper;

import doself.admin.member.domain.Member;


@Mapper
public interface LoginMapper {

	// 특정회원 조회
	Member getMemberInfoById(String mbrId);

}
