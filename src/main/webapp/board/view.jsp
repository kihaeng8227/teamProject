<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@page import="javax.swing.border.Border" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.*" %>
<%@ page import="com.teamProject.board.model.BoardDTO" %>
<%
    BoardDTO board = (BoardDTO) request.getAttribute("board");

    int num = ((Integer) request.getAttribute("num")).intValue();
    int nowpage = ((Integer) request.getAttribute("page")).intValue();


%>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="../resources/css/bootstrap.min.css">
    <link rel="stylesheet" href="../resources/css/common.css">
    <meta charset="UTF-8">
    <title>상세보기</title>
    <%--  신고하기--%>
    <script>
        document.addEventListener("DOMContentLoaded", function () {
            const xhr = new XMLHttpRequest(); //XMLHttpRequest 객체 생성

            document.querySelector('span[name=goReport]').addEventListener('click', function () {

                const report = prompt("신고 사유를 입력해주세요." + "");
                let num = document.querySelector('input[name=num]');
                let subject = document.querySelector('input[name=subject]');
                let content = document.querySelector('textarea[name=report]');
                
                if(report == null || report == "") return;

                console.log(subject);

                //xhr.open('POST', '../board/ajax_insert_content.jsp?boardName=board&num=' +
                //num.value + '&name=' + name.value + '&content=' + content.value);
                xhr.open('POST', '../boardController/ReportAction.do?report=' + report + '&num=' + num.value + '&subject=' + subject.value);
                xhr.send();
                xhr.onreadystatechange = () => {
                    if (xhr.readyState !== XMLHttpRequest.DONE) return;

                    if (xhr.status === 200) { //서버(url)에 문서가 존재함
                        console.log(xhr.response);
                        const json = JSON.parse(xhr.response);
                        if (json.result === 'true') {
                            content.value = ''; // input 태그에 입력된 값 삭제
                            insertList();
                        } else {
                            alert("삭제에 실패했습니다.");
                        }
                    } else { //서버(url)에 문서가 존재하지 않음.
                        console.error('Error', xhr.status, xhr.statusText);
                    }
                }
            });
        });
    </script>

</head>
<body>
<jsp:include page="../inc/header.jsp"/>

<div class="container my-5">
    <div class="mb-4 row">
        <label class="col-sm-2 col-form-label text-center">회원ID</label>
        <div class="col-sm-10">
            <input type="text" class="form-control-sm" value="<%=board.getName()%>" readonly>
        </div>
    </div>
    <div class="mb-4 row">
        <label class="col-sm-2 col-form-label text-center">제목</label>
        <div class="col-sm-10">
            <input type="text" class="form-control-sm" value="<%=board.getSubject()%>" readonly style="width:40%">
        </div>
    </div>
    <% if (board.getFilename() != null && !board.getFilename().isEmpty()) { %>
    <div class="mb-3 row">
        <label class="col-sm-2 col-form-label text-center">이미지</label>
        <div class="col">
            <img src="/imgs/<%=board.getFilename()%>" class="user-gallery-image" style="width:40%">
        </div>
    </div>
    <% } %>
    <div class="mb-4 row">
        <label class="col-sm-2 col-form-label text-center">내용</label>
        <div class="col-sm-7">
            <textarea class="form-control" cols="50" rows="5" readonly><%=board.getContent()%></textarea>
        </div>

    </div>


    <!-- 리플목록 출력 영역 -->
    <hr>
    <div class="form-group row user-repple-list">
        <ul class="list-group list-group-numbered " style="width:40%">

        </ul>
    </div>
    <form name="frmRippleDelete" class="form-horizontal" method="post">
        <input type="hidden" name="num" value="<%=board.getNum()%>">
        <input type="hidden" name="pageNum" value="${page}">
        <input type="hidden" name="rippleId">
    </form>

    <!-- //리플 목록 -->

    <!-- 리플쓰기, 로그인 상태에서만 나옴 -->
    <hr>
    <c:if test="${sessionId != null}">
        <form name="frmRipple" class="form-horizontal" method="post">
            <input type="hidden" name="num" value="<%=board.getNum()%>">
            <input type="hidden" name="pageNum" value="${page}">
            <p>댓글 등록 </p>
            <div class="form-group row">
                <label class="col-sm-2 col-form-label text-center">회원ID</label>
                <div class="col-sm-3">
                    <input name="name" type="text" class="form-control-sm" value="${sessionId}" placeholder="name">
                </div>
            </div>
            <div class="form-group row">
                <label class="col-sm-2 col-form-label text-center">내용</label>
                <div class="col-sm-7" style="word-break: break-all;">
                    <textarea name="content" class="form-control" cols="50" rows="3"></textarea>
                </div>
            </div>
            <div class="form-group row">
                <label class="col-sm-2 control-label"></label>
                <div class="col-sm-3">
                    <span class="btn btn-primary mt-2" name="goRippleSubmit">등록</span>
                </div>
            </div>
        </form>
        <script>
            document.addEventListener("DOMContentLoaded", function () {
                const xhr = new XMLHttpRequest(); //XMLHttpRequest 객체 생성

                document.querySelector('span[name=goRippleSubmit]').addEventListener('click', function () {
                    /* 등록 버튼 클릭시
                    1)데이터 베이스에 데이터 등록 2) 화면에 표시 */
                    let num = document.querySelector('input[name=num]');
                    let name = document.querySelector('input[name=name]');
                    let content = document.querySelector('textarea[name=content]');

                    //xhr.open('POST', '../board/ajax_insert_content.jsp?boardName=board&num=' +
                    //num.value + '&name=' + name.value + '&content=' + content.value);
                    xhr.open('POST', '../boardController/RippleWriteAction.do?boardName=board&num=' + num.value + '&name=' + name.value + '&content=' + content.value);
                    xhr.send();
                    xhr.onreadystatechange = () => {
                        if (xhr.readyState !== XMLHttpRequest.DONE) return;

                        if (xhr.status === 200) { //서버(url)에 문서가 존재함
                            console.log(xhr.response);
                            const json = JSON.parse(xhr.response);
                            if (json.result === 'true') {
                                content.value = ''; // input 태그에 입력된 값 삭제
                                insertList();
                            } else {
                                alert("삭제에 실패했습니다.");
                            }
                        } else { //서버(url)에 문서가 존재하지 않음.
                            console.error('Error', xhr.status, xhr.statusText);
                        }
                    }
                });
            });

        </script>
    </c:if>
    <!-- //리플쓰기 -->
    <script>
        const xhr = new XMLHttpRequest(); //XMLHttpRequest 객체 생성
        //함수 선언
        const insertItem = function (item) {
            //목록에 요소를 추가. 처음 로딩시 목록을 출력하거나, 새로운 글 등록시 사용
            let tagNew = document.createElement("li");
            tagNew.setAttribute('class', 'list-group-item');
            tagNew.innerHTML = item.name + ' ----- ' + item.content;
            if (item.isWriter === 'true') { //비로그인 댓글 삭제
                tagNew.innerHTML += '<span class="btn btn-danger btn-sm ms-4" onclick="goRippleDelete(\'' + item.rippleId + '\');">삭제</span>';
            }
            let tagUl = document.querySelector('.user-repple-list ul');
            tagUl.append(tagNew);
        }

        const insertList = function () {
            //목록을 가지고 옴
            let num = document.querySelector('form[name=frmRippleDelete] input[name=num]');
            //xhr.open('GET', '../board/ajax_list_item.jsp?boardName=board&num=' + num.value);
            xhr.open('GET', '../boardController/RippleListAction.do?boardName=board&num=' + num.value);
            xhr.send();
            xhr.onreadystatechange = function () {
                if (xhr.readyState !== XMLHttpRequest.DONE) return;

                if (xhr.status === 200) { //서버(url)에 문서가 존재함
                    console.log(xhr.response);
                    const json = JSON.parse(xhr.response);

                    let tagUl = document.querySelector('.user-repple-list ul');
                    tagUl.innerHTML = ""; //내부 태그 삭제후 인서트

                    for (let data of json.listData) {
                        console.log(data);
                        insertItem(data);
                    }
                } else { //서버 (url)에 문서가 존재하지 않음.
                    console.error('Error', xhr.status, xhr.statusText);
                }
            }
        }
        const goRippleDelete = function (ID) {
            if (confirm("삭제하시겠습니까?")) {
                const xhr = new XMLHttpRequest(); // XMLHttpRequest객체 생성
                //xhr.open('POST', '../board/ajax_delete.jsp?rippleId=' + ID);
                xhr.open('POST', '../boardController/RippleDeleteAction.do?rippleId=' + ID);
                xhr.send();
                xhr.onreadystatechange = () => {
                    if (xhr.readyState !== XMLHttpRequest.DONE) return;

                    if (xhr.status === 200) { //서버(url)에 문서가 존재함
                        console.log(xhr.response);
                        const json = JSON.parse(xhr.response);
                        if (json.result === 'true') {
                            insertList();
                        } else {
                            alert("삭제에 실패했습니다.");
                        }
                    } else { //서버(url)에 문서가 존재하지 않음.
                        console.error('Error', xhr.status, xhr.statusText);
                    }
                }
            }
        }


        document.addEventListener("DOMContentLoaded", function () {

            insertList();
        });
    </script>
    <div class="form-group row">
        <div class="col-sm-offset-2 col-sm-10">
            <c:set var="userId" value="<%=board.getId()%>"/>
            <c:if test="${sessionId==userId}">

                <span class="btn btn-success" onclick="goUpdate();">수정</span>
                <%--          <a href="./BoardListAction.do?pageNum=<%=nowpage%>" class="btn btn-primary">목록</a></p>--%>
            </c:if>
            <a href="./BoardListAction.do?pageNum=<%=nowpage%>" class="btn btn-primary">목록</a>
            <c:if test="${sessionId != null}">
                <input type="hidden" name="subject" value="<%=board.getSubject()%>">
                <span class="btn btn-danger" name="goReport">신고</span>
            </c:if>

        </div>
    </div>
    <form name="frmUpdate" method="post">
        <input type="hidden" name="num" value="<%=num%>">
        <input type="hidden" name="pageNum" value="<%=nowpage%>">

    </form>
    <script>
        let goUpdate = function () {
            const frm = document.frmUpdate;
            frm.action = "./BoardUpdateForm.do";
            frm.submit();
        }
        let goDelete = function () {
            if (confirm("삭제하시겠습니까")) {
                const frm = document.frmUpdate;
                frm.action = "./BoardDeleteAction.do";
                frm.submit();
            }
        }
    </script>
</div>
<jsp:include page="../inc/footer.jsp"/>

</body>
</html>