<%--
  Created by IntelliJ IDEA.
  User: 52629
  Date: 2018/11/6
  Time: 16:56
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
    <script src="plug-in/jquery-easyui-1.5.3/jquery.min.js"></script>
    <script src="plug-in/jquery-easyui-1.5.3/jquery.easyui.min.js"></script>
    <script src="plug-in/jquery-easyui-1.5.3/locale/easyui-lang-zh_CN.js"></script>
    <style>
        .btn_option{
            text-align: center;
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
        #assetTable{
            background-color:#CCCCCC;
        }
        a{
            text-decoration: none;
            color: black;
        }
        .datagrid-header-row td{
            background-color:#CCCCFF;
            color:black;
        }
        .textBox{
            position: relative;
            bottom: 35px;
            background-color:#CCCCCC;
        }
    </style>
</head>
<body>
<p>当前位置><a href="AssetController.do?queryAssetInventory">资产查询</a>><a href="#">资产详情</a></p>
<form id="assetEntryForm" action="AssetController.do?saveAsset" method="post">
    <input type="hidden" id="assetId" name="assetId" value="${hrmAssetInventory.id}">
    <table id="assetTable" width="100%">
        <tr style="width: 100%;height: 60px">
            <td  style="width:33%;">
                <label style="width: 40%;height: 40px" >资产类型&nbsp;&nbsp;&nbsp;</label>
                <select style="width: 50%;height: 40px;color: black" name="hrmAssetTypeId" id="asset_id" disabled>
                    <option value="" >${hrmAssetInventory.hrmAssetType.name}</option>
                </select>
            </td>
            <td style="width:33%;height: 100%">
                <label style="width: 40%;height: 50px">资产编号&nbsp;&nbsp;&nbsp;</label>
                <input type="text" name="asset_no" id="asset_no" style="width: 50%;height: 40px" value="${hrmAssetInventory.asset_no}" readonly>
            </td>
            <td style="width:33%;height: 100%">
                <label style="width: 40%;height: 40px">录入人员&nbsp;&nbsp;&nbsp;</label>
                <input type="text" name="entry_person_id" id="entry_person_id" value="${hrmAssetInventory.entry_person}" style="width: 50%;height: 40px" readonly>
            </td>
        </tr>
        <tr style="width: 100%;height: 60px">
            <td style="width:33%">
                <label style="width: 40%;height: 40px" disabled>品牌&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
                <select style="width: 50%;height: 40px;color: black" name="hrmBrandId" id="brand_id" disabled>
                    <option value="">${hrmAssetInventory.hrmBrand.name}</option>

                </select>
            </td>
            <td style="width:33%">
                <label style="width: 40%;height: 40px">内存大小&nbsp;&nbsp;&nbsp;</label>
                <input type="text" name="hard_memory_size" id="hard_memory_size" style="width: 50%;height: 40px" value="${hrmAssetInventory.hard_memory_size}" readonly>
            </td>
            <td style="width:33%">
                <label style="width: 40%;height: 40px">硬盘大小&nbsp;&nbsp;&nbsp;</label>
                <input type="text" name="disk_size" id="disk_size" style="width: 50%;height: 40px" value="${hrmAssetInventory.disk_size}" readonly>
            </td>
        </tr>
        <tr style="width: 100%;height: 60px">
            <td style="width:33%">
                <label style="width: 40%;height: 40px">主机编号&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
                <input type="text" name="host_no" id="host_no" style="width: 50%;height: 40px" value="${hrmAssetInventory.host_no}">
            </td>
            <td style="width:33%">
                <label style="width: 40%;height: 40px">ip&nbsp;&nbsp;&nbsp;</label>
                <input type="text" name="ip" id="ip" style="width: 50%;height: 40px" onclick="cancelBorder()" value="${hrmAssetInventory.ip}">
            </td>
            <td style="width:33%">
                <label style="width: 40%;height: 40px">新资产编号&nbsp;&nbsp;&nbsp;</label>
                <input type="text" name="asset_no_new" id="asset_no_new" style="width: 50%;height: 40px" onclick="cancelBorder()" value="${hrmAssetInventory.asset_no_new}">
            </td>
            <%--<td colspan="3">--%>
                <%--<label style="width: 40%;height: 40px">主机编号&nbsp;&nbsp;&nbsp;</label>--%>
                <%--<input type="text" name="host_no" id="host_no" style="width: 16.5%;height: 40px" value="${hrmAssetInventory.host_no}" readonly>--%>
            <%--</td>--%>
        </tr>
        <tr style="width: 100%;height: 60px">
            <td colspan="3" style="width: 100%">
                <label class="textBox">型号规格&nbsp;&nbsp;&nbsp;</label>
                <textarea  name="model_specifiction" id="model_specifiction" style="width: 83.5%;height: 80px" readonly>${hrmAssetInventory.model_specifiction}</textarea>
            </td>
        </tr>
        <tr style="width: 100%;height: 60px">
            <td colspan="3">
                <label class="textBox">详细配置&nbsp;&nbsp;&nbsp;</label>
                <textarea  name="detailed_configuration" id="detailed_configuration" style="width: 83.5%;height: 80px"  readonly>${hrmAssetInventory.detailed_configuration}</textarea>
            </td>
        </tr>
        <tr style="width: 100%;height: 60px">
            <td style="width:33%">
                <label style="width: 40%;height: 40px">采购单价&nbsp;&nbsp;&nbsp;</label>
                <input type="text" name="purchase_unit_price"id="purchase_unit_price" style="width: 50%;height: 40px" value="${hrmAssetInventory.purchase_unit_price}" readonly>
            </td>
            <td style="width:33%">
                <label style="width: 40%;height: 40px">采购日期&nbsp;&nbsp;&nbsp;</label>
                <input type="text" name="purchase_date" id="purchase_date" style="width: 50%;height: 40px" value="<fmt:formatDate value="${hrmAssetInventory.purchase_date }" type="date" pattern="yyyy/MM/dd"/>" readonly>
            </td>
            <td style="width:33%">
                <label style="width: 40%;height: 40px">启用日期&nbsp;&nbsp;&nbsp;</label>
                <input type="text" name="use_date" id="use_date" style="width: 50%;height: 40px" value="<fmt:formatDate value="${hrmAssetInventory.use_date }" type="date" pattern="yyyy/MM/dd"/>" readonly>
            </td>
        </tr>
        <tr style="width: 100%;height: 60px">
            <td style="width:33%">
                <label style="width: 40%;height: 40px">折旧年限&nbsp;&nbsp;&nbsp;</label>
                <input type="text" name="depreciation_years" id="depreciation_years" style="width: 50%;height: 40px" value="${hrmAssetInventory.depreciation_years}" readonly>
            </td>
            <td style="width:33%">
                <label style="width: 40%;height: 40px">资产状态&nbsp;&nbsp;&nbsp;</label>
                <select name="asset_status" id="asset_status" style="width: 50%;height: 40px;color: black"  disabled>
                    <option value="库存" >${hrmAssetInventory.asset_status}</option>
                </select>
            </td>
            <td style="width:33%">
                <label style="width: 40%;height: 40px">使用部门&nbsp;&nbsp;&nbsp;</label>
                <select name="hrmDepartId" id="hrmDepartId" style="width: 50%;height: 40px;color: black"  disabled>
                    <option value="">${hrmAssetInventory.hrmDepart.name}</option>
                </select>
            </td>
        </tr>
        <tr style="width: 100%;height: 60px">
            <td style="width:33%">
                <label style="width: 40%;height: 40px">使用人员&nbsp;&nbsp;&nbsp;</label>
                <input type="text" name="use_person" id="use_person" style="width: 50%;height: 40px" value="${hrmAssetInventory.use_person}" readonly>
            </td>
            <td style="width:33%">
                <label style="width: 40%;height: 40px">使用类型&nbsp;&nbsp;&nbsp;</label>
                <select name="use_type" id="use_type" style="width: 50%;height: 40px;color: black" onclick="cancelBorder()" disabled>
                    <option value="">${hrmAssetInventory.use_type}</option>
                </select>
            </td>
            <td style="width:33%">
                <label style="width: 40%;height: 40px">借出日期&nbsp;&nbsp;&nbsp;</label>
                <input type="text" name="lend_date" id="lend_date" style="width: 50%;height: 40px" value="<fmt:formatDate value="${hrmAssetInventory.lend_date }" type="date" pattern="yyyy/MM/dd"/>" readonly>
            </td>
        </tr>
        <tr style="width: 100%;height: 60px">
            <td style="width:33%">
                <label style="width: 40%;height: 40px">归还日期&nbsp;&nbsp;&nbsp;</label>
                <input type="text" name="return_date" id="return_date" style="width: 50%;height: 40px;" value="<fmt:formatDate value="${hrmAssetInventory.return_date }" type="date" pattern="yyyy/MM/dd"/>" readonly>
            </td>
            <td colspan="3">
                <label class="textBox">备注&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
                <textarea name="remark" id="remark" style="width: 75%;height: 80px" readonly>
                    ${hrmAssetInventory.remark}
                    </textarea>
            </td>
        </tr>
    </table>
    <br>
    <br>
</form>

<table id="table" width="100%"></table>
<script>
    //easyui表格构造
    $(document).ready(function () {
        var assetId = $("#assetId").val();
        //配置datagrid
        $("#table").datagrid({
            url:"AssetController.do?collarRecordDetail&assetId="+assetId,
            method:"get",
            pagination:true,
            singleSelect:true,
            toolbar:"#toolbar",
            title:"领用记录",
            fitColumns:true,
            nowrap:false,
            columns:[[{
                field:"id",
                title:"编号",
                hidden:true
                //sortable:true
            },{
                field:"lend_date",
                title:"领用日期",
                width:fixWidth(0.15),
                align:"center",
                formatter : function(value){
                    var date = new Date(value);
                    var y = date.getFullYear();
                    var m = date.getMonth() + 1;
                    var d = date.getDate();
                    return y + '-' +m + '-' + d;
                }
            },{
                field:"hrmDepart",
                title:"领用部门",
                width:fixWidth(0.15),
                align:"center",
                formatter:function(value,rowData,rowIndex){
                    if(rowData.hrmDepart==null){
                        return "无";
                    }else{
                        return rowData.hrmDepart.name;
                    }
                }
            },{
                field:"lend_person",
                title:"领用人员",
                width:fixWidth(0.15),
                align:"center"
            },{
                field:"return_date",
                title:"归还日期",
                width:fixWidth(0.15),
                align:"center",
                formatter : function(value){
                    var date = new Date(value);
                    var y = date.getFullYear();
                    var m = date.getMonth() + 1;
                    var d = date.getDate();
                    return y + '-' +m + '-' + d;
                }
            },{
                field:"remark",
                title:"备注",
                width:fixWidth(0.4),
                align:"center"
            }]]
        });
    });
    function fixWidth(percent)
    {
        return document.body.clientWidth * percent ;//根据自身情况更改

    }
</script>
</body>
</html>
