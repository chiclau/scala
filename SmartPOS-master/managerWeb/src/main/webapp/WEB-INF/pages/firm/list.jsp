<%@ page import="com.yada.spos.mag.core.shiro.Role" %>
<%@ page language="java" pageEncoding="UTF-8" %>
<%--@elvariable id="page" type="org.springframework.data.domain.Page"--%>
<!DOCTYPE html>
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
            <span class="positionText">当前位置:</span><span class="positionText">设备管理</span><span
                class="positionText">></span><a
                href="#" class="positionText">厂家管理</a>
        </div>
        <div class="fromInputBtn">
            <div class="seachEquip marginTop10">
                <form id="queryForm" name="queryForm" action="list.do" method="get">
                    <table class="threeBtn tanTable">
                        <tr>
                            <td width="120" class="tdodd"><label class="marginRight10">厂商代码:</label></td>
                            <td>
                                <div class="Pos_rela">
                                    <input type="text" class="seachIptText  borderRadius" name="firmCode"
                                           value="${firmQuery.firmCode}"/>
                                    <span class="placeholder">请输入厂商代码</span>
                                    <div class="errorBox borderRadius">
                                        <div class="errorArrow"></div>
                                    </div>
                                </div>
                            </td>
                            <td width="120" class="tdodd"><label class="marginRight10">厂商名称:</label></td>
                            <td>
                                <div class="Pos_rela">
                                    <input type="text" class="seachIptText  borderRadius" name="firmName"
                                           value="${firmQuery.firmName}"/>
                                    <span class="placeholder">请输入厂商名称</span>
                                    <div class="errorBox borderRadius">
                                        <div class="errorArrow"></div>
                                    </div>
                                </div>
                            </td>

                        </tr>
                    </table>

                    <table class="threeBtn ">
                        <tr>
                            <td>
                                <input type="submit" class="btn width80 borderRadius backgroundTransparent" value="查询"/>
                            </td>
                            <shiro:hasRole name="<%= Role.FIRMCREATE().toString()%>">
                                <td>
                                    <input type="button" class="btn width80 borderRadius backgroundTransparent"
                                           onclick="window.location.href='insert.do'" value="新增"/>
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
                    <shiro:hasRole name="<%= Role.FIRMUPDATE().toString()%>">
                        <th width="40">操作</th>
                    </shiro:hasRole>
                    <th>厂商代码</th>
                    <th>厂家名称</th>
                </tr>
                </thead>
                <tbody>
                <c:if test="${page eq null}">
                    <shiro:hasRole name="<%= Role.FIRMUPDATE().toString()%>">
                        <tr>
                            <td colspan="3">
                                点击查询查询数据
                            </td>
                        </tr>
                    </shiro:hasRole>
                    <shiro:lacksRole name="<%= Role.FIRMUPDATE().toString()%>">
                        <tr>
                            <td colspan="2">
                                点击查询查询数据
                            </td>
                        </tr>
                    </shiro:lacksRole>
                </c:if>
                <c:if test="${page ne null and fn:length(page.content) eq 0  }">
                    <shiro:hasRole name="<%= Role.FIRMUPDATE().toString()%>">
                        <tr>
                            <td colspan="3">
                                没有数据
                            </td>
                        </tr>
                    </shiro:hasRole>
                    <shiro:lacksRole name="<%= Role.FIRMUPDATE().toString()%>">
                        <tr>
                            <td colspan="2">
                                没有数据
                            </td>
                        </tr>
                    </shiro:lacksRole>
                </c:if>
                <c:if test="${page ne null and fn:length(page.content) ne 0  }">
                    <c:forEach items="${page.content}" var="items">
                        <tr>
                            <shiro:hasRole name="<%= Role.FIRMUPDATE().toString()%>">
                                <td>
                                    <a href="edit.do?firmCode=${items.firmCode}"
                                       class="Editor">编辑</a></td>
                            </shiro:hasRole>
                            <td><c:out value='${items.firmCode}'/></td>
                            <td><c:out value='${items.firmName}'/></td>
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