<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<html>
<head>
    <title>智能POS管理平台</title>
    <jsp:include page="../common/base.jsp"/>

</head>
<body>
<!-- 右侧内容-->
<div class="leftContent Boxshadow marginLeft10 marginTop10">
    <div class=" ModelDetail paddingBottom10">
        <div class="CurrentPosition marginBottom10">
            <span class="positionText">当前位置:</span><a href="#"
                                                      class="positionText">ota管理</a><span
                class="positionText">></span><a href="#" class="positionText">发布OTA</a>
        </div>
        <div class="fromInputBtn">
            <div class="seachEquip marginTop10">
                <form id="uploadAppFileForm" method="post" action="uploadOtaToTempDir.do" enctype="multipart/form-data">
                    <table class="threeBtn tanTable">
                        <tr>
                            <td width="120" class="tdodd"><label
                                    class="marginRight10 floatLeft selectLabel ">厂商名称:</label></td>
                            <td>
                                <div class="Pos_rela">
                                    <div class="selectBox">
                                        <span class="seachSelect borderRadius width120" id="firmCodeShow">请选择厂商</span>
                                        <select class="selectHidden" name="firmCode" onclick="cleanError()"
                                                onchange="firmCodeChange()" id="firmCode">
                                            <option value=''>请选择厂商</option>
                                            <c:forEach items="${firms}" var="item">
                                                <option value="${item.firmCode}">${item.firmName}</option>
                                            </c:forEach>
                                        </select></div>
                                    <div class="errorBox borderRadius" style=" margin-top: 16px;">
                                        <div class="errorArrow"></div>
                                        <span id="firmCodeMsg"></span></div>
                                </div>
                            </td>
                            <td width="120" class="tdodd">
                                <label class="marginRight10 floatLeft selectLabel">产品名称:</label></td>
                            <td>
                                <div class="Pos_rela">
                                    <div class="selectBox">

                                        <span class="seachSelect borderRadius width120 seachSelectEE" id="prodCodeShow">请选择</span>
                                        <select class="selectHidden" name="prodCode" id="prodCode"
                                                onclick="cleanError()" onChange="showProdCode() ">
                                        </select></div>
                                    <div class="errorBox borderRadius">
                                        <div class="errorArrow"></div>
                                        <span id="prodCodeMsg"></span></div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td width="120" class="tdodd"><label class="marginRight10">上传文件:</label></td>
                            <td colspan="3">
                                <div class="Pos_rela">
                                    <div class="floatLeft">
                                    <span class="inputFileBox">
                                        <div class="Pos_rela">
                                            <input type="text" class="seachIptText width150 marginRight10 borderRadius"
                                                   name="test" id="test" readonly="readonly"
                                                   style="background-color:#eee;"/>
                                            <span class="placeholder"
                                            >未选择文件</span>
                                            <input type="file" name="zipFile" id="zipFile" style="display:none;"
                                                   onclick="cleanError()">
                                            <a href="javascript:" class="file" id="file"
                                               onclick="selectFile()"> 浏览文件</a></div>
                                    </span>
                                    </div>
                                    <div class="errorBox borderRadius">
                                        <div class="errorArrow"></div>
                                        <span id="fileMsg"></span></div>
                                </div>
                            </td>
                        </tr>
                    </table>
                    <table class="threeBtn ">
                        <tr>
                            <td>
                                <input type="submit" class="btn width80 borderRadius backgroundTransparent"
                                       onclick="return validate_submit();" value="下一步"/>
                            </td>
                        </tr>
                    </table>
                </form>
            </div>
        </div>
    </div>
</div>
<script>
    var firmCodeMsg = $("#firmCodeMsg");
    var wait_box = $(".wait_box");
    var prodCodeMsg = $("#prodCodeMsg");
    var fileMsg = $("#fileMsg");
    var firmCodeShow = $("#firmCodeShow");
    var prodCodeShow = $("#prodCodeShow");
    var firmCode = $("#firmCode");
    var prodCode = $("#prodCode");
    var textElement = $("#test");
    var zipFile = $("#zipFile");

    $(function () {
        var selOptNewz = prodCode.find("option");
        if (selOptNewz.length > 1) {
            prodCode.css("display", "block");
            prodCodeShow.removeClass("seachSelectEE");
        } else {
            prodCode.css("display", "none");
            prodCodeShow.addClass("seachSelectEE");
        }
        if (textElement.val() == "" || textElement.val() == null) {
            textElement.next(".placeholder").show();
        } else {
            textElement.next(".placeholder").hide();
        }
    });

    function selectFile() {
        $('input[name=zipFile]').click();
    }

    $(function () {
        var zipFileVal = zipFile.val();
        if (zipFileVal != null || zipFileVal != "") {
            insertTitle();
        }
        zipFile.change(function () {
            insertTitle();
        });
    });

    function insertTitle() {
        var path = zipFile.val();
        var test1 = path.lastIndexOf("/");  //对路径进行截取
        var test2 = path.lastIndexOf("\\");  //对路径进行截取
        var test = Math.max(test1, test2);
        if (test < 0) {
            textElement.val(path);
        } else {
            textElement.val(path.substring(test + 1));//赋值文件名
        }
        if (textElement.val() == "" || textElement.val() == null) {
            textElement.next(".placeholder").show();
        } else {
            textElement.next(".placeholder").hide();
        }
    }

    function cleanError() {
        hiddenError(firmCodeMsg);
        hiddenError(prodCodeMsg);
        hiddenError(fileMsg);
    }

    function showError(target, msg) {
        target.parent(".errorBox").css("display", "block");
        target.text(msg);
    }

    function hiddenError(target) {
        target.parent(".errorBox").css("display", "none");
        target.text("");
    }

    function firmCodeChange() {
        firmCodeShow.text(firmCode.find("option:selected").text());
        prodCodeShow.text("请选择");
        $.ajax({
            url: "getProducts.do",
            data: {
                firmCode: firmCode.val()
            },
            type: "post",
            cache: false,
            dataType: "json",
            async: false
        }).done(function (result) {
            var selOpt = prodCode.find("option");
            selOpt.remove();// 清空
            jQuery("#prodCode").append(("<option value=''>请选择</option>"));
            var obj = eval(result);
            $(obj).each(function (idx) {

                jQuery("#prodCode").append("<option value='" + result[idx].prodCode + "'>" + result[idx].prodName + "</option>");

            });
            var selOptNew = prodCode.find("option");
            if (selOptNew.length > 1) {
                prodCode.css("display", "block");
                prodCodeShow.removeClass("seachSelectEE");
            } else {
                prodCode.css("display", "none");
                prodCodeShow.addClass("seachSelectEE");
            }
        }).fail(function () {
            alert("*未知错误！");
            prodCode.css("display", "none");
            prodCodeShow.css("background-color", "#eee");
        });
    }

    function showProdCode() {
        prodCodeShow.text(prodCode.find("option:selected").text());
    }

    var flag = false;
    function validate_submit() {
        wait_box.fadeIn(500);
        if (!flag) {
            flag = true;
            var firmCodeVal = $.trim(firmCode.val());
            var prodCodeVal = $.trim(prodCode.val());
            if (firmCodeVal == null || firmCodeVal == "") {
                flag = false;
                wait_box.fadeOut(500);
                showError(firmCodeMsg, "*请选择厂商！");
                return false;
            }
            if (prodCodeVal == null || prodCodeVal == "") {
                flag = false;
                wait_box.fadeOut(500);
                showError(prodCodeMsg, "*请选择产品！");
                return false;
            }
            var zipFileVal = $.trim(zipFile.val());
            if (zipFileVal == null || zipFileVal == "") {
                flag = false;
                wait_box.fadeOut(500);
                showError(fileMsg, "*请选择文件！");
                return false;
            }
            var fileName = "";
            if (zipFileVal.lastIndexOf("/") > -1) {
                fileName = zipFileVal.substr(zipFileVal.lastIndexOf("/"));
            } else {
                fileName = zipFileVal;
            }
            if (fileName.substr(fileName.lastIndexOf("."), 4) != ".zip") {
                flag = false;
                wait_box.fadeOut(500);
                showError(fileMsg, "*请上传zip文件！");
                return false;
            }
            return true;
        }
        return false;
    }
</script>
</body>
</html>