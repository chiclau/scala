<%@ page import="com.yada.spos.mag.core.shiro.Role" %>
<%@ page language="java" pageEncoding="UTF-8" %>

<html>
<head>
    <%@include file="../common/base.jsp" %>
    <title>智能POS管理平台</title>
</head>

<body>
<!-- 右侧内容-->
<div class="leftContent Boxshadow marginLeft10 marginTop10">

    <div class="ModelDetail paddingBottom10">

        <div class="CurrentPosition marginBottom10">
            <span class="positionText">当前位置:</span><a href="#" class="positionText">应用分组</a>
        </div>
        <div class="fromInputBtn">
            <div class="seachEquip marginTop10">
                <form action="list.do" method="get">
                    <table class="threeBtn tanTable">
                        <tr>
                            <td width="120" class="tdodd"><label class="marginRight10">分组名称:</label></td>
                            <td>
                                <div class="Pos_rela">
                                    <input type="text" class="seachIptText marginRight10 borderRadius"
                                           name="appGroupName" value="${appGroupQuery.appGroupName}"/>
                                    <span class="placeholder">请输入分组名称</span>
                                    <input type="submit" class="btn width80 borderRadius backgroundTransparent"
                                           value="查询"/>
                                </div>
                            </td>
                        </tr>
                    </table>
                    <table class="threeBtn">
                        <tr>

                            <shiro:hasRole name="AppGroupCreateRole">
                                <td>
                                    <c:if test='${!(orgType eq "1" and orgId eq "00") and !(orgType eq "2" and orgId eq "000")}'>
                                        <input type="button" class="btn width80 borderRadius backgroundTransparent "
                                               onclick="window.location='insert.do?reqParams=${reqParam}'" value="新增"/>
                                    </c:if>
                                </td>
                            </shiro:hasRole>
                        </tr>
                    </table>
                </form>
            </div>
        </div>
        <div class="ModuleBOX marginTop10">
            <table class="tableDetail">
                <thead>
                <tr>
                    <th>操作</th>
                    <th>应用分组名称</th>
                    <th>分组描述</th>
                </tr>
                </thead>
                <tbody>
                <c:if test="${page eq null}">
                    <tr>
                        <td colspan="3">
                            点击查询查询数据
                        </td>
                    </tr>
                </c:if>
                <c:if test="${page ne null and fn:length(page.content) eq 0  }">
                    <tr>
                        <td colspan="3">
                            没有数据
                        </td>
                    </tr>
                </c:if>
                <c:if test="${page ne null and fn:length(page.content) ne 0  }">
                    <c:forEach items="${page.content}" var="item" varStatus="status">
                        <tr>
                            <td>
                                <a class="Editor" href="edit.do?appGroupId=${item.appGroupId}">编辑分组</a>
                                <a class="Editor" href="associatedAppList.do?appGroupId=${item.appGroupId}">关联应用</a>
                                <a class="Editor" href="unAssociateAppList.do?appGroupId=${item.appGroupId}">取消关联应用</a>
                                    <%--总行不显示关联设备--%>
                                <c:if test='${!(orgType eq "1" and orgId eq "00") and !(orgType eq "2" and orgId eq "000")}'>
                                    <shiro:hasRole name="<%= Role.GROUPREDEVICE().toString()%>">
                                        <a class="Editor"
                                           href="associatedDevList.do?appGroupId=${item.appGroupId}">关联设备</a>
                                    </shiro:hasRole>
                                    <shiro:hasRole name="<%= Role.GROUPDEDEVICE().toString()%>">
                                        <a class="Editor" href="unAssociatedDevList.do?appGroupId=${item.appGroupId}">取消关联设备</a>
                                    </shiro:hasRole>
                                </c:if>
                            </td>
                            <td>${item.appGroupName}</td>
                            <td>${item.appGroupDesc}</td>
                        </tr>
                    </c:forEach>
                </c:if>
                </tbody>
            </table>
            <%@include file="../common/page.jsp" %>
        </div>
    </div>
</div>
<script>
    var inputText = $("input[type='text']");
    $(".placeholder").click(function () {
        $(this).hide();
        $(this).prev("input[type='text']").focus();
    });
    inputText.focus(function () {
        $(this).next(".placeholder").hide();
    });
    inputText.blur(function () {
        if ($(this).val() == "") {
            $(this).next(".placeholder").show();
        }
    });

    $(function () {
        $(".tableDetail tbody tr:even").css("background-color", "#fff");
    });
</script>
</body>
</html>