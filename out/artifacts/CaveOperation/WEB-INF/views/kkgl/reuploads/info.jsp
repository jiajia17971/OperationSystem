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
        <li>重发项信息</li>
    </ul>
<div class="layui-fluid" >
    <div class="layui-card" style="background: #f9f9f9">
        <div class="layui-card-header" id="btn_add" style="border-bottom:none">添加重发项</div>
        <hr>

        <div class="layui-card-body" style="padding: 15px;">
            <input type="hidden" value="${type}" id="pageType">
            <form class="layui-form" action=""  id="info_form" >
                <input type="hidden" name="voucherid" value="${voucherid}" id="kid">
                <input type="hidden" name="id" value="${reload.id}" id="rid">
                <div class="layui-form-item ">
                    <label class="layui-form-label">批次号&nbsp;<font color="red">*</font></label>
                    <div class="layui-input-inline" style="width: 80%">
                        <input type="text" name="batchNo" id="bathNo" lay-verify="title" autocomplete="off" placeholder="请输入需要重发的批次号"  class="layui-input" style="width: 42%;float: left" <c:if test="${'view' eq type}">disabled</c:if> value="${reload.batchNo}">
                        <a class="layui-btn"   id="btn_search"   style="margin-left: 20px" >查询</a>
                    </div>
                </div>
                <div class="layui-form-item" style="width: 41%;">
                    <label class="layui-form-label">机构&nbsp;<font color="red">*</font></label>
                    <div class="layui-input-block">
                        <select name="orgid" lay-filter=""  id="orgid" lay-verify="required" value="${reload.orgid}" <c:if test="${'view' eq type}">disabled</c:if>>
                            <option value="" selected="">全部</option>
                            <option value="102" <c:if test="${'102' eq reload.orgid}">selected</c:if>>工商银行</option>
                            <option value="103" <c:if test="${'103' eq reload.orgid}">selected</c:if>>农业银行</option>
                            <option value="104" <c:if test="${'104' eq reload.orgid}">selected</c:if>>中国银行</option>
                            <option value="105" <c:if test="${'105' eq reload.orgid}">selected</c:if>>建设银行</option>
                            <option value="402" <c:if test="${'402' eq reload.orgid}">selected</c:if>>农商</option>
                            <option value="100" <c:if test="${'100' eq reload.orgid}">selected</c:if>>邮储</option>
                        </select>
                    </div>
                </div>
                <div class="layui-form-item" style="width: 51.5%;">
                    <label class="layui-form-label">总数&nbsp;<font color="red">*</font></label>
                    <div class="layui-input-block">
                        <input type="text"  lay-verify="" id="EExtiNum" name="EExtiNum" autocomplete="off" value="${reload.EExtiNum}"  readonly class="layui-input" style="width:76%;display: inline-block;background: #f9f9f9">
                    </div>
                </div>
                <div class="layui-form-item" style="width: 51.5%;">
                    <label class="layui-form-label">金额(元)&nbsp;<font color="red">*</font></label>
                    <div class="layui-input-block">
                        <input type="text"  lay-verify="" id="EExtiMoney" name="EExtiMoney" autocomplete="off" value="${reload.EExtiMoney}" readonly class="layui-input" style="width:76%;display: inline-block;background: #f9f9f9">
                    </div>
                </div>


                <div class="layui-form-item" style="width: 41%;">
                <label class="layui-form-label">备注&nbsp;</label>
                <div class="layui-input-block">
                    <textarea placeholder="卡片备注信息"  name="remark" lay-verify="" class="layui-textarea" >${reload.remark}</textarea>
                </div>
                </div>
                <c:if test="${filelist!= null || fn:length(filelist) != 0}">
                <div class="layui-form-item" style="width: 41%;">
                    <label class="layui-form-label">附件图片&nbsp;<font color="red">*</font></label>
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
    var submitURL = "${ctx}/api/v1/reload/save";
    $("#btn_add").click(function(){
        window.location.href = "${ctx}/card/info"
    })
    $("body").on("click", ".img_pre", previewImg);
    $("body").on("click", "#btn_choseFlow", openFlows);
    $("body").on("click", "#btn_back", returnBack);
    $("body").on("click", "#btn_search", searchBatch);

    function searchBatch(){
       
        var bathNo = $("#bathNo").val();
        var orgid = $("#orgid").val();
        if(bathNo==""||bathNo==null ){
            layer.alert("请输入重发批次号！");
            return;
        }else{
            $.ajax({
                url:"${ctx}/api/v1/reload/search",
                data:{
                    "batchNo":bathNo,
                     "orgid":orgid
                },
                type:"POST",
                success:function(result){

                    if(result){
                        $("#EExtiNum").val(result.eextiNum);
                        $("#EExtiMoney").val(result.eextiMoney);
                    }else{
                        layer.alert("未查询到批次号！");
                        return ;
                    }

                },
                error:function(){
                    layer.alert("服务端错误！");
                    return;
                }
            })
        }


    }

    function returnBack(){
        window.location.href = "${ctx}/voucher/reuploads/" + $("#kid").val();
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
            cancel: function () {
                //layer.msg('捕获就是从页面已经存在的元素上，包裹layer的结构', { time: 5000, icon: 6 });
            }
        });
    }

    function openFlows(){
        layer.open({
            type: 1,
            title: '消费流水',
            shadeClose: true,
            shade: false,
            maxmin: true, //开启最大化最小化按钮
            area: ['893px', '600px'],
            content: '${ctx}/consume/list'
        });
    }

    layui.use(['form', 'layedit', 'laydate','upload'], function() {

        var form = layui.form,
            layer = layui.layer,
            layedit = layui.layedit,
            laydate = layui.laydate,
            upload = layui.upload;

        form.on('submit(btn_submit)', function(data){


                <%--window.location.href = "${ctx}/voucher/cardlist";--%>
                <%--return ;--%>


            $.ajax({
                url:submitURL,
                data:data.field,
                type:"post",
                success:function(result){

                    if(result){

                        if(confirm('数据保存成功！')){ //只有当点击confirm框的确定时，
                            window.location.href = "${ctx}/voucher/reuploads/"+$("#kid").val();
                        }
                    }else{
                        layer.alert("数据保存失败！");
                        return ;
                    }

                },
                error:function(){
                    layer.alert("服务端错误！");
                    return;
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
            }
        });

    });
</script>

</body>
</html>
