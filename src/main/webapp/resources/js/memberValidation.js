function checkForm() {
	
	if (!document.frmMemberInsert.id.value) {
		alert("아이디를 입력하세요.");
		frmMemberInsert.id.focus();
		return false;
	}
	
	if (frmMemberInsert.id.value.length < 5 || frmMemberInsert.id.value.length > 12){
		alert("아이디는 5자 이상 12자 이하로 작성해주세요.");
		frmMemberInsert.id.focus();
		return false;
	}
	

	if (!document.frmMemberInsert.password.value) {
		alert("비밀번호를 입력하세요.");
		frmMemberInsert.password.focus();
		return false;
	}
	
	//비밀번호 영문자+숫자+특수조합(8~25자리 입력) 정규식
	  var pwdCheck = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,25}$/;

	  if (!pwdCheck.test(frmMemberInsert.password.value)) {
	    alert("비밀번호는 영문자+숫자+특수문자 조합으로 8~25자리 사용해야 합니다.");
	    password.focus();
	    return false;
	  };
	  
	// 같은 문자 연속 4번 있는지
	  var pwdCheck2 = /(\w)\1\1\1/;
	
	  if (pwdCheck2.test(frmMemberInsert.password.value)) {
		    alert("같은 문자를 4번 이상 연속해서 사용하실 수 없습니다.");
		    password.focus();
		    return false;
		  };
		  
	// \s : 띄어쓰기
	// password.value.search(n) : n의 인덱스 반환, -1이면 없다는 뜻
	if(document.frmMemberInsert.password.value.search(" ") != -1){
		alert("비밀번호는 공백을 포함할 수 없습니다");
	    password.focus();
	    return false;
	}

	if (document.frmMemberInsert.password.value != document.frmMemberInsert.password_confirm.value) {
		alert("비밀번호를 동일하게 입력하세요.");
		frmMemberInsert.password_confirm.focus();
		return false;
	}
	
	
	if (!document.frmMemberInsert.name.value){
		alert("이름을 입력하세요.");
		frmMemberInsert.name.focus();
		return false;
	}
	
	//이름 : 한글 검사
	var regExpName = /^[가-힣]*$/;
	
	if (!regExpName.test(frmMemberInsert.name.value)){
		alert("이름은 한글만 입력해 주세요.");
		frmMemberInsert.name.focus();
		return false;
	}
	
	
	var num_check = /[0-9]/;	// 숫자 
	
	
	if (!document.frmMemberInsert.birthyy.value){
		alert("태어난 해를 입력하세요.");
		frmMemberInsert.birthyy.focus();
		return false;
	}
	
	if (!document.frmMemberInsert.birthmm.value){
		alert("태어난 월를 입력하세요.");
		frmMemberInsert.birthmm.focus();
		return false;
	}
	
	if (!document.frmMemberInsert.birthdd.value){
		alert("태어난 일을 입력하세요.");
		frmMemberInsert.birthdd.focus();
		return false;
	}
	
	if(num_check.test(frmMemberInsert.birthyy.value) == false){
		alert("숫자만 입력할 수 있습니다.");
		frmMemberInsert.birthyy.focus();
		return false;
	}
	
	if(num_check.test(frmMemberInsert.birthdd.value) == false){
		alert("숫자만 입력할 수 있습니다.");
		frmMemberInsert.birthdd.focus();
		return false;
	}
	
	
	
	if (!document.frmMemberInsert.mail1.value){
		alert("이메일을 입력하세요.");
		frmMemberInsert.mail1.focus();
		return false;
	}
	
	var exptext = /^[A-Za-z0-9_\.\-]+/;

			if(exptext.test(frmMemberInsert.mail1.value)==false){

			//이메일 형식이 알파벳+숫자@알파벳+숫자.알파벳+숫자 형식이 아닐경우			

			alert("이메일은 영어와 숫자만 조합할 수 있습니다.");

			frmMemberInsert.mail1.focus();

		return false;
			}
	
	if (!document.frmMemberInsert.address1.value){
		alert("주소를 입력하세요.");
		frmMemberInsert.address1.focus();
		return false;
	}
	
	if (!document.frmMemberInsert.address2.value){
		alert("상세 주소를 입력하세요.");
		frmMemberInsert.address2.focus();
		return false;
	}
	
	
	if (document.frmMemberInsert.phone2.value.length < 4){
		alert("휴대폰 번호 4자리를 입력하세요.");
		frmMemberInsert.phone2.focus();
		return false;
	}
	
	if (document.frmMemberInsert.phone3.value.length < 4){
		alert("휴대폰 번호 4자리를 입력하세요.");
		frmMemberInsert.phone3.focus();
		return false;
	}
	
	
	
	if(num_check.test(frmMemberInsert.phone2.value) == false){
		alert("숫자만 입력할 수 있습니다.");
		frmMemberInsert.phone2.focus();
		return false;
	}
	
	if(num_check.test(frmMemberInsert.phone3.value) == false){
		alert("숫자만 입력할 수 있습니다.");
		frmMemberInsert.phone3.focus();
		return false;
	}
	
	if (document.frmMemberInsert.termsYN.value == "no"){
		alert("약관에 동의해 주세요.");
		return false;
	}
	
}