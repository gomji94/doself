$(document).ready(function () {
	
    // 옵션 버튼 클릭 시 모달창 표시
    $('.create-button button').on('click', function () {
        $('.popup-wrap').fadeIn(); // 모달창 활성화
    });

    // 닫기 버튼 클릭 시 모달창 닫기
    $('.popup-wrap .close').on('click', function () {
        $('.popup-wrap').fadeOut(); // 모달창 비활성화
    });

    // 모달창 바깥을 클릭하면 모달창 닫기
    $('.popup-wrap').on('click', function (e) {
        if ($(e.target).is('.popup-wrap')) {
            $(this).fadeOut();
        }
    });
});

$(document).ready(function () {
    // 옵션 버튼 클릭 시 모달창 표시
    $('#modal-request-form__submitButton').on('click', function () {
        $('.confirm-popup-wrap').fadeIn(); // 모달창 활성화
        $('.popup-wrap').fadeOut();
        // 2초 후에 새 창을 닫고 다른 페이지로 이동
        setTimeout(() => {
            $('.confirm-popup-wrap').fadeOut();
			
            window.open("/medicine/list", "_self");
        }, 1000);
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
});