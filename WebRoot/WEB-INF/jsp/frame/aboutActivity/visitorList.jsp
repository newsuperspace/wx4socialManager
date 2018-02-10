<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
<!-- Bootstrap core CSS -->
<link href="${pageContext.request.contextPath}/css/bootstrap.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/bootstrap-theme.css"
	type="text/css" />
<link href="${pageContext.request.contextPath}/css/dashboard.css"
	rel="stylesheet">
</head>

<body>
	<!-- =======================页面主题结构部分==================== -->
	<div class="col-sm-12  col-md-12  main">
		<div class="row" style="vertical-align:middle">
			<h2 class="sub-header col-md-8">活动详情列</h2>
			<div class="col-md-4  col-xs-push-2" style="margin-top:30px">
				<a href="javascript:history.go(-1);">
					<button type="button" class="btn btn-primary btn-sm">
						返回 <span class="glyphicon glyphicon-share-alt"></span>
					</button>
				</a>
			</div>
		</div>
		<div class="table-responsive">
			<table class="table table-striped table-hover">
				<thead>
					<tr>
						<th>参与者姓名</th>
						<th>openid</th>
						<th>电话</th>
						<th>积分</th>
					</tr>
				</thead>
				<tbody>
					<s:iterator value="#visitors">
						<tr>
							<td><s:property value="username" /></td>
							<td><s:property value="openid" /></td>
							<td><s:property value="phone" /></td>
							<td><s:property value="score" /></td>
							</tr>
					</s:iterator>

				</tbody>
			</table>
		</div>

	</div>
	</div>
	</div>
</body>
<script src="${pageContext.request.contextPath}/js/jquery-3.2.0.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
</html>