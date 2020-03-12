<%--
  Created by IntelliJ IDEA.
  User: tangzhen
  Date: 2019/7/18
  Time: 17:58
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
    <link rel="stylesheet" href="plug-in/ace/css/bootstrap.css"/>
    <link rel="stylesheet" href="plug-in/ace/css/font-awesome.css"/>
    <link rel="stylesheet" type="text/css" href="plug-in/accordion/css/accordion.css">
    <link rel="stylesheet" type="text/css" href="plug-in/hrm/css/resttime.css">
    <link rel="stylesheet" type="text/css" href="plug-in/datetimepicker/css/bootstrap-datetimepicker.min.css">
    <!-- text fonts -->
    <link rel="stylesheet" href="plug-in/ace/css/ace-fonts.css"/>
    <script src="plug-in/jquery-easyui-1.5.3/jquery.min.js"></script>
    <script src="plug-in/jquery-easyui-1.5.3/jquery.easyui.min.js"></script>
    <script src="plug-in/jquery-easyui-1.5.3/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="plug-in/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="plug-in/datetimepicker/js/bootstrap-datetimepicker.min.js"></script>
    <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.bootcss.com/bootstrap-datetimepicker/4.17.47/css/bootstrap-datetimepicker.min.css" rel="stylesheet">
    <script src="https://cdn.bootcss.com/jquery/3.3.1/jquery.js"></script>
    <script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="https://cdn.bootcss.com/moment.js/2.24.0/moment-with-locales.js"></script>
    <script src="https://cdn.bootcss.com/bootstrap-datetimepicker/4.17.47/js/bootstrap-datetimepicker.min.js"></script>
    <style>
        .datagrid-header-row td{background-color:#9AC3E1;color:black;height:32px;}
        .operate{
            border: black;
            color: #0b6cbc;
        }
        a:hover{
            color:red;
        }

        #toolbar{
            float: left;
        }
        .btn-primary{
            width: 80px;
            height: 35px;
            background: #0e78c9;
            color: white;
        }
    </style>
</head>

<body>
<!-- AddModal -->

<div  id="addreimburse"  >
        <div >
            <div style="font-size: x-large;">
                费用报销单录入
                <div>
                    <table border="1" cellspacing="0" cellpadding="0" id="restTable" class="table table-bordered">

                        <tr height="40px">
                            <td class="noneborderright">姓&nbsp;&nbsp;名：</td>
                            <%--<td class="noneborderboth"><input type="text" id="userName" class="apply_name" name="userName" readonly/>--%>
                            <td class="noneborderboth"><input type="text" id="userName" class="apply_name" name="userName" style="width:156px;" readonly/>
                                <%--<c:if test="${isResponsPerson==0}">--%>
                                    <%--<select id="userName" name="userName">--%>
                                    <%--</select>--%>
                                <%--</c:if>--%>
                                <%--<c:if test="${isResponsPerson==1}">--%>
                                    <%--<select id="userName" name="userName">--%>
                                        <%--<option value="0" >===请选择员工===</option>--%>
                                        <%--<c:forEach items="${userList}" var="var">--%>
                                             <%--<option value="${var.id}">${var.userName}</option>--%>
                                        <%--</c:forEach>--%>
                                    <%--</select>--%>

                                <%--</c:if>--%>
                            </td>
                            <td class="noneborderboth">工&nbsp;&nbsp;号：</td>
                            <td class="noneborderleft"><input type="text" id="jobNo" class="apply_name" name="jobNo"
                                                              style="width:156px;" readonly/></td>
                        </tr>
                        <tr height="40px">
                            <td class="noneborderright">部&nbsp;&nbsp;门：</td>
                            <td class="noneborderboth"><input type="text" id="dept" class="depart_name apply_name" name="dept" readonly/></td>
                            <td class="noneborderboth">日&nbsp;&nbsp;期：</td>
                            <td class="noneborderleft"><input type="text" id="currentDate" class="depart_name apply_name" name="currentDate"
                                                              style="width:156px;" readonly/></td>
                        </tr>
                        <tr height="40px">
                            <td class="noneborderright">实际产生项目 </td>
                            <td class="noneborderboth">
                                <div>
                                    <select name="projectId" id="projectId"  style="width: 55%;height: 25px; ">
                                        <option value="0">==请选择项目==</option>
                                        <c:forEach items="${projectList}" var="var">
                                            <option value="${var.projectName}" >${var.projectName}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </td>
                            <td class="noneborderboth">报销支出项目 </td>
                            <td class="noneborderleft">
                                <div>
                                    <select name="expendId" id="expendId" style="width: 55%;height: 25px;" >
                                        <option value="0">==请选择项目==</option>
                                        <c:forEach items="${projectList}" var="var">
                                            <option value="${var.projectName}" >${var.projectName}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </td>
                        </tr>
                        <tr height="40px">
                            <td class="noneborderright">实际产生项目编号 </td>
                            <td class="noneborderboth">
                                <div>
                                    <input name="projectCode" id="projectCode" style="width: 55%;height: 25px; " readonly />
                                </div>
                            </td>
                            <td class="noneborderboth">报销支出项目编号 </td>
                            <td class="noneborderleft">
                                <div>
                                    <input name="expendCode" id="expendCode" style="width: 55%;height: 25px;"  readonly />

                                </div>
                            </td>
                        </tr>
                        <tr height="40px">
                            <td class="noneborderright">出差开始时间 </td>
                            <td class="noneborderboth">
                                <%--<div>--%>
                                    <%--<input type="datetime-local" id="startTime" name="startTime"  onblur="countTime(this)" style="height:25px;" >--%>
                                <%--</div>--%>
                                    <div class='input-group date' id='datetimepicker1' style="width: 1px;" >

                                        <input type='text'  id="startTime" class="form-control" onblur="countTime(this)" style="width: 328px;height: 30px; " />

                                        <span class="input-group-addon">

                    <span class="glyphicon glyphicon-calendar"></span>

                </span>

                                    </div>
                            </td>
                            <td class="noneborderboth">出差结束时间 </td>
                            <td class="noneborderleft">
                                <%--<div>--%>
                                <%--<input type="datetime-local" id="endTime" name="endTime" onblur="countTime(this)" style="height:25px;">--%>
                                <%--</div>--%>
                                <div class='input-group date' id='datetimepicker2' style="width: 1px;">

                                    <input type='text' id="endTime" class="form-control" onblur="countTime(this)" style="width: 328px;height: 30px;" />

                                    <span class="input-group-addon">

                    <span class="glyphicon glyphicon-calendar"></span>

                </span>

                                </div>
                            </td>
                        </tr>
                        <tr height="40px">
                            <td class="noneborderright">成本中心号 </td>
                            <td class="noneborderboth">
                                <input type="text" id="costcenterCode"  name="costcenterCode" style=" border-bottom:2px solid #eee;width:148px;text-align:center;">
                            </td>
                            <td class="noneborderboth">项目 </td>
                            <td class="noneborderleft">
                                <input type="text" id="projectName" name="projectName" style=" border-bottom:2px solid #eee;width:165px;text-align:center;" readonly>
                            </td>
                        </tr>
                        <tr height="40px">
                            <td class="noneborderright">出差地点 </td>
                            <td class="noneborderboth">
                                <div>
                                    <select name="countryName" id="countryName" style="width: 55%;height: 25px;">
                                        <option value="0">==请选择国家==</option>
                                        <c:forEach items="${countryList}" var="var">
                                            <option value="${var.allowance_fee}">${var.country_name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </td>
                            <td class="noneborderboth">餐补金额 </td>
                            <td class="noneborderleft">
                                <input type="text" id="allowanceFee"  name="allowanceFee" style=" border-bottom:2px solid #eee;width:165px;text-align:center;" >
                            </td>
                        </tr>
                        <tr height="40px">
                            <td >货币类型 </td>
                            <td  >
                                <input type="text" id="currency" name="currency" style=" border-bottom:2px solid #eee;width:148px;text-align:center;">
                            </td>
                        </tr>
                    </table>
                </div>
                <div>
                    <table id="detailsData" class="table table-bordered">
                        <thead style="text-align: center">
                        <tr>
                            <td>日期</td>
                            <td>费用类型</td>
                            <td>简述</td>
                            <td>金额</td>
                        </tr>
                        </thead>
                        <tbody id="addbody">
                        </tbody>
                    </table>
                    <button type="button" class="btn btn-primary" id="addtr"> + </button>
                    <button type="button" class="btn btn-danger" id="deletetr" style="width: 80px; height: 35px;"> — </button>
                </div>
            </div>
            <div class="modal-footer">
                <a href="reimburseController.do?closeAdd" class="btn btn-danger" data-dismiss="modal" id="close">关闭</a>
                <button type="button" class="btn btn-primary" id="add_button">提交</button>
            </div>

        </div>
</div>

<script>
    var checkTime=false;
    var i=0;
    var userId;
    //判断时间是否正确
    function countTime(th,c) {

        /*  var type = $(th).attr("name");
         var beginTime;
         var endTime;
         if(type=="beginTime"){
             beginTime = $(th).val();
             endTime = $(th).next().val();
             alert(beginTime+endTime);
         }else{
             beginTime = $(th).prev().val();
             endTime = $(th).val();
             alert(beginTime+endTime);
         } */
        var startTime = $("#startTime").val();
        var endTime = $("#endTime").val();

        console.log(startTime+"============="+endTime)
        //判断是否为空
        if(($.trim(startTime).length!=0 && $.trim(endTime).length!=0)||c!=undefined){
            var startMill = Date.parse(startTime);
            var endMill = Date.parse(endTime);

            //判断开始时间是否小于结束时间
            if(startMill>endMill){
                alert("开始时间大于结束时间，请调整！");
                return false;
            }else if(startMill==endMill){
                alert("开始时间等于结束时间，请调整！");
                return false;
            }
            checkTime=true;
        }
    }
    $("#countryName").change(function () {
        console.log($("#countryName").val()+'-----------------')
        if(checkTime==false){
            alert("请设置好时间以便于预算补贴金额")
            return
        }
        var startTime = $("#startTime").val();
        var endTime = $("#endTime").val();
        var startMill = Date.parse(startTime);
        var endMill = Date.parse(endTime);
        var chour = (new Date(endMill) - new Date(startMill)) / 3600  / 1000;
        var a=parseInt(chour/24)
        var yu=chour%24;
        var money= 0
        money=money+$("#countryName").val()*a
        var money1=($("#countryName").val())/3;
        console.log("=================="+yu)
        console.log("=================="+money)
        if(0<yu && yu<4){
            money=money+money1
        }else if(4<=yu && yu<8){
            money=money+money1*2
        }else if(yu>=8){
            money=money+money1*3
        }
        $("#allowanceFee").val(money)
    })
    $("#add").click(function(){
        getinfo();
        $("#add_vacationApply_modal").modal();
    })
    $("#addtr").click(function () {
        $("#addbody").append("<tr><td style='width: 140px'><input style='width: 100%;height: 20px;'  type='date'  /></td>" +
            "<td style='width: 140px;'><div ><select style='width:100%;height:20px;'>" +
            "<option value='1'>交通</option>" +
            "<option value='2'>酒店</option>" +
            "<option value='3'>通讯</option>" +
            "<option value='4'>补贴</option>" +
            "<option value='9'>其它</option>" +
            "</select></div></td>" +
            "<td><input style='width:100% ;outline: none;border-width: 0px;' type='text'/></td>" +
            "<td style='width: 100px;'><input style='outline: none;border-width: 0px;' type='text'/></td></tr>")
    })

    $("#deletetr").click(function () {
        var len=$("#addbody").find("tr").length
        if(len>1){
            $("#addbody").find("tr").last().remove()
        }
    })

    $("#expendId").change(function () {
        $("#projectName").val($("#expendId").val());
    })
    $("#add_button").click(function (){
        //请假时间校验是否选择
        var beginTime = $("input[name='startTime']").val();
        var endTime = $("input[name='endTime']").val();
        // if($.trim(beginTime).length==0){
        //     $("#startTime").css("border-color","red");
        //     alert("请设置正确的时间格式");
        //     return false;
        // }
        // if($.trim(endTime).length==0){
        //     $("#endTime").css("border-color","red");
        //     alert("请设置正确的时间格式");
        //     return false;
        // }

        //联系方式
        if($("#projectId").val()==0){
            $("#projectId").css("border-color","red");
            return false;
        }
        //请说明
        if($("#expendId").val()==0){
            $("#expendId").css("border-color","red");
            return false;
        }
        //申请者签字
        if($("#countryName").val()==0){
            $("#countryName").css("border-color","red");
            return false;
        }
        var startTime = $("#startTime").val();
        var endTime = $("#endTime").val();
        var startMill = Date.parse(startTime);
        var endMill = Date.parse(endTime);
        var map={
            userId:userId,
            projectName:$("#projectId").val() ,
            expendName:$("#expendId").val(),
            countryName:$.trim($("#countryName").find("option:selected").text()),
            applyDate:$.trim($("#currentDate").val()),
            startTime:startMill,
            endTime:endMill,
            costcenterCode:$("#costcenterCode").val(),
            currency:$("#currency").val(),
            allowanceFee:$("#allowanceFee").val(),
            reimburseList:[]
        };

        $("#detailsData tbody tr").each(function(){
            var obj=new Object();
            var date = $(this).find("input").eq(0).val();//获取每一行
            var feeType=$(this).find("select").eq(0).val()
            var details=$(this).find("input").eq(1).val()
            var money=$(this).find("input").eq(2).val()
            obj.createDate=date;
            obj.costType=feeType;
            obj.details=details;
            obj.costFee=money;
            map.reimburseList.push(obj)
        });
        $.ajax({
            type: "POST",
            url: "reimburseController.do?addReimburse",
            contentType: "application/json",
            async: false,
            cache: false,
            data:JSON.stringify(map),
            dataType: "json",
            success:function (message) {
                alert(JSON.stringify(message.msg));
                if(message.success==true){
                    window.history.back(-1);
                }
            },
            error:function (message) {
                alert("提交失败");
            }
        });

    })

    $(document).ready(function () {

        getinfo();
        //$("#gainTime").append(time);
    });

    $("#projectId").change(function () {
        var data={projectName:this.value};
        $.ajax({
            type: "POST",
            url: "reimburseController.do?findProjectCode",
            contentType: "application/json",
            async: false,
            cache: false,
            data:JSON.stringify(data),
            dataType: "json",
            success:function (message) {
                $("#projectCode").val(message.obj.code);
            },
            error:function (message) {
                alert("提交失败");
            }
        });


    });

    $("#expendId").change(function () {
        var data={projectName:this.value};
        $.ajax({
            type: "POST",
            url: "reimburseController.do?findProjectCode",
            contentType: "application/json",
            async: false,
            cache: false,
            data:JSON.stringify(data),
            dataType: "json",
            success:function (message) {
                $("#expendCode").val(message.obj.code);
            },
            error:function (message) {
                alert("提交失败");
            }
        });


    });

    function getinfo(){
        $.ajax({
            type: 'get',
            url: 'attendance.do?getuseronline',
            async: false,
            cache: false,
            success: function (result) {
                var r = JSON.parse(result);
                if (!r.success) {
                    alert(r.msg);
                    return;
                }
                var list = r.obj;
                userName = list.userName;
                userId=list.id;
                $("#userName").val(userName);
                $("#dept").val(list.department);
                $("#jobNo").val(list.jobNum);
            }
        });
        var now = new Date();
        var time = timestampToTime(now.getTime());
        $("#gainTime").val(time);
        $("#currentDate").val(time);
    }




    // <!-- 时间戳转日期 -->
    // <!-- begin -->
    function timestampToTime(timestamp) {
        var datetime = new Date(timestamp);//时间戳为10位需*1000，时间戳为13位的话不需乘1000
        var year = datetime.getFullYear();
        var month = datetime.getMonth() + 1 < 10 ? "0" + (datetime.getMonth() + 1) : datetime.getMonth() + 1;
        var date = datetime.getDate() < 10 ? "0" + datetime.getDate() : datetime.getDate();
        return year + "-" + month + "-" + date;
    }
    // <!-- 时间戳转日期 -->
    // YYYY-MM-dd HH:mm:ss
    function timestampToTime2(timestamp){
        var datetime = new Date(timestamp);
        var year = datetime.getFullYear();
        var month = datetime.getMonth() + 1 < 10 ? "0" + (datetime.getMonth() + 1) : datetime.getMonth() + 1;
        var date = datetime.getDate() < 10 ? "0" + datetime.getDate() : datetime.getDate();
        var hour = datetime.getHours()< 10 ? "0" + datetime.getHours() : datetime.getHours();
        var minute = datetime.getMinutes()< 10 ? "0" + datetime.getMinutes() : datetime.getMinutes();
        var second = datetime.getSeconds()< 10 ? "0" + datetime.getSeconds() : datetime.getSeconds();
        return year + "-" + month + "-" + date+" "+hour+":"+minute+":"+second;
    }

    // YYYY-MM-dd HH:mm
    function timestampToTime3(timestamp){
        var datetime = new Date(timestamp);
        var year = datetime.getFullYear();
        var month = datetime.getMonth() + 1 < 10 ? "0" + (datetime.getMonth() + 1) : datetime.getMonth() + 1;
        var date = datetime.getDate() < 10 ? "0" + datetime.getDate() : datetime.getDate();
        var hour = datetime.getHours()< 10 ? "0" + datetime.getHours() : datetime.getHours();
        var minute = datetime.getMinutes()< 10 ? "0" + datetime.getMinutes() : datetime.getMinutes();
        return year + "-" + month + "-" + date+"T"+hour+":"+minute;
    }
    $(function () {

        $('#datetimepicker1').datetimepicker({

            format: 'YYYY-MM-DD HH:mm',

            locale: moment.locale('zh-cn')

        });

        $('#datetimepicker2').datetimepicker({

            format: 'YYYY-MM-DD HH:mm',

            locale: moment.locale('zh-cn')

        });

    });








</script>

</body>
</html>
