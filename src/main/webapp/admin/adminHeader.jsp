<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<header class="navbar navbar-dark sticky-top bg-dark flex-md-nowrap p-0 shadow">
    <a class="navbar-brand col-md-3 col-lg-2 me-0 px-3" href="AdminProductsList.ad">I-GEOYA</a>
    <%
        String RequestURI = request.getRequestURI(); // 전체 경로를 가져옴.
        String contextPath = request.getContextPath(); // 프로젝트 Path만 가져옴.
        String command =  RequestURI.substring(contextPath.length()); // 전체경로에서 프로젝트 Path 길이 만큼의 인덱스 이후의 문자열을 가져옴.
        if (command.contains("adminProducts")) { %>
            <select style="background: #535559; height: 48px; font-size: 15px" id="items">
                <option value="productId">제품 ID</option>
                <option value="productName">제품명</option>
                <option value="category">카테고리</option>
            </select>
    <script>
        let itemsP = document.querySelector("#items");
        itemsP.value = "${items}"
        if(itemsP.value == "") {
            itemsP.value = "productId";
        }
    </script>
    <%
        } else if (command.contains("adminMember")) {
    %>
        <select style="background: #535559; height: 48px; font-size: 15px" id="items">
            <option value="id">회원 ID</option>
            <option value="name">이름</option>
            <option value="phone">전화번호</option>
        </select>
        <script>
            let itemsU = document.querySelector("#items");
            itemsU.value = "${items}";
            if(itemsU.value == "") {
                itemsU.value = "id";
            }
        </script>
    <%
        }
    %>
        <input class="form-control form-control-dark w-100" type="text" placeholder="Search" aria-label="Search" id="text" value="${text}">
        <div class="navbar-nav">
            <div class="nav-item text-nowrap">
                <input type="button" class="nav-link px-3 green bg-secondary" href="#" id="search" value="검색" style="height: 48px"/>
            </div>
        </div>
</header>