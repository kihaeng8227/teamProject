<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<jsp:include page="/inc/header.jsp"/>
<div class="jumbotron">
	<div class="container">
		<h1 class="display-3">결제 정보</h1>
	</div>
</div>
<div class="container">
	<table class="table table-hover">
		<tr>
			<th>상품</th>
			<th>가격</th>
			<th>수량</th>
			<th>소계</th>
		</tr>
		<c:forEach var="data" items="${datas}">
		<tr>
			<td>${data.productId} - ${data.productName} </td>
			<td>${data.productPrice} </td>
			<td>${data.cnt} </td>
			<td>${data.sumPrice} </td>
		</tr>
		</c:forEach>
	</table>
		<div class="form-group row">
			<label class="col-sm-2">결제 단계</label>
			<div class="col-sm-3">
				${info.orderStep}
			</div>
		</div>
		<div class="form-group row">
			<label class="col-sm-2">결제 방법</label>
			<div class="col-sm-3">
				${info.payMethod}
			</div>
		</div>
		<div class="form-group row">
			<label class="col-sm-2">결제 금액</label>
			<div class="col-sm-3">
				${info.payAmount}
			</div>
		</div>
		<div class="form-group row">
			<label class="col-sm-2">주문자 이름</label>
			<div class="col-sm-3">
				${info.orderName}
			</div>
		</div>
		<div class="form-group row">
			<label class="col-sm-2">주문자 연락처</label>
			<div class="col-sm-3">
				${info.orderTel}
			</div>
		</div>
		<div class="form-group row">
			<label class="col-sm-2">주문자 이메일</label>
			<div class="col-sm-3">
				${info.orderEmail}
			</div>
		</div>
		<div class="form-group row">
			<label class="col-sm-2">받는 사람 이름</label>
			<div class="col-sm-3">
				${info.receiveName}
			</div>
		</div>
		<div class="form-group row">
			<label class="col-sm-2">받는 사람 연락처</label>
			<div class="col-sm-3">
				${info.receiveTel}
			</div>
		</div>
		<div class="form-group row">
			<label class="col-sm-2">받는 사람 주소</label>
			<div class="col-sm-3">
				${info.receiveAddress}
			</div>
		</div>
</body>
</html>