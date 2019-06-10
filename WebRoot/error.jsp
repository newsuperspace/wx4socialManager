<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>出现问题</title>
</head>
<body>
	<h1 style="color: #f00">有一个问题发生</h1>
	<p style="font-size: 20px">
		<s:property value="errorMessage"/>
	</p>
</body>
</html>
