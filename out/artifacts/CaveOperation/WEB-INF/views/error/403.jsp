<%@ page contentType="text/html; charset=UTF-8" isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
	<title>未授权的访问</title>
</head>

<body>
	<h2>403 - 非法访问.</h2>
	<p>请重新<a href="${ctx}/login">登录</a></p>
</body>
</html>
