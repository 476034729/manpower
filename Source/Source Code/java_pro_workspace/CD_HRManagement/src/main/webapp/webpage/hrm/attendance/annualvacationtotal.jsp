<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Public_Stee_Develope
  Date: 2018/8/2
  Time: 14:29
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


    <style>
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

        th{
            text-align:center;
            font-size: 16px;
        }
        .thColor{
            background: #9AC3E1;
        }
        .thHight{
            height: 40px;
        }
        .thNameColor{
            background: #CCCCCC;
        }
        input{
            width: 100%;
            text-align: center;
        }
        td{
            text-align: center;
        }
       #export{
       		float:right;
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
<input size="18" type="button"
				class="btn btn-primary btn-xs" id="export" value="导出" />
    <table width="100%" id = "attendance_record_table">
        <thead>
        <tr>
            <th width="10%" class="thNameColor">姓名</th>
            <th width="10%" class="thColor thHight">去年年假天数</th>
            <th width="10%" class="thColor thHight">今年年假天数</th>
            <th width="10%" class="thColor thHight">年假总天数</th>
            <th width="20%" class="thColor thHight">已使用记录</th>
            <th width="10%" class="thColor thHight">已使用天数</th>
            <th width="10%" class="thColor thHight">剩余天数</th>
            <th width="20%" class="thColor thHight">备注</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="annualLeaveStatistics" items="${annualLeaveStatisticsList}">
            <tr id="${annualLeaveStatistics.id}">
                <input type="hidden" name="id" value="${annualLeaveStatistics.id}">
                <td width="10%" class="thNameColor">
                        ${annualLeaveStatistics.user_name}
                </td>
                <td width="10%">
                    <input type="text" name="previous_year" value="${annualLeaveStatistics.previous_year}" onblur="calculation(this)">
                </td>
                <td width="10%">
                    <input type="text" name="this_year" value="${annualLeaveStatistics.this_year}" onblur="calculation(this)">
                </td>
                <td width="10%">
                    <input type="text" name="count_annual_leave" value="${annualLeaveStatistics.count_annual_leave}" readonly>
                </td>
                <td width="20%">
                    <input type="text" name="changofrest_time" value="${annualLeaveStatistics.changofrest_time}" readonly>
                </td>
                <td width="10%">
                    <input type="text" name="use_days" value="${annualLeaveStatistics.use_days}" readonly>
                </td>
                <td width="10%">
                    <input type="text" name="surplus_days" value="${annualLeaveStatistics.surplus_days}" readonly>
                </td>
                <td width="20%">
                    <input type="text" name="remark" value="${annualLeaveStatistics.remark}">
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <br>
    <br>
    <div  class="btn_option" >
        <button type="button" class="btn btn-primary" id="submit" onclick="tableSubmit()">提交</button>
        <button type="button" class="btn btn-danger" id="reset" style="margin-left: 50px">重置</button>
    </div>
    <script type="text/javascript" src="plug-in/jquery/jquery-1.8.3.min.js"></script>
    <script type="text/javascript" src="plug-in/jquery/jquery.cookie.js"></script>
	<script type="text/javascript" src="plug-in/mutiLang/en.js"></script>
	<script type="text/javascript" src="plug-in/mutiLang/zh-cn.js"></script>
	<script type="text/javascript" src="plug-in/login/js/jquery.tipsy.js"></script>
	<script type="text/javascript" src="plug-in/login/js/iphone.check.js"></script>
	<script src="plug-in/bootstrap/js/bootstrap.js"></script>
	<script src="plug-in/datetimepicker/js/bootstrap-datetimepicker.min.js"></script>
	<script type="text/javascript"
		src="plug-in/jquery/tableExport/tableExport.js"></script>
    <script type="text/javascript">
    $("#export").click(function(){
            $('#attendance_record_table').tableExport({
                type:'excel',
                escape:'false',
                fileName: '年假统计'
            });
    });
        function calculation(th) {
            var parentTr = $(th).parent().parent().children();
            var before = parentTr.eq(2).find("input").eq(0).val()
            var thisYear = parentTr.eq(3).find("input").eq(0).val();
            var sumDays = parentTr.eq(4).find("input").eq(0);
            var surplusDays = parentTr.eq(7).find("input").eq(0);
            var countDays = sumDays.val();
            sumDays.val(parseFloat(before)+parseFloat(thisYear));
            var countDays1 = sumDays.val();
            surplusDays.val(parseFloat(surplusDays.val())+parseFloat(countDays1)-parseFloat(countDays));
        }

        function tableSubmit() {
            var ids = new Array();
            var befores = new Array();
            var thisYears = new Array();
            var sumDays = new Array();
            var surplusDays = new Array();
            var remarks = new Array();
          /*    var idList = $("td[name='id']");  */
             var idList= $("input[name=id]")
            var beforeList = $("input[name='previous_year']");
            var thisYearList = $("input[name='this_year']");
            var sumDayList = $("input[name='count_annual_leave']");
            var surplusDayList = $("input[name='surplus_days']");
            var remarkList = $("input[name='remark']");
            for(var i=0;i<idList.length;i++){
                ids.push(idList.eq(i).val());
                //ids.push(idList.eq(i).attr("datavaladadad"));
            }
            for(var i=0;i<beforeList.length;i++){
                befores.push(beforeList.eq(i).val());
            }
            for(var i=0;i<thisYearList.length;i++){
                thisYears.push(thisYearList.eq(i).val());
            }
            for(var i=0;i<sumDayList.length;i++){
                sumDays.push(sumDayList.eq(i).val());
            }
            for(var i=0;i<surplusDayList.length;i++){
                surplusDays.push(surplusDayList.eq(i).val());
            }
            for(var i=0;i<remarkList.length;i++){
                remarks.push(remarkList.eq(i).val());
            }
            console.log(JSON.stringify(ids));
             $.ajax({
                type: 'post',
                url: 'attendance.do?saveAnnualVacation',
                data: {
                    'idList': JSON.stringify(ids),
                    'beforeList': JSON.stringify(befores),
                    'thisYearList': JSON.stringify(thisYears),
                    'sumDayList': JSON.stringify(sumDays),
                    'surplusDayList': JSON.stringify(surplusDays),
                    'remarkList': JSON.stringify(remarks)
                },
                async: false,
                cache: false,
                success: function (result) {
                    var r = JSON.parse(result);
                    if(r.msg=="ok"){
                        alert("保存成功");
                    }
                }
            }); 

        }
    </script>
</body>
</html>
