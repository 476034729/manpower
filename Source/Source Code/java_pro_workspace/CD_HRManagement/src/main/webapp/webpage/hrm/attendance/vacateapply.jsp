<%--
  Created by IntelliJ IDEA.
  User: Public_Stee_Develope
  Date: 2018/8/2
  Time: 14:44
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
<div>
    <button class="btn btn-primary" id="add" style="float: right;margin-right: 20px">添加申请</button>
</div>
<table class="table table-bordered" id="vacation_apply_table" text-align="center">
    <thead>
    <tr>
        <th>申请人</th>
        <th>类型</th>
        <th>开始时间</th>
        <th>结束时间</th>
        <th>状态</th>
    </tr>
    </thead>
    <tbody id="tbody">

    </tbody>

</table>

<!-- EditModal -->
<div class="modal fade" id="edit_vacationApply_modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="edit_myModalLabel">换休申请表</h4>
            </div>
            <div class="modal-body">
                <!-- 申请表表单 -->
                <form action="###" method="post" style="height: 84%;" id="edit_vacationApply_form">
                    <table border="1" cellspacing="0" cellpadding="0" id="edit_restTable" class="rest_table">

                        <tr height="40px">
                            <td class="noneborderright">姓&nbsp;&nbsp;名：</td>
                            <td class="noneborderboth"><input type="text" id="edit_applyName" class="apply_name" disabled/>
                            </td>
                            <td class="noneborderboth">部&nbsp;&nbsp;门：</td>
                            <td class="noneborderleft"><input type="text" id="edit_departName" class="depart_name apply_name"
                                                              style="width:156px;" disabled/></td>
                        </tr>
                        <tr height="40">
                            <td class="noneborderright">申请休假类型：</td>
                            <td colspan="3" class="noneborderleft">
                                <select id="edit_vacationType" class="vacation_type"
                                        style="width:429px;height:24px;">
                                    <option value="personal">事假</option>
                                    <option value="sick">病假</option>
                                    <option value="year">年假</option>
                                    <option value="marriage">婚假</option>
                                    <option value="funeral">丧假</option>
                                    <option value="vacation">换休</option>
                                </select>
                            </td>
                        </tr>
                        <tr height="40px">
                            <td class="noneborderright">请假开始时间：</td>
                            <td class="noneborderboth">
                                <div>
                                    <input type="text" id="edit_beginTime">
                                </div>
                            </td>
                            <td class="noneborderboth">请假结束时间：</td>
                            <td class="noneborderleft">
                                <div>
                                    <input type="text" id="edit_endTime">
                                </div>
                            </td>
                        </tr>
                        <tr height="40px">
                            <td class="noneborderright">共计天数：</td>
                            <td class="noneborderboth">
                                <input type="text" id="edit_totalDays">
                            </td>
                            <td class="noneborderboth" id="edit_residueTd" hidden>剩余天数：</td>
                            <td class="noneborderleft" id="edit_residueDay" hidden>
                                <span id="edit_havedays"></span>
                            </td>

                        </tr>
                        <tr height="140px">
                            <td class="noneborderright">请假理由：</td>
                            <td colspan="3" class="noneborderleft">
                                <textarea id="edit_restReason" class="rest_reason"></textarea>
                            </td>
                        </tr>
                        <tr height="200px">
                            <td colspan="4" style="margin-top:-9px;" id="edit_site">
                                <div>休假地点：</div>
                                <div style="margin-left:15px">
                                    <input type="radio" name="where" value="inside" class="in_china"/>
                                    <span>国内</span>
                                    <span style="margin-left:15px">联系方式：</span>
                                    <input type="text" id="edit_contact" class="contact_me" value=""/>
                                </div>
                                <div style="margin-left:15px">
                                    <input type="radio" name="where" value="outside" class="out_china"/>
                                    <span>国外</span>
                                    <span style="margin-left:15px">请说明：</span>
                                    <input type="text" id="edit_details" class="details_me contact_me" value=""/>
                                </div>
                                <div>
                                    <span style="display: block;margin-left:27px;margin-top:9px;">如休假累计天数超过公司所规定之天数，超出部分将被取消或视作“无薪假”</span>
                                </div>
                                <div style="margin-top:9px;margin-left:140px;">
                                    <span class="sign">申请者签字：</span>
                                    <input type="text" id="edit_signer" class="signer_me contact_me" value=""
                                           style="width:138px"/>
                                    <span style="margin:0 12px">日期:</span>
                                    <span id="edit_gainTime"></span>
                                </div>
                            </td>
                        </tr>
                        <tr height="200px">
                            <td colspan="4">
                                <div style="margin-top:-20px;">审批者签字：</div>
                                <div style="margin-top:30px;margin-left:40px;">
                                    <span class="leader">直属上司：</span>
                                    <input type="radio" name="leader" id="edit_leaderAllowYes" checked="checked"
                                           style="margin-left:15px;"/>
                                    <span>同意</span>
                                    <input type="radio" name="leader" id="edit_leaderAllowNo" style="margin-left:35px;"/>
                                    <span>不同意</span>
                                    <span class="deal" style="margin-left:35px;">处理意见：</span>
                                    <input type="text" id="edit_leaderDeal" class="leader_deal" style="margin-left:35px;"/>
                                </div>
                                <div style="margin-top:40px;margin-left:40px;">
                                    <span class="leader">部门经理：</span>
                                    <input type="radio" name="depart" id="edit_departAllowYes" checked="checked"
                                           style="margin-left:15px;"/>
                                    <span>同意</span>
                                    <input type="radio" name="depart" id="edit_departAllowNo" style="margin-left:35px;"/>
                                    <span>不同意</span>
                                    <span class="deal" style="margin-left:35px;">处理意见：</span>
                                    <input type="text" id="edit_dealWith" class="leader_deal" style="margin-left:35px;"/>
                                </div>
                            </td>

                        </tr>

                    </table>
                </form>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary" id="edit_button">提交</button>
            </div>
        </div>
    </div>
</div>

<!-- AddModal -->
<div class="modal fade" id="add_vacationApply_modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="myModalLabel">换休申请表</h4>
            </div>
            <div class="modal-body">
                <!-- 申请表表单 -->
                <form action="###" method="post" style="height: 84%;" id="add_vacationApply_form">
                    <table border="1" cellspacing="0" cellpadding="0" id="restTable" class="rest_table">

                        <tr height="40px">
                            <td class="noneborderright">姓&nbsp;&nbsp;名：</td>
                            <td class="noneborderboth"><input type="text" id="applyName" class="apply_name" disabled/>
                            </td>
                            <td class="noneborderboth">部&nbsp;&nbsp;门：</td>
                            <td class="noneborderleft"><input type="text" id="departName" class="depart_name apply_name"
                                                              style="width:156px;" disabled/></td>
                        </tr>
                        <tr height="40">
                            <td class="noneborderright">申请休假类型：</td>
                            <td colspan="3" class="noneborderleft">
                                <select id="vacationType" class="vacation_type"
                                        style="width:429px;height:24px;">
                                    <option value="personal">事假</option>
                                    <option value="sick">病假</option>
                                    <option value="year">年假</option>
                                    <option value="marriage">婚假</option>
                                    <option value="funeral">丧假</option>
                                    <option value="vacation">换休</option>
                                </select>
                            </td>
                        </tr>
                        <tr height="40px">
                            <td class="noneborderright">请假开始时间：</td>
                            <td class="noneborderboth">
                                <div>
                                    <input type="datetime-local" id="beginTime">
                                </div>
                            </td>
                            <td class="noneborderboth">请假结束时间：</td>
                            <td class="noneborderleft">
                                <div>
                                    <input type="text" id="endTime">
                                </div>
                            </td>
                        </tr>
                        <tr height="40px">
                            <td class="noneborderright">共计天数：</td>
                            <td class="noneborderboth">
                                <input type="text" id="totalDays">
                            </td>
                            <td class="noneborderboth" id="residueTd" hidden>剩余天数：</td>
                            <td class="noneborderleft" id="residueDay" hidden>
                                <span id="havedays"></span>
                            </td>

                        </tr>
                        <tr height="140px">
                            <td class="noneborderright">请假理由：</td>
                            <td colspan="3" class="noneborderleft">
                                <textarea id="restReason" class="rest_reason"></textarea>
                            </td>
                        </tr>
                        <tr height="200px">
                            <td colspan="4" style="margin-top:-9px;" id="site">
                                <div>休假地点：</div>
                                <div style="margin-left:15px">
                                    <input type="radio" name="where" value="inside" class="in_china"/>
                                    <span>国内</span>
                                    <span style="margin-left:15px">联系方式：</span>
                                    <input type="text" id="contact" class="contact_me" value=""/>
                                </div>
                                <div style="margin-left:15px">
                                    <input type="radio" name="where" value="outside" class="out_china"/>
                                    <span>国外</span>
                                    <span style="margin-left:15px">请说明：</span>
                                    <input type="text" id="details" class="details_me contact_me" value=""/>
                                </div>
                                <div>
                                    <span style="display: block;margin-left:27px;margin-top:9px;">如休假累计天数超过公司所规定之天数，超出部分将被取消或视作“无薪假”</span>
                                </div>
                                <div style="margin-top:9px;margin-left:140px;">
                                    <span class="sign">申请者签字：</span>
                                    <input type="text" id="signer" class="signer_me contact_me" value=""
                                           style="width:138px"/>
                                    <span style="margin:0 12px">日期:</span>
                                    <span id="gainTime"></span>
                                </div>
                            </td>
                        </tr>
                        <tr height="200px">
                            <td colspan="4">
                                <div style="margin-top:-20px;">审批者签字：</div>
                                <div style="margin-top:30px;margin-left:40px;">
                                    <span class="leader">直属上司：</span>
                                    <input type="radio" name="leader" id="leaderAllowYes" checked="checked"
                                           style="margin-left:15px;"/>
                                    <span>同意</span>
                                    <input type="radio" name="leader" id="leaderAllowNo" style="margin-left:35px;"/>
                                    <span>不同意</span>
                                    <span class="deal" style="margin-left:35px;">处理意见：</span>
                                    <input type="text" id="leaderDeal" class="leader_deal" style="margin-left:35px;"/>
                                </div>
                                <div style="margin-top:40px;margin-left:40px;">
                                    <span class="leader">部门经理：</span>
                                    <input type="radio" name="depart" id="departAllowYes" checked="checked"
                                           style="margin-left:15px;"/>
                                    <span>同意</span>
                                    <input type="radio" name="depart" id="departAllowNo" style="margin-left:35px;"/>
                                    <span>不同意</span>
                                    <span class="deal" style="margin-left:35px;">处理意见：</span>
                                    <input type="text" id="dealWith" class="leader_deal" style="margin-left:35px;"/>
                                </div>
                            </td>

                        </tr>

                    </table>
                </form>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary" id="add_button">提交</button>
            </div>
        </div>
    </div>
</div>


<script type="text/javascript" src="plug-in/jquery/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="plug-in/jquery/jquery.cookie.js"></script>
<script type="text/javascript" src="plug-in/mutiLang/en.js"></script>
<script type="text/javascript" src="plug-in/mutiLang/zh-cn.js"></script>
<script type="text/javascript" src="plug-in/login/js/jquery.tipsy.js"></script>
<script type="text/javascript" src="plug-in/login/js/iphone.check.js"></script>
<script type="text/javascript" src="plug-in/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="plug-in/datetimepicker/js/bootstrap-datetimepicker.min.js"></script>
<script>

    var userName = '';
    $(document).ready(function () {
        var now = new Date();
        $("#beginTime").datetimepicker();
        $("#endTime").datetimepicker();
        $("#edit_beginTime").datetimepicker();
        $("#edit_endTime").datetimepicker();
        $.ajax({
            type: 'get',
            url: 'VacationApplyController.do?getuseronline',
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
                $("#applyName").val(list.userName);
                $("#departName").val(list.department);
                $("#gainTime").append(timestampToTime(now.getTime()));
                $("#edit_applyName").val(list.userName);
                $("#edit_departName").val(list.department);
                $("#edit_gainTime").append(timestampToTime(now.getTime()));
            }
        });
        getVacationApplyList();
    });


    <!-- 获取申请列表 -->
    function getVacationApplyList() {
        $.ajax({
            type: 'get',
            url: 'VacationApplyController.do?getapplylist',
            async: false,
            cache: false,
            success: function (result) {
                var r = JSON.parse(result);
                var list = r.obj;
                $("#tbody").empty();
                $.each(list, function (index, item) {
                    var tr = $("<tr></tr>");
                    $("<td></td>").append(userName).appendTo(tr);
                    $("<td></td>").append(getType(item.type)).appendTo(tr);
                    $("<td></td>").append(timestampToTimeWithHour(item.beginTime)).appendTo(tr);
                    $("<td></td>").append(timestampToTimeWithHour(item.endTime)).appendTo(tr);
                    $("<td></td>").append(getStatus(item.status, item.id)).appendTo(tr);
                    tr.appendTo($("#tbody"));
                })
            }
        })
    }

    $("#vacationType").change(function () {
        var type = $("#vacationType").val();
        if (type != null && type == 'vacation') {
            $("#residueTd").show();
            $("#residueDay").show();
        } else {
            $("#residueTd").hide();
            $("#residueDay").hide();
        }
    });

    $("#edit_vacationType").change(function () {
        var type = $("#edit_vacationType").val();
        if (type != null && type == 'vacation') {
            $("#edit_residueTd").show();
            $("#edit_residueDay").show();
        } else {
            $("#edit_residueTd").hide();
            $("#edit_residueDay").hide();
        }
    });

    $("#add").click(function () {
        $("#add_vacationApply_modal").modal();
    })

    <!-- 时间戳转日期 -->
    <!-- begin -->
    function timestampToTime(timestamp) {
        var date = new Date(timestamp);//时间戳为10位需*1000，时间戳为13位的话不需乘1000
        Y = date.getFullYear() + '-';
        M = (date.getMonth() + 1) + '-';
        D = date.getDate();
        return Y + M + D;
    }

    function timestampToTimeWithHour(timestamp) {
        var date = new Date(timestamp); //时间戳为10位需*1000，时间戳为13位的话不需乘1000
        Y = date.getFullYear() + '-';
        M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-';
        D = change(date.getDate()) + ' ';
        h = change(date.getHours()) + ':';
        m = change(date.getMinutes()) + ':';
        s = change(date.getSeconds());
        return Y + M + D + h + m + s;
    }

    function change(t) {
        if (t < 10) {
            return "0" + t;
        } else {
            return t;
        }
    }

    <!-- end -->


    <!-- 获得请假类型 -->
    function getType(t) {
        if (t == 'personal') {
            return "事假";
        }
        if (t == 'sick') {
            return "病假";
        }
        if (t == 'year') {
            return "年假";
        }
        if (t == 'marriage') {
            return "婚假";
        }
        if (t == 'funeral') {
            return "丧假";
        }
        if (t == 'vacation') {
            return "换休";
        }
    }

    <!-- 获得审批状态 -->
    function getStatus(t, id) {
        if (t == '0') {
            return '<a href=javascript:edit("'+id+'") style="color: red">编辑</a>';
        }
        if (t == '1') {
            return "通过";
        }
        if (t == '2') {
            return "打回";
        }
        if (t == '3') {
            return "审核中";
        }
    }

    $("#add_button").click(function () {
        var type = $("#vacationType").val();
        var beginTime = Date.parse($("#beginTime").val());
        var endTime = Date.parse($("#endTime").val());
        alert(beginTime);
        if (beginTime >= endTime) {
            alert("结束时间应在开始时间之前");
            return;
        }
        var reason = $("#restReason").val();
        var site = $("#site input[name='where']:checked").val();
        var signature = $("#signer").val();
        var phone = $("#contact").val();
        var totalDay = $("#totalDays").val();
        var remark = $("#details").val();
        if ($.trim(type) == null || $.trim(type) == 0) {
            alert("请选择类型");
            return;
        }
        if ($.trim(beginTime) == null || $.trim(beginTime) == 0) {
            alert("请选择开始时间");
            return;
        }
        if ($.trim(endTime) == null || $.trim(endTime) == 0) {
            alert("请选择结束时间");
            return;
        }
        if ($.trim(reason) == null || $.trim(reason) == 0) {
            alert("请输入请假原因");
            return;
        }
        if ($.trim(phone) == null || $.trim(phone) == 0) {
            alert("请输入联系方式");
            return;
        }
        if ($.trim(totalDay) == null || $.trim(totalDay) == 0) {
            alert("请输入总计天数");
            return;
        }
        if ($.trim(site) == null || $.trim(site) == 0) {
            alert("请选择请假地点");
            return;
        }
        if ($.trim(signature) == null || $.trim(signature) == 0) {
            alert("请签字");
            return;
        }

        $.ajax({
            type: 'post',
            url: 'VacationApplyController.do?add',
            data: {
                'reason': reason,
                'begin': beginTime,
                'end': endTime,
                'type': type,
                'site': site,
                'signature': signature,
                'totalTime': totalDay,
                'phone': phone,
                'remark': remark
            },
            async: false,
            cache: false,
            success: function (result) {
                var r = JSON.parse(result);
                if (r.success == true) {
                    alert(r.msg);
                    $("#add_vacationApply_modal").modal('hide');
                    getVacationApplyList();
                }
            }
        })
    })



    $("#edit_button").click(function () {
        var type = $("#edit_vacationType").val();
        var beginTime = Date.parse($("#edit_beginTime").val());
        var endTime = Date.parse($("#edit_endTime").val());
        if (beginTime >= endTime) {
            alert("结束时间应在开始时间之前");
            return;
        }
        var reason = $("#edit_restReason").val();
        var site = $("#edit_site input[name='where']:checked").val();
        var signature = $("#edit_signer").val();
        var phone = $("#edit_contact").val();
        var totalDay = $("#edit_totalDays").val();
        var remark = $("#edit_details").val();

        var id =$("#edit_button").attr("data-id");
        var department = $("#edit_departName").val();

        if ($.trim(type) == null || $.trim(type) == 0) {
            alert("请选择类型");
            return;
        }
        if ($.trim(beginTime) == null || $.trim(beginTime) == 0) {
            alert("请选择开始时间");
            return;
        }
        if ($.trim(endTime) == null || $.trim(endTime) == 0) {
            alert("请选择结束时间");
            return;
        }
        if ($.trim(reason) == null || $.trim(reason) == 0) {
            alert("请输入请假原因");
            return;
        }
        if ($.trim(phone) == null || $.trim(phone) == 0) {
            alert("请输入联系方式");
            return;
        }
        if ($.trim(totalDay) == null || $.trim(totalDay) == 0) {
            alert("请输入总计天数");
            return;
        }
        if ($.trim(site) == null || $.trim(site) == 0) {
            alert("请选择请假地点");
            return;
        }
        if ($.trim(signature) == null || $.trim(signature) == 0) {
            alert("请签字");
            return;
        }

        $.ajax({
            type: 'post',
            url: 'VacationApplyController.do?update',
            data: {
                'id':id,
                'department':department,
                'reason': reason,
                'begin': beginTime,
                'end': endTime,
                'type': type,
                'site': site,
                'signature': signature,
                'totalTime': totalDay,
                'phone': phone,
                'remark': remark
            },
            async: false,
            cache: false,
            success: function (result) {
                var r = JSON.parse(result);
                if (r.success == true) {
                    alert(r.msg);
                    $("#edit__vacationApply_modal").modal('hide');
                    getVacationApplyList();
                }else {
                    alert(r.msg);
                    $("#edit__vacationApply_modal").modal('hide');
                    getVacationApplyList();
                }
                $("#edit_button").remove("data-id");
            }
        })
    })


    <!-- 编辑按钮事件 -->
    function edit(id) {
        $.ajax({
            type: 'get',
            url: 'VacationApplyController.do?getapplybyid',
            data:{
                'id':id
            },
            async: false,
            cache: false,
            success: function (result) {
                var r = JSON.parse(result);
                var list = r.obj;
                $("#edit_vacationType").val(list.type);
                $("#edit_beginTime").val(timestampToTimeWithHour(list.beginTime));
                $("#edit_endTime").val(timestampToTimeWithHour(list.endTime));
                $("#edit_totalDays").val(list.totalTime);
                $("#edit_restReason").val(list.reason);
                $("#edit_contact").val(list.phone);
                $("#edit_details").val(list.remark);
                $("#edit_signer").val(list.signature);
                $("#edit_site input[name='where'][value="+list.site+"]").prop("checked",true);
                $("#edit_button").attr("data-id",id);
                $("#edit_vacationApply_modal").modal();
            }
        })
    }
</script>
</body>
</html>
