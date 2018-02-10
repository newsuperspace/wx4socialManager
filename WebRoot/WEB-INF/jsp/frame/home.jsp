<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="icon" href="${pageContext.request.contextPath}/img/logo.ico">
<title>承载社会工作创新中心-微信用户管理</title>
</head>

<frameset rows="50,*"   frameborder="0">
	<frame name="top" scrolling="no" src="directPageAction.action?forward=top&timestamp=new Date()" />
    <frameset cols="15%,85%" frameborder="0">
    	<frame name="left" src="directPageAction.action?forward=left&timestamp=new Date()" scrolling="no" />
        <frame name="right" src="userAction_getUserList.action" scrolling="auto" />
    </frameset>
</frameset>
<noframes>
	<p>Your Browser's version is unsupported, please upgrade it or change another one.</p>
</noframes>

</html>
