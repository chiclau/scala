<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="mvc" uri="http://www.springframework.org/tags/form" %>
<!doctype html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <%@include file="../common/base.jsp" %>
    <title>智能POS管理平台</title>
</head>
<body>
<!-- 右侧内容-->
<div class="leftContent Boxshadow marginLeft10 marginTop10">
    <div class=" ModelDetail paddingBottom10">
        <div class="CurrentPosition marginBottom10">
            <span class="positionText">当前位置:</span><span class="positionText">设备管理</span><span
                class="positionText">></span><a href="#" class="positionText">产品型号</a><span
                class="positionText">></span><a
                href="#" class="positionText">添加型号</a>
        </div>
        <div class="fromInputBtn">
            <div class="seachEquip marginTop10">
                <form id="saveForm" name="saveForm" action="save.do" method="post">
                    <input type="hidden" name="reqParams" value="${reqParams}"/>
                    <table class="threeBtn tanTable">
                        <tr>
                            <td width="120" class="tdodd"><label class="marginRight10">产品型号:</label></td>
                            <td>
                                <div class="Pos_rela">
                                    <input type="text" class="seachIptText marginRight10 borderRadius" maxlength="12"
                                           onclick="cleanError()"
                                           name="prodCode" id="prodCode"/>
                                    <span class="placeholder" onclick="cleanError()">请输入产品型号</span>
                                    <div class="errorBox borderRadius" style="width:120px;">
                                        <div class="errorArrow"></div>
                                        <span id="prodCodeErrorMsg"></span>
                                    </div>
                                </div>
                            </td>
                            <td width="120" class="tdodd"><label class="marginRight10">产品名称:</label></td>
                            <td>
                                <div class="Pos_rela"><input type="text" maxlength="30"
                                                             class="seachIptText marginRight10 borderRadius"
                                                             name="prodName" onclick="cleanError()"
                                                             id="prodName"/>
                                    <span class="placeholder" onclick="cleanError()">请输入产品名称</span>
                                    <div class="errorBox borderRadius">
                                        <div class="errorArrow"></div>
                                        <span id="prodNameErrorMsg"></span>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td width="120" class="tdodd"><label class="marginRight10">设备模式:</label></td>
                            <td>
                                <div class="Pos_rela">
                                    <div class="selectBox">
                                        <span class="seachSelect borderRadius width120">--请选择---</span>
                                        <select class="selectHidden" onChange="selectChang(this)" name="deviceMode"
                                                onclick="cleanError()"
                                                id="deviceMode">
                                            <option value="">--请选择---</option>
                                            <option value="1">HD模式</option>
                                            <option value="2">HAND模式</option>
                                        </select>
                                    </div>
                                    <div class="errorBox borderRadius">
                                        <div class="errorArrow"></div>
                                        <span id="deviceModeErrorMsg"></span>
                                    </div>
                                </div>
                            </td>

                            <td width="120" class="tdodd"><label class="marginRight10 ">所属厂家编号:</label></td>
                            <td>
                                <div class="Pos_rela">
                                    <div class="selectBox">
                                        <span class="seachSelect borderRadius width120">--请选择---</span>
                                        <select class="selectHidden" name="firmCode" onclick="cleanError()"
                                                onchange="chooseFirmName()" id="firmCode">
                                            <option value="">--请选择---</option>
                                            <c:forEach items="${firmList}" var="items">
                                                <option value="<c:out value='${items.firmCode}'/>"
                                                        id="<c:out value='${items.firmName}'/>">
                                                    <c:out value='${items.firmCode}'/>
                                                </option>
                                            </c:forEach>
                                        </select>
                                    </div>

                                    <div class="errorBox borderRadius">
                                        <div class="errorArrow"></div>
                                        <span id="firmCodeErrorMsg"></span>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td width="120" class="tdodd"><label class="marginRight10">所属厂家名称:</label></td>
                            <td colspan="3">

                                <div class="selectBox">
                                    <span class="seachSelect borderRadius width120">--请选择---</span>
                                    <select class="selectHidden" name="firmName" onclick="cleanError()"
                                            onchange="chooseFirmCode()" id="firmName">
                                        <option value="">--请选择---</option>
                                        <c:forEach items="${firmList}" var="items">
                                            <option value="<c:out value='${items.firmName}'/>"
                                                    id="<c:out value='${items.firmCode}'/>">
                                                <c:out value='${items.firmName}'/>
                                            </option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </td>

                        </tr>
                    </table>
                    <table class="threeBtn ">
                        <tr>
                            <td>
                                <input type="button" class="btn width80 borderRadius backgroundTransparent" value="保存"
                                       onclick="validate_form();"/>
                            </td>
                            <td>
                                <input type="button" class="btn width80 borderRadius backgroundTransparent"
                                       onClick="window.location.href='list.do'" value="取消"/>
                            </td>
                        </tr>
                    </table>
                </form>
            </div>
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

    var isClicked = false;
    var can = false;
    var wait_box = $(".wait_box");
    var firmCodeErrorMsg = $("#firmCodeErrorMsg");
    var firmNameErrorMsg = $("#firmNameErrorMsg");
    var prodNameErrorMsg = $("#prodNameErrorMsg");
    var prodCodeErrorMsg = $("#prodCodeErrorMsg");
    var deviceModeErrorMsg = $("#deviceModeErrorMsg");
    var firmNameOption = $("#firmName");
    var firmCodeOption = $("#firmCode");
    var prodCode = $("#prodCode");
    var prodName = $("#prodName");

    function showError(target, msg) {
        target.parent(".errorBox").css("display", "block");
        target.text(msg);
    }

    function hiddenError(target) {
        target.parent(".errorBox").css("display", "none");
        target.text("");
    }

    function cleanError() {
        hiddenError(firmCodeErrorMsg);
        hiddenError(firmNameErrorMsg);
        hiddenError(prodCodeErrorMsg);
        hiddenError(prodNameErrorMsg);
        hiddenError(deviceModeErrorMsg);
    }

    function validate_form() {
        wait_box.fadeIn(500);
        if (!isClicked) {
            var firmCode = $.trim($("#firmCode").val());
            var firmName = $.trim($("#firmName").val());
            var prodCodeVal = $.trim(prodCode.val());
            var prodNameVal = $.trim(prodName.val());
            var deviceMode = $.trim($("#deviceMode").val());
            isClicked = true;
            if (prodCodeVal == null || prodCodeVal == "") {
                wait_box.fadeOut(500);
                showError(prodCodeErrorMsg, "*产品型号不能为空！");
                isClicked = false;
                return;
            }
            var req = /^[0-9A-Z]+$/;
            if (!req.test(prodCodeVal)) {
                isClicked = false;
                wait_box.fadeOut(500);
                showError(prodCodeErrorMsg, "*产品型号是数字和大写字母的组合！");
                return;
            }
            if (prodNameVal == null || prodNameVal == "") {
                wait_box.fadeOut(500);
                showError(prodNameErrorMsg, "*产品名称不能为空！");
                isClicked = false;
                return;
            }
            if (firmCode == null || firmCode == "") {
                wait_box.fadeOut(500);
                showError(firmCodeErrorMsg, "*厂家编号不能为空！");
                isClicked = false;
                return;
            }
            if (firmName == null || firmName == "") {
                isClicked = false;
                wait_box.fadeOut(500);
                showError(firmNameErrorMsg, "*厂家名称不能为空！");
                return;
            }
            if (deviceMode == null || deviceMode == "") {
                isClicked = false;
                wait_box.fadeOut(500);
                showError(deviceModeErrorMsg, "*请选择设备模式！");
                return;
            }
            prodCode.val(prodCodeVal);
            prodName.val(prodNameVal);
            ajaxValidateDuplicateId();
        } else {
            console.log("重复点击");
        }
    }

    function ajaxValidateDuplicateId() {
        var data = {
            firmCode: $("#firmCode").val(),
            productCode: $("#prodCode").val()
        };
        $.ajax({
            url: "ajaxValidateDuplicateId.do",
            async: true,
            type: "post",
            data: data,
            dataType: "text",
            cache: false
        }).done(function (data) {
            //提示信息
            if (can = (data == "1")) {
                wait_box.fadeOut(500);
                showError(firmCodeErrorMsg, "*同一产品型号、厂商编号系统已存在！");
                showError(prodCodeErrorMsg, "*同一产品型号、厂商编号系统已存在！");
                isClicked = false;
            }
            else {
                hiddenError(prodCodeErrorMsg);
                hiddenError(firmCodeErrorMsg);
                $("#saveForm").submit();
            }
        }).fail(function () {
            wait_box.fadeOut(500);
            alert("*未知错误！");
            isClicked = false;
        })
    }

    /**
     * 根据厂商编号选择厂商名称
     */
    function chooseFirmName() {
        var firmName = firmCodeOption.find("option:selected").attr("id");
        firmNameOption.val(firmName);
        selectChang("#firmCode");
        selectChang("#firmName");
    }
    /**
     * 根据厂商名称选择厂商编号
     */
    function chooseFirmCode() {
        var firmCode = firmNameOption.find("option:selected").attr("id");
        firmCodeOption.val(firmCode);
        selectChang("#firmCode");
        selectChang("#firmName");
    }
</script>
</body>
</html>