<%--
  Created by IntelliJ IDEA.
  User: tangzhen
  Date: 2019/7/17
  Time: 13:35
  To change this template use File | Settings | File Templates.
--%>
<%--
  Created by IntelliJ IDEA.
  User: 52629
  Date: 2018/11/2
  Time: 17:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html >
<head>
    <title>Title</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <meta charset="utf-8"/>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <!--
    <link rel="stylesheet" type="text/css" href="styles.css">
    -->
    <link rel="stylesheet" href="plug-in/jquery-easyui-1.5.3/themes/gray/easyui.css" />
    <link rel="stylesheet" href="plug-in/jquery-easyui-1.5.3/themes/icon.css" />
    <link rel="stylesheet" href="plug-in/ace/css/bootstrap.css"/>
    <link rel="stylesheet" href="plug-in/ace/css/font-awesome.css"/>
    <link rel="stylesheet" type="text/css" href="plug-in/accordion/css/accordion.css">
    <link rel="stylesheet" type="text/css" href="plug-in/hrm/css/resttime.css">
    <link rel="stylesheet" type="text/css" href="plug-in/datetimepicker/css/bootstrap-datetimepicker.min.css">
    <!-- text fonts -->
    <link rel="stylesheet" href="plug-in/ace/css/ace-fonts.css"/>
    <script src="plug-in/jquery-easyui-1.5.3/jquery.min.js"></script>
    <script src="plug-in/jquery-easyui-1.5.3/jquery.easyui.min.js"></script>
    <script src="plug-in/jquery-easyui-1.5.3/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="plug-in/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="plug-in/datetimepicker/js/bootstrap-datetimepicker.min.js"></script>
    <style>
        .datagrid-header-row td{background-color:#9AC3E1;color:black;height:32px;}
        .operate{
            border: black;
            color: #0b6cbc;
        }
        a:hover{
            color:red;
        }

        #toolbar{
            float: left;
        }
        .btn-primary{
            width: 80px;
            height: 35px;
            background: #0e78c9;
            color: white;
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

<body>
<table id="table" width="100%" height="95%" title="出差申请查询"></table>
<div id="toolbar" >
    <span for="status">费用报销</span>
    <span >申请日期</span>
    <input type="date" id="startDate" name="startDate" style="height:25px;">-
    <input type="date" id="endDate" name="endDate" style="height:25px;">
    <a onclick="query()" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true">查询</a>
    <button class="btn-primary" id="add"><a  href="reimburseController.do?addReim" style="text-decoration: none;color: white">添加申请</a></button>
</div>



<script>


    var userId;

    $(document).ready(function () {
        getinfo();
        gettabledate();

        //$("#gainTime").append(time);
    });

    function getinfo(){
        $.ajax({
            type: 'get',
            url: 'attendance.do?getuseronline',
            async: false,
            cache: false,
            success: function (result) {
                var r = JSON.parse(result);
                if (!r.success) {
                    alert(r.msg);
                    return;
                }
                var list = r.obj;
                userName = list.userName;
                userId=list.id
                $("#userName").val(list.userName);
                $("#dept").val(list.department);
                $("#jobNo").val(list.jobNum);
            }
        });
        var now = new Date();
        var time = timestampToTime(now.getTime());
        $("#gainTime").val(time);
        $("#currentDate").val(time);
    }

    function gettabledate(){
        //配置datagrid
        $("#table").datagrid({loadFilter:pagerFilter}).datagrid({
            url:"reimburseController.do?getReimburseList&userId="+userId,
            method:"post",
            pagination:true,
            pageSize:10,
            pageList:[10,20,30,40,50],
            singleSelect:true,
            toolbar:"#toolbar",
            columns:[[{
                field:"id",
                title:"编号",
                hidden:true
                //sortable:true
            },{
                field:"username",
                title:"姓名",
                width:100,
                align:"center"
            },{
                field:"jobNum",
                title:"工号",
                width:180,
                align:"center"
            }, {
                field:"applyDate",
                title:"申请时间",
                width:150,
                align:"center",
                formatter:function (value) {
                    return timestampToTime2(value);
                }
            }, {
                field:"startTime",
                title:"出差开始时间",
                width:150,
                align:"center",
                formatter:function (value) {
                    return timestampToTime2(value);
                }
            },{
                field:"endTime",
                title:"出差结束时间",
                width:150,
                align:"center",
                formatter:function (value) {
                    return timestampToTime2(value);                 }
            },{
                field:"projectName",
                title:"实际项目",
                width:100,
                align:"center"
            },{
                field:"expendName",
                title:"支出项目",
                width:100,
                align:"center"
            },{
                field:"costCenterCode",
                title:"成本中心号",
                width:100,
                align:"center"
            },{
                field:"countryName",
                title:"出差地点",
                width:180,
                align:"center"
            },{
                field:"_operate",
                title:"操作",
                width:190,
                align:"center",
                formatter:formatOper
            }]]
        });
    }
    function formatOper(val,row,index) {

        var ss = '<a class="operate btn btn-primary btn-xs" style="cursor:pointer;height: 25px;width: 60px;" href="reimburseController.do?getReimburseDetails&reimburseId='+row.id+'" >编辑 </a> ';
        ss = ss + ' <a class="operate btn btn-primary btn-xs" style="cursor:pointer;height: 25px;width: 60px;" href="reimburseController.do?exportEXL&reimburseId='+row.id+'" >导出 </a>';        ss = ss + ' <a class="operate btn btn-primary btn-xs" style="cursor:pointer;height: 25px;width: 50px;" href="reimburseController.do?deleteById&reimburseId='+row.id+'" >删除 </a>';

        return ss;
    }

    // <!-- 时间戳转日期 -->
    // <!-- begin -->
    function timestampToTime(timestamp) {
        var datetime = new Date(timestamp);//时间戳为10位需*1000，时间戳为13位的话不需乘1000
        var year = datetime.getFullYear();
        var month = datetime.getMonth() + 1 < 10 ? "0" + (datetime.getMonth() + 1) : datetime.getMonth() + 1;
        var date = datetime.getDate() < 10 ? "0" + datetime.getDate() : datetime.getDate();
        return year + "-" + month + "-" + date;
    }
    // <!-- 时间戳转日期 -->
    // YYYY-MM-dd HH:mm:ss
    function timestampToTime2(timestamp){
        if(timestamp==null){
            return "";
        }
        var datetime = new Date(timestamp);
        var year = datetime.getFullYear();
        var month = datetime.getMonth() + 1 < 10 ? "0" + (datetime.getMonth() + 1) : datetime.getMonth() + 1;
        var date = datetime.getDate() < 10 ? "0" + datetime.getDate() : datetime.getDate();
        var hour = datetime.getHours()< 10 ? "0" + datetime.getHours() : datetime.getHours();
        var minute = datetime.getMinutes()< 10 ? "0" + datetime.getMinutes() : datetime.getMinutes();
        var second = datetime.getSeconds()< 10 ? "0" + datetime.getSeconds() : datetime.getSeconds();
        return year + "-" + month + "-" + date+" "+hour+":"+minute+":"+second;
    }

    // YYYY-MM-dd HH:mm
    function timestampToTime3(timestamp){
        var datetime = new Date(timestamp);
        var year = datetime.getFullYear();
        var month = datetime.getMonth() + 1 < 10 ? "0" + (datetime.getMonth() + 1) : datetime.getMonth() + 1;
        var date = datetime.getDate() < 10 ? "0" + datetime.getDate() : datetime.getDate();
        var hour = datetime.getHours()< 10 ? "0" + datetime.getHours() : datetime.getHours();
        var minute = datetime.getMinutes()< 10 ? "0" + datetime.getMinutes() : datetime.getMinutes();
        return year + "-" + month + "-" + date+"T"+hour+":"+minute;
    }
    // 分页数据的操作
    function pagerFilter(data) {
        if (typeof data.length == 'number' && typeof data.splice == 'function') {   // is array
            data = {
                total: data.length,
                rows: data
            }
        }
        var dg = $(this);
        var opts = dg.datagrid('options');
        var pager = dg.datagrid('getPager');
        pager.pagination({
            onSelectPage: function (pageNum, pageSize) {
                opts.pageNumber = pageNum;
                opts.pageSize = pageSize;
                pager.pagination('refresh', {
                    pageNumber: pageNum,
                    pageSize: pageSize
                });
                dg.datagrid('loadData', data);
            }
        });
        if (!data.originalRows) {
            data.originalRows = (data.rows);
        }
        var start = (opts.pageNumber - 1) * parseInt(opts.pageSize);
        var end = start + parseInt(opts.pageSize);
        data.rows = (data.originalRows.slice(start, end));
        return data;
    }
    //查询工具栏
    function query(){
        $("#table").datagrid("load",{
            startDate:$("#startDate").val(),
            endDate:$("#endDate").val()
        });
    }
    function exportReim(id) {

    }

</script>
</body>
</html>
