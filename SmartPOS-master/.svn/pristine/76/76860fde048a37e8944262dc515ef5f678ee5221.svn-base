<%@ page import="com.yada.spos.mag.core.shiro.Role" %>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="mvc" uri="http://www.springframework.org/tags/form" %>
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
                class="positionText">></span><a href="#"
                                                class="positionText">产品型号</a>
        </div>
        <div class="fromInputBtn">
            <div class="seachEquip marginTop10">
                <form id="queryForm" name="queryForm" action="list.do" method="get">
                    <table class="threeBtn tanTable">
                        <tr>
                            <td width="120" class="tdodd"><label class="marginRight10">产品型号:</label></td>
                            <td>
                                <div class="Pos_rela">
                                    <input type="text" class="seachIptText borderRadius width120" name="prodCode"
                                           value="${productsQuery.prodCode}"/>
                                    <span class="placeholder">请输入产品型号</span>
                                    <div class="errorBox borderRadius">
                                        <div class="errorArrow"></div>
                                    </div>
                                </div>
                            </td>
                            <td width="120" class="tdodd"><label class="marginRight10">厂商编号:</label></td>
                            <td>
                                <div class="Pos_rela">
                                    <div class="selectBox">
                                        <span class="seachSelect borderRadius width120">--请选择--</span>
                                        <select class="selectHidden" onChange="selectChang(this)" name="firmCode"
                                                id="firmCode">
                                            <option value="">--请选择--</option>
                                            <c:forEach items="${firmList}" var="items">
                                                <option value="${items.firmCode}"
                                                        <c:if test="${items.firmCode == productsQuery.firmCode}">selected</c:if>>
                                                    <c:out value='${items.firmCode}-${items.firmName}'/>
                                                </option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="errorBox borderRadius">
                                        <div class="errorArrow"></div>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td width="120" class="tdodd"><label class="marginRight10">模式:</label></td>
                            <td colspan="3">
                                <div class="selectBox">
                                    <span class="seachSelect borderRadius width120">--请选择---</span>
                                    <select class="selectHidden" name="deviceMode"
                                            onChange="selectChang(this)" id="deviceMode">
                                        <option value="">--请选择---</option>
                                        <option value="1"
                                                <c:if test="${productsQuery.deviceMode==1}">selected</c:if>>
                                            HD模式
                                        </option>
                                        <option value="2"
                                                <c:if test="${productsQuery.deviceMode==2}">selected</c:if>>
                                            HAND模式
                                        </option>
                                    </select>
                                </div>
                            </td>
                        </tr>
                    </table>
                    <table class="threeBtn ">
                        <tr>
                            <td>
                                <input type="submit" class="btn width80 borderRadius backgroundTransparent" value="查询"/>
                            </td>
                            <shiro:hasRole name="<%= Role.PRODUCTCREATE().toString()%>">
                                <td>
                                    <input type="button" class="btn width80 borderRadius backgroundTransparent"
                                           onClick="window.location.href='insert.do?reqParams=${reqParam}'" value="新增"/>
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
                    <shiro:hasRole name="<%= Role.PRODUCTUPDATE().toString()%>">
                        <th width="40">操作</th>
                    </shiro:hasRole>
                    <th>产品型号</th>
                    <th>产品型号名称</th>
                    <th>所属厂商编号</th>
                    <th>所属厂商名称</th>
                    <th>设备模式</th>
                </tr>
                </thead>
                <tbody>
                <%--@elvariable id="page" type="org.springframework.data.domain.Page"--%>
                <c:if test="${page eq null}">
                    <shiro:hasRole name="<%= Role.PRODUCTUPDATE().toString()%>">
                        <tr>
                            <td colspan="6">
                                点击查询查询数据
                            </td>
                        </tr>
                    </shiro:hasRole>
                    <shiro:lacksRole name="<%= Role.PRODUCTUPDATE().toString()%>">
                        <tr>
                            <td colspan="5">
                                点击查询查询数据
                            </td>
                        </tr>
                    </shiro:lacksRole>
                </c:if>
                <c:if test="${page ne null and fn:length(page.content) eq 0  }">
                    <shiro:hasRole name="<%= Role.PRODUCTUPDATE().toString()%>">
                        <tr>
                            <td colspan="6">
                                没有数据
                            </td>
                        </tr>
                    </shiro:hasRole>
                    <shiro:lacksRole name="<%= Role.PRODUCTUPDATE().toString()%>">
                        <tr>
                            <td colspan="5">
                                没有数据
                            </td>
                        </tr>
                    </shiro:lacksRole>
                </c:if>
                <c:if test="${page ne null and fn:length(page.content) ne 0  }">
                    <c:forEach items="${page.content}" var="items">
                        <tr>
                            <shiro:hasRole name="<%= Role.PRODUCTUPDATE().toString()%>">
                                <td>
                                    <a href="${pageContext.request.contextPath}/products/edit.do?prodCode=<c:out value='${items.prodCode}'/>&firmCode=<c:out value='${items.firmCode}'/>"
                                       class="Editor">修改</a></td>
                            </shiro:hasRole>
                            <td><c:out value='${items.prodCode}'/></td>
                            <td><c:out value='${items.prodName}'/></td>
                            <td><c:out value='${items.firmCode}'/></td>
                            <td><c:out value='${items.firmName}'/></td>
                            <td>
                                <c:if test="${items.deviceMode==1}">HD模式</c:if>
                                <c:if test="${items.deviceMode==2}">HAND模式</c:if>
                            </td>
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
        selectChang($("#deviceMode"));
        selectChang($("#firmCode"));
    });
</script>
</body>
</html>