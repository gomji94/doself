package doself.admin.point.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import doself.admin.point.domain.Point;
import doself.admin.point.domain.PointUserHistory;
import doself.admin.point.mapper.PointMapper;
import doself.common.mapper.CommonMapper;
import doself.util.PageInfo;
import doself.util.Pageable;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class PointServiceImpl implements PointService {
	
	private final PointMapper pointMapper;
	private final CommonMapper commonMapper;
	
	// 포인트 상품목록 조회
	@Override
	public List<Point> getPointList() {
		
		return pointMapper.getPointList();
	}

	// 포인트 사용내역 조회
	@Override
	public PageInfo<PointUserHistory> getPointUserHistoryList(String searchType, String searchKeyword, String startDate, String endDate, Pageable pageable) {
		
		switch(searchType) {
			case "peplName" 	-> searchType = "pepl.pepl_name";
			case "mbrName" 		-> searchType = "m.mbr_name";	
		}
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("searchType", searchType);
		searchMap.put("searchKeyword", searchKeyword);
		searchMap.put("startDate", startDate);
		searchMap.put("endDate", endDate);
		searchMap.put("pageable", pageable);
		
		int rowCnt = pointMapper.getCntPointUserHistoryList(searchMap);		
		List<PointUserHistory> pointUserHistoryList = pointMapper.getPointUserHistoryList(searchMap);
	
		return new PageInfo<>(pointUserHistoryList, pageable, rowCnt);
	}
	@Override
	public void createPointList(Point point) {
		pointMapper.createPointList(point);
	}
	

	//특정 포인트 상품 조회
	@Override
	public Point getPointInfoByPeplNum(String peplNum) {
		
		return pointMapper.getPointInfoByPeplNum(peplNum);
	}

	//특정 포인트 상품 수정
	@Override
	public void modifyPoint(Point point) {

		pointMapper.modifyPoint(point);
	}
	
	//포인트상품 마지막번호 찾은후 다음키생성
	@Override
	public String pointLastNum() {
		
		String newKey = commonMapper.getPrimaryKey("pepl_", "point_exchange_product_list", "pepl_num");
		return newKey;
	}
	
	// 포인트 상품 삭제
	@Override
	public void deletePointList(String peplNum) {
		
		pointMapper.deletePointList(peplNum);
	}

}
