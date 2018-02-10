<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib  uri="/struts-tags"  prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登录系统</title>
</head>
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/login.css" rel="stylesheet">
<body style="background-image:url(img/bg.jpg)">

  <div  class="bai" style="border-radius:10px;
							background-color: #FFF;">
  		<div class="qrcode">
        	<img class="img"   id="qrcode"  src="${pageContext.request.contextPath}/img/loading.jpg"  />
            <div>
            	<p class="sub_title"   id="title">请使用扫码登录功能</p>
                <p class="sub_desc"   id="lastTime">二维码有效期60秒</p>
            </div>
        </div>
   </div>

</body>
<script src="js/jquery-3.2.0.js"></script>
<script src="js/bootstrap.min.js"></script>
<script type="text/javascript"   src="js/ws4login.js"></script>
</html>