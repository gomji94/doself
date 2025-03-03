// 검색조건에 따라 자동완성값 변경
function updateSearchSuggestions() {
    const searchType = document.getElementById("searchType").value;
    const suggestions = document.getElementById("searchSuggestions");
    suggestions.innerHTML = ""; // 기존 데이터 초기화

    let options = [];
    if (searchType === "rcName") {
        options.push("불법 컨텐츠", "사기 및 사칭", "폭력적 및 유해 콘텐츠", "욕설 및 비방", "스팸 및 광고", "허위 사실 유포");
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

    const $form = $('<form />', { 'action': '/admin/declare/userlist', 'method': 'get' });

    const $searchType = $('<input />', { 'type': 'hidden', 'name': 'searchType' }).val(searchType);
    const $searchKeyword = $('<input />', { 'type': 'hidden', 'name': 'searchKeyword' }).val(searchKeyword);
    const $startDate = $('<input />', { 'type': 'hidden', 'name': 'startDate' }).val(startDate);
    const $endDate = $('<input />', { 'type': 'hidden', 'name': 'endDate' }).val(endDate);
    const $currentPage = $('<input />', { 'type': 'hidden', 'name': 'currentPage' }).val(currentPage);

    $form.append($searchType, $searchKeyword, $startDate, $endDate, $currentPage);
    $('body').append($form);
    $form.submit();
});