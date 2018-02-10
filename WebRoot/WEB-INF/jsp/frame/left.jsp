<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
<body style="background-color:#f5f5f5">

<div class="container-fluid">
      <div class="row">
      
      <div class="col-sm-12 col-md-12 sidebar">
          <ul class="nav nav-sidebar">
          <shiro:hasPermission name="user:retrieve">
            <li class="active"><a href="${pageContext.request.contextPath}/userAction_getUserList.action" target="right">用户管理</a></li>
          </shiro:hasPermission>
          <shiro:hasPermission name="activity:retrieve">
            <li><a href="${pageContext.request.contextPath}/activityAction_showAllList.action" target="right">活动管理</a></li>
          </shiro:hasPermission>
          <shiro:hasPermission name="ware:retrieve">
            <li><a href="${pageContext.request.contextPath}/wareAction_showAllList.action"  target="right">物资管理</a></li>
          </shiro:hasPermission>
          </ul>
          
          <ul class="nav nav-sidebar">
          <shiro:hasPermission name="role:retrieve">
            <li><a href="${pageContext.request.contextPath}/roleAction_showAdmins.action" target="right">权限分配</a></li>
          </shiro:hasPermission>
          <shiro:hasPermission name="role:retrieve">
            <li><a href="${pageContext.request.contextPath}/roleAction_showRoles.action" target="right">角色设置</a></li>
          </shiro:hasPermission>
          <shiro:hasPermission name="permission:retrieve">
            <li><a href="${pageContext.request.contextPath}/permissionAction_showPermissions.action" target="right">权限定义</a></li>
          </shiro:hasPermission>
          </ul>
        </div>
        
	</div>
</div>


</body>
<script src="${pageContext.request.contextPath}/js/jquery-3.2.0.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
<script>

	// 当用户点击某一条目的时候，应该将该条目高亮（设置该条目的<a>的父标签<li>的class属性成active即可）
	$().ready(function(e) {
        $("a").unbind("click");
		$("a").bind("click",function(){
			$("li").attr("class","");
			$(this).parent().attr("class","active");	
		});
    });

</script>
</html>
