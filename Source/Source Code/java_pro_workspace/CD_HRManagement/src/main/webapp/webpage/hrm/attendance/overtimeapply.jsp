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
<form id="overtimeApplyForm" action="attendance.do?saveOvertimeApply" onsubmit="return formSubmit();" method="post">
    <input type="hidden" name="timeIsRight" id="timeIsRight" value="1">
    <input type="hidden" name="id" id="id" value="${overtimeApply.id}">
    <input type="hidden" name="division_manager_examine" id="division_manager_examine" value="${overtimeApply.division_manager_examine}">
    <input type="hidden" name="copyType" value="${overtimeApply.copy_type}">
    <input type="hidden" name="immediateSupervisorExamine" id="immediate_supervisor_examine" value="${overtimeApply.immediate_supervisor_examine}">
    <%--<input type="hidden" name="immediate_supervisor_examine" value="${overtimeApply.immediate_supervisor_examine}">--%>
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
                    <input type="text" name="user_name" id="user_name" class="input1" value="${user.userName}" readonly onclick="cancelVerfiy()">
                    <input type="hidden" name="user_id" value="${user.id}">
                </td>
                <td class="three">
                    <label>&nbsp;&nbsp;&nbsp;&nbsp;部门：</label>
                </td>
                <td class="forth">
                    <select class="input2" name="dept" id="dept" onclick="cancelVerfiy()">
                        <option value="">请选择...</option>
                        <option value="开发部" ${overtimeApply.dept=="开发部"?"selected":''} ${user.department=="开发部"?'selected':''}>开发部</option>
                        <option value="测试部" ${overtimeApply.dept=="测试部"?"selected":''} ${user.department=="测试部"?'selected':''}>测试部</option>
                        <option value="系统部" ${overtimeApply.dept=="系统部"?"selected":''} ${user.department=="系统部"?'selected':''}>系统部</option>
                    </select>
                </td>
            </tr>
            <tr style="height: 150px">
                <td class="one">
                    <label>&nbsp;&nbsp;&nbsp;&nbsp;加班事由：</label>
                </td>
                <td style="width: 80%" colspan="3">
                    <textarea style="width: 100%;height: 150px" name="overtime_reason" id="overtime_reason" onclick="cancelVerfiy()">
                        ${overtimeApply.overtime_reason}
                    </textarea>
                </td>
            </tr>
            <tr style="height: 50px">
                <td class="one">
                    <label>&nbsp;&nbsp;&nbsp;&nbsp;预批：</label>
                </td>
                <td colspan="3" style="width: 80%">
                    <input type="text" name="prebatch" id="prebatch" style="width: 100%;height:35px" onclick="cancelVerfiy()" value="${overtimeApply.prebatch}">
                </td>
            </tr>
            <tr style="height: 50px" >
                <td class="one">
                    <label>&nbsp;&nbsp;&nbsp;&nbsp;加班时间</label>
                </td>
                <td colspan="3" style="width: 80%" id="overtimeParent">
                    <p>
                        <!--value="<fmt:formatDate value="${overtimeApply.hrmOvertimes[0].start_time }"  pattern="yyyy-MM-dd HH:mm"/>"-->
                    <input name="oneStart"type="datetime-local" style="width: 30%;height: 35px" onclick="cancelVerfiy()" onblur="autoCount(this)" value="${oneDateFormat.start_time}" >-
                    <input name="oneEnd" type="datetime-local" style="width: 30%;height: 35px" onclick="cancelVerfiy()" onblur="autoCount(this)" value="${oneDateFormat.end_time}">
                        <input type="hidden" name="countTime" value="${oneDateFormat.count_time}">
                        <input type="hidden" name="surplusTime" value="${oneDateFormat.surplus_time}">
                    <button type="button" style="height: 30px;background:darkgrey" onclick="addOvertime()">+</button>
                    </p>
                    <div id="overtime">
                        <c:forEach var="dateFormat" items="${dateFormats}">
                            <p>
                                <input name="oneStart"type="datetime-local" style="width: 30%;height: 35px" value="${dateFormat.start_time}" onclick="cancelVerfiy()" onblur="autoCount(this)">-
                                <input name="oneEnd" type="datetime-local" style="width: 30%;height: 35px" value="${dateFormat.end_time}" onclick="cancelVerfiy()" onblur="autoCount(this)">
                                <input type="hidden" name="countTime" value="${dateFormat.count_time}">
                                <input type="hidden" name="surplusTime" value="${dateFormat.surplus_time}">
                                <button type="button" style="height: 30px;background:darkgrey" onclick="cancelOvertime(this)">x</button>
                                </p>
                        </c:forEach>
                    </div>
                </td>
            </tr>
            <tr style="height: 200px">
                <td colspan="4">
                    <p>
                        <label>&nbsp;&nbsp;&nbsp;&nbsp;其中扣除用餐时间：</label>
                        <input type="text" style="width: 50px" name="deduct_meal_time" id="deduct_meal_time" value="${overtimeApply.deduct_meal_time}" readonly>小时
                        <label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;净计：</label>
                        <input type="text" style="width: 50px" name="net_meter" id="net_meter" value="${overtimeApply.net_meter}" readonly>小时
                    </p>
                    <p>
                        <label>&nbsp;&nbsp;&nbsp;&nbsp;当月累计已加班时间：</label>
                        <input type="text" style="width: 50px" name="count_month_overtime" id="count_month_overtime" value="${sumMonthOverTime}" readonly>小时
                    </p>
                    <p>
                        <label>&nbsp;&nbsp;&nbsp;&nbsp;上级领导批准：</label>
                        <input type="radio" name="immediate_supervisor_examine" value="1" ${isResponsPerson==0?"disabled":''} ${overtimeApply.immediate_supervisor_examine==null || overtimeApply.immediate_supervisor_examine==1|| isResponsPerson!=0?"checked":''}>
                        <label>同意</label>
                        <input type="radio" name="immediate_supervisor_examine" value="2" ${isResponsPerson==0?"disabled":''} ${overtimeApply.immediate_supervisor_examine==2?"checked":''}>
                        <label>不同意</label>
                        <input type="radio" name="immediate_supervisor_examine" value="0" ${isResponsPerson==0?"disabled":''} ${isResponsPerson==0?"checked":''}>
                        <label>未批准</label>
                        &nbsp;&nbsp;&nbsp;&nbsp;
                         <c:choose>
                             <c:when test="${overtimeApply.id==null && isResponsPerson!=0}">
                                 <input type="text" name="immediate_supervisor_sign" id="approver" placeholder="批准人名字" value="${user.userName}" readonly onclick="cancelVerfiy()">
                             </c:when>
                             <c:otherwise>
                                <input type="text" name="immediate_supervisor_sign" id="approver" placeholder="批准人名字" value="${overtimeApply.immediate_supervisor_sign}" ${isResponsPerson==0?"readonly":''} onclick="cancelVerfiy()">
                             </c:otherwise>
                         </c:choose>
                    </p>
                    <p style="display:${(isResponsPerson==0&&overtimeApply.id==null)||(overtimeApply.id!=null&&overtimeApply.immediate_supervisor_examine==2)?"none":''}" id="copyPerson">
                        <label>&nbsp;&nbsp;&nbsp;&nbsp;抄送人：</label>
                        <input type="radio" name="copy_type" value="1" ${overtimeApply.copy_type==1?"checked":''} ${overtimeApply.id!=null?"disabled":''} onclick="addCopyPerson(1)" >
                        <label>行政</label>
                        <input type="radio" name="copy_type" value="2" ${overtimeApply.copy_type==2?"checked":''} ${overtimeApply.id!=null?"disabled":''} onclick="addCopyPerson(2)">
                        <label>部门经理</label>
                    </p>
                    <p id="admin" style="display: ${overtimeApply.id==null||(overtimeApply.id!=null&&overtimeApply.copy_type!=1)?"none":''}">
                        <label id="adminLabel">&nbsp;&nbsp;&nbsp;&nbsp;行政人员：</label>
                        <input type="text" name="personnel_matters_name"  placeholder="请输入姓名" id="adminName" value="${overtimeApply.personnel_matters_name}" onclick="cancelVerfiy()" ${overtimeApply.id!=null?"readonly":''}>
                    </p>
                    <p id="manager" style="display:${overtimeApply.id==null||(overtimeApply.id!=null&&overtimeApply.copy_type!=2)?"none":''}">
                        <label id="managerLabel">&nbsp;&nbsp;&nbsp;&nbsp;部门经理：</label>
                        <input type="text" name="division_manager_sign"  id="managerName" placeholder="请输入姓名" value="${overtimeApply.division_manager_sign}" onclick="cancelVerfiy()" ${overtimeApply.id!=null?"readonly":''}>
                    </p>
                    <c:if test="${overtimeApply.id!=null&&overtimeApply.division_manager_examine!=null}">
                        <%--<p id="admin" style="display:${overtimeApply.copy_type==1?'':"none"} ">
                            <label id="adminLabel1">&nbsp;&nbsp;&nbsp;&nbsp;行政人员：</label>
                            <input type="text" name="personnel_matters_name"  value="${overtimeApply.personnel_matters_name}" readonly>
                        </p>
                        <p id="manager" style="display:${overtimeApply.copy_type==2?'':"none"}">
                            <label id="managerLabel1">&nbsp;&nbsp;&nbsp;&nbsp;部门经理：</label>
                            <input type="text" name="division_manager_sign"  value="${overtimeApply.division_manager_sign}" readonly>
                        </p>--%>
                        <label>&nbsp;&nbsp;&nbsp;&nbsp;部门经理批准：</label>
                        <input type="radio" name="divisionManagerExamine" value="1" ${overtimeApply.division_manager_examine==1 || overtimeApply.division_manager_examine==0?"checked":''} onclick="cancelVerfiy(),displayAdminName()" disabled>
                        <label>同意</label>
                        <input type="radio" name="divisionManagerExamine" value="2" ${overtimeApply.division_manager_examine==2?"checked":''} onclick="cancelVerfiy(),displayAdminName()" disabled>
                        <label>不同意</label>
                        <input type="radio" name="divisionManagerExamine" value="0" ${overtimeApply.division_manager_examine==0?"checked":''} onclick="cancelVerfiy(),displayAdminName()" disabled>
                        <label>未批准</label>
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
    function addOvertime() {
        /*var obj = document.getElementById("overtime");
        obj.innerHTML = obj.innerHTML
            + '<p>'
            + '<input name="oneStart"type="datetime-local" style="width: 30%;height: 35px" onclick="cancelVerfiy()" onblur="autoCount(this)">'
            + '- <input name="oneEnd" type="datetime-local" style="width: 30%;height: 35px" onclick="cancelVerfiy()" onblur="autoCount(this)">'
            + ' <button type="button" style="height: 30px;background:darkgrey" onclick="cancelOvertime(this)">x</button>'
            + '</p>';*/
        var childp = '<p>'
            + '<input name="oneStart"type="datetime-local" style="width: 30%;height: 35px" onclick="cancelVerfiy()" onblur="autoCount(this)">'
            + '- <input name="oneEnd" type="datetime-local" style="width: 30%;height: 35px" onclick="cancelVerfiy()" onblur="autoCount(this)">'
            + '<input type="hidden" name="countTime">'
            + '<input type="hidden" name="surplusTime">'
            + ' <button type="button" style="height: 30px;background:darkgrey" onclick="cancelOvertime(this)">x</button>'
            + '</p>';
        $("#overtime").append(childp);
    }
    function formSubmit() {
        //判断加班开始时间和结束时间是否为一天
        var time = $("#timeIsRight").val();
        if($.trim($("#user_name").val()).length==0){
            $("#user_name").css("border-color","red");
            return false;
        }
        if($.trim($("#dept").val()).length==0){
            $("#dept").css("border-color","red");
            return false;
        }
        if($.trim($("#overtime_reason").val()).length==0){
            $("#overtime_reason").css("border-color","red");
            return false;
        }
        if($.trim($("#prebatch").val()).length==0){
            $("#prebatch").css("border-color","red");
            return false;
        }
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
        if(time==2){
            alert("加班开始时间和结束时间必须在同一天");
            return false;
        }
        if($.trim($("#approver").val()).length==0){
            if($("#approver").prop("readonly")==false){
                $("#approver").css("border-color","red");
                return false;
            }
        }
        if($("#copyPerson").css("display")=="block"){
            var copy_type = $("input:radio[name='copy_type']:checked").val();
            if(copy_type==null){
                alert("请选择抄送人");
                return false;
            }
        }
        if($("#admin").css("display")=="block"){
            if($.trim($("#adminName").val()).length==0){
                $("#adminName").css("border-color","red");
                return false;
            }
        }
        if($("#manager").css("display")=="block"){
            if($.trim($("#managerName").val()).length==0){
                $("#managerName").css("border-color","red");
                return false;
            }
        }
        document.getElementById("overtimeApplyForm").submit();
    }
    function cancelVerfiy() {
        $("#user_name").css("border-color","");
        $("#dept").css("border-color","");
        $("#overtime_reason").css("border-color","");
        $("#prebatch").css("border-color","");
        $("#approver").css("border-color","");
        $("#adminName").css("border-color","");
        $("#managerName").css("border-color","");
        var startDate = $("*[name='oneStart']");
        for(var i=0;i<startDate.length;i++){
            if($.trim(startDate.eq(i).val()).length==0){
                startDate.eq(i).css("border-color","");
            }
        }
        var endDate = $("*[name='oneEnd']");
        for(var i=0;i<endDate.length;i++){
            if($.trim(endDate.eq(i).val()).length==0){
                endDate.eq(i).css("border-color","");
            }
        }
    }
    function cancelOvertime(th) {
        $(th).parent().remove();
    }
    function formReset() {
        var r = confirm("是否重置？");
        if(r == true){
            window.location.reload();
        }else{
            return ;
        }
    }
    function autoCount(th) {
        //用餐小时
        var deduct_meal_time = 0.0;
        //净计
        var net_meter = 0.0;
        //分钟数
        var min = 0.0;
        //上次一的分钟数
        var upperTime = 0.0;

        var type = $(th).attr("name");
        var startDate;
        var endDate;
        var startPre;
        var endPre;
        var startFix;
        var endFix;
        var startTime;
        var startSecond;
        var endTime;
        var endSecond;
        if(type=="oneStart"){
            startDate = $(th).val();
            endDate = $(th).next().val();
        }else{
            startDate = $(th).prev().val();
            endDate = $(th).val();
        }
        if($.trim(startDate).length!=0 && $.trim(endDate).length!=0){
            var index1 = startDate.lastIndexOf("T");
            startPre = startDate.substring(0,index1);
            startFix = startDate.substring(index1+1,startDate.length);

            var index2 = endDate.lastIndexOf("T");
            endPre = endDate.substring(0,index2);
            endFix = endDate.substring(index2+1,endDate.length);

            var index3 = startFix.lastIndexOf(":");
            startTime = startFix.substring(0,index3);
            startSecond = startFix.substring(index3+1,startFix.length);

            var index4 = endFix.lastIndexOf(":");
            endTime = endFix.substring(0,index4);
            endSecond = endFix.substring(index4+1,endFix.length);

            if(startPre!=endPre){
                $("#timeIsRight").val(2);
                alert("加班开始时间和结束时间必须在同一天，请调整！");
                alert($("#timeIsRight").val());
            }else if(startTime>endTime){
                $("#timeIsRight").val(2);
                alert("加班开始时间小于结束时间，请调整！");
            }else if(startTime<=endTime){
                if(startTime==endTime){
                    if(startSecond>endSecond){
                        $("#timeIsRight").val(2);
                        alert("加班开始时间小于结束时间，请调整！");
                        return ;
                    }
                }
                $("#timeIsRight").val(1);
                //通过验证后对用餐时间和净计进行计算

                var ss = $("#overtimeParent p");
                for(var i=0;i<ss.length;i++){
                    var startDate = ss.eq(i).find("input").eq(0).val();
                    var endDate = ss.eq(i).find("input").eq(1).val();

                    var index1 = startDate.lastIndexOf("T");
                    startPre = startDate.substring(0,index1);
                    startFix = startDate.substring(index1+1,startDate.length);

                    var index2 = endDate.lastIndexOf("T");
                    endPre = endDate.substring(0,index2);
                    endFix = endDate.substring(index2+1,endDate.length);

                    var index3 = startFix.lastIndexOf(":");
                    startTime = startFix.substring(0,index3);
                    startSecond = startFix.substring(index3+1,startFix.length);

                    var index4 = endFix.lastIndexOf(":");
                    endTime = endFix.substring(0,index4);
                    endSecond = endFix.substring(index4+1,endFix.length);

                    //计算加班分钟数
                    min = min+(endTime-startTime)*60;
                    //结束分钟大于开始分钟
                    if(endSecond>startSecond){
                        min = min+(endSecond-startSecond);
                    }else if(endSecond<startSecond){
                        min = min - 60 + (startSecond - endSecond);
                    }
                    //用餐小时计算
                    if(startTime>=18){
                        if(startSecond==0){
                            deduct_meal_time = parseFloat(deduct_meal_time) + 0.5;
                        }
                    }else if(startTime<=18){
                        if(endTime-startTime<=9&&startTime<=11){
                            deduct_meal_time = parseFloat(deduct_meal_time) + 1;
                        }else if(endTime-startTime>9){
                            deduct_meal_time = parseFloat(deduct_meal_time) + 1.5;
                        }
                    }
                    $("#deduct_meal_time").val("");
                    $("#deduct_meal_time").val(deduct_meal_time);
                    //净计
                    net_meter = min/60-deduct_meal_time;
                    $("#net_meter").val("");
                    $("#net_meter").val(net_meter);

                    ss.eq(i).find("input").eq(2).val(net_meter-upperTime);
                    ss.eq(i).find("input").eq(3).val(net_meter-upperTime);
                    //上一次的分钟数
                    upperTime = net_meter;

                }
                /*//用餐小时计算
                if(startTime>=18){
                    if(startSecond==0){
                        deduct_meal_time = parseFloat(deduct_meal_time) + 0.5;
                    }
                }else if(startTime<=18){
                    if(endTime-startTime<=9&&startTime<=11){
                        deduct_meal_time = parseFloat(deduct_meal_time) + 1;
                    }else if(endTime-startTime>9){
                        deduct_meal_time = parseFloat(deduct_meal_time) + 1.5;
                    }
                }
                $("#deduct_meal_time").val("");
                $("#deduct_meal_time").val(deduct_meal_time);
                //净计
                net_meter = parseFloat(net_meter)+min/60-deduct_meal_time;
                $("#net_meter").val("");
                $("#net_meter").val(net_meter);*/
            }
        }
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

    //时间格式化
    Date.prototype.Format = function(fmt) {
        var o = {
            "M+" : this.getMonth()+1,                 //月份
            "d+" : this.getDate(),                    //日
            "h+" : this.getHours(),                   //小时
            "m+" : this.getMinutes(),                 //分
            "s+" : this.getSeconds(),                 //秒
            "q+" : Math.floor((this.getMonth()+3)/3), //季度
            "S"  : this.getMilliseconds()             //毫秒
        };
        if(/(y+)/.test(fmt))
            fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
        for(var k in o)
            if(new RegExp("("+ k +")").test(fmt))
                fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
        return fmt;
    };

</script>
</body>
</html>
