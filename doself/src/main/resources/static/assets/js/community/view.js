$('#comment-submit').click(event => {
	event.preventDefault();
})

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
	}
		
	);
	request.fail(function(jQXHR, textStatus, error){
		console.log(error);
	});	
	
})