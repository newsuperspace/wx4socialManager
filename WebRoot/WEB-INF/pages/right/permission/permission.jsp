<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>社区层级化组织管理系统平台</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<!-- Bootstrap CSS -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/bootstrap.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/bootstrap-grid.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/bootstrap-reboot.css">
</head>
<body>


	<!-- =================Navbar（动态include）=============== -->
	<jsp:include page="/WEB-INF/pages/up/navbar.jsp"></jsp:include>
	<div class="container-fluid">
		<div class="row">
			<!-- =================Menu（动态include）=============== -->
			<jsp:include page="/WEB-INF/pages/left/menu.jsp"></jsp:include>

			<div class="col-xs-12 col-md-10 col-lg-10">
				<!-- =================Breadcromb（动态include）=============== -->
				<jsp:include page="/WEB-INF/pages/others/breadcromb.jsp"></jsp:include>
				<!-- =================================★★真正的页面数据内容显示区域★★===================================== -->
				<div class="row">
					<div class="col">
						<!-- =============标题=========== -->
						<div
							class="justify-content-between d-flex flex-wrap flex-md-nowrap align-items-center pb-1 mb-4 border-bottom">
							<h1 class="h2">权限信息</h1>
							<!-- ● -->
							<div class="btn-toolbar mb-2 mb-md-0">
								<div class="btn-group mr-2">
									<button class="btn btn-sm btn-outline-secondary"
										data-toggle="modal" data-target="#newModal">
										<span class="glyphicon glyphicon-plus"></span> 新建
									</button>
									<button class="btn btn-sm btn-outline-secondary"
										data-toggle="modal" data-target="#selectModal">
										<span class="glyphicon glyphicon-search"></span> 筛选
									</button>
									<button class="btn btn-sm btn-outline-secondary"
										data-toggle="modal" data-target="#orderModal">
										<span class="glyphicon glyphicon-sort-by-order"></span> 排序
									</button>
								</div>
								<div class="dropdown">
									<button
										class="btn btn-sm btn-outline-secondary dropdown-toggle"
										type="button" id="others" data-toggle="dropdown"
										aria-haspopup="true" aria-expanded="false">
										<span class="glyphicon glyphicon-cog"></span> 其他
									</button>
									<div class="dropdown-menu dropdown-menu-right" aria-labelledby="others">
										<a class="dropdown-item" href="#" onclick="permissionModal.op.batchAutoCreate();">自动创建基础权限</a> 
										<a class="dropdown-item disabled" href="#">Disabled action</a>
										<h6 class="dropdown-header">Section header</h6>
										<a class="dropdown-item" href="#">Action</a>
										<div class="dropdown-divider"></div>
										<a class="dropdown-item" href="#">After divider action</a>
									</div>
								</div>

							</div>
						</div>
						<!-- =============表格=========== -->
						<div
							style="
                        white-space: nowrap;
                        overflow-x: hidden;
                        overflow-x: auto;">
							<table
								class="table table-striped table-sm table-bordered table-hover text-center">
								<thead class="thead-dark">
									<tr>
										<!-- ● -->
										<th>权限层级</th>
										<th>权限类型</th>
										<th>权限名</th>
										<th>pid</th>
										<th>描述</th>
										<th>街道拥有数</th>
										<th>社区拥有数</th>
										<th>第1层级拥有数</th>
										<th>第2层级拥有数</th>
										<th>第3层级拥有数</th>
										<th>第4层级拥有数</th>
										<th>权限状态</th>
										<!-- ● -->
										<th>操作</th>
									</tr>
								</thead>
								<tbody>
									<s:iterator value="#permissions">
										<tr>
											<!-- ● -->
											<td><s:property
													value="permissionType.permissionLevel.permissionLevelName" />
											</td>
											<td><s:property
													value="permissionType.permissionTypeName" /></td>
											<td><s:a href="#"
													onclick="permissionModal.op.itemInfo('%{pid}')">
													<s:property value="permissionName" />
												</s:a></td>
											<td class="text-truncate" data-toggle="tooltip"
												title=<s:property value="pid" />><s:property
													value="pid" /></td>
											<td><s:property value="description" /></td>
											<td><s:property value="minusFirstLevels.size()" /></td>
											<td><s:property value="zeroLevels.size()" /></td>
											<td><s:property value="firstLevels.size()" /></td>
											<td><s:property value="secondLevels.size()" /></td>
											<td><s:property value="thirdLevels.size()" /></td>
											<td><s:property value="fourthLevels.size()" /></td>
											<td><s:if test="enabled">
													已启用
												</s:if> <s:else>
													未启用
												</s:else></td>
											<!-- ● -->
											<td>
												<div class="btn-group" role="group">
													<!-- ● -->
													<button type="button"
														class="btn btn-outline-secondary btn-sm">修改</button>
													<button type="button"
														class="btn btn-outline-secondary btn-sm">群通知</button>
													<button type="button"
														class="btn btn-outline-secondary btn-sm">其他</button>
												</div>
											</td>
										</tr>
									</s:iterator>
								</tbody>
							</table>
						</div>
						<!-- 分页栏 -->
						<nav aria-label="Page navigation" class="mt-3">
							<ul class="pagination justify-content-center">
								<li class="page-item disabled"><a class="page-link"
									href="#" aria-label="向前"> <span aria-hidden="true">&laquo;</span>
										<span class="sr-only">向前</span>
								</a></li>
								<li class="page-item active"><a class="page-link" href="#">1</a></li>
								<li class="page-item"><a class="page-link" href="#">2</a></li>
								<li class="page-item"><a class="page-link" href="#">3</a></li>
								<li class="page-item"><a class="page-link" href="#">100</a></li>
								<li class="page-item"><a class="page-link" href="#">101</a></li>
								<li class="page-item"><a class="page-link" href="#"
									aria-label="向后"> <span aria-hidden="true">&raquo;</span> <span
										class="sr-only">向后</span>
								</a></li>
							</ul>
						</nav>
						<!-- 表格结束 -->
					</div>
				</div>
			</div>
		</div>
		<!-- =================================================模态对话框==================================================== -->

		<!-- Modal 4 新建当前数据 -->
		<div class="modal fade" id="newModal" tabindex="-1" role="dialog"
			aria-labelledby="newModal" aria-hidden="true">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<h4 class="modal-title">新建权限</h4>
						<!-- ● -->
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="modal-body">
						<div class="container-fluid">

							<!-- ● -->
							<div class="input-group input-group-sm mb-3">
								<div class="input-group-prepend">
									<span class="input-group-text"><span
										class="glyphicon glyphicon-tint"></span></span>
								</div>
								<s:select list="%{#permissionLevels}" cssClass="custom-select"
									id="plid" name="plid" listKey="plid" headerKey="0"
									headerValue="请选择..." listValue="permissionLevelName"
									onchange="permissionModal.op.preparePermissionTypeByPermissionLevel();">
								</s:select>
								<div class="input-group-append">
									<label class="input-group-text">权限层级</label>
								</div>
							</div>
							<!-- ● -->

							<!-- ● -->
							<div class="input-group input-group-sm mb-3">
								<div class="input-group-prepend">
									<span class="input-group-text"><span
										class="glyphicon glyphicon-tint"></span></span>
								</div>
								<select class="custom-select" id="ptid" name="ptid"
									disabled="disabled">
									<option value="0" selected>请先选择权限层级</option>
								</select>
								<div class="input-group-append">
									<label class="input-group-text">权限类型</label>
								</div>
							</div>
							<!-- ● -->

							<div class="input-group input-group-sm mb-3">
								<div class="input-group-prepend">
									<span class="input-group-text"><span
										class="glyphicon glyphicon-user"></span></span>
								</div>
								<!-- ● -->
								<input type="text" class="form-control" name="permissionName"
									id="permissionName">
								<div class="input-group-prepend">
									<span class="input-group-text">权限名称</span>
								</div>
								<!-- ● -->
							</div>

							<div class="input-group input-group-sm mb-3">
								<div class="input-group-prepend">
									<span class="input-group-text"><span
										class="glyphicon glyphicon-glass"></span></span>
								</div>
								<input type="text" class="form-control" name="description"
									id="description">
								<div class="input-group-prepend">
									<span class="input-group-text">描述</span>
								</div>
							</div>

						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-secondary"
							data-dismiss="modal">关闭</button>
						<!-- ● -->
						<button type="button" class="btn btn-primary"
							onclick="permissionModal.op.create();">新建</button>
					</div>
				</div>
			</div>
		</div>
		<!-- Modal 详情 -->
		<div class="modal fade" id="detialsModal" tabindex="-1" role="dialog"
			aria-labelledby="detialsModal" aria-hidden="true">
			<div class="modal-dialog modal-lg" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<h4 class="modal-title" id="detialsModal_title">信息详情</h4>
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="modal-body">
						<div class="container-fluid">
							<div class="row">
								<div class="col-lg-2 pr-0 col-xs-12">
									<img src="./img/qrcode.gif" class="img-fluid"
										id="detialsModal_qrcode" alt="Responsive image">
								</div>
								<div class="col-lg-10 pl-0 col-xs-12">
									<div class="row">
										<div class="col-3 text-right font-weight-bold text-truncate">
											用户名:</div>
										<div class="col-9 text-left text-truncate"
											id="detialsModal_username">newsuperspace</div>
									</div>
									<div class="row">
										<div class="col-3 text-right font-weight-bold text-truncate">
											OpenID:</div>
										<div class="col-9 text-left text-truncate"
											id="detialsModal_openid">okNKU0Qdq6WC9bGO22gcp6tSCuJs</div>
									</div>
								</div>
							</div>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-secondary"
								data-dismiss="modal">Close</button>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- Modal 4 更改 -->

		<!-- Modal 4 排序 -->

		<!-- Modal 4 筛选 -->
		<div class="modal fade" id="selectModal" tabindex="-1" role="dialog"
			aria-labelledby="selectModal" aria-hidden="true">
			<div class="modal-dialog  modal-lg" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<h4 class="modal-title">筛选</h4>
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="modal-body">
						<div class="container-fluid">Add rows here</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-secondary"
								data-dismiss="modal">关闭</button>
							<button type="button" class="btn btn-primary">确定</button>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- container结束 -->
	</div>
</body>
<script src="${pageContext.request.contextPath}/js/jquery-3.2.0.js"></script>
<script src="${pageContext.request.contextPath}/js/popper.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.bundle.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/myJS.js"></script>
</html>