package doself.admin.point.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import doself.admin.point.domain.Point;
import doself.admin.point.domain.PointUserHistory;

@Mapper
public interface PointMapper {
	
	//포인트 상품목록 조회
	List<Point> getPointList();
	
	//포인트 사용내역 조회
	List<PointUserHistory> getPointUserHistoryList(Map<String, Object> searchMap);
	//포인트 사용내역 개수
	int getCntPointUserHistoryList(Map<String, Object> searchMap);
	
	//포인트 상품 추가
	int createPointList(Point point);
	
	//특정 포인트 상품 조회
	Point getPointInfoByPeplNum(String peplNum);
	
	//특정 포인트 상품 수정
	int modifyPoint(Point point);
	
	//포인트상품 마지막번호 찾은후 다음키생성
	int getPointLastNum();
	
	//포인트상품 삭제
	void deletePointList(String peplNum);
}
