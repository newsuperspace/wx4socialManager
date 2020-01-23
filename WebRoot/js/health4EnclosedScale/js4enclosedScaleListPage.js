
// =================全局变量=================



// ================页面初始化工作============
$(function(){
	initLaypage();
});



// ===================Methods====================
// 从服务器获取量表详细信息，显示特定页面单元中并展示出来
function enclosedScaleInfo(esid) {
	weui.alert(esid);
}


//初始化Laypage組件
function initLaypage() {
	layui.use('laypage', function() {
		var laypage = layui.laypage;
		//执行一个laypage实例
		laypage.render({
			elem : 'laypage', //注意，这里的 test1 是 ID，不用加 # 号
			count : getCountandCreateFirstPage4InitLaypage(), //数据总数，从服务端得到
			layout : [ 'count', 'prev', 'page', 'next', 'limit', 'refresh', 'skip' ],
			limit : 10,
			limits : [ 10, 15, 20, 25, 30 ],
			prev : "<",
			next : ">",
			first : "首页",
			last : "尾页",
			// theme : '#1E9FFF',
			jump : function(obj, first) {
				//obj包含了当前分页的所有参数，比如：
				let targetPageNum = obj.curr; //得到目标页页码（从1开始），以便向服务端请求对应页的数据。
				let pageItemNumLimit = obj.limit; //得到每页显示的条数
				let pages = obj.pages; // 总页数
				let count = obj.count; // 总记录数
				//首次不执行
				if (first) {
					// 首次，不执行
				} else {
					// 非首次执行，基于AJAX获取数据
					let url = "healthAction_getEssByPageLimit.action";
					let param = {
						// 【分页查询】下面两个参数是由LayUI提供的，分别是目标页码和每页数据条目数
						"targetPageNum" : targetPageNum,
						"pageItemNumLimit" : pageItemNumLimit
					}
					$.post(url, param, function(data, textStatus, req) {
						console.log(data);
						// 定位表格体
						let table = $("#tbody");
						// 清空表格体中的数据
						table.empty();
						// 重新组织数据
						for (let i = 0; i < data.enclosedScales.length; i++) {
							let es = data.enclosedScales[i];
							let tr = $("<tr></tr>");
							let chName = $('<td><a href="#" onclick="enclosedScaleInfo(\''+es.esid+'\')">'+es.chName+'</a></td>');
							let engName = $('<td>'+es.engName+'</td>');
							let date = $('<td>'+es.date+'</td>');
							let version = $('<td>'+es.version+'</td>');
							let factorNum = $('<td>'+es.factorNum+'</td>');
							let topicNum = $('<td>'+es.topicNum+'</td>');
							let sampleNum = $('<td>'+es.sampleNum+'</td>');
							
							let btnGroupTd = $('<td></td>');
							let btnGroup = $('<div class="btn-group" role="group"></div>');
							let btn1 = $('<button type="button" class="btn btn-outline-secondary btn-sm">新样本</button>')
							let btn2 = $('<button type="button" class="btn btn-outline-secondary btn-sm">旧样本</button>')
							let btn3 = $('<button type="button" class="btn btn-outline-secondary btn-sm">更新</button>')
							btnGroup.append(btn1).append(btn2).append(btn3);
							btnGroupTd.append(btnGroup);
							
							// 拼装
							tr.append(chName).append(engName).append(date).append(version).append(factorNum).append(topicNum).append(sampleNum).append(btnGroupTd);
							// 将新建行显示在页面上
							table.append(tr);
						}
						
					});
				}
			}
		});
	});
}

//基于AJAX————从数据库获取如下内容：
//（1） 总条目数并作为方法返回值
//（2）第一页的20个数据，并在页面上组建并显示
function getCountandCreateFirstPage4InitLaypage() {
	let count = 0;

	let url = "healthAction_getCountandCreateFirstPage4InitLaypage4EslistPage.action";
	let param = {
		// 请求参数，如果前端通过picker选择了目标过滤层级，则这两个参数是有值的，否则后端得到的是null
	}

	$.ajaxSetup({
		async : false // 全局设置Ajax为同步执行
	});

	$.post(url, param, function(data, textStatus, req) {
		count = data.count;
		console.log(data);
		// 定位表格体
		let table = $("#tbody");
		table.empty();
		// 开始组织首页数据
		for (let i = 0; i < data.enclosedScales.length; i++) {
			let es = data.enclosedScales[i];
			let tr = $("<tr></tr>");
			let chName = $('<td><a href="#" onclick="enclosedScaleInfo(\''+es.esid+'\')">'+es.chName+'</a></td>');
			let engName = $('<td>'+es.engName+'</td>');
			let date = $('<td>'+es.date+'</td>');
			let version = $('<td>'+es.version+'</td>');
			let factorNum = $('<td>'+es.factorNum+'</td>');
			let topicNum = $('<td>'+es.topicNum+'</td>');
			let sampleNum = $('<td>'+es.sampleNum+'</td>');
			
			let btnGroupTd = $('<td></td>');
			let btnGroup = $('<div class="btn-group" role="group"></div>');
			let btn1 = $('<button type="button" class="btn btn-outline-secondary btn-sm">新样本</button>')
			let btn2 = $('<button type="button" class="btn btn-outline-secondary btn-sm">旧样本</button>')
			let btn3 = $('<button type="button" class="btn btn-outline-secondary btn-sm">更新</button>')
			btnGroup.append(btn1).append(btn2).append(btn3);
			btnGroupTd.append(btnGroup);
			
			// 拼装
			tr.append(chName).append(engName).append(date).append(version).append(factorNum).append(topicNum).append(sampleNum).append(btnGroupTd);
			// 将新建行显示在页面上
			table.append(tr);
		}
	});

	$.ajaxSetup({
		async : true // 恢复全局设置Ajax为异步执行
	});

	return count;
}