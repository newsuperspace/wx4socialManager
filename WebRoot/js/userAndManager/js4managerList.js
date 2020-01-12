var dataSource4LevelSelector = []; // 在通过showSelector()显示picker时的数据源
var defaultValue4LevelSelector = []; // picker-extend.js 所控制的层级过滤器在用户选中筛选结果后，通过数组[0、0、0、0、1] 来返回各个wheel的选中结果

var pickerObj = null; // 级联选择器操作对象

var whereFrom = "managerList"; // 用于服务器端userAction中关于人员分页查询的共用方法内区分相关请求来自于userList.jsp和managerList.jsp的哪个页面

var groupTag = null; // 用于过滤managerList.jsp页面上过滤不同group.tag的标签

$(function() {
	initLaypage();
	initGroupTagSelector();
});

// 显示层级过滤器（基于picker-extend.js的联动选择器）
function showGroupTagSelector() {
	pickerObj.show();
}
// 初始化层级过滤器所需要用到的数据源
function initGroupTagSelector() {
	let url = "userAction_initGroupTagSelector.action";
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
			trigger : '#groupTagSelector',
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
				let value = data[index].value;
				groupTag = value;
				console.log("groupTag:" + groupTag);
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
						// 【grouping.tag标签过滤】如果前端通过picker选择了目标过滤层级，则这个参数是有值的，否则后端得到的是null并被设置为“all”意思是查询所有标签
						"groupTag" : groupTag,
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
						for (let i = 0; i < data.users.length; i++) {
							let tr = $("<tr></tr>");
							let name = $("<td><a href='#' onclick='userModal.op.userInfo(\"" + data.users[i].uid + "\")'>" + data.users[i].username + "</a></td>");
							let score = $("<td><a href='#' onclick='toUserVisitList(\"" + data.users[i].uid + "\");'>" + data.users[i].score + "</a></td>");
							let sickname = $("<td>" + data.users[i].sickname + "</td>");
							let openid = $('<td class="text-truncate" data-toggle="tooltip" title="' + data.users[i].openid + '">' + data.users[i].openid + '</td>');
							let group = $('<td><span class="badge badge-success">' + data.users[i].member.grouping.groupName + '</span></td>');

							let levels = $("<td></td>");
							if (null == data.users[i].member.managers || 0 == data.users[i].member.managers.length) {
								levels.text("未匹配");
							} else {
								let link = $('<a href="#" onclick="managerModal.op.toManagedLevelList(\'' + data.users[i].member.memberid + '\');"></a>');
								let content = "";
								if (data.users[i].member.managers.length > 3) {
									// 管理的子层级数量大于3
									for (let j = 0; j < 3; j++) {
										switch (data.users[i].member.grouping.tag) {
										case "minus_first":
											if (2 == j) {
												content = content + data.users[i].member.managers[j].minusFirstLevel.name + "...";
											} else {
												content = content + data.users[i].member.managers[j].minusFirstLevel.name + ",";
											}
											break;
										case "zero":
											if (2 == j) {
												content = content + data.users[i].member.managers[j].zeroLevel.name + "...";
											} else {
												content = content + data.users[i].member.managers[j].zeroLevel.name + ",";
											}
											break;
										case "first":
											if (2 == j) {
												content = content + data.users[i].member.managers[j].firstLevel.name + "...";
											} else {
												content = content + data.users[i].member.managers[j].firstLevel.name + ",";
											}
											break;
										case "second":
											if (2 == j) {
												content = content + data.users[i].member.managers[j].secondLevel.name + "...";
											} else {
												content = content + data.users[i].member.managers[j].secondLevel.name + ",";
											}
											break;
										case "third":
											if (2 == j) {
												content = content + data.users[i].member.managers[j].thirdLevel.name + "...";
											} else {
												content = content + data.users[i].member.managers[j].thirdLevel.name + ",";
											}
											break;
										case "fourth":
											if (2 == j) {
												content = content + data.users[i].member.managers[j].fourthLevel.name + "...";
											} else {
												content = content + data.users[i].member.managers[j].fourthLevel.name + ",";
											}
											break;
										}
									}
								} else {
									// 管理的子层级数量不足3个
									for (let j = 0; j < data.users[i].member.managers.length; j++) {
										switch (data.users[i].member.grouping.tag) {
										case "minus_first":
											if (data.users[i].member.managers.length == (j + 1)) {
												content = content + data.users[i].member.managers[j].minusFirstLevel.name;
											} else {
												content = content + data.users[i].member.managers[j].minusFirstLevel.name + ",";
											}
											break;
										case "zero":
											if (data.users[i].member.managers.length == (j + 1)) {
												content = content + data.users[i].member.managers[j].zeroLevel.name;
											} else {
												content = content + data.users[i].member.managers[j].zeroLevel.name + ",";
											}
											break;
										case "first":
											if (data.users[i].member.managers.length == (j + 1)) {
												content = content + data.users[i].member.managers[j].firstLevel.name;
											} else {
												content = content + data.users[i].member.managers[j].firstLevel.name + ",";
											}
											break;
										case "second":
											if (data.users[i].member.managers.length == (j + 1)) {
												content = content + data.users[i].member.managers[j].secondLevel.name;
											} else {
												content = content + data.users[i].member.managers[j].secondLevel.name + ",";
											}
											break;
										case "third":
											if (data.users[i].member.managers.length == (j + 1)) {
												content = content + data.users[i].member.managers[j].thirdLevel.name;
											} else {
												content = content + data.users[i].member.managers[j].thirdLevel.name + ",";
											}
											break;
										case "fourth":
											if (data.users[i].member.managers.length == (j + 1)) {
												content = content + data.users[i].member.managers[j].fourthLevel.name;
											} else {
												content = content + data.users[i].member.managers[j].fourthLevel.name + ",";
											}
											break;
										}
									}
								}
								link.text(content);
								levels.append(link);
							}

							let phone = $("<td>" + data.users[i].phone + "</td>");

							let btns = $('<td></td>');
							let btnGroup = $('<div class="btn-group" role="group"></div>');
							let btn1 = $('<a class="btn btn-outline-secondary btn-sm" href="#" onclick="managerModal.op.showAppointModal(\'' + data.users[i].uid + '\');">任命</a>');
							let btn2 = $('<a class="btn btn-outline-secondary btn-sm" href="#" onclick="userModal.op.showUpdateUserModal(\'' + data.users[i].uid + '\');">修改</a>');
							let btn3 = $('<a class="btn btn-outline-secondary btn-sm" href="#" onclick="managerModal.op.showAssignedUserModal(\'' + data.users[i].uid + '\');">分配</a>');
							let btn4 = $('<button type="button" class="btn btn-outline-secondary btn-sm">其他</button>');
							btnGroup.append(btn1).append(btn2).append(btn3).append(btn4);
							btns.append(btnGroup);

							// 拼装
							tr.append(name).append(score).append(sickname).append(openid).append(group).append(levels).append(phone).append(btns);
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
// （2）第一页的10个数据，并在页面上组建并显示
function getCountandCreateFirstPage4InitLaypage() {
	let count = 0;

	let url = "userAction_getCountandCreateFirstPage4InitLaypage.action";
	let param = {
		"whereFrom" : whereFrom,
		"groupTag": groupTag
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
		for (let i = 0; i < data.users.length; i++) {
			let tr = $("<tr></tr>");
			let name = $("<td><a href='#' onclick='userModal.op.userInfo(\"" + data.users[i].uid + "\")'>" + data.users[i].username + "</a></td>");
			let score = $("<td><a href='#' onclick='toUserVisitList(\"" + data.users[i].uid + "\");'>" + data.users[i].score + "</a></td>");
			let sickname = $("<td>" + data.users[i].sickname + "</td>");
			let openid = $('<td class="text-truncate" data-toggle="tooltip" title="' + data.users[i].openid + '">' + data.users[i].openid + '</td>');
			let group = $('<td><span class="badge badge-success">' + data.users[i].member.grouping.groupName + '</span></td>');

			let levels = $("<td></td>");
			if (null == data.users[i].member.managers || 0 == data.users[i].member.managers.length) {
				levels.text("未匹配");
			} else {
				let link = $('<a href="#" onclick="managerModal.op.toManagedLevelList(\'' + data.users[i].member.memberid + '\');"></a>');
				let content = "";
				if (data.users[i].member.managers.length > 3) {
					// 管理的子层级数量大于3
					for (let j = 0; j < 3; j++) {
						switch (data.users[i].member.grouping.tag) {
						case "minus_first":
							if (2 == j) {
								content = content + data.users[i].member.managers[j].minusFirstLevel.name + "...";
							} else {
								content = content + data.users[i].member.managers[j].minusFirstLevel.name + ",";
							}
							break;
						case "zero":
							if (2 == j) {
								content = content + data.users[i].member.managers[j].zeroLevel.name + "...";
							} else {
								content = content + data.users[i].member.managers[j].zeroLevel.name + ",";
							}
							break;
						case "first":
							if (2 == j) {
								content = content + data.users[i].member.managers[j].firstLevel.name + "...";
							} else {
								content = content + data.users[i].member.managers[j].firstLevel.name + ",";
							}
							break;
						case "second":
							if (2 == j) {
								content = content + data.users[i].member.managers[j].secondLevel.name + "...";
							} else {
								content = content + data.users[i].member.managers[j].secondLevel.name + ",";
							}
							break;
						case "third":
							if (2 == j) {
								content = content + data.users[i].member.managers[j].thirdLevel.name + "...";
							} else {
								content = content + data.users[i].member.managers[j].thirdLevel.name + ",";
							}
							break;
						case "fourth":
							if (2 == j) {
								content = content + data.users[i].member.managers[j].fourthLevel.name + "...";
							} else {
								content = content + data.users[i].member.managers[j].fourthLevel.name + ",";
							}
							break;
						}
					}
				} else {
					// 管理的子层级数量不足3个
					for (let j = 0; j < data.users[i].member.managers.length; j++) {
						switch (data.users[i].member.grouping.tag) {
						case "minus_first":
							if (data.users[i].member.managers.length == (j + 1)) {
								content = content + data.users[i].member.managers[j].minusFirstLevel.name;
							} else {
								content = content + data.users[i].member.managers[j].minusFirstLevel.name + ",";
							}
							break;
						case "zero":
							if (data.users[i].member.managers.length == (j + 1)) {
								content = content + data.users[i].member.managers[j].zeroLevel.name;
							} else {
								content = content + data.users[i].member.managers[j].zeroLevel.name + ",";
							}
							break;
						case "first":
							if (data.users[i].member.managers.length == (j + 1)) {
								content = content + data.users[i].member.managers[j].firstLevel.name;
							} else {
								content = content + data.users[i].member.managers[j].firstLevel.name + ",";
							}
							break;
						case "second":
							if (data.users[i].member.managers.length == (j + 1)) {
								content = content + data.users[i].member.managers[j].secondLevel.name;
							} else {
								content = content + data.users[i].member.managers[j].secondLevel.name + ",";
							}
							break;
						case "third":
							if (data.users[i].member.managers.length == (j + 1)) {
								content = content + data.users[i].member.managers[j].thirdLevel.name;
							} else {
								content = content + data.users[i].member.managers[j].thirdLevel.name + ",";
							}
							break;
						case "fourth":
							if (data.users[i].member.managers.length == (j + 1)) {
								content = content + data.users[i].member.managers[j].fourthLevel.name;
							} else {
								content = content + data.users[i].member.managers[j].fourthLevel.name + ",";
							}
							break;
						}
					}
				}
				link.text(content);
				levels.append(link);
			}

			let phone = $("<td>" + data.users[i].phone + "</td>");

			let btns = $('<td></td>');
			let btnGroup = $('<div class="btn-group" role="group"></div>');
			let btn1 = $('<a class="btn btn-outline-secondary btn-sm" href="#" onclick="managerModal.op.showAppointModal(\'' + data.users[i].uid + '\');">任命</a>');
			let btn2 = $('<a class="btn btn-outline-secondary btn-sm" href="#" onclick="userModal.op.showUpdateUserModal(\'' + data.users[i].uid + '\');">修改</a>');
			let btn3 = $('<a class="btn btn-outline-secondary btn-sm" href="#" onclick="managerModal.op.showAssignedUserModal(\'' + data.users[i].uid + '\');">分配</a>');
			let btn4 = $('<button type="button" class="btn btn-outline-secondary btn-sm">其他</button>');
			btnGroup.append(btn1).append(btn2).append(btn3).append(btn4);
			btns.append(btnGroup);

			// 拼装
			tr.append(name).append(score).append(sickname).append(openid).append(group).append(levels).append(phone).append(btns);
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