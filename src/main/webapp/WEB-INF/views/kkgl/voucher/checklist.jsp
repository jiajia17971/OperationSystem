<%--
  Created by IntelliJ IDEA.
  User: wqp
  Date: 2018-7-29
  Time: 9:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/common/taglibs.jsp" %>

<!DOCTYPE html>
<html>
<head>
    <%--<%@ include file="/common/meta.jsp" %>--%>
    <%--<script type="text/javascript" src="${ctx}/js/dev/rbac/user/list.js"></script>--%>
    <link rel="stylesheet" href="${ctx}/js/lib/layui/css/layui.css">
    <script type="text/javascript" src="${ctx}/js/lib/layui/layui.js"></script>
    <script type="text/javascript" src="${ctx}/js/lib/jquery-1.11.1.min.js"></script>
    <style>
        body{
            overflow-x:auto ;
            overflow-y:auto;
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
<body style="overflow-x: hidden">
    <ul class="breadcrumb">
        <li class="icon"></li>
        <li>您所在的位置：</li>
        <li>开库管理</li>
        <li>开库单管理111</li>
    </ul>

<div class="layui-form-item">
    <div class="layui-inline" style="width: 100%;background-color: #f2f2f2;border-radius: 3px;height: 60px">
            <div class="layui-inline s_inpt">
                <label class="layui-form-label "  style="line-height:12px;">开库单编号</label>
                <div class="layui-input-inline tb_input ">
                    <input type="text" name="voucherid" id="searchid" autocomplete="off" class="layui-input tb_input">
                </div>
            </div>

            <div class="layui-inline s_inpt">
                <label class="layui-form-label"  style="line-height:12px;">开库类型</label>

                <div class="layui-input-inline tb_input" >
                    <select name="type" class="tb_input" lay-verify="required" id="searchtype" style="height: 30px;border-radius: 3px;border-color: #e5e5e5;">
                        <option value="9">全部</option>
                        <option value="0">车道流水丢失处理</option>
                        <option value="1">车道重复交易</option>
                        <option value="2">银行充值长款</option>
                        <option value="3">银行记账划账文件处理</option>
                        <option value="4">跨行30天办理</option>

                    </select>
                </div>


            </div>

            <div class="layui-inline s_inpt">
                <label class="layui-form-label " style="line-height:12px;">提出时间</label>
                <div class="layui-input-inline tb_input">
                 <input type="text" name="creTime" id="searchcretime" class="layui-input tb_input" >
                </div>
            </div>
            <div class="layui-inline s_inpt">
                <label class="layui-form-label "  style="line-height:12px;">状态</label>
                <div class="layui-input-inline tb_input ">
                    <c:set var="iscontain" value="false" />
                    <c:forEach items="${user.user.roles}" var="map">
                        <c:if test="${map.id eq 'checkrole'}">
                            <c:set var="iscontain" value="true" />
                        </c:if>
                    </c:forEach>
                    <select name="status" class="tb_input" lay-verify="required" id="status" style="height: 30px;border-radius: 3px;border-color: #e5e5e5;">
                        <c:if test="${iscontain}">
                            <option value="9">全部</option>
                            <option value="3">待通过</option>
                            <option value="4">已通过</option>
                            <option value="5">已驳回</option>
                        </c:if>
                        <c:if test="${!iscontain}">
                            <option value="">全部</option>
                            <option value="0">注销</option>
                            <option value="1">待发送</option>
                            <option value="2">已执行</option>
                            <option value="3">待通过</option>
                            <option value="4">已通过</option>
                            <option value="5">已驳回</option>
                        </c:if>

                    </select>
                </div>
            </div>

            <div style="margin:12px 26px 0px 0px;float: right">
                <button class="layui-btn" data-type="reload" id="layui-btn1">查询</button>
                <button class="layui-btn" id="layui-clean">清空</button>
            </div>
        </div>



    </div>
    <table class="layui-hide" id="test"  lay-filter="tb_filter">

    </table>
    <script>
        layui.use('laydate', function() {
            var laydate = layui.laydate;

            //执行一个laydate实例
            laydate.render({
                elem: '#searchcretime', //指定元素
                range: true
            });
        });
    </script>
    <script type="text/html" id="btnTpl">
        {{# if(d.status=="待发送"){ }}
            <a class="layui-btn layui-btn-xs" lay-event="detail" target="_self">查看</a>
            <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
            <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">注销</a>
        {{# } else if(d.status=="已驳回"&&d.usertype!="1"){}}
        <a class="layui-btn layui-btn-xs" lay-event="detail" target="_self">查看</a>
        <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
        <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">注销</a>
        {{# } else if(d.status=="已驳回"&&d.usertype=="1"){}}
        <a class="layui-btn layui-btn-xs" lay-event="detail" target="_self">查看</a>
        {{# } else if(d.status=="已执行"){}}
             <a class="layui-btn layui-btn-xs" lay-event="detail" target="_self">查看</a>
        {{# } else if(d.status=="待通过"){}}
             <a class="layui-btn layui-btn-xs" lay-event="detail" target="_self">查看</a>
        {{# } else if(d.status=="已通过"){}}
             <a class="layui-btn layui-btn-xs" lay-event="detail" target="_self">查看</a>
        {{# } else if (d.status=="注销"){}}
             <a class="layui-btn layui-btn-xs" lay-event="detail" target="_self">查看</a>
            <%-- <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">注销</a>--%>
        {{# } }}

    </script>
    <script>

        layui.use(['table','laydate'], function() {
            var table = layui.table,
            laydate = layui.laydate;

            var tableIns = table.render({
                elem: '#test',
                url:'${ctx}/api/v1/voucher/query',
                method : "post",
                id:"kid",
                where:{
                    type:9,//默认查询全部
                    status:$("#status").val()
                },
                cols: [
                    [ //标题栏
                        /*{
                            checkbox: true,
                            LAY_CHECKED: true
                        } //默认全选
                        ,*/{type:'numbers',
                            title: '序号',
                        templet: '#indexTpl',
                        width:80,
                        align:'center'
                    },{
                        field: 'voucherid',
                        title: '开库单编号',
                        align:'center',
                        sort: true
                    }, {
                        field: 'type',
                        title: '开库类型',
                        align:'center',
                        templet:'#typeclow'
                    }, {
                        field: 'orgnization',
                        title: '提出单位',
                        align:'center',
                        templet:'#orgclow'
                    },{
                        field: 'owner',
                        title: '提出人',
                        align:'center',
                    }, {
                        field: 'creTime',
                        title: '提出时间',
                        align:'center',
                        sort: true
                    },{
                        field: 'processTime',
                        title: '办结时间',
                        align:'center',
                        sort: true
                    }/*,{
                        field: 'endTime',
                        title: '截止时间',
                        align:'center',
                        sort: true
                    }*/,{
                        field: 'theme',
                        title: '主题',
                        align:'center'
                    }, {
                        field: 'status',
                        title: '状态',
                        align:'center',
                        sort: true
                    },{
                        field:'operate',
                        title:'操作',
                        width:160,
                        align:'center',
                        toolbar:'#btnTpl',
                        unresize: true}
                    ]
                ],
                skin: 'row' //表格风格
                ,
                even: true,
                page: true //是否显示分页
                ,
                limits: [5, 7, 10, 20],
                limit: 7 ,//每页默认显示的数量

            });

            $("#layui-btn1").on("click", function () {

                var type = $(this).data("type");
                active[type] ? active[type].call(this) : "";
            });
            $("#layui-clean").on("click", function () {

               $("#searchid").val("");
               $("#searchtype").val(9);
               $("#searchcretime").val("");
               $("#status").val("");
            });
            var active = {
                reload: function () {
                    //执行重载
                    tableIns.reload({
                        page: {
                            curr: 1 //重新从第 1 页开始
                        }
                        , where: {//绑定查询条件
                            voucherid: $("#searchid").val(),
                            type:$("#searchtype").val(),
                            creTime:$("#searchcretime").val(),
                            status:$("#status").val()
                        }
                    });
                }
            };
        });


    </script>

    <script type="text/html" id="orgclow">
        {{# if(d.orgnization==0){ }}
        联网中心
        {{#}else if(d.orgnization==1){}}
        工商银行
        {{#}else if(d.orgnization==2){}}
        农业银行
        {{#}else if(d.orgnization==3){}}
        中国银行
        {{#}else if(d.orgnization==4){}}
        建设银行
        {{#}else if(d.orgnization==6){}}
        邮储
        {{#}else if(d.orgnization==5){}}
        农商
        {{# }}}
    </script>

    <script type="text/html" id="typeclow">
        {{# if(d.type==0){ }}
        车道流水丢失处理
        {{#}else if(d.type==1){}}
        车道重复交易
        {{#}else if(d.type==2){}}
        银行充值长款
        {{#}else if(d.type==3){}}
        银行记账划账文件处理
        {{#}else if(d.type==4){}}
        跨行30天办理
        {{#}else if(d.type==5){}}
        收费异常
        {{# }}}
    </script>
    <script type="text/html" id="indexTpl">
        {{d.LAY_TABLE_INDEX+1}}
    </script>

    <script>
        layui.use('table', function() {
            var table = layui.table;
            table.on('tool(tb_filter)', function(obj){

                var data = obj.data;
                var layEvent = obj.event;
                var tr = obj.tr;
                var id = data.id;
                if(layEvent === 'detail'){
                    //发送请求

                    location.href = '${ctx}/voucher/completeInfo/' + id;

                } else if(layEvent === 'edit'){
                    //do something
                    location.href = '${ctx}/voucher/edit/' + id;
                }else if(layEvent === 'del'){
                    layer.confirm('确定注销当前行吗', function(index){
                        $.ajax({
                            url:"${ctx}/api/v1/voucher/delete/"+ id,

                            type:"get",
                            success:function(result){

                                if(result==true){
                                    layer.alert("已注销！");
                                    // obj.del(); //删除对应行（tr）的DOM结构，并更新缓存
                                    obj.update();
                                    layer.close(index);
                                }else{
                                    layer.alert("数据修改失败！");
                                    return;
                                }

                            },
                            error:function(){
                                layer.alert("服务端错误！");
                                return;
                            }
                        })
                    });


                }
            });

        });


    </script>
</div>
</body>
</html>
