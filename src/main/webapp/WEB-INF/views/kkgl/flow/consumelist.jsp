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
        <li>流水列表</li>
    </ul>

<div class="layui-form-item">
    <input type="hidden" name="id" value="${voucherid}" id="kid">
    <input type="hidden" name="vtype" value="${vtype}" id="vtype">

    <input type="hidden" name="id" value="${faceCardNum}" id="faceCardNum">
    <div class="layui-inline" style="width: 100%;background-color: #f2f2f2;border-radius: 3px;height: 60px">
            <%--<div class="layui-inline s_inpt">
                <label class="layui-form-label "  style="line-height:12px;width: 100px">开库单编号</label>
                <div class="layui-input-inline tb_input ">
                    <input type="text" name="voucheridcode" id="e_voucherid" autocomplete="off" class="layui-input tb_input">
                </div>
            </div>--%>
            <c:if test="${'0' ne vtype}">
                <div class="layui-inline s_inpt">
                    <label class="layui-form-label " style="line-height:12px;width: 100px">${vtype}时间</label>
                    <div class="layui-input-inline tb_input">
                        <input type="text" name="createTime" id="createTime" class="layui-input tb_input" placeholder="请选择流水时间范围">
                    </div>
                </div>
            </c:if>
        <c:if test="${'0' eq vtype}">
            <div class="layui-inline s_inpt">
                <label class="layui-form-label " style="line-height:12px;width: 150px">上一笔交易时间</label>
                <div class="layui-input-inline tb_input">
                    <input type="text" name="tradeTime2" id="tradeTime2" class="layui-input tb_input" placeholder="请选择上行交易时间">
                </div>
            </div>

            <div class="layui-inline s_inpt">
                <label class="layui-form-label " style="line-height:12px;width: 150px">本次交易时间</label>
                <div class="layui-input-inline tb_input">
                    <input type="text" name="tradeTime" id="tradeTime" class="layui-input tb_input" placeholder="请选择下行交易时间">
                </div>
            </div>
        </c:if>


            <div style="margin:12px 26px 0px 0px;float: right">
                <button class="layui-btn" data-type="reload" id="layui-btn1">查询</button>
                <button class="layui-btn"  id="layui-btn_select">选择</button>
            </div>
        </div>



    </div>
    <table class="layui-hide" id="test">

    </table>

    <%--<script type="text/html" id="btnTpl">
        <button class="layui-btn layui-btn-sm">选择</button>
        <button class="layui-btn layui-btn-sm layui-btn-danger"><i class="layui-icon"></i></button>
    </script>--%>
    <script>
        var voucherid = 2;
        layui.use(['table','laydate'], function() {
            var table = layui.table;
            var laydate = layui.laydate;
            //执行一个laydate实例
            if($("#vtype").val()=="0"){
                laydate.render({
                    elem: '#createTime', //指定元素
                    range: true,
                    max:-30
                });
            }else{
                laydate.render({
                    elem: '#createTime', //指定元素
                    range: true
                });
            }
            laydate.render({
                elem: '#tradeTime', //指定元素
                range: false

            });
            laydate.render({
                elem: '#tradeTime2', //指定元素
                range: false

            });

            var tableIns = table.render({
                elem: '#test',
                height: 500,
                url:'${ctx}/api/v1/consume/query',
                id:'id',
                where: { faceCardNum: $("#faceCardNum").val(),
                          createTime: $("#createTime").val(),
                          tradeTime:$("#tradeTime").val(),
                          preBalance:$("#preBalance").val(),
                          tradeTime2:$("#tradeTime2").val(),
                          optAmount:$("#optAmount").val(),
                          nextBalance:$("#nextBalance").val()


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
                        field: 'faceCardNum',
                        title: '卡号',
                        align:'center',
                        sort: true
                    }, {
                        field: 'businessTime',
                        title: '时间',
                        align:'center',
                    }, {
                        field: 'amount',
                        title: '金额(元)',
                        align:'center'
                    },{
                        field: 'cardBalance',
                        title: '卡余额(元)',
                        width:100,
                        align:'center',
                    },{
                        field: 'vehplate',
                        title: '车牌号',
                        align:'center',
                    }/*, {
                        field: 'cardType',
                        title: '卡类型',
                        align:'center',
                    }*/, {
                        field: 'tradeType',
                        title: '交易类型',
                        align:'center',
                    }, {
                        field: 'highway',
                        title: '路段',
                        align:'center',
                    }, {
                        field: 'remark',
                        title: '备注',
                        align:'center'
                    }/*,{
                        field:'operate',
                        title:'操作',
                        width:160,
                        align:'center',
                        toolbar:'#btnTpl',
                        unresize: true}*/
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

                var checkStatus = table.checkStatus('id');
                //对重复流水进行校验
                if($("#vtype").val()=="1"){
                    if(checkStatus.data.length!=2){
                        layer.alert("请选择两条重复交易流水!");
                    }else{
                        try{
                            if(checkStatus.data[0].flowHead!=null||checkStatus.data[0].flowTail!=null||checkStatus.data[1].flowHead!=null||checkStatus.data[1].flowTail!=null){
                                alert("所选择流水已经做处理!相关流水为：");
                                return;
                            }
                            if(checkStatus.data[0].amount!=checkStatus.data[1].amount||checkStatus.data[0].vehplate!=checkStatus.data[1].vehplate||checkStatus.data[0].highway!=checkStatus.data[1].highway){
                                if(confirm("消费流水可能不为重复交易流水,确认继续？")){
                                    //下一步操作
                                    parent.setFlow(checkStatus.data);
                                    var index = parent.layer.getFrameIndex(window.name);
                                    parent.layer.close(index);//关闭当前页
                                }else{
                                    return;
                                }
                            }else{
                                //流水校验正确
                                parent.setFlow(checkStatus.data);
                            }
                        } catch(err){
                            layer.alert("流水校验错误!");
                        }
                    }
                }else if($("#vtype").val()=="0"){
                    if(checkStatus.data.length!=2){
                        layer.alert("请选择两条上下行流水!");
                    }else{
                        try{

                            if(checkStatus.data[0].flowHead!=null||checkStatus.data[0].flowTail!=null||checkStatus.data[1].flowHead!=null||checkStatus.data[1].flowTail!=null){
                                alert("所选择流水已经做处理!开库单为：" +checkStatus.data[0].flowHead);
                                return;
                            }
                            if(confirm("确认绑定此两条流水？")){
                                //下一步操作
                                parent.setFlow(checkStatus.data);
                                var index = parent.layer.getFrameIndex(window.name);
                                parent.layer.close(index);//关闭当前页
                            }else{
                                return;
                            }
                        } catch(err){
                            layer.alert("流水绑定错误!");
                        }
                    }
                }else if($("#vtype").val()=="5"){//收费异常
                        if(checkStatus.data.length!=1){
                            layer.alert("请选择一条流水!");
                        }else{
                            try{
                                if(checkStatus.data[0].flowHead!=null&&checkStatus.data[0].flowHead!=undefined){
                                    alert("此条流水已做相关处理，开库单号为:" + checkStatus.data[0].flowHead);
                                }else{
                                    if(confirm("确定绑定此条流水?")){
                                        parent.setFlow(checkStatus.data);
                                        var index = parent.layer.getFrameIndex(window.name);
                                        parent.layer.close(index);//关闭当前页
                                    }else{
                                        return;
                                    }
                                }

                            }catch (err){
                                layer.alert("流水绑定错误!");
                            }
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
                            createTime:$("#createTime").val(),
                            tradeTime1:$("#tradeTime").val(),
                            tradeTime2:$("#tradeTime2").val()
                        }
                    });
                }
            };




        });
    </script>
    <script type="text/html" id="indexTpl">
        {{d.LAY_TABLE_INDEX+1}}
    </script>

</div>
</body>
</html>
