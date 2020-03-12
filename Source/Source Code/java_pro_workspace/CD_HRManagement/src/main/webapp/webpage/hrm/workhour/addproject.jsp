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
<p>当前位置><a href="ProjectCodeController.do?goprojectlist">项目详情</a>><a href="#">新增项目</a></p>
<form id="overtimeApplyForm">
    <input type="hidden" name="parentId" id="parentId" value="${parentId}">
    <div class="parentConnt">
        <table class="tab">
            <tr style="height: 60px">
                <td colspan="4">
                    <h1 style="text-align: center">新增项目</h1>
                </td>
                 <a href="ProjectCodeController.do?goprojectlist" class="btn btn-default" style="float:right;"> <span class="glyphicon glyphicon-remove"></span>
	        	 </a>  
            </tr>
            <tr style="height: 50px;">
                <td class="one">
                    <label>&nbsp;&nbsp;&nbsp;&nbsp;项目名称：</label>
                </td>
                <td class="two">
                    <input type="text" name="project_name" id="project_name" class="input1">
                </td>
                <td class="one">
                    <label>&nbsp;&nbsp;&nbsp;&nbsp;EG Code：</label>
                </td>
                <td class="two">
                    <input type="text" name="project_eg_code" id="project_eg_code" class="input1">
                </td>
            </tr>
            
             <tr style="height: 50px;">
                <td class="one">
                    <label>&nbsp;&nbsp;&nbsp;&nbsp;代号：</label>
                </td>
                <td class="two">
                    <input type="text" name="code" id="code" class="input1" >
                </td>
                <td class="one">
                    <label>&nbsp;&nbsp;&nbsp;&nbsp;项目类型：</label>
                </td>
                <td class="two">
                    <select class="input1" name="type" id="type"  style="color: black">
                        <option value="执行中项目" selected>执行中项目</option>
                        <option value="外派项目">外派项目</option>
                        <option value="关闭项目">关闭项目</option>
                    </select>
                </td>
            </tr>
             <tr style="height: 50px;">
              <td class="one">
                    <label>&nbsp;&nbsp;&nbsp;&nbsp;状态：</label>
                </td>
                <td class="two">
                    <select class="input1" name="status" id="status"  style="color: black">
                        <%--<option value="开发阶段" selected>开发阶段</option>--%>
                         <option value="执行中">执行中</option>
                          <option value="质保期">质保期</option>
                           <option value="关闭">关闭</option>
                    </select>
                </td>
                  <td class="one">
                    <label>&nbsp;&nbsp;&nbsp;&nbsp;备注：</label>
                </td>
                <td class="two">
                    <input type="text" name="remark" id="remark" class="input1" >
                </td>
            </tr>

            <tr style="height: 50px;">
                <td class="one">
                    <label>&nbsp;&nbsp;&nbsp;&nbsp;项目编码：</label>
                </td>
                <td class="two">
                    <input type="text" name="numCode" id="numCode" class="input1" >
                </td>
            </tr>
            
        </table>
        <br>
        <div  class="btn_option" >
            <input type="button" class="btn-primary" id="sure" value="提交" >
        </div>
    </div>
</form>
<script type="text/javascript" src="plug-in/jquery/jquery-1.8.3.min.js"></script>
<script type="text/javascript">
function formSubmit(){

     document.getElementById("overtimeApplyForm").submit(); 
}
$(function(){
	$("#sure").click(function(){
		var sure = true;
		var project_name =$("#project_name").val();
		var project_eg_code =$("#project_eg_code").val();
		var code =$("#code").val();
		var type =$("#type").val();
		var status =$("#status").val();
		var numCode = $("#numCode").val();
		if(project_name==""){
			$("#project_name").css("border-color","red");
			sure= false;
		}
		if(project_eg_code==""){
			$("#project_eg_code").css("border-color","red");
			sure= false;
		}
		if(code==""){
			$("#code").css("border-color","red");
			sure= false;
		}
		if(type==""){
			$("#type").css("border-color","red");
			sure=false;
		}
		if(status==""){
			$("#status").css("border-color","red");
			sure=false;
		}
		if (numCode==""){
		    $("#numCode").css("border-color","red");
		    sure=false;
        }
		if(sure==true){
			 $.ajax({
		            type: 'post',
		            // contentType:'application/x-www-form-urlencoded:charset=UTF-8',
		            url: 'ProjectCodeController.do?doAddProject',
		            data: {
		            	 project_name :project_name,
		        		 project_eg_code :project_eg_code,
		        		 code :code,
		        		 type :type,
		        		 remark:$("#remark").val(),
		        		 status :status,
                         numCode : numCode,
		        		 parentId:$("#parentId").val()
		            },
		            async: false,
		            cache: false,
		            success: function (result) {
		                var r = JSON.parse(result);
		                if (r.success == false){
		                    alert(r.msg);
		                    return;
		                }else{
		                	window.location.href="ProjectCodeController.do?goprojectlist";
		                }
		            }
		        })
			
		}
		
	})
	
	
})

</script>
</body>
</html>
