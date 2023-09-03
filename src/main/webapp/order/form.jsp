<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>배송 정보</title>
</head>
<body>
<jsp:include page="/inc/header.jsp"/>
<div class="jumbotron">
	<div class="container">
		<h1 class="display-3">배송 정보</h1>
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
		<tr>
			<th></th>
			<th></th>
			<th>총액</th>
			<th>${totalPrice}</th>
			<th></th>
		</tr>
	</table>
	<form action="./pay.co" class="form-horizontal" method="post">
		<div class="form-group row">
			<label class="col-sm-2">주문자 이름</label>
			<div class="col-sm-3">
				<input name="orderName" value="이름" type="text" class="form-control"/>
			</div>
		</div>
		<div class="form-group row">
			<label class="col-sm-2">주문자 연락처</label>
			<div class="col-sm-3">
				<input name="orderTel" value="연락처" type="text" class="form-control"/>
			</div>
		</div>
		<div class="form-group row">
			<label class="col-sm-2">주문자 이메일</label>
			<div class="col-sm-3">
				<input name="orderEmail" value="이메일" type="text" class="form-control"/>
			</div>
		</div>
		<div class="form-group row">
			<label class="col-sm-2">받는 사람 이름</label>
			<div class="col-sm-3">
				<input name="receiveName" value="받는 사람 이름" type="text" class="form-control"/>
			</div>
		</div>
		<div class="form-group row">
			<label class="col-sm-2">받는 사람 연락처</label>
			<div class="col-sm-3">
				<input name="receiveTel" value="받는 사람 연락처" type="text" class="form-control"/>
			</div>
		</div>
		<div class="form-group row">
			<label class="col-sm-2">받는 사람 주소</label>
			<div class="col-sm-3">
				<input name="zipcode" id="zipcode" type="text" onclick="execDaumPostcode();" class="form-control"/>
				<input name="receiveAddress" id="receiveAddress" type="text" class="form-control"/>
				<input name="detailAddr" id="detailAddr" type="text" class="form-control"/>
			</div>
		</div>
		<div class="form-group row">
			<div class="col-sm-offset-2 col-sm-10">
				<a href="../shop_db/cart.jsp" class="btn btn-secondary" role="button">이전</a>
				<input type="submit" class="btn btn-primary" value="등록"/>
			</div>
		</div>
		
	</form>
	
	<script src="https://spi.maps.daum.net/imap/map_js_init/postcode.v2.js"></script>
    <script>
        function execDaumPostcode() {
            new daum.Postcode({
                oncomplete: function(data) {
                    // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

                    // 각 주소의 노출 규칙에 따라 주소를 조합한다.
                    // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                    var fullAddr = ''; // 최종 주소 변수
                    var extraAddr = ''; // 조합형 주소 변수

                    // 사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
                    if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                        fullAddr = data.roadAddress;
                    }
                    else { // 사용자가 지번 주소를 선택했을 경우(J)
                        fullAddr = data.jibunAddress;
                    }

                    // 사용자가 선택한 주소가 도로명 타입일때 조합한다.
                    if(data.userSelectedType === 'R'){
                        //법정동명이 있을 경우 추가한다.
                        if(data.bname !== ''){
                            extraAddr += data.bname;
                        }
                        // 건물명이 있을 경우 추가한다.
                        if(data.buildingName !== ''){
                            extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                        }
                        // 조합형주소의 유무에 따라 양쪽에 괄호를 추가하여 최종 주소를 만든다.
                        fullAddr += (extraAddr !== '' ? ' ('+ extraAddr +')' : '');
                    }

                    // 우편번호와 주소 정보를 해당 필드에 넣는다.
                    document.getElementById('zipcode').value = data.zonecode; //5자리 새우편번호 사용
                    document.getElementById('receiveAddress').value = fullAddr;

                    // 커서를 상세주소 필드로 이동한다.
                    document.getElementById('detailAddr').focus();
                }
            }).open();
        }
    </script>
</body>
</html>