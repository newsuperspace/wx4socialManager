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
		<div class="weui-panel__hd">用户中心</div>
		<div class="weui-panel__bd">
			<div class="weui-media-box weui-media-box_appmsg">
				<div class="weui-media-box__hd">
					<img class="weui-media-box__thumb"
						src="https://team.weui.io/avatar/bear.jpg" alt="">
				</div>
				<div class="weui-media-box__bd">
					<h4 class="weui-media-box__title">
						用户名：<a href="javascript:openModal('电子工作证');"><s:property
								value="username" /></a>
					</h4>
					<p class="weui-media-box__desc">
						昵称：
						<s:property value="sickname" />
					</p>
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

	<!-- 模态框——电子工作证 -->
	<div class="modal fade font-" id="workCardModal" tabindex="-1"
		role="dialog" aria-labelledby="workCardModal" aria-hidden="true">
		<div class="modal-dialog modal-sm" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title" id="title4workCardModal">电子工作证</h4>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body mb-5">
					<div class="container" style="font-size: 12px">

						<div class="row">
							<div class="col-3"></div>
							<div class="col-6">
								<img src="#" class="img-thumbnail rounded-top"
									id="qrcode4WorkCardModal" alt="二维码">
							</div>
							<div class="col-3"></div>
						</div>

						<div class="row mt-3">
							<div class="col-4"></div>
							<div class="col-7">
								<div class="row mb-1">
									<div class="col-3 font-weight-bold p-0">用户名：</div>
									<div class="col-auto p-0" id="username4WorkCardModal">呼家楼</div>
								</div>
								<div class="row mb-1">
									<div class="col-3 font-weight-bold p-0">电&nbsp&nbsp&nbsp话：</div>
									<div class="col-auto p-0" id="phone4WorkCardModal">呼家楼</div>
								</div>
								<div class="row mb-1">
									<div class="col-3 font-weight-bold p-0">性&nbsp&nbsp&nbsp别：</div>
									<div class="col-auto p-0" id="sex4WorkCardModal">呼家楼</div>
								</div>
							</div>
							<div class="col-1"></div>
						</div>
						
					</div>
				</div>
			</div>
		</div>
	</div>


	<div class="weui-cells__title">活动信息</div>
	<div class="weui-cells">
		<a class="weui-cell weui-cell_access"
			href="javascript:canJoinActivityList();">
			<div class="weui-cell__bd">
				<span style="vertical-align: middle">可参加活动</span> <span
					class="weui-badge" style="margin-left: 5px;">8</span>
			</div>
			<div class="weui-cell__ft"></div>
		</a> <a class="weui-cell weui-cell_access"
			href="javascript:joiningActivityList();">
			<div class="weui-cell__bd">
				<span style="vertical-align: middle">已报名活动</span> <span
					class="weui-badge" style="margin-left: 5px;">8</span>
			</div>
			<div class="weui-cell__ft"></div>
		</a> <a class="weui-cell weui-cell_access"
			href="javascript:joinedActivityList();">
			<div class="weui-cell__bd">
				<span style="vertical-align: middle">活动历史</span> <span
					class="weui-badge" style="margin-left: 5px;" hidden=“true”>8</span>
			</div>
			<div class="weui-cell__ft"></div>
		</a>
	</div>

	<div class="weui-cells__title">组织信息</div>
	<div class="weui-cells">
		<a class="weui-cell weui-cell_access"
			href="javascript:joiningLevelList();">
			<div class="weui-cell__bd">
				<span style="vertical-align: middle">我的组织</span>
			</div>
			<div class="weui-cell__ft"></div>
		</a>
	</div>

	<div class="weui-cells__title">积分信息</div>
	<div class="weui-cells">
		<a class="weui-cell weui-cell_access" href="javascript:;">
			<div class="weui-cell__bd">
				<!--弹出类似微信钱包的消费记录列表，什么时间什么活动+多少分；什么时间什么物品-多少分-->
				<span style="vertical-align: middle"
					onclick="weui.alert('开发中，敬请期待')">我的积分</span>
			</div>
			<div class="weui-cell__ft">
				<s:property value="score" />
			</div>
		</a> <a class="weui-cell weui-cell_access" href="javascript:;">
			<div class="weui-cell__bd">
				<span style="vertical-align: middle"
					onclick="weui.alert('开发中，敬请期待')">公益商城</span> <span
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
				<span style="vertical-align: middle"
					onclick="weui.alert('开发中，敬请期待')">意见反馈</span>
			</div>
			<div class="weui-cell__ft"></div>
		</a> <a class="weui-cell weui-cell_access" href="javascript:;">
			<div class="weui-cell__hd">
				<!-- <img src="../img/logo.jpg" alt="" style="width:20px;margin-right:5px;display:block"> -->
			</div>
			<div class="weui-cell__bd">
				<span style="vertical-align: middle"
					onclick="weui.alert('开发中，敬请期待')">在线调研</span>
			</div>
			<div class="weui-cell__ft"></div>
		</a> <a class="weui-cell weui-cell_access" href="javascript:;">
			<div class="weui-cell__hd">
				<!-- <img src="../img/logo.jpg" alt="" style="width:20px;margin-right:5px;display:block"> -->
			</div>
			<div class="weui-cell__bd">
				<span style="vertical-align: middle"
					onclick="weui.alert('开发中，敬请期待')">设置</span>
			</div>
			<div class="weui-cell__ft"></div>
		</a>
	</div>


	<!--FOOT-->
	<div class="weui-footer mt-5">
		<p class="weui-footer__text">Copyright &copy; 2017-2019 联合会提供技术支持</p>
		<p class="weui-footer__text">感谢Bootstrap为我们提供前端框架支持</p>
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
	// 跳转到历史活动页	
	function joinedActivityList() {
		$(location).attr("href", "personalCenterAction_getJoinedActivityList.action");
	}
	// 跳转到正在参加活动页
	function joiningActivityList() {
		$(location).attr("href", "personalCenterAction_getJoiningActivityList.action");
	}
	// 跳转到可报名活动页
	function canJoinActivityList() {
		$(location).attr("href", "personalCenterAction_getCanJoinActivityList.action");
	}
	// 跳转到“我得组织”活动页面 
	function joiningLevelList() {
		$(location).attr("href", "personalCenterAction_getJoiningLevelList.action");
	}

	// 基于bootstrap打开各种模态对话框
	function openModal(modalName) {
		switch (modalName) {
		case "电子工作证":
			var url = "personalCenterAction_getWorkCard.action";
			$.post(url, null, function(data, textStatus, req) {
				if (data.result) {
					var username = data.username;
					var phone = data.phone;
					var qrcode = data.qrcodeFullPath;
					var sex = data.sex;
					$("#qrcode4WorkCardModal").attr("src", qrcode);
					$("#username4WorkCardModal").text(username);
					$("#phone4WorkCardModal").text(phone);
					$("#sex4WorkCardModal").text(sex);
					$("#workCardModal").modal("show");
				} else {
					alert(data.message);
				}
			});
			break;
		}
	}
</script>
</html>