package doself.user.medicine.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import doself.user.food.domain.NutritionRequestInfo;
import doself.user.food.service.FoodService;
import doself.user.medicine.service.MedicineService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/medicine")
@RequiredArgsConstructor
@Slf4j
public class MedicineController {
	
	private final MedicineService medicineService;
	private final FoodService foodService;
	
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
	
	@GetMapping("/search")
	public String searchMedicineByMedicineName(@RequestParam(name = "medicineName", required = false) String medicineName, Model model) {
		if (medicineName != null && medicineName.trim().length() > 0) {
			model.addAttribute("medicineList", medicineService.searchMedicineByMedicineName(medicineName));
		} 		
		
		return "user/medicine/list";
	}
	
	@PostMapping("/createrequest")
	public String createNutritionRequest(NutritionRequestInfo nutritionRequestInfo, HttpSession session) {
		//TODO: process POST request
		
		nutritionRequestInfo.setMemberId((String) session.getAttribute("SID"));
		foodService.createNutritionRequest(nutritionRequestInfo);
		
		return "redirect:/medicine/list";
	}
	

}
