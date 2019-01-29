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
								<s:property value="#title"/>
							</h1>
							<div class="btn-toolbar mb-2 mb-md-0">
								<div class="btn-group mr-2">

									<button class="btn btn-sm btn-outline-secondary"
										data-toggle="modal" data-target="#selectProjects">
										<span class="glyphicon glyphicon-search"></span> 筛选
									</button>
									<button class="btn btn-sm btn-outline-secondary"
										data-toggle="modal" data-target="#orderProjects">
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
											<a class="dropdown-item" href="#"
												onclick="userModal.op.batchCreateQR();">批量重建二维码</a> <a
												class="dropdown-item disabled" href="#">Disabled action</a>
											<h6 class="dropdown-header">Section header</h6>
											<a class="dropdown-item" href="#">Action</a>
											<div class="dropdown-divider"></div>
											<a class="dropdown-item" href="#">After divider action</a>
										</div>
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
										<th>项目</th>
										<th>活动名</th>
										<th>活动描述</th>
										<th>开始日期</th>
										<th>结束日期</th>
										<th>参与限制</th>
										<th>参与者</th>
										<th>积分奖励</th>
										<th>状态</th>
										<th>操作</th>
									</tr>
								</thead>
								<tbody>
									<s:iterator value="#activities">
										<tr>
											<td><s:property value="dpName" /></td>
											<td><s:a href="#" onclick="activityModal.op.showDetialModal('%{aid}');"><s:property value="name" /></s:a></td>
											<td><s:property value="description" /></td>
											<td><s:property value="beginTimeStr" /></td>
											<td><s:property value="endTimeStr" /></td>
											<td>
												<s:if test="%{type==1}">开放报名</s:if>
												<s:elseif test="%{type==2}">限定人数</s:elseif>
												<s:else>缺失</s:else>
											</td>
											<td>
												<s:a href="#"
													onclick="toVisitorList('%{aid}');">
													<s:property value="visitors.size()" />
												</s:a>
											</td>
											<td><s:property value="score" /></td>
											<td>
												<s:if test="%{state=='筹备中'}">筹备中</s:if>
												<s:elseif test="%{state=='进行中'}">进行中</s:elseif>
												<s:elseif test="%{state=='已取消'}">已取消</s:elseif>
												<s:elseif test="%{state=='已完成'}">已完成</s:elseif>
												<s:elseif test="%{state=='即将开始'}">即将开始</s:elseif>
											</td>
											<td>
												<div class="btn-group" role="group">
													<s:a cssClass="btn btn-sm btn-outline-secondary"
														role="button"
														onclick="activityModal.op.showVisitors('%{aid}');"
														href="#">补签</s:a>
													<s:a cssClass="btn btn-sm btn-outline-secondary"
														role="button"
														onclick="toArticlePage('%{aid}');"
														href="#">活动记录</s:a>
													<s:a cssClass="btn btn-sm btn-outline-secondary"
														role="button"
														onclick="activityModal.op.showVisitors('%{aid}');"
														href="#">取消</s:a>
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

		<!-- Modal4项目详情 -->
		<div class="modal fade" id="detialModal" tabindex="-1" role="dialog"
			aria-labelledby="detialModal" aria-hidden="true">
			<div class="modal-dialog modal-lg" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<h4 class="modal-title" id="detial4title">活动A-信息详情</h4>
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
										id="detial4qrcode" alt="二维码丢失">
								</div>
								<div class="col-lg-10 pl-0 col-xs-12">
									<div class="row">
										<div class="col-3 text-right font-weight-bold text-truncate">
											活动名:</div>
										<div class="col-9 text-left text-truncate"
											id="detial4Name">志愿者服务</div>
									</div>
									<div class="row">
										<div class="col-3 text-right font-weight-bold text-truncate">
											活动描述:</div>
										<div class="col-9 text-left text-truncate"
											id="detial4Description">项目描述</div>
									</div>
									<div class="row">
										<div class="col-3 text-right font-weight-bold text-truncate">
											活动地点:</div>
											<div class="col-9 text-left text-truncate"
											id="detial4PositionName">房屋名或地点名</div>
									</div>
									<div class="row">
										<div class="col-3 text-right font-weight-bold text-truncate">
											AID:</div>
										<div class="col-9 text-left text-truncate"
											id="detial4Aid">
											5d7323e0-70b5-4ff6-9c77-3bdd70a507f1</div>
									</div>
									<div class="row">
										<div class="col-3 text-right font-weight-bold text-truncate">
											开始时间:</div>
										<div class="col-9 text-left text-truncate"
											id="detial4StartTime">2018-12-22 12:56:06</div>
									</div>
									<div class="row">
										<div class="col-3 text-right font-weight-bold text-truncate">
											结束时间:</div>
										<div class="col-9 text-left text-truncate"
											id="detail4EndTime">2018-12-22 12:56:06</div>
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
<script src="http://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/myJS.js"></script>
<script type="text/javascript"
	src="https://res.wx.qq.com/open/libs/weuijs/1.1.4/weui.min.js"></script>
<script>
	
	// 跳转到活动的新闻稿页面
	function toArticlePage(aid){
		$(location).attr("href","articleAction_getArticle.action?aid="+aid);
	}
	
	// 跳转到活动的报名参与者列表页面
	function toVisitorList(aid){
		let url  =  "activityAction_getVisitorList.action?"+"aid="+aid;
		$(location).attr("href",url);
	}
	
	
</script>
</html>