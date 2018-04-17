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
            <span class="positionText">当前位置:</span><a href="#" class="positionText">OTA管理</a>
        </div>
        <div class="fromInputBtn">
            <div class="seachEquip marginTop10">
                <form action="list.do" method="get">
                    <table class="threeBtn tanTable">
                        <tr>
                            <td width="120" class="tdodd"><label
                                    class="marginRight10 floatLeft selectLabel">厂商编号:</label></td>
                            <td>
                                <div class="Pos_rela">
                                    <input type="text" class="seachIptText  borderRadius" name="firmCode"
                                           value="${otaHistoryQuery.firmCode}" maxlength="4"/>
                                    <span class="placeholder">请输入厂商编号</span>
                                </div>
                            </td>
                            <td width="120" class="tdodd"><label
                                    class="marginRight10 floatLeft selectLabel">产品型号:</label></td>
                            <td>
                                <div class="Pos_rela">
                                    <input type="text" class="seachIptText  borderRadius" name="prodCode"
                                           value="${otaHistoryQuery.prodCode}" maxlength="12"/>
                                    <span class="placeholder">请输入产品型号</span>
                                </div>
                            </td>
                        </tr>
                    </table>
                    <table class="threeBtn">
                        <tr>
                            <td>
                                <input type="submit" class="btn width80 borderRadius backgroundTransparent" value="查询"/>
                            </td>
                            <td>
                                <input type="button" class="btn width80 borderRadius backgroundTransparent" value="上传"
                                       onclick="window.location='uploadOta.do'"/>
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
                    <th>ota名称</th>
                    <th>版本</th>
                    <th>厂商名称</th>
                    <th>产品名称</th>
                    <th>上传时间</th>
                </tr>
                </thead>
                <tbody>
                <c:if test="${page eq null}">
                    <tr>
                        <td colspan="5">
                            点击查询查询数据
                        </td>
                    </tr>
                </c:if>
                <c:if test="${page ne null and fn:length(page.content) eq 0  }">
                    <tr>
                        <td colspan="5">
                            没有数据
                        </td>
                    </tr>
                </c:if>
                <c:if test="${page ne null and fn:length(page.content) ne 0  }">
                    <c:forEach items="${page.content}" var="items">
                        <tr>
                            <td><c:out value='${items.otaName}'/></td>
                            <td><c:out value='${items.versionName}'/></td>
                            <td><c:out value='${items.firmName}'/></td>
                            <td><c:out value='${items.prodName}'/></td>
                            <td><c:out value='${items.upTime}'/></td>
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