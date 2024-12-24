$(document).ready(function () {
    // 모달 열기
    $("#cl-create").on("click", function () {
        $("#modalOverlay").fadeIn(300); // 부드럽게 표시
    });

    // 모달 닫기 (닫기 버튼 클릭)
    $("#modalCloseBtn").on("click", function () {
        $("#modalOverlay").fadeOut(300); // 부드럽게 숨기기
    });

    // 모달 닫기 (오버레이 클릭)
    $("#modalOverlay").on("click", function (e) {
        if ($(e.target).is("#modalOverlay")) { // 오버레이 클릭 시만 닫기
            $(this).fadeOut(300);
        }
    });
});
