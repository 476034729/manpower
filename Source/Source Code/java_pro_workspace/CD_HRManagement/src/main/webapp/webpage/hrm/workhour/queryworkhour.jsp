<%--
  Created by IntelliJ IDEA.
  User: song2
  Date: 2018/7/16
  Time: 14:22
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
        }


        .multiselect-container{
         padding: 15px!important;
        }

        .multiselect-container.dropdown-menu{
            overflow: auto;
            height: 300px;
        }
        .multiselect-container.dropdown-menu > li{
            height: 30px;
        }

        .td_item{
            width: 120px;
        }

        #work_hour_table{
            margin-top: 118px;
        }

       #thth{
           position: fixed;
           top:50px;
           left: 0;
       }
        .btn-group{
            margin-top: -4px
        }

    </style>
</head>
<body style="overflow: scroll">
<div id="head_select" style="">

    <div class="input-append date form_datetime" style="display:inline-block;margin-left: 10px;padding: 8px;">
        <span style="color: white">请选择时间：</span>
        <input size="14" placeholder="点击选择时间" id="timeSelector" type="text" value="" style="display:inline-block;" readonly>
        <span class="add-on"><i class="icon-th"></i></span>
    </div>
    <div style="display:inline-block;margin-left: 10px" id="userContent">
        <span style="color: white">员工名：</span> <select id="Ste_user_selector" style="" multiple="multiple"></select>
    </div>
    <button type="button" class="btn btn-primary" id="query" style="display:inline-block; margin-left: 10px;    border: none;
    margin-top: -4px;">查询</button>
    <button type="button" class="btn btn-primary" id="export" style="display:inline-block;; margin-left: 10px;    border: none;
    margin-top: -4px;">导出</button>

</div>


<table class="table table-bordered" id="work_hour_table" text-align="center" >


</table>

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




    var queryYear;
    var queryMonth;
    var projectList;
    var dateList;


    <!-- 获得所有项目信息 和员工信息 -->
    $(document).ready(function () {

        $("#userContent").hide();

         $.ajax({
            type: 'get',
            url: 'ProjectCodeController.do?getprojectlist',
            async: false,
            cache: false,
            success: function (result) {
                projectList = JSON.parse(result);
            }
        })
 

    });

    <!-- 获得项目名 -->
    function getProjectName(code) {
        for (var i = 0; i < projectList.length; i++) {
            if (projectList[i].code == code) {
                return projectList[i].project_name;
            }
        }
    }

    <!-- 建立table基本信息 -->
    function build_table_once(year, month) {
        $.ajax({
            type: 'get',
            url: 'WorkHourController.do?showlist&year=' + year + '&month=' + month,
            async: false,
            cache: false,
            success: function (result) {
                r = JSON.parse(result);
                var list = r.obj;
                dateList = list;
                var thead = $("<thead id='thth'></thead>").append($("<tr></tr>").attr("id", "date").append(
                    $("<th></th>").attr("rowspan", "2").attr("colspan", "2").append("姓名")).append(
                    $("<th></th>").attr("rowspan", "2").attr("colspan", "2").append($("<div style='width: 41px;text-align: center'>时间</div>")))).append($("<tr></tr>").attr("id", "week"));
                thead.appendTo($("#work_hour_table"));
                $.each(list, function (index, item) {
                    var date = $("<th></th>").attr("rowspan", "1").append("<div class='td_item'>"+timestampToTime(item.work_date)+"<div>");
                    var week = $("<th></th>").attr("rowspan", "1").append("<div class='td_item'>"+getWeek(item.work_date)+"<div>");
                    date.appendTo($("#date"));
                    week.appendTo($("#week"));
                });



            }
        })
    }



    <!-- 将工时信息对table再一次补全 -->
    function build_table_twice(ids, year, month) {
        $.ajax({
            type: 'get',
            url: 'WorkHourController.do?getworkhourlistbyids',
            data: {
                'ids': JSON.stringify(ids),
                'year': year,
                'month': month
            },
            async: false,
            cache: false,
            success: function (result) {
                var r = JSON.parse(result);
                console.log(r);
                $("<tbody></tbody>").attr("id", "tbody").appendTo($("#work_hour_table"));
                $.each(r, function (index, item) {
                    var name = index;
                    var WorkHourList = item;
                    var amCode = $("<tr></tr>").attr("id", "amcode" + name).append($("<td></td>").attr("rowspan", "2").attr("colspan", "2").append(index))
                        .append($("<td></td>").attr("rowspan", "2").append("上午")).append($("<td></td>").append("项目编码"));
                    var amWork = $("<tr></tr>").attr("id", "amwork" + name).append($("<td></td>").append("工作内容"));
                    var pmCode = $("<tr></tr>").attr("id", "pmcode" + name).append($("<td></td>").attr("rowspan", "2").attr("colspan", "2").append(index))
                        .append($("<td></td>").attr("rowspan", "2").append("下午")).append($("<td></td>").append("项目编码"));
                    var pmWork = $("<tr></tr>").attr("id", "pmwork" + name).append($("<td></td>").append("工作内容"));
                    amCode.appendTo($("#tbody"));
                    amWork.appendTo($("#tbody"));
                    pmCode.appendTo($("#tbody"));
                    pmWork.appendTo($("#tbody"));

                    $.each(dateList, function (index1, item1) {
                        $("<td></td>").attr("id", timestampToTime(item1.work_date) + "amCodeTd" + name).appendTo($("#amcode" + name));
                        $("<td></td>").attr("id", timestampToTime(item1.work_date) + "amWorkTd" + name).appendTo($("#amwork" + name));
                        $("<td></td>").attr("id", timestampToTime(item1.work_date) + "pmCodeTd" + name).appendTo($("#pmcode" + name));
                        $("<td></td>").attr("id", timestampToTime(item1.work_date) + "pmWorkTd" + name).appendTo($("#pmwork" + name));
                    })

                    $.each(WorkHourList, function (index2, item2) {
                        var id = '#' + timestampToTime(item2.work_day) + item2.amOrPm;
                        if (!$.trim(item2.project_code)) {
                            $(id + 'CodeTd' + name).css("color", "red").append("<div class='td_item'>未填写</div>")
                        }
                        else {
                            var projectName = getProjectName(item2.project_code);
                            projectName===undefined ||  projectName==null ? projectName="":projectName;
                            $(id + 'CodeTd' + name).append("<div class='td_item'>"+projectName+"</div>")
                        }

                        if (!$.trim(item2.work_details)) {
                            $(id + 'WorkTd' + name).css("color", "red").append("<div class='td_item'>未填写</div>")
                        }
                        else {
                            item2.work_details===undefined ||  item2.work_details==null ? item2.work_details="":item2.work_details;
                            $(id + 'WorkTd' + name).append("<div class='td_item'>"+item2.work_details+"</div>")
                        }
                    })
                    
                    $.each(dateList,function (index3,item3) {
                        if (item3.status != 0) {
                            $("#" + timestampToTime(item3.work_date) + "amCodeTd" + name).text("");
                            $("#" + timestampToTime(item3.work_date) + "amWorkTd" + name).text("");
                            $("#" + timestampToTime(item3.work_date) + "pmCodeTd" + name).text("");
                            $("#" + timestampToTime(item3.work_date) + "pmWorkTd" + name).text("");
                            $("#" + timestampToTime(item3.work_date) + "amCodeTd" + name).append("<div class='td_item'></div>");
                            $("#" + timestampToTime(item3.work_date) + "amWorkTd" + name).append("<div class='td_item'></div>");
                            $("#" + timestampToTime(item3.work_date) + "pmCodeTd" + name).append("<div class='td_item'></div>");
                            $("#" + timestampToTime(item3.work_date) + "pmWorkTd" + name).append("<div class='td_item'></div>");
                        }
                    })
                })

            }
        })
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
        $("#work_hour_table").empty();
        var ids = $("#Ste_user_selector").val();
        if (ids == null || ids.length == 0) {
            alert("请选择员工!");
            return;
        }
        var time = $("#timeSelector").val();
        if (time == null || time.length == 0) {
            alert("请选择日期!");
            return;
        }
        queryYear = time.split("-")[0];
        queryMonth = time.split("-")[1];
        build_table_once(queryYear, queryMonth);
        build_table_twice(ids, queryYear, queryMonth);
    })

    <!--导出为excel事件-->
    $("#export").click(function () {
        var ids = $("#Ste_user_selector").val();
        console.log(ids);
        var tableContainer = $("#work_hour_table").html();
        if ($.trim(tableContainer) == null || $.trim(tableContainer) == 0) {
            alert("请查询数据!");
            return;
        }
        $('#work_hour_table').tableExport({
            type: 'excel',
            escape: 'false',
            fileName: queryYear + '/' + queryMonth + '工时信息'
        });


    });

    <!-- 时间戳转日期 -->
    function timestampToTime(timestamp) {
        var date = new Date(timestamp);//时间戳为10位需*1000，时间戳为13位的话不需乘1000
        Y = date.getFullYear() + '-';
        M = (date.getMonth() + 1) + '-';
        D = date.getDate();
        return Y + M + D;
    }

    <!-- 时间戳获取星期几 -->
    function getWeek(time) {

        var timedat = new Date(time);

        var week;

        if (timedat.getDay() == 0) week = "星期日";

        if (timedat.getDay() == 1) week = "星期一";

        if (timedat.getDay() == 2) week = "星期二";

        if (timedat.getDay() == 3) week = "星期三";

        if (timedat.getDay() == 4) week = "星期四";

        if (timedat.getDay() == 5) week = "星期五";

        if (timedat.getDay() == 6) week = "星期六";

        return week;
    }

    <!-- 判断是否为周末 -->
    function isWeekday(time) {
        var d = new Date(time);
        if (d.getDay() == 0 || d.getDay() == 6) {
            return true;
        } else {
            return false;
        }
    }

    <!-- 选择时间后显示员工列表事件 -->
    $("#timeSelector").change(function () {
        var time = $("#timeSelector").val();

        if (time == null || time.length == 0) {
            alert("请选择日期!");
            return;
        }

        var queryYear = time.split("-")[0];
        var queryMonth = time.split("-")[1];

        $.ajax({
            type: 'get',
            url: 'WorkHourController.do?getsteuserlist',
            data: {
                'year': queryYear,
                'month': queryMonth
            },
            async: false,
            cache: false,
            success: function (result) {
                $("#Ste_user_selector").empty();
                var r = JSON.parse(result);
                if (r.length == 0) {
                    alert("该月没有数据！");
                    return;
                }
                $.each(r, function (index, item) {
                    $("<option></option>").attr("value", item.id).append(item.userName).appendTo($("#Ste_user_selector"));
                })
            }
        });

        $("#Ste_user_selector").multiselect("destroy").multiselect({
            buttonWidth: 195,  //选择框的大小
            includeSelectAllOption: true//是否现实全选
        });

        $("#userContent").show();

    })

    window.onscroll = function () {
        var sl = -Math.max(document.body.scrollLeft, document.documentElement.scrollLeft);
        // console.log(sl);
        document.getElementById('thth').style.left = sl + 'px';
    }
    

</script>

</body>
</html>