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
								受测人员
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
										<th>姓名</th>
										<th>样本量</th>
										<th>性别</th>
										<th>年龄</th>
										<th>电话</th>
										<th>住址</th>
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
											<td><s:a href="#" onclick="toSampleList4User('%{uid}')">
													<s:property value="samples4EnclosedScale.size()" />
												</s:a></td>
											<td><s:property value="sex" /></td>
											<td><s:property value="age" /></td>
											<td><s:property value="phone" /></td>
											<td class="text-truncate" data-toggle="tooltip"
												title="<s:property value='address'/>"><s:property
													value="address" /></td>
											<td>
												<div class="btn-group" role="group">
													<button type="button"
														class="btn btn-outline-secondary btn-sm"
														onclick="showEnclosedScaleSelector('<s:property value="uid"/>');">新样本</button>
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

		<!-- container结束 -->
	</div>
</body>
<script src="${pageContext.request.contextPath}/js/jquery-3.2.0.js"></script>
<script
	src="${pageContext.request.contextPath}/js/jquery-ui/jquery-ui.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
<script src="${pageContext.request.contextPath}/js/popper.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.bundle.js"></script>
<script
	src="${pageContext.request.contextPath}/js/easyui-v1.7.0/jquery.easyui.min.js"></script>
<script
	src="${pageContext.request.contextPath}/js/layui-v2.5.5/layui/layui.js"></script>

<!-- wechat相关 -->
<script src="http://res.wx.qq.com/open/js/jweixin-1.4.0.js"></script>
<script src="https://res.wx.qq.com/open/libs/weuijs/1.2.1/weui.min.js"></script>
<script src="${pageContext.request.contextPath}/js/myJS.js"></script>
<script type="text/javascript">

	// 用于存放通过 initEnclosedScaleTips 从服务器端获取的所有可用量表的数据信息，共给showEnclosedScaleSelector显示选择器用
	var enclosedScaleTips = [];

	$(function() {
		initLaypage();
		initEnclosedScaleTips();
	});


	// 从服务器端获取所有可用可用量表的数据信息
	function initEnclosedScaleTips() {
		// TODO 基于AJAX向服务器端获取可用量表的数据信息，并解析
		for (let i = 0; i < 10; i++) {
			let tip = {};
			tip['label'] = "量表" + i; // 量表名
			tip['value'] = i; // 量表的esid
			enclosedScaleTips.push(tip);
		}
	}


	function showEnclosedScaleSelector(uid) {
		weui.picker(enclosedScaleTips, {
			className : 'custom-classname',
			container : 'body',
			defaultValue : [ 0 ],
			onChange : function(result) {
				console.log(result)
			},
			onConfirm : function(result) {
				console.log(result)
				let name = result[0].label; // 获取到选择的量表名
				let esid = result[0].value; // 获取到选择的
				// TODO 打开对应的量表页面
				weui.alert("用户：" + uid + ",所打开的量表名为：" + name + ",esid:" + esid);
			},
			id : 'enclosedScalePicker'
		});
	}


	// 初始化Laypage組件
	function initLaypage() {
		layui.use('laypage', function() {
			var laypage = layui.laypage;
			//执行一个laypage实例
			laypage.render({
				elem : 'laypage', //注意，这里的 test1 是 ID，不用加 # 号
				count : 150, //数据总数，从服务端得到
				layout : [ 'count', 'prev', 'page', 'next', 'limit', 'refresh', 'skip' ],
				limit : 20,
				limits : [ 10, 15, 20, 25, 30 ],
				prev : "<",
				next : ">",
				first : "首页",
				last : "尾页",
				// theme : '#1E9FFF',
				jump : function(obj, first) {
					//obj包含了当前分页的所有参数，比如：
					console.log(obj.curr); //得到当前页，以便向服务端请求对应页的数据。
					console.log(obj.limit); //得到每页显示的条数

					//首次不执行
					if (!first) {
						//do something
					}
				}
			});
		});
	}


	function toSampleList4User(uid) {
		weui.alert(uid);
	}


	// 切换筛选栏的图标显示
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
</script>

</html>