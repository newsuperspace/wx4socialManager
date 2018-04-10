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
							<h1 class="h2">第一层级信息</h1> <!-- ● -->
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
								<button class="btn btn-sm btn-outline-secondary dropdown-toggle"
									data-toggle="toggle" data-target="#">
									<span class="glyphicon glyphicon-cog"></span> 其他
								</button>
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
										<th>名称</th>
										<th>flid</th>   <!-- ● -->
										<th>描述</th>
										<th>级别</th>
										<th>管理者</th>
										<th>所属街道</th>
										<th>所属社区</th>  <!-- ● -->
										<th>次层级数量</th>
										<th>管辖人数</th>
										<th>操作</th>
									</tr>
								</thead>
								<tbody>
									<s:iterator value="#levels">
										<tr>
											<td><s:a href="#"
													onclick="firstLevelModal.op.levelInfo('%{flid}')">  <!-- ● -->
													<s:property value="name" />
												</s:a></td>
												<!-- ● -->
											<td class="text-truncate" data-toggle="tooltip"
												title=<s:property value="flid" />><s:property 
													value="flid" /></td>
											<td><s:property value="description" /></td>
											<td><s:property value="level" /></td>
											<td><s:if test="null==manager || null==manager.user">
													<a href="#">未分配</a>
												</s:if> <s:else>
													<a href="#"><s:property value="manager.user.username" /></a>
												</s:else></td>
											<!-- ● -->
											<td>
												<s:property  value="parent.parent.name"/>
											</td>
											<td>
												<s:property value="parent.name"/>
											</td>
											<td><s:if test="null==children">0</s:if> <s:else>
													<a href="#"><s:property value="children.size()" /></a>
												</s:else></td>
											<td><s:if test="null==members">0</s:if> <s:else>
													<a href="#"><s:property value="members.size()" /></a>
												</s:else></td>
											<td>
												<div class="btn-group" role="group">
													<!-- ● -->
													<s:a cssClass="btn btn-sm btn-outline-secondary"
														role="button"
														onclick="firstLevelModal.op.showCreateSonLevelModal(this);"
														href="#">建子项</s:a>
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
		<!-- Modal 建立子层级对象 -->
		<div class="modal fade" id="newSonLevelModal" tabindex="-1"
			role="dialog" aria-labelledby="newSonLevelModal" aria-hidden="true">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<h4 class="modal-title" id="newSonLevelModal_title">新建xxx的子层级</h4>
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="modal-body">
						<div class="container-fluid">
							<input type="hidden" name="parentId" id="parentId" />

							<div class="input-group input-group-sm mb-3">
								<div class="input-group-prepend">
									<span class="input-group-text"> P </span>
								</div>
								<input type="text" class="form-control" name="parentDescription"
									id="parentDescription" disabled>
								<div class="input-group-prepend">
									<span class="input-group-text">父层级描述</span>
								</div>
							</div>

							<div class="input-group input-group-sm mb-3">
								<div class="input-group-prepend">
									<span class="input-group-text"> <span
										class="glyphicon glyphicon-user"></span>
									</span>
								</div>
								<input type="text" class="form-control" name="sonName"
									id="sonName">
								<div class="input-group-prepend">
									<span class="input-group-text">名称</span>
								</div>
							</div>

							<div class="input-group input-group-sm mb-3">
								<div class="input-group-prepend">
									<span class="input-group-text"> <span
										class="glyphicon glyphicon-glass"></span>
									</span>
								</div>
								<input type="text" class="form-control" name="sonDescription"
									id="sonDescription">
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
							onclick="firstLevelModal.op.createSonLevel();">新建</button>
					</div>
				</div>
			</div>
		</div>
		<!-- Modal 4 新建当前层级 -->
		<div class="modal fade" id="newModal" tabindex="-1" role="dialog"
			aria-labelledby="newModal" aria-hidden="true">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<!-- ● -->
						<h4 class="modal-title">新建第一层级</h4>
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="modal-body">
						<div class="container-fluid">
							<div class="input-group input-group-sm mb-3">
								<div class="input-group-prepend">
									<span class="input-group-text"><span
										class="glyphicon glyphicon-user"></span></span>
								</div>
								<input type="text" class="form-control" name="name" id="name">
								<div class="input-group-prepend">
									<span class="input-group-text">名称</span>
								</div>
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
							onclick="firstLevelModal.op.createLevel();">新建</button>
					</div>
				</div>
			</div>
		</div>
		<!-- Modal 层级详情 -->
		<div class="modal fade" id="detialsModal" tabindex="-1" role="dialog"
			aria-labelledby="detialsModal" aria-hidden="true">
			<div class="modal-dialog modal-lg" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<h4 class="modal-title" id="detialsModal_title">张三-信息详情</h4>
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
											UID:</div>
										<div class="col-9 text-left text-truncate"
											id="detialsModal_uid">
											5d7323e0-70b5-4ff6-9c77-3bdd70a507f1</div>
									</div>
									<div class="row">
										<div class="col-3 text-right font-weight-bold text-truncate">
											OpenID:</div>
										<div class="col-9 text-left text-truncate"
											id="detialsModal_openid">okNKU0Qdq6WC9bGO22gcp6tSCuJs</div>
									</div>
									<div class="row">
										<div class="col-3 text-right font-weight-bold text-truncate">
											注册时间:</div>
										<div class="col-9 text-left text-truncate"
											id="detialsModal_registrationTime">2018-12-22 12:56:06</div>
									</div>
									<div class="row">
										<div class="col-3 text-right font-weight-bold text-truncate">
											街道:</div>
										<div class="col-9 text-left text-truncate"
											id="detialsModal_minusFirst">呼家楼</div>
									</div>
									<div class="row">
										<div class="col-3 text-right font-weight-bold text-truncate">
											社区:</div>
										<div class="col-9 text-left text-truncate"
											id="detialsModal_zero">呼家楼北里</div>
									</div>
									<div class="row">
										<div class="col-3 text-right font-weight-bold text-truncate">
											第1层级:</div>
										<div class="col-9 text-left text-truncate"
											id="detialsModal_first">志愿者</div>
									</div>
									<div class="row">
										<div class="col-3 text-right font-weight-bold text-truncate">
											第2层级:</div>
										<div class="col-9 text-left text-truncate"
											id="detialsModal_second">为老志愿者</div>
									</div>
									<div class="row">
										<div class="col-3 text-right font-weight-bold text-truncate">
											第3层级:</div>
										<div class="col-9 text-left text-truncate"
											id="detialsModal_third">殷金凤工作室</div>
									</div>
									<div class="row">
										<div class="col-3 text-right font-weight-bold text-truncate">
											第4层级:</div>
										<div class="col-9 text-left text-truncate"
											id="detialsModal_fourth">第一支队</div>
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