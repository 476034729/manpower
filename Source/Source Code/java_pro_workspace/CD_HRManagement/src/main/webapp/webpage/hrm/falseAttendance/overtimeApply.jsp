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
    <style>
        .parentConnt{
            background:wheat;
            width: 80%;
            height: 100%;
            margin: 0 auto;
            border: 1px solid grey;
        }
        .title{
            width: 100%;
            height: 60px;
            border-bottom: 1px solid grey;
            text-align: center;
        }
        .one{
            width: 100%;
            height: 50px;
            border-bottom: 1px solid grey;
        }
        .oneLeft{
            float: left;
            width: 50%;
            height: 50px;
        }
        .oneRight{
            float: right;
            width: 50%;
            height: 50px;
        }
        .oneLabel{
            width: 50%;
        }
        .input1{
            width: 50%;
            position: relative;
            top: 10px;
            height: 30px;
        }
        .oneLleft{
            float: left;
            width: 30%;
            height: 100%;
            text-align: center;
        }
        .oneLright{
            float: left;
            width: 70%;
            height: 100%;
        }
        .po{
            position: relative;
            top: 12px;
        }
        .oneRright{
            float: left;
            width: 60%;
        }
    </style>
</head>
<body>
<form>
    <div class="parentConnt">
        <div class="title">
            <h1>加班申请表</h1>
        </div>
        <div class="one">
            <div class="oneLeft">
                <div class="oneLleft"><label class="po">申请人姓名：</label ></div>
                <div class="oneLright"><input type="text" class="input1"></div>
            </div>
            <div class="oneRight">
                <div class="oneLeft">
                    <label class="po">部门：</label>
                </div>
                <div class="oneRright">
                    <select class="input1">
                        <option>ICS</option>
                    </select>
                </div>
            </div>
        </div>
    </div>
</form>
</body>
</html>
