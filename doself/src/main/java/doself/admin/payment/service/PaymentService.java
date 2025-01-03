package doself.admin.payment.service;

import java.util.List;

import doself.admin.payment.domain.Payment;
import doself.admin.payment.domain.PaymentRefund;

public interface PaymentService {

	// 결제내역 출력
	List<Payment> getPaymentList();
	
	// 결제 환불내역 출력
	List<PaymentRefund> getRefundList();
}
