<%@ page contentType="text/html; charset=UTF-8" %>


<nav id="sidebarMenu" class="col-md-3 col-lg-2 d-md-block bg-light sidebar collapse">
    <div class="position-sticky pt-3">
        <ul class="nav flex-column">
            <li class="nav-item">
                <a class="nav-link" href="../admin/AdminProductsList.ad">
                    <span data-feather="file"></span>
                    제품 관리
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="../admin/AdminMemberList.ad">
                    <span data-feather="users"></span>
                    회원 관리
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="../admin/adminOrderManagement.ad">
                    <span data-feather="shopping-cart"></span>
                    주문 관리
                </a>
            </li>
        </ul>
    </div>
    <a href="Logout.lo" class="btn btn-danger" role="button" style="position: absolute; bottom: 20px; left: 20px;">로그아웃</a>
</nav>
<script>
    feather.replace()
</script>