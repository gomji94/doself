// 날짜 포맷팅 함수
function formatDate(unixTimestamp) {
    let date = new Date(unixTimestamp * 1000); // 초 단위를 밀리초 단위로 변환
    let year = date.getFullYear();
    let month = String(date.getMonth() + 1).padStart(2, '0'); // 월은 0부터 시작하므로 +1
    let day = String(date.getDate()).padStart(2, '0');
    let hours = String(date.getHours()).padStart(2, '0');
    let minutes = String(date.getMinutes()).padStart(2, '0');
    let seconds = String(date.getSeconds()).padStart(2, '0');

    return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
}

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
		dataType : "json", 
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
	      amount: data.ticketPrice,
		  buyer_name: data.memberId 
	  }, function (response) { // callback
	      if (response.success) {
			console.log(response);	
			
	          // jQuery로 HTTP 요청
	          jQuery.ajax({
	            url: "/ticket/payment/result", 
				method: "POST",
				contentType: 'application/x-www-form-urlencoded',
				dataType: 'json',
				data: {
					orderPkValue : response.merchant_uid,
					ordererId : response.buyer_name,
					paymentMethod : response.pay_method,
					paymentCardName : response.card_name,
					paymentCardNum : response.card_number,
					paymentUniqueValue : response.imp_uid,
					paymentDate : formatDate(response.paid_at)
				}
	          }).done(function (data) {
	        	console.log("결제 성공")
				
				window.location.href = "/ticket/purchaselist";
				
	          })
	      } else {
	    	  var msg = '결제에 실패하였습니다.';
	          msg += '에러내용 : ' + response.error_msg;
	          alert(msg);
	      }
	  });
    }


