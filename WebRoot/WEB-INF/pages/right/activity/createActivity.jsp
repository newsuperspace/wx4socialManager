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
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/jqueryui/jquery-ui.css"></link>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/jqueryui/jquery-ui.structure.css"></link>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/jqueryui/jquery-ui.theme.css"></link>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/jqueryui/jquery-ui-timepicker-addon.css" />
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
						<div class="container">
							<input type="hidden" id="dpid"
								value="<s:property value='%{#dpid}'/>" />
							<div class="row mt-3">
								<div class="col-md-1"></div>
								<div class="col-md-10">
									<div class="form-group">
										<label for="name">活动名称:</label> <input type="text" data-myInput="me"
											onchange="activityModal.op.checkInput();"
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
										<label for="description">活动内容:</label> <input type="text" data-myInput="me"
											onchange="activityModal.op.checkInput();"
											class="form-control" name="description" id="description">
										<small id="info4description" class="form-text text-muted"
											hidden="true">必填</small>
									</div>
								</div>
								<div class="col-md-1"></div>
							</div>

							<div class="row">
								<div class="col-md-1"></div>
								<div class="col-md-10">
									<div class="form-group">
										<label for="type">名额限制:</label> <select class="custom-select"
											id="type" name="type" 
											onchange="activityModal.op.typeChangeListener();">
											<option value="1" selected>开放报名</option>
											<option value="2">限定人数</option>
										</select> <small id="info4type" class="form-text text-muted"
											hidden="true">请选择人数限制类型，默认不设限</small>
									</div>
								</div>
								<div class="col-md-1"></div>
							</div>
							<div class="row">
								<div class="col-md-1"></div>
								<div class="col-md-10">
									<div class="form-group">
										<label for="baoMingUplimit">设置人数:</label> <input type="number" data-myInput="me"
											onchange="activityModal.op.checkInput();"
											class="form-control" min="1" value="1"
											max='<s:property value="%{#size}" />' name="baoMingUplimit"
											id="baoMingUplimit"> <small id="info4baoMingUplimit"
											style="color:red;" class="form-text text-muted" hidden="true">人数设置为1~<s:property
												value="%{'#size'}" /></small>
									</div>
								</div>
								<div class="col-md-1"></div>
							</div>

							<!--
                            开始日期和时间
                            myJS中要在前端判断用户所选择的日期必须是当前操作日期提前1天
                            开始时间则确定了“可签到”的时间，一般为开始时间前30分钟~开始时间后15分钟的时间段内，提早和超时都不予签到
        -->
							<div class="row">
								<div class="col-md-1"></div>
								<div class="col-md-10">
									<div class="form-group">
										<label for="date">选择日期:</label> <input type="text" data-myInput="me"
											onchange="activityModal.op.checkInput();"
											class="form-control" name="date" id="date"
											aria-describedby="info4date" placeholder="选择日期"> <small
											id="info4date" class="form-text text-muted" hidden="true">必填，单击输入框选择日期和时间</small>
									</div>
								</div>
								<div class="col-md-1"></div>
							</div>

							<!-- 
                            持续时间表明活动从开始时间开始会持续多久
                            持续时间记录了用户参加本次活动所累积的工作时长
                            同时还确定了签退时间，也就是活动结束前30分钟到结束后1小时的时间段内，如果过时签退不予签退
                            只有签退成功才能获得积分和积累工作时长
         -->
							<div class="row">
								<div class="col-md-1"></div>
								<div class="col-md-10">
									<div class="form-group">
										<label for="hour">活动时长(小时): <input type="text" data-myInput="me"
											value="1" id="hour"
											onchange="activityModal.op.checkHour(this);activityModal.op.checkInput();"
											style="border:0; color:#f6931f; font-weight:bold; font-size: 17px">
										</label>
										<div id="hourBar" name="hourBar"></div>
										<small id="hourBar" class="form-text text-muted" hidden="true">默认活动时常为1小时</small>
									</div>
								</div>
								<div class="col-md-1"></div>
							</div>

							<div class="row">
								<div class="col-md-1"></div>
								<div class="col-md-10">
									<div class="form-group">
										<label for="score">积分值:</label> <input type="number" min="0" data-myInput="me"
											onchange="activityModal.op.checkInput();"
											class="form-control" name="score" id="score" value="0">
										<small id="info4score" class="form-text text-muted"
											hidden="true">签到后获取的积分</small>
									</div>
								</div>
								<div class="col-md-1"></div>
							</div>

							<div class="row mb-5">
								<div class="col-md-1"></div>
								<div class="col-md-10">
									<button type="button " name="commit" id="commit"
										disabled="true" onclick="activityModal.op.createActivity();"
										class="btn btn-primary btn-lg btn-block">发起活动</button>
								</div>
								<div class="col-md-1"></div>
							</div>

						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>

<script src="${pageContext.request.contextPath}/js/jquery-3.2.0.js"></script>
<script src="${pageContext.request.contextPath}/jqueryui/jquery-ui.js"></script>
<script
	src="${pageContext.request.contextPath}/jqueryui/jquery-ui-timepicker-addon.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/jqueryui/jquery-ui-timepicker-zh-CN.js"></script>
<script src="${pageContext.request.contextPath}/js/popper.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.bundle.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/myJS.js"></script>
<script>
	$(function(){
		// 针对jQueryUI的一些控件执行初始化设置
		activityModal.init.initOp();
	});
</script>
</html>