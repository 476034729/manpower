<%--
  Created by IntelliJ IDEA.
  User: songbo
  Date: 2018/7/18
  Time: 15:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%--<title>Title</title>--%>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <meta charset="utf-8"/>
    <%--<title><t:mutiLang langKey="jeect.platform"/></title>--%>
    <link rel="shortcut icon" href="images/favicon.ico">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0"/>
    <!-- bootstrap & fontawesome -->
    <link rel="stylesheet" href="plug-in/ace/css/bootstrap.css"/>
    <link rel="stylesheet" href="plug-in/ace/css/font-awesome.css"/>
    <link rel="stylesheet" type="text/css" href="plug-in/accordion/css/accordion.css">
    <link rel="stylesheet" type="text/css" href="plug-in/datetimepicker/css/bootstrap-datetimepicker.min.css">
    <link rel="stylesheet" type="text/css" href="plug-in/bootstrap-multiselect/css/bootstrap-multiselect.css">
    <!-- text fonts -->
    <link rel="stylesheet" href="plug-in/ace/css/ace-fonts.css"/>

    <link rel="stylesheet" href="plug-in/ace/css/jquery-ui.css"/>
    <!-- ace styles -->
    <link rel="stylesheet" href="plug-in/ace/css/ace.css" class="ace-main-stylesheet" id="main-ace-style"/>
    <!--[if lte IE 9]>
    <link rel="stylesheet" href="plug-in/ace/css/ace-part2.css" class="ace-main-stylesheet"/>
    <![endif]-->

    <!--[if lte IE 9]>
    <link rel="stylesheet" href="plug-in/ace/css/ace-ie.css"/>
    <![endif]-->
    <!-- ace settings handler -->
    <script src="plug-in/ace/js/ace-extra.js"></script>

    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->

    <!--[if lte IE 8]>
    <script src="plug-in/ace/js/html5shiv.js"></script>
    <script src="plug-in/ace/js/respond.js"></script>
    <![endif]-->
    <style>
        td {
            text-align: center;
            border: 1px solid;
        }
        th{
            text-align: center;
            border: 1px solid;
        }

        #thth{
            position: fixed;
            top:50px;
            left: 0;
        }
        #hr_statistics_table{
            margin-top: 181px;
        }
        #head_select {
            position: fixed;
            width: 100%;
            height: 50px;
            background-color: #5f5f5f;
            top: 0;
            left: 0;
            z-index: 101;
            opacity: 0.85;
            padding: 8px;
        }
        body{
            overflow-y: auto;
        }
    </style>
</head>
<body style="">
<div  id="head_select" style="width: 100%">
    <div class="input-append date form_datetime" style="float: left;margin-left: 10px;">
        <span style="color: white">选择年月:</span>
        <input size="16" id="timeSelector" type="text" value="" readonly>
        <span class="add-on"><i class="icon-th"></i></span>
    </div>
    <button type="button" class="btn btn-primary" id="query" style="float: left; margin-left: 10px;    border: none;">查询</button>
    <button type="button" class="btn btn-primary" id="export" style="float: left; margin-left: 10px;    border: none;">导出</button>

</div>
<table class="table table-bordered" id="hr_statistics_table" text-align="center">


</table>
</body>
<script type="text/javascript" src="plug-in/jquery/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="plug-in/jquery/jquery.cookie.js"></script>
<script type="text/javascript" src="plug-in/mutiLang/en.js"></script>
<script type="text/javascript" src="plug-in/mutiLang/zh-cn.js"></script>
<script type="text/javascript" src="plug-in/login/js/jquery.tipsy.js"></script>
<script type="text/javascript" src="plug-in/login/js/iphone.check.js"></script>
<script type="text/javascript" src="plug-in/bootstrap/js/bootstrap.js"></script>
<script type="text/javascript" src="plug-in/datetimepicker/js/bootstrap-datetimepicker.min.js"></script>
<script type="text/javascript" src="plug-in/bootstrap-multiselect/js/bootstrap-multiselect.js"></script>
<script type="text/javascript" src="plug-in/jquery/tableExport/tableExport.js"></script>
<script type="text/javascript" src="plug-in/jquery/base64/base64.js"></script>
<script>
    var codeList;

    <!-- 时间戳转日期 -->
    function timestampToTime(timestamp) {
        var date = new Date(timestamp);//时间戳为10位需*1000，时间戳为13位的话不需乘1000
        Y = date.getFullYear() + '-';
        M = (date.getMonth() + 1) + '-';
        D = date.getDate();
        return Y + M + D;
    }

    $('.form_datetime').datetimepicker({
        format: 'yyyy-m',
        autoclose: true,
        todayBtn: true,
        startView: 'year',
        minView: 'year',
        maxView: 'decade',
        language: 'zh-CN',
    });

    <!-- 查询按钮事件 -->
    $("#query").click(function () {
        $("#hr_statistics_table").empty();
        var time = $("#timeSelector").val();
        if (time == null || time.length == 0) {
            alert("请选择日期!");
            return;
        }
        var queryYear = time.split("-")[0];
        var queryMonth = time.split("-")[1];

        build_table_once();
        build_table_twice(queryYear, queryMonth);
    })
    <!-- 导出为excel表事件 -->
    $("#export").click(function(){
        var time = $("#timeSelector").val();
        var tableContainer = $("#hr_statistics_table").html();
        if ($.trim(tableContainer) == null || $.trim(tableContainer) == 0) {
            alert("请查询数据!");
            return;
        }
            $('#hr_statistics_table').tableExport({
                type:'excel',
                escape:'false',
                fileName: time+'人力统计清单'
            });


    });


    <!-- 建立表头 -->
    function build_table_once() {
        $.ajax({
            type: 'get',
            url: 'HrCMAnalyzeController.do?getprojectlist',
            async: false,
            cache: false,
            success: function (result) {
                var r = JSON.parse(result);
                codeList = r;
                var thead = $("<thead id='thth'></thead>");
                var tr_projectName = $("<tr></tr>").attr("id", "project_name");
                var tr_projectCode = $("<tr></tr>").attr("id", "project_code");
                $("<th></th>").attr("rowspan", "2").attr("colspan", "2").append("序号").appendTo(tr_projectName);
                $("<th></th>").attr("rowspan", "2").attr("colspan", "2").append("姓名").appendTo(tr_projectName);
                $("<th></th>").attr("rowspan", "2").append("时间").appendTo(tr_projectName);
                $("<th></th>").append("Total").appendTo(tr_projectName);
                $("<th></th>").append("Utilistaion(%)").appendTo(tr_projectCode);
                tr_projectName.appendTo(thead);
                tr_projectCode.appendTo(thead);
                thead.appendTo($("#hr_statistics_table"));
                $.each(r, function (index, item) {
                    $("<th></th>").append(item.project_name).appendTo($("#project_name"));
                    $("<th></th>").append(item.code).appendTo($("#project_code"));
                })
            }
        })
    }

    function build_table_twice(year, month) {
        $.ajax({
            type: 'get',
            url: 'HrCMAnalyzeController.do?getstatisticsinfo',
            data: {
                'year': year,
                'month': month
            },
            async: false,
            cache: false,
            success: function (result) {
                var r = JSON.parse(result);
                if (r.success == false) {
                    $("#hr_statistics_table").empty();
                    alert(r.msg);
                    return;
                }
                var list = r.obj;
                $("<tbody></tbody>").attr("id", "tbody").appendTo($("#hr_statistics_table"));
                $.each(list, function (index1, item1) {
                    var userCodeList = item1;
                    var name = index1;

                    var serinalNum;
                    $.each(userCodeList,function (index4,items4) {
                        if (index4 ="serialNum"){
                            serinalNum=userCodeList[index4];
                        }
                    });

                    var amTr = $("<tr></tr>").attr("id", "am" + index1).append($("<td></td>").attr("rowspan", "2").attr("colspan", "2").append(serinalNum))
                        .append($("<td></td>").attr("rowspan", "2").attr("colspan", "2").append(index1))
                        .append($("<td></td>").append("上午"));
                    var pmTr = $("<tr></tr>").attr("id", "pm" + index1).append($("<td></td>").append("下午"));
                    amTr.appendTo($("#tbody"));
                    pmTr.appendTo($("#tbody"));
                    $("<td></td>").attr("id", "amRate" + name).appendTo($("#am" + name));
                    $("<td></td>").attr("id", "pmRate" + name).appendTo($("#pm" + name));
                    $.each(codeList, function (index2, item2) {
                        $("<td></td>").attr("id", "am" + item2.code + name).appendTo($("#am" + name));
                        $("<td></td>").attr("id", "pm" + item2.code + name).appendTo($("#pm" + name));
                    })

                    $.each(userCodeList, function (index3, item3) {
                        if (item3 != 0) {
                            $("#" + index3 + name).css("color", "red").append(item3);
                        } else {
                            $("#" + index3 + name).append(item3);
                        }
                    })
                })
                build_table_third(year, month);
            }
        })
    }

    //设置th的宽度
    function setThWidth() {

// var tdList=document.getElementById("#build_tbody_third").querySelector("td");

        var body=document.querySelector("#tbody");
        var firstThTr=document.querySelectorAll("tr")[0];
        var thList=firstThTr.querySelectorAll("th");
        var firstTr=body.querySelectorAll("tr")[0];
        var tdList=firstTr.querySelectorAll("td");
        for (var i=0;i<tdList.length;i++){
            thList[i].style.width=tdList[i].offsetWidth;
        }


        //动态设置表格距离顶部高度

        var tableDiv= document.getElementById("hr_statistics_table");
        var selectDiv=document.getElementById("head_select");
        var theadDiv=document.getElementById("thth");
        var mh=selectDiv.offsetHeight+theadDiv.offsetHeight+"px";
        tableDiv.style.marginTop=mh




    }


    //监听窗口变化
    window.onresize=function (ev) {
        setThWidth()

    }


    //监听滚动条的变化
    window.onscroll = function () {
        var sl = -Math.max(document.body.scrollLeft, document.documentElement.scrollLeft);
        document.getElementById('thth').style.left = sl + 'px';
    }

    function build_table_third(year, month) {
        $.ajax({
            type: 'get',
            url: 'HrCMAnalyzeController.do?gettotalinfo',
            data: {
                'year': year,
                'month': month
            },
            async: false,
            cache: false,
            success: function (result) {
                var r = JSON.parse(result);

                var amTr = $("<tr></tr>").attr("id", "ProjectPeopleDay").append($("<td></td>").attr("rowspan", "2").attr("colspan", "2").append(" "))
                    .append($("<td></td>").append(" "));
                var pmTr = $("<tr></tr>").attr("id", "ProjectPeopleDayRate").append($("<td></td>").append(" "));
                amTr.appendTo($("#tbody"));
                pmTr.appendTo($("#tbody"));
                $("<td></td>").append("项目人日量").appendTo($("#ProjectPeopleDay"));
                $("<td></td>").append("项目人日百分比(%)").appendTo($("#ProjectPeopleDayRate"));
                $.each(codeList, function (index2, item2) {
                    $("<td></td>").attr("id", "ProjectPeopleDay" + item2.code).appendTo($("#ProjectPeopleDay"));
                    $("<td></td>").attr("id", "ProjectPeopleDayRate" + item2.code).appendTo($("#ProjectPeopleDayRate"));
                })

                $.each(r, function (index1, item1) {
                    var totalCodeList = item1;
                    var name = index1;
                    $.each(totalCodeList, function (index3, item3) {
                        $("#" + name + index3).append(item3);
                    })
                })

                setThWidth()
            }
        })
    }
</script>
</html>
