<%--
  Created by IntelliJ IDEA.
  User: Public_Stee_Develope
  Date: 2018/7/31
  Time: 9:33
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
    <!-- ace settings handler -->
    <script src="plug-in/ace/js/ace-extra.js"></script>

    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->

    <!--[if lte IE 8]>
    <script src="plug-in/ace/js/html5shiv.js"></script>
    <script src="plug-in/ace/js/respond.js"></script>
    <![endif]-->
</head>
<body style="overflow: auto">

<div style="width: auto">
    项目名称：
    <input id="SteProjectName" style=" margin-left: 10px;"/>
    项目状态：
    <select id="status" style="margin-left: 10px">
        <option value="all">全部</option>
        <option value="执行中">执行中</option>
        <option value="关闭">关闭</option>
    </select>
    <button type="button" class="btnd btn-primary" id="query" style="margin-left: 10px;">查询</button>
	<button type="button" class="btnd btn-primary" id="add" style="margin-left: 10px;">新增</button>
</div>
<table class="table table-bordered" id="project_list_table">
    <tr>
    <th></th>
    <th>项目名</th>
    <th>项目EG编码</th>
    <th>代号/code</th>
    <th>状态</th>
    <th>备注</th>
    <th>项目编码</th>
    <th>操作</th>
    </tr>
    <tbody id="tbody">

    </tbody>
</table>
<script type="text/javascript" src="plug-in/jquery/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="plug-in/jquery/jquery.cookie.js"></script>
<script type="text/javascript" src="plug-in/mutiLang/en.js"></script>
<script type="text/javascript" src="plug-in/mutiLang/zh-cn.js"></script>
<script type="text/javascript" src="plug-in/login/js/jquery.tipsy.js"></script>
<script type="text/javascript" src="plug-in/login/js/iphone.check.js"></script>
<script src="plug-in-ui/hplus/js/plugins/layer/layer.min.js"></script>
<script>
//主函数
var projectQuery =function(){
	this.doGetProjetList = function(name,status){
		 $.ajax({
	            type: 'get',
	            // contentType:'application/x-www-form-urlencoded:charset=UTF-8',
	            url: 'ProjectCodeController.do?queryproject',
	            data: {
	                'name': encodeURI(name),
	                'status': encodeURI(status)
	            },
	            async: false,
	            cache: false,
	            success: function (result) {
	                $("#tbody").empty();
	                var r = JSON.parse(result);
	                if (r.success == false){
	                    alert(r.msg);
	                    return;
	                }
	                var list = r.obj;
	                var billMonthHtml="";
	                $.each(list, function (index, item) {
	                	billMonthHtml += "<tr name ='select_item' data-id = '"+item.id+"'>"
	                	+ "<td><input type='checkbox'></td>"
	                	+ "<td>"
						+ item.project_name
						+ "</td>"
						+ "<td>"
						+ item.project_eg_code
						+ "</td>"
						+ "<td>"
						+ item.code
						+ "</td>"
						+ "<td>"
						+ nullEmpty(item.status,"无")
						+ "</td>" 
						+ "<td>" + nullEmpty(item.remark,"无")
						+ "</td>"
                        + "<td>" + nullEmpty(item.numCode,"无")
                        + "</td>"
						+ "<td>"
					 	+ "<button class='btnd btn-primary' id='proEdit'>修改</button>" +
						" <button class='btnd btn-primary' id='proDelect'>删除</button></td></tr>"; 
						
						
	                /*     var tr = $("<tr></tr>");
	                    $("<td></td>").append(item.project_name).appendTo(tr);
	                    $("<td></td>").append(item.project_eg_code).appendTo(tr);
	                    $("<td></td>").append(item.code).appendTo(tr);
	                    $("<td></td>").append(item.status).appendTo(tr);
	                    $("<td></td>").append(item.remark).appendTo(tr);
	                    tr.appendTo($("#tbody")); */
	                })
	                $("#tbody").html(billMonthHtml);
	            }
	        })
	};
	
	
	//table选中某一行事件
/* 	this.tablePublicHover = function(){
		$("#project_list_table").on("click","tr td input[type='checkbox']",function(event){
			event.stopPropagation();
		});
		$("#project_list_table").on("click","tr",function(event){
			event.stopPropagation();
			var check = $(this).find("td:first input[type='checkbox']");
			if(check.is(":checked")){
				check.removeAttr("checked");
			}else{
				check.prop("checked",true);
			}
		});	
	}; */
	//处理空的方法
	var nullEmpty = function (val,str) {
		if(str == undefined){
			str = "";
		}
		if(val == null || val == "null" || val == " " || val == "undefined" || val == undefined){
			return str;
		}else{
			return val;
		}
	};
	
	
	
	
}
$(function(){
    var projects =new projectQuery();
/*     projects.tablePublicHover(); */
    projects.doGetProjetList("null","all");
    //查询点击事件
    $("#query").click(function () {
        var name = $("#SteProjectName").val();
        var status = $("#status").val();
        projects.doGetProjetList(name,status);
    });
    //新增点击事件
    $("#add").click(function(){
    	var parentId="";
    	//获取是否有选中事件.如果有说明新增的是子项目 如果没有说明新增的主项目
    	 $(".table tbody tr").find("td:first input:checkbox").each(function () {
    	        var ischecked = $(this).prop("checked");
    	        if(ischecked){
    	        	var tr=	$(this).parents("tr");
    	        	parentId =tr.attr("data-id");
    	        }
    	   });
    	 layer.confirm('新增子项目需要选中主项目,否则视为新增主项目', {
			 offset: ['200px', '650px'],
	            btn: ['确定','取消'], //按钮
	            shade: false //不显示遮罩
	        }, function(){
	       	 window.location.href='ProjectCodeController.do?doJumpAddProject&parentId='+parentId;
	        }, function(){
	            return;
	        });
    });
    
    
    //修改点击事件
    $("#project_list_table").on("click","#proEdit",function(){
		/*获取当前选中按钮父元素*/
		var tr =$(this).parents("tr");
		var x =tr.find("input[type='checkbox']");
	/* 	var y= x.is(":checked"); */
		/*获取对应子元素*/
		var parentId = tr.attr("data-id");
		window.location.href='ProjectCodeController.do?doJumpUpdateProject&parentId='+parentId;
	});
    //删除点击事件
    $("#project_list_table").on("click","#proDelect",function(){
		/*获取当前选中按钮父元素*/
		var tr =$(this).parents("tr");
		var x =tr.find("input[type='checkbox']");
		var y= x.is(":checked");
		/*获取对应子元素*/
		var str = tr.attr("data-id");
		 layer.confirm('您确定要删除吗？', {
			 offset: ['200px', '650px'],
	            btn: ['确定','取消'], //按钮
	            shade: false //不显示遮罩
	        }, function(){
	        	 $.ajax({
	 	            type: 'get',
	 	            // contentType:'application/x-www-form-urlencoded:charset=UTF-8',
	 	            url: 'ProjectCodeController.do?doDeleteProjectCodeById',
	 	            data: {
	 	                'id': encodeURI(str)
	 	            },
	 	            async: false,
	 	            cache: false,
	 	            success: function (result) {
	 	            	 var r = JSON.parse(result);
	 	                if (r.success == false){
	 	                	alert(r.msg);
	 	                    return;
	 	                }else{
	 	            	 window.location.reload(); 
	 	                }
	 	            }
	 	        })
	        }, function(){
	            return;
	        });
		
		
		
	});
    //复选框选中了 其它的不能选
    $("#project_list_table tbody").on("click","input",function(){
    	$(":checkbox").not($(this)).removeAttr("checked");
    })
});
  
</script>
</body>
</html>
