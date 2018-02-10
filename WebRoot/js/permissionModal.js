/**
 * 
 */

var permModal = {
	data : {

	},

	op : {
		
		// ---------------------------------通用方法--------------------------------
		common:{
			//获取权限所属的所有角色的列表
			roleList : function(pid) {
				$(location).attr('href', 'permissionAction_getRolesByPid.action' + '?pid=' + pid);
			},
			
			// 用户点击权限所属角色的数量连接的时候，就会执行本方法，跳转到展示角色的页面
			toRolesByPid: function(param){
				$(location).attr("href", "permissionAction_getRolesByPid.action"+"?pid="+param);
			}
		},

		//-------------------------------- 与模态对话框有关的操作------------------------------
		modal : {
			// 打开确认对话框【完成】
			confirm : function() {
				$("#confirm").modal("show");
			},

			// 显示新建权限的Modal对话框【完成】
			createPermissionModal : function() {
				$("#addModal").modal("show");
			},
			// 提交新建权限的请求【完成】
			commitCreate : function() {
				var data = {
					permission : $("#newPermission").val(),
					description : $("#newDescription").val(),
					state : $("#newState").val(),
				}

				$.post("permissionAction_createPermission.action", data, function(data, textStatus, req) {
					$("#addModal").modal("hide");
					if (data.result) {
						// 新建成功
						alert(data.message);
						$(location).attr('href', 'permissionAction_showPermissions.action');
					} else {
						// 新建失败
						alert(data.message);
					}
				});
			},


			// 展示模态对话框，用以修改权限数据，并在打开之前回显权限数据【完成】
			showUpdateModal : function(param) {
				var data = {
					pid : param
				}

				$.post("permissionAction_getPermissionByPid.action", data, function(data, textStatus, req) {
					// console.log(data);   // 这样在火狐和chrome浏览器上通过控制台能看到详细的JSON结构
					$("#updatePid").val(data.pid);
					$("#updatePermission").val(data.permission);
					$("#updateDescription").val(data.description);
					if (data.available) {
						$("#updateState").val("able");
					} else {
						$("#updateState").val("unable");
					}
				});

				$("#updateModal").modal("show");
			},

			// 将表单新填写的属性通过ajax提交给服务器保存到数据库，然后更新前端页面上的内容
			commitUpdate : function() {
				var data = {
					pid : $("#updatePid").val(),
					permission : $("#updatePermission").val(),
					description : $("#updateDescription").val(),
					state : $("#updateState").val(),
				}

				$.post("permissionAction_updatePermission.action", data, function(data, textStatus, req) {
					$("#updateModal").modal("hide");

					if(data.result){
						alert(data.message);
						// 使用这种引导当前页面定位的方式，可以即时性地刷新页面，在列表中显现修改后的结果。
						$(location).attr('href', 'permissionAction_showPermissions.action');
					}else{
						alert(data.message);
					}
				});
			},
		}
	},

}