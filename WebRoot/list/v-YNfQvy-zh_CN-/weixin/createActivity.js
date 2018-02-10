define(function(require){
	var $ = require("jquery");
	var justep = require("$UI/system/lib/justep");
	// Wex5中添加其他JavaScript脚本模块的方法
	// 路径的意思是"加载当前*.w文件同级（./的意思就是同级目录）目录下的js文件夹内的名叫oauth2的js文件"
	// 需要注意的是用作"模块"的JS脚本文件需要有特定的书写格式(AMD规范或CMD规范)，可以参照该路径下的JS文件中的实际格式来定义自己的模块。
	var oauth2 = require("./js/oauth2");
	// 加载外网的符合AMD规范的模块或者普通js脚本都必须是以http://开头的完整绝对路径，并且以js拓展名结尾，拓展名不能省略 ★★
	// 至此，当前脚本中可以通过wx.xxxxx() 来调用微信的JS-SDK了，不过前提必须是拖过了签名授权
	var wx = require("http://res.wx.qq.com/open/js/jweixin-1.2.0.js");
	
	var Model = function(){
		this.callParent();
		this.button = justep.Bind.observable(false);
	};

	// 用户点击titleBar的左箭头，返回之前的页面
	Model.prototype.back = function(event){
		this.close();
	};

	// 通过Ajax请求，将表单数据提交到前端，创建新活动
	Model.prototype.createActivity = function(event){
		var self = this;
		
		// 动态修改前端UI上按钮组件的bind-disable 属性★★★
		self.button.set(true);
	
		var source = this.comp("regData");
		
		var url = oauth2.getServiceUrl() + "/" + "ajaxAction4weixin_createActivity.action"
		param = {
			name: source.val("name"),
			description: source.val("description"),
			score: source.val("score") 
		};
		$.post(url, param, function(data) {
		
			if(data.result){
				var options = {
					type : "success",
					position : "bottom",
					style : "text-align:center;font-size:15px;"
				};
				justep.Util.hint(data.message, options);
				self.close();
			}else{
				var options = {
					type : "warning",
					position : "bottom",
					style : "text-align:center;font-size:15px;"
				};
				justep.Util.hint(data.message, options);
				source.clear();   // 清空数据关联
				// 动态修改前端UI上按钮组件的bind-disable 属性 ★★★
				self.button.set(false);
			}
		});

	};

	

	return Model;
});