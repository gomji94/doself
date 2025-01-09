package doself.admin.payment.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import doself.admin.payment.domain.Payment;
import doself.admin.payment.domain.PaymentRefund;

@Mapper
public interface PaymentMapper {
	
	//결제내역 출력
	List<Payment> getPaymentList(Map<String, Object> searchMap);
	//결제내역 개수
	int getCntPaymentList(Map<String, Object> searchMap);
	
	//결제 환불 내역 출력
	List<PaymentRefund> getRefundList(Map<String, Object> searchMap);
	//결제 환불 내역 개수
	int getCntRefundList(Map<String, Object> searchMap);
}
