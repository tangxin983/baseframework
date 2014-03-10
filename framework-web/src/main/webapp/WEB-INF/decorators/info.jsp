<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<div class="clearfix">
	<div class="profile">
		<a class="thumbnail dropdown-toggle" data-toggle="dropdown" href="#">
			<img id="portrait" src="" />
		</a>
		<ul class="dropdown-menu" role="menu">
			<li>
				<a href="#">
					<span class="glyphicon glyphicon-file"></span> 用户设置
				</a>
			</li>
			<li class="divider"></li>
			<li>
				<a href="${ctx}/logout">
					<span class="glyphicon glyphicon-log-out"></span> 退出系统
				</a>
			</li>
		</ul>
	</div>
	<div class="user-info">
		<span class="name"><shiro:principal/></span>
	</div>
</div>