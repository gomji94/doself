package doself.admin.payment.service;

import doself.admin.payment.domain.Payment;
import doself.admin.payment.domain.PaymentRefund;
import doself.util.PageInfo;
import doself.util.Pageable;

public interface PaymentService {

	// 결제내역 출력
	PageInfo<Payment> getPaymentList(String searchType, String searchKeyword, String startDate, String endDate, Pageable pageable);
	
	// 결제 환불내역 출력
	PageInfo<PaymentRefund> getRefundList(String searchType, String searchKeyword, String startDate, String endDate, Pageable pageable);
}
