<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>

<link rel="stylesheet" href="../resources/css/bootstrap.min.css">
<link rel="stylesheet" href="../resources/css/header.css">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.3/font/bootstrap-icons.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="../resources/js/bootstrap.min.js"></script>

<%
String sessionId = (String) session.getAttribute("sessionId");
%>

<div class="container">
	<header
		class="d-flex flex-wrap align-items-center justify-content-center justify-content-lg-between py-3 mb-1 border-bottom">
		<a href="../index.jsp"
			class="d-flex align-items-center mb-2 mb-md-0 text-dark text-decoration-none">
			<img width="120" height="120" role="img"
			src="../resources/images/logo3.png"></img>
		</a>

		<ul class="nav my-2 justify-content-center my-md-0 text-small">
			<li><a href="../index.jsp" class="nav-link text-black"> Home
			</a></li>
			<li><a href="../shop_db/productsList.ad"
				class="nav-link text-muted"> 제품 </a></li>
			<li><a href="../board/BoardListAction.do?pageNum=1"
				class="nav-link text-muted"> 게시판 </a></li>
			<li><a href="../shop_db/cart.jsp" class="nav-link text-muted">
					장바구니 </a></li>
			<li class="nav-item dropdown"><a
				class="nav-link dropdown-toggle text-muted" href="#" role="button"
				data-bs-toggle="dropdown" aria-expanded="false"> 내정보 </a>
				<ul class="dropdown-menu">
					<li><a class="dropdown-item"
						href="../shop_db/jjimListAction.my">찜한 목록</a></li>
					<li><a class="dropdown-item" href="../shop_db/orderList.my">주문
							목록</a></li>
					<li><a class="dropdown-item" href="../shop_db/myPostList.my">내가
							쓴 글</a></li>
				</ul></li>
		</ul>


		<c:choose>
			<c:when test="${empty sessionId }">
				<div>
					<a class="btn btn-outline-primary"
						href="<c:url value="/LoginPage.lo" />">로그인</a> <a
						class="btn btn-primary" href="<c:url value="/AddMemberPage.lo" />">회원가입</a>
				</div>
			</c:when>
			<c:otherwise>
				<div>
					[<%=sessionId%>님] <a class="btn btn-outline-primary"
						href="<c:url value="/Logout.lo" />">로그아웃</a> <a
						class="btn btn-primary"
						href="<c:url value="/UpdateMemberPage.lo" />">회원 수정</a>
				</div>
			</c:otherwise>
		</c:choose>
	</header>

</div>
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4"
	crossorigin="anonymous"></script>