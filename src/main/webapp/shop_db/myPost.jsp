<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.teamProject.board.model.BoardDTO"%>

<%
String sessionId = (String) session.getAttribute("sessionId");
List boardlist = (List) request.getAttribute("boardlist");
int total_record = ((Integer) request.getAttribute("total_record")).intValue();
int pageNum = ((Integer) request.getAttribute("pageNum")).intValue();
int total_page = ((Integer) request.getAttribute("total_page")).intValue();
int limit = ((Integer) request.getAttribute("limit")).intValue();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
.list-group {
	max-width: 600px;
	margin: 4rem auto;
}
</style>
</head>
<body>
	<jsp:include page="../inc/header.jsp" />
<h2 class="text-center mt-5">내가 쓴 글</h2>
	<div class="list-group w-auto my-5">
		<!-- 목록 출력 -->
		<%
		for (int j = 0; j < boardlist.size(); j++) {
			BoardDTO list = (BoardDTO) boardlist.get(j);
		%>
		<a href="./BoardViewAction.do?num=<%=list.getNum()%>&pageNum=<%=pageNum%>"
			class="list-group-item list-group-item-action d-flex gap-3 py-3"
			aria-current="true">
			<div class="d-flex gap-2 w-100 justify-content-between">
				<div>
					<h6 class="mb-0"><%=list.getSubject()%></h6>
				</div>
				<small class="opacity-50 text-nowrap"><%=list.getRegist_day()%></small>
			</div>
		</a>
		<%
		}
		%>
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
							href="<c:url value="./orderList.my?pageNum=1"/>">첫 페이지</a></li>
						<c:if test="${thisBlock > 1}">
							<li class="page-item"><a class="page-link"
								href="<c:url value="./myPostList.my?pageNum=${firstPage -1}"/>">이전</a>
							</li>
						</c:if>
						<c:forEach var="i" begin="<%=firstPage%>" end="${lastPage}">
							<c:choose>
								<c:when test="${pageNum==i}">
									<a class="page-link active"
										href="<c:url value="myPostList.my?pageNum=${i}"/>"> ${i} </a>
								</c:when>
								<c:otherwise>
									<a class="page-link"
										href="<c:url value="./myPostList.my?pageNum=${i}"/>">${i}</a>
								</c:otherwise>
							</c:choose>
						</c:forEach>
						<c:if test="${thisBlock < totalBlock}">
							<li class="page-item"><a class="page-link"
								href="<c:url value="./myPostList.my?pageNum=${lastPage + 1}"/>">다음</a>
							</li>
						</c:if>
						<li class="page-item"><a class="page-link"
							href="<c:url value="./myPostList.my?pageNum=${total_page}"/>">끝
								페이지</a></li>
					</ul>
				</nav>
			</c:when>
		</c:choose>
	</c:forEach>
	<jsp:include page="../inc/footer.jsp" />
</body>
</html>