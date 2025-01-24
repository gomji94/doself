$('#item-purchase__button').click(event => {

	event.preventDefault();
	
	let isValid = true; 
	const isAgreeChecked = $('#pay-agree-checkbox').prop('checked');
	
	if(!isAgreeChecked) {
		alert("결제 서비스 동의 체크를 확인해주세요");
		$('#pay-agree-checkbox').focus();
		isValid = false;
		return;
	} 
	
	if(isValid) {
		
		const request = $.ajax({
			url: '/market/purchaseitem',
			method: 'post',
			data : { pointItemKeyNum : $('#pointItemKeyNum').val(), itemPrice : $('#itemPrice').val() },
			contentType: 'application/x-www-form-urlencoded',
			dataType: 'json'
		});
		
		request.done(response => {
			
			// 부모 페이지의 URL을 특정 경로로 변경
			if (window.opener) {
			    window.opener.location.href = '/market/purchaselist'; // 부모 페이지가 이동할 URL
			}

			// 현재 창 닫기
			window.close();
			
		})
		
		request.fail((jqXHR, textStatus, error)=>{
			console.log(textStatus);
		})
		
	}
	
	// if(isValid) $('#purchaseItemForm').submit();
	
	
})