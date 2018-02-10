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

	<!--  =============================以下为正常页面显示内容================================ -->
	<div class="col-sm-12  col-md-12  main">
		<div class="row" style="vertical-align:middle">
			<h2 class="sub-header col-md-9">
				权限所属角色的列表
			</h2>
			<div class="col-md-3" style="margin-top:30px">
				<a href="javascript: window.history.go(-1);">
					<button type="button" class="btn btn-primary btn-sm">
						返回<span class="glyphicon glyphicon-plus"></span>
					</button>
				</a>
			</div>
		</div>
		<div class="table-responsive">
			<table class="table table-striped table-hover">
				<thead>
					<tr>
						<th>角色名</th>
						<th>rid</th>
						<th>角色描述</th>
						<th>当前状态</th>
					</tr>
				</thead>
				<tbody>
					<s:iterator>
						<tr>
							<td><s:property value="role" /></td>
							<td><s:property value="rid" /></td>
							<td><s:property value="description" /></td>
							<td><s:if test="available">
									可用
								</s:if> <s:else>
									不可用
								</s:else></td>
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