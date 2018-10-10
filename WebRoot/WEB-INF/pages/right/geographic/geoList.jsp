<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@taglib uri="/WEB-INF/tlds/myShiro.tld" prefix="myShiro"%>
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
<link rel="stylesheet"
	href="https://res.wx.qq.com/open/libs/weui/1.1.3/weui.min.css">
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
							<h1 class="h2">位置坐标管理列表</h1>
							<!-- ● -->
							<div class="btn-toolbar mb-2 mb-md-0">
								<div class="btn-group mr-2">
									<shiro:hasPermission name="zero:geo:createGeo">
										<button class="btn btn-sm btn-outline-secondary"
											data-toggle="modal"
											onclick="geoModal.op.showCreateModal();">
											<span class="glyphicon glyphicon-plus"></span> 新建
										</button>
									</shiro:hasPermission>
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
										<!-- ● -->
										<th>描述</th>
										<th>经度</th>
										<th>纬度</th>
										<th>活动数</th>
										<th>状态</th>
										<!-- ● -->
										<th>操作</th>
									</tr>
								</thead>
								<tbody>
									<s:iterator value="#geos">
										<tr>
											<td><s:a href="#"
													onclick="geoModal.op.geoInfo('%{geoid}')">
													<!-- ● -->
													<s:property value="name" />
												</s:a></td>
											<!-- ● -->
											<td><s:property value="description" /></td>
											<td><s:property value="longitude"/></td>
											<td><s:property value="latitude"/></td>
											<td><s:property value="activities.size()"/></td>
											<td>
												<s:if test="enable">
													使用中
												</s:if> <s:else>
													已停用
												</s:else>
											</td>
											<!-- ● -->
											<td>
												<myShiro:hasAnyPermissions name="admin,minus_first:geo:updateGeo,zero:geo:updateGeo,first:geo:updateGeo,second:geo:updateGeo,third:geo:updateGeo,fourth:geo:updateGeo">
													<div class="btn-group" role="group">
														<!-- ● -->
														<s:a cssClass="btn btn-sm btn-outline-secondary"
															role="button"
															onclick="geoModal.op.showUpdateModal('%{geoid}');"
															href="#">修改</s:a>

														<s:if test="enable">
															<s:a cssClass="btn btn-sm btn-outline-secondary"
																role="button"
																onclick="geoModal.op.closeGeo('%{geoid}');" href="#">关闭</s:a>
														</s:if>
														<s:else>
															<s:a cssClass="btn btn-sm btn-outline-secondary"
																role="button"
																onclick="geoModal.op.openGeo('%{geoid}');" href="#">启用</s:a>
														</s:else>

														<s:a cssClass="btn btn-sm btn-outline-secondary"
															role="button"
															onclick="geoModal.op.deleteGeo('%{geoid}');" href="#">删除</s:a>
														
													</div>
												</myShiro:hasAnyPermissions>
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

		<!-- Modal 4 新建活动室-->
		<div class="modal fade" id="createModal" tabindex="-1" role="dialog"
			aria-labelledby="newModal" aria-hidden="true">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<!-- ● -->
						<h4 class="modal-title">新建活动地点</h4>
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
							
							<div class="input-group input-group-sm mb-3">
								<div class="input-group-prepend">
									<span class="input-group-text"><span
										class="glyphicon glyphicon-glass"></span></span>
								</div>
								<input type="text" class="form-control" name="latitude"
									id="latitude">
								<div class="input-group-prepend">
									<span class="input-group-text">纬度</span>
								</div>
							</div>

							<div class="input-group input-group-sm mb-3">
								<div class="input-group-prepend">
									<span class="input-group-text"><span
										class="glyphicon glyphicon-glass"></span></span>
								</div>
								<input type="text" class="form-control" name="longitude"
									id="longitude">
								<div class="input-group-prepend">
									<span class="input-group-text">经度</span>
								</div>
							</div>
							
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-secondary"
							data-dismiss="modal">关闭</button>
						<!-- ● -->
						<button type="button" class="btn btn-primary"
							onclick="geoModal.op.createGeo();">新建</button>
					</div>
				</div>
			</div>
		</div>
		<!-- Modal 更新信息 -->
		<div class="modal fade" id="updateModal" tabindex="-1" role="dialog"
			aria-labelledby="newModal" aria-hidden="true">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<!-- ● -->
						<h4 class="modal-title">更新活动地点</h4>
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="modal-body">
						<div class="container-fluid">

							<input type="hidden" id="geoid4update">

							<div class="input-group input-group-sm mb-3">
								<div class="input-group-prepend">
									<span class="input-group-text"><span
										class="glyphicon glyphicon-user"></span></span>
								</div>
								<input type="text" class="form-control" name="name4update"
									id="name4update">
								<div class="input-group-prepend">
									<span class="input-group-text">名称</span>
								</div>
							</div>

							<div class="input-group input-group-sm mb-3">
								<div class="input-group-prepend">
									<span class="input-group-text"><span
										class="glyphicon glyphicon-glass"></span></span>
								</div>
								<input type="text" class="form-control"
									name="description4update" id="description4update">
								<div class="input-group-prepend">
									<span class="input-group-text">描述</span>
								</div>
							</div>

							<div class="input-group input-group-sm mb-3">
								<div class="input-group-prepend">
									<span class="input-group-text"><span
										class="glyphicon glyphicon-glass"></span></span>
								</div>
								<input type="text" class="form-control" name="latitude4update"
									id="latitude4update">
								<div class="input-group-prepend">
									<span class="input-group-text">纬度</span>
								</div>
							</div>

							<div class="input-group input-group-sm mb-3">
								<div class="input-group-prepend">
									<span class="input-group-text"><span
										class="glyphicon glyphicon-glass"></span></span>
								</div>
								<input type="text" class="form-control" name="longitude4update"
									id="longitude4update">
								<div class="input-group-prepend">
									<span class="input-group-text">经度</span>
								</div>
							</div>

							<div class="input-group input-group-sm mb-3">
								<div class="input-group-prepend">
									<span class="input-group-text"><span
										class="glyphicon glyphicon-glass"></span></span>
								</div>
								<input type="text" class="form-control" name="radus4update"
									id="radus4update">
								<div class="input-group-prepend">
									<span class="input-group-text">半径(米)</span>
								</div>
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-secondary"
							data-dismiss="modal">关闭</button>
						<button type="button" class="btn btn-info"
							onclick="getPosition();">自动获取地理位置</button>
						<!-- ● -->
						<button type="button" class="btn btn-primary"
							onclick="geoModal.op.updateGeo();">更新</button>
					</div>
				</div>
			</div>
		</div>
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
<script src="http://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/myJS.js"></script>
<script type="text/javascript"
	src="https://res.wx.qq.com/open/libs/weuijs/1.1.4/weui.min.js"></script>
<script type="text/javascript">
	function getPosition(){
		wx.getLocation({
			type : 'gcj02', // 默认为wgs84的gps坐标，如果要返回直接给openLocation用的火星坐标，可传入'gcj02'
			success : function(res) {
				//使用微信内置地图查看位置接口
				latitude = res.latitude; // 纬度，浮点数，范围为90 ~ -90
				longitude = res.longitude; // 经度，浮点数，范围为180 ~ -180。
				speed = res.speed; // 速度，以米/每秒计
				accuracy = res.accuracy; // 位置精度
			// 下面是打开腾讯地图的API，暂时不用
			//wx.openLocation({
			//latitude : res.latitude, // 纬度，浮点数，范围为90 ~ -90
			//longitude : res.longitude, // 经度，浮点数，范围为180 ~ -180。
			//name : '我的位置', // 位置名
			//address : '329创业者社区', // 地址详情说明
			//scale : 28, // 地图缩放级别,整形值,范围从1~28。默认为最大
			//infoUrl : 'http://www.gongjuji.net' // 在查看位置界面底部显示的超链接,可点击跳转（测试好像不可用）
			//});
				$("#longitude4update").val(longitude);
				$("#latitude4update").val(latitude);
			},
			cancel : function(res) {}
		});
	}
	
	
</script>
</html>

