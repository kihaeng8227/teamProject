<%@page import="java.io.PrintWriter"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.teamProject.cart.model.CartDAO"%>

<%
/* 장바구니에서 상품을 선택 삭제 */
String orderNum = session.getId();
CartDAO cartDAO = new CartDAO();

// 문자열로 넘어온 chkdID 사용. 
String chkdID = request.getParameter("chkdID");

PrintWriter write = response.getWriter();
if (chkdID == "") {
	write.println("<script>alert('삭제할 상품을 선택해 주세요'); location.href='cart.jsp';</script>");
	write.flush();
} else {
	cartDAO.deleteCartSel(orderNum, chkdID);
}

response.sendRedirect("cart.jsp");
%>
