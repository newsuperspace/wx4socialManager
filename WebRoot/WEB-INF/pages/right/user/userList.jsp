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

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/weui-v2.1.3/weui.css">
<link
	href="https://cdn.bootcss.com/awesome-bootstrap-checkbox/v0.2.3/awesome-bootstrap-checkbox.css"
	rel="stylesheet" />
<link
	href="${pageContext.request.contextPath}/js/jquery-ui/jquery-ui.css"
	rel="stylesheet" />
<link
	href="${pageContext.request.contextPath}/js/easyui-v1.7.0/themes/icon.css"
	rel="stylesheet" />
<link
	href="${pageContext.request.contextPath}/js/easyui-v1.7.0/themes/default/easyui.css"
	rel="stylesheet" />
<link
	href="${pageContext.request.contextPath}/js/layui-v2.5.5/layui/css/layui.css"
	rel="stylesheet" />
<link
	href="${pageContext.request.contextPath}/js/picker-extend/picker-extend.css"
	rel="stylesheet" />
<!-- 
	layui 中会对所有<a> 标签进行颜色上的样式设定。
	CSS的所有样式冲突遵循，后加载的样式覆盖之前加载的样式，因此为了保持所有页面基于bootstrap的表现基础
	我们需要最后再加载bootstrap样式
 -->
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
								非直辖人员信息
								<button class="btn btn-sm btn-light" id="button4selectorPanel"
									data-toggle="collapse" data-target="#selectorPanel"
									onclick="userModal.op.changeIcon();">
									<span class="glyphicon glyphicon-chevron-down"
										id="icon4selectorPanel"></span>
								</button>
							</h1>
							<div class="btn-toolbar mb-2 mb-md-0">
								<div class="btn-group mr-2">

									<div id="levelSelector" hidden="hidden"></div>
									<button class="btn btn-sm btn-outline-secondary"
										onclick="showLevelSelector();">
										<span class="glyphicon glyphicon-search"></span> 筛选
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
											<a class="dropdown-item" href="#" onclick="alert('AAAAA');">操作A</a>
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

						<!-- =============Selector开始=========== -->
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
										<th>用户名</th>
										<th>昵称</th>
										<th>性别</th>
										<th>年龄</th>
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
								<tbody id="tbody">


								</tbody>
							</table>
						</div>
						<!-- 表格结束 -->
						<!-- 分页栏开始 -->
						<div class="row mt-2">
							<div class="col"></div>
							<div class="col-auto">
								<div id="laypage"></div>
							</div>
							<div class="col"></div>
						</div>
						<!-- 分页栏结束 -->

					</div>
				</div>
			</div>
		</div>
		<!-- =================================================模态对话框==================================================== -->

		<!-- Modal4用户详情 -->
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
										id="detialsModal_qrcode" alt="二维码丢失">
								</div>
								<div class="col-lg-10 pl-0 col-xs-12">
									<div class="row">
										<div class="col-3 text-right font-weight-bold text-truncate">
											用户名:</div>
										<div class="col-9 text-left text-truncate"
											id="detialsModal_username">XX</div>
									</div>
									<div class="row">
										<div class="col-3 text-right font-weight-bold text-truncate">
											UID:</div>
										<div class="col-9 text-left text-truncate"
											id="detialsModal_uid">XX</div>
									</div>
									<div class="row">
										<div class="col-3 text-right font-weight-bold text-truncate">
											OpenID:</div>
										<div class="col-9 text-left text-truncate"
											id="detialsModal_openid">XX</div>
									</div>
									<div class="row">
										<div class="col-3 text-right font-weight-bold text-truncate">
											注册时间:</div>
										<div class="col-9 text-left text-truncate"
											id="detialsModal_registrationTime">XX</div>
									</div>
									<!-- 
									<div class="row">
										<div class="col-3 text-right font-weight-bold text-truncate">
											街道:</div>
										<div class="col-9 text-left text-truncate"
											id="detialsModal_minusFirst">XX</div>
									</div>
									<div class="row">
										<div class="col-3 text-right font-weight-bold text-truncate">
											社区:</div>
										<div class="col-9 text-left text-truncate"
											id="detialsModal_zero">XX</div>
									</div>
									<div class="row">
										<div class="col-3 text-right font-weight-bold text-truncate">
											第1层级:</div>
										<div class="col-9 text-left text-truncate"
											id="detialsModal_first">XX</div>
									</div>
									<div class="row">
										<div class="col-3 text-right font-weight-bold text-truncate">
											第2层级:</div>
										<div class="col-9 text-left text-truncate"
											id="detialsModal_second">XX</div>
									</div>
									<div class="row">
										<div class="col-3 text-right font-weight-bold text-truncate">
											第3层级:</div>
										<div class="col-9 text-left text-truncate"
											id="detialsModal_third">XX</div>
									</div>
									<div class="row">
										<div class="col-3 text-right font-weight-bold text-truncate">
											第4层级:</div>
										<div class="col-9 text-left text-truncate"
											id="detialsModal_fourth">XX</div>
									</div>
									 -->
								</div>
							</div>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-secondary"
								data-dismiss="modal">关闭</button>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- Modal 4 用户更改 -->

		<!-- Modal 4 排序 -->

		<!-- container结束 -->
	</div>
</body>
<!-- 必要JS -->
<script src="${pageContext.request.contextPath}/js/jquery-3.2.0.js"></script>
<script
	src="${pageContext.request.contextPath}/js/jquery-ui/jquery-ui.js"></script>
<!-- popper.js必须在bootstrap.js之前被加载否则无法使用弹出菜单 -->
<script src="${pageContext.request.contextPath}/js/popper.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.bundle.js"></script>
<script
	src="${pageContext.request.contextPath}/js/easyui-v1.7.0/jquery.easyui.min.js"></script>
<script
	src="${pageContext.request.contextPath}/js/layui-v2.5.5/layui/layui.js"></script>
<!-- 独占脚本 -->
<script
	src="${pageContext.request.contextPath}/js/userAndManager/js4userList.js"></script>
<script
	src="${pageContext.request.contextPath}/js/picker-extend/picker-extend.js"></script>

<!-- wechat相关 -->
<script src="http://res.wx.qq.com/open/js/jweixin-1.4.0.js"></script>
<script src="https://res.wx.qq.com/open/libs/weuijs/1.2.1/weui.min.js"></script>
<script src="${pageContext.request.contextPath}/js/myJS.js"></script>
<script type="text/javascript">

	
</script>
</html>