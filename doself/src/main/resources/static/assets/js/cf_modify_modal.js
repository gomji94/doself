$(document).ready(function () {
    // 모달 열기
    $('#cl-modify-modal').on('click', function () {
      $('#cf-modify-modalOverlay').fadeIn(200);
    });
  
    // 모달 닫기 (닫기 버튼 클릭 시)
    $('#cf-modify-modalCloseBtn').on('click', function () {
      $('#cf-modify-modalOverlay').fadeOut(200);
    });
  
    // 모달 닫기 (오버레이 클릭 시)
    $('#cf-modify-modalOverlay').on('click', function (e) {
      if ($(e.target).is('#cf-modify-modalOverlay')) {
        $(this).fadeOut(200);
      }
    });
  });