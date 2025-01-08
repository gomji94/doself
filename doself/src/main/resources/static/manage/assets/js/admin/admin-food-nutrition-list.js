
// 검색 기능
$('#searchBtn').click(function(){
	const searchType = $('#searchType').val();
	const searchKeyword = $('#searchKeyword').val();
	
	
	const $form = $('<form />', { 'action' : '/admin/nutrition/foodlist', 'method': 'get'});
	const $searchType = 
			$('<input />', {'type':'hidden', 'name' : 'searchType'}).val(searchType);
	const $searchKeyword = 
			$('<input />', {'type':'hidden', 'name' : 'searchKeyword'}).val(searchKeyword);
	
	$form.append($searchType, $searchKeyword);
	$('body').append($form);
	$form.submit();
});