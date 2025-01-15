$(document).ready(function () {
    // 버튼 클릭 이벤트 핸들러
    $("#info-link").on("click", function (event) {
		
        event.preventDefault();

        // href 속성에서 링크 경로 가져오기
        var targetPage = $(this).attr("href");
		
		console.log("Target URL:", targetPage);
        if (targetPage) {
            window.open(targetPage, "_blank", 'width=500, height=600, left=500, top=200');
        } else {
            alert("페이지 경로가 설정되지 않았습니다.");
        }
    });

    $("#item-close__button").on("click", function () {
        window.close();
    });
});
