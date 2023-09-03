<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="../resources/css/member.css">

<link rel="stylesheet" href="../resources/css/bootstrap.min.css">
<%-- <%
	String sessionId = (String) session.getAttribute("sessionId");
%> --%>

<meta charset="UTF-8">
	<title>회원 정보 수정</title>
</head>
<script src="https://spi.maps.daum.net/imap/map_js_init/postcode.v2.js"></script>
<script type="text/javascript" src="../resources/js/searchAddress.js"></script>
<script type="text/javascript" src="../resources/js/memberValidation.js"></script>
    
<body>
	<jsp:include page="../inc/header.jsp" />
	
	
	<h4>회원 수정</h4>
	<form class="formWrap" action="UpdateMember.lo"
		name="frmMemberInsert" method="post" onsubmit="return checkForm()">
		<div class="row mb-3">
			<label for="id" class="col-sm-3 col-form-label">아이디</label>
			<div class="col-sm-7 d-flex id">
				<input type="text" class="form-control" id="id" name="id" placeholder="id"
				value="${memberDTO.id}" readonly > 
			</div>
		</div>
		
		<div class="row mb-3">
			<label for="password" class="col-sm-3 col-form-label">비밀번호</label>
			<div class="col-sm-5">
				<input type="password" class="form-control" id="password"
					name="password" placeholder="password" value="${memberDTO.password}">
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
			<label for="name" class="col-sm-3 col-form-label">이름</label>
			<div class="col-sm-5">
				<input type="text" class="form-control" id="name" name="name"
				placeholder="name" value="${memberDTO.name}">
			</div>
		</div>
		
		<div class="row mb-3">
			<label for="birth" class="col-sm-3 col-form-label">생년월일</label>
			<div class="col-sm-7 birth">
				<input type="text" name="birthyy" class="form-control" maxlength="4" placeholder="년(4자)" size="6" value="${memberDTO.birth.split('/')[0]}"> 
				<select name="birthmm" id="birthmm" class="form-control">
					<option value="${memberDTO.birth.split('/')[1]}" selected>${memberDTO.birth.split('/')[1]}</option>
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
				</select> <input type="text" name="birthdd" class="form-control" maxlength="2" placeholder="일" size="4" value="${memberDTO.birth.split('/')[2]}">
			</div>
		</div>
		
		
		

		<div class="row mb-3">
			<label class="col-sm-3 col-form-label">성별</label>
			<div class="col-sm-5">
				<c:set var="gender" value="${memberDTO.gender }" />
				<input name="gender" type="radio" value="남" <c:if test="${memberDTO.gender.equals('남') }"> <c:out value="checked" /> </c:if> >남 
				<input name="gender" type="radio" value="여" <c:if test="${memberDTO.gender.equals('여') }"> <c:out value="checked" /> </c:if> >여
			</div>
		</div>
		

		<div class="row mb-3">
			<label for="mail" class="col-sm-3 col-form-label">이메일</label>
			<div class="col-sm-7 d-flex">
				<input type="text" class="form-control" id="mail1" maxlength="50" name="mail1" value="${memberDTO.mail.split('@')[0]}">@
				<select name="mail2" id="mail2" class="form-control">
					<option value="${memberDTO.mail.split('@')[1]}" selected>${memberDTO.mail.split('@')[1]}</option>
					<option value="">선택</option>
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
					<input name="zipcode" id="zipcode" readonly class="form-control" value="${memberDTO.address.split('/')[0] }">
					<button type="button" class="btn btn-primary btn-sm"
						onclick="execDaumPostcode();">우편번호 검색</button>
				</div>
				<input name="address1" id="address1" class="form-control" size="40" value="${memberDTO.address.split('/')[1] }"
					maxlength="40" readonly> 
				<input name="address2" id="address2" class="form-control" size="40" maxlength="40"
					value="${memberDTO.address.split('/')[2] }">
			</div>
		</div>

		<div class="row mb-3">
			<label for="phone" class="col-sm-3 col-form-label">휴대폰 번호</label>
			<div class="col-sm-7 phone d-flex">
				<select class="form-select" aria-label="Default select example"
					name="phone1" value="${memberDTO.phone.split('-')[0] }">
					<option value="${memberDTO.phone.split('-')[0] }" selected>${memberDTO.phone.split('-')[0] }</option>
					<option value="">선택</option>
					<option value="010">010</option>
					<option value="011">011</option>
					<option value="016">016</option>
					<option value="017">017</option>
					<option value="019">019</option>
				</select> 
				<input type="text" class="form-control" id="phone2" name="phone2" maxlength="4" size="4" value="${memberDTO.phone.split('-')[1] }">
				<input type="text" class="form-control" id="phone3" name="phone3" maxlength="4" size="4" value="${memberDTO.phone.split('-')[2] }">
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
								id="mailY" value="yes"<c:if test="${memberDTO.receive_mail.equals('yes') }"> <c:out value="checked" /> </c:if>> 
							<label class="form-check-label" for="mailY"> 동의 </label>
						</div>
						<div class="form-check form-check-inline">
							<input class="form-check-input" type="radio" name="mailYN"
								id="mailN" value="no" <c:if test="${memberDTO.receive_mail.equals('no') }"> <c:out value="checked" /> </c:if>> 
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
								id="smsY" value="yes" <c:if test="${memberDTO.receive_phone.equals('yes') }"> <c:out value="checked" /> </c:if>> 
							<label class="form-check-label" for="smsY"> 동의 </label>
						</div>
						<div class="form-check form-check-inline">
							<input class="form-check-input" type="radio" name="smsYN"
								id="smsN" value="no" <c:if test="${memberDTO.receive_phone.equals('no') }"> <c:out value="checked" /> </c:if>> 
								<label class="form-check-label" for="mailN"> 동의 안함 </label>
						</div>
					</div>
				</div>
				<div class="row mb-3">
					<div class="col-form-label col-sm-5 pt-0 text-center">약관동의</div>
					<div class="col-sm-5">
						<div class="form-check form-check-inline">
							<input class="form-check-input" type="radio" name="termsYN"
								id="termsY" value="yes" <c:if test="${memberDTO.agreement.equals('yes') }"> <c:out value="checked" /> </c:if>>
								<label class="form-check-label" for="termsY"> 동의 </label>
						</div>
						<div class="form-check form-check-inline">
							<input class="form-check-input" type="radio" name="termsYN"
								id="termsN" value="no" <c:if test="${memberDTO.agreement.equals('no') }"> <c:out value="checked" /> </c:if>>
								<label class="form-check-label" for="termsN"> 동의 안함 </label>
						</div>
					</div>
				</div>

			</div>
		</div>
		<div class="text-center">
			<button type="button" onclick="goIndex()" 
			class="btn btn-primary">취소하기</button>
			<button type="submit" class="btn btn-primary">수정하기</button>
			<button type="button" onclick="withdrawal()" 
			class="btn btn-primary withdrawal">탈퇴하기</button>
		</div>

	</form>
	
	
	<script>
	
		function goIndex(){
			window.location.href="http://localhost:8080/index.jsp";
		}
	
	
		function withdrawal(){
	        const result = confirm("정말 탈퇴 하시겠습니까?");
	
	        if(result == true){
	        	const xhr = new XMLHttpRequest(); // XMLHttpRequest 객체 생성
	    		
	    		//const id = ;
	    		console.log(id);
	    		
	    		xhr.open('GET', 'WithdrawalMember.lo'); // HTTP 요청 초기화. 통신 방식과 url 설정.
	    		xhr.send(); // url에 요청을 보냄.
	    		
	    		// 이벤트 등록. XMLHttpRequest 객체의 readyState 프로퍼티 값이 변할때마다 자동으로 호출
	    		xhr.onreadystatechange = () => {
	    			// readyState 프로퍼티의 값이 DONE : 요청한 데이터의 처리가 완료되어 응답할 준비가 완료됨.
	    			if( xhr.readyState !== XMLHttpRequest.DONE) return;
	    		
	    			/* if(xhr.status === 200){ // 서버 (url)에 문서가 존재함
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
	    			} */
	    		}
	            alert("탈퇴되었습니다.");
	            window.location.href="http://localhost:8080/LoginPage.lo";
	        }
	        else{
	            alert("취소되었습니다.");
	        }
	    }
		
		
		
	</script>
	
	
</body>
</html>