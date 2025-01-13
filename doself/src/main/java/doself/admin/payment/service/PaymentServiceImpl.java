package doself.admin.payment.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import doself.admin.payment.domain.Payment;
import doself.admin.payment.domain.PaymentRefund;
import doself.admin.payment.mapper.PaymentMapper;
import doself.util.PageInfo;
import doself.util.Pageable;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentServiceImpl implements PaymentService {
	
	private final PaymentMapper paymentMapper;
	
	//결제내역 출력
	@Override
	public PageInfo<Payment> getPaymentList(String searchType, String searchKeyword, String startDate, String endDate, Pageable pageable) {

		switch(searchType) {
			case "ctcCategory" 	-> searchType = "ctc.ctc_category";
			case "mbrName" 	-> searchType = "m.mbr_name";
		}
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("searchType", searchType);
		searchMap.put("searchKeyword", searchKeyword);
		searchMap.put("startDate", startDate);
		searchMap.put("endDate", endDate);
		searchMap.put("pageable", pageable);
		
		int rowCnt = paymentMapper.getCntPaymentList(searchMap);		
		List<Payment> paymentList = paymentMapper.getPaymentList(searchMap);
		
		return new PageInfo<>(paymentList, pageable, rowCnt);
	}

	//결제 환불내역 출력
	@Override
	public PageInfo<PaymentRefund> getRefundList(String searchType, String searchKeyword, String startDate, String endDate, Pageable pageable) {
		
		switch(searchType) {
			case "mbrName" 	-> searchType = "m.mbr_name";

		}
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("searchType", searchType);
		searchMap.put("searchKeyword", searchKeyword);
		searchMap.put("startDate", startDate);
		searchMap.put("endDate", endDate);
		searchMap.put("pageable", pageable);
		
		int rowCnt = paymentMapper.getCntRefundList(searchMap);		
		List<PaymentRefund> paymentList = paymentMapper.getRefundList(searchMap);
		
		return new PageInfo<>(paymentList, pageable, rowCnt);
	}
	
}
