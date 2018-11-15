<%--
  Created by IntelliJ IDEA.
  User: wqp
  Date: 2018-8-29
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
    <li>日志管理</li>
    <li>日志查询</li>
</ul>
<div class="layui-form-item">

    <div class="layui-inline" style="width: 100%;background-color: #f2f2f2;border-radius: 3px;">

        <div class="layui-inline s_inpt">
            <label class="layui-form-label"  style="line-height:12px;width: 100px">开库类型</label>

            <div class="layui-input-inline tb_input" >
                <select name="businessType" class="tb_input" lay-verify="required" id="businessType" value="" style="height: 30px;border-radius: 3px;border-color: #e5e5e5;">
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
            <label class="layui-form-label " style="line-height:12px;width: 100px">操作时间</label>
            <div class="layui-input-inline tb_input">
                <input type="text" name="optTime" id="searchcretime" class="layui-input tb_input" value="" >
            </div>
        </div>


        <button class="layui-btn" data-type="reload" id="layui-btn1" style="margin-left: 140px">查询</button>
        <button class="layui-btn" id="laybtn-clean">清空</button>

    </div>
    <table class="layui-hide" id="test" lay-filter="tb_filter">

    </table>
    <script>
        layui.use('table', function() {
            var table = layui.table;

            var tableIns = table.render({
                elem: '#test',
                url:'${ctx}/api/v1/business/query',
                id:"id",
                method:'post',
                height: 500,
                where:{
                    businessType:$("#businessType").val()
                },
                cols: [
                    [ //标题栏
                        {
                            checkbox: true,
                            LAY_CHECKED: true
                        } //默认全选
                        , {title: '序号',
                        templet: '#indexTpl',
                        width:80
                    },
                        {
                        field: 'operator',
                        title: '操作者',
                        align:'center',
                    }, {
                        field: 'optTime',
                        title: '操作时间',
                        align:'center',
                        /*templet:'<div>{{ Format(d.optTime,"yyyy-MM-dd")}}</div>'*/
                    },{
                        field: 'businessType',
                        title: '操作类型',
                        align:'center',
                        templet: '#titleTpl',
                        sort: true
                    },{
                        field: 'source',
                        title: '开库单',
                        align:'center',
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
            $("#laybtn-clean").on("click", function () {
                $("#searchcretime").val("");
                $("#businessType").val(9);
            });
            var active = {
                reload: function () {
                    //执行重载
                    tableIns.reload({
                        page: {
                            curr: 1 //重新从第 1 页开始
                        }
                        , where: {//绑定查询条件
                            optTime:$("#searchcretime").val(),
                            businessType: $("#businessType").val()


                        }
                    });
                }
            };
        });


    </script>
</div>
</body>
</html>
<script type="text/html" id="titleTpl">
    {{# if(d.businessType==4){ }}
    跨行30天办理
    {{#}else if(d.businessType==3){}}
    记账文件重发
    {{#}else if(d.businessType==2){}}
    充值长款
    {{#}else if(d.businessType==1){}}
    车道重复交易
    {{#}else if(d.businessType==0){}}
    车道流水丢失
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
            elem: '#searchcretime', //指定元素
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



<!-- 分页模版 -->
<%@ include file="/common/page.jsp" %>