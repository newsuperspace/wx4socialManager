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
</head>
<body>


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
							<h1 class="h2">项目信息</h1>
							<div class="btn-toolbar mb-2 mb-md-0">
								<div class="btn-group mr-2">

									<button class="btn btn-sm btn-outline-secondary"
										data-toggle="modal" data-target="#selectProjects">
										<span class="glyphicon glyphicon-search"></span> 筛选
									</button>
									<button class="btn btn-sm btn-outline-secondary"
										data-toggle="modal" data-target="#orderProjects">
										<span class="glyphicon glyphicon-sort-by-order"></span> 排序
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
											<a class="dropdown-item" href="#"
												onclick="userModal.op.batchCreateQR();">批量重建二维码</a> <a
												class="dropdown-item disabled" href="#">Disabled action</a>
											<h6 class="dropdown-header">Section header</h6>
											<a class="dropdown-item" href="#">Action</a>
											<div class="dropdown-divider"></div>
											<a class="dropdown-item" href="#">After divider action</a>
										</div>
									</div>
								</div>
							</div>
						</div>
						<!-- =============表格=========== -->
						<div
							style="
                        white-space: nowrap;
                        overflow-x: hidden;
                        overflow-x: auto;">
							<table
								class="table table-striped table-sm table-bordered table-hover text-center">
								<thead class="thead-dark">
									<tr>
										<th>所属层级对象</th>
										<th>项目名</th>
										<th>DPID</th>
										<th>项目类型</th>
										<th>活动</th>
										<th>操作</th>
									</tr>
								</thead>
								<tbody>
									<s:iterator value="#projects">
										<tr>
											<td><s:if test="%{thirdLevel!=null}">
													<s:property value="thirdLevel.name" />
												</s:if> <s:elseif test="%{secondLevel!=null&&thirdLevel==null}">
													<s:property value="secondLevel.name" />
												</s:elseif> <s:elseif test="%{firstLevel!=null&&secondLevel==null}">
													<s:property value="firstLevel.name" />
												</s:elseif> <s:elseif test="%{zeroLevel!=null&&firstLevel==null}">
													<s:property value="zeroLevel.name" />
												</s:elseif> <s:elseif test="%{minusFirstLevel!=null&&zeroLevel==null}">
													<s:property value="minusFirstLevel.name" />
												</s:elseif></td>
											<td><s:a href="#"
													onclick="projectModal.op.projectInfo('%{dpid}')">
													<s:property value="besureProject.name" />
												</s:a></td>
											<td><s:property value="dpid" /></td>
											<td><s:property value="besureProject.projectType.name" /></td>
											<td><s:a href="#"
													onclick="projectModal.op.getActivities('%{dpid}')">
													<s:property value="activities.size()" />
												</s:a></td>
											<td>
												<div class="btn-group" role="group">
													<!-- 
														<s:a cssClass="btn btn-sm btn-outline-secondary"
														role="button"
														onclick="projectModal.op.createActivity('%{dpid}');"
														href="#">新活动</s:a>
													 -->
													<s:a cssClass="btn btn-sm btn-outline-secondary"
														role="button" type="button"
														data-toggle="modal" data-target="#modelId"
														onclick="dpid='%{dpid}';" href="#">新活动</s:a>
													<button type="button"
														class="btn btn-outline-secondary btn-sm">其他</button>
												</div>
											</td>
										</tr>
									</s:iterator>
								</tbody>
							</table>
						</div>
						<!-- 分页栏 -->
						<nav aria-label="Page navigation" class="mt-3">
							<ul class="pagination justify-content-center">
								<li class="page-item disabled"><a class="page-link"
									href="#" aria-label="向前"> <span aria-hidden="true">&laquo;</span>
										<span class="sr-only">向前</span>
								</a></li>
								<li class="page-item active"><a class="page-link" href="#">1</a></li>
								<li class="page-item"><a class="page-link" href="#">2</a></li>
								<li class="page-item"><a class="page-link" href="#">3</a></li>
								<li class="page-item"><a class="page-link" href="#">100</a></li>
								<li class="page-item"><a class="page-link" href="#">101</a></li>
								<li class="page-item"><a class="page-link" href="#"
									aria-label="向后"> <span aria-hidden="true">&raquo;</span> <span
										class="sr-only">向后</span>
								</a></li>
							</ul>
						</nav>
						<!-- 表格结束 -->
					</div>
				</div>
			</div>
		</div>
		<!-- =================================================模态对话框==================================================== -->
		<!-- 创建活动前需要 同意的《志愿者安全行为守则》条款的Modal -->
		<div class="modal fade" id="modelId" tabindex="-1" role="dialog"
			aria-labelledby="modelTitleId" aria-hidden="true">
			<div class="modal-dialog modal-lg" role="document">
				<div class="modal-content">

					<div class="modal-header">
						<h4 class="modal-title" id="modelTitleId">社区志愿服务安全行为守则</h4>
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>

					<div class="modal-body">
						<div class="container-fluid">

							<div class="row">
								<div id="ruler" data-spy="scroll" data-target="#navbar-example"
									data-offset="0"
									style="color: #24bcbd;height:250px;overflow:auto;position:relative;text-indent:35px">
									<p>为丰富社区居民日常文化生活，促进和加强居民之间健康交流，小红帆驿站活动室特提供日常居民文化活动服务功能。为保障活动室人员健康安全，维护公共财产现制定相关安全管理制度如下，请广大居民遵照执行。</p>
									1、文体活动室由东大桥社区居委会和小红帆驿站负责全面管理和协调，社区拥有对活动室的最终解释权。<br>
									2、活动室的开放时间为：工作日上午9：00—11：00， 下午
									14：00-16：00，晚上开放。活动室使用者须严格遵守开放时间，如无特殊情况不得随意要求提前或延长开放时间。<br>
									3、所有活动室使用团队在正式开展活动之前必须进行规定时长的包括防火、防盗、紧急救护、制度学习等安全教育，否则不予批准使用。室内包括电灯、水源开关、网络、音响等机械和电子设备使用方法须对各个领队进行培训，确保其独立使用时安全合规。<br>
									4、请自行保管好随身携带的物品，避免携带贵重物品或现金参加活动，防止遗失或被窃。<br>
									5、禁止携带易燃易爆、尖锐、有毒、生物威胁等危险品进入，请活动室参与者相互监督彼此提醒，一旦发现将立刻取消该团队的活动室使用权限。<br>
									6、成年人活动禁止带领婴幼儿和宠物参加，防止发生走失、意外伤害等突发情况。<br>
									7、活动室内禁止开展登高、跳远、搏击、球类等剧烈对抗运动，防止意外事故发生。<br>
									8、对于活动室内出现诸如灯泡、水管、暖气、线路等设施损坏的情况，禁止居民和志愿者擅自更换和维修，应立刻向小红帆和社区负责人报告及时维修。<br>
									9、文体活动室主要为社区居民和小红帆志愿服务团队服务，原则上不对外开放。如需邀请外部人员参与活动，需经提前与社区负责人沟通在获得同意的情况下方可使用。<br>
									凡在活动室开展活动，须遵守活动规则，讲求文明礼貌，注意公共卫生；不随地吐痰、乱丢垃圾；不在室内争吵打闹，不大声喧哗；不在墙壁、
									地面、桌面上涂抹乱画、便溺等；不在室内吸烟、酗酒；不在室内进行赌博等违法活动。以上情况一经发现，取消其以后使用活动室的权利。<br>
									10、活动室内一切设施、设备、器具，仅供就地使用，除桌椅板凳外的器械设备不得随意挪动或带离。否则将按照带出设备、器具的实际价格给予相应罚款。<br>
									11、活动人员须按照设施、设备、器具使用规则安全使用，使用完毕须归放原处，不得随意摆放影响后来者使用。活动人员须爱护室内设施、设备、器具，如有人为损坏，照价赔偿。<br>
									12、活动人员离场时，务必由专人负责切断电源、水源、关闭门窗、打扫卫生。<br>
									13、在活动室内开展志愿服务活动，除遵守本管理制度外，还应严格遵守《小红帆社区志愿者安全行为守则》以保护服务对象和志愿者自身安全。<br>
									14、违反以上规定的团队，一经发现按照情节严重程度给予记过，屡犯或情节特别严重者将被立刻剥夺活动室使用权利。<br>
								</div>
							</div>
							<div class="row mt-4">
								<div class="form-check">
									<label class="form-check-label">
										<div id="agreeDiv" hidden="true">
											<input type="checkbox" class="form-check-input" name="agree"
												id="agree" value="1" > <span
												style="color: #8e8e8e">认真贯彻志愿者安全行为规范</span>
										</div>
									</label>
								</div>
							</div>


						</div>
					</div>

					<div class="modal-footer">
						<button type="button" class="btn btn-secondary"
							data-dismiss="modal">取消</button>
						<button type="button" id="nextBtn" class="btn btn-primary"
							disabled="true" onclick="createActivity();">继续</button>
					</div>

				</div>
			</div>
		</div>


		<!-- Modal4用户详情 -->
		<div class="modal fade" id="detialsModal" tabindex="-1" role="dialog"
			aria-labelledby="detialsModal" aria-hidden="true">
			<div class="modal-dialog modal-lg" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<h4 class="modal-title" id="detialsModal_title">张三-信息详情</h4>
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="modal-body">
						<div class="container-fluid">
							<div class="row">
								<div class="col-lg-2 pr-0 col-xs-12">
									<img src="./img/qrcode.gif" class="img-fluid"
										id="detialsModal_qrcode" alt="二维码丢失">
								</div>
								<div class="col-lg-10 pl-0 col-xs-12">
									<div class="row">
										<div class="col-3 text-right font-weight-bold text-truncate">
											用户名:</div>
										<div class="col-9 text-left text-truncate"
											id="detialsModal_username">newsuperspace</div>
									</div>
									<div class="row">
										<div class="col-3 text-right font-weight-bold text-truncate">
											UID:</div>
										<div class="col-9 text-left text-truncate"
											id="detialsModal_uid">
											5d7323e0-70b5-4ff6-9c77-3bdd70a507f1</div>
									</div>
									<div class="row">
										<div class="col-3 text-right font-weight-bold text-truncate">
											OpenID:</div>
										<div class="col-9 text-left text-truncate"
											id="detialsModal_openid">okNKU0Qdq6WC9bGO22gcp6tSCuJs</div>
									</div>
									<div class="row">
										<div class="col-3 text-right font-weight-bold text-truncate">
											注册时间:</div>
										<div class="col-9 text-left text-truncate"
											id="detialsModal_registrationTime">2018-12-22 12:56:06</div>
									</div>
									<div class="row">
										<div class="col-3 text-right font-weight-bold text-truncate">
											街道:</div>
										<div class="col-9 text-left text-truncate"
											id="detialsModal_minusFirst">呼家楼</div>
									</div>
									<div class="row">
										<div class="col-3 text-right font-weight-bold text-truncate">
											社区:</div>
										<div class="col-9 text-left text-truncate"
											id="detialsModal_zero">呼家楼北里</div>
									</div>
									<div class="row">
										<div class="col-3 text-right font-weight-bold text-truncate">
											第1层级:</div>
										<div class="col-9 text-left text-truncate"
											id="detialsModal_first">志愿者</div>
									</div>
									<div class="row">
										<div class="col-3 text-right font-weight-bold text-truncate">
											第2层级:</div>
										<div class="col-9 text-left text-truncate"
											id="detialsModal_second">为老志愿者</div>
									</div>
									<div class="row">
										<div class="col-3 text-right font-weight-bold text-truncate">
											第3层级:</div>
										<div class="col-9 text-left text-truncate"
											id="detialsModal_third">殷金凤工作室</div>
									</div>
									<div class="row">
										<div class="col-3 text-right font-weight-bold text-truncate">
											第4层级:</div>
										<div class="col-9 text-left text-truncate"
											id="detialsModal_fourth">第一支队</div>
									</div>
								</div>
							</div>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-secondary"
								data-dismiss="modal">Close</button>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- Modal 4 用户更改 -->

		<!-- Modal 4 排序 -->

		<!-- Modal 4 筛选 -->
		<div class="modal fade" id="selectUserModal" tabindex="-1"
			role="dialog" aria-labelledby="selectUserModal" aria-hidden="true">
			<div class="modal-dialog  modal-lg" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<h4 class="modal-title">筛选用户</h4>
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="modal-body">
						<div class="container-fluid">Add rows here</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-secondary"
								data-dismiss="modal">关闭</button>
							<button type="button" class="btn btn-primary">确定</button>
						</div>
					</div>
				</div>
			</div>
		</div>
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
	var nScrollHight = 0; //滚动距离总长(注意不是滚动条的长度)
	var nScrollTop = 0; //滚动到的当前位置
	var nDivHight = $("#ruler").height(); // 当前div的height像素值
	var dpid = ""; // 存放需要新建活动的doingProject的id值
	$(function() {
		$("#ruler").scroll(function() {
			nScrollHight = $(this)[0].scrollHeight;
			nScrollTop = $(this)[0].scrollTop;
			if (nScrollTop + nDivHight >= nScrollHight) {
				console.log("滚动条到底部了")
				$("#agreeDiv").attr("hidden", false);
			}
		});

		$("#agree").change(function() {
			var self = $(this).get(0);
			console.log("复选框选中状态为：" + self.checked);
			if (self.checked) {
				$("#nextBtn").attr("disabled", false);
			} else {
				$("#nextBtn").attr("disabled", true);
			}
		});
	});
	
	function createActivity(){
		console.log("新建活动的dpid是："+dpid);
		projectModal.op.createActivity(dpid);
	}
	
</script>

</html>