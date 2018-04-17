<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
         import="com.yada.spos.mag.core.shiro.Role" %>
<!--左侧菜单-->
<div class="page_left_fixed leftMenu">
    <input type="hidden" id="curMenuID" value="${curMenuID}">
    <input type="hidden" id="isSubMenu" value="${isSubMenu}">
    <ul>
        <li>
            <div class="menu menuBig" id="menuDevMag"><span
                    class="iconEquipment iconEquipment2 menuIcon"></span>设备管理<span
                    class="rightIcon addIcon"></span></div>
            <div class="submenuBox" style="display: none">
                <shiro:hasRole name="<%= Role.FIRMMAG().toString()%>">
                    <a href="${pageContext.request.contextPath}/firm/emptyList.do">
                        <div class="submenu" id="firm">厂家管理</div>
                    </a>
                </shiro:hasRole>
                <shiro:hasRole name="<%= Role.PRODUCTMAG().toString()%>">
                    <a href="${pageContext.request.contextPath}/products/emptyList.do">
                        <div class="submenu" id="products">产品型号</div>
                    </a>
                </shiro:hasRole>
                <shiro:hasRole name="<%= Role.DEVICEMAG().toString()%>">
                    <a href="${pageContext.request.contextPath}/device/emptyList.do">
                        <div class="submenu" id="device">设备明细</div>
                    </a>
                </shiro:hasRole>
            </div>
        </li>
        <shiro:hasRole name="<%= Role.APPMAG().toString()%>">
            <li class="menu menus" id="appFileHistory"><a
                    href="${pageContext.request.contextPath}/appFileHistory/emptyList.do"><span
                    class="iconApplicateManage menuIcon"></span>应用管理</a></li>
        </shiro:hasRole>
        <shiro:hasRole name="<%= Role.APPGROUPMAG().toString()%>">
            <li class="menu menus" id="appGroup"><a
                    href="${pageContext.request.contextPath}/appGroup/emptyList.do"><span
                    class="iconApplicateGroup menuIcon"></span>应用分组</a></li>
        </shiro:hasRole>
        <shiro:hasRole name="<%= Role.DEVICEPARAMMAG().toString()%>">
            <li class="menu menus" id="onlineParam"><a
                    href="${pageContext.request.contextPath}/onlineParam/emptyList.do"><span
                    class="iconApplicateParameter menuIcon"></span>参数维护</a></li>
        </shiro:hasRole>
        <shiro:hasRole name="<%= Role.OTAMAG().toString()%>">
            <li class="menu menus" id="otaHistory"><a
                    href="${pageContext.request.contextPath}/otaHistory/emptyList.do"><span
                    class="iconOTA menuIcon"></span>OTA管理</a></li>
        </shiro:hasRole>
        <shiro:hasRole name="<%= Role.MPOSMAG().toString()%>">
            <li class="menu menus" id="authDevice"><a
                    href="${pageContext.request.contextPath}/authDevice/emptyList.do"><span
                    class="iconMuPOS menuIcon"></span>母POS管理</a></li>
        </shiro:hasRole>
        <shiro:hasRole name="<%= Role.TERMSNMAG().toString()%>">
            <li class="menu menus" id="termWorkKey"><a
                    href="${pageContext.request.contextPath}/termWorkKey/emptyList.do"><span
                    class="iconBranch menuIcon"></span>终端SN管理</a></li>
        </shiro:hasRole>
    </ul>
</div>
<script>
    /**/
    $(".menuBig").click(function () {
        $(this).children(".rightIcon").toggleClass("addIcon");
        $(this).next(".submenuBox").slideToggle();
    });
    $(".menus").click(function () {
        menuChange(this)
    });
    $(".submenu").click(function () {
        subMenuChange(this)
    });
    $(function () {
        var curMenuID = "#" + $("#curMenuID").val();
        var isSubMenu = $("#isSubMenu").val();
        if ("true" == isSubMenu) {
            subMenuChange($(curMenuID));
        } else {
            menuChange($(curMenuID));
        }
    });

    function menuChange(target) {
        $(".menu").removeClass("MenuCurrent");
        target.addClass("MenuCurrent");
        $(".menu .iconEquipment").removeClass("iconEquipment2");
        $(".menu .iconApplicateManage").removeClass("iconApplicateManage2");
        $(".menu .iconApplicateGroup").removeClass("iconApplicateGroup2");
        $(".menu .iconApplicateParameter").removeClass("iconApplicateParameter2");
        $(".menu .iconOTA").removeClass("iconOTA2");
        $(".menu .iconMuPOS").removeClass("iconMuPOS2");
        $(".menu .iconBranch").removeClass("iconBranch2");
        target.find(".iconEquipment").addClass("iconEquipment2");
        target.find(".iconApplicateManage").addClass("iconApplicateManage2");
        target.find(".iconApplicateGroup").addClass("iconApplicateGroup2");
        target.find(".iconApplicateParameter").addClass("iconApplicateParameter2");
        target.find(".iconOTA").addClass("iconOTA2");
        target.find(".iconMuPOS").addClass("iconMuPOS2");
        target.find(".iconBranch").addClass("iconBranch2");
        $(".submenu").removeClass("currentSubmenu");
    }

    function subMenuChange(target) {
        $(".submenu").removeClass("currentSubmenu");
        $(".menu").removeClass("MenuCurrent");
        $(".menu .iconEquipment").removeClass("iconEquipment2");
        $(".menu .iconCertificate").removeClass("iconCertificate2");
        $(".menu .iconOTA").removeClass("iconOTA2");
        $(".menu .iconMuPOS").removeClass("iconMuPOS2");
        $(".menu .iconBranch").removeClass("iconBranch2");
        target.parent("a").parent(".submenuBox").prev(".menu").find(".iconEquipment").addClass("iconEquipment2");
        $(".menuBig").click();
        target.parent("a").parent(".submenuBox").prev(".menu").addClass("MenuCurrent");
        target.addClass("currentSubmenu");
    }
</script>

