<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>组织申请加入记录</title>
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
				style="text-align: left;font-size: 20px;font-weight: 400;font-weight: bold;">申请历史</h1>
			<p
				style="margin-top: 5px;color: #888;text-align: left;font-size: 14px;">在这里您可以查看所有申请记录的处理结果</p>
		</div>
		<!--容纳各种PreView-->
		<s:iterator value="#applies">
			<div>
				<!--这是一个preView，用来展示一个活动信息-->
				<div class="weui-form-preview mt-2">
					<!--头-->
					<!-- <div class="weui-form-preview__hd">
                    <div class="weui-form-preview__item">
                        <label class="weui-form-preview__label">活动名称</label>
                        <em class="weui-form-preview__value">呼家楼街道核桃园社区创享计划说明会</em>
                    </div>
                </div> -->
					<!--体-->
					<div class="weui-form-preview__bd">
						<div class="weui-form-preview__item">
							<label class="weui-form-preview__label">组织名称</label> <span
								class="weui-form-preview__value" id="levelName"><s:property
									value="levelName" /></span>
						</div>

						<div class="weui-form-preview__item">
							<label class="weui-form-preview__label">组织简介</label> <span
								class="weui-form-preview__value" id="levelDescription"> <s:property
									value="levelDescription" />
							</span>
						</div>

						<div class="weui-form-preview__item">
							<label class="weui-form-preview__label">申请时间</label> <span
								class="weui-form-preview__value" id="time"><s:property
									value="timeStr" /></span>
						</div>

						<div class="weui-form-preview__item">
							<label class="weui-form-preview__label">申请状态</label> <span
								class="weui-form-preview__value" id="initiator"> <s:if
									test="status==0">
									<span class="badge badge-primary">未审核</span>
								</s:if> <s:elseif test="status==1">
									<span class="badge badge-secondary">已拒绝</span>
								</s:elseif> <s:elseif test="status==2">
									<span class="badge badge-success">已通过</span>
								</s:elseif>
							</span>
						</div>

						<s:if test="status==1">
							<br>
							<div class="weui-form-preview__item">
								<label class="weui-form-preview__label">受理时间</label> <span
									class="weui-form-preview__value" id="approveTime"><s:property
										value="approve4UserJoinLevel.timeStr" /></span>
							</div>

							<div class="weui-form-preview__item">
								<label class="weui-form-preview__label">拒绝原因</label> <span
									class="weui-form-preview__value" id="message"> <s:property
										value="approve4UserJoinLevel.replies[0].message" />
								</span>
							</div>
						</s:if>
						<s:elseif test="status==2">
							<br>
							<div class="weui-form-preview__item">
								<label class="weui-form-preview__label">受理时间</label> <span
									class="weui-form-preview__value" id="approveTime"><s:property
										value="approve4UserJoinLevel.timeStr" /></span>
							</div>

							<div class="weui-form-preview__item">
								<label class="weui-form-preview__label">回复信息</label> <span
									class="weui-form-preview__value" id="message"> <s:property
										value="approve4UserJoinLevel.replies[0].message" />
								</span>
							</div>
						</s:elseif>



					</div>
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




</script>
</html>