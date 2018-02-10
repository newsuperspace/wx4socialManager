define(function(require) {
	var $ = require("jquery");
	var justep = require("$UI/system/lib/justep");

	// Wex5中添加其他JavaScript脚本模块的方法
	// 路径的意思是"加载当前*.w文件同级（./的意思就是同级目录）目录下的js文件夹内的名叫oauth2的js文件"
	// 需要注意的是用作"模块"的JS脚本文件需要有特定的书写格式(AMD规范或CMD规范)，可以参照该路径下的JS文件中的实际格式来定义自己的模块。
	var oauth2 = require("./js/oauth2");
	// 加载外网的符合AMD规范的模块或者普通js脚本都必须是以http://开头的完整绝对路径，并且以js拓展名结尾，拓展名不能省略 ★★
	// 至此，当前脚本中可以通过wx.xxxxx() 来调用微信的JS-SDK了，不过前提必须是拖过了签名授权
	var wx = require("http://res.wx.qq.com/open/js/jweixin-1.2.0.js");

	var Model = function() {
		this.callParent();
	};

	// 图片路径转换,该方法是供给页面设计端在设置数据绑定的时候，使用$model.toUrl()使用的函数，在JS脚本并不需要使用。
	Model.prototype.toUrl = function(url) {
		return url ? require.toUrl(url) : "";
	};

	// 当list.w页面被激活（显示出来）的时候，就会触发onActive方法
	// 该事件处理历程的作用是从服务器端获取全部的活动信息。
	Model.prototype.content3Active = function(event) {
		var self = this;

		var url = oauth2.getServiceUrl() + "/" + "ajaxAction4weixin_getActivityList.action"
		var param = {
		// TODO 未来这里应该携带者社区管理者的OpenID，用以获取由该社区管理者发起的本社区的Activity
		// DEMO中暂时不用管具体用户，只需要将数据库中全部的activity提取出来就好
		};
		$.post(url, param, function(data) {
			if (!jQuery.isEmptyObject(data)) { // 返回的JSON数组不为空，则说明有借阅图书的信息
				// loadData必须是一个JSON数组（格式的字符串），也就是[{},{},{}]这样的数组 ★
				// 而服务器端通过struts-json-plugin.jar提供的结果集，将放置在对象栈栈顶的list<Activity>容器对象解析成的正是这样格式的json字符串
				// 因此这里可以直接拿来用。
				self.comp("activityData").loadData(data);
				// 修正数据组件内的指针到第一行，这一点很重要，因为在构建list列表逐行填入$row数据的时候，应该是从数据组件的首行开始的
				// 但是在loadData()之后数据组件内部的指针会停留在最后一行的下一个位置，因此需要重新修正游标指针到第一行（$row）上
				// ★
				self.comp("activityData").first();
				$(self.getElementByXid("scrollView1")).show(); // 由于显示组件已经与数据组件进行了双向数据感知的关联，因此只要数据组件中字段值改变，则前端显示组件的显示也会改变
				$(self.getElementByXid("tips1")).hide();
			} else {
				$(self.getElementByXid("scrollView1")).hide();
				$(self.getElementByXid("tips1")).text("没有活动").show();
			}
		});
	};

	// 从服务器端重新获取数据到Data数据组件中，以达到刷新借书列表的目的
	// 因为在执行创建新活动（界面右上角的+号），会进入createActivity.w页面创建新活动
	// 当新活动创建成功会重新返回当前list.w页面，因此需要重新刷新数据组件（activityData）中的数据
	// 以便将刚刚新建的活动也显示出来。
	// 数据组件的CustomRefresh事件会在其所属*.w页面重新获得焦点，也就是触发onActivity事件的时候自动触发
	// 这个事件通常都是加载数据到数据组件，也就是初始化数据组件的重要时机。★★
	Model.prototype.dataCustomRefresh = function(event) {
		var self = this;
		self.content3Active(event);
	};

	// 用户点击右上角+号后，打开新建活动的 createActivity.w 页面
	Model.prototype.createActivity = function(event) {
		justep.Shell.showPage(require.toUrl("./createActivity.w"));
	};

	// 单击列表项，打开扫码签到的dialog对话框
	Model.prototype.signin = function(event) {
		// alert("点击了行");

		var self = this; // ★提前提取当前脚本的this到self，省的在内部函数中被覆盖，这是个好习惯

		// 获取所单击的list组件的当前行所对应的数据组件的当前行对象。 ★
		var $row = event.bindingContext.$object;
		var aid = $row.val("aid");
		var name = $row.val("name");
		var qrcode = $row.val("qrcodeUrl");
		var endData = $row.val("endData");

		// 获取当前时间戳(以ms为单位)
		var currentTime = Date.parse(new Date());
		// 获取活动结束时间格式的时间戳（以ms为单位）
		var endTime = Date.parse(new Date(endData));
		// 比对两次时间
		if (currentTime > endTime) {
			// 活动已经过期
			var options = {
				type : "warning",
				position : "bottom",
				style : "text-align:center;font-size:15px;"
			};
			justep.Util.hint("活动已过期。", options);
		} else {
			// 组装活动专属二维码图片的url，该二维码上就是该活动在数据库中aid
			var qrcodeURL = oauth2.getServiceUrl() + "/" + qrcode;
			// 开启dialog，其上有一个二维码图片，可以供普通用户，通过微信菜单上的"扫码签到"按钮打开微信二维码的扫码推功能。
			self.comp("dialog").open({
				data : {
					'qrcode' : qrcodeURL, // 将二维码传递到dialog绑定的dialog4Signin.w页面上，作为其上的<img
				// src=""> 标签显示该图
				}
			});
		}

		// 由于嵌套结构在触发事件（包括点击事件）的时候会产生冒泡现象（儿子只知道一件事情发生了，他要告诉爸爸，爸爸又告诉爷爷）
		// 因此通过这种方式可以阻止冒泡（儿子知道事情发生了就保密，不告诉任何人），但不阻止默认事件（浏览器自己的行动）
		// 如果你要连浏览器的默认事件都禁止，可以使用event.preventDefault();但单独使用它不会阻止冒泡。 ★★★
		event.stopPropagation();
	};

	// 打开qrcode扫描功能，用来扫描用户的专属qrcode，实现对没有加入公众号的用户的签到功能
	Model.prototype.saoClick = function(event) {
		// alert("点击了按钮");

		// 通过以下方式可以获取到数据组件的"当前行$row"对象，从而可以进一步获取具体字段的值
		var $row = event.bindingContext.$object;
		// alert($row.val("name"));

		var endData = $row.val("endData");

		// 获取当前时间戳(以ms为单位)
		var currentTime = Date.parse(new Date());
		// 获取活动结束时间格式的时间戳（以ms为单位）
		var endTime = Date.parse(new Date(endData));
		// 比对两次时间
		if (currentTime > endTime) {
			// 活动已经过期
			var options = {
				type : "warning",
				position : "bottom",
				style : "text-align:center;font-size:15px;"
			};
			justep.Util.hint("活动已过期。", options);
		} else {
			// 通过微信的JS-SDK，实现对qrcode扫描器的开启
			wx.scanQRCode({
				needResult : 1,
				desc : '签到',
				success : function(res) {
					// 每个被扫描的qrcode就是的值就是这个用户的uid
					// 扫描成功后的回调函数的参数是一个JSON对象，通过它的resultStr就可以得到用户的uid字符串
					var uid = res.resultStr;
					// 得到当前活动的aid
					var aid = $row.val("aid");
					// 用户签到所需要请求的哦们自己的服务器的url
					var url = oauth2.getServiceUrl() + "/" + "ajaxAction4weixin_signIn.action";
					// ajax的请求参数
					var param = {
						'uid' : uid,
						'aid' : aid
					};
					// 发起AJAX
					$.post(url, param, function(data) {
						if (data.result) {
							// 签到成功
							var options = {
								type : "success",
								position : "bottom",
								style : "text-align:center;font-size:15px;"
							};
							justep.Util.hint(data.message, options);
						} else {
							// 签到不成功
							var options = {
								type : "warning",
								position : "bottom",
								style : "text-align:center;font-size:15px;"
							};
							justep.Util.hint(data.message, options);
						}
					});
				}
			});
		}

		// 由于嵌套结构在触发事件（包括点击事件）的时候会产生冒泡现象（儿子只知道一件事情发生了，他要告诉爸爸，爸爸又告诉爷爷）
		// 因此通过这种方式可以阻止冒泡（儿子知道事情发生了就保密，不告诉任何人），但不阻止默认事件（浏览器自己的行动）
		// 如果你要连浏览器的默认事件都禁止，可以使用event.preventDefault();但单独使用它不会阻止冒泡。 ★★★
		event.stopPropagation();
	};

	// 初始化工作，包括JS-SDK的认证授权和当前页的配置工作
	Model.prototype.modelModelConstruct = function(event) {
		var self = this;

		// =============================JS-SDK使用权限签名=============================
		// 因为当前页面需要在"实名制认证成功"后就让微信浏览器关闭此页面，因此需要使用微信提供的JS-SDK来调用微信的功能
		// 在正式调用JS-SDK中的api之前需要做到
		// （1）加载微信JS-SDK的模块【已通过require()方法加载】
		// （2）加载JS-SDK模块后可以直接通过wx这个全局变量实现对微信web功能的API调用，但再此之前还需要config配置过程
		url = oauth2.getServiceUrl() + "/" + "ajaxAction4weixin_getJsapiSignature.action"
		// 得到需要调用JS-SDK的页面的URL，但不能包括"#"号及其后面的部分，因此需要通过JS原生的split()函数切割一下 ★
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
			// 具体且详细的API的使用方法可以通过 微信官方的web开发者工具
			// 打开
			// http://203.195.235.76/jssdk/页面，然后再Source选线卡下选择demo.js脚本，然后查看该脚本上具体的API使用示例代码
			// 有详细的注释说明，应该很容易看懂。

			// 隐藏右上角的弹出式菜单，防止当前页面被分享出去
			wx.hideOptionMenu();

			// 调用其他JS-SKD.......
			// .....
		});
	};

	return Model;
});