package doself.user.medicine.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import doself.user.medicine.service.MedicineService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/medicine")
@RequiredArgsConstructor
@Slf4j
public class MedicineController {
	
	private final MedicineService medicineService;
	
	@GetMapping("/list")
	public String getMedicineList(Model model) {
		
		model.addAttribute("medicineList", medicineService.getMedicineList());
		
		return "user/medicine/list";
	}
	
	@GetMapping("/view")
	public String getMedicineDetail(@RequestParam(name = "medicineKeyNum") String medicineKeyNum, Model model) {
		
		model.addAttribute("medicineInfo", medicineService.getMedicineDetail(medicineKeyNum));
		
		return "user/medicine/view";
	}
	

}
