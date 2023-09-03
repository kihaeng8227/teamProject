<%@page import="com.teamProject.board.model.RippleDTO"%>
<%@page import="com.teamProject.board.model.RippleDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%
    request.setCharacterEncoding("UTF-8");
    int rippleId = Integer.parseInt(request.getParameter("rippleId"));

    RippleDAO dao = RippleDAO.getInstance();
    RippleDTO ripple = new RippleDTO();
    ripple.setRippleId(rippleId);
    if(dao.deleteRipple(ripple)) {
%>
{"result": "true"}
<%
}
else {%>
{"result" : "false"}
<%}
%>