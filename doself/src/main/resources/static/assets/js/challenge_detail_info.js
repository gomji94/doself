$(document).ready(function () {
    // 카드 클릭 이벤트: 모달 표시
    $("#card-1").on("click", function () {
        $("#card-modal-overlay").fadeIn(); // 오버레이 표시
        $("#card-modal").fadeIn(); // 모달 표시
    });

    // 모달 닫기 이벤트: 닫기 버튼 또는 오버레이 클릭 시
    $("#card-modal-close, #card-modal-overlay").on("click", function () {
        $("#card-modal-overlay").fadeOut(); // 오버레이 숨김
        $("#card-modal").fadeOut(); // 모달 숨김
    });
});
