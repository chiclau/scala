<%@ page language="java" pageEncoding="UTF-8" %>
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
            <span class="positionText">当前位置:</span><span class="positionText">设备参数维护</span><span
                class="positionText">></span><a href="#" class="positionText">设备参数新增</a>
        </div>
        <div class="fromInputBtn">
            <div class="seachEquip marginTop10">
                <form id="updateForm" name="updateForm" action="save.do" method="post">
                    <table class="threeBtn tanTable">
                        <input type="hidden" name="reqParams" value="${reqParams}"/>
                        <tr>
                            <td width="120" class="tdodd"><label class="marginRight10">参数名称:</label></td>
                            <td>
                                <div class="Pos_rela">
                                    <input type="text"
                                           class="seachIptText marginRight10 borderRadius"
                                           name="paramName" maxlength="32" onclick="cleanError()"
                                           id="paramName"/>
                                    <span class="placeholder" onclick="cleanError()">请输入参数名称</span>
                                    <div class="errorBox borderRadius">
                                        <div class="errorArrow"></div>
                                        <span id="nameErrorMsg"></span>
                                    </div>
                                </div>
                            </td>

                            <td width="120" class="tdodd"><label class="marginRight10">参数描述:</label></td>
                            <td>
                                <div class="Pos_rela">
                                    <input type="text" class="seachIptText marginRight10 borderRadius" name="paramDesc"
                                           maxlength="127" onclick="cleanError()"
                                           id="paramDesc"/>
                                    <span class="placeholder" onclick="cleanError()">请输入参数描述</span>
                                    <div class="errorBox borderRadius">
                                        <div class="errorArrow"></div>
                                        <span id="descErrorMsg"></span>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td width="120" class="tdodd"><label class="marginRight10">参数值:</label></td>
                            <td colspan="3">
                                <div class="Pos_rela">
                                    <input type="text" class="seachIptText marginRight10 borderRadius" maxlength="127"
                                           onclick="cleanError()"
                                           name="paramValue" id="paramValue"/>
                                    <span class="placeholder" onclick="cleanError()">请输入参数值</span>
                                    <div class="errorBox borderRadius">
                                        <div class="errorArrow"></div>
                                        <span id="valueErrorMsg"></span>
                                    </div>
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
                                       onClick="history.back()" value="取消"/>
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
    var nameErrorMsg = $("#nameErrorMsg");
    var valueErrorMsg = $("#valueErrorMsg");
    var descErrorMsg = $("#descErrorMsg");
    var paramName = $("#paramName");
    var paramDesc = $("#paramDesc");
    var paramValue = $("#paramValue");

    function showError(target, msg) {
        target.parent(".errorBox").css("display", "block");
        target.text(msg);
    }

    function hiddenError(target) {
        target.parent(".errorBox").css("display", "none");
        target.text("");
    }

    function cleanError() {
        hiddenError(nameErrorMsg);
        hiddenError(descErrorMsg);
        hiddenError(valueErrorMsg);
    }

    function ajaxValidateParamName() {
        var paramNameVal = $.trim(paramName.val());
        $.ajax({
            url: "ajaxValidateParamName.do",
            async: true,
            type: "post",
            data: {
                paramName: paramNameVal
            },
            dataType: "text",
            cache: false
        }).done(function (data) {
            if (data == "1") {
                wait_box.fadeOut(500);
                showError(nameErrorMsg, "*参数名称已经存在！");
                isClicked = false;
            } else {
                hiddenError(nameErrorMsg);
                $("#updateForm").submit();
            }
        }).fail(function () {
            wait_box.fadeOut(500);
            alert("*未知错误！");
            isClicked = false;
        });
    }

    function validate_form() {
        wait_box.fadeIn(500);
        if (!isClicked) {
            isClicked = true;
            var paramNameVal = $.trim(paramName.val());
            var paramDescVal = $.trim(paramDesc.val());
            var paramValueVal = $.trim(paramValue.val());
            if (paramNameVal == null || paramNameVal == "") {
                wait_box.fadeOut(500);
                showError(nameErrorMsg, "*参数名称不能为空！");
                isClicked = false;
                return;
            }
            if (paramDescVal == null || paramDescVal == "") {
                wait_box.fadeOut(500);
                showError(descErrorMsg, "*参数描述不能为空！");
                isClicked = false;
                return;
            }
            if (paramValueVal == null || paramValueVal == "") {
                wait_box.fadeOut(500);
                showError(valueErrorMsg, "*参数值不能为空！");
                isClicked = false;
                return;
            }
            paramName.val(paramNameVal);
            paramDesc.val(paramDescVal);
            paramValue.val(paramValueVal);
            ajaxValidateParamName();
        } else {
            console.log("重复点击");
        }
    }
</script>
</body>
</html>