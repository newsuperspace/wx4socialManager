define(function(require){
	var $ = require("jquery");
	var justep = require("$UI/system/lib/justep");

	var Model = function(){
		this.callParent();
	};
	//打开左侧设置
	Model.prototype.settingBtnClick = function(event){
		justep.Shell.showLeft();
	};

	return Model;
});