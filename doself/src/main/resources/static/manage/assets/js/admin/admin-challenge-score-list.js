// 검색기능
$('#searchBtn').click(function(){
	const searchType = $('#searchType').val();
	const searchKeyword = $('#searchKeyword').val();
	const startDate = $('#startDate').val();
	const endDate = $('#endDate').val();
	
	const $form = $('<form />', { 'action' : '/admin/challenge/scorelist', 'method': 'get'});
	const $searchType = 
			$('<input />', {'type':'hidden', 'name' : 'searchType'}).val(searchType);
	const $searchKeyword = 
			$('<input />', {'type':'hidden', 'name' : 'searchKeyword'}).val(searchKeyword);
	const $startDate = 
			$('<input />', {'type':'hidden', 'name' : 'startDate'}).val(startDate);
	const $endDate = 
			$('<input />', {'type':'hidden', 'name' : 'endDate'}).val(endDate);
	$form.append($searchType, $searchKeyword, $startDate, $endDate);
	$('body').append($form);
	$form.submit();
});