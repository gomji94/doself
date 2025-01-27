

$('#refund-request-submit').click(event => {
		event.preventDefault();
		
		const ticketNum = $('#ticketNum').val();
		const requestChk = $.ajax({
			url: '/ticket/purchasedetail/isCheck',
			method: 'post',
			data : { ticketNum : ticketNum
				 },
			contentType: 'application/x-www-form-urlencoded',
			dataType: 'json'
		});
		
		requestChk.done(isRefund => {	
			
			if(isRefund) {
				alert("이미 환불처리가 되었습니다.");
			} else {
				const refundRequest = $.ajax({
					url: '/ticket/purchasedetail/refund',
					method: 'post',
					data : { ticketNum : ticketNum
						 },
					contentType: 'application/x-www-form-urlencoded',
					dataType: 'json'
				});
			
				refundRequest.done(response => {
					if (response){
						alert("환불처리가 완료되었습니다.");
							
						// 부모 페이지의 URL을 특정 경로로 변경
						if (window.opener) {
						    window.opener.location.href = '/ticket/purchaselist'; // 부모 페이지가 이동할 URL
						}
					// 현재 창 닫기
					window.close();
					} else {
						alert("환불처리에 실패했습니다. 다시 시도해주세요")
					}
				});
			}
			request.fail((jqXHR, textStatus, error)=>{
				console.log(textStatus);
			});
		});
});

$('#backBtn').click(e =>{
	window.close();
})