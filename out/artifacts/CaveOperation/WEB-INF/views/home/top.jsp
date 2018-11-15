<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/common/taglibs.jsp" %>
<div class="navbar-header">
    <%--<img src="${ctx}/images/logo.png" height="50px"/>--%>
</div>
<div class="navbar-collapse">
    <ul class="nav navbar-nav navbar-right">
        <li>
            <a style="cursor:default;">
                <i class="icon-user"></i>&nbsp;&nbsp;员工姓名：【${sessionScope.operator.userName}】
            </a>
        </li>
        <li>
            <a href="${ctx}/index" class="dropdown-toggle theme-toggle">
                <i class="icon-home"></i>&nbsp;&nbsp;欢迎页面
            </a>
        </li>
        <li><a href="#" data-toggle="dropdown" data-target="#"
               id="dLabel"><i class="icon-signout"></i>&nbsp;&nbsp;退出系统</a>
            <ul class="dropdown-menu text-center" role="menu"
                aria-labelledby="dLabel">
                <li style="line-height: 25px; height: 35px;">&nbsp;&nbsp;&nbsp;&nbsp;确认退出吗?</li>
                <li>
                    &nbsp;&nbsp;&nbsp;&nbsp;
                    <button class="btn">返 回</button>
                    <button onclick="javascript:window.location.href='${ctx}/logout';" class="btn btn-primary">退 出
                    </button>
                </li>
            </ul>
        </li>
    </ul>
</div>