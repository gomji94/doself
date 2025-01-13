$(document).ready(function () {
    // 수정 버튼 클릭 시
    $('#comment-list').on('click', '#modify-comment-btn', function () {
        const commentLi = $(this).closest('li.comment');
        
        // 댓글 내용과 수정 폼을 찾는다
        const commentContent = commentLi.find('.comment-content');
        const commentEditForm = commentLi.find('#comment-edit-form');
        const commentRight = commentLi.find('.comment-info__right');

        // 댓글 내용 숨기기
        commentContent.hide();
        // 수정 폼 보이기
        commentEditForm.show();
        // 댓글 정보 오른쪽 숨기기
        commentRight.hide();
    });

    // 취소 버튼 클릭 시
    $('#comment-list').on('click', '#edit-comment-cancel-btn', function () {
        const commentLi = $(this).closest('li.comment');
        
        // 댓글 내용과 수정 폼을 찾는다
        const commentContent = commentLi.find('.comment-content');
        const commentEditForm = commentLi.find('#comment-edit-form');
        const commentRight = commentLi.find('.comment-info__right');

        // 댓글 내용 보이기
        commentContent.show();
        // 수정 폼 숨기기
        commentEditForm.hide();
        // 댓글 정보 오른쪽 보이기
        commentRight.show();
    });

    // 저장 버튼 클릭 시
    $('#comment-list').on('click', '#edit-comment-submit-btn', function (event) {
        event.preventDefault(); // 기본 폼 제출 동작을 막음
        
        const commentLi = $(this).closest('li.comment');
        const commentKeyNum = commentLi.find('input[name="commentKeyNum"]').val();
        const newContent = commentLi.find('.edit-comment-text').val().trim();

        // 빈 내용은 제출하지 않음
        if (newContent === "") {
            alert("댓글 내용을 입력해주세요.");
            return;
        }

        // 댓글을 수정하는 POST 요청
        $.ajax({
            url: '/community/modifycomment', // 댓글 수정 URL
            method: 'POST',
            data: {
                commentKeyNum: commentKeyNum,
                commentContent: newContent
            },
            dataType: 'json',
            success: function (response) {
                if (response.success) {
                    // 댓글 내용 수정 후 p 태그로 다시 변경
                    commentLi.find('.comment-content').text(newContent).show();
                    commentLi.find('#comment-edit-form').hide();
                    commentLi.find('.comment-info__right').show();
                } else {
                    alert("댓글 수정에 실패했습니다.");
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.log("Error:", textStatus, errorThrown);
                alert("댓글 수정에 실패했습니다.");
            }
        });
    });
});
