<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.teamProject.myPage.model.LikeDAO"%>
<%@ page import="com.teamProject.myPage.model.LikeDTO"%>

<%
String sessionId = (String) session.getAttribute("sessionId");
List boardList = (List) request.getAttribute("jjimlist");
int total_record = ((Integer) request.getAttribute("total_record")).intValue();
int pageNum = ((Integer) request.getAttribute("pageNum")).intValue();
int total_page = ((Integer) request.getAttribute("total_page")).intValue();
int limit = ((Integer) request.getAttribute("limit")).intValue();
%>
<!doctype html>

<html lang="ko">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>mypage</title>
<style>
.list-group {
	max-width: 400px;
	margin: 4rem auto;
}
</style>
<link href="../resources/css/bootstrap.min.css" rel="stylesheet">

</head>
<body>
	<jsp:include page="../inc/header.jsp" />
	<h2 class="text-center mt-5">찜한 목록</h2>

	<!-- 목록 출력 -->
	<%
	for (int j = 0; j < boardList.size(); j++) {
		LikeDTO list = (LikeDTO) boardList.get(j);
	%>
	<div class="list-group w-auto my-5">

		<a href="./product.jsp?productId=<%=list.getProductId()%>"
			class="list-group-item list-group-item-action d-flex gap-3 py-3"
			aria-current="true"> <img
			src="${pageContext.request.contextPath}/resources/images/<%=list.getFileName()%>"
			alt="twbs" width="80" height="80"
			class="rounded-circle flex-shrink-0">
			<div
				class="d-flex gap-2 w-100 justify-content-between align-items-center">
				<div>
					<h6 class="mb-0"><%=list.getProductName()%></h6>
				</div>
				<small class="opacity-50 text-nowrap">[<%=list.getProductId()%>]</small>
			</div></a>
			<a href="./deletejjim.my?productId=<%=list.getProductId()%>"
			class="btn btn-light">삭제</a>
	</div>



	<%
	}
	%>
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
							href="<c:url value="./jjimListAction.my?pageNum=1"/>">첫 페이지</a></li>
						<c:if test="${thisBlock > 1}">
							<li class="page-item"><a class="page-link"
								href="<c:url value="./jjimListAction.my?pageNum=${firstPage -1}"/>">이전</a>
							</li>
						</c:if>
						<c:forEach var="i" begin="<%=firstPage%>" end="${lastPage}">
							<c:choose>
								<c:when test="${pageNum==i}">
									<a class="page-link active"
										href="<c:url value="jjimListAction.my?pageNum=${i}"/>">
										${i} </a>
								</c:when>
								<c:otherwise>
									<a class="page-link"
										href="<c:url value="./jjimListAction.my?pageNum=${i}"/>">${i}</a>
								</c:otherwise>
							</c:choose>
						</c:forEach>
						<c:if test="${thisBlock < totalBlock}">
							<li class="page-item"><a class="page-link"
								href="<c:url value="./jjimListAction.my?pageNum=${lastPage + 1}"/>">다음</a>
							</li>
						</c:if>
						<li class="page-item"><a class="page-link"
							href="<c:url value="./jjimListAction.my?pageNum=${total_page}"/>">끝
								페이지</a></li>
					</ul>
				</nav>
			</c:when>
		</c:choose>
	</c:forEach>


	<jsp:include page="../inc/footer.jsp" />
</body>
</html>
