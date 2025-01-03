package doself.admin.payment.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import doself.admin.payment.domain.Payment;
import doself.admin.payment.domain.PaymentRefund;
import doself.admin.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;


@Controller
@RequestMapping("/admin/payment")
@RequiredArgsConstructor
public class PaymentController {
	
	private final PaymentService paymentService;
	
	// 결제 내역 조회
	@GetMapping("list")
	public String getPaymentList(Model model) {
		
		List<Payment> paymentList = paymentService.getPaymentList();
		
		model.addAttribute("title","결제 내역");
		model.addAttribute("paymentList", paymentList);
		return "admin/payment/admin-payment-list";
	}
	
	// 환불 접수 내역 조회
	@GetMapping("refund/list")
	public String getRefundList(Model model) {

		List<PaymentRefund> refundList = paymentService.getRefundList();
		
		model.addAttribute("title","환불 접수 내역");
		model.addAttribute("refundList", refundList);
		return "admin/payment/admin-refund-list";
	}
}
