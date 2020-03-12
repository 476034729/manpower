<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>总部通讯录员工信息</title>
<t:base type="jquery,easyui,tools"></t:base>
</head>
<body style="overflow-y: hidden" scroll="no">
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="hrmEmployee.do?saveUser">
	<input id="id" name="id" type="hidden" value="${user.id }">
    <input id="site" name="site" type="hidden" value="HQ">
	<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
        <tr>
            <td align="right" width="25%" nowrap>
                <label class="Validform_label">  姓名:  </label>
            </td>
            <td class="value" width="85%">
                <c:if test="${user.id!=null }">
                    <input name="userName"  value="${user.userName }" readonly="readonly">
                </c:if>
                <c:if test="${user.id==null }">
                    <input id="userName" class="inputxt" name="userName" validType="t_s_base_user,userName,id" value="${user.userName }" datatype="s2-10" />
                    <span class="Validform_checktip"> <t:mutiLang langKey="username.rang2to10"/></span>
                </c:if>
            </td>
        </tr>
		<%--<c:if test="${user.id==null }">
			<tr>
				<td align="right"><label class="Validform_label"> 密码: </label></td>
				<td class="value">
                    <input type="password" class="inputxt" value="" name="password" plugin="passwordStrength" datatype="*6-18" errormsg="" />
                    <span class="passwordStrength" style="display: none;">
                        <span><t:mutiLang langKey="common.weak"/></span>
                        <span><t:mutiLang langKey="common.middle"/></span>
                        <span class="last"><t:mutiLang langKey="common.strong"/></span>
                    </span>
                    <span class="Validform_checktip"> <t:mutiLang langKey="password.rang6to18"/></span>
                </td>
			</tr>
		</c:if>--%>
		<tr>
			<td align="right"><label class="Validform_label"> 职位: </label></td>
			<td class="value" nowrap>
                <input id="position" name="position" type="text" class="inputxt" datatype="*" value="${user.position}">
                <span class="Validform_checktip">不可为空</span>
            </td>
		</tr>
        <tr>
            <td align="right"><label class="Validform_label"> <t:mutiLang langKey="common.common.mail"/>: </label></td>
            <td class="value">
                <input class="inputxt" name="email" value="${user.email}" datatype="e" errormsg="邮箱格式不正确!">
                <span class="Validform_checktip">不可为空</span>
            </td>
        </tr>
		<tr>
			<td align="right" nowrap><label class="Validform_label">  <t:mutiLang langKey="common.phone"/>: </label></td>
			<td class="value">
                <input class="inputxt" name="mobilePhone" value="${user.mobilePhone}" datatype="m" errormsg="手机号码不正确">
                <span class="Validform_checktip">不可为空</span>
            </td>
		</tr>
        <tr>
            <td align="right"><label class="Validform_label"> 备注: </label></td>
            <td class="value">
                <input id="remark" name="remark" type="text" class="inputxt" datatype="*" value="${user.remark}" ignore="ignore">
            </td>
        </tr>
	</table>
</t:formvalid>

</body>