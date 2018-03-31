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
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
	integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
	crossorigin="anonymous">
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
							<h1 class="h2">Dashboard</h1>
							<div class="btn-toolbar mb-2 mb-md-0">
								<div class="btn-group mr-2">
									<button class="btn btn-sm btn-outline-secondary"
										data-toggle="modal" data-target="#newUser">
										<span class="glyphicon glyphicon-plus"></span> 新用户
									</button>
									<button class="btn btn-sm btn-outline-secondary"
										data-toggle="modal" data-target="#selectUsers">
										<span class="glyphicon glyphicon-search"></span> 筛选
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
													class="btn btn-outline-secondary btn-sm">详情</button>
												<button type="button"
													class="btn btn-outline-secondary btn-sm">修改</button>
												<button type="button"
													class="btn btn-outline-secondary btn-sm">推送</button>
												<button type="button"
													class="btn btn-outline-secondary btn-sm">其他</button>
											</div>
										</td>
									</tr>
									<tr>
										<td>libero</td>
										<td>Sed</td>
										<td class="table-info">cursus</td>
										<td>ipsum</td>
										<td>dolor</td>
										<td>ipsum</td>
										<td>dolor</td>
										<td>ipsum</td>
										<td>dolor</td>
										<td>sit</td>
										<td>sit</td>
										<td>sit</td>
										<td>sit</td>
										<td>sit</td>
									</tr>
									<tr>
										<td>libero</td>
										<td>Sed</td>
										<td class="table-info">cursus</td>
										<td>ipsum</td>
										<td>dolor</td>
										<td>ipsum</td>
										<td>dolor</td>
										<td>ipsum</td>
										<td>dolor</td>
										<td>sit</td>
										<td>sit</td>
										<td>sit</td>
										<td>sit</td>
										<td>sit</td>
									</tr>
									<tr>
										<td>libero</td>
										<td>Sed</td>
										<td class="table-info">cursus</td>
										<td>ipsum</td>
										<td>dolor</td>
										<td>ipsum</td>
										<td>dolor</td>
										<td>ipsum</td>
										<td>dolor</td>
										<td>sit</td>
										<td>sit</td>
										<td>sit</td>
										<td>sit</td>
										<td>sit</td>
									</tr>
									<tr>
										<td>libero</td>
										<td>Sed</td>
										<td class="table-info">cursus</td>
										<td>ipsum</td>
										<td>dolor</td>
										<td>ipsum</td>
										<td>dolor</td>
										<td>ipsum</td>
										<td>dolor</td>
										<td>sit</td>
										<td>sit</td>
										<td>sit</td>
										<td>sit</td>
										<td>sit</td>
									</tr>
									<tr>
										<td>libero</td>
										<td>Sed</td>
										<td class="table-info">cursus</td>
										<td>ipsum</td>
										<td>dolor</td>
										<td>ipsum</td>
										<td>dolor</td>
										<td>ipsum</td>
										<td>dolor</td>
										<td>sit</td>
										<td>sit</td>
										<td>sit</td>
										<td>sit</td>
										<td>sit</td>
									</tr>
									<tr>
										<td>libero</td>
										<td>Sed</td>
										<td class="table-info">cursus</td>
										<td>ipsum</td>
										<td>dolor</td>
										<td>ipsum</td>
										<td>dolor</td>
										<td>ipsum</td>
										<td>dolor</td>
										<td>sit</td>
										<td>sit</td>
										<td>sit</td>
										<td>sit</td>
										<td>sit</td>
									</tr>
									<tr>
										<td>libero</td>
										<td>Sed</td>
										<td class="table-info">cursus</td>
										<td>ipsum</td>
										<td>dolor</td>
										<td>ipsum</td>
										<td>dolor</td>
										<td>ipsum</td>
										<td>dolor</td>
										<td>sit</td>
										<td>sit</td>
										<td>sit</td>
										<td>sit</td>
										<td>sit</td>
									</tr>
									<tr>
										<td>libero</td>
										<td>Sed</td>
										<td class="table-info">cursus</td>
										<td>ipsum</td>
										<td>dolor</td>
										<td>ipsum</td>
										<td>dolor</td>
										<td>ipsum</td>
										<td>dolor</td>
										<td>sit</td>
										<td>sit</td>
										<td>sit</td>
										<td>sit</td>
										<td>sit</td>
									</tr>
									<tr>
										<td>libero</td>
										<td>Sed</td>
										<td class="table-info">cursus</td>
										<td>ipsum</td>
										<td>dolor</td>
										<td>ipsum</td>
										<td>dolor</td>
										<td>ipsum</td>
										<td>dolor</td>
										<td>sit</td>
										<td>sit</td>
										<td>sit</td>
										<td>sit</td>
										<td>sit</td>
									</tr>
									<tr>
										<td>libero</td>
										<td>Sed</td>
										<td class="table-info">cursus</td>
										<td>ipsum</td>
										<td>dolor</td>
										<td>ipsum</td>
										<td>dolor</td>
										<td>ipsum</td>
										<td>dolor</td>
										<td>sit</td>
										<td>sit</td>
										<td>sit</td>
										<td>sit</td>
										<td>sit</td>
									</tr>
									<tr>
										<td>libero</td>
										<td>Sed</td>
										<td class="table-info">cursus</td>
										<td>ipsum</td>
										<td>dolor</td>
										<td>ipsum</td>
										<td>dolor</td>
										<td>ipsum</td>
										<td>dolor</td>
										<td>sit</td>
										<td>sit</td>
										<td>sit</td>
										<td>sit</td>
										<td>sit</td>
									</tr>
									<tr>
										<td>libero</td>
										<td>Sed</td>
										<td class="table-info">cursus</td>
										<td>ipsum</td>
										<td>dolor</td>
										<td>ipsum</td>
										<td>dolor</td>
										<td>ipsum</td>
										<td>dolor</td>
										<td>sit</td>
										<td>sit</td>
										<td>sit</td>
										<td>sit</td>
										<td>sit</td>
									</tr>
									<tr>
										<td>libero</td>
										<td>Sed</td>
										<td class="table-info">cursus</td>
										<td>ipsum</td>
										<td>dolor</td>
										<td>ipsum</td>
										<td>dolor</td>
										<td>ipsum</td>
										<td>dolor</td>
										<td>sit</td>
										<td>sit</td>
										<td>sit</td>
										<td>sit</td>
										<td>sit</td>
									</tr>
									<tr>
										<td>libero</td>
										<td>Sed</td>
										<td class="table-info">cursus</td>
										<td>ipsum</td>
										<td>dolor</td>
										<td>ipsum</td>
										<td>dolor</td>
										<td>ipsum</td>
										<td>dolor</td>
										<td>sit</td>
										<td>sit</td>
										<td>sit</td>
										<td>sit</td>
										<td>sit</td>
									</tr>
								</tbody>
							</table>
						</div>
						<!-- 分页栏 -->
						<nav aria-label="Page navigation">
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
		<div class="modal fade" id="newUser" tabindex="-1" role="dialog"
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
						<div class="container-fluid">Add rows here</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-secondary"
							data-dismiss="modal">关闭</button>
						<button type="button" class="btn btn-primary">新建</button>
					</div>
				</div>
			</div>
		</div>
		<!-- Modal 4 筛选 -->
		<div class="modal fade" id="selectUsers" tabindex="-1" role="dialog"
			aria-labelledby="selectUsers" aria-hidden="true">
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
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-secondary"
							data-dismiss="modal">关闭</button>
						<button type="button" class="btn btn-primary">确定</button>
					</div>
				</div>
			</div>
		</div>


		<!-- container结束 -->
	</div>

	<!-- Optional JavaScript -->
	<!-- jQuery first, then Popper.js, then Bootstrap JS -->
	<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
		integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
		crossorigin="anonymous"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"
		integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
		crossorigin="anonymous"></script>
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"
		integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
		crossorigin="anonymous"></script>
	<script type="text/javascript" src="http://localhost/weixin/js/myJS.js"></script>
</body>
</html>