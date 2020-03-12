<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Calendar" %><%--
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
    <link rel="stylesheet" href="plug-in/ace/css/bootstrap.css"/>
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
        .input3{
            width: 95.5%;
            height: 35px;
        }
        .btn_option{
            text-align: center;
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
    </style>
</head>
<body>
<form id="vacationapplyForm" action="attendance.do?saveVacationapply" onsubmit="return formSubmit();" method="post">
    <%--<input type="hidden" name="immediate_supervisor_examine" value="${overtimeApply.immediate_supervisor_examine}">--%>
    <input type="hidden" name="timeIsRight" id="timeIsRight" value="1">
    <input type="hidden" name="count_day" id="count_day">
    <input type="hidden" name="count_time" id="countTimes">
    <div class="parentConnt">
        <table class="tab">
            <tr style="height: 60px">
                <td colspan="4">
                    <h1 style="text-align: center">换休申请表</h1>
                </td>
                 <a href="attendance.do?vacationApplyListPageJump" class="btn btn-default" style="float:right;">
	         			 <span class="glyphicon glyphicon-remove"></span>
	        	 </a>  
            </tr>
            <tr style="height: 50px;">
                <td class="one">
                    <label>&nbsp;&nbsp;&nbsp;&nbsp;申请人姓名：</label>
                </td>
                <td class="two">
                    <input type="text" name="user_name" id="user_name" class="input1" value="${changeOfRestApply.user_name}" readonly >
                </td>
                <td class="three">
                    <label>&nbsp;&nbsp;&nbsp;&nbsp;部门：</label>
                </td>
                <td class="forth">
                    <select class="input2" name="dept" id="dept" style="color:black;" disabled>
                        <option value="${changeOfRestApply.dept}">${changeOfRestApply.dept}</option>
                    </select>
                </td>
            </tr>
            <tr style="height: 50px">
                <td class="one">
                    <label>&nbsp;&nbsp;&nbsp;&nbsp;申请休假类型：</label>
                </td>
                <td colspan="3">
                    <select class="input3" name="vacation_type" id="vacation_type" style="color: black" disabled>
                        <option value="${changeOfRestApply.vacation_type}">${changeOfRestApply.vacation_type}</option>
                    </select>
                </td>
            </tr>
            <tr style="height: 50px" >
                <td class="one">
                    <label>&nbsp;&nbsp;&nbsp;&nbsp;申请日期</label>
                </td>
                <td colspan="3" style="width: 80%" id="vacationTimeParent">
                    <c:forEach var="vacationTime" items="${changeOfRestApply.hrmChangofrestTimes}">
                        <p>
                            <input name="oneStart"type="text" style="width: 30%;height: 35px" value="<fmt:formatDate value="${vacationTime.start_time}" pattern="yyyy-MM-dd HH:mm"/>" readonly>-
                            <input name="oneEnd" type="text" style="width: 30%;height: 35px" value="<fmt:formatDate value="${vacationTime.end_time}" pattern="yyyy-MM-dd HH:mm"/>" readonly>
                        </p>
                    </c:forEach>
                </td>
            </tr>
            <tr style="height: 50px">
                <td class="one">
                    <label>&nbsp;&nbsp;&nbsp;&nbsp;共计天数：</label>
                </td>
                <td colspan="3">
                    <label id="countDays">${changeOfRestApply.count_day}天（${vacationApply.count_time}小时）</label>
                </td>
            </tr>
            <tr style="height: 150px">
                <td class="one">
                    <label>&nbsp;&nbsp;&nbsp;&nbsp;请假理由：</label>
                </td>
                <td style="width: 80%" colspan="3">
                    <textarea style="width: 100%;height: 150px" name="leave_reason" id="leave_reason" readonly>
                        ${changeOfRestApply.leave_reason}
                    </textarea>
                </td>
            </tr>
            <tr style="height: 50px">
                <td colspan="4">
                    <p>休假地点：</p>
                    <p>
                        &nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="vacation_place" value="1" ${changeOfRestApply.vacation_place==1?'checked':''} readonly/>
                        <span>国内</span>
                        <span style="margin-left:15px">联系方式：</span>
                        <input type="text"  name="contact_information" id="contact_information" value="${changeOfRestApply.contact_information}" />
                    </p>
                    <p>
                        &nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="vacation_place" value="2"  ${changeOfRestApply.vacation_place==2?'checked':''} readonly/>
                        <span>国外</span>
                        <span style="margin-left:15px">请说明：</span>
                        <input type="text" id="instruction" name="instruction" value="${changeOfRestApply.instruction}" />
                    </p>
                    <p>
                        <span style="display: block;margin-left:27px;margin-top:9px;">如休假累计天数超过公司所规定之天数，超出部分将被取消或视作“无薪假”</span>
                    </p>
                    <div style="float: right">
                        <span class="sign">申请者签字：</span>
                        <input type="text" id="applicant_sign" name="applicant_sign"  value="${changeOfRestApply.applicant_sign}" style="width:138px" readonly/>
                        <span style="margin:0 12px">日期:</span>
                        <input type="text" name="apply_date" id="apply_date" value="<fmt:formatDate value="${changeOfRestApply.apply_date}" pattern="yyyy-MM-dd"/>" readonly>
                    </div>
                </td>
            </tr>
            <tr style="height: 120px">
                <td colspan="4">
                    <p>审批者签字</p>
                    <p>
                        <label>&nbsp;&nbsp;&nbsp;&nbsp;直属上司：</label>
                        <input type="radio" name="immediate_supervisor_examine" value="1" ${changeOfRestApply.immediate_supervisor_examine==1?"checked":''} disabled>
                        <label>同意</label>
                        <input type="radio" name="immediate_supervisor_examine" value="2" ${changeOfRestApply.immediate_supervisor_examine==2?"checked":''} disabled>
                        <label>不同意</label>
                        <input type="radio" name="immediate_supervisor_examine" value="0" ${changeOfRestApply.immediate_supervisor_examine==0?"checked":''} disabled>
                        <label>未批准</label>
                        &nbsp;&nbsp;&nbsp;&nbsp;<label>处理意见：</label>
                        <input type="text" name="immediate_supervisor_advice" id="immediate_supervisor_advice" style="width: 40%" value="${changeOfRestApply.immediate_supervisor_advice}" readonly>
                        <input type="text" name="immediate_supervisor_sign" id="approver" value="${changeOfRestApply.immediate_supervisor_sign}" readonly>
                    </p>
                    <p style="display: ${changeOfRestApply.division_manager_examine==3?'none':''}" id="division_manager">
                        <label>&nbsp;&nbsp;&nbsp;&nbsp;部门经理：</label>
                        <input type="radio" name="division_manager_examine" value="1" ${changeOfRestApply.division_manager_examine==1?'checked':''} disabled>
                        <label>同意</label>
                        <input type="radio" name="division_manager_examine" value="2" ${changeOfRestApply.division_manager_examine==2?'checked':''} disabled>
                        <label>不同意</label>
                        <input type="radio" name="division_manager_examine" value="3" ${changeOfRestApply.division_manager_examine==3||changeOfRestApply.division_manager_examine==null||changeOfRestApply.division_manager_examine==0?'checked':''} disabled>
                        <label>未批准</label>
                        &nbsp;&nbsp;&nbsp;&nbsp;<label>处理意见：</label>
                        <input type="text" name="division_manager_advice" id="division_manager_advice" style="width: 40%;" value="${changeOfRestApply.division_manager_advice}" readonly>
                        <input type="text" name="division_manager_sign" id="approver1" placeholder="批准人名字" value="${changeOfRestApply.division_manager_sign}" readonly >
                    </p>
                </td>
            </tr>
        </table>
        <br>
    </div>
</form>
<script type="text/javascript" src="plug-in/jquery/jquery-1.8.3.min.js"></script>
<script type="text/javascript">

</script>
</body>
</html>
