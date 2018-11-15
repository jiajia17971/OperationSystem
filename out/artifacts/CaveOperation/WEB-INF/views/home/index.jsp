<%--
  Created by IntelliJ IDEA.
  User: wqp
  Date: 2018/7/26
  Time: 9:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>湖北高速开库管理系统</title>
    <link rel="stylesheet" href="${ctx}/js/lib/layui/css/layui.css">

    <link rel="stylesheet" type="text/css" href="${ctx}/css/index/style.css" />
    <script type="text/javascript" src="${ctx}/js/lib/layui/layui.js"></script>
    <script type="text/javascript" src="${ctx}/js/dev/index.js"></script>


    <style>
        #cv {
            width: 600px; height: 400px;
            background: #000;
            border-radius: 20px;
            padding: 20px;
            margin: 20px auto;
            box-shadow: 0 0 40px #222;
            behavior: url(${ctx}/js/dev/h5/ie-css3.htc);
        }
    </style>
</head>

<body class="layui-layout-body container_body" >
<div class="layui-layout layui-layout-admin">
    <div class="layui-header">
        <div class="layui-logo">湖北高速开库管理平台</div>
        <!-- 头部区域（可配合layui已有的水平导航） -->
        <%--<ul class="layui-nav layui-layout-left">
            <li class="layui-nav-item"><a href="#" onclick="reloadPages1()">控制台</a></li>
            <li class="layui-nav-item"><a href="">开库管理</a></li>
            <li class="layui-nav-item"><a href="">用户</a></li>
            <li class="layui-nav-item">
                <a href="javascript:;">其它系统</a>
                <dl class="layui-nav-child">
                    <dd><a href="">邮件管理</a></dd>
                    <dd><a href="">消息管理</a></dd>
                    <dd><a href="">授权管理</a></dd>
                </dl>
            </li>
        </ul>--%>
        <ul class="layui-nav layui-layout-right" style="width: 250px">
            <li class="layui-nav-item">
                <a href="javascript:;">
                    <img src="${ctx}/images/user.jpg" class="layui-nav-img">
                    ${sessionScope.operator.userName}
                </a>
                <dl class="layui-nav-child">
                    <dd><a href="${ctx}/user/edit/${sessionScope.operator.id}" target="right_frame">基本资料</a></dd>
                    <%--<dd><a href="">安全设置</a></dd>--%>
                </dl>
            </li>
            <li class="layui-nav-item" onclick="confirmLogout()"><a href="${ctx}/logout">退出</a></li>
        </ul>
    </div>

    <%@include file="/WEB-INF/views/home/left.jsp" %>

    <div class="layui-body ">
        <!-- 内容主体区域 -->
        <c:set var="iscontain" value="false" />
        <c:forEach items="${user.roles}" var="map">
            <c:if test="${map.id eq 'checkrole'}">
                <c:set var="iscontain" value="true" />
            </c:if>
        </c:forEach>
        <div style="padding: 15px;height: 95%;" class="layadmin-tabsbody-item layui-show">
            <c:if test="${!iscontain}">
                <iframe id="right_frame" name="right_frame" src="${ctx}/voucher/list" frameborder=0 width=100% height=100% marginheight=2 marginwidth=2  ></iframe>
            </c:if>

            <c:if test="${iscontain}">
                <iframe id="right_frame" name="right_frame" src="${ctx}/voucher/checklist" frameborder=0 width=100% height=100% marginheight=2 marginwidth=2  ></iframe>
            </c:if>
        </div>
    </div>

    <div class="layui-footer">
        <!-- 底部固定区域 -->
        © 技术支持：广州华工信息软件有限公司
    </div>
</div>

<script>
    //JavaScript代码区域
    function confirmLogout (){
        layer.confirm('确定注销？', {
            btn: ['确定', '取消'] //可以无限个按钮

        }, function(index, layero){
            window.location.href='${ctx}/logout';
        }, function(index){
            //按钮【按钮二】的回调
        });
    }
    layui.use('element', function(){
        var element = layui.element;

    });
</script>
</body>

</html>
