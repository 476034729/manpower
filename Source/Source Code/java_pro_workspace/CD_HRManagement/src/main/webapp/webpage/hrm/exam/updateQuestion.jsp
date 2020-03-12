<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import = "java.util.*" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript" src="plug-in/jquery/jquery-1.8.3.min.js"></script>
<title>修改试题</title>
</head>
<body>
	<h3>修改试题</h3>
	<form action="examQuestion.do?updateQuestion" id = "updateForm" method="post">
		<table border="1" style="text-align: center;">
			<tr>
				<td>
					<div>${question.examinationQuestion_num }</div>
					<input type="hidden" name = "examinationQuestion_id" value = "${question.examinationQuestion_id }">
				</td>
				<td>
					<div contenteditable="true" >${question.examinationQuestion_title }</div>
					<input type="hidden" name = "examinationQuestion_title"  id="input2">
				</td>
			</tr>
			<!-- 判断有没有题目补充 -->
			<tr>
				<c:if test="${not empty question.examinationQuestion_content }">
					<td  colspan="2">
						<div contenteditable="true" >${question.examinationQuestion_content }</div>
						<input type="hidden" name = "examinationQuestion_content" id="input3">
					</td>
				</c:if>
			</tr>
			<!-- 选项不为空就显示出来 -->
			<c:if test="${not empty question.options }">
				<c:forEach  var="option" items = "${question.options }">
					<tr>
						<td>
							<div>${option.examinationQuestion_key }</div>
							<input type = "hidden" name = "examinationQuestion_key" class = "input4">
						</td>
						<td>
							<div contenteditable="true" >${option.examinationQuestion_value }</div>
							 <input type="hidden" name="examinationQuestion_value" class="input5">
						</td>
					</tr>
				</c:forEach>
			</c:if>
			
			<!-- 显示正确答案 -->
			<c:if test="${not empty question.examination_answer }">
				<tr>
					<td>正确答案：</td>
					<td>
						<div contenteditable="true" >${question.examination_answer }</div>
						<input type="hidden" name = "examination_answer" id = "input5">
					</td>
				</tr>
			</c:if>
			<tr>
				<td colspan="2"><input type="button" value="确认" id="submitBtn">
			</tr>
		</table>
	</form>
</body>
<script type="text/javascript">
	$("#submitBtn").click(function(){
		//alert($("#input2").prev().html());
		//标题
		$("#input2").attr("value",$("#input2").prev().html());
		//题目补充
		$("#input3").attr("value",$("#input3").prev().html());
		
		//赋值选项
		$(".input4").each(function(){
			//alert($(this).prev().html());
			$(this).val($(this).prev().html());
			//alert($(this).val());
		}); 
		$(".input5").each(function(){
			//alert($(this).prev().html());
			$(this).val($(this).prev().html());
			//alert($(this).val());
		}); 
		
		$("#updateForm").submit();
	})
</script>
</html>