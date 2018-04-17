<%@ page language="java" pageEncoding="UTF-8" %>
<html>
<head>
    <title>智能POS管理平台</title>
    <%@include file="../common/base.jsp" %>
</head>

<body>
<!-- 右侧内容-->
<div class="leftContent Boxshadow marginLeft10 marginTop10">

    <div class=" ModelDetail paddingBottom10">
        <div class="errorPage ">
            <div class="overflowWidth">
                <img src="../images/error.jpg" class="BoxCenter">
                <div class="errorPageText BoxCenter marginTop10">
                    <c:if test="${msg eq null}">
                        系统繁忙，请稍后再试！
                    </c:if>

                    <c:if test="${msg ne null}">
                        ${msg}
                    </c:if>

                </div>
                <input type="button" class="errorPageBtn BoxCenter marginTop10" onclick="history.back()" value="返回">
            </div>
        </div>
    </div>
</div>

</body>
</html>