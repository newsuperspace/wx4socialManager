/**
 * 
 */
var  activityModal = {
		// 初始化用
		init : {
			// 初始化数据
			initData : {
			},
			// 初始化事件
			initEvent : {
			},
		},
		
		// 操作函数
		op:{
			// 操作模态对话框的
			modalOp:{
			},
			// 通用方法
			commonOp:{
				
				// 获取某一活动的所有参与者
				getVisitors:function(param){
					var aid =  param;
					$(location).attr('href','activityAction_getVisitors.action'+'?aid='+aid);
				}
				
			}
		},
		
		// 存放数据用
		data:{
			
		}
		
}