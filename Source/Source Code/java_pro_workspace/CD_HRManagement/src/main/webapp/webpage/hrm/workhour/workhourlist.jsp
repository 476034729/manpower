<%--
  Created by IntelliJ IDEA.
  User: songbo
  Date: 2018/7/10
  Time: 16:43
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


        <style>
            .btn_option{

                position: fixed;
               left: 50%;
                top: 80%;
               transform: translateX(-50%);

            }
            #submit{
                transition: all 0.3s;
                box-shadow: 0px 0px 5px #428bca !important;
            }

            #submit:hover{
                box-shadow: 0px 0px 20px #428bca !important;
                background-color: #428bca !important;
            }

            #reset{
                transition: all 0.3s;
                box-shadow: 0px 0px 5px #d15b47  !important;
            }
            #reset:hover{
                box-shadow: 0px 0px 20px #d15b47  !important;
                background-color: #d15b47 !important;
            }

        </style>
    <!-- ace settings handler -->
    <script src="plug-in/ace/js/ace-extra.js"></script>

    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->

    <!--[if lte IE 8]>
    <script src="plug-in/ace/js/html5shiv.js"></script>
    <script src="plug-in/ace/js/respond.js"></script>
    <![endif]-->
</head>
<body style="overflow: auto;-webkit-overflow-scrolling: touch;">
<table class="table table-bordered" id="work_hour_table">
    <thead>
    <tr id="date">
        <th rowspan="2" colspan="2">时间</th>


    </tr>
    <tr id="week">

    </tr>
    </thead>

    <tbody>
    <tr id="amCode">
        <td rowspan="2">上午</td>
        <td>项目编码</td>

    </tr>
    <tr id="amWork">
        <td>工作内容</td>

    </tr>
    <tr id="pmCode">
        <td rowspan="2">下午</td>
        <td>项目编码</td>

    </tr>
    <tr id="pmWork">
        <td>工作内容</td>

    </tr>
    </tbody>
</table>
<div  class="btn_option" >
    <button type="button" class="btn btn-primary" id="submit">提交</button>
    <button type="button" class="btn btn-danger" id="reset" style="margin-left: 50px" hidden disabled="disabled">重置</button>
</div>
<script type="text/javascript" src="plug-in/jquery/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="plug-in/jquery/jquery.cookie.js"></script>
<script type="text/javascript" src="plug-in/mutiLang/en.js"></script>
<script type="text/javascript" src="plug-in/mutiLang/zh-cn.js"></script>
<script type="text/javascript" src="plug-in/login/js/jquery.tipsy.js"></script>
<script type="text/javascript" src="plug-in/login/js/iphone.check.js"></script>
<script>
    var nowadays = new Date();
    var project_list;
    var selector = $("<select></select>").addClass("selectpicker");
    
    <!-- 获得所有项目信息 -->
    $(document).ready(function () {
        $.ajax({
            type: 'get',
            url: 'ProjectCodeController.do?getprojectlist',
            async: false,
            cache: false,
            success: function (result) {
                project_list = JSON.parse(result);
                $("<option></option>").append("请选择项目编码").appendTo(selector);
                $.each(project_list, function (index, item) {
                    $("<option></option>").attr("value", item.code).attr("id", item.code).append(item.project_name).appendTo(selector);
                })
                build_table_once();
            }
        })
    });

    <!-- 建立table基本信息 -->
    function build_table_once() {
        $.ajax({
            type: 'get',
            url: 'WorkHourController.do?showlist&year=' + nowadays.getFullYear() + '&month= ' + (nowadays.getMonth()+1),
            async: false,
            cache: false,
            success: function (result) {
                r = JSON.parse(result);
                var list = r.obj;
                $.each(list, function (index, item) {
                    var date = $("<th></th>").attr("rowspan", "1").append(timestampToTime(item.work_date));
                    var week = $("<th></th>").attr("rowspan", "1").append(getWeek(item.work_date));
                    date.appendTo($("#date"));
                    week.appendTo($("#week"));
                    if (!parseInt(item.status) == 0) {
                        var amSelector = selector.clone().attr("disabled", "disabled").attr("id", timestampToTime(item.work_date) + "amSelector").css({"border-color":"red","border-width":"5px"});
                        var pmSelector = selector.clone().attr("disabled", "disabled").attr("id", timestampToTime(item.work_date) + "pmSelector").css({"border-color":"red","border-width":"5px"});
                        var amInput = $("<textarea></textarea>").attr("rows", "3").attr("disabled", "disabled").attr("id", timestampToTime(item.work_date) + "amInput").css({"border-color":"red","border-width":"5px"});
                        var pmInput = $("<textarea></textarea>").attr("rows", "3").attr("disabled", "disabled").attr("id", timestampToTime(item.work_date) + "pmInput").css({"border-color":"red","border-width":"5px"});
                    } else {
                        var amSelector = selector.clone().attr("id", timestampToTime(item.work_date) + "amSelector");
                        var pmSelector = selector.clone().attr("id", timestampToTime(item.work_date) + "pmSelector");
                        var amInput = $("<textarea></textarea>").attr("rows", "3").attr("id", timestampToTime(item.work_date) + "amInput");
                        var pmInput = $("<textarea></textarea>").attr("rows", "3").attr("id", timestampToTime(item.work_date) + "pmInput");
                    }
                    var amCode = $("<td></td>").append($("<div></div>").addClass("form-group").append(amSelector));
                    var amWork = $("<td></td>").append(amInput);
                    amCode.appendTo($("#amCode"));
                    amWork.appendTo($("#amWork"));
                    var pmCode = $("<td></td>").append($("<div></div>").addClass("form-group").append(pmSelector));
                    var pmWork = $("<td></td>").append(pmInput);
                    pmCode.appendTo($("#pmCode"));
                    pmWork.appendTo($("#pmWork"));
                })
                build_table_twice();
            }
        })
    }

    <!-- 将工时信息对table再一次补全 -->
    function build_table_twice() {
        $.ajax({
            type: 'get',
            url: 'WorkHourController.do?getworkhourlist&year=' + nowadays.getFullYear() + '&month=' + (nowadays.getMonth()+1),
            async: false,
            cache: false,
            success: function (result) {
                r = JSON.parse(result);
                $.each(r, function (index, item) {
                    var id = '#' + timestampToTime(item.work_day) + item.amOrPm;
                    $(id + 'Selector').attr("data-id", item.id).val(item.project_code);
                    // console.log(item.project_code);
                    $(id + 'Input').attr("data-id", item.id).val(item.work_details);

                })
            }
        })
    }

    <!-- 时间戳转日期 -->
    function timestampToTime(timestamp) {
        var date = new Date(timestamp);//时间戳为10位需*1000，时间戳为13位的话不需乘1000
        var len = date.getTime();
        //本地时间与GMT时间的时间偏移差
        var offset = date.getTimezoneOffset() * 60000;
        //得到现在的格林尼治时间=
        var utcTime = len + offset;
        var newDate=new Date(utcTime + 3600000 * 8);
        Y = newDate.getFullYear() + '-';
        M = (newDate.getMonth() + 1) + '-';
        D = newDate.getDate();
        return Y + M + D;
    }

    <!-- 时间戳获取星期几 -->
    function getWeek(time) {

        var timedat = new Date(time);
        var len = timedat.getTime();
        //本地时间与GMT时间的时间偏移差
        var offset = timedat.getTimezoneOffset() * 60000;
        //得到现在的格林尼治时间=
        var utcTime = len + offset;
        var newDate=new Date(utcTime + 3600000 * 8);
        var week;

        if (newDate.getDay() == 0) week = "星期日";

        if (newDate.getDay() == 1) week = "星期一";

        if (newDate.getDay() == 2) week = "星期二";

        if (newDate.getDay() == 3) week = "星期三";

        if (newDate.getDay() == 4) week = "星期四";

        if (newDate.getDay() == 5) week = "星期五";

        if (newDate.getDay() == 6) week = "星期六";

        return week;
    }

    <!-- 重新构成界面函数 -->
    function reset_table() {
        $("#work_hour_table").empty();
        var thead = $("<thead></thead>").append($("<tr></tr>").attr("id", "date").append(
            $("<th></th>").attr("rowspan", "2").attr("colspan", "2").append("时间"))).append($("<tr></tr>").attr("id", "week"));
        var trAmCode = $("<tr></tr>").attr("id", "amCode").append($("<td></td>").attr("rowspan", "2").append("上午"))
            .append($("<td></td>").append("项目编码"));
        var trAmWork = $("<tr></tr>").attr("id", "amWork").append($("<td></td>").append("工作内容"));
        var trPmCode = $("<tr></tr>").attr("id", "pmCode").append($("<td></td>").attr("rowspan", "2").append("下午"))
            .append($("<td></td>").append("项目编码"));
        var trPmWork = $("<tr></tr>").attr("id", "pmWork").append($("<td></td>").append("工作内容"));
        var tbody = $("<tbody></tbody>").append(trAmCode).append(trAmWork).append(trPmCode).append(trPmWork);
        thead.appendTo($("#work_hour_table"));
        tbody.appendTo($("#work_hour_table"));
        build_table_once();
    }

    <!-- 重置按钮事件 -->
    $("#reset").click(function () {
        var r = confirm("是否重置？");
        if (r == true){
            reset_table();
        }else {
            alert("false");
        }
    })

    <!-- 提交按钮事件 -->
    $("#submit").click(function () {
        var ids = new Array();
        var codes = new Array();
        var texts = new Array();
        var amCodeTdList = $("#amCode").find("td");
        var amInputTdList = $("#amWork").find("td");
        var pmCodeTdList = $("#pmCode").find("td");
        var pmInputTdList = $("#pmWork").find("td");
        for (var i = 2; i < amCodeTdList.length; i++) {
            ids.push(amCodeTdList.eq(i).find("select").attr("data-id"));
            codes.push(amCodeTdList.eq(i).find("select").val());
            texts.push(amInputTdList.eq(i - 1).find("textarea").val());
        }
        for (var j = 2; j < pmCodeTdList.length; j++) {
            ids.push(pmCodeTdList.eq(j).find("select").attr("data-id"));
            codes.push(pmCodeTdList.eq(j).find("select").val());
            texts.push(pmInputTdList.eq(j - 1).find("textarea").val());
        }
        $.ajax({
            type: 'post',
            url: 'WorkHourController.do?submitworkhourlist',
            data: {
                'ids': JSON.stringify(ids),
                'codes': JSON.stringify(codes),
                'texts': JSON.stringify(texts)
            },
            async: false,
            cache: false,
            success: function (result) {
                var j = JSON.parse(result);
                alert(j.msg);
                reset_table();
            }
        })
    })
</script>
</body>
</html>
