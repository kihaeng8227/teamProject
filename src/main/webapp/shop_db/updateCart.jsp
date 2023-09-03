<%@page import="com.teamProject.admin.model.ProductsDTO"%>
<%@page import="com.teamProject.admin.model.ProductsDAO"%>
<%@page import="com.teamProject.cart.model.CartDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.teamProject.cart.model.CartDAO" %>
<%@ include file="../inc/dbconn.jsp"%>

<%
	
	
	int cartId = Integer.parseInt(request.getParameter("chkID"));
	int cnt  = Integer.parseInt(request.getParameter("cnt" + cartId));
	
	
	
	String productId = request.getParameter("productId");
	
	ProductsDAO dao = new ProductsDAO();
	
	ProductsDTO product = dao.getBoardByProductId(productId);
	if (product == null) {
		response.sendRedirect("exceptionNoProductId.jsp");
	}
	
	String orderNum = session.getId();
	String memberId = (String) session.getAttribute("sessionId");
	
	
	CartDAO cartDAO = new CartDAO();


	boolean flag = cartDAO.updateCnt(product, cartId, memberId, cnt);


	response.sendRedirect("cart.jsp");
%>
