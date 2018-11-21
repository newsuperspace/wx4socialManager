/**
 * ========================managerLevelList.jsp页面逻辑功能========================
 */
var managedLevelList = {
	init : {},
	data : {},
	op : {
		/**
		 * 解除任命
		 * level:  被解除任命的人所解除的层级对象的tag（minus_first、zero、first、second、third、fourth）
		 * lid: 被解除任命的人所接触的层级对象的lid
		 * 
		 */
		disappoint : function(managerid) {
			var answer = confirm("是否真的要解除当用户对该层级的任命？");
			if (answer) {
				// 解除任命
				var  param  = {
					"managerid": managerid	
				}
				
				$.post("userAction_doDisappoint.action", param, function(data, textStatus, req) {
					if(data.result){
						alert(data.message);
						window.location.reload();
					}else{
						alert(data.message);
					}
				});
			} else {
				// 什么也不做
			}
		},
	
	
	}
}


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

		/**
		 * 根据某个用户的memberid
		 * 跳转到其所管理的所有直接子层级列表页面
		 * managedLevelList.jsp
		 */
		toManagedLevelList: function(memberid){
			let url = "managerAction_toManagedLevelList.action";
			$(location).attr("href", url+"?memberid="+memberid);
		},
		
		/**
		 * 根据在minus_first.jsp/zero.jsp/first.jsp/second.jsp/third.jsp/fourth.jsp页面上点击“管理者”
		 * 则会将被点击的层级的levelLid和tag传递过来，用于跳转到managersOfLevel.jsp页面上用于展示该层级
		 * 的所有管理者，并且在页面上可以执行卸任某个特定用户的操作。
		 */
		toManagersOfLevel: function(levelLid,tag){
			let url = "managerAction_toManagersOfLevel.action"+"?levelLid="+levelLid+"&tag="+tag;
			$(location).attr("href", url);
		},
		
		/*
		 * 判断指派人员的modal是否可以提交了（是否在select中选择了正确的option，value非0）
		 */
//		showAssignedUserModalCanBeCommit : function(uid, level) {
//
//			var $select = $("#userAssigned" + level)
//
//			if ($select.val() == 0) {
//				// 用户没有选中正确的option，不予提交
//				// 设置按钮为不可用状态，并直接返回
//				$("#button4UserAssigned").attr("disabled", true);
//				return;
//			}
//			// 用户选择了 某个层级对象的option，可以执行指派操作了
//			$("#button4UserAssigned").unbind().bind("click", function() {
//				managerModal.op.assignedUser(uid, level, $select.val());
//			}).attr("disabled", false);
//		},


		/*
		 * 分配用户到子层级的“中间层&直属层”中
		 * uid： 被分配的人员的uid
		 */
//		showAssignedUserModal : function(uid) {
//
//			// 先要判断被操作用户的是否被委任了职责，其tag是否为common或unreal
//			var data = {
//				uid : uid
//			}
//
//			// 需要先等待下面的post请求从服务器获得返回的结果并判断后才能决定是否执行下面弹出Modal的操作，因此需要手动设置Ajax为同步执行（默认是异步执行）
//			$.ajaxSetup({
//				async : false // 全局设置Ajax为同步执行
//			});
//			var weContinue = true;
//			$.post("userAction_getUserInfo.action", data, function(data, textStatus, req) {
//				if (data.managers!= null && data.managers.size()>0) {
//					alert("该用户当前正在任职，请先将其卸任");
//					weContinue = false;
//				}
//				if (data.member.grouping.tag != "common" && data.member.grouping.tag != "unreal") {
//					alert("该用户当前的分组为管理者分组,请修改后再试");
//					weContinue = false;
//				}
//			});
//			// 重新设置Ajax为异步执行
//			$.ajaxSetup({
//				async : true
//			});
//			// 判断是否继续
//			if (!weContinue) {
//				return;
//			}
			/*
			 * 通过Ajax可以从后端返回当前操作者的层级对象，
			 * 从中我们可以通过 data.grouping.tag 分析出操作者的层级分组,进而得知在userAssignedModal中应该显示的是那个select
			 * 然后从 data.children4Ajax 得到可以指派人员的直接子层级数据，用来组织select中的option
			 *  
			 */
//			$.post("userAction_showUserAssignedModal.action", null, function(data, textStatus, req) {
//				// 先隐藏userAssigned的Modal中的所有select
//				$("#userAssignedModal div.row").hide();
//				// 将提交按钮预制成“不可用状态”
//				$("#button4UserAssigned").attr("disabled", true)
//
//				let level = data.level;
//				switch (level) {
//				case -1:
//					// 操作者是街道层级，将直属人员分配到社区
//					var $select = $("#userAssigned0").empty();
//					console.log($select.attr("id"));
//					var $option = $("<option value='0' selected>--请选择--</option>");
//					$select.append($option);
//					$select.unbind().bind("change", function() {
//						managerModal.op.showAssignedUserModalCanBeCommit(uid, 0)
//					});
//					for (let i = 0; i < data.allChildren4Ajax.length; i++) {
//						$option = $("<option></option>");
//						$option.attr("value", data.allChildren4Ajax[i].zid);
//						$option.text(data.allChildren4Ajax[i].name);
//						$select.append($option);
//					}
//					break;
//				case 0:
//					// 操作者是社区层级，将直属人员分配到第一级
//					var $select = $("#userAssigned1").empty();
//					var $option = $("<option value='0' selected>--请选择--</option>");
//					$select.append($option);
//					$select.unbind().bind("change", function() {
//						managerModal.op.showAssignedUserModalCanBeCommit(uid, 1);
//					});
//					for (let i = 0; i < data.allChildren4Ajax.length; i++) {
//						var $option = $("<option></option>");
//						$option.attr("value", data.allChildren4Ajax[i].flid);
//						$option.text(data.allChildren4Ajax[i].name);
//						$select.append($option);
//					}
//					break;
//				case 1:
//					// 操作者是第一层级，将直属人员分配到第二级
//					var $select = $("#userAssigned2").empty();
//					var $option = $("<option value='0' selected>--请选择--</option>");
//					$select.append($option);
//					$select.unbind().bind("change", function() {
//						managerModal.op.showAssignedUserModalCanBeCommit(uid, 2);
//					});
//					for (let i = 0; i < data.allChildren4Ajax.length; i++) {
//						var $option = $("<option></option>");
//						$option.attr("value", data.allChildren4Ajax[i].scid);
//						$option.text(data.allChildren4Ajax[i].name);
//						$select.append($option);
//					}
//					break;
//				case 2:
//					// 操作者是第二层层级，将直属人员分配到第三级
//					var $select = $("#userAssigned3").empty();
//					var $option = $("<option value='0' selected>--请选择--</option>");
//					$select.append($option);
//					$select.unbind().bind("change", function() {
//						managerModal.op.showAssignedUserModalCanBeCommit(uid, 3);
//					});
//					for (let i = 0; i < data.allChildren4Ajax.length; i++) {
//						var $option = $("<option></option>");
//						$option.attr("value", data.allChildren4Ajax[i].thid);
//						$option.text(data.allChildren4Ajax[i].name);
//						$select.append($option);
//					}
//					break;
//				case 3:
//					// 操作者是第三层级，将直属人员分配到第四级
//					var $select = $("#userAssigned4").empty();
//					var $option = $("<option value='0' selected>--请选择--</option>");
//					$select.append($option);
//					$select.unbind().bind("change", function() {
//						managerModal.op.showAssignedUserModalCanBeCommit(uid, 4);
//					});
//					for (let i = 0; i < data.allChildren4Ajax.length; i++) {
//						var $option = $("<option></option>");
//						$option.attr("value", data.allChildren4Ajax[i].foid);
//						$option.text(data.allChildren4Ajax[i].name);
//						$select.append($option);
//					}
//					break;
//				default:
//					// admin操作者会将用户分派到街道层级（-1）
//					var $select = $("#userAssigned-1").empty();
//					var $option = $("<option value='0' selected>--请选择--</option>");
//					$select.append($option);
//					$select.unbind().bind("change", function() {
//						managerModal.op.showAssignedUserModalCanBeCommit(uid, -1);
//					});
//					for (let i = 0; i < data.length; i++) {
//						$option = $("<option></option>");
//						$option.attr("value", data[i].mflid);
//						$option.text(data[i].name);
//						$select.append($option);
//					}
//					break;
//				}
//				$select.parent().parent().show();
//				$("#userAssignedModal").modal("show");
//			});
//		},
//
//		/*
//		 * 执行分配操作
//		 * uid : 待分配人的uid
//		 * level: 被分配到层级对象的层级数（-1，0，1，2，3，4）
//		 * lid: 被分配到的层级对象的lid
//		 */
//		assignedUser : function(uid, level, lid) {
//
//			var data = {
//				uid : uid,
//				level : level,
//				lid : lid
//			}
//
//			$.post("userAction_assignedUser.action", data, function(data, textStatus, req) {
//				if (data.result) {
//					// 人员派遣成功
//					alert(data.message);
//					window.location.reload();
//				} else {
//					alert(data.message);
//					$("#userAssignedModal").modal("hide");
//				}
//			});
//
//		},

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
		 * uid： 被任命的管理者的uid
		 */
		changeAppointSelect : function(level, lid, lowest, uid) {
			/*
			 *  判断是不是目标（select呈现的可任命层级对象与被任命者的层级tag一致）select被onchange了
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
		},

		/*
		 * 显示任命Modal——————人员与层级对象绑定
		 * uid： 被任命的管理者的uid
		 */
		showAppointModal : function(uid) {
			// 先取消“提交”按钮的可用性
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
					var controllerNum = data.controllerNum;
					if (10086 == controllerNum) {
						// Admin用户
						// 待分配者的层级信息（-1/0/1/2/3/4），根据该层级可以知道应该显示哪个select以供选择任命的层级对象
						var lowest = data.lowest;
						// 先找到并清空用来显示可委任层级对象的select，以便重建option
						var $select = $("#appoint" + lowest).empty();
						// 先创建一个默认的option
						$select.append($("<option value='0' selected>--请选择--</option>"));
						// 开始遍历子层级数据，并创建对应的option
						for (let i = 0; i < data.minusLevels.length; i++) {
							var minusLevel = data.minusLevels[i];
							var option = $("<option></option>");
							option.attr("value", minusLevel.mflid);
							option.text(minusLevel.name);
							$select.append(option);
						}
						// 显示该Select所在的row
						$select.attr("disabled", false).parent().parent().show();
						// 设置onchange事件驱动
						$select.unbind().bind("change", function() {
							/**
							 * ★★★★★
							 * 这里有个大学问，因为我们要向方法中传递参数，而参数我们只希望传递当前时刻的数值
							 * 由于这里传递进去的是变量值，如果变量 I 是全局变量（使用var关键字声明），则会以
							 * 类似“址传递”的方式传递进去，则变量会随着I的实时状态而改变，这不是我们想看到的。
							 * 如果想以“值传递”的方式传参，则需要变量I为局部变量，因此在for循环头部中使用let
							 * 关键字来定义变量I。
							 */
							managerModal.op.changeAppointSelect(-1, $select.val(), data.lowest, uid);
						});
					} else {
						// 非Admin用户
						// // 待分配者的层级信息（-1/0/1/2/3/4），根据该层级可以知道应该显示哪个select以供选择任命的层级对象
						var lowest = data.lowest;
						// 先找到并清空用来显示可委任层级对象的select，以便重建option
						var $select = $("#appoint" + lowest).empty();
						// 先创建一个默认的option
						$select.append($("<option value='0' selected>--请选择--</option>"));
						// 分析操作者的层级
						var level = null;
						switch (controllerNum) {
						case -1:
							// 操作者是街道，则操作者的层级对象是data.minusFirst
							level = data.minusFirst;
							for (let i = 0; i < level.allChildren4Ajax.length; i++) {
								// 获取次层级zeroLevel对象
								var child = level.allChildren4Ajax[i];
								var option = $("<option></option>");
								option.attr("value", child.zid).text(child.name);
								$select.append(option);
							}
							break;
						case 0:
							// 操作者是社区，则操作者的层级对象是data.zero
							level = data.zero;
							for (let i = 0; i < level.allChildren4Ajax.length; i++) {
								// 获取次层级firstLevel对象
								var child = level.allChildren4Ajax[i];
								var option = $("<option></option>");
								option.attr("value", child.flid).text(child.name);
								$select.append(option);
							}
							break;
						case 1:
							// 操作者是第一级，则操作者的层级对象是data.first
							level = data.first;
							for (let i = 0; i < level.allChildren4Ajax.length; i++) {
								// 获取次层级secondLevel对象
								var child = level.allChildren4Ajax[i];
								var option = $("<option></option>");
								option.attr("value", child.scid).text(child.name);
								$select.append(option);
							}
							break;
						case 2:
							// 操作者是第二级，则操作者的层级对象是data.second
							level = data.second;
							for (let i = 0; i < level.allChildren4Ajax.length; i++) {
								// 获取次层级thirdLevel对象
								var child = level.allChildren4Ajax[i];
								var option = $("<option></option>");
								option.attr("value", child.thid).text(child.name);
								$select.append(option);
							}
							break;
						case 3:
							// 操作者是第三级，则操作者的层级对象是data.third
							level = data.third;
							for (let i = 0; i < level.allChildren4Ajax.length; i++) {
								// 获取次层级fourthLevel对象
								var child = level.allChildren4Ajax[i];
								var option = $("<option></option>");
								option.attr("value", child.foid).text(child.name);
								$select.append(option);
							}
							break;
						}
						// 显示该Select所在的row
						$select.attr("disabled", false).parent().parent().show();
						// 设置onchange事件驱动
						$select.unbind().bind("change", function() {
							/**
							 * ★★★★★
							 * 这里有个大学问，因为我们要向方法中传递参数，而参数我们只希望传递当前时刻的数值
							 * 由于这里传递进去的是变量值，如果变量 I 是全局变量（使用var关键字声明），则会以
							 * 类似“址传递”的方式传递进去，则变量会随着I的实时状态而改变，这不是我们想看到的。
							 * 如果想以“值传递”的方式传参，则需要变量I为局部变量，因此在for循环头部中使用let
							 * 关键字来定义变量I。
							 */
							managerModal.op.changeAppointSelect(data.lowest, $select.val(), data.lowest, uid);
						});
					}
					$("#appoint").modal("show");
				} else {
					alert(data.message);
				}
			});

		},

		/*
		 * 当点击某个管理者的"绑定层级对象"的时候，会跳转到managedLevelList.jsp的页面，用来展示该管理者所管理的所有层级
		 */
		jump2LevelPage : function(tag) {
			let lid = $("#levelID").text();
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
				if (null != data.managers && 0 != data.managers.length) {
					// 已被委任，不能选动select
					let op = $("<option></option>");
					switch (data.managers[0].member.grouping.tag) {
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
				} else {
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
					$('#tag4update').find("option[value='" + data.member.grouping.tag + "']").attr("selected", "selected");
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
//				if (null == data.member4Ajax) {
//					$("#detialsModal_minusFirst").text("无");
//					$("#detialsModal_zero").text("无");
//					$("#detialsModal_first").text("无");
//					$("#detialsModal_second").text("无");
//					$("#detialsModal_third").text("无");
//					$("#detialsModal_fourth").text("无");
//				} else {
//					if (null == data.member4Ajax.minusFirstLevel) {
//						$("#detialsModal_minusFirst").text("无");
//					} else {
//						$("#detialsModal_minusFirst").text(data.member4Ajax.minusFirstLevel.name);
//					}
//					if (null == data.member4Ajax.zeroLevel) {
//						$("#detialsModal_zero").text("无");
//					} else {
//						$("#detialsModal_zero").text(data.member4Ajax.zeroLevel.name);
//					}
//					if (null == data.member4Ajax.firstLevel) {
//						$("#detialsModal_first").text("无");
//					} else {
//						$("#detialsModal_first").text(data.member4Ajax.firstLevel.name);
//					}
//					if (null == data.member4Ajax.secondLevel) {
//						$("#detialsModal_second").text("无");
//					} else {
//						$("#detialsModal_second").text(data.member4Ajax.secondLevel.name);
//					}
//					if (null == data.member4Ajax.thirdLevel) {
//						$("#detialsModal_third").text("无");
//					} else {
//						$("#detialsModal_third").text(data.member4Ajax.thirdLevel.name);
//					}
//					if (null == data.member4Ajax.fourthLevel) {
//						$("#detialsModal_fourth").text("无");
//					} else {
//						$("#detialsModal_fourth").text(data.member4Ajax.fourthLevel.name);
//					}
//				}
				$("#detialsModal").modal('show');
			});
			return false;
		},
	} // ======== op is over ========
};


/**
 *========================可能用到的全局配置都在这里========================
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
					overAll.init.data.isWeixin = false;
				} else {
					overAll.init.data.isWeixin = true;
				}
				console.log("当前屏幕像素分辨为" + window.screen.availWidth + ",功能菜单样式设置完成");
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
			isWeixin : false,
		},
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



var projectModal = {
	init : {},

	data : {},

	op : {
		/*
		 * 点击项目名称————弹出projectInfoModal，显示项目的详细信息的Modal。
		 */
		projectInfo : function(dpid) {
			alert(dpid);
		},

		/*
		 * 点击已开展活动的数字，可以跳转到Activity页面，显示当前项目所开展过的活动信息历史纪录表————activityList.jsp
		 */
		getActivities : function(dpid) {
			var url = "activityAction_showDoingProjectActivityList.action?dpid=" + dpid;
			$(location).attr("href", url);
		},
		/*
		 * projectList.jsp页面上点击某个项目的"新活动"按钮时调用本方法
		 * dpid： 是在当初请求projectList.jsp的时候，由服务器端的struts2通过OGNL表达式将doingProject的dpid填入进来的
		 */
		createActivity : function(dpid) {
			var url = "activityAction_createPage.action?dpid=" + dpid;
			$(location).attr("href", url);
		},
	}
};


// ===========================最上边工具条有关的业务========================
var navbarModal = {
	data : {},
	init : {},
	op : {
		/*
		 * 向后端请求获取当前操作者的层级信息
		 * 层级QRCODE
		 * 层级名
		 * 层级描述
		 * 层级所有者
		 */
		preMyselfLevelInfo : function() {
			var url = "userAction_preMyselfLevelInfo.action";

			$.post(url, null, function(data, textStatus, req) {
				if (data.result) {
					var levelName = data.levelName;
					var levelDescription = data.levelDescription;
					var qrcode = data.qrcode;
					var levelManager = data.levelManager;
					$("#levelQrcode").attr("src", qrcode);
					$("#levelName").text(levelName);
					$("#levelDescription").text(levelDescription);
					$("#levelManager").text(levelManager);
					$("#levelInfoModal").modal("show");
				} else {
					alert(data.message);
				}
			});
		}
	}
};



/*
 * geoList.jsp页面上，与位置坐标有关的功能
 */
var geoModal = {
	init : {},
	data : {},
	op : {
		/**
		 * 显示新建位置坐标的Modal对话框前的准备工作
		 */
		showCreateModal : function() {
			$("#name").val("");
			$("#description").val("");
			$("#longitude").val("");
			$("#latitude").val("");
			$("#createModal").modal('show');
		},
		/**
		 * AJAX 
		 * 新建坐标
		 */
		createGeo : function() {
			let param = {
				name : $("#name").val(),
				description : $("#description").val(),
				longitude : $("#longitude").val(),
				latitude : $("#latitude").val()
			}
			let url = "geographicAction_createGeo.action";
			$.post(url, param, function(data, textStatus, req) {
				$("#createModal").modal('hide');
				weui.alert(data.message, {
					title : '处理结果',
					buttons : [ {
						label : '确认',
						type : 'primary',
						onClick : function() {
							window.location.reload();
						}
					} ]
				});
			});
		},
		/**
		 * AJAX
		 * 显示更新坐标数据信息的MODAL前的准备工作(数据回显)
		 */
		showUpdateModal : function(geoid) {

			let param = {
				geoid : geoid
			}
			let url = "geographicAction_showUpdateModal.action";
			$.post(url, param, function(data, textStatus, req) {
				if (data.result) {
					// result == true,表明根据前端所提供的hid，获取到了指定房屋的数据信息，可以准备Modal的数据显示了
					$("#geoid4update").val(geoid);
					$("#name4update").val(data.geographic.name);
					$("#description4update").val(data.geographic.description);
					$("#longitude4update").val(data.geographic.longitude);
					$("#latitude4update").val(data.geographic.latitude);
					$("#radus4update").val(data.geographic.radus);
					$("#updateModal").modal('show');
				} else {
					weui.alert("获取不到指定活动室的数据信息，请重试！");
				}
			});
		},
		/**
		 * AJAX
		 * 执行更新坐标数据信息的操作
		 */
		updateGeo : function() {

			let param = {
				geoid : $("#geoid4update").val(),
				name : $("#name4update").val(),
				description : $("#description4update").val(),
				longitude : $("#longitude4update").val(),
				latitude : $("#latitude4update").val(),
				radus : $("#radus4update").val()
			}
			let url = "geographicAction_updateGeo.action";
			$.post(url, param, function(data, textStatus, req) {
				$("#updateModal").modal('hide');

				weui.alert(data.message, {
					title : '处理结果',
					buttons : [ {
						label : '确认',
						type : 'primary',
						onClick : function() {
							window.location.reload();
						}
					} ]
				});
			});
		},
		/**
		 * AJAX
		 * 停用位置坐标
		 */
		closeGeo : function(geoid) {
			weui.confirm('活动地点停用后，在创建活动时将不可用', {
				title : '是否停用该活动地点？',
				buttons : [ {
					label : '不',
					type : 'default',
					onClick : function() {}
				}, {
					label : '是的',
					type : 'primary',
					onClick : function() {
						let param = {
							geoid : geoid
						}
						let url = "geographicAction_closeGeo.action";
						$.post(url, param, function(data, textStatus, req) {
							window.location.reload();
						});
					}
				} ]
			});
		},
		/**
		 * AJAX
		 * 启用位置坐标
		 */
		openGeo : function(geoid) {
			weui.confirm('活动地点启用后，在创建活动时可以使用', {
				title : '是否启用该活动地点？',
				buttons : [ {
					label : '不',
					type : 'default',
					onClick : function() {}
				}, {
					label : '是的',
					type : 'primary',
					onClick : function() {
						let param = {
							geoid : geoid
						}
						let url = "geographicAction_openGeo.action";
						$.post(url, param, function(data, textStatus, req) {
							window.location.reload();
						});
					}
				} ]
			});
		},
		/**
		 * AJAX
		 * 删除位置坐标
		 */
		deleteGeo : function(geoid) {
			weui.confirm('刪除该活动地点后将不可恢复', {
				title : '是否确认删除？',
				buttons : [ {
					label : '不',
					type : 'default',
					onClick : function() {}
				}, {
					label : '是的',
					type : 'primary',
					onClick : function() {
						let param = {
							geoid : geoid
						}
						let url = "geographicAction_deleteGeo.action";
						$.post(url, param, function(data, textStatus, req) {
							window.location.reload();
						});
					}
				} ]
			});
		},
		/**
		 * AJAX
		 * 获得位置坐标的详情信息到详情Modal上显示
		 */
		geoInfo : function(geoid) {
			weui.alert("您所点击的活动地点的ID是：" + geoid);
		}
	}
}


/**
 * houseList.jsp页面上与活动室有关的功能
 */
var houseModal = {
	init : {},
	data : {},
	op : {
		/**
		 * 【完成】
		 * 显示新建房屋窗口前的准备工作
		 */
		showCreateModal : function() {
			$("#name").val("");
			$("#description").val("");
			$("#createModal").modal('show');
		},
		/**
		 * 【完成】
		 * AJAX
		 * 新建活动室
		 */
		createHouse : function() {
			let param = {
				name : $("#name").val(),
				description : $("#description").val()
			}
			let url = "houseAction_createHouse.action";
			$.post(url, param, function(data, textStatus, req) {
				$("#createModal").modal('hide');
				weui.alert(data.message, {
					title : '处理结果',
					buttons : [ {
						label : '确认',
						type : 'primary',
						onClick : function() {
							window.location.reload();
						}
					} ]
				});
			});
		},

		/**
		 * 【完成】
		 * AJAX
		 * 显示更新房屋窗口前的准备工作
		 */
		showUpdateModal : function(hid) {
			let param = {
				hid : hid
			}
			let url = "houseAction_showUpdateModal.action";
			$.post(url, param, function(data, textStatus, req) {
				if (data.result) {
					// result == true,表明根据前端所提供的hid，获取到了指定房屋的数据信息，可以准备Modal的数据显示了
					$("#hid4update").val(hid);
					$("#name4update").val(data.house.name);
					$("#description4update").val(data.house.description);
					$("#longitude4update").val(data.house.longitude);
					$("#latitude4update").val(data.house.latitude);
					$("#radus4update").val(data.house.radus);
					$("#updateModal").modal('show');
				} else {
					weui.alert("获取不到指定活动室的数据信息，请重试！");
				}
			});
		},
		/**
		 * 【完成】
		 * AJAX
		 * 更新活动室数据
		 */
		updateHouse : function() {
			let param = {
				hid : $("#hid4update").val(),
				name : $("#name4update").val(),
				description : $("#description4update").val(),
				longitude : $("#longitude4update").val(),
				latitude : $("#latitude4update").val(),
				radus : $("#radus4update").val()
			}
			let url = "houseAction_updateHouse.action";
			$.post(url, param, function(data, textStatus, req) {
				$("#updateModal").modal('hide');

				weui.alert(data.message, {
					title : '处理结果',
					buttons : [ {
						label : '确认',
						type : 'primary',
						onClick : function() {
							window.location.reload();
						}
					} ]
				});
			});
		},

		/**
		 * 【完成】
		 * AJAX
		 * 关闭房屋
		 */
		closeHouse : function(hid) {
			weui.confirm('房屋停用后，在创建活动时将不可见本房屋', {
				title : '是否停用该房屋？',
				buttons : [ {
					label : '不',
					type : 'default',
					onClick : function() {}
				}, {
					label : '是的',
					type : 'primary',
					onClick : function() {
						let param = {
							hid : hid
						}
						let url = "houseAction_closeHouse.action";
						$.post(url, param, function(data, textStatus, req) {
							window.location.reload();
						});
					}
				} ]
			});
		},
		/**
		 * 【完成】
		 * AJAX
		 * 开放房屋
		 */
		openHouse : function(hid) {
			weui.confirm('房屋启用后，在创建活动时可以预约使用本房屋', {
				title : '是否启用该房屋？',
				buttons : [ {
					label : '不',
					type : 'default',
					onClick : function() {}
				}, {
					label : '是的',
					type : 'primary',
					onClick : function() {
						let param = {
							hid : hid
						}
						let url = "houseAction_openHouse.action";
						$.post(url, param, function(data, textStatus, req) {
							window.location.reload();
						});
					}
				} ]
			});
		},

		/**
		 * 【完成】
		 * AJAX
		 * 刪除房屋
		 */
		deleteHouse : function(hid) {
			weui.confirm('刪除该活动室后将不可恢复', {
				title : '是否确认删除？',
				buttons : [ {
					label : '不',
					type : 'default',
					onClick : function() {}
				}, {
					label : '是的',
					type : 'primary',
					onClick : function() {
						let param = {
							hid : hid
						}
						let url = "houseAction_deleteHouse.action";
						$.post(url, param, function(data, textStatus, req) {
							window.location.reload();
						});
					}
				} ]
			});
		},


		/**
		 * AJAX 
		 * 根据提供的hid从后端获取指定房屋的数据信息，
		 * 然后显示在InfoModal上
		 */
		houseInfo : function(hid) {
			weui.alert("您所点击的房屋的hid是：" + hid);
		},
	}
};



/**
 * 包含后台与微信端进行交互的所有逻辑
 */
var aboutWeixin = {
	init : {
		/**
		 * op中包含的是与微信端进行初始化操作的逻辑，有页面加载完成后通过jQuery的$(function() {})自动完成对下列方法的调用
		 */
		op : {
			/*
			 * 负责Weixin公众号端访问的页面的所有必要前提操作
			 * （1）微信网页认证授权
			 * （2）JS-SDK使用授权认证
			 */
			getOpenIdAndConfigJSSDK : function() {

				// 设置ajax请求成同步请求
				$.ajaxSetup({
					async : false // 全局设置Ajax为同步执行
				});

				// ===============================微信网页认证授权===========================
				// 由于当前页面是用来实名制认证的，因此通过OAthure2.0认证后会将用来获取access_token的票据code以请求参数的形式传递过来
				// 因此这里就需要预先获取到请求参数code的值，并且进行Ajax通讯让服务器从微信官方得到该用户真正的openID，并且保存到数据组件中备用
				var code = aboutWeixin.op.getAccess_Token("code");
				if (null != code && "" != code) {
					// 如果存在code请求参数，则可以开始准备进行Ajax通讯获取来访用户的OpenID
					var url = "ajaxAction4weixin_getOpenIdthroughCode.action";
					var param = {
						"code" : code,
					};
					$.post(url, param, function(data) {
						//						if (!jQuery.isEmptyObject(data)) {
						//							// 将当前操作的用户的openid保存到localStorage中
						//							localStorage.setItem("openid", data.openid);
						//						} else {
						//							console.log("以Code换取用户OpenID时出现异常（可能之前已经用该code换取过openID了）");
						//						}
						if (data.result) {
							localStorage.setItem("openid", data.openid);
							console.log(data.message);
						} else {
							console.log(data.message);
						}
					});
				}

				// =============================JS-SDK使用权限签名=============================
				// 因为当前页面需要在"实名制认证成功"后就让微信浏览器关闭此页面，因此需要使用微信提供的JS-SDK来调用微信的功能
				// 在正式调用JS-SDK中的api之前需要做到
				// （1）加载微信JS-SDK的模块【已通过在页面上使用<script></script>标签完成】
				// （2）加载JS-SDK模块后可以直接通过"wx"或"jWeixin"这个全局变量实现对微信web功能的API调用，但再此之前还需要config配置过程
				url = "ajaxAction4weixin_getJsapiSignature.action"
				// 得到当前需要调用JS-SDK的页面URL，但不包括"#"号及其后面的部分，因此需要通过JS原生的split()函数切割一下  ★
				var paramUrl = window.location.href.split("#")[0];
				console.log(paramUrl);
				param = {
					"url" : paramUrl
				};
				$.post(url, param, function(data) {

					// 当从哦们自己的服务器返回后，这个data中就包含这已经通过微信服务器认证的signature、timestamp、nonceStr、appId
					// 这四个关键认证信息了，之后就可以通过JS-SDK脚本模块中的config()方法来按照下列方式再次向微信服务器请求所要使用
					// 的微信JS-SDK功能后，就可以在当前页面接下来的脚本中通过wx.xxx() 实现JS-SDK功能的调用了。
					wx.config({
						debug : false,
						appId : data.appId,
						timestamp : data.timestamp,
						nonceStr : data.nonceStr,
						signature : data.signature,
						jsApiList : [ 'checkJsApi', 'onMenuShareTimeline', 'onMenuShareAppMessage', 'onMenuShareQQ', 'onMenuShareWeibo', 'onMenuShareQZone', 'hideMenuItems', 'showMenuItems',
							'hideAllNonBaseMenuItem', 'showAllNonBaseMenuItem', 'translateVoice', 'startRecord', 'stopRecord', 'onVoiceRecordEnd', 'playVoice', 'onVoicePlayEnd', 'pauseVoice',
							'stopVoice', 'uploadVoice', 'downloadVoice', 'chooseImage', 'previewImage', 'uploadImage', 'downloadImage', 'getNetworkType', 'openLocation', 'getLocation', 'hideOptionMenu',
							'showOptionMenu', 'closeWindow', 'scanQRCode', 'chooseWXPay', 'openProductSpecificView', 'addCard', 'chooseCard', 'openCard' ]
					});
					console.log("JS-SDK准备完毕");
				});

				// 记得要重新恢复异步请求
				$.ajaxSetup({
					async : true
				});

				// 由于通过ajax通讯，让哦们自己的服务器与微信服务器交互后获得JS-SDK认证的signature等信息的过程
				// 默认是异步执行的，也就是当发起ajax通讯后没有必要等待哦们自己服务器返回响应就可以继续执行$.post()之后的语句
				// 但当设置了同步ajax请求后，在$.post()之后的语句就必须等待ajax的回调函数执行结束后才能继续执行
				// 因此在执行下面的wx.ready()对JS-SDK进行初始化配置之前，已经预先执行了位于$.post()回调中的wx.config()的设置步骤
				// 因此可以放心的将wx.ready()代码放在ajax请求的后面，就是因为此次ajax请求（也急速$.post()）是一次同步操作。
				// 在微信web的JS-SDK的config配置执行完毕后，就可以通过下面的方法进行当前页面所需的JS-SDK功能的初始化配置。
				// 在ready()方法执行完毕前，如果你率先异步执行了wx中的其他API，则这些提前执行的API是不能发挥作用的，因此如果有初始化逻辑应该放在ready()中而不是在外部执行。⭐⭐⭐切记
				wx.ready(function() {

					// 这里包含了对当前页面上所使用的JS-SDK功能的初始化设置操作，例如绑定某个按钮的点击事件触发对应的JS-SDK的功能调用
					// 具体且详细的API的使用方法可以通过   微信官方的web开发者工具
					// 打开 http://203.195.235.76/jssdk/页面，然后再Source选线卡下选择demo.js脚本，然后查看该脚本上具体的API使用示例代码
					// 有详细的注释说明，应该很容易看懂。

					// （1）隐藏右上角的弹出式菜单，防止当前页面被分享出去
					wx.hideOptionMenu();
				// TODO 调用其他需要使用JS-SKD进行的初始设置工作.......
				});
			},
		}
	},
	data : {},

	/**
	 * 这个op中包含的是被动调用的方法
	 */
	op : {
		/*
		 * 获得指定请求参数名的请求参数值。
		 * 该方法主要是用来获取OAuth2.0页面认证授权后，微信服务器通过请求转发方式跳转到当前真正业务页面时通常
		 * 会以redirect_uri/?code=CODE&state=STATE形式将进一步获取access_token的code票据以请求参数的形式
		 * 传递过来，此方法就是专门用来获取该请求参数值而建立的
		 * 
		 * 方法调用的参数名就是 "code"
		 * 方法调用返回的结果就是code的值或者是null
		 */
		getAccess_Token : function(code) {
			// 构造一个含有目标参数名的正则表达式对象，当前工程就是code这个请求参数名
			var reg = new RegExp("(^|&)" + code + "=([^&]*)(&|$)");
			var r = window.location.search.substr(1).match(reg); // 匹配目标参数
			// r有时候可能为""
			if (r != null)
				return unescape(r[2]);
			return null; // 返回参数值
		},


		/*
		 * realName.jsp前端表单验证逻辑，只有当
		 * username
		 * sex
		 * age
		 * phone
		 * 这四个input都有值得时候，提交按钮才可用
		 */
		canCheckRealName : function() {
			var username = $("#username").val();
			var sex = $('input[name="sex"]:checked').val();
			var birth = $("#birth").val();
			var phone = $("#phone").val();
			if ("" == username || null == username) {
				console.log("姓名为必填项");
				$("#submit").addClass("weui-btn_disabled");
				return;
			}
			if ("" == phone || null == phone) {
				console.log("电话为必填项");
				$("#submit").addClass("weui-btn_disabled");
				return;
			}
			if ("" == birth || null == birth) {
				console.log("年龄为必填项");
				$("#submit").addClass("weui-btn_disabled");
				return;
			}
			console.log("现在可以提交了");
			$("#submit").removeClass("weui-btn_disabled");
		},

		/*
		 * 执行realName.jsp页面上提交表单时进行实名认证的Ajax操作
		 */
		checkRealName : function() {
			var url = "ajaxAction4weixin_checkRealName.action";
			var username = $("#username").val();
			var sex = $('input[name="sex"]:checked').val();
			var birth = $("#birth").val();
			var phone = $("#phone").val();
			if ("" == username || null == username) {
				$("#helpId4Username").text("姓名为必填项");
				return;
			} else if ("" == sex || null == sex || 0 == sex) {
				$("#helpId4Sex").text("性别为必填项");
				return;
			} else if ("" == birth || null == birth) {
				$("#helpId4Age").text("年龄为必填项");
				return;
			} else if ("" == phone || null == phone) {
				$("#helpId4Phone").text("电话为必填项");
				return;
			}

			var param = {
				username : username,
				sex : sex,
				birth : birth,
				phone : phone
			};
			$.post(url, param, function(data) {
				if (data.result) {
					// 实名制认证通过,则直接关闭微信浏览器，返回公众号平台
					wx.closeWindow();
				} else {
					// 实名制认证未通过
					alert(data.message);
				}
			});
		}
	}
};

/**
 * 处理活动相关的前端逻辑
 */
var activityModal = {
	init : {
		// 对当前页面上的jQueryUI组件进行一些初始化工作
		initOp : function() {
			// 设置日期时间选择器
			$("#date4selector").prop("readonly", true).datetimepicker({
				showAnim : "blind",
				showButtonPanel : false, // 不显示日期时间选择下面的按钮行
				showSecond : false,
				onClose : function(selectedDate) {
					// 当选择器关闭时出发本事件回调
				},
				// 可选日期范围从当前日期偏移1天开始，到1个月的为选择范围
				minDate : 1,
				maxDate : "+1M"
			});

			// 设置活动时间的滚动条
			$("#hourBar").slider({
				range : false, // 如果设置成true就会出现前后两个滑块
				min : 1, // 最小值为1（小时）
				max : 12, // 最大值为12（小时）
				change : function(event) {
					// 获取滑块儿的jQuery对象，因为event.target是出发当前回调方法的DOM对象（一个div），因此需要先转化成jQuery对象，才能调用jQuery的API
					var $hourBar = $(event.target);
					// 当滚动条被波动时，出发本回调，设置显示的数值
					/*
					 * 如果设置的range为true，则此处虎丘两个滑块儿的值的时候应该使用values， 
					 * 返回一个数组index=0为第一个滑块儿的数值；index=1为第二个滑块儿的数值
					 */
					// var hour = $(event.target).slider("values")[1];
					/* 
					 * 而如果range为false，则只有一个滑块儿，因此直接使用value就能获取到这个滑块儿的数值了。
					 */
					var hour = $hourBar.slider("value");
					$("#hour").val(hour);
				},
			});

			// 由于默认活动参与人数不设限制，因此在刚刚载入页面的时候需要预先隐藏baoMingUplimit
			$("#baoMingUplimit").parent().attr("hidden", true);
		}
	},
	data : {},
	op : {

		/*
		 * 筹备某一activity的详情信息（主要是二维码）并显示Modal
		 */
		showDetialModal : function(aid) {
			let param = {
				aid : aid
			}
			let url = "activityAction_showDetialModal.action";
			$.post(url, param, function(data, textStatus, req) {

				$("#detial4title").text(data.name + "-详情信息");
				$("#detial4qrcode").attr("src", data.qrcodeUrl);
				$("#detial4Name").text(data.name);
				$("#detial4Description").text(data.description);
				if ("1" == data.activityType) {
					// 室外活动
					$("#detial4PositionName").text(data.geographic.name);
				} else {
					// 室内活动
					$("#detial4PositionName").text(data.house.name);
				}
				$("#detial4Aid").text(data.aid);
				$("#detial4StartTime").text(data.beginTimeStr);
				$("#detail4EndTime").text(data.endTimeStr);

				$("#detialModal").modal("show");
			});
		},

		/*
		 * 跳转到展示活动参与者的list列表页面
		 */
		showVisitors : function(aid) {
			alert("当前活动的aid是：" + aid);
		},

		/*
		 * 【完成】
		 * 监听活动人数限制类型的selector的变化，用来显示设置参与人数的input
		 */
		typeChangeListener : function() {
			var type = $("#type").val();
			if ('1' == type) {
				$("#baoMingUplimit").parent().attr("hidden", true);
			} else if ('2' == type) {
				$("#baoMingUplimit").parent().attr("hidden", false);
			}
		},

		/*
		 * 【完成】
		 * 校验函数——校验id=hour的input的值是否在1~12之间
		 */
		checkHour : function(self) {
			var $hourInput = $(self);
			/*
			 * 然后再将数字字符串转变为真正的Number类型的数值
			 * 需要注意的是，如果hour.val()不是数字字符串而是字符，则转化后的是NaN
			 * 因此为了保险起见，我们通过 "parseInt(hour.val())||1"的形式来获取数值
			 * 因为如果parseInt(hour.val()) 是 NaN/Null/undefined 则就会默认为1
			 * 否则才是parseInt(hour.val())的本值，这是一个JS中的常用技巧★★
			 */
			var hour = parseInt($hourInput.val()) || 1;
			// 然后判断hour是否在1~12之间
			if (hour < 1) {
				hour = 1;
			} else if (hour > 12) {
				hour = 12;
			}
			console.log("当前hour的值被修正成：" + hour);
			$hourInput.val(hour);
			$("#hourBar").slider("value", hour);
		},

		/*
		 * 【完成】
		 * 当活动类型的selector发生变动时出发本回调
		 */
		activityTypeChangeListener : function() {
			let activityType = $("#activityType").val();
			switch (activityType) {
			case '0': /*没确定活动*/
				$("#indoor").attr("hidden", true);
				$("#outdoor").attr("hidden", true);
				break;
			case '1': /*室外活动*/
				$("#indoor").attr("hidden", true);
				$("#outdoor").attr("hidden", false);
				break;
			case '2': /*室内活动*/
				$("#indoor").attr("hidden", false);
				$("#outdoor").attr("hidden", true);
				break;
			}
		},

		/*
		 * 【完成】
		 * AJAX
		 * 当房屋的selector发生变动时出发，主要的功能就是：
		 * （1）清空date4calendar的内容
		 * （2）清空date4calendar时会自动出发checkInput()；
		 * （3）删除自定义calendar的事件源
		 * （4）AJAX从服务器获取指定House的新的事件源
		 */
		houseChangeListener : function() {
			$("#date4calendar").val("");
			// 先隐藏日历
			$('#calendar').attr("hidden", true);
			$("#date4calendar").attr("disabled", true); // 其实date4calendar一直是disabled，因为该input只能通过calendar日历选择填入
			// 获取用户所选中的活动室的hid
			let hid = $("#houseSelector").val();
			// 如果用户选择的是"--请选择活动室--"，则
			if ('0' == hid) {
				$('#calendar').fullCalendar('removeEventSources');
				return;
			}
			// 否则用户确实选择了一个活动室，开始向本地服务器所要该活动室当月的全部活动数据信息
			let param = {
				hid : hid
			}
			let url = "houseAction_getEventSource4month.action";
			$.post(url, param, function(data, textStatus, req) {
				// 在这里可以设置calendar的数据源
				// 先删除视图中所有的旧事件数据源
				$('#calendar').fullCalendar('removeEventSources');
				// 再通过FullCalendar的“添加事件源”的功能，将JSON对象直接设置成日历的事件源，然后自动绘制时间图标到日历上显示出来。
				$('#calendar').fullCalendar('addEventSource', data);
				// 显示日历
				$('#calendar').attr("hidden", false);
				$("[aria-label='next']").attr("hidden", true).click();
				$("[aria-label='prev']").attr("hidden", true).click();
			});
		},


		/*
		 * 【完成】
		 *  当活动地点发生变动时，出发本回调
		 *  （1）清空date4selector
		 *  （2）清空date4selector时会自动触发checkInput（）
		 *  （3）时间滑块重归1
		 *   (4) 根据geoSelector的选择重新部署date4selector和hourBar（显示或隐藏）
		 *  
		 */
		geoChangeListener : function() {
			$("#date4selector").val("");
			//			$("#hourBar").slider("value", 1);
			let geoid = $("#geoSelector").val();
			if ('0' == geoid) {
				$("#date4selector").attr("hidden", true);
				$("#hourBar").attr("hidden", true);
			} else {
				$("#date4selector").attr("hidden", false);
				$("#hourBar").attr("hidden", false);
			}
		},

		/*
		 * 【完成】
		 * 检查主要的幾個input是否为空，为空则commit提交按钮为disabled
		 */
		checkInput : function() {
			let aflag = true;
			// 遍历每个拥有data-myInput="me" 的input，查看有没有值
			let activityType = $("#activityType").val();
			let type = $("#type").val();

			let name = $("#name").val();
			let description = $("#description").val();
			let baoMingUplimit = $("#baoMingUplimit").val();
			let date4calendar = $("#date4calendar").val(); // 室内
			let date4selector = $("#date4selector").val(); // 室外
			let hour = $("#hour").val(); // 室外
			let score = $("#score").val();

			switch (activityType) {
			case '0':
				aflag = false;
				break;
			case '1': /*室外活动*/
				if (type == 1) {
					// 开放报名，不用校验baoMingUplimit
					if ("" == name || "" == description || "" == date4selector || "" == hour || "" == score) {
						aflag = false;
					}
				} else {
					// 限制人数报名，还需要校验baoMingUplimit
					if ("" == name || "" == description || "" == baoMingUplimit || "" == date4selector || "" == hour || "" == score) {
						aflag = false;
					}
				}
				break;
			case '2': /*室内活动*/
				if (type == 1) {
					// 开放报名，不用校验baoMingUplimit
					if ("" == name || "" == description || "" == date4calendar || "" == score) {
						aflag = false;
					}
				} else {
					// 限制人数报名，还需要校验baoMingUplimit
					if ("" == name || "" == description || "" == baoMingUplimit || "" == date4calendar || "" == score) {
						aflag = false;
					}
				}
				break;
			}
			if (aflag) {
				// 准备好，可提交
				$("#commit").attr("disabled", false);
			} else {
				// 未准备好，不可提交
				$("#commit").attr("disabled", true);
			}
		},

		/*
		 * 【完成】
		 * 查看baoMingUplimit这个input的人数是否在1~max之间
		 */
		checkBaomingUplimit : function() {
			if ($("#type").val() == '1') {
				// 不限定人数，无需检查
			} else {
				// 限定人数，检查
				var baoMingUplimit = parseInt($("#baoMingUplimit").val());
				var min = parseInt($("#baoMingUplimit").attr("min"));
				var max = parseInt($("#baoMingUplimit").attr("max"));
				if (baoMingUplimit <= max && baoMingUplimit >= min) {
					$("#commit").attr("disabled", false);
					$("#info4baoMingUplimit").attr("hidden", true);
				} else {
					$("#commit").attr("disabled", true);
					$("#info4baoMingUplimit").attr("hidden", false).text("报名人数限制应在" + min + "~" + max + "之间");
				}
			}
		},

		/*
		 * 【完成】
		 *AJAX 
		 *创建活动
		 */
		createActivity : function() {

			var url = "activityAction_createActivity.action";
			let dpid = $("#dpid").val();
			let activityType = $("#activityType").val();
			let type = $("#type").val();

			let name = $("#name").val();
			let description = $("#description").val();
			let baoMingUplimit = $("#baoMingUplimit").val();
			let hid = $("#houseSelector").val(); // 室内
			let date4calendar = $("#date4calendar").val(); // 室内
			let geoid = $("#geoSelector").val(); // 室外
			let date4selector = $("#date4selector").val(); // 室外
			let hour = $("#hour").val(); // 室外
			let score = $("#score").val();

			var param = {
				"dpid" : dpid,
				"activityType" : activityType,
				"type" : type,
				"name" : name,
				"description" : description,
				"baoMingUplimit" : baoMingUplimit,
				"hid" : hid, // 室内
				"date4calendar" : date4calendar, // 室内
				"geoid" : geoid, // 室外
				"date4selector" : date4selector, // 室外
				"hour" : hour, // 室外
				"score" : score,
			}
			$.post(url, param, function(data, textStatus, req) {
				if (data.result) {
					// 创建成功
					alert(data.message);
					// 跳转到活动列表——activityList.jsp
					$(location).attr("href", "activityAction_showDoingProjectActivityList.action?dpid=" + param.dpid);
				} else {
					// 创建失败
					alert(data.message);
				}
			});
		},
	}
}



/**
 * 通过Jquery调用通用方法中的初始化方法完成一些页面初始化工作
 */
$(function() {
	// 通用初始化工作
	overAll.init.op.startToolTip();
	overAll.init.op.screenIsBigOrSmall();
	overAll.init.op.prepareCollapse();
	/*
	 * 微信端访问的页面所需要的初始化工作
	 * 涉及微信的初始化操作要放在所有通用初始化工作之后，因为
	 * 当前myJS文件是微信端浏览页面和桌面端浏览页面都要加载的，
	 * 而当桌面端浏览页面时由于缺少必要的weixin票据请求参数，则在执行微信初始化的时候就会爆出错误
	 * 一旦报错就会终止后续的初始化代码运行，因此要将微信初始化放在所有通用初始化工作的最后执行。
	 */
	if (overAll.init.data.isWeixin) {
		// 只有屏幕分辨率小到一定程度才能被认定为是微信端在访问页面，因此才执行与weixin有关的初始化操作。
		// 否则认定是桌面在访问页面，不需要设计微信的初始化操作
		aboutWeixin.init.op.getOpenIdAndConfigJSSDK();
	}
});