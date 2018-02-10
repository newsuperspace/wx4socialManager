<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib  uri="/struts-tags"  prefix="s" %>
 <%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>   
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
<!-- Bootstrap core CSS -->
<link href="${pageContext.request.contextPath}/css/bootstrap.css" rel="stylesheet">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap-theme.css" type="text/css" />
<link href="${pageContext.request.contextPath}/css/dashboard.css" rel="stylesheet">
</head>

<body>
<!--  ==============================显示详情的浮动窗口================================= -->
<div class="modal fade" id="infoModal" tabindex="-1" role="dialog"
		aria-labelledby="exampleModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"
						aria-label="Close"> <span aria-hidden="true">&times;</span> </button>
        <h4 class="modal-title" >活动详情</h4>
      </div>
      <div class="modal-body"> 
        <!-- 模态框内容——————开始 -->
        <div class="row">
        	<div class="col-md-4">
            	<img class="img-thumbnail"  width="200px"  height="200px" src="#"  id="info_codePath"/>
            </div>
            <div class="col-md-8">
            	<div class="row">
                	<div class="col-md-3"><strong>名书</strong></div>
                    <div class="col-md-9"  id="info_name"></div>
                </div>
                <div class="row">
                	<div class="col-md-3"><strong>售价</strong></div>
                    <div class="col-md-9" id="info_price"></div>
                </div>
                <div class="row">
                	<div class="col-md-3"><strong>ISBN</strong></div>
                    <div class="col-md-9" id="info_isbn"></div>
                </div>
                <div class="row">
                	<div class="col-md-3"><strong>描述</strong></div>
                    <div class="col-md-9" id="info_description"></div>
                </div>
            </div>
        </div>
        <!-- 模态框内容——————结束 -->
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
      </div>
    </div>
  </div>
</div>



<!-- =======================页面主题结构部分==================== -->
<div class="col-sm-12  col-md-12  main">
  <div class="row" style="vertical-align:middle">
    <h2 class="sub-header col-md-8">
    	活动详情列
    </h2>
    	<div class="col-md-4  col-xs-push-2" style="margin-top:30px"> 
             <a href="javascript:history.go(-1);">
    			<button type="button" class="btn btn-primary btn-sm">返回 <span class="glyphicon glyphicon-share-alt"></span></button>
    		</a> 
    	</div>
    </div>
  <div class="table-responsive">
    <table class="table table-striped table-hover">
      <thead>
        <tr>
          <th>名称</th>
          <th>描述</th>
          <th>开始时间</th>
          <th>结束时间</th>
          <th>积分</th>
          <th>操作</th>
        </tr>
      </thead>
      <tbody>
      <s:iterator  value="#activities">
      	<tr>
      		<!-- 名称、描述、开始和结束时间、积分 -->
      		<td><s:property value="name" /></td>
      		<td><s:property value="description"/></td>
      		<td><s:property value="beginData"/></td>
      		<td><s:property value="endData"/></td>
      		<td><s:property value="score"/></td>
            <!-- 操作 -->
            <td>
	            <s:a href="javascript:activityModal.op.commonOp.getVisitors('%{aid}')">
	            	<button class="btn btn-info btn-xs">统计 <span class="glyphicon glyphicon-equalizer"></span></button>
	            </s:a>
	            <s:a href="javascript:alert('取消活动判断是提前取消则弹出confirm框不是则显示活动已过期的alert。通过ajax请求服务器端，取消是提前取消因此需要将当前活动积分从所有参与用户扣除后再显示取消%{aid}')">
	            	<button class="btn btn-primary btn-xs">取消 <span class="glyphicon glyphicon-qrcode"></span></button>
	            </s:a>
	       	</td>
	       
      	</tr>
      </s:iterator>
      
      </tbody>
    </table>
  </div>
  
</div>
</div>
</div>
</body>
<script src="${pageContext.request.contextPath}/js/jquery-3.2.0.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
<script src="${pageContext.request.contextPath}/js/activityModal.js"></script>
</html>