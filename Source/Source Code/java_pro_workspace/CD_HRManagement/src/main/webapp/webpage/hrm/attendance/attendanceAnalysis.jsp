<%--
  Created by Eclipse.
  User: administrator
  Date: 2018/12/7
  Time: 9:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
<%--<title>Title</title>--%>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta charset="utf-8" />
<%--<title><t:mutiLang langKey="jeect.platform"/></title>--%>
<link rel="shortcut icon" href="images/favicon.ico">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
<!-- bootstrap & fontawesome -->
<link rel="stylesheet" href="plug-in/ace/css/bootstrap.css" />
<link rel="stylesheet" href="plug-in/ace/css/font-awesome.css" />
<link rel="stylesheet" type="text/css"
	href="plug-in/accordion/css/accordion.css">
<!-- text fonts -->
<link rel="stylesheet" href="plug-in/ace/css/ace-fonts.css" />

<link rel="stylesheet" href="plug-in/ace/css/jquery-ui.css" />
<!-- ace styles -->
<link rel="stylesheet" href="plug-in/ace/css/ace.css"
	class="ace-main-stylesheet" id="main-ace-style" />
<link rel="stylesheet"
	href="plug-in/datetimepicker/css/bootstrap-datetimepicker.min.css">

<!--[if lte IE 9]>
    <link rel="stylesheet" href="plug-in/ace/css/ace-part2.css" class="ace-main-stylesheet"/>
    <![endif]-->

<!--[if lte IE 9]>
    <link rel="stylesheet" href="plug-in/ace/css/ace-ie.css"/>
    <![endif]-->


<style>
.btn_option {
	position: fixed;
	left: 50%;
	bottom: 0%;
	transform: translateX(-50%);
}

#submit {
	transition: all 0.3s;
	box-shadow: 0px 0px 5px #428bca !important;
}

#submit:hover {
	box-shadow: 0px 0px 20px #428bca !important;
	background-color: #428bca !important;
}

#reset {
	transition: all 0.3s;
	box-shadow: 0px 0px 5px #d15b47 !important;
}

#reset:hover {
	box-shadow: 0px 0px 20px #d15b47 !important;
	background-color: #d15b47 !important;
}

#ulshow {
	display: block;
	width: auto;
	height: auto;
}

.lishow {
	font-size: 15px;
	list-style: none;
	float: left;
	width: auto;
	height: auto;
	margin-right: 3px;
	margin-bottom: 5px;
	text-align: center;
}

.lishow span {
	border: solid 1px black;
	width: 30px;
	height: 20px;
	display: block;
}

#tb_title {
	border: solid 1px black;
}

#tb_title td {
	border: solid 1px black;
}

#firsttitle {
	margin-left: 200px;
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



<body style="overflow: auto; -webkit-overflow-scrolling: touch;">
	<div style="width: 100%; height: auto; visibility: visible;"
		id="firsttitle">
		<ul id="ulshow">
			
			<li class="lishow" style="margin-left: 20px;">已打卡人数:</li>
			<li class="lishow"><input id="cardingsize" size="8" type="text" readonly class="cardingsize"></li>
			
			<li class="lishow" style="margin-left: 20px;">选择开始时间:</li>
			<li class="lishow"><input id="form_startdatetime" size="16" type="text" readonly class="form_datetime"></li>
			<li class="lishow" style="margin-left: 20px;">选择结束时间:</li>
			<li class="lishow"><input id="form_enddatetime" size="16" type="text" readonly class="form_datetime"></li>
			
			<li class="lishow"><input size="18" type="button" id="query"
				class="btn btn-primary btn-xs" value="查询" /></li>
			<li class="lishow"><input size="18" type="button"
				class="btn btn-primary btn-xs" id="export" value="导出" /></li>
		</ul>
	</div>
	<table class="table table-bordered" id="attendance_record_table">
		<thead>
			<tr id="date">
			<!--  	<th rowspan="2" colspan="1"><div id="yangshi">姓&nbsp;&nbsp;名</div></th>
				<th rowspan="2" colspan="1"><div id="yangshi1">工&nbsp;&nbsp;号</div></th>-->
			</tr>
			<tr id="week">

			</tr>
		</thead>

		<tbody id="table">
		</tbody>
	</table>
	<!-- <div class="btn_option">
		<button type="button" class="btn btn-primary" id="submit">提交</button>
		<button type="button" class="btn btn-danger" id="reset"
			style="margin-left: 50px">重置</button>
	</div> -->
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
	<script>
    var nowadays = new Date();
    var project_list;
    var selector = $("<div></div>").css({"width":"80px","height":"30px","padding":"5px","text-align":"center"});
    //导出为excel表事件
    $("#export").click(function(){
        var time = $("#form_startdatetime").val();
        //获得选择的年份和月份
        var year = time.split("/")[1];
        var month = time.split("/")[0];
        var showtime = year+"年"+month+"月";
        var tableContainer = $("#attendance_record_table").html();
        if ($.trim(tableContainer) == null || $.trim(tableContainer) == 0) {
            alert("请查询数据!");
            return;
        }
            $('#attendance_record_table').tableExport({
                type:'excel',
                escape:'false',
                fileName: showtime+'考勤记录统计清单'
            });
    });
    
    
    var begTime=$("#form_startdatetime").val();
    var endTime=$("#form_enddatetime").val();
    
    
    //建立table基本信息
    function build_table_once(begTime,endTime) {
        $.ajax({
            type: 'get',
            url: 'AttendanceAnalysis.do?getAttendanceAnalysisTimes&begtime=' + begTime + '&endtime= ' + endTime,
            async: false,
            cache: false,
            success: function (result) {
                r = JSON.parse(result);
                var list = r.obj;
                var Selector;                                
                $.each(list, function (index, item) {
                	$.each(item, function (index, items) {
                		$("#yangshi").css({"width":"40px","height":"30px","padding":"5px","text-align":"center"});
                		$("#yangshi1").css({"width":"40px","height":"30px","padding":"5px","text-align":"center"});
                		var date = $("<th></th>").css({"text-align":"center"}).attr("rowspan", "1").attr("colspan","1").append(timestampToTime(items.work_date.time));
                        var week = $("<th></th>").css({"text-align":"center"}).attr("rowspan", "1").attr("colspan","1").append(getWeek(items.work_date.time));
                        date.appendTo($("#date"));
                        week.appendTo($("#week"));
                		
                	})
                })
                
                $.ajax({
                	type: 'get',
                	url: 'attendance.do?getnameandjobnum',
                	async: false,
                	cache: false,
                	success: function (infolistresult){
                		b = JSON.parse(infolistresult);
                		  $.each(b,function(index,infoitem){
                			 //console.log(infoitem.jobNum);
                			 var hang = $("<tr></tr>").attr("id",infoitem.jobNum);
                             var name = $("<td></td>").css({"width":"60px","height":"30px","padding":"5px","text-align":"center"}).attr("colspan","1").append(infoitem.userName);
                             var gonghao = $("<td></td>").css({"width":"60px","height":"30px","padding":"5px","text-align":"center"}).attr("colspan","1").append(infoitem.jobNum);
                             hang.appendTo($("#table"));
                             name.appendTo($("#"+infoitem.jobNum));
                             gonghao.appendTo($("#"+infoitem.jobNum));
                	
			                $.each(list, function (index, item) {
			                	$.each(item, function (index, items) {
			                		if (!parseInt(items.status) == 0) {
				                		 Selector = selector.clone().attr("id", timestampToTime(items.work_date.time)+"and"+infoitem.jobNum+"weekday");
				                	}else{
				                		 Selector = selector.clone().attr("id", timestampToTime(items.work_date.time)+"and"+infoitem.jobNum);
				                	}
				                    var status = $("<td></td>").append($("<div></div>").addClass("form-group").append(Selector));
				                    status.appendTo($("#"+infoitem.jobNum));
			                	})
			                	
			                })
                		})  
                	}
                })
                 
                build_table_twice(begTime,endTime);
            }
        })
    }
  //将工时信息对table再一次补全 
    function build_table_twice(begTime,endTime) {
    	 $.ajax({
             type: 'get',
             url: 'AttendanceAnalysis.do?getAttendanceAnalysis&begtime=' + begTime + '&endtime=' + endTime,
             async: false,
             cache: false,
             success: function (result) {
                 r = JSON.parse(result);
                // console.log(result);
                 var list=r.obj;
                 $.each(list, function (index, item) {
                	 
                	 $.each(item, function (index, items) {
                		 if(!("size" in items)){
                			 var weekid = '#' + timestampToTime(items.creatTime.work_date.time) +"and"+ items.jobNum +"weekday";
        			      	 var id = '#' + timestampToTime(items.creatTime.work_date.time) +"and"+ items.jobNum;
        			      	 if(items.analysis=="暂无打卡"){
        			      		$(weekid).attr("data-id",items.jobNum).append("无需打卡");
               			     	$(id).attr("data-id", items.jobNum).append(items.analysis);
        			      	 }else{
        			      		$(weekid).attr("data-id",items.jobNum).append(items.analysis);
               			     	$(id).attr("data-id", items.jobNum).append(items.analysis);
        			      	 }
        			          switch(items.status){
        			          case 3:
         			          	 $(id).css("background-color","white").css("color","#858585");
         			          	 $(weekid).css("background-color","white").css("color","#858585");
         			          	break;
        			          	case 2:
        			          	 $(id).css("background-color","red").css("color","#858585");
        			          	 $(weekid).css("background-color","red").css("color","#858585");
        			          	break;
        			           case 1:
        			          	 $(id).css("background-color","red").css("color","white");
        			          	 $(weekid).css("background-color","red").css("color","white");
        			          	 break;
        			           case 0:
        			          	 $(id).css("background-color","yellow").css("color" ,"#858585");
        			          	 $(weekid).css("background-color","blue").css("color" ,"white");
        			          	 break;
        			           default:
        			          	 $(id).css("background-color","white").css("color","#858585");
        			          	 $(weekid).css("background-color","white").css("color","#858585");
        			          }
                		 }else{
                			 $("#cardingsize").attr('value', items.size); 
                		 }
                		 
                	 })
			      	 
			     })
             }
    	 })
  }
    	 

    //时间戳转日期 
    function timestampToTime(timestamp) {
        var date = new Date(timestamp);//时间戳为10位需*1000，时间戳为13位的话不需乘1000
        Y = date.getFullYear() + '-';
        M = (date.getMonth() + 1) + '-';
        D = date.getDate();
        return Y + M + D;
    }

    //时间戳获取星期几
    function getWeek(time) {

        var timedat = new Date(time);

        var week;

        if (timedat.getDay() == 0) week = "星期日";

        if (timedat.getDay() == 1) week = "星期一";

        if (timedat.getDay() == 2) week = "星期二";

        if (timedat.getDay() == 3) week = "星期三";

        if (timedat.getDay() == 4) week = "星期四";

        if (timedat.getDay() == 5) week = "星期五";

        if (timedat.getDay() == 6) week = "星期六";

        return week;
    }
 
        //时间选择器
	$(".form_datetime").datetimepicker({format: 'yyyy-mm-dd hh:ii:ss'});
     //查询按钮事件
    $("#query").click(function () {
    	var begtime = $("#form_startdatetime").val();
    	var endtime = $("#form_enddatetime").val();
    	if((new Date(endtime.replace(/-/g,"\/"))) < (new Date(begtime.replace(/-/g,"\/")))){
    		alert("请输入正确的时间格式!")
    		return;
    	}
    	if (begtime == null || begtime.length == 0 || endtime==null || endtime.length==0) {
            alert("请选择日期!");
            return ;
        }
    	//当所选月份改变时先清空一次表格内容
        $("#attendance_record_table").empty();
       var thead = $("<thead></thead>").append($("<tr></tr>").attr("id", "date").append(
           $("<th></th>").attr("rowspan", "2").attr("colspan", "1").attr("id","yangshi").append("&nbsp;&nbsp;姓&nbsp;&nbsp;名&nbsp;"))
           .append($("<th></th>").attr("rowspan", "2").attr("colspan", "1").attr("id","yangshi1").append("&nbsp;工&nbsp;&nbsp;号"))).append($("<tr></tr>").attr("id", "week"));
       var tbody = $("<tbody></tbody>").attr("id","table");
       thead.appendTo($("#attendance_record_table"));
       tbody.appendTo($("#attendance_record_table"));
        
        build_table_once(begtime,endtime);
    });
</script>
</body>
</html>
