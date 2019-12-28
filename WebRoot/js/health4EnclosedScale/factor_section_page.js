// 当前因子总数量
var factorNum = 0;
// 数组中的每个元素中记录着与其下标数+1所得值相同的因子序数的因子中所包含的计分规则数量
var sectionArr = new Array();

// 新增因子
function addFactor() {
    // 定位因子列表
    let factorList = $("#factorList");
    // 准备配件
    let timestamp = Date.parse(new Date()); // 获取创建组建的时间戳，用于唯一标志该组建（因为id序数是会变动的）
    let newFactor = $('<div class="weui-form__control-area" id="factor_' + (factorNum + 1) + '" data-stamp="' + timestamp + '"></div>');
    let factorHead = $('<div style="font-size:170%;"><div class="row"><div class="col-md-auto" id="factor_' + (factorNum + 1) + '_title">第' + (factorNum + 1) + '个因子</div><div class="col-md"></div><div class="col-md-auto"><a id="factor_' + (factorNum + 1) + '_link" href="javascript:deleteFactor(\'factor_' + (factorNum + 1) + '\');" class="weui-btn weui-btn_mini weui-btn_warn">X</a></div></div></div>');
    let factorBase = $('<div class="weui-cells weui-cells_form"><input type="hidden" id="factor_' + (factorNum + 1) + '_fid" /><div class="weui-cell"><div class="weui-cell__hd"><label class="weui-label">因子名称</label></div><div class="weui-cell__bd"><input class="weui-input" placeholder="请填写因子的名称" id="factor_' + (factorNum + 1) + '_name" /></div></div><div class="weui-cell"><div class="weui-cell__hd"><label class="weui-label">关键词</label></div><div class="weui-cell__bd"><input class="weui-input" placeholder="概括该因子的关键词" id="factor_' + (factorNum + 1) + '_keyword" /></div></div><div class="weui-cell"><div class="weui-cell__hd"><label class="weui-label">因子简介</label></div><div class="weui-cell__bd"><textarea class="weui-textarea" placeholder="因子简介" rows="3" id="factor_' + (factorNum + 1) + '_description"></textarea><div class="weui-textarea-counter"><span>0</span>/200</div></div></div></div>');
    let sectionList = $('<div class="weui-panel weui-panel_access"><div class="weui-panel__hd">诊断标准</div><div class="weui-panel__bd" id="factor_' + (factorNum + 1) + '_sectionList"></div></div>');
    let addSectionBtn = $('<div class="weui-panel__ft"><a id="factor_' + (factorNum + 1) + '_Btn4addSection" href="javascript:addSection(\'factor_' + (factorNum + 1) + '\');" class="weui-cell weui-cell_access weui-cell_link"><div class="weui-cell__bd" id="factor_' + (factorNum + 1) + '_title4Btn4addSection">为因子' + (factorNum + 1) + '新增诊断标准</div><span class="weui-cell__ft"></span></a></div>');
    // 开始组装
    newFactor.append(factorHead).append(factorBase).append(sectionList).append(addSectionBtn);
    // 将新建成功的因子组件放入到List中显示到页面上
    factorList.append(newFactor);
    // 至此组件创建成功，更新全局参数
    factorNum += 1;
    // 为新建因子保留一位数组，存放该因子的计分规则数量
    sectionArr.push(0);
}

// 删除因子 id是形如 factor_1 的因子组件ID
function deleteFactor(id) {
    // 解析删除的因子ID序数
    let factorOrder = parseInt(id.split("_")[1]);
    if (factorOrder <= 0) {
        weui.alert("待删除的因子的序数小于等于0，非法操作！");
        return;
    }
    // 删除因子组件
    $("#" + id).remove();
    // 判断被删除因子的位置
    if (factorOrder < factorNum) {
        // 被删除因子不是因子序列中的最后一个因子，因此需要将其后的因子序数全面向前挪动一位
        for (let i = (factorOrder + 1); i <= factorNum; i++) {
            let currentID = "factor_" + i;  // factor_2
            let purposeID = "factor_" + (i - 1);   // factor_1
            // 先更新因子的相关属性
            $("#" + currentID).attr("id", purposeID);
            $("#" + currentID + "_title").attr("id", purposeID + "_title").text("第" + (i - 1) + "个因子");
            $("#" + currentID + "_link").attr("id", purposeID + "_link").attr("href", "javascript:deleteFactor('" + purposeID + "');");
            $("#" + currentID + "_fid").attr("id", purposeID + "_fid");
            $("#" + currentID + "_name").attr("id", purposeID + "_name");
            $("#" + currentID + "_keyword").attr("id", purposeID + "_keyword");
            $("#" + currentID + "_description").attr("id", purposeID + "_description");
            $("#" + currentID + "_sectionList").attr("id", purposeID + "_sectionList");
            $("#" + currentID + "_Btn4addSection").attr("id", purposeID + "_Btn4addSection").attr("href", "javascript:addSection('" + purposeID + "');");
            $("#" + currentID + "_title4Btn4addSection").attr("id", purposeID + "_title4Btn4addSection").text("为因子" + (i - 1) + "新增诊断标准");
            // 再更新因子中包含的各个计分规则的属性
            for (let j = 1; j <= sectionArr[i - 1]; j++) {
                $("#" + currentID + "_section_" + j).attr("id", purposeID + "_section_" + j);
                $("#" + currentID + "_section_" + j + "_title").attr("id", purposeID + "_section_" + j + "_title");
                $("#" + currentID + "_section_" + j + "_link").attr("id", purposeID + "_section_" + j + "_link").attr("href", "javascript:deleteSection('" + (purposeID + "_section_" + j) + "');");
                $("#" + currentID + "_section_" + j + "_sid").attr("id", purposeID + "_section_" + j + "_sid");
                $("#" + currentID + "_section_" + j + "_name").attr("id", purposeID + "_section_" + j + "_name");
                $("#" + currentID + "_section_" + j + "_description").attr("id", purposeID + "_section_" + j + "_description");
                $("#" + currentID + "_section_" + j + "_minScore").attr("id", purposeID + "_section_" + j + "_minScore").attr("onchange", "checkScore4Section('" + (purposeID + "_section_" + j) + "','minScore');");
                $("#" + currentID + "_section_" + j + "_maxScore").attr("id", purposeID + "_section_" + j + "_maxScore").attr("onchange", "checkScore4Section('" + (purposeID + "_section_" + j) + "','maxScore');");
                $("#" + currentID + "_section_" + j + "_diagnosis").attr("id", purposeID + "_section_" + j + "_diagnosis");
            }
            // 因子的计分规则数量在数组中跟随对应因子序数的的变动向前挪动一位
            sectionArr[(i - 1) - 1] = sectionArr[i - 1];
        }
    } else {
        // 说明删除的因子是因子列表的最后一位，无需任何挪位操作
    }
    // 无论被删除因子是不是因子列表最后一位，经过删除和挪位操作后
    sectionArr.pop();
    // 最后更新factorNum
    factorNum -= 1;
}

// 新增计分规则   参数id是形如： factor_1 的因子ID，用于定位是哪个因子需要新增规则
function addSection(id) {
    // 首先确定是在哪个因子的sectionList中添加规则
    let sectionList = $("#" + id + "_sectionList");
    // 解析因子的ID序数
    let factorOrder = parseInt(id.split("_")[1]);
    // 根据因子的ID序数，从规则数组中查询当前因子之下的规则数量
    let sectionOrder = sectionArr[factorOrder - 1];
    let newSection = $('<div class="weui-media-box weui-media-box_text" id="factor_' + factorOrder + '_section_' + (sectionOrder + 1) + '"></div>');
    let sectionHead = $('<div class="row"><div class="col-md-auto"><h4 class="weui-media-box__title" id="factor_' + factorOrder + '_section_' + (sectionOrder + 1) + '_title">标准' + (sectionOrder + 1) + '</h4></div><div class="col-md"></div><div class="col-md-auto"><a id="factor_' + factorOrder + '_section_' + (sectionOrder + 1) + '_link" href="javascript:deleteSection(\'factor_' + factorOrder + '_section_' + (sectionOrder + 1) + '\');" class="weui-btn weui-btn_mini weui-btn_warn">X</a></div></div>');
    let sectionBody = $('<div class="weui-cells weui-cells_form"><input type="hidden" id="factor_' + factorOrder + '_section_' + (sectionOrder + 1) + '_sid" /><div class="weui-cell"><div class="weui-cell__hd"><label class="weui-label">规则名称</label></div><div class="weui-cell__bd"><input class="weui-input" placeholder="请填写规则的名称" id="factor_' + factorOrder + '_section_' + (sectionOrder + 1) + '_name" /></div></div><div class="weui-cell"><div class="weui-cell__hd"><label class="weui-label">规则描述</label></div><div class="weui-cell__bd"><textarea class="weui-textarea" placeholder="规则描述" rows="3" id="factor_' + factorOrder + '_section_' + (sectionOrder + 1) + '_description"></textarea><div class="weui-textarea-counter"><span>0</span>/200</div></div></div><div class="weui-cell"><div class="weui-cell__hd"><label class="weui-label">分值标准</label></div><div class="weui-cell__bd"><input type="number" id="factor_' + factorOrder + '_section_' + (sectionOrder + 1) + '_minScore" style="width: 10%;" onchange="checkScore4Section(\'factor_' + factorOrder + '_section_' + (sectionOrder + 1) + '\',\'minScore\');" />&nbsp&nbsp≤&nbsp&nbsp加权得分&nbsp&nbsp≤&nbsp&nbsp<input type="number" onchange="checkScore4Section(\'factor_' + factorOrder + '_section_' + (sectionOrder + 1) + '\',\'maxScore\');" id="factor_' + factorOrder + '_section_' + (sectionOrder + 1) + '_maxScore" style="width: 10%;" /></div></div><div class="weui-cell"><div class="weui-cell__hd"><label class="weui-label">诊断用语</label></div><div class="weui-cell__bd"><textarea class="weui-textarea" placeholder="分值基于该范围内时的诊断用语" rows="3" id="factor_' + factorOrder + '_section_' + (sectionOrder + 1) + '_diagnosis"></textarea><div class="weui-textarea-counter"><span>0</span>/500</div></div></div></div>');
    // 开始组装新的规则组件
    newSection.append(sectionHead).append(sectionBody);
    // 将新建成功的规则组件放入到List中显示到页面上
    sectionList.append(newSection);
    // 至此组件创建成功，更新计数器
    sectionArr[factorOrder - 1] += 1;

}

// 删除计分规则  id是形如 “factor_2_section_2”的计分规则ID值
function deleteSection(id) {
    // 解析出要删除的规则的序数以及其所属于的因子序数
    let factorOrder = parseInt(id.split("_")[1]);
    let sectionOrder = parseInt(id.split("_")[3]);
    if (sectionOrder <= 0) {
        weui.alert("待删除的计分规则的序数小于等于0，非法操作！");
        return;
    }
    // 查阅sectionArr中对应的因子序数下所记录的规则总数
    let sectionNum = sectionArr[factorOrder - 1];
    // 先删除自己
    $("#" + id).remove();
    // 条件判定开始
    if (sectionOrder < sectionNum) {
        // 说明要删除的规则是从规则列表的中间或首位删除的，需要后面的规则计数向前挪动一位，因此需要循环遍历
        // 定位要向前挪动一位的计分规则的基础id（形如：factor_2_section_）
        let baseSectionId = "factor_" + factorOrder + "_section_";
        for (let i = 1; i <= (sectionNum - sectionOrder); i++) {
            // 形如： factor_2_section_3 的待向前挪移位的计分规则组件的基础ID
            let sectionId = baseSectionId + (i + sectionOrder);
            // 修改组件总ID
            $("#" + sectionId).attr("id", baseSectionId + (i + sectionOrder - 1));
            $("#" + sectionId + "_title").text("标准" + (i + sectionOrder - 1)).attr("id", baseSectionId + (i + sectionOrder - 1) + "_title");
            $("#" + sectionId + "_link").attr("href", "javascript:deleteSection('" + baseSectionId + (i + sectionOrder - 1) + "');").attr("id", baseSectionId + (i + sectionOrder - 1) + "_link");
            $("#" + sectionId + "_sid").attr("id", baseSectionId + (i + sectionOrder - 1) + "_sid");
            $("#" + sectionId + "_name").attr("id", baseSectionId + (i + sectionOrder - 1) + "_name");
            $("#" + sectionId + "_description").attr("id", baseSectionId + (i + sectionOrder - 1) + "_description");
            $("#" + sectionId + "_minScore").attr("onchange", "checkScore4Section('" + baseSectionId + (i + sectionOrder - 1) + "','minScore');").attr("id", baseSectionId + (i + sectionOrder - 1) + "_minScore");
            $("#" + sectionId + "_maxScore").attr("onchange", "checkScore4Section('" + baseSectionId + (i + sectionOrder - 1) + "','maxScore');").attr("id", baseSectionId + (i + sectionOrder - 1) + "_maxScore");
            $("#" + sectionId + "_diagnosis").attr("id", baseSectionId + (i + sectionOrder - 1) + "_diagnosis");
        }
    } else {
        // 说明删除的是列表中的最后一个，无需修改其他位置的序数
    }
    sectionArr[factorOrder - 1] = (sectionNum - 1);
}

// 检测因子计分规则的范围是否符合常理，并重置
function checkScore4Section(section, scoreType) {
    if ('minScore' === scoreType) {
        let minScore = $("#" + section + "_" + "minScore");
        let maxScore = $("#" + section + "_" + "maxScore");
        if (minScore.val() > maxScore.val()) {
            weui.alert("最小值不得小于最大值");
            minScore.val(0);
            maxScore.val(1);
        }
    } else {
        let minScore = $("#" + section + "_" + "minScore");
        let maxScore = $("#" + section + "_" + "maxScore");
        if (minScore.val() > maxScore.val()) {
            weui.alert("最小值不得小于最大值");
            minScore.val(0);
            maxScore.val(1);
        }
    }
}