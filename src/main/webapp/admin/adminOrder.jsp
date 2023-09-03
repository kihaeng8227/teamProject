<%@ page contentType="text/html; charset=UTF-8" %>
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
                <h1 class="h2">주문 관리</h1>
            </div>

            <c:forEach var="datas" items="${orderMergeDTOList}">
            <div class="container">
                <div class="row">
                    <div class="col-6" align="left">
                        <h3>${datas.orderInfoDTO.memberId}님의 주문 목록</h3>
                    </div>
                    <div class="col-6" align="right">
                        <a href="shippingDone.ad?orderNum=${datas.orderInfoDTO.orderNum}" class="btn btn-success" role="button">발송 완료</a>
                    </div>
                </div>
                <table class="table table-hover">
                    <tr>
                        <th>상품 ID</th>
                        <th>상품 명</th>
                        <th>가격</th>
                        <th>수량</th>
                        <th>소계</th>
                    </tr>

                    <c:forEach var="data" items="${datas.list}">
                        <tr>
                            <td>${data.productId}</td>
                            <td>${data.productName}</td>
                            <td>${data.productPrice}</td>
                            <td>${data.cnt}</td>
                            <td>${data.sumPrice}</td>
                        </tr>
                    </c:forEach>

                </table>

                <div class="form-group row">
                    <label class="col-sm-2">주문 번호</label>
                    <div class="col-sm-3">
                            ${datas.orderInfoDTO.orderNum}
                    </div>
                </div>
                <div class="form-group row">
                    <label class="col-sm-2">주문 일자</label>
                    <div class="col-sm-3">
                            ${datas.orderInfoDTO.datePay}
                    </div>
                </div>
                <div class="form-group row">
                    <label class="col-sm-2">결제 단계</label>
                    <div class="col-sm-3">
                        ${datas.orderInfoDTO.orderStep}
                    </div>
                </div>
                <div class="form-group row">
                    <label class="col-sm-2">결제 방법</label>
                    <div class="col-sm-3">
                        ${datas.orderInfoDTO.payMethod}
                    </div>

                </div>
                <div class="form-group row">
                    <label class="col-sm-2">결제 금액</label>
                    <div class="col-sm-3">
                        ${datas.orderInfoDTO.payAmount}
                    </div>
                </div>
                <div class="form-group row">
                    <label class="col-sm-2">주문자 이름</label>
                    <div class="col-sm-3">
                        ${datas.orderInfoDTO.orderName}
                    </div>
                </div>
                <div class="form-group row">
                    <label class="col-sm-2">주문자 연락처</label>
                    <div class="col-sm-3">
                        ${datas.orderInfoDTO.orderTel}
                    </div>
                </div>
                <div class="form-group row">
                    <label class="col-sm-2">주문자 이메일</label>
                    <div class="col-sm-3">
                        ${datas.orderInfoDTO.orderEmail}
                    </div>
                </div>

                <div class="form-group row">
                    <label class="col-sm-2">받는 사람 이름</label>
                    <div class="col-sm-3">
                        ${datas.orderInfoDTO.receiveName}
                    </div>
                </div>

                <div class="form-group row">
                    <label class="col-sm-2">받는 사람 연락처</label>
                    <div class="col-sm-3">
                        ${datas.orderInfoDTO.receiveTel}
                    </div>
                </div>

                <div class="form-group row">
                    <label class="col-sm-2">받는 사람 주소</label>
                    <div class="col-sm-3">
                        ${datas.orderInfoDTO.receiveAddress}
                    </div>
                </div>
                <br><br><br><br><br><br><hr>
                </c:forEach>

            </div>
        </main >

    </div>
</div>
<%--<script src="../resources/js/bootstrap.bundle.min.js"></script>--%>
<%--<script src="https://cdn.jsdelivr.net/npm/feather-icons@4.28.0/dist/feather.min.js" integrity="sha384-uO3SXW5IuS1ZpFPKugNNWqTZRRglnUJK6UAZ/gxOX80nxEkN9NcGZTftn6RzhGWE" crossorigin="anonymous"></script><script src="https://cdn.jsdelivr.net/npm/chart.js@2.9.4/dist/Chart.min.js" integrity="sha384-zNy6FEbO50N+Cg5wap8IKA4M/ZnLJgzc6w2NqACZaK0u0FXfOWRRJOnQtpZun8ha" crossorigin="anonymous"></script><script src="../resources/js/dashboard.js"></script>--%>
</body>
</html>
