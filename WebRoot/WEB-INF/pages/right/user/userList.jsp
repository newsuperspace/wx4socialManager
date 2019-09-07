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
								非直辖人员信息
								<button class="btn btn-sm btn-light" id="button4selectorPanel" data-toggle="collapse" data-target="#selectorPanel"
                                    onclick="userModal.op.changeIcon();">
                                    <span class="glyphicon glyphicon-chevron-down" id="icon4selectorPanel"></span>
                                </button>
							</h1>
							<div class="btn-toolbar mb-2 mb-md-0">
								<div class="btn-group mr-2">
									
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
										<div class="dropdown-menu dropdown-menu-right" aria-labelledby="others">
											<a class="dropdown-item" href="#"
												onclick="userModal.op.batchCreateQR();">批量重建二维码</a>
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
                                        街道层级
                                        <select class="form-control form-control-sm" id="minusFirst" name="minusFirst" disabled="true" onchange="userModal.op.getData4Selector(this);"> 
                                            <option value="0" selected>--请选择--</option>
                                        </select>
                                    </div>
                                    <div class="col-md-2">
                                        社区层级
                                        <select class="form-control form-control-sm" id="zero" name="zero" disabled="true" onchange="userModal.op.getData4Selector(this);">
                                            <option value="0" selected>--请选择--</option>
                                        </select>
                                    </div>
                                    <div class="col-md-2">
                                        第一层级
                                        <select class="form-control form-control-sm" id="first" name="first" disabled="true" onchange="userModal.op.getData4Selector(this);">
                                            <option value="0" selected>--请选择--</option>
                                        </select>
                                    </div>
                                    <div class="col-md-2">
                                        第二层级
                                        <select class="form-control form-control-sm" id="second" name="second" disabled="true" onchange="userModal.op.getData4Selector(this);">
                                            <option value="0" selected>--请选择--</option>
                                        </select>
                                    </div>
                                    <div class="col-md-2">
                                        第三层级
                                        <select class="form-control form-control-sm" id="third" name="third" disabled="true" onchange="userModal.op.getData4Selector(this);">
                                            <option value="0" selected>--请选择--</option>
                                        </select>
                                    </div>
                                    <div class="col-md-2">
                                        第四层级
                                        <select class="form-control form-control-sm" id="fourth" name="fourth" disabled="true" onchange="userModal.op.getData4Selector(this);">
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
									<s:iterator value="#users">
										<tr>
											<td><s:a href="#"
													onclick="userModal.op.userInfo('%{uid}')">
													<s:property value="username" />
												</s:a></td>
											<td><s:property value="sickname" /></td>
											<td><s:property value="sex" /></td>
											<td><s:property value="age" /></td>
											<td><s:property value="serveTime" /></td>
											<td><s:a href="#" onclick="toUserVisitList('%{uid}')"><s:property value="score" /></s:a></td>
											<td><s:property value="phone" /></td>
											<td class="text-truncate" data-toggle="tooltip"
												title="<s:property value='address'/>"><s:property
													value="address" /></td>
											<td><s:property value="email" /></td>
											<td><s:property value="cardid" /></td>
											<td><s:property value="registrationTimeStr" /></td>
											<td><s:if test="ishere">是</s:if> <s:else>否</s:else></td>
											<td><s:if test="locked">
													<span class="badge badge-danger">锁死</span>
												</s:if> <s:else>
													<span class="badge badge-success">正常</span>
												</s:else></td>
											<td>
												<div class="btn-group" role="group">
													<button type="button"
														class="btn btn-outline-secondary btn-sm">推送</button>
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

<script src="http://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/myJS.js"></script>
<script type="text/javascript"
	src="https://res.wx.qq.com/open/libs/weuijs/1.1.4/weui.min.js"></script>
<script type="text/javascript">
	// 本内部脚本用于初始化当前页面——userList.jsp中的一些组件
	$(function(){
		// 初始化selector面板
		userModal.init.initSelector();
		// 其他初始化工作...
		
	});
	
	
	function toUserVisitList(uid){
		$(location).attr("href","userAction_toUserVisitList.action?uid="+uid);
	}
	

</script>
	
</html>