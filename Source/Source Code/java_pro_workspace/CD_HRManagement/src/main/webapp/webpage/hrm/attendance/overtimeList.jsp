<%--
  Created by IntelliJ IDEA.
  User: 52629
  Date: 2018/11/2
  Time: 17:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/context/mytags.jsp"%>
<html>
<head>
    <title>Title</title>
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
    <script src="plug-in/jquery-easyui-1.5.3/jquery.min.js"></script>
    <script src="plug-in/jquery-easyui-1.5.3/jquery.easyui.min.js"></script>
    <script src="plug-in/jquery-easyui-1.5.3/locale/easyui-lang-zh_CN.js"></script>
    <style>
        .datagrid-header-row td{background-color:#9AC3E1;color:black;height:32px;}
        .operate{
            border: black;
            color: #0b6cbc;
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
</head>

<body>
<table id="table" width="100%" height="95%"></table>
<input type="hidden" id="userId" name="userId" value="${user.id}" >
<input type="hidden" id="isResponsPerson" name="isResponsPerson" value="${isResponsPerson}">
<div id="toolbar" style="padding: 5px">
    <label for="status">审批状态</label>
    <select name="status" id="status">
        <option value="">请选择...</option>
        <option value="0">待审批</option>
        <option value="1">通过</option>
        <option value="2">驳回</option>
        <option value="3">审核中</option>
    </select>
    <label for="userName" style="display:${isResponsPerson==0?"none":''}">申请人</label>
    <input type="text" id="userName" name="userName" style="display: ${isResponsPerson==0?"none":''}">
    <label >申请日期</label>
    <input type="date" id="startDate" name="startDate">-
    <input type="date" id="endDate" name="endDate">
    <a onclick="query()" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true">查询</a>
    <button class="btn-primary"><a  href="attendance.do?goovertimeapply" style="text-decoration: none;color: white">添加申请</a></button>
</div>
<script>
    $(document).ready(function () {
        //配置datagrid
        $("#table").datagrid({loadFilter:pagerFilter}).datagrid({
            url:"attendance.do?queryOvertime",
            method:"get",
            pagination:true,
            pageSize:10,
            pageList:[10,20,30,40,50],
            singleSelect:true,
            toolbar:"#toolbar",
            title:"加班申请查询",
            columns:[[{
                field:"id",
                title:"编号",
                hidden:true
                //sortable:true
            },{
                field:"user_name",
                title:"姓名",
                width:100,
                align:"center",
            },{
                field:"create_date",
                title:"申请日期",
                width:100,
                align:"center",
                formatter:function (value) {
                    var datetime = new Date(value);
                    var year = datetime.getFullYear();
                    var month = datetime.getMonth() + 1 < 10 ? "0" + (datetime.getMonth() + 1) : datetime.getMonth() + 1;
                    var date = datetime.getDate() < 10 ? "0" + datetime.getDate() : datetime.getDate();
                    return year + "-" + month + "-" + date;
                }
            },{
                field:"net_meter",
                title:"净计(小时)",
                width:100,
                align:"center",
            },{
                field:"status",
                title:"审核状态",
                width:100,
                align:"center",
                formatter:function(value,rowDate,rowIndex){
                    if(rowDate.status==0){
                        return "待审核";
                    }else if(rowDate.status==1){
                        return "通过";
                    }else if(rowDate.status==2){
                        return "驳回";
                    }else if(rowDate.status==3){
                        return"审核中";
                    }
                }
            },{
                field:"immediate_supervisor_examine",
                title:"直属上司审核状态",
                width:150,
                align:"center",
                formatter:function(value,rowData,rowIndex){
                    if(rowData.immediate_supervisor_examine==0){
                        return "待审核";
                    }else if(rowData.immediate_supervisor_examine==1){
                        return "同意";
                    }else if(rowData.immediate_supervisor_examine==2){
                        return "不同意"
                    }
                }
            },{
                field:"division_manager_examine",
                title:"部门经理审核状态",
                width:150,
                align:"center",
                formatter:function(value,rowData,rowIndex){
                    if(rowData.division_manager_examine==0){
                        return "待审核";
                    }else if(rowData.division_manager_examine==1){
                        return "同意";
                    }else if(rowData.division_manager_examine==2){
                        return "不同意";
                    }else if(rowData.division_manager_examine==4){
                        return "不审核";
                    }
                }
            },{
                field:"user_status",
                title:"申请人确认状态",
                width:150,
                align:"center",
                formatter:function (value,rowDate,rowIndex) {
                    if(rowDate.user_status==0){
                        return "未确认";
                    }else if(rowDate.user_status==1){
                        return "确认";
                    }
                }
            },{
                field:"personnel_matters_status",
                title:"人事状态",
                width:100,
                align:"center",
                formatter:function(value,rowDate,rowIndex){
                    if(rowDate.personnel_matters_status==0){
                        return "未确认";
                    }else if(rowDate.personnel_matters_status==1){
                        return "确认";
                    }
                }
            },{
                field:"_operate",
                title:"操作",
                width:150,
                align:"center",
                formatter:formatOper
            }]]
        });
    });
    function formatOper(val,row,index) {
        var isResponsPerson = $("#isResponsPerson").val();
        var ss = '<a class="operate" href="attendance.do?overtimeApplyDetail&overtimeApplyId='+row.id+'" >详情</a>';
        var userId = $("#userId").val();
        if(row.status==2 && userId==row.user_id){
            ss = ss + ' <a class=\"operate\" href=\"attendance.do?goovertimeapply&overtimeApplyId='+row.id+'\">修改</a>';
        }
        if(userId==row.user_id){
            if(row.user_status==0){
                if(row.immediate_supervisor_examine==1&&(row.division_manager_examine==1||row.division_manager_examine==4)){
                    ss = ss + ' <a class="operate" href="#" onclick="confirmOvertime(\''+row.id+'\')">确认</a>';
                }
            }else if(row.user_status==1){
                ss = ss + ' <a class="operate" href="#">已确认</a>';
            }
        }
        //上级领导审批
        if(isResponsPerson==1){
            if(row.user_status==0){
                if((row.immediate_supervisor_examine==0||row.immediate_supervisor_examine==2)&&row.division_manager_examine!=1&&row.division_manager_examine!=2){
                    if(row.status==2){
                        ss = ss + ' <c:if test="${isResponsPerson!=0&&isResponsPerson!=3}"><a class="operate" href="attendance.do?overtimeApplyExamination&overtimeApplyId='+row.id+'">重审</a></c:if> ';
                    }else{
                        ss = ss + ' <c:if test="${isResponsPerson!=0&&isResponsPerson!=3}"><a class="operate" href="attendance.do?overtimeApplyExamination&overtimeApplyId='+row.id+'">审批</a></c:if> ';
                    }
                }
            }
        }

        //部门经理审批
        if(isResponsPerson==2){
            if(row.user_status==0&&row.copy_type==2&&(row.division_manager_examine==0||row.division_manager_examine==2)){
                if(row.status==2){
                    ss = ss + ' <c:if test="${isResponsPerson!=0&&isResponsPerson!=3}"><a class="operate" href="attendance.do?overtimeApplyExamination&overtimeApplyId='+row.id+'">重审</a></c:if> ';
                }else{
                    ss = ss + ' <c:if test="${isResponsPerson!=0&&isResponsPerson!=3}"><a class="operate" href="attendance.do?overtimeApplyExamination&overtimeApplyId='+row.id+'">审批</a></c:if> ';
                }

            }
        }

        if(row.user_status==1&&row.personnel_matters_status==0){

            ss = ss + '<c:if test="${isResponsPerson==3}"> <a class="operate" href="#" onclick="adminConfirm(\''+row.id+'\')">关闭</a></c:if>';
        }
        return ss;
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
    function  editAsset(id) {
        $('#table').datagrid('selectRow',index);
        var row = $('#table').datagrid('getSelected');
        if(row){
            $('#dlg').dialog('open').dialog('setTitle','修改学生信息');
            $("#fm").form('load',row);
            url = row.id;
        }
    }
    //查询工具栏
    function query(){
        var status = document.getElementById("status");
        var status1 = status.options[status.selectedIndex].value;
        $("#table").datagrid("load",{
            status:status1,
            userName:$("#userName").val(),
            startDate:$("#startDate").val(),
            endDate:$("#endDate").val()
        });
    }
    //删除资产
    function delAsset(id,index) {
        var r = confirm("是否删除？");
        if(r == true){
            $.ajax({
                type: 'get',
                url: 'AssetController.do?delAsset&assetId=' + id,
                async: false,
                cache: false,
                success: function (result) {
                    var resultDate = JSON.parse(result);
                    if(resultDate.msg=='ok'){
                        $('#table').datagrid('deleteRow',index);
                    }
                }
            });
        }else{
            return;
        }

    }
    //用户确认加班
    function confirmOvertime(id) {
        var r = confirm("是否确认加班？确认后将提交给人事。");
        if(r == true) {
            $.ajax({
                type: 'get',
                url: 'attendance.do?userConfirm&overtimeApplyId=' + id,
                async: false,
                cache: false,
                success: function (result) {
                    var resultDate = JSON.parse(result);
                    if (resultDate.msg == 'ok') {
                        window.location.reload();
                    }
                }
            });
        }else{
            return ;
        }
    }
    //人事关闭申请
    function adminConfirm(id) {
        var r = confirm("是否确认关闭？并登记记录。");
        if(r == true){
            $.ajax({
                type:'get',
                url:'attendance.do?adminConfirm&overtimeApplyId='+id,
                async:false,
                cache:false,
                success:function(result){
                    var resultDate = JSON.parse(result);
                    if(resultDate.msg == 'ok'){
                        window.location.reload();
                    }
                }
            });
        }
    }
</script>
</body>
</html>
