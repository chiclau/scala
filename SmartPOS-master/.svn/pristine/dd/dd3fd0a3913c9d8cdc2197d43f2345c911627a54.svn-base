<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>智能POS管理平台</title>
    <%@include file="../common/base.jsp" %>
</head>
<body>
<!-- 右侧内容-->
<div class="leftContent Boxshadow marginLeft10 marginTop10">
    <div class="ModelDetail paddingBottom10">
        <div class="CurrentPosition marginBottom10">
            <span class="positionText">当前位置:</span><a href="#" class="positionText">母POS管理</a>
        </div>
        <div class="fromInputBtn">
            <div class="seachEquip marginTop10">
                <form action="list.do" method="get">
                    <table class="threeBtn tanTable">
                        <tr>
                            <td width="120" class="tdodd"><label class="marginRight10">母POS&nbsp;sn号:</label></td>
                            <td>
                                <div class="Pos_rela">
                                    <input type="text" class="seachIptText marginRight10 borderRadius"
                                           name="authPosSn" value="${authDeviceQuery.authPosSn}"/>
                                    <span class="placeholder">请输入母POS sn号</span>
                                    <input type="submit" class="btn width80 borderRadius backgroundTransparent"
                                           value="查询"/>
                                </div>
                            </td>
                        </tr>
                    </table>
                    <table class="threeBtn">
                        <tr>
                            <td><input type="button" class="btn width80 borderRadius backgroundTransparent"
                                       onClick="window.location.href='insert.do'" value="新增"/>
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
                    <th width="40">操作</th>
                    <th width="100">设备序列号</th>
                    <th>公钥</th>
                    <th width="40">状态</th>
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
                    <c:forEach items="${page.content}" var="items">
                        <tr>
                            <td><a href="edit.do?authPosSn=${items.authPosSn}" class="Editor">编辑</a></td>
                            <td><c:out value='${items.authPosSn}'/></td>
                            <td><c:out value='${items.rsaPublicKey}'/></td>
                            <td>
                                <c:if test="${items.status==1}">激活</c:if>
                                <c:if test="${items.status==0}">未激活</c:if>
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
    });
</script>
</body>
</html>