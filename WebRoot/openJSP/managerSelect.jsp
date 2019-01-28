<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>管理层选择</title>
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

<body style="background-color: #f8f8f8;">

	<div>
		<!--头部信息-->
		<div style="padding: 30px;">
			<h1
				style="text-align: left;font-size: 20px;font-weight: 400;font-weight: bold;">请选择您要登陆的管理层</h1>
			<p
				style="margin-top: 5px;color: #888;text-align: left;font-size: 14px;">您当前是多个层级的管理者，请选择要登录的目标层级</p>
		</div>
	</div>

	<div class="page actionsheet js_show">
		<div class="page__bd page__bd_spacing">
			<a href="javascript:;" class="weui-btn weui-btn_default"
				id="showIOSActionSheet">层级选择</a>
		</div>
		<!--BEGIN actionSheet-->
		<div>
			<div class="weui-mask" id="iosMask" style="opacity: 1;" hidden></div>
			<div class="weui-actionsheet" id="iosActionsheet">
				<div class="weui-actionsheet__title">
					<p class="weui-actionsheet__title-text">下面是您管理的所有层级，请选择其中一个登陆</p>
				</div>
				<div class="weui-actionsheet__menu">

					<s:iterator value="%{#managers}">
						<div class="weui-actionsheet__cell" onclick="selector(this);"
							id='<s:property value="lid"/>'  data-tag='<s:property value="member.grouping.tag"/>'><s:property value="levelName"/></div>
					</s:iterator>

				</div>
				<div class="weui-actionsheet__action">
					<div class="weui-actionsheet__cell" id="iosActionsheetCancel">取消</div>
				</div>
			</div>
		</div>

	</div>

	<!--FOOT-->
	<div class="weui-footer mt-5">
		<p class="weui-footer__text">Copyright &copy; 2017-2019 联合会提供技术支持</p>
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

	// 选择结果，进行跳转，进入shiroAction的正式登陆环节，从这里开始				
	function selector(self) {
		var $self = $(self);
		weui.confirm('是否登陆' + $self.text() + "?", {
			title : '选择结果',
			buttons : [ {
				label : '算了',
				type : 'default',
				onClick : function() {
					console.log("算了");
				}
			}, {
				label : '确定',
				type : 'primary',
				onClick : function() {
					let  param  = {
						lid: $self.attr("id"),
						tag: $self.attr("data-tag"),
					}
					$.post("shiroAction_authenticationByRealms.action", param, function(data, textStatus, req) {
						$(location).attr("href","index.jsp");
					});
				}
			} ]
		});
	}

	// ios
	$(function() {
		var $iosActionsheet = $('#iosActionsheet');
		var $iosMask = $('#iosMask');

		function hideActionSheet() {
			$iosActionsheet.removeClass('weui-actionsheet_toggle');
			$iosMask.attr("hidden", true);
			$iosMask.fadeOut(200);
		}

		$iosMask.on('click', hideActionSheet);
		$('#iosActionsheetCancel').on('click', hideActionSheet);

		$("#showIOSActionSheet").on("click", function() {
			$iosActionsheet.addClass('weui-actionsheet_toggle');
			$iosMask.attr("hidden", false);
			$iosMask.fadeIn(200);
		});

	});
</script>
</html>