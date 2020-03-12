<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import = "java.util.*" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<script type="text/javascript" src="plug-in/jquery/jquery-1.8.3.min.js"></script>

<style> 
    .black_overlay{ 
        display: none; 
        position: absolute; 
        top: 0%; 
        left: 0%; 
        width: 100%; 
        height: 100%; 
        background-color: black; 
        z-index:1001; 
        -moz-opacity: 0.8; 
        opacity:.80; 
        filter: alpha(opacity=88); 
    } 
   .white_content { 
       display: none; 
       position: absolute; 
       top: 25%; 
       left: 25%; 
       width: 55%; 
       height: 55%; 
       padding: 20px; 
       border: 10px solid orange; 
       background-color: white; 
       z-index:1002; 
       overflow: auto; 
   } 
	#theadSel{
		font-size: 22px;
	}
	
	.questionId{
		display: none;
	}
</style> 
</head>
<body>
	这是我录入笔试题的入口
	<span>岗位名称:</span>
	<select id="postSel" onchange = "postSelChange()">
		<c:forEach var = "examPost" items = "${examPosts }">
			<option value = "${examPost.post_id}">${examPost.post_name }</option>
		</c:forEach>
	</select>
	<button type = "button" onclick="showSel()">查询</button>
	<button type="button"  onclick = "openDialog()">添加问题</button>
	<hr>
	<!-- 显示题目信息 -->
	<div style="background-color: #8FBC8F">
		<span id = "theadSel">${defaultSel.post_name }</span>
		<table border="1" style="width: 85%; font-size: 16px;">
		<!-- 单选题 -->
			<c:forEach var = "list1" items = "${danxuan }">
				<tr>
					<td>
						${list1.examinationQuestion_num }.${list1.examinationQuestion_title }
						<span class = "questionId">${list1.examinationQuestion_id }</span>
						<button type = "button" class="updateBtn">修改</button>
						<button type = "button">作废</button>
					</td>
				</tr>
				<!-- 遍历选项及答案 -->
				<c:forEach var="option1" items = "${list1.options }">
					<tr>
						<td>
							<input type="radio" name = "sel1">${option1.examinationQuestion_key }.${option1.examinationQuestion_value }
							
						</td>
					</tr>
				</c:forEach>
			</c:forEach>
			
			<!-- 多选题 -->
			<c:forEach var = "list2" items = "${duoxuan }">
				<tr>
					<td>
						${list2.examinationQuestion_num }.${list2.examinationQuestion_title }
						<span class = "questionId">${list2.examinationQuestion_id }</span>
						<button type = "button" class="updateBtn">修改</button>
						<button>作废</button>
					</td>
				</tr>
				<!-- 遍历选项及答案 -->
				<c:forEach var="option2" items = "${list2.options }">
					<tr>
						<td>
							<input type="checkbox" name = "sel2">${option2.examinationQuestion_key }.${option2.examinationQuestion_value }
						</td>
					</tr>
				</c:forEach>
			</c:forEach>
		</table>
	</div>
	
	<!-- 添加问题的窗口 -->
	<div>
		<div id="light" class="white_content">
			<form action="examQuestion.do?updateQuestion">
				<!-- 利用ajax传回来的数据-->
					<span id="quesID">123</span><br>
					<p>1.<textarea style="width: 200px; height: 100px">
						我是通过width和height定的大小，我的width是200px，heigh是100px
						</textarea>
					</p>
					<p><textarea style="width: 200px; height: 100px">A.XXXXXXXXXX</textarea></p>
					<p><textarea style="width: 200px; height: 100px">B.XXXXXXXXXX</textarea></p>
					<p><textarea style="width: 200px; height: 100px">A.XXXXXXXXXX</textarea></p>
					<p><textarea style="width: 200px; height: 100px">B.XXXXXXXXXX</textarea></p>
					<input type="submit" value = "确认修改">
			</form>
		
		
            <a href = "javascript:void(0)" onclick = "closeDialog()">取消修改并关闭窗口</a>
        </div> 
        <div id="fade" class="black_overlay"></div> 
	</div>

</body>
<script type="text/javascript">
	
	//显示标题
	function showSel(){
		$("#theadSel").text($("#theadSel").text()+"面试题");
		//让下拉列表中默认选中刚刚选择的值
		window.location.href="examQuestion.do?showQuestionsList&postId="+$("#postSel option:selected").val();
	}
	
	//点击修改 跳转到 修改问题的界面
	
	$(".updateBtn").each(function(){
		$(this).click(function(){
			//alert($(this).prev().text());
			var questionId = $(this).prev().text();
			window.location.href='examQuestion.do?showUpdateQuestion&questionId='+questionId;
			
		})
	});
	
	
	//点击关闭，关闭弹出窗口
	 function closeDialog(){
		 //关闭窗口
         document.getElementById('light').style.display='none';
         document.getElementById('fade').style.display='none'
     }
	
	//点击添加问题弹出窗口
	function openDialog(){
		 document.getElementById('light').style.display='block';
         document.getElementById('fade').style.display='block';
         
	}
</script>
</html>