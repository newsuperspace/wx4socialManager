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


	<!-- 新建权限的模态对话框【完成】 -->
	<div class="modal fade" id="addModal" tabindex="-1" role="dialog"
		aria-labelledby="exampleModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">

				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">新建权限</h4>
				</div>

				<div class="modal-body">

					<!-- 表单数据 -->
					<form class="form-horizontal">

						<div class="form-group">
							<label for="email" class="col-md-2 control-label">权限名</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" id="newPermission">
							</div>
						</div>

						<div class="form-group">
							<label for="email" class="col-md-2 control-label">描述</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" id="newDescription">
							</div>
						</div>

						<div class="form-group">
							<label for="level" class="col-md-2 control-label">状态</label>
							<div class="col-sm-10">
								<select class="form-control" id="newState">
									<option value="able">可用</option>
									<option value="unable">不可用</option>
								</select>
							</div>
						</div>

					</form>

				</div>

				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<a href="javascript:permModal.op.modal.commitCreate();"><button
							type="button" class="btn btn-primary">新建</button></a>
				</div>

			</div>
		</div>
	</div>

	<!-- 修改权限的模态对话框【完成】 -->
	<div class="modal fade" id="updateModal" tabindex="-1" role="dialog"
		aria-labelledby="exampleModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">

				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="permName"></h4>
				</div>

				<div class="modal-body">

					<!-- 表单数据 -->
					<form class="form-horizontal">
						<input type="hidden" id="updatePid" />

						<div class="form-group">
							<label for="email" class="col-md-2 control-label">权限名</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" id="updatePermission">
							</div>
						</div>

						<div class="form-group">
							<label for="community" class="col-md-2 control-label">权限描述</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" id="updateDescription">
							</div>
						</div>

						<div class="form-group">
							<label for="level" class="col-md-2 control-label">权限状态</label>
							<div class="col-sm-10">
								<select class="form-control" id="updateState">
									<option value="able">可用</option>
									<option value="unable">不可用</option>
								</select>
							</div>
						</div>

					</form>

				</div>

				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<a href="javascript:permModal.op.modal.commitUpdate();"><button
							type="button" class="btn btn-primary">提交修改</button></a>
				</div>

			</div>
		</div>
	</div>

	<!--  =============================以下为正常页面显示内容================================ -->
	<div class="col-sm-12  col-md-12  main">
		<div class="row" style="vertical-align:middle">
			<h2 class="sub-header col-md-7">权限列表</h2>
			<div class="col-md-5" style="margin-top:30px">
				<shiro:hasPermission name="permission:create">
					<a href="javascript:permModal.op.modal.createPermissionModal();">
						<button type="button" class="btn btn-primary btn-sm">
							新建权限 <span class="glyphicon glyphicon-plus"></span>
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
						<th>权限名</th>
						<th>描述</th>
						<th>状态</th>
						<th>所属角色</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
					<s:iterator>
						<tr>
							<!-- 权限名、权限描述 -->
							<td><s:property value="permission" /></td>
							<td><s:property value="description" /></td>

							<!-- 权限状态 -->
							<s:if test="available">
								<td><span class="label label-success">可用</span></td>
							</s:if>
							<s:else>
								<td><span class="label label-danger">不可用</span></td>
							</s:else>

							<!-- 所属角色的数量 -->
							<td><s:a
									href="javascript:permModal.op.common.toRolesByPid('%{pid}');">
									<s:property value="%{roles.size()}" />
								</s:a></td>

							<!-- 操作 -->
							<td><shiro:hasPermission name="permission:update">
								<s:a href="javascript:permModal.op.modal.showUpdateModal('%{pid}');">
									<button class="btn btn-primary btn-xs">
										修改 <span class="glyphicon glyphicon-wrench"></span>
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
<script src="${pageContext.request.contextPath}/js/permissionModal.js"></script>
</html>