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
</head>
<body>

	<div class="d-none d-md-block d-lg-block">
		<nav class="navbar navbar-expand-sm navbar-dark bg-dark">
			<a class="navbar-brand" href="#" onclick="navbarModal.op.preMyselfLevelInfo();">你好，<shiro:principal></shiro:principal></a>
			<button class="navbar-toggler hidden-lg-up" type="button"
				data-toggle="collapse" data-target="#collapsibleNavId"
				aria-controls="collapsibleNavId" aria-expanded="false"
				aria-label="Toggle navigation">
				<span class="glyphicon glyphicon-menu-hamburger" aria-hidden="true"></span>
			</button>
			<div class="collapse navbar-collapse" id="collapsibleNavId">
				<ul class="navbar-nav mr-auto mt-2 mt-lg-0">
					<li class="nav-item active"><a class="nav-link" href="#">Home
							<span class="sr-only">(current)</span>
					</a></li>
					<li class="nav-item"><a class="nav-link" href="#">Link</a></li>
					<li class="nav-item dropdown"><a
						class="nav-link dropdown-toggle" href="#" id="navbarDropdown"
						data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">其他</a>
						<div class="dropdown-menu" aria-labelledby="navbarDropdown">
							<a class="dropdown-item" href="#">Action 1</a> <a
								class="dropdown-item"
								href="${pageContext.request.contextPath}/logout">退出</a>
						</div></li>
				</ul>
				<form class="form-inline my-2 my-lg-0">
					<input class="form-control mr-sm-2" type="text"
						placeholder="Search">
					<button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
				</form>
			</div>
		</nav>
	</div>

	<!-- ===================================模态对话框===================================== -->
	<!-- 显示当前层级对象的信息  -->
	<div class="modal fade font-" id="levelInfoModal" tabindex="-1"
		role="dialog" aria-labelledby="levelInfo" aria-hidden="true">
		<div class="modal-dialog modal-sm" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title" id="levelInfo">层级信息</h4>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body mb-5">
					<div class="container" style="font-size: 12px">

						<div class="row">
							<div class="col-2"></div>
							<div class="col-8">
								<img src="#" class="img-thumbnail rounded-top"
									id="levelQrcode" alt="层级二维码">
							</div>
							<div class="col-2"></div>
						</div>

						<div class="mt-3 ml-3">
							<div class="row">
								<div class="col-3 font-weight-bold p-0">层级名：</div>
								<div class="col-auto p-0" id="levelName">呼家楼</div>
							</div>
							<div class="row">
								<div class="col-3 font-weight-bold p-0">
									描&nbsp;&nbsp;&nbsp;&nbsp;述：</div>
								<div class="col-auto p-0" id="levelDescription">
									北京市朝阳区呼家楼街道办事处</div>
							</div>
							<div class="row">
								<div class="col-3 font-weight-bold p-0">管理员：</div>
								<div class="col-auto p-0" id="levelManager">欧阳靖海</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>


</body>
<!-- 由于当前navbar.jsp是被动态include到其他页面的，因此不能在这里过早提早引入js脚本（js脚本应该在所有页面都加载完成后再在最后运行） -->
</html>