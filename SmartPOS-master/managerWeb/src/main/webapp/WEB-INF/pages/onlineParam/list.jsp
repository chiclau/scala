<%@page language="java" pageEncoding="UTF-8" %>
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
            <span class="positionText">当前位置:</span><a href="#" class="positionText">设备参数维护</a>
        </div>
        <div class="fromInputBtn">
            <div class="seachEquip marginTop10">
                <form action="list.do" method="get">
                    <table class="threeBtn tanTable">
                        <tr>
                            <td width="120" class="tdodd"><label class="marginRight10">参数名称:</label></td>
                            <td>
                                <div class="Pos_rela">
                                    <input type="text" class="seachIptText marginRight10 borderRadius"
                                           name="paramName" value="${onlineParamQuery.paramName}"/>
                                    <span class="placeholder">请输入参数名称</span>
                                </div>
                            </td>
                        </tr>
                    </table>
                    <table class="threeBtn ">
                        <tr>
                            <td>
                                <input type="submit" class="btn width80 borderRadius backgroundTransparent"
                                       value="查询"/>
                            </td>
                            <td>
                                <input type="button" class="btn width80 borderRadius backgroundTransparent"
                                       onclick="window.location.href='create.do?reqParams=${reqParam}'" value="新增"/>
                            </td>
                        </tr>
                    </table>
                </form>
            </div>
        </div>
        <div class="ModuleBOX marginTop10">
            <table class="tableDetail">
                <thead>
                <tr>
                    <th style="width:40px">操作</th>
                    <th>参数名称</th>
                    <th>参数描述</th>
                    <th>参数值</th>
                </tr>
                </thead>
                <tbody>
                <c:if test="${page eq null}">
                    <tr>
                        <td colspan="4">
                            点击查询查询数据
                        </td>
                    </tr>
                </c:if>
                <c:if test="${page ne null and fn:length(page.content) eq 0  }">
                    <tr>
                        <td colspan="4">
                            没有数据
                        </td>
                    </tr>
                </c:if>
                <c:if test="${page ne null and fn:length(page.content) ne 0  }">
                    <c:forEach items="${page.content}" var="items" varStatus="status">
                        <tr>
                            <td>
                                <a href="edit.do?onlineParamId=${items.onlineParamId}&reqParams=${reqParam}"
                                   class="Editor">修改</a>
                            </td>
                            <td><c:out value='${items.paramName}'/></td>
                            <td><c:out value='${items.paramDesc}'/></td>
                            <td><c:out value='${items.paramValue}'/></td>
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