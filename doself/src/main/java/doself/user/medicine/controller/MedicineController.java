package doself.user.medicine.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/medicine")
public class MedicineController {
	
	@GetMapping("/list")
	public String getMedicineList() {
		return "user/medicine/list";
	}
	
	@GetMapping("/view")
	public String getMedicineDetail() {
		return "user/medicine/view";
	}
	

}
