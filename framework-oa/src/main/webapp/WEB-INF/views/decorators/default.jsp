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
	<div class="main-container">
		<div class="panel panel-primary">
			<shiro:user>
				<!-- 导航 -->
				<%@ include file="/WEB-INF/views/include/navbar.jsp"%>
			</shiro:user>
			<sitemesh:body />
		</div>
	</div>
	<!-- 页脚 -->
	<%@ include file="/WEB-INF/views/include/footer.jsp"%>
</body>
<script type="text/javascript">
	var currentLocation = window.location.pathname.split("/")[window.location.pathname
			.split("/").length - 1];
	if (currentLocation != "" && typeof (currentLocation) != "undefined") {
		$.get("${ctx}/resource/breadcrumb?path=" + currentLocation, function(
				resp) {
			if (resp && resp != "") {
				$('.data-content').prepend(resp);
			}
		});
	}
</script>
</html>