<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>社区层级化组织管理系统平台</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<!-- Bootstrap CSS -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/bootstrap.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/weui-v2.1.3/weui.css">
<link
	href="https://cdn.bootcss.com/awesome-bootstrap-checkbox/v0.2.3/awesome-bootstrap-checkbox.css"
	rel="stylesheet" />
<link
	href="${pageContext.request.contextPath}/js/jquery-ui/jquery-ui.css"
	rel="stylesheet" />
<link
	href="${pageContext.request.contextPath}/js/easyui-v1.7.0/themes/icon.css"
	rel="stylesheet" />
<link
	href="${pageContext.request.contextPath}/js/easyui-v1.7.0/themes/default/easyui.css"
	rel="stylesheet" />
</head>
<body>


	<!-- =================Navbar（动态include）=============== -->
	<jsp:include page="/WEB-INF/pages/up/navbar.jsp"></jsp:include>
	<div class="container-fluid">
		<div class="row">
			<!-- =================Menu（动态include）=============== -->
			<jsp:include page="/WEB-INF/pages/left/menu.jsp"></jsp:include>

			<div class="col-xs-12 col-md-10 col-lg-10">
				<!-- =================Breadcromb（动态include）=============== -->
				<jsp:include page="/WEB-INF/pages/others/breadcromb.jsp"></jsp:include>
				<!-- =================================★★真正的页面数据内容显示区域★★===================================== -->
				<div class="container">
					<!-- ==========一切的开始=========== -->
					<!-- 第一页开始——量表基础信息 -->
					<div id="firstPage">
						<div class="weui-form">
							<div class="weui-form__text-area">
								<h2 class="weui-form__title">创建封闭式心理量表</h2>
								<div class="weui-form__desc">第一步：填写量表基础信息</div>
							</div>
							<!-- 基础信息部分-开始 -->
							<div class="weui-form__control-area">
								<div class="weui-cells__group weui-cells__group_form">
									<div class="weui-cells__title">量表基础信息</div>
									<div class="weui-cells weui-cells_form">

										<input type="hidden" id="esid" name="esid" />

										<div class="weui-cell">
											<div class="weui-cell__hd">
												<label class="weui-label">中文名</label>
											</div>
											<div class="weui-cell__bd">
												<input id="chName" name="chName" class="weui-input"
													placeholder="请输入量表的中文名称" />
											</div>
										</div>

										<div class="weui-cell">
											<div class="weui-cell__hd">
												<label class="weui-label">英文名</label>
											</div>
											<div class="weui-cell__bd">
												<input id="engName" name="engName" class="weui-input"
													placeholder="请输入量表的英文名称" />
											</div>
										</div>

										<div class="weui-cell">
											<div class="weui-cell__hd">
												<label class="weui-label">功能简介</label>
											</div>
											<div class="weui-cell__bd">
												<textarea class="weui-textarea" placeholder="面向患者的量表简介"
													rows="3" id="introduce" name="introduce"> </textarea>
												<div class="weui-textarea-counter">
													<span>0</span>/200
												</div>
											</div>
										</div>

										<div class="weui-cell">
											<div class="weui-cell__hd">
												<label class="weui-label">使用说明</label>
											</div>
											<div class="weui-cell__bd">
												<textarea class="weui-textarea" placeholder="面向咨询师的量表使用说明"
													rows="5" id="description" name="description"></textarea>
												<div class="weui-textarea-counter">
													<span>0</span>/500
												</div>
											</div>
										</div>

										<div class="weui-cell">
											<div class="weui-cell__hd">
												<label class="weui-label">权重值</label>
											</div>
											<div class="weui-cell__bd">
												<input id="weigh" name="weigh" class="weui-input"
													placeholder="请输入权重" type="number"  />
											</div>
										</div>

									</div>
								</div>
							</div>
							<!-- 基础信息部分-结束 -->
						</div>
					</div>
					<!-- 第一页结束——量表基础信息 -->

					<!-- 第二页开始——创建因子和计分规则 -->
					<div id="secondPage">
						<!-- 因子和标准-开始 -->
						<div class="weui-form">
							<div class="weui-form__text-area">
								<h2 class="weui-form__title">新建封闭式心理量表</h2>
								<div class="weui-form__desc">第二步：创建测量因子及诊断规则</div>
							</div>
							<!-- ============Factor和 section 动态生成的位置============ -->
							<div id="factorList">
								<!-- <div class="weui-form__control-area" id="factor_1">
                <div style="font-size:170%;">
                    <div class="row">
                        <div class="col-md-auto" id="factor_1_title">第1个因子</div>
                        <div class="col-md"></div>
                        <div class="col-md-auto"><a id="factor_1_link" href="javascript:deleteFactor('factor_1');"
                                class="weui-btn weui-btn_mini weui-btn_warn">X</a></div>
                    </div>
                </div>

                <div class="weui-cells weui-cells_form">
                    <input type="hidden" id="factor_1_fid" />
                    <div class="weui-cell">
                        <div class="weui-cell__hd"><label class="weui-label">因子名称</label></div>
                        <div class="weui-cell__bd"><input class="weui-input" placeholder="请填写因子的名称"
                                id="factor_1_name" />
                        </div>
                    </div>
                    <div class="weui-cell">
                        <div class="weui-cell__hd"><label class="weui-label">关键词</label></div>
                        <div class="weui-cell__bd"><input class="weui-input" placeholder="概括该因子的关键词"
                                id="factor_1_keyword" />
                        </div>
                    </div>
                    <div class="weui-cell">
                        <div class="weui-cell__hd"><label class="weui-label">因子简介</label></div>
                        <div class="weui-cell__bd">
                            <textarea class="weui-textarea" placeholder="因子简介" rows="3"
                                id="factor_1_description"></textarea>
                            <div class="weui-textarea-counter"><span>0</span>/200</div>
                        </div>
                    </div>
                </div>

                <div class="weui-panel weui-panel_access">
                    <div class="weui-panel__hd">诊断标准</div>
                    <div class="weui-panel__bd" id="factor_1_sectionList">
                        <div class="weui-media-box weui-media-box_text" id="factor_1_section_1">
                            <div class="row">
                                <div class="col-md-auto">
                                    <h4 class="weui-media-box__title" id="factor_1_section_1_title">标准1</h4>
                                </div>
                                <div class="col-md">
                                </div>
                                <div class="col-md-auto">
                                    <a id="factor_1_section_1_link"
                                        href="javascript:deleteSection('factor_1_section_1');"
                                        class="weui-btn weui-btn_mini weui-btn_warn">X</a>
                                </div>
                            </div>

                            <div class="weui-cells weui-cells_form">
                                <input type="hidden" id="factor_1_section_1_sid" />
                                <div class="weui-cell">
                                    <div class="weui-cell__hd"><label class="weui-label">规则名称</label></div>
                                    <div class="weui-cell__bd"><input class="weui-input" placeholder="请填写规则的名称"
                                            id="factor_1_section_1_name" />
                                    </div>
                                </div>
                                <div class="weui-cell">
                                    <div class="weui-cell__hd"><label class="weui-label">规则描述</label></div>
                                    <div class="weui-cell__bd">
                                        <textarea class="weui-textarea" placeholder="规则描述" rows="3"
                                            id="factor_1_section_1_description"></textarea>
                                        <div class="weui-textarea-counter"><span>0</span>/200</div>
                                    </div>
                                </div>
                                <div class="weui-cell">
                                    <div class="weui-cell__hd"><label class="weui-label">分值标准</label></div>
                                    <div class="weui-cell__bd">
                                        <input type="number" id="factor_1_section_1_minScore" style="width: 10%;"
                                            onchange="checkScore4Section('factor_1_section_1','minScore');" />
                                        &nbsp&nbsp≤&nbsp&nbsp加权得分&nbsp&nbsp≤&nbsp&nbsp
                                        <input type="number"
                                            onchange="checkScore4Section('factor_1_section_1','maxScore');"
                                            id="factor_1_section_1_maxScore" style="width: 10%;" />
                                    </div>
                                </div>
                                <div class="weui-cell">
                                    <div class="weui-cell__hd"><label class="weui-label">诊断用语</label></div>
                                    <div class="weui-cell__bd">
                                        <textarea class="weui-textarea" placeholder="分值基于该范围内时的诊断用语" rows="3"
                                            id="factor_1_section_1_diagnosis"></textarea>
                                        <div class="weui-textarea-counter"><span>0</span>/500</div>
                                    </div>
                                </div>

                            </div>


                        </div>
                    </div>
                </div>

                <div class="weui-panel__ft">
                    <a href="javascript:addSection('factor_1');" id="factor_1_Btn4addSection" class="weui-cell weui-cell_access weui-cell_link">
                        <div class="weui-cell__bd" id="factor_1_title4Btn4addSection">为因子1新增诊断标准</div>
                        <span class="weui-cell__ft"></span>
                    </a>
                </div>
            </div> -->

							</div>
						</div>
						<!-- 因子和标准-结束 -->
					</div>
					<!-- 第二页结束——创建因子和计分规则 -->

					<!-- 第三页开始——创建选项组和选项 -->
					<div id="thirdPage">
						<!-- 选项组及选项部分-开始 -->
						<div class="weui-form">
							<div class="weui-form__text-area">
								<h2 class="weui-form__title">新建封闭式心理量表</h2>
								<div class="weui-form__desc">第三步：创建选项组及选项</div>
							</div>
							<!-- ============optionGroup 和 option动态生成的位置============ -->
							<div id="opGroupList">
								<!-- <div class="weui-form__control-area" id="opGroup_1">
                <div style="font-size:170%;">
                    <div class="row">
                        <div class="col-md-auto" id="opGroup_1_title">第1个选项组</div>
                        <div class="col-md"></div>
                        <div class="col-md-auto"><a id="opGroup_1_link" href="javascript:deleteOpGroup('opGroup_1');"
                                class="weui-btn weui-btn_mini weui-btn_warn">X</a></div>
                    </div>
                </div>

                <div class="weui-cells weui-cells_form">
                    <input type="hidden" id="opGroup_1_ogid" />
                    <div class="weui-cell">
                        <div class="weui-cell__hd"><label class="weui-label">选项组名称</label></div>
                        <div class="weui-cell__bd"><input class="weui-input" placeholder="请填写选项组的名字，用于识别"
                                id="opGroup_1_name" />
                        </div>
                    </div>
                    <div class="weui-cell">
                        <div class="weui-cell__hd"><label class="weui-label">选项组简介</label></div>
                        <div class="weui-cell__bd">
                            <textarea class="weui-textarea" placeholder="请简单介绍何种情况下使用该选项组用作题目的选项" rows="2"
                                id="opGroup_1_introduce"></textarea>
                            <div class="weui-textarea-counter"><span>0</span>/200</div>
                        </div>
                    </div>
                </div>

                <div class="weui-panel weui-panel_access">
                    <div class="weui-panel__hd">选项组内的选项列</div>
                    <div class="weui-panel__bd" id="opGroup_1_optionList">


                        <div class="weui-media-box weui-media-box_text" id="opGroup_1_option_1">
                            <div class="row">
                                <div class="col-md-auto">
                                    <h4 class="weui-media-box__title" id="opGroup_1_option_1_title">选项1</h4>
                                </div>
                                <div class="col-md">
                                </div>
                                <div class="col-md-auto">
                                    <a id="opGroup_1_option_1_link"
                                        href="javascript:deleteOption('opGroup_1_option_1');"
                                        class="weui-btn weui-btn_mini weui-btn_warn">X</a>
                                </div>
                            </div>
                            <div class="weui-cells weui-cells_form">
                                <input type="hidden" id="opGroup_1_option_1_opid" />
                                <div class="weui-cell">
                                    <div class="weui-cell__hd"><label class="weui-label">选项序数</label></div>
                                    <div class="weui-cell__bd"><input class="weui-input"
                                            placeholder="选项在选项组内的排列顺序（从1开始）" value="1" disabled="true" type="number"
                                            id="opGroup_1_option_1_order" />
                                    </div>
                                </div>
                                <div class="weui-cell">
                                    <div class="weui-cell__hd"><label class="weui-label">选项文本</label></div>
                                    <div class="weui-cell__bd"><input class="weui-input" placeholder="出现在量表中供被试者参考的选项文本"
                                            id="opGroup_1_option_1_content" />
                                    </div>
                                </div>
                                <div class="weui-cell">
                                    <div class="weui-cell__hd"><label class="weui-label">选项分值</label></div>
                                    <div class="weui-cell__bd">
                                        <input type="number" id="opGroup_1_option_1_score" />
                                    </div>
                                </div>
                            </div>
                        </div>


                    </div>
                </div>

                <div class="weui-panel__ft">
                    <a href="javascript:addOption('opGroup_1');" id="opGroup_1_Btn4addOption"
                        class="weui-cell weui-cell_access weui-cell_link">
                        <div class="weui-cell__bd" id="opGroup_1_title4Btn4addOption">为选项组1添加选项</div>
                        <span class="weui-cell__ft"></span>
                    </a>
                </div>

            </div> -->
							</div>
						</div>
						<!-- 选项组及选项部分-结束 -->
					</div>
					<!-- 第三页结束——创建选项组和选项 -->

					<!-- 第四页开始——创建题目（设置使用的选项组和归属的因子） -->
					<div id="fourthPage">
						<!-- 题目部分-开始 -->
						<div class="weui-form">
							<div class="weui-form__text-area">
								<h2 class="weui-form__title">新建封闭式心理量表</h2>
								<div class="weui-form__desc">第四步：创建题目</div>
							</div>
							<!-- ==========所有topic动态生成的位置============ -->
							<div id="topicList">
								<!-- <div class="weui-form__control-area" id="topic_1">

                <div style="font-size:170%;">
                    <div class="row">
                        <div class="col-md-auto" id="topic_1_title">第1题</div>
                        <div class="col-md"></div>
                        <div class="col-md-auto"><a id="topic_1_link" href="javascript:deleteTopic('topic_1');"
                                class="weui-btn weui-btn_mini weui-btn_warn">X</a></div>
                    </div>
                </div>

                <div class="weui-cells weui-cells_form">
                    <input type="hidden" id="topic_1_tid" />
                    <div class="weui-cell">
                        <div class="weui-cell__hd"><label class="weui-label">题目序号</label></div>
                        <div class="weui-cell__bd"><input class="weui-input" placeholder="题目的序号（从1开始）"
                                id="topic_i_order" value="1" disabled="true" />
                        </div>
                    </div>
                    <div class="weui-cell">
                        <div class="weui-cell__hd"><label class="weui-label">题目内容</label></div>
                        <div class="weui-cell__bd"><input class="weui-input" placeholder="请填写题目的内容"
                                id="topic_1_content" />
                        </div>
                    </div>
                    <div class="weui-cell">
                        <div class="weui-cell__hd"><label class="weui-label">关键词</label></div>
                        <div class="weui-cell__bd"><input class="weui-input" placeholder="该问题的关键词"
                                id="topic_1_keyword" />
                        </div>
                    </div>
                    <div class="weui-cell">
                        <div class="weui-cell__hd"><label class="weui-label">所属因子</label></div>
                        <div class="weui-cell__bd"><input class="weui-input" placeholder="该问题归属于哪个因子？"
                                id="topic_1_factor" onfocus="showPickers('topic_1_factor');" />
                        </div>
                    </div>
                    <div class="weui-cell">
                        <div class="weui-cell__hd"><label class="weui-label">使用的选项组</label></div>
                        <div class="weui-cell__bd"><input class="weui-input" placeholder="该问题所使用的选项组是？"
                                id="topic_1_opGroup" onfocus="showPickers('topic_1_opGroup');" />
                        </div>
                    </div>
                </div>

            </div> -->
							</div>
						</div>
						<!-- 题目部分-结束 -->
					</div>
					<!-- 第四页开始——创建题目（设置使用的选项组和归属的因子） -->

					<!-- 第五页开始——预览 -->
					<div id="finalPage">
						<div class="weui-form">
							<div class="weui-form__text-area">
								<h2 class="weui-form__title">新建封闭式心理量表</h2>
								<div class="weui-form__desc">最后一步：创建前预览</div>
							</div>

							<dl class="row mt-3">
								<dt class="col-sm-3">中文名</dt>
								<dd class="col-sm-9" id="fp_chName">焦虑自评量表</dd>

								<dt class="col-sm-3">英文名</dt>
								<dd class="col-sm-9" id="fp_engName">SAS</dd>

								<dt class="col-sm-3">量表简介</dt>
								<dd class="col-sm-9">
									<p id="fp_introduce">
										焦虑自评量表,无论量表的构造形式还是具体的评定办法,都与抑郁自评量表十分相似。它也是一个含有20个项目,分为4级评分的自评量表,用于评出焦虑病人的主观感受。
									</p>
								</dd>

								<dt class="col-sm-3">使用方法</dt>
								<dd class="col-sm-9">
									<p id="fp_description">
										SAS采用4级评分,主要评定项目所定义的症状出现的频度,其标准为:“1”没有或很少时间:“2”小部分时间:“3”相当多的时间；“4”绝大部分或全部时间。(其中“1”“2”“3”“4”均指计分分数)
									</p>
								</dd>

								<dt class="col-sm-3">权重值</dt>
								<dd class="col-sm-9" id="fp_weigh">1.25</dd>

								<dt class="col-sm-3">题目总数</dt>
								<dd class="col-sm-9" id="fp_total_topic">20</dd>

								<dt class="col-sm-3">包含因子数</dt>
								<dd class="col-sm-9" id="fp_total_factor">4</dd>

								<dt class="col-sm-3">选项组数</dt>
								<dd class="col-sm-9" id="fp_total_opGroup">4</dd>

								<table class="table table-bordered mt-3">
									<thead>
										<tr>
											<th scope="col">序数</th>
											<th scope="col">题目</th>
											<th scope="col">关键词</th>
											<th scope="col">所属因子</th>
											<th scope="col">所用选项组</th>
											<th scope="col">选项</th>
										</tr>
									</thead>
									<tbody id="fp_tbody">

										<tr>
											<td scope="row">1
											</th>

											<td>我觉得一切都很好，也不会发生什么不幸</td>

											<td>不幸预感</td>

											<td>第一因子</td>
											<td>第一选项组</td>

											<td>
												<div class="form-check form-check-inline">
													<label class="form-check-label" for="inlineRadio1">没有或偶尔</label>
													<input class="form-check-input" type="radio"
														name="topic_1_option" id="inlineRadio1" value="option1">
												</div>
												<div class="form-check form-check-inline">
													<label class="form-check-label" for="inlineRadio2">有时如此</label>
													<input class="form-check-input" type="radio"
														name="topic_1_option" id="inlineRadio2" value="option1">
												</div>
												<div class="form-check form-check-inline">
													<label class="form-check-label" for="inlineRadio3">经常如此</label>
													<input class="form-check-input" type="radio"
														name="topic_1_option" id="inlineRadio3" value="option1">
												</div>
												<div class="form-check form-check-inline">
													<label class="form-check-label" for="inlineRadio4">总是如此</label>
													<input class="form-check-input" type="radio"
														name="topic_1_option" id="inlineRadio4" value="option1">
												</div>
											</td>

										</tr>


									</tbody>
								</table>
						</div>
					</div>
					<!-- 第五页结束——预览 -->

					<!-- 底部按钮部分 -->
					<div class="row mt-5">
						<div class="col"></div>
						<div class="col-auto">
							<div class="weui-form__opr-area">
								<div class="row">
									<div class="col" id="preBtn">
										<a class="weui-btn weui-btn_primary"
											href="javascript:preBtn();">上一步</a>
									</div>

									<div class="col" id="addFactorBtn">
										<a class="weui-btn self_weui-btn_blue"
											href="javascript:addFactor();">新增因子</a>
									</div>
									<div class="col" id="addOpGroupBtn">
										<a class="weui-btn self_weui-btn_blue"
											href="javascript:addOpGroup();">新增选项组</a>
									</div>
									<div class="col" id="addTopicBtn">
										<a class="weui-btn self_weui-btn_blue"
											href="javascript:addTopic();">新增题目</a>
									</div>
									<div class="col" id="createScaleBtn">
										<a class="weui-btn self_weui-btn_warn"
											href="javascript:sendData();">创建量表</a>
									</div>

									<div class="col" id="nextBtn">
										<a class="weui-btn weui-btn_primary weui-btn_primary"
											href="javascript:nextBtn();">下一步</a>
									</div>
								</div>
							</div>
						</div>
						<div class="col"></div>
					</div>

				</div>
			</div>
			
		</div>
	</div>
</body>
<script src="${pageContext.request.contextPath}/js/jquery-3.2.0.js"></script>
<script
	src="${pageContext.request.contextPath}/js/jquery-ui/jquery-ui.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
<script src="${pageContext.request.contextPath}/js/popper.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.bundle.js"></script>
<script
	src="${pageContext.request.contextPath}/js/easyui-v1.7.0/jquery.easyui.min.js"></script>
<script
	src="${pageContext.request.contextPath}/js/layui-v2.5.5/layui/layui.js"></script>

<!-- wechat相关 -->
<script src="http://res.wx.qq.com/open/js/jweixin-1.4.0.js"></script>
<script src="https://res.wx.qq.com/open/libs/weuijs/1.2.1/weui.min.js"></script>
<script src="${pageContext.request.contextPath}/js/myJS.js"></script>
<!-- 本页专属 -->
<script
	src="${pageContext.request.contextPath}/js/health4EnclosedScale/myScript.js"></script>
<script
	src="${pageContext.request.contextPath}/js/health4EnclosedScale/factor_section_page.js"></script>
<script
	src="${pageContext.request.contextPath}/js/health4EnclosedScale/group_option_page.js"></script>
<script
	src="${pageContext.request.contextPath}/js/health4EnclosedScale/topic_page.js"></script>

<script type="text/javascript">

	// 从服务器获取量表详细信息，显示特定页面单元中并展示出来
	function enclosedScaleInfo(esid) {
		weui.alert(esid);
	}

	// 跳转到
</script>

</html>