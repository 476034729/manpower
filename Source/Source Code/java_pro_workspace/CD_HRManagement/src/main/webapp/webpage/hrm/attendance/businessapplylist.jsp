<%--
  Created by IntelliJ IDEA.
  User: 52629
  Date: 2018/11/2
  Time: 17:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/context/mytags.jsp"%>
<html>
<head>
    <title>Title</title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <!--
    <link rel="stylesheet" type="text/css" href="styles.css">
    -->
    <link rel="stylesheet" href="plug-in/jquery-easyui-1.5.3/themes/gray/easyui.css" />
    <link rel="stylesheet" href="plug-in/jquery-easyui-1.5.3/themes/icon.css" />
    <link rel="stylesheet" href="plug-in/ace/css/bootstrap.css"/>
    <link rel="stylesheet" href="plug-in/ace/css/font-awesome.css"/>
    <link rel="stylesheet" type="text/css" href="plug-in/accordion/css/accordion.css">
    <link rel="stylesheet" type="text/css" href="plug-in/hrm/css/resttime.css">
    <link rel="stylesheet" type="text/css" href="plug-in/datetimepicker/css/bootstrap-datetimepicker.min.css">
    <!-- text fonts -->
    <link rel="stylesheet" href="plug-in/ace/css/ace-fonts.css"/>
    <script src="plug-in/jquery-easyui-1.5.3/jquery.min.js"></script>
    <script src="plug-in/jquery-easyui-1.5.3/jquery.easyui.min.js"></script>
    <script src="plug-in/jquery-easyui-1.5.3/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="plug-in/bootstrap/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="plug-in/datetimepicker/js/bootstrap-datetimepicker.min.js"></script>
    <style>
        .datagrid-header-row td{background-color:#9AC3E1;color:black;height:32px;}
        .operate{
            border: black;
            color: #0b6cbc;
        }
        a:hover{
        	color:red;
        }
        
        #toolbar{
            float: left;
        }
        .btn-primary{
            width: 80px;
            height: 35px;
            background: #0e78c9;
            color: white;
        }
    </style>
</head>

<body>
<table id="table" width="100%" height="95%" title="出差申请查询"></table>
<input type="hidden" id="userId" name="userId" value="${user.id}" >
<div id="toolbar" >
    <span for="status">人事确认状态</span>
    <select name="status" id="status" style="height:25px">
        <option value="">请选择...</option>
        <option value="0">未确认</option>
        <option value="1">已确认</option>
    </select>
    <span for="userName" style="height:25px;display:${roleType==0?"none":''}">申请人</span>
    <input type="text" id="applyName" name="applyName" style="display:${roleType==0?"none":''}">
    <span >申请日期</span>
    <input type="date" id="startDate" name="startDate" style="height:25px;">-
    <input type="date" id="endDate" name="endDate" style="height:25px;">
    <a onclick="query()" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true">查询</a>
    <button class="btn-primary" id="add"><a  href="#" style="text-decoration: none;color: white">添加申请</a></button>
</div>
<!-- AddModal -->

<div class="modal fade" id="add_vacationApply_modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="myModalLabel">出差申请表</h4>
            </div>
            <div class="modal-body">
            		    申请表表单
                <form action="attendance.do?businessapply" method="post" style="height: 80%;" id="add_vacationApply_form" enctype="multipart/form-data">
                    <table border="1" cellspacing="0" cellpadding="0" id="restTable" class="rest_table">

                        <tr height="40px">
                            <td class="noneborderright">姓&nbsp;&nbsp;名：</td>
                            <td class="noneborderboth"><input type="text" id="userName" class="apply_name" name="userName" readonly/>
                            </td>
                            <td class="noneborderboth">部&nbsp;&nbsp;门：</td>
                            <td class="noneborderleft"><input type="text" id="dept" class="depart_name apply_name" name="dept"
                                                              style="width:156px;" readonly/></td>
                        </tr>
                        <tr height="40px">
                            <td class="noneborderright">申请休假类型：</td>
                              <td class="noneborderboth"><input type="text" id="vacationType" name="vacationType" class="vacation_type apply_name" name="vacation_type" value="出差" readonly/>
                            </td>
                        </tr>
                        <tr height="30px">
                            <td class="noneborderright">请假开始时间：</td>
                            <td class="noneborderboth">
                                <div>
                                    <input type="datetime-local" id="beginTime" name="beginTime"  onblur="countTime(this)" style="height:25px;" >
                                </div>
                            </td>
                            <td class="noneborderboth">请假结束时间：</td>
                            <td class="noneborderleft">
                                <div>
                                    <input type="datetime-local" id="endTime" name="endTime" onblur="countTime(this)" style="height:25px;">
                                </div>
                            </td>
                        </tr>
                        <tr height="40px">
                            <td class="noneborderright">共计天数：</td>
                            <td class="noneborderboth">
                                <input type="text" id="totalDays"  name="countDay" style="border:none; border-bottom:2px solid #eee;outline: none;width:50px;text-align:center;" readonly>(天)
                            </td>
                             <td class="noneborderboth">共计小时数：</td>
                            <td class="noneborderleft">
                                <input type="text" id="totalHours" name="countHour" style="border:none; border-bottom:2px solid #eee;outline: none;width:50px;text-align:center;" readonly>(H)
                            </td>
                        </tr>
                        <tr height="90px">
                            <td class="noneborderright">请假理由：</td>
                            <td colspan="3" class="noneborderleft">
                                <textarea id="leaveReason" class="rest_reason" name="leaveReason"></textarea>
                            </td>
                        </tr>
                          <tr height="30px">
                            <td class="noneborderright">附件：</td>
                            <td  class="noneborderboth">
                                <input type="file" id="file" name="file"></input>
                            </td>
                        </tr>
                        <tr height="120px">
                            <td colspan="4" style="margin-top:-9px;" id="site">
                                <div>休假地点：</div>
                                <div style="margin-left:15px">
                                    <input type="radio" id="radion" name="vacationPlace" value="1"   class="in_china"/>
                                    <span>国内</span>
                                    <span style="margin-left:15px">联系方式：</span>
                                    <input type="text" id="contactInformation" class="contact_me" name="contactInformation" value=""/>
                                </div>
                                <div style="margin-left:15px">
                                    <input type="radio" id="radion" name="vacationPlace" value="2" class="out_china"/>
                                    <span>国外</span>
                                    <span style="margin-left:15px">请说明：</span>
                                    <input type="text" id="instruction" class="details_me contact_me" name="instruction" value=""/>
                                </div>
                                <div style="margin-top:9px;margin-left:10px;margin-bottom:5px;">
                                    <span class="sign">申请者签字：</span>
                                    <input type="text" id="applicantSign" class="signer_me contact_me" name="applicantSign" value=""
                                           style="width:138px"/>
                                    <span style="margin:0 12px">日期:</span>
                                      <input type="text" id="gainTime" name="applyDate" value=""
                                           style="width:138px" readonly/>
                                </div>
                            </td>
                        </tr>
                    </table>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal" id="close">Close</button>
                <button type="button" class="btn btn-primary" id="add_button">提交</button>
            </div> 
            
        </div>
    </div>
</div>

 
<script>
//判断时间是否正确
function countTime(th,c) {
	
   /*  var type = $(th).attr("name");
    var beginTime;
    var endTime;
    if(type=="beginTime"){
    	beginTime = $(th).val();
    	endTime = $(th).next().val();
    	alert(beginTime+endTime);
    }else{
    	beginTime = $(th).prev().val();
    	endTime = $(th).val();
    	alert(beginTime+endTime);
    } */
    var beginTime = $("input[name='beginTime']").val();
    var endTime = $("input[name='endTime']").val();
    //判断是否为空
    if(($.trim(beginTime).length!=0 && $.trim(endTime).length!=0)||c!=undefined){
        var startMill = Date.parse(beginTime);
        var endMill = Date.parse(endTime);
        //判断开始时间是否小于结束时间
        if(startMill>endMill){
            alert("开始时间大于结束时间，请调整！");
            return false;
        }else if(startMill==endMill){
            alert("开始时间等于结束时间，请调整！");
            return false;
        }
        	var sum = 0.0;
            var startDate = startMill;
            var endDate = endMill;
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
        var day = sum/8;
        $("#totalDays").val(day);
        $("#totalHours").val(sum);
    }
}
	$("#add").click(function(){
		  getinfo();
		 $("#add_vacationApply_modal").modal();
	})
	$("#close").click(function(){
		$("#add_vacationApply_form")[0].reset(); 
	})
	
	 $("#add_button").click(function (){
		 	//请假时间校验是否选择
			var beginTime = $("input[name='beginTime']").val();
		    var endTime = $("input[name='endTime']").val();
		    if($.trim(beginTime).length==0){
		        $("#beginTime").css("border-color","red");
		        return false;
		     }
	        if($.trim(endTime).length==0){
	            $("#endTime").css("border-color","red");
	            return false;
	         }
		 	//请假事由
	        if($.trim($("#leaveReason").val()).length==0){
	            $("#leaveReason").css("border-color","red");
	            return false;
	        }
		 	//文件
		 	if($("#file").val().length == 0){
		 		alert("请选择上传的附件");
		 		return false;
		 	}
	        //休假地点
	        var vacation_place = $("input:radio[name='vacationPlace']:checked").val();
	        if(vacation_place==null){
	            alert("请选择休假地点");
	            return false;
	        }
	        //联系方式
	        if($.trim($("#contactInformation").val()).length==0){
	            $("#contactInformation").css("border-color","red");
	            return false;
	        }
	        //请说明
	        if($.trim($("#instruction").val()).length==0){
	            $("#instruction").css("border-color","red");
	            return false;
	        }
	        //申请者签字
	        if($.trim($("#applicantSign").val()).length==0){
	            $("#applicantSign").css("border-color","red");
	            return false;
	        }
		$("#add_vacationApply_form").submit();
		 $("#add_vacationApply_modal").modal('hide');
	}) 
	
    $(document).ready(function () {
    	
       gettabledate();
      getinfo();
       //$("#gainTime").append(time);
    });
	
	function getinfo(){
		 $.ajax({
	           type: 'get',
	           url: 'attendance.do?getuseronline',
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
	               $("#userName").val(list.userName);
	               $("#dept").val(list.department);
	               $("#applicantSign").val(list.userName);
	           }
	       });
	       var now = new Date();
	       var time = timestampToTime(now.getTime());
	       $("#gainTime").val(time);
	}
	
    function gettabledate(){
    	 //配置datagrid
        $("#table").datagrid({loadFilter:pagerFilter}).datagrid({
            method:"get",
            pagination:true,
            pageSize:10,
            pageList:[10,20,30,40,50],
            singleSelect:true,
            toolbar:"#toolbar",
            columns:[[{
                field:"id",
                title:"编号",
                hidden:true
                //sortable:true
            },{
                field:"userName",
                title:"姓名",
                width:100,
                align:"center",
            },{
                field:"applyDate",
                title:"申请日期",
                width:120,
                align:"center",
                formatter:function (value) {
                   return  timestampToTime(value);
                }
            },{
                field:"vacationType",
                title:"休假类型",
                width:100,
                align:"center",
                formatter:function (value) {
                    if(value==null){
                        return "无";
                    }else {
                        return value;
                    }
                }
            },{
            	field:"leaveStart",
            	title:"出差开始时间",
            	width:150,
            	align:"center",
            	formatter:function (value) {
            		return timestampToTime2(value);
                 }
            },{
            	field:"leaveEnd",
            	title:"出差结束时间",
            	width:150,
            	align:"center",
            	formatter:function (value) {
            		return timestampToTime2(value);                 }
            },{
                field:"countDay",
                title:"净计(天数)",
                width:100,
                align:"center",
            },{
                field:"userStatus",
                title:"申请人确认状态",
                width:100,
                align:"center",
                formatter:function (value,rowDate,rowIndex) {
                    if(rowDate.userStatus==0){
                        return "未确认";
                    }else if(rowDate.userStatus==1){
                        return "确认";
                    }
                }
            },{
                field:"personnelMattersStatus",
                title:"人事状态",
                width:100,
                align:"center",
                formatter:function(value,rowDate,rowIndex){
                    if(rowDate.personnelMattersStatus==0){
                        return "未确认";
                    }else if(rowDate.personnelMattersStatus==1){
                        return "确认";
                    }
                }
            },{
                field:"_operate",
                title:"操作",
                width:180,
                align:"center",
                formatter:formatOper
            }]]
        });
    }
    function formatOper(val,row,index) {
    	
        var ss = '<a class="operate" style="cursor:pointer" onclick="showdetails(\''+row.id+'\')" >详情</a>';
        var userId = $("#userId").val();
        if(userId==row.userId){
            if(row.userStatus==0){
                    ss = ss + ' <a class="operate" style="cursor:pointer" href="#" onclick="confirmdetails(\''+row.id+'\')">确认</a>';
            }else if(row.userStatus==1){
                ss = ss + ' <a class="operate" href="#">已确认</a>';
            }
        }
        if(row.userStatus==1&&row.personnelMattersStatus==0){
            ss = ss + '<c:if test="${roleType==3}"> <a class="operate" href="#" onclick="adminConfirm(\''+row.id+'\')">关闭</a></c:if>';
        }
        return ss;
    }
    
    // <!-- 时间戳转日期 -->
    // <!-- begin -->
     function timestampToTime(timestamp) {
         var datetime = new Date(timestamp);//时间戳为10位需*1000，时间戳为13位的话不需乘1000
         var year = datetime.getFullYear();
         var month = datetime.getMonth() + 1 < 10 ? "0" + (datetime.getMonth() + 1) : datetime.getMonth() + 1;
         var date = datetime.getDate() < 10 ? "0" + datetime.getDate() : datetime.getDate();
         return year + "-" + month + "-" + date;
     }
   // <!-- 时间戳转日期 -->
   // YYYY-MM-dd HH:mm:ss
     function timestampToTime2(timestamp){
    	 var datetime = new Date(timestamp);
         var year = datetime.getFullYear();
         var month = datetime.getMonth() + 1 < 10 ? "0" + (datetime.getMonth() + 1) : datetime.getMonth() + 1;
         var date = datetime.getDate() < 10 ? "0" + datetime.getDate() : datetime.getDate();
         var hour = datetime.getHours()< 10 ? "0" + datetime.getHours() : datetime.getHours();
         var minute = datetime.getMinutes()< 10 ? "0" + datetime.getMinutes() : datetime.getMinutes();
         var second = datetime.getSeconds()< 10 ? "0" + datetime.getSeconds() : datetime.getSeconds();
         return year + "-" + month + "-" + date+" "+hour+":"+minute+":"+second;
     }
   
  // YYYY-MM-dd HH:mm
     function timestampToTime3(timestamp){
    	 var datetime = new Date(timestamp);
         var year = datetime.getFullYear();
         var month = datetime.getMonth() + 1 < 10 ? "0" + (datetime.getMonth() + 1) : datetime.getMonth() + 1;
         var date = datetime.getDate() < 10 ? "0" + datetime.getDate() : datetime.getDate();
         var hour = datetime.getHours()< 10 ? "0" + datetime.getHours() : datetime.getHours();
         var minute = datetime.getMinutes()< 10 ? "0" + datetime.getMinutes() : datetime.getMinutes();
         return year + "-" + month + "-" + date+"T"+hour+":"+minute;
     }
    // 分页数据的操作
    function pagerFilter(data) {
        if (typeof data.length == 'number' && typeof data.splice == 'function') {   // is array
            data = {
                total: data.length,
                rows: data
            }
        }
        var dg = $(this);
        var opts = dg.datagrid('options');
        var pager = dg.datagrid('getPager');
        pager.pagination({
            onSelectPage: function (pageNum, pageSize) {
                opts.pageNumber = pageNum;
                opts.pageSize = pageSize;
                pager.pagination('refresh', {
                    pageNumber: pageNum,
                    pageSize: pageSize
                });
                dg.datagrid('loadData', data);
            }
        });
        if (!data.originalRows) {
            data.originalRows = (data.rows);
        }
        var start = (opts.pageNumber - 1) * parseInt(opts.pageSize);
        var end = start + parseInt(opts.pageSize);
        data.rows = (data.originalRows.slice(start, end));
        return data;
    }
    //查询工具栏
    function query(){
        var status = document.getElementById("status");
        var status1 = status.options[status.selectedIndex].value;
        $("#table").datagrid("load",{
            status:status1,
            applyName:$("#applyName").val(),
            startDate:$("#startDate").val(),
            endDate:$("#endDate").val()
        });
    }
   
    
    //用户确认
    function confirmdetails(id) {
        var r = confirm("是否确认换休？确认后将提交给人事。");
        if(r == true) {
            $.ajax({
                type: 'get',
                url: 'attendance.do?userConfirmbusinessApply&businessId=' + id,
                async: false,
                cache: false,
                success: function (result) {
                    var resultDate = JSON.parse(result);
                    if (resultDate.msg == 'ok') {
                        window.location.reload();
                    }
                }
            });
        }else{
            return ;
        }
    }
    //人事关闭申请
    function adminConfirm(id) {
        var r = confirm("是否确认关闭？并登记记录。");
        if(r == true){
            $.ajax({
                type:'get',
                url:'attendance.do?adminConfirmBusinessApply&businessId='+id,
                async:false,
                cache:false,
                success:function(result){
                    var resultDate = JSON.parse(result);
                    //alert(resultDate.msg);
                    if(resultDate.msg == 'ok'){
                        window.location.reload();
                    }
                }
            });
        }
    }
    
    function showdetails(id){
    	  $("#file1").empty();
    	$.ajax({
    		type: 'get',
            url: 'attendance.do?showdetails&businessId=' + id,
            async: false,
            cache: false,
            success: function (result) {
            	  var resultDate = JSON.parse(result);
            	  var obj = resultDate.obj;
            	 /*  console.log(resultDate);
            	  console.log(obj);
            	  alert(obj.dept); */
            	
            	  $("#userName1").val(obj.userName);
            	  $("#dept1").val(obj.dept);
            	  $("#vacationType1").val(obj.vacationType);
            	  $("#beginTime1").val(timestampToTime3(obj.leaveStart));
            	  $("#endTime1").val(timestampToTime3(obj.leaveEnd));
            	  $("#totalDays1").val(obj.countDay);
            	  $("#totalHours1").val(obj.countHour);
            	  $("#leaveReason1").val(obj.leaveReason);
            	  $("#file1").append('<a class="operate" href="attendance.do?downloadfile&id='+obj.id+'">'+obj.fileName+'</a>')
            	  if(obj.vacationPlace == 0){
            		  $("input[name='vacationPlace'][value=0]").attr("checked",true); 
            	  }else{
            		  $("input[name='vacationPlace'][value=1]").attr("checked",true); 
            	  }
            	  $("#contactInformation1").val(obj.contactInformation);
            	  $("#instruction1").val(obj.instruction);
            	  $("#applicantSign1").val(obj.applicantSign);
            	  $("#gainTime1").val(timestampToTime(obj.applyDate));
            	  $("#add_vacationApply_modal_details").modal();
            }
    	})
    }
</script>
<div class="modal fade" id="add_vacationApply_modal_details" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="myModalLabel">出差申请表</h4>
            </div>
            <div class="modal-body">
            		    申请表表单
                <form action="#" method="post" style="height: 80%;" id="add_vacationApply_form" enctype="multipart/form-data">
                    <table border="1" cellspacing="0" cellpadding="0" id="restTable" class="rest_table">

                        <tr height="40px">
                            <td class="noneborderright">姓&nbsp;&nbsp;名：</td>
                            <td class="noneborderboth"><input type="text" id="userName1" class="apply_name" name="userName" readonly/>
                            </td>
                            <td class="noneborderboth">部&nbsp;&nbsp;门：</td>
                            <td class="noneborderleft"><input type="text" id="dept1" class="depart_name apply_name" name="dept"
                                                              style="width:156px;" readonly/></td>
                        </tr>
                        <tr height="40px">
                            <td class="noneborderright">申请休假类型：</td>
                              <td class="noneborderboth"><input type="text" id="vacationType1" name="vacationType" class="vacation_type apply_name" name="vacation_type" value="出差" readonly/>
                            </td>
                        </tr>
                        <tr height="30px">
                            <td class="noneborderright">请假开始时间：</td>
                            <td class="noneborderboth">
                                <div>
                                    <input type="datetime-local" id="beginTime1" name="beginTime"  onblur="countTime(this)" style="height:25px;" >
                                </div>
                            </td>
                            <td class="noneborderboth">请假结束时间：</td>
                            <td class="noneborderleft">
                                <div>
                                    <input type="datetime-local" id="endTime1" name="endTime" onblur="countTime(this)" style="height:25px;">
                                </div>
                            </td>
                        </tr>
                        <tr height="40px">
                            <td class="noneborderright">共计天数：</td>
                            <td class="noneborderboth">
                                <input type="text" id="totalDays1"  name="countDay" style="border:none; border-bottom:2px solid #eee;outline: none;width:50px;text-align:center;" readonly>(天)
                            </td>
                             <td class="noneborderboth">共计小时数：</td>
                            <td class="noneborderleft">
                                <input type="text" id="totalHours1" name="countHour" style="border:none; border-bottom:2px solid #eee;outline: none;width:50px;text-align:center;" readonly>(H)
                            </td>
                        </tr>
                        <tr height="90px">
                            <td class="noneborderright">请假理由：</td>
                            <td colspan="3" class="noneborderleft">
                                <textarea id="leaveReason1" class="rest_reason" name="leaveReason"></textarea>
                            </td>
                        </tr>
                          <tr height="30px">
                            <td class="noneborderright">附件：</td>
                            <td  class="noneborderboth">
                               <span id="file1"></span>
                            </td>
                        </tr>
                        <tr height="120px">
                            <td colspan="4" style="margin-top:-9px;" id="site">
                                <div>休假地点：</div>
                                <div style="margin-left:15px">
                                    <input type="radio"  name="vacationPlace" value="1"   class="in_china"/>
                                    <span>国内</span>
                                    <span style="margin-left:15px">联系方式：</span>
                                    <input type="text" id="contactInformation1" class="contact_me" name="contactInformation" value=""/>
                                </div>
                                <div style="margin-left:15px">
                                    <input type="radio" id="radion" name="vacationPlace" value="2" class="out_china"/>
                                    <span>国外</span>
                                    <span style="margin-left:15px">请说明：</span>
                                    <input type="text" id="instruction1" class="details_me contact_me" name="instruction" value=""/>
                                </div>
                                <div style="margin-top:9px;margin-left:10px;margin-bottom:5px;">
                                    <span class="sign">申请者签字：</span>
                                    <input type="text" id="applicantSign1" class="signer_me contact_me" name="applicantSign" value=""
                                           style="width:138px"/>
                                    <span style="margin:0 12px">日期:</span>
                                      <input type="text" id="gainTime1" name="applyDate" value=""
                                           style="width:138px" readonly/>
                                </div>
                            </td>
                        </tr>
                    </table>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal" id="close">Close</button>
            </div> 
            
        </div>
    </div>
</div>

</body>
</html>
