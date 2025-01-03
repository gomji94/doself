package doself.user.medicine.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import doself.user.medicine.domain.Medicine;
import doself.user.medicine.mapper.MedicineMapper;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MedicineServiceImpl implements MedicineService {
	
	private final MedicineMapper medicineMapper;
	
	@Override
	public List<Medicine> getMedicineList() {
		// TODO Auto-generated method stub
		return medicineMapper.getMedicineList();
	}

	@Override
	public Medicine getMedicineDetail(String medicineKeyNum) {
		// TODO Auto-generated method stub
		return medicineMapper.getMedicineDetail(medicineKeyNum);
	}

	@Override
	public List<Medicine> searchMedicineByMedicineName(String medicineName) {
		// TODO Auto-generated method stub
		return medicineMapper.searchMedicineByMedicineName(medicineName);
	}

}
