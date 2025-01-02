package doself.user.medicine.service;

import java.util.List;

import doself.user.medicine.domain.Medicine;

public interface MedicineService {
	
	// 영양제 목록 조회
	List<Medicine> getMedicineList();
	
	// 영양제 상세 정보 조회
	Medicine getMedicineDetail(String medicineKeyNum);

}
