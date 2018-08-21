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
</head>

<body style="background-color: #f8f8f8;">

	<div>
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
									value="beginTimeStr" />~<s:property value="endTimeStr" />
							</span>
						</div>
						<div class="weui-form-preview__item">
							<label class="weui-form-preview__label">活动简介</label> <span
								class="weui-form-preview__value" id=”description“><s:property
									value="description" /></span>
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
							<s:a onclick="qianDao();" href="#"
								cssClass="weui-form-preview__btn weui-form-preview__btn_primary">扫码签到</s:a>
						</s:elseif>
						<s:elseif test="buttonState=='可签退'">
							<s:a onclick="qianTui();" href="#"
								cssClass="weui-form-preview__btn weui-form-preview__btn_primary">扫码签退</s:a>
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
					</div>
				</div>
			</div>
		</s:iterator>

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
	function cancelBaoming(aid) {
		weui.confirm('自定义按钮的confirm', {
			title : '是否取消报名？',
			content : "报名取消后，活动信息会移回可报名活动列表",
			buttons : [ {
				label : '再想想',
				type : 'default',
				onClick : function() {
				}
			}, {
				label : '狠心取消',
				type : 'primary',
				onClick : function() {
					// 执行报名逻辑ajax操作
					let url = "personalCenterAction_cancelBaoMing.action";
					let param = {
						aid: aid
					}
					
					$.post(url, param, function(data, textStatus, req) {
						weui.alert(data.message);
						window.location.reload();
					});
				}
			} ]
		});
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
				let url = "personalCenterAction_qianDao.action?"+"aid="+aid;
				$(location).attr("href",url);
			},
			// 扫码失败的回调
			error : function(res) {
				if (res.errMsg.indexOf('function_not_exist') > 0) {
					alert('版本过低请升级')
				}
			}
		});
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
				let url = "personalCenterAction_qianTui.action?"+"aid="+aid;
				$(location).attr("href",url);
			},
			// 扫码失败的回调
			error : function(res) {
				if (res.errMsg.indexOf('function_not_exist') > 0) {
					alert('版本过低请升级')
				}
			}
		});
	}
	
	function hasQianDao(time){
		weui.alert("您已于"+time+"完成签到");
	}
	
	function hasQianTui(time){
		weui.alert("您已于"+time+"完成签退");
	}
	
	function shuangYue(){
		weui.alert("由于你没有在活动开始后30分钟内签到，已被系统认定为爽约，如有疑问请联系管理员");
	}
	
</script>
</html>