$(function() {
	// 게시글 쓰기 폼 유효성 검사
	$("#writeForm").on("submit", function() {
		if($("#memberId").val().length <= 0) {
		alert("작성자가 입력되지 않았습니다.\n작성자를 입력해주세요");
		$("#memberId").focus();
		return false;
	}
	
	if($("#boardTitle").val().length <= 0) {
		alert("제목이 입력되지 않았습니다.\n제목을 입력해주세요");
		$("#boardTitle").focus();
		return false;
	}
	
	if($("#boardPass").val().length <= 0) {
		alert("비밀번호가 입력되지 않았습니다.\n비밀번호를 입력해주세요");
		$("#boardPass").focus();
		return false;
	}
	
	if($("#boardContent").val().length <= 0) {
		alert("내용이 입력되지 않았습니다.\n내용을 입력해주세요");
		$("#boardContent").focus();
		return false;
	}
	
	});
	
	// 게시글 수정 비밀번호 확인
	$("#detailUpdate").on("click", function() {
		let pass = $("#boardPass").val();
		if(pass.length <= 0) {
		alert("게시글을 수정하려면 비밀번호를 입력해주세요");
		return false;
	}	
	
	$("#rPass").val(pass);
	$("#checkForm").attr("action", "updateForm");
	$("#checkForm").attr("method", "post");
	$("#checkForm").submit();
	
	});
	
	// 게시글 수정 폼 유효성 검사
	$("#updateForm").on("submit", function() {
	if($("#memberId").val().length <= 0) {
		alert("작성자가 입력되지 않았습니다.\n작성자를 입력해주세요");
		$("#memberId").focus();
		return false;
	}
	
	if($("#boardTitle").val().length <= 0) {
		alert("제목이 입력되지 않았습니다.\n제목을 입력해주세요");
		$("#boardTitle").focus();
		return false;
	}
	
	if($("#boardPass").val().length <= 0) {
		alert("비밀번호가 입력되지 않았습니다.\n비밀번호를 입력해주세요");
		$("#boardPass").focus();
		return false;
	}
	
	if($("#boardContent").val().length <= 0) {
		alert("내용이 입력되지 않았습니다.\n내용을 입력해주세요");
		$("#boardContent").focus();
		return false;
	}
	
	});
	
	// 게시글 삭제 비밀번호 확인
	$("#detailDelete").on("click", function() {
		let pass = $("#boardPass").val();
		if(pass.length <= 0) {
		alert("게시글을 삭제하려면 비밀번호를 입력해주세요");
		return false;
	}
	
	$("#rPass").val(pass);
	$("#checkForm").attr("action", "delete");
	$("#checkForm").attr("method", "post");
	$("#checkForm").submit();
	});
	
	
});