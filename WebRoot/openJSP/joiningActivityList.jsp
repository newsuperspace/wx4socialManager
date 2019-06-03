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
<link rel="stylesheet" type="text/css" href="css/default.css">
</head>

<body style="background-color: #f8f8f8;">

	<div id="theContent" hidden="false">
		<!--头部信息-->
		<div style="padding: 30px;">
			<h1
				style="text-align: left;font-size: 20px;font-weight: 400;font-weight: bold;">正在参加的活动</h1>
			<p
				style="margin-top: 5px;color: #888;text-align: left;font-size: 14px;">在这里您可以看到已经报名的所有活动，您可以了解活动时间或者取消报名</p>
		</div>

		<s:iterator value="#list">
			<!--容纳各种PreView-->
			<div>
				<!--这是一个preView，用来展示一个活动信息-->
				<div class="weui-form-preview mt-2">
					<!--头-->
					<div class="weui-form-preview__hd">
						<div class="weui-form-preview__item">
							<label class="weui-form-preview__label">活动名称</label> <em
								class="weui-form-preview__value" id="name"><s:property
									value="name" /></em>
						</div>
					</div>
					<!--体-->
					<div class="weui-form-preview__bd">
						<div class="weui-form-preview__item">
							<label class="weui-form-preview__label">发起方</label> <span
								class="weui-form-preview__value" id="initiator"> <s:if
									test="project.zeroLevel==null">
									<span class="weui-form-preview__value" id="initiator"><s:property
											value="project.minusFirstLevel.name" /></span>
								</s:if> <s:elseif test="project.firstLevel==null">
									<span class="weui-form-preview__value" id="initiator"><s:property
											value="project.zeroLevel.name" /></span>
								</s:elseif> <s:elseif test="project.secondLevel==null">
									<span class="weui-form-preview__value" id="initiator"><s:property
											value="project.firstLevel.name" /></span>
								</s:elseif> <s:elseif test="project.thirdLevel==null">
									<span class="weui-form-preview__value" id="initiator"><s:property
											value="project.secondLevel.name" /></span>
								</s:elseif> <s:else>
									<span class="weui-form-preview__value" id="initiator"><s:property
											value="project.thirdLevel.name" /></span>
								</s:else></span>
						</div>
						<div class="weui-form-preview__item">
							<label class="weui-form-preview__label">活动时间</label> <span
								class="weui-form-preview__value" id="time"> <s:property
									value="beginTimeStr" />到<s:property value="endTimeStr" />
							</span>
						</div>
						<div class="weui-form-preview__item">
							<label class="weui-form-preview__label">活动简介</label> <span
								class="weui-form-preview__value" id=”description“><s:property
									value="description" /></span>
						</div>
						<div class="weui-form-preview__item">
							<label class="weui-form-preview__label">活动地点</label> <span
								class="weui-form-preview__value" id="position"> <s:if
									test="activityType==1">
									<s:property value="geographic.name" />
								</s:if> <s:elseif test="activityType==2">
									<s:property value="house.name" />
								</s:elseif>
							</span>
						</div>
						<!-- <div class="weui-form-preview__item">
                                <label class="weui-form-preview__label">活动地点</label>
                                <span class="weui-form-preview__value" id="place">核桃园社区会议室</span>
                            </div> -->
						<div class="weui-form-preview__item">
							<label class="weui-form-preview__label">活动积分</label> <span
								class="weui-form-preview__value" id="score"><s:property
									value="score" /></span>
						</div>
						<div class="weui-form-preview__item">
							<label class="weui-form-preview__label">活动状态</label> <span
								class="weui-form-preview__value" id="limit"> <!--badge-primary|secondary|success|danger|warning|info|light|dark-->
								<span class="badge badge-primary"><s:property
										value="state" /></span>
							</span>
						</div>
					</div>
					<!--足-->
					<div class="weui-form-preview__ft">
						<!-- <a class="weui-form-preview__btn weui-form-preview__btn_default" href="javascript:">取消报名</a> -->
						<s:if test="buttonState=='可取消'">
							<s:a onclick="cancelBaoming('%{aid}');" href="#"
								cssClass="weui-form-preview__btn weui-form-preview__btn_default">取消报名</s:a>
						</s:if>
						<s:elseif test="buttonState=='可签到'">
							<s:a onclick="access4qd('%{needSignin}','','qianDao');" href="#"
								cssClass="weui-form-preview__btn weui-form-preview__btn_primary">扫码签到</s:a>
							<s:a
								onclick="access4qd('%{needSignin}','%{aid}','qianDao4Position');"
								href="#"
								cssClass="weui-form-preview__btn weui-form-preview__btn_primary">自主签到</s:a>
						</s:elseif>
						<s:elseif test="buttonState=='可签退'">
							<s:a onclick="qianTui();" href="#"
								cssClass="weui-form-preview__btn weui-form-preview__btn_primary">扫码签退</s:a>
							<s:a onclick="qianTui4Position('%{aid}');" href="#"
								cssClass="weui-form-preview__btn weui-form-preview__btn_primary">自主签退</s:a>
						</s:elseif>
						<s:elseif test="buttonState=='已签到'">
							<s:a onclick="hasQianDao('%{theVisitor.startTimeStr}');" href="#"
								cssClass="weui-form-preview__btn weui-form-preview__btn_primary">您已签到</s:a>
						</s:elseif>
						<s:elseif test="buttonState=='已签退'">
							<s:a onclick="hasQianTui('%{theVisitor.endTimeStr}');" href="#"
								cssClass="weui-form-preview__btn weui-form-preview__btn_primary">您已签退</s:a>
						</s:elseif>
						<s:elseif test="buttonState=='已爽约'">
							<s:a onclick="shuangYue();" href="#"
								cssClass="weui-form-preview__btn weui-form-preview__btn_primary">您已爽约</s:a>
						</s:elseif>
						<s:elseif test="buttonState=='同步签到签退'">
							<s:a onclick="sychronize('%{theVisitor.startTimeStr}');" href="#"
								cssClass="weui-form-preview__btn weui-form-preview__btn_primary">您已同步签到/退</s:a>
						</s:elseif>
					</div>
				</div>
			</div>
		</s:iterator>
	</div>


	<div id="signinBoard" class="htmleaf-container" hidden="true">
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
<script src="js/jq-signature.js"></script>
<script>

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


	// 与电子签名板有关的逻辑
	$(document).on('ready', function() {
		if ($('.js-signature').length) {
			$('.js-signature').jqSignature();
		}
	});

	function clearCanvas() {
		$('.js-signature').eq(0).jqSignature('clearCanvas');
		$('#saveBtn').attr('disabled', true);
	}

	function saveSignature() {
		var dataUrl = $('.js-signature').eq(0).jqSignature('getDataURL');
		var b64Str = dataUrl.replace(/^data:image\/(png|jpg);base64,/, "");
		localStorage.setItem("b64Str", b64Str);
		// 开始根据localStorage中的type来调用qianDao或qianDao4position方法执行签到逻辑
		$("#signinBoard").attr("hidden", true);
		$("#theContent").attr("hidden", false);
		var type = localStorage.getItem("type");
		if ("qianDao" == type) {
			qianDao();
		} else if ("qianDao4Position" == type) {
			var aid = localStorage.getItem("aid");
			qianDao4Position(aid);
		} else {
			weui.alert("错误！未指定签到类型，请重试。");
		}

	}

	$('.js-signature').eq(0).on('jq.signature.changed', function() {
		$('#saveBtn').attr('disabled', false);
	});


	// 签到功能入口，参数分别为：是否手签、活动aid（如果有），签到类型（用于确定调用qianDao()还是qianDao4position()）
	function access4qd(needSignin, aid, type) {
		if ("true" == needSignin) {
			localStorage.setItem("type", type);
			localStorage.setItem("aid", aid);
			// 需要手签
			clearCanvas();
			localStorage.setItem("b64Str", "");
			$("#signinBoard").attr("hidden", false);
			$("#theContent").attr("hidden", true);
		} else {
			// 不需要手签，直接调用目标方法
			if ("qianDao" == type) {
				qianDao();
			} else if ("qianDao4Position" == type) {
				qianDao4Position(aid);
			}
		}
	}


	function qianDao() {
		wx.scanQRCode({
			desc : '扫描活动二维码完成签到',
			needResult : 1, // 默认为0，扫描结果由微信处理，1则直接返回扫描结果，
			scanType : [ "qrCode", "barCode" ], // 可以指定扫二维码还是一维码，默认二者都有
			// 扫码成功的回调
			success : function(res) {
				// 通过res.resultStr 得到二维码的结果字符串
				let aid = res.resultStr;
				let b64Str = localStorage.getItem("b64Str");
				let param = {
					"aid" : aid,
					"b64Str" : b64Str
				}
				let url = "personalCenterAction_qianDao.action";
				$.StandardPost(url, param);
			//let url = "personalCenterAction_qianDao.action?" + "aid=" + aid;
			//$(location).attr("href", url);
			},
			// 扫码失败的回调
			error : function(res) {
				if (res.errMsg.indexOf('function_not_exist') > 0) {
					alert('版本过低请升级')
				}
			}
		});
	}

	// 基于地理位置完成自主签到
	function qianDao4Position(aid) {
		let param;
		// 首先通过微信的JS-SDK获取当前地理位置坐标（经纬度）
		wx.getLocation({
			type : 'gcj02', // 默认为wgs84的gps坐标，如果要返回直接给openLocation用的火星坐标，可传入'gcj02'
			success : function(res) {
				// 获取可能存在的Base64编码字符串
				let b64Str = localStorage.getItem("b64Str");				
				//使用微信内置地图查看位置接口
				let latitude = res.latitude; // 纬度，浮点数，范围为90 ~ -90
				let longitude = res.longitude; // 经度，浮点数，范围为180 ~ -180。
				let speed = res.speed; // 速度，以米/每秒计
				let accuracy = res.accuracy; // 位置精度
				// 准备POST请求参数
				param = {
					"aid": aid,
					"b64Str": b64Str,
					"latitude": latitude,
					"longitude": longitude,
				}
				
			// 下面是打开腾讯地图的API，暂时不用
			//wx.openLocation({
			//latitude : res.latitude, // 纬度，浮点数，范围为90 ~ -90
			//longitude : res.longitude, // 经度，浮点数，范围为180 ~ -180。
			//name : '我的位置', // 位置名
			//address : '329创业者社区', // 地址详情说明
			//scale : 28, // 地图缩放级别,整形值,范围从1~28。默认为最大
			//infoUrl : 'http://www.gongjuji.net' // 在查看位置界面底部显示的超链接,可点击跳转（测试好像不可用）
			//});
			},
			cancel : function(res) {}
		});
		// 准备路径跳转	
		let url = "personalCenterAction_qianDao4Position.action";
		$.StandardPost(url, param);
	}

	function qianTui() {
		wx.scanQRCode({
			desc : '扫描活动二维码完成签退',
			needResult : 1, // 默认为0，扫描结果由微信处理，1则直接返回扫描结果，
			scanType : [ "qrCode", "barCode" ], // 可以指定扫二维码还是一维码，默认二者都有
			// 扫码成功的回调
			success : function(res) {
				// 通过res.resultStr 得到二维码的结果字符串
				let aid = res.resultStr;
				let url = "personalCenterAction_qianTui.action?" + "aid=" + aid;
				$(location).attr("href", url);
			},
			// 扫码失败的回调
			error : function(res) {
				if (res.errMsg.indexOf('function_not_exist') > 0) {
					alert('版本过低请升级')
				}
			}
		});
	}

	// 基于地理位置的自主签退
	function qianTui4Position(aid) {
		// 首先通过微信的JS-SDK获取当前地理位置坐标（经纬度）
		wx.getLocation({
			type : 'gcj02', // 默认为wgs84的gps坐标，如果要返回直接给openLocation用的火星坐标，可传入'gcj02'
			success : function(res) {
				//使用微信内置地图查看位置接口
				latitude = res.latitude; // 纬度，浮点数，范围为90 ~ -90
				longitude = res.longitude; // 经度，浮点数，范围为180 ~ -180。
				speed = res.speed; // 速度，以米/每秒计
				accuracy = res.accuracy; // 位置精度
			// 下面是打开腾讯地图的API，暂时不用
			//wx.openLocation({
			//latitude : res.latitude, // 纬度，浮点数，范围为90 ~ -90
			//longitude : res.longitude, // 经度，浮点数，范围为180 ~ -180。
			//name : '我的位置', // 位置名
			//address : '329创业者社区', // 地址详情说明
			//scale : 28, // 地图缩放级别,整形值,范围从1~28。默认为最大
			//infoUrl : 'http://www.gongjuji.net' // 在查看位置界面底部显示的超链接,可点击跳转（测试好像不可用）
			//});
			},
			cancel : function(res) {}
		});
		// 准备路径跳转	
		let url = "personalCenterAction_qianTui4Position.action?" + "aid=" + aid + "&latitude=" + latitude + "&longitude=" + longitude;
		$(location).attr("href", url);
	}




	// 取消报名
	function cancelBaoming(aid) {
		weui.confirm('自定义按钮的confirm', {
			title : '是否取消报名？',
			content : "报名取消后，活动信息会移回可报名活动列表",
			buttons : [ {
				label : '再想想',
				type : 'default',
				onClick : function() {}
			}, {
				label : '狠心取消',
				type : 'primary',
				onClick : function() {
					// 执行报名逻辑ajax操作
					let url = "personalCenterAction_cancelBaoMing.action";
					let param = {
						aid : aid
					}

					$.post(url, param, function(data, textStatus, req) {
						weui.alert(data.message);
						window.location.reload();
					});
				}
			} ]
		});
	}

	function hasQianDao(time) {
		weui.alert("您已于" + time + "完成签到");
	}

	function hasQianTui(time) {
		weui.alert("您已于" + time + "完成签退");
	}

	function shuangYue() {
		weui.alert("由于你没有在活动开始后30分钟内签到，已被系统认定为爽约，如有疑问请联系管理员");
	}

	function sychronize(time) {
		weui.alert("您已与" + time + "同步完成签到和签退，无需再签退了");
	}
</script>
</html>