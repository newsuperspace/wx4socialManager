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
											<td><s:a href="#" onclick="toUserVisitList('%{uid}')">
													<s:property value="score" />
												</s:a></td>
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

<script src="http://res.wx.qq.com/open/js/jweixin-1.4.0.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/myJS.js"></script>
<script type="text/javascript"
	src="https://res.wx.qq.com/open/libs/weuijs/1.1.4/weui.min.js"></script>
<script type="text/javascript">
	// 本内部脚本用于初始化当前页面——users.jsp中的一些组件
	$(function() {
		// 初始化selector面板
		initSelector();
		// 其他初始化工作...

	});



	/*
		 * 初始化userList.jsp页面上的selector面板
		 */
	function initSelector() {
		// 获取minusFirst选择器之下的所有子孙selector备用
		var minusFirst = $("#minusFirst")
		var zero = $("#zero");
		var first = $("#first");
		var second = $("#second");
		var third = $("#third");
		var fourth = $("#fourth");
		// 准备AJAX
		var url = "userAction_initSelector.action";
		var param = null;
		var levels = null;
		$.post(url, param, function(data, textStatus, req) {
			switch (data.tag) {
			case "admin": // 当前页面请求者是 admin系统管理员
				minusFirst.empty().append($('<option value="0" selected>--请选择--</option>')).val("0").attr("disabled", false);
				levels = data.minusFirstLevels;
				for (var i = 0; i < levels.length; i++) {
					minusFirst.append($('<option value="' + levels[i].mflid + '">' + levels[i].name + '</option>'));
				}
				break;
			case "minus_first": // 当前页面请求者是街道层级
				// 将当前操作者层级的基础信息（mflid/名称）保存在对应selector（该selector保持隐藏状态）中
				minusFirst.append($('<option value="' + data.minusFirstLevel.mflid + '" selected>' + data.minusFirstLevel.name + '</option>'));
				// 组织子层级的selector
				zero.empty().append($('<option value="0" selected>--请选择--</option>')).val("0").attr("disabled", false);
				levels = data.zeroLevels;
				for (var i = 0; i < levels.length; i++) {
					zero.append($('<option value="' + levels[i].zid + '">' + levels[i].name + '</option>'));
				}
				break;
			case "zero":
				// 将当前操作者层级的基础信息（zid/名称）保存在对应的selector（该selector保持隐藏状态）中
				zero.empty().append($('<option value="' + data.zeroLevel.zid + '" selected>' + data.zeroLevel.name + '</option>'))
				// 组织子层级的selector
				first.empty().append($('<option value="0" selected>--请选择--</option>')).val("0").attr("disabled", false);
				levels = data.firstLevels;
				for (var i = 0; i < levels.length; i++) {
					first.append($('<option value="' + levels[i].flid + '">' + levels[i].name + '</option>'));
				}
				break;
			case "first":
				// 将当前操作者层级的基础信息（flid/名称）保存在对应的selector（该selector保持隐藏状态）中
				first.empty().append($('<option value="' + data.firstLevel.flid + '" selected>' + data.firstLevel.name + '</option>'))
				// 组织子层级的selector
				second.empty().append($('<option value="0" selected>--请选择--</option>')).val("0").attr("disabled", false);
				levels = data.secondLevels;
				for (var i = 0; i < levels.length; i++) {
					second.append($('<option value="' + levels[i].scid + '">' + levels[i].name + '</option>'));
				}
				break;
			case "second":
				// 将当前操作者层级的基础信息（scid/名称）保存在对应的selector（该selector保持隐藏状态）中
				second.empty().append($('<option value="' + data.secondLevel.scid + '" selected>' + data.secondLevel.name + '</option>'))
				// 组织子层级的selector
				third.empty().append($('<option value="0" selected>--请选择--</option>')).val("0").attr("disabled", false);
				levels = data.thirdLevels;
				for (var i = 0; i < levels.length; i++) {
					third.append($('<option value="' + levels[i].thid + '">' + levels[i].name + '</option>'));
				}
				break;
			case "third":
				// 将当前操作者层级的基础信息（thid/名称）保存在对应的selector（该selector保持隐藏状态）中
				third.empty().append($('<option value="' + data.thirdLevel.thid + '" selected>' + data.thirdLevel.name + '</option>'))
				// 组织子层级的selector
				fourth.empty().append($('<option value="0" selected>--请选择--</option>')).val("0").attr("disabled", false);
				levels = data.fourthLevels;
				for (var i = 0; i < levels.length; i++) {
					fourth.append($('<option value="' + levels[i].foid + '">' + levels[i].name + '</option>'));
				}
				break;
			case "fourth":
				// 将当前操作者层级的基础信息（foid/名称）保存在对应的selector（该selector保持隐藏状态）中
				fourth.empty().append($('<option value="' + data.fourthLevel.foid + '" selected>' + data.fourthLevel.name + '</option>'))
				break;
			}
		});
	}


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

	/**
	 * 进共给getData4Selector()内部调用，不对外使用
	 * 用于基于已有数据重新组织用户信息列表中显示的数据
	 * TODO  分页查询 以及之后要功能强化加入的分页查询
	 * 
	 * 参数含义：
	 * users : 存放要显示到table中的用户的数据
	 * 
	 */
	function installTbody(users) {
		// tbody 中用于显示用户数据的table
		var tbody = $("#tbody");
		// 清空旧数据
		tbody.empty();
		for (var i = 0; i < users.length; i++) {
			let tr = $("<tr></tr>");
			// 创建用户名uid/username
			let td = $("<td><a href='#' onclick='userModal.op.userInfo(\"" + users[i].uid + "\")'>" + users[i].username + "</a></td>");
			tr.append(td);
			// 昵称sickname
			td = $("<td>" + users[i].sickname + "</td>");
			tr.append(td);
			// 性别sex
			td = $("<td>" + users[i].sex + "</td>");
			tr.append(td);
			// 年龄age
			td = $("<td>" + users[i].age + "</td>");
			tr.append(td);
			// 服务时长servTime
			td = $("<td>" + users[i].serveTime + "</td>");
			tr.append(td);
			// score
			td = $("<td><a href=\"#\" onclick=\"toUserVisitList('" + users[i].uid + "')\">" + users[i].score + "</a></td>");
			tr.append(td);
			// phone
			td = $("<td>" + users[i].phone + "</td>");
			tr.append(td);
			// address
			td = $("<td class='text-truncate' data-toggle='tooltip' title='" + users[i].address + "'>" + users[i].address + "</td>");
			tr.append(td);
			// email
			td = $("<td>" + users[i].email + "</td>");
			tr.append(td);
			// cardid
			td = $("<td>" + users[i].cardid + "</td>");
			tr.append(td);
			// registrationTimeStr
			td = $("<td>" + users[i].registrationTimeStr + "</td>");
			tr.append(td);
			// isHere是否在公众号内
			if (users[i].ishere) {
				td = $("<td>是</td>");
			} else {
				td = $("<td>否</td>");
			}
			tr.append(td);
			// locked 账号是否被锁死
			if (users[i].locked) {
				td = $("<td><span class='badge badge-danger'>锁死</span></td>");
			} else {
				td = $("<td><span class='badge badge-success'>正常</span></td>");
			}
			tr.append(td);
			// 功能按键
			td = $("<td><div class='btn-group' role='group'><button type='button' class='btn btn-outline-secondary btn-sm'>推送</button><button type='button' class='btn btn-outline-secondary btn-sm'>其他</button></div></td>");
			tr.append(td);
			tbody.append(tr);
		}
	// TODO 分页查询 部分的设置
	}

	/**
	 * 当用户点击六个下拉列表的任何一个列表并做出选择的时候，会触发本方法
	 * 用于通过jQueryt实现子selector内容的组织、用户table数据的更新显示
	 */
	function getData4Selector(self) {
		// 确定进行选择操作的selector下拉列表选择器
		console.log(self.name);
		// 产生选择变化的selector的jquery对象
		var selector = $(self);
		// 获取其选择的具体值（从value=""属性中获得选择的层级对象的ID）
		var value = selector.val();

		// 获取minusFirst选择器之下的所有子孙selector备用
		var minusFirst = $("#minusFirst")
		var zero = $("#zero");
		var first = $("#first");
		var second = $("#second");
		var third = $("#third");
		var fourth = $("#fourth");

		// tbody 中用于显示用户数据的table
		var tbody = $("#tbody");

		var levels = null; // 用来存放从ajax返回的data中的子层级的容器对象
		var users = null; // 用来存放从ajax返回的data中的users用户数据容器对象
		// 开始进行业务逻辑判断
		switch (selector.attr("name")) {
		// 只有admin用户才能使用minusFirst的selector做出了选择
		case "minusFirst":
			// step1 关闭所有子孙 selector
			zero.empty().append($('<option value="0" selected>--请选择--</option>')).val("0").attr("disabled", true);
			first.empty().append($('<option value="0" selected>--请选择--</option>')).val("0").attr("disabled", true);
			second.empty().append($('<option value="0" selected>--请选择--</option>')).val("0").attr("disabled", true);
			third.empty().append($('<option value="0" selected>--请选择--</option>')).val("0").attr("disabled", true);
			fourth.empty().append($('<option value="0" selected>--请选择--</option>')).val("0").attr("disabled", true);
			// step2 组织子selector的可选项目、组织用户列表信息
			if ('0' == value) {
				// step2-1 向服务器获取父选择器所选定父层级下辖的全部成员并更新前端显示
				let param = {
					tag : "all",
					lid : "all"
				};
				let url = "userAction_getData4Selector.action";
				$.post(url, param, function(data, textStatus, req) {
					users = data.users;
					userModal.op.installTbody(users);
				});
			} else {
				// step2-2-1 当前操作的selector所选定的tag和lid，从服务器获取所有子层级名称和lid后，开始像下面这样进行DOM元素的组建
				let param = {
					tag : "minus_first",
					lid : value,
				};
				let url = "userAction_getData4Selector.action";
				/*
				 * ★★★★★ AJAX默认都是“异步执行”★★★★★
				 * 这意味着post内部的处理方法会跟post之后的代码语句同时执行，
				 * 而我们需要的逻辑是先处理POST内的逻辑，等users数据变量获取完成后
				 * 再调用installTbody()、zero.val('0').attr('disabled',false) 这些逻辑
				 * 因此我们别无选择，只有两种处理方式：
				 * （1）通过
				 *     $.ajaxSetup({
							async : false // 全局设置Ajax为同步执行
						});
					代码设置AJAX全局为同步执行，这样post后续代码必须等post处理完成后才能执行
					但是记住了，用完之后记得回复全局设置为异步！
					（2）简单粗暴，直接将post后续逻辑代码都放入到post中。
				 */
				$.post(url, param, function(data, textStatus, req) {
					users = data.users;
					levels = data.zeroLevels;
					// 组建子层级的selector
					for (var i = 0; i < levels.length; i++) {
						zero.append('<option value="' + levels[i].zid + '">' + levels[i].name + '</option>');
					}
					// 打开子selector
					zero.val("0").attr("disabled", false);
					// step2-2-2 从服务器获取当前选定层级的用户数据,并更新到用户列表中显示出来
					userModal.op.installTbody(users);
				});
			}
			break;

		// 用户使用了zero的selector做出了选择
		case "zero":
			// step1 关闭所有子孙 selector
			first.empty().append($('<option value="0" selected>--请选择--</option>')).val("0").attr("disabled", true);
			second.empty().append($('<option value="0" selected>--请选择--</option>')).val("0").attr("disabled", true);
			third.empty().append($('<option value="0" selected>--请选择--</option>')).val("0").attr("disabled", true);
			fourth.empty().append($('<option value="0" selected>--请选择--</option>')).val("0").attr("disabled", true);
			// step2 组织子selector的可选项目、组织用户列表信息
			if ('0' == value) {
				// step2-1 向服务器获取父选择器所选定父层级下辖的全部成员并更新前端显示
				let url = "userAction_getData4Selector.action";
				let param = {
					tag : "minus_first",
					lid : minusFirst.val()
				}
				$.post(url, param, function(data, textStatus, req) {
					users = data.users;
					userModal.op.installTbody(users);
				});
			} else {
				// step2-2-1 当前操作的selector所选定的tag和lid，从服务器获取所有子层级名称和lid后，开始像下面这样进行DOM元素的组建
				let url = "userAction_getData4Selector.action";
				let param = {
					tag : "zero",
					lid : value
				}
				$.post(url, param, function(data, textStatus, req) {
					users = data.users;
					levels = data.firstLevels;
					for (var i = 0; i < levels.length; i++) {
						first.append($('<option value="' + levels[i].flid + '">' + levels[i].name + '</option>'));
					}
					// 打开子selector
					first.val("0").attr("disabled", false);
					// step2-2-2 从服务器获取当前选定层级的用户数据,并更新到用户列表中显示出来
					userModal.op.installTbody(users);
				});
			}
			break;

		// 用户使用了first的selector做出了选择
		case "first":
			// step1 关闭所有子孙 selector
			second.empty().append($('<option value="0" selected>--请选择--</option>')).val("0").attr("disabled", true);
			third.empty().append($('<option value="0" selected>--请选择--</option>')).val("0").attr("disabled", true);
			fourth.empty().append($('<option value="0" selected>--请选择--</option>')).val("0").attr("disabled", true);
			// step2 组织子selector的可选项目、组织用户列表信息
			if ('0' == value) {
				// step3-1 向服务器获取父选择器所选定父层级下辖的全部成员并更新前端显示
				let url = "userAction_getData4Selector.action";
				let param = {
					tag : "zero",
					lid : zero.val()
				}
				$.post(url, param, function(data, textStatus, req) {
					users = data.users;
					userModal.op.installTbody(users);
				});
			} else {
				let url = "userAction_getData4Selector.action";
				let param = {
					tag : "first",
					lid : value
				}
				$.post(url, param, function(data, textStatus, req) {
					users = data.users;
					levels = data.secondLevels;
					for (var i = 0; i < levels.length; i++) {
						second.append($('<option value="' + levels[i].scid + '">' + levels[i].name + '</option>'));
					}
					// 打开子selector
					second.val("0").attr("disabled", false);
					// step2-2-2 从服务器获取当前选定层级的用户数据,并更新到用户列表中显示出来
					userModal.op.installTbody(users);
				});
			}
			break;

		// 用户使用了second的selector做出了选择
		case "second":
			// step1 关闭所有子孙 selector
			third.empty().append($('<option value="0" selected>--请选择--</option>')).val("0").attr("disabled", true);
			fourth.empty().append($('<option value="0" selected>--请选择--</option>')).val("0").attr("disabled", true);
			// step2 组织子selector的可选项目、组织用户列表信息
			if ('0' == value) {
				// step3-1 向服务器获取父选择器所选定父层级下辖的全部成员并更新前端显示
				let url = "userAction_getData4Selector.action";
				let param = {
					tag : "first",
					lid : first.val()
				}
				$.post(url, param, function(data, textStatus, req) {
					users = data.users;
					userModal.op.installTbody(users);
				});
			} else {
				let url = "userAction_getData4Selector.action";
				let param = {
					tag : "second",
					lid : value
				}
				$.post(url, param, function(data, textStatus, req) {
					users = data.users;
					levels = data.thirdLevels;
					for (var i = 0; i < levels.length; i++) {
						third.append($('<option value="' + levels[i].thid + '">' + levels[i].name + '</option>'));
					}
					// 打开子selector
					third.val("0").attr("disabled", false);
					// step2-2-2 从服务器获取当前选定层级的用户数据,并更新到用户列表中显示出来
					userModal.op.installTbody(users);
				});
			}
			break;

		// 用户使用了third的selector做出了选择
		case "third":
			// step1 关闭所有子孙 selector
			fourth.empty().append($('<option value="0" selected>--请选择--</option>')).val("0").attr("disabled", true);
			// step2 组织子selector的可选项目、组织用户列表信息
			if ('0' == value) {
				// step2-1 向服务器获取父选择器所选定父层级下辖的全部成员并更新前端显示
				let url = "userAction_getData4Selector.action";
				let param = {
					tag : "second",
					lid : second.val()
				}
				$.post(url, param, function(data, textStatus, req) {
					users = data.users;
					userModal.op.installTbody(users);
				});
			} else {
				let url = "userAction_getData4Selector.action";
				let param = {
					tag : "third",
					lid : value
				}
				$.post(url, param, function(data, textStatus, req) {
					users = data.users;
					levels = data.fourthLevels;
					for (var i = 0; i < levels.length; i++) {
						fourth.append($('<option value="' + levels[i].foid + '">' + levels[i].name + '</option>'));
					}
					// 打开子selector
					fourth.val("0").attr("disabled", false);
					// step2-2-2 从服务器获取当前选定层级的用户数据,并更新到用户列表中显示出来
					userModal.op.installTbody(users);
				});
			}
			break;

		// 用户使用了fourth的selector做出了选择
		case "fourth":
			// step1 关闭所有子孙 selector
			// step2 组织子selector的可选项目、组织用户列表信息
			if ('0' == value) {
				// step2-1 向服务器获取父选择器所选定父层级下辖的全部成员并更新前端显示
				let url = "userAction_getData4Selector.action";
				let param = {
					tag : "third",
					lid : third.val()
				}
				$.post(url, param, function(data, textStatus, req) {
					users = data.users;
					userModal.op.installTbody(users);
				});
			} else {
				// step2-2-2 从服务器获取当前选定层级的用户数据,并更新到用户列表中显示出来
				let url = "userAction_getData4Selector.action";
				let param = {
					tag : "fourth",
					lid : value
				}
				$.post(url, param, function(data, textStatus, req) {
					users = data.users;
					// step2-2-2 从服务器获取当前选定层级的用户数据,并更新到用户列表中显示出来
					userModal.op.installTbody(users);
				});
			}
			break;
		}
	}
</script>

</html>