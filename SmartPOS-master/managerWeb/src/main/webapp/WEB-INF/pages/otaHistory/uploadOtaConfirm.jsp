<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<html>
<head>
    <jsp:include page="../common/base.jsp"/>
    <title>智能POS管理平台</title>
</head>
<body>
<!-- 右侧内容-->
<div class="leftContent Boxshadow marginLeft10 marginTop10">
    <div class="CurrentPosition marginBottom10">
        <span class="positionText">当前位置:</span><a href="#"
                                                  class="positionText">ota管理</a><span
            class="positionText">></span><a href="#" class="positionText">发布OTA确认</a>
    </div>
    <div class="fromInputBtn">
        <table class="tableDetail marginBottom10">
            <tr>
                <td colspan="3">
                    <span class="marginRight10 packTit ">厂商名称：</span>
                    <span class="packContent"><c:out value="${result.firmName}"/></span>
                </td>
            </tr>
            <tr>
                <td>
                    <span class="marginRight10 packTit ">应用名称：</span>
                    <span class="packContent"><c:out value="${result.otaName}"/></span>
                </td>

                <td>
                    <span class="marginRight10 packTit ">产品名称：</span>
                    <span class="packContent"><c:out value="${result.prodName}"/></span>
                </td>
                <td>
                    <span class="marginRight10 packTit"><span class="packTit"
                                                              style="padding-right:28px;">包</span>名:</span>
                    <span class="packContent"><c:out value="${result.otaPackageName}"/></span>
                </td>
            </tr>
            <tr>
                <td>
                    <span class="marginRight10 packTit ">版本序号：</span>
                    <span class="packContent"><c:out value="${result.versionName}"/></span>
                </td>
                <td>
                    <span class="marginRight10 packTit ">是否手持：</span>
                        <span class="packContent">
                            <c:choose>
                                <c:when test="${result.modeHand == 1}">
                                    是
                                </c:when>
                                <c:otherwise>
                                    否
                                </c:otherwise>
                            </c:choose>
                        </span>
                </td>
                <td>
                    <span class="marginRight10 packTit">是否横屏:</span>
                        <span class="packContent">
                            <c:choose>
                                <c:when test="${result.modeHd == 1}">
                                    是
                                </c:when>
                                <c:otherwise>
                                    否
                                </c:otherwise>
                            </c:choose>
                        </span>
                </td>

            </tr>
            <tr>
                <td colspan="2">
                    <span class="marginRight10 packTit ">发布时间：</span>
                        <span class="packContent">
                            <c:if test="${result.publicDate ne null}">
                                <c:out value="${fn:substring(result.publicDate ,0,4)}"/>
                                <c:if test="${fn:length(result.publicDate) ge 6}">
                                    - <c:out value="${fn:substring(result.publicDate ,4,6)}"/>
                                </c:if>
                                <c:if test="${fn:length(result.publicDate) ge 8}">
                                    - <c:out value="${fn:substring(result.publicDate ,6,8)}"/>
                                </c:if>
                                <c:if test="${fn:length(result.publicDate) ge 10}">
                                    &nbsp;&nbsp;<c:out value="${fn:substring(result.publicDate ,8,10)}"/>
                                </c:if>

                            </c:if>
                        </span>
                </td>
                <td>
                    <span class="marginRight10 packTit ">强制更新：</span>
                        <span class="packContent">
                            <c:choose>
                                <c:when test="${result.forceUpdate == 1}">
                                    是
                                </c:when>
                                <c:otherwise>
                                    否
                                </c:otherwise>
                            </c:choose>
                        </span>
                </td>
            </tr>
            <tr>
                <td colspan="3" height="80">
                    <span class="marginRight10 packTit ">更新说明：</span>
                    <span class="packContent">
                        <c:out value="${result.remark}"/>
                    </span>
                </td>
            </tr>
        </table>
        <table class="threeBtn ">
            <tr>
                <td width="50%">
                    <input type="button" class="btn width80 borderRadius backgroundTransparent"
                           onclick="saveOta()" value="确定"/>
                </td>
                <td width="50%">
                    <input type="button" class="btn width80 borderRadius backgroundTransparent"
                           onclick="cancel()" value="取消"/>
                </td>
            </tr>
        </table>
    </div>
</div>
<script>
    $(function () {
        $(".tableDetail tbody tr:even").css("background-color", "#fff");
    });
    var wait_box = $(".wait_box");
    var flag = false;
    function saveOta() {
        wait_box.fadeIn(500);
        if (!flag) {
            flag = true;
            window.location = 'saveOta.do';
        } else {
            console.log("重复上传");
        }
    }

    var cancel1 = false;
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