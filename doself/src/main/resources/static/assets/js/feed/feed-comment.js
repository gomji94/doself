document.addEventListener("DOMContentLoaded", () => {
    const commentButtons = document.querySelectorAll(".comment-img");

    commentButtons.forEach(button => {
        button.addEventListener("click", () => {
            // 이동할 페이지 경로
            window.location.href = "/feed/comments";
        });
    });
});