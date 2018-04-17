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
                class="positionText">></span><a
                href="#" class="positionText">产品型号</a><span
                class="positionText">></span><a href="#" class="positionText">修改型号</a>
        </div>
        <div class="fromInputBtn">
            <div class="seachEquip marginTop10">
                <form id="updateForm" name="updateForm" action="update.do" method="post">
                    <table class="threeBtn tanTable">
                        <tr>
                            <td width="120" class="tdodd"><label class="marginRight10 marginLeftFont2">产品型号:</label>
                            </td>
                            <td>
                                <div class="Pos_rela"><input
                                        type="text" name="prodCode" readonly class="seachIptText  borderRadius"
                                        value="${products.prodCode}"
                                        style="border:none !important;background-color:#fff;">

                                    <div class="errorBox borderRadius">
                                        <div class="errorArrow"></div>
                                    </div>
                                </div>
                            </td>
                            <td width="120" class="tdodd"><label class="marginRight10 marginLeftFont2">产品名称:</label>
                            </td>
                            <td>
                                <div class="Pos_rela">
                                    <input type="text" class="seachIptText  borderRadius" onclick="cleanError()"
                                           name="prodName" id="prodName" maxlength="30" value="${products.prodName}"/>
                                    <span class="placeholder">请输入产品名称</span>
                                    <div class="errorBox borderRadius">
                                        <div class="errorArrow"></div>
                                        <span id="prodNameErrorMsg"></span>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td width="120" class="tdodd"><label class="marginRight10">模式</label></td>
                            <td>
                                <div class="Pos_rela">
                                    <div class="selectBox">
                                    <span class="seachSelect borderRadius width120">
                                        <c:choose>
                                            <c:when test="${products.deviceMode==1}">HD模式</c:when>
                                            <c:when test="${products.deviceMode==2}">HAND模式</c:when>
                                            <c:otherwise>--请选择---</c:otherwise>
                                        </c:choose>
                                    </span>
                                        <select class="selectHidden" name="deviceMode" id="deviceMode"
                                                onChange="selectChang(this)" onclick="cleanError()">
                                            <option value="">--请选择--</option>
                                            <option value="1"
                                                    <c:if test="${products.deviceMode==1}">selected='selected'</c:if>>
                                                HD模式
                                            </option>
                                            <option value="2"
                                                    <c:if test="${products.deviceMode==2}">selected='selected'</c:if>>
                                                HAND模式
                                            </option>
                                        </select>
                                    </div>
                                    <div class="errorBox borderRadius">
                                        <div class="errorArrow"></div>
                                        <span id="deviceModeErrorMsg"></span>
                                    </div>
                                </div>
                            </td>

                            <td width="120" class="tdodd"><label class="marginRight10 marginLeftFont2">所属厂家编号:</label>
                            </td>
                            <td>
                                ${products.firmCode}<input type="hidden" name="firmCode" value="${products.firmCode}">
                            </td>
                        </tr>
                        <tr>
                            <td width="120" class="tdodd"><label class="marginRight10 marginLeftFont2">所属厂家名称:</label>
                            </td>
                            <td colspan="3">
                                ${products.firmName}<input type="hidden" name="firmName" value="${products.firmName}">
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
    var wait_box = $(".wait_box");
    var prodNameErrorMsg = $("#prodNameErrorMsg");
    var deviceModeErrorMsg = $("#deviceModeErrorMsg");
    var prodName = $("#prodName");
    var deviceMode = $("#deviceMode");

    function cleanError() {
        hiddenError(prodNameErrorMsg);
        hiddenError(deviceModeErrorMsg);
    }

    function showError(target, msg) {
        target.parent(".errorBox").css("display", "block");
        target.text(msg);
    }

    function hiddenError(target) {
        target.parent(".errorBox").css("display", "none");
        target.text("");
    }

    function validate_form() {
        wait_box.fadeIn(500);
        if (!isClicked) {
            isClicked = true;
            var prodNameVal = $.trim(prodName.val());
            var deviceModeVal = $.trim(deviceMode.val());
            if (prodNameVal == null || prodNameVal == "") {
                wait_box.fadeOut(500);
                showError(prodNameErrorMsg, "*产品名称不能为空！");
                isClicked = false;
                return;
            }
            if (deviceModeVal == null || deviceModeVal == "") {
                wait_box.fadeOut(500);
                showError(deviceModeErrorMsg, "*请选择设备模式！");
                isClicked = false;
                return;
            }
            prodName.val(prodNameVal);
            deviceMode.val(deviceModeVal);
            $("#updateForm").submit();
        } else {
            console.log("重复点击");
        }
    }

    $(function () {
        selectChang($("#deviceMode"));
    });
</script>
</body>
</html>