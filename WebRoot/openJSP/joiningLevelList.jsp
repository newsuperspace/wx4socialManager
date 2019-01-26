<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>已加入的组织（member）</title>
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
			<div class="container">
				<div class="row">
					<div class="col-9">
						<h1
							style="text-align: left;font-size: 20px;font-weight: 400;font-weight: bold;">您当前已加入的组织</h1>
						<p
							style="margin-top: 5px;color: #888;text-align: left;font-size: 14px;">退出或加入</p>
					</div>
					<div class="col-3">
						<div class="dropdown ml-1">
							<button class="btn btn-sm btn-outline-secondary dropdown-toggle"
								type="button" id="others" data-toggle="dropdown"
								aria-haspopup="true" aria-expanded="false">
								<span class="glyphicon glyphicon-align-justify"></span> 
							</button>
							<div class="dropdown-menu dropdown-menu-right"
								aria-labelledby="others">
								<a class="dropdown-item" href="#" onclick="joinByScanQRCode();">扫码加入</a>
								<a class="dropdown-item" href="#" onclick="myApplies();">我的申请</a>
								<div class="dropdown-divider"></div>
								<a class="dropdown-item" href="#">其他功能</a>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>

		<s:iterator value="#members">
			<!--容纳各种PreView-->
			<div>
				<!--这是一个preView，用来展示一个活动信息-->
				<div class="weui-form-preview mt-2">
					<!--头开始-->
					<div class="weui-form-preview__hd">
						<div class="weui-form-preview__item">
							<label class="weui-form-preview__label">组织名称</label> <em
								class="weui-form-preview__value" id="name"> <s:if
									test="null!=fourthLevel">
									<s:property value="fourthLevel.name" />
								</s:if> <s:elseif test="null!=thirdLevel">
									<s:property value="thirdLevel.name" />
								</s:elseif> <s:elseif test="null!=secondLevel">
									<s:property value="secondLevel.name" />
								</s:elseif> <s:elseif test="null!=firstLevel">
									<s:property value="firstLevel.name" />
								</s:elseif> <s:elseif test="null!=zeroLevel">
									<s:property value="zeroLevel.name" />
								</s:elseif> <s:elseif test="null!=minusFirstLevel">
									<s:property value="minusFirstLevel.name" />
								</s:elseif>
							</em>
						</div>
					</div>
					<!--头结束-->
					<!--体开始-->
					<div class="weui-form-preview__bd">
						<div class="weui-form-preview__item">
							<label class="weui-form-preview__label">组织简介</label> <span
								class="weui-form-preview__value" id="time"> <s:if
									test="null!=fourthLevel">
									<s:property value="fourthLevel.description" />
								</s:if> <s:elseif test="null!=thirdLevel">
									<s:property value="thirdLevel.description" />
								</s:elseif> <s:elseif test="null!=secondLevel">
									<s:property value="secondLevel.description" />
								</s:elseif> <s:elseif test="null!=firstLevel">
									<s:property value="firstLevel.description" />
								</s:elseif> <s:elseif test="null!=zeroLevel">
									<s:property value="zeroLevel.description" />
								</s:elseif> <s:elseif test="null!=minusFirstLevel">
									<s:property value="minusFirstLevel.description" />
								</s:elseif>
							</span>
						</div>

						<div class="weui-form-preview__item">
							<label class="weui-form-preview__label">组织等级</label> <span
								class="weui-form-preview__value" id=”description“> <s:property
									value="grouping.tag" />
							</span>
						</div>

						<div class="weui-form-preview__item">
							<label class="weui-form-preview__label">组织人数</label> <span
								class="weui-form-preview__value" id="limit"> <!--badge-primary|secondary|success|danger|warning|info|light|dark-->
								<span class="badge badge-success"> <s:if
										test="null!=fourthLevel">
										<s:property value="fourthLevel.members.size()" />
									</s:if> <s:elseif test="null!=thirdLevel">
										<s:property value="thirdLevel.members.size()" />
									</s:elseif> <s:elseif test="null!=secondLevel">
										<s:property value="secondLevel.members.size()" />
									</s:elseif> <s:elseif test="null!=firstLevel">
										<s:property value="firstLevel.members.size()" />
									</s:elseif> <s:elseif test="null!=zeroLevel">
										<s:property value="zeroLevel.members.size()" />
									</s:elseif> <s:elseif test="null!=minusFirstLevel">
										<s:property value="minusFirstLevel.members.size()" />
									</s:elseif>
							</span>
							</span>
						</div>
					</div>
					<!--体结束-->
					<!--足开始-->
					<div class="weui-form-preview__ft">

						<s:if test="null!=fourthLevel">
							<s:a onclick="tuichu('fourth','%{fourthLevel.foid}');" href="#"
								cssClass="weui-form-preview__btn weui-form-preview__btn_primary">退出组织</s:a>
						</s:if>
						<s:elseif test="null!=thirdLevel">
							<s:a onclick="tuichu('third','%{thirdLevel.thid}');" href="#"
								cssClass="weui-form-preview__btn weui-form-preview__btn_primary">退出组织</s:a>
						</s:elseif>
						<s:elseif test="null!=secondLevel">
							<s:a onclick="tuichu('second','%{secondLevel.scid}');" href="#"
								cssClass="weui-form-preview__btn weui-form-preview__btn_primary">退出组织</s:a>
						</s:elseif>
						<s:elseif test="null!=firstLevel">
							<s:a onclick="tuichu('first','%{firstLevel.flid}');" href="#"
								cssClass="weui-form-preview__btn weui-form-preview__btn_primary">退出组织</s:a>
						</s:elseif>
						<s:elseif test="null!=zeroLevel">
							<s:a onclick="tuichu('zero','%{zeroLevel.zid}');" href="#"
								cssClass="weui-form-preview__btn weui-form-preview__btn_primary">退出组织</s:a>
						</s:elseif>
						<s:elseif test="null!=minusFirstLevel">
							<s:a onclick="tuichu('minus_first','%{minusFirstLevel.mflid}');"
								href="#"
								cssClass="weui-form-preview__btn weui-form-preview__btn_primary">退出组织</s:a>
						</s:elseif>

					</div>
					<!--足结束-->
				</div>
			</div>
		</s:iterator>
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
	// -------------扫码加入------------
	function joinByScanQRCode() {
		wx.scanQRCode({
			desc : '扫描组织层级二维码来加入该组织',
			needResult : 1, // 默认为0，扫描结果由微信处理，1则直接返回扫描结果，
			scanType : [ "qrCode", "barCode" ], // 可以指定扫二维码还是一维码，默认二者都有
			// 扫码成功的回调
			success : function(res) {
				// 通过res.resultStr 得到二维码的结果字符串
				// 层级对象二维码的格式形如:"tag=fourth&lid=abb8c3b7-ad77-4730-aedb-1475dcaa0772" 可直接用作url的GET请求参数拼接在URL之后
				let result = res.resultStr;
				let url = "personalCenterAction_joinByScanQRCode.action?" + result;
				$(location).attr("href", url);
			},
			// 扫码失败的回调
			error : function(res) {
				if (res.errMsg.indexOf('function_not_exist') > 0) {
					weui.alert('版本过低请升级');
				}
			}
		});
	}

	// -------------跳转到当前用户所提交的加入组织的所有申请列表页面------------
	function myApplies(){
		$(location).attr("href","personalCenterAction_applies4JoinLevel.action");
	}

	// -------------退出组织------------
	function tuichu(tag, lid) {
		weui.confirm('自定义按钮的confirm', {
			title : '是否确定退出该组织？',
			content : "退出该组织后将收不到该组织发送的活动信息了",
			buttons : [ {
				label : '再想想',
				type : 'default',
				onClick : function() {
					console.log("不退出了");
				}
			}, {
				label : '确定退出',
				type : 'primary',
				onClick : function() {
					// 执行报名逻辑ajax操作
					let url = "personalCenterAction_tuichu.action";
					let param = {
						'tag' : tag,
						'lid' : lid
					}
					$.post(url, param, function(data, textStatus, req) {
						if (data.result) {
							// 退出成功，弹出提示并刷新joiningLevelList.jsp页面
							weui.alert(data.message);
							// 刷新页面
							window.location.reload();
						} else {
							// 退出失败，只弹出错误提示信息（例如：您当前为该组织管理员，请联系组织管理者卸任后再退出）
							weui.alert(data.message);
						}
					});
				}
			} ]
		});

	}
</script>
</html>