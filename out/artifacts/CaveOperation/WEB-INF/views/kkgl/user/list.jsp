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
            overflow-x:hidden ;
            overflow-y:hidden;}
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
    <li>系统管理</li>
    <li>用户管理</li>
</ul>
<div class="layui-form-item">

    <div class="layui-inline" style="width: 100%;background-color: #f2f2f2;border-radius: 3px;">
        <div class="layui-inline" style="padding-top: 5px">
            <label class="layui-form-label ">登录名</label>
            <div class="layui-input-inline " style="margin-top: 5px">
                <input type="text" name="loginName" autocomplete="off" class="layui-input search_input" id="searchlgname" style="height: 30px">
            </div>
        </div>
        <div class="layui-inline" style="padding-top: 5px">
            <label class="layui-form-label ">用户名</label>
            <div class="layui-input-inline " style="margin-top: 5px">
                <input type="text" name="userName" autocomplete="off" class="layui-input search_input" id="searchname" style="height: 30px">
            </div>
        </div>

        <div class="layui-inline" style="padding-top: 5px">
            <label class="layui-form-label">状态</label>

            <div class="layui-input-inline">
                <select name="status" lay-verify="required" style="height: 30px;margin-top: 5px;border-radius: 3px;border-color: #efefef;width: 100%;" id="searchstatus">
                    <option value="">全部</option>
                    <option value="1">锁定</option>
                    <option value="0">未锁定</option>

                </select>
            </div>


        </div>


        <button class="layui-btn" data-type="reload" id="layui-btn1" style="margin-left: 140px">查询</button>
        <button class="layui-btn" id="tbn_add">添加</button>

    </div>
    <table class="layui-hide" id="test" lay-filter="tb_filter">

    </table>
    <script>
        layui.use('table', function() {
            var table = layui.table;

            var tableIns = table.render({
                elem: '#test',
                url:'${ctx}/api/v1/user/query',
                id:"id",
                method:'post',
                height: 500,
                cols: [
                    [ //标题栏
                        {
                            checkbox: true,
                            LAY_CHECKED: true
                        } //默认全选
                        , {type:'numbers',
                        title: '序号',
                        templet: '#indexTpl',
                        width:80
                    },
                        {
                        field: 'loginName',
                        title: '登录名',
                        align:'center',
                    }, {
                        field: 'userName',
                        title: '用户名',
                        align:'center',
                    },{
                        field: 'state',
                        title: '状态',
                        align:'center',
                        templet: '#titleTpl',
                        sort: true
                    },{
                        field: 'updateTime',
                        title: '更新时间',
                        align:'center',
                        sort: true,
                        templet:'<div>{{ Format(d.updateTime,"yyyy-MM-dd")}}</div>'
                    },{
                        field: 'lastLoginTime',
                        title: '最后登录时间',
                        align:'center',
                        sort: true,
                        templet:'<div>{{ Format(d.lastLoginTime,"yyyy-MM-dd")}}</div>'

                    },{
                        field: 'operate',
                        title: '操作',
                        align:'center',
                        templet: '#btnTpl',
                        sort: true
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
            var active = {
                reload: function () {
                    //执行重载
                    tableIns.reload({
                        page: {
                            curr: 1 //重新从第 1 页开始
                        }
                        , where: {//绑定查询条件
                            loginName: $("#searchlgname").val(),
                            userName:$("#searchname").val(),
                            status:$("#searchstatus").val()
                        }
                    });
                }
            };
        });

        $("#tbn_add").click(function(){
            window.location.href = '${ctx}/user/add';
        })
    </script>
</div>
</body>
</html>
<script type="text/html" id="titleTpl">
    {{# if(d.status==1){ }}
    锁定
    {{#}else if(d.status==0){}}
    未锁定
    {{# }}}
</script>
<script type="text/html" id="indexTpl">
    {{d.LAY_TABLE_INDEX+1}}
</script>

<script>
    layui.use('laydate', function() {
        var laydate = layui.laydate;

        //执行一个laydate实例
        laydate.render({
            elem: '#test1', //指定元素
            range: true
        });
    });
    function Format(datetime,fmt) {
        if (parseInt(datetime)==datetime) {
            if (datetime.length==10) {
                datetime=parseInt(datetime)*1000;
            } else if(datetime.length==13) {
                datetime=parseInt(datetime);
            }
        }
        datetime=new Date(datetime);
        var o = {
            "M+" : datetime.getMonth()+1,                 //月份
            "d+" : datetime.getDate(),                    //日
            "h+" : datetime.getHours(),                   //小时
            "m+" : datetime.getMinutes(),                 //分
            "s+" : datetime.getSeconds(),                 //秒
            "q+" : Math.floor((datetime.getMonth()+3)/3), //季度
            "S"  : datetime.getMilliseconds()             //毫秒
        };
        if(/(y+)/.test(fmt))
            fmt=fmt.replace(RegExp.$1, (datetime.getFullYear()+"").substr(4 - RegExp.$1.length));
        for(var k in o)
            if(new RegExp("("+ k +")").test(fmt))
                fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
        return fmt;
    }
</script>
<script type="text/template" id="btnTpl">
    <a class="layui-btn layui-btn-xs" lay-event="detail" target="_self">修改</a>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
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
            location.href = '${ctx}/user/edit/' + id;
        } else if(layEvent === 'edit'){
        //do something
            location.href = '${ctx}/user/edit/' + id;
        }else if(layEvent === 'del'){
            layer.confirm('确定删除当前行吗', function(index){
            $.ajax({
            url:"${ctx}/api/v1/user/delete/"+ id,

            type:"get",
            success:function(result){

                if(result==true){

                    layer.alert("已删除！");
                    obj.del(); //删除对应行（tr）的DOM结构，并更新缓存
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


<!-- 分页模版 -->
<%@ include file="/common/page.jsp" %>