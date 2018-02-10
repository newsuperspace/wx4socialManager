/**
 * 
 */

var userModal = {
	data : {

	},

	operation : {

		// --------------------------批量生成用户二维码---------------------------
		batchCreateUserQR : function() {
			// 先关闭模态框
			$("#confirm").modal("hide");
			// 准备json数据（并没有需要提交给服务器的数据）
			var data = {
			};
			// 开始ajax通讯
			$.post("userAction_batchCreateUserQR4ajax.action", data, function(data, textStatus, req) {
				if (data.result) {
					// 新建成功
					alert(data.message);
				} else {
					// 新建失败
					alert(data.message);
				}
			});
		},

		//--------------------------------单击某个用户的借阅信息后跳转到借阅图书的列表页面--------------------------
		activityList : function(uid) {
			$(location).attr('href', 'activityAction_showList.action' + '?uid=' + uid);
		},

		// 跳转到该用户的兑换详情页面
		showExchangeInfo : function(param) {
			var uid = param;
			$(location).attr('href','userAction_getExchangeList.action'+'?uid='+uid);
		},


		//-------------------------------- 与模态对话框有关的操作------------------------------
		modal : {
			// 打开confirm对话框
			confirm : function() {
				$("#confirm").modal("show");
			},

			// 显示新建用户的Modal对话框
			showAddModal : function() {
				$("#addModal").modal("show");
			},
			// 提交新建用户的请求
			commitAdd : function() {
				var data = {
					tag : $("#newGrouping").val(),
					address : $("#newAddress").val(),
					cardid : $("#newCardid").val(),
					username : $("#newUsername").val()
				}

				$.post("userAction_addUser4ajax.action", data, function(data, textStatus, req) {
					$("#addModal").modal("hide");
					if (data.result) {
						// 新建成功
						alert(data.message);
					} else {
						// 新建失败
						alert(data.message);
					}
					$(location).attr('href', 'userAction_getUserList.action');
				});
			},

			// 显示用户详细信息（主要是qrcode）的modal对话框
			showInfoModal : function(param) {
				var data = {
					uid : param
				}

				$.post("userAction_getUser4ajax.action", data, function(data, textStatus, req) {
					// console.log(data);   // 这样在火狐和chrome浏览器上通过控制台能看到详细的JSON结构
					$("#info_username").text(data.username);
					$("#info_openid").text(data.openid);
					$("#info_address").text(data.address);
					$("#info_cardid").text(data.cardid);
					$("#info_score").text(data.score);

					// 服务器端通过  ServletActionContext.getServletContext().getContextPath() + qrcode;  得到URL绝对路径
					// 等同于jsp页面上的EL表达式——${pageContext.request.contextPath}/qrcode/2/11/xxx.gif  的效果一样，就是拼装该qrcode图片绝对路径共<img>标签显示
					$("#info_qrcode").attr("src", data.qrcode);
				});

				$("#infoModal").modal("show");
			},


			// 展示模态对话框，用以修改用户数据，并回显用户数据
			showUpdateModal : function(param) {
				var data = {
					uid : param
				}

				$.post("userAction_getUser4ajax.action", data, function(data, textStatus, req) {
					// console.log(data);   // 这样在火狐和chrome浏览器上通过控制台能看到详细的JSON结构
					$("#uid").val(data.uid);
					$("#username").text(data.username);
					$("#cardid").val(data.cardid);
					$("#address").val(data.address);
					if (data.grouping.tag == 'no_real_name_user') {
						$("#grouping").val("no_real_name_user");
					} else if (data.grouping.tag == 'common_user') {
						$("#grouping").val("common_user");
					} else {
						$("#grouping").val("community_user");
					}
				});

				$("#updateModal").modal("show");
			},

			// 将表单新填写的属性通过ajax提交给服务器保存到数据库，然后更新前端页面上的内容
			commitUpdate : function() {
				var data = {
					uid : $("#uid").val(),
					username : $("#username").val(),
					cardid : $("#cardid").val(),
					address : $("#address").val(),
					tag : $("#grouping").val()
				}

				$.post("userAction_update4Json.action", data, function(data, textStatus, req) {
					$("#updateModal").modal("hide");
					alert("保存成功！");
					// 使用这种引导当前页面定位的方式，可以即时性地刷新页面，在列表中显现修改后的结果。
					$(location).attr('href', 'userAction_getUserList.action');
				})
			},
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