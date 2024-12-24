$(document).ready(function () {
    // 모달 열기
    $("#cf-create").on("click", function () {
        $("#cf-modalOverlay").fadeIn(300); // 부드럽게 모달 열기
    });

    // 모달 닫기 (닫기 버튼)
    $("#cf-modalCloseBtn").on("click", function () {
        $("#cf-modalOverlay").fadeOut(300); // 부드럽게 모달 닫기
    });

    // 모달 닫기 (오버레이 클릭)
    $("#cf-modalOverlay").on("click", function (e) {
        if ($(e.target).is("#cf-modalOverlay")) {
            $(this).fadeOut(300); // 오버레이 배경 클릭 시 닫기
        }
    });
});
