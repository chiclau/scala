<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="mvc" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="./common/base.jsp" %>
    <link href="${pageContext.request.contextPath}/css/DefaultStyle.css" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/css/framePos.css" rel="stylesheet" type="text/css">
    <script src="${pageContext.request.contextPath}/webjars/jquery/1.12.4/jquery.min.js"
            type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/framePos.js" type="text/javascript"></script>
    <title>智能POS管理平台</title>
    <shiro:authenticated>
        <script type="text/javascript">
            $(function () {
                window.location = "${pageContext.request.contextPath}/welcome.do";
            })
        </script>
    </shiro:authenticated>
</head>

<body class="indexCotainer">
<div class="indexBox">
    <div class="indexheader">
        <div class="login_logo floatLeft"><img src="${pageContext.request.contextPath}/images/logo.png"></div>
        <div class="login_titile floatLeft">智能POS管理平台</div>
    </div>
    <div class="indexMain">
        <div class="loginBox">
            <div class="loginPadding">
                <div class="loginTitle">登录</div>
                <div class="loginInput marginTop10">
                    <form action="${ctx}/login.do" method="post">
                        <div class="loginInputBox loginUser">
                            <input type="text" class="logininputText" id="username" name="username"
                                   placeholder="请输入用户名">

                        </div>
                        <div class="loginInputBox loginPassword">
                            <input type="password" class="logininputText" id="password" name="password"
                                   placeholder="请输入密码">

                        </div>
                        <div class="LoginError" id="LoginError">
                        <span id="mess" style="color:red"><c:if
                                test="${shiroLoginFailure == 'org.apache.shiro.authc.AccountException'}">
                            用户名密码不匹配，或用户已停用
                        </c:if> </span>
                        </div>
                        <input type="submit" value="登录" class="loginButton" onClick="return validate_form();">
                    </form>
                </div>
            </div>
        </div>
    </div>
    <div class="copyrightText">© 2016 中国银行</div>
</div>
</body>
<script type="text/javascript">
    var isClicked = false;
    function validate_form() {
        if (isClicked == false) {
            var username = $("#username").val();
            var password = $("#password").val();
            var mess = $("#mess");
            if (username == null || username == "") {
                mess.text("用户名不能为空，请输入！");
                return false;
            }
            if (password == null || password == "") {
                mess.text("密码不能为空，请输入！");
                return false;
            }
            isClicked = true;
            return true;
        } else {
            return false;
        }
    }
</script>
</html>