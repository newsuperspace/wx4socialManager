<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>社区层级化组织管理系统平台</title>
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
	<!-- =================Navbar（动态include）=============== -->
	<jsp:include page="/WEB-INF/pages/up/navbar.jsp"></jsp:include>
	<div class="container-fluid">
		<div class="row">
			<!-- =================Menu（动态include）=============== -->
			<jsp:include page="/WEB-INF/pages/left/menu.jsp"></jsp:include>

			<div class="col-xs-12 col-md-10 col-lg-10">
				<!-- =================Breadcromb（动态include）=============== -->
				<jsp:include page="/WEB-INF/pages/others/breadcromb.jsp"></jsp:include>
				<!-- =================================★★真正的页面数据内容显示区域★★===================================== -->
				<div class="row">
					<div class="col">
						<!-- =============标题=========== -->
						<div
							class="justify-content-between d-flex flex-wrap flex-md-nowrap align-items-center pb-1 mb-4 border-bottom">
							<h1 class="h2">
								<s:if test="null==title">
									该活动还未编写活动记录
								</s:if>
								<s:else>
									<s:property value="title" />
								</s:else>
							</h1>
							<div class="btn-toolbar mb-2 mb-md-0">
								<div class="btn-group mr-2">

									<s:a cssClass="btn btn-sm btn-outline-secondary"
															role="button"
															onclick="toInputPage('%{artid}');" href="#">编写</s:a>
									<button class="btn btn-sm btn-outline-secondary"
										data-toggle="modal" data-target="#orderProjects">
										<span class="glyphicon glyphicon-sort-by-order"></span> 模态2
									</button>
									<div class="dropdown ml-1">
										<button
											class="btn btn-sm btn-outline-secondary dropdown-toggle"
											type="button" id="others" data-toggle="dropdown"
											aria-haspopup="true" aria-expanded="false">
											<span class="glyphicon glyphicon-cog"></span> 其他
										</button>
										<div class="dropdown-menu dropdown-menu-right"
											aria-labelledby="others">
											<a class="dropdown-item" href="#" onclick="alert('功能待开放');">功能1</a>
											<a class="dropdown-item disabled" href="#">Disabled
												action</a>
											<h6 class="dropdown-header">Section header</h6>
											<a class="dropdown-item" href="#">Action</a>
											<div class="dropdown-divider"></div>
											<a class="dropdown-item" href="#">After divider action</a>
										</div>
									</div>
								</div>
							</div>
						</div>
						<!-- =============文章开始=========== -->
						
						
						
						<!-- =============文章结束=========== -->
					</div>
				</div>
			</div>
		</div>
		<!-- =================================================模态对话框==================================================== -->
		<!-- container结束 -->
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
	function toInputPage(artid){
		$(location).attr('href','articleAction_showInputPage.action?artid='+artid);
	}

</script>
</html>