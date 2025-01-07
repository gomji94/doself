// 검색조건에 따라 자동완성값 변경
function updateSearchSuggestions() {
    const searchType = document.getElementById("searchType").value;
    const suggestions = document.getElementById("searchSuggestions");
    suggestions.innerHTML = ""; // 기존 데이터 초기화

    let options = [];
    if (searchType === "ctcCategory") {
        options.push("개설 티켓", "참여 티켓");
    }

    options.forEach(value => {
        const option = document.createElement("option");
        option.value = value;
        suggestions.appendChild(option);
    });
}

// 검색 기능
$('#searchBtn').click(function(){
	const searchType = $('#searchType').val();
	const searchKeyword = $('#searchKeyword').val();
	const startDate = $('#startDate').val();
	const endDate = $('#endDate').val();
	
	const $form = $('<form />', { 'action' : '/admin/payment/list', 'method': 'get'});
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