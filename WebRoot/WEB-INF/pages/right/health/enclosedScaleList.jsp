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
							<h1 class="h2">
								封闭式量表库
								<button class="btn btn-sm btn-light" id="button4selectorPanel"
									data-toggle="collapse" data-target="#selectorPanel"
									onclick="changeIcon();">
									<span class="glyphicon glyphicon-chevron-down"
										id="icon4selectorPanel"></span>
								</button>
							</h1>
							<div class="btn-toolbar mb-2 mb-md-0">
								<div class="btn-group mr-2">

									<button class="btn btn-sm btn-outline-secondary"
										onclick="$(location).attr('href','healthAction_toCreateEnclosedScalePage.action');">
										<span class="glyphicon glyphicon-search"></span> 新建量表
									</button>
									<button class="btn btn-sm btn-outline-secondary"
										data-toggle="modal" data-target="#selectUsers">
										<span class="glyphicon glyphicon-sort-by-order"></span> 排序
									</button>
									<div class="dropdown ml-1">
										<button
											class="btn btn-sm btn-outline-secondary dropdown-toggle"
											type="button" id="others" data-toggle="dropdown"
											aria-haspopup="true" aria-expanded="false">
											<span class="glyphicon glyphicon-cog"></span> 其他
										</button>
										<div class="dropdown-menu dropdown-menu-right"
											aria-labelledby="others">
											<a class="dropdown-item disabled" href="#">Disabled
												action</a>
											<h6 class="dropdown-header">Section header</h6>
											<a class="dropdown-item" href="#">Action</a>
											<div class="dropdown-divider"></div>
											<a class="dropdown-item" href="#">After divider action</a>
										</div>
									</div>
								</div>
							</div>
						</div>
						<!-- =============标题结束=========== -->

						<!-- =============Selector开始=========== -->
						<div class="collapse mb-2" id="selectorPanel">
							<div class="card card-body">
								<div class="row">
									<div class="col-md-2">
										街道层级 <select class="form-control form-control-sm"
											id="minusFirst" name="minusFirst" disabled="true"
											onchange="getData4Selector(this);">
											<option value="0" selected>--请选择--</option>
										</select>
									</div>
									<div class="col-md-2">
										社区层级 <select class="form-control form-control-sm" id="zero"
											name="zero" disabled="true"
											onchange="getData4Selector(this);">
											<option value="0" selected>--请选择--</option>
										</select>
									</div>
									<div class="col-md-2">
										第一层级 <select class="form-control form-control-sm" id="first"
											name="first" disabled="true"
											onchange="getData4Selector(this);">
											<option value="0" selected>--请选择--</option>
										</select>
									</div>
									<div class="col-md-2">
										第二层级 <select class="form-control form-control-sm" id="second"
											name="second" disabled="true"
											onchange="getData4Selector(this);">
											<option value="0" selected>--请选择--</option>
										</select>
									</div>
									<div class="col-md-2">
										第三层级 <select class="form-control form-control-sm" id="third"
											name="third" disabled="true"
											onchange="getData4Selector(this);">
											<option value="0" selected>--请选择--</option>
										</select>
									</div>
									<div class="col-md-2">
										第四层级 <select class="form-control form-control-sm" id="fourth"
											name="fourth" disabled="true"
											onchange="getData4Selector(this);">
											<option value="0" selected>--请选择--</option>
										</select>
									</div>
								</div>
							</div>
						</div>
						<!-- =============Selector结束=========== -->

						<!-- =============表格开始=========== -->
						<div
							style="
                        white-space: nowrap;
                        overflow-x: hidden;
                        overflow-x: auto;">
							<table
								class="table table-striped table-sm table-bordered table-hover text-center">
								<thead class="thead-dark">
									<tr>
										<th>中文名</th>
										<th>英文名</th>
										<th>创建时间</th>
										<th>版本号</th>
										<th>因子数</th>
										<th>题目数</th>
										<th>样本量</th>
										<th>操作</th>
									</tr>
								</thead>
								<tbody id="tbody">
									<s:iterator value="#list">
										<tr>
											<td><s:a href="#" onclick="enclosedScaleInfo('%{esid}')">
													<s:property value="chName" />
												</s:a></td>
											<td><s:property value="engName" /></td>
											<td><s:property value="date" /></td>
											<td><s:a href="#" onclick="">
													<s:property value="version" />
												</s:a></td>
											<td><s:property value="factors.size()" /></td>
											<td><s:property value="topics.size()" /></td>
											<td><s:property value="samples.size()" /></td>
											<td>
												<div class="btn-group" role="group">
													<button type="button"
														class="btn btn-outline-secondary btn-sm">新样本</button>
													<button type="button"
														class="btn btn-outline-secondary btn-sm">旧样本</button>
													<button type="button"
														class="btn btn-outline-secondary btn-sm">更新</button>
												</div>
											</td>
										</tr>
									</s:iterator>
								</tbody>
							</table>
						</div>
						<!-- 表格结束 -->
						<!-- 分页栏开始 -->
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
						<!-- 分页栏结束 -->

					</div>
				</div>
			</div>
		</div>
		<!-- =================================================模态对话框==================================================== -->

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
<!-- popper.js必须在bootstrap.js之前被加载否则无法使用弹出菜单 -->
<script src="${pageContext.request.contextPath}/js/popper.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.bundle.js"></script>

<script src="http://res.wx.qq.com/open/js/jweixin-1.4.0.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/myJS.js"></script>
<script type="text/javascript"
	src="https://res.wx.qq.com/open/libs/weuijs/1.2.1/weui.min.js"></script>
<script type="text/javascript">

	// 从服务器获取量表详细信息，显示特定页面单元中并展示出来
	function enclosedScaleInfo(esid) {
		weui.alert(esid);
	}

	// 跳转到
</script>

</html>