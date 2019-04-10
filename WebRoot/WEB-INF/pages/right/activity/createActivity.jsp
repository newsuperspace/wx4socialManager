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
<!-- jQueryUI CSS -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/jqueryui/jquery-ui.css"></link>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/jqueryui/jquery-ui.structure.css"></link>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/jqueryui/jquery-ui.theme.css"></link>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/jqueryui/jquery-ui-timepicker-addon.css" />
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
				<div class="row">
					<div class="col">
						<!-- =============标题=========== -->
						<div
							class="justify-content-between d-flex flex-wrap flex-md-nowrap align-items-center pb-1 mb-4 border-bottom">
							<h1 class="h2">创建新活动</h1>
						</div>
						<!-- =============正文=========== -->
						<input type="hidden" id="dpid"
							value="<s:property value='%{#dpid}'/>" />
						<div class="row mt-3">
							<div class="col-md-1"></div>
							<div class="col-md-10">
								<div class="form-group">
									<label for="name">活动名称:</label> <input type="text"
										data-myInput="me" onchange="activityModal.op.checkInput();"
										class="form-control" name="name" id="name"> <small
										id="info4name" class="form-text text-muted" hidden="true">必填</small>
								</div>
							</div>
							<div class="col-md-1"></div>
						</div>

						<div class="row">
							<div class="col-md-1"></div>
							<div class="col-md-10">
								<div class="form-group">
									<label for="description">活动内容:</label> <input type="text"
										data-myInput="me" onchange="activityModal.op.checkInput();"
										class="form-control" name="description" id="description">
									<small id="info4description" class="form-text text-muted"
										hidden="true">必填</small>
								</div>
							</div>
							<div class="col-md-1"></div>
						</div>

						<!-- 人数限制设置 -->
						<div class="row">
							<div class="col-md-1"></div>
							<div class="col-md-10">
								<div class="form-group">
									<label for="type">名额限制:</label> <select class="custom-select"
										id="type" name="type"
										onchange="activityModal.op.typeChangeListener();activityModal.op.checkInput();activityModal.op.checkBaomingUplimit();">
										<option value="1" selected>开放报名</option>
										<option value="2">限定人数</option>
									</select> <small id="info4type" class="form-text text-muted"
										hidden="true">请选择人数限制类型，默认不设限</small>
								</div>
							</div>
							<div class="col-md-1"></div>
						</div>
						<div class="container">
							<div class="row">
								<div class="col-md-1"></div>
								<div class="col-md-10">
									<div class="form-group">
										<label for="baoMingUplimit">设置人数:</label> <input type="number"
											data-myInput="me" onchange="activityModal.op.checkInput();"
											class="form-control" min="1" value="1"
											max='<s:property value="%{#size}" />' name="baoMingUplimit"
											id="baoMingUplimit"> <small id="info4baoMingUplimit"
											style="color:red;" class="form-text text-muted" hidden="true">人数设置为1~<s:property
												value="%{#size}" /></small>
									</div>
								</div>
								<div class="col-md-1"></div>
							</div>
						</div>

						<!-- 签到方式 -->
						<div class="row">
							<div class="col-md-1"></div>
							<div class="col-md-10">
								<div class="form-group">
									<label for="activityType">签到类型</label>
									<div class="form-check form-check-inline">
										<input class="form-check-input" type="radio" checked
											name="sychronizeRadio" id="sychronizeRadio1" value="1">
										<label class="form-check-label" for="inlineRadio1">签到和签退分开进行</label>
									</div>
									<div class="form-check form-check-inline">
										<input class="form-check-input" type="radio"
											name="sychronizeRadio" id="sychronizeRadio2" value="2">
										<label class="form-check-label" for="inlineRadio2">同步完成签到和签退</label>
									</div>
								</div>
							</div>
							<div class="col-md-1"></div>
						</div>

						<!-- 活动类型设置 -->
						<div class="row">
							<div class="col-md-1"></div>
							<div class="col-md-10">
								<div class="form-group">
									<label for="activityType">活动类型</label> <select
										class="custom-select" name="activityType" id="activityType"
										onchange="activityModal.op.activityTypeChangeListener();activityModal.op.checkInput();">
										<option value="0" selected>--请选择活动类型--</option>
										<option value="1">室外活动</option>
										<option value="2">室内活动</option>
									</select>
								</div>
							</div>
							<div class="col-md-1"></div>
						</div>
						<!-- 
								室内活动
							 -->
						<div class="container">
							<div class="row" id="indoor" hidden="hidden">
								<div class="col-md-1"></div>
								<div class="col-md-10">
									<div class="form-group">
										<label for="houseSelector">当前活动室：</label> <select
											class="form-control form-control-sm" name="houseSelector"
											id="houseSelector"
											onchange="activityModal.op.houseChangeListener();activityModal.op.checkInput();">
											<option value="0" selected>--请选择活动室--</option>
											<s:iterator value="%{#houses}">
												<option value="<s:property value='hid'/>"><s:property
														value="%{name}" />
												</option>
											</s:iterator>
										</select>
									</div>
									<!-- 这个id=calendar 的div就是盛放Calendar插件的容器 -->
									<div id='calendar' hidden="true"></div>

									<input type="text" data-myInput="me" disabled="true"
										onchange="activityModal.op.checkInput();" class="form-control"
										name="date4calendar" id="date4calendar"
										aria-describedby="info4date4calendar" placeholder="选择日期">
									<small id="info4date4calendar" class="form-text text-muted"
										hidden="true">必填</small>
								</div>
								<div class="col-md-1"></div>
							</div>
						</div>
						<!-- 
								室外活动
						 -->
						<div class="container">
							<div class="row" id="outdoor" hidden="hidden">
								<div class="col-md-1"></div>
								<div class="col-md-10">
									<div class="form-group">
										<label for="geo">当前活动地点：</label> <select
											class="form-control form-control-sm" name="geoSelector"
											id="geoSelector"
											onchange="activityModal.op.geoChangeListener();activityModal.op.checkInput();">
											<option value="0" selected>--请选择活动地点--</option>
											<s:iterator value="%{#geos}">
												<option value="<s:property value='geoid'/>"><s:property
														value="name" /></option>
											</s:iterator>
										</select>
									</div>
									<!--
                            开始日期和时间
                            myJS中要在前端判断用户所选择的日期必须是当前操作日期提前1天
                            开始时间则确定了“可签到”的时间，一般为开始时间前30分钟~开始时间后15分钟的时间段内，提早和超时都不予签到
       						 -->
									<div class="form-group">
										<label for="date4selector">选择日期:</label> <input type="text"
											hidden="true" data-myInput="me"
											onchange="activityModal.op.checkInput();"
											class="form-control" name="date4selector" id="date4selector"
											aria-describedby="info4date4selector" placeholder="选择日期">
										<small id="info4date4selector" class="form-text text-muted"
											hidden="true">必填，单击输入框选择日期和时间</small>
									</div>
									<!-- 
                            持续时间表明活动从开始时间开始会持续多久
                            持续时间记录了用户参加本次活动所累积的工作时长
                            同时还确定了签退时间，也就是活动结束前30分钟到结束后1小时的时间段内，如果过时签退不予签退
                            只有签退成功才能获得积分和积累工作时长
        					-->
									<div class="form-group">
										<label for="hourBar">活动时长(小时):</label> <input type="text"
											data-myInput="me" value="1" id="hour"
											onchange="activityModal.op.checkHour(this);activityModal.op.checkInput();"
											style="border:0; color:#f6931f; font-weight:bold; font-size: 17px">

										<div id="hourBar" name="hourBar"></div>
										<small id="hourBar" class="form-text text-muted" hidden="true">默认活动时常为1小时</small>
									</div>
								</div>
								<div class="col-md-1"></div>
							</div>
						</div>
						
						<!-- 设置活动获取的积分分值 -->
						<div class="row">
							<div class="col-md-1"></div>
							<div class="col-md-10">
								<div class="form-group">
									<label for="score">积分值:</label> <input type="number" min="0"
										data-myInput="me" onchange="activityModal.op.checkInput();"
										class="form-control" name="score" id="score" value="0">
									<small id="info4score" class="form-text text-muted"
										hidden="true">签到后获取的积分</small>
								</div>
							</div>
							<div class="col-md-1"></div>
						</div>
						
						<!-- 发起活动的按钮 -->
						<div class="row mb-5">
							<div class="col-md-1"></div>
							<div class="col-md-10">
								<button type="button " name="commit" id="commit" disabled="true"
									onclick="activityModal.op.createActivity();"
									class="btn btn-primary btn-lg btn-block">发起活动</button>
							</div>
							<div class="col-md-1"></div>
						</div>

					</div>
				</div>
			</div>
		</div>
	</div>
</body>

<!-- jQuery JS-->
<script src="${pageContext.request.contextPath}/js/jquery-3.2.0.js"></script>
<!-- jQueryUI JS-->
<script src="${pageContext.request.contextPath}/jqueryui/jquery-ui.js"></script>
<script
	src="${pageContext.request.contextPath}/jqueryui/jquery-ui-timepicker-addon.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/jqueryui/jquery-ui-timepicker-zh-CN.js"></script>
<!-- Bootstrap JS -->
<script src="${pageContext.request.contextPath}/js/popper.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.bundle.js"></script>
<!-- 微信的JS-SDK -->
<script src="http://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>
<!-- 自己的 JS -->
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/myJS.js"></script>
<!-- WEUI的 JS -->
<script type="text/javascript"
	src="https://res.wx.qq.com/open/libs/weuijs/1.1.4/weui.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/moment.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/fullcalendar.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/zh-cn.js"></script>
<script>
	$(function() {
		// 针对jQueryUI的一些控件执行初始化设置
		activityModal.init.initOp();
		// 获取室内活动创建的时间范围
		getStartDayAndEndDayByAjax();
		// 初始化FullCalendar
		$('#calendar').fullCalendar({
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
			// 展示的默认视图
			defaultView : 'agenda4A',
			// 设置页面上方的按钮阵列

			// 用来显示是否在日历上显示周六和周日，默认为true表示显示
			weekends : true,
			// 在日历中不显示一周中的某几天，数组形式，从0-6分别表示周日（Sunday）-周一（Monday）
			hiddenDays : [],
			// 设置日程安排得可选范围，默认从0点到24点
			minTime : '09:00:00',
			maxTime : '17:00:00',
			// 自定义视图，如果自带的mouth/week等视图不满意，我们可以自己定义一个
			views : {
				agenda4A : {
					// 设置agenda系列视图中是否现实“all-day”全天格，默认为true（显示）
					allDaySlot : false,
					// 设置时间轴显示的时间格式
					slotLabelFormat : 'HH:mm',
					// 可以选择日期（月视图）/时间（周和天视图）
					selectable : true,
					// 设置默认视图的基础类型
					type : 'agenda',
					// duration: {
					//     days: 9
					// },
					// 定义咱们的自定义视图中的可见日期范围，start设置成0代表当前（但是当天不可操作），end设置成8代表自定义视图上显示从当天（0）往后共8天的日期
					visibleRange : function(currentDate) {
						return {
							start : currentDate.clone().subtract(0, 'days'),
							// 这里设置可发布活动的最后一天，因此需要后台进行设置AJAX
							end : currentDate.clone().add(getEndDay(), 'days')
						};
					},
					// 显示当前自定义视图的可见日期范围，单独使用只显示从开始时间到结束时间前一天的日期，其他日期不可见，可如果和visibleRange重叠使用
					// 则实际显示效果是visibleRange范围和validRange的交集，记住是交集
					// 因此这里为了达成“显示从明天开始往后七天的效果”必须两个属性重叠使用，不然自定义视图上会显示包含今天的日期，而如果强制设置start为-1
					// 则自定义视图上虽然其实日期变为了明天，但时间的选择会收到当前时间的影响（过去的时间不能选择），所以目前来说像我这样设置自定义视图
					// 是唯一选择
					validRange : {
						// 作为可发布活动的第一天，需要AJAX由后台设置，因此这里应该是一个函数
						start : getStartDay(),
					},
					// 设置日程视图中的事件是否可以重叠，值为布尔类型，默认值为true，事件会相互重叠，最多一半会被遮住。
					slotEventOverlap : false,
					// 设置当点击页面其他地方的时候，是否清除已选择的区域，值为布尔类型，默认值 true。只有当 selectable 设置为true的时候才有效。
					unselectAuto : false,
					// 如果已设置unselectAuto为true（默认），则点击页面中任何其他地方都会取消选择，只有unselectCancel规定的包含特定css类的DOM对象除外
					unselectCancel : ".unselectCancel",
				},
			},
			// 用户拖选该最小长度后（15对应一个半小时一个格子，30对应1个小时两个格子）才能选中
			selectMinDistance : 30,
			// 【全局视图属性】当拖拽选择触及到视图上其他已有事件的时候就会触发本回调，当返回结果为false时取消选择
			selectOverlap : function(event) {
				console.log("selectOverlap is running");
				//console.log(event);
				// 清空input中的内容
				$("#date4calendar").val("");
				// 检查
				activityModal.op.checkInput();
				return false;
			},
			// 【全局视图属性】在selectable为true（默认为false）的情况下，可以选择每个视图中的多个时间，并且报告所选择的时间信息
			select : function(start, end, jsEvent, view) {
				// 开始时间start.format() → 2018-09-05T08:30:00
				let startTime = start.format();
				// 结束时间end.format() → 2018-09-05T10:00:00
				let endTime = end.format()
				// 直接放入到date4calendar这个input中等待提交
				$("#date4calendar").val(startTime + "~" + endTime);
				// 检查
				activityModal.op.checkInput();
			},
			// 这个属性设置每个日期上可见的时间如果超过3个是否被隐藏
			eventLimit : true,
		});
		// 其他初始化操作.....

	});

	// 【完成】
	function getStartDay() {
		// 这里应该通过AJAX由后端根据当前时间、操作者层级类型（社区用户可在每周五发起下周活动，其他层级只能在周日当天发起下周活动）动态设置
		// 自定义视图你希望显示的第一天是哪号，这里就填什么即可，原则上是当天的明天
		return localStorage.getItem("startDay");
	}
	// 【完成】
	function getEndDay() {
		// 这里应该通过AJAX由后端根据当前时间和操作者层级类型动态设置。
		// 你希望从getStartDay() 第一天开始整个自定义视图总共显示多少天这里就返回多少即可,最小1天
		return parseInt(localStorage.getItem("endDay")) || 1;
	}
	// 【完成】通过AJAX从服务器后端获得自定义日历视图的起始日期（明天）和截至天数，并放入到localStorage中供给getStartDay()和getEndDay()随时获取
	function getStartDayAndEndDayByAjax() {
		let url = "activityAction_getStartDayAndEndDayByAjax.action";
		let param = {
			dpid : $("#dpid").val()
		}
		$.ajaxSetup({
			async : false // 全局设置Ajax为同步执行
		});
		$.post(url, param, function(data, textStatus, req) {
			if (data.result) {
				// 获取成功
				let startDay = data.startDay;
				let endDay = data.endDay;
				localStorage.setItem("startDay", startDay);
				localStorage.setItem("endDay", endDay);
			} else {
				// 获取失败
				console.log(data.message);
			}
		});
		$.ajaxSetup({
			async : true // 恢复Ajax为异步执行
		});
		console.log("startDay=" + localStorage.getItem("startDay"));
		console.log("endDay=" + localStorage.getItem("endDay"));
	}
</script>
</html>