<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <%@ page
contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<nav id="sidebar" class="sidebar-wrapper">
  <div class="sidebar-content" data-simplebar style="height: calc(100% - 60px)">
    <div class="sidebar-brand">
      <a href="javascript:void(0)" 
            onclick='loadInFrame("<c:url value="/view/home/content/dashboard_admin.jsp"/>")'>
        <img
          src="<c:url value='/picture/hrms-removebg-preview.png'/>"
          height="24"
          class="logo-light-mode"
          alt="HRMS"
        />
        HRMS
      </a>
    </div>

    <ul class="sidebar-menu pt-3">
      <li class="sidebar-dropdown">
        <a href="javascript:void(0)"
          ><i class="uil uil-user me-2 d-inline-block"></i>Profile</a
        >
        <div class="sidebar-submenu">
          <ul>
            <li>
              <a
                href="<c:url value='/view'/>"
                target="mainFrame"
                >View Profile</a
              >
            </li>
            <li>
              <a
                href="<c:url value='/edit'/>"
                target="mainFrame"
                >Edit Profile</a
              >
            </li>
          </ul>
        </div>
      </li>

      <!-- ACCOUNT -->
       <li class="sidebar-dropdown">
        <a href="javascript:void(0)"
          ><i class="uil uil-user me-2 d-inline-block"></i>Account</a
        >
        <div class="sidebar-submenu">
          <ul>
            <li>
              <a
                href="#"
                target="mainFrame"
                >View Account List</a
              >
            </li>
          </ul>
        </div>
      </li>

      <!-- CONTRACT -->
      <li class="sidebar-dropdown">
        <a href="javascript:void(0)"
          ><i class="uil uil-wheelchair me-2 d-inline-block"></i>Contract</a
        >
        <div class="sidebar-submenu">
          <ul>
            <li>
              <a
                href="<c:url value='/myContracts'/>"
                target="mainFrame"
                >My Contract</a
              >
            </li>
          </ul>
        </div>
      </li>

      <!-- CV -->
      <li class="sidebar-dropdown">
        <a href="javascript:void(0)"
          ><i class="uil uil-apps me-2 d-inline-block"></i>CV</a
        >
        <div class="sidebar-submenu">
          <ul>
            <li>
              <a
                href="<c:url value='/view/cv/submitCV.jsp'/>"
                target="mainFrame"
                >Submit CV</a
              >
            </li>
          </ul>
        </div>
      </li>

      <!-- PAYROLL -->
      <li class="sidebar-dropdown">
        <a href="javascript:void(0)"
          ><i class="uil uil-shopping-cart me-2 d-inline-block"></i>Payroll</a
        >
        <div class="sidebar-submenu">
          <ul>
            <li>
              <a
                href="#"
                target="mainFrame"
                >Personal Payroll</a
              >
            </li>
          </ul>
        </div>
      </li>

      <!-- TICKET -->
      <li class="sidebar-dropdown">
        <a href="javascript:void(0)"
          ><i class="uil uil-flip-h me-2 d-inline-block"></i>Ticket</a
        >
        <div class="sidebar-submenu">
          <ul>
            <li>
              <a
                href="#"
                target="mainFrame"
                >My Ticket</a
              >
            </li>
          </ul>
        </div>
      </li>
    </ul>
  </div>
</nav>

<!-- JS helper -->
<script>
  // Function giúp load trang vào iframe theo đường dẫn
  function loadInFrame(url) {
    const frame = document.getElementById("mainFrame");
    if (frame) frame.src = url;
  }
</script>
