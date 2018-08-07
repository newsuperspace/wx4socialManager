<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>实名认证</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<!-- Bootstrap CSS -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/bootstrap.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/signin.css">
</head>
<body>
	<div class="container mt-3">
		<form action="#" method="post">
			<div class="row">
				<div class="col-1"></div>
				<div class="col-10">
					<div class="form-group">
						<label for="username">姓名</label> <input onchange="aboutWeixin.op.canCheckRealName();" type="text"
							class="form-control col-12" name="username" id="username"
							aria-describedby="helpId4Username" placeholder="例如：張三"> <small
							id="helpId4Username" class="form-text text-muted text-warm">请填写您的真实姓名</small>
					</div>
				</div>
				<div class="col-1"></div>
			</div>

			<div class="row">
				<div class="col-1"></div>
				<div class="col-10">
					<div class="form-group">
						<label for="sex">性别</label> <select onchange="aboutWeixin.op.canCheckRealName();" class="form-control col-12"
							name="sex" id="sex">
							<option value="0">--请选择--</option>
							<option value="1">男</option>
							<option value="2">女</option>
						</select> <small id="helpId4Sex" class="form-text text-muted text-warm">选择您的性别</small>
					</div>
				</div>
				<div class="col-1"></div>
			</div>

			<div class="row">
				<div class="col-1"></div>
				<div class="col-10">
					<div class="form-group">
						<label for="age">年龄</label> <input onchange="aboutWeixin.op.canCheckRealName();" type="text"
							class="form-control col-12" name="age" id="age"
							aria-describedby="helpId4Age" placeholder="例如：22"> <small
							id="helpId4Age" class="form-text text-muted text-warm">您的年龄是？</small>
					</div>
				</div>
				<div class="col-1"></div>
			</div>

			<div class="row">
				<div class="col-1"></div>
				<div class="col-10">
					<div class="form-group">
						<label for="telephone">电话</label> <input onchange="aboutWeixin.op.canCheckRealName();" type="text"
							class="form-control col-12" name="phone" id="phone"
							aria-describedby="helpId4Phone" placeholder="例如：65083142">
						<small id="helpId4Phone" class="form-text text-muted text-warm">联系电话</small>
					</div>
				</div>
				<div class="col-1"></div>
			</div>

			<div class="row">
				<div class="col-2"></div>
				<div class="col-8">
					<button disabled="true" type="button" name="commit" id="commit"
						onclick="aboutWeixin.op.checkRealName();"
						class="btn btn-primary btn-block">提交</button>
				</div>
				<div class="col-2 "></div>
			</div>

		</form>
	</div>
</body>

<script src="${pageContext.request.contextPath}/js/jquery-3.2.0.js"></script>
<script src="${pageContext.request.contextPath}/js/popper.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.bundle.js"></script>
<script src="http://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/myJS.js"></script>
<script type="text/javascript">

	$(function(){
		// 判断当前操作者是否已经实名认证过了，如果已经实名认证过就直接关闭页面
		aboutWeixin.init.op.preCheckRealName();
	});
</script>
</html>