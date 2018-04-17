<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>智能POS管理平台</title
    <%@include file="../common/base.jsp" %>

</head>
<body>
<!-- 右侧内容-->
<div class="leftContent Boxshadow marginLeft10 marginTop10">
    <div class="ModelDetail paddingBottom10">
        <div class="CurrentPosition marginBottom10">
            <span class="positionText">当前位置:</span><a href="#" class="positionText">终端SN管理</a><span
                class="positionText">></span><a href="#" class="positionText">设备绑定</a>
        </div>
        <div class="fromInputBtn">
            <div class="seachEquip marginTop10">
                <form action="boundConfirm.do" method="post">
                    <input type="hidden" name="firmCode" id="firmCode" value="${firmCode}"/>
                    <input type="hidden" name="devSn" id="devSn" value="${devSn}"/>
                    <table class="threeBtn tanTable">
                        <tr>
                            <td width="120" class="tdodd"><label class="marginRight10">商户号:</label></td>
                            <td>
                                <div class="Pos_rela">
                                    <input type="text" name="merNo" id="merNo" onclick="cleanError()"
                                           class="seachIptText marginRight10 borderRadius"
                                           maxlength="15"/>
                                    <span class="placeholder">请输入商户号</span>
                                    <div class="errorBox borderRadius">
                                        <div class="errorArrow"></div>
                                        <span id="merNoMsg"></span></div>
                                </div>
                            </td>
                            <td width="120" class="tdodd"><label class="marginRight10">终端号:</label></td>
                            <td>
                                <div class="Pos_rela">
                                    <input type="text" name="termNo" id="termNo" onclick="cleanError()"
                                           class="seachIptText marginRight10 borderRadius"
                                           maxlength="8"/>
                                    <span class="placeholder">请输入终端号</span>
                                    <div class="errorBox borderRadius">
                                        <div class="errorArrow"></div>
                                        <span id="termNoMsg"></span></div>
                                </div>
                            </td>
                        </tr>

                    </table>
                    <table class="threeBtn ">
                        <tr>
                            <td>
                                <input type="submit" class="btn width80 borderRadius backgroundTransparent"
                                       onClick="return validate_bound()" value="确认"/>
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
<div class="wait_box">
    <div style="position:absolute;left:50%;margin-left:-50px;top:50%;margin-top:-50px; width:100px;">
        <img src="${ctx}/images/loading.gif" style="width:100px;">
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

    var flag = false;
    var merNoMsg = $("#merNoMsg");
    var wait_box = $(".wait_box");
    var termNoMsg = $("#termNoMsg");

    function showError(target, msg) {
        target.parent(".errorBox").css("display", "block");
        target.text(msg);
    }

    function hiddenError(target) {
        target.parent(".errorBox").css("display", "none");
        target.text("");
    }

    function cleanError() {
        hiddenError(merNoMsg);
        hiddenError(termNoMsg);
    }

    function validate_bound() {
        if (!flag) {
            wait_box.fadeIn(500);
            flag = true;
            var merNo = $.trim($("#merNo").val());
            var termNo = $.trim($("#termNo").val());
            if (merNo == null || merNo == "") {
                flag = false;
                wait_box.fadeOut(500);
                showError(merNoMsg, "*请输入商户号！");
                return false;
            }
            if (termNo == null || termNo == "") {
                flag = false;
                wait_box.fadeOut(500);
                showError(termNoMsg, "*请输入终端号！");
                return false;
            }
            termNoMsg.parent(".errorBox").css("display", "none");
            termNoMsg.text("");
            if (!validateIsMayBound(merNo, termNo)) {
                flag = false;
                wait_box.fadeOut(500);
                return false;
            }
            return true;
        } else {
            return false;
        }
    }
    //验证是否可以绑定
    function validateIsMayBound(merNo, termNo) {
        var devSn = $.trim($("#devSn").val());
        var firmCode = $.trim($("#firmCode").val());
        var result = true;
        $.ajax({
            url: "ajaxValidateIsMayBound.do",
            async: false,
            type: "post",
            data: {
                merNo: merNo,
                termNo: termNo,
                firmCode: firmCode,
                devSn: devSn
            },
            dataType: "text",
            cache: false,
            success: function (data) {
                if (data == "0") {
                    result = false;
                    alert("*商户下无此终端, 绑定失败！");
                } else if (data == "2") {
                    result = false;
                    alert("*设备不存在或已绑定，绑定失败！");
                } else if (data == "3") {
                    result = false;
                    alert("*终端号已经绑定，绑定失败！");
                } else if (data == "1") {
                    result = true;
                } else if (data == "4") {
                    result = false;
                    alert("*您所在的机构下不存在该商户，绑定失败！");
                }
            }
        });
        return result;
    }

</script>
</body>
</html>