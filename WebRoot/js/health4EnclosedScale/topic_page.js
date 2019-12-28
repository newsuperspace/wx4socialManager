// 当前题目的总数量
var topicNum = 0;
// 用于weui.picker弹出的因子列表
var factorTips = [];
// 用于weui.picker弹出的选项组列表
var opGroupTips = [];

// topicPage页面显示时负责处理初始化工作
function init4TopicPage() {
    // ------------------weui.picker 数据源更新-------------------
    // 获取所有因子信息，组成因子picker所需的数组
    // 先清空数据
    factorTips = [];
    for (let i = 0; i < factorNum; i++) {
        let tip = {};
        // 获取名称——共给显示
        tip["label"] = $("#factor_" + (i + 1) + "_name").val();
        // 获取时间戳——组建值并用作唯一识别异同的标记
        timestamp = $("#factor_" + (i + 1)).attr("data-stamp");
        tip["value"] = "factor_" + (i + 1) + "_" + timestamp;
        factorTips.push(tip);
    }
    // 获取所有选项组信息，组成选项组picker所需的JSON
    // 先清空数据
    opGroupTips = [];
    for (let i = 0; i < opGroupNum; i++) {
        let tip = {};
        // 获取名称——用于显示
        tip["label"] = $("#opGroup_" + (i + 1) + "_name").val();
        // 获取时间戳——组建值并用作唯一识别异同的标记
        timestamp = $("#opGroup_" + (i + 1)).attr("data-stamp");
        tip["value"] = "opGroup_" + (i + 1) + "_" + timestamp;
        opGroupTips.push(tip);
    }
    // ------------------所有topic的两个picker的值更新-------------------
    // 过滤所有topic的因子
    for (let i = 0; i < topicNum; i++) {
        // 找到topic的factor选择器对象
        let topic = $("#topic_" + (i + 1) + "_factor")
        // 获取旧的目标值,并分解出 "因子序数" 和 "时间戳"
        let oldTarget = topic.attr("data-target");  // 得到形如： "factor_1_1576897679000"
        let oldOrder = parseInt(oldTarget.split("_")[1]);  // 1
        let oldStamp = oldTarget.split("_")[2];   // 1576897679000
        // 根据oldOrder找到该序数当前对应的factor因子对象，查看其时间戳是否一致
        let currentFactor = $("#factor_" + oldOrder);
        if (currentFactor.length > 0) {
            // 指定id的jQuery对象存在，则进一步判断时间戳是否一样
            let currentStamp = currentFactor.attr("data-stamp");
            if (oldStamp != currentStamp) {
                // 如果时间戳不一样，制空该topic的factor的选值
                topic.val("");
                topic.attr("data-target", "");
            }
        } else {
            // 指定id的jQuery都不存在，直接清空该topic的factor选值
            topic.val("");
            topic.attr("data-target", "");
        }
    }
    // 过滤所有topic的选项组
    for (let i = 0; i < topicNum; i++) {
        // 找到topic的opGroup选择器对象
        let topic = $("#topic_" + (i + 1) + "_opGroup")
        // 获取旧的目标值,并分解出 "因子序数" 和 "时间戳"
        let oldTarget = topic.attr("data-target");  // 得到形如： "opGroup_1_1576897679000"
        let oldOrder = parseInt(oldTarget.split("_")[1]);  // 1
        let oldStamp = oldTarget.split("_")[2];   // 1576897679000
        // 根据oldOrder找到该序数当前对应的Group组对象，查看其时间戳是否一致
        let currentGroup = $("#opGroup_" + oldOrder);
        if (currentGroup.length > 0) {
            // 指定id的jQuery对象存在，则进一步判断时间戳是否一样
            let currentStamp = currentGroup.attr("data-stamp");
            if (oldStamp != currentStamp) {
                // 如果时间戳不一样，制空该topic的factor的选值
                topic.val("");
                topic.attr("data-target", "");
            }
        } else {
            // 指定id的jQuery都不存在，直接清空该topic的factor选值
            topic.val("");
            topic.attr("data-target", "");
        }
    }

}


// 显示选择器， 参数type是形如 topic_1_factor 或 topic_1_opGroup 用于定位目标input以及所需要的选择器类型
function showPickers(type) {

    if (type.indexOf("factor") != -1) {
        weui.picker(factorTips, {
            className: 'custom-classname',
            container: 'body',
            defaultValue: [0],
            onChange: function (result) {
                console.log(result)
            },
            onConfirm: function (result) {
                console.log(result)
                $("#" + type).val(result[0].label);
                $("#" + type).attr("data-target", result[0].value);
            },
            id: 'factorPicker'
        });
    } else if (type.indexOf("opGroup") != -1) {
        weui.picker(opGroupTips, {
            className: 'custom-classname',
            container: 'body',
            defaultValue: [0],
            onChange: function (result) {
                console.log(result)
            },
            onConfirm: function (result) {
                console.log(result)
                $("#" + type).val(result[0].label);
                $("#" + type).attr("data-target", result[0].value);
            },
            id: 'opGroupPicker'
        });
    } else {
        weui.alert("参数异常，无法确定所需picker");
    }

}


// 向topicList列表中新建题目
function addTopic() {
    let id = topicNum + 1;
    let list = $("#topicList");

    let topic = $('<div class="weui-form__control-area" id="topic_' + id + '"></div>');
    let title = $('<div style="font-size:170%;"><div class="row"><div class="col-md-auto" id="topic_' + id + '_title">第' + id + '题</div><div class="col-md"></div><div class="col-md-auto"><a id="topic_' + id + '_link" href="javascript:deleteTopic(\'topic_' + id + '\');" class="weui-btn weui-btn_mini weui-btn_warn">X</a></div></div></div>');
    let body = $(' <div class="weui-cells weui-cells_form"><input type="hidden" id="topic_' + id + '_tid" /></div>');

    let order = $('<div class="weui-cell"><div class="weui-cell__hd"><label class="weui-label">题目序号</label></div><div class="weui-cell__bd"><input class="weui-input" placeholder="题目的序号（从1开始）" id="topic_' + id + '_order" value="' + id + '" disabled="true" /></div></div>');
    let content = $('<div class="weui-cell"><div class="weui-cell__hd"><label class="weui-label">题目内容</label></div><div class="weui-cell__bd"><input class="weui-input" placeholder="请填写题目的内容" id="topic_' + id + '_content" /></div></div>');
    let keyword = $('<div class="weui-cell"><div class="weui-cell__hd"><label class="weui-label">关键词</label></div><div class="weui-cell__bd"><input class="weui-input" placeholder="该问题的关键词" id="topic_' + id + '_keyword" /></div></div>');
    let factor = $('<div class="weui-cell"><div class="weui-cell__hd"><label class="weui-label">所属因子</label></div><div class="weui-cell__bd"><input class="weui-input" placeholder="该问题归属于哪个因子？" id="topic_' + id + '_factor" onfocus="showPickers(\'topic_' + id + '_factor\');" /></div><div>');
    let opGroup = $('<div class="weui-cell"><div class="weui-cell__hd"><label class="weui-label">使用的选项组</label></div><div class="weui-cell__bd"><input class="weui-input" placeholder="该问题所使用的选项组是？" id="topic_' + id + '_opGroup" onfocus="showPickers(\'topic_' + id + '_opGroup\');" /></div></div>');

    // 组装开始
    body.append(order).append(content).append(keyword).append(factor).append(opGroup);
    topic.append(title).append(body);
    // 显示
    list.append(topic);
    // 更新全局变量
    topicNum += 1;
}


// 从topicList列表中删除指定题目，并将其之后的题目序数向前挪动一位
function deleteTopic(id) {

    // 解析序数，进行判断
    let order = parseInt(id.split("_")[1]);
    // 条件判断
    if (order < topicNum) {
        // 等待删除的topic并非列表中的最后一个，因此删除它之后还需要将其之后的所有topic的序数向前挪动一位
        for (let i = order + 1; i <= topicNum; i++) {
            $("#topic_" + i).attr("id", "topic_" + (i - 1));
            $("#topic_" + i + "_title").attr("id", "topic_" + (i - 1) + "_title").text("第" + (i - 1) + "题");
            $("#topic_" + i + "_link").attr("id", "topic_" + (i - 1) + "_link").attr("href", "javascript:deleteTopic('topic_" + (i - 1) + "');");
            $("#topic_" + i + "_tid").attr("id", "topic_" + (i - 1) + "_tid");
            $("#topic_" + i + "_order").attr("id", "topic_" + (i - 1) + "_order").val((i - 1)).attr("value", (i - 1));
            $("#topic_" + i + "_content").attr("id", "topic_" + (i - 1) + "_content");
            $("#topic_" + i + "_keyword").attr("id", "topic_" + (i - 1) + "_keyword");
            $("#topic_" + i + "_factor").attr("id", "topic_" + (i - 1) + "_factor").attr("onfocus", "showPickers('topic_" + (i - 1) + "_factor');");
            $("#topic_" + i + "_opGroup").attr("id", "topic_" + (i - 1) + "_opGroup").attr("onfocus", "showPickers('topic_" + (i - 1) + "_opGroup');");
        }
        $("#" + id).remove();
    } else if (order == topicNum) {
        // 待删除的topic是列表的最后一位，直接删除即可
        $("#" + id).remove();
    } else {
        // id不对
        weui.alert("ERROR：待删除的topic的id为：" + id + " 不存在");
        return;
    }
    // 调整全局变量
    topicNum -= 1;
}