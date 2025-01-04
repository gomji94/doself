package doself.admin.payment.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import doself.admin.payment.domain.Payment;
import doself.admin.payment.domain.PaymentRefund;
import doself.admin.payment.mapper.PaymentMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentServiceImpl implements PaymentService {
	
	private final PaymentMapper paymentMapper;
	
	//결제내역 출력
	@Override
	public List<Payment> getPaymentList(String searchType, String searchKeyword, String startDate, String endDate) {

		return paymentMapper.getPaymentList(searchType, searchKeyword, startDate, endDate);
	}

	//결제 환불내역 출력
	@Override
	public List<PaymentRefund> getRefundList(String searchType, String searchKeyword, String startDate, String endDate) {
		
		return paymentMapper.getRefundList(searchType, searchKeyword, startDate, endDate);
	}
	
}
