<%--
  Created by IntelliJ IDEA.
  User: hegc
  Date: 2016/4/1
  Time: 22:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>湖北客户服务综合平台</title>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
    <meta http-equiv="Cache-Control" content="no-store" />
    <meta http-equiv="Pragma" content="no-cache" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <meta http-equiv="Expires" content="0" />

    <link rel="stylesheet" type="text/css"  href="${ctx}/css/bootstrap.min.css"  charset="utf-8"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/css/login.css" />
    <link rel="stylesheet" href="${ctx}/css/plugins/font-awesome/css/font-awesome.min.css" />
    <!--[if IE 7]>
      <link rel="stylesheet" href="${ctx}/css/plugins/font-awesome/css/font-awesome-ie7.min.css" />
    <![endif]-->
    <link rel="stylesheet" type="text/css" href="${ctx}/css/style.css" />
    <link rel="stylesheet" type="text/css" href="${ctx}/css/theme/dandelion2/main.css" />

    <script type="text/javascript" src="${ctx}/js/lib/jquery-1.11.1.min.js" charset="utf-8"></script>
    <script type="text/javascript" src="${ctx}/js/lib/bootstrap.min.js" charset="utf-8"></script>

<script type="text/javascript">
    window.onload = function ()
    {
        if(window.parent.length>0)
            window.parent.location=location;
    }
    var pwdInpu,nameInput;
    
    function check(){
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
        $("#loginform").submit();
    }

    function checkBlank(username){
        if(!$.trim(username)) {
            $(".warm-info").html("请输入用户名！");
        } else {
            $(".warm-info").html("");
        }
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
<body class="loginpage">
	<div class="loginbox">            
        <form id="loginform" class="form-vertical" name="loginForm" action="${ctx}/login" method="post">
            <div class="formtitle">
            	<img src="${ctx}/images/logo.png" />
            </div>
            <div class="control-group">
                <div class="controls">
                    <div class="main_input_box">
                        <span class="add-on bg_lr"><i class="icon-user"></i></span><input type="text" placeholder="用户名" name="username" value="${username}" id="username" maxlength="20" onblur="checkBlank(this.value);"/>
                    </div>
                </div>
            </div>
            <div class="control-group">
                <div class="controls">
                    <div class="main_input_box">
                        <span class="add-on bg_lb"><i class="icon-lock"></i></span><input type="password" placeholder="密码" name="password" id="password" maxlength="32" />
                    </div>
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
            </div>
            <div class="form-bottom">
                <a href="javascript:void(0);" class="btn btn-primary" onclick="check()" />登&nbsp;&nbsp;&nbsp;&nbsp;录</a>
            </div>
        </form>
    </div>
    <div class="info-box">
        <div class="system-info">
            湖北客户服务综合平台<br />
			技术支持：广州华工信息软件有限公司
        </div>
    </div>
</body>
</html>