<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
<div region="center" style="padding:0px;border:0px">
	<t:datagrid name="customerList" title="客户联系人" actionUrl="hrmCustomer.do?datagrid"
				idField="id" fit="true"
				queryMode="group"
				sortName="id"
				sortOrder="asc"
                pagination="true">
		<t:dgCol title="主键" field="id" hidden="true"></t:dgCol>
		<t:dgCol title="姓名" sortable="false" field="customerName" width="20" query="true"></t:dgCol>
		<t:dgCol title="邮箱" sortable="false" field="customerEmail" width="20"></t:dgCol>
		<t:dgCol title="电话" sortable="false" field="customerPhone" width="20"></t:dgCol>
		<t:dgCol title="备注" sortable="false" field="remark" width="20" query="true"></t:dgCol>
		<t:dgCol title="操作" field="opt" width="20"></t:dgCol>
		<t:dgDelOpt title="删除" url="hrmCustomer.do?del&id={id}&customerName={customerName}" />
	</t:datagrid>

   <div id="userListtb" style="padding: 3px; height: 25px">
	<div style="float: left;">
		<a href="#" id="add" class="easyui-linkbutton" plain="true" icon="icon-add" onclick="add('新增员工','hrmCustomer.do?addorupdate','customerList')">新增员工</a>
		<a href="#" class="easyui-linkbutton" plain="true" icon="icon-edit" onclick="update('员工编辑','hrmCustomer.do?addorupdate','customerList')">员工编辑</a>
	</div>
	<%--<div align="right">--%>
		<%--用户名: <input class="easyui-validatebox" name="userName" style="width: 80px">--%>
		<%--<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="userListsearch();">查询</a>--%>
	<%--</div>--%>
   </div>
</div>
</div>

