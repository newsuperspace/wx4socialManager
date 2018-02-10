<!DOCTYPE HTML>
<html style="width:100%;height:100%" class="no-js">
    <head><meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1">
        <meta name="apple-mobile-web-app-capable" content="yes">
        <meta name="apple-mobile-web-app-status-bar-style" content="black">
        <meta name="format-detection" content="telephone=no">
        <meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
        <script src="../system/lib/base/modernizr-2.8.3.min.js"></script>
		<script id="__varReplace">
    	
    	    	window.__justep = window.__justep || {};
				window.__justep.isDebug = false;
				window.__justep.__packageMode = "1";
				window.__justep.__isPackage = true;;
				window.__justep.url = location.href;
				window.__justep.versionInfo = {};
		 
    	</script>
        <script id="__updateVersion">
        
				(function(url, mode){
					if (("@"+"mode@") === mode) mode = "3";
					if (("@"+"versionUrl@") === url) url = "system/service/common/app.j";
					if ((mode!=="1" && (mode!="2") && (mode!="3"))) return;
					var async = (mode=="1");
					var x5Version = "noApp";
					var x5AppAgents = /x5app\/([0-9.]*)/.exec(navigator.userAgent);
					if(x5AppAgents && x5AppAgents.length > 1){
					   	x5Version = x5AppAgents[1] || "";
					} 
					function createXhr(){
						try {	
							return new XMLHttpRequest();
						}catch (tryMS) {	
							var version = ["MSXML2.XMLHttp.6.0",
							               "MSXML2.XMLHttp.3.0",
							               "MSXML2.XMLHttp",
							               "Miscrosoft.XMLHTTP"];
							for(var i = 0; i < version.length; i++){
								try{
							    	return new ActiveXObject(version[i]);
								}catch(e){}
							}
						}
						throw new Error("您的系统或浏览器不支持XHR对象！");
					}
					
					function createGuid(){	
						var guid = '';	
						for (var i = 1; i <= 32; i++){		
							var n = Math.floor(Math.random()*16.0).toString(16);		
							guid += n;		
							if((i==8)||(i==12)||(i==16)||(i==20))			
								guid += '-';	
						}	
						return guid;
					}
					
					function parseUrl(href){
						href = href.split("#")[0];
						var items = href.split("?");
						href = items[0];
						var query = items[1] || "";
						items = href.split("/");
						var baseItems = [];
						var pathItems = [];
						var isPath = false;
						for (var i=0; i<items.length; i++){
							if (mode == "3"){
								if (items[i] && (items[i].indexOf("v_") === 0) 
										&& (items[i].indexOf("l_") !== -1)
										&& (items[i].indexOf("s_") !== -1)
										&& (items[i].indexOf("d_") !== -1)
										|| (items[i]=="v_")){
									isPath = true;
									continue;
								}
							}else{
								if (items[i] && (items[i].indexOf("v-")===0) && (items[i].charAt(items[i].length-1)=="-") ){
									isPath = true;
									continue;
								}
							}
							if (isPath){
								pathItems.push(items[i]);
							}else{
								baseItems.push(items[i]);	
							}
							
						}
						var base = baseItems.join("/");
						if (base.charAt(base.length-1)!=="/") base += "/";
						
						var path = pathItems.join("/");
						if (path.charAt(0) !== "/") path = "/" + path;
						return [base, path, query];
					}
					
					
					var items = parseUrl(window.location.href);
					var base = items[0];
					var path = items[1];
					var query = items[2];
					var xhr = createXhr();
					url += ((url.indexOf("?")!=-1) ? "&" : "?") +"_=" + createGuid();
					if (mode === "3"){
						url += "&url=" + path;
						if (query)
							url += "&" + query;
					}
					xhr.open('GET', base + url, async);
					
					if (async){
						xhr.onreadystatechange=function(){
							if((xhr.readyState == 4) && (xhr.status == 200) && xhr.responseText){
								var versionInfo = JSON.parse(xhr.responseText);
								window.__justep.versionInfo = versionInfo;
								window.__justep.versionInfo.baseUrl = base;
								if (query){
									path = path + "?" + query;
								}
								path = versionInfo.resourceInfo.indexURL || path; /* 如果返回indexPath则使用indexPath，否则使用当前的path */
								var indexUrl = versionInfo.baseUrl + versionInfo.resourceInfo.version + path;
								versionInfo.resourceInfo.indexPageURL = indexUrl;
								if(versionInfo.resourceInfo.resourceUpdateMode != "md5"){
									if (window.__justep.url.indexOf(versionInfo.resourceInfo.version) == -1){
										versionInfo.resourceInfo.isNewVersion = true;
									};
								}
							}
						}
					}
					
					try{
						xhr.send(null);
						if (!async && (xhr.status == 200) && xhr.responseText){
							var versionInfo = JSON.parse(xhr.responseText);
							window.__justep.versionInfo = versionInfo;
							window.__justep.versionInfo.baseUrl = base;
							if ((mode==="3") && window.__justep.isDebug){
								/* 模式3且是调试模式不重定向 */
							}else{
								if (query){
									path = path + "?" + query;
								}
								if(versionInfo.resourceInfo.resourceUpdateMode == "md5"){
									path = versionInfo.resourceInfo.indexURL || path; /* 如果返回indexPath则使用indexPath，否则使用当前的path */
									var indexUrl = versionInfo.baseUrl + versionInfo.resourceInfo.version + path;
									versionInfo.resourceInfo.indexPageURL = indexUrl; 
								}else if (versionInfo.resourceInfo && versionInfo.resourceInfo.version && (window.__justep.url.indexOf(versionInfo.resourceInfo.version) == -1)){
									path = versionInfo.resourceInfo.indexURL || path; /* 如果返回indexPath则使用indexPath，否则使用当前的path */
									var indexUrl = versionInfo.baseUrl + versionInfo.resourceInfo.version + path;
									window.location.href = indexUrl;
								}
							}
						}
					}catch(e2){}
				}("appMetadata_in_server.json", "1"));
                 
        </script>
    <link rel="stylesheet" href="../system/components/bootstrap.min.css" include="$model/UI2/system/components/bootstrap/lib/css/bootstrap,$model/UI2/system/components/bootstrap/lib/css/bootstrap-theme"><link rel="stylesheet" href="../system/components/comp.min.css" include="$model/UI2/system/components/justep/lib/css2/dataControl,$model/UI2/system/components/justep/input/css/datePickerPC,$model/UI2/system/components/justep/messageDialog/css/messageDialog,$model/UI2/system/components/justep/lib/css3/round,$model/UI2/system/components/justep/input/css/datePicker,$model/UI2/system/components/justep/row/css/row,$model/UI2/system/components/justep/attachment/css/attachment,$model/UI2/system/components/justep/barcode/css/barcodeImage,$model/UI2/system/components/bootstrap/dropdown/css/dropdown,$model/UI2/system/components/justep/dataTables/css/dataTables,$model/UI2/system/components/justep/contents/css/contents,$model/UI2/system/components/justep/common/css/forms,$model/UI2/system/components/justep/locker/css/locker,$model/UI2/system/components/justep/menu/css/menu,$model/UI2/system/components/justep/scrollView/css/scrollView,$model/UI2/system/components/justep/loadingBar/loadingBar,$model/UI2/system/components/justep/dialog/css/dialog,$model/UI2/system/components/justep/bar/css/bar,$model/UI2/system/components/justep/popMenu/css/popMenu,$model/UI2/system/components/justep/lib/css/icons,$model/UI2/system/components/justep/lib/css4/e-commerce,$model/UI2/system/components/justep/toolBar/css/toolBar,$model/UI2/system/components/justep/popOver/css/popOver,$model/UI2/system/components/justep/panel/css/panel,$model/UI2/system/components/bootstrap/carousel/css/carousel,$model/UI2/system/components/justep/wing/css/wing,$model/UI2/system/components/bootstrap/scrollSpy/css/scrollSpy,$model/UI2/system/components/justep/titleBar/css/titleBar,$model/UI2/system/components/justep/lib/css1/linear,$model/UI2/system/components/justep/numberSelect/css/numberList,$model/UI2/system/components/justep/list/css/list,$model/UI2/system/components/justep/dataTables/css/dataTables"></head>
	
    <body style="width:100%;height:100%;margin: 0;">
        <script intro="none"></script>
    	<div id="applicationHost" class="applicationHost" style="width:100%;height:100%;" __component-context__="block"><div xid="window" class="window cnaEFja" component="$model/UI2/system/components/justep/window/window" design="device:m;" data-bind="component:{name:'$model/UI2/system/components/justep/window/window'}" __cid="cnaEFja" components="$model/UI2/system/components/justep/model/model,$model/UI2/system/components/justep/loadingBar/loadingBar,$model/UI2/system/components/justep/button/button,$model/UI2/system/components/justep/scrollView/scrollView,$model/UI2/system/components/justep/list/list,$model/UI2/system/components/justep/panel/child,$model/UI2/system/components/justep/windowDialog/windowDialog,$model/UI2/system/components/justep/labelEdit/labelEdit,$model/UI2/system/components/justep/panel/panel,$model/UI2/system/components/justep/contents/content,$model/UI2/system/components/justep/row/row,$model/UI2/system/components/justep/output/output,$model/UI2/system/components/justep/titleBar/titleBar,$model/UI2/system/components/justep/contents/contents,$model/UI2/system/components/justep/data/data,$model/UI2/system/components/justep/window/window,,$model/UI2/system/components/justep/windowContainer/windowContainer,$model/UI2/system/components/justep/wing/wing,$model/UI2/system/components/justep/wing/child,">
  <style>.x-wing-left.cnaEFja > .x-titlebar.cnaEFja{background-color: orange}</style>  
  <div component="$model/UI2/system/components/justep/model/model" xid="model" style="display:none" data-bind="component:{name:'$model/UI2/system/components/justep/model/model'}" data-events="onLoad:modelLoad" __cid="cnaEFja" class="cnaEFja"></div>  
  <div component="$model/UI2/system/components/justep/wing/wing" class="x-wing cnaEFja" xid="wing" data-bind="component:{name:'$model/UI2/system/components/justep/wing/wing'}" data-config="{&#34;display&#34;:&#34;push&#34;}" __cid="cnaEFja"> 
    <div class="x-wing-left cnaEFja" xid="left" component="$model/UI2/system/components/justep/wing/child" data-bind="component:{name:'$model/UI2/system/components/justep/wing/child'},disable:true" __cid="cnaEFja"> 
      <div component="$model/UI2/system/components/justep/titleBar/titleBar" class="x-titlebar cnaEFja" xid="titleBar1" data-bind="component:{name:'$model/UI2/system/components/justep/titleBar/titleBar'}" data-config="{&#34;title&#34;:&#34;设置&#34;}" __cid="cnaEFja"> 
        <div class="x-titlebar-left cnaEFja" xid="div2" __cid="cnaEFja"></div>  
        <div class="x-titlebar-title cnaEFja" xid="div3" __cid="cnaEFja">设置</div>  
        <div class="x-titlebar-right  cnaEFja" xid="div4" __cid="cnaEFja">
          <div class="empty cnaEFja" __cid="cnaEFja"></div>
        </div> 
      </div> 
    </div>  
    <div class="x-wing-content cnaEFja" xid="content1" component="$model/UI2/system/components/justep/wing/child" data-bind="component:{name:'$model/UI2/system/components/justep/wing/child'}" __cid="cnaEFja"> 
      <div class="x-wing-backdrop cnaEFja" xid="div1" __cid="cnaEFja"></div>  
      <div component="$model/UI2/system/components/justep/contents/contents" class="x-contents x-full cnaEFja" xid="pages" data-bind="component:{name:'$model/UI2/system/components/justep/contents/contents'}" data-config="{&#34;active&#34;:0,&#34;slidable&#34;:true,&#34;wrap&#34;:false}" __cid="cnaEFja">
        <div class="x-contents-content active cnaEFja" xid="main" component="$model/UI2/system/components/justep/contents/content" data-bind="component:{name:'$model/UI2/system/components/justep/contents/content'}" __cid="cnaEFja">
          <div component="$model/UI2/system/components/justep/windowContainer/windowContainer" class="x-window-container cnaEFja" xid="mainContainer" __component-context__="block" data-bind="component:{name:'$model/UI2/system/components/justep/windowContainer/windowContainer'}" data-config="{&#34;src&#34;:&#34;$model/UI2/weixin/list.w&#34;}" __cid="cnaEFja">
            <div xid="window" class="window cIbmUri" component="$model/UI2/system/components/justep/window/window" design="device:m;" data-bind="component:{name:'$model/UI2/system/components/justep/window/window'}" __cid="cIbmUri" components="$model/UI2/system/components/justep/model/model,$model/UI2/system/components/justep/loadingBar/loadingBar,$model/UI2/system/components/justep/button/button,$model/UI2/system/components/justep/scrollView/scrollView,$model/UI2/system/components/justep/list/list,$model/UI2/system/components/justep/panel/child,$model/UI2/system/components/justep/windowDialog/windowDialog,$model/UI2/system/components/justep/labelEdit/labelEdit,$model/UI2/system/components/justep/panel/panel,$model/UI2/system/components/justep/contents/content,$model/UI2/system/components/justep/row/row,$model/UI2/system/components/justep/output/output,$model/UI2/system/components/justep/titleBar/titleBar,$model/UI2/system/components/justep/contents/contents,$model/UI2/system/components/justep/data/data,$model/UI2/system/components/justep/window/window,">  
              <div component="$model/UI2/system/components/justep/model/model" xid="model" style="display:none" data-bind="component:{name:'$model/UI2/system/components/justep/model/model'}" data-events="onModelConstruct:modelModelConstruct" __cid="cIbmUri" class="cIbmUri"></div>  
              <div component="$model/UI2/system/components/justep/panel/panel" class="x-panel x-full pcqIBbei-iosstatusbar cIbmUri" xid="panel1" data-bind="component:{name:'$model/UI2/system/components/justep/panel/panel'}" __cid="cIbmUri">  
                <div class="x-panel-top cIbmUri" xid="top1" component="$model/UI2/system/components/justep/panel/child" data-bind="component:{name:'$model/UI2/system/components/justep/panel/child'}" __cid="cIbmUri"> 
                  <div component="$model/UI2/system/components/justep/titleBar/titleBar" class="x-titlebar cIbmUri" xid="titleBar1" data-bind="component:{name:'$model/UI2/system/components/justep/titleBar/titleBar'}" data-config="{&#34;title&#34;:&#34;活动列表&#34;}" __cid="cIbmUri"> 
                    <div class="x-titlebar-left cIbmUri" xid="left1" __cid="cIbmUri"></div>  
                    <div class="x-titlebar-title cIbmUri" xid="title1" __cid="cIbmUri">活动列表</div>  
                    <div class="x-titlebar-right  cIbmUri" xid="right1" __cid="cIbmUri"> 
                      <div class="empty cIbmUri" __cid="cIbmUri"></div>  
                      <a component="$model/UI2/system/components/justep/button/button" class="btn btn-default btn-only-icon cIbmUri" xid="button1" data-bind="component:{name:'$model/UI2/system/components/justep/button/button'}" data-events="onClick:createActivity" data-config="{&#34;icon&#34;:&#34;linear linear-bus&#34;,&#34;label&#34;:&#34;button&#34;}" __cid="cIbmUri"> 
                        <i xid="i1" class="linear linear-bus cIbmUri" __cid="cIbmUri"></i>  
                        <span xid="span1" __cid="cIbmUri" class="cIbmUri"></span> 
                      </a> 
                    </div> 
                  </div> 
                </div>  
                <div class="x-panel-content cIbmUri" xid="content1" style="background-color:#f2f2f2;" component="$model/UI2/system/components/justep/panel/child" data-bind="component:{name:'$model/UI2/system/components/justep/panel/child'}" __cid="cIbmUri"> 
                  <div component="$model/UI2/system/components/justep/panel/panel" class="x-panel x-full pcmaUJNr-iosstatusbar cIbmUri" xid="panel2" data-bind="component:{name:'$model/UI2/system/components/justep/panel/panel'}" __cid="cIbmUri"> 
                    <div class="x-panel-content cIbmUri" xid="content2" component="$model/UI2/system/components/justep/panel/child" data-bind="component:{name:'$model/UI2/system/components/justep/panel/child'}" __cid="cIbmUri"> 
                      <div component="$model/UI2/system/components/justep/contents/contents" class="x-contents x-full cIbmUri" xid="contents1" data-bind="component:{name:'$model/UI2/system/components/justep/contents/contents'}" data-config="{&#34;active&#34;:0}" __cid="cIbmUri"> 
                        <div class="x-contents-content  x-scroll-view active cIbmUri" xid="content3" component="$model/UI2/system/components/justep/contents/content" data-bind="component:{name:'$model/UI2/system/components/justep/contents/content'}" data-events="onActive:content3Active" __cid="cIbmUri"> 
                          <div class="x-scroll cIbmUri" component="$model/UI2/system/components/justep/scrollView/scrollView" xid="scrollView1" data-bind="component:{name:'$model/UI2/system/components/justep/scrollView/scrollView'}" __cid="cIbmUri"> 
                            <div class="x-content-center x-pull-down container cIbmUri" xid="div1" __cid="cIbmUri"> 
                              <i class="x-pull-down-img glyphicon x-icon-pull-down cIbmUri" xid="i4" __cid="cIbmUri"></i>  
                              <span class="x-pull-down-label cIbmUri" xid="span4" __cid="cIbmUri">下拉刷新...</span> 
                            </div>  
                            <div class="x-scroll-content cIbmUri" xid="div2" __cid="cIbmUri"> 
                              <div component="$model/UI2/system/components/justep/list/list" class="x-list x-cards cIbmUri" xid="list2" data-bind="component:{name:'$model/UI2/system/components/justep/list/list'}" data-config="{&#34;data&#34;:&#34;activityData&#34;,&#34;limit&#34;:5}" __cid="cIbmUri"> 
                                <ul class="x-list-template hide cIbmUri" xid="listTemplateUl2" __cid="cIbmUri" data-bind="foreach:{data:$model.foreach_list2($element),afterRender:$model.foreach_afterRender_list2.bind($model,$element)}"> 
                                  <li xid="li2" style="background-color:white;" __cid="cIbmUri" class="cIbmUri"> 
                                    <div component="$model/UI2/system/components/justep/row/row" class="x-row cIbmUri" xid="row2" data-bind="component:{name:'$model/UI2/system/components/justep/row/row'},event:{click:$model._callModelFn.bind($model, 'row2Click')}" __cid="cIbmUri"> 
                                      <div class="x-col cIbmUri" xid="col4" __cid="cIbmUri" data-bind="event:{click:$model._callModelFn.bind($model, 'signin')}"> 
                                        <div component="$model/UI2/system/components/justep/labelEdit/labelEdit" class="x-label-edit x-label30 cIbmUri" xid="labelEdit1" data-bind="component:{name:'$model/UI2/system/components/justep/labelEdit/labelEdit'}" __cid="cIbmUri"> 
                                          <label class="x-label cIbmUri" xid="label1" style="font-size:16px;font-weight:bold;" __cid="cIbmUri">活动名称</label>  
                                          <div component="$model/UI2/system/components/justep/output/output" class="x-output x-edit cIbmUri" xid="output10" style="font-size:16px;font-weight:bold;" data-bind="component:{ref:ref(&#34;name&#34;),name:'$model/UI2/system/components/justep/output/output'}" __cid="cIbmUri"></div> 
                                        </div>  
                                        <div component="$model/UI2/system/components/justep/labelEdit/labelEdit" class="x-label-edit x-label30 cIbmUri" xid="labelEdit2" data-bind="component:{name:'$model/UI2/system/components/justep/labelEdit/labelEdit'}" __cid="cIbmUri"> 
                                          <label class="x-label cIbmUri" xid="label2" __cid="cIbmUri">开始时间</label>  
                                          <div component="$model/UI2/system/components/justep/output/output" class="x-output x-edit cIbmUri" xid="output11" data-bind="component:{ref:ref(&#34;beginData&#34;),name:'$model/UI2/system/components/justep/output/output'}" __cid="cIbmUri"></div> 
                                        </div>  
                                        <div component="$model/UI2/system/components/justep/labelEdit/labelEdit" class="x-label-edit x-label30 cIbmUri" xid="labelEdit3" data-bind="component:{name:'$model/UI2/system/components/justep/labelEdit/labelEdit'}" __cid="cIbmUri"> 
                                          <label class="x-label cIbmUri" xid="label3" __cid="cIbmUri">结束时间</label>  
                                          <div component="$model/UI2/system/components/justep/output/output" class="x-output x-edit cIbmUri" xid="output12" data-bind="component:{ref:ref(&#34;endData&#34;),name:'$model/UI2/system/components/justep/output/output'}" __cid="cIbmUri"></div> 
                                        </div> 
                                      </div>  
                                      <div class="x-col x-col-fixed cIbmUri" xid="col3" style="width:auto;" __cid="cIbmUri"> 
                                        <div component="$model/UI2/system/components/justep/row/row" class="x-row cIbmUri" xid="row3" data-bind="component:{name:'$model/UI2/system/components/justep/row/row'}" __cid="cIbmUri"> 
                                          <div class="x-col cIbmUri" xid="col2" __cid="cIbmUri"></div> 
                                        </div>  
                                        <div component="$model/UI2/system/components/justep/row/row" class="x-row cIbmUri" xid="row1" style="height:46px;" data-bind="component:{name:'$model/UI2/system/components/justep/row/row'}" __cid="cIbmUri"> 
                                          <div class="x-col cIbmUri" xid="col1" __cid="cIbmUri"> 
                                            <a component="$model/UI2/system/components/justep/button/button" class="btn btn-default cIbmUri" xid="button3" style="height:36px;" data-bind="component:{name:'$model/UI2/system/components/justep/button/button'},event:{click:$model._callModelFn.bind($model, 'saoClick')}" data-config="{&#34;label&#34;:&#34;扫码签到&#34;}" __cid="cIbmUri"> 
                                              <i xid="i3" __cid="cIbmUri" class="cIbmUri"></i>  
                                              <span xid="span3" __cid="cIbmUri" class="cIbmUri">扫码签到</span> 
                                            </a> 
                                          </div> 
                                        </div>  
                                        <div component="$model/UI2/system/components/justep/row/row" class="x-row cIbmUri" xid="row4" data-bind="component:{name:'$model/UI2/system/components/justep/row/row'}" __cid="cIbmUri"> 
                                          <div class="x-col cIbmUri" xid="col7" __cid="cIbmUri"></div> 
                                        </div> 
                                      </div> 
                                    </div>  
                                    <div xid="div6" style="border-bottom: 1px #ccc solid;" __cid="cIbmUri" class="cIbmUri"></div> 
                                  </li> 
                                </ul> 
                              </div> 
                            </div>  
                            <div class="x-content-center x-pull-up cIbmUri" xid="div3" __cid="cIbmUri"> 
                              <span class="x-pull-up-label cIbmUri" xid="span5" __cid="cIbmUri">加载更多...</span> 
                            </div> 
                          </div> 
                        </div>  
                        <span xid="tips1" class="center-block cIbmUri" style="text-align:center;" component="$model/UI2/system/components/justep/contents/content" data-bind="component:{name:'$model/UI2/system/components/justep/contents/content'}" __cid="cIbmUri"></span> 
                      </div> 
                    </div>  
                    <style __cid="cIbmUri" class="cIbmUri">.x-panel.pcmaUJNr-iosstatusbar >.x-panel-top {height: 0px;}.x-panel.pcmaUJNr-iosstatusbar >.x-panel-content { top: 0px;bottom: nullpx;}.x-panel.pcmaUJNr-iosstatusbar >.x-panel-bottom {height: nullpx;}.iosstatusbar .x-panel.pcmaUJNr-iosstatusbar >.x-panel-top,.iosstatusbar .x-panel .x-panel-content .x-has-iosstatusbar.x-panel.pcmaUJNr-iosstatusbar >.x-panel-top {height: 0px;}.iosstatusbar .x-panel.pcmaUJNr-iosstatusbar >.x-panel-content,.iosstatusbar .x-panel .x-panel-content .x-has-iosstatusbar.x-panel.pcmaUJNr-iosstatusbar >.x-panel-content { top: 0px;}.iosstatusbar .x-panel .x-panel-content .x-panel.pcmaUJNr-iosstatusbar >.x-panel-top {height: 0px;}.iosstatusbar .x-panel .x-panel-content .x-panel.pcmaUJNr-iosstatusbar >.x-panel-content {top: 0px;}</style> 
                  </div> 
                </div>  
                <style __cid="cIbmUri" class="cIbmUri">.x-panel.pcqIBbei-iosstatusbar >.x-panel-top {height: 48px;}.x-panel.pcqIBbei-iosstatusbar >.x-panel-content { top: 48px;bottom: nullpx;}.x-panel.pcqIBbei-iosstatusbar >.x-panel-bottom {height: nullpx;}.iosstatusbar .x-panel.pcqIBbei-iosstatusbar >.x-panel-top,.iosstatusbar .x-panel .x-panel-content .x-has-iosstatusbar.x-panel.pcqIBbei-iosstatusbar >.x-panel-top {height: 68px;}.iosstatusbar .x-panel.pcqIBbei-iosstatusbar >.x-panel-content,.iosstatusbar .x-panel .x-panel-content .x-has-iosstatusbar.x-panel.pcqIBbei-iosstatusbar >.x-panel-content { top: 68px;}.iosstatusbar .x-panel .x-panel-content .x-panel.pcqIBbei-iosstatusbar >.x-panel-top {height: 48px;}.iosstatusbar .x-panel .x-panel-content .x-panel.pcqIBbei-iosstatusbar >.x-panel-content {top: 48px;}</style> 
              </div>  
              <span component="$model/UI2/system/components/justep/windowDialog/windowDialog" xid="dialog" style="top:11px;left:90px;" data-bind="component:{name:'$model/UI2/system/components/justep/windowDialog/windowDialog'}" data-config="{&#34;src&#34;:&#34;$model/UI2/weixin/dialog4Signin.w&#34;,&#34;status&#34;:&#34;normal&#34;}" __cid="cIbmUri" class="cIbmUri">  
                <div class="x-dialog-overlay cIbmUri" __cid="cIbmUri"></div>  
                <div class="x-dialog cIbmUri" style="display:none;" __cid="cIbmUri">  
                  <div class="x-dialog-title cIbmUri" __cid="cIbmUri"> 
                    <button class="close cIbmUri" __cid="cIbmUri"> 
                      <span __cid="cIbmUri" class="cIbmUri">×</span> 
                    </button>  
                    <div class="x-dialog-title-text cIbmUri" __cid="cIbmUri"></div> 
                  </div>  
                  <div class="x-dialog-body cIbmUri" __cid="cIbmUri"></div> 
                </div> 
              </span> 
            </div>
          </div>
        </div>
      </div> 
    </div> 
  </div> 
</div></div>
        
        <div id="downloadGCF" style="display:none;padding:50px;">
        	<span>您使用的浏览器需要下载插件才能使用, </span>
        	<a id="downloadGCFLink" href="#">下载地址</a>
        	<p>(安装后请重新打开浏览器)</p>
        </div>
    	<script>
    	
    	            //判断浏览器, 判断GCF
    	 			var browser = {
    			        isIe: function () {
    			            return navigator.appVersion.indexOf("MSIE") != -1;
    			        },
    			        navigator: navigator.appVersion,
    			        getVersion: function() {
    			            var version = 999; // we assume a sane browser
    			            if (navigator.appVersion.indexOf("MSIE") != -1)
    			                // bah, IE again, lets downgrade version number
    			                version = parseFloat(navigator.appVersion.split("MSIE")[1]);
    			            return version;
    			        }
    			    };
    				function isGCFInstalled(){
    			      try{
    			        var i = new ActiveXObject('ChromeTab.ChromeFrame');
    			        if (i) {
    			          return true;
    			        }
    			      }catch(e){}
    			      return false;
    				}
    	            //判断浏览器, 判断GCF
    	            var __continueRun = true;
    				if (browser.isIe() && (browser.getVersion() < 10) && !isGCFInstalled()) {
    					document.getElementById("applicationHost").style.display = 'none';
    					document.getElementById("downloadGCF").style.display = 'block';
    					var downloadLink = "/" + location.pathname.match(/[^\/]+/)[0] + "/v8.msi";
    					document.getElementById("downloadGCFLink").href = downloadLink; 
    					__continueRun = false;
    	            }
		 	
    	</script>
        
        <script id="_requireJS" src="../system/lib/require/require.2.1.10.js"> </script>
        <script src="../system/core.min.js"></script><script src="../system/common.min.js"></script><script src="../system/components/comp.min.js"></script><script id="_mainScript">
        
			if (__continueRun) {
                window.__justep.cssReady = function(fn){
                	var promises = [];
                	for (var p in window.__justep.__ResourceEngine.__loadingCss){
                		if(window.__justep.__ResourceEngine.__loadingCss.hasOwnProperty(p))
                			promises.push(window.__justep.__ResourceEngine.__loadingCss[p].promise());
                	}
                	$.when.apply($, promises).done(fn);
                };
                
            	window.__justep.__ResourceEngine = {
            		readyRegExp : navigator.platform === 'PLAYSTATION 3' ? /^complete$/ : /^(complete|loaded)$/,
            		url: window.location.href,	
            		/*contextPath: 不包括语言 */
            		contextPath: "",
            		serverPath: "",
            		__loadedJS: [],
            		__loadingCss: {},
            		onLoadCss: function(url, node){
            			if (!this.__loadingCss[url]){
            				this.__loadingCss[url] = $.Deferred();	
                			if (node.attachEvent &&
                                    !(node.attachEvent.toString && node.attachEvent.toString().indexOf('[native code') < 0) &&
                                    !(typeof opera !== 'undefined' && opera.toString() === '[object Opera]')) {
                                node.attachEvent('onreadystatechange', this.onLinkLoad.bind(this));
                            } else {
                                node.addEventListener('load', this.onLinkLoad.bind(this), false);
                                node.addEventListener('error', this.onLinkError.bind(this), false);
                            }
            			}
            		},
            		
            		onLinkLoad: function(evt){
            	        var target = (evt.currentTarget || evt.srcElement);
            	        if (evt.type === 'load' ||
                                (this.readyRegExp.test(target.readyState))) {
            	        	var url = target.getAttribute("href");
            	        	if (url && window.__justep.__ResourceEngine.__loadingCss[url]){
            	        		window.__justep.__ResourceEngine.__loadingCss[url].resolve(url);
            	        	}
                        }
            		},
            		
            		onLinkError: function(evt){
            	        var target = (evt.currentTarget || evt.srcElement);
        	        	var url = target.getAttribute("href");
        	        	if (url && window.__justep.__ResourceEngine.__loadingCss[url]){
        	        		window.__justep.__ResourceEngine.__loadingCss[url].resolve(url);
        	        	}
            		},
            		
            		initContextPath: function(){
            			var baseURL = document.getElementById("_requireJS").src;
            			var before = location.protocol + "//" + location.host;
            			var after = "/system/lib/require/require.2.1.10";
            			var i = baseURL.indexOf(after);
            			if (i !== -1){
    	        			var middle = baseURL.substring(before.length, i);
    						var items = middle.split("/");
    						
    						
    						if ((items[items.length-1].indexOf("v_") === 0) 
    								&& (items[items.length-1].indexOf("l_") !== -1)
    								&& (items[items.length-1].indexOf("s_") !== -1)
    								&& (items[items.length-1].indexOf("d_") !== -1)
    								|| (items[items.length-1]=="v_")){
    							items.splice(items.length-1, 1);
    						}
    						
    						
    						if (items.length !== 1){
    							window.__justep.__ResourceEngine.contextPath = items.join("/");
    						}else{
    							window.__justep.__ResourceEngine.contextPath = before;
    						}
    						var index = window.__justep.__ResourceEngine.contextPath.lastIndexOf("/");
    						if (index != -1){
    							window.__justep.__ResourceEngine.serverPath = window.__justep.__ResourceEngine.contextPath.substr(0, index);
    						}else{
    							window.__justep.__ResourceEngine.serverPath = window.__justep.__ResourceEngine.contextPath;
    						}
            			}else{
            				throw new Error(baseURL + " hasn't  " + after);
            			}
            		},
            	
            		loadJs: function(urls){
            			if (urls && urls.length>0){
            				var loadeds = this._getResources("script", "src").concat(this.__loadedJS);
    	       				for (var i=0; i<urls.length; i++){
								var url = urls[i];
    	        				if(!this._isLoaded(url, loadeds)){
    	        					this.__loadedJS[this.__loadedJS.length] = url;
    	        					/*
    	        					var script = document.createElement("script");
    	        					script.src = url;
    	        					document.head.appendChild(script);
    	        					*/
    	        					//$("head").append("<script  src='" + url + "'/>");
									var url = require.toUrl("$UI" + url);
    	        					$.ajax({
    	        						url: url,
    	        						dataType: "script",
    	        						cache: true,
    	        						async: false,
    	        						success: function(){}
    	        						});
    	        				} 
    	       				}
            			}
            		},
            		
            		loadCss: function(styles){
           				var loadeds = this._getResources("link", "href");
            			if (styles && styles.length>0){
            				for (var i=0; i<styles.length; i++){
    	       					var url = window.__justep.__ResourceEngine.contextPath + styles[i].url
    	        				if(!this._isLoaded(url, loadeds)){
    	        					var include = styles[i].include || "";
    	        					var link = $("<link type='text/css' rel='stylesheet' href='" + url + "' include='" + include + "'/>");
    	        					this.onLoadCss(url, link[0]);
    	        					$("head").append(link);
    	        				} 
            				}
            			}
            			
            		},
            		
            		
            		_isLoaded: function(url, loadeds){
            			if (url){
            				var newUrl = "";
            				var items = url.split("/");
            				var isVls = false;
            				for (var i=0; i<items.length; i++){
            					if (isVls){
                					newUrl += "/" + items[i];
            					}else{
                					if (items[i] && (items[i].indexOf("v_")===0)
            								&& (items[i].indexOf("l_")!==-1)
            								&& (items[i].indexOf("s_")!==-1)
            								&& (items[i].indexOf("d_")!==-1)
            								|| (items[i]=="v_")){
                						isVls = true;
                					}
            					}
            				}
            				if (!newUrl)
            					newUrl = url;
            				
            				for (var i=0; i<loadeds.length; i++){
								var originUrl = this._getOriginUrl(loadeds[i]);
								if (originUrl && (originUrl.indexOf(newUrl)!==-1)){
									return true;
								}
    						}
            			}
    					return false;
            		},

					_getOriginUrl: function(url){
						var result = "";
						if (url && (url.indexOf(".md5_")!==-1)){
							url = url.split("#")[0];
							url = url.split("?")[0];
							var items = url.split(".");
							for (var i=0; i<items.length; i++){
								if ((i===items.length-2) && (items[i].indexOf("md5_")!==-1)){
									continue;
								}else{
									if (i>0) result += ".";
									result += items[i];
								}
							}
						}else{
							result = url;
						}
						return result;
					},
            		
            		_getResources: function(tag, attr){
    					var result = [];
    					var scripts = $(tag);
    					for (var i=0; i<scripts.length; i++){
    						var v = scripts[i][attr];
    						if (v){
    							result[result.length] = v;
    						}
    					}
    					return result;
            		}
            	};
            	
            	window.__justep.__ResourceEngine.initContextPath();
    			requirejs.config({
    				baseUrl: window.__justep.__ResourceEngine.contextPath + '/weixin',
    			    paths: {
    			    	/* 解决require.normalizeName与require.toUrl嵌套后不一致的bug   */
    			    	'$model/UI2/v_': window.__justep.__ResourceEngine.contextPath + '',
    			    	'$model/UI2': window.__justep.__ResourceEngine.contextPath + '',
    			    	'$model': window.__justep.__ResourceEngine.serverPath,
    			        'text': window.__justep.__ResourceEngine.contextPath + '/system/lib/require/text.2.0.10',
    			        'bind': window.__justep.__ResourceEngine.contextPath + '/system/lib/bind/bind',
    			        'jquery': window.__justep.__ResourceEngine.contextPath + '/system/lib/jquery/jquery-1.11.1.min'
    			    },
    			    map: {
    				        '*': {
    				            res: '$model/UI2/system/lib/require/res',
    				            cordova: '$model/UI2/system/lib/require/cordova',
    				            w: '$model/UI2/system/lib/require/w',
    				            css: '$model/UI2/system/lib/require/css'
    				        }
    				},
    				waitSeconds: 300
    			});
    			
    			requirejs(['require', 'jquery', '$model/UI2/system/lib/base/composition', '$model/UI2/system/lib/base/url', '$model/UI2/system/lib/route/hashbangParser', '$model/UI2/system/components/justep/versionChecker/versionChecker', '$model/UI2/system/components/justep/loadingBar/loadingBar', '$model/UI2/system/lib/jquery/domEvent',  '$model/UI2/system/lib/cordova/cordova'],  function (require, $, composition, URL, HashbangParser,versionChecker) { 
    				document.addEventListener('deviceready', function() {
    	                if (navigator && navigator.splashscreen && navigator.splashscreen.hide) {
    	                	/*延迟隐藏，视觉效果更理想*/
    	                	setTimeout(function() {navigator.splashscreen.hide();}, 800);
    	                }
    	            }, false);
					setTimeout(function(){
						versionChecker.check();
					},2000);
    				var context = {};
    				context.model = '$model/UI2/weixin/index.w' + (document.location.search || "");
    				context.view = $('#applicationHost').children()[0];
    				var element = document.getElementById('applicationHost');

					    				
    				
    				var ownerid = new URL(window.__justep.__ResourceEngine.url).getParam("$ownerid");
    				var pwindow = opener;
    				if (!pwindow && window.parent && window.parent.window){
    					pwindow = window.parent.window;
    				}
    				if(ownerid && pwindow 
    						&& pwindow.__justep && pwindow.__justep.windowOpeners
    						&& pwindow.__justep.windowOpeners[ownerid]
    						&& $.isFunction(pwindow.__justep.windowOpeners[ownerid].sendToWindow)){
    					window.__justep.setParams = function(params){
    						/* 给windowOpener提供再次传参数的接口  */
    						params = params || {};
    						composition.setParams(document.getElementById('applicationHost'), params);
    					};
    					var winOpener = pwindow.__justep.windowOpeners[ownerid];
    					if(winOpener) winOpener.window = window;
    					$(window).unload(function(event){
    						if(winOpener && winOpener.dispatchCloseEvent) winOpener.dispatchCloseEvent();
    					});
    					var params = winOpener.sendToWindow();
						context.owner = winOpener;
						context.params = params || {};
	        			composition.compose(element, context);
    				}else{
        				var params =  {};
    					var state = new HashbangParser(window.location.hash).parse().__state;
    					if (state){
    						params = state.get("");
    						try{
    							params = JSON.parse(params);
    							if (params.hasOwnProperty("__singleValue__")){
    								params = params.__singleValue__;
    							}
    						}catch(e1){}
    					}
    					context.noUpdateState = true;
        				context.params = params;
        				composition.compose(element, context);
    				}
    			});    
            }
		 	
        </script>
    </body>
</html>