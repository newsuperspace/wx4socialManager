<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@taglib uri="/WEB-INF/tlds/myShiro.tld" prefix="myShiro"%>
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
					<div id="accordianId" role="tablist" aria-multiselectable="true">
						<div class="card">
							<div class="card-header px-0 py-1" role="tab"
								id="section1HeaderId">
								<a class="nav-link" data-toggle="collapse" href="#userContent"
									aria-expanded="false" aria-controls="userContent"> 人员管理 </a>
							</div>
							<!--
                                        注意！data-parent必须放置在card-body上一层的div上才能实现多个card的手风琴开关效果
                                        并且data-parent必须同时指向整个card组的最外层div，用以表示这些card是属于同一个parent的
                                        因此同一时间只能打开其中一个card
                                    -->
							<div id="userContent" class="collapse in"
								data-parent="#accordianId" role="tabpanel"
								aria-labelledby="section1HeaderId">
								<div class="card-body py-1">

									<ul class="navbar-nav mr-auto mt-0 pt-0">
										<li class="nav-item"><a class="nav-link" href="#"
											onclick='overAll.op.saveCollapseContentID2LS("userContent", "userAction_getUserList.action")'>非直辖人员</a></li>
										<li class="nav-item"><a class="nav-link" href="#"
											onclick='overAll.op.saveCollapseContentID2LS("userContent", "userAction_getManagerList.action")'>直辖人员</a>
										</li>
									</ul>

								</div>
							</div>
						</div>
						<div class="card">
							<div class="card-header px-0 py-1" role="tab"
								id="section3HeaderId">
								<a class="nav-link" data-toggle="collapse"
									href="#projectContent" aria-expanded="false"
									aria-controls="projectContent"> 项目管理 </a>
							</div>
							<!--
                                        注意！data-parent必须放置在card-body上一层的div上才能实现多个card的手风琴开关效果
                                        并且data-parent必须同时指向整个card组的最外层div，用以表示这些card是属于同一个parent的
                                        因此同一时间只能打开其中一个card
                                    -->
							<div id="projectContent" class="collapse in"
								data-parent="#accordianId" role="tabpanel"
								aria-labelledby="section3HeaderId">
								<div class="card-body py-1">

									<ul class="navbar-nav mr-auto mt-0 pt-0">

										<myShiro:hasAnyPermissions
											name="minus_first:project:retrieve,zero:project:retrieve,first:project:retrieve,second:project:retrieve,third:project:retrieve">
											<li class="nav-item"><a class="nav-link" href="#"
												onclick='overAll.op.saveCollapseContentID2LS("projectContent", "doingProjectAction_getProjects.action")'>我的项目</a>
											</li>
										</myShiro:hasAnyPermissions>

										<myShiro:hasAnyPermissions name="admin">
											<li class="nav-item"><a class="nav-link" href="#"
												onclick='overAll.op.saveCollapseContentID2LS("projectContent", "doingProjectAction_getProjects.action?level=minusFirst")'>街道级项目</a>
											</li>
										</myShiro:hasAnyPermissions>

										<myShiro:hasAnyPermissions
											name="admin,minus_first:project:retrieve">
											<li class="nav-item"><a class="nav-link" href="#"
												onclick='overAll.op.saveCollapseContentID2LS("projectContent", "doingProjectAction_getProjects.action?level=zero")'>社区级项目</a>
											</li>
										</myShiro:hasAnyPermissions>

										<myShiro:hasAnyPermissions
											name="admin,minus_first:project:retrieve,zero:project:retrieve">
											<li class="nav-item"><a class="nav-link" href="#"
												onclick='overAll.op.saveCollapseContentID2LS("projectContent", "doingProjectAction_getProjects.action?level=first")'>第一级项目</a>
											</li>
										</myShiro:hasAnyPermissions>

										<myShiro:hasAnyPermissions
											name="admin,minus_first:project:retrieve,zero:project:retrieve,first:project:retrieve">
											<li class="nav-item"><a class="nav-link" href="#"
												onclick='overAll.op.saveCollapseContentID2LS("projectContent", "doingProjectAction_getProjects.action?level=second")'>第二级项目</a>
											</li>
										</myShiro:hasAnyPermissions>

										<myShiro:hasAnyPermissions
											name="admin,minus_first:project:retrieve,zero:project:retrieve,first:project:retrieve,second:project:retrieve">
											<li class="nav-item"><a class="nav-link" href="#"
												onclick='overAll.op.saveCollapseContentID2LS("projectContent", "doingProjectAction_getProjects.action?level=third")'>第三级项目</a>
											</li>
										</myShiro:hasAnyPermissions>

										<!-- 项目只能是街道/社区/第一/第二/第三层级对象持有，第四层级不得持有项目 -->


									</ul>

								</div>
							</div>
						</div>
						<div class="card">
							<div class="card-header px-0 py-1" role="tab"
								id="section2HeaderId">
								<a class="nav-link" data-toggle="collapse" href="#levelContent"
									aria-expanded="false" aria-controls="levelContent"> 组织层级化管理
								</a>
							</div>
							<!--
                                        注意！data-parent必须放置在card-body上一层的div上才能实现多个card的手风琴开关效果
                                        并且data-parent必须同时指向整个card组的最外层div，用以表示这些card是属于同一个parent的
                                        因此同一时间只能打开其中一个card
                                    -->
							<div id="levelContent" class="collapse in" role="tabpanel"
								data-parent="#accordianId" aria-labelledby="section2HeaderId">
								<div class="card-body py-1">

									<ul class="navbar-nav mr-auto mt-0 pt-0">

										<myShiro:hasAnyPermissions name="admin">
											<li class="nav-item active"><a class="nav-link" href="#"
												onclick='overAll.op.saveCollapseContentID2LS("levelContent", "minusFirstLevelAction_getLevelList.action")'>街道层级
													<span class="sr-only">(current)</span>
											</a></li>
										</myShiro:hasAnyPermissions>

										<myShiro:hasAnyPermissions
											name="admin,minus_first:level:retrieve">
											<li class="nav-item"><a class="nav-link" href="#"
												onclick='overAll.op.saveCollapseContentID2LS("levelContent", "zeroLevelAction_getLevelList.action")'>社区层级</a></li>
										</myShiro:hasAnyPermissions>

										<myShiro:hasAnyPermissions
											name="admin,minus_first:level:retrieve,zero:level:retrieve">
											<li class="nav-item"><a class="nav-link" href="#"
												onclick='overAll.op.saveCollapseContentID2LS("levelContent", "firstLevelAction_getLevelList.action")'>第一层级</a>
											</li>
										</myShiro:hasAnyPermissions>

										<myShiro:hasAnyPermissions
											name="admin,minus_first:level:retrieve,zero:level:retrieve,first:level:retrieve">
											<li class="nav-item"><a class="nav-link" href="#"
												onclick='overAll.op.saveCollapseContentID2LS("levelContent", "secondLevelAction_getLevelList.action")'>第二层级</a>
											</li>
										</myShiro:hasAnyPermissions>

										<myShiro:hasAnyPermissions
											name="admin,minus_first:level:retrieve,zero:level:retrieve,first:level:retrieve,second:level:retrieve">
											<li class="nav-item"><a class="nav-link" href="#"
												onclick='overAll.op.saveCollapseContentID2LS("levelContent", "thirdLevelAction_getLevelList.action")'>第三层级</a></li>
										</myShiro:hasAnyPermissions>


										<myShiro:hasAnyPermissions
											name="admin,minus_first:level:retrieve,zero:level:retrieve,first:level:retrieve,second:level:retrieve,third:level:retrieve">
											<li class="nav-item"><a class="nav-link" href="#"
												onclick='overAll.op.saveCollapseContentID2LS("levelContent", "fourthLevelAction_getLevelList.action")'>第四层级</a></li>
										</myShiro:hasAnyPermissions>

									</ul>
								</div>
							</div>
						</div>



						<myShiro:hasAnyPermissions
							name="admin,zero:house:retrieveHouse,zero:house:retrieveActivity">

							<div class="card">
								<div class="card-header px-0 py-1" role="tab"
									id="houseContentHeaderId">
									<a class="nav-link" data-toggle="collapse" href="#houseContent"
										aria-expanded="false" aria-controls="houseContent"> 活动室管理
									</a>
								</div>
								<!--
                                        注意！data-parent必须放置在card-body上一层的div上才能实现多个card的手风琴开关效果
                                        并且data-parent必须同时指向整个card组的最外层div，用以表示这些card是属于同一个parent的
                                        因此同一时间只能打开其中一个card
                                    -->
								<div id="houseContent" class="collapse in" role="tabpanel"
									data-parent="#accordianId"
									aria-labelledby="houseContentHeaderId">
									<div class="card-body py-1">

										<ul class="navbar-nav mr-auto mt-0 pt-0">

											<myShiro:hasAnyPermissions
												name="admin,zero:house:retrieveHouse">
												<li class="nav-item active"><a class="nav-link"
													href="#"
													onclick='overAll.op.saveCollapseContentID2LS("houseContent", "houseAction_getHouseList.action")'>活动室设置
														<span class="sr-only">(current)</span>
												</a></li>
											</myShiro:hasAnyPermissions>

											<myShiro:hasAnyPermissions
												name="admin,zero:house:retrieveActivity">
												<li class="nav-item"><a class="nav-link" href="#"
													onclick='overAll.op.saveCollapseContentID2LS("houseContent", "houseAction_getMonthView.action")'>月使用详情</a></li>
											</myShiro:hasAnyPermissions>

										</ul>
									</div>
								</div>
							</div>
						</myShiro:hasAnyPermissions>


						<myShiro:hasAnyPermissions
							name="admin,minus_first:geo:retrieveGeo,zero:geo:retrieveGeo,first:geo:retrieveGeo,second:geo:retrieveGeo,third:geo:retrieveGeo,fourth:geo:retrieveGeo">

							<div class="card">
								<div class="card-header px-0 py-1" role="tab"
									id="geoContentHeaderId">
									<a class="nav-link" data-toggle="collapse" href="#geoContent"
										aria-expanded="false" aria-controls="geoContent"> 位置管理 </a>
								</div>
								<!--
                                        注意！data-parent必须放置在card-body上一层的div上才能实现多个card的手风琴开关效果
                                        并且data-parent必须同时指向整个card组的最外层div，用以表示这些card是属于同一个parent的
                                        因此同一时间只能打开其中一个card
                                    -->
								<div id="geoContent" class="collapse in" role="tabpanel"
									data-parent="#accordianId" aria-labelledby="geoContentHeaderId">
									<div class="card-body py-1">

										<ul class="navbar-nav mr-auto mt-0 pt-0">

											<li class="nav-item active"><a class="nav-link" href="#"
												onclick='overAll.op.saveCollapseContentID2LS("geoContent", "geographicAction_getGeoList.action")'>地理位置设置
													<span class="sr-only">(current)</span>
											</a></li>


										</ul>
									</div>
								</div>
							</div>
						</myShiro:hasAnyPermissions>


						<shiro:hasPermission name="admin">
							<div class="card">
								<div class="card-header px-0 py-1" role="tab"
									id="section2HeaderId">
									<a class="nav-link" data-toggle="collapse"
										href="#permissionContent" aria-expanded="false"
										aria-controls="permissionContent"> 系统权限管理 </a>
								</div>
								<!--
                                        注意！data-parent必须放置在card-body上一层的div上才能实现多个card的手风琴开关效果
                                        并且data-parent必须同时指向整个card组的最外层div，用以表示这些card是属于同一个parent的
                                        因此同一时间只能打开其中一个card
                                    -->
								<div id="permissionContent" class="collapse in" role="tabpanel"
									data-parent="#accordianId" aria-labelledby="section2HeaderId">
									<div class="card-body py-1">

										<ul class="navbar-nav mr-auto mt-0 pt-0">
											<li class="nav-item"><a class="nav-link" href="#"
												onclick='overAll.op.saveCollapseContentID2LS("permissionContent", "permissionLevelAction_getList.action")'>权限层级管理</a></li>
											<li class="nav-item"><a class="nav-link" href="#"
												onclick='overAll.op.saveCollapseContentID2LS("permissionContent", "permissionTypeAction_getList.action")'>权限类型管理</a></li>
											<li class="nav-item"><a class="nav-link" href="#"
												onclick='overAll.op.saveCollapseContentID2LS("permissionContent", "permissionAction_getList.action")'>权限管理</a></li>
										</ul>
									</div>
								</div>
							</div>
						</shiro:hasPermission>


					</div>
				</div>
			</div>
		</div>
	</div>

</body>
</html>