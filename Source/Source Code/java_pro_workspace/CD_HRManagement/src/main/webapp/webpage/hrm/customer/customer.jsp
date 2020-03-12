<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>客户信息</title>
<t:base type="jquery,easyui,tools"></t:base>
</head>
<body style="overflow-y: hidden" scroll="no">
<t:formvalid formid="formobj" dialog="true" layout="table" action="hrmCustomer.do?saveCustomer">
	<input id="id" name="id" type="hidden" value="${customer.id }">
	<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
		<tr>
			<td align="right" width="25%" nowrap>
                <label class="Validform_label">  姓名:  </label>
            </td>
			<td class="value" width="85%">
                <c:if test="${user.id!=null }">
                    <input name="customerName"  value="${customer.customerName }" readonly="readonly">
                </c:if>
                <c:if test="${user.id==null }">
                    <input id="customerName" class="inputxt" name="customerName" validType="t_s_base_user,userName,id" value="${customer.customerName }" datatype="s2-10" />
                    <span class="Validform_checktip"> <t:mutiLang langKey="username.rang2to10"/></span>
                </c:if>
            </td>
		</tr>
        <tr>
            <td align="right"><label class="Validform_label"> <t:mutiLang langKey="common.common.mail"/>: </label></td>
            <td class="value">
                <input class="inputxt" name="customerEmail" value="${customer.customerEmail}" datatype="e" errormsg="邮箱格式不正确!" ignore="ignore">
                <span class="Validform_checktip"></span>
            </td>
        </tr>
		<tr>
			<td align="right" nowrap><label class="Validform_label">  <t:mutiLang langKey="common.phone"/>: </label></td>
			<td class="value">
                <input class="inputxt" name="customerPhone" value="${customer.customerPhone}" datatype="m" errormsg="手机号码不正确" ignore="ignore">
                <span class="Validform_checktip"></span>
            </td>
		</tr>
        <tr>
            <td align="right"><label class="Validform_label"> 备注: </label></td>
            <td class="value">
                <input id="remark" name="remark" type="text" class="inputxt" datatype="*" value="${customer.remark}" ignore="ignore">
            </td>
        </tr>
	</table>
</t:formvalid>

</body>