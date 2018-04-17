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
            <span class="positionText">当前位置:</span><span class="positionText">设备管理</span><span
                class="positionText">></span><a
                href="#" class="positionText">厂家管理</a><span
                class="positionText">></span><a
                href="#" class="positionText">新增厂家</a>
        </div>
        <div class="fromInputBtn">
            <div class="seachEquip marginTop10">
                <form id="saveForm" name="saveForm" action="save.do" method="post">
                    <table class="threeBtn tanTable">
                        <tr>
                            <td width="120" class="tdodd"><label class="marginRight10">厂家编号:</label></td>
                            <td>
                                <div class="Pos_rela">
                                    <input type="text"
                                           class="seachIptText marginRight10 borderRadius"
                                           name="firmCode" id="firmCode"
                                           maxlength="4" onclick="cleanError()"/>
                                    <span class="placeholder" onclick="cleanError()">请输入厂家编号</span>
                                    <div class="errorBox borderRadius" style="width:120px;">
                                        <div class="errorArrow"></div>
                                        <span id="codeErrorMsg"></span>
                                    </div>
                                </div>
                            </td>
                            <td width="120" class="tdodd"><label class="marginRight10">厂家名称:</label></td>
                            <td>
                                <div class="Pos_rela">
                                    <input type="text"
                                           class="seachIptText marginRight10 borderRadius"
                                           name="firmName" id="firmName"
                                           maxlength="60" onclick="cleanError()"/>
                                    <span class="placeholder" onclick="cleanError()">请输入厂家名称</span>
                                    <div class="errorBox borderRadius">
                                        <div class="errorArrow"></div>
                                        <span id="nameErrorMsg"></span>
                                    </div>
                                </div>
                            </td>
                        </tr>
                    </table>
                    <table class="threeBtn ">
                        <tr>
                            <td>
                                <input type="button" class="btn width80 borderRadius backgroundTransparent"
                                       value="保存" onclick="validate_form();"/>
                            </td>
                            <td>
                                <input type="button" class="btn width80 borderRadius backgroundTransparent"
                                       onClick="history.back()" value="返回"/>
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
    var codeErrorMsg = $("#codeErrorMsg");
    var wait_box = $(".wait_box");
    var nameErrorMsg = $("#nameErrorMsg");
    var firmCode = $("#firmCode");
    var firmName = $("#firmName");


    function cleanError() {
        hiddenError(codeErrorMsg);
        hiddenError(nameErrorMsg);
    }

    function showError(target, msg) {
        target.parent(".errorBox").css("display", "block");
        target.text(msg);
    }

    function hiddenError(target) {
        target.parent(".errorBox").css("display", "none");
        target.text("");
    }

    function ajaxValidateFirmCode() {
        var firmCode = $("#firmCode").val();
        $.ajax({
            url: "ajaxValidateFirmCode.do",
            async: true,
            type: "post",
            data: {
                firmCode: firmCode
            },
            dataType: "text",
            cache: false
        }).done(function (data) {
            if (data == "1") {
                showError(codeErrorMsg, "*厂家编号已经存在！");
                isClicked = false;
                wait_box.fadeOut(500);
            } else {
                hiddenError(codeErrorMsg);
                $("#saveForm").submit();
            }
        }).fail(function () {
            alert("*未知错误！");
            isClicked = false;
            wait_box.fadeOut(500);
        });
    }
    function validate_form() {
        wait_box.fadeIn(500);
        if (!isClicked) {
            isClicked = true;
            var firmCodeVal = $.trim(firmCode.val());
            var firmNameVal = $.trim(firmName.val());
            if (firmCodeVal == null || firmCodeVal == "") {
                wait_box.fadeOut(500);
                showError(codeErrorMsg, "*厂家编号不能为空！");
                isClicked = false;
                return;
            }
            var req = /^[A-Z0-9]{4}$/;
            if (!req.test(firmCodeVal)) {
                wait_box.fadeOut(500);
                showError(codeErrorMsg, "*厂家编号是大写字母和数字的组合,共四位！");
                isClicked = false;
                return;
            }
            if (firmNameVal == null || firmNameVal == "") {
                wait_box.fadeOut(500);
                showError(nameErrorMsg, "*厂家名称不能为空！");
                isClicked = false;
                return;
            }
            firmCode.val(firmCodeVal);
            firmName.val(firmNameVal);
            ajaxValidateFirmCode();
        } else {
            console.log("重复点击");
        }
    }
</script>
</body>
</html>