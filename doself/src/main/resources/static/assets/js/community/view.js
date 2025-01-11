$('#article-delete__button').click(event => {
	event.preventDefault();
 	const isDelete = confirm('게시글을 삭제하시겠습니까?');
	if(isDelete) $('#deleteForm').submit();
})

$('#article-like__button').click(event => {
	
	// ajax 요청
	const request = $.ajax({
		url : '/community/like',
		method : 'POST',
		data : { 'likeOccurArticleNum' : $('#articleNum').val(), 'currentArticleLikeCnt' : $('#currentArticleLikeCnt').val() },
		dataType : 'json'
	});
	request.done(response => {
		console.log(response);
	}
		
	);
	request.fail(function(jQXHR, textStatus, error){
		console.log(error);
	});	
	
})

$(document).ready(function () {
	
    // 옵션 버튼 클릭 시 모달창 표시
    $('#article-report__button').on('click', function () {
        $('.popup-wrap').fadeIn(); // 모달창 활성화
    });

    // 닫기 버튼 클릭 시 모달창 닫기
    $('#modal-request-form__cancelButton').on('click', function () {
        $('.popup-wrap').fadeOut(); // 모달창 비활성화
    });

    // 모달창 바깥을 클릭하면 모달창 닫기
    $('.popup-wrap').on('click', function (e) {
        if ($(e.target).is('.popup-wrap')) {
            $(this).fadeOut();
        }
    });
});



// 댓글 메뉴 토글
$('.comment-button').on('click', function (event) {
    const $button = $(this); // 클릭한 버튼
    const $menu = $button.siblings('.comment-menu'); // 버튼 옆의 메뉴
    const buttonOffset = $button.offset(); // 버튼의 화면 위치 계산

    // 현재 버튼 기준으로 메뉴 위치 지정
    $menu.css({
        top: buttonOffset.top + $button.outerHeight(), // 버튼 바로 아래
        left: buttonOffset.left, // 버튼의 왼쪽 위치
        display: 'block', // 메뉴 표시
        position: 'absolute', // 위치 설정
    });

    // 다른 메뉴 닫기 (현재 클릭한 버튼 제외)
    $('.comment-menu').not($menu).hide();

    event.stopPropagation(); // 이벤트 전파 방지
});

// 외부 클릭 시 모든 메뉴 닫기
$(document).on('click', function () {
    $('.comment-menu').hide();
});

// 메뉴 클릭 시 이벤트 전파 방지
$('.comment-menu').on('click', function (event) {
    event.stopPropagation();
});


