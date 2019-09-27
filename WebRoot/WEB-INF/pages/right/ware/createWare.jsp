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
	href="https://res.wx.qq.com/open/libs/weui/2.0.1/weui.min.css">
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

							<s:if test="''==wid">
								<h1 class="h2">创建新兑换品</h1>
							</s:if>
							<s:else>
								<h1 class="h2">
									更新商品：
									<s:property value="wname" />
									的信息
								</h1>
							</s:else>

							<div class="btn-toolbar mb-2 mb-md-0">
								<div class="btn-group mr-2">

									<button class="btn btn-sm btn-outline-secondary"
										data-toggle="modal" data-target="#selectProjects">
										<span class="glyphicon glyphicon-search"></span> 功能A
									</button>
									<button class="btn btn-sm btn-outline-secondary"
										data-toggle="modal" data-target="#orderProjects">
										<span class="glyphicon glyphicon-sort-by-order"></span> 功能B
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
											<a class="dropdown-item" href="#" onclick="alert('功能待开放');">功能C</a>
											<a class="dropdown-item disabled" href="#">功能D action</a>
											<h6 class="dropdown-header">特殊功能</h6>
											<a class="dropdown-item" href="#">功能E</a>
											<div class="dropdown-divider"></div>
											<a class="dropdown-item" href="#">功能F</a>
										</div>
									</div>
								</div>
							</div>
						</div>
						<!-- =============创建开始=========== -->
						<input type="hidden" id="wid" name="wid"
							value="<s:property value='wid' />" />

						<div class="weui-cells__title">商品名</div>
						<div class="weui-cells">
							<div class="weui-cell">
								<div class="weui-cell__bd">
									<input class="weui-input" type="text" placeholder="请输入20字以内文本"
										id="wname" name="wname" value="<s:property value='wname'/>" />
								</div>
							</div>
						</div>

						<div class="weui-cells__title">描述</div>
						<div class="weui-cells">
							<div class="weui-cell">
								<div class="weui-cell__bd">
									<input class="weui-input" type="text" placeholder="请输入20字以内文本"
										id="description" name="description"
										value="<s:property value='description'/>" />
								</div>
							</div>
						</div>

						<div class="weui-cells__title">商品数量</div>
						<div class="weui-cells">
							<div class="weui-cell">
								<div class="weui-cell__bd">
									<input class="weui-input" type="number"
										placeholder="请输入商品当前备货数量" id="surplus" name="surplus"
										value="<s:property value='surplus'/>" />
								</div>
							</div>
						</div>

						<div class="weui-cells__title">累计兑换数量</div>
						<div class="weui-cells">
							<div class="weui-cell">
								<div class="weui-cell__bd">
									<input class="weui-input" disabled="disabled" type="number"
										placeholder="请输入商品当前备货数量" id="total" name="total"
										value="<s:property value='total'/>" />
								</div>
							</div>
						</div>

						<div class="weui-cells__title">创建日期</div>
						<div class="weui-cells">
							<div class="weui-cell">
								<div class="weui-cell__bd">
									<input class="weui-input" disabled="disabled" type="text"
										placeholder="自动生成的创建日期" id="str4CreateDate"
										name="str4CreateDate"
										value="<s:property value='str4CreateDate'/>" />
								</div>
							</div>
						</div>

						<div class="weui-cells__title">兑换分值</div>
						<div class="weui-cells">
							<div class="weui-cell">
								<div class="weui-cell__bd">
									<input class="weui-input" type="number" placeholder="请输入商品积分价值"
										id="score" name="score" value="<s:property value='score'/>" />
								</div>
							</div>
						</div>

						<!-- gallery是用来展示上传的图片和删除图片之用 -->
						<div class="weui-gallery" id="gallery" data-index="">
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
												<span id="uploadCount"> <s:property
														value="photos.size()" />
												</span>/3
											</div>
										</div>
										<div class="weui-uploader__bd">
											<ul class="weui-uploader__files" id="uploaderFiles">

												<!-- 这里存放着上传的图片的预览图列表 -->
												<s:iterator value="photos" var="photo">
													<s:if test="''!=#photo.value">
														<li class="weui-uploader__file"
															style="background-image:url(<s:property value='#photo.value' />)"
															data-b64="<s:property value='#photo.value' />"
															id="<s:property value='#photo.key' />"></li>
													</s:if>
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
<script src="http://res.wx.qq.com/open/js/jweixin-1.4.0.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/myJS.js"></script>
<script type="text/javascript"
	src="https://res.wx.qq.com/open/libs/weuijs/1.2.1/weui.min.js"></script>
<script>

	// 向后端回传数据保存/更新到数据库
	function save() {
		let wid = $("#wid").val();
		let wname = $("#wname").val();
		let description = $("#description").val();
		let surplus = $("#surplus").val();
		let score = $("#score").val();
		let base64str4image1 = localStorage.getItem(1);
		let base64str4image2 = localStorage.getItem(2);
		let base64str4image3 = localStorage.getItem(3);

		if ('' != wid) {
			// 当前页面已经存在商品数据，表明此时的“保存”按钮是用来“更新数据”的
			// TODO 前端数据校验
			
			let url = "wareAction_updateWare.action";
			let param = {
				'wid' : wid,
				'wname' : wname,
				'description' : description,
				'surplus' : surplus,
				'score' : score,
				'base64str4image1' : base64str4image1,
				'base64str4image2' : base64str4image2,
				'base64str4image3' : base64str4image3,
			}
			$.post(url, param, function(data, textStatus, req) {

				weui.alert(data.message);
				if (data.result) {
					// 服务器已接纳数据，并完成新建/更新操作，现在刷新页面
					$(location).attr("href", "wareAction_findAll.action");
				} else {
					// TODO 此处应该用红色高亮出问题输入

				}
			});
		} else {
			// 当前页面还不存在商品数据，表明此时的“保存”按钮是用来“新建数据”的
			// TODO 前端数据校验

			let url = "wareAction_createWare.action";
			let param = {
				'wid' : wid,
				'wname' : wname,
				'description' : description,
				'surplus' : surplus,
				'score' : score,
				'base64str4image1' : base64str4image1,
				'base64str4image2' : base64str4image2,
				'base64str4image3' : base64str4image3,
			}
			$.post(url, param, function(data, textStatus, req) {

				weui.alert(data.message);
				if (data.result) {
					// 服务器已接纳数据，并完成新建/更新操作，现在刷新页面
					$(location).attr("href", "wareAction_findAll.action");
				} else {
					// TODO 此处应该用红色高亮出问题输入

				}
			});
		}

	}

	// 用于初始化表单内容和状态的功能
	function initForm() {
		let score = $("#score");
		let surplus = $("#surplus");
		let total = $("#total");
		let str4CreateDate = $("#str4CreateDate");

		if ('' == score.val()) {
			score.val('0');
		}

		if ('' == surplus.val()) {
			surplus.val('0');
		}

		if ('' == total.val()) {
			total.val(0);
			total.attr("disabled", true);
		}


		if ('' != str4CreateDate.val()) {
			str4CreateDate.attr("disabled", true);
		}


		// 获取id为1、2、3的<li> 中的base64编码数据，这些数据是基于服务器端struts2的OGNL显示在前端的，我们在这里要获取回来，存放在前端localStorage中备用（主要应对删除操作）
		let one = $("#1");
		let two = $("#2");
		let three = $("#3");

		if (one.length > 0) {
			localStorage.setItem(1, one.attr("data-b64"));
		} else {
			localStorage.setItem(1, "");
		}

		if (one.length > 0) {
			localStorage.setItem(2, two.attr("data-b64"));
		} else {
			localStorage.setItem(2, "");
		}

		if (one.length > 0) {
			localStorage.setItem(3, three.attr("data-b64"));
		} else {
			localStorage.setItem(3, "");
		}


	}


	// 初始化
	$(function() {
		initForm();

		// -----------------与Gallery相关的初始化操作------------------
		var $gallery = $("#gallery");
		var $galleryImg = $("#galleryImg");
		// 当用户点击gallery上的其他部分时会关闭该gallery
		$gallery.on("click", function() {
			$gallery.fadeOut(100);
		});

		// 点击gallery下面的"垃圾桶"按钮实现图片删除操作
		$(".weui-gallery__del").click(function() {
			let index = $gallery.attr("data-index");
			console.log('删除了' + index);
			// 先移除<ul>中代表该图片的缩微图<li>
			$("#" + index).remove();
			// 减少上传图片计数器
			--uploadCount;
			// 更新计数器显示
			$("#uploadCount").text(uploadCount);


			// TODO 操作localStorage，清空该name（1，2，3）下的数据为空字符串""
			localStorage.setItem(index, "");


		});
		// 给每个图片预览<li>绑定click功能，用于设定点击预览图后放大显示的gallery中显示的图片内容（style给出）以及图片id（1-3）
		$("li.weui-uploader__file").unbind().bind("click", function() {
			// 告诉gallery放大显示什么图片
			$galleryImg.attr("style", $(this).attr("style"));
			// 在gallery上设置所放大图片的apid，用作删除之用
			$gallery.attr("data-index", $(this).attr("id"));
			console.log($(this).attr("id"));
			// 然后放大显示图片
			$gallery.fadeIn(100);
		});


		// -----------------与使用weui.js上传图片相关的初始化操作------------------
		// 通过onBeforeQueued()校验的允许上传的图片的数量
		var uploadCount = parseInt($("#uploadCount").text());
		// 用来记录上传文件信息的全局属性
		var b64 = "";

		weui.uploader('#uploader', {
			url : 'wareAction_upload.action',
			auto : false, // 是否自动上传，如果设置为false则可以在onQueued()方法中通过this.upload()手中执行上传
			type : 'base64', // 上传类型file类型
			fileVal : 'file', // 服务器端接收上传文件的File类型属性名（该字段必须存在，即便是type为base64而不是file的时候也一样，否则服务器会报出no action异常）
			// 设定图片压缩
			compress : {
				width : 1280,
				height : 720,
				quality : .5
			},

			// 【文件添加前的回调】，return false则不添加，通常我们会把格式校验添加到这里
			onBeforeQueued : function(files) {

				console.log("当前图片数量为：" + uploadCount);

				// `this` 是轮询到的文件, `files` 是所有文件
				if ([ "image/jpg", "image/jpeg", "image/png", "image/gif" ].indexOf(this.type) < 0) {
					weui.alert('请上传图片');
					return false; // 阻止文件添加
				}
				if (this.size > 10 * 1024 * 1024) {
					weui.alert('请上传不超过10M的图片');
					return false;
				}
				// 控制单次多选上传图片数量
				if (files.length > 3 || files.length + uploadCount > 3) { // 防止一下子选择过多文件
					weui.alert('最多只能上传3张图片，请重新选择');
					return false;
				}
				// 控制上传数量
				if (uploadCount + 1 > 3) {
					weui.alert('最多只能上传3张图片');
					return false;
				}
				// 校验成功，计数器+1
				++uploadCount;
				$("#uploadCount").text(uploadCount);
				return true; // 返回false会阻止默认行为，不插入预览图的框架
			},

			// 【文件添加成功的回调】,在方法中引用的this包含刚刚成功通过onBeforeQueued校验允许上传的图片文件的全部关键信息,其中包含的信息如下：
			// id: 1
			// lastModified: 1534309141540
			// base64: 如果我们之前设置type：'base64',则能在这里获取形如"data:image/jpeg;base64,/9j/4AAQSkZJRgABAQAA......"的base64图片编码，可直接用于style="background-image:url(。。。)"以显示图片
			// lastModifiedDate: Wed Aug 15 2018 12: 59: 01 GMT + 0800(中国标准时间) {}
			// name: "“互联网+社会工作” 保障社区志愿服务安全开展项目结构.png"
			// size: 85094
			// status: "ready"
			// stop: ƒ()
			// type: "image/png"
			// upload: ƒ()
			// url: "blob:null/32f13f99-d241-4575-aee8-36ee016ce587"
			onQueued : function() {
				console.log(this);

				// 便利localStorage中name为1，2，3的键值对，如果发现哪个没有就设置新增图片的id为该值并向localStorage中保存base64编码字符串
				let one = localStorage.getItem(1);
				let two = localStorage.getItem(2);
				let three = localStorage.getItem(3);

				if ("" == one || 'undefined' == one) {
					this.id = 1;
				} else if ("" == two || 'undefined' == two) {
					this.id = 2;
				} else if ("" == three || 'undefined' == three) {
					this.id = 3;
				} else {
					weui.alert("图片数量超出限定值");
					// 终端上传
					this.stop();
				// this.stop(); // 调用中断上传的方法
				// return true; // 阻止默认行为，不显示预览图的图像
				}
				// 获取b64编码字符串，形如（data:image/jpeg;base64,/9j/4AA）可直接用于 style="background-img:url()"括号的内容，以显示图片
				b64 = this.base64;
				localStorage.setItem(this.id, b64);

				// console.log(this.status); // 文件的状态：'ready', 'progress', 'success', 'fail'
				// console.log(this.base64); // 如果是base64上传，file.base64可以获得文件的base64
				var dom = '<li class="weui-uploader__file weui-uploader__file_status" style="background-image:url(' + this.url + ')" id="' +
					this.id + '"><div  class="weui-uploader__file-content">50%</div></li>';
				// 将dom转变为jQuery对象
				var $dom = $(dom);
				// 设置上传进度从0开始
				$dom.find("div").text("0" + "%");
				// 新dom设置好后，将其添加到div中以显示
				$("#uploaderFiles").prepend($dom);


				// 如果是手动上传，这里可以通过调用upload来实现；也可以用它来实现重传。
				this.upload();
			},

			// 【文件上传前调用】，用于设置请求头部信息以及其他请求参数信息
			onBeforeSend : function(data, headers) {
				//console.log(this, data, headers);
				// $.extend(data, { test: 1 }); // 可以扩展此对象来控制上传参数
				// $.extend(headers, { Origin: 'http://127.0.0.1' }); // 可以扩展此对象来控制上传头部信息

				// 设置请求头部信息
				$.extend(headers, {
					'Access-Control-Allow-Origin' : '*',
				});

			// 由于文件上传是通过ajax实现的，因此这里可以自定义请求参数 
			//$.extend(data, {
			//b64 : b64,
			//});
			//console.log("=====================");
			//console.log(data);
			// return false; // 阻止文件上传
			},

			// 【上传进度的回调】，每次上传进度发生变化都会回调本函数，参数procent就是进度从0~100的数字
			onProgress : function(procent) {
				console.log(this, procent);
				// 先获取上传的图片的本地id（从0开始的数字）
				let id = this.id;
				// 找到代表该上传图片的<li>
				let $li = $("#" + id);
				// 找到<li>中用来显示上传进度的div,并修改进度显示
				$li.find("div").text(procent + "%");
			// return true; // 阻止默认行为，不使用默认的进度显示
			},

			// 【上传成功的回调】，ret为从后台通过AJAX回传过来的JSON对象，其中包含着重要的数据信息（比如图片在后台数据库中的apid,用作删除图片之必备）
			onSuccess : function(ret) {
				console.log(this, ret);
				// 找到代表该上传图片的<li>
				let $li = $("#" + this.id);
				// 变更<li>的显示，去除掉遮罩和用来显示上传进度的<div>
				$li.empty();
				$li.removeClass("weui-uploader__file_status");

				$li.unbind().bind("click", function() {
					// 告诉gallery放大显示什么图片
					$galleryImg.attr("style", $(this).attr("style"));
					// 在gallery上设置所放大图片的apid，用作删除之用
					$gallery.attr("data-index", $(this).attr("id"));
					// 然后放大显示图片
					$gallery.fadeIn(100);
				});

			// return true; // 阻止默认行为，不使用默认的成功态
			},

			// 【上传失败的回调】
			onError : function(err) {
				console.log("上传图片出现异常");
				console.log(this, err);
			// return true; // 阻止默认行为，不使用默认的失败态
			}
		});
	});
</script>
</html>