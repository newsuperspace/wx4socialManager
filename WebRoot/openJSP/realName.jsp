<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>实名认证</title>
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

	<form action="personalCenterAction_realName.action" method="get">
	<div class="weui-cells__title">您的姓名是？</div>
	<div class="weui-cells weui-cells_form">
		<div class="weui-cell">
			<!-- <div class="weui-cell__hd">
                <span class="weui-label" style="vertical-align: middle">姓名</span>
            </div> -->
			<div class="weui-cell__bd">
				<input onchange="aboutWeixin.op.canCheckRealName();"
					class="weui-input" type="text" 
					placeholder="例如：张三" name="username" id="username" />
			</div>
		</div>
	</div>

	<div class="weui-cells__title">您的性别是？</div>
	<div class="weui-cells weui-cells_radio">
		 <label class="weui-cell weui-check__label" for="x11">
            <div class="weui-cell__bd">
                <span style="vertical-align: middle">男</span>
            </div>
            <div class="weui-cell__ft">
                <input type="radio" class="weui-check" name="sex" id="x11" value="1" checked="checked">
                <span class="weui-icon-checked"></span>
            </div>
        </label>
        <label class="weui-cell weui-check__label" for="x12">
            <div class="weui-cell__bd">
                <span style="vertical-align: middle">女</span>
            </div>
            <div class="weui-cell__ft">
                <input type="radio" name="sex" class="weui-check" value="2" id="x12">
                <span class="weui-icon-checked"></span>
            </div>
        </label>
	</div>

	<div class="weui-cells__title">您的手机号是？</div>
	<div class="weui-cells weui-cells_form">
		<div class="weui-cell">
			<!-- <div class="weui-cell__hd">
                <span class="weui-label" style="vertical-align: middle">姓名</span>
            </div> -->
			<div class="weui-cell__bd">
				<input id="phone" name="phone"
					onchange="aboutWeixin.op.canCheckRealName();" class="weui-input"
					type="tel" name="phone" id="phone" 
					placeholder="例如：15001222837">
			</div>
		</div>
	</div>

	<div class="weui-cells__title">您的生日是？</div>
	<div class="weui-cells weui-cells_form">
		<div class="weui-cell">
			<!-- <div class="weui-cell__hd">
                <span class="weui-label" style="vertical-align: middle">姓名</span>
            </div> -->
			<div class="weui-cell__bd">
				<input id="birth" class="weui-input"
					onchange="aboutWeixin.op.canCheckRealName();" type="text" value=""
					name="birth" />
			</div>
		</div>
	</div>

	<div class="weui-cells__tips">请您如实填写每项内容</div>
	<button type="submit" class="weui-btn weui-btn_primary mt-2 weui-btn_disabled" 
		id="submit">确认</button>
		
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
<script>

	$('#birth').on('focus', function() {
		// 当birth获得焦点后就会触发下方的weui的日期拾取器的初始化
		weui.datePicker({
			// 设置起始日期（yyyy-MM-dd）
			start : "1900-1-1",
			// 设置截至日期
			end : new Date(),
			// 设置默认开始日期
			defaultValue : [ 1980, 7, 15 ],
			// 监听变化后触发
			onChange : function(result) {
				console.log(result);
			},
			// 监听提交时触发
			onConfirm : function(result) {
				let birth = "";
				for (let i = 0; i < result.length; i++) {
					console.log(result[i].value);
					birth += result[i].value;
					if ((i + 1) != result.length) {
						birth += "-";
					}
				}
				console.log(birth);
				$("#birth").val(birth);
				aboutWeixin.op.canCheckRealName();
			}
		});
	});
</script>
</html>