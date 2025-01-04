package doself.admin.payment.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
	public String getPaymentList(@RequestParam(value = "searchType", required = false) String searchType,
            @RequestParam(value = "searchKeyword", required = false) String searchKeyword,
            @RequestParam(value = "startDate", required = false) String startDate,
            @RequestParam(value = "endDate", required = false) String endDate, Model model) {
		
		List<Payment> paymentList = paymentService.getPaymentList(searchType, searchKeyword, startDate, endDate);
		
		model.addAttribute("title","결제 내역");
		model.addAttribute("paymentList", paymentList);
		return "admin/payment/admin-payment-list";
	}
	
	// 환불 접수 내역 조회
	@GetMapping("refund/list")
	public String getRefundList(@RequestParam(value = "searchType", required = false) String searchType,
            @RequestParam(value = "searchKeyword", required = false) String searchKeyword,
            @RequestParam(value = "startDate", required = false) String startDate,
            @RequestParam(value = "endDate", required = false) String endDate, Model model) {

		List<PaymentRefund> refundList = paymentService.getRefundList(searchType, searchKeyword, startDate, endDate);
		
		model.addAttribute("title","환불 접수 내역");
		model.addAttribute("refundList", refundList);
		return "admin/payment/admin-refund-list";
	}
}
