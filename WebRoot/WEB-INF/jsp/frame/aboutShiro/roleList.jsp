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
					<p style="color: red;">
						该操作将修改角色的权限集合，可能会造成部分社区管理者用户功能授权被禁用或更严重的安全问题，是否继续？</p>
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

	<!-- 显示 角色 详细信息（主要是权限列表和用户列表）【实现】 -->
	<div class="modal fade bs-example-modal-lg" id="infoModal"
		tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel">
		<div class="modal-dialog " role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">详细信息</h4>
				</div>
				<div class="modal-body">
					<!-- 模态框内容——————开始 -->
					<div class="row">
						<div class="col-md-3">
							<strong>角色名</strong>
						</div>
						<div class="col-md-9" id="infoRoleName">admin</div>
					</div>
					<div class="row">
						<div class="col-md-3">
							<strong>角色ID</strong>
						</div>
						<div class="col-md-9" id="infoRID">doskfpdosf8039284</div>
					</div>
					<div class="row">
						<div class="col-md-3">
							<strong>角色说明</strong>
						</div>
						<div class="col-md-9" id="infoDescription">管理员</div>
					</div>

					<hr class="divider" />

					<div class="row">
						<div class="col-md-3">
							<strong>包含权限</strong>
						</div>
						<div class="col-md-9" id="infoPermissions">

							<div class="row">
								<div class="col-md-3">
									<span class="label label-info">user:read</span>
								</div>
								<div class="col-md-3">
									<span class="label label-info">user:delete</span>
								</div>
								<div class="col-md-3">
									<span class="label label-info">user:update</span>
								</div>
								<div class="col-md-3">
									<span class="label label-info">user:create</span>
								</div>
							</div>

						</div>
					</div>

					<hr class="divider" />

					<div class="row">
						<div class="col-md-3">
							<strong>绑定用户</strong>
						</div>
						<div class="col-md-9" id="infoUsers">
						
						</div>
					</div>

					<!-- 模态框内容——————结束 -->
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				</div>
			</div>
		</div>
	</div>

	<!-- 新建 角色 的模态对话框 【实现】 -->
	<div class="modal fade" id="addModal" tabindex="-1" role="dialog"
		aria-labelledby="exampleModalLabel">
		<div class="modal-dialog modal-lg" role="document">
			<div class="modal-content">

				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">新建角色</h4>
				</div>

				<div class="modal-body">

					<!-- 表单数据 -->
					<form class="form-horizontal">
						<div class="form-group">
							<label for="email" class="col-md-2 control-label">角色名</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" id="newRoleName"
									name="role">
							</div>
						</div>

						<div class="form-group">
							<label for="email" class="col-md-2 control-label">角色描述</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" id="newDescription"
									name="description">
							</div>
						</div>

						<hr class="divider" />

						<div class="form-group">
							<label for="email" class="col-md-2 control-label">权限设置</label>
							<div class="col-sm-10" id="createPermissions">

								<!-- 下面这大段是帮助哦通过jQuery构造节点的模板，不会出现在实际页面中 -->
								<div class="row">
									<div class="col-md-3">
										<span class="label label-info">user:read</span> <input
											type="checkbox" name="pids" value="dsof8s39j2fjfodsjofd">
									</div>
									<div class="col-md-3">
										<span class="label label-info">user:read</span> <input
											type="checkbox" name="pids" value="dsof8s39j2fjfodsjofd">
									</div>
									<div class="col-md-3">
										<span class="label label-info">user:read</span> <input
											type="checkbox" name="pids" value="dsof8s39j2fjfodsjofd">
									</div>
									<div class="col-md-3">
										<span class="label label-info">user:read</span> <input
											type="checkbox" name="pids" value="dsof8s39j2fjfodsjofd">
									</div>
								</div>

							</div>
						</div>

					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<a href="javascript:roleModal.op.modal.commitCreate();"><button
							type="button" class="btn btn-primary">新建</button></a>
				</div>

			</div>
		</div>
	</div>

	<!-- 更新 角色 的模态对话框 -->
	<div class="modal fade" id="updateModal" tabindex="-1" role="dialog"
		aria-labelledby="exampleModalLabel">
		<div class="modal-dialog modal-lg" role="document">
			<div class="modal-content">

				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">角色设置</h4>
				</div>

				<div class="modal-body">

					<!-- 表单数据 -->
					<form class="form-horizontal">
						<input type="hidden" id="updateRid" />

						<div class="form-group">
							<label for="email" class="col-md-2 control-label">角色名</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" id="updateRoleName">
							</div>
						</div>

						<div class="form-group">
							<label for="community" class="col-md-2 control-label">角色描述</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" id="updateDescription">
							</div>
						</div>

						<div class="form-group">
							<label for="level" class="col-md-2 control-label">权限设置</label>
							<div class="col-sm-10" id="updatePermissions">

								<!-- 下面这大段是帮助哦通过jQuery构造节点的模板，不会出现在实际页面中 -->
								<div class="row">
									<div class="col-md-3">
										<span class="label label-info">user:read</span> <input
											type="checkbox" name="pids" value="dsof8s39j2fjfodsjofd">
									</div>
									<div class="col-md-3">
										<span class="label label-info">user:read</span> <input
											type="checkbox" name="pids" value="dsof8s39j2fjfodsjofd">
									</div>
									<div class="col-md-3">
										<span class="label label-info">user:read</span> <input
											type="checkbox" name="pids" value="dsof8s39j2fjfodsjofd">
									</div>
									<div class="col-md-3">
										<span class="label label-info">user:read</span> <input
											type="checkbox" name="pids" value="dsof8s39j2fjfodsjofd">
									</div>
								</div>

							</div>
						</div>

					</form>

				</div>

				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<a href="javascript:roleModal.op.modal.commitUpdate();"><button
							type="button" class="btn btn-primary">提交修改</button></a>
				</div>

			</div>
		</div>
	</div>

	<!--  =============================以下为正常页面显示内容================================ -->
	<div class="col-sm-12  col-md-12  main">
		<div class="row" style="vertical-align:middle">
			<h2 class="sub-header col-md-7">角色列表</h2>
			<div class="col-md-5" style="margin-top:30px">
				<shiro:hasPermission name="role:create">
					<a href="javascript:  roleModal.op.modal.createModal();">
						<button type="button" class="btn btn-primary btn-sm">
							新建角色 <span class="glyphicon glyphicon-plus"></span>
						</button>
					</a>
				</shiro:hasPermission>
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
						<th>角色名</th>
						<th>角色说明</th>
						<th>权限数量</th>
						<th>用户数量</th>
						<th>状态</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
					<s:iterator>
						<tr>
							<!-- 角色名、角色ID、角色说明 -->
							<td><s:property value="role" /></td>
							<td><s:property value="description" /></td>

							<!-- 权限数量、用户数量 -->
							<td><s:property value="%{permissions.size()}" /></td>
							<td><s:property value="%{users.size()}" /></td>

							<!-- 状态 -->
							<s:if test="available">
								<td><span class="label label-success">可用</span></td>
							</s:if>
							<s:else>
								<td><span class="label label-danger">不可用</span></td>
							</s:else>

							<!-- 操作 -->
							<td><s:a
									href="javascript:roleModal.op.modal.infoModal('%{rid}');">
									<button class="btn btn-info btn-xs">
										详情 <span class="glyphicon glyphicon-equalizer"></span>
									</button>
								</s:a> <shiro:hasPermission name="role:update">
									<s:a
										href="javascript: roleModal.op.modal.updateModal('%{rid}');">
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
	</div>
	</div>
</body>
<script src="${pageContext.request.contextPath}/js/jquery-3.2.0.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
<script src="${pageContext.request.contextPath}/js/roleModal.js"></script>

</html>