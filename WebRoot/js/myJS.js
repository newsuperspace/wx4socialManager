if (window.screen.availWidth >= 768) {
	$('#contentId').collapse({
		toggle : true
	});
}

function toggle() {
	/*
	 要操作Collapse中的某一个特定的card的时候，需要先获取到该card的card-body上一层的div的id
	 然后再通过执行collapse()配合 hide（关闭）、show（展开）、toggle（切换——结合了hide和show）
	*/
	console.log("关闭关闭")
	$('#section1ContentId').collapse('toggle');
}