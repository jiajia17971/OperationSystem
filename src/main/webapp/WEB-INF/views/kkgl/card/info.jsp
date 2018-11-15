<%--
  Created by IntelliJ IDEA.
  User: wqp
  Date: 2018-7-29
  Time: 9:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/common/taglibs.jsp" %>
<%--<%@ include file="/common/meta.jsp" %>--%>
<!DOCTYPE html>
<html>
<head>
  <%--  <%@ include file="/common/meta.jsp" %>--%>
    <link rel="stylesheet" href="${ctx}/js/lib/layui/css/layui.css">

    <%--<link type="text/css" rel="stylesheet" href="${ctx}/css/bootstrap.min.css" charset="utf-8"/>--%>
    <script type="text/javascript" src="${ctx}/js/dev/kkgl/voucher/info.js"></script>
    <script type="text/javascript" src="${ctx}/js/lib/laydate/laydate.js"></script>
    <script type="text/javascript" src="${ctx}/js/lib/layui/layui.js"></script>
    <script type="text/javascript" src="${ctx}/js/lib/jquery-1.11.1.min.js"></script>


    <style>
        .breadcrumb{
            border-radius: 3px 3px 0px 0px;
            height: 30px;
            line-height: 30px;
            padding: 0px 8px;
            margin: 0px;
            font-size: 9pt;
            margin-bottom: 10px;
            border: 1px solid #CACACA;
            background-color: #f2f2f2;
            box-shadow: 0px 1px 0px #FFF inset;
        }
        .breadcrumb li{
            display: inline-block;
            float: left;
        }
        li:before{
            content: ">";
        }

    </style>
</head>
<body>
    <ul class="breadcrumb">
        <li class="icon"></li>
        <li>您所在的位置：</li>
        <li>开库管理</li>
        <li>卡片信息</li>
    </ul>
<div class="layui-fluid" >
    <div class="layui-card" style="background: #f9f9f9">
        <div class="layui-card-header" id="btn_add" style="border-bottom:none">添加卡片</div>
        <hr>

        <div class="layui-card-body" style="padding: 15px;">
            <input type="hidden" value="${type}" id="pageType">
            <form class="layui-form" action=""  id="info_form" >
                <input type="hidden" name="voucherid" value="${voucher.id}" id="kid">
                <input type="hidden" name="vouchertype" value="${voucher.type}" id="v_type">
                <input type="hidden" name="vstate" value="${voucher.status}" id="vstate">


                <input type="hidden" name="id" value="${card.id}" id="cid">
                <div class="layui-form-item ">
                    <label class="layui-form-label">卡号&nbsp;<font color="red">*</font></label>
                    <div class="layui-input-inline" style="width: 80%">
                        <input type="text" name="faceCardNum" id="faceCardNum" lay-verify="title" autocomplete="off" placeholder="请输入16位通衢卡卡号"  class="layui-input" style="width: 42%;float: left" <c:if test="${'view' eq type}">disabled</c:if> value="${card.faceCardNum}">
                    </div>
                </div>

                <div class="layui-form-item" style="width: 41%;">
                    <label class="layui-form-label">金额(元)&nbsp;<font color="red">*</font></label>
                    <div class="layui-input-block">
                        <input type="text" name="optAmount" id="i_amount" lay-verify="amount"  autocomplete="off" <c:if test="${voucher.type ne '0' && voucher.type ne '2'&& voucher.type ne '1' && voucher.type ne '5'}">readonly style="background: #f9f9f9" </c:if> value="${card.optAmount}"  class="layui-input" >
                        <input id="i_amount_backup" type="hidden" >
                    </div>
                </div>
                <div class="layui-form-item" style="width: 51.5%;">
                    <label class="layui-form-label">关联流水号&nbsp;<font color="red">*</font></label>
                    <div class="layui-input-block">
                        <input type="text"  lay-verify="person" id="i_flow" name="flowHead" autocomplete="off" readonly value="${card.flowHead}"  class="layui-input" style="width:76%;display: inline-block;background: #f9f9f9">
                        <a class="layui-btn"   id="btn_choseFlow"  style="margin-left: 20px" >选择流水</a>
                    </div>
                </div>
                <div class="layui-form-item" style="width: 41%;">
                    <label class="layui-form-label">备注&nbsp;</label>
                    <div class="layui-input-block">
                        <textarea placeholder="卡片备注信息" id="remark_area" name="remark" class="layui-textarea" >${card.remark}</textarea>
                    </div>
                </div>

                <c:if test="${filelist!= null || fn:length(filelist) != 0}">
                <div class="layui-form-item" style="width: 41%;">
                    <label class="layui-form-label">附件图片&nbsp;</label>
                    <div class="layui-input-block">
                        <c:forEach items="${filelist}" var="attch">
                            <img class="img_pre" src="${ctx}/upload/${attch.filepath}.${attch.extension}" at="${attch.filepath}_${attch.extension}" style="width: 60px;height: 60px;border: 1px solid black;float: left;margin-right: 5px;" >
                        </c:forEach>
                    </div>
                </div>
                </c:if>
                <div class="layui-form-item layui-layout-admin">
                    <div class="layui-input-block">
                        <div class="layui-footer" style="left: 0;">
                            <button class="layui-btn" lay-submit="" lay-filter="btn_submit" id="" style="margin-left: 40%; " >确定</button>
                            <button type="reset" id="btn_back"  class="layui-btn layui-btn-primary">返回</button>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
<script type="text/html">

</script>
<script>
    var tip_index=0
    var files = [];
    var submitURL = "${ctx}/api/v1/card/save";
    $("#btn_add").click(function(){
        window.location.href = "${ctx}/card/info"
    })
    $("body").on("click", ".img_pre", previewImg);
    $("body").on("click", "#btn_choseFlow", openFlows);
    $("body").on("click", "#btn_back", returnBack);

    function returnBack(){
        window.location.href = "${ctx}/voucher/cardlist/" + $("#kid").val();
    }
    var img = null
    function previewImg(e){
        if(img==null){
            img = new Image();
        }

        var at = $(e.currentTarget).attr("at");
        var path = at.split("_")[0];
        var ext = at.split("_")[1];
        var url = "${ctx}/upload/"+path+"."+ext;
        src = url;
        img.src = url;
        var imgHtml = "<img src='" + src + "' width='650px' height='650px'/>";
        //弹出层
        layer.open({
            type: 1,
            shade: 0,
            offset: 'auto',
            area: [650 + 'px',650+'px'],
            shadeClose:true,
            scrollbar: false,
            title: "图片预览", //不显示标题
            content: imgHtml, //捕获的元素，注意：最好该指定的元素要存放在body最外层，否则可能被其它的相对元素所影响
            cancel: function (layer,index) {
                //layer.msg('捕获就是从页面已经存在的元素上，包裹layer的结构', { time: 5000, icon: 6 });

            }
        });

    }
    var title = "";
    function reloadURL(){//根据开库类型不同跳转不同里列表
        var type = $("#v_type").val();
        switch(type)
        {
            /*case '0':
                return '${ctx}/consume/list/'+$("#faceCardNum").val();
                break;*/
            case '2'://加载充值流水

                title = "充值长款流水"
                return '${ctx}/recharge/list/'+$("#faceCardNum").val();
                break;

            default://加载消费流水
                title = "消费流水"
               return '${ctx}/consume/list/'+$("#faceCardNum").val()+'/'+$("#kid").val();
        }

    }
    var amt ;
    function setFlow(arr){//根据开库单类型来对金额进行操作
        var type = $("#v_type").val();

        switch (type){
            case "0"://车道流水丢失
                if(arr!=undefined&&arr.length==2){

                    $("#i_amount_backup").val(arr[0].cardBalance-arr[1].amount*100 - arr[1].cardBalance);
                    amt = arr[1].cardBalance-arr[0].amount - arr[0].cardBalance;
                    $("#i_amount").val(amt);
                    $("#i_flow").val(arr[0].listno + "_" + arr[1].listno);

                }else{
                    layer.alert("流水数据有误，请确认后重试！");
                    return;
                }
                break;

            case "1"://车道重复交易
                if(arr!=undefined&&arr.length==2){

                    $("#i_amount_backup").val(arr[0].amount);
                    $("#i_amount").val(arr[0].amount);
                    $("#i_flow").val(arr[0].listno + "_" + arr[1].listno);

                }else{
                    layer.alert("流水数据有误，请确认后重试！");
                    return;
                }
                break;
            case "2"://充值长款
                if(arr!=undefined&&arr.length==1){
                    $("#i_amount_backup").val(arr[0].tradeAmount);
                    $("#i_amount").val((arr[0].tradeAmount));
                    $("#i_flow").val(arr[0].listno);
                }else{
                    layer.alert("流水数据有误，请确认后重试！");
                    return;
                }
                break;
            //3为记账文件重发
            //4为跨行30天办理
            case "5"://车道扣费异常
                if(arr!=undefined&&arr.length==1){
                    $("#i_flow").val(arr[0].listno);//关联流水号
                }
                break;
            case "6"://充值错误

                break;


        }



       if(arr!=undefined&&arr.length==2){
           if($("#v_type").val()=="0"){
               $("#i_amount_backup").val(arr[0].amount);
           }else{
               $("#i_amount").val(arr[0].amount);
           }

           $("#i_flow").val(arr[0].listno + "_" + arr[1].listno);
       }else if(arr!=undefined&&arr.length==1){
           $("#i_amount_backup").val(arr[0].tradeAmount);
           $("#i_flow").val(arr[0].listno);
       }else{
           layer.alert("流水数据有误，请确认后重试！");
           return;
       }
    }

    function openFlows(index){
        if($("#faceCardNum").val()==""){
            alert("请先输入16位通衢卡号！");

        }else{
            var url = reloadURL();
            var win = layer.open({
                type: 2,
                title: title,
                shadeClose: true,
                shade: false,
                maxmin: true, //开启最大化最小化按钮
                area: ['900px', '500px'],
                content: url,

            });

            layer.full(win);
        }


    }

    layui.use(['form', 'layedit', 'laydate','upload'], function() {

        var form = layui.form,
            layer = layui.layer,
            layedit = layui.layedit,
            laydate = layui.laydate,
            upload = layui.upload;
        var cid = $("#cid").val();
        var vstate = $("#vstate").val();
        if(cid!=null&&vstate!="1"&&vstate!="5"){

            $("#faceCardNum").attr("readonly",true)
            $("#i_amount").attr("readonly",true)
            $("#btn_choseFlow").css('display','none');
            $("#remark_area").attr("readonly",true)
        }

        var type = $("#v_type").val();
        if('2'==type){
            $("#i_amount").attr("readOnly","readOnly");
        }

        form.on('submit(btn_submit)', function(data){

            if($("#i_amount").val()==null||$("#i_amount").val()==''||$("#faceCardNum").val()==null||$("#faceCardNum").val()==''){
                alert("必填项不能为空！");
                return;
            }

            if($("#pageType").val()=="view"){
                window.location.href = "${ctx}/voucher/cardlist";
                return ;
            }




            $.ajax({
                url:submitURL,
                data:data.field,
                type:"post",
                success:function(result){

                    if(result){

                        if(confirm('数据保存成功！')){ //只有当点击confirm框的确定时，
                            window.location.href = "${ctx}/voucher/cardlist/"+result.voucherid
                        }
                    }else{
                        layer.alert("数据保存失败！");
                        return ;
                    }

                },
                error:function(){
                    layer.alert("服务端错误！");
                    return;
                },
                before:function(){//金额验证
//连续性校核
                    if(amt!=$("#i_amount").val( ) ){
                        if(!confirm("新增流水记录不满足金额连续性，确认继续？")){
                            return;
                        }
                    }

                }
            })

            return false;
        });



        //日期
        laydate.render({
            elem: '#date',
           // value: new Date()
        });
        var date = new Date();
        date.setMonth(date.getMonth()+1);
        laydate.render({
            elem: '#dateline',
            // value: date
        });

        //自定义验证规则
        form.verify({
            title: function(value) {
                if(value.length < 1) {
                    return '开库单编号不能为空！';
                }
            },
            type: function(value) {
                if(value.length < 1) {
                    return '开库类型不能为空！';
                }
            },
            pass: [/(.+){6,12}$/, '密码必须6到12位'],
            content: function(value) {
                layedit.sync(editIndex);
            },
            person:function(value) {
                if(value.length < 1) {
                    return '提出人不能为空！';
                }
            },
            area:function(value) {
                if(value.length < 1) {
                    return '主题不能为空！';
                }
            },
            organization:function(value) {
                if(value.length < 1) {
                    return '机构不能为空！';
                }
            },
            amount:function(value){

                  /*  if(value*100!= $("#i_amount_backup").val()){
                        return '所填金额与流水金额不一致请确认!';
                    }*/
                }
        });

    });
</script>

</body>
</html>
