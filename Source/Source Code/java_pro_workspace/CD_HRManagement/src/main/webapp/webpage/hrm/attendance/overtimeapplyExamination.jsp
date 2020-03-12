<%--
  Created by IntelliJ IDEA.
  User: 52629
  Date: 2018/11/15
  Time: 10:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/context/mytags.jsp"%>
<html>
<head>
    <title>Title</title>
    <style>
        .parentConnt{
            width: 80%;
            height: 100%;
            margin: 0 auto;
        }
        .tab{
            width: 100%;

        }
        .one{
            width: 20%;
        }
        .two{
            width: 36.5%;
        }
        .three{
            width: 20%;
        }
        .forth{
            width: 23.5%;
        }
        tr{
            border-top:1px solid grey;
            border-left: 1px solid grey;
            border-right: 1px solid grey;
        }
        table{
            border-collapse:collapse;
            border-bottom: 1px solid grey;

        }
        .input1{
            width: 60%;
            height: 35px;
        }
        .input2{
            width: 85%;
            height: 35px;
        }
        a{
            text-decoration: none;
            color: black;
        }
        .btn-primary{
            width: 80px;
            height: 35px;
            background: #0e78c9;
            color: white;
        }
        .btn-danger{
            width: 80px;
            height: 35px;
            background: #8a3104;
            color: white;
        }
        .btn_option{
            text-align: center;
        }
    </style>
</head>
<body>
<p>当前位置><a href="attendance.do?overTimeApplyListPageJump">加班申请列表</a>><a href="#">加班审批</a></p>
<form id="overtimeApplyForm" action="attendance.do?saveOvertimeApplyExamination" onsubmit="return formSubmit();" method="post">
    <input type="hidden" name="roleType" id="roleType" value="${type}">
    <input type="hidden" name="overtimeApplyId" id="overtimeApplyId" value="${overtimeApply.id}">
    <div class="parentConnt">
        <table class="tab">
            <tr style="height: 60px">
                <td colspan="4">
                    <h1 style="text-align: center">加班申请表</h1>
                </td>
            </tr>
            <tr style="height: 50px;">
                <td class="one">
                    <label>&nbsp;&nbsp;&nbsp;&nbsp;申请人姓名：</label>
                </td>
                <td class="two">
                    <input type="text" name="user_name" id="user_name" class="input1" value="${overtimeApply.user_name}" readonly>
                </td>
                <td class="three">
                    <label>&nbsp;&nbsp;&nbsp;&nbsp;部门：</label>
                </td>
                <td class="forth">
                    <select class="input2" name="dept" id="dept" disabled style="color: black">
                        <option value="${overtimeApply.dept}">${overtimeApply.dept}</option>
                    </select>
                </td>
            </tr>
            <tr style="height: 150px">
                <td class="one">
                    <label>&nbsp;&nbsp;&nbsp;&nbsp;加班事由：</label>
                </td>
                <td style="width: 80%" colspan="3">
                    <textarea style="width: 100%;height: 150px" name="overtime_reason" id="overtime_reason" readonly>
                        ${overtimeApply.overtime_reason}
                    </textarea>
                </td>
            </tr>
            <tr style="height: 50px">
                <td class="one">
                    <label>&nbsp;&nbsp;&nbsp;&nbsp;预批：</label>
                </td>
                <td colspan="3" style="width: 80%">
                    <input type="text" name="prebatch" id="prebatch" style="width: 100%;height:35px" value="${overtimeApply.prebatch}">
                </td>
            </tr>
            <tr style="height: 50px" >
                <td class="one">
                    <label>&nbsp;&nbsp;&nbsp;&nbsp;加班时间</label>
                </td>
                <td colspan="3" style="width: 80%" id="overtimeParent">
                    <c:forEach var="overtime" items="${overtimeApply.hrmOvertimes}">
                        <p>
                            <input name="oneStart"type="text" style="width: 30%;height: 35px" value="<fmt:formatDate value="${overtime.start_time}" pattern="yyyy-MM-dd HH:mm"/>" readonly>-
                            <input name="oneEnd" type="text" style="width: 30%;height: 35px" value="<fmt:formatDate value="${overtime.end_time}" pattern="yyyy-MM-dd HH:mm"/>" readonly>
                        </p>
                    </c:forEach>
                </td>
            </tr>
            <tr style="height: 200px">
                <td colspan="4">
                    <p>
                        <label>&nbsp;&nbsp;&nbsp;&nbsp;其中扣除用餐时间：</label>
                        <input type="text" style="width: 50px" name="deduct_meal_time" id="deduct_meal_time" value="${overtimeApply.deduct_meal_time}" readonly>小时
                        <label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;净计：</label>
                        <input type="text" style="width: 50px" name="net_meter" id="net_meter" value="${overtimeApply.net_meter}">小时
                    </p>
                    <p>
                        <label>&nbsp;&nbsp;&nbsp;&nbsp;当月累计已加班时间：</label>
                        <input type="text" style="width: 50px" name="count_month_overtime" id="count_month_overtime" value="${overtimeApply.count_month_overtime}">小时
                    </p>
                    <!--上级领导-->
                    <p>
                        <label>&nbsp;&nbsp;&nbsp;&nbsp;上级领导批准：</label>
                        <%--<input type="text" name="" id="" value="${changeOfRestApply.immediate_supervisor_examine}">--%>
                        <input type="radio" name="immediateSupervisorExamine" value="1" ${overtimeApply.immediate_supervisor_examine==1 || overtimeApply.immediate_supervisor_examine==0?"checked":''} ${type==1?'':"disabled"} onclick="cancelVerfiy(),displayCopy()">
                        <label>同意</label>
                        <input type="radio" name="immediateSupervisorExamine" value="2" ${overtimeApply.immediate_supervisor_examine==2?"checked":''} ${type==1?'':"disabled"} onclick="cancelVerfiy(),cancelCopy()">
                        <label>不同意</label>
                        <%--<input type="radio" name="immediateSupervisorExamine" value="0" ${overtimeApply.immediate_supervisor_examine==0?"checked":''} ${type==1?'':"disabled"} onclick="cancelVerfiy()">
                        <label>未批准</label>--%>
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <c:if test="${type==1}">
                            <input type="text" name="immediateSupervisorSign" id="approver" placeholder="批准人名字12" value="${user.userName}" onclick="cancelVerfiy()">
                        </c:if>
                        <c:if test="${type!=1}">
                            <input type="text" name="immediateSupervisorSign" id="approver" placeholder="批准人名字14" value="${overtimeApply.immediate_supervisor_sign}" disabled onclick="cancelVerfiy()">
                        </c:if>
                    </p>
                    <p style="display:${isResponsPerson==0||overtimeApply.immediate_supervisor_examine==2?"none":''}" id="copyPerson">
                        <label>&nbsp;&nbsp;&nbsp;&nbsp;抄送人：</label>
                        <input type="radio" name="copyType"  value="1" onclick="addCopyPerson(1),cancelVerfiy()" ${overtimeApply.copy_type==1?"checked":''} ${type==1?'':"disabled"}>
                        <label>行政</label>
                        <input type="radio" name="copyType"  value="2" onclick="addCopyPerson(2),cancelVerfiy()" ${overtimeApply.copy_type==2?"checked":''} ${type==1?'':"disabled"}>
                        <label>部门经理</label>
                    </p>
                    <%--<p id="admin" style="display: none">
                        <label id="adminLabel">&nbsp;&nbsp;&nbsp;&nbsp;行政人员：</label>
                        <input type="text" name="personnelMattersName" id="personnel_matters_name" ${type==1?'':"disabled"} onclick="cancelVerfiy()">
                    </p>
                    <p id="manager" style="display:none">
                        <label id="managerLabel">&nbsp;&nbsp;&nbsp;&nbsp;部门经理：</label>
                        <input type="text" name="divisionManagerSign" id="division_manager_sign" ${type==1?'':"disabled"} onclick="cancelVerfiy()">
                    </p>--%>
                    <c:choose>
                        <c:when test="${overtimeApply.copy_type==1}">
                            <p id="admin" >
                                <label id="adminLabel1">&nbsp;&nbsp;&nbsp;&nbsp;行政人员：</label>
                                <input type="text" name="personnelMattersName" id="personnel_matters_name1" value="${overtimeApply.personnel_matters_name}" readonly >
                            </p>
                        </c:when>
                        <c:when test="${overtimeApply.copy_type==2}">
                            <p id="manager">
                                <label id="managerLabel1">&nbsp;&nbsp;&nbsp;&nbsp;部门经理：</label>
                                <input type="text" name="divisionManagerSign" id="division_manager_sign1" value="${overtimeApply.division_manager_sign}" readonly>
                            </p>
                        </c:when>
                        <c:otherwise>
                            <p id="admin" style="display: none">
                                <label id="adminLabel">&nbsp;&nbsp;&nbsp;&nbsp;行政人员：</label>
                                <input type="text" name="personnelMattersName" id="personnel_matters_name" ${type==1?'':"disabled"} onclick="cancelVerfiy()">
                            </p>
                            <p id="manager" style="display:none">
                                <label id="managerLabel">&nbsp;&nbsp;&nbsp;&nbsp;部门经理：</label>
                                <input type="text" name="divisionManagerSign" id="division_manager_sign" ${type==1?'':"disabled"} onclick="cancelVerfiy()">
                            </p>
                        </c:otherwise>
                    </c:choose>
                    <c:if test="${type==2}">
                        <label>&nbsp;&nbsp;&nbsp;&nbsp;部门经理批准：</label>
                        <input type="radio" name="divisionManagerExamine" value="1" ${overtimeApply.division_manager_examine==1 || overtimeApply.division_manager_examine==0?"checked":''} onclick="cancelVerfiy(),displayAdminName()">
                        <label>同意</label>
                        <input type="radio" name="divisionManagerExamine" value="2" ${overtimeApply.division_manager_examine==2?"checked":''} onclick="cancelVerfiy(),displayAdminName()">
                        <label>不同意</label>
                       <%-- <input type="radio" name="divisionManagerExamine" value="0" ${overtimeApply.division_manager_examine==0?"checked":''} onclick="cancelVerfiy(),displayAdminName()">
                        <label>未批准</label>--%>
                        <%--<p id="adminNameP" style="display: none">
                            <label>&nbsp;&nbsp;&nbsp;&nbsp;行政人员：</label>
                            <input type="text" name="adminName" id="adminName">
                        </p>--%>
                        <%--&nbsp;&nbsp;&nbsp;&nbsp;
                        <input type="text" name="divisionManagerSign" id="managerApprover" placeholder="批准人名字" value="${overtimeApply.division_manager_sign}" onclick="cancelVerfiy()">--%>
                    </c:if>
                    <c:if test="${type==3}">
                        <label>&nbsp;&nbsp;&nbsp;&nbsp;行政人员：</label>
                        <input type="radio" name="personnelMattersStatus" value="1" ${overtimeApply.personnel_matters_status==0?"checked":''} onclick="cancelVerfiy()">
                        <label>未关闭</label>
                        <input type="radio" name="personnelMattersStatus" value="2" ${overtimeApply.division_manager_examine==1?"checked":''} onclick="cancelVerfiy()">
                        <label>关闭</label>
                    </c:if>
                    <p>
                        &nbsp;&nbsp;&nbsp;&nbsp;备注：当月累积已加班时间到达80%（28小时）时，必须提前向公司管理层预警，并经总经理批准后才能安排加班。
                    </p>
                </td>
            </tr>
        </table>
        <br>
        <div  class="btn_option" >
            <input type="button" class="btn-primary" id="sure" value="提交" onclick="formSubmit()">
            <input type="button" class="btn-danger" id="reset" style="margin-left: 50px" value="重置" onclick="formReset()">
        </div>
    </div>
</form>
<script type="text/javascript" src="plug-in/jquery/jquery-1.8.3.min.js"></script>
<script type="text/javascript">
    function formSubmit(){
        if($.trim($("#approver").val()).length==0){
            $("#approver").css("border-color","red");
            return false;
        }
        var immediate_supervisor_examine = $("input:radio[name='immediateSupervisorExamine']:checked").val();
        if(immediate_supervisor_examine==null){
            alert("请选择上级领导批准类型");
            return false;
        }
        var type = $("#roleRype").val();
        if(type==1){
            var copy_type = $("input:radio[name='copyType']:checked").val();
            if(copy_type==null){
                alert("请选择抄送人");
                return false;
            }
            if($("#admin").css("display")=="block"){
                if($.trim($("#personnel_matters_name").val()).length==0){
                    $("#personnel_matters_name").css("border-color","red");
                    return false;
                }
            }
            if($("#manager").css("display")=="block"){
                if($.trim($("#division_manager_sign1").val()).length==0){
                    $("#division_manager_sign1").css("border-color","red");
                    return false;
                }
            }
        }

        if(type==2){
            var divisionManagerExamine = $("input:radio[name='divisionManagerExamine']:checked").val();
            if(divisionManagerExamine==null){
                alert("请选择部门经理批准类型");
                return false;
            }
            /*if($.trim($("#managerApprover").val()).length()==0){
                $("#managerApprover").css("border-color","red");
                return false;
            }*/
            if($("#adminNameP").css("display")=="block"){
                if($.trim($("#adminName").val()).length==0){
                    $("#adminName").css("border-color","red");
                    return false;
                }
            }
        }
        if(type==3){
            var personnelMattersStatus = $("input:radio[name='personnelMattersStatus']:checked").val();
            if(personnelMattersStatus==null){
                alert("请选择行政人员批准类型");
                return false;
            }
        }
        document.getElementById("overtimeApplyForm").submit();
    }
    //抄送人判断
    function addCopyPerson(a) {
        if(a==1){
            $("#admin").css("display","");
            $("#manager").css("display","none");
        }else if(a==2){
            $("#admin").css("display","none");
            $("#manager").css("display","");
        }
    }

    //取消验证
    function cancelVerfiy(){
        $("#approver").css("border-color","");
        $("#personnel_matters_name").css("border-color","");
        $("#division_manager_sign").css("border-color","");
        $("#managerApprover").css("border-color","");
    }
    //是否显示行政人员
    function displayAdminName() {
        var divisionManagerExamine = $("input:radio[name='divisionManagerExamine']:checked").val();
        if(divisionManagerExamine==1){
            $("#adminNameP").css("display","");
        }else if(divisionManagerExamine==2||divisionManagerExamine==0){
            $("#adminNameP").css("display","none");
        }
    }

    //若上级领导不同意，则隐藏抄送人
    function cancelCopy() {
        $("#copyPerson").css("display","none");
        $("#admin").css("display","none");
        $("#manager").css("display","none");
    }
    //若上级领导同意，则显示抄送人
    function displayCopy() {
        $("#copyPerson").css("display","");
        $("#admin").css("display","");
    }
</script>
</body>
</html>
