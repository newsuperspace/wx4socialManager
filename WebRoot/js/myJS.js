/**
 * ========================权限层级PermissionLevel========================
 */
var permissionLevelModal = {
	init : {
		data : {},
		op : {},
	},
	data : {},
	op : {

		/**
		 * 新建权限
		 */
		create : function() {
			var data = {
				permissionLevelName : $("#permissionLevelName").val(),
				description : $("#description").val(),
			};
			$.post("permissionLevelAction_create.action", data, function(data, textStatus, req) {
				alert(data.message);
				window.location.reload();
			});
		},

		/**
		 * 获取详细信息
		 */
		itemInfo : function(id) {
			alert("当前获取详细信息的ID是：" + id);
			return false;
		},
	}
}


/**
 * ========================权限类型PermissionType========================
 */
var permissionTypeModal = {
	init : {
		data : {},
		op : {},
	},
	data : {},
	op : {

		/**
		 * 新建权限
		 */
		create : function() {
			var data = {
				plid : $("#plid").val(),
				permissionTypeName : $("#permissionTypeName").val(),
				description : $("#description").val(),
			};
			$.post("permissionTypeAction_create.action", data, function(data, textStatus, req) {
				alert(data.message);
				window.location.reload();
			});
		},

		/**
		 * 获取详细信息
		 */
		itemInfo : function(id) {
			alert("当前获取详细信息的ID是：" + id);
			return false;
		},
	}
}


/**
 * ========================权限Permission========================
 */
var permissionModal = {
	init : {
		data : {},
		op : {},
	},
	data : {},
	op : {

		/**
		 * 新建权限
		 */
		create : function() {
			var data = {
				plid : $("#plid").val(),
				ptid : $("#ptid").val(),
				permissionName : $("#permissionName").val(),
				description : $("#description").val(),
			};
			$.post("permissionAction_create.action", data, function(data, textStatus, req) {
				alert(data.message);
				window.location.reload();
			});
		},

		/**
		 * 获取详细信息
		 */
		itemInfo : function(id) {
			alert("当前获取详细信息的ID是：" + id);
			return false;
		},

		/**
		 * 当用户在新建权限Modal中选定了PermissionLevel后，会调用本方法，
		 * 它会通过Ajax技术从后端获取指定PermissionLevel之下的PermissionType数据放入到Select中
		 */
		preparePermissionTypeByPermissionLevel : function() {

			var plid = $("#plid").val();
			$("#ptid").empty();

			if ('0' == plid) {
				$("#ptid").append("<option value='0' selected>请先选择权限层级</option>").attr("disabled", disabled);
				return;
			}

			var data = {
				plid : plid,
			};
			$.post("permissionAction_getPermissionTypeList.action", data, function(data, textStatus, req) {
				console.log(data);
				// 激活该select;增加默认选项
				$("#ptid").removeAttr("disabled").append("<option value='0' selected>请选择...</option>");
				$.each(data, function(index, elt) {
					// 解析出的每个元素都是一个包含一个PermissionType数据的JSON对象，可从中获取必要的PermissionType数据信息
					$("#ptid").append("<option value=" + elt.ptid + ">" + elt.permissionTypeName + "</option>");
				});
			});
		},

		/**
		 * 批量自动化创建系统基础权限
		 */
		batchAutoCreate : function() {
			var answer = confirm("自动化批量创建权限将删除系统后台已有权限，是否继续？");
			if (answer) {
				$.post("permissionAction_batchCreatePermission.action", null, function(data, textStatus, req) {
					alert(data.message);
					window.location.reload();
				});
			} else {
				// 什么也不做
			}
			return false;
		},
	}
}

/**
 * ========================第四层级========================
 */
// <!-- ● -->
var fourthLevelModal = {
	init : {},
	data : {},
	op : {
		/**
		 * 创建当前层级
		 */
		createLevel : function() {

			var data = {
				description : $("#description").val(),
				name : $("#name").val(),
			};

			// <!-- ● -->
			$.post("fourthLevelAction_createLevel.action", data, function(data, textStatus, req) {

				$("#newModal").modal("hide");
				alert(data.message);
				window.location.reload();
			});
		},

		/**
		 * 新建某个当前级的子层级对象
		 */
		createSonLevel : function() {
			$("#newSonLevelModal").modal('hide');

			var data = {
				parentId : $('#parentId').val(),
				sonDescription : $('#sonDescription').val(),
				sonName : $("#sonName").val()
			};

			// <!-- ● -->
			$.post("fourthLevelAction_createSonLevel.action", data, function(data, textStatus, req) {
				alert(data.message);
				window.location.reload();
			});
		},

		/**
		 * 获取详细信息
		 */
		levelInfo : function(id) {
			// <!-- ● -->
			alert("当前获取详细信息的thirdLevel的ID是：" + id);
			return false;
		},
	}
};

/**
 * ========================第三层级========================
 */
// <!-- ● -->
var thirdLevelModal = {
	init : {},
	data : {},
	op : {
		/**
		 * 创建当前层级
		 */
		createLevel : function() {

			var data = {
				description : $("#description").val(),
				name : $("#name").val(),
			};

			// <!-- ● -->
			$.post("thirdLevelAction_createLevel.action", data, function(data, textStatus, req) {

				$("#newModal").modal("hide");
				alert(data.message);
				window.location.reload();
			});
		},

		/**
		 * 准备并显示用于创建某个当前级的子层级对象的
		 * MODAL
		 * 这个函数的实现告诉我们，在学会Angular之前，掌握jQuery的选择器是多么重要
		 */
		showCreateSonLevelModal : function(obj) {

			var self = $(obj);
			var parentDescription = self.parent().parent().prev().prev().prev().prev().prev().prev().prev().prev().prev(); // <!-- ● -->
			var parentId = parentDescription.prev();
			var parentName = parentId.prev();

			$("#newSonLevelModal_title").text("新建" + parentName.children().text() + "的子层级");
			$("#parentId").val(parentId.attr("data-original-title"));
			$("#parentDescription").val(parentDescription.text());

			$("#newSonLevelModal").modal('show');
			return false;
		},

		/**
		 * 新建某个当前级的子层级对象
		 */
		createSonLevel : function() {
			$("#newSonLevelModal").modal('hide');

			var data = {
				parentId : $('#parentId').val(),
				sonDescription : $('#sonDescription').val(),
				sonName : $("#sonName").val()
			};

			// <!-- ● -->
			$.post("thirdLevelAction_createSonLevel.action", data, function(data, textStatus, req) {
				alert(data.message);
				window.location.reload();
			});
		},

		/**
		 * 获取详细信息
		 */
		levelInfo : function(id) {
			// <!-- ● -->
			alert("当前获取详细信息的thirdLevel的ID是：" + id);
			return false;
		},
	}
};

/**
 * ========================第二层级========================
 */
// <!-- ● -->
var secondLevelModal = {
	init : {},
	data : {},
	op : {
		/**
		 * 创建当前层级
		 */
		createLevel : function() {

			var data = {
				description : $("#description").val(),
				name : $("#name").val(),
			};

			// <!-- ● -->
			$.post("secondLevelAction_createLevel.action", data, function(data, textStatus, req) {

				$("#newModal").modal("hide");
				alert(data.message);
				window.location.reload();
			});
		},

		/**
		 * 准备并显示用于创建某个当前级的子层级对象的
		 * MODAL
		 * 这个函数的实现告诉我们，在学会Angular之前，掌握jQuery的选择器是多么重要
		 */
		showCreateSonLevelModal : function(obj) {

			var self = $(obj);
			var parentDescription = self.parent().parent().prev().prev().prev().prev().prev().prev().prev().prev(); // <!-- ● -->
			var parentId = parentDescription.prev();
			var parentName = parentId.prev();

			$("#newSonLevelModal_title").text("新建" + parentName.children().text() + "的子层级");
			$("#parentId").val(parentId.attr("data-original-title"));
			$("#parentDescription").val(parentDescription.text());

			$("#newSonLevelModal").modal('show');
			return false;
		},

		/**
		 * 新建某个当前级的子层级对象
		 */
		createSonLevel : function() {
			$("#newSonLevelModal").modal('hide');

			var data = {
				parentId : $('#parentId').val(),
				sonDescription : $('#sonDescription').val(),
				sonName : $("#sonName").val()
			};

			// <!-- ● -->
			$.post("secondLevelAction_createSonLevel.action", data, function(data, textStatus, req) {
				alert(data.message);
				window.location.reload();
			});
		},

		/**
		 * 获取详细信息
		 */
		levelInfo : function(id) {
			// <!-- ● -->
			alert("当前获取详细信息的SecondLevel的ID是：" + id);
			return false;
		},
	}
};

/**
 * ========================第一层级========================
 */
var firstLevelModal = {
	init : {},
	data : {},
	op : {
		/**
		 * 创建当前层级
		 */
		createLevel : function() {

			var data = {
				description : $("#description").val(),
				name : $("#name").val(),
			};

			// <!-- ● -->
			$.post("firstLevelAction_createLevel.action", data, function(data, textStatus, req) {

				$("#newModal").modal("hide");
				alert(data.message);
				window.location.reload();
			});
		},

		/**
		 * 准备并显示用于创建某个当前级的子层级对象的
		 * MODAL
		 * 这个函数的实现告诉我们，在学会Angular之前，掌握jQuery的选择器是多么重要
		 */
		showCreateSonLevelModal : function(obj) {

			var self = $(obj);
			var parentDescription = self.parent().parent().prev().prev().prev().prev().prev().prev().prev(); // <!-- ● -->
			var parentId = parentDescription.prev();
			var parentName = parentId.prev();

			$("#newSonLevelModal_title").text("新建" + parentName.children().text() + "的子层级");
			$("#parentId").val(parentId.attr("data-original-title"));
			$("#parentDescription").val(parentDescription.text());

			$("#newSonLevelModal").modal('show');
			return false;
		},

		/**
		 * 新建某个当前级的子层级对象
		 */
		createSonLevel : function() {
			$("#newSonLevelModal").modal('hide');

			var data = {
				parentId : $('#parentId').val(),
				sonDescription : $('#sonDescription').val(),
				sonName : $("#sonName").val()
			};

			// <!-- ● -->
			$.post("firstLevelAction_createSonLevel.action", data, function(data, textStatus, req) {
				alert(data.message);
				window.location.reload();
			});
		},

		/**
		 * 获取详细信息
		 */
		levelInfo : function(id) {
			// <!-- ● -->
			alert("当前获取详细信息的FirstLevel的ID是：" + id);
			return false;
		},
	}
};

/**
 * ========================社区层级========================
 */
var zeroLevelModal = {
	init : {},
	data : {},
	op : {
		createLevel : function() {

			var data = {
				description : $("#description").val(),
				name : $("#name").val(),
			};

			$.post("zeroLevelAction_createLevel.action", data, function(data, textStatus, req) {

				$("#newModal").modal("hide");
				alert(data.message);
				window.location.reload();
			});
		},

		/**
		 * 准备并显示用于创建某个当前级的子层级对象的
		 * MODAL
		 * 这个函数的实现告诉我们，在学会Angular之前，掌握jQuery的选择器是多么重要
		 */
		showCreateSonLevelModal : function(obj) {

			var self = $(obj);
			var parentDescription = self.parent().parent().prev().prev().prev().prev().prev().prev();
			var parentId = parentDescription.prev();
			var parentName = parentId.prev();

			$("#newSonLevelModal_title").text("新建" + parentName.children().text() + "的子层级");
			$("#parentId").val(parentId.attr("data-original-title"));
			$("#parentDescription").val(parentDescription.text());

			$("#newSonLevelModal").modal('show');
			return false;
		},

		/**
		 * 新建某个当前级的子层级对象
		 */
		createSonLevel : function() {
			$("#newSonLevelModal").modal('hide');

			var data = {
				parentId : $('#parentId').val(),
				sonDescription : $('#sonDescription').val(),
				sonName : $("#sonName").val()
			};

			$.post("zeroLevelAction_createSonLevel.action", data, function(data, textStatus, req) {
				alert(data.message);
				window.location.reload();
			});
		},

		/**
		 * 获取详细信息
		 */
		levelInfo : function(id) {
			alert("当前获取详细信息的MinusFirstLevel的ID是：" + id);
			return false;
		},
	}
};

/**
 * ========================街道层级========================
 */
var minusFirstLevelModal = {
	init : {

	},

	data : {

	},

	op : {

		/**
		 * 新建街道层级
		 */
		createMinusFirstLevel : function() {
			var data = {
				name : $("#name").val(),
				description : $("#description").val(),
			};

			$.post("minusFirstLevelAction_createLevel.action", data, function(data, textStatus, req) {
				$("#newMinusFirstLevelModal").modal("hide");
				alert(data.message);
				window.location.reload()
			});
			return false;
		},


		/**
		 * 准备并显示用于创建某个街道的子层级对象的
		 * MODAL
		 * 这个函数的实现告诉我们，在学会Angular之前，掌握jQuery是多么重要
		 */
		showCreateSonLevelModal : function(obj) {

			var self = $(obj);
			var description = self.parent().parent().prev().prev().prev().prev().prev();
			var mflid = description.prev();
			var name = mflid.prev();

			$("#newSonLevelModal_title").text("新建" + name.children().text() + "的子层级");
			$("#mflid").val(mflid.attr("data-original-title"));
			$("#parentDescription").val(description.text());

			$("#newSonLevelModal").modal('show');
			return false;
		},


		/**
		 * 新建某个街道层级的子层级对象
		 */
		createSonLevel : function() {
			$("#newSonLevelModal").modal('hide');

			var data = {
				mflid : $('#mflid').val(),
				sonDescription : $('#sonDescription').val(),
				sonName : $("#sonName").val()
			};

			$.post("minusFirstLevelAction_createSonLevel.action", data, function(data, textStatus, req) {
				alert(data.message);
				window.location.reload();
			});
		},

		/**
		 * 获取详细信息
		 */
		levelInfo : function(id) {
			alert("当前获取详细信息的MinusFirstLevel的ID是：" + id);
			return false;
		},
	}
};

/**
 *========================层级页面的Modal通用方法========================
 */
var commonLevelModal = {
	init : {},
	data : {},
	op : {

		/**
		 * 保存Permission数据到后端
		 */
		savePermission : function(level, lid) {

			// 准备AJAX的请求参数
			var data = {
				selected : '',
				level : level,
				lid : lid
			};
			// 获取到所有权限设置的checkbox
			var $inputs = $("input[data-permission='-1']");
			console.log($inputs);
			// 遍历每个input
			$inputs.each(function(i, element) {
				if ($(this).is(':checked')) {
					data.selected += $(this).val() + ",";
				}
			});
			console.log(data.selected);
			// 将被选中的权限ID字符串发送到后端
			$.post("permissionAction_permissionSaving.action", data, function(data, textStatus, req) {
				alert(data.message);
			});
		},

		/**
		 * 显示权限配置的Modal
		 * 1、获取当前JSP页面对应操作的层级对象的level数值（当前MinusFirst为-1）
		 * 2、获取被操作的层级对象的ID（当前MinusFirstLevel为mflid）
		 * 3、通过AJAX与后端通讯，获取当前层级的所有权限类型，以及每个权限的设置状态（是否被当前层级对象所拥有）
		 * 4、遍历从后端传递来的数据，并使用jQuery重组前端页面的HTML元素，用来构建MODAL中的内容
		 * 5、显示MODAL
		 */
		showPermissionModal : function(level, lid) {

			$("#savePermission").unbind().bind("click", function() {
				commonLevelModal.op.savePermission(level, lid);
			});

			// 准备AJAX的请求参数
			var data = {
				level : level,
				lid : lid
			};
			// 执行AJAX的POST请求
			$.post("permissionAction_permissionSetting.action", data, function(data, textStatus, req) {
				// 先清空MODAL中负责显示权限内容的DIV中的内容
				$("#permission-modal-body").empty();
				// 然后开始分析data中的数据，重建MODAL中的内容
				for (var i = 0; i < data.length; i++) {
					// 得到“权限类型”
					var permissionType = data[i];
					// 新建外层的collapse的Card部分及其card-Header部分
					var titleHTML = $("<a></a>");
					titleHTML.attr("data-toggle", "collapse").attr("data-parent", "#permission-modal-body").attr("data-parent", "#accordianId").attr("href", "#" + permissionType.ptid);
					titleHTML.text(permissionType.description);
					var h5HTML = $("<h5></h5>").attr("class", "mb-0");
					h5HTML.append(titleHTML);
					var cardHeaderHTML = $("<div></div>").attr("role", "tab").attr("class", "card-header").append(h5HTML);
					var cardHTML = $("<div></div>").attr("class", "card").append(cardHeaderHTML);
					// 得到该类型下的所有“权限”
					var permissions = permissionType.permissions4Ajax;
					var rowHTML = $("<div></div>").attr("class", "row");
					// 开始创建row中包含权限复选框的的文档结构
					for (var j = 0; j < permissions.length; j++) {
						var permission = permissions[j];
						// 开始组建一个权限复选框的HTML对象
						var permissionInput = $("<input></input>");
						permissionInput.attr("class", "form-check-input");
						permissionInput.attr("type", "checkbox");
						permissionInput.attr("data-permission", "-1");
						permissionInput.attr("name", "permission");
						permissionInput.attr("value", permission.pid);
						if (permission.open) {
							permissionInput.attr("checked", true);
						}
						var permissionLabel = $("<label></label>");
						permissionLabel.attr("class", "form-check-label").append(permissionInput).append(permission.description);
						var permissionHTML = $("<div></div>");
						permissionHTML.attr("class", "col-lg-2  col-md-3 col-sm-6").append(permissionLabel);
						rowHTML.append(permissionHTML);
					}
					// 至此ROW已经完善了，开始组建card-body部分
					var containerHTML = $("<div></div>").attr("class", "container").append(rowHTML);
					var cardBodyHTML = $("<div></div>").attr("class", "card-body").append(containerHTML);
					var contentHTML = $("<div></div>").attr("id", permissionType.ptid).attr("class", "collapse in").attr("role", "tabpanel").append(cardBodyHTML);
					// 最后将card-body也组装到card之上，至此包含有一个permissionType信息的Card创建完成
					cardHTML.append(contentHTML);
					// 最后的最后，将创建完成的card添加到id=permission-modal-body的div下即可完成一个有关permissionType下拉的创建工作
					$("#permission-modal-body").append(cardHTML);
				}
				;
				$("#permissionModal").modal("show");
			});
		},
	},
};

/**
 *========================管理者设置========================
 */
var managerModal = {
	init : {},
	data : {},
	op : {

		/*
		 * 卸任 
		 */
		disappoint : function(uid) {
			var answer = confirm("即将解除该同志的任命，是否继续？");
			if (answer) {
				// 确定卸任
				var data = {
					uid : uid
				};

				$.post("userAction_doDisappoint.action", data, function(data, textStatus, req) {
					alert(data.message);
					window.location.reload();
				});
			} else {
				// 什么也不做
			}
		},

		/*
		 * 向后端发起正式的任命操作
		 * uid： 待委任的用户uid
		 * level: 待委任的层级对象的所属层级
		 * lid： 层级对象的id
		 */
		appoint : function(uid, level, lid) {

			var data = {
				uid : uid,
				level : level,
				lid : lid
			};

			$.post("userAction_doAppoint.action", data, function(data, textStatus, req) {
				if (data.result) {
					alert(data.message);
					window.location.reload();
				} else {
					alert(data.message)
				}
			});

		},

		/*
		 * 根据操作者在AppointModal中的选择情况（由id=appoint-1~4的Select的onchange事件触发本方法）
		 * 然后更新下一级的以及后续层级（直到fourthLevel）中的显示内容（option选项） 
		 * level: INT类型  触发onchange事件的select对应的层级（也就是其id=appointX中的X的数字）
		 * lid： 为操作者选中的层级对象的id
		 * lowest: 标记当前被任命的管理对象的对应层级
		 */
		changeAppointSelect : function(level, lid, lowest, uid) {
			/*
			 *  判断是不是目标（与被任命对象的层级一致的）select被onchange了
			 *  如果是进一步判断其所选中的option的value是不是！=0 如果的确！=0
			 *  那就说明可以提交“任命”
			 */
			if (level == lowest) {
				var appointLevelID = $("#appoint" + level).val();
				if ('0' == appointLevelID) {
					return;
				}
				// 变更“提交按钮”的方法绑定，并且回复其disabled状态（可点击/可提交）
				$("#button4appoint").attr("disabled", false).unbind().bind("click", function() {
					managerModal.op.appoint(uid, level, appointLevelID);
				});
			}

			var data = {
				level : level,
				lid : lid
			};

			$.post("userAction_getAppointSelectInfo.action", data, function(data, textStatus, req) {
				if (data.result) {
					switch (level + 1) {
					case 0:
						// 清空-1层级以下的所有select中的内容，并重置初始option，然后设置disabled属性
						for (var i = 0; i < 5; i++) {
							$("#appoint" + i).attr("disabled", "disabled").empty().append($("<option value='0' selected>--请选择--</option>"));

						}
						// 重建option
						$("#appoint0").attr("disabled", false);
						var option = null;
						for (var i = 0; i < data.minusFirst.children4Ajax.length; i++) {
							var zero = data.minusFirst.children4Ajax[i];
							option = $("<option></option>");
							option.val(zero.zid);
							option.text(zero.name);
							$("#appoint0").append(option);
						}
						break;
					case 1:
						// 清0层级以下的所有select中的内容，并重置初始option，然后设置disabled属性
						for (var i = 1; i < 5; i++) {
							$("#appoint" + i).attr("disabled", "disabled").empty().append($("<option value='0' selected>--请选择--</option>"));

						}
						// 重建option
						$("#appoint1").attr("disabled", false);
						var option = null;
						for (var i = 0; i < data.zero.children4Ajax.length; i++) {
							var first = data.zero.children4Ajax[i];
							option = $("<option></option>");
							option.val(first.flid);
							option.text(first.name);
							$("#appoint1").append(option);
						}
						break;
					case 2:
						// 清空1层级以下的所有select中的内容，并重置初始option，然后设置disabled属性
						for (var i = 2; i < 5; i++) {
							$("#appoint" + i).attr("disabled", "disabled").empty().append($("<option value='0' selected>--请选择--</option>"));

						}
						// 重建option
						$("#appoint2").attr("disabled", false);
						var option = null;
						for (var i = 0; i < data.first.children4Ajax.length; i++) {
							var second = data.first.children4Ajax[i];
							option = $("<option></option>");
							option.val(second.scid);
							option.text(second.name);
							$("#appoint2").append(option);
						}
						break;
					case 3:
						// 清空2层级以下的所有select中的内容，并重置初始option，然后设置disabled属性
						for (var i = 3; i < 5; i++) {
							$("#appoint" + i).attr("disabled", "disabled").empty().append($("<option value='0' selected>--请选择--</option>"));

						}
						// 重建option
						$("#appoint3").attr("disabled", false);
						var option = null;
						for (var i = 0; i < data.second.children4Ajax.length; i++) {
							var third = data.second.children4Ajax[i];
							option = $("<option></option>");
							option.val(third.thid);
							option.text(third.name);
							$("#appoint3").append(option);
						}
						break;
					case 4:
						// 清空3层级以下的所有select中的内容，并重置初始option，然后设置disabled属性
						for (var i = 4; i < 5; i++) {
							$("#appoint" + i).attr("disabled", "disabled").empty().append($("<option value='0' selected>--请选择--</option>"));

						}
						// 重建option
						$("#appoint4").attr("disabled", false);
						var option = null;
						for (var i = 0; i < data.third.children4Ajax.length; i++) {
							var fourth = data.third.children4Ajax[i];
							option = $("<option></option>");
							option.val(fourth.foid);
							option.text(fourth.name);
							$("#appoint4").append(option);
						}
						break;
					}
				} else {
					alert(data.message);
				}
			});
		},

		/*
		 * 显示任命Modal——————人员与层级对象绑定
		 * uid： 被任命的管理者的uid
		 */
		showAppointModal : function(uid) {
			// 先给“提交”按钮绑定上方法
			$("#button4appoint").attr("disabled", "disabled");
			// 然后，开始通过Ajax从后端获取必要的层级对象的信息，用来组织MODAL的页面显示
			var data = {
				uid : uid, // 需要被任命的manager 的uid
			};

			$.post("userAction_getAppointInfo.action", data, function(data, textStatus, req) {
				if (data.result) {
					// 先隐藏全部row
					$("#appoint div.row").hide();

					// 开始分析JSON数据然后组织Modal页面呈现
					var controller = data.controller;
					if (10086 == controller) {
						// ADMIN
						var lowest = data.lowest;

						for (let i = lowest; i >= -1; i--) {
							if (-1 == i) {
								// 创建MinusFirstLevel的Select
								$("#appoint-1").empty();
								var option = $("<option value='0' selected>--请选择--</option>");
								$("#appoint-1").append(option);
								for (var j = 0; j < data.minusLevels.length; j++) {
									var minusLevel = data.minusLevels[j];
									option = $("<option></option>");
									option.attr("value", minusLevel.mflid);
									option.text(minusLevel.name);
									$("#appoint-1").append(option);
								}
								// 显示该Select所在的row
								$("#appoint-1").parent().parent().show();
								// 设置onchange事件驱动
								$("#appoint-1").parent().parent().unbind().bind("change", function() {
									managerModal.op.changeAppointSelect(-1, $("#appoint-1").val(), data.lowest, uid);
								});
							} else {
								// 其他Select默认只有“--请选择--”
								$("#appoint" + i).empty().append($("<option value='0' selected>--请选择--</option>"));
								// 显示该Select所在的row
								$("#appoint" + i).attr("disabled", "disabled").parent().parent().show();
								// 设置onchange事件驱动
								$("#appoint" + i).parent().parent().unbind().bind("change", function() {
									/**
									 * ★★★★★
									 * 这里有个大学问，因为我们要向方法中传递参数，而参数我们只希望传递当前时刻的数值
									 * 由于这里传递进去的是变量值，如果变量 I 是全局变量（使用var关键字声明），则会以
									 * 类似“址传递”的方式传递进去，则变量会随着I的实时状态而改变，这不是我们想看到的。
									 * 如果想以“值传递”的方式传参，则需要变量I为局部变量，因此在for循环头部中使用let
									 * 关键字来定义变量I。
									 */
									managerModal.op.changeAppointSelect(i, $("#appoint" + i).val(), data.lowest, uid);
								});
							}
						}
					} else {
						// 非ADMIN
						// TODO shiro重启后再编写
					}
					$("#appoint").modal("show");
				} else {
					alert(data.message);
				}
			});

		},


		/*
		 * 当点击某个管理者的"绑定层级对象"的时候，会跳转到特定的Level的页面，用来显示该层级对象的详细信息。
		 */
		jump2LevelPage : function(tag, lid) {
			switch (tag) {
			case "minus_first":
				$(location).attr('href', 'minusFirstLevelAction_getLevelInfo.action?mflid=' + lid);
				break;
			case "zero":
				$(location).attr('href', 'zeroLevelAction_getLevelInfo.action?zid=' + lid);
				break;
			case "first":
				$(location).attr('href', 'firstLevelAction_getLevelInfo.action?flid=' + lid);
				break;
			case "second":
				$(location).attr('href', 'secondLevelAction_getLevelInfo.action?scid=' + lid);
				break;
			case "third":
				$(location).attr('href', 'thirdLevelAction_getLevelInfo.action?thid=' + lid);
				break;
			case "fourth":
				$(location).attr('href', 'fourthLevelAction_getLevelInfo.action?foid=' + lid);
				break;

			}
		},
	}
};

/**
 *========================用户User========================
 */
var userModal = {
	init : {

	},

	data : {

	},

	op : {
		/*
		 * 批量生成用户二维码
		 */
		batchCreateQR : function() {
			$.post("userAction_batchCreateQR.action", null, function(data, textStatus, req) {
				if (data.result) {
					alert(data.message);
					window.location.href("userAction_getUserList.action");
				} else {
					alert("二维码生成失败");
				}
			});
		},

		/*
		 * 更新用户数据信息
		 */
		updateUser : function(uid) {
			var data = {
				uid : uid,
				username : $("#username4update").val(),
				sickname : $("#sickname4update").val(),
				cardid : $("#cardid4update").val(),
				age : $("#age4update").val(),
				phone : $("#phone4update").val(),
				email : $("#email4update").val(),
				address : $("#address4update").val(),
				sex : $('#sex4update').val(),
				tag : $('#tag4update').val(),
			};

			$.post("userAction_update.action", data, function(data, textStatus, req) {
				// 使用这种引导当前页面定位的方式，可以即时性地刷新页面，在列表中显现修改后的结果。
				//						$(location).attr('href', 'userAction_getUserList.action');
				window.location.reload();
			});
		},

		/*
		 * 显示更新用户的Modal
		 */
		showUpdateUserModal : function(uid) {

			$('#updateUserButton').unbind().bind('click', function() {
				userModal.op.updateUser(uid);
			});

			var data = {
				uid : uid,
			};

			$.post("userAction_getUserInfo.action", data, function(data, textStatus, req) {

				$("#updateUserModalTitle").text("修改用户——" + data.username);
				$("#username4update").val(data.username);
				$("#sickname4update").val(data.sickname);
				$("#cardid4update").val(data.cardid);
				$("#age4update").val(data.age);
				$("#phone4update").val(data.phone);
				$("#email4update").val(data.email);
				$("#address4update").val(data.address);

				if ('男' == data.sex) {
					$("#sex4update").find("option[value = '1']").attr("selected", "selected");
				} else {
					$("#sex4update").find("option[value = '2']").attr("selected", "selected");
				}

				// 根据从后端发来的tags字段，处理设置用户分组的select中的显示内容
				// 清空select
				$('#tag4update').empty();
				// 根据被操作对象是否已经被委任 data.manager==null? 来确定select是否可以被设置
				if(null!=data.manager4Ajax){
					// 已被委任，不能选动select
					let op  =  $("<option></option>");
					switch (data.grouping.tag) {
					case "minus_first":
						op.attr("value", data.grouping.tag).text("街道管理者");
						break;
					case "zero":
						op.attr("value", data.grouping.tag).text("社区管理者");
						break;
					case "first":
						op.attr("value", data.grouping.tag).text("第一层级管理者");
						break;
					case "second":
						op.attr("value", data.grouping.tag).text("第二层级管理者");
						break;
					case "third":
						op.attr("value", data.grouping.tag).text("第三层级管理者");
						break;
					case "fourth":
						op.attr("value", data.grouping.tag).text("第四层级管理者");
						break;
					}
					$('#tag4update').append(op).find("option[value='" + data.grouping.tag + "']").attr("selected", "selected");
					$("#tag4update").attr("disabled", true);
				}else{
					// 没有被委任，则可以选动select
					// 分析tags中所包含的tag名称，并重新组建option
					for (var i = 0; i < data.tags.length; i++) {
						let op = $("<option></option>");
						switch (data.tags[i]) {
						case "minus_first":
							op.attr("value", data.tags[i]).text("街道管理者");
							break;
						case "zero":
							op.attr("value", data.tags[i]).text("社区管理者");
							break;
						case "first":
							op.attr("value", data.tags[i]).text("第一层级管理者");
							break;
						case "second":
							op.attr("value", data.tags[i]).text("第二层级管理者");
							break;
						case "third":
							op.attr("value", data.tags[i]).text("第三层级管理者");
							break;
						case "fourth":
							op.attr("value", data.tags[i]).text("第四层级管理者");
							break;
						case "unreal":
							op.attr("value", data.tags[i]).text("未实名认证");
							break;
						case "common":
							op.attr("value", data.tags[i]).text("普通认证用户");
							break;
						}
						$('#tag4update').append(op);
						$("#tag4update").attr("disabled", false);
					}
					$('#tag4update').find("option[value='" + data.grouping.tag + "']").attr("selected", "selected");
				}

				$("#updateUserModal").modal('show');
			});
			$("#updateUserModal").modal('show');
			return false;
		},

		createUser : function() {
			var data = {
				username : $("#username").val(),
				sickname : $("#sickname").val(),
				cardid : $("#cardid").val(),
				address : $("#address").val(),
				email : $("#email").val(),
				phone : $("#phone").val(),
				age : $("#age").val(),
				sex : $("#sex").val(),
			};

			$.post("userAction_create.action", data, function(data, textStatus, req) {
				$("#newUserModal").modal("hide");
				$("#username").val(""),
				$("#sickname").val(""),
				$("#cardid").val(""),
				$("#address").val(""),
				$("#email").val(""),
				$("#phone").val(""),
				$("#age").val(""),
				$("#sex").val(""),
				alert(data.message);
				// 使用这种引导当前页面定位的方式，可以即时性地刷新页面，在列表中显现修改后的结果。
				//						$(location).attr('href', 'userAction_getUserList.action');
				window.location.reload();
			});
		},

		userInfo : function(uid) {
			var data = {
				uid : uid,
			};

			$.post("userAction_getUserInfo.action", data, function(data, textStatus, req) {
				$("#detialsModal_title").text(data.username + "的详细信息");
				$("#detialsModal_qrcode").attr("src", data.qrcode);
				$("#detialsModal_username").text(data.username);
				$("#detialsModal_uid").text(data.uid);
				$("#detialsModal_openid").text(data.openid);
				$("#detialsModal_registrationTime").text(data.registrationTimeStr);
				if (null == data.member4Ajax) {
					$("#detialsModal_minusFirst").text("无");
					$("#detialsModal_zero").text("无");
					$("#detialsModal_first").text("无");
					$("#detialsModal_second").text("无");
					$("#detialsModal_third").text("无");
					$("#detialsModal_fourth").text("无");
				} else {
					if (null == data.member4Ajax.minusFirstLevel) {
						$("#detialsModal_minusFirst").text("无");
					} else {
						$("#detialsModal_minusFirst").text(data.member4Ajax.minusFirstLevel.name);
					}
					if (null == data.member4Ajax.zeroLevel) {
						$("#detialsModal_zero").text("无");
					} else {
						$("#detialsModal_zero").text(data.member4Ajax.zeroLevel.name);
					}
					if (null == data.member4Ajax.firstLevel) {
						$("#detialsModal_first").text("无");
					} else {
						$("#detialsModal_first").text(data.member4Ajax.firstLevel.name);
					}
					if (null == data.member4Ajax.secondLevel) {
						$("#detialsModal_second").text("无");
					} else {
						$("#detialsModal_second").text(data.member4Ajax.secondLevel.name);
					}
					if (null == data.member4Ajax.thirdLevel) {
						$("#detialsModal_third").text("无");
					} else {
						$("#detialsModal_third").text(data.member4Ajax.thirdLevel.name);
					}
					if (null == data.member4Ajax.fourthLevel) {
						$("#detialsModal_fourth").text("无");
					} else {
						$("#detialsModal_fourth").text(data.member4Ajax.fourthLevel.name);
					}
				}
				$("#detialsModal").modal('show');
			});
			return false;
		},
	} // ======== op is over ========
};


/**
 *========================用户User========================
 */
var overAll = {
	init : {
		op : {
			/*
			 * 通过jQuery实现的初始化操作，作用是将当前页面上的所有tooltip效果激活
			 */
			startToolTip : function() {
				$('[data-toggle="tooltip"]').tooltip();
			},
			/*
			 * 默认执行语句，如果当前运行应用程序的设备屏幕宽度是大屏幕，则显示左侧工具栏。
			 * 否则小平面不现实左侧工具栏。
			 */
			screenIsBigOrSmall : function() {
				if (window.screen.availWidth >= 768) {
					$('#contentId').collapse({
						toggle : true
					});
				}
				;
				console.log("屏幕像素分辨完成");
			},
			/*
			 * 页面被加载的时候通过localStorage确定左侧边栏应该打开的是哪个content
			 */
			prepareCollapse : function() {
				let cid = localStorage.getItem("contentID");
				if (null === cid) {
					$("#userContent").collapse('show');
				} else {
					$("#" + cid).collapse('show');
				}
			},
		},

		data : {

		},
	},

	data : {

	},

	op : {
		/*
		 *当用户点击左侧Menu侧边栏上的连接的时候会调用本方法 
		 *作用：
		 *（1）通过localStorage保存所点击的连接所属的Collapse分页的xxxxContent的ID
		 *（2）执行页面跳转操作
		 */
		saveCollapseContentID2LS : function(contentID, uri) {

			localStorage.setItem("contentID", contentID);
			$(location).attr('href', uri);
		},
	},
};





/**
 * 通过Jquery调用通用方法中的初始化方法完成一些页面初始化工作
 */
$(function() {
	overAll.init.op.startToolTip();
	overAll.init.op.screenIsBigOrSmall();
	overAll.init.op.prepareCollapse();
});