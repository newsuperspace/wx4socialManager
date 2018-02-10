define(function(require) {
	var $ = require("jquery");
	var justep = require("$UI/system/lib/justep");

	return {

		/*
		 * 获得指定请求参数名的请求参数值。
		 * 该方法主要是用来获取OAuth2.0页面认证授权后，微信服务器通过请求转发方式跳转到当前真正业务页面时通常
		 * 会以redirect_uri/?code=CODE&state=STATE形式将进一步获取access_token的code票据以请求参数的形式
		 * 传递过来，此方法就是专门用来获取该请求参数值而建立的
		 * 
		 * 方法调用的参数名就是 "code"
		 * 方法调用返回的结果就是code的值或者是null
		 */
		getParam : function(paramName) {

			var reg = new RegExp("(^|&)" + paramName + "=([^&]*)(&|$)"); // 构造一个含有目标参数的正则表达式对象
			var r = window.location.search.substr(1).match(reg); // 匹配目标参数
			if (r != null)
				return unescape(r[2]);
			return null; // 返回参数值
		},
		
		/**
		 * 获取服务器的根路径URL
		 * 然后根据需要自己拼接后续的部分
		 */
		getServiceUrl: function(){
			var result = "";
			
			// 一定要记住！！！！ ★★★
			//"./"代表当前文件同级目录
			// "../"代表当前文件上一级目录
			// 当前要找到目录中的文件，因此必须先用"../"返回到/js同级目录中，然后制定是该同级目录下的/config目录下的ServiceConfig.json文件
			url = require.toUrl("../config/ServiceConfig.json");
			$.ajaxSettings.async = false;  // 设置同步请求
//			$.ajaxSetup({
//		        async: false
//		    });
			$.getJSON(url, function(data) {
				// 此时参数data就是本地种指定路径的json文件的JSON对象
				$.ajaxSettings.async = true;
				result = data[0].serviceUrl;
			});
			// 返回的通常是服务器的根路径URL如——————http://localhost/weixin 这样的字符串
			return result;
		}
			
		
		
	}

});