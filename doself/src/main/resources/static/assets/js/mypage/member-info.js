$(document).ready(function () {
    // 옵션 버튼 클릭 시 모달창 표시
    $('#addMedicineButton').on('click', function () {
        $('.refund-popup-wrap').fadeIn(); // 모달창 활성화
    });

    // 닫기 버튼 클릭 시 모달창 닫기
    $('.refund-popup-wrap .close').on('click', function () {
        $('.refund-popup-wrap').fadeOut(); // 모달창 비활성화
    });

    // 모달창 바깥을 클릭하면 모달창 닫기
    $('.refund-popup-wrap').on('click', function (e) {
        if ($(e.target).is('.refund-popup-wrap')) {
            $(this).fadeOut();
        }
    });
});