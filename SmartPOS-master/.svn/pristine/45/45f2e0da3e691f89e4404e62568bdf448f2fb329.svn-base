<%@page contentType="text/html" pageEncoding="UTF-8" %>
<html>
<head>
    <title>智能POS管理平台</title>
    <jsp:include page="../common/base.jsp"/>

</head>
<body>
<div class="leftContent Boxshadow marginLeft10 marginTop10">

    <div class=" ModelDetail paddingBottom10">
        <div class="CurrentPosition marginBottom10">
            <span class="positionText">当前位置:</span><a href="#"
                                                      class="positionText">应用管理</a><span class="positionText">></span><a
                href="#"
                class="positionText">发布应用包</a>
        </div>
        <div class="fromInputBtn">
            <div class="seachEquip marginTop10">
                <form action="uploadAppFileToTempDir.do" method="post" enctype="multipart/form-data">
                    <table class="threeBtn tanTable">
                        <tr>
                            <td width="80" class="tdodd"><label class="marginRight10">上传文件:</label></td>
                            <td>
                                <div class="Pos_rela">
                                    <div class="floatLeft">
                                    <span class="inputFileBox">
                                        <div class="Pos_rela">
                                            <input type="text" class="seachIptText width150 marginRight10 borderRadius"
                                                   name="zipFile" id="test" readonly onclick="cleanError()"
                                                   style="background-color:#eee;"/>
                                            <span class="placeholder">未选择文件</span>
                                            <input type="file" name="zipFile" id="zipFile" style="display:none;">
                                            <a href="javascript:" class="file" id="file" onclick="selectFile()">
                                                浏览文件</a></div>
                                    </span>
                                    </div>
                                    <div class="errorBox borderRadius">
                                        <div class="errorArrow"></div>
                                        <span id="msgError">错误信息</span></div>

                                    <input style="margin-left:80px;" type="submit"
                                           class="btn width80 borderRadius backgroundTransparent floatLeft"
                                           onclick="return validate_submit()" value="下一步"/></div>
                            </td>
                        </tr>

                    </table>

                </form>
            </div>
        </div>
    </div>
</div>
<script>
    var testElement = $("#test");
    $(function () {
        if (testElement.val() == "" || testElement.val() == null) {
            testElement.next(".placeholder").show();
        } else {
            testElement.next(".placeholder").hide();
        }
    });

    function selectFile() {
        $('input[name=zipFile]').click();
    }
    $("#zipFile").change(function () {
        var path = $("#zipFile").val();
        var test1 = path.lastIndexOf("/");  //对路径进行截取
        var test2 = path.lastIndexOf("\\");  //对路径进行截取
        var test = Math.max(test1, test2);
        if (test < 0) {
            testElement.val(path);
        } else {
            testElement.val(path.substring(test + 1));//赋值文件名
        }
        if (testElement.val() == "" || testElement.val() == null) {
            testElement.next(".placeholder").show();
        } else {
            testElement.next(".placeholder").hide();
        }
    });
    var wait_box = $(".wait_box");
    var msgError = $("#msgError");
    var flag = false;

    function cleanError() {
        msgError.parent(".errorBox").css("display", "none");
        msgError.text("");
    }

    function validate_submit() {
        wait_box.fadeIn(500);
        msgError.parent(".errorBox").css("display", "none");
        msgError.text("");
        if (!flag) {
            flag = true;
            var zipFile = $("#zipFile").val();
            if (zipFile == null || zipFile == "") {
                wait_box.fadeOut(500);
                msgError.parent(".errorBox").css("display", "block");
                msgError.text("请选择文件！");
                flag = false;
                return false
            }
            var fileName = "";
            if (zipFile.lastIndexOf("/") > -1) {
                fileName = zipFile.substr(zipFile.lastIndexOf("/"))
            } else {
                fileName = zipFile
            }
            if (fileName.substr(fileName.lastIndexOf("."), 4) != ".zip") {
                wait_box.fadeOut(500);
                msgError.parent(".errorBox").css("display", "block");
                msgError.text("您选择的文件不是zip文件，不能上传！");
                flag = false;
                return false
            }
            return true
        }
        return false
    }
</script>
</body>
</html>