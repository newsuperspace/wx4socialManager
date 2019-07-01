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
							<h1 class="h2">批量创建本层级直辖用户</h1>
							<div class="btn-toolbar mb-2 mb-md-0">
								<div class="btn-group mr-2">

									<button class="btn btn-sm btn-outline-secondary"
										data-toggle="modal" data-target="#newUserModal">
										<span class="glyphicon glyphicon-plus"></span>返回
									</button>
									<button class="btn btn-sm btn-outline-secondary"
										data-toggle="modal" data-target="#batchNewUserModal">
										<span class="glyphicon glyphicon-plus"></span> 其他功能
									</button>

								</div>
							</div>
						</div>

						<!-- =============上传文件开始=========== -->
						<div id="download">
							请上传指定格式的 <a href="userAction_downloadExcel4BatchCreate.action">xlsx文档</a>
							<input type="file" id="excel-file">
						</div>
						<!-- =============上传文件结束=========== -->

						<!-- =============表格开始=========== -->
						<div id="excelTable"
							style="white-space: nowrap;overflow-x: hidden;overflow-x: auto;">
						</div>
						<!-- =============表格结束=========== -->


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
<script lang="text/javascript"
	src="${pageContext.request.contextPath}/js/xlsx.full.min.js"></script>
<script>

	// 页面初始化工作
	$(function() {
		// 功能模块的初始显隐状态设置
		$("#excelTable").hide();
		$("#download").show();
		// 文件上传后的事件回调
		// 【完成】负责xlsx文档上传，并基於SheetJS将其中数据解析为JSONarray对象后执行 批量创建新用户的业务逻辑
		$('#excel-file').change(function(e) {
			var files = e.target.files;

			var fileReader = new FileReader();
			fileReader.onload = function(ev) {
				try {
					var data = ev.target.result,
						workbook = XLSX.read(data, {
							type : 'binary'
						}), // 以二进制流方式读取得到整份excel表格对象
						persons = []; // 存储获取到的数据
				} catch (e) {
					console.log('文件类型不正确');
					return;
				}

				// 表格的表格范围，可用于判断表头是否数量是否正确
				var fromTo = '';
				// 遍历每张表读取
				for (var sheet in workbook.Sheets) {
					if (workbook.Sheets.hasOwnProperty(sheet)) {
						fromTo = workbook.Sheets[sheet]['!ref'];
						console.log(fromTo);
						persons = persons.concat(XLSX.utils.sheet_to_json(workbook.Sheets[sheet]));
					// break; // 如果只取第一张表，就取消注释这行
					}
				}
				console.log(persons);
				// 通过AJAX向后端传递数据的业务逻辑
				batchCreateUser(persons);
			};
			// 以二进制方式打开文件
			fileReader.readAsBinaryString(files[0]);
		});
	});

	//前端上传xlsx文档，基于SheetJS解析成JSONarray后，本方法负责通过AJAX提交给服务器并向用户展示服务器的处理结果
	function batchCreateUser(batchUser) {
		let url = "userAction_batchCreate.action";
		let param = {
			// JSON的全局方法stringify()可将JSON对象转变为JSON格式字符串
			// 相反的操作是通过 JSON.parse(str) 由JSON字符串转换为JSON对象
			"batchUserStr" : JSON.stringify(batchUser),
		};
		$.post(url, param, function(data, textStatus, req) {
			if (!data.result) {
				alert(data.message);
				return;
			}

			// 根据后端对前端解析出的Excel表数据的校验结果，组织生成前端页面
			let container = $("#excelTable");
			let table = $('<table class="table table-sm"></table>');
			let thead = $('<thead><tr><th scope="col">#</th><th scope="col">姓名</th><th scope="col">性别</th><th scope="col">电话</th><th scope="col">年龄</th><th scope="col">状态</th></tr></thead>');

			let tbody = $("<tbody></tbody>");
			let tr,
				th,
				td;

			for (var i = 0; i < data.list.length; i++) {
				if ("table-warning" == data.list[i].style) {
					tr = $('<tr class="table-warning"></tr>');
				} else if ("table-danger" == data.list[i].style) {
					tr = $('<tr class="table-danger"></tr>');
				} else {
					tr = $('<tr></tr>');
				}
				th = $('<th scope="row">' + (i + 1) + '</th>');
				tr.append(th);

				td = $("<td>" + data.list[i].username + "</td>");
				tr.append(td);
				td = $("<td>" + data.list[i].sex + "</td>");
				tr.append(td);
				td = $("<td>" + data.list[i].phone + "</td>");
				tr.append(td);
				td = $("<td>" + data.list[i].age + "</td>");
				tr.append(td);
				td = $("<td>" + data.list[i].state + "</td>");
				tr.append(td);

				tbody.append(tr);
			}

			table.append(thead);
			table.append(tbody);
			container.append(table);

			let p = $("<p>以上是从上传的Excel中解析出的全部用户数据，其中正确数据" + data.normalNum + "个，错误数据" + data.invaliableNum + "个，已存在用户数据" + data.existNum + "个，是否继续？</p>")
			let btnOk = $('<button type="button" class="btn btn-primary mr-1" onclick="alert(\'确定创建\');">创建</button>');
			let btnCancle = $('<button type="button" class="btn btn-secondary" onclick="alert(\'取消创建\');">取消</button>');
			container.append(p);
			container.append(btnOk);
			container.append(btnCancle);
			$("#excelTable").show();
			$("#download").hide();
		});
	}
</script>

</html>