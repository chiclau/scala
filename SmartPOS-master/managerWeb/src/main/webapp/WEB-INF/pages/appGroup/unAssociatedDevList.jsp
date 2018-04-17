<%@ page language="java" pageEncoding="UTF-8" %>

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
            <span class="positionText">当前位置:</span><a href="#" class="positionText">应用分组:</a><a href="#"
                                                                                                class="positionText">取消关联设备</a>
        </div>
        <div class="fromInputBtn">
            <div class="seachEquip marginTop10">
                <form action="unAssociatedDevList.do" method="get">
                    <input type="hidden" name="appGroupId" value="${appGroupId}"/>
                    <table class="threeBtn tanTable">
                        <tr>
                            <td width="120" class="tdodd"><label class="marginRight10">厂商编号:</label></td>
                            <td>
                                <div class="Pos_rela">
                                    <input type="text" class="seachIptText marginRight10 borderRadius"
                                           onclick="cleanError()"
                                           name="firmCode" id="firmCode"
                                           value="${unAssociatedVAppGroupDeviceQuery.firmCode}"/>
                                    <span class="placeholder">请输入厂商编号</span>
                                    <div class="errorBox borderRadius">
                                        <div class="errorArrow"></div>
                                        <span id="firmCodeMsg"></span></div>
                                </div>
                            </td>
                            <td width="120" class="tdodd"><label class="marginRight10">产品编号:</label></td>
                            <td>
                                <div class="Pos_rela">
                                    <input type="text" class="seachIptText marginRight10 borderRadius"
                                           onclick="cleanError()"
                                           name="prodCode" id="prodCode"
                                           value="${unAssociatedVAppGroupDeviceQuery.prodCode}"/>
                                    <span class="placeholder">请输入产品编号</span>
                                    <div class="errorBox borderRadius">
                                        <div class="errorArrow"></div>
                                        <span id="prodCodeMsg"></span></div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td width="120" class="tdodd"><label class="marginRight10">商户号:</label></td>
                            <td>
                                <div class="Pos_rela">
                                    <input type="text" class="seachIptText marginRight10 borderRadius"
                                           onclick="cleanError()"
                                           name="merNo" value="${unAssociatedVAppGroupDeviceQuery.merNo}"/>
                                    <span class="placeholder">请输入商户号</span>
                                </div>
                            </td>
                            <td width="120" class="tdodd"><label class="marginRight10">终端号:</label></td>
                            <td>
                                <div class="Pos_rela">
                                    <input type="text" class="seachIptText marginRight10 borderRadius"
                                           onclick="cleanError()"
                                           name="termNo" value="${unAssociatedVAppGroupDeviceQuery.termNo}"/>
                                    <span class="placeholder">请输入终端号</span>
                                </div>
                            </td>
                        </tr>
                    </table>
                    <table class="threeBtn ">
                        <tr>
                            <td>
                                <input type="submit" class="btn width80 borderRadius backgroundTransparent "
                                       value="查询" onclick="return validate_query()"/>
                            </td>
                            <td>
                                <input type="button" class="btn width80 borderRadius backgroundTransparent "
                                       onclick="allSelect()" value="取消关联"/>
                            </td>
                            <td>
                                <input type="button" class="btn width80 borderRadius backgroundTransparent "
                                       onclick="history.back()" value="返回"/>
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
                    <th id="th"></th>
                    <th>厂商编号</th>
                    <th>设备SN号</th>
                </tr>
                </thead>
                <tbody>
                <c:if test="${page eq null or fn:length(page.content) eq 0  }">
                    <tr>
                        <td colspan="3">
                            没有数据
                        </td>
                    </tr>
                </c:if>
                <c:if test="${page ne null and fn:length(page.content) ne 0  }">
                    <c:forEach items="${page.content}" var="item" varStatus="status">
                        <tr>
                            <td><input type="checkbox" name="firmCodeAndDevSns"
                                       value="${item.firmCode}~${item.devSn}"></td>
                            <td><c:out value="${item.firmCode}"/></td>
                            <td><c:out value="${item.devSn}"/></td>
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
        var th = $("#th");
        var firmCodeAndDevSns = $("input[name=firmCodeAndDevSns]");
        if (firmCodeAndDevSns.length != 0) {
            th.html("<input type='checkbox' id='checkA' onclick='checkAll();'/>");
        } else {
            th.html("");
        }
    });
    //设置全选
    function checkAll() {
        var checkA = $("#checkA");
        var firmCodeAndDevSns = $("input[name=firmCodeAndDevSns]");
        for (var i = 0; i < firmCodeAndDevSns.length; i++) {
            firmCodeAndDevSns.get(i).checked = checkA.get(0).checked;
        }
    }

    /**
     * 全部取消关联应用
     */
    var flag = false;
    function allSelect() {
        if (!flag) {
            flag = true;
            var firmCodeAndDevSns = $("input[name='firmCodeAndDevSns']:checked");
            var length = firmCodeAndDevSns.length;
            if (firmCodeAndDevSns == null || length == 0) {
                alert("目前还没有关联设备，不能取消关联！");
                flag = false;
                return;
            }
            var firmCodeAndDevSn = "";
            for (var i = 0; i < length; i++) {
                if (firmCodeAndDevSns.get(i).checked) {
                    if (i != length - 1) {
                        firmCodeAndDevSn += firmCodeAndDevSns[i].value + ",";
                    } else {
                        firmCodeAndDevSn += firmCodeAndDevSns[i].value;
                    }
                }
            }
            if (firmCodeAndDevSn == "") {
                alert("请选择设备！");
                flag = false;
                return;
            }
            window.location = "AllUnAssociatedDev.do?firmCodeAndDevSns=" + firmCodeAndDevSn + "&appGroupId=${appGroupId}";

        }

    }

    var firmCodeMsg = $("#firmCodeMsg");
    var prodCodeMsg = $("#prodCodeMsg");

    function cleanError() {
        hiddenError(firmCodeMsg);
        hiddenError(prodCodeMsg);
    }

    function showError(target, msg) {
        target.parent(".errorBox").css("display", "block");
        target.text(msg);
    }

    function hiddenError(target) {
        target.parent(".errorBox").css("display", "none");
        target.text("");
    }

    function validate_query() {
        var firmCode = $.trim($("#firmCode").val());
        var prodCode = $.trim($("#prodCode").val());
        if (firmCode == null || firmCode == "") {
            showError(firmCodeMsg, "*请输入厂商编号！");
            return false;
        }
        var req = /^[A-Z0-9]{4}$/;
        if (!req.test(firmCode)) {
            showError(firmCodeMsg, "*厂家编号格式不正确！");
            return false;
        }
        if (prodCode == null || prodCode == "") {
            showError(prodCodeMsg, "*请输入产品编号！");
            return false;
        }
        return true;
    }
</script>
</body>
</html>