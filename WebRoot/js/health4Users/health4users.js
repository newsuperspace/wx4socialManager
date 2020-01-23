// 在通过showSelector()显示picker时的数据源
var dataSource4LevelSelector = [];
var defaultValue4LevelSelector = [];
var pickerObj = null; // 级联选择器操作对象
var selectedTag = null; // 用于过滤层级用户的级联选择器选中的目标层级标签（minus_first、zero、first、second、third、fourth）
var selectedLid = null; // 用于过滤层级用户的级联选择器选中的目标层级ID

// 用于存放通过 initEnclosedScaleTips 从服务器端获取的所有可用量表的数据信息，共给showEnclosedScaleSelector显示选择器用
var dataSource4EnclosedScaleSelector = [];

$(function() {
	initLaypage();
	initEnclosedScaleSelector();
	initLevelSelector();
});

// 显示层级过滤器（基于picker-extend.js的联动选择器）
function showLevelSelector() {
	pickerObj.show();
}
// 初始化层级过滤器所需要用到的数据源
function initLevelSelector() {
	let url = "healthAction_initLevelSelector.action";
	let param = null;
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



// 从服务器端获取所有可用量表的数据信息
function initEnclosedScaleSelector() {
	// TODO 基于AJAX向服务器端获取可用量表的数据信息，并解析
	for (let i = 0; i < 10; i++) {
		let tip = {};
		tip['label'] = "量表" + i; // 量表名
		tip['value'] = i; // 量表的esid
		dataSource4EnclosedScaleSelector.push(tip);
	}
}

function showEnclosedScaleSelector(uid) {
	weui.picker(enclosedScaleTips, {
		className : 'custom-classname',
		container : 'body',
		defaultValue : [ 0 ],
		onChange : function(result) {
			console.log(result)
		},
		onConfirm : function(result) {
			console.log(result)
			let name = result[0].label; // 获取到选择的量表名
			let esid = result[0].value; // 获取到选择的
			// TODO 打开对应的量表页面
			weui.alert("用户：" + uid + ",所打开的量表名为：" + name + ",esid:" + esid);
		},
		id : 'enclosedScalePicker'
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
					let url = "healthAction_getCurrentLevelUsersByPageLimit.action";
					let param = {
						// 【层级过滤】如果前端通过picker选择了目标过滤层级，则这两个参数是有值的，否则后端得到的是null
						"selectedTag" : selectedTag,
						"selectedLid" : selectedLid,
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
							let sampleNum = $("<td><a href='#' onclick='toSampleList4User(\"" + data.users[i].uid + "\")'>" + data.users[i].sampleNum + "</a></td>");
							let sex = $("<td>" + data.users[i].sex + "</td>");
							let age = $("<td>" + data.users[i].age + "</td>");
							let phone = $("<td>" + data.users[i].phone + "</td>");
							let address = $('<td class="text-truncate" data-toggle="tooltip" title="' + data.users[i].address + '">' + data.users[i].address + '</td>');
							let btns = $('<td><div class="btn-group" role="group"><button type="button" class="btn btn-outline-secondary btn-sm" onclick="showEnclosedScaleSelector(\'' + data.users[i].uid + '\');">新样本</button><button type="button" class="btn btn-outline-secondary btn-sm">其他</button></div></td>');
							// 拼装
							tr.append(name).append(sampleNum).append(sex).append(age).append(phone).append(address).append(btns);
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

	let url = "healthAction_getCountandCreateFirstPage4InitLaypage.action";
	let param = {
		// 请求参数，如果前端通过picker选择了目标过滤层级，则这两个参数是有值的，否则后端得到的是null
		"selectedTag" : selectedTag,
		"selectedLid" : selectedLid
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
			let sampleNum = $("<td><a href='#' onclick='toSampleList4User(\"" + data.users[i].uid + "\")'>" + data.users[i].sampleNum + "</a></td>");
			let sex = $("<td>" + data.users[i].sex + "</td>");
			let age = $("<td>" + data.users[i].age + "</td>");
			let phone = $("<td>" + data.users[i].phone + "</td>");
			let address = $('<td class="text-truncate" data-toggle="tooltip" title="' + data.users[i].address + '">' + data.users[i].address + '</td>');
			let btns = $('<td><div class="btn-group" role="group"><button type="button" class="btn btn-outline-secondary btn-sm" onclick="showEnclosedScaleSelector(\'' + data.users[i].uid + '\');">新样本</button><button type="button" class="btn btn-outline-secondary btn-sm">其他</button></div></td>');
			// 拼装
			tr.append(name).append(sampleNum).append(sex).append(age).append(phone).append(address).append(btns);
			// 将新建行显示在页面上
			table.append(tr);
		}

	});

	$.ajaxSetup({
		async : true // 恢复全局设置Ajax为异步执行
	});

	return count;
}


function toSampleList4User(uid) {
	weui.alert(uid);
}


// 切换筛选栏的图标显示
function changeIcon() {
	let $icon = $("#icon4selectorPanel");
	let $panel = $("#selectorPanel");

	if ($icon.attr("class") == "glyphicon glyphicon-chevron-down") {
		$panel.collapse('show');
		$icon.attr("class", "glyphicon glyphicon-chevron-up");
	} else {
		$panel.collapse('hide');
		$icon.attr("class", "glyphicon glyphicon-chevron-down");
	}
}