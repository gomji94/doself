package doself.admin.member.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import doself.admin.member.domain.MemberDTO;

@Mapper
public interface MemberMapper {

	List<MemberDTO> getMemberList();
}
