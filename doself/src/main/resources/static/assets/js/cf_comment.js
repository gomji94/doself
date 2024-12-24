$(document).ready(function () {
    // 댓글 모달 오버레이 및 컨테이너 설정
    const commentModalOverlay = $('.cf-comment-modal-overlay');
    const commentModalContainer = $('.cf-comment-modal-container');

    // 댓글 버튼 클릭 이벤트
    $('.commentBtn').on('click', function () {
        // 현재 피드 내 댓글 모달을 표시
        const feed = $(this).closest('.feed'); // 클릭된 댓글 버튼의 부모 피드 찾기
        const feedOverlay = feed.find('.cf-comment-modal-overlay'); // 해당 피드의 댓글 모달 오버레이 찾기

        // 다른 모달 숨기기
        commentModalOverlay.hide();

        // 해당 피드의 댓글 모달 보이기
        feedOverlay.show();
    });

    // 모달 닫기 버튼 이벤트
    $('.cf-comment-modalCloseBtn').on('click', function () {
        $(this).closest('.cf-comment-modal-overlay').hide(); // 현재 모달만 숨김
    });

    // 오버레이 클릭 시 모달 닫기
    $('.cf-comment-modal-overlay').on('click', function (e) {
        if (e.target === this) {
            $(this).hide();
        }
    });
});
