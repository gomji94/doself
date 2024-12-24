$(document).on('click', '.likeBtn', function() {
    const likeImg = $(this).find('.likeImg');
    const likedSrc = 'https://velog.velcdn.com/images/mekite/post/e8818752-b4ba-4e58-bdfb-e8c352cad8ea/image.png';
    const defaultSrc = 'https://velog.velcdn.com/images/mekite/post/5d41002f-857b-4c4e-9d7c-80fe9fb35e59/image.png';
    const currentSrc = likeImg.attr('src');

    if (currentSrc === defaultSrc) {
        likeImg.attr('src', likedSrc).css({ 'width': '24.5px', 'height': 'auto' });
    } else if (currentSrc === likedSrc) {
        likeImg.attr('src', defaultSrc).css({ 'width': '', 'height': '' });
    }
});
