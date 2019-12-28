// 用于标记出当前操作的页面（firstPage=1 ;  secondPage =2 ; thirdPage = 3; fourthPage=4; finalPage=5）
var currentPage = 1;
var closedScale = null;

// 总体的初始化工作
$(function() {
	init();
});

// 向后端发送通过第五页确认后的最终JSON数据包
function sendData() {
	// 服务器地址（相对路径）
	let url = "healthAciton_createEnclosedScale.action";
	// 获取JSON数据
	let param = getJson();
	console.log(param);
//	执行AJAX的POST请求
	$.post(url, param,
		function(data, textStatus, jqXHR) {
			// 将服务器端返回的处理结果告知前端用户
			weui.alert(data.message);
		},
		"dataType"
	);
}

// 全局初始化工作
function init() {
	// // 初始化按钮状态
	// $("#preBtn").removeClass("weui-btn_disabled");
	// $("#addNewBtn").removeClass("weui-btn_disabled");
	// $("#nextBtn").removeClass("weui-btn_disabled");
	// $("#createScaleBtn").removeClass("weui-btn_disabled");
	// 初始化页面状态
	$("#firstPage").attr("hidden", false);
	$("#secondPage").attr("style", "display: none;");
	$("#thirdPage").attr("style", "display: none;");
	$("#fourthPage").attr("style", "display: none;");
	$("#finalPage").attr("style", "display: none;");
	// 关键按钮的显隐
	$("#preBtn").attr("hidden", true);
	$("#addFactorBtn").attr("hidden", true);
	$("#addOpGroupBtn").attr("hidden", true);
	$("#addTopicBtn").attr("hidden", true);
	$("#createScaleBtn").attr("hidden", true);
	$("#nextBtn").attr("hidden", false);
}

// 向前翻页
function preBtn() {
	if (5 === currentPage) {
		$("#finalPage").hide("blind", {
			'direction' : 'right'
		}, 400, function() {
			console.log("fourthPage 隐藏完成");
			// 设置关键按钮的显隐
			$("#preBtn").attr("hidden", false);
			$("#addFactorBtn").attr("hidden", true);
			$("#addOpGroupBtn").attr("hidden", true);
			$("#addTopicBtn").attr("hidden", false);
			$("#createScaleBtn").attr("hidden", true);
			$("#nextBtn").attr("hidden", false);
			// 显示第四页面
			$("#fourthPage").show("blind", {
				'direction' : 'left'
			}, 400, function() {
				console.log("fourthPage 显示完成");
			});
		});
		currentPage -= 1;
	} else if (4 === currentPage) {
		$("#fourthPage").hide("blind", {
			'direction' : 'right'
		}, 400, function() {
			console.log("fourthPage 隐藏完成");
			// 设置关键按钮的显隐
			$("#preBtn").attr("hidden", false);
			$("#addFactorBtn").attr("hidden", true);
			$("#addOpGroupBtn").attr("hidden", false);
			$("#addTopicBtn").attr("hidden", true);
			$("#createScaleBtn").attr("hidden", true);
			$("#nextBtn").attr("hidden", false);
			// 显示第三页面
			$("#thirdPage").show("blind", {
				'direction' : 'left'
			}, 400, function() {
				console.log("thirdPage 显示完成");
			});
		});
		currentPage -= 1;
	} else if (3 === currentPage) {
		$("#thirdPage").hide("blind", {
			'direction' : 'right'
		}, 400, function() {
			console.log("thirdPage 隐藏完成");
			// 设置关键按钮的显隐
			$("#preBtn").attr("hidden", false);
			$("#addFactorBtn").attr("hidden", false);
			$("#addOpGroupBtn").attr("hidden", true);
			$("#addTopicBtn").attr("hidden", true);
			$("#createScaleBtn").attr("hidden", true);
			$("#nextBtn").attr("hidden", false);
			// 显示第二页面
			$("#secondPage").show("blind", {
				'direction' : 'left'
			}, 400, function() {
				console.log("secondPage 显示完成");
			});
		});
		currentPage -= 1;
	} else if (2 === currentPage) {
		$("#secondPage").hide("blind", {
			'direction' : 'right'
		}, 400, function() {
			console.log("secondPage 隐藏完成");
			// 设置关键按钮的显隐
			$("#preBtn").attr("hidden", true);
			$("#addFactorBtn").attr("hidden", true);
			$("#addOpGroupBtn").attr("hidden", true);
			$("#addTopicBtn").attr("hidden", true);
			$("#createScaleBtn").attr("hidden", true);
			$("#nextBtn").attr("hidden", false);
			// 显示第一页面
			$("#firstPage").show("blind", {
				'direction' : 'left'
			}, 400, function() {
				console.log("firstPage 显示完成");
			});
		});
		currentPage -= 1;
	} else {
		return false;
	}
}

// 向后翻页
function nextBtn() {
	if (1 === currentPage) {
		$("#firstPage").hide("blind", {
			'direction' : 'left'
		}, 400, function() {
			console.log("firstPage 隐藏完成");
			// 设置关键按钮的显隐
			$("#preBtn").attr("hidden", false);
			$("#addFactorBtn").attr("hidden", false);
			$("#addOpGroupBtn").attr("hidden", true);
			$("#addTopicBtn").attr("hidden", true);
			$("#createScaleBtn").attr("hidden", true);
			$("#nextBtn").attr("hidden", false);
			// 显示第二页面
			$("#secondPage").show("blind", {
				'direction' : 'right'
			}, 400, function() {
				console.log("secondPage 显示完成");
			});
		});
		currentPage += 1;
	} else if (2 === currentPage) {
		$("#secondPage").hide("blind", {
			'direction' : 'left'
		}, 400, function() {
			console.log("secondPage 隐藏完成");
			// 设置关键按钮的显隐
			$("#preBtn").attr("hidden", false);
			$("#addFactorBtn").attr("hidden", true);
			$("#addOpGroupBtn").attr("hidden", false);
			$("#addTopicBtn").attr("hidden", true);
			$("#createScaleBtn").attr("hidden", true);
			$("#nextBtn").attr("hidden", false);
			// 显示第三页面
			$("#thirdPage").show("blind", {
				'direction' : 'right'
			}, 400, function() {
				console.log("thirdPage 显示完成");
			});
		});
		currentPage += 1;
	} else if (3 === currentPage) {
		$("#thirdPage").hide("blind", {
			'direction' : 'left'
		}, 400, function() {
			console.log("thirdPage 隐藏完成");
			// 设置关键按钮的显隐
			$("#preBtn").attr("hidden", false);
			$("#addFactorBtn").attr("hidden", true);
			$("#addOpGroupBtn").attr("hidden", true);
			$("#addTopicBtn").attr("hidden", false);
			$("#createScaleBtn").attr("hidden", true);
			$("#nextBtn").attr("hidden", false);
			// 根据页面2和页面3的内容更新weui.picker所需要的两个数据源factorTips和opGroupTips
			init4TopicPage();
			// 显示第四页面
			$("#fourthPage").show("blind", {
				'direction' : 'right'
			}, 400, function() {
				console.log("fourthPage 显示完成");
			});
		});
		currentPage += 1;
	} else if (4 === currentPage) {
		$("#fourthPage").hide("blind", {
			'direction' : 'left'
		}, 400, function() {
			console.log("fourthPage 隐藏完成");
			// 设置关键按钮的显隐
			$("#preBtn").attr("hidden", false);
			$("#addFactorBtn").attr("hidden", true);
			$("#addOpGroupBtn").attr("hidden", true);
			$("#addTopicBtn").attr("hidden", true);
			$("#createScaleBtn").attr("hidden", false);
			$("#nextBtn").attr("hidden", true);
			// 显示第五页页面
			compareFinalPage(); // 动态显示最终页面的内容
			$("#finalPage").show("blind", {
				'direction' : 'right'
			}, 400, function() {
				console.log("finalPage 显示完成");
			});
		});
		currentPage += 1;
	} else {
		// alert("ERROR:currentPage变量当前数值为" + currentPage + "，您的操作超出变量表达范围");
		// window.location.reload();
		return false;
	}
}


// 【AJAX数据】将表单数据全部封装成JSON并传输给服务器后端
function getJson() {
	// 创建JSON终极对象
	let closedScale = {};
	// ---------获取主表数据------------
	let esid = $("#esid").val();
	let chName = $("#chName").val();
	let engName = $("#engName").val();
	let introduce = $("#introduce").val();
	let description = $("#description").val();
	let weigh = parseFloat($("#weigh").val());
	closedScale['general'] = {
		"esid" : esid,
		"chName" : chName,
		"engName" : engName,
		"introduce" : introduce,
		"description" : description,
		"weigh" : weigh
	};
	// --------获取所有factor数据----------
	closedScale['factors'] = [];
	for (let i = 0; i < factorNum; i++) {
		// 遍历所有factor
		let factor = {};
		factor['order'] = i + 1;
		factor['stamp'] = $("#factor_" + (i + 1)).attr("data-stamp");
		factor['fid'] = $("#factor_" + (i + 1) + "_fid").val();
		factor['name'] = $("#factor_" + (i + 1) + "_name").val();
		factor['keyword'] = $("#factor_" + (i + 1) + "_keyword").val();
		factor['description'] = $("#factor_" + (i + 1) + "_description").val();
		factor['sections'] = [];
		// 开始遍历当前因子中包含的全部计分规则
		for (let j = 0; j < sectionArr[i]; j++) {
			let section = {};
			section['order'] = j + 1;
			section['sid'] = $("#factor_" + (i + 1) + "_section_" + (j + 1) + "_sid").val();
			section['name'] = $("#factor_" + (i + 1) + "_section_" + (j + 1) + "_name").val();
			section['description'] = $("#factor_" + (i + 1) + "_section_" + (j + 1) + "_description").val();
			section['minScore'] = parseInt($("#factor_" + (i + 1) + "_section_" + (j + 1) + "_minScore").val());
			section['maxScore'] = parseInt($("#factor_" + (i + 1) + "_section_" + (j + 1) + "_maxScore").val());
			section['diagnosis'] = $("#factor_" + (i + 1) + "_section_" + (j + 1) + "_diagnosis").val();
			// 压入数组
			factor.sections.push(section);
		}
		// 压入数组
		closedScale.factors.push(factor);
	}
	// --------获取所有opGroup数据----------
	closedScale['opGroups'] = [];
	for (let i = 0; i < opGroupNum; i++) {
		let group = {};
		group["order"] = i + 1;
		group["stamp"] = $("#opGroup_" + (i + 1)).attr("data-stamp");
		group["ogid"] = $("#opGroup_" + (i + 1) + "_ogid").val();
		group["name"] = $("#opGroup_" + (i + 1) + "_name").val();
		group["introduce"] = $("#opGroup_" + (i + 1) + "_introduce").val();
		group["options"] = [];
		// 开始便利当前选先祖中包含的所有选项
		for (let j = 0; j < optionArr[i]; j++) {
			let option = {};
			option['order'] = j + 1;
			option['opid'] = $("#opGroup_" + (i + 1) + "_option_" + (j + 1) + "_opid").val();
			option['content'] = $("#opGroup_" + (i + 1) + "_option_" + (j + 1) + "_content").val();
			option['score'] = parseInt($("#opGroup_" + (i + 1) + "_option_" + (j + 1) + "_score").val());
			// 压入数组
			group.options.push(option);
		}
		// 压入数组
		closedScale.opGroups.push(group);
	}
	// -----------获取所有topic数据-------------
	closedScale['topics'] = [];
	for (let i = 0; i < topicNum; i++) {
		let topic = {};
		topic['order'] = i + 1;
		topic['tid'] = $("#topic_" + (i + 1) + "_tid").val();
		topic['content'] = $("#topic_" + (i + 1) + "_content").val();
		topic['keyword'] = $("#topic_" + (i + 1) + "_keyword").val();
		// 解析当前topic所关联的factor序数和opGroup序数
		targetFactor = $("#topic_" + (i + 1) + "_factor").attr("data-target"); // factor_1_1576897679000
		targetOpGroup = $("#topic_" + (i + 1) + "_opGroup").attr("data-target"); // opGroup_1_1576897679000
		// 解析并将关联的序数存入json
		topic['factor_order'] = parseInt(targetFactor.split("_")[1]);
		topic['opGroup_order'] = parseInt(targetOpGroup.split("_")[1]);
		// 压入数组
		closedScale.topics.push(topic);
	}

	// 返回最终数据封装结果
	return closedScale;
}



// 用于动态生成最终确认页面的预览展示
function compareFinalPage() {
	closedScale = getJson();

	// ---------获取主表数据------------
	let chName = closedScale.general.chName;
	let engName = closedScale.general.engName;
	let introduce = closedScale.general.introduce;
	let description = closedScale.general.description;
	let weigh = closedScale.general.weigh;
	$("#fp_chName").text(chName);
	$("#fp_engName").text(engName);
	$("#fp_introduce").text(introduce);
	$("#fp_description").text(description);
	$("#fp_weigh").text(weigh);
	$("#fp_total_topic").text(topicNum);
	$("#fp_total_factor").text(factorNum);
	$("#fp_total_opGroup").text(opGroupNum);

	// -----------获取所有topic数据-------------
	$("#fp_tbody").empty();
	for (let i = 0; i < closedScale.topics.length; i++) {
		let $tr = $("<tr></tr>");
		let topic = closedScale.topics[i];
		// 组装td
		let $td_numb = $("<td scope='row'>" + topic.order + "</td>");
		let $td_description = $("<td>" + topic.content + "</td>");
		let $td_keyword = $("<td>" + topic.keyword + "</td>");

		let factor = closedScale.factors[topic.factor_order - 1];
		let $td_factor = $("<td>" + factor.name + "</td>");
		let group = closedScale.opGroups[topic.opGroup_order - 1];
		let $td_group = $("<td>" + group.name + "</td>");

		// -----------获取所有option数据-------------
		let $td_op = $("<td></td>");
		for (let j = 0; j < group.options.length; j++) {
			let option = group.options[j];

			let $inline = $('<div class="form-check form-check-inline radio radio-success"></div>')
			let $label = $('<label class="form-check-label" for="topic_' + topic.order + '_option_' + option.order + '">' + option.content + '</label>');
			let $input = $('<input class="form-check-input" type="radio" name="topic_' + topic.order + '_option" id="topic_' + topic.order + '_option_' + option.order + '" value="' + option.order + '">');
			// 开始组装：切记input一定要在label之前，否则layui会发生无法选取radio的异常问题
			$inline.append($input).append($label);
			// 显示出来
			$td_op.append($inline);
		}
		// 进行总装
		$tr.append($td_numb).append($td_description).append($td_keyword).append($td_factor).append($td_group).append($td_op);
		// 显示出来一行数据
		$("#fp_tbody").append($tr);
	}

}