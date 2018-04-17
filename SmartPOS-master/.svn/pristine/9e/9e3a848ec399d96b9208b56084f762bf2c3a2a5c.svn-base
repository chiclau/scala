<%@ page language="java" pageEncoding="UTF-8" %>
<%--@elvariable id="model" type="com.yada.spos.db.model.AppFileHistory"--%>
<html>
<head>
    <title>智能POS管理平台</title>
    <%@include file="../common/base.jsp" %>
</head>

<body>
<!-- 右侧内容-->
<div class="leftContent Boxshadow marginLeft10 marginTop10">
    <div class=" ModelDetail paddingBottom10">
        <div class="CurrentPosition marginBottom10">
            <span class="positionText">当前位置:</span><a href="#" class="positionText">应用管理</a><span class="positionText">><a
                href="#" class="positionText">应用包详情</a></span>
        </div>
        <div class="fromInputBtn">
            <div class="seachEquip marginTop10">
                <div class="overflowWidth">
                    <table class="tableDetail">
                        <tbody>
                        <tr>
                            <td><span class="marginRight10 packTit">应用名称:</span><span class="packContent">
                                <c:out
                                        value="${model.appName}"/></span></td>

                            <td>
                                <span class="marginRight10 packTit">是否HD横屏模式:</span>
                                    <span class="packContent">
                                        <c:if test="${model.modeHd eq '0'}">否</c:if>
                                        <c:if test="${model.modeHd eq '1'}">是</c:if>
                                    </span>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2"><span class="marginRight10 packTit">全量更新包的包名:</span><span
                                    class="packContent"><c:out
                                    value="${model.fileName}"/></span></td>
                        </tr>
                        <tr>
                            <td colspan="2"><span class="marginRight10 packTit">全量包MD5:</span><span class="packContent"><c:out
                                    value="${model.fileMd5}"/></span></td>
                        </tr>
                        <tr>
                            <td><span class="marginRight10 packTit">发布时间:</span><span class="packContent">
                                     <c:if test="${model.publicDate ne null}">
                                         <c:out value="${fn:substring(model.publicDate ,0,4)}"/>-<c:out
                                             value="${fn:substring(model.publicDate ,4,6)}"/>-<c:out
                                             value="${fn:substring(model.publicDate ,6,8)}"/>&nbsp;&nbsp;<c:out
                                             value="${fn:substring(model.publicDate ,8,10)}"/>
                                     </c:if>
                                    </span></td>

                            <td><span class="marginRight10 packTit">强制更新:</span><span class="packContent">
                                    <c:if test="${model.deleteUpdate eq '0'}">否</c:if>
                                    <c:if test="${model.deleteUpdate eq '1'}">是</c:if>
                                </span></td>
                        </tr>
                        <tr>
                            <td colspan="2"><span class="marginRight10 packTit">更新说明:</span><span
                                    class="packContent"><c:out
                                    value="${model.remark}"/></span></td>
                        </tr>
                        <tr>
                            <td colspan="2"><span class="marginRight10 packTit">APK权限编码串:</span><span
                                    class="packContent" style="clear: both;"><c:out
                                    value="${model.appPermission}"/></span></td>
                        </tr>
                        <tr>
                            <td><span class="marginRight10 packTit">机构ID:</span><span class="packContent"><c:out
                                    value="${model.orgId}"/></span></td>

                            <td><span class="marginRight10 packTit">机构类型:</span><span class="packContent"><c:out
                                    value="${model.orgType}"/></span></td>
                        </tr>
                        </tbody>
                    </table>
                    <table class="threeBtn ">
                        <tr>
                            <td width="50%">
                                <input type="submit" class="btn width80 borderRadius backgroundTransparent"
                                       onclick="history.back()" value="返回"/>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    $(function () {
        $(".tableDetail tbody tr:even").css("background-color", "#fff");
    });
</script>
</body>
</html>