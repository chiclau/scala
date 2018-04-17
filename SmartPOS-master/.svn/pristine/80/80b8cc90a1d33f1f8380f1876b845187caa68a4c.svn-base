<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
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
            <span class="positionText">当前位置:</span><a href="#" class="positionText">设备管理</a><span
                class="positionText">></span><a href="#" class="positionText">设备明细</a><span
                class="positionText">></span><a href="#" class="positionText">上传设备文件</a>
        </div>
        <div class="fromInputBtn">
            <div class="seachEquip marginTop10">
                <form action="importSn.do" method="post" enctype="multipart/form-data">
                    <table class="threeBtn tanTable">
                        <tr>
                            <td width="120" class="tdodd">
                                <label class="marginRight10">上传文件:</label></td>
                            <td>
                                <div class="Pos_rela">
                                    <div class="floatLeft">
                                    <span class="inputFileBox">
                                        <div class="Pos_rela">
                                            <input type="text" class="seachIptText width180 marginRight10 borderRadius"
                                                   name="test" id="test" readonly style="background-color:#eee;"/>
                                            <span class="placeholder"
                                            >未选择文件</span>
                                            <input type="file" name="snFile" id="snFile" style="display:none;">
                                            <a href="javascript:;" class="file" id="file"
                                               onclick="selectFile();"> 浏览文件</a></div>
                                    </span>
                                    </div>
                                    <div class="errorBox borderRadius">
                                        <div class="errorArrow"></div>
                                    </div>
                                    <input type="submit" class="btn width80 borderRadius backgroundTransparent"
                                           value="上传"
                                           onclick="return validate_form();" style="margin-left: 80px;"/>
                                    <input type="button"
                                           class="btn width80 borderRadius marginLeft10 backgroundTransparent"
                                           onclick="window.location.href='emptyList.do'" value="取消"/></div>
                            </td>
                        </tr>

                    </table>

                </form>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    var isClicked = false;
    var wait_box = $(".wait_box");
    var snFile = $("#snFile");
    var testElement = $("#test");
    function selectFile() {
        $('input[name=snFile]').click();
    }

    $(function () {
        var snFileVal = snFile.val();
        if (snFileVal != null || snFileVal != "") {
            insertTitle();
        }
        snFile.change(function () {
            insertTitle();
        });
        if (testElement.val() == "" || testElement.val() == null) {
            testElement.next(".placeholder").show();
        } else {
            testElement.next(".placeholder").hide();
        }
    });

    //显示文件
    function insertTitle() {
        var path = snFile.val();
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
    }

    function validate_form() {
        wait_box.fadeIn(500);
        if (!isClicked) {
            isClicked = true;
            var snFileVal = $.trim(snFile.val());
            var testElementVal = $.trim(testElement.val());
            if (snFileVal == null || snFileVal == "" || testElementVal == null || testElementVal == "") {
                isClicked = false;
                wait_box.fadeOut(500);
                alert("请选择文件！");
                return false;
            }
            var fileName = "";
            if (snFileVal.lastIndexOf("/") > -1) {
                fileName = snFileVal.substr(snFile.lastIndexOf("/"));
            } else {
                fileName = snFileVal;
            }
            if (fileName.substr(fileName.lastIndexOf("."), fileName.length) != ".xls" && fileName.substr(fileName.lastIndexOf("."), fileName.length) != ".csv") {
                isClicked = false;
                wait_box.fadeOut(500);
                alert("您选择的文件不是xls或csv文件，不能上传！");
                return false;
            }
            testElement.val("");
            return true;
        } else {
            wait_box.fadeOut(500);
            return false;
        }
    }
</script>
</body>
</html>