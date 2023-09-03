<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<title>로그인 실패</title>
<link rel="stylesheet" href="../resources/css/bootstrap.min.css" />
</head>
<body>
	<jsp:include page="../inc/header.jsp"/>
	<div class="jumborton">
	  <div class="container">
	    <h1 class="display-3">로그인 실패</h1>
	  </div>
	</div>
	<div class="container div2" align="center">

		<script>
			let div2 = document.querySelector('.div2');
			let result = "${result}";
			
			if(result != ""){
				
				if(result == "1"){
					let h2 = document.createElement('h2');
					h2.classList.add('alert');
					h2.classList.add('alert-danger');
					
					let text = document.createTextNode('이용이 제한된 고객입니다.');
					
					h2.appendChild(text);
					div2.appendChild(h2);
				}
				else if(result == "2"){
					let h2 = document.createElement('h2');
					h2.classList.add('alert');
					h2.classList.add('alert-danger');
					
					let text = document.createTextNode('탈퇴한 회원입니다.');
					
					h2.appendChild(text);
					div2.appendChild(h2);
				}
			
			}
			else{
				let h2 = document.createElement('h2');
				h2.classList.add('alert');
				h2.classList.add('alert-danger');
				
				let text = document.createTextNode('해당 작업에 실패하였습니다.');
				
				h2.appendChild(text);
				div2.appendChild(h2);
			}
			
		</script>
	</div>
	<%-- <jsp:include page="../inc/footer.jsp" /> --%>
</body>
</html>