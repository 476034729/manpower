<%--
  Created by Eclipse.
  User: liuyi
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
			<li class="lishow">正常</li>
			<li class="lishow"><span style="background-color: white;"></span>
			</li>
			<li class="lishow">年假</li>
			<li class="lishow"><span style="background-color: red;"></span>
			</li>
			<li class="lishow">事假</li>
			<li class="lishow"><span style="background-color: yellow;"></span>
			</li>
			<li class="lishow">病假</li>
			<li class="lishow"><span style="background-color: green;"></span>
			</li>
			<li class="lishow">换休</li>
			<li class="lishow"><span style="background-color: blue;"></span>
			</li>
			<li class="lishow">婚嫁</li>
			<li class="lishow"><span style="background-color: pink;"></span>
			</li>
			<li class="lishow">丧假</li>
			<li class="lishow"><span style="background-color: black;"></span>
			</li>
			<li class="lishow">出差</li>
			<li class="lishow"><span style="background-color: darkmagenta;"></span>
			</li>
			<li class="lishow" style="margin-left: 20px;">选择年月:</li>
			<li class="lishow"><input size="16" type="text"
				id="form_datetime" placeholder="时间查询菜单"
				style="width: 100px; height: 25px; padding: 0px;" readonly /></li>
			<li class="lishow"><input size="18" type="button" id="query"
				class="btn btn-primary btn-xs" value="查询" /></li>
			<li class="lishow"><input size="18" type="button"
				class="btn btn-primary btn-xs" id="export" value="导出" /></li>
		</ul>
	</div>
	<table class="table table-bordered" id="attendance_record_table">
		<thead>
			<tr id="date">
				<th rowspan="2" colspan="1"><div id="yangshi">姓&nbsp;&nbsp;名</div></th>
				<th rowspan="2" colspan="1"><div id="yangshi1">工&nbsp;&nbsp;号</div></th>
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
        var time = $("#form_datetime").val();
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
    
    // 获得所有项目信息 
    $(document).ready(function () {
    	
    	var nowYear = nowadays.getFullYear();
		var nowMonth = nowadays.getMonth()+1;
		$("#form_datetime").val(nowMonth+"/"+nowYear);
		$("#yangshi").css({"width":"40px","height":"30px","padding":"5px","text-align":"center"});
		$("#yangshi1").css({"width":"40px","height":"30px","padding":"5px","text-align":"center"});
        build_table_once(nowYear,nowMonth);
    	
      /*   $.ajax({
            type: 'get',
            url: 'attendance.do?attendancerecordtypelist',
            async: false,
            cache: false,
            success: function (result) {
                record_list = JSON.parse(result);
                console.log(record_list);
                $("<option></option>").append("请选择状态").appendTo(selector);
                 $.each(record_list, function (index, item) {
                    $("<option></option>").attr("value", item.type).attr("id", item.type).append(item.status).appendTo(selector);
                }) 
                 var nowYear = nowadays.getFullYear();
   				 var nowMonth = nowadays.getMonth()+1;
                build_table_once(nowYear,nowMonth);
            }
        }) */
    });
    
    //建立table基本信息
    function build_table_once(nowYear,nowMonth) {
        $.ajax({
            type: 'get',
            url: 'WorkHourController.do?showlist&year=' + nowYear + '&month= ' + nowMonth,
            async: false,
            cache: false,
            success: function (result) {
            	//alert(result);
                r = JSON.parse(result);
                console.log(r);
                var list = r.obj;
                var Selector;
                $.each(list, function (index, item) {
                    var date = $("<th></th>").css({"text-align":"center"}).attr("rowspan", "1").attr("colspan","1").append(timestampToTime(item.work_date));
                    var week = $("<th></th>").css({"text-align":"center"}).attr("rowspan", "1").attr("colspan","1").append(getWeek(item.work_date));
                    date.appendTo($("#date"));
                    week.appendTo($("#week"));
                })
                 $.ajax({
                	type: 'get',
                	url: 'attendance.do?getnameandjobnum',
                	async: false,
                	cache: false,
                	success: function (infolistresult){
                		b = JSON.parse(infolistresult);
                		console.log(b);
                		  $.each(b,function(index,infoitem){
                			 //console.log(infoitem.jobNum);
                			 var hang = $("<tr></tr>").attr("id",infoitem.jobNum);
                             var name = $("<td></td>").css({"width":"60px","height":"30px","padding":"5px","text-align":"center"}).attr("colspan","1").append(infoitem.userName);
                             var gonghao = $("<td></td>").css({"width":"60px","height":"30px","padding":"5px","text-align":"center"}).attr("colspan","1").append(infoitem.jobNum);
                             hang.appendTo($("#table"));
                             name.appendTo($("#"+infoitem.jobNum));
                             gonghao.appendTo($("#"+infoitem.jobNum));
                	
			                $.each(list, function (index, item) {
			                	if (!parseInt(item.status) == 0) {
			                		 Selector = selector.clone().attr("id", timestampToTime(item.work_date)+"and"+infoitem.jobNum+"weekday");
			                	}else{
			                		 Selector = selector.clone().attr("id", timestampToTime(item.work_date)+"and"+infoitem.jobNum);
			                	}
			                    var status = $("<td></td>").append($("<div></div>").addClass("form-group").append(Selector));
			                    status.appendTo($("#"+infoitem.jobNum));
			                })
                				var tongjiinfo = $("<td></td>").attr("colspan","3").attr("id",("tongji"+infoitem.jobNum));
           	 					tongjiinfo.appendTo($("#"+infoitem.jobNum));
                		})  
                	}
                })
                 
                build_table_twice(nowYear,nowMonth);
            }
        })
    }

    //将工时信息对table再一次补全 
    function build_table_twice(nowYear,nowMonth) {
    	 $.ajax({
             type: 'get',
             url: 'attendance.do?getattendancerecordlist&year=' + nowYear + '&month=' + nowMonth,
             async: false,
             cache: false,
             success: function (result) {
                 r = JSON.parse(result);
                 console.log(result);
                 $.each(r, function (index, item) {
	      	var weekid = '#' + timestampToTime(item.creatTime) +"and"+ item.jobNum +"weekday";
	      	var id = '#' + timestampToTime(item.creatTime) +"and"+ item.jobNum;
	      	if(item.type == 0){
	      		$(weekid).attr("data-id",item.id).append("节假日");
	      		$(id).attr("data-id", item.id).append("正常");
	      	}if(item.type == 1){
	      		$(weekid).attr("data-id",item.id).append("年假").append(item.hour<8?(item.hour+"H"):"1天");
	      		$(id).attr("data-id", item.id).append("年假").append(item.hour<8?(item.hour+"H"):"1天");
	      	}if(item.type == 2){
	      		$(weekid).attr("data-id",item.id).append("事假").append(item.hour<8?(item.hour+"H"):"1天");
	      		$(id).attr("data-id", item.id).append("事假").append(item.hour<8?(item.hour+"H"):"1天");
	      	}if(item.type == 3){
	      		$(weekid).attr("data-id",item.id).append("病假").append(item.hour<8?(item.hour+"H"):"1天");
	      		$(id).attr("data-id", item.id).append("病假").append(item.hour<8?(item.hour+"H"):"1天");
	      	}if(item.type == 4){
	      		$(weekid).attr("data-id",item.id).append("换休").append(item.hour<8?(item.hour+"H"):"1天");
	      		$(id).attr("data-id", item.id).append("换休").append(item.hour<8?(item.hour+"H"):"1天");
	      	}if(item.type == 5){
	      		$(weekid).attr("data-id",item.id).append("婚嫁").append(item.hour<8?(item.hour+"H"):"1天");
	      		$(id).attr("data-id", item.id).append("婚嫁").append(item.hour<8?(item.hour+"H"):"1天");
	      	}if(item.type == 6){
	      		$(weekid).attr("data-id",item.id).append("丧假").append(item.hour<8?(item.hour+"H"):"1天");
	      		$(id).attr("data-id", item.id).append("丧假").append(item.hour<8?(item.hour+"H"):"1天");
	      	}if(item.type == 7){
	      		$(weekid).attr("data-id",item.id).append("出差").append(item.hour<8?(item.hour+"H"):"1天");
	      		$(id).attr("data-id", item.id).append("出差").append(item.hour<8?(item.hour+"H"):"1天");
	      	}
	      	
	          switch(item.type){
	           case 0:
	          	 $(id).css("background-color","white").css("color","#858585");
	          	 $(weekid).css("background-color","white").css("color","#858585");
	          	break;
	           case 1:
	          	 $(id).css("background-color","red").css("color","white");
	          	 $(weekid).css("background-color","red").css("color","white");
	          	 break;
	           case 2:
	          	 $(id).css("background-color","yellow").css("color" ,"#858585");
	          	 $(weekid).css("background-color","yellow").css("color" ,"#858585");
	          	 break;
	           case 3:
	          	 $(id).css("background-color","green").css("color","white");
	          	 $(weekid).css("background-color","green").css("color","white");
	          	 break;
	           case 4:
	          	 $(id).css("background-color","blue").css("color","white");
	          	 $(weekid).css("background-color","blue").css("color","white");
	          	 break;
	           case 5:
	          	 $(id).css("background-color","pink").css("color","white");
	          	 $(weekid).css("background-color","pink").css("color","white");
	          	 break;
	           case 6:
	          	 $(id).css("background-color","black").css("color","white");
	          	 $(weekid).css("background-color","black").css("color","white");
	          	 break;
	           case 7:
	          	 $(id).css("background-color","darkmagenta").css("color","white");
	          	 $(weekid).css("background-color","darkmagenta").css("color","white");
	          	 break;
	           default:
	          	 $(id).css("background-color","white").css("color","#858585");
	          	 $(weekid).css("background-color","white").css("color","#858585");
	          }
	      }) 
  		}
	})
			var yearsum = 0;
    		var shisum = 0;
    		var binsum = 0;
    		var huansum = 0;
    		var marrysum = 0;
    		var sangsum = 0;
    		var chusum = 0;
    	 	$.ajax({
            	type: 'get',
            	url: 'attendance.do?getnameandjobnum',
            	async: false,
            	cache: false,
            	success: function (infolistresult){
            		b = JSON.parse(infolistresult);
            		console.log(b);
            		  $.each(b,function(index,infoitem){
            			 
						    	 $.ajax({
						            type: 'get',
						            url: 'attendance.do?getattendancerecordlist&year=' + nowYear + '&month=' + nowMonth,
						            async: false,
						            cache: false,
						            success: function (result) {
						                r = JSON.parse(result);
						                console.log(result);
						                $.each(r, function (index, item) {
								                	if(infoitem.id == item.userId){
								                		if(item.type == 1){
								                			yearsum += item.hour ;
								                		}else if(item.type == 2){
								                			shisum += item.hour;
								                		}else if(item.type == 3){
								                			binsum += item.hour;
								                		}else if(item.type == 4){
								                			huansum += item.hour;
								                		}else if(item.type == 5){
								                			marrysum += item.hour;
								                		}else if(item.type == 6){
								                			sangsum += item.hour;
								                		}else if(item.type == 7){
								                			chusum += item.hour;
								                		}
								                			
								                	}	
                									
					      					  })
					     					}
					 					})
					 					$("#tongji"+infoitem.jobNum).append(
					 							"年假："+(yearsum / 8+"天"+"("+yearsum+"小时)")+
					 							",事假："+(shisum / 8+"天("+shisum+"小时)")+
					 									",病假"+(binsum / 8+"天("+binsum+"小时)")+
					 									",换休："+(huansum / 8+"天("+huansum+"小时)")+
					 									",婚嫁："+(marrysum / 8+"天("+marrysum+"小时)")+
					 											",丧假："+(sangsum / 8+"天("+sangsum+"小时)")+
					 											",出差："+(chusum / 8+"天("+chusum+"小时)")); 
						    	  		yearsum = 0;
						    		 	shisum = 0;
						    		 	binsum = 0;
						    		 	huansum = 0;
						    			marrysum = 0;
						    			sangsum = 0;
						    			chusum = 0;
            						})
            					}
            				})
         var zongji = $("<div></div>").css({"width":"400px","text-align":"center"}).append("总&nbsp;&nbsp;计");
       	 var tongji = $("<th></th>").attr("id","tongji").attr("rowspan", "2").attr("colspan","3");
       	 tongji.appendTo($("#date"));
       	 zongji.appendTo($("#tongji"));
   	
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
	$('#form_datetime').datetimepicker({
        format: 'mm/yyyy',
        autoclose: true,
        todayBtn: true,
        startView: 'year',
        minView: 'year',
        maxView: 'decade',
        language: 'zh-CN',
    });
     //查询按钮事件
    $("#query").click(function () {
    	var time = $("#form_datetime").val();
    	if (time == null || time.length == 0) {
            alert("请选择日期!");
            return ;
        }
    	//当所选月份改变时先清空一次表格内容
        $("#attendance_record_table").empty();
       var thead = $("<thead></thead>").append($("<tr></tr>").attr("id", "date").append(
           $("<th></th>").attr("rowspan", "2").attr("colspan", "1").append("&nbsp;&nbsp;姓&nbsp;&nbsp;名&nbsp;"))
           .append($("<th></th>").attr("rowspan", "2").attr("colspan", "1").append("&nbsp;工&nbsp;&nbsp;号"))).append($("<tr></tr>").attr("id", "week"));
       var tbody = $("<tbody></tbody>").attr("id","table");
       thead.appendTo($("#attendance_record_table"));
       tbody.appendTo($("#attendance_record_table"));
        //获得选择的年份和月份
        var queryMonth = time.split("/")[0];
        var queryYear = time.split("/")[1];
        build_table_once(queryYear,queryMonth);
    });
     
    /* //日期插件
    $("#form_datetime").datetimepicker({
        format: 'mm/yyyy',//显示格式
        startView: 'year',
        minView:'year',
        maxView:'decade',
        autoclose: true//选择后自动关闭
    }).on('changeDate',function (ev) {
        //当所选月份改变时先清空一次表格内容
         $("#attendance_record_table").empty();
        var thead = $("<thead></thead>").append($("<tr></tr>").attr("id", "date").append(
            $("<th></th>").attr("rowspan", "2").attr("colspan", "1").append("&nbsp;&nbsp;姓&nbsp;&nbsp;&nbsp;&nbsp;名&nbsp;"))
            .append($("<th></th>").attr("rowspan", "2").attr("colspan", "1").append("工&nbsp;&nbsp;号"))).append($("<tr></tr>").attr("id", "week"));
        var tbody = $("<tbody></tbody>").attr("id","table");
        thead.appendTo($("#attendance_record_table"));
        tbody.appendTo($("#attendance_record_table"));
       // console.log(ev.date.getFullYear().toString());
      // alert(ev.date.getFullYear().toString()+"&month="+(ev.date.getMonth()+1).toString());
      build_table_once(ev.date.getFullYear().toString(),(ev.date.getMonth()+1).toString());
    });
 */
 /*    <!-- 重新构成界面函数 -->
 function reset_table() {
     $("#attendance_record_table").empty();
     var thead = $("<thead></thead>").append($("<tr></tr>").attr("id", "date").append(
         $("<th></th>").attr("rowspan", "2").attr("colspan", "1").append("&nbsp;&nbsp;姓&nbsp;&nbsp;&nbsp;&nbsp;名&nbsp;"))
         .append($("<th></th>").attr("rowspan", "2").attr("colspan", "1").append("工&nbsp;&nbsp;号"))).append($("<tr></tr>").attr("id", "week"));
     var tbody = $("<tbody></tbody>").attr("id","table");
     thead.appendTo($("#attendance_record_table"));
     tbody.appendTo($("#attendance_record_table"));
     var nowYear = nowadays.getFullYear();
		var nowMonth = nowadays.getMonth()+1;
     build_table_once(nowYear,nowMonth);
 }
 <!-- 重置按钮事件 -->
 $("#reset").click(function () {
     var r = confirm("是否重置？");
     if (r == true){
         reset_table();
     }else {
         alert("false");
     }
 })
  <!-- 提交按钮事件 -->
 $("#submit").click(function () {
     var ids = new Array();
     var codes = new Array();
     var texts = new Array();
     var amCodeTdList = $("#amCode").find("td");
     var amInputTdList = $("#amWork").find("td");
     var pmCodeTdList = $("#pmCode").find("td");
     var pmInputTdList = $("#pmWork").find("td");
     for (var i = 2; i < amCodeTdList.length; i++) {
         ids.push(amCodeTdList.eq(i).find("select").attr("data-id"));
         codes.push(amCodeTdList.eq(i).find("select").val());
         texts.push(amInputTdList.eq(i - 1).find("textarea").val());
     }
     for (var j = 2; j < pmCodeTdList.length; j++) {
         ids.push(pmCodeTdList.eq(j).find("select").attr("data-id"));
         codes.push(pmCodeTdList.eq(j).find("select").val());
         texts.push(pmInputTdList.eq(j - 1).find("textarea").val());
     }
     $.ajax({
         type: 'post',
         url: 'WorkHourController.do?submitworkhourlist',
         data: {
             'ids': JSON.stringify(ids),
             'codes': JSON.stringify(codes),
             'texts': JSON.stringify(texts)
         },
         async: false,
         cache: false,
         success: function (result) {
             var j = JSON.parse(result);
             alert(j.msg);
             reset_table();
         }
     })
 }) */
</script>
</body>
</html>
