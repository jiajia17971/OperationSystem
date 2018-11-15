<%--
  Created by IntelliJ IDEA.
  User: J.K
  Date: 2018-7-27
  Time: 10:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>湖北高速开库管理系统</title>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
    <meta http-equiv="Cache-Control" content="no-store" />
    <meta http-equiv="Pragma" content="no-cache" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <meta http-equiv="Expires" content="0" />


    <link rel="stylesheet" type="text/css" href="${ctx}/css/login/normalize.css" />
    <link rel="stylesheet" type="text/css" href="${ctx}/css/login/demo.css" />
    <!--必要样式-->
    <link rel="stylesheet" type="text/css" href="${ctx}/css/login/component.css" />
    <script type="text/javascript" src="${ctx}/js/lib/jquery-1.11.1.min.js" charset="utf-8"></script>
    <!--[if IE]>
    <script src="${ctx}/js/dev/h5/html5.js"></script>
    <![endif]-->

    <script type="text/javascript">

        function check(){
            $(".warm-info").html('');
            pwdInput = $("#password");
            nameInput = $("#username");
            if(!$.trim(nameInput.val())){
                nameInput.focus();
                $(".warm-info").html("请输入用户名！");
                return;
            }
            if(!pwdInput.val()){
                pwdInput.focus();
                $(".warm-info").html("请输入密码！");
                return;
            }
            $("#loginForm").submit();
        }

        $(function(){
            $("#username").focus();
            document.onkeydown = function(e){
                var ev = document.all ? window.event : e;
                if(ev.keyCode==13) {
                    check();
                }
            };
        });
    </script>


</head>
<body>
    <div class="container demo-1">
        <div class="content">
            <div id="large-header" class="large-header">
                <canvas id="demo-canvas"></canvas>
                <div class="logo_box">
                    <h3>湖北高速开库管理系统</h3>
                    <form  id="loginForm" action="${ctx}/login" method="post">
                        <div class="input_outer">
                            <span class="u_user"></span>
                            <input  class="text" style="color: #FFFFFF !important" name="username" value="${username}" id="username" type="text" maxlength="20" placeholder="请输入账户">
                        </div>
                        <div class="input_outer">
                            <span class="us_uer"></span>
                            <input  class="text" style="color: #FFFFFF !important; position:absolute; z-index:100;"value="" type="password" name="password" id="password" maxlength="32" placeholder="请输入密码">
                        </div>
                        <div class="info-box" <c:if test="${not empty error}">style="display:block;"</c:if>>
                            <span class="warm-info text-danger">
                                <c:choose>
                                    <c:when test="${not empty error}">
                                        ${error}
                                    </c:when>
                                </c:choose>
                            </span>
                        </div>
                        <div class="mb2"><a class="act-but submit" href="javascript:;" onclick="check()" style="color: #FFFFFF">登录</a></div>
                    </form>
                </div>
            </div>
        </div>
    </div><!-- /container -->
    <script src="${ctx}/js/dev/h5/TweenLite.min.js"></script>
    <script src="${ctx}/js/dev/h5/EasePack.min.js"></script>
    <script src="${ctx}/js/dev/h5/rAF.js"></script>
    <script src="${ctx}/js/dev/h5/demo-1.js"></script>
</body>
</html>