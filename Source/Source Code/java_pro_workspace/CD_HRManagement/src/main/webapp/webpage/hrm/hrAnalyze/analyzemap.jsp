<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/7/23
  Time: 9:26
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

        th {
            text-align: center;
            border: 1px solid;
        }
    </style>
</head>
<body style="overflow: auto">


<form action="HrAnalyzeController.do?exportmapexcel" method="post" id="exportForm">
    <input type="hidden" name="img" id="img"/>
    <input type="hidden" name="year" id="year"/>
    <input type="hidden" name="month" id="month"/>
</form>
<div style="width: auto">
    <div class="input-append date form_datetime" style="float: left;margin-left: 10px;">
        选择年月:
        <input size="16" id="timeSelector" type="text" value="" readonly>
        <span class="add-on"><i class="icon-th"></i></span>
    </div>
    <button type="button" class="btn btn-primary" id="query" style="float: left; margin-left: 10px;">查询</button>
    <button type="button" class="btn btn-primary" id="export" style="float: left; margin-left: 10px;">导出</button>

</div>
<table class="table table-bordered" id="hr_analyze_table" text-align="center">


</table>

<div id="hr_analyze_map" style="width: 100%; height: 100%">

</div>
</body>
<script type="text/javascript" src="plug-in/jquery/jquery-1.9.1.js"></script>
<script type="text/javascript" src="plug-in/jquery/jquery.cookie.js"></script>
<script type="text/javascript" src="plug-in/mutiLang/en.js"></script>
<script type="text/javascript" src="plug-in/mutiLang/zh-cn.js"></script>
<script type="text/javascript" src="plug-in/login/js/jquery.tipsy.js"></script>
<script type="text/javascript" src="plug-in/login/js/iphone.check.js"></script>
<script type="text/javascript" src="plug-in/bootstrap/js/bootstrap.js"></script>
<script type="text/javascript" src="plug-in/datetimepicker/js/bootstrap-datetimepicker.min.js"></script>
<script type="text/javascript" src="plug-in/bootstrap-multiselect/js/bootstrap-multiselect.js"></script>
<script type="text/javascript" src="plug-in/echarts/echarts.min.js"></script>
<script type="text/javascript" src="plug-in/jquery/base64/base64.js"></script>
<script>


    var thisChart = '';
    var chartFlag = false;
    $('.form_datetime').datetimepicker({
        format: 'yyyy-m',
        autoclose: true,
        todayBtn: true,
        startView: 'year',
        minView: 'year',
        maxView: 'decade',
        language: 'zh-CN',
    });

    <!-- 按钮点击事件 -->
    $("#query").click(function () {
        $("#hr_analyze_table").empty();
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

    <!-- 建立表格基本信息 -->
    function build_table_once() {
        $.ajax({
            type: 'get',
            url: 'HrAnalyzeController.do?getprojectlist',
            async: false,
            cache: false,
            success: function (result) {
                var r = JSON.parse(result);
                $("<thead></thead>").attr("id", "thead").appendTo($("#hr_analyze_table"));
                $("<tbody></tbody>").attr("id", "tbody").appendTo($("#hr_analyze_table"));
                $.each(r, function (index, item) {
                    $("<th></th>").append(item.project_name).appendTo($("#thead"));
                    $("<td></td>").attr("id", "ProjectPeopleDayRate" + item.code).appendTo($("#tbody"));
                })
            }
        })
    }

    <!-- 补全表格 -->
    function build_table_twice(year, month) {
        $.ajax({
            type: 'get',
            url: 'HrAnalyzeController.do?getprojectpeopledayrate',
            data: {
                'year': year,
                'month': month
            },
            async: false,
            cache: false,
            success: function (result) {
                var r = JSON.parse(result);
                if (r.success == true) {
                    var list = r.obj;
                    $.each(list, function (index, item) {
                        $("#" + "ProjectPeopleDayRate" + index).append(item + "%");
                    })
                    $("#hr_analyze_map").show();
                    build_map(year, month);
                } else if (r.success == false) {
                    $("#hr_analyze_table").empty();
                    $("#hr_analyze_map").hide();
                    alert(r.msg);
                }
            }
        })
    }

    <!-- 构建横向柱状图 -->
    function build_map(year, month) {
        var projects = new Array();
        var rates = new Array();
        var colors = new Array();
        var projectList = $("#thead").find("th");
        var rateList = $("#tbody").find("td");
        for (var i = 0; i < projectList.length; i++) {
            projects.push(projectList.eq(i).text());
            var rate = rateList.eq(i).text()
            rates.push(rate.replace("%", ""));
            i % 2 == 0 ? colors.push("red") : colors.push("blue");
        }


        var myChart = echarts.init($("#hr_analyze_map")[0]);
        var app = {};
        option = null;
        option = {
            title: {
                text: year + '/' + month + '人力分析图' + '(%)',
                left: 'center'
            },
            tooltip: {
                trigger: 'axis',
                axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                    type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                }
            },
            xAxis: {},
            yAxis: {
                type: 'category',
                data: projects

            },
            series: [{
                data: rates,
                type: 'bar',
                label: {
                    normal: {
                        show: true,
                        position: 'right'
                    }
                },
                itemStyle: {
                    normal: {
                        color: function (params) {
                            var colorList = colors;
                            return colorList[params.dataIndex]
                        }
                    }
                }

            }]
        };

        if (option && typeof option === "object") {
            myChart.setOption(option, true);
        }
        thisChart = myChart;
        chartFlag = true;
    }

    <!-- 导出按钮事件 -->
    $("#export").click(function () {
        var time = $("#timeSelector").val();
        if (time == null || time.length == 0) {
            alert("请选择日期!");
            return;
        }

        var queryYear = time.split("-")[0];
        var queryMonth = time.split("-")[1];

        if (!chartFlag) {
            alert("请先查询结果!");
            return;
        }
        var data = thisChart.getDataURL();
        $("#img").val(data);
        $("#year").val(queryYear);
        $("#month").val(queryMonth);
        $("#exportForm").submit();

    })
</script>
</html>
