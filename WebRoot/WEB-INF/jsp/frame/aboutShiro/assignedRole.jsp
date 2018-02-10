<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
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
	<!--  ==============================以下为浮动对话框的内容=============================== -->
	<!-- 用来显示确认信息的模态对话框 -->
	<div class="modal fade" tabindex="-1" role="dialog" id="confirm">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">确认操作</h4>
				</div>
				<div class="modal-body">
					<p>该操作将重新批量生成每个用户的二维码，是否继续？</p>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
					<button type="button" class="btn btn-primary"
						onclick="userModal.operation.batchCreateUserQR();">确认</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!-- /.modal -->


	<!-- 设置 “社区管理者” 与 “角色”的关联 -->
	<div class="modal fade" id="updateModal" tabindex="-1" role="dialog"
		aria-labelledby="exampleModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">

				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="titleUsername"></h4>
				</div>

				<div class="modal-body">

					<!-- 表单数据 -->
					<form class="form-horizontal">
						<input type="hidden" id="uid" />

						<div class="form-group">
							<label for="email" class="col-md-2 control-label">用户名</label>
							<div class="col-sm-10">
								<p id="updateUsername"></p>
							</div>
						</div>

						<div class="form-group">
							<label for="community" class="col-md-2 control-label">用户ID</label>
							<div class="col-sm-10">
								<p id="updateUid"></p>
							</div>
						</div>
						<div class="form-group">
							<label for="level" class="col-md-2 control-label">当前角色</label>
							<div class="col-sm-10">
								<select class="form-control" id="selector">
									<option value="-1" selected="selected">--请选择--</option>
								</select>
							</div>
						</div>

					</form>

				</div>

				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<a href="javascript:assignedRoleModal.op.modal.commitUpdate();"><button
							type="button" class="btn btn-primary">提交修改</button></a>
				</div>

			</div>
		</div>
	</div>

	<!--  =============================以下为正常页面显示内容================================ -->
	<div class="col-sm-12  col-md-12  main">
		<div class="row" style="vertical-align:middle">
			<h2 class="sub-header col-md-7">社区管理者列表</h2>
			<div class="col-md-5" style="margin-top:30px">
				<a href="#">
					<button type="button" class="btn btn-primary btn-sm">
						搜索 <span class="glyphicon glyphicon-search"></span>
					</button>
				</a>
			</div>
		</div>
		<div class="table-responsive">
			<table class="table table-striped table-hover">
				<thead>
					<tr>
						<th>用户名</th>
						<th>ID</th>
						<th>电话</th>
						<th>当前角色</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
					<s:iterator value="%{#admins}">
						<tr>
							<!-- 用户名、ID、电话、角色 -->
							<td><s:property value="username" /></td>
							<td><s:property value="uid" /></td>
							<td><s:property value="phone" /></td>
							<td><s:property value="role.role" /></td>

							<!-- 操作 -->
							<td><s:a href="#">
									<button class="btn btn-info btn-xs">
										详情 <span class="glyphicon glyphicon-equalizer"></span>
									</button>
								</s:a> <shiro:hasPermission name="role:assigned">
									<s:a
										href="javascript:assignedRoleModal.op.modal.showUpdateModal('%{uid}');">
										<button class="btn btn-primary btn-xs">
											设置 <span class="glyphicon glyphicon-wrench"></span>
										</button>
									</s:a>
								</shiro:hasPermission></td>
						</tr>
					</s:iterator>
				</tbody>
			</table>
		</div>
	</div>
</body>
</body>
<script src="${pageContext.request.contextPath}/js/jquery-3.2.0.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
<script src="${pageContext.request.contextPath}/js/assignedRoleModal.js"></script>

</html>