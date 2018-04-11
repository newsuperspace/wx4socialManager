
/**
 * ========================第三层级========================
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
			var parentDescription = self.parent().parent().prev().prev().prev().prev().prev().prev().prev().prev().prev();  // <!-- ● -->
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
			var parentDescription = self.parent().parent().prev().prev().prev().prev().prev().prev().prev().prev();  // <!-- ● -->
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
			var parentDescription = self.parent().parent().prev().prev().prev().prev().prev().prev().prev();  // <!-- ● -->
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


/*
 * 默认执行语句，如果当前运行应用程序的设备屏幕宽度是大屏幕，则显示左侧工具栏。
 * 否则小平面不现实左侧工具栏。
 */
if (window.screen.availWidth >= 768) {
	$('#contentId').collapse({
		toggle : true
	});
}
;

/*
 * 通过jQuery实现的初始化操作，作用是将当前页面上的所有tooltip效果激活
 */
$(function() {
	$('[data-toggle="tooltip"]').tooltip();
});

function toggle() {
	/*
	 要操作Collapse中的某一个特定的card的时候，需要先获取到该card的card-body上一层的div的id
	 然后再通过执行collapse()配合 hide（关闭）、show（展开）、toggle（切换——结合了hide和show）
	*/
	console.log("关闭关闭")
	$('#section1ContentId').collapse('toggle');
}
;