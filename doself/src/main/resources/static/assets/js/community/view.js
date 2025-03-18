let occurTypeValue = null;
let reportedKeyNum = null;

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
		location.reload();
	});
	request.fail(function(jQXHR, textStatus, error){
		console.log(error);
	});	
	
})

// 모든 신고 버튼에 클릭 이벤트 추가
$('[data-occurType]').on('click', function (event) {
    event.preventDefault(); // 기본 동작 방지

    // 신고 유형 가져오기 (int로 변환)
    occurTypeValue = parseInt($(this).data('occurtype'), 10);
	
	// 신고 대상 객체의 키값 설정
	if (occurTypeValue === 1) { // 게시글 신고
	    reportedKeyNum = $('#articleNum').val(); // 게시글의 키값
	} else if (occurTypeValue === 2) { // 댓글 신고
	    reportedKeyNum = $(this).data('comment-key'); // 댓글의 키값
	}

    // 신고 모달의 hidden input에 값 설정
    const $form = $('#modal-request-form');
    let $occurTypeInput = $form.find('input[name="occurType"]');

    if ($occurTypeInput.length === 0) {
        // hidden input이 없으면 생성
        $occurTypeInput = $('<input>', {
            type: 'hidden',
            name: 'occurType',
        });
        $form.append($occurTypeInput);
    }

    // occurType 값 설정
    $occurTypeInput.val(occurTypeValue);

    $('.popup-wrap').fadeIn(); // 모달창 활성화
});


// 외부 클릭 시 모든 메뉴 닫기
$(document).on('click', function () {
    $('.comment-menu').hide();
});

// 메뉴 클릭 시 이벤트 전파 방지
$('.comment-menu').on('click', function (event) {
    event.stopPropagation();
});

// 옵션 버튼 클릭 시 모달창 표시
$('#modal-request-form__submitButton').on('click', function () {
	
	let isSubmit = true;
	
	const $reportContent = $('#reportContent').val().trim();
	const inputValueLength = $reportContent.length;
	
	if(inputValueLength === 0) {
		alert('신고 사유를 입력해주세요');
		isSubmit = false;
		$('#reportContent').focus();
		return false;
	}
	
	if(isSubmit) {
		const request = $.ajax({
			url: '/community/createreport',
			method: 'post',
			data : { 	reportCateNum : $('#reports-code').val(), 
						reportContent : $('#reportContent').val(), 
						occurType : occurTypeValue,
						reportedKeyNum: reportedKeyNum
					},
			dataType: 'json'
		});
		
		request.done(response => {
	        // 2초 후에 새 창을 닫고 다른 페이지로 이동
	        $('.confirm-popup-wrap').fadeIn(); // 모달창 활성화
	        $('.popup-wrap').fadeOut();
	        setTimeout(() => {
	            $('.confirm-popup-wrap').fadeOut();
				location.reload();
				
	        }, 1000);
			
		});
		
		request.fail((jqXHR, textStatus, error)=>{
			console.log(error)
		})
		
	}

});

// 닫기 버튼 클릭 시 모달창 닫기
$('#modal-request-form__cancelButton').on('click', function () {
   $('.popup-wrap').fadeOut(); // 모달창 비활성화
});

// 모달창 바깥을 클릭하면 모달창 닫기
$('.confirm-popup-wrap').on('click', function (e) {
    if ($(e.target).is('.confirm-popup-wrap')) {
        $(this).fadeOut();
    }
});


