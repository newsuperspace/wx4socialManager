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
	href="https://res.wx.qq.com/open/libs/weui/1.1.3/weui.min.css">
</head>
<body style="background-color: #f8f8f8;">
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
				<div class="row">
					<div class="col">
						<!-- =============标题=========== -->
						<div
							class="justify-content-between d-flex flex-wrap flex-md-nowrap align-items-center pb-1 mb-4 border-bottom">
							<h1 class="h2">编辑新闻稿</h1>
							<div class="btn-toolbar mb-2 mb-md-0">
								<div class="btn-group mr-2">

									<button class="btn btn-sm btn-outline-secondary"
										data-toggle="modal" data-target="#selectProjects">
										<span class="glyphicon glyphicon-search"></span> 模态1
									</button>
									<button class="btn btn-sm btn-outline-secondary"
										data-toggle="modal" data-target="#orderProjects">
										<span class="glyphicon glyphicon-sort-by-order"></span> 模态2
									</button>
									<div class="dropdown ml-1">
										<button
											class="btn btn-sm btn-outline-secondary dropdown-toggle"
											type="button" id="others" data-toggle="dropdown"
											aria-haspopup="true" aria-expanded="false">
											<span class="glyphicon glyphicon-cog"></span> 其他
										</button>
										<div class="dropdown-menu dropdown-menu-right"
											aria-labelledby="others">
											<a class="dropdown-item" href="#" onclick="alert('功能待开放');">功能1</a>
											<a class="dropdown-item disabled" href="#">功能2 action</a>
											<h6 class="dropdown-header">特殊功能</h6>
											<a class="dropdown-item" href="#">功能3</a>
											<div class="dropdown-divider"></div>
											<a class="dropdown-item" href="#">功能4</a>
										</div>
									</div>
								</div>
							</div>
						</div>
						<!-- =============文章编辑开始=========== -->
						<input type="hidden" name="artid" id="artid"
							value="<s:property value='artid'/>" /> <input type="hidden"
							name="aid" id="aid" value='<s:property value="activity.aid"/>' />


						<div class="weui-cells__title">文章标题</div>
						<div class="weui-cells">
							<div class="weui-cell">
								<div class="weui-cell__bd">
									<input class="weui-input" type="text" placeholder="请输入30字以内文本"
										id="title" name="title" value="<s:property value='%{title}'/>" />
								</div>
							</div>
						</div>

						<div class="weui-cells__title">活动日期</div>
						<div class="weui-cells">
							<div class="weui-cell">
								<div class="weui-cell__bd">
									<input class="weui-input" disabled="disabled" type="text"
										placeholder="请输入文本"
										value="<s:property value='%{activity.endTimeStr}'/>" />
								</div>
							</div>
						</div>

						<div class="weui-cells__title">新闻稿</div>
						<div class="weui-cells weui-cells_form">
							<div class="weui-cell">
								<div class="weui-cell__bd">
									<textarea class="weui-textarea" placeholder="请输入文本" rows="5"
										id="content" name="content" oninput="getNumbers(this);"><s:property
											value='content' /></textarea>
									<div class="weui-textarea-counter">
										<span id="total"><s:property value="content.length()" /></span>/500
									</div>
								</div>
							</div>
						</div>

						<!-- gallery是用来展示上传的图片和删除图片之用 -->
						<div class="weui-gallery" id="gallery" data-apid="">
							<span class="weui-gallery__img" id="galleryImg"></span>
							<div class="weui-gallery__opr">
								<a href="javascript:" rel="external nofollow"
									class="weui-gallery__del"> <i
									class="weui-icon-delete weui-icon_gallery-delete"></i>
								</a>
							</div>
						</div>
						<!-- 上传图片之用 -->
						<div class="weui-cells weui-cells_form" id="uploader">
							<div class="weui-cell">
								<div class="weui-cell__bd">
									<div class="weui-uploader">
										<div class="weui-uploader__hd">
											<p class="weui-uploader__title">图片上传</p>
											<div class="weui-uploader__info">
												<span id="uploadCount"><s:property
														value="photos.size()" /></span>/5
											</div>
										</div>
										<div class="weui-uploader__bd">
											<ul class="weui-uploader__files" id="uploaderFiles">
												<!-- 这里存放着上传的图片的预览所谓图列表 -->
												<s:iterator value="photos">
													<li class="weui-uploader__file"
														style="background-image:url(<s:property value='%{url}' />)"
														id="<s:property value='%{apid}' />"></li>
												</s:iterator>
												<!-- 触发上传的+号按钮 -->
												<div class="weui-uploader__input-box">
													<input id="uploaderInput" class="weui-uploader__input"
														type="file" accept="image/*" capture="camera" multiple />
												</div>
											</ul>
										</div>
									</div>
								</div>
							</div>
						</div>
						<!-- =============文章编辑结束=========== -->
						<a href="javascript:save();"
							class="weui-btn weui-btn_primary mt-3 mb-2">保存</a>

					</div>
				</div>
			</div>
		</div>
		<!-- =================================================模态对话框==================================================== -->
		<!-- container结束 -->
	</div>
</body>
<script src="${pageContext.request.contextPath}/js/jquery-3.2.0.js"></script>
<script src="${pageContext.request.contextPath}/js/popper.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.bundle.js"></script>
<script src="http://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/myJS.js"></script>
<script type="text/javascript"
	src="https://res.wx.qq.com/open/libs/weuijs/1.1.4/weui.min.js"></script>
<script>

	//  保存文章
	function save() {
		let url = "articleAction_saveActicle.action";
		let param = {
			artid : $("#artid").val(),
			content : $("#content").val(),
			title : $("#title").val(),
		// photos的保存是随着图片上传实时完成的
		};
		$.post(url, param, function(data, textStatus, req) {
			weui.alert(data.message);
			let aid = $("#aid").val();
			$(location).attr("href", "articleAction_getArticle.action?aid=" + aid);
		});
	}

	// 字数统计
	function getNumbers(self) {
		$self = $(self);
		let area = $self.val();
		let len = area.length;
		console.log(len);
		$("#total").text(len);
	}

	// 初始化
	$(function() {
		// -----------------与Gallery相关的初始化操作------------------
		var $gallery = $("#gallery");
		var $galleryImg = $("#galleryImg");
		// 当用户点击gallery上的其他部分时会关闭该gallery
		$gallery.on("click", function() {
			$gallery.fadeOut(100);
		});

		// 点击gallery下面的"垃圾桶"按钮实现图片删除操作
		$(".weui-gallery__del").click(function() {
			let apid = $gallery.attr("data-apid");
			console.log('删除了' + apid);
			// 先移除<ul>中代表该图片的缩微图<li>
			$("#" + apid).remove();
			--uploadCount;
			$("#uploadCount").text(uploadCount);
			// 通过AJAX 从后台删除该图片
			let param = {
				apid : apid
			}
			$.post("articleAction_deletePhoto.action", param, function(data, textStatus, req) {
				weui.alert(data.message);
			});
		});
		$("li.weui-uploader__file").unbind().bind("click", function() {
			// 告诉gallery放大显示什么图片
			$galleryImg.attr("style", $(this).attr("style"));
			// 在gallery上设置所放大图片的apid，用作删除之用
			$gallery.attr("data-apid", $(this).attr("id"));
			// 然后放大显示图片
			$gallery.fadeIn(100);
		});


		// -----------------与使用weui.js上传图片相关的初始化操作------------------
		// 通过onBeforeQueued()校验的允许上传的图片的数量
		var uploadCount = parseInt($("#uploadCount").text());
		// 用来记录上传文件信息的全局属性
		var fileName = "";

		weui.uploader('#uploader', {
			url : 'articleAction_uploadPhoto.action',
			auto : false,
			type : 'file',
			fileVal : 'file',
			compress : {
				width : 1600,
				height : 1600,
				quality : .8
			},

			// 文件添加前的回调，return false则不添加，通常我们会把格式校验添加到这里
			onBeforeQueued : function(files) {
				// `this` 是轮询到的文件, `files` 是所有文件
				if ([ "image/jpg", "image/jpeg", "image/png", "image/gif" ].indexOf(this.type) < 0) {
					weui.alert('请上传图片');
					return false; // 阻止文件添加
				}
				if (this.size > 10 * 1024 * 1024) {
					weui.alert('请上传不超过10M的图片');
					return false;
				}
				if (files.length > 5) { // 防止一下子选择过多文件
					weui.alert('最多只能上传5张图片，请重新选择');
					return false;
				}
				if (uploadCount + 1 > 5) {
					weui.alert('最多只能上传5张图片');
					return false;
				}
				// 校验成功，计数器+1
				++uploadCount;
				$("#uploadCount").text(uploadCount);
				return true; // 返回false会阻止默认行为，不插入预览图的框架
			},

			// 文件添加成功的回调,this包含刚刚成功通过onBeforeQueued校验允许上传的图片文件的全部关键信息,其中包含的信息如下：
			// id: 1
			// lastModified: 1534309141540
			// lastModifiedDate: Wed Aug 15 2018 12: 59: 01 GMT + 0800(中国标准时间) {}
			// name: "“互联网+社会工作” 保障社区志愿服务安全开展 项目结构.png"
			// size: 85094
			// status: "ready"
			// stop: ƒ()
			// type: "image/png"
			// upload: ƒ()
			// url: "blob:null/32f13f99-d241-4575-aee8-36ee016ce587"
			onQueued : function() {
				console.log(this);

				// console.log(this.status); // 文件的状态：'ready', 'progress', 'success', 'fail'
				// console.log(this.base64); // 如果是base64上传，file.base64可以获得文件的base64
				var dom = '<li class="weui-uploader__file weui-uploader__file_status" style="background-image:url(' + this.url + ')" id="' +
					'li' + this.id + '"><div  class="weui-uploader__file-content">50%</div></li>';
				// 将dom转变为jQuery对象
				var $dom = $(dom);
				// 设置上传进度从0开始
				$dom.find("div").text("0" + "%");
				$("#uploaderFiles").prepend($dom);

				fileName = this.name;
				console.log("上传的文件名：" + fileName);

				this.upload(); // 如果是手动上传，这里可以通过调用upload来实现；也可以用它来实现重传。
			// this.stop(); // 调用中断上传的方法
			// return true; // 阻止默认行为，不显示预览图的图像
			},

			// 文件上传前调用
			onBeforeSend : function(data, headers) {
				console.log(this, data, headers);
				// $.extend(data, { test: 1 }); // 可以扩展此对象来控制上传参数
				// $.extend(headers, { Origin: 'http://127.0.0.1' }); // 可以扩展此对象来控制上传头部信息
				$.extend(headers, {
					'Access-Control-Allow-Origin' : '*',
				});

				// 由于文件上传是通过ajax实现的，因此这里可以自定义请求参数 
				$.extend(data, {
					// 我在这里就设置了一个名为fileName的请求参数，用来想后台返回当前上传的文件名,不过没什么用因为上传图片的所有信息都包含在this中了，这里只是演示
					fileName : fileName,
					aid : $("#aid").val(),
				});
			// return false; // 阻止文件上传
			},

			// 上传进度的回调，每次上传进度发生变化都会回调本函数，参数procent就是进度从0~100的数字
			onProgress : function(procent) {
				console.log(this, procent);
				// 先获取上传的图片的本地id（从0开始的数字）
				let id = this.id;
				// 找到代表该上传图片的<li>
				let $li = $("#li" + id);
				// 找到<li>中用来显示上传进度的div,并修改进度显示
				$li.find("div").text(procent + "%");
			// return true; // 阻止默认行为，不使用默认的进度显示
			},

			// 上传成功的回调，return为从后台通过AJAX回传过来的JSON对象，其中包含着重要的数据信息（比如图片在后台数据库中的apid,用作删除图片之必备）
			onSuccess : function(ret) {
				console.log(this, ret);
				// 先获取上传成功图片的本地id（从0开始的数字）
				let id = this.id;
				// 找到代表该上传图片的<li>
				let $li = $("#li" + id);
				// 变更<li>的显示，去除掉遮罩和用来显示上传进度的<div>
				$li.empty();
				$li.removeClass("weui-uploader__file_status");
				$li.attr("id", ret.apid);
				// 当被点击的时候会弹出gallery放大显示，并出现删除按钮
				$li.unbind().bind("click", function() {
					// 告诉gallery放大显示什么图片
					$galleryImg.attr("style", $li.attr("style"));
					// 在gallery上设置所放大图片的apid，用作删除之用
					$gallery.attr("data-apid", ret.apid);
					// 然后放大显示图片
					$gallery.fadeIn(100);
				});

			// return true; // 阻止默认行为，不使用默认的成功态
			},

			// 上传失败的回调
			onError : function(err) {
				console.log(this, err);
			// return true; // 阻止默认行为，不使用默认的失败态
			}
		});
	});
</script>
</html>