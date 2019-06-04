<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>已参加的活动</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<!-- Bootstrap CSS -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/bootstrap.css">
<link rel="stylesheet"
	href="https://res.wx.qq.com/open/libs/weui/1.1.3/weui.min.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/default.css">
</head>

<body style="background-color: #f8f8f8;">

	
	<div id="signinBoard" class="htmleaf-container">
		<div class="container">
			<div class="row">
				<div class="col-xs-12">
					<h3>电子签名板：</h3>
					<p>请在下方手写签名:</p>
					<div class="js-signature" data-width="600" data-height="200"
						data-border="1px solid black" data-line-color="#bc0000"
						data-auto-fit="true"></div>
					<p>
						<button id="clearBtn" class="btn btn-default"
							onclick="clearCanvas();">重写</button>
						&nbsp;
						<button id="saveBtn" class="btn btn-default"
							onclick="saveSignature();" disabled>提交</button>
					</p>
				</div>
			</div>
		</div>
	</div>



</body>
<script src="${pageContext.request.contextPath}/js/jquery-3.2.0.js"></script>
<script src="${pageContext.request.contextPath}/js/popper.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.bundle.js"></script>
<script src="${pageContext.request.contextPath}/js/jq-signature.js"></script>
<script>

	// 与电子签名板有关的逻辑
	$(function() {
			$('.js-signature').jqSignature();
			console.log("signature 准备完毕");
	});

	function cancelSignature() {
		clearCanvas();
		localStorage.setItem("b64Str", "");
		$("#signinBoard").attr("hidden", true);
		$("#theContent").attr("hidden", false);
	}

	function clearCanvas() {
		$('.js-signature').eq(0).jqSignature('clearCanvas');
		$('#saveBtn').attr('disabled', true);
	}

	function saveSignature() {
		var dataUrl = $('.js-signature').eq(0).jqSignature('getDataURL');
		var b64Str = dataUrl.replace(/^data:image\/(png|jpg);base64,/, "");
	}

	$('.js-signature').eq(0).on('jq.signature.changed', function() {
		$('#saveBtn').attr('disabled', false);
	});

	// 为了能够使用POST方式传递签名图片的B64字符串（过长，无法使用GET方式），特意在jQuery中拓展了一个方法
	$.extend({
		StandardPost : function(url, args) {
			var body = $(document.body),
				form = $("<form method='post'></form>"),
				input;
			form.attr({
				"action" : url
			});
			$.each(args, function(key, value) {
				input = $("<input type='hidden'>");
				input.attr({
					"name" : key
				});
				input.val(value);
				form.append(input);
			});
			form.appendTo(document.body);
			form.submit();
			document.body.removeChild(form[0]);
		}
	});



</script>
</html>