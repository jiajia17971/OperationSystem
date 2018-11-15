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
    <%--<%@ include file="/common/meta.jsp" %>--%>
    <%--<script type="text/javascript" src="${ctx}/js/dev/rbac/user/list.js"></script>--%>
    <link rel="stylesheet" href="${ctx}/js/lib/layui/css/layui.css">
    <%--<link type="text/css" rel="stylesheet" href="${ctx}/css/theme/dandelion2/main.css" charset="utf-8"/>--%>
    <script type="text/javascript" src="${ctx}/js/lib/layui/layui.js"></script>
    <script type="text/javascript" src="${ctx}/js/lib/jquery-1.11.1.min.js"></script>
    <style>
        body{
            overflow-x:auto ;
            overflow-y:hidden;
        }
        .tb_input{
            height: 30px;
        }
        .s_inpt{
            margin-top: 14px;

        }
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
    <li>开库单管理</li>
    <li>跨行30天办理</li>

</ul>

<div class="layui-form-item">
    <div class="layui-inline" style="width: 100%;background-color: #f2f2f2;border-radius: 3px;height: 60px">
        <div class="layui-inline s_inpt">
            <label class="layui-form-label "  style="line-height:12px;width: 100px">通衢卡卡号</label>
            <div class="layui-input-inline tb_input ">
                <input type="text" name="faceCardNum" id="faceCardNum" lay-verify="title" autocomplete="off" class="layui-input tb_input">
            </div>
        </div>

        <div class="layui-form-item layui-layout-admin" style="display: inline">
            <div style="margin:12px 26px 0px 0px;float: right">
                <button class="layui-btn" lay-submit="" data-type="reload" id="layui-btn1">查询</button>
                <button class="layui-btn btn_handle">办理</button>
                <button class="layui-btn" id="laybtn-clean">清空</button>
            </div>
        </div>
    </div>



</div>

<table class="layui-hide" id="test">

</table>
<script>
    layui.use(['form','laydate'], function() {
        var laydate = layui.laydate,
       form = layui.form;
        //执行一个laydate实例
        laydate.render({
            elem: '#test1', //指定元素
            range: true
        });
        form.verify({
            title: function(value) {
                if(value.length < 1) {
                    return '卡号不能为空！';
                }
            }
        });


    });
</script>
<script>

    var cardNum = "";
    $(".btn_handle").click(function () {

        $.ajax({
            type:"get",
            url:"${ctx}/api/v1/voucher/handleTime",
            data:{faceCardNum:cardNum},
            success:function(data){

                if(data){
                    if(confirm("修改成功！")){
                        window.location.href="${ctx}/voucher/day30handle";
                    }


                }else{
                    layer.alert("修改失败！");
                }
            }
        })
    });
    layui.use('table', function() {
        var  faceCardNum = $("#faceCardNum").val();
        var table = layui.table;

        var tableIns = table.render({
            elem: '#test',
            url:'${ctx}/api/v1/voucher/dayshandle/' +faceCardNum,
            height: 500,
            id:"ID",
            cols: [
                [ //标题栏
                    {
                    field: 'faceCardNum',
                    title: '卡号',
                    align:'center',
                    sort: true
                }, {
                    field: 'vehiclePlate',
                    title: '车牌号码',
                    align:'center',
                },{
                    field: 'vehicleColor',
                    title: '车牌颜色',
                    align:'center',
                    templet:"#colorclow"
                }, {
                    field: 'lastModifyTime',
                    title: '注销时间',
                    align:'center',
                },{
                    field: 'state',
                    title: '状态',
                    align:'center',
                    templet:"#stateclow"
                }
                ]
            ],
            skin: 'row' //表格风格
            ,
            even: true,
            page: false //是否显示分页
            ,
            limits: [5, 7, 10, 20],
            limit: 7 //每页默认显示的数量
        });

        $("#layui-btn1").on("click", function () {

            var type = $(this).data("type");
            active[type] ? active[type].call(this) : "";
        });
        $("#laybtn-clean").on("click", function () {
            $("#faceCardNum").val("");
        });
        var active = {
            reload: function () {
                cardNum = $("#faceCardNum").val();
                //执行重载
                tableIns.reload({
                    page: {
                        curr: 1 //重新从第 1 页开始
                    }
                    , where: {//绑定查询条件
                        faceCardNum: $("#faceCardNum").val()
                    }
                });
            }
        };
    });
</script>
<script type="text/html" id="colorclow">
    {{# if(d.vehicleColor==0){ }}
    蓝色
    {{#}else if(d.vehicleColor==1){}}
    黄色
    {{# }}}
</script>
<script type="text/html" id="stateclow">
    {{# if(d.state==2){ }}
    注销卡
    {{#}else if(d.state!=2){}}
    非注销卡
    {{# }}}
</script>
</div>
</body>
</html>
