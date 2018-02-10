define(function(require) {

	var $ = require("jquery");
	var justep = require("$UI/system/lib/justep");

	// =====================================================以下才是基于WebSocket协议的传阅功能逻辑部分=================================================
	// var cyMethod = {
	// data : {
	// wsData : {
	// wsURL : "ws://192.168.23.1/library/ws/websocket",
	// wsObject : null,
	// result : false,
	// resultMessage : "已取消传阅"
	// }
	// },
	//
	// option : {
	// common : {
	// // 核心功能都在这个计时器中 【通过】
	// setTimer : function(self) {
	//
	// var lastTime = self.comp("data").getCurrentRow().val("lastTime");
	// var bid = self.comp("data").getCurrentRow().val("bid");
	//
	// var message = "";
	// if (lastTime <= 0) {
	// //
	// -----------------------------------------------------------------------分支1：ws连接超时，关闭连接
	// // 先向服务器发送消息，让服务器把bid从ServletContext域中删除，完成善后工作
	// message += "type:closing,";
	// message += "bid:" + bid + ",";
	// message += "lastTime:" + lastTime;
	// cyMethod.data.wsData.wsObject.send(message);
	// // 正式关闭连接，该方法执行后就正式关闭WebSocket连接（不能再向服务器端send信息了），并会触发ws对象的onclose事件，
	// cyMethod.data.wsData.wsObject.close();
	// return; // 终止定时器继续循环调用（防止执行本方法最后一行代码）
	// } else if (20 == lastTime) { // 千万不要把 = 当成 ==
	// // ，因为正常赋值操作也会返回true，因此当这里写成lastTime
	// // = 20 后
	// // 就会一直调用这条语句。所以为了避免错误，推荐写成
	// // 20 ==
	// // lastTime，这样即便写成赋值，也会报错及时发现。★★★★★
	// //
	// -----------------------------------------------------------------------分支2：20秒代表刚刚建立连接，需要服务器端发送“第一条”信息
	// self.comp("data").getCurrentRow().val("lastTime", lastTime - 1);
	// message += "type:begin,";
	// message += "bid:" + bid + ",";
	// message += "lastTime:" + lastTime;
	// cyMethod.data.wsData.wsObject.send(message);
	// } else if (lastTime > 0 && lastTime < 20) {
	// //
	// ----------------------------------------------------------------------分支3：倒计时并与服务器日常交互
	// self.comp("data").getCurrentRow().val("lastTime", lastTime - 1);
	// // 更新dialog倒计时
	// cyMethod.option.wsOP.refreshTimer(self);
	// }
	//
	// setTimeout(function() {
	// cyMethod.option.common.setTimer(self);
	// }, 1000);
	// },
	// },
	//
	// wsOP : {
	// // 测试开启WebSocket【通过】
	// startChuanYue : function(self) {
	//
	// // 初始化任务表的相关参数。
	// self.comp("data").getCurrentRow().val("lastTime", 20);
	// // 初始化结果标志位为false
	// cyMethod.data.wsData.result = false;
	// cyMethod.data.wsData.resultMessage = "已取消传阅";
	// // 开启websocket连接
	// cyMethod.data.wsData.wsObject = new
	// WebSocket(cyMethod.data.wsData.wsURL);
	//
	// // ws连接建立成功后会触发该事件，向服务器传递第一条消息
	// cyMethod.data.wsData.wsObject.onopen = function() {
	//
	// /**
	// * 需要特别注意的地方就是在onopen回调中，这里只能存放一些非通讯代码
	// * 也就是说，只有在onopen执行完毕后，才能通过ws.send() 向服务端发送
	// * 消息，因此不能再onopen这里发送消息，所以只能让该回调设置一个标志
	// * canTalk是true，则代表脚本其他地方可以通过send()向服务器端发送信息
	// * 为false则代表不能发送信息。
	// */
	// console.log("WS连接已经建立！");
	// // 开启20秒定时器
	// setTimeout(cyMethod.option.common.setTimer(self), 1000);
	// // TODO 准备dialog内容的转化（从等待gif转变成二维码图片）
	// cyMethod.option.wsOP.showQRcodeDialog(self);
	// };
	//
	// // 处理从服务器传递过来的信息
	// cyMethod.data.wsData.wsObject.onmessage = function(message) {
	// if (message.data == "success") {
	// self.comp("data").getCurrentRow().val("lastTime", 0); //
	// -----------------------------------------------------------------------------重置倒计时0，能够让定时器回调中的判断分支提前关闭ws连接
	// // TODO
	// // 传阅成功，修改标志位result为true，用以告诉showResultDialog，应该组织绿对勾页面
	// cyMethod.data.wsData.result = true;
	// cyMethod.data.wsData.resultMessage = "恭喜你，图书传阅成功！";
	// } else if (message.data == "timeout") {
	// self.comp("data").getCurrentRow().val("lastTime", 0); //
	// -----------------------------------------------------------------------------重置倒计时0，能够让定时器回调中的判断分支提前关闭ws连接
	// // TODO
	// // 传阅失败，修改标志位result为false，用以告诉showResultDialog，应该组织红叉子页面
	// cyMethod.data.wsData.result = false;
	// cyMethod.data.wsData.resultMessage = "操作超时传阅失败，请重试！";
	// } else if (message.data == "close") {
	// self.comp("data").getCurrentRow().val("lastTime", 0); //
	// -----------------------------------------------------------------------------重置倒计时0，能够让定时器回调中的判断分支提前关闭ws连接
	// // TODO
	// // 传阅失败，修改标志位result为false，用以告诉showResultDialog，应该组织红叉子页面
	// cyMethod.data.wsData.result = false;
	// cyMethod.data.wsData.resultMessage = "系统异常传阅失败，请重试！";
	// } else if (message.data == "request") {
	// // 向服务器端发送协议类型为running的信息内容
	// // 传递消息
	// var returnMessage = "";
	// returnMessage += "type:running,";
	// returnMessage += "bid:" + self.comp("data").getCurrentRow().val("bid") +
	// ",";
	// returnMessage += "lastTime:" +
	// self.comp("data").getCurrentRow().val("lastTime");
	// cyMethod.data.wsData.wsObject.send(returnMessage);
	// }
	// };
	//
	// /**
	// * 当当前ws连接关闭 显式地调用 close()方法的时候
	// * 触发该回调，哦们可以这里做一些本地善后工作，但是由于ws连接已经关闭才会触发onclose事件
	// * 也就是说此时ws连接已经关闭，所以不能再向服务器端通过send()发送消息。
	// */
	// cyMethod.data.wsData.wsObject.onclose = function() {
	// console.log("ws连接已经关闭");
	// cyMethod.data.wsData.wsObject = null;
	// // 组织展示结果的dialog
	// cyMethod.option.wsOP.showResultDialog(self);
	// };
	// },
	//
	// // 准备转圈圈的载入中的dialog
	// showPrepareDialog : function(self) {
	// // 设置loading图片
	// self.test.set(require.toUrl("./image/loading.png"));
	// // 设置提示文字
	// self.font.set("数据加载中...");
	// },
	//
	// // WebSocket连接已经开启，展示二维码和倒计时的dialog
	// showQRcodeDialog : function(self) {
	// // 设置二维码图片
	// var url = self.comp("data").getCurrentRow().val("qrcode");
	// self.test.set(url);
	// // 设置倒计时
	// self.font.set("剩余时间" + self.comp("data").getCurrentRow().val("lastTime")
	// + "秒");
	// },
	//
	// // 刷新倒计时
	// refreshTimer : function(self) {
	// self.font.set("剩余时间" + self.comp("data").getCurrentRow().val("lastTime")
	// + "秒");
	// },
	//
	// // 展示传阅结果的dialog
	// showResultDialog : function(self) {
	// if (cyMethod.data.wsData.result) {
	// // 传阅成功,绿对勾
	// self.test.set(require.toUrl("./image/bingo.png"));
	// self.font.set(cyMethod.data.wsData.resultMessage);
	// } else {
	// // 传阅失败，红叉子
	// self.test.set(require.toUrl("./image/error.png"));
	// self.font.set(cyMethod.data.wsData.resultMessage);
	// }
	// },
	// }
	// }
	// };

	// =====================================================以下才是WEX5的开发逻辑部分=================================================
	var Model = function() {
		this.callParent();
		// 为了能动态（通过代码）修改Image的bind-attr-src属性，以达到动态更换图片的目的就需要设置这样的KO对象
		// 然后就可以在设计界面，将image的bind-attr-src属性设置成 $model.test
		// 之后我们只需要通过JS代码书写 this.test.set(require.toUrl("./gif/bingo.png"));
		// 就可以实现动态更换图片的目的了。
		// 这又是一个脚本部分与前段UI部分的bind-*属性交互的方式，之前我们接触过预先在脚本中写入
		// Model.prototype.xxxx()方法后
		// 就可以在前端的bind-*属性中通过$model.xxxx()来调用该方法了；
		// 而这里我们用到的方式则相反，是与预先为前端的bind-* 属性设定一个 以
		// $model开头的xxx属性，这样在脚本中就可以通过对this.xxx设值来动态对bind-*属性设值了
		// ★★★ 这是一个很重要的窍门
		this.test = justep.Bind.observable(require.toUrl("./image/loading.jpg"));
		this.font = justep.Bind.observable("加载中...");
	};

	Model.prototype.toUrl = function(url) {
		return url ? require.toUrl(url) : "";
	};

	// borrowList.w 页面传递来了数据，作为开启当前dialog的准备传阅的依据
	Model.prototype.wReceiverReceive = function(event) {
		var self = this; // ★

		// 准备传阅
		var data = event.data; // 得到从borrowList.w页面传递过来的数据,是一个json对象
		var arr = [ data ];
		self.comp("data").loadData(arr);
		self.comp("data").first();

		// 设置二维码图片
		var url = self.comp("data").getCurrentRow().val("qrcode");
		self.test.set(url);
		self.font.set("扫码签到")
	};

	Model.prototype.cancel = function(event) {
		var self = this;
		// 直接关闭dialog，返回list.w页面就好了
		self.comp("wReceiver").windowCancel();
	};

	return Model;
});