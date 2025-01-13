// 검색조건에 따라 자동완성값 변경
function updateSearchSuggestions() {
    const searchType = document.getElementById("searchType").value;
    const suggestions = document.getElementById("searchSuggestions");
    suggestions.innerHTML = ""; // 기존 데이터 초기화

    let options = [];
    if (searchType === "peplName") {
        options.push("개설 티켓", "참여 티켓", "배민 상품권 1000", "배민 상품권 5000", "배민 상품권 10000", "네이버페이 포인트쿠폰 1000", "네이버페이 포인트쿠폰 5000", "네이버페이 포인트쿠폰 10000");
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
    const currentPage = 1; // 검색 시 페이지 번호를 초기화

    const $form = $('<form />', { 'action': '/admin/point/userhistorylist', 'method': 'get' });

    const $searchType = $('<input />', { 'type': 'hidden', 'name': 'searchType' }).val(searchType);
    const $searchKeyword = $('<input />', { 'type': 'hidden', 'name': 'searchKeyword' }).val(searchKeyword);
    const $startDate = $('<input />', { 'type': 'hidden', 'name': 'startDate' }).val(startDate);
    const $endDate = $('<input />', { 'type': 'hidden', 'name': 'endDate' }).val(endDate);
    const $currentPage = $('<input />', { 'type': 'hidden', 'name': 'currentPage' }).val(currentPage);

    $form.append($searchType, $searchKeyword, $startDate, $endDate, $currentPage);
    $('body').append($form);
    $form.submit();
});