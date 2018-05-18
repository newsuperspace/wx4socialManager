/**
 * 这个脚本是专用于通过websocket实现后台系统登录的前端页面上的脚本逻辑
 */
var preData = {
	// 这里可以根据实际的服务器域名随时变动配置
	baseURL : "ddaig.nat200.top/weixin",
	lastTime : 60,
};


var wsModal = {
	// 数据
	data : {
		// 公共数据
		common : {
			// 这里可以根据实际的服务器域名随时变动配置
			baseURL : preData.baseURL,
		},
		// 与WebSocket有关的数据
		wsData : {
			wsObject : null, // ws连接对象
			wsURL : "ws://" + preData.baseURL + "/ws/login", // 开启ws连接的时候 连接的服务器的URL
			wsQR : "", // 服务器上二维码的相对路径 如 temp/1/12/SJDIFSJ38223L4J.gif
			sub_title : "请使用扫码登录功能", // 显示文本
			sub_desc : "二维码有效期" + preData.lastTime + "秒", // 显示的附文本
			wsCode : "", // 二维码的uuid值
		}
	},

	// 函数
	op : {
		// 与WebSocket有关的操作
		wsOP : {
			// 处理ws连接建立成功事件的回调函数
			wsOpen : function() {
				// 告诉服务器，可以将本次登录使用的uuid放入到ServletContext中了
				var message = "type:start,";
				message += "uuid:" + wsModal.data.wsData.wsCode + ",";
				message += "content:" + wsModal.data.wsData.wsQR;
				wsModal.data.wsData.wsObject.send(message);
			},

			// 处理ws传递过来的信息的事件的回调函数
			wsMessage : function(message) {
				// 获取从服务器端通过WebSocket传递过来的文本信息  形如：  success,32Ijwi89sdj324jn2fdisu83    或   waiting,继续等待
				var result = message.data.split(",");

				if ("success" == result[0]) { // 用户已经扫码成功
					// 先立刻停止计时器，并交给计时器最后一个任务——告诉服务器关闭ws连接吧，我前端已经不需要了 ★
					preData.lastTime = -1;
					// 用户已经使用微信的扫码功能完成了扫码操作，并且已经将自己的openID存入了ServletContext域中的当前uuid的键值对儿中
					var openid = result[1];
					// 通过Ajax请求
					var url = "shiroAction_ws4Login.action";
					var data = {
						"openid" : openid,  // 已经扫码者的微信openID
					};
					$.post(url, data, function(re, textStatus, req) {
						if (re.result) {
							// 登录成功，没毛病
							// 设置二维码图片 成 对勾
							$("#qrcode").attr("src", "/weixin/img/bingo.png");
							// 设置相关文字的显示
							$("#title").text(re.message);
							$("#lastTime").text("自动跳转到系统首页...");
							// 跳转页面
							window.location.href = re.reLocal;
						} else {
							// 登录失败，告知信息
							// 设置二维码图片 成 红叉子
							$("#qrcode").attr("src", "/weixin/img/error.png");
							// 设置相关文字的显示
							$("#title").text(re.message);
							$("#lastTime").text("请刷新页面重新登录或联系管理员");
						}
					});
				} else if ("waiting" == result[0]) { // 用户还没有扫码，继续等待...
					// TODO 暂时什么也不用做
				}
			},

			//处理ws连接关闭的事件的回调函数
			wsClose : function() {},

			wsGetQR : function() {
				// 为了确保Ajax的回调函数执行完毕之后在执行之后的开启WebSocket的操作， 因此需要手动设置Ajax为同步执行（默认是异步执行）
				$.ajaxSetup({
					async : false // 全局设置Ajax为同步执行
				});

				var url = "shiroAction_getLoginQR.action";
				var data = {};
				$.post(url, data, function(data, textStatus, req) {
					wsModal.data.wsData.wsQR = data.qrURI;
					wsModal.data.wsData.wsCode = data.uuid;
				})

				// 记得回复Ajax为异步执行
				$.ajaxSetup({
					async : true // 回复全局Ajax设置为异步执行
				});
			},

			// 计时器，用于倒数计时轮询服务器用户是否已经扫码和关闭WebSocket连接
			wsTimer : function() {
				var message = "";
				if (preData.lastTime > 0) {
					// 倒计时
					preData.lastTime -= 1;
					console.log("剩余时间：" + preData.lastTime);
					// 更新页面显示
					$("#lastTime").text("二维码有效期" + preData.lastTime + "秒");
					// 通过WebSocket与服务器沟通，让服务器查看UUID码是否已经被填入了登陆者的OpenID
					message = "type:logined,";
					message += "uuid:" + wsModal.data.wsData.wsCode + ",";
					message += "content:" + preData.lastTime;
					wsModal.data.wsData.wsObject.send(message);
				} else if (preData.lastTime == 0) { // 定时器自然时间耗尽了
					// 告知服务器，关闭连接（一定要注意，连接不能从前端关闭，这样服务器端不知道何时处理收尾工作）
					message = "type:close,";
					message += "uuid:" + wsModal.data.wsData.wsCode + ",";
					message += "content:" + wsModal.data.wsData.wsQR;
					wsModal.data.wsData.wsObject.send(message);
					// 设置二维码图片 成 红叉子
					$("#qrcode").attr("src", "/weixin/img/error.png");
					// 设置相关文字的显示
					$("#title").text("二维码已过期");
					$("#lastTime").text("请刷新页面后再次尝试");
					// 一定要return出来，这样就不会执行最后一句计时器循环了。
					return;
				} else { // 服务器端传来扫码成功的信息后
					// 告知服务器，关闭连接（一定要注意，连接不能从前端关闭，这样服务器端不知道何时处理收尾工作）
					message = "type:close,";
					message += "uuid:" + wsModal.data.wsData.wsCode + ",";
					message += "content:" + wsModal.data.wsData.wsQR;
					wsModal.data.wsData.wsObject.send(message);
					// 设置二维码图片 成 载入中
					$("#qrcode").attr("src", "/weixin/img/loading.jpg");
					// 设置相关文字的显示
					$("#title").text("身份验证中...");
					$("#lastTime").text("请稍候...");
					// 一定要return出来，这样就不会执行最后一句计时器循环了。
					return;
				}

				/*
				 * 计时器循环的关键
				 *  在使用定时器的时候，一定要将需要执行的逻辑或者调用的函数放入到第一个参数，也就是匿名函数中，不然定时器会失效被立即执行。★★★★★
				 */
				setTimeout(function() {
					wsModal.op.wsOP.wsTimer();
				}, 1000);
			},
		},
	},

	// 初始化
	init : {

	}
}

window.onload = function() {
	// 先通过Ajax从服务器端获取登录UUID的qrcode路径数据———— 形如： “temp/2/10/3842-284-382-181-31.gif”的字符串返回
	wsModal.op.wsOP.wsGetQR();
	// 将通过ajax返回的图片的相对路径 存放在data.wsData
	$("#qrcode").attr("src", "/weixin/" + wsModal.data.wsData.wsQR);
	// 建立WebSocket连接，并设置onopen、onmessage、onclose三个事件处理回调函数
	wsModal.data.wsData.wsObject = new WebSocket(wsModal.data.wsData.wsURL);
	wsModal.data.wsData.wsObject.onopen = wsModal.op.wsOP.wsOpen;
	wsModal.data.wsData.wsObject.onmessage = wsModal.op.wsOP.wsMessage;
	wsModal.data.wsData.wsObject.onclose = wsModal.op.wsOP.wsClose;
	/*
	  *  启动计时器，用于倒数计时,负责轮询服务器用户是否已经通过微信完成了扫码操作
	  *  在使用定时器的时候，一定要将需要执行的逻辑或者调用的函数放入到第一个参数，也就是匿名函数中，不然定时器会失效被立即执行。★★★★★
	  */
	setTimeout(function() {
		wsModal.op.wsOP.wsTimer();
	}, 1000);
}

