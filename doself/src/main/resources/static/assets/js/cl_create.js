document.addEventListener('DOMContentLoaded', () => {
    const uploadBtn = document.getElementById('cl-create-upload-btn'); // 파일 불러오기 버튼
    const fileInput = document.getElementById('cl-create-file-input'); // 파일 input 요소
    const imagePreview = document.getElementById('cl-create-preview-image'); // 미리보기 이미지
    const previewContainer = document.getElementById('cl-create-preview-container'); // 미리보기 컨테이너

    // 파일 불러오기 버튼 클릭 이벤트
    uploadBtn.addEventListener('click', () => {
        fileInput.click(); // 파일 선택 창 열기
    });

    // 파일 선택 시 이벤트
    fileInput.addEventListener('change', (event) => {
        const file = event.target.files[0];
        if (file) {
            const reader = new FileReader();
            reader.onload = (e) => {
                imagePreview.src = e.target.result; // 이미지 소스를 미리보기로 설정
                previewContainer.style.display = 'block'; // 미리보기 컨테이너 보이기
            };
            reader.readAsDataURL(file); // 파일을 DataURL로 읽기
        }
    });

    // 글자수 카운터
    const content = document.getElementById('content'); // 텍스트 입력 박스
    const textCount = document.getElementById('text-count'); // 글자 수 카운터
    const maxLength = 500; // 최대 글자 수

    content.addEventListener('input', () => {
        const currentLength = content.value.length;
        textCount.textContent = currentLength;

        if (currentLength > maxLength) {
            textCount.style.color = 'red'; // 초과 시 색상 변경
        } else {
            textCount.style.color = ''; // 기본 색상
        }
    });
});
