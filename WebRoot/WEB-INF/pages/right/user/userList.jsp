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
							<h1 class="h2">用户信息</h1>
							<div class="btn-toolbar mb-2 mb-md-0">
								<div class="btn-group mr-2">
									<button class="btn btn-sm btn-outline-secondary"
										data-toggle="modal" data-target="#newUserModal">
										<span class="glyphicon glyphicon-plus"></span> 新建
									</button>
									<button class="btn btn-sm btn-outline-secondary"
										data-toggle="modal" data-target="#selectUsers">
										<span class="glyphicon glyphicon-search"></span> 筛选
									</button>
									<button class="btn btn-sm btn-outline-secondary"
										data-toggle="modal" data-target="#selectUsers">
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
										<th>用户名</th>
										<th>昵称</th>
										<th>性别</th>
										<th>年龄</th>
										<th>分组</th>
										<th>服务时长（时）</th>
										<th>积分</th>
										<th>电话</th>
										<th>住址</th>
										<th>邮箱</th>
										<th>身份证</th>
										<th>注册日期</th>
										<th>是否在公众号</th>
										<th>账号状态</th>
										<th>操作</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td>张三李四</td>
										<td>isthereanybody</td>
										<td>男</td>
										<td>1000</td>
										<td><span class="badge badge-success">一级管理者</span></td>
										<td>11.12</td>
										<td>300</td>
										<td>13718805500</td>
										<td>北京市朝阳区呼家楼</td>
										<td>isthereanybody@foxmail.com</td>
										<td>110105198808211118</td>
										<td>2018-03-11 21:33:56</td>
										<td>是</td>
										<td><span class="badge badge-success">正常</span></td>
										<td>
											<div class="btn-group" role="group">
												<button type="button"
													class="btn btn-outline-secondary btn-sm">修改</button>
												<button type="button"
													class="btn btn-outline-secondary btn-sm">推送</button>
												<button type="button"
													class="btn btn-outline-secondary btn-sm">其他</button>
											</div>
										</td> </tr>
										<s:iterator value="#users">
										<tr>
									<td><s:a  href="#"  onclick="userModal.op.userInfo('%{uid}')">
											<s:property value="username" />
										</s:a></td>
									<td><s:property value="sickname" /></td>
									<td><s:property value="sex" /></td>
									<td><s:property value="age" /></td>
									<td><s:if test="grouping.tag=='common'">
											<span class="badge badge-success"> <s:property
													value="grouping.groupName" />
											</span>
										</s:if> <s:elseif test="grouping.tag=='unreal'">
											<span class="badge badge-success"> <s:property
													value="grouping.groupName" />
											</span>
										</s:elseif> <s:elseif test="grouping.tag=='admin'">
											<span class="badge badge-success"> <s:property
													value="grouping.groupName" />
											</span>
										</s:elseif> <s:elseif test="grouping.tag=='zero'">
											<span class="badge badge-success"> <s:property
													value="grouping.groupName" />
											</span>
										</s:elseif> <s:elseif test="grouping.tag=='first'">
											<span class="badge badge-success"> <s:property
													value="grouping.groupName" />
											</span>
										</s:elseif> <s:elseif test="grouping.tag=='second'">
											<span class="badge badge-success"> <s:property
													value="grouping.groupName" />
											</span>
										</s:elseif> <s:elseif test="grouping.tag=='third'">
											<span class="badge badge-success"> <s:property
													value="grouping.groupName" />
											</span>
										</s:elseif> <s:elseif test="grouping.tag=='fourth'">
											<span class="badge badge-success"> <s:property
													value="grouping.groupName" />
											</span>
										</s:elseif> <s:elseif test="grouping.tag=='minus_first'">
											<span class="badge badge-success"> <s:property
													value="grouping.groupName" />
											</span>
										</s:elseif> <s:elseif test="grouping.tag=='money'">
											<span class="badge badge-success"> <s:property
													value="grouping.groupName" />
											</span>
										</s:elseif></td>
									<td><s:property value="serveTime" /></td>
									<td><s:property value="score" /></td>
									<td><s:property value="phone" /></td>
									<td><s:property value="address" /></td>
									<td><s:property value="email" /></td>
									<td><s:property value="cardid" /></td>
									<td><s:property value="registrationTime" /></td>
									<td><s:if test="ishere">是</s:if> <s:else>否</s:else></td>
									<td><s:if test="locked">
											<span class="badge badge-danger">锁死</span>
										</s:if> <s:else>
											<span class="badge badge-success">正常</span>
										</s:else></td>
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
		<!-- Modal 4 新建用户 -->
		<div class="modal fade" id="newUserModal" tabindex="-1" role="dialog"
			aria-labelledby="createUser" aria-hidden="true">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<h4 class="modal-title">新建用户</h4>
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
										class="glyphicon glyphicon-user" /></span>
								</div>
								<input type="text" class="form-control" name="username"
									id="username">
								<div class="input-group-prepend">
									<span class="input-group-text">用户名</span>
								</div>
							</div>

							<div class="input-group input-group-sm mb-3">
								<div class="input-group-prepend">
									<span class="input-group-text"><span
										class="glyphicon glyphicon-glass" /span>
								</div>
								<input type="text" class="form-control" name="sickname"
									id="sickname">
								<div class="input-group-prepend">
									<span class="input-group-text">昵称</span>
								</div>
							</div>

							<div class="input-group input-group-sm mb-3">
								<div class="input-group-prepend">
									<span class="input-group-text">ID</span>
								</div>
								<input type="text" class="form-control" name="cardid"
									id="cardid">
								<div class="input-group-prepend">
									<span class="input-group-text">身份证</span>
								</div>
							</div>

							<div class="input-group input-group-sm mb-3">
								<div class="input-group-prepend">
									<span class="input-group-text"><span
										class="glyphicon glyphicon-tint" /></span>
								</div>
								<select class="custom-select" id="sex" name="sex">
									<option value="0" selected>请选择...</option>
									<option value="1">男</option>
									<option value="2">女</option>
								</select>
								<div class="input-group-append">
									<label class="input-group-text">性别</label>
								</div>
							</div>

							<div class="input-group input-group-sm mb-3">
								<div class="input-group-prepend">
									<span class="input-group-text"><span
										class="glyphicon glyphicon-text-background"></span>
								</div>
								<input type="text" class="form-control" name="age" id="age">
								<div class="input-group-prepend">
									<span class="input-group-text">年龄</span>
								</div>
							</div>

							<div class="input-group input-group-sm mb-3">
								<div class="input-group-prepend">
									<span class="input-group-text"><span
										class="glyphicon glyphicon-phone-alt" /></span>
								</div>
								<input type="text" class="form-control" name="phone" id="phone">
								<div class="input-group-prepend">
									<span class="input-group-text">电话</span>
								</div>
							</div>

							<div class="input-group input-group-sm mb-3">
								<div class="input-group-prepend">
									<span class="input-group-text">@</span>
								</div>
								<input type="text" class="form-control" name="email" id="email">
								<div class="input-group-prepend">
									<span class="input-group-text">E-mail</span>
								</div>
							</div>

							<div class="input-group input-group-sm mb-3">
								<div class="input-group-prepend">
									<span class="input-group-text"><span
										class="glyphicon glyphicon-font" /></span>
								</div>
								<input type="text" class="form-control" name="address"
									id="address">
								<div class="input-group-prepend">
									<span class="input-group-text">地址</span>
								</div>
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-secondary"
							data-dismiss="modal">关闭</button>
						<button type="button" class="btn btn-primary"
							onclick="userModal.op.createUser();">新建</button>
					</div>
				</div>
			</div>
		</div>
		<!-- Modal 4 用户详情 -->
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
		<!-- Modal 4 用户更改 -->

		<!-- Modal 4 排序 -->

		<!-- Modal 4 筛选 -->
		<div class="modal fade" id="selectUserModal" tabindex="-1"
			role="dialog" aria-labelledby="selectUserModal" aria-hidden="true">
			<div class="modal-dialog  modal-lg" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<h4 class="modal-title">筛选用户</h4>
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