// ========================街道层级========================
var  minusFirstLevelModal  =  {
		init:{
			
		},
		
		data:{
			
		},
		
		op:{
			
			/**
			 * 新建街道层级
			 */
			createMinusFirstLevel:function(){
				var data ={
						name: $("#name").val(),
						description: $("#description").val(),
				};
				
				$.post("minusFirstLevelAction_createLevel.action", data, function(data, textStatus, req) {
					$("#newMinusFirstLevelModal").modal("hide");
					alert(data.message);
					window.location.reload()
				});
				return false;
			},
			
			/**
			 * 获取详细信息
			 */
			levelInfo:function(id){
				alert("当前获取详细信息的MinusFirstLevel的ID是："+id);
				return false;
			},
			
		}
};




// ========================用户User========================
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
				$("#detialsModal_qrcode").attr("src",data.qrcode);
				$("#detialsModal_username").text(data.username);
				$("#detialsModal_uid").text(data.uid);
				$("#detialsModal_openid").text(data.openid);
				$("#detialsModal_registrationTime").text(data.registrationTime);
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
	} // op is over
};


if (window.screen.availWidth >= 768) {
	$('#contentId').collapse({
		toggle : true
	});
};

$(function(){
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