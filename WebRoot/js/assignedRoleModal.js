/**
 * 
 */

var assignedRoleModal = {
	data : {

	},

	op : {
		
		// ---------------------------------通用方法--------------------------------
		common:{
			
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


			// 展示模态对话框，并在打开之前回显该用户的角色选择数据【完成】
			showUpdateModal : function(param) {
				var data = {
					uid : param
				};

				$.post("roleAction_getUserAndRole.action", data, function(data, textStatus, req) {
					$("#titleUsername").text(data.user.username+"的角色配置");
					$("#updateUsername").text(data.user.username);
					$("#updateUid").text(data.user.uid);
					$("#uid").val(data.user.uid);
					
					// 清空子元素
					$("#selector").empty();
					// 如果for循环中已经设置了选中，则设置成true,否则是false
					var flag = false;  
					// 创建默认option子元素
					var option = $("<option  value='-1'>--无角色--</option>");
					$("#selector").append(option);
					// 创建各个“角色”的option子元素
					for(var i = 0; i<data.roles.length; i++){
						var rid = data.roles[i].rid;
						var role = data.roles[i].role;
						
						var option2 = $("<option></option>").attr("value", rid).text(role);
						$("#selector").append(option2);
						
						if(null == data.user.role){
							continue;
						}
						
						if(data.user.role.rid == rid){
							option2.attr("selected", "selected");
							flag = true;
						}
					}
					
					if(!flag)
						option.attr("selected", "selected");
				});
				$("#updateModal").modal("show");
			},

			// 将表单新填写的属性通过ajax提交给服务器保存到数据库，然后更新前端页面上的内容【完成】
			commitUpdate : function() {
				var data = {
					uid : $("#uid").val(),
					rid : $("#selector").val(),
				}

				$.post("roleAction_updateAssignedRole.action", data, function(data, textStatus, req) {
					$("#updateModal").modal("hide");

					if(data.result){
						alert(data.message);
						// 使用这种引导当前页面定位的方式，可以即时性地刷新页面，在列表中显现修改后的结果。
						$(location).attr('href', 'roleAction_showAdmins.action');
					}else{
						alert(data.message);
					}
				});
			},
		}
	},

}