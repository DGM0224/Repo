$(function() {
	
	// 좋아요/싫어요
	$(".btnCommend").click(function() {
		let com = $(this).attr("id");
		console.log("com : " + com);
		$.ajax({
			url: "recommend.ajax",
			type: "post",
			data: { recommend: com, boardNo: $("#boardNo").val() },
			dataType: "json",
			success: function(data) {
				let msg = com == 'boardLike' ? "좋아요가" : "싫어요가";
				alert(msg + " 반영 되었습니다.");
				$("#boardLike> .recommend").text(" (" + data.boardLike + ")");
				$("#boardDislike > .recommend").text(" (" + data.boardDislike + ")");
			},
			error: function(xhr, status, error) {
				alert("error : " + xhr.statusText + ", " + status + ", " + error);
			}
		});
	});
	
	// 댓글 쓰기
	
	
	
	
	
});