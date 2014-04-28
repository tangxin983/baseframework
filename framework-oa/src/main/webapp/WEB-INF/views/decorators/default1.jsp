<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title><sitemesh:title /></title>
<%@include file="/WEB-INF/views/include/head1.jsp"%>
<sitemesh:head />
</head>
<body>
	<shiro:user>
		<!-- navbar begin -->
		<%@ include file="/WEB-INF/views/include/navbar1.jsp"%>
		<!-- navbar end -->
	</shiro:user>

	<div class="container">
		<div class="row">
			<!-- sidebar begin -->
			<div class="col-md-2 bootstrap-admin-col-left" id="sidebar">
				<shiro:user>
					<%@ include file="/WEB-INF/views/include/sidebar.jsp"%>
				</shiro:user>
			</div>
			<!-- sidebar end -->
			<!-- content begin -->
			<div class="col-md-10" id="mainContent">

				<ol class="breadcrumb">
					<a href="#"> 
						<span class="glyphicon glyphicon-chevron-left hide-sidebar" title="隐藏侧边栏"></span>
					</a>
					<a href="#"> 
						<span class="glyphicon glyphicon-chevron-right show-sidebar" title="显示侧边栏" style="display: none;"></span>
					</a>
					<li>系统管理</li>
					<li class="active">Data</li>
				</ol>
				<sitemesh:body />
			</div>
			<!-- content end -->
		</div>
	</div>

	<!-- 页脚 -->
	<%@ include file="/WEB-INF/views/include/footer1.jsp"%>
</body>
<script type="text/javascript">
$(document).ready(function() {
	// 边栏隐藏与显示
    $('.hide-sidebar').click(function() {
	  $('#sidebar').hide('fast', function() {
	  	$('#mainContent').removeClass('col-md-10');
	  	$('#mainContent').addClass('col-md-12');
	  	$('.hide-sidebar').hide();
	  	$('.show-sidebar').show();
	  });
	});

	$('.show-sidebar').click(function() {
		$('#mainContent').removeClass('col-md-12');
	   	$('#mainContent').addClass('col-md-10');
	   	$('.show-sidebar').hide();
	   	$('.hide-sidebar').show();
	  	$('#sidebar').show('fast');
	});
	
	// 边栏点击
	$("#sidebarMenu .panel-heading a").click(function(){
		$("#sidebarMenu .panel-heading a").children('span').removeClass('glyphicon-chevron-down');
		$("#sidebarMenu .panel-heading a").children('span').addClass('glyphicon-chevron-right');
		if(!$($(this).attr('href')).hasClass('in')){
			$(this).children('span').removeClass('glyphicon-chevron-right');
			$(this).children('span').addClass('glyphicon-chevron-down');
		}
	});
	$("#sidebarMenu .panel-body a").click(function(){
		$("#sidebarMenu .panel-body a").removeClass("active");
		$(this).addClass("active");
	});
	
	//<c:if test="${not empty rId && not empty prId}">
	$("[href='#collapse${prId}']").click();
	$("#${rId}").click();
	//</c:if>
	//$(".accordion-body a:first i").click();
});
</script>
</html>