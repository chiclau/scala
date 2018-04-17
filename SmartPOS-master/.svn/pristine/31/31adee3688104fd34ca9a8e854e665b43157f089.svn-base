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
            <span class="positionText">当前位置:</span><span class="positionText">设备管理</span><span
                class="positionText">></span><span class="positionText">厂家管理</span><span
                class="positionText">></span><a
                href="#" class="positionText">编辑厂家</a>
        </div>
        <div class="fromInputBtn">
            <div class="seachEquip marginTop10">
                <form id="updateForm" name="updateForm" action="update.do" method="post">
                    <input type="hidden" value="${firm.firmCode}" name="firmCode">
                    <table class="threeBtn tanTable">
                        <tr>
                            <td width="120" class="tdodd"><label class="marginRight10">厂家编号:</label></td>
                            <td>
                                <div class="Pos_rela">
                                    <input type="text"
                                           class="seachIptText marginRight10 borderRadius"
                                           readonly
                                           maxlength="4"
                                           value="${firm.firmCode}" style="background-color:transparent;border:none;"/>

                                </div>
                            </td>

                            <td width="120" class="tdodd"><label class="marginRight10">厂家名称:</label></td>
                            <td>
                                <div class="Pos_rela">
                                    <input type="text"
                                           class="seachIptText marginRight10 borderRadius"
                                           name="firmName" value="${firm.firmName}"
                                           id="firmName" maxlength="60" onclick="cleanError()"/>
                                    <span class="placeholder" onclick="cleanError()">请输入厂家名称</span>

                                    <div class="errorBox borderRadius" style="width: 120px;">
                                        <div class="errorArrow"></div>
                                        <span id="nameMsg"></span>
                                    </div>
                                </div>
                            </td>
                        </tr>
                    </table>
                    <table class="threeBtn ">
                        <tr>
                            <td>
                                <input type="submit" class="btn width80 borderRadius backgroundTransparent" value="保存"
                                       onclick="return validate_form()"/>
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
    var nameMsg = $("#nameMsg");
    var wait_box = $(".wait_box");
    var firmName = $("#firmName");

    function cleanError() {
        hiddenError(nameMsg);
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
            var firmNameVal = $.trim(firmName.val());
            if (firmNameVal == null || firmNameVal == "") {
                wait_box.fadeOut(500);
                showError(nameMsg, "*厂家名称不能为空！");
                return false;
            }
            firmName.val(firmNameVal);
            isClicked = true;
            return true;
        } else {
            return false;
        }
    }
</script>
</body>
</html>