<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<shiro:user>
	<!-- ==================== TOP MENU START ==================== -->
	<div class="navbar navbar-inverse navbar-fixed-top">
		<div class="navbar-inner">
			<div class="container-fluid">
				<a class="brand" href="#"><strong class="brandBold">后台管理</strong></a>
				<div class="nav pull-right">
					<form class="navbar-form">
						<div class="input-append">
							<div class="collapsibleContent">
								<a href="#tasksContent" class="sidebar"><span
									class="add-on add-on-middle add-on-mini add-on-dark" id="tasks"><i
										class="icon-tasks"></i><span class="notifyCircle cyan">3</span></span></a>
								<a href="#notificationsContent" class="sidebar"><span
									class="add-on add-on-middle add-on-mini add-on-dark"
									id="notifications"><i class="icon-bell-alt"></i><span
										class="notifyCircle orange">9</span></span></a> 
								<a href="#messagesContent" class="sidebar"><span
									class="add-on add-on-middle add-on-mini add-on-dark"
									id="messages"><i class="icon-comments-alt"></i><span
										class="notifyCircle red">12</span></span></a> 
								<a href="#settingsContent"
									class="sidebar"><span
									class="add-on add-on-middle add-on-mini add-on-dark"
									id="settings"><i class="icon-cog"></i></span></a> 
								<!-- 	
								<a href="#profileContent" class="sidebar"><span
									class="add-on add-on-mini add-on-dark" id="profile"><i
										class="icon-user"></i><span class="username">唐欣</span></span></a>
								 -->
								 <div class="btn-group">	
									<a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
										<i class="icon-user"></i> 
										<shiro:principal/>
										<span class="caret"></span>
									</a>
									<ul class="dropdown-menu">
										<shiro:hasRole name="admin">
											<li><a href="${ctx}/admin/user">Admin Users</a></li>
											<li class="divider"></li>
										</shiro:hasRole>
										<li><a href="${ctx}/profile">Edit Profile</a></li>
										<li><a href="${ctx}/logout">Logout</a></li>
									</ul>
								</div>
							</div>
							<a href="#collapsedSidebarContent" class="collapseHolder sidebar"><span
								class="add-on add-on-mini add-on-dark"><i
									class="icon-sort-down"></i></span></a>
						</div>
					</form>
				</div>
				<!--/.nav-collapse -->
			</div>
		</div>
	</div>
	<!-- ==================== TOP MENU END ==================== -->
	<!-- ==================== MAIN MENU START ==================== -->
	<div class="mainmenu">
		<div class="container-fluid">
			<ul class="nav">
				<li class="left-side active"><a href="${ctx}"><i
						class="icon-dashboard"></i> 首页</a></li>
				<li class="divider-vertical"></li>
				<c:forEach items="${shiroEntity.menus}" var="pmenu">
					<c:if test="${empty pmenu.children}">
						<li><a href="${ctx}${pmenu.uri}"><i class="icon-list"></i>
								${pmenu.resourceName}</a></li>
					</c:if>
					<c:if test="${!empty pmenu.children}">
						<li class="dropdown"><a class="dropdown-toggle"
							data-toggle="dropdown" href="#"> <i class="icon-list"></i>
								${pmenu.resourceName} <span class="label label-pressed">${fn:length(pmenu.children)}</span>
						</a>
							<ul class="dropdown-menu">
								<c:forEach items="${pmenu.children}" var="menu">
									<li><a href="${ctx}${menu.uri}">${menu.resourceName}</a></li>
								</c:forEach>
							</ul></li>
					</c:if>
					<li class="divider-vertical"></li>
				</c:forEach>
			</ul>
		</div>
	</div>
	<!-- ==================== MAIN MENU END ==================== -->
</shiro:user>