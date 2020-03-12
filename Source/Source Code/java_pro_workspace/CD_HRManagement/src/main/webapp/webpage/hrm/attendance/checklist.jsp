<%--
  Created by IntelliJ IDEA.
  User: Public_Stee_Develope
  Date: 2018/8/13
  Time: 14:54
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
    <link rel="stylesheet" type="text/css" href="plug-in/hrm/css/resttime.css">
    <link rel="stylesheet" type="text/css" href="plug-in/datetimepicker/css/bootstrap-datetimepicker.min.css">
    <link rel="stylesheet" type="text/css" href="plug-in/easyui/themes/default/easyui.css">
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
</head>
<body>


<table id="dg" title="审核列表" class="easyui-datagrid" style="height:250px"
       url="ApplyCheckController.do?getchecklist"
       toolbar="#toolbar"
       rownumbers="true" fitColumns="true" singleSelect="true" pagination="true">
    <thead>
    <tr>
        <th field="apply_id" hidden>apply_id</th>
        <th field="id" hidden>id</th>
        <th field="applyPerson" width="20%">申请人</th>
        <th field="beginTime" width="20%">开始时间</th>
        <th field="endTime" width="20%">结束时间</th>
        <th field="type" width="20%">类型</th>
        <th field="status" width="20%">状态</th>
    </tr>
    </thead>
</table>


<div id="toolbar" style="padding:5px;height:auto">
    <div style="margin-bottom:20px">
        <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" style="float: right"
           onclick="javaScript:check()">审核</a>
    </div>
</div>

<div id="win" class="easyui-window" closed="true" title="My Window" style="width:550px;height:450px;padding:5px;">
    <div style="padding:10px 60px 20px 60px">
        <form id="ff" method="post">
            <table cellpadding="5">
                <input id="select_id" type="text" hidden/>
                <tr>
                    <td>部门:</td>
                    <td><input id="department" class="easyui-textbox" type="text" data-options="required:true"
                               disabled/></td>
                </tr>
                <tr>
                    <td>类型:</td>
                    <td><input id="type" class="easyui-textbox" type="text" data-options="required:true" disabled/></td>
                </tr>
                <tr>
                    <td>共计天数:</td>
                    <td><input id="totalDays" class="easyui-textbox" type="text" data-options="required:true" disabled/>
                    </td>
                </tr>
                <tr>
                    <td>地点:</td>
                    <td><input id="site" class="easyui-textbox" type="text" data-options="required:true" disabled/></td>
                </tr>
                <tr>
                    <td>联系方式:</td>
                    <td><input id="phone" class="easyui-textbox" type="text" data-options="required:true" disabled/>
                    </td>
                </tr>
                <tr>
                    <td>请假理由:</td>
                    <td><input id="reason" class="easyui-textbox" data-options="multiline:true" style="height:60px"
                               disabled/></td>
                </tr>
                <tr id ="isAgree">
                    <td><input type="radio" name="agree" value="agree">同意</td>
                    <td><input type="radio" name="agree" value="disagree">不同意</td>
                </tr>

                <tr>
                    <td>处理意见:</td>
                    <td><input id="remark" class="easyui-textbox" data-options="multiline:true" style="height:60px"
                    /></td>
                </tr>

            </table>
        </form>
        <div style="text-align:center;padding:5px">
            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="javaScript:submitCheck()">Submit</a>
        </div>
    </div>
</div>

<script type="text/javascript" src="plug-in/jquery/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="plug-in/jquery/jquery.cookie.js"></script>
<script type="text/javascript" src="plug-in/mutiLang/en.js"></script>
<script type="text/javascript" src="plug-in/mutiLang/zh-cn.js"></script>
<script type="text/javascript" src="plug-in/login/js/jquery.tipsy.js"></script>
<script type="text/javascript" src="plug-in/login/js/iphone.check.js"></script>
<script type="text/javascript" src="plug-in/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="plug-in/bootstrap/js/bootstrap.min.js"></script>
<script>

    $(document).ready(function () {
        //<!-- 加载分页控件 -->
        //<!-- begin -->
        var pager = $('#dg').datagrid('getPager');    // get the pager of datagrid
        pager.pagination({
            showPageList: false,
            onBeforeRefresh: function () {
                return true;
            }
        });
 //       <!-- end -->
    });

    <!-- 审核按钮事件 -->
    function check() {
        var row = $('#dg').datagrid('getSelected');
        if (row) {
            if (row.status == "已审核"){
                alert("已审批!");
                return;
            }
            $.ajax({
                type: 'get',
                url: 'VacationApplyController.do?getapplybyid',
                data: {
                    'id': row.apply_id
                },
                async: false,
                cache: false,
                success: function (result) {
                    var r = JSON.parse(result);
                    var list = r.obj;
                    $("#department").val(list.department);
                    $("#type").val(list.type);
                    $("#totalDays").val(list.totalTime);
                    $("#site").val(list.site);
                    $("#reason").val(list.remark);
                    $("#phone").val(list.phone);
                }
            });
            $("#select_id").val(row.id);
            $('#win').window('open');
        } else {
            alert("请选择选项!");
        }
    }

    <!-- 提交审核事件 -->
    function submitCheck() {
        var id = $("#select_id").val();
        var agree = $("#isAgree input[name='agree']:checked").val();
        var remark = $("#remark").val();
        $.ajax({
            type: 'post',
            url: 'ApplyCheckController.do?check',
            data: {
                'id': id,
                'agree':agree,
                'remark':remark
            },
            async: false,
            cache: false,
            success:function (result) {
                var r = JSON.parse(result);
                alert(r.msg);
                $('#win').window('close');
            }
        })
    }
</script>
</body>
</html>
