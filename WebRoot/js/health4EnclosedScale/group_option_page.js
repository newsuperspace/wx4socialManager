// 当前选项组的总数量
var opGroupNum = 0;
// 该数组中每个元素存放的是对应选项组序数(数组index+1 = opGroupNum序数)中选项的数量
var optionArr = new Array();

// 创建新的选项组
function addOpGroup() {
    // 找到总列表
    let list = $("#opGroupList");
    // 创建配件
    let timestamp = Date.parse(new Date()); // 获取创建组建的时间戳，用于唯一标志该组建（因为id序数是会变动的）
    let opGroup = $('<div class="weui-form__control-area" id="opGroup_' + (opGroupNum + 1) + '" data-stamp="' + timestamp + '"></div>');
    let title = $('<div style="font-size:170%;"><div class="row"><div class="col-md-auto" id="opGroup_' + (opGroupNum + 1) + '_title">第' + (opGroupNum + 1) + '个选项组</div><div class="col-md"></div><div class="col-md-auto"><a id="opGroup_' + (opGroupNum + 1) + '_link" href="javascript:deleteOpGroup(\'opGroup_' + (opGroupNum + 1) + '\');"class="weui-btn weui-btn_mini weui-btn_warn">X</a></div></div></div>');
    let body = $('<div class="weui-cells weui-cells_form"><input type="hidden" id="opGroup_' + (opGroupNum + 1) + '_ogid" /><div class="weui-cell"><div class="weui-cell__hd"><label class="weui-label">选项组名称</label></div><div class="weui-cell__bd"><input class="weui-input" placeholder="请填写选项组的名字，用于识别" id="opGroup_' + (opGroupNum + 1) + '_name" /></div></div><div class="weui-cell"><div class="weui-cell__hd"><label class="weui-label">选项组简介</label></div><div class="weui-cell__bd"><textarea class="weui-textarea" placeholder="请简单介绍何种情况下使用该选项组用作题目的选项" rows="2" id="opGroup_' + (opGroupNum + 1) + '_introduce"></textarea><div class="weui-textarea-counter"><span>0</span>/200</div></div></div></div>');
    let opList = $('<div class="weui-panel weui-panel_access"><div class="weui-panel__hd">选项组内的选项列</div><div class="weui-panel__bd" id="opGroup_' + (opGroupNum + 1) + '_optionList"><!-- 添加选项的位置 --></div></div>');
    let btn = $(' <div class="weui-panel__ft"><a href="javascript:addOption(\'opGroup_' + (opGroupNum + 1) + '\');" id="opGroup_' + (opGroupNum + 1) + '_Btn4addOption" class="weui-cell weui-cell_access weui-cell_link"><div class="weui-cell__bd" id="opGroup_' + (opGroupNum + 1) + '_title4Btn4addOption">为选项组' + (opGroupNum + 1) + '添加选项</div><span class="weui-cell__ft"></span></a></div>');
    // 组装配件
    opGroup.append(title).append(body).append(opList).append(btn);
    // 显示新建选项组
    list.append(opGroup);
    // 更新宏观变量
    opGroupNum += 1;
    optionArr.push(0);
}


// 删除选项组 参数ID是形如  opGroup_1 的选项组的总ID
function deleteOpGroup(ogid) {
    // 解析删除的选项组的序数
    let groupOrder = parseInt(ogid.split("_")[1]);
    if (groupOrder <= 0) {
        weui.alert("待删除的选项组序数小于等于0，非法操作！");
        return;
    }
    // 先直接删除选项组件
    $("#" + ogid).remove();
    // 判断被删除选项组在选项组列表中的位置
    if (groupOrder < opGroupNum) {
        // 被删除选项组不是列表中的最后一个，因此需要将其后的选项组序数全面向前挪动一位
        for (let i = (groupOrder + 1); i <= opGroupNum; i++) {
            let currentID = "opGroup_" + i; // 需要挪位的选项组的ID  形如： opGroup_2
            let purposeID = "opGroup_" + (i - 1); // 向前挪位后的选项组ID 形如： opGroup_1
            // 先更新选项组的相关属性
            $("#" + currentID).attr("id", purposeID);
            $("#" + currentID + "_title").attr("id", purposeID + "_title").text("第" + (i - 1) + "个选项组");
            $("#" + currentID + "_link").attr("id", purposeID + "_link").attr("href", "javascript:deleteOpGroup('" + purposeID + "');");
            $("#" + currentID + "_ogid").attr("id", purposeID + "_ogid");
            $("#" + currentID + "_name").attr("id", purposeID + "_name");
            $("#" + currentID + "_introduce").attr("id", purposeID + "_introduce");
            $("#" + currentID + "_optionList").attr("id", purposeID + "_optionList");
            $("#" + currentID + "_Btn4addOption").attr("id", purposeID + "_Btn4addOption").attr("href", "javascript:addOption('" + purposeID + "');");
            $("#" + currentID + "_title4Btn4addOption").attr("id", purposeID + "_title4Btn4addOption").text("为选项组" + (i - 1) + "添加选项");
            // 再更新选项组中包含的所有选项的属性
            for (let j = 1; j <= optionArr[i - 1]; j++) {
                $("#" + currentID + "_option_" + j).attr("id", purposeID + "_option_" + j);
                $("#" + currentID + "_option_" + j + "_title").attr("id", purposeID + "_option_" + j + "_title");
                $("#" + currentID + "_option_" + j + "_link").attr("id", purposeID + "_option_" + j + "_link").attr("href", "javascript:deleteOption('" + (purposeID + "_option_" + j) + "');");
                $("#" + currentID + "_option_" + j + "_opid").attr("id", purposeID + "_option_" + j + "_opid");
                $("#" + currentID + "_option_" + j + "_order").attr("id", purposeID + "_option_" + j + "_order");
                $("#" + currentID + "_option_" + j + "_content").attr("id", purposeID + "_option_" + j + "_content");
                $("#" + currentID + "_option_" + j + "_score").attr("id", purposeID + "_option_" + j + "_score");
            }
            // 因子的计分规则数量在数组中跟随对应因子序数的的变动向前挪动一位
            optionArr[(i - 1) - 1] = optionArr[i - 1];
        }
    } else {
        // 说明删除的选项组是列表的最后一位，无需任何挪位操作
    }
    // 无论被删除因子是不是因子列表最后一位，经过删除和挪位操作后
    optionArr.pop();
    // 最后更新factorNum
    opGroupNum -= 1;
}



// 新建选项 参数ID是形如 opGroup_1 的选项组的总ID 指出为哪个选项组添加新选项
function addOption(ogid) {
    // 基于ogid解析要新增option的选项组序数
    let groupOrder = parseInt(ogid.split("_")[1]);
    // 基于选项组序数，定位optionArr中保存该选项组下选项数量的元素
    let optionNum = optionArr[groupOrder - 1];
    // 定位ogid下的 optionList
    let optionList = $("#" + ogid + "_optionList");
    // 创建组件
    let option = $('<div class="weui-media-box weui-media-box_text" id="' + ogid + '_option_' + (optionNum + 1) + '"></div>');
    let title = $('<div class="row"><div class="col-md-auto"><h4 class="weui-media-box__title" id="' + ogid + '_option_' + (optionNum + 1) + '_title">选项' + (optionNum + 1) + '</h4></div><div class="col-md"></div><div class="col-md-auto"><a id="' + ogid + '_option_' + (optionNum + 1) + '_link" href="javascript:deleteOption(\'' + ogid + '_option_' + (optionNum + 1) + '\');" class="weui-btn weui-btn_mini weui-btn_warn">X</a></div></div>');
    let body = $('<div class="weui-cells weui-cells_form"><input type="hidden" id="' + ogid + '_option_' + (optionNum + 1) + '_opid" /><div class="weui-cell"><div class="weui-cell__hd"><label class="weui-label">选项序数</label></div><div class="weui-cell__bd"><input class="weui-input" placeholder="选项在选项组内的排列顺序（从1开始）" value="' + (optionNum + 1) + '" disabled="true" type="number" id="' + ogid + '_option_' + (optionNum + 1) + '_order" /></div></div><div class="weui-cell"><div class="weui-cell__hd"><label class="weui-label">选项文本</label></div><div class="weui-cell__bd"><input class="weui-input" placeholder="出现在量表中供被试者选择的选项文本" id="' + ogid + '_option_' + (optionNum + 1) + '_content" /></div></div><div class="weui-cell"><div class="weui-cell__hd"><label class="weui-label">选项分值</label></div><div class="weui-cell__bd"><input type="number" id="' + ogid + '_option_' + (optionNum + 1) + '_score" /></div></div></div>');
    // 组装
    option.append(title).append(body);
    // 显示
    optionList.append(option);
    // 更新全局变量
    optionArr[groupOrder - 1] = optionNum + 1;
}

// 删除选项 参数ID是形如   opGroup_1_option_1 的选项ID
function deleteOption(opid) {

    //解析选项组序数和等待删除的选项的序数
    let groupOrder = parseInt(opid.split("_")[1]);
    let optionOrder = parseInt(opid.split("_")[3]);
    // 根据组序数从数组中查询该选项组所包含的选项总数
    let optionNum = optionArr[groupOrder - 1];
    // 进行条件判断
    if (optionOrder < optionNum) {
        // 说明等待删除的选项不是选项组列表的最后一个，需要将待删除选项之后的所有选项中的序数向前挪动一位
        $("#" + opid).remove();
        // 开始循环挪位
        for (let i = optionOrder + 1; i <= optionNum; i++) {
            $("#opGroup_" + groupOrder + "_option_" + i).attr("id", "opGroup_" + groupOrder + "_option_" + (i - 1));
            $("#opGroup_" + groupOrder + "_option_" + i + "_title").attr("id", "opGroup_" + groupOrder + "_option_" + (i - 1) + "_title").text("选项" + (i - 1));
            $("#opGroup_" + groupOrder + "_option_" + i + "_link").attr("id", "opGroup_" + groupOrder + "_option_" + (i - 1) + "_link").attr("href", 'javascript:deleteOption(\'opGroup_' + groupOrder + '_option_' + (i - 1) + '\');');
            $("#opGroup_" + groupOrder + "_option_" + i + "_opid").attr("id", "opGroup_" + groupOrder + "_option_" + (i - 1) + "_opid");
            $("#opGroup_" + groupOrder + "_option_" + i + "_order").attr("id", "opGroup_" + groupOrder + "_option_" + (i - 1) + "_order").val((i - 1)).attr("value", (i - 1));
            $("#opGroup_" + groupOrder + "_option_" + i + "_content").attr("id", "opGroup_" + groupOrder + "_option_" + (i - 1) + "_content");
            $("#opGroup_" + groupOrder + "_option_" + i + "_score").attr("id", "opGroup_" + groupOrder + "_option_" + (i - 1) + "_score");
        }

    } else if (option = optionNum) {
        //  说明待删除选项是选项列表的最后一个，直接删除无需挪位
        $("#" + opid).remove();
    } else {
        // opid提供异常
        weui.alert("等待删除的选项ID不存在，请确认后重新操作（opid:" + opid + ")");
        return;
    }
    // 更新全局变量
    optionArr[groupOrder - 1] = optionNum - 1;
}