<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
	<% 
		request.setCharacterEncoding("UTF-8");
		//String id = request.getParameter("id");
		String result = request.getAttribute("result").toString();
	 %>
	 {"result":
			<c:if test='${result eq "true"}'>
				"true"
			</c:if>
			<c:if test='${result eq "false"}'>
				"false"
			</c:if>

	}