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
			<h2 class="sub-header col-md-8">兑换详情</h2>
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
						<th>兑换人</th>
						<th>兑换物品</th>
						<th>兑换时间</th>
						<th>时扣分</th>
					</tr>
				</thead>
				<tbody>
					<s:iterator value="#exchanges">
						<tr>
							<td><s:property value="user.username" /></td>
							<td><s:property value="ware.wname" /></td>
							<td><s:property value="exchangeData" /></td>
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
<!--<script src="${pageContext.request.contextPath}/js/bookModal.js"></script> -->
</html>