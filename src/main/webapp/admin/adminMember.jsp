<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="Mark Otto, Jacob Thornton, and Bootstrap contributors">
    <meta name="generator" content="Hugo 0.84.0">
    <title>title</title>
    <script src="https://unpkg.com/feather-icons"></script>
    <link rel="canonical" href="https://getbootstrap.com/docs/5.0/examples/dashboard/">
    <link href="../resources/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .bd-placeholder-img {
            font-size: 1.125rem;
            text-anchor: middle;
            -webkit-user-select: none;
            -moz-user-select: none;
            user-select: none;
        }

        @media (min-width: 768px) {
            .bd-placeholder-img-lg {
                font-size: 3.5rem;
            }
        }
    </style>
    <!-- Custom styles for this template -->
    <link href="../resources/css/dashboard.css" rel="stylesheet">
</head>
<body>
<script>
    let items = "${items}";
    let text = "${text}";
</script>
<%@ include file="./adminHeader.jsp"%>
<div class="container-fluid">
    <div class="row">
        <%@ include file="./adminMenu.jsp"%>
        <main class="col-md-9 ms-sm-auto col-lg-10 px-md-4">
            <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
                <h1 class="h2" data-feather="file">회원 관리</h1>
            </div>
            <div class="container">
                <div class="row">
                    <div class="col-12" align="center">
                        <span>전체 ${total_record}건</span>
                    </div>
                </div>
                <table class="table table-hover table-striped text-center">
                    <thead>
                    <tr>
                        <th>회원 ID</th>
                        <th>이름</th>
                        <th>생일</th>
                        <th>성별</th>
                        <th>이메일</th>
                        <th>주소</th>
                        <th>전화번호</th>
                        <th>상태</th>
                    </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="item" items="${memberList}">
                        <tr>
                            <td>${item.id}</td>
                            <td>${item.name}</td>
                            <td>${item.birth}</td>
                            <td>${item.gender}</td>
                            <td>${item.mail}</td>
                            <td>${item.address}</td>
                            <td>${item.phone}</td>
                            <td>
                                <select id="a${item.id}">
                                    <option value="0">일반</option>
                                    <option value="1">이용 제한</option>
                                    <option value="2">탈퇴</option>
                                    <option value="3">관리자</option>
                                </select>
                            </td>
                        </tr>
                        <script>

                            const select${item.id} = document.querySelector("#a${item.id}")
                            select${item.id}.value = ${item.state}
                            select${item.id}.addEventListener("change", function () {
                                let state = this.options[this.selectedIndex].value;
                                if (confirm(this.id + "님의 상태를 " + this.options[this.selectedIndex].innerText + "(으)로 변경하시겠습니까?"))
                                    if (items == "" && text == "") {
                                        location.href = "../admin/updateState.ad?id=${item.id}&state=" + state + "&pageNum=${pageNum}";
                                    } else {
                                        location.href = "../admin/updateState.ad?id=${item.id}&state=" + state + "&items=${items}&text=${text}&pageNum=${pageNum}";
                                    }

                            })
                        </script>
                        </c:forEach>
                    </tbody>
                </table>
                <c:choose>
                    <c:when test="${items == null && text == null}">
                        <div class="col-sm-12" align="center">
                            <button onclick="location.href='AdminMemberList.ad?pageNum=1'">첫 페이지</button>
                            <c:if test="${block_this > 1}">
                                <button onclick="location.href='AdminMemberList.ad?pageNum=${block_this_first_page - 1}'">이전</button>
                            </c:if>
                            <c:forEach var="i" begin="${block_this_first_page}" end="${block_this_last_page}" step="1">
                                <button onclick="location.href='AdminMemberList.ad?pageNum=${i}'">${i}</button>
                            </c:forEach>
                            <c:if test="${block_this < block_total}">
                                <button onclick="location.href='AdminMemberList.ad?pageNum=${block_this_last_page + 1}'">다음</button>
                            </c:if>
                            <button onclick="location.href='AdminMemberList.ad?pageNum=${total_page}'">마지막 페이지</button>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="col-sm-12" align="center">
                            <button onclick="location.href='AdminMemberList.ad?pageNum=1&items=${items}&text=${text}'">첫 페이지</button>
                            <c:if test="${block_this > 1}">
                                <button onclick="location.href='AdminMemberList.ad?pageNum=${block_this_first_page - 1}&items=${items}&text=${text}'">이전</button>
                            </c:if>
                            <c:forEach var="i" begin="${block_this_first_page}" end="${block_this_last_page}" step="1">
                                <button onclick="location.href='AdminMemberList.ad?pageNum=${i}&items=${items}&text=${text}'">${i}</button>
                            </c:forEach>
                            <c:if test="${block_this < block_total}">
                                <button onclick="location.href='AdminMemberList.ad?pageNum=${block_this_last_page + 1}&items=${items}&text=${text}'">다음</button>
                            </c:if>
                            <button onclick="location.href='AdminMemberList.ad?pageNum=${total_page}&items=${items}&text=${text}'">마지막 페이지</button>
                        </div>
                    </c:otherwise>
                </c:choose>

            </div>
        </main >
    </div>
</div>
<%--<script src="../resources/js/bootstrap.bundle.min.js"></script>--%>
<%--<script src="https://cdn.jsdelivr.net/npm/feather-icons@4.28.0/dist/feather.min.js" integrity="sha384-uO3SXW5IuS1ZpFPKugNNWqTZRRglnUJK6UAZ/gxOX80nxEkN9NcGZTftn6RzhGWE" crossorigin="anonymous"></script><script src="https://cdn.jsdelivr.net/npm/chart.js@2.9.4/dist/Chart.min.js" integrity="sha384-zNy6FEbO50N+Cg5wap8IKA4M/ZnLJgzc6w2NqACZaK0u0FXfOWRRJOnQtpZun8ha" crossorigin="anonymous"></script><script src="../resources/js/dashboard.js"></script>--%>
<script>
    document.querySelector("#search").addEventListener("click", function () {
        let items = document.querySelector("#items").options[document.querySelector("#items").selectedIndex].value;
        let text = document.querySelector("#text").value;
        location.href = "../admin/AdminMemberList.ad?items=" + items + "&text=" + text;
    });
</script>
</body>
</html>
