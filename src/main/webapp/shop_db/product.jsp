<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%
String sessionId = (String) session.getAttribute("sessionId");
String pageNum = (String) request.getParameter("pageNum");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>제품 상세</title>
<link rel="stylesheet" href="../resources/css/bootstrap.min.css">
<style>

/*
 * Custom translucent site header
 */
.site-header {
	background-color: rgba(0, 0, 0, .85);
	-webkit-backdrop-filter: saturate(180%) blur(20px);
	backdrop-filter: saturate(180%) blur(20px);
}

.site-header a {
	color: #8e8e8e;
	transition: color .15s ease-in-out;
}

.site-header a:hover {
	color: #fff;
	text-decoration: none;
}

/*
 * Dummy devices (replace them with your own or something else entirely!)
 */
.product-device {
	position: absolute;
	right: 10%;
	bottom: -30%;
	width: 300px;
	background-color: #333;
	border-radius: 21px;
	transform: rotate(30deg);
}

.product-device::before {
	position: absolute;
	top: 10%;
	right: 10px;
	bottom: 10%;
	left: 10px;
	content: "";
	background-color: rgba(255, 255, 255, .1);
	border-radius: 5px;
}

.product-device-2 {
	top: -25%;
	right: auto;
	bottom: 0;
	left: 5%;
	background-color: #e5e5e5;
}

/*
 * Extra utilities
 */
.flex-equal>* {
	flex: 1;
}

@media ( min-width : 768px) {
	.flex-md-equal>* {
		flex: 1;
	}
}
</style>



</head>
<body>
	<jsp:include page="../inc/header.jsp" />

	<main>
		<%@ include file="../inc/dbconn.jsp"%>
		<%
		String productId = request.getParameter("productId");

		String sql = "select * from products where productId = ?";
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, productId);
		rs = pstmt.executeQuery();
		if (rs.next()) {
		%>
		<div class="d-md-flex flex-md-equal w-100 mb-5 ps-md-3">

			<div
				class="me-md-3 pt-3 px-3 pt-md-5 px-md-5 text-center overflow-hidden">
				<div class="pt-3">
					<h1><%=rs.getString("productName")%></h1>
					<p class="lead text-muted"><%=rs.getString("category")%></p>
				</div>
				<div class="bg-body shadow-sm mx-auto my-5"
					style="width: 50%; border-radius: 21px 21px 0 0;">
					<img
						src="${pageContext.request.contextPath}/resources/images/<%=rs.getString("fileName")%>"
						style="width: 100%" alt="">
				</div>

				<h2 class="featurette-heading"><%=rs.getString("productPrice")%>
					원
				</h2>
				<p class="text-muted col-md-6" style="margin: auto;"><%=rs.getString("description")%></p>
				<div class="d-grid gap-2 d-md-flex justify-content-center mt-5">
					<form name="addForm"
						action="./addCart.jsp?productId=<%=rs.getString("productId")%>"
						method="post">
						<a href="javascript:;" class="btn btn-warning" onclick="buyNow()">
							<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16"
								fill="currentColor" class="bi bi-credit-card"
								viewBox="0 0 16 16">
  <path
									d="M0 4a2 2 0 0 1 2-2h12a2 2 0 0 1 2 2v8a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2V4zm2-1a1 1 0 0 0-1 1v1h14V4a1 1 0 0 0-1-1H2zm13 4H1v5a1 1 0 0 0 1 1h12a1 1 0 0 0 1-1V7z"></path>
  <path
									d="M2 10a1 1 0 0 1 1-1h1a1 1 0 0 1 1 1v1a1 1 0 0 1-1 1H3a1 1 0 0 1-1-1v-1z"></path>
</svg> 주문하기
						</a> <a href="#" class="btn btn-primary" onclick="addToCart()"> <svg
								xmlns="http://www.w3.org/2000/svg" width="16" height="16"
								fill="currentColor" class="bi bi-cart2" viewBox="0 0 16 16">
  <path
									d="M0 2.5A.5.5 0 0 1 .5 2H2a.5.5 0 0 1 .485.379L2.89 4H14.5a.5.5 0 0 1 .485.621l-1.5 6A.5.5 0 0 1 13 11H4a.5.5 0 0 1-.485-.379L1.61 3H.5a.5.5 0 0 1-.5-.5zM3.14 5l1.25 5h8.22l1.25-5H3.14zM5 13a1 1 0 1 0 0 2 1 1 0 0 0 0-2zm-2 1a2 2 0 1 1 4 0 2 2 0 0 1-4 0zm9-1a1 1 0 1 0 0 2 1 1 0 0 0 0-2zm-2 1a2 2 0 1 1 4 0 2 2 0 0 1-4 0z"></path>
</svg> 장바구니
						</a> <a href="#" onclick="checkForm(); return false;"
							class="btn btn-danger"> <svg
								xmlns="http://www.w3.org/2000/svg" width="16" height="16"
								fill="currentColor" class="bi bi-heart" viewBox="0 0 16 16">
  <path
									d="m8 2.748-.717-.737C5.6.281 2.514.878 1.4 3.053c-.523 1.023-.641 2.5.314 4.385.92 1.815 2.834 3.989 6.286 6.357 3.452-2.368 5.365-4.542 6.286-6.357.955-1.886.838-3.362.314-4.385C13.486.878 10.4.28 8.717 2.01L8 2.748zM8 15C-7.333 4.868 3.279-3.04 7.824 1.143c.06.055.119.112.176.171a3.12 3.12 0 0 1 .176-.17C12.72-3.042 23.333 4.867 8 15z"></path>
</svg> 찜하기
						</a>

						<%
						if (pageNum == null) {
						%>
						<a href="/shop_db/productsList.ad" class="btn btn-success"
							onclick=""> <svg xmlns="http://www.w3.org/2000/svg"
								width="16" height="16" fill="currentColor"
								class="bi bi-arrow-left-circle" viewBox="0 0 16 16">
  <path fill-rule="evenodd"
									d="M1 8a7 7 0 1 0 14 0A7 7 0 0 0 1 8zm15 0A8 8 0 1 1 0 8a8 8 0 0 1 16 0zm-4.5-.5a.5.5 0 0 1 0 1H5.707l2.147 2.146a.5.5 0 0 1-.708.708l-3-3a.5.5 0 0 1 0-.708l3-3a.5.5 0 1 1 .708.708L5.707 7.5H11.5z"></path>
</svg> 제품 목록
						</a>
						<%
						} else {
						%>
						<a href="/shop_db/productsList.ad?pageNum=<%=pageNum%>"
							class="btn btn-success" onclick=""> <svg
								xmlns="http://www.w3.org/2000/svg" width="16" height="16"
								fill="currentColor" class="bi bi-arrow-left-circle"
								viewBox="0 0 16 16">
  <path fill-rule="evenodd"
									d="M1 8a7 7 0 1 0 14 0A7 7 0 0 0 1 8zm15 0A8 8 0 1 1 0 8a8 8 0 0 1 16 0zm-4.5-.5a.5.5 0 0 1 0 1H5.707l2.147 2.146a.5.5 0 0 1-.708.708l-3-3a.5.5 0 0 1 0-.708l3-3a.5.5 0 1 1 .708.708L5.707 7.5H11.5z"></path>
</svg> 제품 목록
						</a>
						<%
						}
						%>
					</form>
				</div>
			</div>
		</div>
	</main>


	<script type="text/javascript">

	
	function addToCart() {
		if (confirm("상품을 장바구니에 추가하시겠습니까?")) {
			document.addForm.submit();
		} else {
			document.addForm.reset();
		}
	}
	

    function checkForm() {
        if (${sessionId==null}) {
		alert("로그인 후 이용가능합니다.");
		return false;
	}
 
        if (confirm("상품을 찜하시겠습니까?")) {
        	const form = document.addForm;

        	form.action = "./addjjim.my?productId=<%=rs.getString("productId")%>";
        	form.submit();
		}
        
    }
    
    
    function buyNow() {
        if (${sessionId==null}) {
           alert("로그인 후 이용가능합니다.");
        } 
        
        else if (confirm("상품을 바로 주문하시겠습니까?") ) {
           
           document.addForm.action ="${pageContext.request.contextPath}/order/form.co?productId=<%=rs.getString("productId")%>";
           document.addForm.submit();
        }
        else {
           document.addForm.reset();
        }
     }

</script>



	<%
	}
	if (rs != null)
	rs.close();
	if (pstmt != null)
	pstmt.close();
	if (conn != null)
	conn.close();
	%>

	<jsp:include page="../inc/footer.jsp" />
</body>
</html>