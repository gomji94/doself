//문서가 준비되면 제일 먼저 실행
$(document).ready(function(){ 
	$("#payment").click(function(){ 
    	proceedPay(); //버튼 클릭하면 호출 
    }); 
})

function proceedPay() {
			 
	 // ajax 요청
	 const request = $.ajax({
		url : '/ticket/payment',
		type : 'POST',
		dataType : "text ", 
		data : { ticketKey : $('#ticketKey').val() }
	 });
	 request.done(response => {
		requestPay(response);
	 }
	 );
	 request.fail(function(jQXHR, textStatus, error){
	 	console.log(error);
	 });	
			 
}

function requestPay(data) {
	var IMP = window.IMP;
	IMP.init("imp15443850"); // 예: imp00000000
      	// IMP.request_pay(param, callback) // 결제창 호출
		
	  IMP.request_pay({ // param
	      pg: "uplus.tlgdacomxpay", //결제대행사 설정에 따라 다르며 공식문서 참고
	      pay_method: "card", //결제방법 설정에 따라 다르며 공식문서 참고
	      merchant_uid: data.orderKeyValue, //주문(db에서 불러옴) 고유번호
	      name: data.ticketName,
	      amount: 10000
	  }, function (rsp) { // callback
	      if (rsp.success) {
	          // jQuery로 HTTP 요청
	          jQuery.ajax({
	            url: "/ticket/payment/result", 
	            method: "POST",
				data : {
					
					
				}
	          }).done(function (data) {
	        	console.log("구매이력 저장 로직실행")
	          })
	      } else {
	    	  var msg = '결제에 실패하였습니다.';
	          msg += '에러내용 : ' + rsp.error_msg;
	          alert(msg);
	      }
	  });
    }


