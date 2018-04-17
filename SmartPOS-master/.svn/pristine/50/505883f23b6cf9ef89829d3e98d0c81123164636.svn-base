<%@ page language="java" pageEncoding="UTF-8" %>

<html>
<head>
    <%@include file="../common/base.jsp" %>
    <title>智能POS管理平台</title>
    <script src="${pageContext.request.contextPath}/webjars/jquery-ui/1.12.1/jquery-ui.min.js"
            type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/webjars/jquery-ui/1.12.1/ui/i18n/datepicker-zh-CN.js"
            type="text/javascript"></script>
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/webjars/jquery-ui/1.12.1/themes/blitzer/jquery-ui.min.css">
</head>

<body>
<div class="leftContent Boxshadow marginLeft10 marginTop10">

    <div class="ModelDetail paddingBottom10">

        <div class="CurrentPosition marginBottom10">
            <span class="positionText">当前位置:</span><a href="#" class="positionText">应用分组:</a><a href="#"
                                                                                                class="positionText">关联应用</a>
        </div>
        <div class="fromInputBtn">
            <div class="seachEquip marginTop10">
                <form action="associatedAppList.do" method="get">
                    <input type="hidden" name="appGroupId" value="${appGroupId}"/>
                    <table class="threeBtn tanTable">
                        <tr>
                            <td width="120" class="tdodd"><label class="marginRight10">应用名称:</label></td>
                            <td>
                                <div class="Pos_rela">
                                    <input type="text" class="seachIptText marginRight10 borderRadius"
                                           name="appName"
                                           value="${appName}"/>
                                    <span class="placeholder">请输入应用名称</span></div>
                            </td>
                            <td width="120" class="tdodd"><label class="marginRight10 selectLabel">上传日期:</label></td>
                            <td>
                                <div class="Pos_rela">
                                    <input type="text" class="Wdate seachIptText marginRight10 borderRadius"
                                           id="creTime" name="creTime"/>
                                    <span class="placeholder">请选择上传日期</span>
                                </div>
                            </td>
                        </tr>
                    </table>
                    <table class="threeBtn ">
                        <tr>
                            <td>
                                <input type="submit" class="btn width80 borderRadius backgroundTransparent" value="查询"/>
                            </td>
                            <td>
                                <input type="button" class="btn width80 borderRadius backgroundTransparent "
                                       onclick="allSelect()" value="关联"/>
                            </td>
                            <td>
                                <input type="button" class="btn width80 borderRadius backgroundTransparent "
                                       onclick="history.back()" value="返回"/>
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
                    <th id="th"></th>
                    <th>应用包名</th>
                    <th>应用名称</th>
                </tr>
                </thead>
                <tbody>
                <c:if test="${page eq null or fn:length(page.content) eq 0  }">
                    <tr>
                        <td colspan="3">
                            没有数据
                        </td>
                    </tr>
                </c:if>
                <%--@elvariable id="page" type="org.springframework.data.domain.Page"--%>
                <c:if test="${page ne null and fn:length(page.content) ne 0  }">
                    <c:forEach items="${page.content}" var="item" varStatus="status">
                        <tr>
                            <td><input type="checkbox" name="appNameAndAppPackageNames"
                                       value="${item.appName}~${item.appPackageName}"></td>
                            <td><c:out value="${item.appPackageName}"/></td>
                            <td><c:out value="${item.appName}"/></td>
                        </tr>
                    </c:forEach>
                </c:if>
                </tbody>
            </table>
            <%@include file="../common/page.jsp" %>
        </div>
    </div>
</div>
<script type="text/javascript">
    var inputText = $("input[type='text']");
    var creTimes = $("#creTime");

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

    creTimes.change(function () {
        if (creTimes.val() == null || creTimes.val() == '') {
            creTimes.next(".placeholder").show();
        } else {
            creTimes.next(".placeholder").hide();
        }
    });

    $(function () {
        var creTimes = $("#creTime");
        var th = $("#th");
        var appNameAndAppPackageNames = $("input[name=appNameAndAppPackageNames]");
        if (appNameAndAppPackageNames.length != 0) {
            th.html("<input type='checkbox' id='checkA' onclick='checkAll();'/>");
        } else {
            th.html("");
        }
        creTimes.datepicker({
            dateFormat: 'yymmdd'
        });
        var creTime = "${creTime}";
        if (creTime != null && creTime != "") {
            creTimes.val(creTime.substr(0, 8))
        }
        if (creTimes.val() == null || creTimes.val() == '') {
            creTimes.next(".placeholder").show();
        } else {
            creTimes.next(".placeholder").hide();
        }
    });
    //设置全选
    function checkAll() {
        var checkA = $("#checkA");
        var appNameAndAppPackageNames = $("input[name=appNameAndAppPackageNames]");
        for (var i = 0; i < appNameAndAppPackageNames.length; i++) {
            appNameAndAppPackageNames.get(i).checked = checkA.get(0).checked;
        }
    }

    /**
     * 全部关联
     */
    var flag = false;
    function allSelect() {
        if (!flag) {
            flag = true;
            var appNameAndAppPackageNames = $("input[name=appNameAndAppPackageNames]");
            var length = appNameAndAppPackageNames.length;
            if (appNameAndAppPackageNames == null || length == 0) {
                alert("目前还没有应用不能关联！");
                flag = false;
                return;
            }
            var appNameAndAppPackageName = "";
            for (var i = 0; i < length; i++) {
                if (appNameAndAppPackageNames.get(i).checked) {
                    if (i != length - 1) {
                        appNameAndAppPackageName += appNameAndAppPackageNames[i].value + ",";
                    } else {
                        appNameAndAppPackageName += appNameAndAppPackageNames[i].value
                    }
                }
            }
            if (appNameAndAppPackageName == "") {
                alert("请选择应用！");
                flag = false;
                return;
            }
            window.location = "allAssociatedApp.do?appNameAndAppPackageNames=" + appNameAndAppPackageName + "&appGroupId=${appGroupId}";
        }
    }

</script>
</body>
</html>