<%--
  Created by IntelliJ IDEA.
  User: 52629
  Date: 2018/11/2
  Time: 9:14
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
    <script type="text/javascript" src="plug-in/My97DatePicker/WdatePicker.js"></script>
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
        .textBox{
            position: relative;
            bottom: 35px;
        }
        a{
            text-decoration: none;
            color: black;
        }
    </style>
</head>
<body>
<p>当前位置><a href="AssetController.do?queryAssetInventory">资产查询</a>>资产修改</p>
<form id="assetEntryForm" action="AssetController.do?saveAssetEdit" method="post" onsubmit="return formSubmit();">
    <input type="hidden" name="id" id="id" value="${assetInventory.id}">
    <input type="hidden" name="status" id="status" value="${assetInventory.asset_status}">
    <table width="100%">
        <tr style="width: 100%;height: 60px">
            <td  style="width:33%;">
                <label style="width: 40%;height: 40px" >资产类型&nbsp;&nbsp;&nbsp;</label>
                <select  style="width: 50%;height: 40px;color: black" name="hrmAssetTypeId" id="asset_id" disabled>
                    <option value="${assetInventory.hrmAssetType.id}">${assetInventory.hrmAssetType.name}</option>
                </select>
            </td>
            <td style="width:33%;height: 100%">
                <label style="width: 40%;height: 50px">资产编号&nbsp;&nbsp;&nbsp;</label>
                <input type="text" name="asset_no" id="asset_no" style="width: 50%;height: 40px" value="${assetInventory.asset_no}" disabled>
            </td>
            <td style="width:33%;height: 100%">
                <label style="width: 40%;height: 40px">录入人员&nbsp;&nbsp;&nbsp;</label>
                <input type="text" name="entry_person" id="entry_person" value="${assetInventory.entry_person}" style="width: 50%;height: 40px" disabled>
            </td>
        </tr>
        <tr style="width: 100%;height: 60px">
            <td style="width:33%">
                <label style="width: 40%;height: 40px">品牌&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
                <select style="width: 50%;height: 40px;color: black" name="hrmBrandId" id="brand_id" disabled>
                    <option value="${assetInventory.hrmBrand.id}">${assetInventory.hrmBrand.name}</option>

                </select>
            </td>
            <td style="width:33%">
                <label style="width: 40%;height: 40px">内存大小&nbsp;&nbsp;&nbsp;</label>
                <input type="text" name="hard_memory_size" id="hard_memory_size" style="width: 50%;height: 40px" onclick="cancelBorder()" value="${assetInventory.hard_memory_size}">
            </td>
            <td style="width:33%">
                <label style="width: 40%;height: 40px">硬盘大小&nbsp;&nbsp;&nbsp;</label>
                <input type="text" name="disk_size" id="disk_size" style="width: 50%;height: 40px" onclick="cancelBorder()" value="${assetInventory.disk_size}">
            </td>
        </tr>
        <tr style="width: 100%;height: 60px">
            <%--<td colspan="3">--%>
                <%--<label style="width: 40%;height: 40px">主机编号&nbsp;&nbsp;&nbsp;</label>--%>
                <%--<input type="text" name="host_no" id="host_no" style="width: 16.5%;height: 40px" value="${assetInventory.host_no}">--%>
            <%--</td>--%>
            <td style="width:33%">
                <label style="width: 40%;height: 40px">主机编号&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
                <input type="text" name="host_no" id="host_no" style="width: 50%;height: 40px" value="${assetInventory.host_no}">
            </td>
            <td style="width:33%">
                <label style="width: 40%;height: 40px">ip&nbsp;&nbsp;&nbsp;</label>
                <input type="text" name="ip" id="ip" style="width: 50%;height: 40px" onclick="cancelBorder()" value="${assetInventory.ip}">
            </td>
            <td style="width:33%">
                <label style="width: 40%;height: 40px">新资产编号&nbsp;&nbsp;&nbsp;</label>
                <input type="text" name="asset_no_new" id="asset_no_new" style="width: 50%;height: 40px" onclick="cancelBorder()" value="${assetInventory.asset_no_new}">
            </td>
        </tr>
        <tr style="width: 100%;height: 60px">
            <td colspan="3" style="width: 100%">
                <label class="textBox">型号规格&nbsp;&nbsp;&nbsp;</label>
                <textarea name="model_specifiction" id="model_specifiction" style="width: 83.5%;height: 80px" onclick="cancelBorder()" >${assetInventory.model_specifiction}</textarea>
            </td>
        </tr>
        <tr style="width: 100%;height: 60px">
            <td colspan="3">
                <label class="textBox">详细配置&nbsp;&nbsp;&nbsp;</label>
                <textarea name="detailed_configuration" id="detailed_configuration" style="width: 83.5%;height: 80px" onclick="cancelBorder()" >${assetInventory.detailed_configuration}</textarea>
            </td>
        </tr>
        <tr style="width: 100%;height: 60px">
            <td style="width:33%">
                <label style="width: 40%;height: 40px">采购单价&nbsp;&nbsp;&nbsp;</label>
                <input type="text" name="purchase_unit_price"id="purchase_unit_price" style="width: 50%;height: 40px" onclick="cancelBorder()" value="${assetInventory.purchase_unit_price}">
            </td>
            <td style="width:33%">
                <label style="width: 40%;height: 40px">采购日期&nbsp;&nbsp;&nbsp;</label>
                <input type="date" name="purchase_date" id="purchase_date" style="width: 50%;height: 40px" onclick="cancelBorder()" onFocus="WdatePicker({isShowClear:true,readOnly:true})" value='<fmt:formatDate value="${assetInventory.purchase_date }" pattern="yyyy-MM-dd"/>'>
            </td>
            <td style="width:33%">
                <label style="width: 40%;height: 40px">启用日期&nbsp;&nbsp;&nbsp;</label>
                <input type="date" name="use_date" id="use_date" style="width: 50%;height: 40px" onclick="cancelBorder()" onFocus="WdatePicker({isShowClear:true,readOnly:true})" value='<fmt:formatDate value="${assetInventory.use_date }" pattern="yyyy-MM-dd"/>'>
            </td>
        </tr>
        <tr style="width: 100%;height: 60px">
            <td style="width:33%">
                <label style="width: 40%;height: 40px">折旧年限&nbsp;&nbsp;&nbsp;</label>
                <input type="text" name="depreciation_years" id="depreciation_years" style="width: 50%;height: 40px" onclick="cancelBorder()" value="${assetInventory.depreciation_years}">
            </td>
            <td style="width:33%">
                <label style="width: 40%;height: 40px">资产状态&nbsp;&nbsp;&nbsp;</label>
                <select name="asset_status" id="asset_status" style="width: 50%;height: 40px" onclick="cancelBorder()">
                    <option value="库存" ${assetInventory.asset_status == "库存"?"selected='selected'":''}>库存</option>
                    <option value="借出" ${assetInventory.asset_status eq "借出"?"selected='selected'":''}>借出</option>
                </select>
            </td>
            <td style="width:33%">
                <label style="width: 40%;height: 40px">使用部门&nbsp;&nbsp;&nbsp;</label>
                <select name="hrmDepartId" id="hrmDepartId" style="width: 50%;height: 40px" onclick="cancelBorder()">
                    <option value="">请选择...</option>
                    <c:forEach var="hrmDepart" items="${hrmDepartList}">
                        <option value="${hrmDepart.id}" name="" ${assetInventory.hrmDepart.id==hrmDepart.id?"selected='selected'":''}>${hrmDepart.name}</option>
                    </c:forEach>
                </select>
            </td>
        </tr>
        <tr style="width: 100%;height: 60px">
            <td style="width:33%">
                <label style="width: 40%;height: 40px">使用人员&nbsp;&nbsp;&nbsp;</label>
                <input type="text" name="use_person" id="use_person" style="width: 50%;height: 40px" onclick="cancelBorder()" value="${assetInventory.use_person}" >
            </td>
            <td style="width:33%">
                <label style="width: 40%;height: 40px">使用类型&nbsp;&nbsp;&nbsp;</label>
                <select name="use_type" id="use_type" style="width: 50%;height: 40px" onclick="cancelBorder()">
                    <option value="" ${assetInventory.use_type==null?"selected='selected'":''}>请选择...</option>
                    <option value="ChianMember(中国员工)" ${assetInventory.use_type=="ChianMember(中国员工)"?"selected='selected'":''}>ChianMember(中国员工)</option>
                    <option value="Inventory(库存)" ${assetInventory.use_type=="Inventory(库存)"?"selected='selected'":''}>Inventory(库存)</option>
                    <option value="Low configurationI(低配置)" ${assetInventory.use_type=="Low configurationI(低配置)"?"selected='selected'":''}>Low configurationI(低配置)</option>
                    <option value="HardDiskError(硬件损坏)" ${assetInventory.use_type=="HardDiskError(硬件损坏)"?"selected='selected'":''}>HardDiskError(硬件损坏)</option>
                    <option value="Singapore(新加坡资产)" ${assetInventory.use_type=="Singapore(新加坡资产)"?"selected='selected'":''}>Singapore(新加坡资产)</option>
                    <option value="Scrapped（报废）" ${assetInventory.use_type=="Scrapped（报废）"?"selected='selected'":''}>Scrapped（报废）</option>
                </select>
            </td>
            <td style="width:33%">
                <label style="width: 40%;height: 40px">借出日期&nbsp;&nbsp;&nbsp;</label>
                <input type="date" name="lend_date" id="lend_date" style="width: 50%;height: 40px" onclick="cancelBorder()" onFocus="WdatePicker({isShowClear:true,readOnly:true})" value='<fmt:formatDate value="${assetInventory.lend_date }" pattern="yyyy-MM-dd"/>' >
            </td>
        </tr>
        <tr style="width: 100%;height: 60px">
            <td style="width:33%">
                <label style="width: 40%;height: 40px">归还日期&nbsp;&nbsp;&nbsp;</label>
                <input type="date" name="return_date" id="return_date" style="width: 50%;height: 40px;" onclick="cancelBorder()" onFocus="WdatePicker({isShowClear:true,readOnly:true})" value='<fmt:formatDate value="${assetInventory.return_date }" pattern="yyyy-MM-dd"/>'>
            </td>
            <td colspan="2">
                <label class="textBox">备注&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
                <textarea name="remark" id="remark" style="width: 75%;height: 80px" onclick="cancelBorder()">
                    ${assetInventory.remark}
                    </textarea>
            </td>
        </tr>
    </table>
    <br>
    <br>
    <div  class="btn_option" >
        <input type="button" class="btn-primary" id="sure" value="提交" onclick="formSubmit()">
        <input type="button" class="btn-danger" id="reset" style="margin-left: 50px" value="还原" onclick="formReducation()">
    </div>
</form>
<script type="text/javascript" src="plug-in/jquery/jquery-1.8.3.min.js"></script>
<script type="text/javascript">
    //表单提交前进行验证
    function formSubmit() {
        //资产类型
        var assetType = $("#asset_id").find("option:selected").text();
        if(assetType=="笔记本电脑"||assetType=="台式电脑"||assetType=="显示器"){
            //资产状态
            //验证取消
            var status = $("#asset_status").val();
            if($.trim($("#hard_memory_size").val()).length==0){
                if($("#asset_id option:selected").text()=='笔记本电脑'||$("#asset_id option:selected").text()=='台式电脑'||$("#asset_id option:selected").val()=='服务器'){
                    $("#hard_memory_size").css("border-color","red");
                    return false;
                }
            }
            if($.trim($("#disk_size").val()).length==0){
                if($("#asset_id option:selected").text()=='笔记本电脑'||$("#asset_id option:selected").text()=='台式电脑'||$("#asset_id option:selected").val()=='服务器'){
                    $("#disk_size").css("border-color","red");
                    return false;
                }
            }
            if($.trim($("#model_specifiction").val()).length==0){
                $("#model_specifiction").css("border-color","red");
                return false;
            }
            if($.trim($("#detailed_configuration").val()).length==0){
                $("#detailed_configuration").css("border-color","red");
                return false;
            }
            /*if(parseInt($("#purchase_unit_price").val()).toString() == "NaN"){
                $("#purchase_unit_price").css("border-color","red");
                return false;
            }*/
            if(!(/(^(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*))$)/.test($("#purchase_unit_price").val()))){
                $("#purchase_unit_price").css("border-color","red");
                return false;
            }
            if($.trim($("#purchase_date").val()).length==0){
                $("#purchase_date").css("border-color","red");
                return false
            }
            if($.trim($("#use_date").val()).length==0){
                $("#use_date").css("border-color","red");
                return false;
            }
            /*if(parseFloat($("#depreciation_years").val()).toString() == "NaN"){
                $("#depreciation_years").css("border-color","red");
                return false;
            }*/
            if(!(/(^[1-9][0-9]*$)/.test($("#depreciation_years").val()))){
                $("#depreciation_years").css("border-color","red");
                return false;
            }
            if($.trim($("#return_date").val()).length==0){
                if($("#status").val()=="借出" && status=="库存"){
                    $("#return_date").css("border-color","red");
                    return false;
                }
            }
            if(status=="借出"){
                if($.trim($("#hrmDepartId").val()).length==0){
                    $("#hrmDepartId").css("border-color","red");
                    return false;
                }
                if($.trim($("#use_person").val()).length==0){
                    $("#use_person").css("border-color","red");
                    return false;
                }
                if($.trim($("#use_type").val()).length==0){
                    $("#use_type").css("border-color","red");
                    return false;
                }
                if($.trim($("#lend_date").val()).length==0){
                    $("#lend_date").css("border-color","red");
                    return false;
                }
            }
            if($("#asset_status option:selected").val()=='库存' && $.trim($("#use_person").val()).length!=0 && $.trim($("#return_date").val()).length==0){
                $("#return_date").css("border-color","red");
                return false;
            }
        }
        document.getElementById("assetEntryForm").submit();
    }
    //点击文本框取消验证
    function cancelBorder() {
        $("#asset_no").css("border-color","");
        $("#hard_memory_size").css("border-color","");
        $("#disk_size").css("border-color","");
        $("#model_specifiction").css("border-color","");
        $("#hard_memory_size").css("border-color","");
        $("#detailed_configuration").css("border-color","");
        $("#purchase_unit_price").css("border-color","");
        $("#purchase_date").css("border-color","");
        $("#use_date").css("border-color","");
        $("#asset_id").css("border-color","");
        $("#brand_id").css("border-color","");
        $("#hrmDepartId").css("border-color","");
        $("#use_type").css("border-color","");
        $("#depreciation_years").css("border-color","");
        $("#use_person").css("border-color","");
        $("#lend_date").css("border-color","");
        $("#return_date").css("border-color","");
    }
    //借出状态修改时，改变相应字段的可用性
    function changeStatues() {
        if($("#asset_status option:selected").val()=='借出'){
            $("#hrmDepartId").attr("disabled",false);
            $("#use_person").attr("disabled",false);
            $("#use_type").attr("disabled",false);
            $("#lend_date").attr("disabled",false);
            $("#return_date").attr("disabled",false);
        }else if($("#asset_status option:selected").val()=='库存'){
            $("#hrmDepartId").attr("disabled",true);
            $("#use_person").attr("disabled",true);
            $("#use_type").attr("disabled",true);
            $("#lend_date").attr("disabled",true);
            $("#return_date").attr("disabled",true);
            //把借出的相应字段设置为空
            $("#hrmDepartId").val("");
            $("#use_person").val("");
            $("#use_type").val("");
            $("#lend_date").val("");
            $("#return_date").val("");
        }
    }
    //表单重置
    function formReset() {
        $("#hard_memory_size").val("");
        $("#disk_size").val("");
        $("#model_specifiction").val("");
        $("#detailed_configuration").val("");
        $("#purchase_unit_price").val("");
        $("#purchase_date").val("");
        $("#use_date").val("");
        $("#asset_status").val("库存");
        $("#hrmDepartId").val("");
        $("#use_person").val("");
        $("#use_type").val("");
        $("#lend_date").val("");
        $("#return_date").val("");
        $("#depreciation_years").val("");
    }

    //还原表单
    function formReducation() {
        var r = confirm("是否还原？");
        if(r == true){
            window.location.reload();
        }else{
            return ;
        }
    }
</script>
</body>
</html>
