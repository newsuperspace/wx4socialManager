<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>活动历史记录</title>
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
            <h1 style="text-align: left;font-size: 20px;font-weight: 400;font-weight: bold;">活动历史</h1>
            <p style="margin-top: 5px;color: #888;text-align: left;font-size: 14px;">在这里您可以看到以往参加活动的历史记录</p>
        </div>
        <!--容纳各种PreView-->
        <s:iterator value="#list">
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
                        <label class="weui-form-preview__label">活动名称</label>
                        <span class="weui-form-preview__value" id="name"><s:property value="name"/></span>
                    </div>
                    <div class="weui-form-preview__item">
                        <label class="weui-form-preview__label">发起方</label>
                        <span class="weui-form-preview__value" id="initiator">
                        	<s:if test="project.zeroLevel==null">
								<span class="weui-form-preview__value" id="initiator"><s:property value="project.minusFirstLevel.name"/></span>
							</s:if>
							<s:elseif test="project.firstLevel==null">
								<span class="weui-form-preview__value" id="initiator"><s:property value="project.zeroLevel.name"/></span>
							</s:elseif>
							<s:elseif test="project.secondLevel==null">
								<span class="weui-form-preview__value" id="initiator"><s:property value="project.firstLevel.name"/></span>
							</s:elseif>
							<s:elseif test="project.thirdLevel==null">
								<span class="weui-form-preview__value" id="initiator"><s:property value="project.secondLevel.name"/></span>
							</s:elseif>
							<s:else>
								<span class="weui-form-preview__value" id="initiator"><s:property value="project.thirdLevel.name"/></span>
							</s:else>
                        </span>
                    </div>
                    <div class="weui-form-preview__item">
                        <label class="weui-form-preview__label">活动时间</label>
                        <span class="weui-form-preview__value" id="time"><s:property value="beginTimeStr"/>到<s:property value="endTimeStr"/></span>
                    </div>
                    <div class="weui-form-preview__item">
                        <label class="weui-form-preview__label">活动简介</label>
                        <span class="weui-form-preview__value" id=”description“>
                        	<s:property value="description"/>	
                        </span>
                    </div>
                    <div class="weui-form-preview__item">
							<label class="weui-form-preview__label">活动地点</label> 
							<span class="weui-form-preview__value" id="position">
								<s:if test="activityType=='1'">
									<s:property value="geographic.name"/>	
								</s:if>
								<s:elseif test="activityType=='2'">
									<s:property value="house.name"/>	
								</s:elseif>
							</span>
						</div>
                    <!-- <div class="weui-form-preview__item">
                                        <label class="weui-form-preview__label">活动地点</label>
                                        <span class="weui-form-preview__value" id="place">核桃园社区会议室</span>
                                    </div> -->
                    <div class="weui-form-preview__item">
                        <label class="weui-form-preview__label">活动积分</label>
                        <span class="weui-form-preview__value" id="score">
                        	<s:property value="score"/>
                        </span>
                    </div>
                    <div class="weui-form-preview__item">
                        <label class="weui-form-preview__label">您的表现</label>
                        <span class="weui-form-preview__value" id="limit">
                            <!--badge-primary|secondary|success|danger|warning|info|light|dark-->
                            <s:if test="theVisitor.startTime==-1">
                            	<span class="badge badge-secondary">未参加</span>
                            </s:if>
                            <s:elseif test="theVisitor.endTime==-1">
                            	<span class="badge badge-warning">未签退</span>
                            </s:elseif>
                            <s:elseif test="theVisitor.workTime!=-1">
                            	<span class="badge badge-success">已参加</span>
                            </s:elseif>
                        </span>
                    </div>
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




</script>
</html>