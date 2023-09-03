<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="../resources/css/bootstrap.min.css" />
<meta charset="UTF-8">
	<title>Login</title>
</head>
<body>
	<jsp:include page="../inc/header.jsp" />
	<div class="jumbotron">
		<div class="container">
			<h1 class="display-3">로그인</h1>
		</div>
	</div>
	<div class="container" align="center">
		<div class="col-md-4 col-md-offset-4 div2">
			<h3 class="form-signin-heading">Please sign in</h3>
			<%-- <%
				String error = request.getParameter("error");
				if (error != null) {
					out.println("<div class='alert alert-danger'>");
					out.println("아이디와 비밀번호를 확인해 주세요");
					out.println("</div>");
				}
			%> --%>
			
			<script>
				let div2 = document.querySelector('.div2');
				let error = "${error}";
				
				if(error != ""){
					let div = document.createElement('div');
					div.classList.add('alert');
					div.classList.add('alert-danger');
					
					let text = document.createTextNode('아이디와 비밀번호를 확인해 주세요.');
					
					div.appendChild(text);
					
					div2.appendChild(div);
					
				}
			</script>
			
			<form class="form-signin" action="/Login.lo" method="post">
				<div class="form-group">
					<label for="inputUserName" class="sr-only">User Name</label> 
					<input type="text" class="form-control" placeholder="ID" name="id" required autofocus>
				</div>
				<div class="form-group">
					<label for="inputPassword" class="sr-only">Password</label> 
					<input type="password" class="form-control" placeholder="Password" name="password" required>
				</div>
				<button class="btn btn btn-lg btn-success btn-block" type="submit">로그인</button>
			</form>
		</div>
	</div>
</body>
</html>