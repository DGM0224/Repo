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
	
	// 댓글 쓰기 폼 띄우기
	$("#replyWrite").on("click", function() {
	
		if($("#replyForm").is(":visible")) {	
			let $prev = $("#replyTitle").prev();
			
			if(! $prev.is("#replyForm")) {
				$("#replyForm").slideUp(300);
			}
			
			setTimeout(function(){
				$("#replyForm").insertBefore("#replyTitle").slideDown(300);		
			}, 300);
		} else {
			$("#replyForm").removeClass("d-none").css("display", "none").insertBefore("#replyTitle").slideDown(300);
		}
		
		$("#replyForm").find("form").attr("id", "replyWriteForm").removeAttr("data-no");
		$("#replyContent").val("");
		$("#replyWriteButton").val("댓글쓰기");
	});
	
	// 댓글 쓰기
	$(document).on("submit", "#replyWriteForm", function() {
		
		if ($("#replyContent").val().length < 5) {
			alert("댓글은 5자 이상 입력해야 합니다.");
			return false;
		}
		
		let params = $(this).serialize();
		// this를 쿼리 문자열 형식으로 바꿈
		
		$.ajax({
			"url": "replyWrite.ajax",
			"data": params,
			"type": "post",
			"dataType": "json",
			"success": function(resData) {
				$("#replyList").empty();
				console.log(params);
				
				$.each(resData, function(i, v) {
					let date = new Date(v.replyRegDate);
					let strDate = date.getFullYear() + "-" + ((date.getMonth() + 1 < 10)
						? "0" + (date.getMonth() + 1) : (date.getMonth() + 1)) + "-"
						+ (date.getDate() < 10 ? "0" + date.getDate() : date.getDate()) + " "
						+ (date.getHours() < 10 ? "0" + date.getHours() : date.getHours()) + ":"
						+ (date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes()) + ":"
						+ (date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds());
					let result =	
						'<div class="replyRow row border border-top-0">'
						+'	<div class="col">'
						+'		<div class="row bg-light p-2">'
						+'			<div class="col-4">'
						+'				<span>' + v.memberId + '</span>'
						+'			</div>'
						+'			<div class="col-8 text-end">'
						+'				<span class="me-3">' + strDate + '</span>'
						+ '					<button class="modifyReply btn btn-outline-success btn-sm" data-no="' + v.replyNo + '">'
						+'					<i class="bi bi-journal-text">수정</i>'
						+'				</button>'
						+ '					<button class="deleteReply btn btn-outline-warning btn-sm" data-no="' + v.replyNo + '">' 
						+'					<i class="bi bi-trash">삭제</i>'
						+'				</button>'
						+'			</div>'
						+'		</div>'
						+'		<div class="row">'
						+'			<div class="col p-3">'
						+'				<pre>' + v.replyContent + '</pre>'
						+'			</div>'
						+'		</div>'
						+'	</div>'
						+'</div>'
										
					$("#replyList").append(result);
					$("#replyList").removeClass("text-center");
					$("#replyList").removeClass("p-5");
				}); 
				
				$("#replyForm").slideUp(300)
					.add("#replyContent").val("");
			},
			"error": function(xhr, status) {
				console.log("error : " + status);
			}
		});
		return false;
	});
	
	// 댓글 수정 폼 띄우기
	$(document).on("click", ".modifyReply", function() {
		
		let replyNo = $(this).attr("data-no");  
		console.log("수정할 댓글 번호: " + replyNo);
		
		let $replyRow = $(this).parents(".replyRow");
		if ($("#replyForm").is(":visible")) {
			let $next = $replyRow.next();
			if (!$next.is("#replyForm")) {
				$("#replyForm").slideUp(300);
			}
			setTimeout(function() {
				$("#replyForm").insertAfter($replyRow).slideDown(300);
			}, 300);
		} else { 
			$("#replyForm").removeClass("d-none").css("display", "none").insertAfter($replyRow).slideDown(300);
		}
		
		$("#replyForm").find("form").attr({ "id": "replyUpdateForm", "data-no": $(this).data("no") });
		$("#replyWriteButton").val("댓글수정");
		let reply = $(this).parent().parent().next().find("pre").text();
		$("#replyContent").val($.trim(reply));
	});
	
	// 댓글 수정
	$(document).on("submit", "#replyUpdateForm", function(e) {
		
		e.preventDefault();
		
		if ($("#replyContent").val().length <= 5) {
			alert("댓글은 5자 이상 입력해야 합니다.");
			return false;
		}
		$("#global-content > div").append($("#replyForm").slideUp(300));	
		
		let replyNo = $(this).attr("data-no");
		let params = $(this).serialize() + "&replyNo=" + replyNo;
		console.log(params);
		
		$.ajax({
			url: "replyUpdate.ajax",
			type: "patch",
			data: params,
			dataType: "json",
			success: function(resData, status, xhr) {
				
				$("#replyList").empty();
				
				$.each(resData, function(i, v) {
					let date = new Date(v.replyRegDate);
					let strDate = date.getFullYear() + "-" + ((date.getMonth() + 1 < 10)
						? "0" + (date.getMonth() + 1) : (date.getMonth() + 1)) + "-"
						+ (date.getDate() < 10 ? "0" + date.getDate() : date.getDate()) + " "
						+ (date.getHours() < 10 ? "0" + date.getHours() : date.getHours()) + ":"
						+ (date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes()) + ":"
						+ (date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds());
					let result =	
						'<div class="replyRow row border border-top-0">'
						+'	<div class="col">'
						+'		<div class="row bg-light p-2">'
						+'			<div class="col-4">'
						+'				<span>' + v.memberId + '</span>'
						+'			</div>'
						+'			<div class="col-8 text-end">'
						+'				<span class="me-3">' + strDate + '</span>'
						+ '					<button class="modifyReply btn btn-outline-success btn-sm" data-no="' + v.replyNo + '">'
						+'					<i class="bi bi-journal-text">수정</i>'
						+'				</button>'
						+ '					<button class="deleteReply btn btn-outline-warning btn-sm" data-no="' + v.replyNo + '">' 
						+'					<i class="bi bi-trash">삭제</i>'
						+'				</button>'
						+'			</div>'
						+'		</div>'
						+'		<div class="row">'
						+'			<div class="col p-3">'
						+'				<pre>' + v.replyContent + '</pre>'
						+'			</div>'
						+'		</div>'
						+'	</div>'
						+'</div>'
					
					
					$("#replyList").append(result);
				}); 
				$("#replyContent").val("");
			},
			error: function(xhr, status, error) {
				alert("ajax 실패 : " + status + " - " + xhr.status);
			}
		});
		return false;
	});
	
	// 댓글 삭제
	$(document).on("click", ".deleteReply", function() {
		$("#global-content > div").append($("#replyForm").slideUp(300));
		$("#replyContent").val("");
		let replyNo = $(this).attr("data-no");
		let memberID = $(this).parent().prev().find("span").text();
		let boardNo = $("#replyForm input[name=boardNo]").val();
		let params = "replyNo=" + replyNo + "&boardNo=" + boardNo;
		console.log(params);
		
		let result = confirm(memberID + "님이 작성한 " + replyNo + "번 댓글을 삭제하시겠습니까?");
		if (result) {
			$.ajax({
				url: "replyDelete.ajax",
				type: "delete",
				data: params,
				dataType: "json",
				success: function(resData, status, xhr) {
					console.log(resData);
					$("#replyList").empty();
					$.each(resData, function(i, v) {
						let date = new Date(v.replyRegDate);
						let strDate = date.getFullYear() + "-" + ((date.getMonth() + 1 < 10)
							? "0" + (date.getMonth() + 1) : (date.getMonth() + 1)) + "-"
							+ (date.getDate() < 10 ? "0" + date.getDate() : date.getDate()) + " "
							+ (date.getHours() < 10 ? "0" + date.getHours() : date.getHours()) + ":"
							+ (date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes()) +
							":"
							+ (date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds());
						let result =	
							'<div class="replyRow row border border-top-0">'
							+'	<div class="col">'
							+'		<div class="row bg-light p-2">'
							+'			<div class="col-4">'
							+'				<span>' + v.memberId + '</span>'
							+'			</div>'
							+'			<div class="col-8 text-end">'
							+'				<span class="me-3">' + strDate + '</span>'
							+ '					<button class="modifyReply btn btn-outline-success btn-sm" data-no="' + v.replyNo + '">'
							+'					<i class="bi bi-journal-text">수정</i>'
							+'				</button>'
							+ '					<button class="deleteReply btn btn-outline-warning btn-sm" data-no="' + v.replyNo + '">' 
							+'					<i class="bi bi-trash">삭제</i>'
							+'				</button>'
							+'			</div>'
							+'		</div>'
							+'		<div class="row">'
							+'			<div class="col p-3">'
							+'				<pre>' + v.replyContent + '</pre>'
							+'			</div>'
							+'		</div>'
							+'	</div>'
							+'</div>'
						
										
							$("#replyList").append(result);
					});
				},
				error: function(xhr, status, error) {
					alert("ajax 실패 : " + status + " - " + xhr.status);
				}
			});
		}

		return false;
	});
	
	
});