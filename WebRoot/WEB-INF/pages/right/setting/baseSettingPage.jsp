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
							<h1 class="h2">后台基础设置</h1>
							<!-- ● -->
							<div class="btn-toolbar mb-2 mb-md-0">
								<!--按钮组-->
								<div  class="btn-group mr-4">
									<button  id="save" class="btn btn-sm btn-outline-secondary"
										onclick="saveConfig();">
										<span class="glyphicon glyphicon-plus"></span> 保存
									</button>
									<button id="cancel" class="btn btn-sm btn-outline-secondary"
										onclick="setRadioSelected('no');">
										<span class="glyphicon glyphicon-minus"></span> 取消
									</button>
									<a name="default" id="aaa" class="btn btn-sm btn-outline-secondary"
										href="#" role="button" onclick="defaultConfig();"> <span
										class="glyphicon glyphicon-time"></span> 初始化
									</a>
								</div>
								<!--popup-->
								<div class="dropdown">
									<button
										class="btn btn-sm btn-outline-secondary dropdown-toggle"
										type="button" id="others" data-toggle="dropdown"
										aria-haspopup="true" aria-expanded="false">
										<span class="glyphicon glyphicon-cog"></span> 其他
									</button>
									<div class="dropdown-menu" aria-labelledby="others">
										<a class="dropdown-item" href="#">Action</a> <a
											class="dropdown-item disabled" href="#">Disabled action</a>
										<h6 class="dropdown-header">Section header</h6>
										<a class="dropdown-item" href="#">Action</a>
										<div class="dropdown-divider"></div>
										<a class="dropdown-item" href="#">After divider action</a>
									</div>
								</div>
							</div>
						</div>
						<!-- =============设置开始=========== -->
						<div class="alert alert-success" role="alert">
							<h4 class="alert-heading">申请设置</h4>
							<hr>
							<div class="row mb-3">
								<div class="col-md-auto">新成员加入时是否需要先提交申请：</div>

								<s:if test="'yes'==needJoinApply">
									<div class="col-md-auto mr-4">
										<!-- <input class="form-check-input" type="radio" name="joinApply" id="joinApply"
                                        value="yes"> 需要 -->
										<div class="form-check">
											<input class="form-check-input" type="radio" name="needJoinApply"
												id="needJoinApply1" value="yes" checked="checked"> <label
												class="form-check-label" for="needJoinApply1"> 需要 </label>
										</div>
									</div>
									<div class="col-md-auto">
										<!-- <input class="form-check-input" type="radio" name="joinApply" id="joinApply"
                                        value="no"> 不需要 -->
										<div class="form-check">
											<input class="form-check-input" type="radio" name="needJoinApply"
												id="needJoinApply2" value="no"> <label
												class="form-check-label" for="needJoinApply2"> 不需要 </label>
										</div>
									</div>
								</s:if>
								<s:else>
									<div class="col-md-auto mr-4">
										<!-- <input class="form-check-input" type="radio" name="joinApply" id="joinApply"
                                        value="yes"> 需要 -->
										<div class="form-check">
											<input class="form-check-input" type="radio" name="needJoinApply"
												id="needJoinApply1" value="yes"> <label
												class="form-check-label" for="needJoinApply1"> 需要 </label>
										</div>
									</div>
									<div class="col-md-auto">
										<!-- <input class="form-check-input" type="radio" name="joinApply" id="joinApply"
                                        value="no"> 不需要 -->
										<div class="form-check">
											<input class="form-check-input" type="radio" name="needJoinApply"
												id="needJoinApply2" value="no" checked="checked"> <label
												class="form-check-label" for="needJoinApply2"> 不需要 </label>
										</div>
									</div>
								</s:else>

							</div>
						</div>

						<!-- =============设置结束=========== -->
					</div>
				</div>
			</div>
		</div>
		<!-- =================================================模态对话框==================================================== -->
		<!-- Modal 层级详情 -->
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
										id="detialsModal_qrcode" alt="Responsive image">
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

	$(function() {
		console.log("开始获取页面上各个设置项的初始化数值，并保存到localStorage中用于感知设置是否发生变动");
		//“申请设置”→“申请加入时是否需要提交申请”
		localStorage.setItem("needJoinApply",  $("input[name='needJoinApply']:checked").val());
	// 初始化2：
	// 初始化3：
	// ....
	});

	// 所有INPUT的onchange事件的回调，用以感知配置是否发生了变化，从而决定saveButton和cancelButton是否disabled
	function checkInput4SaveAndCancelButton(){
		
	}

	// 取消已经发生变动的设置，回复数据库中保存的样子
	function cancelConfig(){
		
	}


	// 保存系统设置的功能入口
	function saveConfig() {
		let url = "settingAction_saveConfig.action";
		let param = {
			"needJoinApply":  $("input[name='needJoinApply']:checked").val(),
		}
		
		$.post(url, param, function(data, textStatus, req) {
			if(data.result){
				alert(data.message);
				// 重新载入当前页
				window.location.reload();
				// 或重置button
			}else{
				alert(data.message);
			}
		});
	}


	// 恢复初始化设置
	function defaultConfig() {
		// 基于AJAX实现初始化设置
		let url = "settingAction_defaultConfig.action"
		$.post(url, null, function(data, textStatus, req) {
			if (data.result) {
				// 初始化成功，重载页面
				alert(data.message);
				// 重新载入当前页面
				window.location.reload();
			} else {
				// 初始化失败，提示信息
				alert(data.message);
			}
		});
	}



	// 1.获取值
	function getRadioValue() {
		// $("input[name='killOrder']:checked").val();
		// $('input:radio:checked').val();
		// $("input[type='radio']:checked").val();
		// $(":radio[checked]").each(function (radio) {
		//     alert($(this).val());
		// }
		let v = $("input[name='needJoinApply']:checked").val();
		alert("选中的radio的值是：" + v);
		if ('yes' == v) {
			$("#cancel").attr("onclick", "setRadioSelected('no');");
		} else if ('no' == v) {
			$("#cancel").attr("onclick", "setRadioSelected('yes');");
		}
	}

	// 2.设置第一个Radio为选中值：
	function setFirstRadioSelected() {
		// $('input:radio:first').attr('checked', 'checked');
		// $('input:radio:first').attr('checked', 'true');
	}

	// 3.设置最后一个Radio为选中值：
	function setLastRadioSelected() {

		// $('input:radio:last').attr('checked', 'checked');
		// $('input:radio:last').attr('checked', 'true');

	}

	//  4.根据索引值设置任意一个radio为选中值：
	function setRadioSelected(value) {
		// $('input:radio').eq(索引值).attr('checked', 'true'); 索引值 = 0, 1, 2....
		// $('input:radio').slice(1, 2).attr('checked', 'true');
		if ('yes' == value) {
			$("input[name='joinApply']").eq(0).attr("checked", 'checked');
			$("input[name='joinApply']").eq(1).removeAttr("checked");
		} else if ('no' == value) {
			$("input[name='joinApply']").eq(1).attr("checked", 'checked');
			$("input[name='joinApply']").eq(0).removeAttr("checked");
		}
	}

	// 5.根据Value值设置Radio为选中值
	function setRadioSelectedByValue() {
		// $("input:radio[value='2']").attr('checked', 'true');
		// $("input[value='1']").attr('checked', 'true');
	}

	// 6.删除Value值为2的Radio
	function deleteRadioByValue() {
		// $("input:radio[value='2']").remove();

	}

	// 7.删除第几个Radio
	function deleteRadioByIndex() {
		// $("input:radio").eq(索引值).remove(); //索引值 = 0, 1, 2....
		// // 如删除第3个Radio: $("input:radio").eq(2).remove();

	}

	// 8.遍历Radio
	function foreachRadio() {
		$('input:radio').each(function(index, domEle) {

			//写入代码

		});
	}
</script>

</html>