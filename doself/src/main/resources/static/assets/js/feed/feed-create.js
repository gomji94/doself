// 이미지 업로드 및 미리보기
const uploadBtn = document.querySelector('.upload-btn');
const imagePreview = document.getElementById('image-preview');

uploadBtn.addEventListener('click', () => {
    const input = document.createElement('input');
    input.type = 'file';
    input.accept = 'image';
    input.onchange = (event) => {
        const file = event.target.files[0];
        if (file) {
            const reader = new FileReader();
            reader.onload = () => {
                imagePreview.src = reader.result;
				imagePreview.style.display = "block";
				uploadPlaceholder.style.display = "none"; // Placeholder 숨기기
            };
            reader.readAsDataURL(file);
        }
    };
    input.click();
});

// 글자수 카운터
const content = document.getElementById('content');
const textCount = document.getElementById('text-count');

content.addEventListener('input', () => {
    textCount.textContent = content.value.length;
});