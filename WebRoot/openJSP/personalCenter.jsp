<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>用户中心</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<!-- Bootstrap CSS -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/bootstrap.css">
<link rel="stylesheet"
	href="https://res.wx.qq.com/open/libs/weui/1.1.3/weui.min.css">
</head>

<body style="background-color: #efeff4;">

	<div class="weui-panel weui-panel_access">
		<div class="weui-panel__hd">个人信息</div>
		<div class="weui-panel__bd">
			<div class="weui-media-box weui-media-box_appmsg">
				<div class="weui-media-box__hd">
					<img class="weui-media-box__thumb"
						src="https://team.weui.io/avatar/bear.jpg" alt="">
				</div>
				<div class="weui-media-box__bd">
					<h4 class="weui-media-box__title">用户名：<s:property value="username"/></h4>
					<p class="weui-media-box__desc">昵称：<s:property value="sickname"/></p>
				</div>
			</div>
		</div>
		<div class="weui-panel__ft">
			<a href="javascript:void(0);"
				class="weui-cell weui-cell_access weui-cell_link">
				<div class="weui-cell__bd">查看更多</div> <span class="weui-cell__ft"></span>
			</a>
		</div>
	</div>

	<div class="weui-cells__title">活动信息</div>
	<div class="weui-cells">
		<a class="weui-cell weui-cell_access" href="javascript:canJoinActivityList();">
			<div class="weui-cell__bd">
				<span style="vertical-align: middle">可参加活动</span> <span
					class="weui-badge" style="margin-left: 5px;">8</span>
			</div>
			<div class="weui-cell__ft"></div>
		</a> <a class="weui-cell weui-cell_access" href="javascript:joiningActivityList();">
			<div class="weui-cell__bd">
				<span style="vertical-align: middle">已报名活动</span> <span
					class="weui-badge" style="margin-left: 5px;">8</span>
			</div>
			<div class="weui-cell__ft"></div>
		</a> <a class="weui-cell weui-cell_access" href="javascript:joinedActivityList();">
			<div class="weui-cell__bd">
				<span style="vertical-align: middle">活动历史</span> <span
					class="weui-badge" style="margin-left: 5px;" hidden=“true”>8</span>
			</div>
			<div class="weui-cell__ft"></div>
		</a>
	</div>

	<div class="weui-cells__title">积分信息</div>
	<div class="weui-cells">
		<a class="weui-cell weui-cell_access" href="javascript:;">
			<div class="weui-cell__bd">
				<!--弹出类似微信钱包的消费记录列表，什么时间什么活动+多少分；什么时间什么物品-多少分-->
				<span style="vertical-align: middle">我的积分</span>
			</div>
			<div class="weui-cell__ft"><s:property value="score"/></div>
		</a> <a class="weui-cell weui-cell_access" href="javascript:;">
			<div class="weui-cell__bd">
				<span style="vertical-align: middle">公益商城</span> <span
					class="weui-badge" style="margin-left: 5px;">New!</span>
			</div>
			<div class="weui-cell__ft"></div>
		</a>
	</div>


	<div class="weui-cells__title">设置及反馈</div>
	<div class="weui-cells">

		<a class="weui-cell weui-cell_access" href="javascript:;">
			<div class="weui-cell__hd">
				<!-- <img src="../img/logo.jpg" alt="" style="width:20px;margin-right:5px;display:block"> -->
			</div>
			<div class="weui-cell__bd">
				<span style="vertical-align: middle">意见反馈</span>
			</div>
			<div class="weui-cell__ft"></div>
		</a> <a class="weui-cell weui-cell_access" href="javascript:;">
			<div class="weui-cell__hd">
				<!-- <img src="../img/logo.jpg" alt="" style="width:20px;margin-right:5px;display:block"> -->
			</div>
			<div class="weui-cell__bd">
				<span style="vertical-align: middle">在线调研</span>
			</div>
			<div class="weui-cell__ft"></div>
		</a> <a class="weui-cell weui-cell_access" href="javascript:;">
			<div class="weui-cell__hd">
				<!-- <img src="../img/logo.jpg" alt="" style="width:20px;margin-right:5px;display:block"> -->
			</div>
			<div class="weui-cell__bd">
				<span style="vertical-align: middle">设置</span>
			</div>
			<div class="weui-cell__ft"></div>
		</a>
	</div>


	<!--FOOT-->
	<div class="weui-footer mt-5">
		<p class="weui-footer__text">Copyright &copy; 2017-2019
			承载社会工作创新发展中心</p>
		<p class="weui-footer__links">
			<a href="javascript:void(0);" class="weui-footer__link">访问我们</a>
		</p>
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
	
	function joinedActivityList(){
		$(location).attr("href","personalCenterAction_getJoinedActivityList.action");
	}
	function joiningActivityList(){
		$(location).attr("href","personalCenterAction_getJoiningActivityList.action");
	}
	function canJoinActivityList(){
		$(location).attr("href","personalCenterAction_getCanJoinActivityList.action");
	}


</script>
</html>