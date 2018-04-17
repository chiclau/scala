<%@ page language="java" pageEncoding="UTF-8" %>
<html>
<head>
    <title>智能POS管理平台</title>
    <%@include file="../common/base.jsp" %>

</head>

<body>
<!-- 右侧内容-->
<div class="leftContent Boxshadow marginLeft10 marginTop10">
    <div class=" ModelDetail paddingBottom10">
        <div class="CurrentPosition marginBottom10">
            <span class="positionText">当前位置:</span><a href="#" class="positionText">应用分组</a><span
                class="positionText">></span><a href="#" class="positionText">编辑分组</a>
        </div>
        <div class="fromInputBtn">
            <div class="seachEquip marginTop10">
                <form action="update.do" method="post">
                    <input type="hidden" name="appGroupId" value="${appGroup.appGroupId}"/>
                    <input type="hidden" name="isDefaultGroup" value="${appGroup.isDefaultGroup}"/>
                    <input type="hidden" name="orgId" value="${appGroup.orgId}"/>
                    <input type="hidden" name="orgType" value="${appGroup.orgType}"/>
                    <table class="threeBtn tanTable">
                        <tr>
                            <td width="120" class="tdodd"><label class="marginRight10 selectLabel">分组名称:</label>
                            </td>
                            <td>
                                <div class="Pos_rela">
                                    <input type="text" onclick="cleanError()"
                                           name="appGroupName" id="appGroupName"
                                           class="seachIptText marginRight10 borderRadius "
                                           maxlength="30"
                                           onblur="validate_appGroupName()" value="${appGroup.appGroupName}"/>
                                    <span class="placeholder" onclick="cleanError()">请输入分组名称</span>
                                    <div class="errorBox borderRadius">
                                        <div class="errorArrow"></div>
                                        <span id="errorMsg"></span></div>
                                </div>
                            </td>
                            <td width="120" class="tdodd"><label class="marginRight10 selectLabel">分组描述:</label></td>
                            <td>
                                <div class="Pos_rela">
                                    <input type="text" onclick="cleanError()"
                                           name="appGroupDesc" maxlength="127" id="appGroupDesc"
                                           class="seachIptText marginRight10 borderRadius "
                                           value="${appGroup.appGroupDesc}"/>
                                    <span class="placeholder" onclick="cleanError()">请输入分组描述</span>
                                </div>
                            </td>

                        </tr>
                    </table>
                    <table class="threeBtn">
                        <tr>
                            <td>
                                <input type="submit" class="btn width80 borderRadius backgroundTransparent"
                                       onClick="return validate_submit()" value="提交"/>
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

    var errorMsg = $("#errorMsg");
    var appGroupName = $("#appGroupName");
    var appGroupDesc = $("#appGroupDesc");
    var wait_box = $(".wait_box");

    function cleanError() {
        errorMsg.parent(".errorBox").css("display", "none");
        errorMsg.text("");
    }

    //验证应用分组名称是否重复
    function validate_appGroupName() {
        var appGroupNameVal = $.trim(appGroupName.val());
        if (appGroupNameVal == null || appGroupNameVal == "") {
            errorMsg.parent(".errorBox").css("display", "block");
            errorMsg.text("*请输入应用分组名称！");
            return false;
        }
        $.ajax({
            url: "ajax_validateAppGroupName",
            data: {
                appGroupName: appGroupNameVal
            },
            type: "get",
            cache: false,
            dataType: "json",
            async: true,
            success: function (result) {
                if (result == "false") {
                    errorMsg.parent(".errorBox").css("display", "block");
                    errorMsg.text("*应用分组名称已经存在！");
                }
            }
        })
    }

    var flag = false;
    //表达提交验证
    function validate_submit() {
        wait_box.fadeIn(500);
        var errorMsg = $("#errorMsg");
        if (!flag) {
            flag = true;
            var appGroupNameVal = $.trim(appGroupName.val());

            if (appGroupNameVal == null || appGroupNameVal == "") {
                errorMsg.parent(".errorBox").css("display", "block");
                errorMsg.text("*请输入应用分组名称！");
                flag = false;
                wait_box.fadeOut(500);
                return false
            }
            appGroupName.val(appGroupNameVal);
            appGroupDesc.val($.trim(appGroupDesc.val()));
            return true
        }
    }
</script>
</body>
</html>