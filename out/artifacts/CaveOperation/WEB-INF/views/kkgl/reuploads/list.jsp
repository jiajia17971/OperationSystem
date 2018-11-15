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
        <li>开库管理</li>
        <li>重传列表</li>
    </ul>

<div class="layui-form-item">
    <input type="hidden" name="id" value="${voucherid}" id="kid">
    <div class="layui-inline" style="width: 100%;background-color: #f2f2f2;border-radius: 3px;height: 60px">
        <div class="layui-inline s_inpt">
            <label class="layui-form-label "  style="line-height:12px;width: 400px">当前开库单：${voucher.voucherid}</label>
        </div>
            <div class="layui-inline s_inpt">
                <label class="layui-form-label "  style="line-height:12px;width: 100px">批次号</label>
                <div class="layui-input-inline tb_input ">
                    <input type="text" name="batchNo" id="batchNo" autocomplete="off" class="layui-input tb_input">
                </div>
            </div>

            <div style="margin:12px 26px 0px 0px;float: right">

                <c:set var="iscontain" value="false" />
                <c:forEach items="${user.user.roles}" var="map">
                    <%--<button class="layui-btn"  id="layui-btn2">1</button>--%>
                    <c:if test="${map.id eq '0b183e7d-9777-4d19-b70c-6f192ef393e2'}">
                        <c:set var="iscontain" value="true" />
                        <%--<button class="layui-btn"  id="layui-btn2">${map.id}</button>--%>
                    </c:if>
                </c:forEach>
                <button class="layui-btn" data-type="reload" id="layui-btn1">查询</button>
                <c:if test="${voucher.status==1&&!iscontain}">
                <button class="layui-btn"  id="layui-btn2">添加重发项</button>
                </c:if>
                <c:if test="${voucher.status==3&&iscontain}">
                    <button class="layui-btn"  id="layui-pass">通过</button>
                </c:if>
                <c:if test="${voucher.status==4&&!iscontain}">
                <button class="layui-btn"  id="layui-btn_action">执行</button>
                </c:if>
                <c:if test="${voucher.status==5||voucher.status==1&&!iscontain}">
                    <button class="layui-btn"  id="layui-btn_check">发送审核</button>
                </c:if>
            </div>
        </div>



    </div>
    <table class="layui-hide" id="test" lay-filter="tb_filter">

    </table>

    <script type="text/html" id="btnTpl">
        {{# if(d.status==0&&d.vstate==1 ){ }}
        <a class="layui-btn layui-btn-sm" lay-event="edit">{{#d.status}}修改</a>
        <%--<button class="layui-btn layui-btn-sm" lay-event="update">查看</button>--%>
        <a class="layui-btn layui-btn-sm layui-btn-danger" lay-event="del"><i class="layui-icon"></i>删除</a>
        {{#}else if(d.status==1||d.vstate==2){}}
        <a class="layui-btn layui-btn-sm" lay-event="detail">查看</a>
        {{#}else if(d.status==5||d.vstate==2){}}
        <a class="layui-btn layui-btn-sm" lay-event="detail">查看</a>
        {{# }}}

    </script>
    <script>
        layui.use(['table','laydate'], function() {
            var table = layui.table;
            var laydate = layui.laydate;

            //执行一个laydate实例
            laydate.render({
                elem: '#test1', //指定元素
                range: true
            });
            var tableIns = table.render({
                elem: '#test',
                height: 500,
                url:'${ctx}/api/v1/reload/query',
                where: { voucherid: $("#kid").val() },
                cols: [
                    [ //标题栏
                        {
                            checkbox: true,
                            LAY_CHECKED: true
                        }, //默认全选
                        {type:'numbers',
                            title: '序号',
                            templet: '#indexTpl',
                            width:80,
                            align:'center'
                        }
                        , {
                        field: 'orgid',
                        title: '机构',
                        align:'center',
                        sort: true,
                        templet:'#orgclow'
                    }, {
                        field: 'batchNo',
                        title: '批次号',
                        align:'center',
                    },{
                        field: 'eextiNum',
                        title: '数量',
                        align:'center',
                    },{
                        field: 'eextiMoney',
                        title: '金额(元)',
                        align:'center',
                    }, {
                        field: 'status',
                        title: '状态',
                        align:'center',
                        templet:'#stateclow'
                    }, {
                        field: 'remark',
                        title: '备注',
                        align:'center'
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
                limit: 7 //每页默认显示的数量
            });
            $("#layui-btn1").on("click", function () {

                var type = $(this).data("type");
                active[type] ? active[type].call(this) : "";
            });
            $("#layui-btn2").on("click", function () {

               window.location.href = "${ctx}/reload/add/"+$("#kid").val();
            });
            $("#layui-btn_check").on("click", function () {
                if(confirm("确认发送审核开库单？")){

                    $.ajax({
                        url:"${ctx}/api/v1/card/sendcheck/"+ $("#kid").val(),

                        type:"get",
                        success:function(result){

                            if(result.response==true){
                                if(confirm("发送成功！")){
                                    window.location.href = "${ctx}/voucher/reuploads/"+$("#kid").val();
                                }

                            }else{
                                layer.alert("开库单操作失败，请联系管理员！");
                                return;
                            }

                        },
                        error:function(){
                            layer.close(layer.index);
                            layer.alert("服务端错误！");
                            return;
                        }
                    })
                }else{
                    return;
                }

            });
            $("#layui-btn_action").on("click", function () {

                if(confirm("确认开始执行本开库单？")){
                    $.ajax({
                        url:"${ctx}/api/v1/reload/toRestrain/"+ $("#kid").val(),
                        type:"get",
                        success:function(result){

                            if(result.response==true){
                                if(confirm(("已执行开库单！\n操作:"+result.totalNum+" 条;\n成功:"+result.successNum+"条；\n失败:"+result.failedNum+"条；"))){
                                    window.location.href = "${ctx}/voucher/cardlist/"+$("#kid").val();
                                }
                            }else{
                                layer.alert("开库单执行失败，请联系管理员！");
                                return;
                            }

                        },
                        error:function(){
                            layer.alert("服务端错误！");
                            return;
                        }
                    })
                }else{
                    return;
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
                            batchNo: $("#batchNo").val(),
                            voucherid: $("#kid").val()
                        }
                    });
                }
            };
        });
    </script>
    <script type="text/html" id="orgclow">
        {{# if(d.orgid==102){ }}
        工商银行
        {{#}else if(d.orgid==103){}}
        农业银行
        {{#}else if(d.orgid==104){}}
        建设银行
        {{#}else if(d.orgid==105){}}
        中国银行
        {{#}else if(d.orgid==106){}}
        邮储
        {{#}else if(d.orgid==107){}}
        农商
        {{# }}}
    </script>
    <script type="text/html" id="stateclow">
        {{# if(d.status==0){ }}
        未执行
        {{#}else if(d.status==1){}}
        执行成功
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

                    location.href = '${ctx}/reload/info/' + id + "/" + $("#kid").val();

                } else if(layEvent === 'edit'){
                    //do something
                    location.href = '${ctx}/voucher/edit/' + id;
                }else if(layEvent === 'del'){
                    layer.confirm('确定删除当前行吗', function(index){
                        $.ajax({
                            url:"${ctx}/api/v1/reload/delete/"+ id,

                            type:"get",
                            success:function(result){

                                if(result==true){
                                    layer.alert("已删除！");
                                    obj.del(); //删除对应行（tr）的DOM结构，并更新缓存
                                    // obj.update();
                                    layer.close(index);
                                }else{
                                    layer.alert("数据删除失败！");
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
