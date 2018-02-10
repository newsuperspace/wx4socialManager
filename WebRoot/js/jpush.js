var jpush = {
	init : {},

	op : {
		// 提交表单数据到服务器，进行消息推送
		tui : function() {
			var r = confirm('是否确认推送？');
			if (r) {
				jpush.op.ok();
			}
		},
		ok : function() {
			var data = {
				title : $("#title").val(),
				content : $("#content").val(),
				system : $("#system").val(),
			};

			$.post("jpushAction_push.action", data, function(data, textStatus, req) {
				alert(data.message);
				location.replace("jpushAction_pushPage.action");
			});
		},
	
		
	},

	data : {},
};

// 指定tuisong中的init中的初始化方法
//$(document).ready(function() {
//	
//});