<%--
  Created by IntelliJ IDEA.
  User: Public_Stee_Develope
  Date: 2018/7/27
  Time: 14:15
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
      <link href="plug-in/ace/css/kendo.common.min.css" rel="stylesheet">
  <link href="plug-in/ace/css/kendo.default.min.css" rel="stylesheet">
  <link href="plug-in/ace/css/kendo.default.mobile.min.css" rel="stylesheet">
  <link rel="stylesheet" href="plug-in/ace/css/index.css">
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
        th{
           padding-left: 0px!important;
            padding-right: 0px!important;
            box-sizing: border-box;
            text-align: center;
            /*border-left: none!important;*/
            border: none!important;
        }

        td {
            text-align: center;
        }
        .btn-group{
            margin-top: -4px
        }
        #thead{
            position: fixed;
            top:50px;
            left: 0;
        }
        #head_select {
            position: fixed;
            width: 100%;
            height: 50px;
            background-color: #5f5f5f;
            top: 0;
            left: 0;
            opacity: 0.85;
            padding: 8px;
        }

        #project_input_total_table{
            margin-top: 82px;
            table-layout: fixed;
        }
        .th_item{
            border-left: 1px solid #d7d7d7;
            border-right: 1px solid #d7d7d7;

        }
        .btn-group{
            margin-top: -4px
        }
    </style>
</head>
<body style="overflow: auto">
<div  id="head_select"  >
    <div class="input-append date form_datetime" style="float: left;margin-left: 10px;">
        <span style="color: white">选择开始年月:</span>
        <input size="16" id="BeginTimeSelector" type="text" value="" readonly>
        <span class="add-on"><i class="icon-th"></i></span>
    </div>
    <div class="input-append date form_datetime" style="float: left;margin-left: 10px;">
        <span style="color: white"> 选择结束年月:</span>
        <input size="16" id="endTimeSelector" type="text" value="" readonly>
        <span class="add-on"><i class="icon-th"></i></span>
    </div>
   <!--  <div style="float: left; margin-left: 10px">
        <span style="color: white">项目名称：</span>
        <select class="selectpicker" id="project_selector">
        </select>
    </div> -->
    <div class="form_project" style="float: left;margin-left: 10px;">
        <span style="color: white">项目名称:</span>
        <input class="form_project_input" value="请选择项目编码" readonly></span>
        <div id="treeview" class="form_project_option"></div>
    </div>
        <button type="button" class="btn btn-primary" id="query" style="margin-left: 10px;    border: none;
   ">查询</button>
    <button type="button" class="btn btn-primary" id="export" style=" margin-left:10px;  border: none;
   ">导出</button>
  </div>
    


</div>
<table class="table table-bordered" id="project_input_total_table" >


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
<script src="plug-in/ace/js/kendo.all.min.js"></script>
<script src="plug-in/ace/js/index.js"></script>
<script>
	var code;
    var beginTime;
    var endTime;

    var beginMonth;
    var endMonth;
    $('.form_datetime').datetimepicker({
        format: 'yyyy-m',
        autoclose: true,
        todayBtn: true,
        startView: 'year',
        minView: 'year',
        maxView: 'decade',
        language: 'zh-CN',
    });

    //判断数组是否为空
    function isEmpty(value) {
        return (Array.isArray(value) && value.length === 0) || (Object.prototype.isPrototypeOf(value) && Object.keys(value).length === 0);
    }

    $(document).ready(function () {
        $.ajax({
            type: 'get',
            url: 'ProjectCodeController.do?getProjectListByCodeContact',
            async: false,
            cache: false,
            success: function (result) {
                var project_list = JSON.parse(result);
                $("<option></option>").append("请选择项目编码").appendTo($("#project_selector"));
                $.each(project_list, function (index, item) {
                    $("<option></option>").attr("value", item.code).attr("id", item.code).append(item.project_name).appendTo($("#project_selector"));
                })
            }
        })
    });

    <!-- 查询按钮事件 -->
    $("#query").click(function () {
        $("#project_input_total_table").empty();
        beginTime = $("#BeginTimeSelector").val();
        endTime = $("#endTimeSelector").val();

        beginMonth = parseInt(beginTime.split("-")[1]);
        endMonth = parseInt(endTime.split("-")[1]);

        if (parseInt(beginTime.split("-")[0]) > parseInt(endTime.split("-")[0])) {
            alert("请选择同一年的日期！");
            return;
        }
        if (beginMonth > endMonth) {
            alert("结束日期要大于开始日期");
            return;
        }
        /* var project = $("#project_selector").val(); */
        
        var project =codeContact;
        if (project == null || project.length == 0 || project == '请选择项目编码') {
            alert("请选择项目");
            return;
        }

        build_thead(parseInt(beginTime.split("-")[0]), beginMonth, endMonth);
        build_tbody_once();
        build_tbody_twice(project, beginMonth, endMonth);
        build_tbody_third(project, beginMonth, endMonth);
        build_tbody_fourth(beginMonth,endMonth);
    })

    <!-- 导出为excel表事件 -->
    $("#export").click(function(){
        var beginTime = $("#BeginTimeSelector").val();
        var endTime = $("#endTimeSelector").val();
        var tableContainer = $("#project_input_total_table").html();
        if ($.trim(tableContainer) == null || $.trim(tableContainer) == 0) {
            alert("请查询数据!");
            return;
        }
        $('#project_input_total_table').tableExport({
            type:'excel',
            escape:'false',
            fileName: beginTime + '至'+ endTime + '项目投入统计'
        });


    });


    <!-- 建立表头 -->
    function build_thead(year, beginMonth, endMonth) {
        $("<thead ></thead>").attr("id", "thead").appendTo($("#project_input_total_table"));
        var tr = $("<tr></tr>");
        $("<th></th>").append("<div class='th_item'>姓名<div>").appendTo(tr);
        $("<th></th>").append("<div class='th_item'>级别<div>").appendTo(tr);
        for (var i = beginMonth; i <= endMonth; i++) {
            $("<th></th>").append("<div class='th_item'>"+year + "/" + i+"<div>").appendTo(tr);
        }

        tr.appendTo($("#thead"));
    }




//设置th的宽度
    function setThWidth() {

// var tdList=document.getElementById("#build_tbody_third").querySelector("td");
        var tdList=document.querySelectorAll("td");
        // console.log(tdList[0]);
        var wd=tdList[0].offsetWidth
        var thList=document.querySelectorAll(".th_item");

        for (var i=0;i<thList.length;i++){
            thList[i].style.width=wd;
            // console.log(wd);

        }







    }

    //监听窗口变化
    window.onresize=function (ev) {
        setThWidth()

    }


    //监听滚动条的变化
    window.onscroll = function () {
        var sl = -Math.max(document.body.scrollLeft, document.documentElement.scrollLeft);
        // console.log(sl);
        document.getElementById('thead').style.left = sl + 'px';
    }

    <!-- 获取用户名以及等级 -->
    function build_tbody_once() {
        $.ajax({
            type: 'get',
            url: 'ProjectInputTotalController.do?getuserlist',
            async: false,
            cache: false,
            success: function (result) {
                var r = JSON.parse(result);
                var list = r.obj;
                $("<tbody></tbody>").attr("id", "tbody-one").appendTo($("#project_input_total_table"));
                $.each(list, function (index, item) {
                    $("<tr></tr>").attr("id", item.id).append($("<td></td>").append("<div class='td_item'>"+item.userName+"</div>")).append($("<td></td>").append("<div class='td_item'>"+item.level+"</div>")).appendTo($("#tbody-one"));

                    for (var i = beginMonth; i <= endMonth; i++) {
                        $("<td></td>").attr("id", item.id + "-" + i).text("0").appendTo($("#" + item.id));
                    }

                })



                setThWidth();


            }
        })
    }

    <!-- 构建个人信息模块 -->
    function build_tbody_twice(projectCode, beginMonth, endMonth) {
        $.ajax({
            type: 'get',
            url: 'ProjectInputTotalController.do?getworkhourinfo',
            data: {
                'projectCode': projectCode,
                'year': parseInt(beginTime.split("-")[0]),
                'beginMonth': beginMonth,
                'endMonth': endMonth
            },
            async: false,
            cache: false,
            success: function (result) {
                var r = JSON.parse(result);
                var list = r.obj;
                $.each(list, function (index1, item1) {
                    if (!isEmpty(item1)){
                        $.each(item1, function (index2, item2) {
                            $("#" + item2.id + "-" + index1).text(item2.time);
                        })
                    }
                })
            }
        })
    }

    <!-- 构建等级信息模块 -->
    function build_tbody_third(code, beginMonth, endMonth) {
        $("<tbody></tbody>").attr("id", "tbody-two").appendTo($("#project_input_total_table"));
        $("<tr></tr>").attr("id", "E6").appendTo($("#tbody-two"));
        $("<tr></tr>").attr("id", "E5").appendTo($("#tbody-two"));
        $("<tr></tr>").attr("id", "E4").appendTo($("#tbody-two"));
        $("<tr></tr>").attr("id", "E3").appendTo($("#tbody-two"));
        $("<tr></tr>").attr("id", "E2").appendTo($("#tbody-two"));
        $("<tr></tr>").attr("id", "E1").appendTo($("#tbody-two"));
        $("<tr></tr>").attr("id", "NE").appendTo($("#tbody-two"));
        $("<tr></tr>").attr("id", "开发部").appendTo($("#tbody-two"));
        $("<tr></tr>").attr("id", "测试部").appendTo($("#tbody-two"));
        $("<tr></tr>").attr("id", "系统部").appendTo($("#tbody-two"));

        $("<td></td>").attr("id", "total-E6").appendTo($("#E6"));
        $("<td></td>").attr("id", "total-E5").appendTo($("#E5"));
        $("<td></td>").attr("id", "total-E4").appendTo($("#E4"));
        $("<td></td>").attr("id", "total-E3").appendTo($("#E3"));
        $("<td></td>").attr("id", "total-E2").appendTo($("#E2"));
        $("<td></td>").attr("id", "total-E1").appendTo($("#E1"));
        $("<td></td>").attr("id", "total-NE").appendTo($("#NE"));
        $("<td></td>").attr("id", "total-开发部").appendTo($("#开发部"));
        $("<td></td>").attr("id", "total-测试部").appendTo($("#测试部"));
        $("<td></td>").attr("id", "total-系统部").appendTo($("#系统部"));


        $("<td></td>").append("E6").appendTo($("#E6"));
        $("<td></td>").append("E5").appendTo($("#E5"));
        $("<td></td>").append("E4").appendTo($("#E4"));
        $("<td></td>").append("E3").appendTo($("#E3"));
        $("<td></td>").append("E2").appendTo($("#E2"));
        $("<td></td>").append("E1").appendTo($("#E1"));
        $("<td></td>").append("NE").appendTo($("#NE"));
        $("<td></td>").append("开发部").appendTo($("#开发部"));
        $("<td></td>").append("测试部").appendTo($("#测试部"));
        $("<td></td>").append("系统部").appendTo($("#系统部"));

        for (var i = beginMonth; i <= endMonth; i++) {
            $("<td></td>").attr("id", "E6-" + i).appendTo($("#E6"));
            $("<td></td>").attr("id", "E5-" + i).appendTo($("#E5"));
            $("<td></td>").attr("id", "E4-" + i).appendTo($("#E4"));
            $("<td></td>").attr("id", "E3-" + i).appendTo($("#E3"));
            $("<td></td>").attr("id", "E2-" + i).appendTo($("#E2"));
            $("<td></td>").attr("id", "E1-" + i).appendTo($("#E1"));
            $("<td></td>").attr("id", "NE-" + i).appendTo($("#NE"));
            $("<td></td>").attr("id", "开发部-" + i).appendTo($("#开发部"));
            $("<td></td>").attr("id", "测试部-" + i).appendTo($("#测试部"));
            $("<td></td>").attr("id", "系统部-" + i).appendTo($("#系统部"));
        }

        $.ajax({
            type: 'get',
            url: 'ProjectInputTotalController.do?getworkhourinfobylevel',
            data: {
                'projectCode': code,
                'year': parseInt(beginTime.split("-")[0]),
                'beginMonth': beginMonth,
                'endMonth': endMonth
            },
            async: false,
            cache: false,
            success: function (result) {
                var r = JSON.parse(result);
                var list = r.obj;
                $.each(list, function (index1, item1) {
                    var LevelList = item1;

                    $.each(LevelList, function (index2, item2) {
                        $("#" + index2 + "-" + index1).text(item2);
                    })
                })
            }
        })


        var E6TrList = $("#E6").find("td");
        var E5TrList = $("#E5").find("td");
        var E4TrList = $("#E4").find("td");
        var E3TrList = $("#E3").find("td");
        var E2TrList = $("#E2").find("td");
        var E1TrList = $("#E1").find("td");
        var NETrList = $("#NE").find("td");
        var developTrList = $("#开发部").find("td");
        var testTrList = $("#测试部").find("td");
        var systemTrList = $("#系统部").find("td");

        var E6 = 0;
        var E5 = 0;
        var E4 = 0;
        var E3 = 0;
        var E2 = 0;
        var E1 = 0;
        var NE = 0;
        var develop = 0;
        var test = 0;
        var system = 0;


        for (var e6 = 2; e6 < E6TrList.length; e6++) {
            E6 += parseFloat(E6TrList.eq(e6).text());
        }
        for (var e5 = 2; e5 < E5TrList.length; e5++) {
            E5 += parseFloat(E5TrList.eq(e5).text());
        }
        for (var e4 = 2; e4 < E4TrList.length; e4++) {
            E4 += parseFloat(E4TrList.eq(e4).text());
        }
        for (var e3 = 2; e3 < E3TrList.length; e3++) {
            E3 += parseFloat(E3TrList.eq(e3).text());
        }
        for (var e2 = 2; e2 < E2TrList.length; e2++) {
            E2 += parseFloat(E2TrList.eq(e2).text());
        }
        for (var e1 = 2; e1 < E1TrList.length; e1++) {
            E1 += parseFloat(E1TrList.eq(e1).text());
        }
        for (var ne = 2; ne < NETrList.length; ne++) {
            NE += parseFloat(NETrList.eq(ne).text());
        }
        for (var d = 2; d < developTrList.length; d++) {
            develop += parseFloat(developTrList.eq(d).text());
        }
        for (var t = 2; t < testTrList.length; t++) {
            test += parseFloat(testTrList.eq(t).text());
        }
        for (var s = 2; s < systemTrList.length; s++) {
            system += parseFloat(systemTrList.eq(s).text());
        }

        develop = develop.toFixed(2);
        test = test.toFixed(2);
        system = system.toFixed(2);

        $("#total-E6").text(E6);
        $("#total-E5").text(E5);
        $("#total-E4").text(E4);
        $("#total-E3").text(E3);
        $("#total-E2").text(E2);
        $("#total-E1").text(E1);
        $("#total-NE").text(NE);
        $("#total-开发部").text(develop);
        $("#total-测试部").text(test);
        $("#total-系统部").text(system);


    }

    function build_tbody_fourth(beginMonth, endMonth) {
        var E6TrList = $("#E6").find("td");
        var E5TrList = $("#E5").find("td");
        var E4TrList = $("#E4").find("td");
        var E3TrList = $("#E3").find("td");
        var E2TrList = $("#E2").find("td");
        var E1TrList = $("#E1").find("td");
        var NETrList = $("#NE").find("td");



        $("<tbody></tbody>").attr("id", "tbody-three").appendTo($("#project_input_total_table"));
        $("<tr></tr>").attr("id", "total").appendTo($("#tbody-three"));

        var total = 0;
        total += parseFloat(E6TrList.eq(0).text());
        total += parseFloat(E5TrList.eq(0).text());
        total += parseFloat(E4TrList.eq(0).text());
        total += parseFloat(E3TrList.eq(0).text());
        total += parseFloat(E2TrList.eq(0).text());
        total += parseFloat(E1TrList.eq(0).text());
        total += parseFloat(NETrList.eq(0).text());

        $("<td></td>").text(total).appendTo($("#total"));
        $("<td></td>").appendTo($("#total"));


        for (var i = beginMonth; i <= endMonth; i++) {
            $("<td></td>").attr("id","total-"+i).appendTo($("#total"));
        }

        var count = 2;
        for (var j = beginMonth; j<= endMonth;j++){
            var monthTotal = 0;
            monthTotal+=parseFloat(E6TrList.eq(count).text());
            monthTotal+=parseFloat(E5TrList.eq(count).text());
            monthTotal+=parseFloat(E4TrList.eq(count).text());
            monthTotal+=parseFloat(E3TrList.eq(count).text());
            monthTotal+=parseFloat(E2TrList.eq(count).text());
            monthTotal+=parseFloat(E1TrList.eq(count).text());
            monthTotal+=parseFloat(NETrList.eq(count).text());

            $("#total-"+j).text(monthTotal);
            count++;
        }

    }

</script>
</body>
</html>
