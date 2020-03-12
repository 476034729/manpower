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
    <script src="plug-in/jquery-easyui-1.5.3/jquery.min.js"></script>
    <script src="plug-in/jquery-easyui-1.5.3/jquery.easyui.min.js"></script>
    <script src="plug-in/jquery-easyui-1.5.3/locale/easyui-lang-zh_CN.js"></script>
    <style>
        .datagrid-header-row td{background-color:#0b6cbc;color:black}
        .operate{
            border: black;
            color: #0b6cbc;
        }
    </style>
</head>

<body>
    <table id="table" width="100%"></table>
    <div id="toolbar" style="padding: 5px">
        <label for="assetType">资产类型</label>
        <select name="assetType" id="assetType">
            <option value="">请选择...</option>
            <c:forEach var="hrmAssetType" items="${hrmAssetTypeList}">
                <option value="${hrmAssetType.id}">${hrmAssetType.name}</option>
            </c:forEach>
        </select>
        <label for="assetBrand">资产品牌</label>
        <select name="assetBrand" id="assetBrand">
            <option value="">请选择...</option>
            <c:forEach var="hrmBrand" items="${hrmBrandList}">
                <option value="${hrmBrand.id}" >${hrmBrand.name}</option>
            </c:forEach>
        </select>
        <label for="assetStatus">资产状态</label>
        <select name="assetStatus" id="assetStatus">
            <option value="">请选择...</option>
            <option value="库存">库存</option>
            <option value="借出">借出</option>
        </select>
        <label for="useDept">使用部门</label>
        <select name="useDept" id="useDept">
            <option value="">请选择...</option>
            <c:forEach var="hrmDepart" items="${hrmDepartList}">
                <option value="${hrmDepart.id}" name="">${hrmDepart.name}</option>
            </c:forEach>
        </select>
        <label >使用日期</label>
        <input type="date" id="startDate" name="startDate">-
        <input type="date" id="endDate" name="endDate">
        <label for="usePerson">使用人员</label>
        <input type="text" name="usePerson" id="usePerson">
        <a onclick="query()" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true">查询</a>
    </div>
    <script>
        $(document).ready(function () {
            //配置datagrid
            $("#table").datagrid({loadFilter:pagerFilter}).datagrid({
                url:"AssetController.do?assetList",
                method:"get",
                pagination:true,
                pageSize:10,
                pageList:[10,20,30,40,50],
                singleSelect:true,
                toolbar:"#toolbar",
                title:"资产查询",
                columns:[[{
                    field:"id",
                    title:"编号",
                    hidden:true
                    //sortable:true
                },{
                    field:"hrmAssetType",
                    title:"资产类型",
                    width:200,
                    align:"center",
                    formatter:function(value,rowData,rowIndex){
                        return rowData.hrmAssetType.name;
                    }
                },{
                    field:"asset_no",
                    title:"资产编号",
                    width:200,
                    align:"center"
                },{
                    field:"hrmBrand",
                    title:"资产品牌",
                    width:200,
                    align:"center",
                    formatter:function(value,rowData,rowIndex){
                        if(rowData.hrmBrand==null){
                            return "无";
                        }else{
                            return rowData.hrmBrand.name;
                        }
                    }
                },{
                    field:"model_specifiction",
                    title:"规格型号",
                    width:300,
                    align:"center"
                },{
                    field:"hrmDepart",
                    title:"使用部门",
                    width:200,
                    align:"center",
                    formatter:function(value,rowData,rowIndex){
                        if(rowData.hrmDepart==null){
                            return "无";
                        }else{
                            return rowData.hrmDepart.name;
                        }
                    }
                },{
                    field:"asset_status",
                    title:"使用状态",
                    width:200,
                    align:"center"
                },{
                    field:"use_person",
                    title:"使用人员",
                    width:200,
                    align:"center",
                    formatter:function(value,rowDate,rowIndex){
                        if(rowDate.use_person==null){
                            return "无";
                        }else{
                            return rowDate.use_person;
                        }
                    }
                }, {
                    field:"_operate",
                    title:"操作",
                    width:200,
                    align:"center",
                    formatter:formatOper
                }]]
            });
        });
        function formatOper(val,row,index) {
            return '<a class="operate" href="AssetController.do?assetDetail&assetId='+row.id+'" >详情</a> <a class="operate" href="AssetController.do?assetEdit&assetId='+row.id+'">修改</a> <a class="operate" href="#" onclick="delAsset(\''+row.id+'\',\''+index+'\')">删除</a> '
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
        function  editAsset(id) {
            $('#table').datagrid('selectRow',index);
            var row = $('#table').datagrid('getSelected');
            if(row){
                $('#dlg').dialog('open').dialog('setTitle','修改学生信息');
                $("#fm").form('load',row);
                url = row.id;
            }
        }
        //查询工具栏
        function query(){
            var status = document.getElementById("assetStatus");
            var status1 = status.options[status.selectedIndex].value;
            $("#table").datagrid("load",{
                assetType:$("#assetType").val(),
                assetBrand:$("#assetBrand").val(),
                assetStatus:status1,
                useDept:$("#useDept").val(),
                startDate:$("#startDate").val(),
                endDate:$("#endDate").val(),
                usePerson:$("#usePerson").val()
            });
        }
        //删除资产
        function delAsset(id,index) {
            var r = confirm("是否删除？");
            if(r == true){
                $.ajax({
                    type: 'get',
                    url: 'AssetController.do?delAsset&assetId=' + id,
                    async: false,
                    cache: false,
                    success: function (result) {
                        var resultDate = JSON.parse(result);
                        if(resultDate.msg=='ok'){
                            $('#table').datagrid('deleteRow',index);
                        }
                    }
                });
            }else{
                return;
            }

        }
    </script>
</body>
</html>
