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
            <span class="positionText">当前位置:</span><a href="#" class="positionText">终端SN管理</a>
        </div>
        <div class="fromInputBtn">
            <div class="seachEquip marginTop10">
                <form id="queryForm" name="queryForm" action="list.do" method="get">
                    <table class="threeBtn tanTable ">
                        <tr>
                            <td width="120" class="tdodd"><label class="marginRight10">厂商编号:</label></td>
                            <td>
                                <div class="Pos_rela">
                                    <input type="text" class="seachIptText marginRight10 borderRadius" name="firmCode"
                                           value="${deviceQuery.firmCode}" maxlength="4"/>
                                    <span class="placeholder">请输入厂商编号</span>
                                </div>
                            </td>
                            <td width="120" class="tdodd"><label class="marginRight10">设备SN:</label></td>
                            <td>
                                <div class="Pos_rela">
                                    <input type="text" class="seachIptText marginRight10 borderRadius" name="devSn"
                                           value="${deviceQuery.devSn}" maxlength="32"/>
                                    <span class="placeholder">请输入SN</span>
                                </div>
                            </td>
                        </tr>
                    </table>
                    <table class="threeBtn ">
                        <tr>
                            <td>
                                <input type="submit" class="btn width80 borderRadius backgroundTransparent" value="查询"/>
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
                    <th width="80">操作</th>
                    <th>设备SN</th>
                    <th>厂商编号</th>
                    <th>厂商名称</th>
                    <th>产品型号</th>
                    <th>产品型号名称</th>
                    <th>是否激活</th>
                    <th>商户号</th>
                    <th>终端号</th>
                    <th>设备状态</th>
                    <th>硬件识别码</th>
                </tr>
                </thead>
                <tbody>
                <c:if test="${page eq null}">
                    <tr>
                        <td colspan="11">
                            点击查询查询数据
                        </td>
                    </tr>
                </c:if>
                <c:if test="${page ne null and fn:length(page.content) eq 0  }">
                    <tr>
                        <td colspan="11">
                            没有数据
                        </td>
                    </tr>
                </c:if>
                <c:if test="${page ne null and fn:length(page.content) ne 0  }">
                    <c:forEach items="${page.content}" var="items">
                        <tr>
                            <td>
                                <c:if test="${items.status eq 0}">
                                    <a href="bound.do?devSn=${items.devSn}&firmCode=${items.firmCode}"
                                       class="Editor">绑定</a>
                                </c:if>
                                <c:if test="${items.status eq 1}">
                                    <a class="Editor"
                                       onclick="removeBound('${items.devSn}', '${items.firmCode}')">取消绑定</a>
                                </c:if>
                            </td>
                            <td><c:out value='${items.devSn}'/></td>
                            <td><c:out value='${items.firmCode}'/></td>
                            <td><c:out value='${items.firmName}'/></td>
                            <td><c:out value='${items.prodCode}'/></td>
                            <td><c:out value='${items.prodName}'/></td>
                            <td width="70">
                                <c:choose>
                                    <c:when test="${items.isActive eq 1}">
                                        激活
                                    </c:when>
                                    <c:otherwise>未激活</c:otherwise>
                                </c:choose>
                            </td>
                            <td><c:out value='${items.merNo}'/></td>
                            <td><c:out value='${items.termNo}'/></td>
                            <td width="70">
                                <c:choose>
                                    <c:when test="${items.status eq 1}">
                                        绑定
                                    </c:when>
                                    <c:otherwise>未绑定</c:otherwise>
                                </c:choose>
                            </td>
                            <td><c:out value='${items.deviceFlag}'/></td>
                        </tr>
                    </c:forEach>
                </c:if>
                </tbody>
            </table>
            <%@include file="../common/page.jsp" %>
        </div>
    </div>
</div>
<script type="text/javascript">
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
    function removeBound(devSn, firmCode) {
        if (confirm("是否确认取消绑定!")) {
            window.location = "removeBound.do?devSn=" + devSn + "&firmCode=" + firmCode;
        }
    }
</script>
</body>
</html>