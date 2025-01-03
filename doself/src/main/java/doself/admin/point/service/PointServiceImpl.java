package doself.admin.point.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import doself.admin.point.domain.Point;
import doself.admin.point.domain.PointUserHistory;
import doself.admin.point.mapper.PointMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class PointServiceImpl implements PointService {
	
	private final PointMapper pointMapper;
	
	// 포인트 상품목록 조회
	@Override
	public List<Point> getPointList() {
		
		return pointMapper.getPointList();
	}

	// 포인트 사용내역 조회
	@Override
	public List<PointUserHistory> getPointUserHistoryList() {
		
		return pointMapper.getPointUserHistoryList();
	}

}
