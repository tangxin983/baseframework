<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title><sitemesh:title /></title>
<%@include file="/WEB-INF/views/include/head.jsp"%>
<sitemesh:head />
</head>
<body>
	<shiro:user>
		<!-- navbar begin -->
		<%@ include file="/WEB-INF/views/include/navbar.jsp"%>
		<!-- navbar end -->
	</shiro:user>

	<div class="container">
		<div class="row">
			<shiro:user>
				<!-- sidebar begin -->
				<div class="col-md-2 bootstrap-admin-col-left" id="sidebar">
					<%@ include file="/WEB-INF/views/include/sidebar.jsp"%>
				</div>
				<!-- sidebar end -->
				<!-- content begin -->
				<div class="col-md-10" id="mainContent">
					<ol class="breadcrumb" id="breadcrumb">
						<a href="#"> <span
							class="glyphicon glyphicon-chevron-left hide-sidebar"
							title="隐藏侧边栏"></span>
						</a>
						<a href="#"> <span
							class="glyphicon glyphicon-chevron-right show-sidebar"
							title="显示侧边栏" style="display: none;"></span>
						</a>
					</ol>
					<sitemesh:body />
				</div>
				<!-- content end -->
			</shiro:user>
			<shiro:guest>
   				<sitemesh:body />	
			</shiro:guest>
		</div>
	</div>

	<!-- footer -->
	<%@ include file="/WEB-INF/views/include/footer.jsp"%>
</body>
<script type="text/javascript">
	$(document).ready(function() {
		if (localStorage.menuId) {
			$.get("${ctx}/breadcrumb?id=" + localStorage.menuId,function(resp) {
				if (resp && resp != "") {
					$("#breadcrumb").append(resp);
				}
			});
		}
	});
</script>
</html>