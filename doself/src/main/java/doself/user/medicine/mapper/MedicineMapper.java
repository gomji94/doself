package doself.user.medicine.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import doself.user.medicine.domain.Medicine;

@Mapper
public interface MedicineMapper {
	
	// 영양제 목록 조회
	List<Medicine> getMedicineList();
	
	// 영양제 상세 정보 조회
	Medicine getMedicineDetail(String medicineKeyNum);
	
	// 영양제 검색
	List<Medicine> searchMedicineByMedicineName(String medicineName);

}
