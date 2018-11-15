<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
	<title>非法输入</title>
</head>

<body>
	<h2>400 - 非法输入字符：<span class="text-danger">${dangerousValues}</span>.</h2>
	<div class="sorryReturn"><a href="javascript:history.go(-1);">返回</a></div>
</body>
</html>