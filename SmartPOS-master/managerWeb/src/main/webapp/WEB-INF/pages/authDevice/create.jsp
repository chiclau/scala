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
            <span class="positionText">当前位置:</span><a href="#" class="positionText">母POS管理</a><span
                class="positionText">></span><a href="#" class="positionText">新增</a>
        </div>
        <div class="fromInputBtn">
            <div class="seachEquip marginTop10">
                <form id="saveForm" name="saveForm" action="saveAndUpdate.do" method="post"
                      enctype="multipart/form-data">
                    <table class="threeBtn tanTable">
                        <tr>
                            <td width="120" class="tdodd">
                                <label class="marginRight10 selectLabel">母POS编号:</label></td>
                            <td>
                                <div class="Pos_rela"><input type="text" name="authPosSn" maxlength="32"
                                                             id="authPosSn" onclick="cleanError()"
                                                             class="seachIptText marginRight10 borderRadius"
                                />
                                    <span class="placeholder" onclick="cleanError()">请输入母POS编号</span>
                                    <div class="errorBox borderRadius" style="width:120px;">
                                        <div class="errorArrow"></div>
                                        <span id="authPosSnErrorMsg"></span>
                                    </div>
                                </div>
                            </td>
                            <td width="120" class="tdodd">
                                <label class="marginRight10">上传公钥:</label></td>
                            <td>
                                <div class="Pos_rela">
                                    <div class="floatLeft">
                                    <span class="inputFileBox">
                                        <div class="Pos_rela">
                                            <input type="text" class="seachIptText width150 marginRight10 borderRadius"
                                                   onclick="cleanError()"
                                                   name="test" id="test" readonly style="background-color:#eee;"/>
                                            <span class="placeholderFile">未选择文件</span>
                                            <input type="file" name="p12File" id="p12File" onclick="cleanError()"
                                                   onChange="if(this.value)insertTitle(this.value); "
                                                   style="display:none;">
                                            <a href="javascript:" class="file" id="file"
                                               onclick="$('input[name=p12File]').click();"> 浏览文件</a></div>
                                    </span>
                                    </div>
                                    <div class="errorBox borderRadius" style="width:120px;">
                                        <div class="errorArrow"></div>
                                        <span id="p12FileMag"></span>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td width="120" class="tdodd">
                                <label class="marginRight10 selectLabel">证书密码:</label></td>
                            <td colspan="3">
                                <div class="Pos_rela"><input type="password" name="pwd"
                                                             id="pwd" onclick="cleanError()"
                                                             class="seachIptText marginRight10 borderRadius"
                                                             onblur="if ($(this).val() =='') {
                                    $(this).next('.placeholder').show();}"
                                                             onfocus="$(this).next('.placeholder').hide();"/>
                                    <span class="placeholder"
                                          onclick="$(this).hide();$(this).prev('.seachIptText').focus();"
                                          onblur="cleanError()" style="position:absolute;left:6px;top:7px;color:#999;">请输入密码</span>
                                    <div class="errorBox borderRadius" style="width:120px;">
                                        <div class="errorArrow"></div>
                                        <span id="pwdMsg"></span>
                                    </div>
                                </div>
                            </td>
                        </tr>
                    </table>
                    <table class="threeBtn ">
                        <tr>
                            <td>
                                <input type="button" class="btn width80 borderRadius backgroundTransparent"
                                       onclick="validate_submit()" value="确认"/>
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
    $(function () {
        if (testElement.val() == "" || testElement.val() == null) {
            testElement.next(".placeholderFile").show();
        } else {
            testElement.next(".placeholderFile").hide();
        }
    });
    var testElement = $("#test");
    function insertTitle(path) {
        var test1 = path.lastIndexOf("/");  //对路径进行截取
        var test2 = path.lastIndexOf("\\");  //对路径进行截取
        var test = Math.max(test1, test2);
        if (test < 0) {
            document.getElementById("test").value = path;
        } else {
            document.getElementById("test").value = path.substring(test + 1); //赋值文件名
        }
        if (testElement.val() == "" || testElement.val() == null) {
            testElement.next(".placeholderFile").show();
        } else {
            testElement.next(".placeholderFile").hide();
        }
    }

    var authPosSnErrorMsg = $("#authPosSnErrorMsg");
    var wait_box = $(".wait_box");
    var p12FileMag = $("#p12FileMag");
    var pwdMsg = $("#pwdMsg");
    var authPosSn = $("#authPosSn");
    var p12File = $("#p12File");
    var flag = false;

    function showError(target, msg) {
        target.parent(".errorBox").css("display", "block");
        target.text(msg);
    }

    function hiddenError(target) {
        target.parent(".errorBox").css("display", "none");
        target.text("");
    }

    function cleanError() {
        hiddenError(authPosSnErrorMsg);
        hiddenError(p12FileMag);
        hiddenError(pwdMsg);
    }

    function validate_submit() {
        wait_box.fadeIn(500);
        if (!flag) {
            flag = true;
            var authPosSnVal = $.trim(authPosSn.val());
            if (authPosSnVal == null || authPosSnVal == "") {
                flag = false;
                wait_box.fadeOut(500);
                showError(authPosSnErrorMsg, "*请输入母pos编号！");
                return;
            }
            var req = /^[0-9a-zA-Z]+$/;
            if (!req.test(authPosSnVal)) {
                flag = false;
                wait_box.fadeOut(500);
                showError(authPosSnErrorMsg, "*母pos编号格式不正确，数字和字母的组合！");
                return;
            }
            var p12FileVal = $.trim(p12File.val());
            if (p12FileVal == null || p12FileVal == "") {
                flag = false;
                wait_box.fadeOut(500);
                showError(p12FileMag, "*请选择文件！");
                return;
            }
            var fileName = "";
            if (p12FileVal.lastIndexOf("/") > -1) {
                fileName = p12FileVal.substr(p12FileVal.lastIndexOf("/"));
            } else {
                fileName = p12FileVal;
            }
            var name = fileName.substr(fileName.lastIndexOf("."), 4);
            if (name != ".p12" && name != ".pfx") {
                flag = false;
                wait_box.fadeOut(500);
                showError(p12FileMag, "*您选择的文件格式不正确，不能上传！");
                return;
            }
            var pwd = $.trim($("#pwd").val());
            if (pwd == null || pwd == "") {
                flag = false;
                wait_box.fadeOut(500);
                showError(pwdMsg, "*请输入密码！");
                return;
            }
            authPosSn.val(authPosSnVal);
            validate_authDeviceSn();
        }
    }

    //验证sn不重复
    function validate_authDeviceSn() {
        var authPosSnVal = $.trim(authPosSn.val());
        $.ajax({
            url: "ajax_validateSn.do",
            data: {
                authPosSn: authPosSnVal
            },
            type: "get",
            cache: false,
            dataType: "text",
            async: true
        }).done(function (result) {
            if (result == "false") {
                wait_box.fadeOut(500);
                flag = false;
                showError(authPosSnErrorMsg, "*母pos编号已经存在！");
            } else {
                testElement.val("");
                $("#saveForm").submit();
            }
        }).fail(function () {
            alert("*未知错误！");
            flag = false;
            wait_box.fadeOut(500);
        });
    }
</script>
</body>
</html>