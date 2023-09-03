<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.teamProject.admin.model.ProductsDTO" %>
<%@ page import="com.teamProject.admin.model.ProductsDAO" %>
<%@ page import="com.teamProject.cart.model.CartDAO" %>
<%@ page import="com.teamProject.cart.model.CartDTO" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet">
	<%
		String cartId = session.getId();
		String memberId = (String) session.getAttribute("sessionId");
	%>
<title>장바구니</title>
</head>
<body>
	<jsp:include page="../inc/header.jsp" />
	<div class="jumbotron">
		<div class="container">
			<h1 class="display-3">장바구니</h1>
		</div>
	</div>
	<div class="container">
		<div class="row" >
			<table width="100%">
				<tr>		
					<td align="right"><a href="javascript:;"  class="btn btn-primary btn-lg" onclick="cartOrder()">주문하기</a></td>
				</tr>
			</table>
		</div>
		<div style="padding-top: 50px">
		<script type="text/javascript" src="../resources/js/check_system.js"></script>
		<form name="frmCart" method="get">
			<input type="hidden" name="id">			
			<input type="hidden" name="cnt">
			<input type="hidden" name="chkID">
			<input type="text" name="chkdID" style="display:none;">
	
			<table class="table table-hover">
				<tr>
					<td>
						<span class="btn btn-danger" onclick="deleteCart()">장바구니 비우기</span>&nbsp;
						<span class="btn btn-danger" onclick="deleteCartSel()">선택 삭제하기</span>			
					</td>
				</tr>
			
				<tr class="table-secondary" >
					<th>상품</th>
					<th>이미지</th>
					<th>가격</th>
					<th>수량</th>
					<th>소계</th>
					<th>비고</th>
				</tr>
				
				<%
					int sum = 0;
					
					ArrayList<CartDTO> cartArrayList = (ArrayList<CartDTO>) request.getAttribute("carts");
					for(CartDTO cart : cartArrayList){
						int total = cart.getProductPrice() * cart.getCnt();
						sum += total;
				%>
					
		
				<tr>
					
					<td>
						<input type="hidden" name="productId" value="<%=cart.getProductId()%>">
						<input type="checkbox" name="chkID<%=cart.getCartId()%>" value="<%=cart.getCartId()%>" onClick="setChkAlone(this);">
						<a href="./product.jsp?productId=<%=cart.getProductId()%>" class="btn btn-outline-primary"> <%=cart.getProductId()%> - <%=cart.getProductName() %></a>
					</td>
					<td><img src="${pageContext.request.contextPath}/resources/images/<%=cart.getFileName()%>" class="img-thumbnail" style="width: 200px"></td>
					<td class="price"><%=cart.getProductPrice() %></td>
					<td><input type="number" value="<%=cart.getCnt() %>" name="cnt<%=cart.getCartId()%>" class="productCnt" min="1" onClick="cartCnt()"> <input type="button" value="수정" onclick="updateCart(<%=cart.getCartId()%>)" class="btn btn-primary btn-sm"></td>
					<td class="total"><%=total %></td>
					<td><span class="badge text-bg-danger" onclick="removeCartById('<%=cart.getCartId()%>')">삭제</span></td>	
				</tr>
				
				<%
					}
				%>
				<tr class="table-secondary">
					<th></th>
					<th></th>
					<th></th>
					<th>총액</th>
					<th id="sum"><%=sum %></th>
					<th></th>
				</tr>
			</table>
		</form>
		<script>
			function frmName() {
				return document.frmCart;
			}
			function cartOrder() {
				let memberId = "<%=memberId%>";
				if ( memberId == "null") {
					alert("로그인 후 이용가능합니다.");
					location.href="../member/loginMember.jsp";
				} 
				else {
					location.href="${pageContext.request.contextPath}/order/form.co";
				}
			}
		</script>
		</div>
		<script>
			const frm = document.frmCart;
			let removeCartById = function(ID){
				if(confirm('해당 상품을 삭제하시겠습니까?')){
					location.href = 'removeCart.jsp?id=' + ID;
				}
			}
			let deleteCartSel = function() {
				if(confirm('선택한 상품을 삭제하시겠습니까?')) {
					frm.action = "deleteCartSel.jsp";
					frm.submit();
				}
			}
			
			let deleteCart = function(){
				if(confirm('삭제하시겠습니까?')){
				
					location.href = 'deleteCart.jsp';
				}
			}
			let updateCart = function(id) {
				v = document.querySelector("[name = cnt" + id + "]" ).value;
				v2 = document.querySelector("[name = chkID" + id + "]" ).value;
				console.log(v);
				frm.cnt.value = v;
				frm.chkID.value = v2;
				if(confirm('선택한 상품을 수정하시겠습니까?')) {
					frm.action = "updateCart.jsp?";
					frm.submit();
				}
			}
			
			let cnt = document.querySelectorAll(".productCnt");
			let total = document.querySelectorAll(".total");
			let price = document.querySelectorAll(".price");
			let sum = document.querySelector("#sum");
			let num = 0;
			
			let cartCnt = function() {
				for (let i = 0; i < cnt.length; i++) {
					cnt[i].addEventListener("change", function() {
						num = 0;
						total[i].innerText = Number.parseInt(cnt[i].value) * Number.parseInt(price[i].innerText); 
						for (let j = 0; j < total.length; j++) {
							num += Number.parseInt(total[j].innerText);
						}
						sum.innerText = num;
					});
				}	
			}
		</script>
		<a href="./productsList.ad" class="btn btn-secondary"> &laquo; 쇼핑 계속하기</a>
		<hr>
	<jsp:include page="../inc/footer.jsp" />
</body>
</html>