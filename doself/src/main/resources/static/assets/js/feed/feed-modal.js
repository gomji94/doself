$(document).ready(function () {
    const $overlay = $(".feed-modal-overlay");
    const $feedOptionModal = $(".feed-option-modal");
    const $declarationModal = $("#declaration-modal");

    // 옵션 모달 표시
    $(".option-button").on("click", function (e) {
        e.stopPropagation();
        $feedOptionModal.addClass("show").css("display", "block");
        $overlay.addClass("show").css("display", "block"); // 오버레이 활성화
    });

    // 옵션 모달 닫기
    $(".feed-option-modal .close").on("click", function () {
        $feedOptionModal.removeClass("show").css("display", "none");
        $overlay.removeClass("show").css("display", "none"); // 오버레이 비활성화
    });

    // 신고 모달 표시
    $(".feed-declaration-pop").on("click", function (e) {
        e.stopPropagation();
        $feedOptionModal.removeClass("show").css("display", "none");
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
        $feedOptionModal.add($declarationModal).removeClass("show").css("display", "none");
        $overlay.removeClass("show").css("display", "none");
    });
});

$(document).ready(function () {
    const $overlay = $(".comment-modal-overlay");
    const $commentOptionModal = $(".comment-option-modal");
    const $commentDeclarationModal = $("#comment-declaration-modal");

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
        $commentDeclarationModal.addClass("show").css("display", "block");
        $overlay.addClass("show").css("display", "block"); // 오버레이 활성화
    });

    // 신고 모달 닫기
    $("#comment-declaration-modal .close").on("click", function () {
        $commentDeclarationModal.removeClass("show").css("display", "none");
        $overlay.removeClass("show").css("display", "none"); // 오버레이 비활성화
    });

    // 오버레이 클릭 시 모든 모달 닫기
    $overlay.on("click", function () {
        $feedOptionModal.add($declarationModal).removeClass("show").css("display", "none");
        $overlay.removeClass("show").css("display", "none");
    });
});