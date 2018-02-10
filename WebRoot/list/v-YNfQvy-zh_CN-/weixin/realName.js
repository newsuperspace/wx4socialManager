define(function(require) {
	var $ = require("jquery");
	var justep = require("$UI/system/lib/justep");

	// Wex5中添加其他JavaScript脚本模块的方法
	// 路径的意思是"加载当前*.w文件同级（./的意思就是同级目录）目录下的js文件夹内的名叫oauth2的js文件"
	// 需要注意的是用作"模块"的JS脚本文件需要有特定的书写格式(AMD规范或CMD规范)，可以参照该路径下的JS文件中的实际格式来定义自己的模块。
	var oauth2 = require("./js/oauth2");
	// 加载外网的符合AMD规范的模块或者普通js脚本都必须是以http://开头的完整绝对路径，并且以*.js拓展名结尾，拓展名不能省略 ★★
	// 至此，当前脚本中可以通过wx.xxxxx() 来调用微信的JS-SDK了，不过前提必须是拖过了微信服务器的签名授权（授权操作是由哦们自己的服务器执行的，不需要前端操心）
	var wx = require("http://res.wx.qq.com/open/js/jweixin-1.2.0.js");

	var Model = function() {
		this.callParent();
		
		this.button = justep.Bind.observable(false);
	};

	// 向服务器提交 实名制认证表单
	Model.prototype.submit = function(event) {
		var self = this;
		
		self.button.set(false);
		
		var url = oauth2.getServiceUrl() + "/" + "ajaxAction4weixin_checkRealName.action"
		this.comp("userinfo").first();
		var source = this.comp("userinfo");
		param = {
			openid : localStorage.getItem("openid"),
			cardid : source.val("cardid"),
			username : source.val("username"),
			address : source.val("address"),
			phone : source.val("phone")
		};
		$.post(url, param, function(data) {
			if (data.result) {
				// 实名制认证通过,则直接关闭微信浏览器，返回公众号平台
				wx.closeWindow();
			} else {
				// 实名制认证未通过
				justep.Util.hint(data.message, null);
				self.button.set(true);
			}
		});
	};

	Model.prototype.modelModelConstruct = function(event) {
		console.log("Hello world!");

		var self = this;
		
		$.ajaxSettings.async = false; // 设置ajax请求成同步请求
		// $.ajaxSetup({
		// async : false
		// });
		// ===============================微信网页认证授权===========================
		// 由于当前页面是用来实名制认证的，因此通过OAthure2.0认证后会将用来获取access_token的票据code以请求参数的形式传递过来
		// 因此这里就需要预先获取到请求参数code的值，并且进行Ajax通讯让服务器从微信官方得到该用户真正的openID，并且保存到数据组件中备用
		var code = oauth2.getParam("code");
		// 准备进行Ajax通讯获取来访用户的OpenID
		var url = oauth2.getServiceUrl() + "/" + "ajaxAction4weixin_getOpenIdthroughCode.action"
		var param = {
			"code" : code,
		};
		$.post(url, param, function(data) {
			if (!jQuery.isEmptyObject(data)) {
				self.comp("userinfo").first(); // 修正数据组件内的指针到第一行
//				self.comp("userinfo").val("openid", data.openid);
				localStorage.setItem("openid", data.openid);

				// justep.Util.hint("用户openid是" +
				// self.comp("userinfo").val("openid"), null);
			} else {
				justep.Util.hint("以Code换取用户OpenID时出现异常", null);
			}
		});

		// =============================JS-SDK使用权限签名=============================
		// 因为当前页面需要在"实名制认证成功"后就让微信浏览器关闭此页面，因此需要使用微信提供的JS-SDK来调用微信的功能
		// 在正式调用JS-SDK中的api之前需要做到
		// （1）加载微信JS-SDK的模块【已通过require()方法加载】
		// （2）加载JS-SDK模块后可以直接通过wx这个全局变量实现对微信web功能的API调用，但再此之前还需要config配置过程
		url = oauth2.getServiceUrl() + "/" + "ajaxAction4weixin_getJsapiSignature.action"
		// 得到需要调用JS-SDK的页面的URL，但不能包括"#"号及其后面的部分，因此需要通过JS原生的split()函数切割一下  ★
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
		});

		$.ajaxSettings.async = true; // 记得要重新恢复异步请求
		// $.ajaxSetup({
		// async : true
		// });

		// 由于通过ajax通讯，让哦们自己的服务器与微信服务器交互后获得JS-SDK认证的signature等信息的过程
		// 默认是异步执行的，也就是当发起ajax通讯后没有必要等待哦们自己服务器返回响应就可以继续执行$.post()之后的语句
		// 但当设置了同步ajax请求后，在$.post()之后的语句就必须等待ajax的回调函数执行结束后才能继续执行
		// 因此在执行下面的wx.ready()对JS-SDK进行初始化配置之前，已经预先执行了位于$.post()回调中的wx.config()的设置步骤
		// 因此可以放心的将wx.ready()代码放在ajax请求的后面，就是因为此次ajax请求（也急速$.post()）是一次同步操作。
		// 在微信web的JS-SDK的config配置执行完毕后，就可以通过下面的方法进行当前页面所需的JS-SDK功能的初始化配置。
		wx.ready(function() {
		
			// 这里包含了对当前页面上所使用的JS-SDK功能的初始化设置操作，例如绑定某个按钮的点击事件触发对应的JS-SDK的功能调用
			// 具体且详细的API的使用方法可以通过   微信官方的web开发者工具
			// 打开 http://203.195.235.76/jssdk/页面，然后再Source选线卡下选择demo.js脚本，然后查看该脚本上具体的API使用示例代码
			// 有详细的注释说明，应该很容易看懂。
		
			// 隐藏右上角的弹出式菜单，防止当前页面被分享出去
			wx.hideOptionMenu();
			
			// 调用其他JS-SKD.......
			// .....
			
		});
	};

	return Model;
});