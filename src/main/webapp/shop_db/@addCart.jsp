<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.teamProject.admin.model.ProductsDTO"%>
<%@ page import="com.teamProject.admin.model.ProductsDAO"%>
<%@ page import="com.teamProject.cart.model.CartDAO" %>

<%
	String productId = request.getParameter("productId");
	if(productId == null || productId.trim().equals("")){ // 요청된 파라미터 id 값이 없는 경우
		response.sendRedirect("./products.jsp");
		return;
	}
	
	
	ProductsDAO dao = new ProductsDAO();
	
	ProductsDTO product = dao.getBoardByProductId(productId);
	if (product == null) {
		response.sendRedirect("exceptionNoProductId.jsp");
	}
	
	String orderNum = session.getId();
	String memberId = (String) session.getAttribute("sessionId");
	
	
	CartDAO cartDAO = new CartDAO();
	
	
	boolean flag = cartDAO.updateCart(product, orderNum, memberId);
	
	response.sendRedirect("./product.jsp?productId=" + productId);

%>