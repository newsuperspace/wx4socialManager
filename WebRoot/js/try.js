/**
 * 测试服务端与前段进行Ajax或WebSocket交互的API
 */
var tryMethod = {
	data : {
		ajaxData : {
		},

		wsData : {
			lastTime : 0,
			wsURL : "ws://localhost/library/ws/websocket",
			bid : "454f2080-11f9-4a36-8f4b-f62c751679f1",
			wsObject : null
		}
	},

	init : {
		
	},

	option : {
		common : {

			// 核心功能都在这个计时器中 【通过】
			setTimer : function() {

				if (tryMethod.data.wsData.lastTime <= 0) {
					// -----------------------------------------------------------------------分支1：ws连接超时，关闭连接
					// 先向服务器发送消息，让服务器把bid从ServletContext域中删除，完成善后工作
					var message = "";
					message += "type:closing,";
					message += "bid:" + tryMethod.data.wsData.bid + ",";
					message += "lastTime:" + tryMethod.data.wsData.lastTime;
					tryMethod.data.wsData.wsObject.send(message);
					// 正式关闭连接，该方法执行后就正式关闭WebSocket连接（不能再向服务器端send信息了），并会触发ws对象的onclose事件，
					tryMethod.data.wsData.wsObject.close();
					tryMethod.option.wsOP.ableBtn(); // 初始化按钮
					return; // 终止定时器继续循环调用（防止执行本方法最后一行代码）
				} else if (20 == tryMethod.data.wsData.lastTime) {   // 千万不要把 =  当成 == ，因为正常赋值操作也会返回true，因此当这里写成lastTime = 20 后 就会一直调用这条语句。所以为了避免错误，推荐写成 20 == lastTime，这样即便写成赋值，也会报错及时发现。★★★★★
					// -----------------------------------------------------------------------分支2：20秒代表刚刚建立连接，需要服务器端发送“第一条”信息
					tryMethod.data.wsData.lastTime -=1;
					var message = "";
					message += "type:begin,";
					message += "bid:" + tryMethod.data.wsData.bid + ",";
					message += "lastTime:" + tryMethod.data.wsData.lastTime;
					console.log(message);
					tryMethod.data.wsData.wsObject.send(message);
				} else if (tryMethod.data.wsData.lastTime > 0 && tryMethod.data.wsData.lastTime < 20) {
					// ----------------------------------------------------------------------分支3：倒计时并与服务器日常交互
					tryMethod.data.wsData.lastTime -=1;
					// 更新按钮上的倒计时显示
					tryMethod.option.wsOP.disableBtn();
					console.log("倒计时："+tryMethod.data.wsData.lastTime);
				}
				
				setTimeout(function() {
					tryMethod.option.common.setTimer()
				}, 1000);
			},
		},

		ajaxOP : {
			// 测试AppCustomerAction_preChuanYue 方法【通过】
			preChuanYue : function(uid, bid) {
				var url = "appCustomerAction_preChuanYue";
				var param = {
					'uid' : uid,
					'bid' : bid
				}
				$.post(url, param, function(data) {
					if (data.result) {
						console.log(data.message);
						$("#qrcode").attr("src", "http://localhost/library/" + data.qrcode + "?time=" + new Date().getTime());
					} else {
						console.log("禁止传阅，错误信息：" + data.message);
					}
				});
			},

			// 测试AppCustomerAction_chuanYue方法  B端执行该方法，确认传阅 会终止倒计时并初始化按钮状态 【通过】
			chuanYue : function(buid) {
				var url = "appCustomerAction_chuanYue";
				var param = {
					'uid' : buid,
					'bid' : tryMethod.data.wsData.bid
				}
				$.post(url, param, function(data) {
					if (data.result) {
						console.log(data.message);
					} else {
						console.log(data.message);
					}
				});

			},
			
			// B端获取传阅信息   测试 AppCustomerAction_chuanYueInfo方法  【通过】
			chuanYueInfo: function(uid,bid){
				var url = "appCustomerAction_chuanYueInfo";
				var param = {
					'uid' : uid,
					'bid' : bid
				}
				$.post(url, param, function(data) {
					if($.isEmptyObject(data)){
						// 空JSON，说明并不存在等待被传阅的图书
						console.log("不存在等待被传阅的图书");
					}else{
						// TODO 在服务端存在这本正在等待被传阅的图书，开始根据回传的JSON对象组织B端上的用于确认传阅图书信息的dialog
						// 用户点击该dialog上的确认按钮后，才执行请求AppCusotmerAction_chuanYue 方法，实现图书的传阅
						console.log(data);
					}
				});
			}
			
		},

		wsOP : {
			// 测试开启WebSocket【通过】
			startCountDown : function() {

				// 初始化任务表的相关参数。
				tryMethod.data.wsData.lastTime = 20;
				// 使得按钮失效，并更新倒计时
				tryMethod.option.wsOP.disableBtn();

				// 开启websocket连接
				tryMethod.data.wsData.wsObject = new WebSocket(tryMethod.data.wsData.wsURL);
				
				// ws连接建立成功后会触发该事件，向服务器传递第一条消息
				tryMethod.data.wsData.wsObject.onopen = function() {
					
					/**
					 * 需要特别注意的地方就是在onopen回调中，这里只能存放一些非通讯代码
					 * 也就是说，只有在onopen执行完毕后，才能通过ws.send() 向服务端发送
					 * 消息，因此不能再onopen这里发送消息，所以只能让该回调设置一个标志
					 * canTalk是true，则代表脚本其他地方可以通过send()向服务器端发送信息
					 * 为false则代表不能发送信息。
					 */
					console.log("WS连接已经建立！");
					
					// TODO 准备dialog内容的转化（从等待gif转变成二维码图片）
					
					setTimeout(function() {
						tryMethod.option.common.setTimer()
					}, 1000);
				};
				
				// 处理从服务器传递过来的信息
				tryMethod.data.wsData.wsObject.onmessage = function(message) {
					if (message.data == "success") {
						console.log("传阅成功！");
						tryMethod.data.wsData.lastTime = 0; // -----------------------------------------------------------------------------重置倒计时0，能够让定时器回调中的判断分支提前关闭ws连接
						// TODO 执行dialog页面内容变化逻辑
						// 。。。。。
					} else if (message.data == "timeout") {
						console.log("系统超时，传阅失败");
						tryMethod.data.wsData.lastTime = 0; // -----------------------------------------------------------------------------重置倒计时0，能够让定时器回调中的判断分支提前关闭ws连接
						// TODO 关闭dialog，并弹出hint提示信息
						// 。。。。。。
					} else if (message.data == "close") {
						console.log("系统主动关闭ws连接，传阅失败");
						tryMethod.data.wsData.lastTime = 0; // -----------------------------------------------------------------------------重置倒计时0，能够让定时器回调中的判断分支提前关闭ws连接
						// TODO 关闭dialog，并弹出hint提示信息
						// 。。。。。。
					} else if(message.data == "request" ) {            
						// 向服务器端发送协议类型为running的信息内容
						// 传递消息
						var message = "";
						message += "type:running,";
						message += "bid:" + tryMethod.data.wsData.bid + ",";
						message += "lastTime:" + tryMethod.data.wsData.lastTime;
						tryMethod.data.wsData.wsObject.send(message);
					}
				};
				
				/**
				 * 当当前ws连接关闭  显式地调用 close()方法的时候
				 * 触发该回调，哦们可以这里做一些本地善后工作，但是由于ws连接已经关闭才会触发onclose事件
				 * 也就是说此时ws连接已经关闭，所以不能再向服务器端通过send()发送消息。
				 */
				tryMethod.data.wsData.wsObject.onclose = function(){
					console.log("ws连接已经关闭");
				}

			},

			// 按钮失效
			disableBtn : function() {
				$("#button").attr("disabled", "disabled");
				$("#button").val("剩余时间：" + tryMethod.data.wsData.lastTime);
			},

			// 按钮重启
			ableBtn : function() {
				$("#button").removeAttr("disabled");
				$("#button").val("A端开启传阅");
			}
		}
	}
}