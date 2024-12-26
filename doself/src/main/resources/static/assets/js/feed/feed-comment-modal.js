$(document).ready(function () {
    const $overlay = $(".comment-modal-overlay");
    const $commentOptionModal = $(".comment-option-modal");
    const $declarationModal = $("#declaration-modal");
    const $popupContainer = $(".popup-container");

    // 옵션 모달 표시
    $(".option-img").on("click", function (e) {
        e.stopPropagation();
        $commentOptionModal.addClass("show").css("display", "block");
        $overlay.addClass("show").css("display", "block"); // 오버레이 활성화
    });

    // 옵션 모달 닫기
    $(".comment-option-modal .close").on("click", function () {
        $commentOptionModal.removeClass("show").css("display", "none");
        $overlay.removeClass("show").css("display", "none"); // 오버레이 비활성화
    });

    // 신고 모달 표시
    $(".comment-declaration-pop").on("click", function (e) {
        e.stopPropagation();
        $commentOptionModal.removeClass("show").css("display", "none");
        $declarationModal.addClass("show").css("display", "block");
        $overlay.addClass("show").css("display", "block"); // 오버레이 활성화
    });

    // 신고 모달 닫기
    $("#declaration-modal .close").on("click", function () {
        $declarationModal.removeClass("show").css("display", "none");
        $overlay.removeClass("show").css("display", "none"); // 오버레이 비활성화
    });

    // 오버레이 클릭 시 모든 모달 닫기
    $overlay.on("click", function () {
        $commentOptionModal.add($declarationModal).removeClass("show").css("display", "none");
        $overlay.removeClass("show").css("display", "none");
    });

    // 닫기 아이콘 클릭 시 댓글창 닫기
    $(".close-option-img").on("click", function () {
        $popupContainer.fadeOut(); // 댓글창 팝업 컨테이너 닫기
        $overlay.removeClass("show").css("display", "none"); // 오버레이도 비활성화
    });

    // 오버레이 클릭 시 모든 모달 닫기
    $overlay.on("click", function () {
        $commentOptionModal.add($declarationModal).removeClass("show").css("display", "none");
        $popupContainer.fadeOut(); // 댓글창도 닫기
        $overlay.fadeOut(); // 오버레이 비활성화
    });
});