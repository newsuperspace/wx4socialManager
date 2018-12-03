<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>登陆页面</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
	
<!-- Bootstrap CSS -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/bootstrap.css">
<!-- jQueryUI CSS -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/jqueryui/jquery-ui.css"></link>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/jqueryui/jquery-ui.structure.css"></link>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/jqueryui/jquery-ui.theme.css"></link>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/jqueryui/jquery-ui-timepicker-addon.css" />
<!-- 其他 CSS -->

</head>
<body style="background-image: url(${pageContext.request.contextPath}/img/background.jpg);background-repeat:no-repeat; background-size:120%;">

    <div class="container">
        <div class="row">
            <div class="col-4"></div>
            <div class="col-4">

                <div id="tabs" style="opacity:0.8;margin-top: 50%">
                    <ul>
                        <li>
                            <a href="#tabs-1">二维码登陆</a>
                        </li>
                        <li>
                            <a href="#tabs-2">用户名登陆</a>
                        </li>
                    </ul>
                    
                    <div id="tabs-1">
                        <img id="qrcode" class="form-control" src="../img/qrcode.gif" />
                        <div class="d-flex justify-content-center mt-3">
                            <p id="title" style="margin: 0">请使用扫码登录功能</p>
                        </div>
                        <div class="d-flex justify-content-center">
                            <p id="desc">二维码有效期60秒</p>
                        </div>
                        <div class="d-flex justify-content-center">
                            <p class="mt-1 mb-3 text-muted" style="font-size: 12px">&copy; 2017-2019 联合会提供技术支持</p>
                        </div>
                    </div>
                    
                    <div id="tabs-2">
                        <h1 class="h3 mb-3 font-weight-normal">请输入用户名和密码</h1>
                        <!-- 
                        	★★★method必须为get请求，不然我们在表单中填写的中文提交到服务器端时会成为乱码★★★
                         -->
                        <form action="${pageContext.request.contextPath}/shiroAction_login4Input.action" method="get" onsubmit="wsModal.op.wsOP.loginFromInput()">
	                        <input type="text" id="username" name="username" class="form-control" placeholder="用户名" required autofocus>
	                        <input type="password" id="password" name="password" class="form-control mt-1" placeholder="密码" required>
	                        <div class="checkbox mb-3">
	                            <label>
	                                <input type="checkbox" value="remember-me"> 记住我
	                            </label>
	                        </div>
	                        <button class="btn btn-lg btn-warning btn-block" type="submit">登录</button>
                        </form>
                        <div class="d-flex justify-content-center">
                            <p class="mt-5 mb-3 text-muted" style="font-size: 12px">&copy; 2017-2019 联合会提供技术支持</p>
                        </div>
                    </div>
                </div>

            </div>
            <div class="col-4"></div>
        </div>


    </div>
</body>

<!-- jQuery JS-->
<script src="${pageContext.request.contextPath}/js/jquery-3.2.0.js"></script>
<!-- jQueryUI JS-->
<script src="${pageContext.request.contextPath}/jqueryui/jquery-ui.js"></script>
<script
	src="${pageContext.request.contextPath}/jqueryui/jquery-ui-timepicker-addon.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/jqueryui/jquery-ui-timepicker-zh-CN.js"></script>
<!-- Bootstrap JS -->
<script src="${pageContext.request.contextPath}/js/popper.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.bundle.js"></script>
<!-- 其他 JS -->
<script type="text/javascript" src="${pageContext.request.contextPath}/js/myJS.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ws4login.js"></script>
<script>
        $(function () {
            $("#tabs").tabs();
        });
</script>
</html>