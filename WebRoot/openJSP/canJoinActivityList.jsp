<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>可报名参加的活动</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<!-- Bootstrap CSS -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/bootstrap.css">
<link rel="stylesheet"
	href="https://res.wx.qq.com/open/libs/weui/1.1.3/weui.min.css">
</head>

<body style="background-color: #f8f8f8;">


	<div>
		<!--头部信息-->
		<div style="padding: 30px;">
			<h1
				style="text-align: left;font-size: 20px;font-weight: 400;font-weight: bold;">选择可参加的活动</h1>
			<p
				style="margin-top: 5px;color: #888;text-align: left;font-size: 14px;">在这里您可以选择自己喜欢的活动进行报名</p>
		</div>
		<s:iterator value="#list">
			<!--容纳各种PreView-->
			<div>
				<!--这是一个preView，用来展示一个活动信息-->
				<div class="weui-form-preview mt-2">
					<!--头-->
					<div class="weui-form-preview__hd">
						<div class="weui-form-preview__item">
							<label class="weui-form-preview__label">活动名称</label> <em
								class="weui-form-preview__value" id="name"><s:property value="name"/></em>
						</div>
					</div>
					<!--体-->
					<div class="weui-form-preview__bd">
						<div class="weui-form-preview__item">
							<label class="weui-form-preview__label">发起方</label> 
							<s:if test="project.zeroLevel==null">
								<span class="weui-form-preview__value" id="initiator"><s:property value="project.minusFirstLevel.name"/></span>
							</s:if>
							<s:elseif test="project.firstLevel==null">
								<span class="weui-form-preview__value" id="initiator"><s:property value="project.zeroLevel.name"/></span>
							</s:elseif>
							<s:elseif test="project.secondLevel==null">
								<span class="weui-form-preview__value" id="initiator"><s:property value="project.firstLevel.name"/></span>
							</s:elseif>
							<s:elseif test="project.thirdLevel==null">
								<span class="weui-form-preview__value" id="initiator"><s:property value="project.secondLevel.name"/></span>
							</s:elseif>
							<s:else>
								<span class="weui-form-preview__value" id="initiator"><s:property value="project.thirdLevel.name"/></span>
							</s:else>
						</div>
						<div class="weui-form-preview__item">
							<label class="weui-form-preview__label">活动时间</label> <span
								class="weui-form-preview__value" id="time"><s:property value="beginTimeStr"/>到<s:property value="endTimeStr"/></span>
						</div>
						<div class="weui-form-preview__item">
							<label class="weui-form-preview__label">活动简介</label> 
							<span class="weui-form-preview__value" id=”description“>
								<s:property value="description"/>	
							</span>
						</div>
						<div class="weui-form-preview__item">
							<label class="weui-form-preview__label">活动地点</label> 
							<span class="weui-form-preview__value" id="position">
								<s:if test="activityType==1">
									<s:property value="geographic.name"/>	
								</s:if>
								<s:elseif test="activityType==2">
									<s:property value="house.name"/>	
								</s:elseif>
							</span>
						</div>
						<!-- <div class="weui-form-preview__item">
                        <label class="weui-form-preview__label">活动地点</label>
                        <span class="weui-form-preview__value" id="place">核桃园社区会议室</span>
                    </div> -->
						<div class="weui-form-preview__item">
							<label class="weui-form-preview__label">活动积分</label> <span
								class="weui-form-preview__value" id="score"><s:property value="score"/></span>
						</div>
						<div class="weui-form-preview__item">
							<label class="weui-form-preview__label">人数限制</label> <span
								class="weui-form-preview__value" id="limit"> <!--badge-primary|secondary|success|danger|warning|info|light|dark-->
								<span class="badge badge-success">
									<s:if test="baoMingUplimit!=-1">
										<s:property value="visitors.size()"/>/<s:property value="baoMingUplimit"/>
									</s:if>
									<s:else>
										无限制
									</s:else>
								</span>
							</span>
						</div>
					</div>
					<!--足-->
					<div class="weui-form-preview__ft">
						<!--<s:a onclick="baoming('%{aid}');" href="#" cssClass="weui-form-preview__btn weui-form-preview__btn_primary">报名</s:a> -->
						<s:a cssClass="weui-form-preview__btn weui-form-preview__btn_primary"
														role="button" type="button"
														data-toggle="modal" data-target="#modelId"
														onclick="aid='%{aid}';" href="#">报名参加</s:a>
					</div>
				</div>
			</div>
		</s:iterator>
	</div>


	<!--FOOT-->
	<div class="weui-footer mt-5">
		<p class="weui-footer__text">Copyright &copy; 2017-2019
			联合会提供技术支持</p>
		<p class="weui-footer__links">
			<a href="javascript:void(0);" class="weui-footer__link">访问我们</a>
		</p>
	</div>

<!-- 
	=============================MODAL==========================
 -->
<!-- 创建活动前需要 同意的《志愿者安全行为守则》条款的Modal -->
		<div class="modal fade" id="modelId" tabindex="-1" role="dialog"
			aria-labelledby="modelTitleId" aria-hidden="true">
			<div class="modal-dialog modal-lg" role="document">
				<div class="modal-content">

					<div class="modal-header">
						<h4 class="modal-title" id="modelTitleId">社区志愿服务安全行为守则</h4>
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>

					<div class="modal-body">
						<div class="container-fluid">

							<div class="row">
								<div id="ruler" data-spy="scroll" data-target="#navbar-example"
									data-offset="0"
									style="color: #24bcbd;height:400px;overflow:auto;position:relative;text-indent:35px">
									<p>为丰富社区居民日常文化生活，促进和加强居民之间健康交流，小红帆驿站活动室特提供日常居民文化活动服务功能。为保障活动室人员健康安全，维护公共财产现制定相关安全管理制度如下，请广大居民遵照执行。</p>
									1、文体活动室由东大桥社区居委会和小红帆驿站负责全面管理和协调，社区拥有对活动室的最终解释权。<br>
									2、活动室的开放时间为：工作日上午9：00—11：00， 下午
									14：00-16：00，晚上开放。活动室使用者须严格遵守开放时间，如无特殊情况不得随意要求提前或延长开放时间。<br>
									3、所有活动室使用团队在正式开展活动之前必须进行规定时长的包括防火、防盗、紧急救护、制度学习等安全教育，否则不予批准使用。室内包括电灯、水源开关、网络、音响等机械和电子设备使用方法须对各个领队进行培训，确保其独立使用时安全合规。<br>
									4、请自行保管好随身携带的物品，避免携带贵重物品或现金参加活动，防止遗失或被窃。<br>
									5、禁止携带易燃易爆、尖锐、有毒、生物威胁等危险品进入，请活动室参与者相互监督彼此提醒，一旦发现将立刻取消该团队的活动室使用权限。<br>
									6、成年人活动禁止带领婴幼儿和宠物参加，防止发生走失、意外伤害等突发情况。<br>
									7、活动室内禁止开展登高、跳远、搏击、球类等剧烈对抗运动，防止意外事故发生。<br>
									8、对于活动室内出现诸如灯泡、水管、暖气、线路等设施损坏的情况，禁止居民和志愿者擅自更换和维修，应立刻向小红帆和社区负责人报告及时维修。<br>
									9、文体活动室主要为社区居民和小红帆志愿服务团队服务，原则上不对外开放。如需邀请外部人员参与活动，需经提前与社区负责人沟通在获得同意的情况下方可使用。<br>
									凡在活动室开展活动，须遵守活动规则，讲求文明礼貌，注意公共卫生；不随地吐痰、乱丢垃圾；不在室内争吵打闹，不大声喧哗；不在墙壁、
									地面、桌面上涂抹乱画、便溺等；不在室内吸烟、酗酒；不在室内进行赌博等违法活动。以上情况一经发现，取消其以后使用活动室的权利。<br>
									10、活动室内一切设施、设备、器具，仅供就地使用，除桌椅板凳外的器械设备不得随意挪动或带离。否则将按照带出设备、器具的实际价格给予相应罚款。<br>
									11、活动人员须按照设施、设备、器具使用规则安全使用，使用完毕须归放原处，不得随意摆放影响后来者使用。活动人员须爱护室内设施、设备、器具，如有人为损坏，照价赔偿。<br>
									12、活动人员离场时，务必由专人负责切断电源、水源、关闭门窗、打扫卫生。<br>
									13、在活动室内开展志愿服务活动，除遵守本管理制度外，还应严格遵守《小红帆社区志愿者安全行为守则》以保护服务对象和志愿者自身安全。<br>
									14、违反以上规定的团队，一经发现按照情节严重程度给予记过，屡犯或情节特别严重者将被立刻剥夺活动室使用权利。<br>
								</div>
							</div>
							<div class="row mt-4">
								<div class="form-check">
									<label class="form-check-label">
										<div id="agreeDiv" hidden="true">
											<input type="checkbox" class="form-check-input" name="agree"
												id="agree" value="1" > <span
												style="color: #8e8e8e">认真贯彻志愿者安全行为规范</span>
										</div>
									</label>
								</div>
							</div>


						</div>
					</div>

					<div class="modal-footer">
						<button type="button" class="btn btn-secondary"
							data-dismiss="modal">取消</button>
						<button type="button" id="nextBtn" class="btn btn-primary"
							disabled="true" onclick="baoming();">继续</button>
					</div>

				</div>
			</div>
		</div>




</body>
<script src="${pageContext.request.contextPath}/js/jquery-3.2.0.js"></script>
<script src="${pageContext.request.contextPath}/js/popper.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.bundle.js"></script>
<script src="http://res.wx.qq.com/open/js/jweixin-1.4.0.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/myJS.js"></script>
<script type="text/javascript"
	src="https://res.wx.qq.com/open/libs/weuijs/1.1.4/weui.min.js"></script>
<script>
	var aid = "";  // 用户所要报名的活动aid

	// 执行报名业务逻辑
	function baoming() {
		weui.confirm('自定义按钮的confirm', {
			title : '是否确定报名？',
			content : "报名后该活动将移动到“已报名活动”列表，您可以提前一天取消报名",
			buttons : [ {
				label : '再想想',
				type : 'default',
				onClick : function() {
					console.log("取消报名");
				}
			}, {
				label : '确定报名',
				type : 'primary',
				onClick : function() {
					// 执行报名逻辑ajax操作
					let url = "personalCenterAction_baoMing.action";
					let param = {
						aid: aid
					}
					$.post(url, param, function(data, textStatus, req) {
						if(data.result){
							// 报名成功
							weui.alert(data.message);
							// 刷新页面
							window.location.reload();
						}else{
							// 报名失败
							weui.alert(data.message);
						}
					});
				}
			} ]
		});

	}
	
	// =========================与《志愿者安全行为守则》协议相关的操作========================
	var nScrollHight = 0; //滚动距离总长(注意不是滚动条的长度)
	var nScrollTop = 0; //滚动到的当前位置
	var nDivHight = $("#ruler").height(); // 当前div的height像素值
	$(function() {
		$("#ruler").scroll(function() {
			nScrollHight = $(this)[0].scrollHeight;
			nScrollTop = $(this)[0].scrollTop;
			if (nScrollTop + nDivHight >= nScrollHight) {
				console.log("滚动条到底部了")
				$("#agreeDiv").attr("hidden", false);
			}
		});

		$("#agree").change(function() {
			var self = $(this).get(0);
			console.log("复选框选中状态为：" + self.checked);
			if (self.checked) {
				$("#nextBtn").attr("disabled", false);
			} else {
				$("#nextBtn").attr("disabled", true);
			}
		});
	});
	
</script>
</html>