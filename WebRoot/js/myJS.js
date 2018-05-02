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
		 * 保存Permission数据到后端
		 */
		savePermission: function(level, lid){
			
			// 准备AJAX的请求参数
			var data ={
				selected:'',
				level: level,
				lid: lid
			};
			// 获取到所有权限设置的checkbox
			var $inputs = $("input[data-permission='-1']");
			console.log($inputs);
			// 遍历每个input
			$inputs.each(function(i, element) {
				if($(this).is(':checked')){
					data.selected += $(this).val()+",";
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
				minusFirstLevelModal.op.savePermission(level, lid);
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
					titleHTML.attr("data-toggle", "collapse").attr("data-parent","#permission-modal-body").attr("data-parent", "#accordianId").attr("href", "#"+permissionType.ptid);
					titleHTML.text(permissionType.description);
					var h5HTML = $("<h5></h5>").attr("class", "mb-0");
					h5HTML.append(titleHTML);
					var cardHeaderHTML =  $("<div></div>").attr("role", "tab").attr("class","card-header").append(h5HTML);
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
						permissionInput.attr("value",permission.pid);
						if(permission.open){
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
					var cardBodyHTML = $("<div></div>").attr("class","card-body").append(containerHTML);
					var contentHTML = $("<div></div>").attr("id",permissionType.ptid).attr("class","collapse in").attr("role","tabpanel").append(cardBodyHTML);
					// 最后将card-body也组装到card之上，至此包含有一个permissionType信息的Card创建完成
					cardHTML.append(contentHTML);
					// 最后的最后，将创建完成的card添加到id=permission-modal-body的div下即可完成一个有关permissionType下拉的创建工作
					$("#permission-modal-body").append(cardHTML);
				};
				$("#permissionModal").modal("show");
			});
		},

		/**
		 * 配置某层级对象的权限
		 * level 为当前JSP页面的层级对象的数字层级，minusFirstLevel默认为-1，用以告诉后台需要从什么PermissionLevel中查找权限（如果是Shiro框架则并不需要这么做）
		 * lid 为需要设置权限的层级对象的ID，用以从后台查找该层级对象已有权限，用以在前端显示的时候自动设置为选中状态
		 */
		updatePermission : function(level, lid) {},


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
 *========================用户User========================
 */
var userModal = {
	init : {

	},

	data : {

	},

	op : {
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
				window.location.reload()
			});
		},

		userInfo : function(uid) {
			var data = {
				uid : uid,
			};

			$.post("userAction_getUser4ajax.action", data, function(data, textStatus, req) {
				$("#detialsModal_title").text(data.username + "的详细信息");
				$("#detialsModal_qrcode").attr("src", data.qrcode);
				$("#detialsModal_username").text(data.username);
				$("#detialsModal_uid").text(data.uid);
				$("#detialsModal_openid").text(data.openid);
				$("#detialsModal_registrationTime").text(data.registrationTimeStr);
				if (null == data.member) {
					$("#detialsModal_minusFirst").text("无");
					$("#detialsModal_zero").text("无");
					$("#detialsModal_first").text("无");
					$("#detialsModal_second").text("无");
					$("#detialsModal_third").text("无");
					$("#detialsModal_fourth").text("无");
				} else {
					if (null == data.member.minusFirstLevel) {
						$("#detialsModal_minusFirst").text("无");
					} else {
						$("#detialsModal_minusFirst").text(data.member.minusFirstLevel.name);
					}
					if (null == data.member.zeroLevel) {
						$("#detialsModal_zero").text("无");
					} else {
						$("#detialsModal_zero").text(data.member.zeroLevel.name);
					}
					if (null == data.member.firstLevel) {
						$("#detialsModal_first").text("无");
					} else {
						$("#detialsModal_first").text(data.member.firstLevel.name);
					}
					if (null == data.member.secondLevel) {
						$("#detialsModal_second").text("无");
					} else {
						$("#detialsModal_second").text(data.member.secondLevel.name);
					}
					if (null == data.member.thirdLevel) {
						$("#detialsModal_third").text("无");
					} else {
						$("#detialsModal_third").text(data.member.thirdLevel.name);
					}
					if (null == data.member.fourthLevel) {
						$("#detialsModal_fourth").text("无");
					} else {
						$("#detialsModal_fourth").text(data.member.fourthLevel.name);
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
 * 初始化工作
 */
$(function() {
	overAll.init.op.startToolTip();
	overAll.init.op.screenIsBigOrSmall();
	overAll.init.op.prepareCollapse();
});