<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet">
<title>결제 정보</title>
</head>
<body>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
<jsp:include page="/inc/header.jsp"/>
 
<div class="jumbotron">
	<div class="container">
		<h1 class="display-3">결제 정보</h1>
	</div>
</div>
<div class="container">
	<section>
	<div class="p-3 mb-2 bg-secondary text-white"> 
		<h1> 주문 상품 : ${orderProductName}</h1> 
		<h3> 결제 금액 : ${totalPrice}원</h3> 
	</div>
	<div class="accordion" id="accordionPanelsStayOpenExample">
	  <div class="accordion-item">
	    <h2 class="accordion-header" id="panelsStayOpen-headingOne">
	      <button class="accordion-button" type="button" data-bs-toggle="collapse" data-bs-target="#panelsStayOpen-collapseOne" aria-expanded="true" aria-controls="panelsStayOpen-collapseOne">
	        주문자
	      </button>
	    </h2>
	    <div id="panelsStayOpen-collapseOne" class="accordion-collapse collapse show" aria-labelledby="panelsStayOpen-headingOne">
	      <div class="accordion-body">
	        <p><strong>이름 : </strong> ${info.orderName} </p>
	        <p><strong>연락처 : </strong> ${info.orderTel} </p>
	      </div>
	    </div>
	  </div>
	  <div class="accordion-item">
	    <h2 class="accordion-header" id="panelsStayOpen-headingTwo">
	      <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#panelsStayOpen-collapseTwo" aria-expanded="true" aria-controls="panelsStayOpen-collapseTwo">
	        받는 사람
	      </button>
	    </h2>
	    <div id="panelsStayOpen-collapseTwo" class="accordion-collapse collapse show"  aria-labelledby="panelsStayOpen-headingTwo">
	      <div class="accordion-body">
	        <p><strong>이름 : </strong> ${info.receiveName} </p>
	        <p><strong>연락처 : </strong> ${info.receiveTel} </p>
	        <p><strong>주소 : </strong> ${info.receiveAddress} </p>   
	      </div>
	    </div>
	  </div>
	</div>
	<hr>
	<div class="card">
	  <h5 class="card-header">결제 방법</h5>
	  <div class="card-body">
	    <div class="form-check"><label class="form-check-label"><input class="form-check-input" type="radio" name="method" value="카드" checked/>신용카드</label></div>
		<div class="form-check"><label class="form-check-label"><input class="form-check-input" type="radio" name="method" value="가상계좌"/>가상계좌</label></div>
	    <button class="btn btn-primary btn-lg" id="payment-button">결제하기</button>
	  </div>
	</div>
	</section>
	<script src="https://js.tosspayments.com/v1"></script>
    <script>
        var tossPayments = TossPayments("test_ck_D5GePWvyJnrK0W0k6q8gLzN97Eoq");
        var button = document.getElementById("payment-button");

        button.addEventListener("click", function () {
            var method = document.querySelector('input[name=method]:checked').value; // "카드" 혹은 "가상계좌"

            var paymentData = {
                amount: ${totalPrice},
                orderId: '${info.orderNum}',
                orderName: '${orderProductName}',
                customerName: '${info.orderName}',
                successUrl: window.location.origin + "/order/success.co", // 성공시 리턴될  주소
                failUrl: window.location.origin + "/order/fail.co",  // 실패시 리턴될 주소
            };

            if (method === '가상계좌') {
                paymentData.virtualAccountCallbackUrl = window.location.origin + '/order/virtualAccountCallback.co'
            }

            tossPayments.requestPayment(method, paymentData);
        });
    </script>
</body>
</html>