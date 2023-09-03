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
    <script src="../resources/js/chkProductForm.js" defer></script>
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
                <h1 class="h2">제품 관리</h1>
            </div>
            <div class="container">
                <div class="row">
                    <div class="col-11">
                        <h3>제품 등록</h3>
                    </div>
                    <div class="col-1">
                        <a href="AdminProductsList.ad" class="btn btn-primary" role="button">등록 취소</a>
                    </div>
                </div>
                <br>
                <form name="newProduct" action="./AddProduct.ad" class="form-horizontal" method="post"
                      enctype="multipart/form-data" >
                    <div class="form-group row">
                        <label class="col-sm-2">제품 ID</label>
                        <div class="col-sm-3">
                            <input type="text" name="productId" class="form-control" id="productId">
                            <span class="idCheck"></span>
                        </div>
                    </div>
                    <br>
                    <div class="form-group row">
                        <label class="col-sm-2">제품명</label>
                        <div class="col-sm-3">
                            <input type="text" name="productName" class="form-control">
                        </div>
                    </div>
                    <br>
                    <div class="form-group row">
                        <label class="col-sm-2">가격</label>
                        <div class="col-sm-3">
                            <input type="text" name="productPrice" class="form-control">
                        </div>
                    </div>
                    <br>
                    <div class="form-group row">
                        <label class="col-sm-2">설명</label>
                        <div class="col-sm-5">
                            <textarea name="description" cols="50" rows="2" class="form-control"></textarea>
                        </div>
                    </div>
                    <br>
                    <div class="form-group row">
                        <label class="col-sm-2">카테고리</label>
                        <div class="col-sm-3">
                            <input type="text" name="category" class="form-control">
                        </div>
                    </div>
                    <br>
                    <div class="form-group row">
                        <label class="col-sm-2">재고 수</label>
                        <div class="col-sm-3">
                            <input type="text" name="productsInStock" class="form-control">
                        </div>
                    </div>
                    <br>
                    <div class="form-group row">
                        <label class="col-sm-2">이미지</label>
                        <div class="col-sm-5">
                            <input type="file" name="productImage" class="form-control">
                        </div>
                    </div>
                    <br>
                    <div class="form-group row">
                        <div class="col-sm-offset-2 col-sm-10 ">
                            <input type="submit" class="btn btn-primary" value="등록">
                        </div>
                    </div>
                </form>
            </div>

        </main >

    </div>
</div>
<%--<script src="../resources/js/bootstrap.bundle.min.js"></script>--%>
<%--<script src="https://cdn.jsdelivr.net/npm/feather-icons@4.28.0/dist/feather.min.js" integrity="sha384-uO3SXW5IuS1ZpFPKugNNWqTZRRglnUJK6UAZ/gxOX80nxEkN9NcGZTftn6RzhGWE" crossorigin="anonymous"></script><script src="https://cdn.jsdelivr.net/npm/chart.js@2.9.4/dist/Chart.min.js" integrity="sha384-zNy6FEbO50N+Cg5wap8IKA4M/ZnLJgzc6w2NqACZaK0u0FXfOWRRJOnQtpZun8ha" crossorigin="anonymous"></script><script src="../resources/js/dashboard.js"></script>--%>
</body>
</html>
