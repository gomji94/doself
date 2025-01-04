// 검색한데이터 url매핑
function filterData() {
    const startDate = document.getElementById("startDate").value;
    const endDate = document.getElementById("endDate").value;

    const url = `/admin/point/userhistorylist?startDate=${startDate}&endDate=${endDate}`;

    // 검색 결과를 새로고침
    window.location.href = url;
}