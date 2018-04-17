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
            <span class="positionText">当前位置:</span><span class="positionText">设备管理</span><span
                class="positionText">></span><a href="#"
                                                class="positionText">设备明细</a>
        </div>
        <div class="fromInputBtn">
            <div class="seachEquip marginTop10">
                <form id="queryForm" name="queryForm" action="list.do" method="get">
                    <table class="threeBtn tanTable">
                        <tr>
                            <td width="120" class="tdodd"><label class="marginRight10">厂商编号:</label></td>
                            <td>
                                <div class="Pos_rela">
                                    <input type="text" class="seachIptText marginRight10 borderRadius"
                                           name="firmCode" id="firmCode"
                                           value="${deviceQuery.firmCode}" maxlength="4"/>
                                    <span class="placeholder">请输入厂商编号</span>
                                </div>
                            </td>
                            <td width="120" class="tdodd"><label class="marginRight10">产品型号:</label></td>
                            <td>
                                <div class="Pos_rela">
                                    <input type="text" class="seachIptText marginRight10 borderRadius"
                                           name="prodCode" id="prodCode"
                                           value="${deviceQuery.prodCode}" maxlength="12"/>
                                    <span class="placeholder">请输入产品型号</span>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td width="120" class="tdodd"><label class="marginRight10">商户号:</label></td>
                            <td>
                                <div class="Pos_rela">
                                    <input type="text" class="seachIptText marginRight10 borderRadius"
                                           name="merNo" value="${deviceQuery.merNo}" maxlength="15"/>
                                    <span class="placeholder">请输入商户号</span>
                                </div>
                            </td>
                            <td width="120" class="tdodd"><label class="marginRight10">终端号:</label></td>
                            <td>
                                <div class="Pos_rela">
                                    <input type="text" class="seachIptText marginRight10 borderRadius"
                                           name="termNo" value="${deviceQuery.termNo}" maxlength="8"/>
                                    <span class="placeholder">请输入终端号</span>
                                </div>
                            </td>
                        </tr>
                    </table>
                    <table class="threeBtn ">
                        <tr>
                            <td>
                                <input type="submit" class="btn width80 borderRadius backgroundTransparent" value="查询"/>
                            </td>
                            <td>
                                <input type="button" class="btn width80 borderRadius backgroundTransparent"
                                       onClick="window.location='upload.do'" value="上传"/>
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
                                <c:choose>
                                    <c:when test="${items.status eq 0 and items.isActive eq 0}">
                                        <a onclick="deleteDevice('${items.devSn}', '${items.firmCode}');"
                                           class="Editor">删除</a>
                                    </c:when>
                                </c:choose>
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
                            <td>
                                <c:out value="${items.deviceFlag}"/>
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
<div class="blackTanZZ" id="blackTanZZ">
    <div class="blackZZ"></div>
    <div class="blackContentZZ">
        <span class="blackTitleZZ">提示</span>
        <div class="blackTextZZ">确定要删除此信息吗?</div>
        <input type="button" class="btnTanZZ btnTanGrayZZ" value="取消" onclick="cancel()">
        <input type="button" class="btnTanZZ btnTanZhuZZ" value="确定" onclick="ensure()">
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

    var devSn1;
    var firmCode1;
    var blackTanZZ = $("#blackTanZZ");

    /**
     * 删除设备
     * @param devSn 设备SN
     * @param firmCode 厂商编号
     */
    function deleteDevice(devSn, firmCode) {
        $.ajax({
            url: "ajaxValidateDelete.do",
            async: true,
            type: "post",
            data: {
                firmCode: firmCode,
                devSn: devSn
            },
            dataType: "text",
            cache: false
        }).done(function (data) {
            if (data == "1") {
                alert("设备已激活无法删除！");
            } else if (data == "2") {
                alert("设备已绑定无法删除！");
            } else {
                blackTanZZ.fadeIn(50);
                devSn1 = devSn;
                firmCode1 = firmCode;
            }
        }).fail(function () {
            alert("*未知错误！");
        });
    }
    //确认
    function ensure() {
        blackTanZZ.fadeOut(50);
        window.location = "delete.do?devSn=" + devSn1 + "&firmCode=" + firmCode1;
    }
    //取消
    function cancel() {
        blackTanZZ.fadeOut(50);
    }
</script>

</body>
</html>