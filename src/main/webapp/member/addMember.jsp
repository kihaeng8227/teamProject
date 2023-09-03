<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">

<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>회원가입</title>
<!-- <link rel="stylesheet" href="/teamProject/webapp/resources/css/bootstrap.min.css"> -->
<link rel="stylesheet" href="../resources/css/member.css">
</head>

<body>
	<script src="https://spi.maps.daum.net/imap/map_js_init/postcode.v2.js"></script>
	<script type="text/javascript" src="../resources/js/searchAddress.js"></script>
    <script type="text/javascript" src="../resources/js/memberValidation.js"></script>
	<jsp:include page="../inc/header.jsp" />
	<h4>회원가입</h4>
	<form class="formWrap" action="/AddMember.lo"
		name="frmMemberInsert" method="post" onsubmit="return checkForm()">
		<div class="row mb-3">
			<label for="id" class="col-sm-3 col-form-label">아이디</label>
			<div class="col-sm-7 d-flex id">
				<input type="text" class="form-control" id="id" name="id" placeholder="id"> 
				
				<input type="button" class="btn btn-primary btn-sm"
				id="idCheckButton" name="btnIsDuplication" value="아이디 중복 확인">
				
			</div>
		</div>
		
		<div class="row mb-3">
			<label for="" class="col-sm-3 col-form-label"></label>
			<div class="col-sm-5">
				<span class="idCheck" class="form-control">※아이디는 5자 이상 12자 이하로 작성해주세요.</span>
			</div>
		</div>
		
		<div class="row mb-3">
			<label for="password" class="col-sm-3 col-form-label">비밀번호</label>
			<div class="col-sm-5">
				<input type="password" class="form-control" id="password"
					name="password" placeholder="password">
			</div>
		</div>
		
		<div class="row mb-3">
			<label for="" class="col-sm-3 col-form-label"></label>
			<div class="col-sm-5">
				<span>※비밀번호는 영문자+숫자+특수문자 조합으로 8~25자리 사용해야 합니다.</span>
			</div>
		</div>
		
		<div class="row mb-3">
			<label for="password_confirm" class="col-sm-3 col-form-label">비밀번호 확인</label>
			<div class="col-sm-5">
				<input type="password" class="form-control" id="passwordC"
					name="password_confirm" placeholder="password confirm">
			</div>
		</div>
		
		<div class="row mb-3">
			<label for="" class="col-sm-3 col-form-label"></label>
			<div class="col-sm-5">
				<span class="pwCheck" class="form-control"></span>
			</div>
		</div>
		
		<div class="row mb-3">
			<label for="name" class="col-sm-3 col-form-label">이름</label>
			<div class="col-sm-5">
				<input type="text" class="form-control" id="name" name="name"
				placeholder="name">
			</div>
		</div>
		
		<div class="row mb-3">
			<label for="birth" class="col-sm-3 col-form-label">생년월일</label>
			<div class="col-sm-7 birth">
				<input type="text" name="birthyy" class="form-control" maxlength="4" placeholder="년(4자)" size="6"> 
				<select name="birthmm" class="form-control">
					<option value="">월</option>
					<option value="01">01</option>
					<option value="02">02</option>
					<option value="03">03</option>
					<option value="04">04</option>
					<option value="05">05</option>
					<option value="06">06</option>
					<option value="07">07</option>
					<option value="08">08</option>
					<option value="09">09</option>
					<option value="10">10</option>
					<option value="11">11</option>
					<option value="12">12</option>
				</select> <input type="text" name="birthdd" class="form-control" maxlength="2" placeholder="일" size="4">
			</div>
		</div>

		<div class="row mb-3">
			<label class="col-sm-3 col-form-label">성별</label>
			<div class="col-sm-5">
				<input name="gender" type="radio" value="남" checked /> 남 
				<input name="gender" type="radio" value="여" /> 여
			</div>
		</div>

		<div class="row mb-3">
			<label for="mail" class="col-sm-3 col-form-label">이메일</label>
			<div class="col-sm-7 d-flex">
				<input type="text" class="form-control" id="mail1" maxlength="50" name="mail1" placeholder="email">@
				<select name="mail2" id="mail2" class="form-control">
					<option value="naver.com">naver.com</option>
					<option value="daum.net">daum.net</option>
					<option value="gmail.com">gmail.com</option>
					<option value="nate.com">nate.com</option>
				</select>
			</div>
		</div>
		
		
		<div class="row mb-3">
			<label for="name" class="col-sm-3 col-form-label">주소</label>
			<div class="col-sm-7 zipcodeWrap">
				<div class="zipcode d-flex">
					<input name="zipcode" id="zipcode" readonly class="form-control">
					<button type="button" class="btn btn-primary btn-sm"
						onclick="execDaumPostcode();">우편번호 검색</button>
				</div>
				<input name="address1" id="address1" class="form-control" size="40"
					maxlength="40" readonly> 
				<input name="address2" id="address2" class="form-control" size="40" maxlength="40">
			</div>
		</div>

		<div class="row mb-3">
			<label for="phone" class="col-sm-3 col-form-label">휴대폰 번호</label>
			<div class="col-sm-7 phone d-flex">
				<select class="form-select" aria-label="Default select example"
					id="phone1" name="phone1">
					<option value="010">010</option>
					<option value="011">011</option>
					<option value="016">016</option>
					<option value="017">017</option>
					<option value="019">019</option>
				</select> 
				<input type="text" class="form-control" id="phone2" name="phone2" maxlength="4" size="4">
				<input type="text" class="form-control" id="phone3" name="phone3" maxlength="4" size="4">
			</div>
		</div>
		
		<div class="row mb-3">
			<label for="phoneC" class="col-sm-3 col-form-label">휴대폰 인증</label>
			<div class="col-sm-7 phoneC d-flex">
				<input type="text" class="form-control randomNum" id="phoneC" name="phoneC"
					placeholder="인증번호 입력">
				<button type="button" class="btn btn-primary btn-sm randomNumBtn">인증번호 받기</button>
			</div>
		</div>


		<script type="text/javascript">
			const randomNum = document.querySelector('.randomNum');
		    const randomNumBtn = document.querySelector('.randomNumBtn');
		    
		    randomNumBtn.addEventListener('click', function(){
		        console.log(Math.floor(Math.random() * 1000000));
		        randomNum.value = Math.floor(Math.random() * 1000000)
		    })
		</script>


		<br>

		<div class="row mb-3">
			<label for="interest" class="col-sm-3 col-form-label">동의 여부</label>
			<div class="col-sm-8">

				<div class="row mb-3">
					<div class="col-form-label col-sm-5 pt-0 text-center">
						메일 수신 여부
					</div>
					<div class="col-sm-5">
						<div class="form-check form-check-inline">
							<input class="form-check-input" type="radio" name="mailYN"
								id="mailY" value="yes" checked> 
							<label class="form-check-label" for="mailY"> 동의 </label>
						</div>
						<div class="form-check form-check-inline">
							<input class="form-check-input" type="radio" name="mailYN"
								id="mailN" value="no"> 
							<label class="form-check-label" for="mailN"> 동의 안함 </label>
						</div>
					</div>
				</div>
				<div class="row mb-3">
					<div class="col-form-label col-sm-5 pt-0 text-center">
						문자 수신 여부
					</div>
					<div class="col-sm-5">
						<div class="form-check form-check-inline">
							<input class="form-check-input" type="radio" name="smsYN"
								id="smsY" value="yes" checked> 
							<label class="form-check-label" for="smsY"> 동의 </label>
						</div>
						<div class="form-check form-check-inline">
							<input class="form-check-input" type="radio" name="smsYN"
								id="smsN" value="no"> 
								<label class="form-check-label" for="mailN"> 동의 안함 </label>
						</div>
					</div>
				</div>
				<div class="row mb-3">
					<div class="col-form-label col-sm-5 pt-0 text-center">약관동의</div>
					<div class="col-sm-5">
						<div class="form-check form-check-inline">
							<input class="form-check-input" type="radio" name="termsYN"
								id="termsY" value="yes"> <label
								class="form-check-label" for="termsY"> 동의 </label>
						</div>
						<div class="form-check form-check-inline">
							<input class="form-check-input" type="radio" name="termsYN"
								id="termsN" value="no" checked> <label
								class="form-check-label" for="termsN"> 동의 안함 </label>
						</div>
					</div>
				</div>

			</div>
		</div>
		<div class="text-center">
			<button type="button" onclick="goIndex()" 
			class="btn btn-primary">취소하기</button>
			<button type="submit" class="btn btn-primary">가입하기</button>
		</div>

	</form>
	
	
	
	<script>
	
		function goIndex(){
			window.location.href="http://localhost:8080/index.jsp";
		}
	
	
	
		document.addEventListener("DOMContentLoaded", function(){
			// const inputID = document.querySelector('input[name=id]');
			const frmMemberInsert = document.frmMemberInsert; // 폼을 들고옴.
			
			// 1. 팝업을 이용한 ID 중복 확인
			// 팝업을 띄우는 이유는, 현재 페이지에서 데이터베이스에 중복 조회를 할려면 페이지 새로고침 이외에는 방법이 없음.
			
			// 팝업을 이용하지않고 alert이용
			const btnIsDuplication = document.querySelector('input[name=btnIsDuplication]');
			btnIsDuplication.addEventListener('click', function(){
				const id = frmMemberInsert.id.value; // 아이디 input에 있는 값.
				if(id === ""){
					alert('아이디를 입력해 주세요.');
					frmMemberInsert.id.focus();
					return;
				}
				console.log(id);
				const xhr = new XMLHttpRequest(); // XMLHttpRequest
				xhr.open('GET', 'AjaxIdCheck.lo?id=' + id); // HTTP 요청 초기화. 통신 방식과 url 설정.
				xhr.send(); // url에 요청을 보냄.
				
				// 이벤트 등록. XMLHttpRequest 객체의 readyState 프로퍼티 값이 변할때마다 자동으로 호출
				xhr.onreadystatechange = () => {
					// readyState 프로퍼티의 값이 DONE : 요청한 데이터의 처리가 완료되어 응답할 준비가 완료됨.
					if( xhr.readyState !== XMLHttpRequest.DONE) return;
				
					if(xhr.status === 200){ // 서버 (url)에 문서가 존재함
						const json = JSON.parse(xhr.response);
						console.log(json);
						if(json.result === 'true'){
							alert('동일한 아이디가 있습니다.');
						}
						else{
							alert('동일한 아이디가 없습니다.');
						}
					}
					else{ // 서버 (url)에 문서가 존재하지 않음.
						console.error('Error', xhr.status, xhr.statusText);
					}
				}
				// 아이디 중복 확인을 위해 팝업을 띄움.
		
				//window.open('AjaxIdCheck.lo?id=' + id, 'IdCheck', 'width = 500, height = 500, top = 100, left = 200, location = no');
			});
			
			
			
			// 2. ajax를 이용한 실시간 ID 중복 확인
			/* const xhr = new XMLHttpRequest(); // XMLHttpRequest 객체 생성
			
			const inputId = document.querySelector('input[name=id]');
			inputId.addEventListener('keyup', function(){
				const id = frmMemberInsert.id.value; // 아이디 input에 있는 값.
				console.log(id);
				const idCheck = document.querySelector('.idCheck'); // 결과 문자열이 표현될 영역
				xhr.open('GET', 'AjaxIdCheck.lo'); // HTTP 요청 초기화. 통신 방식과 url 설정.
				xhr.send(); // url에 요청을 보냄.
				
				// 이벤트 등록. XMLHttpRequest 객체의 readyState 프로퍼티 값이 변할때마다 자동으로 호출
				xhr.onreadystatechange = () => {
					// readyState 프로퍼티의 값이 DONE : 요청한 데이터의 처리가 완료되어 응답할 준비가 완료됨.
					if( xhr.readyState !== XMLHttpRequest.DONE) return;
				
					if(xhr.status === 200){ // 서버 (url)에 문서가 존재함
						const json = JSON.parse(xhr.response);
						if(json.result === 'true'){
							idCheck.style.color = 'red';
							idCheck.innerHTML = '동일한 아이디가 있습니다.';
						}
						else{
							idCheck.style.color = 'gray';
							idCheck.innerHTML = '동일한 아이디가 없습니다.';
						}
					}
					else{ // 서버 (url)에 문서가 존재하지 않음.
						console.error('Error', xhr.status, xhr.statusText);
					}
				}
			}) */
			
			
			
		});
	</script>
	
	
</body>
</html>