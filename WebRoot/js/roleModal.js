/**
 * 
 */

var roleModal = {
	data : {

	},

	op : {

		// ---------------------------------通用方法--------------------------------
		common : {

			// 用户点击权限所属角色的数量连接的时候，就会执行本方法，跳转到展示角色的页面
			toRolesByPid : function(param) {
				$(location).attr("href", "permissionAction_getRolesByPid.action" + "?pid=" + param);
			}
		},

		//-------------------------------- 与模态对话框有关的操作------------------------------
		modal : {
			// 打开确认对话框【完成】
			confirm : function() {
				$("#confirm").modal("show");
			},

			// 打开 详情 的modal 【完成】
			infoModal : function(param) {
				var data = {
					rid : param
				};
				var url = "roleAction_getRoleByRid.action";
				$.post(url, data, function(data, textStatus, req) {

					// 设置 文本 
					$('#infoRoleName').text(data.role.role);
					$('#infoDescription').text(data.role.description);
					$('#infoRID').text(data.role.rid);
					// 设置 权限列表
					$('#infoPermissions').empty();
					var row = null;
					var col = null;
					var span = null;

					for (var i = 0; i < data.role.permissions.length; i++) {
						if ((i % 4) == 0) {
							if (null != row) {
								$('#infoPermissions').append(row);
							}
							// 每行有四个,新建一行
							row = $('<div class="row"></div>');
						}
						col = $('<div class="col-md-3"></div>');
						if (data.role.permissions[i].available) {
							span = $('<span class="label label-info"></span>');
						} else {
							span = $('<span class="label label-default"></span>');
						}
						span.text(data.role.permissions[i].description);
						col.append(span);
						row.append(col);
					}

					if (null != row) {
						$('#infoPermissions').append(row);
					}
					// 设置用户列表
					$('#infoUsers').empty();
					row = null;
					col = null;

					for (var i = 0; i < data.users.length; i++) {
						if ((i % 4) == 0) {
							if (null != row) {
								$('#infoUsers').append(row);
							}
							// 每行有四个,新建一行
							row = $('<div class="row"></div>');
						}
						col = $('<div class="col-md-3"></div>');
						col.text(data.users[i].username);
						row.append(col);
					}

					if (null != row) {
						$('#infoUsers').append(row);
					}
				});
				$('#infoModal').modal("show");
			},

			// 显示新建角色的Modal对话框 【完成】
			createModal : function() {

				var url = "roleAction_getInfo4CreateRole.action";

				$.post(url, null, function(data, textStatus, req) {
					$('#createPermissions').empty();

					var row = null;
					var col = null;
					var span = null;
					var checkbox = null;

					for (var i = 0; i < data.permissions.length; i++) {
						if ((i % 4) == 0) {
							if (null != row) {
								$('#createPermissions').append(row);
							}
							row = $('<div class="row" ></div>');
						}

						col = $('<div class="col-md-3"></div>');
						if (data.permissions[i].available) {
							span = $('<span class="label label-info"></div>').text(data.permissions[i].description);
						} else {
							span = $('<span class="label label-default"></div>').text(data.permissions[i].description);
						}
						checkbox = $('<input type="checkbox" name="pids"  />').attr("value", data.permissions[i].pid);
						col.append(span);
						col.append(checkbox);
						row.append(col);
					}

					if (null != row) {
						$('#createPermissions').append(row);
					}
				});

				$("#addModal").modal("show");
			},

			commitCreate : function() {

				var data = {
					role : $('#newRoleName').val(),
					description : $('#newDescription').val(),
					pids : ""
				};
				var url = "roleAction_createRole.action";

				$("input[name='pids']").each(function(i, element) {
					if ($(this).is(':checked')) {
						// 该选项被选中
						if ("" == data.pids) {
							data.pids += $(this).attr("value");
						} else {
							data.pids += ",";
							data.pids += $(this).attr("value");
						}
					}
				});

				$.post(url, data, function(data, textStatus, req) {
					$("#addModal").modal("hide");

					if (data.result) {
						alert(data.message);
						$(location).attr('href', 'roleAction_showRoles.action');
					} else {
						alert(data.message);
					}
				});
			},

			// 展示模态对话框，用以修改角色数据，并在打开之前回显权限数据
			updateModal : function(param) {
				var data = {
					rid : param
				}

				$.post("roleAction_getInfo4UpdateRole.action", data, function(data, textStatus, req) {
					$("#updateRoleName").val(data.role.role);
					$("#updateDescription").val(data.role.description);
					$('#updateRid').val(data.role.rid);

					if (null != data.permissions) {
						$('#updatePermissions').empty();

						var row = null;
						var col = null;
						var span = null;
						var checkbox = null;

						for (var i = 0; i < data.permissions.length; i++) {
							if ((i % 4) == 0) {
								if (null != row) {
									$('#updatePermissions').append(row);
								}
								row = $('<div class="row" ></div>');
							}

							col = $('<div class="col-md-3"></div>');
							if (data.permissions[i].available) {
								span = $('<span class="label label-info"></div>').text(data.permissions[i].description);
							} else {
								span = $('<span class="label label-default"></div>').text(data.permissions[i].description);
							}
							checkbox = $('<input type="checkbox" name="updatePids"  />').attr("value", data.permissions[i].pid);

							// 判断该权限是不是已经数据当前需要更新的角色了，如果是则设置成选中状态
							if (null != data.role.permissions) {
								for (var j = 0; j < data.role.permissions.length; j++) {
									if (data.permissions[i].pid == data.role.permissions[j].pid) {
										checkbox.attr("checked", "checked");
									}
								}
							}

							col.append(span);
							col.append(checkbox);
							row.append(col);
						}

						if (null != row) {
							$('#updatePermissions').append(row);
						}
					}
				});

				$("#updateModal").modal("show");
			},

			// 将表单新填写的属性通过ajax提交给服务器保存到数据库，然后更新前端页面上的内容
			commitUpdate : function() {
				var data = {
					rid:  $('#updateRid').val(),
					role : $('#updateRoleName').val(),
					description : $('#updateDescription').val(),
					pids : ""
				};
				var url = "roleAction_updateRole.action";

				$("input[name='updatePids']").each(function(i, element) {
					if ($(this).is(':checked')) {
						// 该选项被选中
						if ("" == data.pids) {
							data.pids += $(this).attr("value");
						} else {
							data.pids += ",";
							data.pids += $(this).attr("value");
						}
					}
				});

				$.post(url, data, function(data, textStatus, req) {
					$("#addModal").modal("hide");

					if (data.result) {
						alert(data.message);
						$(location).attr('href', 'roleAction_showRoles.action');
					} else {
						alert(data.message);
					}
				});
			},
		}
	},
}