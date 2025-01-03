$(document).ready(function () {
    // 댓글 버튼 클릭 이벤트
    $('.commentBtn').on('click', function () {
        // feed-comment.html 모달 표시
        const commentModal = $('.feed-comment-modal-overlay'); // 댓글 모달 오버레이
        commentModal.fadeIn(300); // 부드럽게 모달 표시
    });

    // 모달 닫기 버튼 클릭 이벤트
    $('.feed-comment-modalCloseBtn').on('click', function () {
        $('.feed-comment-modal-overlay').fadeOut(300); // 부드럽게 모달 숨기기
    });

    // 모달 오버레이 클릭 시 모달 닫기
    $('.feed-comment-modal-overlay').on('click', function (e) {
        if ($(e.target).is('.feed-comment-modal-overlay')) {
            $(this).fadeOut(300);
        }
    });
});