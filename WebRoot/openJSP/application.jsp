<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>加入申请</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<!-- Bootstrap CSS -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/bootstrap.css">
<link rel="stylesheet"
	href="https://res.wx.qq.com/open/libs/weui/1.1.3/weui.min.css">
<!-- jqueryWEUI 中 -->
<link rel="stylesheet" href="https://cdn.bootcss.com/weui/1.1.3/style/weui.min.css">
<link rel="stylesheet" href="https://cdn.bootcss.com/jquery-weui/1.2.1/css/jquery-weui.min.css">
</head>

<body style="background-color: #efeff4;">

	<form action="personalCenterAction_submitApplication.action" method="get">

		<input id="tag" name="tag"  type="hidden"  value='<s:property value="tag"/>' />
		<input id="lid" name="lid"  type="hidden"  value='<s:property value="lid"/>'/>

		<div class="weui-cells__title">您为什么选择我们？</div>
		<div class="weui-cells weui-cells_form">
			<div class="weui-cell">
				<!-- <div class="weui-cell__hd">
	                <span class="weui-label" style="vertical-align: middle">姓名</span>
	            </div> -->
				<div class="weui-cell__bd">
					<input onchange="canSubmit();"
						class="weui-input" type="text" placeholder="例如：我喜欢参加社区公益活动。" name="theReason"
						id="theReason" />
				</div>
			</div>
		</div>


		<div class="weui-cells__title">您的专长是？</div>
		<div class="weui-cells weui-cells_form">
			<div class="weui-cell">
				<!-- <div class="weui-cell__hd">
                <span class="weui-label" style="vertical-align: middle">姓名</span>
            </div> -->
				<div class="weui-cell__bd">
					<input id="theExpertise" name="theExpertise"
						onchange="canSubmit();" class="weui-input"
						type="text"  placeholder="例如：写作，绘画，舞蹈">
				</div>
			</div>
		</div>

		<div class="weui-cells__title">您对加入组织后的期许是？</div>
		<div class="weui-cells weui-cells_form">
			<div class="weui-cell">
				<!-- <div class="weui-cell__hd">
                <span class="weui-label" style="vertical-align: middle">姓名</span>
            </div> -->
				<div class="weui-cell__bd">
					<input id="theDesire" name="theDesire"
						onchange="canSubmit();" class="weui-input"
						type="text"  placeholder="例如：能够让自己变得更阳光外向。">
				</div>
			</div>
		</div>

		<div class="weui-cells__tips">请您如实填写每项内容</div>
		<button class="weui-btn weui-btn_primary mt-2 weui-btn_disabled"  id="submit"   onclick="submit();">提交申请</button>

	</form>
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
<!-- jqueryWEUI 最后 -->
<script src="https://cdn.bootcss.com/jquery-weui/1.2.1/js/jquery-weui.min.js"></script>
<script>

	// 用户点击提交application按钮后调用本方法，停用button防止重复提交
	function submit(){
		let $self = $(this);
		// 让按钮失效防止重复提交
		$self.addClass("weui-btn_disabled");
		// 通过jqueryWEUI实现永久显示 loading的toast
		$.showLoading("提交申请中...");
	}
	
	function canSubmit(){
			var theReason = $("#theReason").val();
			var theExpertise = $("#theExpertise").val();
			var theDesire = $("#theDesire").val();
			if ("" == theReason || null == theReason) {
				console.log("原因为必填项");
				$("#submit").addClass("weui-btn_disabled");
				return;
			}
			if ("" == theExpertise || null == theExpertise) {
				console.log("专长为必填项");
				$("#submit").addClass("weui-btn_disabled");
				return;
			}
			if ("" == theDesire || null == theDesire) {
				console.log("期望为必填项");
				$("#submit").addClass("weui-btn_disabled");
				return;
			}
			console.log("现在可以提交了");
			$("#submit").removeClass("weui-btn_disabled");
	}
	
	
</script>
</html>