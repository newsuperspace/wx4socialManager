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
								<s:property value="#zeroLevel.name" />
								-社区积分兑换品列表
								<button class="btn btn-sm btn-light" id="button4selectorPanel"
									data-toggle="collapse" data-target="#selectorPanel"
									onclick="changeIcon();">
									<span class="glyphicon glyphicon-chevron-down"
										id="icon4selectorPanel"></span>
								</button>
							</h1>
							<div class="btn-toolbar mb-2 mb-md-0">
								<div class="btn-group mr-2">

									<button class="btn btn-sm btn-outline-secondary" onclick="toCreateWarePage();">
										<span class="glyphicon glyphicon-search"></span> 新建
									</button>
									<button class="btn btn-sm btn-outline-secondary"
										data-toggle="modal" data-target="#selectUsers">
										<span class="glyphicon glyphicon-search"></span> 筛选
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

						<!-- =============Selectors=========== -->
						<div class="collapse mb-2" id="selectorPanel">
							<div class="card card-body">
								<div class="row">
									<div class="col-md-2">
										禁用/启用 <select class="form-control form-control-sm"
											id="minusFirst" name="minusFirst" disabled="true"
											onchange="getData4Selector(this);">
											<option value="0" selected>--请选择--</option>
										</select>
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
										<th>品名</th>
										<th>描述</th>
										<th>创建日期</th>
										<th>状态</th>
										<th>分值</th>
										<th>剩余数量</th>
										<th>累计兑换</th>
										<th>操作</th>
									</tr>
								</thead>
								<tbody id="tbody">
									<s:iterator value="#wares">
										<tr>
											<td><s:a href="#" onclick="wareInfo('%{wid}')">
													<s:property value="wname" />
												</s:a></td>
											<td><s:property value="description" /></td>
											<td><s:property value="str4CreateDate" /></td>
											<td><s:if test="%{canUse}">
													可兑换
												</s:if> <s:else>
													不可用
												</s:else></td>
											<td><s:property value="score" /></td>
											<td><s:property value="surplus" /></td>
											<td><s:a href="#" onclick="toExchangeList('%{wid}')">
													<s:property value="total" />
												</s:a></td>
											<td>
												<div class="btn-group" role="group">
													<s:a href="#" onclick="update('%{wid}');"
														class="btn btn-outline-secondary btn-sm">设置</s:a>
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





		<!-- container结束 -->
	</div>
</body>
<script src="${pageContext.request.contextPath}/js/jquery-3.2.0.js"></script>
<script src="${pageContext.request.contextPath}/js/popper.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.bundle.js"></script>

<script src="http://res.wx.qq.com/open/js/jweixin-1.4.0.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/myJS.js"></script>
<script type="text/javascript"
	src="https://res.wx.qq.com/open/libs/weuijs/1.1.4/weui.min.js"></script>
<script type="text/javascript">

	// 获取商品二维码信息
	function wareInfo(wid) {
		// TODO
		weui.alert(wid);
	}

	// 更新商品信息之用
	function update(wid) {
		// TODO
		weui.alert(wid);
	}

	// 跳转到指定商品的兑换历史列表
	function toExchangeList(wid) {
		weui.alert(wid);
	}

	// 用作开启/关闭商品筛选的selector
	function changeIcon() {
		let $icon = $("#icon4selectorPanel");
		let $panel = $("#selectorPanel");

		if ($icon.attr("class") == "glyphicon glyphicon-chevron-down") {
			$panel.collapse('show');
			$icon.attr("class", "glyphicon glyphicon-chevron-up");
		} else {
			$panel.collapse('hide');
			$icon.attr("class", "glyphicon glyphicon-chevron-down");
		}
	}
	
	function getData4Selector(){
	
	}
	
	// 跳转到创建商品页面
	function toCreateWarePage(){
		$(location).attr("href","wareAction_toCreateWarePage.action");
	}
	
	
	
</script>

</html>