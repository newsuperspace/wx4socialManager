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
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/fullcalendar.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/fullcalendar.print.css"
	media='print'>
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
				<!-- Title开始 -->
				<div class="row">
					<div class="col">
						<!-- =============标题=========== -->
						<div
							class="justify-content-between d-flex flex-wrap flex-md-nowrap align-items-center pb-1 mb-4 border-bottom">
							<h1 class="h2">活动室月活动详情</h1>
							<!-- ● -->
							<div class="btn-toolbar mb-2 mb-md-0">
								<div class="btn-group mr-2">
									<button class="btn btn-sm btn-outline-secondary"
										data-toggle="modal" data-target="#selectModal">
										<span class="glyphicon glyphicon-search"></span> 功能1
									</button>
									<button class="btn btn-sm btn-outline-secondary"
										data-toggle="modal" data-target="#orderModal">
										<span class="glyphicon glyphicon-sort-by-order"></span> 功能2
									</button>
								</div>
								<button class="btn btn-sm btn-outline-secondary dropdown-toggle"
									data-toggle="toggle" data-target="#">
									<span class="glyphicon glyphicon-cog"></span> 其他
								</button>
							</div>
						</div>
					</div>
				</div>
				<!-- Title结束 -->
				<!-- FullCalendar开始 -->
				<div class="row mt-1">
					<div class="col-md-11">
						<div class="form-group">
							<select class="form-control form-control-lg" name="aaa" id="aaa"
								onchange="getEventSource4month();">
								<option value="0">--请在这里选择活动室--</option>
								<s:iterator value="#houses">
									<option value="<s:property value='hid'/>"><s:property
											value="name" /></option>
								</s:iterator>
							</select>
						</div>
						<div id='calendar' hidden="true"></div>
					</div>
					<div class="col-md-1"></div>
				</div>
				<!-- FullCalendar结束 -->

			</div>
		</div>


	</div>
</body>
<script src="${pageContext.request.contextPath}/js/jquery-3.2.0.js"></script>
<script src="${pageContext.request.contextPath}/js/popper.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.bundle.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/myJS.js"></script>
<script type="text/javascript"
	src="https://res.wx.qq.com/open/libs/weuijs/1.1.4/weui.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/moment.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/fullcalendar.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/zh-cn.js"></script>

<script type="text/javascript">
	//'use strict'; // 使用严格模式

	// 初始化FullCalendar
	$(function() {

		// 注意fullCalendar的'C'是大写，小写就找不到该function了
		$("#calendar").fullCalendar({
			// 设置日历的主题风格，这里选用了bootstrap3风格
			themeSystem : 'bootstrap3',
			// 当使用Bootstrap风格时，设置header部分的按钮图标。
			bootstrapGlyphicons : {
				close : 'glyphicon-remove',
				prev : 'glyphicon-chevron-left',
				next : 'glyphicon-chevron-right',
				prevYear : 'glyphicon-backward',
				nextYear : 'glyphicon-forward'
			},
			// 设置每周第一天，数字int型，默认0（周日）
			firstDay : 1,
			// 设置页面上方的按钮阵列
			header : {
				left : 'prev,next today',
				center : 'title',
				right : 'month'
			},
			// 用来显示是否在日历上显示周六和周日，默认为true表示显示
			weekends : true,
			// 在日历中不显示一周中的某几天，数组形式，从0-6分别表示周日（Sunday）-周一（Monday）
			hiddenDays : [],
			// 设置月试图时显示的周数，默认true。如果是true则每次固定显示6周，如果设置为false，则每月根据实际周数显示4，5，6周。
			fixedWeekCount : false,
			// 是否显示周序号（一年有52个周）
			weekNumbers : false,
			// 这个属性设置每个日期上可见的时间如果超过3个是否被隐藏
			eventLimit : true,
		});
	});

	// id="aaa"的selector在选择活动室时会触发本回调，用来通过AJAX方式从本地服务器获取当月在该活动室开展的全部活动信息，并以数据源的形式显示在calendar上
	function getEventSource4month() {
		// 先隐藏日历
		$('#calendar').attr("hidden", true);
		// 获取用户所选中的活动室的hid
		let hid = $("#aaa").val();
		// 如果用户选择的是"--请选择活动室--"，则
		if ('0' == hid) {
			$('#calendar').fullCalendar('removeEventSources');
			return;
		}
		// 否则用户确实选择了一个活动室，开始向本地服务器所要该活动室当月的全部活动数据信息
		let param = {
			hid : hid
		}
		let url = "houseAction_getEventSource4month.action";
		$.post(url, param, function(data, textStatus, req) {
			// 在这里可以设置calendar的数据源
			// 先删除视图中所有的旧事件数据源
			$('#calendar').fullCalendar('removeEventSources');
			// 再通过FullCalendar的“添加事件源”的功能，将JSON对象直接设置成日历的事件源，然后自动绘制时间图标到日历上显示出来。
			$('#calendar').fullCalendar('addEventSource', data);
			// 显示日历
			$('#calendar').attr("hidden", false);
		});

	}
</script>
</html>

