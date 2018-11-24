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
<!-- Bootstrap CSS -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/bootstrap.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/bootstrap-grid.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/bootstrap-reboot.css">
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
							<h1 class="h2"><s:property value="#username"/>管理的直接子层级</h1>
							<!-- ● -->
							<div class="btn-toolbar mb-2 mb-md-0">
								<div class="btn-group mr-2">
									<button class="btn btn-sm btn-outline-secondary"
										data-toggle="modal" data-target="#selectModal">
										<span class="glyphicon glyphicon-search"></span> 筛选
									</button>
									<button class="btn btn-sm btn-outline-secondary"
										data-toggle="modal" data-target="#orderModal">
										<span class="glyphicon glyphicon-sort-by-order"></span> 排序
									</button>
								</div>
								<button class="btn btn-sm btn-outline-secondary dropdown-toggle"
									data-toggle="toggle" data-target="#">
									<span class="glyphicon glyphicon-cog"></span> 其他
								</button>
							</div>
						</div>
						<!-- =============表格=========== -->
						<div
							style="
                        white-space: nowrap;
                        overflow-x: hidden;
                        overflow-x: auto;">
                        	<input type="hidden"  id="uid"  value='<s:property value="uid"/>'/>
							<table
								class="table table-striped table-sm table-bordered table-hover text-center">
								<thead class="thead-dark">
									<tr>
										<th>名称</th>
										<th>lid</th>
										<!-- ● -->
										<th>描述</th>
										<th>级别</th>
										<th>直辖人数</th>
										<th>操作</th>
									</tr>
								</thead>
								<tbody>
									<!-- User4Ajax.managers属性 -->
									<s:iterator value="#managers">
										<tr>
											  <td>
											    <s:a href="#" onclick="managerModal.op.jump2LevelPage('%{member.grouping.tag}');">
											    
												    <s:if test="'minus_first'==member.grouping.tag">
												      <s:property value="minusFirstLevel.name" />
												    </s:if>
												    <s:elseif test="'zero'==member.grouping.tag">
												      <s:property value="zeroLevel.name" />
												    </s:elseif>
												    <s:elseif test="'first'==member.grouping.tag">
												      <s:property value="firstLevel.name" />
												    </s:elseif>
												    <s:elseif test="'second'==member.grouping.tag">
												      <s:property value="secondLevel.name" />
												    </s:elseif>
												    <s:elseif test="'third'==member.grouping.tag">
												      <s:property value="thirdLevel.name" />
												    </s:elseif>
												    <s:elseif test="'fourth'==member.grouping.tag">
												      <s:property value="fourthLevel.name" />
												    </s:elseif>
												    
											    </s:a>
											    
											  </td>
											  <!-- ● -->
											  <td class="text-truncate" data-toggle="tooltip"  id="levelID">
											
											    <s:if test="'minus_first'==member.grouping.tag">
											      <s:property value="minusFirstLevel.mflid" />
											    </s:if>
											    <s:elseif test="'zero'==member.grouping.tag">
											      <s:property value="zeroLevel.zid" />
											    </s:elseif>
											    <s:elseif test="'first'==member.grouping.tag">
											      <s:property value="firstLevel.flid" />
											    </s:elseif>
											    <s:elseif test="'second'==member.grouping.tag">
											      <s:property value="secondLevel.scid" />
											    </s:elseif>
											    <s:elseif test="'third'==member.grouping.tag">
											      <s:property value="thirdLevel.thid" />
											    </s:elseif>
											    <s:elseif test="'fourth'==member.grouping.tag">
											      <s:property value="fourthLevel.foid" />
											    </s:elseif>
											
											  </td>
											  <td>
											    <s:if test="'minus_first'==member.grouping.tag">
											      <s:property value="minusFirstLevel.description" />
											    </s:if>
											    <s:elseif test="'zero'==member.grouping.tag">
											      <s:property value="zeroLevel.description" />
											    </s:elseif>
											    <s:elseif test="'first'==member.grouping.tag">
											      <s:property value="firstLevel.description" />
											    </s:elseif>
											    <s:elseif test="'second'==member.grouping.tag">
											      <s:property value="secondLevel.description" />
											    </s:elseif>
											    <s:elseif test="'third'==member.grouping.tag">
											      <s:property value="thirdLevel.description" />
											    </s:elseif>
											    <s:elseif test="'fourth'==member.grouping.tag">
											      <s:property value="fourthLevel.description" />
											    </s:elseif>
											  </td>
											  
											  <td>
											    <s:property value="member.grouping.tag" />
											  </td>
											  
											  <td>
											    <s:if test="'minus_first'==member.grouping.tag">
											      <s:if test="null==minusFirstLevel.members">0</s:if>
											      <s:else>
											        <a href="#">
											          <s:property value="minusFirstLevel.members.size()" /></a>
											      </s:else>
											    </s:if>
											    <s:elseif test="'zero'==member.grouping.tag">
											      <s:if test="null==zeroLevel.members">0</s:if>
											      <s:else>
											        <a href="#">
											          <s:property value="zeroLevel.members.size()" /></a>
											      </s:else>
											    </s:elseif>
											    <s:elseif test="'first'==member.grouping.tag">
											      <s:if test="null==firstLevel.members">0</s:if>
											      <s:else>
											        <a href="#">
											          <s:property value="firstLevel.members.size()" /></a>
											      </s:else>
											    </s:elseif>
											    <s:elseif test="'second'==member.grouping.tag">
											      <s:if test="null==secondLevel.members">0</s:if>
											      <s:else>
											        <a href="#">
											          <s:property value="secondLevel.members.size()" /></a>
											      </s:else>
											    </s:elseif>
											    <s:elseif test="'third'==member.grouping.tag">
											      <s:if test="null==thirdLevel.members">0</s:if>
											      <s:else>
											        <a href="#">
											          <s:property value="thirdLevel.members.size()" /></a>
											      </s:else>
											    </s:elseif>
											    <s:elseif test="'fourth'==member.grouping.tag">
											      <s:if test="null==fourthLevel.members">0</s:if>
											      <s:else>
											        <a href="#">
											          <s:property value="fourthLevel.members.size()" /></a>
											      </s:else>
											    </s:elseif>
											  </td>
											  
											  <td>
											    <div class="btn-group" role="group">
											      <!-- ● -->
											      <s:a cssClass="btn btn-sm btn-outline-secondary" href="#" role="button" onclick="managedLevelList.op.disappoint('%{managerid}');">解除任命</s:a>
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
	
</html>