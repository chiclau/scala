<%@ page language="java" pageEncoding="UTF-8" %>
<html>
<head>
    <title>智能POS管理平台</title>
    <%@include file="../common/base.jsp" %>

</head>

<body>
<!-- 右侧内容-->

<div class="leftContent Boxshadow marginLeft10 marginTop10">
    <div class="ModelDetail paddingBottom10">

        <div class="CurrentPosition marginBottom10">
            <span class="positionText">当前位置:</span><a href="#" class="positionText">应用管理</a>
        </div>
        <div class="fromInputBtn">
            <div class="seachEquip marginTop10">
                <form action="list.do" method="get" id="formId">
                    <table class="threeBtn tanTable">
                        <tr>
                            <td width="80" class="tdodd"><label class="marginRight10">应用名称:</label></td>
                            <td>
                                <div class="Pos_rela">
                                    <input type="text" class="seachIptText marginRight10 borderRadius"
                                           name="appName" value="${appFileHistoryQuery.appName}" maxlength="32"
                                    />
                                    <span class="placeholder">请输入应用名称</span>
                                    <input type="submit"
                                           class="btn width80 borderRadius backgroundTransparent floatLeft"
                                           value="查询"/></div>
                            </td>
                        </tr>
                    </table>
                    <table class="threeBtn ">
                        <tr>
                            <td>
                                <input type="button" class="btn width120 borderRadius backgroundTransparent"
                                       onClick="window.location.href='uploadAppFile.do'"
                                       value="发布应用包"/>
                            </td>
                        </tr>
                    </table>
                </form>
            </div>
        </div>
        <div class="ModuleBOX marginTop10">
            <table class="tableDetail">
                <thead>
                <tr>
                    <th width="90">操作</th>
                    <th>应用名称</th>
                    <th>应用版本序号</th>
                    <th>机构</th>
                    <th>机构类型</th>
                    <th>应用最低版本号</th>
                    <th>发布时间</th>
                    <th>上传时间</th>
                </tr>
                </thead>
                <tbody>
                <c:if test="${page eq null}">
                    <tr>
                        <td colspan="8">
                            点击查询查询数据
                        </td>
                    </tr>
                </c:if>
                <c:if test="${page ne null and fn:length(page.content) eq 0  }">
                    <tr>
                        <td colspan="8">
                            没有数据
                        </td>
                    </tr>
                </c:if>
                <c:if test="${page ne null and fn:length(page.content) ne 0  }">
                    <c:forEach items="${page.content}" var="items">
                        <tr>
                            <td>
                                <a onclick="deleteApp(${items.appFileId})" class="Editor">删除</a>
                                <a href="show.do?appFileId=${items.appFileId}" class="Editor">详情</a>
                            </td>
                            <td><c:out value='${items.appName}'/></td>
                            <td><c:out value='${items.versionCode}'/></td>
                            <td><c:out value='${org}'/></td>
                            <td><c:out value='${orgType}'/></td>
                            <td><c:out value='${items.minVersionCode}'/></td>
                            <td><c:out value='${items.publicDate}'/></td>
                            <td><c:out value='${items.creTime}'/></td>
                        </tr>
                    </c:forEach>
                </c:if>
                </tbody>
            </table>
            <%@include file="../common/page.jsp" %>
        </div>
    </div>
</div>
<div class="blackTanZZ" id="blackTanZZ">
    <div class="blackZZ"></div>
    <div class="blackContentZZ">
        <span class="blackTitleZZ">提示</span>
        <div class="blackTextZZ">确定要删除此信息吗?</div>
        <input type="button" class="btnTanZZ btnTanGrayZZ" value="取消" onclick="cancel()">
        <input type="button" class="btnTanZZ btnTanZhuZZ" value="确定" onclick="ensure()">
    </div>
</div>
<script>
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
        $(".tableDetail tbody tr:even").css("background-color", "#fff");
    });

    var blackTanZZ = $("#blackTanZZ");
    var flag = false;
    var appId;
    function deleteApp(appFileId) {
        if (!flag) {
            flag = true;
            $.ajax({
                url: "ajaxDelete.do",
                async: false,
                type: "post",
                data: {
                    appFileId: appFileId
                },
                dataType: "text",
                cache: false
            }).done(function (data) {
                if (data == "isLatestVersion") {
                    flag = false;
                    alert("*要删除的版本是最新版本，不能删除！");
                } else if (data == "isHeadOrgApp") {
                    flag = false;
                    alert("*要删除的应用是总行上传的，不能删除！");
                } else if (data == "success") {
                    blackTanZZ.fadeIn(50);
                    appId = appFileId;
                } else {
                    flag = false;
                    alert("*未知错误！");
                }
            }).fail(function () {
                flag = false;
                alert("*未知错误！");
            });
        } else {
            console.log("重复提交");
        }
    }

    //确认
    function ensure() {
        blackTanZZ.fadeOut(50);
        //删除成功，重新提交表达
        window.location = 'delete.do?appFileId=' + appId
    }

    //取消
    function cancel() {
        blackTanZZ.fadeOut(50);
        flag = false;
    }
</script>
</body>
</html>