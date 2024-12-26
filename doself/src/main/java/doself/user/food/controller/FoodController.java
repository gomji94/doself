package doself.user.food.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("food")
public class FoodController {

	@GetMapping("/list")
	public String getFoodList() {
		return "user/food/list";
	}
	
	@GetMapping("/view")
	public String getFoodDetail() {
		return "user/food/view";
	}
	
}
