/**
 * 
 */

var bookModal = {
	data : {

	},

	operation : {
		ajaxCommit : {
			// 负责上传图片到临时目录中，并将从服务器返回的图片url路径更改到指定img标签上显示，同时更改指定model的hidden标签该图片的路径
			// 用以服务器知道将那个临时目录路径下的图片永久保存起来。
			upload2Temp : function() {
				$("#uploadImageForm").submit(); // commit  the form
			},

			// 异步提交表单数据之前的回调函数
			beforeSubmit : function(formData, jqForm, options) {
				// formData是将要提交的表单数据，它是一个数组Array对象
				//				var  queryString  =  $.param(formData);
				// jqForm 是被提交的表单的jQuery对象自身
				//				var  formElement  =  jqForm[0]  // 由于jQuery对象就是封装这原生DOM元素节点对象的Array数组，因此可以通过下表来索引到将要被提交的表单的元素节点DOM对象
				// options就是 bookModal.init.ajaxFormOptions.op4uploadImageForm 中的封装有通过AJax方式提交表单数据操作配置信息
			},

			// 异步提交表单数据成功之后的回调函数
			// responseText  返回服务端的JSON对象
			// statusText  返回如"success"的字符串，提示一切成功
			successSubmit : function(responseText, statusText) {
				$("#bookImage").attr("src", responseText.url+"?time="+Date());   // 动态更改图片
				var  arr =  responseText.url.split("/");
				var  imageName  =  arr[arr.length - 1];
				$("#imageName").attr("value", imageName);
			}
		},

		//-------------------------------- 与模态对话框有关的操作------------------------------
		modal : {

			// 显示这本图书的过去借阅情况的统计信息
			statistic : function(param) {
				alert("statistic" + param);
			},

			// 显示某本图书详细信息的模态对话框
			showInfo : function(param) {
				$("#info_codePath").attr("src", "#");
				$("#info_name").text("");
				$("#info_price").text("");
				$("#info_isbn").text("");
				$("#info_description").text("");

				var data = {
					bid : param
				}
				$.post("bookAction_updateUI4Json.action", data, function(data, textStatus, req) {
					// console.log(data);   // 这样在火狐和chrome浏览器上通过控制台能看到详细的JSON结构
					$("#info_image").attr("src",data.picture)
					$("#info_codePath").attr("src", data.codePath);
					$("#info_name").text(data.name);
					$("#info_price").text(data.price);
					$("#info_isbn").text(data.isbn);
					$("#info_description").text(data.description);
				});
				$("#infoModal").modal("show");
			},

			// 展示莫泰对话框，用以新增书籍
			showAdd : function() {
				$("#bookImage").attr("src", "/library/img/upload.jpg");
				$("#addModal").modal("show");
			},

			// 展示模态对话框，用以修改书籍，并回显原有数据（√）
			showUpdate : function(param) {
				$("#bid").val("");
				$("#name").val("");
				$("#price").val("");
				$("#isbn").val("");
				$("#description").val("");

				var data = {
					bid : param
				}
				$.post("bookAction_updateUI4Json.action", data, function(data, textStatus, req) {
					// console.log(data);   // 这样在火狐和chrome浏览器上通过控制台能看到详细的JSON结构
					$("#bid").val(data.bid);
					$("#name").val(data.name);
					$("#price").val(data.price);
					$("#isbn").val(data.isbn);
					$("#description").val(data.description);
				});
				$("#updateModal").modal("show");
			},

			// 将表单新填写的属性通过ajax提交给服务器保存到数据库，然后更新前端页面上的内容
			commit : function() {
				var data = {
					bid : $("#bid").val(),
					name : $("#name").val(),
					price : $("#price").val(),
					isbn : $("#isbn").val(),
					description : $("#description").val(),
				}

				$.post("bookAction_update4Json.action", data, function(data, textStatus, req) {
					$("#myModal").modal("hide");
					alert("保存成功！");
					$(location).attr('href', 'bookAction_showBookList.action');
				})
			},

			addNew : function() {
				var data = {
					picture:  $("#imageName").val(),
					name : $("#add_name").val(),
					price : $("#add_price").val(),
					isbn : $("#add_isbn").val(),
					description : $("#add_description").val(),
				}

				$.post("bookAction_add4Json.action", data, function(data, textStatus, req) {
					$("#myModal").modal("hide");
					alert("新建成功！");
					$(location).attr('href', 'bookAction_showBookList.action');
				})
			}
		}
	},

	init : {
		// ajax表单初始化操作数据
		ajaxFormOptions : {
			// ★★★★★JS中常犯的错★★★★★
			// 下面的这个option不能放在当前JSON对象之中，because字段beforeSubmit和success需要引用到当前JSON自己定义的两个方法
			// 而当浏览器加载本脚本根据当前JSON定义新建JSON对象的时候，由于当前JSON对象bookModal扫描到这里还没有创建出来，因此这里引用到的就是undefine
			// 所以当插件ajaxForm(option)的时候option中的这两个回调函数的字段是undefine，因此会报错。
			// 正确的做法是JSON在定义体中不引用自己之中的任何对象，而是将需要引用当前JSON对象的部分，剥离到当前JSON对象（bookModal）之后的外部，这样当
			// 位于bookModal之后的外部对象中引用bookModal的时候，bookModal对象已经创建完成了，自然引用bookModal中的对象的时候就不会出现undefine了。
			//			op4uploadImageForm : {
			//				option : {
			////					beforeSubmit : bookModal.operation.ajaxCommit.beforeSubmit,
			////					success : bookModal.operation.ajaxCommit.successSubmit,
			//					url : "uploadImage_upload2Temp.action",
			//					dataType : "json",
			//					clearForm : true,  
			//					resetForm : true
			//				}
			//			}
		},

		// 初始化事件
		initEvent : {

		},
	},
}

// for 不出现 befoeSubmit和success引用还没有创建完成的bookModal对象中的成员，而导致undefine的发生，所以必须将需要引用bookModal的option对象的定义
// 放在bookModal之后，以确保在定义当前option的时候，其所需要引用的bookModal对象已经彻底创建完成了。
var option = {
	beforeSubmit : bookModal.operation.ajaxCommit.beforeSubmit,   // 如果你不确保bookModal已经创建完成，则这里将会引用到undefine
	success : bookModal.operation.ajaxCommit.successSubmit,     // 如果你不确保bookModal已经创建完成，则这里将会引用到undefine
	url : "uploadAction_upload2Temp.action", 
	dataType : "json",
	clearForm : true,
	resetForm : true
}

/**
* 通过jQuery的ready()方法，在当前脚本所属页面加载好后，就自动执行其中的逻辑，用以初始化
*/
$().ready(function() {
	$("#uploadImageForm").ajaxForm(option);
});