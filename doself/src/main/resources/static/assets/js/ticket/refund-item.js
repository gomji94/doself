$('#refund-request-submit').click(event => {

		const request = $.ajax({
			url: '/ticket/purchasedetail/refund',
			method: 'post',
			data : { ticketNum : $('#ticketNum').val()
				 },
			contentType: 'application/x-www-form-urlencoded',
			dataType: 'json'
		});
		
		request.done(response => {
			
			// 부모 페이지의 URL을 특정 경로로 변경
			if (window.opener) {
			    window.opener.location.href = '/ticket/purchaselist'; // 부모 페이지가 이동할 URL
			}

			// 현재 창 닫기
			window.close();
			
		})
		
		request.fail((jqXHR, textStatus, error)=>{
			console.log(textStatus);
		})
		
})