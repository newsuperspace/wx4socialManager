<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
<!-- Bootstrap core CSS -->
<link href="${pageContext.request.contextPath}/css/bootstrap.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/bootstrap-theme.css"
	type="text/css" />
<link href="${pageContext.request.contextPath}/css/dashboard.css"
	rel="stylesheet">
</head>

<body>
	<!--  ==============================以下为浮动对话框的内容=============================== -->
	<!-- 用来显示确认信息的模态对话框 √-->
	<div class="modal fade" tabindex="-1" role="dialog" id="confirm">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">确认操作</h4>
				</div>
				<div class="modal-body">
					<p>该操作将重新批量生成每个商品的二维码，是否继续？</p>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
					<button type="button" class="btn btn-primary"
						onclick="wareModal.operation.batchCreateWareQR()">确认</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!-- /.modal -->

	<!-- 显示商品详细信息（主要是qrcode）√ -->
	<div class="modal fade bs-example-modal-lg" id="infoModal"
		tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel">
		<div class="modal-dialog modal-lg" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">商品详情</h4>
				</div>
				<div class="modal-body">
					<!-- 模态框内容——————开始 -->
					<div class="row">
						<!-- 显示商品专属的QRCode的图片 -->
						<div class="col-md-3">
							<img class="img-thumbnail" width="300px" height="300px" src="#"
								id="info_qrcode" />
						</div>

						<!-- 显示其他的详情信息 -->
						<div class="col-md-6">
							<div class="row">
								<div class="col-md-3">
									<strong>商品名称</strong>
								</div>
								<div class="col-md-9" id="info_wname"></div>
							</div>
							<div class="row">
								<div class="col-md-3">
									<strong>商品ID</strong>
								</div>
								<div class="col-md-9" id="info_wid"></div>
							</div>
							<div class="row">
								<div class="col-md-3">
									<strong>兑换积分</strong>
								</div>
								<div class="col-md-9" id="info_score"></div>
							</div>
							<div class="row">
								<div class="col-md-3">
									<strong>剩余商品</strong>
								</div>
								<div class="col-md-9" id="info_surplus"></div>
							</div>
						</div>
					</div>
					<!-- 模态框内容——————结束 -->
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				</div>
			</div>
		</div>
	</div>

	<!-- 新增用户的模态对话框 √-->
	<div class="modal fade" id="addModal" tabindex="-1" role="dialog"
		aria-labelledby="exampleModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">

				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">新增商品</h4>
				</div>

				<div class="modal-body">

					<!-- 表单数据 -->
					<form class="form-horizontal">

						<div class="form-group">
							<label for="email" class="col-md-2 control-label">商品名称</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" id="newWname">
							</div>
						</div>

						<div class="form-group">
							<label for="email" class="col-md-2 control-label">兑换分值</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" id="newCore">
							</div>
						</div>

						<div class="form-group">
							<label for="community" class="col-md-2 control-label">商品数量</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" id="newSurplus">
							</div>
						</div>
					</form>
				</div>

				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<a href="javascript:wareModal.operation.modal.commitAdd();"><button
							type="button" class="btn btn-primary">添加</button></a>
				</div>

			</div>
		</div>
	</div>

	<!-- 更新商品的模态对话框 -->
	<div class="modal fade" id="updateModal" tabindex="-1" role="dialog"
		aria-labelledby="exampleModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">

				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="update_wname"></h4>
				</div>

				<div class="modal-body">

					<!-- 表单数据 -->
					<form class="form-horizontal">
						<input type="hidden" id="update_wid" />

						<div class="form-group">
							<label for="email" class="col-md-2 control-label">商品名称</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" id="update_name">
							</div>
						</div>


						<div class="form-group">
							<label for="email" class="col-md-2 control-label">兑换分值</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" id="update_core">
							</div>
						</div>

						<div class="form-group">
							<label for="community" class="col-md-2 control-label">商品数量</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" id="update_surplus">
							</div>
						</div>
					</form>

				</div>

				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<a href="javascript:wareModal.operation.modal.commitUpdate();"><button
							type="button" class="btn btn-primary">提交修改</button></a>
				</div>

			</div>
		</div>
	</div>

	<!--  =============================以下为正常页面显示内容================================ -->
	<div class="col-sm-12  col-md-12  main">
		<div class="row" style="vertical-align:middle">
			<h2 class="sub-header col-md-7">用户列表</h2>
			<div class="col-md-5" style="margin-top:30px">
				<shiro:hasPermission name="ware:create">
					<a href="javascript:wareModal.operation.modal.showAddModal();">
						<button type="button" class="btn btn-primary btn-sm">
							新商品 <span class="glyphicon glyphicon-plus"></span>
						</button>
					</a>
				</shiro:hasPermission>
				<a href="#">
					<button type="button" class="btn btn-primary btn-sm">
						搜索 <span class="glyphicon glyphicon-search"></span>
					</button>
				</a> <a href="javascript:wareModal.operation.modal.confirm();">
					<button type="button" class="btn btn-primary btn-sm">
						批量生成二维码 <span class="glyphicon glyphicon-phone"></span>
					</button>
				</a> <a href="#">
					<button type="button" class="btn btn-primary btn-sm">
						群邮件 <span class="glyphicon glyphicon-envelope"></span>
					</button>
				</a>
			</div>
		</div>
		<div class="table-responsive">
			<table class="table table-striped table-hover">
				<thead>
					<tr>
						<th>商品名称</th>
						<th>兑换分值</th>
						<th>剩余</th>
						<th>累计</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
					<s:iterator value="#wares">
						<tr>
							<!-- 用户名、openID、身份证号 -->
							<td><s:property value="wname" /></td>
							<td><s:property value="core" /></td>
							<td><s:property value="surplus" /></td>
							<td><s:a
									href="javascript:wareModal.operation.exchangeList('%{wid}');">
									<s:property value="total" />
								</s:a></td>

							<!-- 操作 -->
							<td><shiro:hasPermission name="ware:update">
									<s:a
										href="javascript:wareModal.operation.modal.showUpdateModal('%{wid}');">
										<button class="btn btn-primary btn-xs">
											修改 <span class="glyphicon glyphicon-wrench"></span>
										</button>
									</s:a>
								</shiro:hasPermission> <s:a
									href="javascript:wareModal.operation.modal.showInfoModal('%{wid}');">
									<button class="btn btn-info btn-xs">
										详情 <span class="glyphicon glyphicon-equalizer"></span>
									</button>
								</s:a></td>

						</tr>
					</s:iterator>
				</tbody>
			</table>
		</div>

	</div>
	</div>
	</div>
</body>
<script src="${pageContext.request.contextPath}/js/jquery-3.2.0.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
<script src="${pageContext.request.contextPath}/js/wareModal.js"></script>
<script>

	function statistic(param) {
		alert("statistic" + param);
	}
</script>
</html>