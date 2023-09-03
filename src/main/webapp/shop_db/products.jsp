<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.teamProject.admin.model.ProductsDAO"%>
<%@ page import="com.teamProject.admin.model.ProductsDTO"%>
<%
String sessionId = (String) session.getAttribute("sessionId");
List boardList = (List) request.getAttribute("boardlist");
int total_record = ((Integer) request.getAttribute("total_record")).intValue();
int pageNum = ((Integer) request.getAttribute("pageNum")).intValue();
int total_page = ((Integer) request.getAttribute("total_page")).intValue();
int limit = ((Integer) request.getAttribute("limit")).intValue();
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>제품 목록</title>
<link rel="stylesheet" href="../resources/css/bootstrap.min.css">

<style>
.bd-placeholder-img {
	font-size: 1.125rem;
	text-anchor: middle;
	-webkit-user-select: none;
	-moz-user-select: none;
	user-select: none;
}

@media ( min-width : 768px) {
	.bd-placeholder-img-lg {
		font-size: 3.5rem;
	}
}

.b-example-divider {
	height: 3rem;
	background-color: rgba(0, 0, 0, .1);
	border: solid rgba(0, 0, 0, .15);
	border-width: 1px 0;
	box-shadow: inset 0 .5em 1.5em rgba(0, 0, 0, .1), inset 0 .125em .5em
		rgba(0, 0, 0, .15);
}

.b-example-vr {
	flex-shrink: 0;
	width: 1.5rem;
	height: 100vh;
}

.bi {
	vertical-align: -.125em;
	fill: currentColor;
}

.nav-scroller {
	position: relative;
	z-index: 2;
	height: 2.75rem;
	overflow-y: hidden;
}

.nav-scroller .nav {
	display: flex;
	flex-wrap: nowrap;
	padding-bottom: 1rem;
	margin-top: -1px;
	overflow-x: auto;
	text-align: center;
	white-space: nowrap;
	-webkit-overflow-scrolling: touch;
}
</style>

</head>
<body>
	<main>
		<jsp:include page="../inc/header.jsp" />


		<div class="album py-5 my-5">

			<h2 class="text-center">- 제품 목록 -</h2>

			<div class="container">

				<div class="row row-cols-1 row-cols-sm-2 row-cols-md-3">
					<%-- 제품 목록 출력 --%>
					<%
					//목록에 노출되는 게시물 번호를 전체 게시물의 수 기준으로.
					int serialNumber = total_record - ((pageNum - 1) * limit); //게시물일련번호

					for (int j = 0; j < boardList.size(); j++) {
						ProductsDTO rs = (ProductsDTO) boardList.get(j);
					%>
					<div class="col my-5">
						<div class="card shadow-sm" style="height: 550px;">
							<img
								src="${pageContext.request.contextPath}/resources/images/<%=rs.getFileName()%>"
								style="height: 300px; width: 100%" alt="">


							<div class="card-body">
								<h5 class="fw-bold"><%=rs.getProductName()%></h5>
								<p class="card-text"><%=rs.getDescription()%></p>
							</div>
							<div
								class="card-footer d-flex justify-content-between align-items-end" style="border: 0">
								<div class="btn-group">
									<a href="./product.jsp?productId=<%=rs.getProductId()%>&pageNum=<%=pageNum %>"
										class="btn btn-sm btn-outline-secondary" role="button">
										View &raquo;</a>
								</div>
								<small class="text-muted"><%=rs.getRegist_day()%></small>
							</div>
						</div>
					</div>
					<%
					serialNumber--;
					}
					%>

				</div>

			</div>

			<%
			int pagePerBlock = 5; // 페이지 출력시 나올 범위
			int totalBlock = total_page % pagePerBlock == 0 ? total_page / pagePerBlock : total_page / pagePerBlock + 1;//전체 블럭수
			int thisBlock = ((pageNum - 1) / pagePerBlock) + 1; //현재 블럭
			int firstPage = ((thisBlock - 1) * pagePerBlock) + 1; //블럭의 첫 페이지
			int lastPage = thisBlock * pagePerBlock; //블럭의 마지막 페이지
			lastPage = (lastPage > total_page) ? total_page : lastPage;
			%>
			<c:set var="pagePerBlock" value="<%=pagePerBlock%>" />
			<c:set var="totalBlock" value="<%=totalBlock%>" />
			<c:set var="thisBlock" value="<%=thisBlock%>" />
			<c:set var="firstPage" value="<%=firstPage%>" />
			<c:set var="lastPage" value="<%=lastPage%>" />

			<c:set var="pageNum" value="<%=pageNum%>" />


			<c:forEach var="i" begin="<%=firstPage%>" end="${lastPage}">

				<c:choose>
					<c:when test="${pageNum==i}">
						<nav aria-label="Page navigation example">
							<ul class="pagination pagination-sm justify-content-center">
								<li class="page-item"><a class="page-link"
									href="<c:url value="./productsList.ad?pageNum=1"/>">첫 페이지</a></li>
								<c:if test="${thisBlock > 1}">
									<li class="page-item"><a class="page-link"
										href="<c:url value="./productsList.ad?pageNum=${firstPage -1}"/>">이전</a>
									</li>
								</c:if>
								<c:forEach var="i" begin="<%=firstPage%>" end="${lastPage}">
									<c:choose>
										<c:when test="${pageNum==i}">
											<a class="page-link active"
												href="<c:url value="productsList.ad?pageNum=${i}"/>">
												${i} </a>
										</c:when>
										<c:otherwise>
											<a class="page-link"
												href="<c:url value="./productsList.ad?pageNum=${i}"/>">${i}</a>
										</c:otherwise>
									</c:choose>
								</c:forEach>
								<c:if test="${thisBlock < totalBlock}">
									<li class="page-item"><a class="page-link"
										href="<c:url value="./productsList.ad?pageNum=${lastPage + 1}"/>">다음</a>
									</li>
								</c:if>
								<li class="page-item"><a class="page-link"
									href="<c:url value="./productsList.ad?pageNum=${total_page}"/>">끝
										페이지</a></li>
							</ul>
						</nav>
					</c:when>
				</c:choose>
			</c:forEach>
		</div>


	</main>
	<jsp:include page="../inc/footer.jsp" />
</body>
</html>