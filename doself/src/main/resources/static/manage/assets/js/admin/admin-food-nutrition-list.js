
// 검색 기능
$('#searchBtn').click(function(){
    const searchType = $('#searchType').val();
    const searchKeyword = $('#searchKeyword').val();
    const startDate = $('#startDate').val();
    const endDate = $('#endDate').val();
    const currentPage = 1; // 검색 시 페이지 번호를 초기화

    const $form = $('<form />', { 'action': '/admin/nutrition/foodlist', 'method': 'get' });

    const $searchType = $('<input />', { 'type': 'hidden', 'name': 'searchType' }).val(searchType);
    const $searchKeyword = $('<input />', { 'type': 'hidden', 'name': 'searchKeyword' }).val(searchKeyword);
    const $startDate = $('<input />', { 'type': 'hidden', 'name': 'startDate' }).val(startDate);
    const $endDate = $('<input />', { 'type': 'hidden', 'name': 'endDate' }).val(endDate);
    const $currentPage = $('<input />', { 'type': 'hidden', 'name': 'currentPage' }).val(currentPage);

    $form.append($searchType, $searchKeyword, $startDate, $endDate, $currentPage);
    $('body').append($form);
    $form.submit();
});