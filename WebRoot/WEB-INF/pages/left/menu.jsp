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
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
	integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
	crossorigin="anonymous">
</head>
<body>


	<!-- ============================左侧工具栏========================== -->
	<!-- 
                    col-xx-auto  用于包裹内部内容，实现根据内容物自动修改占用栅格数
                    col-xx  不加数字的意思就是占据剩余的全部栅格
                    但是这样能呈现更好的布局分配，但是如果其中一个列中的横向内容过大
                    则就会另起一行，比如一张很大的表格....(如何解决这个问题呢？)
                    所以还是不得不指定具体的数字来分配布局。
                -->
	<div class="col-xs-12 col-md-2 col-lg-2  border">

		<div class="row mt-3 md-3">
			<div class="col">
				<div class="input-group">
					<input class="form-control" placeholder="查找...">
					<div class="input-group-append">
						<button class="btn btn-secondary ">
							<span class="glyphicon glyphicon-search"></span>
						</button>
					</div>
				</div>
			</div>
			<div
				class="col-auto pl-1  d-lg-none d-xl-none  d-sm-block  d-md-block ">
				<button type="button" data-toggle="collapse" href="#contentId"
					class="btn btn-secondary" aria-label="Left Align">
					<span class="glyphicon glyphicon-menu-hamburger" aria-hidden="true"></span>
				</button>
			</div>
		</div>
		<div class="row mt-3 ">
			<div class="col-12 pr-0 pl-0">

				<div class="collapse mx-0" id="contentId">
					<div id="accordianId" role="tablist" aria-multiselectable="treu">
						<div class="card">
							<div class="card-header px-0 py-1" role="tab"
								id="section1HeaderId">
								<a class="nav-link" data-toggle="collapse"
									href="#section1ContentId" aria-expanded="false"
									aria-controls="section1ContentId"> 人员信息数据化管理 </a>
							</div>
							<!--
                                            注意！data-parent必须放置在card-body上一层的div上才能实现多个card的手风琴开关效果
                                            并且data-parent必须同时指向整个card组的最外层div，用以表示这些card是属于同一个parent的
                                            因此同一时间只能打开其中一个card
                                        -->
							<div id="section1ContentId" class="collapse in"
								data-parent="#accordianId" role="tabpanel"
								aria-labelledby="section1HeaderId">
								<div class="card-body py-1">

									<ul class="navbar-nav mr-auto mt-0 pt-0">
										<li class="nav-item active"><a class="nav-link" href="#">Home
												<span class="sr-only">(current)</span>
										</a></li>
										<li class="nav-item"><a class="nav-link" href="#">Link</a>
										</li>
										<li class="nav-item dropdown"><a
											class="nav-link dropdown-toggle" href="#" id="dropdownId"
											data-toggle="dropdown" aria-haspopup="true"
											aria-expanded="false">Dropdown</a>
											<div class="dropdown-menu" aria-labelledby="dropdownId">
												<a class="dropdown-item" href="#">Action 1</a> <a
													class="dropdown-item" href="#">Action 2</a>
											</div></li>
									</ul>

								</div>
							</div>
						</div>
						<div class="card">
							<div class="card-header px-0 py-1" role="tab"
								id="section2HeaderId">
								<a class="nav-link" data-toggle="collapse"
									href="#section2ContentId" aria-expanded="false"
									aria-controls="section2ContentId"> 项目管理 </a>
							</div>
							<!--
                                            注意！data-parent必须放置在card-body上一层的div上才能实现多个card的手风琴开关效果
                                            并且data-parent必须同时指向整个card组的最外层div，用以表示这些card是属于同一个parent的
                                            因此同一时间只能打开其中一个card
                                        -->
							<div id="section2ContentId" class="collapse in" role="tabpanel"
								data-parent="#accordianId" aria-labelledby="section2HeaderId">
								<div class="card-body py-1">

									<ul class="navbar-nav mr-auto mt-0 pt-0">
										<li class="nav-item active"><a class="nav-link" href="#">Home
												<span class="sr-only">(current)</span>
										</a></li>
										<li class="nav-item"><a class="nav-link" href="#">Link</a>
										</li>
										<li class="nav-item dropdown"><a
											class="nav-link dropdown-toggle" href="#" id="dropdownId"
											data-toggle="dropdown" aria-haspopup="true"
											aria-expanded="false">Dropdown</a>
											<div class="dropdown-menu" aria-labelledby="dropdownId">
												<a class="dropdown-item" href="#">Action 1</a> <a
													class="dropdown-item" href="#">Action 2</a>
											</div></li>
									</ul>

								</div>
							</div>
						</div>
						<a name="toggle" id="toggle" class="btn btn-secondary btn-block"
							href="javascript:toggle();" role="button">折叠切换</a>
					</div>
				</div>
			</div>
		</div>
	</div>
	
</body>
</html>