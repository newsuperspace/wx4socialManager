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
								<s:if test="''==title">
									未编写活动记录
								</s:if>
								<s:else>
									<s:property value="activity.name" /> 活动的记录
								</s:else>
							</h1>
							<div class="btn-toolbar mb-2 mb-md-0">
								<div class="btn-group mr-2">

									<s:a cssClass="btn btn-sm btn-outline-secondary" role="button"
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
						<!--这段示例代码是直接从https://weui.io/#article的源码中copy出来的-->
						<div style="background-color: #fff;opacity: 1;">
							<div style="padding: 20px;">
								<h1
									style="text-align: left;font-size: 30px;font-weight: 400;color: crimson"><s:property value="title"/></h1>
								<span
									style="margin-top: 5px;color: #888;text-align: left;font-size: 14px;">
										<s:if test="activity.project.minusFirstLevel==null">
											佚名
										</s:if>
										<s:elseif test="activity.project.zeroLevel==null">
											<s:property value="activity.project.minusFirstLevel.name"/>
										</s:elseif>
										<s:elseif test="activity.project.firstLevel==null">
											<s:property value="activity.project.zeroLevel.name"/>
										</s:elseif>
										<s:elseif test="activity.project.secondLevel==null">
											<s:property value="activity.project.firstLevel.name"/>
										</s:elseif>
										<s:elseif test="activity.project.thirdLevel==null">
											<s:property value="activity.project.secondLevel.name"/>
										</s:elseif>
										<s:else>
											<s:property value="activity.project.thirdLevel.name"/>
										</s:else>
									&nbsp;&nbsp;&nbsp;&nbsp;</span>
								<span
									style="margin-top: 5px;color: #888;text-align: left;font-size: 14px;">
										<s:property value="activity.endTimeStr"/>
									&nbsp;&nbsp;&nbsp;&nbsp;</span>
							</div>
							<div style="margin: 0;padding: 0;">
								<article class="weui-article pt-0">
									<h1></h1>
									<section>
										<h2 class="title"></h2>
										<section>
											<h3></h3>
											<p style="font-size: 17px">
											
												<s:property value="content"/>
												
											</p>
											<p>
												<s:iterator value="photos">
													<img src="<s:property value='url'/>" alt='<s:property value="description"/>' class="mb-2">
												</s:iterator>
											</p>
										</section>
									</section>
								</article>
							</div>
							<div
								style="padding-top: 40px;padding-bottom: 10px;text-align: center;">
								<a href="javascript:home()"><img src="${pageContext.request.contextPath}/img/qrcode.gif"
									style="height: 20px;"></a>
							</div>
						</div>

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
	
	function home(){
		weui.alert("此處應該返回首頁");
	}

</script>
</html>