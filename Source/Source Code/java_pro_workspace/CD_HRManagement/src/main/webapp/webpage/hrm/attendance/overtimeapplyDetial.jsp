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
        a{
            text-decoration: none;
            color: black;
        }
    </style>
</head>
<body>
<!-- <p>当前位置><a href="attendance.do?overTimeApplyListPageJump">加班申请列表</a>><a href="#">加班申请详情</a></p> -->
<form id="overtimeApplyForm" >
    <input type="hidden" name="timeIsRight" id="timeIsRight" value="1">
    <div class="parentConnt">
        <table class="tab">
            <tr style="height: 60px">
                <td colspan="4">
                    <h1 style="text-align: center">加班申请表</h1>
                </td>
                  <a href="attendance.do?overTimeApplyListPageJump" class="btn btn-default" style="float:right;">
	         			 <span class="glyphicon glyphicon-remove"></span>
	        	 </a>  
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
                    <p>
                        <label>&nbsp;&nbsp;&nbsp;&nbsp;上级领导批准：</label>
                        <input type="radio" name="oneExamine" ${overtimeApply.immediate_supervisor_examine==1?"checked":''} disabled>
                        <label>同意</label>
                        <input type="radio" name="oneExamine" ${overtimeApply.immediate_supervisor_examine==2?"checked":''} disabled>
                        <label>不同意</label>
                        <input type="radio" name="oneExamine" ${overtimeApply.immediate_supervisor_examine==0?"checked":''} disabled>
                        <label>未批准</label>
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <input type="text" name="approver" id="approver" placeholder="批准人名字" value="${overtimeApply.immediate_supervisor_sign}" readonly >
                    </p>
                    <p style="display:${isResponsPerson==0?"none":''}" id="copyPerson">
                        <label>&nbsp;&nbsp;&nbsp;&nbsp;抄送人：</label>
                        <input type="radio" name="copy_type" ${overtimeApply.copy_type==1?"checked":''} disabled>
                        <label>行政</label>
                        <input type="radio" name="copy_type" value="2" ${overtimeApply.copy_type==2?"checked":''} disabled>
                        <label>部门经理</label>
                    </p>
                    <p id="admin" style="display:${overtimeApply.copy_type==1?'':"none"} ">
                        <label id="adminLabel">&nbsp;&nbsp;&nbsp;&nbsp;行政人员：</label>
                        <input type="text" name="personnel_matters_name"  value="${overtimeApply.personnel_matters_name}" readonly>
                    </p>
                    <p id="manager" style="display:${overtimeApply.copy_type==2?'':"none"}">
                        <label id="managerLabel">&nbsp;&nbsp;&nbsp;&nbsp;部门经理：</label>
                        <input type="text" name="division_manager_sign"  value="${overtimeApply.division_manager_sign}">
                    </p>
                    <p style="display: ${overtimeApply.division_manager_examine==4?"none":''}">
                        <label>&nbsp;&nbsp;&nbsp;&nbsp;部门经理批准：</label>
                        <input type="radio" name="divisionManagerExamine" value="1" ${overtimeApply.division_manager_examine==1?"checked":''} onclick="cancelVerfiy(),displayAdminName()">
                        <label>同意</label>
                        <input type="radio" name="divisionManagerExamine" value="2" ${overtimeApply.division_manager_examine==2?"checked":''} onclick="cancelVerfiy(),displayAdminName()">
                        <label>不同意</label>
                        <input type="radio" name="divisionManagerExamine" value="0" ${overtimeApply.division_manager_examine==0?"checked":''} onclick="cancelVerfiy(),displayAdminName()">
                        <label>未批准</label>
                    </p>
                    <p>
                        &nbsp;&nbsp;&nbsp;&nbsp;备注：当月累积已加班时间到达80%（28小时）时，必须提前向公司管理层预警，并经总经理批准后才能安排加班。
                    </p>
                </td>
            </tr>
        </table>
    </div>
</form>
<script type="text/javascript" src="plug-in/jquery/jquery-1.8.3.min.js"></script>
<script type="text/javascript">
</script>
</body>
</html>
