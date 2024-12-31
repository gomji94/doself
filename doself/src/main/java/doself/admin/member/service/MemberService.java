package doself.admin.member.service;

import java.util.List;

import doself.admin.member.domain.MemberDTO;

public interface MemberService {
	
	List<MemberDTO> getMemberList();
	
}
