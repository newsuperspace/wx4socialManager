var dataSource4LevelSelector = []; // 在通过showSelector()显示picker时的数据源
var defaultValue4LevelSelector = []; // picker-extend.js 所控制的层级过滤器在用户选中筛选结果后，通过数组[0、0、0、0、1] 来返回各个wheel的选中结果

var pickerObj = null; // 级联选择器操作对象

var selectedTag = null; // 用于userList.jsp页面上过滤层级用户的级联选择器选中的目标层级标签（minus_first、zero、first、second、third、fourth）
var selectedLid = null; // 用于userList.jsp页面上过滤层级用户的级联选择器选中的目标层级ID

var whereFrom = "userList"; // 用于服务器端userAction中关于人员分页查询的共用方法内区分相关请求来自于userList.jsp和managerList.jsp的哪个页面

$(function() {
	initLaypage();
	initLevelSelector();
});

// 显示层级过滤器（基于picker-extend.js的联动选择器）
function showLevelSelector() {
	pickerObj.show();
}
// 初始化层级过滤器所需要用到的数据源
function initLevelSelector() {
	let url = "userAction_initLevelSelector.action";
	let param = {
		'whereFrom' : whereFrom
	};
	$.post(url, param, function(data, textStatus, req) {
		console.log(data);
		dataSource4LevelSelector = data.dataSource;
		defaultValue4LevelSelector = data.defaultValue;
		// 初始化基于picker-extend.js的级联picker
		//假如你的数据的字段名为id,title,children
		//与mobileSelect的id,value,childs字段名不匹配
		//可以用keyMap属性进行字段名映射
		pickerObj = new PickerExtend({
			trigger : '#levelSelector',
			title : '请选择过滤层级',
			wheels : [
				{
					data : dataSource4LevelSelector
				}
			],
			keyMap : {
				id : 'value',
				value : 'label',
				childs : 'children'
			},
			callback : function(indexArr, data) {
				console.log("------indexArr------");
				console.log(indexArr);
				console.log("--------data--------");
				console.log(data);

				let index = data.length - 1; // 将索引设置到最后一位
				let value;
				if (data[index].value != "0") {
					// 说明最后一个就是要过滤的目标
					value = data[index].value;
					selectedTag = value.split("_")[0];
					selectedLid = value.split("_")[1];
				} else {
					// 需要从后向前循环，指导找到一个value！=0的对象
					index -= 1;
					let isStop = true;
					while (isStop) {
						value = data[index].value;
						if ("0" != value) {
							selectedTag = value.split("_")[0];
							selectedLid = value.split("_")[1];
							isStop = false;
						}
						index -= 1;
					}
				}
				console.log("selectedTag:" + selectedTag + "," + "selectedLid:" + selectedLid);
				initLaypage();
			}
		});
	});
}


// 初始化Laypage組件
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
					let url = "userAction_getUsersByPageLimit.action";
					let param = {
						'whereFrom' : whereFrom,
						// 【层级过滤】如果前端通过picker选择了目标过滤层级，则这两个参数是有值的，否则后端得到的是null
						"selectedTag" : selectedTag,
						"selectedLid" : selectedLid,
						// 【分页查询】下面两个参数是由LayUI提供的，分别是目标页码和每页数据条目数
						"targetPageNum" : targetPageNum,
						"pageItemNumLimit" : pageItemNumLimit
					}
					$.post(url, param, function(data, textStatus, req) {
						// 定位表格体
						let table = $("#tbody");
						// 清空表格体中的数据
						table.empty();
						// 重新组织数据
						for (let i = 0; i < data.users.length; i++) {
							let tr = $("<tr></tr>");
							let name = $("<td><a href='#' onclick='userModal.op.userInfo(\"" + data.users[i].uid + "\")'>" + data.users[i].username + "</a></td>");
							let sickname = $("<td>" + data.users[i].sickname + "</td>");
							let sex = $("<td>" + data.users[i].sex + "</td>");
							let age = $("<td>" + data.users[i].age + "</td>");
							let serveTime = $("<td>" + data.users[i].serveTime + "</td>");
							let score = $("<td><a href='#' onclick='toUserVisitList(\"" + data.users[i].uid + "\");'>" + data.users[i].score + "</a></td>");
							let phone = $("<td>" + data.users[i].phone + "</td>");
							let address = $('<td class="text-truncate" data-toggle="tooltip" title="' + data.users[i].address + '">' + data.users[i].address + '</td>');
							let email = $("<td>" + data.users[i].email + "</td>");

							let cardid = $("<td>" + data.users[i].cardid + "</td>");
							let registrationTimeStr = $("<td>" + data.users[i].registrationTimeStr + "</td>");
							let ishere = $("<td></td>");
							if (data.users[i].ishere) {
								ishere.text("是");
							} else {
								ishere.text("否");
							}
							let locked = $("<td></td>");
							if (data.users[i].locked) {
								let span = $('<span class="badge badge-danger">锁死</span>');
								locked.append(span);
							} else {
								let span = $('<span class="badge badge-success">正常</span>');
								locked.append(span);
							}

							let btns = $('<td><div class="btn-group" role="group"><button type="button" class="btn btn-outline-secondary btn-sm" onclick="alert(\'' + data.users[i].uid + '\');">通知</button><button type="button" class="btn btn-outline-secondary btn-sm">其他</button></div></td>');
							// 拼装
							tr.append(name).append(sickname).append(sex).append(age).append(serveTime).append(score).append(phone).append(address).append(email).append(cardid).append(registrationTimeStr).append(ishere).append(locked).append(btns);
							// 将新建行显示在页面上
							table.append(tr);
						}
					});
				}
			}
		});
	});
}

// 基于AJAX————从数据库获取如下内容：
// （1） 总条目数并作为方法返回值
// （2）第一页的20个数据，并在页面上组建并显示
function getCountandCreateFirstPage4InitLaypage() {
	let count = 0;

	let url = "userAction_getCountandCreateFirstPage4InitLaypage.action";
	let param = {
		"whereFrom":whereFrom,
		// 【层级过滤】如果前端通过picker选择了目标过滤层级，则这两个参数是有值的，否则后端得到的是null
		"selectedTag" : selectedTag,
		"selectedLid" : selectedLid,
	}

	$.ajaxSetup({
		async : false // 全局设置Ajax为同步执行
	});

	let startTime = new Date().getTime();
	$.post(url, param, function(data, textStatus, req) {
		let endTime = new Date().getTime();
		console.log("=========js4userList.initLaypage服务器响应速度:"+(endTime-startTime)+"毫秒========")

		count = data.count;
		// 定位表格体
		let table = $("#tbody");
		table.empty();
		for (let i = 0; i < data.users.length; i++) {
			let tr = $("<tr></tr>");
			let name = $("<td><a href='#' onclick='userModal.op.userInfo(\"" + data.users[i].uid + "\")'>" + data.users[i].username + "</a></td>");
			let sickname = $("<td>" + data.users[i].sickname + "</td>");
			let sex = $("<td>" + data.users[i].sex + "</td>");
			let age = $("<td>" + data.users[i].age + "</td>");
			let serveTime = $("<td>" + data.users[i].serveTime + "</td>");
			let score = $("<td><a href='#' onclick='toUserVisitList(\"" + data.users[i].uid + "\");'>" + data.users[i].score + "</a></td>");
			let phone = $("<td>" + data.users[i].phone + "</td>");
			let address = $('<td class="text-truncate" data-toggle="tooltip" title="' + data.users[i].address + '">' + data.users[i].address + '</td>');
			let email = $("<td>" + data.users[i].email + "</td>");

			let cardid = $("<td>" + data.users[i].cardid + "</td>");
			let registrationTimeStr = $("<td>" + data.users[i].registrationTimeStr + "</td>");
			let ishere = $("<td></td>");
			if (data.users[i].ishere) {
				ishere.text("是");
			} else {
				ishere.text("否");
			}
			let locked = $("<td></td>");
			if (data.users[i].locked) {
				let span = $('<span class="badge badge-danger">锁死</span>');
				locked.append(span);
			} else {
				let span = $('<span class="badge badge-success">正常</span>');
				locked.append(span);
			}

			let btns = $('<td><div class="btn-group" role="group"><button type="button" class="btn btn-outline-secondary btn-sm" onclick="alert(\'' + data.users[i].uid + '\');">通知</button><button type="button" class="btn btn-outline-secondary btn-sm">其他</button></div></td>');
			// 拼装
			tr.append(name).append(sickname).append(sex).append(age).append(serveTime).append(score).append(phone).append(address).append(email).append(cardid).append(registrationTimeStr).append(ishere).append(locked).append(btns);
			// 将新建行显示在页面上
			table.append(tr);
		}

	});

	$.ajaxSetup({
		async : true // 恢复全局设置Ajax为异步执行
	});

	return count;
}


//点击用户的分值，跳转到用户参与活动的历史界面
function toUserVisitList(uid) {
	$(location).attr("href", "userAction_toUserVisitList.action?uid=" + uid);
}