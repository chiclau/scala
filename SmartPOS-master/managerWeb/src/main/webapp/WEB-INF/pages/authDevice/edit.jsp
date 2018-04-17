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
                class="positionText">></span><a href="#" class="positionText">编辑</a>
        </div>
        <div class="fromInputBtn">
            <div class="seachEquip marginTop10">
                <form action="saveAndUpdate.do" method="post" enctype="multipart/form-data">
                    <table class="threeBtn tanTable">
                        <tr>
                            <td width="120" class="tdodd">
                                <label class="marginRight10 selectLabel">母POS编号:</label></td>
                            <td>
                                <div class="Pos_rela">
                                    <input type="text" name="authPosSn" onclick="cleanError()"
                                           id="authPosSn" readonly
                                           class="seachIptText marginRight10 borderRadius seachSelectTransparent"
                                           value="${model.authPosSn}" style="width:240px;"/>
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
                                                   name="test" id="test" readonly
                                                   style="background-color:#eee;"/>
                                        <span class="placeholderFile"
                                        >重新选择文件</span>
                                            <input type="file" name="p12File" id="p12File" onclick="cleanError()"
                                                   onChange="if(this.value)insertTitle(this.value); "
                                                   style="display:none;">
                                            <a href="javascript:" class="file" id="file"
                                               onclick="$('input[name=p12File]').click();"> 浏览文件</a>
                                        </div>
                                    </span>
                                    </div>
                                    <div class="errorBox borderRadius">
                                        <div class="errorArrow"></div>
                                        <span id="p12FileMag"></span></div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td width="120" class="tdodd">
                                <label class="marginRight10 selectLabel">证书密码:</label></td>
                            <td colspan="3">
                                <div class="Pos_rela">
                                    <input type="password" name="pwd"
                                           id="pwd" onclick="cleanError()"
                                           class="seachIptText marginRight10 borderRadius" onblur="if ($(this).val() =='') {
                                    $(this).next('.placeholder').show();}"
                                           onfocus="$(this).next('.placeholder').hide();"
                                    />
                                    <span class="placeholder"
                                          onclick="$(this).hide();$(this).prev('.seachIptText').focus();"
                                          onblur="cleanError()">请输入密码</span>
                                    <div class="errorBox borderRadius">
                                        <div class="errorArrow"></div>
                                        <span id="pwdMsg"></span></div>
                                </div>
                            </td>

                        </tr>
                    </table>
                    <table class="threeBtn ">
                        <tr>
                            <td>
                                <input type="submit" class="btn width80 borderRadius backgroundTransparent"
                                       onclick="return validate_submit();" value="确认"/>
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
    var testElement = $("#test");
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
    var wait_box = $(".wait_box");
    var p12FileMag = $("#p12FileMag");
    var pwdMsg = $("#pwdMsg");
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
        hiddenError(p12FileMag);
        hiddenError(pwdMsg);
    }

    function validate_submit() {
        wait_box.fadeIn(500);
        if (!flag) {
            flag = true;
            var p12FileVal = $.trim(p12File.val());
            if (p12FileVal == null || p12FileVal == "") {
                flag = false;
                wait_box.fadeOut(500);
                showError(p12FileMag, "*请选择文件！");
                return false;
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
                return false;
            }
            var pwd = $.trim($("#pwd").val());
            if (pwd == null || pwd == "") {
                flag = false;
                wait_box.fadeOut(500);
                showError(pwdMsg, "*请输入密码！");
                return false;
            }
            testElement.val("");
            return true;
        }
        return false;
    }
</script>
</body>
</html>