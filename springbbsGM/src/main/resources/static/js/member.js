$(function() {
	// 회원 로그인 폼이 submit 될 때 폼 유효성 검사를 위한 이벤트 처리
	$("#loginForm").submit(function() {
		let id = $("#userId").val();
		let pass = $("#userPass").val();
		if (id.length <= 0) {
			alert("아이디가 입력되지 않았습니다.\n아이디를 입력해주세요");
			$("#userId").focus();
			return false;
		}
		if (pass.length <= 0) {

			alert("비밀번호가 입력되지 않았습니다.\n비밀번호를 입력해주세요");
			$("#userPass").focus();
			return false;
		}
	});
	
	// 모달 로그인 폼이 submit 될 때 폼 유효성 검사를 위한 이벤트 처리
	$("#modalLoginForm").submit(function() {
		let id = $("#modalUserId").val();
		let pass = $("#modalUserPass").val();
		if (id.length <= 0) {
			alert("아이디가 입력되지 않았습니다.\n아이디를 입력해주세요");
			$("#modalUserId").focus();
			return false;
		}
		if (pass.length <= 0) {
			alert("비밀번호가 입력되지 않았습니다.\n비밀번호를 입력해주세요");
			$("#modalUserPass").focus();
			return false;
		}
	});
	
	// 회원가입 - id, pass, eamilId, emailDomain에 유효한 값 받도록 처리
	$("#id").on("keyup", inputCharReplace);
	$("#checkId").on("keyup", inputCharReplace);
	$("#pass1").on("keyup", inputCharReplace);
	$("#pass2").on("keyup", inputCharReplace);
	$("#emailId").on("keyup", inputCharReplace);
	$("#emailDomain").on("keyup", inputEmailDomainReplace);
	
	// 회원가입 - 아이디 중복 체크
	$("#btnOverlapId").on("click", function() {
		let id = $("#id").val();
		url="overlapIdCheck?id=" + id;
		if(id.length == 0) {
			alert("아이디를 입력해주세요");
			return false;
		}
		if(id.length < 5) {
			alert("아이디는 5자 이상 입력해주세요.");
			return false;
		}
		window.open(url, "idCheck", "toolbar=no, scrollbars=no, resizeable=no, status=no, memubar=no, width=500, height=330");
	});		
	
	// 회원가입 - 닉네임 중복 체크
	$("#btnOverlapNickname").on("click", function() {
		let nickname = $("#nickname").val();
		url="overlapNicknameCheck?nickname=" + nickname;
		if(nickname.length == 0) {
			alert("닉네임을 입력해주세요");
			return false;
		}
		if(nickname.length < 5) {
			alert("닉네임은 5자 이상 입력해주세요.");
			return false;
		}
		window.open(url, "nicknameCheck", "toolbar=no, scrollbars=no, resizeable=no, status=no, memubar=no, width=500, height=330");
	});

	// 새로 띄운 id 확인 창에서 유효성 검사
	$("#idCheckForm").on("submit", function() {
		let id = $("#checkId").val();
		if(id.length == 0) {
			alert("아이디를 입력해주세요");
			return false;
		}
		if(id.length < 5) {
			alert("아이디는 5자 이상 입력해주세요.");
			return false;
		}
	
	});
	
	// 새로 띄운 id 확인 창에서 아이디 사용하기 버튼 클릭시
	$("#btnIdCheckClose").on("click", function() {
		let id = $(this).attr("data-id-value");
		opener.document.joinForm.id.value = id;
		opener.document.joinForm.isIdCheck.value = true;
		window.close();
	});
	
	// 새로 띄운 nickname 확인 창에서 유효성 검사
	$("#nicknameCheckForm").on("submit", function() {
		let nickname = $("#checkNickname").val();
		if(nickname.length == 0) {
			alert("닉네임을 입력해주세요");
			return false;
		}
		if(nickname.length < 5) {
			alert("닉네임은 5자 이상 입력해주세요.");
			return false;
		}
	
	});
	
	// 새로 띄운 nickname 확인 창에서 닉네임 사용하기 버튼 클릭시
	$("#btnNicknameCheckClose").on("click", function() {
		let nickname = $(this).attr("data-id-value");
		opener.document.joinForm.nickname.value = nickname;
		opener.document.joinForm.isNicknameCheck.value = true;
		window.close();
	});
	
	// 우편번호 찾기
	$("#btnZipcode").click(findZipcode);
	
	// 이메일 도메인 선택시 자동 입력
	$("#selectDomain").on("change", function() {
		let str = $(this).val();
		if(str == "직접입력") {
			$("#emailDomain").val("");
			$("#emailDomain").prop("readonly", false);
		} else if(str == "네이버"){
			$("#emailDomain").val("naver.com");
			$("#emailDomain").prop("readonly", true);
		} else if(str == "다음") {
			$("#emailDomain").val("daum.net");
			$("#emailDomain").prop("readonly", true);
		} else if(str == "한메일"){
			$("#emailDomain").val("hanmail.net");
			$("#emailDomain").prop("readonly", true);
		} else if(str == "구글") {
			$("#emailDomain").val("gmail.com");
			$("#emailDomain").prop("readonly", true);
		}
	});
	
	// 회원가입 폼이 submit될 때 유효성 검사
	$("#joinForm").on("submit", function() {
		return joinFormCheck();
	});

	
		
});





function inputCharReplace() {
	let regExp = /[^A-Za-z0-9]/gi;
	if(regExp.test($(this).val())) {
		alert("영문 대소문자, 숫자만 입력할 수 있습니다.");
		$(this).val($(this).val().replace(regExp, ""));
	}
}

function inputEmailDomainReplace() {
	let regExp = /[^a-z0-9\.]/gi;
	if(regExp.test($(this).val())) {
		alert("이메일 도메인은 영문 소문자, 숫자, 점(.)만 입력할 수 있습니다.");
		$(this).val($(this).val().replace(regExp, ""));
	}
}


function joinFormCheck() {
	let name = $("#name").val();
	let id = $("#id").val();
	let nickname = $("#nickname").val();
	let pass1 = $("#pass1").val();
	let pass2 = $("#pass2").val();
	let zipcode = $("#zipcode").val();
	let address1 = $("#address1").val();
	let address2 = $("#address2").val();
	let emailId = $("#emailId").val();
	let emailDomain = $("#emailDomain").val();
	let mobile2 = $("#mobile2").val();
	let mobile3 = $("#mobile3").val();
	let isIdCheck = $("#isIdCheck").val();
	let isNicknameCheck = $("#isNicknameCheck").val();
	
	if(name.length == 0) {
		alert("이름이 입력되지 않았습니다.\n이름을 입력해주세요");
		return false;
	}
	if(id.length == 0) {
		alert("아이디가 입력되지 않았습니다.\n아이디를 입력해주세요");
		return false;
	}
	if(isIdCheck == 'false') {
		alert("아이디 중복 체크를 하지 않았습니다.\n아이디 중복 체크를 해주세요");
		return false;
	}
	if(nickname.length == 0) {
		alert("닉네임이 입력되지 않았습니다.\n닉네임을 입력해주세요");
		return false;
	}
	if(isNicknameCheck === 'false') {
		alert("닉네임 중복 체크를 하지 않았습니다.\n닉네임 중복 체크를 해주세요");
		return false;
	}
	if(pass1.length == 0) {
		alert("비밀번호가 입력되지 않았습니다.\n비밀번호를 입력해주세요");
		return false;
	}
	if(pass2.length == 0) {
		alert("비밀번호 확인이 입력되지 않았습니다.\n비밀번호 확인을 입력해주세요");
		return false;
	}
	if(pass1 != pass2) {
		alert("비밀번호와 비밀번호 확인이 일치하지 않습니다.");
		return false;
	}
	if(zipcode.length == 0) {
		alert("우편번호가 입력되지 않았습니다.\n우편번호를 입력해주세요");
		return false;
	}
	if(address1.length == 0) {
		alert("주소가 입력되지 않았습니다.\n주소를 입력해주세요");
		return false;
	}
	if(address2.length == 0) {
		alert("상세주소가 입력되지 않았습니다.\n주소를 입력해주세요");
		return false;
	}
	if(emailId.length == 0) {
		alert("이메일 아이디가 입력되지 않았습니다.\n이메일 아이디를 입력해주세요");
		return false;
	}
	if(emailDomain.length == 0) {
		alert("이메일 도메인이 입력되지 않았습니다.\n이메일 도메인을 입력해주세요");
		return false;
	}
	if(mobile2.length == 0 || mobile3.length == 0) {
		alert("휴대폰 번호가 입력되지 않았습니다.\n휴대폰 번호를 입력해주세요");
		return false;
	}
}


function findZipcode() {
	new daum.Postcode({
	oncomplete: function(data) {
		// 우편번호 검색 결과 항목을 클릭했을때 실행할 코드를 여기에 작성한다.
		// 각 주소의 노출 규칙에 따라 주소를 조합한다.
		// 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기한다.
		let addr = ''; // 주소 변수
		let extraAddr = ''; // 참고 항목 변수
		//사용자가 선택한 주소 타입과 상관없이 모두 도로명 주소로 처리
		addr = data.roadAddress;
		// 법정동명이 있을 경우 추가한다. (법정리는 제외)
		// 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
		if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
			extraAddr += data.bname;
		}
		// 건물명이 있고, 공동주택일 경우 추가한다.
		if(data.buildingName !== '' && data.apartment === 'Y'){
			extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
		}
		// 표시할 참고 항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
		if(extraAddr !== ''){
			extraAddr = ' (' + extraAddr + ')';
		}
		// 조합된 참고 항목을 상세주소에 추가한다.
		addr += extraAddr;
		// 우편번호와 주소 정보를 해당 입력상자에 출력한다.
		$("#zipcode").val(data.zonecode);
		$("#address1").val(addr);
		// 커서를 상세주소 입력상자로 이동한다.
		$("#address2").focus();
		}
	}).open();
}