<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.teamProject.cart.model.CartDAO" %>

<%
	/* 장바구니에서 상품을 전체 삭제 */
	
	String orderNum = session.getId();
	
	CartDAO cartDAO = new CartDAO();
	cartDAO.deleteCartAll(orderNum);

	response.sendRedirect("cart.jsp");
%>
