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
<%@ include file="./adminHeader.jsp"%>
<div class="container-fluid">
    <div class="row">
        <%@ include file="./adminMenu.jsp"%>
        <main class="col-md-9 ms-sm-auto col-lg-10 px-md-4">
            <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
                <h1 class="h2" data-feather="file">제품 관리</h1>
            </div>
            <div class="container">
                <div class="row">
                    <div class="col-9">
                        <h3>제품 목록</h3>
                    </div>
                    <div class="col-1" align="right">
                        <span>전체 ${total_record}건</span>
                    </div>
                    <div class="col-2" align="right">
                        <a href="addProduct.jsp" class="btn btn-primary" role="button">등록하기</a>
                    </div>
                </div>
                <table class="table table-hover table-striped text-center">
                    <thead>
                    <tr>
                        <th>제품 ID</th>
                        <th>제품명</th>
                        <th>가격</th>
                        <th>설명</th>
                        <th>카테고리</th>
                        <th>재고수</th>
                        <th>수정</th>
                        <th>삭제</th>
                    </tr>
                    </thead>
                    <tbody>
                       <c:forEach var="item" items="${productList}">
                           <tr>
                               <td>${item.productId}</td>
                               <td>${item.productName}</td>
                               <td>${item.productPrice}</td>
                               <td>${item.description}</td>
                               <td>${item.category}</td>
                               <td>${item.productsInStock}</td>
                               <c:choose>
                                   <c:when test="${items == null && text == null}">
                                       <td><a href="editProduct.ad?productId=${item.productId}&pageNum=${pageNum}" class="btn btn-success" role="button">수정하기</a></td>
                                       <td><a href="deleteProduct.ad?productId=${item.productId}&pageNum=${pageNum}" class="btn btn-danger" role="button">삭제하기</a></td>
                                   </c:when>
                                   <c:otherwise>
                                       <td><a href="editProduct.ad?productId=${item.productId}&pageNum=${pageNum}&items=${items}&text=${text}" class="btn btn-success" role="button">수정하기</a></td>
                                       <td><a href="deleteProduct.ad?productId=${item.productId}&pageNum=${pageNum}&items=${items}&text=${text}" class="btn btn-danger" role="button">삭제하기</a></td>
                                   </c:otherwise>
                               </c:choose>

                           </tr>
                       </c:forEach>
                    </tbody>
                </table>
                <c:choose>
                    <c:when test="${items == null && text == null}">
                        <div class="col-sm-12" align="center">
                            <button onclick="location.href='AdminProductsList.ad?pageNum=1'">첫 페이지</button>
                            <c:if test="${block_this > 1}">
                                <button onclick="location.href='AdminProductsList.ad?pageNum=${block_this_first_page - 1}'">이전</button>
                            </c:if>
                            <c:forEach var="i" begin="${block_this_first_page}" end="${block_this_last_page}" step="1">
                                <button onclick="location.href='AdminProductsList.ad?pageNum=${i}'">${i}</button>
                            </c:forEach>
                            <c:if test="${block_this < block_total}">
                                <button onclick="location.href='AdminProductsList.ad?pageNum=${block_this_last_page + 1}'">다음</button>
                            </c:if>
                            <button onclick="location.href='AdminProductsList.ad?pageNum=${total_page}'">마지막 페이지</button>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="col-sm-12" align="center">
                            <button onclick="location.href='AdminProductsList.ad?pageNum=1&items=${items}&text=${text}'">첫 페이지</button>
                            <c:if test="${block_this > 1}">
                                <button onclick="location.href='AdminProductsList.ad?pageNum=${block_this_first_page - 1}&items=${items}&text=${text}'">이전</button>
                            </c:if>
                            <c:forEach var="i" begin="${block_this_first_page}" end="${block_this_last_page}" step="1">
                                <button onclick="location.href='AdminProductsList.ad?pageNum=${i}&items=${items}&text=${text}'">${i}</button>
                            </c:forEach>
                            <c:if test="${block_this < block_total}">
                                <button onclick="location.href='AdminProductsList.ad?pageNum=${block_this_last_page + 1}&items=${items}&text=${text}'">다음</button>
                            </c:if>
                            <button onclick="location.href='AdminProductsList.ad?pageNum=${total_page}&items=${items}&text=${text}'">마지막 페이지</button>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>

        </main >

    </div>
</div>
<br>
<br>
<br>
<%--<script src="../resources/js/bootstrap.bundle.min.js"></script>--%>
<%--<script src="https://cdn.jsdelivr.net/npm/feather-icons@4.28.0/dist/feather.min.js" integrity="sha384-uO3SXW5IuS1ZpFPKugNNWqTZRRglnUJK6UAZ/gxOX80nxEkN9NcGZTftn6RzhGWE" crossorigin="anonymous"></script><script src="https://cdn.jsdelivr.net/npm/chart.js@2.9.4/dist/Chart.min.js" integrity="sha384-zNy6FEbO50N+Cg5wap8IKA4M/ZnLJgzc6w2NqACZaK0u0FXfOWRRJOnQtpZun8ha" crossorigin="anonymous"></script><script src="../resources/js/dashboard.js"></script>--%>

<script>
    document.querySelector("#search").addEventListener("click", function () {
        let items = document.querySelector("#items").options[document.querySelector("#items").selectedIndex].value;
        let text = document.querySelector("#text").value;
        location.href = "../admin/AdminProductsList.ad?items=" + items + "&text=" + text;
    });
</script>
</body>
</html>
