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
	<!-- container开始 -->
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
							<h1 class="h2">加入组织的申请</h1>
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
										<th>申请人</th>
										<th>性别</th>
										<th>年龄</th>
										<th>电话</th>
										<th>住址</th>
										<th>邮箱</th>
										<th>注册日期</th>
										<th>操作</th>
									</tr>
								</thead>
								<tbody>
									<s:iterator value="#applies">
										<tr>
											<td><s:a href="#" onclick="alert('%{ua4jlid}');">
													<s:property value="user.username" />
												</s:a></td>
											<td><s:property value="user.sex" /></td>
											<td><s:property value="user.age" /></td>
											<td><s:property value="user.phone" /></td>
											<td class="text-truncate" data-toggle="tooltip"
												title="<s:property value='user.address'/>"><s:property
													value="user.address" /></td>
											<td><s:property value="user.email" /></td>
											<td><s:property value="timeStamp" /></td>

											<td>
												<div class="btn-group" role="group">
													<s:a class="btn btn-outline-secondary btn-sm" href="#"
														onclick="showInfoModal4Apply('%{ua4jlid}')">申请详情</s:a>
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
		<!-- infoModal4Apply - START -->
		<div class="modal fade" id="infoModal4Apply" tabindex="-1"
			role="dialog" aria-labelledby="infoModal4ApplyTitle"
			aria-hidden="true" data-backdrop="static">
			<div class="modal-dialog " role="document">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title" id="infoModal4ApplyTitle">提交的加入申请</h5>
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="modal-body">

						<form>
							<input type="hidden" id="tag" /> <input type="hidden" id="lid" />
							<input type="hidden" id="username" /> <input type="hidden"
								id="ua4jlid" />
							<div>
								<label for="recipient-name" class="col-form-label">微信昵称:</label>
								<input type="text" class="form-control" id="sickname"
									disabled="true">
							</div>
							<div>
								<label for="recipient-name" class="col-form-label">联系电话:</label>
								<input type="text" class="form-control" id="phone"
									disabled="true">
							</div>
							<div>
								<label for="recipient-name" class="col-form-label">申请原因:</label>
								<textarea class="form-control" id="theReason" disabled="true"
									cols="1"></textarea>
							</div>
							<div>
								<label for="recipient-name" class="col-form-label">我的专长:</label>
								<textarea class="form-control" id="theExpertise" disabled="true"
									cols="1"></textarea>
							</div>
							<div>
								<label for="recipient-name" class="col-form-label">加入后的期许:</label>
								<textarea class="form-control" id="theDesire" disabled="true"
									cols="1"></textarea>
							</div>

							<div class="form-group">
								<label for="message-text" class="col-form-label">回复信息:</label>
								<textarea class="form-control" id="message-text" cols="3"
									id="message"></textarea>
							</div>
						</form>

					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-success"
							onclick="theMessageIsEmpty();agree();">同意</button>
						<button type="button" class="btn btn-danger"
							onclick="theMessageIsEmpty();disagree();">不同意</button>
						<button type="button" class="btn btn-secondary"
							data-dismiss="modal">关闭</button>
					</div>
				</div>
			</div>
		</div>
		<!-- infoModal4Apply - END -->

	</div>
	<!-- container结束 -->
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

	// 【AJAX】显示某一申请的详情信息的modal
	function showInfoModal4Apply(ua4jlid) {
		let url = "approveAction_getInfo4UserLevelApply.action";
		let param = {
			'ua4jlid' : ua4jlid
		}
		$.post(url, param, function(data, textStatus, req) {
			// 返回的data就是一个完整UserApply4JoinLevel对象，抽取数据显示到Modal上
			$("#tag").val(data.approve4UserJoinLevel.tag);
			$("#lid").val(data.approve4UserJoinLevel.lid);
			$("#username").val(data.user.username);
			$("#ua4jlid").val(data.ua4jlid);

			$("#infoModal4ApplyTitle").val(data.user.username + $("#infoModal4ApplyTitle").val());
			$("#sickname").val(data.user.sickname);
			$("#phone").val(data.user.phone);
			$("#theReason").text(data.theReason);
			$("#theExpertise").text(data.theExpertise);
			$("#theDesire").text(data.theDesire);
		});

		// 最后在通过jQuery打开Modal显示数据
		$("#infoModal4Apply").modal("show");
	}

	// 当用户点击Modal上的“同意”或“不同意”的时候会先出发本判断方法，用来判断message是否填写了审核意见
	function theMessageIsEmpty() {
		let $message = $("#message");
		if ("" == $message.text() || null == $message.text()) {
			weui.alert("请在“回复信息”中填写审核意见后再试。");
		}
	}

	// 【AJAX】同意申请
	function agree() {
		let $message = $("#message");
		if ("" == $message.text() || null == $message.text()) {
			return;
		}

		weui.confirm('您确定让' + $("#username").val() + '成为我们的一员吗？', {
			title : '通过申请吗？',
			buttons : [ {
				label : '算了',
				type : 'default',
				onClick : function() {
					return;
				}
			}, {
				label : '确定',
				type : 'primary',
				onClick : function() {
					// 开始将必要的数据信息回传给服务器，执行“通过申请”的业务逻辑
					let url = "approveAction_agreeUserApply4JoinLevel.action";
					let param = {
						'ua4jlid' : $("#ua4jlid").val(),
					}
					$.post(url, param, function(data, textStatus, req) {
						// 显示后台回复的message的信息
						weui.alert(data.message);
						// 重载页面
						window.reload();
					});
				}
			} ]
		});
	}

	// 【AJAX】不同意申请
	function disagree() {
		let $message = $("#message");
		if ("" == $message.text() || null == $message.text()) {
			return;
		}

		weui.confirm('您确定不让' + $("#username").val() + '加入我们吗？', {
			title : '驳回申请吗？',
			buttons : [ {
				label : '算了',
				type : 'default',
				onClick : function() {
					return;
				}
			}, {
				label : '确定',
				type : 'primary',
				onClick : function() {
					// 开始将必要的数据信息回传给服务器，执行“驳回申请”的业务逻辑
					let url = "approveAction_refuseUserApply4JoinLevel.action";
					let param = {
						'ua4jlid' : $("#ua4jlid").val(),
					}
					$.post(url, param, function(data, textStatus, req) {
						// 显示后台回复的message的信息
						weui.alert(data.message);
						// 重载页面
						window.reload();
					});
				}
			} ]
		});
	}
</script>

</html>