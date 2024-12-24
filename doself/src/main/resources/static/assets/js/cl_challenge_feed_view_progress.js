let currentProgress = 0;
const targetProgress = 83; // 목표 값
const progressCircle = document.querySelector(".foreground-circle");
const progressPercent = document.getElementById("progress-percent-txt");

// 그래프 업데이트 함수
function updateProgress() {
    if (currentProgress < targetProgress) {
        currentProgress++;
        const offset = 314 - (314 * currentProgress) / 100; // 계산된 stroke-dashoffset
        progressCircle.style.strokeDashoffset = offset;
        progressPercent.textContent = `${currentProgress}%`;
        requestAnimationFrame(updateProgress); // 부드러운 애니메이션
    }
}

// 페이지 로드 시 그래프 시작
document.addEventListener("DOMContentLoaded", () => {
    updateProgress();
});