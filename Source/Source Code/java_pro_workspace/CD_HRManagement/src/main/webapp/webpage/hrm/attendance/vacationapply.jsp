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
     <input type="hidden" name="count_day" id="count_day" value="${changeOfRestApply.count_day}">
        <input type="hidden" name="count_time" id="countTimes" value="${changeOfRestApply.count_time}">
        <input type="hidden" name="id" value="${changeOfRestApply.id}">
        <%--<c:if test="${changeOfRestApply.id!=null && changeOfRestApply.immediate_supervisor_examine!=null}">
            <input type="hidden" name="immediate_supervisor_examine" value="${changeOfRestApply.immediate_supervisor_examine}">
        </c:if>--%>
        <c:if test="${changeOfRestApply.id!=null && changeOfRestApply.division_manager_examine!=null}">
            <input type="hidden" name="division_manager_examine" value="${changeOfRestApply.division_manager_examine}">
        </c:if>
       <%-- <c:if test="${changeOfRestApply.id!=null && changeOfRestApply.immediate_supervisor_examine!=null}">
            <input type="hidden" name="immediate_supervisor_examine" value="${changeOfRestApply.immediate_supervisor_examine}">
        </c:if>--%>
        <input type="hidden" name="create_date" value="${changeOfRestApply.create_date}">
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
                    <input type="text" name="user_name" id="user_name" class="input1" value="${user.userName}" readonly  onclick="cancelVerfiy()">
                    <input type="hidden" name="user_id" id="user_id" value="${user.id}">
                </td>
                <td class="three">
                    <label>&nbsp;&nbsp;&nbsp;&nbsp;部门：</label>
                </td>
                <td class="forth">
                    <select class="input2" name="dept" id="dept" onclick="cancelVerfiy()">
                        <option value="">请选择...</option>
                        <option value="开发部" ${changeOfRestApply.dept=="开发部"?'selected':''} ${user.department=="开发部"?'selected':''}>开发部</option>
                        <option value="测试部" ${changeOfRestApply.dept=="测试部"?'selected':''} ${user.department=="测试部"?'selected':''}>测试部</option>
                        <option value="系统部" ${changeOfRestApply.dept=="系统部"?'selected':''} ${user.department=="系统部"?'selected':''}>系统部</option>
                    </select>
                </td>
            </tr>
            <tr style="height: 50px">
                <td class="one">
                    <label>&nbsp;&nbsp;&nbsp;&nbsp;申请休假类型：</label>
                </td>
                <td colspan="3">
                    <select class="input3" name="vacation_type" id="vacation_type" onclick="cancelVerfiy()">
                        <option value="">请选择...</option>
                        <option value="事假" ${changeOfRestApply.vacation_type=="事假"?'selected':''}>事假</option>
                        <option value="病假" ${changeOfRestApply.vacation_type=="病假"?'selected':''}>病假</option>
                        <option value="年假" ${changeOfRestApply.vacation_type=="年假"?'selected':''}>年假</option>
                        <option value="婚嫁" ${changeOfRestApply.vacation_type=="婚嫁"?'selected':''}>婚嫁</option>
                        <option value="丧假" ${changeOfRestApply.vacation_type=="丧假"?'selected':''}>丧假</option>
                        <option value="换休" ${changeOfRestApply.vacation_type=="换休"?'selected':''}>换休</option>
                    </select>
                </td>
            </tr>
            <tr style="height: 50px" >
                <td class="one">
                    <label>&nbsp;&nbsp;&nbsp;&nbsp;申请日期</label>
                </td>
                <td colspan="3" style="width: 80%" id="vacationTimeParent">
                    <p>

                        <input name="oneStart"type="datetime-local" style="width: 30%;height: 35px" onblur="countTime(this)" value="${oneDateFormat.start_time}" >-
                        <input name="oneEnd" type="datetime-local" style="width: 30%;height: 35px" onblur="countTime(this)" value="${oneDateFormat.end_time}" >
                        <button type="button" style="height: 30px;background:darkgrey" onclick="addOvertime()">+</button>
                    </p>
                    <div id="vacationTime">
                        <c:forEach var="dateFormat" items="${dateFormat}">
                            <input name="oneStart"type="datetime-local" style="width: 30%;height: 35px" value="${dateFormat.start_time}" onclick="cancelVerfiy()" onblur="countTime(this)">-
                            <input name="oneEnd" type="datetime-local" style="width: 30%;height: 35px" value="${dateFormat.end_time}" onclick="cancelVerfiy()" onblur="countTime(this)">
                            <button type="button" style="height: 30px;background:darkgrey" onclick="cancelVacationaTime(this),countTime(this,1)">x</button>
                        </c:forEach>
                    </div>
                </td>
            </tr>
            <tr style="height: 50px">
                <td class="one">
                    <label>&nbsp;&nbsp;&nbsp;&nbsp;共计天数：</label>
                </td>
                <td colspan="3">
                    <label id="countDays">${changeOfRestApply.count_day==null?'0':changeOfRestApply.count_day}天（${vacationApply.count_time==null?'0':vacationApply.count_time}小时）</label>
                </td>
            </tr>
            <tr style="height: 150px">
                <td class="one">
                    <label>&nbsp;&nbsp;&nbsp;&nbsp;请假理由：</label>
                </td>
                <td style="width: 80%" colspan="3">
                    <textarea style="width: 100%;height: 150px" name="leave_reason" id="leave_reason" onclick="cancelVerfiy()">
                        ${changeOfRestApply.leave_reason}
                    </textarea>
                </td>
            </tr>
            <tr style="height: 50px">
                <td colspan="4">
                    <p>休假地点：</p>
                    <p>
                        &nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="vacation_place" value="1" onclick="cancelVerfiy()" ${changeOfRestApply.vacation_place==1||changeOfRestApply.vacation_place==null?'checked':''}  />
                        <span>国内</span>
                        &nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="vacation_place" value="2" ${changeOfRestApply.vacation_place==2?'checked':''} onclick="cancelVerfiy()" />
                        <span>国外</span>
                    </p>
                    <p>
                        <span style="margin-left:15px">联系方式：</span>
                        <input type="text"  name="contact_information" id="contact_information" value="${changeOfRestApply.contact_information}" />
                        <span style="margin-left:15px">请说明：</span>
                        <input type="text" id="instruction" name="instruction" onclick="cancelVerfiy()" value="${changeOfRestApply.instruction}" />
                    </p>
                    <p>
                        <span style="display: block;margin-left:27px;margin-top:9px;">如休假累计天数超过公司所规定之天数，超出部分将被取消或视作“无薪假”</span>
                    </p>
                    <div style="float: right">
                        <span class="sign">申请者签字：</span>
                        <input type="text" id="applicant_sign" name="applicant_sign"  value="${changeOfRestApply.applicant_sign==null?user.userName:changeOfRestApply.applicant_sign}" readonly style="width:138px"  onclick="cancelVerfiy()"/>
                        <span style="margin:0 12px">日期:</span>
                        <c:if test="${changeOfRestApply.id==null}"> 
                            <input type="text" name="apply_date" value="<%=new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime())%>" readonly>
                        </c:if>
                        <c:if test="${changeOfRestApply.id!=null}">                   
                            <input type="text" name="apply_date" value="<fmt:formatDate value="${changeOfRestApply.apply_date}" pattern="yyyy-MM-dd"/>" readonly>
                        </c:if>
                    </div>
                </td>
            </tr>
            <tr style="height: 120px">
                <td colspan="4">
                    <p>审批者签字</p>
                    <p>
                        <label>&nbsp;&nbsp;&nbsp;&nbsp;直属上司：</label>
                        <input type="radio" name="immediate_supervisor_examine" value="1" ${userType==0?"disabled":''}  ${changeOfRestApply.immediate_supervisor_examine==null || changeOfRestApply.immediate_supervisor_examine==1?"checked":''}>
                        <label>同意</label>
                        <input type="radio" name="immediate_supervisor_examine" value="2" ${userType==0?"disabled":''} ${changeOfRestApply.immediate_supervisor_examine==2?"checked":''}>
                        <label>不同意</label>
                        <input type="radio" name="immediate_supervisor_examine" value="0" ${userType==0?"disabled":''} ${userType==0 && changeOfRestApply.immediate_supervisor_examine==null?"checked":''}>
                        <label>未批准</label>
                        &nbsp;&nbsp;&nbsp;&nbsp;<label>处理意见：</label>
                        <input type="text" name="immediate_supervisor_advice" id="immediate_supervisor_advice" style="width: 40%" ${userType==0?"readonly":''} value="${changeOfRestApply.immediate_supervisor_advice}">
                        <c:choose>
                            <c:when test="${changeOfRestApply.id==null && userType!=0}">
                                <input type="text" name="immediate_supervisor_sign" id="approver" placeholder="批准人名字" value="${user.userName}" readonly onclick="cancelVerfiy()">
                            </c:when>
                            <c:otherwise>
                                <input type="text" name="immediate_supervisor_sign" id="approver" placeholder="批准人名字" value="${changeOfRestApply.immediate_supervisor_sign}" ${userType==0?"readonly":''} onclick="cancelVerfiy()">
                            </c:otherwise>
                        </c:choose>
                    </p>
                    <p style="display: ${changeOfRestApply.division_manager_examine==3?'none':''}" id="division_manager">
                        <label>&nbsp;&nbsp;&nbsp;&nbsp;部门经理：</label>
                        <input type="radio" name="division_manager_examine" value="1" ${changeOfRestApply.division_manager_examine==1?"checked":''} disabled>
                        <label>同意</label>
                        <input type="radio" name="division_manager_examine" value="2"  ${changeOfRestApply.division_manager_examine==2?"checked":''} disabled>
                        <label>不同意</label>
                        <input type="radio" name="division_manager_examine" value="3" ${changeOfRestApply.division_manager_examine==3 || changeOfRestApply.division_manager_examine==null?"checked":''} disabled>
                        <label>未批准</label>
                        &nbsp;&nbsp;&nbsp;&nbsp;<label>处理意见：</label>
                        <input type="text" name="division_manager_advice" id="division_manager_advice" value="${changeOfRestApply.division_manager_advice}" style="width: 40%;" readonly>
                        <c:if test="${userType==2}">
                            <input type="text" name="division_manager_sign" id="approver" placeholder="批准人名字" value="${user.userName}" readonly onclick="cancelVerfiy()">
                        </c:if>
                        <c:if test="${userType!=2}">
                            <input type="text" name="division_manager_sign" id="approver" placeholder="批准人名字" readonly onclick="cancelVerfiy()">
                        </c:if>
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
    //表单提交验证
    function formSubmit() {
        //申请人姓名
        if($.trim($("#user_name").val()).length==0){
            $("#user_name").css("border-color","red");
            return false;
        }
        //部门
        if($.trim($("#dept").val()).length==0){
            $("#dept").css("border-color","red");
            return false;
        }
        //申请类型
        if($.trim($("#vacation_type").val()).length==0){
            $("#vacation_type").css("border-color","red");
            return false;
        }
        //加班时间
        var startDate = $("*[name='oneStart']");
        var endDate = $("*[name='oneEnd']");
        for(var i=0;i<startDate.length;i++){
            if($.trim(startDate.eq(i).val()).length==0){
                startDate.eq(i).css("border-color","red");
                return false;
            }
        }
        for(var i=0;i<endDate.length;i++){
            if($.trim(endDate.eq(i).val()).length==0){
                endDate.eq(i).css("border-color","red");
                return false;
            }
        }
        var timeIsRight = $("#timeIsRight").val();
        if(timeIsRight==2){
            alert("时间有误，请调整！");
            return false;
        }
        //请假事由
        if($.trim($("#leave_reason").val()).length==0){
            $("#leave_reason").css("border-color","red");
            return false;
        }
        //休假地点
        var vacation_place = $("input:radio[name='vacation_place']:checked").val();
        if(vacation_place==null){
            alert("请选择休假地点");
            return false;
        }
        //联系方式
        if($.trim($("#contact_information").val()).length==0){
            $("#contact_information").css("border-color","red");
            return false;
        }
        //请说明
        if($.trim($("#instruction").val()).length==0){
            $("#instruction").css("border-color","red");
            return false;
        }
        //申请者签字
        if($.trim($("#applicant_sign").val()).length==0){
            $("#applicant_sign").css("border-color","red");
            return false;
        }

        //根据休假类型判断是否正确
        var vacation_type = $("#vacation_type").val();
        if(vacation_type=="换休"){
            if(breakOff()==0){
                return false;
            }
        }
        if(vacation_type=="年假"){
            if(annualLeave()==0){
                return false;
            }
        }
        if(vacation_type=="病假"){
            if(sickLeave()==0){
                return false;
            }
        }
        if(vacation_type=="婚嫁"){
            var count_day = $("#count_day").val();
            if(count_day>10){
                alert("你的请假时间大于婚嫁时间，婚嫁时间为10天");
                return false;
            }
        }
        document.getElementById("vacationapplyForm").submit();
    }

    //取消验证
    function cancelVerfiy() {
        $("#user_name").css("border-color","");
        $("#dept").css("border-color","");
        $("#vacation_type").css("border-color","");
        $("#leave_reason").css("border-color","");
        $("#contact_information").css("border-color","");
        $("#instruction").css("border-color","");
        $("#applicant_sign").css("border-color","");
    }
    //添加加班时间
    function addOvertime() {
        var childp = '<p>'
            + '<input name="oneStart"type="datetime-local" style="width: 30%;height: 35px" onclick="cancelVerfiy()" onblur="countTime(this)">'
            + '- <input name="oneEnd" type="datetime-local" style="width: 30%;height: 35px" onclick="cancelVerfiy()" onblur="countTime(this)">'
            + ' <button type="button" style="height: 30px;background:darkgrey" onclick="cancelVacationaTime(this),countTime(this,1)">x</button>'
            + '</p>';
        $("#vacationTime").append(childp);
    }
    //删除加班时间
    function cancelVacationaTime(th) {
        $(th).parent().remove();
    }
    //判断时间是否正确
    function countTime(th,c) {
        var type = $(th).attr("name");
        var startDate;
        var endDate;
        if(type=="oneStart"){
            startDate = $(th).val();
            endDate = $(th).next().val();
        }else{
            startDate = $(th).prev().val();
            endDate = $(th).val();
        }
        //判断是否为空
        if(($.trim(startDate).length!=0 && $.trim(endDate).length!=0)||c!=undefined){
            var startMill = Date.parse(startDate);
            var endMill = Date.parse(endDate);
            //判断开始时间是否小于结束时间
            if(startMill>endMill){
                alert("开始时间大于结束时间，请调整！");
                $("#timeIsRight").val(2);
                return ;
            }else if(startMill==endMill){
                alert("开始时间等于结束时间，请调整！");
                $("#timeIsRight").val(2);
                return ;
            }
            //判断时间是否有重复
            var flag = test();
            if(flag==false){
                $("#timeIsRight").val(2);
                return ;
            }
            var ss = $("#vacationTimeParent p");
            var sum = 0.0;
            for(var i=0;i<ss.length;i++){
                var startDate = ss.eq(i).find("input").eq(0).val();
                var endDate = ss.eq(i).find("input").eq(1).val();
                var start = new Date(startDate);
                var end = new Date(endDate);
                var s = start.getHours();
                var e = end.getHours();
                //如果开始时间小于9，就按点开始算
                if(s<=9){
                    start.setHours(9);
                    start.setMinutes(0);
                }
                if(e>18){
                    end.setHours(18);
                    end.setMinutes(0);
                }
                var at = 9,
                    bt = 1,
                    ct = 6,
                    dt = 16,
                    startDate = start.toLocaleDateString(), // 日期
                    endDate = end.toLocaleDateString();
                var res = (end - start  ) / 1000 / 3600;
                // 同一天
                if (startDate === endDate) {
                    if (start.getHours() <= 12 && end.getHours() > 12) {
                        res = res - bt;
                    }
                }
                else {
                    // 相差一天
                    res = res - at - ct;
                    if (start.getHours() <= 12) {
                        res = res - bt;
                    }
                    if (end.getHours() > 12) {
                        res = res - bt;
                    }
                    // 超过一天
                    var cDate = (new Date(endDate) - new Date(startDate)) / 3600 / 24 / 1000;
                    if (cDate > 1) {
                        res = res - dt * (cDate - 1);
                    }
                }
                sum = sum + parseFloat(res);
            }
            var day = sum/8;
            var text = day+"天（"+sum+"小时)";
            $("#countDays").text(text);
            if(day>3){
                $("#division_manager").css("display","");
            }else{
                $("#division_manager").css("display","none");
            }
            $("#count_day").val(day);
            $("#countTimes").val(sum);
                //通过验证
            $("#timeIsRight").val(1);

        }
    }
    //判断时间是否有重复
    function test() {
        var begin = $("input[name='oneStart']");
        var over = $("input[name='oneEnd']");
        for(var i=1;i<begin.length;i++){
            if (begin.eq(i).val() <= over.eq(i-1).val()){
                alert("时间有重复,请调整！");
                return false;
            }
        }
        return true;
    }
    //换休判断
    function breakOff() {
        var oneStart = $("input[name='oneStart']").val();
        var countTimeChange = $("#countTimes").val();
        var user_id = $("#user_id").val();
        var flag = 0;
        $.ajax({
            type: 'get',
            url: 'attendance.do?breakOff&userId='+user_id+'&oneStart='+oneStart,
            async: false,
            cache: false,
            success: function (result){
                var r = JSON.parse(result);
                var countTime = r.attributes.countTime;
                if(countTime>=countTimeChange){
                    flag = 1;
                }else{
                    alert("换休时间不足，你可换休时间为"+countTime);
                }
            }
        });
        return flag;
    }
    //判断年假
    function annualLeave() {
        var countdays = $("#countDays").text();
        var countDays = countdays.charAt(0); 
        var flag = 0;
            $.ajax({
            type: 'get',
            url: 'attendance.do?annualLeave',
            async: false,
            cache: false,
            success: function (result){
                var r = JSON.parse(result);
                var countYearDays = r.attributes.countDays;
                if(countDays<=countYearDays){
                	 flag = 1;
                }else{
                    alert("年假申请天数大于年假剩余天数，你的年假剩余天数为"+countYearDays);
                }
            }
        });
        return flag;
    }
    //判断病假
    function sickLeave() {
    	var countdays = $("#countDays").text();
        var countDays = countdays.charAt(0); 
        var flag = 0;
        $.ajax({
            type: 'get',
            url: 'attendance.do?sickLeave',
            async: false,
            cache: false,
            success: function (result){
                var r = JSON.parse(result);
                var countYearDays = r.attributes.countDays;
                if(countDays<=countYearDays){
                	flag = 1;
                }else{
                    alert("病假申请天数大于病假剩余天数，你的病假剩余天数为"+countYearDays);
                }
            }
        });
        return flag;
    }
</script>
</body>
</html>
