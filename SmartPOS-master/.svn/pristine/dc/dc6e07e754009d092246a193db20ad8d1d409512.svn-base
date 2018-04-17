<%@ page language="java" contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--@elvariable id="page" type="org.springframework.data.domain.Page"--%>
<%--@elvariable id="firstQueryString" type="java.lang.String"--%>
<%--@elvariable id="preQueryString" type="java.lang.String"--%>
<%--@elvariable id="nextQueryString" type="java.lang.String"--%>
<%--@elvariable id="lastQueryString" type="java.lang.String"--%>
<div class="AdjustPage marginTop10">
    <a href="?${firstQueryString}" class="PageBtn" style="margin-right: 10px;">首页</a>
    <a href="?${preQueryString}" class="PageBtn">上一页</a>
    <c:if test="${page eq null}">
        <span class="pageText">第<span class="textZhu">0</span>页/共<span class="textZhu">0</span>
            页&nbsp;&nbsp;总共<span class="textZhu">0</span>条</span>
    </c:if>
    <c:if test="${page ne null}">
        <span class="pageText">第<span class="textZhu">
         <c:if test="${page.totalPages eq 0}">${page.number}</c:if>
        <c:if test="${page.totalPages ne 0}">${page.number + 1}</c:if>
    </span>页/共<span class="textZhu">${page.totalPages}</span>页&nbsp;&nbsp;总共<span
                class="textZhu">${page.totalElements}</span>条</span>
    </c:if>
    <a href="?${nextQueryString}" class="PageBtn" style="margin-right: 10px;">下一页</a>
    <a href="?${lastQueryString}" class="PageBtn">末页</a>
</div>

