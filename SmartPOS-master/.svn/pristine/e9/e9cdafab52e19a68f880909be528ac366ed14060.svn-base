<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<html>
<head>
    <jsp:include page="../common/base.jsp"/>
    <title>智能POS管理平台</title>
</head>
<body>
<div class="leftContent Boxshadow marginLeft10 marginTop10">
    <div class=" ModelDetail paddingBottom10">
        <div class="CurrentPosition marginBottom10">
            <span class="positionText">当前位置:</span><a href="#" class="positionText">应用管理</a><span
                class="positionText">></span><a
                class="positionText">发布应用包</a>><a class="positionText">应用确认</a>
        </div>
        <div class="fromInputBtn">
            <div class="seachEquip marginTop10">
                <div class="overflowWidth">
                    <c:if test="${appFileResult eq null}">
                        <span>上传的zip包里没有应用信息</span>
                    </c:if>
                    <%--遍历每个应用信息--%>
                    <c:if test="${appFileResult ne null}">
                        <c:forEach items="${appFileResult}" var="item" varStatus="status">
                            <table class="tableDetail marginBottom10">
                                <tbody>
                                <tr>
                                    <td>
                                        <span class="marginRight10 packTit ">应用名称：</span>
                                        <span class="packContent"><c:out value="${item.appFileHistory.appName}"/></span>
                                    </td>
                                    <td>
                                    <span class="marginRight10 packTit"><span class="packTit"
                                                                              style="padding-right:28px;">包</span>名:</span>
                                        <span class="packContent"><c:out
                                                value="${item.appFileHistory.appPackageName}"/></span>
                                    </td>
                                    <td>
                                        <span class="marginRight10 packTit ">APK版本：</span>
                                        <span class="packContent"><c:out
                                                value="${item.appFileHistory.versionName}"/></span>
                                    </td>
                                    <td>
                                        <span class="marginRight10 packTit ">版本序号：</span>
                                        <span class="packContent"><c:out
                                                value="${item.appFileHistory.versionCode}"/></span>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <span class="marginRight10 packTit ">是否手持：</span>
                                        <span class="packContent">
                            <c:if test="${item.appFileHistory.modeHand eq '0'}">否</c:if>
                             <c:if test="${item.appFileHistory.modeHand eq '1'}">是</c:if>
                        </span>
                                    </td>
                                    <td>
                                        <span class="marginRight10 packTit">是否横屏:</span>
                                        <span class="packContent">
                            <c:if test="${item.appFileHistory.modeHd eq '0'}">否</c:if>
                             <c:if test="${item.appFileHistory.modeHd eq '1'}">是</c:if>
                        </span>
                                    </td>
                                    <td>
                                        <span class="marginRight10 packTit ">是否强制更新：</span>
                                        <span class="packContent">
                            <c:if test="${item.appFileHistory.forceUpdate eq '0'}">否</c:if>
                             <c:if test="${item.appFileHistory.forceUpdate eq '1'}">是</c:if>
                        </span>
                                    </td>
                                    <td>
                                        <span class="marginRight10 packTit ">是否卸载更新：</span>
                                        <span class="packContent">
                            <c:if test="${item.appFileHistory.deleteUpdate eq '0'}">否</c:if>
                             <c:if test="${item.appFileHistory.deleteUpdate eq '1'}">是</c:if>
                        </span>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="4">
                                        <span class="marginRight10 packTit ">发布时间：</span>
                                        <span class="packContent">
                                            <c:if test="${item.appFileHistory.publicDate ne null}">
                                                <c:out value="${fn:substring(item.appFileHistory.publicDate ,0,4)}"/>
                                                <c:if test="${fn:length(item.appFileHistory.publicDate) ge 6 }">
                                                    - <c:out
                                                        value="${fn:substring(item.appFileHistory.publicDate ,4,6)}"/>
                                                </c:if>
                                                <c:if test="${fn:length(item.appFileHistory.publicDate) ge 8}">
                                                    - <c:out
                                                        value="${fn:substring(item.appFileHistory.publicDate ,6,8)}"/>
                                                </c:if>
                                                <c:if test="${fn:length(item.appFileHistory.publicDate) ge 10}">
                                                    &nbsp;&nbsp;<c:out
                                                        value="${fn:substring(item.appFileHistory.publicDate ,8,10)}"/>
                                                </c:if>
                                            </c:if>
                                        </span>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="4" height="80">
                                        <span class="marginRight10 packTit ">更新说明：</span>
                                        <span class="packContent">
                                                <c:out value="${item.appFileHistory.remark}"/>
                                        </span>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="4">
                                        <span class="marginRight10 packTit ">删除权限：</span>
                                        <span class="packContent displayBlock">
                             <div class="permissions" style="width: 33%">
                                 <c:forEach items="${item.reducePermissions}" var="item1" varStatus="status">
                                 <span class="radioC"><c:out value="${item1}"/></span><br/>
                                 <c:if test="${status.index < fn:length(item.reducePermissions)*(1/3) && status.index >= fn:length(item.reducePermissions)*(1/3)-1}">
                             </div>
                            <div class="permissions" style="width: 33%">
                                </c:if>
                                <c:if test="${status.index < fn:length(item.reducePermissions)*(2/3) && status.index >= fn:length(item.reducePermissions)*(2/3)-1}">
                            </div>
                            <div class="permissions" style="width: 33%">
                                </c:if>
                                </c:forEach>
                            </div>
                        </span>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="4">
                                        <span class="marginRight10 packTit ">新增权限：</span>
                                        <span class="packContent displayBlock">
                                        <div class="permissions" style="width: 33%">
                                            <c:forEach items="${item.insertPermissions}" var="item2" varStatus="status">
                                            <span class="radioC"><c:out value="${item2}"/></span><br/>
                                            <c:if test="${status.index < fn:length(item.insertPermissions)*(1/3) && status.index >= fn:length(item.insertPermissions)*(1/3)-1}">
                                        </div>
                                        <div class="permissions" style="width: 33%">
                                            </c:if>
                                            <c:if test="${status.index < fn:length(item.insertPermissions)*(2/3) && status.index >= fn:length(item.insertPermissions)*(2/3)-1}">
                                        </div>
                                        <div class="permissions" style="width: 33%">
                                            </c:if>
                                            </c:forEach>
                                        </div>
                                    </span>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="4">
                                        <span class="marginRight10 packTit ">已有权限：</span>
                                        <span class="packContent displayBlock">
                                         <div class="permissions" style="width: 33%">
                                             <c:forEach items="${item.existPermissions}" var="item3" varStatus="status">
                                             <span class="radioC"><c:out value="${item3}"/></span><br/>
                                             <c:if test="${status.index < fn:length(item.existPermissions)*(1/3) && status.index >= fn:length(item.existPermissions)*(1/3)-1}">
                                         </div>
                                        <div class="permissions" style="width: 33%">
                                            </c:if>
                                            <c:if test="${status.index < fn:length(item.existPermissions)*(2/3) && status.index >= fn:length(item.existPermissions)*(2/3)-1}">
                                        </div>
                                        <div class="permissions" style="width: 33%">
                                            </c:if>
                                            </c:forEach>
                                        </div>
                                    </span>
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                        </c:forEach>
                    </c:if>
                    <table class="threeBtn ">
                        <tr>
                            <td width="50%">
                                <input type="button" class="btn width80 borderRadius backgroundTransparent" value="确认上传"
                                       onclick="saveAppFile()"/>
                            </td>
                            <td width="50%">
                                <input type="button" class="btn width80 borderRadius backgroundTransparent"
                                       value="取消" onclick="cancel()"/>
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
    var wait_box = $(".wait_box");
    var cancel1 = false;
    var flag = false;
    function saveAppFile() {
        wait_box.fadeIn(500);
        if (!flag) {
            flag = true;
            window.location = 'saveAppFile.do';
        } else {
            console.log("重复上传");
        }
    }


    function cancel() {
        wait_box.fadeIn(500);
        if (!cancel1) {
            cancel1 = true;
            window.location = 'cancel.do';
        } else {
            console.log("重复上传");
        }
    }
</script>
</body>
</html>