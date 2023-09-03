<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@page import="javax.swing.border.Border" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.*" %>
<%@ page import="com.teamProject.board.model.BoardDTO" %>
<%
    BoardDTO board = (BoardDTO) request.getAttribute("board");
    int nowpage = ((Integer) request.getAttribute("page")).intValue();
%>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="../resources/css/bootstrap.min.css">
    <link rel="stylesheet" href="../resources/css/common.css">
    <meta charset="UTF-8">
    <title>업데이트</title>
</head>
<body>
<jsp:include page="../inc/header.jsp"/>
<div class="container mt-5">
    <form name="newUpdate"
          action="BoardUpdateAction.do?num=<%=board.getNum()%>&pageNum=<%=nowpage%>"
          class="form-horizontal" enctype="multipart/form-data" method="post">
        <div class="mb-4 row">
            <label class="col-sm-2 col-form-label text-center">회원ID</label>
            <div class="col-sm-10">
                <input name="name" type="text" class="form-control-sm"
                       value="<%=board.getName()%>" readonly>
            </div>
        </div>
        <div class="mb-4 row">
            <label class="col-sm-2 col-form-label text-center">제목</label>
            <div class="col-sm-10">
                <input name="subject" type="text" class="form-control-sm"
                       value="<%=board.getSubject()%>" style="width: 40%">
            </div>
        </div>
        <div class="mb-4 row">
            <label class="col-sm-2 col-form-label text-center">내용</label>
            <div class="col-sm-7">
                <textarea name="content" class="form-control" cols="50" rows="5"><%=board.getContent()%></textarea>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 control-label">이미지</label>
            <div class="col-sm-8">
                <input type="file" name="fileName" class="form-control">

                <input type="hidden" name="chkdID">
                <input type="checkbox" name="chkID"
                       value="<%=board.getFilename()%>" onclick="setChkAlone(this);">이미지삭제
            </div>
        </div>
        <div class="form-group row">
            <div class="col-sm-offset-2 col-sm-10">
                <c:set var="userId" value="<%=board.getId()%>"/>
                <c:if test="${sessionId==userId}">
                <p>
                    <a
                            href="./BoardDeleteAction.do?num=<%=board.getNum()%>&pageNum=<%=nowpage%>"
                            class="btn btn-danger">삭제</a> <input type="submit"
                                                                 class="btn btn-success" value="수정">
                    </c:if>
                    <a href="./BoardListAction.do?pageNum=<%=nowpage%>"
                       class="btn btn-primary">목록</a>
            </div>
        </div>
    </form>
    <hr>
</div>
<jsp:include page="../inc/footer.jsp"/>
<script>
    let arrID = new Array();

    let setChkAlone = function (T) { //개별 선택
        with (frmName()) {
            if (T.checked) {
                setArrChange(true, T.value);
            } else {
                setArrChange(false, T.value);
            }
        }
    }

    function frmName() {
        return document.newUpdate;
    }

    let setArrChange = function (flag, ID) {
        var idx = null;

        for (i = 0; i < arrID.length; i++) {
            if (arrID[i] === ID) {
                idx = i;
            }
        }

        if (idx != null) {
            arrID.splice(idx, 1);
        }

        if (flag) {
            arrID.push(ID);
        }

        frmName().chkdID.value = arrID;
    }


</script>
</body>
</html>