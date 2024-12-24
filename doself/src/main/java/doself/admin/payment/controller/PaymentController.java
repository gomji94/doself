package doself.admin.payment.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("/admin/payment")
public class PaymentController {
	
	@GetMapping("list")
	public String paymentList(HttpServletRequest request, Model model) {
		
		model.addAttribute("currentURI", request.getRequestURI());
		model.addAttribute("title","결제 내역");
		return "admin/payment/admin-payment-list";
	}
	
	@GetMapping("refund/list")
	public String refundList(HttpServletRequest request, Model model) {
		
		model.addAttribute("currentURI", request.getRequestURI());
		model.addAttribute("title","환불 접수 내역");
		return "admin/payment/admin-refund-list";
	}
}
