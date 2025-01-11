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
			data : { pointItemKeyNum : $('#pointItemKeyNum').val(), itemPrice : $('#itemPrice').val()},
			dataType: 'json'
		});
		
		request.done(response => {
			alert('성공');
		})
		
		request.fail((jqXHR, textStatus, error)=>{
			console.log(error);
		})
		
	}
	
	
	// if(isValid) $('#purchaseItemForm').submit();
	
	
})