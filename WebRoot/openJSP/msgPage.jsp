<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title><s:property value="total"/></title>
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

	<div class="weui-msg">
        <div class="weui-msg__icon-area">
        	<s:if test="icon=='weui-icon-warn-red'">
            	<i class="weui-icon-warn weui-icon_msg"></i>
        	</s:if>
			<s:elseif test="icon=='weui-icon-success'">
				<i class="weui-icon-success weui-icon_msg"></i>
			</s:elseif>        	
            <s:elseif test="icon=='weui-icon-info'">
            	<i class="weui-icon-info weui-icon_msg"></i>
            </s:elseif>
            <!-- <i class="weui-icon-warn weui-icon_msg-primary">黄色警告</i> -->
            <!-- <i class="weui-icon-waiting weui-icon_msg">蓝色等待</i> -->
            <!-- <i class="weui-icon-cancel weui-icon_msg">取消</i> -->
            <!-- <i class="weui-icon-download weui-icon_msg">下载</i> -->
        </div>
        <div class="weui-msg__text-area">
            <h2 class="weui-msg__title" id="title"><s:property value="title"/></h2>
            <p class="weui-msg__desc" id="message"><s:property value="message"/>
                <a href="<s:property value='detailsURL'/>"><s:property value="details"/></a>
            </p>
        </div>
        <div class="weui-msg__opr-area">
            <p class="weui-btn-area">
                <a href="javascript:closeMsgPage();" class="weui-btn weui-btn_primary">确认</a>
                <!-- <a href="javascript:history.back();" class="weui-btn weui-btn_default">取消</a> -->
            </p>
        </div>
        <!--FOOT-->
        <div class="weui-footer mt-5">
            <p class="weui-footer__text">Copyright &copy; 2017-2019 联合会提供技术支持</p>
            <p class="weui-footer__links">
                <a href="javascript:void(0);" class="weui-footer__link">访问我们</a>
            </p>
        </div>
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
	
	// 关闭消息页面Msg Page
	function closeMsgPage(){
		wx.closeWindow();
	}

</script>
</html>