<%@ page language="java" pageEncoding="UTF-8"%>
<nav class="navbar navbar-default" role="navigation">
	<div class="navbar-header">
    	<!-- <a class="navbar-brand" href="#">普泉</a>  -->
    	<img data-src="holder.js/300x50/text:logo" />
  	</div>
	<div class="collapse navbar-collapse">
		<ul class="nav navbar-nav">
			<c:forEach items="${shiroEntity.menus}" var="pmenu">
				<li class="dropdown">
					<a href="#" id="${pmenu.id}">
						<span class="glyphicon glyphicon-home"></span>
						<span class="nav-title">${pmenu.resourceName}</span>
					</a>
				</li>
			</c:forEach>
		</ul>
	    <ul class="nav navbar-nav navbar-right">
	    	<li class="dropdown">
	        	<a href="#" class="dropdown-toggle" data-toggle="dropdown"><shiro:principal/> 
	        	<b class="caret"></b></a>
	        	<ul class="dropdown-menu">
		          	<li>
						<a href="#">
							<span class="glyphicon glyphicon-file"></span> 用户设置
						</a>
					</li>
	          		<li class="divider"></li>
	          		<li>
						<a href="${ctx}/logout">
							<span class="glyphicon glyphicon-log-out"></span> 退出系统
						</a>
					</li>
	        	</ul>
	      	</li>
	    </ul>
	</div>
</nav>

<script>
	$(".navbar-nav .dropdown a[id]").click(function(e) {
		$(this).parent().siblings().removeClass("active");
		$(this).parent().addClass("active");
		// 点击父菜单时获取子菜单界面
		$.get("${ctx}/module/" + $(this).attr("id"), function(resp) {
			$(".data-content").html(resp);
			Holder.run();
		});
	});
</script>