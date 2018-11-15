<%--
  Created by IntelliJ IDEA.
  User: wqp
  Date: 2018-7-29
  Time: 9:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/common/taglibs.jsp" %>
<%@ include file="/common/meta.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <%--<%@ include file="/common/meta.jsp" %>--%>
    <%--<script type="text/javascript" src="${ctx}/js/dev/rbac/user/list.js"></script>--%>
    <link rel="stylesheet" href="${ctx}/js/lib/layui/css/layui.css">
    <script type="text/javascript" src="${ctx}/js/lib/layui/layui.js"></script>
    <link type="text/css" rel="stylesheet" href="${ctx}/css/theme/dandelion2/main.css" charset="utf-8"/>
    <script type="text/javascript" src="${ctx}/js/lib/layui/layui.js"></script>
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
    </style>
</head>
<body>
    <ul class="breadcrumb">
        <li class="icon"></li>
        <li>您所在的位置：</li>
        <li>开库单管理</li>
        <li>充值列表</li>
    </ul>

<div class="layui-form-item">
    <input type="hidden" name="id" value="${voucherid}" id="kid">
    <div class="layui-inline" style="width: 100%;background-color: #f2f2f2;border-radius: 3px;height: 60px">
            <div class="layui-inline s_inpt">

                <input type="hidden" name="id" value="${faceCardNum}" id="faceCardNum">
                <%--<label class="layui-form-label "  style="line-height:12px;width: 100px">开库单编号</label>
                <div class="layui-input-inline tb_input ">
                    <input type="text" name="voucheridcode" id="e_voucherid" autocomplete="off" class="layui-input tb_input">
                </div>--%>
            </div>
            <div class="layui-inline s_inpt">
                <label class="layui-form-label " style="line-height:12px;width: 100px">时间</label>
                <div class="layui-input-inline tb_input">
                    <input type="text" name="createTime" id="createTime" style="width: 200px;" class="layui-input tb_input" placeholder="" >
                </div>
            </div>

            <div style="margin:12px 26px 0px 0px;float: right">
                <button class="layui-btn" data-type="reload" id="layui-btn1">查询</button>
                <button class="layui-btn"  id="layui-btn_select">选择</button>
            </div>
        </div>



    </div>
    <table class="layui-hide" id="test">

    </table>

    <script type="text/html" id="btnTpl">
        <button class="layui-btn layui-btn-sm">查看</button>

        <button class="layui-btn layui-btn-sm layui-btn-danger"><i class="layui-icon"></i></button>
    </script>
    <script>
        var voucherid = 2;
        layui.use(['table','laydate'], function() {
            var table = layui.table;
            var laydate = layui.laydate;
            var curDate = new Date();
            var e = curDate.getFullYear()+'-'+curDate.getMonth()+'-'+curDate.getDate();
            curDate.setMonth( curDate.getMonth()-3 );
            var s = curDate.getFullYear()+'-'+curDate.getMonth()+'-'+curDate.getDate();
            //执行一个laydate实例
            laydate.render({
                elem: '#createTime', //指定元素
                range: true,
                value: s + ' - ' +e
            });


            
            var tableIns = table.render({
                elem: '#test',
                height: 500,
                url:'${ctx}/api/v1/recharge/query',
                id:'listno',
                where: {faceCardNum: $("#faceCardNum").val(),
                        createTime: $("#createTime").val()
                },
                cols: [
                    [ //标题栏
                        {
                            checkbox: true,
                            LAY_CHECKED: false
                        }, //默认全选
                        {title: '序号',
                            templet: '#indexTpl',
                            width:80,
                            align:'center'
                        }
                        , {
                        field: 'orgid',
                        title: '机构',
                        align:'center',
                        templet:'#orgcolw',
                        sort: true
                    }, {
                        field: 'etcAccountNum',
                        title: '号码',
                        align:'center',
                    },{
                        field: 'accountType',
                        title: '充值类型',
                        align:'center',
                        templet:'#typeclow'
                    }, {
                        field: 'tradeAmount',
                        title: '金额',
                        align:'center',
                    },{
                        field: 'tradeTime',
                        title: '交易时间',
                        align:'center',
                    }
                    ]
                ],
                skin: 'row' //表格风格
                ,
                even: true,
                page: true //是否显示分页
                ,
                limits: [5, 7, 10, 20],
                limit: 7 //每页默认显示的数量
            });
            $("#layui-btn1").on("click", function () {

                var type = $(this).data("type");
                active[type] ? active[type].call(this) : "";
            });
            $("#layui-btn2").on("click", function () {

               window.location.href = "${ctx}/card/add/"+$("#kid").val();
            });
            $("#layui-btn_select").on("click", function () {
                
                var checkStatus = table.checkStatus('listno');
                //对重复流水进行校验
                if(checkStatus.data.length!=1){
                    layer.alert("请选择一条长款流水!");
                }else{
                    try{
                        
                        if(checkStatus.data[0].voucherid!=null&&checkStatus.data[0].voucherid!=0){
                            alert("该条流水已做处理，相关开库单号:"+checkStatus.data[0].voucherid);
                            return ;
                        }
                        if(checkStatus.data[0].tradeAmount!=undefined){
                            if(checkStatus.data[0].voucherid==null||checkStatus.data[0].voucherid==0){
                                //下一步操作
                                parent.setFlow(checkStatus.data);
                                var index = parent.layer.getFrameIndex(window.name);
                                parent.layer.close(index);//关闭当前页
                            }else{
                                layer.alert("该条流水已做处理，相关开库单号:"+checkStatus.data[0].voucherid);
                            }

                        }else{
                            layer.confirm("绑定流水失败！");
                            return;
                        }
                    } catch(err){
                        layer.alert("流水校验错误!");
                    }
                }
            });
            var active = {
                reload: function () {
                    //执行重载
                    tableIns.reload({
                        page: {
                            curr: 1 //重新从第 1 页开始
                        }
                        , where: {//绑定查询条件
                            faceCardNum: $("#faceCardNum").val(),
                            createTime:$("#createTime").val()
                        }
                    });
                }
            };
        });
    </script>
    <script type="text/html" id="typeclow">
        {{# if(d.accountType==0){ }}
        卡账充值
        {{#}else if(d.accountType==1){}}
        子账充值
        {{# }}}
    </script>
    <script type="text/html" id="orgcolw">
        {{# if(d.orgid==102){ }}
        工商银行
        {{#}else if(d.orgid==103){}}
        农业银行
        {{#}else if(d.orgid==104){}}
        建设银行
        {{#}else if(d.orgid==105){}}
        建设银行
        {{#}else if(d.orgid==402){}}
        农商银行
        {{#}else if(d.orgid==100){}}
        邮储
        {{# }}}
    </script>
    <script type="text/html" id="indexTpl">
        {{d.LAY_TABLE_INDEX+1}}
    </script>

</div>
</body>
</html>
