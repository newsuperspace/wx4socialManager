/**
 * 
 */

var wareModal = {
	data : {

	},

	operation : {

		// --------------------------批量商品二维码（√）---------------------------
		batchCreateWareQR : function() {
			// 先关闭模态框
			$("#confirm").modal("hide");
			// 准备json数据（并没有需要提交给服务器的数据）
			var data = {
			};
			// 开始ajax通讯
			$.post("wareAction_batchCreateWareQR4ajax.action", data, function(data, textStatus, req) {
				if (data.result) {
					// 新建成功
					alert(data.message);
				} else {
					// 新建失败
					alert(data.message);
				}
			});
		},

		//--------------------------------单击某个商品“兑换详情”跳转到有关该商品的兑换记录列表页面--------------------------
		exchangeList : function(wid) {
			$(location).attr('href', 'wareAction_showExchangeList.action' + '?wid=' + wid);
		},

		//-------------------------------- 与模态对话框有关的操作------------------------------
		modal : {
			// 打开confirm对话框 (√)
			confirm : function() {
				$("#confirm").modal("show");
			},

			// 显示新建商品的Modal对话框  （√）
			showAddModal : function() {
				$("#addModal").modal("show");
			},
			// 提交新建用户的请求  （√）
			commitAdd : function() {
				var data = {
					core : $("#newCore").val(),
					surplus : $("#newSurplus").val(),
					wname : $("#newWname").val()
				}

				$.post("wareAction_addWare4ajax.action", data, function(data, textStatus, req) {
					$("#addModal").modal("hide");
					if (data.result) {
						// 新建成功
						alert(data.message);
					} else {
						// 新建失败
						alert(data.message);
					}
					$(location).attr('href', 'wareAction_showAllList.action');
				});
			},

			// 显示商品详细信息（主要是qrcode）的modal对话框（√）
			showInfoModal : function(param) {
				var data = {
					wid : param
				}

				$.post("wareAction_getWare4ajax.action", data, function(data, textStatus, req) {
					// console.log(data);   // 这样在火狐和chrome浏览器上通过控制台能看到详细的JSON结构
					$("#info_wname").text(data.wname);
					$("#info_wid").text(data.wid);
					$("#info_surplus").text(data.surplus);
					$("#info_score").text(data.core);

					// 服务器端通过  ServletActionContext.getServletContext().getContextPath() + qrcode;  得到URL绝对路径
					// 等同于jsp页面上的EL表达式——${pageContext.request.contextPath}/qrcode/2/11/xxx.gif  的效果一样，就是拼装该qrcode图片绝对路径共<img>标签显示
					$("#info_qrcode").attr("src", data.qrcodeUrl);
				});

				$("#infoModal").modal("show");
			},


			// 展示模态对话框，用以修改商品数据信息，并回显数据信息 （√）
			showUpdateModal : function(param) {
				var data = {
					wid : param
				}

				$.post("wareAction_getUser4ajax.action", data, function(data, textStatus, req) {
					// console.log(data);   // 这样在火狐和chrome浏览器上通过控制台能看到详细的JSON结构
					$("#update_wid").val(data.wid);
					$("#update_wname").text(data.wname);
					$("#update_name").text(data.wname);
					$("#update_core").text(data.core);
					$("#update_surplus").text(data.surplus);
				});
				$("#updateModal").modal("show");
			},

			// 将表单新填写的属性通过ajax提交给服务器保存到数据库，然后更新前端页面上的内容 (√)
			commitUpdate : function() {
				var data = {
					wid : $("#update_wid").val(),
					wname : $("#update_name").val(),
					core : $("#update_core").val(),
					surplus : $("#update_surplus").val()
				}

				$.post("wareAction_update4Json.action", data, function(data, textStatus, req) {
					$("#updateModal").modal("hide");
					alert("保存成功！");
					// 使用这种引导当前页面定位的方式，可以即时性地刷新页面，在列表中显现修改后的结果。
					$(location).attr('href', 'wareAction_showAllList.action');
				})
			}
		}
	},

	init : {
		// 初始化数据
		initData : {

		},

		// 初始化事件
		initEvent : {

		},
	},
}