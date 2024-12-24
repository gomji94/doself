// 이미지 업로드 및 미리보기
document.addEventListener('DOMContentLoaded', () => {
    const uploadBtn = document.getElementById('cf-modify-upload-btn');
    const fileInput = document.getElementById('cf-modify-file-input');
    const imagePreview = document.getElementById('image-preview');
    const previewContainer = document.getElementById('cf-modify-preview-container');

    uploadBtn.addEventListener('click', () => {
        fileInput.click(); // 파일 선택 창 열기
    });

    fileInput.addEventListener('change', (event) => {
        const file = event.target.files[0];
        if (file) {
            const reader = new FileReader();
            reader.onload = (e) => {
                imagePreview.src = e.target.result; // 미리보기 설정
                previewContainer.style.display = 'block'; // 미리보기 보이기
            };
            reader.readAsDataURL(file);
        }
    });

    // 글자 수 카운트
    const content = document.getElementById('cf-modify-content');
    const textCount = document.getElementById('cf-modify-text-count');
    const maxLength = 2000;

    content.addEventListener('input', () => {
        const currentLength = content.value.length;
        textCount.textContent = currentLength;

        // 글자수 초과 시 스타일 변경
        if (currentLength > maxLength) {
            textCount.style.color = 'red';
        } else {
            textCount.style.color = '';
        }
    });
});
