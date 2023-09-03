<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<title>회원 정보</title>
<link rel="stylesheet" href="../resources/css/bootstrap.min.css" />
</head>
<body>
	<jsp:include page="../inc/header.jsp"/>
	<div class="jumborton">
	  <div class="container">
	    <h1 class="display-3">회원정보</h1>
	  </div>
	</div>
	<div class="container div2" align="center">
		<%--
			String msg = request.getParameter("msg");
		
			if(msg != null){
				if(msg.equals("0"))
					out.println("<h2 class='alert alert-danger'>회원정보가 수정되었습니다.</h2>");
				else if(msg.equals("1"))
					out.println("<h2 class='alert alert-danger'>회원가입을 축하드립니다.</h2>");
				else if(msg.equals("2")){
					String loginId = (String) session.getAttribute("sessionId");
					out.println("<h2 class='alert alert-danger'>" + loginId + "님 환영합니다.</h2>");
				}
			}
			else{
				out.println("<h2 class='alert alert-danger'>회원정보가 삭제되었습니다.</h2>");
			}
		--%>
		
		<script>
			let div2 = document.querySelector('.div2');
			let msg = "${msg}";
			
			if(msg != ""){
				
				if(msg == "0"){
					let h2 = document.createElement('h2');
					h2.classList.add('alert');
					h2.classList.add('alert-danger');
					
					let text = document.createTextNode('회원정보가 수정되었습니다.');
					
					h2.appendChild(text);
					div2.appendChild(h2);
				}
				else if(msg == "1"){
					let h2 = document.createElement('h2');
					h2.classList.add('alert');
					h2.classList.add('alert-danger');
					
					let text = document.createTextNode('회원가입을 축하드립니다.');
					
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