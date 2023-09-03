<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.*" %>
<%@ page import="com.teamProject.board.model.BoardDAO" %>
<%@ page import="com.teamProject.board.model.BoardDTO" %>
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
    <link rel="stylesheet" href="../resources/css/bootstrap.min.css">
    <link rel="stylesheet" href="../resources/css/common.css">
    <meta charset="UTF-8">
    <title>Board</title>
    <script type="text/javascript">
        function checkForm() {
            if (

                ${sessionId==null}) {
                alert("로그인 해주세요.");
                location.href = "../member/loginMember.jsp"
            } else {
                location.href = "./BoardWriteForm.do?id=<%=sessionId%>"
            }


        }
    </script>
</head>
<body>
<jsp:include page="../inc/header.jsp"/>
<div class="container mt-5 mb-5">
    <form action="<c:url value="./BoardListAction.do"/>" method="post">
        <div>

            <div class="text-end">
					<span class="badge bg-primary">전체<%=total_record%>건
					</span>
            </div>
        </div>
        <table class="table table table-hover">
            <thead>
            <tr>
                <th>번호</th>
                <th>제목</th>
                <th>작성일</th>
                <th>조회</th>
                <th>글쓴이</th>
            </tr>
            </thead>
            <tbody>
            <%
                //목록에 노출되는 게시물 번호를 전체 게시물의 수 기준으로.
                int serialNumber = total_record - ((pageNum - 1) * limit); //게시물일련번호

                for (int j = 0; j < boardList.size(); j++) {
                    BoardDTO notice = (BoardDTO) boardList.get(j);
            %>
            <tr>
                <td><%=serialNumber%>
                </td>
                <td><a
                        href="./BoardViewAction.do?num=<%=notice.getNum()%>&pageNum=<%=pageNum%>"><%=notice.getSubject()%>
                </a></td>
                <td><%=notice.getRegist_day()%>
                </td>
                <td><%=notice.getHit()%>
                </td>
                <td><%=notice.getName()%>
                </td>
            </tr>
            <%
                    serialNumber--;
                }
            %>
            </tbody>
        </table>

        <div id="srcbar">
            <%
                int pagePerBlock = 5; // 페이지 출력시 나올 범위
                int totalBlock = total_page % pagePerBlock == 0 ? total_page / pagePerBlock : total_page / pagePerBlock + 1;//전체 블럭수
                int thisBlock = ((pageNum - 1) / pagePerBlock) + 1; //현재 블럭
                int firstPage = ((thisBlock - 1) * pagePerBlock) + 1; //블럭의 첫 페이지
                int lastPage = thisBlock * pagePerBlock; //블럭의 마지막 페이지
                lastPage = (lastPage > total_page) ? total_page : lastPage;
            %>
            <c:set var="pagePerBlock" value="<%=pagePerBlock%>"/>
            <c:set var="totalBlock" value="<%=totalBlock%>"/>
            <c:set var="thisBlock" value="<%=thisBlock%>"/>
            <c:set var="firstPage" value="<%=firstPage%>"/>
            <c:set var="lastPage" value="<%=lastPage%>"/>

            <c:set var="pageNum" value="<%=pageNum%>"/>
            <%--para 검색 후 해당게시물만 표시 --%>
            <c:choose>
                <c:when test="${empty text}">
                    <c:set var="para" value=""/>
                </c:when>
                <c:otherwise>
                    <c:set var="para" value="&items=${items}&text=${text}"/>
                </c:otherwise>
            </c:choose>
            <%--            -------%>
            <c:forEach var="i" begin="<%=firstPage%>" end="${lastPage}">

                <c:choose>
                    <c:when test="${pageNum==i}">
                        <nav aria-label="Page navigation example">
                            <ul class="pagination pagination-sm">
                                <li class="page-item"><a class="page-link"
                                                         href="<c:url value="./BoardListAction.do?pageNum=1${para}"/>">첫
                                    페이지</a></li>
                                <c:if test="${thisBlock > 1}">
                                    <li class="page-item">
                                        <a class="page-link"
                                           href="<c:url value="./BoardListAction.do?pageNum=${firstPage -1}${para}"/>">이전</a>
                                    </li>
                                </c:if>
                                <c:forEach var="i" begin="<%=firstPage%>" end="${lastPage}">
                                    <c:choose>
                                        <c:when test="${pageNum==i}">
                                            <a class="page-link active"
                                               href="<c:url value="BoardListAction.do?pageNum=${i}${para}"/>">
                                                    ${i} </a>
                                        </c:when>
                                        <c:otherwise>
                                            <a class="page-link"
                                               href="<c:url value="./BoardListAction.do?pageNum=${i}${para}"/>">${i}</a>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                                <c:if test="${thisBlock < totalBlock}">
                                    <li class="page-item">
                                        <a class="page-link"
                                           href="<c:url value="./BoardListAction.do?pageNum=${lastPage + 1}${para}"/>">다음</a>
                                    </li>
                                </c:if>
                                <li class="page-item"><a class="page-link"
                                                         href="<c:url value="./BoardListAction.do?pageNum=${total_page}${para}"/>">끝
                                    페이지</a></li>
                            </ul>
                        </nav>
                    </c:when>
                </c:choose>
            </c:forEach>

            <div id="abc">
                <select name="items">
                    <option value="subject">제목에서</option>
                    <option value="content">본문에서</option>
                    <option value="name">글쓴이에서</option>

                    <input class="" name="text" type="text" placeholder="검색어입력"/>
                    <%--                <input class="form-control me-2" type="search" placeholder="Search" aria-label="Search">--%>
                    <input type="submit"
                           class="btn btn-primary "
                           value="검색 "/>
                    <a href="#"
                       onclick="checkForm(); return false;" class="btn btn-primary">글쓰기</a>
            </div>

        </div>
    </form>
</div>
<%@ include file="../inc/footer.jsp" %>
</body>
</html>